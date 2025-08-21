package admin.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import admin.dto.AdminDto;
import jdbc.JdbcUtil;

public class AdminDao {
	public void insert(Connection conn, String[] insertList, String[] reqVal)  
			throws SQLException {
		PreparedStatement pstmt = null;

		try { 
			//클라이언트가 입력한 속성과 값의 수에 맞는 sql문 작성 クライアントが入力した内容と一致するsql文の作成
			StringBuilder sql = new StringBuilder("insert into user_tbl(");
			sql.append(String.join(",", insertList));
			sql.append(") values (");
			sql.append("?,".repeat(insertList.length - 1)).append("?)");

			pstmt = conn.prepareStatement(sql.toString());

			for (int i = 0; i < reqVal.length; i++) { //입력한만큼 반복하여 세팅 入力した分だけ繰り返しセッティング
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

	public List<AdminDto> selectList(Connection conn, String keyWord, String keyField, String date, String[] sDate) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		if (!date.equals("null") && keyWord != null && keyWord != "") {	
			//검색할 내용과 기간을 설정했는지 확인 検索する内容と期間を設定したか確認
			try {
			pstmt = conn.prepareStatement(
					"Select u.*,d.department_name from user_tbl u, department_tbl d where u.department_id = d.department_id(+) and "
							+keyField+" = ? and "+date+" between ? and ?");
			pstmt.setString(1, keyWord);
			pstmt.setString(2, sDate[0]);
			pstmt.setString(3, sDate[1]);
			rs = pstmt.executeQuery();
			
			List<AdminDto> result = new ArrayList<>();

			while (rs.next()) {
				result.add(convertUser(rs));
			}
			return result;
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
				
		} else if(date.equals("null")  && keyWord != null && keyWord != ""){
			//검색할 내용이 있지만 기간을 설정하지 않았을 때 실행 検索する内容がありますが、期間を設定していない場合に実行
				try {pstmt = conn.prepareStatement("Select u.*,d.department_name from user_tbl u, department_tbl d where u.department_id = d.department_id(+) and "
														+keyField+" = ?");
				pstmt.setString(1, keyWord);
				rs = pstmt.executeQuery();
				List<AdminDto> result = new ArrayList<>();
				while (rs.next()) {
					result.add(convertUser(rs));

				}
				return result;
			} finally {
				JdbcUtil.close(rs);
				JdbcUtil.close(pstmt);
			}
				
		} else if((keyWord == "" || keyWord == null) && !date.equals("null") ) {
			//검색할 내용은 없지만 기간은 설정했을 때 실행 検索する内容はありませんが、期間は設定した時に実行
			try {
				pstmt = conn.prepareStatement(
						"Select u.*,d.department_name from user_tbl u, department_tbl d where u.department_id = d.department_id(+) and "+date+" between ? and ?");
				pstmt.setString(1, sDate[0]);
				pstmt.setString(2, sDate[1]);
				rs = pstmt.executeQuery();
				
				List<AdminDto> result = new ArrayList<>();

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
			//모든 조건에 부합하지 않았을 경우 모두 조회 すべての条件を満たしていない場合、すべて照会
			pstmt = conn.prepareStatement("Select u.*,d.department_name from user_tbl u, department_tbl d where u.department_id = d.department_id(+)");
			rs = pstmt.executeQuery();
			List<AdminDto> result = new ArrayList<>();
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


	private AdminDto convertUser(ResultSet rs) throws SQLException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//DB에서 조회한 날짜데이터를 정해진 형식으로 변경 DBで照会した日付データを決められた形式に変更
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

		return new AdminDto(rs.getString("user_id"), rs.getString("password"), rs.getString("name"),
				rs.getString("email"), rs.getString("phone"), birthDate, joinDate, retireDate, rs.getString("position"),
				rs.getInt("department_id"), rs.getString("is_admin"), rs.getString("emp_status"),
				rs.getString("work_status"), rs.getString("login_status"),rs.getString("department_name"));
	}

	public void Delete(Connection conn, String[] userId) throws SQLException {
		if (userId != null && userId.length > 0) {
			//요청 페이지에서 삭제 할 사원을 체크 했는지 확인 リクエストページで削除する社員をチェックしたか確認
			PreparedStatement pstmt = null;
		
			try {
				//체크한 만큼 반복 チェックした分だけ繰り返し
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
			//수정한 수만큼 반복 修正した数だけ繰り返す
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
			//퇴사일을 받아서 수정 退社日をもらって修正
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
