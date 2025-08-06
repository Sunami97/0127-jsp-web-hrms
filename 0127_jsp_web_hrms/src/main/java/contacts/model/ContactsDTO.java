package contacts.model;

import java.util.Date;

public class ContactsDTO {

    String departmentName; // 부서명 / 部署名
    String name;           // 이름 / 名前
    String email;          // 이메일 / メール
    String phone;          // 연락처 / 連絡先
    Date joinDate;         // 입사일 / 入社日
    String position;       // 직급 / 職位
    String loginStatus;    // 로그인 상태 / ログイン状態
    String statusType;     // 상태 유형(연차, 출장 등) / 状態タイプ（休暇、出張など）

    public ContactsDTO() {
        // 기본 생성자 / デフォルトコンストラクタ
    }

    public ContactsDTO(String departmentName, String name, String email, String phone, Date joinDate, String position,
            String loginStatus, String statusType) {
        this.departmentName = departmentName;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.joinDate = joinDate;
        this.position = position;
        this.loginStatus = loginStatus;
        this.statusType = statusType;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
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

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(String loginStatus) {
        this.loginStatus = loginStatus;
    }

    public String getStatusType() {
        return statusType;
    }

    public void setStatusType(String statusType) {
        this.statusType = statusType;
    }

}
