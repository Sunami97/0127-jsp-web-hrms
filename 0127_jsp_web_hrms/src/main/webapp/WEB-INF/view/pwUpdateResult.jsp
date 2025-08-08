<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>비밀번호 변경 결과</title>
    <style>
        /* 전체 페이지 배경을 살짝 어둡게 (Background overlay for popup) */
        /* ページ全体の背景を少し暗く */
        body { background: rgba(0,0,0,0.15); }
        /* 팝업 박스 스타일 (Popup box style) */
        /* ポップアップボックスのスタイル */
        .popup-box {
            width: 350px;
            margin: 120px auto;
            background: #fff;
            border-radius: 15px;
            box-shadow: 0 4px 24px rgba(0,0,0,0.16);
            text-align: center;
            padding: 36px 30px 28px 30px;
            position: relative;
            animation: popupIn 0.2s; /* 팝업 등장 애니메이션 */
        }
        /* 팝업 등장 애니메이션 (Popup animation) */
        /* ポップアップ登場アニメーション */
        @keyframes popupIn {
            from { transform: translateY(30px) scale(0.95); opacity: 0;}
            to { transform: none; opacity: 1;}
        }
        /* 팝업 제목 (Popup Title) */
        /* ポップアップタイトル */
        .popup-title {
            font-size: 22px;
            font-weight: bold;
            margin-bottom: 18px;
            color: #23377a;
        }
        /* 메시지(결과 표시) (Result message) */
        /* 結果メッセージ表示 */
        .popup-msg {
            font-size: 17px;
            color: #444;
            margin-bottom: 32px;
        }
        /* 확인/돌아가기 버튼 스타일 (Button style) */
        /* ボタンスタイル */
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
        /* 버튼 마우스오버 시 색상 변경 (Button hover) */
        /* ボタンホバー時の色変更 */
        .popup-btn:hover {
            background: #143092;
        }
    </style>
</head>
<body>
    <!-- 알림 팝업 박스 (Popup box for notification) -->
    <!-- 通知用ポップアップボックス -->
    <div class="popup-box">
        <!-- 팝업 타이틀 (Popup title) -->
        <!-- ポップアップタイトル -->
        <div class="popup-title">알림</div>
        <!-- 비밀번호 변경 결과 메시지 (Result message from server) -->
        <!-- サーバーからの結果メッセージ -->
        <div class="popup-msg"><%= request.getAttribute("msg") %></div>
        <!-- 내 정보로 이동 버튼 (Move to mypage) -->
        <!-- マイページへ戻るボタン -->
        <button class="popup-btn" onclick="window.location.href='<%=request.getContextPath()%>/mypage.do'">
            내 정보로 돌아가기
        </button>
    </div>
</body>
</html>
