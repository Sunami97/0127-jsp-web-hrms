package myPage.model;

import java.sql.Date;

// 이 클래스는 사용자 정보 + 부서 정보를 한 번에 저장하는 DTO(Data Transfer Object)다
// このクラスはユーザー情報 + 部署情報を一度に保存する DTO（データ転送オブジェクト）である
// (즉, 여러 테이블에서 가져온 다양한 정보를 하나의 객체에 담아 전달할 때 사용한다)
// （つまり、複数のテーブルから取得したさまざまな情報を一つのオブジェクトにまとめて渡すために使う）

public class UserDepartmentDTO {
    // 1. 사용자 아이디를 저장할 String 타입의 userId 변수를 선언한다
    // 1. ユーザーIDを保存する String 型の userId 変数を宣言する
    private String userId;

    // 2. 사용자 이름을 저장할 String 타입의 name 변수를 선언한다
    // 2. ユーザー名を保存する String 型の name 変数を宣言する
    private String name;

    // 3. 이메일 주소를 저장할 String 타입의 email 변수를 선언한다
    // 3. メールアドレスを保存する String 型の email 変数を宣言する
    private String email;

    // 4. 전화번호를 저장할 String 타입의 phone 변수를 선언한다
    // 4. 電話番号を保存する String 型の phone 変数を宣言する
    private String phone;

    // 5. 생년월일을 저장할 Date 타입의 birthDate 변수를 선언한다
    // 5. 生年月日を保存する Date 型の birthDate 変数を宣言する
    private Date birthDate;

    // 6. 입사일을 저장할 Date 타입의 joinDate 변수를 선언한다
    // 6. 入社日を保存する Date 型の joinDate 変数を宣言する
    private Date joinDate;

    // 7. 퇴사일을 저장할 Date 타입의 retireDate 변수를 선언한다
    // 7. 退職日を保存する Date 型の retireDate 変数を宣言する
    private Date retireDate;

    // 8. 직책(포지션)을 저장할 String 타입의 position 변수를 선언한다
    // 8. 職位（ポジション）を保存する String 型の position 変数を宣言する
    private String position;

    // 9. 부서ID를 저장할 Integer 타입의 departmentId 변수를 선언한다
    // 9. 部署IDを保存する Integer 型の departmentId 変数を宣言する
    private Integer departmentId;

    // 10. 관리자 여부를 저장할 String 타입의 isAdmin 변수를 선언한다
    // 10. 管理者であるかどうかを保存する String 型の isAdmin 変数を宣言する
    private String isAdmin;

    // 11. 근로상태를 저장할 String 타입의 empStatus 변수를 선언한다
    // 11. 雇用状態を保存する String 型の empStatus 変数を宣言する
    private String empStatus;

    // 12. 근무상태를 저장할 String 타입의 workStatus 변수를 선언한다
    // 12. 勤務状態を保存する String 型の workStatus 変数を宣言する
    private String workStatus;

    // 13. 로그인상태를 저장할 String 타입의 loginStatus 변수를 선언한다
    // 13. ログイン状態を保存する String 型の loginStatus 変数を宣言する
    private String loginStatus;

    // 14. 부서 이름을 저장할 String 타입의 departmentName 변수를 선언한다
    // (조인해서 얻어오는 값)
    // 14. 部署名を保存する String 型の departmentName 変数を宣言する
    // （JOIN で取得する値）
    private String departmentName;


    // ---------- Getter & Setter (값을 꺼내고 넣는 메소드) ----------
    // ---------- Getter & Setter（値を取得・設定するメソッド） ----------

    // userId 값을 꺼내고 넣는 메소드
    // userId の値を取得・設定するメソッド
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    // name 값을 꺼내고 넣는 메소드
    // name の値を取得・設定するメソッド
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    // email 값을 꺼내고 넣는 메소드
    // email の値を取得・設定するメソッド
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    // phone 값을 꺼내고 넣는 메소드
    // phone の値を取得・設定するメソッド
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    // birthDate 값을 꺼내고 넣는 메소드
    // birthDate の値を取得・設定するメソッド
    public Date getBirthDate() { return birthDate; }
    public void setBirthDate(Date birthDate) { this.birthDate = birthDate; }

    // joinDate 값을 꺼내고 넣는 메소드
    // joinDate の値を取得・設定するメソッド
    public Date getJoinDate() { return joinDate; }
    public void setJoinDate(Date joinDate) { this.joinDate = joinDate; }

    // retireDate 값을 꺼내고 넣는 메소드
    // retireDate の値を取得・設定するメソッド
    public Date getRetireDate() { return retireDate; }
    public void setRetireDate(Date retireDate) { this.retireDate = retireDate; }

    // position 값을 꺼내고 넣는 메소드
    // position の値を取得・設定するメソッド
    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }

    // departmentId 값을 꺼내고 넣는 메소드
    // departmentId の値を取得・設定するメソッド
    public Integer getDepartmentId() { return departmentId; }
    public void setDepartmentId(Integer departmentId) { this.departmentId = departmentId; }

    // isAdmin 값을 꺼내고 넣는 메소드
    // isAdmin の値を取得・設定するメソッド
    public String getIsAdmin() { return isAdmin; }
    public void setIsAdmin(String isAdmin) { this.isAdmin = isAdmin; }

    // empStatus 값을 꺼내고 넣는 메소드
    // empStatus の値を取得・設定するメソッド
    public String getEmpStatus() { return empStatus; }
    public void setEmpStatus(String empStatus) { this.empStatus = empStatus; }

    // workStatus 값을 꺼내고 넣는 메소드
    // workStatus の値を取得・設定するメソッド
    public String getWorkStatus() { return workStatus; }
    public void setWorkStatus(String workStatus) { this.workStatus = workStatus; }

    // loginStatus 값을 꺼내고 넣는 메소드
    // loginStatus の値を取得・設定するメソッド
    public String getLoginStatus() { return loginStatus; }
    public void setLoginStatus(String loginStatus) { this.loginStatus = loginStatus; }

    // departmentName 값을 꺼내고 넣는 메소드
    // departmentName の値を取得・設定するメソッド
    public String getDepartmentName() { return departmentName; }
    public void setDepartmentName(String departmentName) { this.departmentName = departmentName; }
}
