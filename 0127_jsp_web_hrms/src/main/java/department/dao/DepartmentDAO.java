package department.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import department.model.UserStatusDTO;

public class DepartmentDAO {

    // 전체 조직도 가져오기 (부서 + 직책 + 사용자 + 상태)
    public List<UserStatusDTO> getOrgChart(Connection conn) throws SQLException {
        List<UserStatusDTO> list = new ArrayList<>();

        String sql = "SELECT d.department_name, u.position, u.name, s.status_type, s.is_current "
                   + "FROM user_tbl u "
                   + "LEFT JOIN department_tbl d ON u.department_id = d.department_id "
                   + "LEFT JOIN user_status_tbl s ON u.user_id = s.user_id "
                   + "WHERE s.is_current = 'Y' "
                   + "ORDER BY d.department_name, u.position, u.name";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                UserStatusDTO dto = new UserStatusDTO();
                dto.setDepartmentName(rs.getString("department_name"));
                dto.setPosition(rs.getString("position"));
                dto.setName(rs.getString("name"));
                dto.setStatusType(rs.getString("status_type"));
                dto.setIsCurrent(rs.getString("is_current"));
                list.add(dto);
            }
        }

        return list;
    }

    // 특정 부서에 속한 사용자만 조회
    public List<UserStatusDTO> getUsersByDepartment(Connection conn, String departmentId) throws SQLException {
        List<UserStatusDTO> list = new ArrayList<>();

        String sql = "SELECT d.department_name, u.position, u.name, s.status_type, s.is_current "
                   + "FROM user_tbl u "
                   + "LEFT JOIN department_tbl d ON u.department_id = d.department_id "
                   + "LEFT JOIN user_status_tbl s ON u.user_id = s.user_id "
                   + "WHERE s.is_current = 'Y' AND d.department_id = ? "
                   + "ORDER BY u.position, u.name";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, departmentId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    UserStatusDTO dto = new UserStatusDTO();
                    dto.setDepartmentName(rs.getString("department_name"));
                    dto.setPosition(rs.getString("position"));
                    dto.setName(rs.getString("name"));
                    dto.setStatusType(rs.getString("status_type"));
                    dto.setIsCurrent(rs.getString("is_current"));
                    list.add(dto);
                }
            }
        }

        return list;
    }
}
