<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>비밀번호 변경 결과</title>
    <style>
         body { background: rgba(0,0,0,0.15); }
        .popup-box {
            width: 350px;
            margin: 120px auto;
            background: #fff;
            border-radius: 15px;
            box-shadow: 0 4px 24px rgba(0,0,0,0.16);
            text-align: center;
            padding: 36px 30px 28px 30px;
            position: relative;
            animation: popupIn 0.2s;
        }
        @keyframes popupIn {
            from { transform: translateY(30px) scale(0.95); opacity: 0;}
            to { transform: none; opacity: 1;}
        }
        .popup-title {
            font-size: 22px;
            font-weight: bold;
            margin-bottom: 18px;
            color: #23377a;
        }
        .popup-msg {
            font-size: 17px;
            color: #444;
            margin-bottom: 32px;
        }
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
    <div class="popup-box">
        <div class="popup-title">알림</div>
        <div class="popup-msg"><%= request.getAttribute("msg") %></div>
        <button class="popup-btn" onclick="window.location.href='<%=request.getContextPath()%>/mypage.do'">내 정보로 돌아가기</button>
    </div>
</body>
</html>
