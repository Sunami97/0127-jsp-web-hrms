package attendance.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import attendance.model.AttendanceRecord;

/**
 * 출퇴근 데이터 접근 객체(DAO) // 出退勤データアクセスオブジェクト（DAO） - 대상 테이블: user_status_tbl（出退勤履歴）,
 * user_tbl（ユーザー） - 책임: 1) 출근 시작/퇴근 종료 레코드 갱신 // 出勤開始/退勤終了レコードの更新 2) work_status
 * 업데이트 // work_status の更新 3) 최신순/페이지네이션 조회 // 新しい順/ページネーション取得
 *
 * 참고: JOIN으로 이름을 표시하려면 AttendanceRecord에 userName 필드가 있어야 함 // JOINで氏名を表示するには
 * AttendanceRecord に userName フィールドが必要
 */
public class AttendanceDao {

	/**
	 * 현재 열린(진행 중) 출퇴근 기록 존재 여부 확인 // 現在オープン（進行中）の出退勤記録の有無を確認
	 * 
	 * @param c      DB 커넥션 // DBコネクション
	 * @param userId 대상 사용자 ID // 対象ユーザーID
	 * @return 진행 중 레코드가 1건 이상이면 true // 進行中レコードが1件以上なら true
	 */
	public boolean existsOpenStatus(Connection c, String userId) throws SQLException {
		final String sql = "SELECT COUNT(*) FROM user_status_tbl WHERE user_id=? AND is_current='Y'";
		try (PreparedStatement ps = c.prepareStatement(sql)) {
			ps.setString(1, userId); // 바인딩: userId // バインド: userId
			try (ResultSet rs = ps.executeQuery()) {
				rs.next();
				return rs.getInt(1) > 0; // 카운트 > 0 → 오픈 상태 존재 // 件数 > 0 → オープン状態あり
			}
		}
	}

	/**
	 * 출근(새 기록 시작) // 出勤（新規レコード開始） - 트리거로 status_id 자동 발번이므로 INSERT에 status_id 포함하지
	 * 않음 // トリガで status_id 自動採番 → INSERTに含めない - status_start=SYSDATE,
	 * is_current='Y' 로 시작 // status_start=SYSDATE, is_current='Y' で開始
	 */
	public int insertStart(Connection c, String userId, String statusType) throws SQLException {
		final String sql = "INSERT INTO user_status_tbl (user_id, status_type, status_start, is_current) "
				+ "VALUES (?, ?, SYSDATE, 'Y')";
		try (PreparedStatement ps = c.prepareStatement(sql)) {
			ps.setString(1, userId);
			ps.setString(2, statusType); // 예: 'WORK' 등 // 例: 'WORK' など
			return ps.executeUpdate(); // 영향 행 수 반환 // 影響行数を返す
		}
	}

	/**
	 * 퇴근(특정 status_id 종료) // 退勤（特定 status_id を終了） - 안전장치: status_id + user_id +
	 * is_current='Y' 조건으로만 종료 // 安全策: status_id + user_id + is_current='Y' の条件でのみ終了
	 * - status_end=SYSDATE, is_current='N' 로 갱신 // status_end=SYSDATE,
	 * is_current='N' に更新
	 */
	public int closeById(Connection c, String userId, long statusId) throws SQLException {
		final String sql = "UPDATE user_status_tbl " + "   SET status_end = SYSDATE, is_current = 'N' "
				+ " WHERE status_id = ? AND user_id = ? AND is_current = 'Y'";
		try (PreparedStatement ps = c.prepareStatement(sql)) {
			ps.setLong(1, statusId);
			ps.setString(2, userId);
			return ps.executeUpdate();
		}
	}

	/**
	 * work_status 업데이트(user_tbl) // work_status の更新（user_tbl） - 예: 출근 시 '勤務中', 퇴근 시
	 * '退勤' // 例: 出勤時は『勤務中』、退勤時は『退勤』
	 */
	public int updateWorkStatus(Connection c, String userId, String workStatus) throws SQLException {
		final String sql = "UPDATE user_tbl SET work_status = ? WHERE user_id = ?";
		try (PreparedStatement ps = c.prepareStatement(sql)) {
			ps.setString(1, workStatus);
			ps.setString(2, userId);
			return ps.executeUpdate();
		}
	}

	/**
	 * 최신순 전체 조회(이름 포함) // 新しい順の全件取得（氏名含む） - user_status_tbl(ust) ∥ user_tbl(u)
	 * JOIN으로 u.name 별칭 user_name 반환 // JOIN で u.name を user_name 別名で返す - ORDER BY
	 * status_start DESC (최신이 위) // 最新が上に来る
	 */
	public List<AttendanceRecord> selectAllByUserDesc(Connection c, String userId) throws SQLException {
		// user_tbl 과 JOIN 하여 name 컬럼을 함께 조회 // user_tbl と JOIN して name カラムも取得
		final String sql = "SELECT ust.status_id, ust.user_id, u.name AS user_name, "
				+ "       ust.status_type, ust.status_start, ust.status_end, ust.is_current "
				+ "  FROM user_status_tbl ust " + "  JOIN user_tbl u ON u.user_id = ust.user_id "
				+ " WHERE ust.user_id=? ORDER BY ust.status_start DESC";
		try (PreparedStatement ps = c.prepareStatement(sql)) {
			ps.setString(1, userId);
			try (ResultSet rs = ps.executeQuery()) {
				List<AttendanceRecord> list = new ArrayList<>();
				while (rs.next()) {
					AttendanceRecord r = new AttendanceRecord();
					r.setStatusId(rs.getLong("status_id")); // PK
					r.setUserId(rs.getString("user_id")); // 사용자 ID // ユーザーID
					r.setUserName(rs.getString("user_name")); // 이름 매핑 // 氏名のマッピング
					r.setStatusType(rs.getString("status_type")); // 상태 타입 // 状態タイプ
					Timestamp s = rs.getTimestamp("status_start");
					Timestamp e = rs.getTimestamp("status_end");
					r.setStatusStart(s != null ? new java.util.Date(s.getTime()) : null); // 시작시각 // 開始時刻
					r.setStatusEnd(e != null ? new java.util.Date(e.getTime()) : null); // 종료시각 // 終了時刻
					r.setCurrent("Y".equals(rs.getString("is_current"))); // 진행중 여부 // 進行中かどうか
					list.add(r);
				}
				return list;
			}
		}
	}

	/**
	 * 해당 유저 출퇴근 내역 총 개수 // 対象ユーザーの出退勤履歴総件数 - 페이지네이션 계산용 // ページネーション計算用
	 */
	public int countByUser(Connection c, String userId) throws SQLException {
		final String sql = "SELECT COUNT(*) FROM user_status_tbl WHERE user_id = ?";
		try (PreparedStatement ps = c.prepareStatement(sql)) {
			ps.setString(1, userId);
			try (ResultSet rs = ps.executeQuery()) {
				rs.next();
				return rs.getInt(1);
			}
		}
	}

	/**
	 * 페이지 조회(최신순) — Oracle ROWNUM 방식 // ページ取得（新しい順）— Oracle ROWNUM 方式 - 내부 서브쿼리에서
	 * 정렬 후 ROWNUM 부여 → BETWEEN 로 범위 슬라이스 // 内部サブクエリでソート後に ROWNUM 付与 → BETWEEN
	 * で範囲スライス - JOIN으로 user_name 포함 // JOIN で user_name を含む
	 * 
	 * @param startRow 1부터 시작하는 시작행 // 1始まりの開始行
	 * @param endRow   1부터 시작하는 종료행 // 1始まりの終了行
	 */
	public List<attendance.model.AttendanceRecord> selectPageByUserDesc(Connection c, String userId, int startRow,
			int endRow) throws SQLException {

		// 내부 정렬 서브쿼리에서 JOIN + user_name 포함 // 内部の並び替えサブクエリで JOIN＋user_name を含む
		final String sql = "SELECT * FROM (" + "  SELECT t.*, ROWNUM rn FROM ("
				+ "    SELECT ust.status_id, ust.user_id, u.name AS user_name, "
				+ "           ust.status_type, ust.status_start, ust.status_end, ust.is_current "
				+ "      FROM user_status_tbl ust " + "      JOIN user_tbl u ON u.user_id = ust.user_id "
				+ "     WHERE ust.user_id=? " + "     ORDER BY ust.status_start DESC" + "  ) t"
				+ ") WHERE rn BETWEEN ? AND ?";

		try (PreparedStatement ps = c.prepareStatement(sql)) {
			ps.setString(1, userId); // 조건 바인딩 // 条件バインド
			ps.setInt(2, startRow); // 시작행 // 開始行
			ps.setInt(3, endRow); // 종료행 // 終了行
			try (ResultSet rs = ps.executeQuery()) {
				List<attendance.model.AttendanceRecord> list = new java.util.ArrayList<>();
				while (rs.next()) {
					attendance.model.AttendanceRecord r = new attendance.model.AttendanceRecord();
					r.setStatusId(rs.getLong("status_id"));
					r.setUserId(rs.getString("user_id"));
					r.setUserName(rs.getString("user_name")); // 이름 매핑 // 氏名のマッピング
					r.setStatusType(rs.getString("status_type"));
					java.sql.Timestamp s = rs.getTimestamp("status_start");
					java.sql.Timestamp e = rs.getTimestamp("status_end");
					r.setStatusStart(s != null ? new java.util.Date(s.getTime()) : null);
					r.setStatusEnd(e != null ? new java.util.Date(e.getTime()) : null);
					r.setCurrent("Y".equals(rs.getString("is_current")));
					list.add(r);
				}
				return list;
			}
		}
	}
}
