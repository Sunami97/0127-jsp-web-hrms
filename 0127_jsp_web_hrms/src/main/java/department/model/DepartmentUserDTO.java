package department.model;

public class DepartmentUserDTO {
	private String departmentName; // 부서 이름 // 部署名
	private String position;       // 직책 // 職位
	private String name;           // 사용자 이름 // ユーザー名
	private String workStatus;     // 근무중, 연차, 출장 등 // 勤務中、年休、出張など

	public DepartmentUserDTO() {
		
	}

	public DepartmentUserDTO(String departmentName, String position, String name, String workStatus) {
		this.departmentName = departmentName;
		this.position = position;
		this.name = name;
		this.workStatus = workStatus;
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

}
