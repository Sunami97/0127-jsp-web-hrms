package myPage.model;

import java.sql.Date;

// 이 클래스는 사용자 정보 + 부서 정보를 한 번에 저장하는 DTO(Data Transfer Object)다
// (즉, 여러 테이블에서 가져온 다양한 정보를 하나의 객체에 담아 전달할 때 사용한다)
public class UserDepartmentDTO {
    // 1. 사용자 아이디를 저장할 String 타입의 userId 변수를 선언한다
    private String userId;
    // 2. 사용자 이름을 저장할 String 타입의 name 변수를 선언한다
    private String name;
    // 3. 이메일 주소를 저장할 String 타입의 email 변수를 선언한다
    private String email;
    // 4. 전화번호를 저장할 String 타입의 phone 변수를 선언한다
    private String phone;
    // 5. 생년월일을 저장할 Date 타입의 birthDate 변수를 선언한다
    private Date birthDate;
    // 6. 입사일을 저장할 Date 타입의 joinDate 변수를 선언한다
    private Date joinDate;
    // 7. 퇴사일을 저장할 Date 타입의 retireDate 변수를 선언한다
    private Date retireDate;
    // 8. 직책(포지션)을 저장할 String 타입의 position 변수를 선언한다
    private String position;
    // 9. 부서ID를 저장할 Integer 타입의 departmentId 변수를 선언한다
    private Integer departmentId;
    // 10. 관리자 여부를 저장할 String 타입의 isAdmin 변수를 선언한다
    private String isAdmin;
    // 11. 근로상태를 저장할 String 타입의 empStatus 변수를 선언한다
    private String empStatus;
    // 12. 근무상태를 저장할 String 타입의 workStatus 변수를 선언한다
    private String workStatus;
    // 13. 로그인상태를 저장할 String 타입의 loginStatus 변수를 선언한다
    private String loginStatus;
    // 14. 부서 이름을 저장할 String 타입의 departmentName 변수를 선언한다
    // (조인해서 얻어오는 값)
    private String departmentName;

    // ---------- Getter & Setter (값을 꺼내고 넣는 메소드) ----------

    // userId 값을 꺼내고 넣는 메소드
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    // name 값을 꺼내고 넣는 메소드
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    // email 값을 꺼내고 넣는 메소드
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    // phone 값을 꺼내고 넣는 메소드
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    // birthDate 값을 꺼내고 넣는 메소드
    public Date getBirthDate() { return birthDate; }
    public void setBirthDate(Date birthDate) { this.birthDate = birthDate; }

    // joinDate 값을 꺼내고 넣는 메소드
    public Date getJoinDate() { return joinDate; }
    public void setJoinDate(Date joinDate) { this.joinDate = joinDate; }

    // retireDate 값을 꺼내고 넣는 메소드
    public Date getRetireDate() { return retireDate; }
    public void setRetireDate(Date retireDate) { this.retireDate = retireDate; }

    // position 값을 꺼내고 넣는 메소드
    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }

    // departmentId 값을 꺼내고 넣는 메소드
    public Integer getDepartmentId() { return departmentId; }
    public void setDepartmentId(Integer departmentId) { this.departmentId = departmentId; }

    // isAdmin 값을 꺼내고 넣는 메소드
    public String getIsAdmin() { return isAdmin; }
    public void setIsAdmin(String isAdmin) { this.isAdmin = isAdmin; }

    // empStatus 값을 꺼내고 넣는 메소드
    public String getEmpStatus() { return empStatus; }
    public void setEmpStatus(String empStatus) { this.empStatus = empStatus; }

    // workStatus 값을 꺼내고 넣는 메소드
    public String getWorkStatus() { return workStatus; }
    public void setWorkStatus(String workStatus) { this.workStatus = workStatus; }

    // loginStatus 값을 꺼내고 넣는 메소드
    public String getLoginStatus() { return loginStatus; }
    public void setLoginStatus(String loginStatus) { this.loginStatus = loginStatus; }

    // departmentName 값을 꺼내고 넣는 메소드
    public String getDepartmentName() { return departmentName; }
    public void setDepartmentName(String departmentName) { this.departmentName = departmentName; }
}
