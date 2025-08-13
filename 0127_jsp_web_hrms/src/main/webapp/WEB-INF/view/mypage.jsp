<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="common/header.jsp"%>
<%@ page import="myPage.model.UserDepartmentDTO"%>
<%
// [서버에서 user 객체 받기!]
UserDepartmentDTO userDepartment = (UserDepartmentDTO) request.getAttribute("user");
// [로그인 or 세션 없으면 안내]
if (userDepartment == null) {
%>
<h2>개인정보</h2>
<p style="color: red;">
	회원 정보가 없습니다.<br> 세션이 만료되었거나, 로그인 정보가 올바르지 않습니다.<br> <a
		href="<%=request.getContextPath()%>/login.jsp" style="color: #007bff;">로그인
		페이지로 이동</a>
</p>
<%
return;
}
String position = userDepartment.getPosition() == null ? "" : userDepartment.getPosition();
String borderColor = "#16a34a"; // 기본 (초록)
String bgColor = "#bbf7d0"; // 얼굴 배경 (밝은 초록)
String hairColor = "#0f172a"; // 머리색
String bodyColor = "#fbbf24"; // 옷색

// 직급별 컬러만 변경
if ("部長".equals(position)) {
borderColor = "#d4af37";
bgColor = "#fef3c7";
hairColor = "#78350f";
bodyColor = "#fde68a";
} else if ("課長".equals(position)) {
borderColor = "#2563eb";
bgColor = "#dbeafe";
hairColor = "#334155";
bodyColor = "#3b82f6";
} else if ("社長".equals(position)) {
borderColor = "#0f172a";
bgColor = "#f1f5f9";
hairColor = "#0f172a";
bodyColor = "#94a3b8";
}
String displayPosition = position.isEmpty() ? "직원" : position;
%>

<!DOCTYPE html>
<html>
<head>
<title>개인정보</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" type="text/css" href="css/style.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
<style>
body {
	font-family: 'Segoe UI', Arial, sans-serif;
	background: #f5f7fa;
}

.profile-card {
	display: flex;
	max-width: 700px;
	margin: 40px auto;
	background: #fff;
	border-radius: 18px;
	box-shadow: 0 2px 16px rgba(0, 0, 0, 0.11);
	overflow: hidden;
	min-height: 340px;
	padding: 50px;
}

.profile-left {
	background: #f0f4fa;
	padding: 36px 18px 24px 18px;
	display: flex;
	flex-direction: column;
	align-items: center;
	min-width: 230px;
	width: 230px;
	position: relative;
}

.profile-img-wrapper {
	position: relative;
	width: 140px;
	height: 140px;
	margin-bottom: 16px;
}

.profile-img {
	width: 140px;
	height: 140px;
	border-radius: 50%;
	object-fit: cover;
	background: #d6e0f7;
	box-shadow: 0 2px 10px #dde3ee;
	display: flex;
	align-items: center;
	justify-content: center;
}

.profile-img-edit {
	position: absolute;
	right: 6px;
	bottom: 8px;
	background: #386cf4;
	color: #fff;
	border-radius: 50%;
	width: 32px;
	height: 32px;
	display: flex;
	align-items: center;
	justify-content: center;
	box-shadow: 0 1px 5px rgba(0, 0, 0, .13);
	border: 2px solid #fff;
	cursor: pointer;
	z-index: 2;
	font-size: 15px;
	transition: background 0.16s;
}

.profile-img-edit:hover {
	background: #2749ad;
}

.profile-img-edit input[type="file"] {
	position: absolute;
	left: 0;
	top: 0;
	width: 100%;
	height: 100%;
	opacity: 0;
	cursor: pointer;
}

.profile-name {
	font-size: 1.3em;
	font-weight: bold;
	margin-bottom: 10px;
	color: #283047;
	text-align: center;
}

.profile-pos {
	color: #71829b;
	font-size: 16px;
	text-align: center;
}

.btn-group {
	width: 100%;
	margin-top: 38px;
	display: flex;
	justify-content: flex-start;
	align-items: flex-end;
}

.my-btn {
	width: 100%;
	background: #386cf4;
	color: #fff;
	border: none;
	padding: 14px 0;
	border-radius: 19px;
	font-size: 16px;
	cursor: pointer;
	font-weight: 500;
	transition: background 0.18s;
}

.my-btn:hover {
	background: #003bb8;
}

.profile-right {
	flex: 1;
	padding: 38px 32px;
	min-width: 0;
	display: flex;
	flex-direction: column;
	justify-content: center;
}

.profile-table {
	width: 100%;
	border-collapse: collapse;
	background: none;
}

.profile-table th, .profile-table td {
	padding: 11px 10px 10px 0;
	border-bottom: 1px solid #e0e3ea !important;
	text-align: left;
	font-size: 16px;
	color: #353f50;
}

.profile-table th {
	background: none;
	width: 104px;
	color: #7682a0;
	font-weight: 600;
}

.profile-table tr:last-child td, .profile-table tr:last-child th {
	border-bottom: 1px solid #e0e3ea !important;
}

@media ( max-width : 700px) {
	.profile-card {
		flex-direction: column;
		width: 98vw;
		min-width: 0;
	}
	.profile-left, .profile-right {
		width: 100% !important;
		min-width: 0;
		padding: 24px 14px;
		justify-content: center;
	}
	.btn-group {
		margin-top: 26px;
	}
	.profile-img-wrapper, .profile-img {
		width: 100px;
		height: 100px;
	}
	.profile-img-edit {
		width: 22px;
		height: 22px;
		font-size: 11px;
		right: 2px;
		bottom: 2px;
	}
	.profile-table th, .profile-table td {
		font-size: 14px;
	}
}
</style>
<script>
	function previewProfileImg(input) {
		if (input.files && input.files[0]) {
			var reader = new FileReader();
			reader.onload = function(e) {
				document.getElementById('svgProfile').style.display = 'none';
				document.getElementById('profilePreview').src = e.target.result;
				document.getElementById('profilePreview').style.display = '';
			}
			reader.readAsDataURL(input.files[0]);
		}
	}
</script>
</head>
<body>
	<div class="profile-card">
		<div class="profile-left">
			<div class="profile-img-wrapper" style="position: relative;">
				<svg width="140" height="140" viewBox="0 0 140 140"
					style="display: block;">
        <!-- 외곽 원 -->
        <circle cx="70" cy="70" r="68" fill="<%=bgColor%>"
						stroke="<%=borderColor%>" stroke-width="4" />
        <!-- 머리 (심즈 스타일) -->
        <ellipse cx="70" cy="56" rx="34" ry="28" fill="<%=hairColor%>" />
        <!-- 얼굴 (피부색) -->
        <ellipse cx="70" cy="68" rx="31" ry="26" fill="#fee2b6" />
        <!-- 몸통(상의) -->
        <rect x="43" y="90" width="54" height="28" rx="14"
						fill="<%=bodyColor%>" />
        <!-- 눈 -->
        <ellipse cx="60" cy="68" rx="4" ry="3" fill="#111" />
        <ellipse cx="80" cy="68" rx="4" ry="3" fill="#111" />
        <!-- 미소 -->
        <path d="M60 80 Q70 87 80 80" stroke="#b91c1c" stroke-width="2"
						fill="none" />
        <!-- 얼굴에 직급명 (굵은 폰트) -->
        <text x="70" y="120" text-anchor="middle" font-size="17"
						font-weight="bold" fill="<%=borderColor%>"><%=displayPosition%></text>
    </svg>
				<img src="" alt="프로필" id="profilePreview" class="profile-img"
					style="display: none; position: absolute; left: 0; top: 0;" /> <label
					class="profile-img-edit" title="사진 변경"> <i
					class="fa-solid fa-camera"></i> <input type="file"
					name="profileImg" accept="image/*"
					onchange="previewProfileImg(this)">
				</label>
			</div>
			<div class="profile-name"><%=userDepartment.getName()%></div>
			<div class="profile-pos">
				<%=userDepartment.getDepartmentName() == null ? "" : userDepartment.getDepartmentName()%>
				<%
				if (userDepartment.getPosition() != null && !userDepartment.getPosition().isEmpty()) {
				%>
				/
				<%=userDepartment.getPosition()%>
				<%
				}
				%>
			</div>
			<div class="btn-group">
				<a href="<%=request.getContextPath()%>/mypageEditForm.do"
					style="width: 100%;">
					<button type="button" class="my-btn">修正</button>
				</a>
			</div>
		</div>
		<div class="profile-right">
			<table class="profile-table">
				<tr>
					<th>ユーザーID</th>
					<td><%=userDepartment.getUserId()%></td>
				</tr>
				<tr>
					<th>氏名</th>
					<td><%=userDepartment.getName()%></td>
				</tr>
				<tr>
					<th style="white-space: nowrap; min-width: 120px;">メールアドレス <%-- メエルアドレス --%></th>
					<td><%=userDepartment.getEmail() == null ? "" : userDepartment.getEmail()%></td>
				</tr>
				<tr>
					<th>電話番号</th>
					<td><%=userDepartment.getPhone() == null ? "" : userDepartment.getPhone()%></td>
				</tr>
				<tr>
					<th>生年月日</th>
					<td><%=userDepartment.getBirthDate() == null ? "" : userDepartment.getBirthDate().toString()%></td>
				</tr>
				<tr>
					<th>部署</th>
					<td><%=userDepartment.getDepartmentName() == null ? "" : userDepartment.getDepartmentName()%></td>
				</tr>
				<tr>
					<th>役職</th>
					<td><%=userDepartment.getPosition() == null ? "" : userDepartment.getPosition()%></td>
				</tr>
				<tr>
					<th>入社日</th>
					<td><%=userDepartment.getJoinDate() == null ? "" : userDepartment.getJoinDate().toString()%></td>
				</tr>
				<tr>
					<th>退職日</th>
					<td><%=userDepartment.getRetireDate() == null ? "" : userDepartment.getRetireDate().toString()%></td>
				</tr>
				<%
				if ("Y".equals(userDepartment.getIsAdmin())) {
				%>
				<tr>
					<th>管理者権限</th>
					<td>はい</td>
				</tr>
				<%
				}
				%>
				<tr>
					<th>雇用状態</th>
					<td><%=userDepartment.getEmpStatus() == null ? "" : userDepartment.getEmpStatus()%></td>
				</tr>
				<tr>
					<th>勤務状態</th>
					<td><%=userDepartment.getWorkStatus() == null ? "" : userDepartment.getWorkStatus()%></td>
				</tr>
				<tr>
					<th>ログイン状態</th>
					<td><%="Y".equals(userDepartment.getLoginStatus()) ? "はい" : "いいえ"%></td>
				</tr>
			</table>
		</div>
	</div>
	<%@ include file="common/footer.jsp"%>
</body>
</html>
