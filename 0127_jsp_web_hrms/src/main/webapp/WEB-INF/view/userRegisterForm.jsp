<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>	<!-- 사원을 추가하기 위한 정보입력 폼 社員を追加するための情報入力フォーム -->
	<form action="insert.do" method="post">
		ID	<input type="text" name="userId" required="required"><br>
		PW		<input type="password" name="password" required="required"><br>
		名前		<input type="text" name="name" required="required"><br>
		メール<input type="text" name="email"><br>
		連絡先	<input type="text" name="phone" required="required"><br>
		生年月日	<input type="date" name="birthDate" required="required"><br>
		入社日	<input type="date" name="joinDate"><br>
		職位		<select name="position">
					<option value="社員">社員</option>
					<option value="主任">主任</option>
					<option value="係長">係長</option>
					<option value="課長">課長</option>
					<option value="次長">次長</option>
					<option value="部長">部長</option>
					<option value="常務">常務</option>
					<option value="専務">専務</option>
					<option value="取締役">取締役</option>
					<option value="代表取締役">代表取締役</option>
					<option value="会長">会長</option>
					<option value="社長">社長</option>
				</select><br>			
		部署	<select name="departmentId">
					<option value="1">総務部</option>
					<option value="2">人事課</option>
					<option value="3">経理部</option>
					<option value="4">財務部</option>
					<option value="5">営業部</option>
					<option value="6">販売部</option>
					<option value="7">マーケティング部</option>
					<option value="8">開発部</option>
					<option value="9">技術部</option>
					<option value="10">情報システム部</option>
					<option value="11">生産部</option>
					<option value="12">品質管理部</option>
					<option value="13">法務部</option>
					<option value="14">企画部</option>
				</select><br>	
		管理者 権限  N<input type="radio" name="isAdmin" checked="checked" value="N">
				  Y<input type="radio" name="isAdmin" value="Y"><br>
		状態		<select name="empStatus">
					<option value="在職">在職</option>
					<option value="休職">休職</option>
					<option value="退職">退職</option>
				</select><br>	
		勤務状態	<select name="workStatus">
					<option value="勤務中">勤務中</option>
					<option value="年休">年休</option>
					<option value="出張">出張</option>
				</select><br>	
		ログイン状態	N<input type="radio" name="loginStatus" checked="checked" value="N">
					Y<input type="radio" name="loginStatus" value="Y"><br>
		<button type="submit">追加</button>
	</form>
</body>
</html>