<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>연락망 / 連絡網</title>
    <style>
        /* 🇰🇷 전체 페이지 배경과 기본 폰트 설정 / 🇯🇵 ページ全体の背景と基本フォント設定 */
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f8fc; /* 연한 블루 배경 / 淡いブルーの背景 */
            margin: 0;
            padding: 0;
        }

        /* 🇰🇷 페이지 제목 스타일 / 🇯🇵 ページタイトルのスタイル */
        h2 {
            background-color: #1976d2; /* 진한 파랑 / 濃い青 */
            color: white; /* 흰색 텍스트 / 白文字 */
            margin: 0;
            padding: 20px;
            font-size: 20px;
        }

        /* 🇰🇷 부서명 스타일 / 🇯🇵 部署名のスタイル */
        h3 {
            color: #1976d2; /* 진한 파랑 텍스트 / 濃い青文字 */
            margin: 30px 20px 10px;
            font-size: 18px;
        }

        /* 🇰🇷 표 스타일 / 🇯🇵 テーブルのスタイル */
        table {
            width: 95%;
            margin: 10px auto 30px;
            border-collapse: collapse; /* 테두리 겹치기 제거 / 枠線の重なりを削除 */
            background-color: white; /* 표 배경 흰색 / テーブルの背景は白 */
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1); /* 그림자 / シャドウ */
            border-radius: 8px; /* 모서리 둥글게 / 角を丸める */
            overflow: hidden; /* 둥근 모서리에 테두리 숨김 / 角丸に枠線を隠す */
        }

        /* 🇰🇷 표 헤더 스타일 / 🇯🇵 テーブルヘッダーのスタイル */
        th {
            background-color: #1976d2; /* 진한 파랑 배경 / 濃い青背景 */
            color: white; /* 흰색 텍스트 / 白文字 */
            padding: 12px;
            text-align: center;
            font-weight: bold;
            font-size: 14px;
        }

        /* 🇰🇷 표 셀 스타일 / 🇯🇵 テーブルセルのスタイル */
        td {
            padding: 10px;
            text-align: center;
            border-bottom: 1px solid #ddd; /* 아래쪽 경계선 / 下線 */
            font-size: 13px;
        }

        /* 🇰🇷 행 호버 시 강조 효과 / 🇯🇵 行ホバー時のハイライト */
        tbody tr:hover {
            background-color: #e3f2fd; /* 연한 블루로 강조 / 淡いブルーで強調 */
        }

        /* 🇰🇷 상태 박스 스타일 / 🇯🇵 ステータスボックスのスタイル */
        .status-box {
            width: 15px;
            height: 15px;
            display: inline-block;
            border-radius: 3px;
        }

        /* 🇰🇷 로그인 상태: 초록색 / 🇯🇵 ログイン中：緑 */
        .online { background-color: #43a047; }

        /* 🇰🇷 오프라인 상태: 빨간색 / 🇯🇵 オフライン：赤 */
        .offline { background-color: #e53935; }

        /* 🇰🇷 구분선 스타일 / 🇯🇵 区切り線のスタイル */
        hr {
            border: 0;
            height: 1px;
            background-color: #1976d2; /* 진한 파랑 / 濃い青 */
            width: 95%;
            margin: 20px auto;
        }
    </style>
</head>
<body>

<!-- 🇰🇷 페이지 상단 제목 / 🇯🇵 ページ上部のタイトル -->
<h2>📇 연락망 보기 / 連絡網の表示</h2>

<!-- 🇰🇷 부서별 연락처 그룹(Map) 반복 / 🇯🇵 部署ごとの連絡先グループ(Map)を繰り返し -->
<c:forEach var="entry" items="${groupedContacts}">
    <c:set var="departmentName" value="${entry.key}" />
    <c:set var="contactsList" value="${entry.value}" />

    <!-- 🇰🇷 부서명 표시 / 🇯🇵 部署名の表示 -->
    <h3>${departmentName}</h3>

    <!-- 🇰🇷 연락처 테이블 / 🇯🇵 連絡先テーブル -->
    <table>
        <thead>
            <tr>
                <th>이름 / 名前</th>
                <th>이메일 / メール</th>
                <th>연락처 / 連絡先</th>
                <th>입사일 / 入社日</th>
                <th>직급 / 職位</th>
                <th>상태 유형 / 状態タイプ</th>
                <th>로그인 상태 / ログイン状態</th>
            </tr>
        </thead>
        <tbody>
            <!-- 🇰🇷 부서 내 연락처 리스트 반복 / 🇯🇵 部署内の連絡先リストを繰り返し -->
            <c:forEach var="contact" items="${contactsList}">
                <tr>
                    <td>${contact.name}</td>
                    <td>${contact.email}</td>
                    <td>${contact.phone}</td>
                    <td>${contact.joinDate}</td>
                    <td>${contact.position}</td>
                    <td>${contact.statusType}</td>
                    <td>
                        <!-- 🇰🇷 로그인 상태에 따라 색상 변경 / 🇯🇵 ログイン状態によって色変更 -->
                        <span class="status-box ${contact.loginStatus.toLowerCase() == 'login' ? 'online' : 'offline'}"></span>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <!-- 🇰🇷 부서 구분선 / 🇯🇵 部署の区切り線 -->
    <hr>
</c:forEach>

</body>
</html>
