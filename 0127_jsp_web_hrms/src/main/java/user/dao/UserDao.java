package user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import jdbc.JdbcUtil;
import user.dto.UserDto;

public class UserDao {
	public void insert(Connection conn/* , UserDto userDto */, String[] insertList, String[] reqVal)
			throws SQLException {
		PreparedStatement pstmt = null;

		try {
			StringBuilder sql = new StringBuilder("insert into user_tbl(");
			sql.append(String.join(",", insertList));
			sql.append(") values (");
			sql.append("?,".repeat(insertList.length - 1)).append("?)");

			pstmt = conn.prepareStatement(sql.toString());

			// 값 바인딩
			for (int i = 0; i < reqVal.length; i++) {
			    try {
			        // 정수면 setInt, 아니면 setString
			        pstmt.setInt(i + 1, Integer.parseInt(reqVal[i]));
			    } catch (NumberFormatException e) {
			        pstmt.setString(i + 1, reqVal[i]);
			    }
			}
			System.out.print(sql);
			pstmt.executeUpdate();
			
			/*
			 * pstmt = conn.
			 * prepareStatement("insert into user_tbl(user_id, password, name, email, phone, birth_date, join_date,position, department_id, is_admin, emp_status, work_status, login_status) values (?,?,?,?,?,?,?,?,?,?,?,?,?)"
			 * ); pstmt.setString(1, userDto.getUserId()); pstmt.setString(2,
			 * userDto.getPassword()); pstmt.setString(3, userDto.getName());
			 * pstmt.setString(4, userDto.getEmail()); pstmt.setString(5,
			 * userDto.getPhone()); pstmt.setDate(6,
			 * java.sql.Date.valueOf(userDto.getBirthDate())); pstmt.setDate(7,
			 * java.sql.Date.valueOf(userDto.getJoinDate())); pstmt.setString(8,
			 * userDto.getPosition()); pstmt.setInt(9, userDto.getDepartmentId());
			 * pstmt.setString(10, userDto.getIsAdmin()); pstmt.setString(11,
			 * userDto.getEmpStatus()); pstmt.setString(12, userDto.getWorkStatus());
			 * pstmt.setString(13, userDto.getLoginStatus());
			 */

		} finally {
			JdbcUtil.close(pstmt);

		}

	}

	public List<UserDto> selectList(Connection conn, String keyWord, String keyField) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		if (keyWord == "" || keyWord == null) {
			try {
				pstmt = conn.prepareStatement("Select * from user_tbl");
				rs = pstmt.executeQuery();
				List<UserDto> result = new ArrayList<>();
				while (rs.next()) {
					result.add(convertUser(rs));

				}

				return result;
			} finally {
				JdbcUtil.close(rs);
				JdbcUtil.close(pstmt);
			}
		} else {

			try {
				pstmt = conn.prepareStatement("Select * from user_tbl where " + keyField + " = ?");
				pstmt.setString(1, keyWord);
				rs = pstmt.executeQuery();
				List<UserDto> result = new ArrayList<>();

				while (rs.next()) {
					result.add(convertUser(rs));
				}
				return result;
			} finally {
				JdbcUtil.close(rs);
				JdbcUtil.close(pstmt);
			}
		}
	}

	private UserDto convertUser(ResultSet rs) throws SQLException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		String birthDate = null;
		Date birth = rs.getDate("birth_date");
		if (birth != null)
			birthDate = sdf.format(birth);

		String joinDate = null;
		Date join = rs.getDate("join_date");
		if (join != null)
			joinDate = sdf.format(join);

		String retireDate = null;
		Date retire = rs.getDate("retire_date");
		if (retire != null)
			retireDate = sdf.format(retire);

		return new UserDto(rs.getString("user_id"), rs.getString("password"), rs.getString("name"),
				rs.getString("email"), rs.getString("phone"), birthDate, joinDate, retireDate, rs.getString("position"),
				rs.getInt("department_id"), rs.getString("is_admin"), rs.getString("emp_status"),
				rs.getString("work_status"), rs.getString("login_status"));
	}

	public void Delete(Connection conn, String[] userId) throws SQLException {
		if (userId != null && userId.length > 0) {
			PreparedStatement pstmt = null;
		
			try {
				

				 for (int i = 0; i < userId.length; i++)  {
					pstmt = conn.prepareStatement("delete from user_tbl where user_id = ?");
					pstmt.setString(1, userId[i]);
					pstmt.executeUpdate();
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {

				JdbcUtil.close(pstmt);
			}
		}
	}

	public void Update(Connection conn, String userId, String[] UpdateList, String[] reqVal) {
		PreparedStatement pstmt = null;
		try {
			for (int i = 0; i < UpdateList.length; i++) {
				pstmt = conn.prepareStatement("update user_tbl set " + UpdateList[i] + " = ? where user_id = ?");
				pstmt.setString(1, reqVal[i]);
				pstmt.setString(2, userId);
				pstmt.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			JdbcUtil.close(pstmt);
		}

	}
	
	public void fire(Connection conn, String userId) {
		PreparedStatement pstmt = null;
		try {
			System.out.print(userId);
			pstmt = conn.prepareStatement("update user_tbl set retire_date = sysdate where user_id = ?");
			pstmt.setString(1, userId);
			pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			JdbcUtil.close(pstmt);
		}
		
	}
}
