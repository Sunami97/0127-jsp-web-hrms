<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>비밀번호 변경 결과</title>
    <style>
         /* 🌙 뒷배경을 살짝 어둡게~ 팝업만 또렷! */
         body { background: rgba(0,0,0,0.15); }
        /* 💌 팝업 박스 디자인(가운데, 동글동글, 그림자) */
        .popup-box {
            width: 350px; /* 팝업 가로 */
            margin: 120px auto; /* 위아래 마진, 가운데 정렬 */
            background: #fff; /* 흰색 배경 */
            border-radius: 15px; /* 모서리 둥글게 */
            box-shadow: 0 4px 24px rgba(0,0,0,0.16); /* 그림자 효과 */
            text-align: center;
            padding: 36px 30px 28px 30px; /* 안쪽 여백 */
            position: relative;
            animation: popupIn 0.2s; /* 등장 애니메이션 */
        }
        /* 등장 애니메이션 (밑에서 슝 올라오게) */
        @keyframes popupIn {
            from { transform: translateY(30px) scale(0.95); opacity: 0;}
            to { transform: none; opacity: 1;}
        }
        /* 💙 제목 스타일 */
        .popup-title {
            font-size: 22px;
            font-weight: bold;
            margin-bottom: 18px;
            color: #23377a;
        }
        /* 메세지(변경 성공/실패 결과) */
        .popup-msg {
            font-size: 17px;
            color: #444;
            margin-bottom: 32px;
        }
        /* ✔️ 버튼 스타일 */
        .popup-btn {
            background: #386cf4; /* 파란색 배경 */
            color: #fff;
            border: none;
            border-radius: 22px;
            font-size: 16px;
            padding: 9px 36px;
            cursor: pointer;
            transition: background 0.15s;
        }
        /* 버튼 위에 마우스 올리면 색 진해짐 */
        .popup-btn:hover {
            background: #143092;
        }
    </style>
</head>
<body>
    <!-- 💬 알림 팝업 박스 -->
    <div class="popup-box">
        <!-- 📢 팝업 타이틀 -->
        <div class="popup-title">알림</div>
        <!-- ⚡ 처리 결과 메시지(서버에서 온 내용 보여줌) -->
        <div class="popup-msg"><%= request.getAttribute("msg") %></div>
        <!-- 🏠 내 정보(마이페이지)로 이동 버튼 -->
        <button class="popup-btn" onclick="window.location.href='<%=request.getContextPath()%>/mypage.do'">내 정보로 돌아가기</button>
    </div>
</body>
</html>
