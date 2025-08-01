<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
  request.setCharacterEncoding("UTF-8");
  String userI = request.getParameter("userId");
%>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="update.do" method="post">
		이메일	<input type="text" name="email"><br>
		연락처	<input type="text" name="phone"><br>
		직급		<select name="position">
					<option value="社員">社員</option>
					<option value="主任">主任</option>
					<option value="課長">課長</option>
					<option value="部長">部長</option>
					<option value="社長">社長</option>
				</select><br>			
		부서코드	<select name="departmentId">
					<option value="1">생산</option>
					<option value="2">회계</option>
					<option value="3">인사</option>
					<option value="4">전산</option>
				</select><br>	
		관리자 권한  N<input type="radio" name="isAdmin" checked="checked" value="N">
				  Y<input type="radio" name="isAdmin" value="Y"><br>
		상태		<select name="empStatus">
					<option value="在職">在職</option>
					<option value="休職">休職</option>
					<option value="退職">退職</option>
				</select><br>	
				<input type="hidden" name="userId" value="<%=userI %>">
		<button type="submit">수정</button>
	</form>
</body>
</html>