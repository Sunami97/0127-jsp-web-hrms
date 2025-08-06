package department.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import department.dao.DepartmentDAO;
import department.model.UserStatusDTO;

public class DepartmentService {

    private DepartmentDAO departmentDAO = new DepartmentDAO();
    // DAO 객체 생성 (데이터 접근을 담당)
    // DAO オブジェクトの生成（データアクセスを担当）

    // 전체 조직도 조회
    // 組織図全体の取得
    public List<UserStatusDTO> getOrgChart(Connection conn) throws SQLException {
        return departmentDAO.getOrgChart(conn);
        // DAO 메서드를 호출하여 조직도 데이터 반환
        // DAO のメソッドを呼び出して組織図データを返す
    }

    // 특정 부서의 사용자만 조회
    // 特定部署に所属するユーザーのみ取得
    public List<UserStatusDTO> getUsersByDepartment(Connection conn, String departmentId) throws SQLException {
        return departmentDAO.getUsersByDepartment(conn, departmentId);
        // DAO 메서드를 호출하여 해당 부서 사용자 반환
        // DAO のメソッドを呼び出して該当部署のユーザーを返す
    }
}
