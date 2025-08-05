 <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
 <style>
 .search-box {
  position: relative;
  width: 300px;
}

input {
  width: 100%;
  border: 1px solid #bbb;
  border-radius: 8px;
  padding: 10px 12px;
  font-size: 14px;
}

img {
  position : absolute;
  width: 17px;
  top: 10px;
  right: 12px;
  margin: 0;
}
</style>
</head>
<body>
	<form class="search-box" action="select.do" method="post">
		<table >
				<tr> 
					<td><select name="keyField">
						<option>전체</option>
						<option value="name">이름</option>
						<option value="user_id">아이디</option>
						<option value="position">직급</option>
						<option value="phone">연락처</option>
						</select>
						<select name="date">
						<option	value="null" selected>기간</option>
						<option value="join_date">입사일</option>
						<option value="retire_date">퇴사일</option>
						</select>
						<input type="date" name="sDate">~ 
						<input type="date" name="sDate"> 
					 <input class="search-txt" type="text" name="keyWord" placeholder="검색어를 입력하세요.">
					<button type="submit">조회</button>
					</td>
					
				</tr>
			
		</table>
	
	</form>
	<jsp:include page="/WEB-INF/view/userListForm.jsp" flush="false">
		<jsp:param name="cmd" value="ListForm" />	
	</jsp:include>	
	<form>
	<button type="submit" onclick="javascript: form.action='registerForm.do'">추가</button>
	</form>
	
</body>
</html>