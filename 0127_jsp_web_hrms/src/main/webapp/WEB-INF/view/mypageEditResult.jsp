<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>情報修正結果</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <style>
        /* 배경: 투명도 적용 (Background with transparency) */
        /* 背景に半透明を適用 */
        body { background: rgba(0,0,0,0.15); }

        /* 팝업 박스: 중앙 정렬, 그림자, 둥근 테두리 (Popup box styling) */
        /* ポップアップボックスの中央揃え・影・角丸 */
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
        /* 팝업 등장 애니메이션 (Popup animation) */
        /* ポップアップ登場アニメーション */
        @keyframes popupIn {
            from { transform: translateY(30px) scale(0.95); opacity: 0;}
            to { transform: none; opacity: 1;}
        }
        /* 타이틀(Title) */
        /* タイトル */
        .popup-title {
            font-size: 22px;
            font-weight: bold;
            margin-bottom: 18px;
            color: #23377a;
        }
        /* 알림 메시지 영역 (Notification message) */
        /* メッセージ表示エリア */
        .popup-msg {
            font-size: 17px;
            color: #444;
            margin-bottom: 32px;
        }
        /* 확인 버튼 (OK button) */
        /* 確認ボタン */
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
    <!-- 팝업 메인 컨테이너 (Popup Main Container) -->
    <!-- ポップアップメインコンテナ -->
 <div class="popup-box">
    <!-- タイトルエリア -->
    <div class="popup-title">お知らせ</div>
    
    <!-- コントローラーから渡されたメッセージを表示 -->
    <div class="popup-msg"><%= request.getAttribute("msg") %></div>
    
    <!-- 確認ボタン：クリックでマイページへ遷移 -->
    <button class="popup-btn" onclick="window.location.href='<%=request.getContextPath()%>/mypage.do'">
        マイページへ戻る
    </button>
</div>

</body>
</html>
