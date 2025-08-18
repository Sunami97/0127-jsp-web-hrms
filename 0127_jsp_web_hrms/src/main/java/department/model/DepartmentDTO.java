package department.model;

public class DepartmentDTO {
	private String departmentName; // 부서 이름 // 部署名
	private String position;       // 직책 // 職位
	private String name;           // 사용자 이름 // ユーザー名
	private String workStatus;     // 근무중, 연차, 출장 등 // 勤務中、年休、出張など
	private String isCurrent;	   // 로그인 상태 (Y/N) // ログイン状態 (Y/N)

	public DepartmentDTO() {
		
	}

	public DepartmentDTO(String departmentName, String position, String name, String workStatus, String isCurrent) {
		this.departmentName = departmentName;
		this.position = position;
		this.name = name;
		this.workStatus = workStatus;
		this.isCurrent = isCurrent;
	}

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

	public String getWorkStatus() {
		return workStatus;
	}

	public void setWorkStatus(String workStatus) {
		this.workStatus = workStatus;
	}
	
	public String getIsCurrent() {
		return isCurrent;
	}

	public void setIsCurrent(String isCurrent) {
		this.isCurrent = isCurrent;
	}

}
