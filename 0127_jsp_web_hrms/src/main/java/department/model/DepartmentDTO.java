package department.model;

public class DepartmentDTO {

	private int departmentId;      // 부서 ID // 部署ID
	private String departmentName; // 부서 이름 // 部署名
	private int parentId;          // 상위 부서 ID // 親部署ID（上位部署）

	// 생성자 // コンストラクタ
	public DepartmentDTO(int departmentId, String departmentName, int parentId) {
		this.departmentId = departmentId;
		this.departmentName = departmentName;
		this.parentId = parentId;
	}

	// 부서 ID 반환 // 部署ID を返す
	public int getDepartmentId() {
		return departmentId;
	}

	// 부서 이름 반환 // 部署名 を返す
	public String getDepartmentName() {
		return departmentName;
	}

	// 상위 부서 ID 반환 // 親部署ID を返す
	public int getParentId() {
		return parentId;
	}
}
