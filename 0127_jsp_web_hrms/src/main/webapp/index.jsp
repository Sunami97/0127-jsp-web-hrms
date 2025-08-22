<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>HRMS 로그인</title>
<!-- Font Awesome -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css"
	crossorigin="anonymous" referrerpolicy="no-referrer" />

<style>
body#hrms-login {
	background: #fff;
	font-family: Arial, sans-serif;
	margin: 0;
}

#hrms-header {
	background: #2E83F2;
	color: #fff;
	text-align: center;
	padding: 20px;
	font-size: 22px;
	font-weight: 700;
}

#login-box {
	width: 360px;
	margin: 80px auto;
	padding: 26px;
	background: #f9f9f9;
	border: 1px solid #e5e7eb;
	border-radius: 12px;
	box-shadow: 0 4px 12px rgba(0, 0, 0, .08);
}

#login-title {
	text-align: center;
	color: #2E83F2;
	font-size: 20px;
	font-weight: 700;
	margin-bottom: 20px;
}

.field {
	display: flex;
	align-items: center;
	background: #fff;
	border: 1px solid #d1d5db;
	border-radius: 8px;
	margin-bottom: 14px;
}

.field:focus-within {
	border-color: #2E83F2;
	box-shadow: 0 0 0 3px rgba(46, 131, 242, .15);
}

.field label {
	width: 42px;
	text-align: center;
	color: #2E83F2;
}

.field input {
	flex: 1;
	border: none;
	outline: none;
	padding: 12px;
	font-size: 14px;
	background: transparent;
}

#submit-btn {
	width: 100%;
	padding: 12px;
	background: #2E83F2;
	color: #fff;
	border: none;
	border-radius: 8px;
	font-size: 16px;
	cursor: pointer;
}

#submit-btn:hover {
	background: #3071F2;
}

.msg-error {
	margin-top: 12px;
	color: #b91c1c;
	background: #fee2e2;
	border: 1px solid #fecaca;
	padding: 10px 12px;
	border-radius: 8px;
}
</style>
</head>
<body id="hrms-login">
	<!-- 상단 타이틀 -->
	<div id="hrms-header">HRMS 人事管理システム</div>

	<div id="login-box">
		<div id="login-title">LOGIN</div>

		<form action="<c:url value='login.do'/>" method="post">
			<div class="field">
				<label for="user_id" title="id"><i class="fa fa-user"
					aria-hidden="true"></i></label> <input type="text" id="user_id"
					name="user_id" placeholder="ID" required />
			</div>

			<div class="field">
				<label for="password" title="pw"><i class="fa fa-lock"
					aria-hidden="true"></i></label> <input type="password" id="password"
					name="password" placeholder="PASSWORD" required />
			</div>

			<button type="submit" id="submit-btn">SIGN IN</button>

			<%
			String msg = (String) request.getAttribute("msg");
			if (msg != null) {
			%>
			<div class="msg-error"><%=msg%></div>
			<%
			}
			%>
		</form>
	</div>
</body>
</html>
