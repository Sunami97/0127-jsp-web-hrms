package department.model;

public class DepartmentDTO {

	private int departmentId;
	private String departmentName;
	private int parentId;
		
	public DepartmentDTO(int departmentId, String departmentName, int parentId) {
		this.departmentId = departmentId;
		this.departmentName = departmentName;
		this.parentId = parentId;
	}

	public int getDepartmentId() {
		return departmentId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public int getParentId() {
		return parentId;
	}

	
}
