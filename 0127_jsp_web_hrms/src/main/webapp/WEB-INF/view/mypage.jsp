<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="myPage.dto.UserDepartmentDTO"%>
<%
UserDepartmentDTO user = (UserDepartmentDTO) request.getAttribute("user");
if (user == null) {
%>
<h2>개인정보</h2>
<p style="color: red;">
    회원 정보가 없습니다.<br> 세션이 만료되었거나, 로그인 정보가 올바르지 않습니다.<br>
    <a href="<%=request.getContextPath()%>/login.jsp" style="color: #007bff;">로그인 페이지로 이동</a>
</p>
<%
return;
}
%>
<!DOCTYPE html>
<html>
<head>
    <title>개인정보</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- FontAwesome for camera icon -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
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
            box-shadow: 0 2px 16px rgba(0,0,0,0.11);
            overflow: hidden;
            min-height: 340px;
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
        }
        /* 사진변경 카메라 버튼 (프로필 오른쪽아래 겹침) */
        .profile-img-edit {
            position: absolute;
            right: 6px;
            bottom: 8px;
            background: #386cf4;
            color: #fff;
            border-radius: 50%;
            width: 32px; height: 32px;
            display: flex; align-items: center; justify-content: center;
            box-shadow: 0 1px 5px rgba(0,0,0,.13);
            border: 2px solid #fff;
            cursor: pointer;
            z-index: 2;
            font-size: 15px;
            transition: background 0.16s;
        }
        .profile-img-edit:hover { background: #2749ad; }
        .profile-img-edit input[type="file"] {
            position: absolute;
            left: 0; top: 0; width: 100%; height: 100%;
            opacity: 0; cursor: pointer;
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
        /* 줄이 마지막까지 보이게 */
        .profile-table tr:last-child td, .profile-table tr:last-child th {
            border-bottom: 1px solid #e0e3ea !important;
        }
        @media (max-width: 700px) {
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
            .btn-group { margin-top: 26px; }
            .profile-img-wrapper, .profile-img { width: 100px; height: 100px; }
            .profile-img-edit { width: 22px; height: 22px; font-size: 11px; right: 2px; bottom: 2px;}
            .profile-table th, .profile-table td { font-size: 14px; }
        }
    </style>
    <script>
        // 미리보기 (프론트 Only, 실제 업로드는 구현 필요)
        function previewProfileImg(input) {
            if (input.files && input.files[0]) {
                var reader = new FileReader();
                reader.onload = function(e) {
                    document.getElementById('profilePreview').src = e.target.result;
                }
                reader.readAsDataURL(input.files[0]);
            }
        }
    </script>
</head>
<body>
    <div class="profile-card">
        <div class="profile-left">
            <div class="profile-img-wrapper">
                <img src="https://cdn-icons-png.flaticon.com/512/1946/1946429.png"
                     alt="프로필" id="profilePreview"
                     class="profile-img" />
                <label class="profile-img-edit" title="사진 변경">
                    <i class="fa-solid fa-camera"></i>
                    <input type="file" name="profileImg" accept="image/*" onchange="previewProfileImg(this)">
                </label>
            </div>
            <div class="profile-name"><%=user.getName()%></div>
            <div class="profile-pos">
                <%=user.getDepartmentName() == null ? "" : user.getDepartmentName()%>
                <% if(user.getPosition() != null && !user.getPosition().isEmpty()) { %>
                    / <%= user.getPosition() %>
                <% } %>
            </div>
            <div class="btn-group">
                <a href="<%=request.getContextPath()%>/mypageEditForm.do" style="width:100%;">
                    <button type="button" class="my-btn">수정</button>
                </a>
            </div>
        </div>
        <div class="profile-right">
            <table class="profile-table">
                <tr><th>아이디</th><td><%=user.getUserId()%></td></tr>
                <tr><th>이름</th><td><%=user.getName()%></td></tr>
                <tr><th>부서</th><td><%=user.getDepartmentName() == null ? "" : user.getDepartmentName()%></td></tr>
                <tr><th>직책</th><td><%=user.getPosition() == null ? "" : user.getPosition()%></td></tr>
                <tr><th>이메일</th><td><%=user.getEmail() == null ? "" : user.getEmail()%></td></tr>
                <tr><th>전화번호</th><td><%=user.getPhone() == null ? "" : user.getPhone()%></td></tr>
                <tr><th>생년월일</th><td><%=user.getBirthDate() == null ? "" : user.getBirthDate().toString()%></td></tr>
                <tr><th>입사일</th><td><%=user.getJoinDate() == null ? "" : user.getJoinDate().toString()%></td></tr>
                <tr><th>퇴사일</th><td><%=user.getRetireDate() == null ? "" : user.getRetireDate().toString()%></td></tr>
                <% if ("Y".equals(user.getIsAdmin())) { %>
                    <tr><th>관리자여부</th><td>예</td></tr>
                <% } %>
                <tr><th>근로상태</th><td><%=user.getEmpStatus() == null ? "" : user.getEmpStatus()%></td></tr>
                <tr><th>근무상태</th><td><%=user.getWorkStatus() == null ? "" : user.getWorkStatus()%></td></tr>
                <tr><th>로그인상태</th><td><%="Y".equals(user.getLoginStatus()) ? "예" : "아니오"%></td></tr>
            </table>
        </div>
    </div>
</body>
</html>
