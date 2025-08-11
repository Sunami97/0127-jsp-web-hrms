package user.model;

import java.sql.Date;

public class UserDTO {
	
	private String user_id;
    private String password;
    private String name;
    private String email; 
    private String phone;
    private Date birth_date;
    private Date join_date;
    private Date retire_date;
    private String position;
    private int department_id;
    private String is_admin;
    private String emp_status;
    private String work_status;
    private String login_status;
	
    
    public UserDTO() {
		super();
		// TODO Auto-generated constructor stub
	}


	public UserDTO(String user_id, String password, String name, String email, String phone, Date birth_date,
			Date join_date, Date retire_date, String position, int department_id, String is_admin, String emp_status,
			String work_status, String login_status) {
		super();
		this.user_id = user_id;
		this.password = password;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.birth_date = birth_date;
		this.join_date = join_date;
		this.retire_date = retire_date;
		this.position = position;
		this.department_id = department_id;
		this.is_admin = is_admin;
		this.emp_status = emp_status;
		this.work_status = work_status;
		this.login_status = login_status;
	}


	public String getUser_id() {
		return user_id;
	}


	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public Date getBirth_date() {
		return birth_date;
	}


	public void setBirth_date(Date birth_date) {
		this.birth_date = birth_date;
	}


	public Date getJoin_date() {
		return join_date;
	}


	public void setJoin_date(Date join_date) {
		this.join_date = join_date;
	}


	public Date getRetire_date() {
		return retire_date;
	}


	public void setRetire_date(Date retire_date) {
		this.retire_date = retire_date;
	}


	public String getPosition() {
		return position;
	}


	public void setPosition(String position) {
		this.position = position;
	}


	public int getDepartment_id() {
		return department_id;
	}


	public void setDepartment_id(int department_id) {
		this.department_id = department_id;
	}


	public String getIs_admin() {
		return is_admin;
	}


	public void setIs_admin(String is_admin) {
		this.is_admin = is_admin;
	}


	public String getEmp_status() {
		return emp_status;
	}


	public void setEmp_status(String emp_status) {
		this.emp_status = emp_status;
	}


	public String getWork_status() {
		return work_status;
	}


	public void setWork_status(String work_status) {
		this.work_status = work_status;
	}


	public String getLogin_status() {
		return login_status;
	}


	public void setLogin_status(String login_status) {
		this.login_status = login_status;
	}
    
    
    

}
