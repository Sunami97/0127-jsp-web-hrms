package admin.dto;


public class AdminDto {
	  private String userId; 
	  private String password; 
	  private String name; 
	  private String email; 
	  private String phone; 
	  private String birthDate; 
	  private String joinDate; 
	  private String retireDate;
	  private String position; 
	  private int departmentId;
	  private String isAdmin; 
	  private String empStatus; 
	  private String workStatus;
	  private String loginStatus;
	  private String departmentName;
	  
	public String getDepartmentName() {
		return departmentName;
	}
	public String getUserId() {
		return userId;
	}
	public String getPassword() {
		return password;
	}
	public String getName() {
		return name;
	}
	public String getEmail() {
		return email;
	}
	public String getPhone() {
		return phone;
	}
	public String getBirthDate() {
		return birthDate;
	}
	public String getJoinDate() {
		return joinDate;
	}
	public String getRetireDate() {
		return retireDate;
	}
	public String getPosition() {
		return position;
	}
	public int getDepartmentId() {
		return departmentId;
	}
	public String getIsAdmin() {
		return isAdmin;
	}
	public String getEmpStatus() {
		return empStatus;
	}
	public String getWorkStatus() {
		return workStatus;
	}
	public String getLoginStatus() {
		return loginStatus;
	}

	//조회용 DTO 照会用DTO
	public AdminDto(String userId, String password, String name, String email, String phone, String birthDate,
			String joinDate, String retireDate, String position, int departmentId, String isAdmin, String empStatus,
			String workStatus, String loginStatus, String departmentName) {
		super();
		this.userId = userId;
		this.password = password;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.birthDate = birthDate;
		this.joinDate = joinDate;
		this.retireDate = retireDate;
		this.position = position;
		this.departmentId = departmentId;
		this.isAdmin = isAdmin;
		this.empStatus = empStatus;
		this.workStatus = workStatus;
		this.loginStatus = loginStatus;
		this.departmentName = departmentName;
	}
	
	//추가용 DTO 追加用DTO
	public AdminDto(String userId, String password, String name, String email, String phone, String birthDate,
			String joinDate, String position, int departmentId, String isAdmin, String empStatus, String workStatus,
			String loginStatus, String departmentName) {
		super();
		this.userId = userId;
		this.password = password;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.birthDate = birthDate;
		this.joinDate = joinDate;
		this.position = position;
		this.departmentId = departmentId;
		this.isAdmin = isAdmin;
		this.empStatus = empStatus;
		this.workStatus = workStatus;
		this.loginStatus = loginStatus;
		this.departmentName = departmentName;
	}
	public AdminDto() {
		super();
	}
	
}
