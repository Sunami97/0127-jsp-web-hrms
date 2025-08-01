<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>수정 성공</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <style>
        /* 📄 전체 페이지 폰트와 배경색 */
        body { 
            font-family: 'Segoe UI', Arial, sans-serif; 
            background: #f6f8fc; 
        }
        /* ✨ 팝업 뒷배경(어둡게) - 중앙정렬용 flex */
        .popup-bg {
            position: relative; 
            background: rgba(0,0,0,0.22); /* 살짝 투명한 검정 */
            display: flex; 
            align-items: center; 
            justify-content: center; 
            z-index: 50;
            /* 이 스타일은 전체 화면 커버를 원하면 height: 100vh; 추가! */
        }
        /* 🟦 팝업 박스 디자인 (동글동글, 그림자, 중앙정렬) */
        .popup-box {
            background: #fff; 
            padding: 40px 30px 26px 30px; 
            border-radius: 18px;
            min-width: 320px; 
            box-shadow: 0 2px 18px rgba(60,80,150,0.18);
            text-align: center;
            animation: popupShow .32s cubic-bezier(.62,.2,.36,1.12);
        }
        /* ✨ 팝업 등장이 부드럽게! (애니메이션) */
        @keyframes popupShow {
            0% { opacity:0; transform:scale(0.8);}
            100% { opacity:1; transform:scale(1);}
        }
        /* 📢 팝업 타이틀 (굵고 크게) */
        .popup-title {
            font-size: 1.25em; 
            font-weight: bold; 
            color: #213;
            margin-bottom: 18px;
        }
        /* 🟦 확인 버튼 스타일 */
        .popup-btn {
            background: #386cf4; 
            color: #fff; 
            border: none;
            padding: 11px 36px; 
            border-radius: 24px;
            font-size: 15px; 
            font-weight: 500;
            cursor: pointer; 
            margin-top: 20px;
            transition: background 0.16s;
        }
        /* 버튼에 마우스 올리면 색 진해짐 */
        .popup-btn:hover { background: #1536ac; }
    </style>
    <script>
        // "확인" 버튼 클릭 시 마이페이지로 이동하는 함수
        function goMyPage() {
            window.location.href = "<%=request.getContextPath()%>/mypage.do";
        }
    </script>
</head>
<body>
    <!-- 📦 팝업 배경(중앙 정렬) -->
    <div class="popup-bg">
        <!-- 🗨️ 팝업 내용 박스 -->
        <div class="popup-box">
            <!-- 🎉 성공 메시지 -->
            <div class="popup-title">정보가 성공적으로<br>수정되었습니다.</div>
            <!-- 🔵 확인 버튼 : 클릭 시 goMyPage() 실행 -->
            <button class="popup-btn" onclick="goMyPage()">확인</button>
        </div>
    </div>
</body>
</html>
