<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>수정 성공</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <style>
        /* 전체 페이지 기본 폰트, 배경색 (Page global font & background) */
        /* ページ全体のフォント・背景色 */
        body { 
            font-family: 'Segoe UI', Arial, sans-serif; 
            background: #f6f8fc; 
        }
        /* 팝업 전체를 감싸는 배경 (Popup background: center alignment, dark transparent) */
        /* ポップアップ全体の背景 (中央配置・半透明) */
        .popup-bg {
            position: relative; 
            background: rgba(0,0,0,0.22); /* 투명한 검정 배경 */
            display: flex; 
            align-items: center; 
            justify-content: center; 
            z-index: 50;
            /* 만약 화면 전체 커버 필요시 height: 100vh; 추가 */
        }
        /* 팝업 박스 (Popup Box) */
        /* ポップアップボックス */
        .popup-box {
            background: #fff; 
            padding: 40px 30px 26px 30px; 
            border-radius: 18px;
            min-width: 320px; 
            box-shadow: 0 2px 18px rgba(60,80,150,0.18);
            text-align: center;
            animation: popupShow .32s cubic-bezier(.62,.2,.36,1.12);
        }
        /* 팝업 등장 애니메이션 (Popup show animation) */
        /* ポップアップ表示アニメーション */
        @keyframes popupShow {
            0% { opacity:0; transform:scale(0.8);}
            100% { opacity:1; transform:scale(1);}
        }
        /* 팝업 타이틀 (Popup Title) */
        /* ポップアップタイトル */
        .popup-title {
            font-size: 1.25em; 
            font-weight: bold; 
            color: #213;
            margin-bottom: 18px;
        }
        /* 확인 버튼 (OK Button) */
        /* 確認ボタン */
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
        /* 버튼 마우스오버시 색상 변경 (Button hover) */
        /* ボタンにホバー時の色変更 */
        .popup-btn:hover { background: #1536ac; }
    </style>
    <script>
        // [확인] 버튼 클릭 시 마이페이지로 이동 (Go to mypage when button is clicked)
        // [確認]ボタンクリックでマイページに遷移
        function goMyPage() {
            window.location.href = "<%=request.getContextPath()%>/mypage.do";
        }
    </script>
</head>
<body>
    <!-- 팝업 전체 레이아웃 (Popup outer layout) -->
    <!-- ポップアップ全体レイアウト -->
    <div class="popup-bg">
        <!-- 팝업 내용 영역 (Popup content) -->
        <!-- ポップアップ内容部分 -->
        <div class="popup-box">
            <!-- 성공 메시지 (Success message) -->
            <!-- 成功メッセージ -->
           <div class="popup-title">情報が正常に<br>更新されました。</div>
<!-- 確認ボタン（goMyPage関数実行） -->
<button class="popup-btn" onclick="goMyPage()">確認</button>

        </div>
    </div>
</body>
</html>
