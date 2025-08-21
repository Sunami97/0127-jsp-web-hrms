package attendance.model;

import java.util.Date;

/**
 * 출퇴근 단일 레코드를 표현하는 도메인 모델 // 出退勤の単一レコードを表すドメインモデル
 *
 * 매핑 정보 // マッピング情報 - user_status_tbl의 1행에 해당 + JOIN으로 user_tbl.name을 userName에
 * 매핑 // user_status_tblの1行＋JOINでuser_tbl.nameをuserNameへマッピング - status_id →
 * statusId - user_id → userId - status_type → statusType - status_start/end →
 * statusStart/statusEnd (java.util.Date) - is_current('Y'/'N') →
 * current(boolean) : DAO에서 'Y'.equals(...) 로 변환 // DAOで'Y'.equals(...)により変換
 *
 * 뷰 표시 규칙(참고) // ビュー表示ルール（参考） - is_current='N'이면 화면에서 status_type을 "-" 로 표시(DB
 * 값은 변경하지 않음) // is_current='N'なら画面でstatus_typeを"-"表示（DB値は変更しない）
 */
public class AttendanceRecord {

	// PK: user_status_tbl.status_id // PK: user_status_tbl.status_id
	private long statusId;

	// 사용자 ID: user_status_tbl.user_id // ユーザーID: user_status_tbl.user_id
	private String userId;

	// 상태 타입: user_status_tbl.status_type // 状態タイプ: user_status_tbl.status_type
	private String statusType;

	// 시작 시각: user_status_tbl.status_start // 開始時刻: user_status_tbl.status_start
	private Date statusStart;

	// 종료 시각(퇴근 시): user_status_tbl.status_end // 終了時刻（退勤時）:
	// user_status_tbl.status_end
	private Date statusEnd;

	// 진행 중 여부: user_status_tbl.is_current ('Y'→true, 'N'→false) // 進行中かどうか:
	// user_status_tbl.is_current（'Y'→true, 'N'→false）
	private boolean current;

	// 사용자 이름: user_tbl.name (JOIN으로 채움) // 氏名: user_tbl.name（JOINで詰める）
	private String userName;

	/** PK getter // PKゲッター */
	public long getStatusId() {
		return statusId;
	}

	/** PK setter // PKセッター */
	public void setStatusId(long statusId) {
		this.statusId = statusId;
	}

	/** 사용자 ID getter // ユーザーIDゲッター */
	public String getUserId() {
		return userId;
	}

	/** 사용자 ID setter // ユーザーIDセッター */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/** 상태 타입 getter // 状態タイプゲッター */
	public String getStatusType() {
		return statusType;
	}

	/** 상태 타입 setter // 状態タイプセッター */
	public void setStatusType(String statusType) {
		this.statusType = statusType;
	}

	/** 시작 시각 getter // 開始時刻ゲッター */
	public Date getStatusStart() {
		return statusStart;
	}

	/** 시작 시각 setter // 開始時刻セッター */
	public void setStatusStart(Date statusStart) {
		this.statusStart = statusStart;
	}

	/** 종료 시각 getter // 終了時刻ゲッター */
	public Date getStatusEnd() {
		return statusEnd;
	}

	/** 종료 시각 setter // 終了時刻セッター */
	public void setStatusEnd(Date statusEnd) {
		this.statusEnd = statusEnd;
	}

	/** 진행 중 여부 getter // 進行中かどうかゲッター */
	public boolean isCurrent() {
		return current;
	}

	/** 진행 중 여부 setter // 進行中かどうかセッター */
	public void setCurrent(boolean current) {
		this.current = current;
	}

	/** 사용자 이름 getter // 氏名ゲッター */
	public String getUserName() {
		return userName;
	}

	/** 사용자 이름 setter // 氏名セッター */
	public void setUserName(String userName) {
		this.userName = userName;
	}
}
