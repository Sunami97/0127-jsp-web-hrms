package department.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import department.dao.DepartmentDAO;
import department.model.UserStatusDTO;

public class DepartmentService {

    private DepartmentDAO departmentDAO = new DepartmentDAO();

    // 전체 조직도 조회
    public List<UserStatusDTO> getOrgChart(Connection conn) throws SQLException {
        return departmentDAO.getOrgChart(conn);
    }

    // 특정 부서의 사용자만 조회
    public List<UserStatusDTO> getUsersByDepartment(Connection conn, String departmentId) throws SQLException {
        return departmentDAO.getUsersByDepartment(conn, departmentId);
    }
}
