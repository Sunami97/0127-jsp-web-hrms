package department.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import department.model.DepartmentDTO;
import department.model.UserStatusDTO;

public class DepartmentDAO {
	
	public List<UserStatusDTO> selectDepartmentById(Connection conn, String departmentId) throws SQLException {
		List<UserStatusDTO> list = new ArrayList<>();
		String sql = "SELECT u.name, u.position, s.status_type, s.is_current, d.department_name"
				+ "FROM user_tbl u"
				+ "LEFT JOIN department_tbl d ON u.department_id = d.department_id"
				+ "LEFT JOIN user_status_tbl s ON u.user_id = s.user_id";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
	                UserStatusDTO dto = new UserStatusDTO();
	                dto.setName(rs.getString("name"));
	                dto.setPosition(rs.getString("position"));
	                dto.setStatusType(rs.getString("status_type"));
	                dto.setIsCurrent(rs.getString("is_current"));
	                dto.setDepartmentName(rs.getString("department_name"));
	                list.add(dto);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        return list;
	    }
	}
}
