<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="myPage.dto.UserDepartmentDTO"%>
<%
UserDepartmentDTO user = (UserDepartmentDTO) request.getAttribute("user");
if (user == null) {
%>
<h2>개인정보 수정</h2>
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
    <title>개인정보 수정</title>
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
        .profile-table tr:last-child td, .profile-table tr:last-child th {
            border-bottom: 1px solid #e0e3ea !important;
        }
        /* --- 비밀번호 변경 모달 스타일 --- */
        .pw-modal {
            display: none; position: fixed; z-index: 9999;
            left: 0; top: 0; width: 100vw; height: 100vh;
            background: rgba(0,0,0,0.18);
        }
        .pw-modal-content {
            background: #fff;
            width: 350px; max-width: 95vw;
            margin: 90px auto 0 auto; padding: 0 30px 30px 30px;
            border-radius: 10px;
            box-shadow: 0 3px 16px rgba(60,60,90,0.13);
            position: relative;
        }
        .pw-modal-header {
            background: #377df6;
            color: #fff;
            font-size: 1.13em;
            font-weight: 600;
            padding: 14px 18px 10px 14px;
            border-radius: 10px 10px 0 0;
            position: relative;
            display: flex; align-items: center; justify-content: space-between;
        }
        .pw-modal-close {
            font-size: 1.5em; font-weight: 300;
            cursor: pointer; margin-left: 10px;
        }
        .pw-form-row {
            margin-top: 16px;
            display: flex; flex-direction: column;
        }
        .pw-form-row label {
            margin-bottom: 6px;
            font-weight: 500; font-size: 15px;
        }
        .pw-form-row input {
            font-size: 15px;
            border: 1px solid #ccd8ee;
            border-radius: 8px;
            padding: 8px 12px;
            background: #f7fafd;
            outline: none;
        }
        .pw-modal-btn {
            margin-top: 16px;
            background: #377df6;
            color: #fff; border: none;
            border-radius: 20px;
            padding: 11px 40px;
            font-size: 16px; font-weight: 600;
            cursor: pointer;
            transition: background 0.14s;
        }
        .pw-modal-btn:hover { background: #003bb8; }
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
            .pw-modal-content { width: 95vw; min-width: 0;}
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
        // 비밀번호 변경 모달 열기/닫기
        function openPwModal() {
            document.getElementById('pwModal').style.display = 'block';
        }
        function closePwModal() {
            document.getElementById('pwModal').style.display = 'none';
        }
        window.onclick = function(event) {
            var modal = document.getElementById('pwModal');
            if (event.target == modal) { modal.style.display = "none"; }
        }
    </script>
</head>
<body>
    <form action="<%=request.getContextPath()%>/mypageEditPro.do" method="post">
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
                <button type="button" class="my-btn" onclick="openPwModal()">비밀번호 변경</button>
            </div>
            <div class="btn-group" style="margin-top:12px;">
                <button type="submit" class="my-btn">저장</button>
            </div>
        </div>
        <div class="profile-right">
            <table class="profile-table">
                <tr>
                    <th>아이디</th>
                    <td>
                        <input type="text" name="userId" value="<%=user.getUserId()%>" readonly
                            style="background:#f2f3f8;border:none;color:#888;padding:5px 10px;font-size:15px;width:85%;">
                    </td>
                </tr>
                <tr>
                    <th>이름</th>
                    <td>
                        <input type="text" name="name" value="<%=user.getName()%>"readonly
                            style="background:#f2f3f8;border:none;color:#888;padding:5px 10px;font-size:15px;width:85%;">
                    </td>
                </tr>
                <tr>
                    <th>이메일</th>
                    <td>
                        <input type="email" name="email" value="<%=user.getEmail()==null?"":user.getEmail()%>"
                            style="padding:5px 10px;font-size:15px;width:85%;">
                    </td>
                </tr>
                <tr>
                    <th>전화번호</th>
                    <td>
                        <input type="text" name="phone" value="<%=user.getPhone()==null?"":user.getPhone()%>"
                            style="padding:5px 10px;font-size:15px;width:85%;">
                    </td>
                </tr>
                <tr>
                    <th>생년월일</th>
                    <td>
                        <input type="date" name="birthDate"
                               value="<%=user.getBirthDate()==null?"":user.getBirthDate().toString()%>"readonly
                            style="background:#f2f3f8;border:none;color:#888;padding:5px 10px;font-size:15px;width:85%;">
                    </td>
                </tr>
                <tr>
                    <th>입사일</th>
                    <td>
                        <input type="date" name="joinDate"
                               value="<%=user.getJoinDate()==null?"":user.getJoinDate().toString()%>"readonly
                            style="background:#f2f3f8;border:none;color:#888;padding:5px 10px;font-size:15px;width:85%;">
                    </td>
                </tr>
                <tr>
                    <th>퇴사일</th>
                    <td>
                        <input type="date" name="retireDate"
                               value="<%=user.getRetireDate()==null?"":user.getRetireDate().toString()%>"readonly
                            style="background:#f2f3f8;border:none;color:#888;padding:5px 10px;font-size:15px;width:85%;">
                    </td>
                </tr>
                <tr>
                    <th>부서</th>
                    <td>
                        <input type="text" name="departmentName"
                            value="<%=user.getDepartmentName()==null?"":user.getDepartmentName()%>"readonly
                            style="background:#f2f3f8;border:none;color:#888;padding:5px 10px;font-size:15px;width:85%;">
                    </td>
                </tr>
                <tr>
                    <th>직책</th>
                    <td>
                        <input type="text" name="position"
                            value="<%=user.getPosition()==null?"":user.getPosition()%>" readonly
                            style="background:#f2f3f8;border:none;color:#888;padding:5px 10px;font-size:15px;width:85%;">
                    </td>
                </tr>
            </table>
        </div>
    </div>
    </form>

    <!-- ========== [비밀번호 변경 모달 영역] ========== -->
    <div id="pwModal" class="pw-modal">
        <div class="pw-modal-content">
            <div class="pw-modal-header">
                <span>비밀번호 변경</span>
                <span class="pw-modal-close" onclick="closePwModal()">&times;</span>
            </div>
         <form method="post" action="<%=request.getContextPath()%>/pwUpdatePro.do" style="margin-top:12px;">
    <input type="hidden" name="userId" value="<%=user.getUserId()%>">
    <div class="pw-form-row">
        <label>기존 비밀번호</label>
        <input type="password" name="currentPw" required autocomplete="current-password">
    </div>
    <div class="pw-form-row">
        <label>새 비밀번호</label>
        <input type="password" name="newPw" required autocomplete="new-password">
    </div>
    <div class="pw-form-row">
        <label>새 비밀번호 확인</label>
        <input type="password" name="newPw2" required autocomplete="new-password">
    </div>
    <div style="text-align:center; margin-top:24px;">
        <button type="submit" class="pw-modal-btn">변경</button>
    </div>
</form>

        </div>
    </div>
</body>
</html>
