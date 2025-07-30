package myPage.model;

import java.sql.Date;

public class UserDepartmentDTO {
    private String userId;
    private String name;
    private String email;
    private String phone;
    private Date birthDate;
    private Date joinDate;
    private Date retireDate;
    private String position;
    private Integer departmentId;
    private String isAdmin;
    private String empStatus;
    private String workStatus;
    private String loginStatus;
    private String departmentName; // 부서명(JOIN용)

    // Getter & Setter
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public Date getBirthDate() { return birthDate; }
    public void setBirthDate(Date birthDate) { this.birthDate = birthDate; }

    public Date getJoinDate() { return joinDate; }
    public void setJoinDate(Date joinDate) { this.joinDate = joinDate; }

    public Date getRetireDate() { return retireDate; }
    public void setRetireDate(Date retireDate) { this.retireDate = retireDate; }

    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }

    public Integer getDepartmentId() { return departmentId; }
    public void setDepartmentId(Integer departmentId) { this.departmentId = departmentId; }

    public String getIsAdmin() { return isAdmin; }
    public void setIsAdmin(String isAdmin) { this.isAdmin = isAdmin; }

    public String getEmpStatus() { return empStatus; }
    public void setEmpStatus(String empStatus) { this.empStatus = empStatus; }

    public String getWorkStatus() { return workStatus; }
    public void setWorkStatus(String workStatus) { this.workStatus = workStatus; }

    public String getLoginStatus() { return loginStatus; }
    public void setLoginStatus(String loginStatus) { this.loginStatus = loginStatus; }

    public String getDepartmentName() { return departmentName; }
    public void setDepartmentName(String departmentName) { this.departmentName = departmentName; }
}
