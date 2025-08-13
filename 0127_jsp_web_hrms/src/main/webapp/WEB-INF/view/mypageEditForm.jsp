<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="common/header.jsp" %>
<%@ page import="myPage.model.UserDepartmentDTO"%>
<%
    // [컨트롤러에서 전달받은 유저 정보] 
    // (コントローラーから受け取ったユーザー情報)
    UserDepartmentDTO userDepartment = (UserDepartmentDTO) request.getAttribute("user");

    // [유저 정보가 없을 때 안내] 
    // (ユーザー情報がない場合の案内)
    if (user == null) {
%>
<h2>개인정보 수정</h2>
<p style="color: red;">
    회원 정보가 없습니다.<br> 세션이 만료되었거나, 로그인 정보가 올바르지 않습니다.<br>
    <a href="<%=request.getContextPath()%>/login.jsp" style="color: #007bff;">로그인 페이지로 이동</a>
</p>
<%
    // 더 이상 실행하지 않고 종료
    // (これ以上実行せずに終了)
    return;
    }
    String position = userDepartment.getPosition() == null ? "" : userDepartment.getPosition();
    String borderColor = "#16a34a"; // 기본 (초록)
    String bgColor = "#bbf7d0"; // 얼굴 배경 (밝은 초록)
    String hairColor = "#0f172a"; // 머리색
    String bodyColor = "#fbbf24"; // 옷색

    // 심즈 느낌: 직급별 컬러만 변경
    if ("部長".equals(position)) {
        borderColor = "#d4af37"; bgColor = "#fef3c7"; hairColor = "#78350f"; bodyColor = "#fde68a";
    } else if ("課長".equals(position)) {
        borderColor = "#2563eb"; bgColor = "#dbeafe"; hairColor = "#334155"; bodyColor = "#3b82f6";
    } else if ("社長".equals(position)) {
        borderColor = "#0f172a"; bgColor = "#f1f5f9"; hairColor = "#0f172a"; bodyColor = "#94a3b8";
    }
    String displayPosition = position.isEmpty() ? "직원" : position;
%>
<!DOCTYPE html>
<html>
<head>
    <title>개인정보 수정</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <!-- 아이콘 폰트 라이브러리 (FontAwesome 등) -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
    <style>
        /* [페이지 전체 배경 및 폰트] */
        body {
            font-family: 'Segoe UI', Arial, sans-serif;
            background: #f5f7fa;
        }
        /* [프로필 카드 레이아웃] */
        .profile-card {
            display: flex;
            max-width: 700px;
            margin: 40px auto;
            background: #fff;
            border-radius: 18px;
            box-shadow: 0 2px 16px rgba(0,0,0,0.11);
            overflow: hidden;
            min-height: 340px;
            padding: 80px;
        }
        /* [왼쪽 영역: 사진/버튼] */
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
        /* [프로필 사진] */
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
        /* [사진 변경 버튼] */
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
        /* [이름/부서/직책] */
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
        /* [버튼 그룹 영역] */
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
        /* [오른쪽: 내 정보 입력 영역] */
        .profile-right {
            flex: 1;
            padding: 38px 32px;
            min-width: 0;
            display: flex;
            flex-direction: column;
            justify-content: center;
        }
        /* [테이블 스타일] */
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
        /* [비밀번호 변경 모달 영역 스타일] */
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
        /* [모바일 반응형 레이아웃] */
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
        // [프로필 사진 미리보기] (프로필画像プレビュー)
        function previewProfileImg(input) {
            if (input.files && input.files[0]) {
                var reader = new FileReader();
                reader.onload = function(e) {
                    document.getElementById('profilePreview').src = e.target.result;
                }
                reader.readAsDataURL(input.files[0]);
            }
        }
        // [비밀번호 변경 모달 열기] (パスワード変更モーダルを開く)
        function openPwModal() {
            document.getElementById('pwModal').style.display = 'block';
        }
        // [모달 닫기] (モーダルを閉じる)
        function closePwModal() {
            document.getElementById('pwModal').style.display = 'none';
        }
        // [모달 바깥 클릭 시 닫힘] (モーダル外クリックで閉じる)
        window.onclick = function(event) {
            var modal = document.getElementById('pwModal');
            if (event.target == modal) { modal.style.display = "none"; }
        }
    </script>
</head>
<body>
    <!-- [개인정보 수정 폼] (個人情報編集フォーム) -->
    <form action="<%=request.getContextPath()%>/mypageEditPro.do" method="post">
    <div class="profile-card">
        <!-- [왼쪽 영역: 프로필/이름/버튼] -->
        <div class="profile-left">
         <div class="profile-img-wrapper" style="position:relative;">
    <svg width="140" height="140" viewBox="0 0 140 140" style="display:block;">
        <!-- 외곽 원 -->
        <circle cx="70" cy="70" r="68" fill="<%=bgColor%>" stroke="<%=borderColor%>" stroke-width="4"/>
        <!-- 머리 (심즈 스타일) -->
        <ellipse cx="70" cy="56" rx="34" ry="28" fill="<%=hairColor%>" />
        <!-- 얼굴 (피부색) -->
        <ellipse cx="70" cy="68" rx="31" ry="26" fill="#fee2b6"/>
        <!-- 몸통(상의) -->
        <rect x="43" y="90" width="54" height="28" rx="14" fill="<%=bodyColor%>"/>
        <!-- 눈 -->
        <ellipse cx="60" cy="68" rx="4" ry="3" fill="#111"/>
        <ellipse cx="80" cy="68" rx="4" ry="3" fill="#111"/>
        <!-- 미소 -->
        <path d="M60 80 Q70 87 80 80" stroke="#b91c1c" stroke-width="2" fill="none"/>
        <!-- 얼굴에 직급명 (굵은 폰트) -->
        <text x="70" y="120" text-anchor="middle" font-size="17" font-weight="bold" fill="<%=borderColor%>"><%=displayPosition%></text>
    </svg>
 
</div>
            <div class="profile-name"><%=user.getName()%></div>
            <div class="profile-pos">
                <%=userDepartment.getDepartmentName() == null ? "" : userDepartment.getDepartmentName()%>
                <% if(user.getPosition() != null && !user.getPosition().isEmpty()) { %>
                    / <%= user.getPosition() %>
                <% } %>
            </div>
            <!-- [비밀번호 변경 버튼] -->
            <div class="btn-group">
          <button type="button" class="my-btn" onclick="openPwModal()">パスワード変更</button>
</div>
<!-- [저장 버튼] -->
<div class="btn-group" style="margin-top:12px;">
    <button type="submit" class="my-btn">保存</button>
            </div>
        </div>
        <!-- [오른쪽: 내 정보 입력폼 (일부 readonly)] -->
        <div class="profile-right">
            <table class="profile-table">
             <tr>
    <th>ユーザーID <%-- ユーザーアイディー (yuuzaa aideii) --%></th>
    <td>
        <input type="text" name="userId" value="<%=userDepartment.getUserId()%>" readonly
            style="background:#f2f3f8;border:none;color:#888;padding:5px 10px;font-size:15px;width:85%;">
    </td>
</tr>
<tr>
    <th>氏名 <%-- しめい (shimei) --%></th>
    <td>
        <input type="text" name="name" value="<%=user.getName()%>" readonly
            style="background:#f2f3f8;border:none;color:#888;padding:5px 10px;font-size:15px;width:85%;">
    </td>
</tr>
<tr>
<th style="white-space:nowrap;">メールアドレス <%-- 메에루 아도레스 --%></th>
    
    <td>
        <input type="email" name="email" value="<%=userDepartment.getEmail()==null?"":userDepartment.getEmail()%>"
            style="padding:5px 10px;font-size:15px;width:85%;">
    </td>
</tr>
<tr>
    <th>電話番号 <%-- でんわばんごう (denwa bangou) --%></th>
    <td>
        <input type="text" name="phone" value="<%=userDepartment.getPhone()==null?"":userDepartment.getPhone()%>"
            style="padding:5px 10px;font-size:15px;width:85%;">
    </td>
</tr>
<tr>
    <th>生年月日 <%-- せいねんがっぴ (seinengappi) --%></th>
    <td>
        <input type="date" name="birthDate"
               value="<%=userDepartment.getBirthDate()==null?"":userDepartment.getBirthDate().toString()%>" readonly
            style="background:#f2f3f8;border:none;color:#888;padding:5px 10px;font-size:15px;width:85%;">
    </td>
</tr>
<tr>
    <th>部署 <%-- ぶしょ (busho) --%></th>
    <td>
        <input type="text" name="departmentName"
            value="<%=userDepartment.getDepartmentName()==null?"":userDepartment.getDepartmentName()%>" readonly
            style="background:#f2f3f8;border:none;color:#888;padding:5px 10px;font-size:15px;width:85%;">
    </td>
</tr>
<tr>
    <th>役職 <%-- やくしょく (yakushoku) --%></th>
    <td>
        <input type="text" name="position"
            value="<%=user.getPosition()==null?"":user.getPosition()%>" readonly
            style="background:#f2f3f8;border:none;color:#888;padding:5px 10px;font-size:15px;width:85%;">
    </td>
</tr>
<tr>
    <th>入社日 <%-- にゅうしゃび (nyuushabi) --%></th>
    <td>
        <input type="date" name="joinDate"
               value="<%=userDepartment.getJoinDate()==null?"":userDepartment.getJoinDate().toString()%>" readonly
            style="background:#f2f3f8;border:none;color:#888;padding:5px 10px;font-size:15px;width:85%;">
    </td>
</tr>
<tr>
    <th>退社日 <%-- たいしゃび (taishabi) --%></th>
    <td>
        <input type="date" name="retireDate"
               value="<%=userDepartment.getRetireDate()==null?"":userDepartment.getRetireDate().toString()%>" readonly
            style="background:#f2f3f8;border:none;color:#888;padding:5px 10px;font-size:15px;width:85%;">
    </td>
</tr>

               
            </table>
        </div>
    </div>
    </form>

    <!-- [비밀번호 변경 모달 영역] -->
    <div id="pwModal" class="pw-modal">
        <div class="pw-modal-content">
            <div class="pw-modal-header">
                <span>パスワード変更</span>
                <span class="pw-modal-close" onclick="closePwModal()">&times;</span>
            </div>
            <!-- [비밀번호 변경 폼] -->
           <form method="post" action="<%=request.getContextPath()%>/pwUpdatePro.do" style="margin-top:12px;">
    <input type="hidden" name="userId" value="<%=userDepartment.getUserId()%>">
    <div class="pw-form-row">
        <label>現在のパスワード <%-- げんざい の ぱすわーど --%></label>
        <input type="password" name="currentPw" required autocomplete="current-password">
    </div>
    <div class="pw-form-row">
        <label>新しいパスワード <%-- あたらしい ぱすわーど --%></label>
        <input type="password" name="newPw" required autocomplete="new-password">
    </div>
    <div class="pw-form-row">
        <label>新しいパスワード（確認） <%-- あたらしい ぱすわーど（かくにん） --%></label>
        <input type="password" name="newPw2" required autocomplete="new-password">
    </div>
    <div style="text-align:center; margin-top:24px;">
        <button type="submit" class="pw-modal-btn">変更 <%-- へんこう --%></button>
    </div>
</form>

                </div>
            </form>
        </div>
    </div>
    <%@ include file="common/footer.jsp" %>
</body>
</html>
