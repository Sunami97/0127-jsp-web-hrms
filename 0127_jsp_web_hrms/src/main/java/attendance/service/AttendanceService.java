package attendance.service;

import java.sql.Connection;
import java.util.List;

import attendance.dao.AttendanceDao;
import attendance.model.AttendanceRecord;
import jdbc.connection.ConnectionProvider;

/**
 * 출퇴근 비즈니스 로직을 담당하는 서비스 레이어 // 出退勤のビジネスロジックを担当するサービス層
 *
 * 역할 // 役割 - 출근/퇴근 트랜잭션 처리 (INSERT/UPDATE) // 出勤/退勤のトランザクション処理（INSERT/UPDATE） -
 * user_tbl.work_status 동시 갱신 // user_tbl.work_status の同時更新 - 페이지네이션 조회 제공 //
 * ページネーション取得の提供
 *
 * 트랜잭션 정책 // トランザクション方針 - 각 액션(출근/퇴근)은 커넥션 단위 트랜잭션으로 처리하고, 완료 시 commit //
 * 各アクション（出勤/退勤）はコネクション単位のTxで処理し、完了時にcommit - 예외 발생 시 try-with-resources 범위를
 * 벗어나며 자동 롤백(컨테이너/드라이버 설정에 따름) //
 * 例外時はtry-with-resources範囲を抜ける際に自動ロールバック（コンテナ/ドライバ設定による）
 */
public class AttendanceService {
	private final AttendanceDao dao = new AttendanceDao(); // DAO 의존성 // DAO依存性

	/**
	 * 현재 열린(진행 중) 출퇴근 기록 존재 여부 // 現在オープン（進行中）の出退勤記録の有無
	 * 
	 * @param userId 대상 사용자 ID // 対象ユーザーID
	 * @return true=진행 중(Y) 레코드 존재 // true=進行中（Y）のレコードが存在
	 */
	public boolean hasOpen(String userId) throws Exception {
		try (Connection c = ConnectionProvider.getConnection()) {
			return dao.existsOpenStatus(c, userId);
		}
	}

	/**
	 * 출근: 오픈 기록 없을 때만 생성 + work_status=勤務中 // 出勤：オープン記録がない場合のみ作成＋work_status=勤務中 -
	 * 중복 출근 방지: 이미 is_current='Y'가 있으면 INSERT 생략 // 二重出勤防止：既に is_current='Y'
	 * があればINSERT省略 - status_type은 현재 "勤務中"로 저장(표시/분류용) // status_type
	 * は現在「勤務中」で保存（表示/分類用）
	 */
	public void clockIn(String userId) throws Exception {
		try (Connection c = ConnectionProvider.getConnection()) {
			c.setAutoCommit(false); // Tx 시작 // Tx開始
			boolean open = dao.existsOpenStatus(c, userId); // 진행 중 여부 확인 // 進行中の有無確認
			if (!open) {
				dao.insertStart(c, userId, "勤務中"); // 새 레코드 시작 // 新規レコード開始
				dao.updateWorkStatus(c, userId, "勤務中"); // 사용자 현재 상태 갱신 // ユーザー現在状態を更新
			}
			c.commit(); // Tx 커밋 // Txコミット
		}
	}

	/**
	 * 퇴근: 지정된 행 종료 + work_status=退勤 // 退勤：指定行の終了＋work_status=退勤 - 안전장치: status_id +
	 * user_id + is_current='Y' 조건으로만 종료 // 安全策：status_id + user_id + is_current='Y'
	 * の条件でのみ終了
	 * 
	 * @param statusId 종료할 user_status_tbl.status_id // 終了対象の
	 *                 user_status_tbl.status_id
	 */
	public void clockOut(String userId, long statusId) throws Exception {
		try (Connection c = ConnectionProvider.getConnection()) {
			c.setAutoCommit(false); // Tx 시작 // Tx開始
			dao.closeById(c, userId, statusId); // 대상 행 종료 // 対象行を終了
			dao.updateWorkStatus(c, userId, "退勤"); // 사용자 현재 상태=퇴근 // ユーザー現在状態=退勤
			c.commit(); // Tx 커밋 // Txコミット
		}
	}

	/**
	 * 전체 이력(최신순) 조회 // 全履歴（新しい順）取得 - 페이징 미적용 버전(소량 조회용) // ページング未適用版（少量取得用）
	 */
	public List<AttendanceRecord> getAllHistory(String userId) throws Exception {
		try (Connection c = ConnectionProvider.getConnection()) {
			return dao.selectAllByUserDesc(c, userId);
		}
	}

	/**
	 * 페이지 결과 전달용 DTO // ページ結果のためのDTO
	 * 
	 * @param <T> 아이템 타입 // アイテム型
	 */
	public static class PageResult<T> {
		private final java.util.List<T> items; // 현재 페이지 아이템들 // 現在ページのアイテム
		private final int page; // 1부터 시작하는 페이지 번호 // 1始まりのページ番号
		private final int size; // 페이지당 개수 // 1ページあたりの件数
		private final int totalCount; // 전체 개수 // 総件数

		public PageResult(java.util.List<T> items, int page, int size, int totalCount) {
			this.items = items;
			this.page = page;
			this.size = size;
			this.totalCount = totalCount;
		}

		public java.util.List<T> getItems() {
			return items;
		}

		public int getPage() {
			return page;
		}

		public int getSize() {
			return size;
		}

		public int getTotalCount() {
			return totalCount;
		}

		public int getTotalPages() {
			return (int) Math.ceil(totalCount / (double) size);
		} // 총 페이지 수 // 総ページ数

		public boolean getHasPrev() {
			return page > 1;
		} // 이전 있음? // 前へあり？

		public boolean getHasNext() {
			return page < getTotalPages();
		} // 다음 있음? // 次へあり？
		// 페이지 바(블록) 계산: 5개씩 보여줌 // ページバー（ブロック）計算：5個ずつ表示

		public int getStartPage() {
			int blk = (page - 1) / 5;
			return blk * 5 + 1;
		}

		public int getEndPage() {
			return Math.min(getStartPage() + 4, getTotalPages());
		}
	}

	/**
	 * 페이지네이션: 최신순, 1페이지당 size개 // ページネーション：新しい順、1ページあたりsize件 - Oracle ROWNUM 페이지
	 * 쿼리를 호출하기 위한 startRow/endRow 계산 포함 // Oracle ROWNUMページクエリ呼出のための
	 * startRow/endRow 計算を含む - page/size가 1 미만이면 안전한 기본값으로 보정 // page/size が 1
	 * 未満なら安全なデフォルトに補正
	 */
	public PageResult<attendance.model.AttendanceRecord> getHistoryPage(String userId, int page, int size)
			throws Exception {
		if (page < 1)
			page = 1; // 페이지 최소 1 // ページ最小1
		if (size < 1)
			size = 6; // 기본 6건 // 既定6件

		try (Connection c = ConnectionProvider.getConnection()) {
			int total = dao.countByUser(c, userId); // 총 개수 // 総件数
			int startRow = (page - 1) * size + 1; // 1-based 시작행 // 1始まりの開始行
			int endRow = page * size; // 1-based 종료행 // 1始まりの終了行

			java.util.List<attendance.model.AttendanceRecord> items = total > 0
					? dao.selectPageByUserDesc(c, userId, startRow, endRow)
					: java.util.Collections.emptyList();

			return new PageResult<>(items, page, size, total);
		}
	}
}
