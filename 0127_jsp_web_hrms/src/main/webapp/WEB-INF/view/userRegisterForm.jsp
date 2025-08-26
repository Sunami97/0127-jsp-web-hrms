<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="common/header.jsp"%>
<%@ include file="common/nav.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/adminRegister.css">
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
<link href="https://cdn.jsdelivr.net/npm/remixicon@3.5.0/fonts/remixicon.css" rel="stylesheet">
</head>
<body>
	<!-- 사원을 추가하기 위한 정보입력 폼 社員を追加するための情報入力フォーム -->
	<form class="register-form" action="insert.do" method="post">
		<div>
			<div class="container">
				<h2>
					社員ID作成のため<br>情報を入力してください.
				</h2>

				<label>* ID <br>
				<input type="text" name="userId" required="required"><br>
				<br></label> <label>* PW <br>
				<input type="password" name="password" required="required"><br>
				<br></label> <label>* 名前 <br>
				<input type="text" name="name" required="required"><br>
				<br></label> <label>* メール <br>
				<input type="text" name="email"><br>
				<br></label> <label>* 連絡先 <br>
				<input type="text" name="phone" required="required"><br>
				<br></label> <label>* 生年月日 <br>
				<input type="date" name="birthDate" required="required"><br>
				<br></label> <label>* 入社日 <br>
				<input type="date" name="joinDate"><br>
				<br></label> <label>* 職位 <br>
				<select name="position">
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
				<br></label> <label>* 部署 <br>
				<select name="departmentId">
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
				<br></label> <label>* 管理者 権限 <br> 
				N<input type="radio" name="isAdmin" checked="checked" value="N"> 
				Y<input type="radio" name="isAdmin" value="Y"><br>
				<br></label> 
				<button type="submit" class="admin-button">登録</button>
			</div>
		</div>
	</form>
	<%@ include file="common/footer.jsp"%>
</body>
</html>
