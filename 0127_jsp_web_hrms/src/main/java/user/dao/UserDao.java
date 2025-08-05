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
	public void insert(Connection conn, String[] insertList, String[] reqVal)
			throws SQLException {
		PreparedStatement pstmt = null;

		try {
			StringBuilder sql = new StringBuilder("insert into user_tbl(");
			sql.append(String.join(",", insertList));
			sql.append(") values (");
			sql.append("?,".repeat(insertList.length - 1)).append("?)");

			pstmt = conn.prepareStatement(sql.toString());

			for (int i = 0; i < reqVal.length; i++) {
			    try {
			        pstmt.setInt(i + 1, Integer.parseInt(reqVal[i]));
			    } catch (NumberFormatException e) {
			        pstmt.setString(i + 1, reqVal[i]);
			    }
			}
			pstmt.executeUpdate();
			
		

		} finally {
			JdbcUtil.close(pstmt);

		}

	}

	public List<UserDto> selectList(Connection conn, String keyWord, String keyField, String date, String[] sDate) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		if (!date.equals("null") && keyWord != null && keyWord != "") {	
			try {
			pstmt = conn.prepareStatement(
					"Select u.*,d.department_name from user_tbl u, department_tbl d where u.department_id = d.department_id(+) and "
							+keyField+" = ? and "+date+" between ? and ?");
			pstmt.setString(1, keyWord);
			pstmt.setString(2, sDate[0]);
			pstmt.setString(3, sDate[1]);
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
				
		} else if(date.equals("null")  && keyWord != null && keyWord != ""){
				try {pstmt = conn.prepareStatement("Select u.*,d.department_name from user_tbl u, department_tbl d where u.department_id = d.department_id(+) and "
														+keyField+" = ?");
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
				
		} else if((keyWord == "" || keyWord == null) && !date.equals("null") ) {
			try {
				pstmt = conn.prepareStatement(
						"Select u.*,d.department_name from user_tbl u, department_tbl d where u.department_id = d.department_id(+) and "+date+" between ? and ?");
				pstmt.setString(1, sDate[0]);
				pstmt.setString(2, sDate[1]);
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
			pstmt = conn.prepareStatement("Select u.*,d.department_name from user_tbl u, department_tbl d where u.department_id = d.department_id(+)");
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
				rs.getString("work_status"), rs.getString("login_status"),rs.getString("department_name"));
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
	
	public void fire(Connection conn, String userId, String date) {
		PreparedStatement pstmt = null;
		try {
			System.out.print(userId);
			pstmt = conn.prepareStatement("update user_tbl set retire_date = ? where user_id = ?");
			pstmt.setString(1, date);
			pstmt.setString(2, userId);
			pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			JdbcUtil.close(pstmt);
		}
		
	}
}
