<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>수정 성공</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <style>
        body { font-family: 'Segoe UI', Arial, sans-serif; background: #f6f8fc; }
        .popup-bg {
            position: relative; 
            background: rgba(0,0,0,0.22); display: flex; align-items: center; justify-content: center; z-index: 50;
        }
        .popup-box {
            background: #fff; padding: 40px 30px 26px 30px; border-radius: 18px;
            min-width: 320px; box-shadow: 0 2px 18px rgba(60,80,150,0.18);
            text-align: center;
            animation: popupShow .32s cubic-bezier(.62,.2,.36,1.12);
        }
        @keyframes popupShow {
            0% { opacity:0; transform:scale(0.8);}
            100% { opacity:1; transform:scale(1);}
        }
        .popup-title {
            font-size: 1.25em; font-weight: bold; color: #213;
            margin-bottom: 18px;
        }
        .popup-btn {
            background: #386cf4; color: #fff; border: none;
            padding: 11px 36px; border-radius: 24px;
            font-size: 15px; font-weight: 500;
            cursor: pointer; margin-top: 20px;
            transition: background 0.16s;
        }
        .popup-btn:hover { background: #1536ac; }
    </style>
    <script>
        function goMyPage() {
            // 마이페이지로 이동
            window.location.href = "<%=request.getContextPath()%>/mypage.do";
        }
    </script>
</head>
<body>
    <div class="popup-bg">
        <div class="popup-box">
            <div class="popup-title">정보가 성공적으로<br>수정되었습니다.</div>
            <button class="popup-btn" onclick="goMyPage()">확인</button>
        </div>
    </div>
</body>
</html>
