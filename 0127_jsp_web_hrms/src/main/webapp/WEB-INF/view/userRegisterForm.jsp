<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="insert.do" method="post">
		아이디	<input type="text" name="userId" required="required"><br>
		비번		<input type="password" name="password" required="required"><br>
		이름		<input type="text" name="name" required="required"><br>
		이메일	<input type="text" name="email"><br>
		연락처	<input type="text" name="phone" required="required"><br>
		생년월일	<input type="date" name="birthDate" required="required"><br>
		입사일	<input type="date" name="joinDate"><br>
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
		근무상태	<select name="workStatus">
					<option value="勤務中">勤務中</option>
					<option value="年休">年休</option>
					<option value="出張">出張</option>
				</select><br>	
		로그인상태	N<input type="radio" name="loginStatus" checked="checked" value="N">
				Y<input type="radio" name="loginStatus" value="Y"><br>
		<button type="submit">추가</button>
	</form>
</body>
</html>