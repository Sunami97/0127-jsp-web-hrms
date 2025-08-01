package contacts.dao;

import contacts.model.ContactsDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ContactsDAO {

    private Connection getConnection() throws SQLException {
        // DB 연결 정보 (필요에 따라 수정)
        String url = "jdbc:oracle:thin:@localhost:1521:xe";
        String user = "system";
        String password = "1234";
        return DriverManager.getConnection(url, user, password);
    }

    public List<ContactsDTO> findAll() {
        List<ContactsDTO> list = new ArrayList<>();

        String sql = "SELECT " +
                     "d.department_name, " +
                     "u.name, " +
                     "u.email, " +
                     "u.phone, " +
                     "u.join_date, " +
                     "u.position, " +
                     "u.login_status, " +
                     "s.status_type " +
                     "FROM user_tbl u " +
                     "JOIN department_tbl d ON u.department_id = d.department_id " +
                     "LEFT JOIN user_status_tbl s ON u.user_id = s.user_id AND s.is_current = 'Y' " +
                     "WHERE u.retire_date IS NULL " +
                     "ORDER BY d.department_name, u.name";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                ContactsDTO dto = new ContactsDTO(
                    rs.getString("department_name"),                     // departmentName
                    rs.getString("name"),                                // name
                    rs.getString("email"),                               // email
                    rs.getString("phone"),                               // phone
                    rs.getDate("join_date"),                             // joinDate (java.util.Date로 자동 변환)
                    rs.getString("position"),                            // position
                    "Y".equals(rs.getString("login_status")) ? "login" : "logout", // loginStatus
                    rs.getString("status_type") != null ? rs.getString("status_type") : "정상근무" // statusType
                );
                list.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
