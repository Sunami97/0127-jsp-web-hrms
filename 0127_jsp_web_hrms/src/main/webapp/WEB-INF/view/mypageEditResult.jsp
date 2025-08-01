<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>정보 수정 결과</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <style>
        /* 🌸 살짝 투명한 배경 */
        body { background: rgba(0,0,0,0.15); }
        /* 🩵 팝업 박스 스타일 (중앙에, 그림자, 동글동글) */
        .popup-box {
            width: 350px;
            margin: 120px auto;
            background: #fff;
            border-radius: 15px;
            box-shadow: 0 4px 24px rgba(0,0,0,0.16);
            text-align: center;
            padding: 36px 30px 28px 30px;
            position: relative;
            animation: popupIn 0.2s; /* 등장 애니메이션 */
        }
        /* 💫 팝업 등장 애니메이션 (밑에서 올라오고 살짝 커짐) */
        @keyframes popupIn {
            from { transform: translateY(30px) scale(0.95); opacity: 0;}
            to { transform: none; opacity: 1;}
        }
        /* 📝 팝업 타이틀 */
        .popup-title {
            font-size: 22px;
            font-weight: bold;
            margin-bottom: 18px;
            color: #23377a;
        }
        /* ✉️ 알림 메시지(성공/실패 결과 등) */
        .popup-msg {
            font-size: 17px;
            color: #444;
            margin-bottom: 32px;
        }
        /* 💙 확인 버튼 스타일 */
        .popup-btn {
            background: #386cf4;
            color: #fff;
            border: none;
            border-radius: 22px;
            font-size: 16px;
            padding: 9px 36px;
            cursor: pointer;
            transition: background 0.15s;
        }
        .popup-btn:hover {
            background: #143092;
        }
    </style>
</head>
<body>
    <!-- ✨ 팝업 전체 박스 (중앙 정렬) -->
    <div class="popup-box">
        <!-- 🎉 알림(타이틀) -->
        <div class="popup-title">알림</div>
        <!-- 😎 컨트롤러에서 setAttribute로 넘겨준 msg(성공/실패 내용) 보여줌! -->
        <div class="popup-msg"><%= request.getAttribute("msg") %></div>
        <!-- 🏠 내 정보로 돌아가는 버튼 (클릭하면 mypage.do로 이동) -->
        <button class="popup-btn" onclick="window.location.href='<%=request.getContextPath()%>/mypage.do'">
            내 정보로 돌아가기
        </button>
    </div>
</body>
</html>
