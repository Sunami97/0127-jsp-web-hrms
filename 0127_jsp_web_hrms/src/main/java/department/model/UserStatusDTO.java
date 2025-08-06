package department.model;

public class UserStatusDTO {
	private String departmentName; // 부서 이름 // 部署名
	private String position;       // 직책 // 職位
	private String name;           // 사용자 이름 // ユーザー名
	private String statusType;     // 상태 종류 (예: 근무중) // 状態タイプ（例：勤務中）
	private String isCurrent;      // 현재 상태 여부 (Y/N) // 現在の状態かどうか（Y/N）

	// Getter / Setter
	// ゲッター / セッター

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatusType() {
		return statusType;
	}

	public void setStatusType(String statusType) {
		this.statusType = statusType;
	}

	public String getIsCurrent() {
		return isCurrent;
	}

	public void setIsCurrent(String isCurrent) {
		this.isCurrent = isCurrent;
	}
}
