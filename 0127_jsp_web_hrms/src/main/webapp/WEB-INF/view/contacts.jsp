<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>連絡網</title>
    <style>
        /* 전체 페이지 기본 스타일 / ページ全体の基本スタイル */
        body { font-family: Arial, sans-serif; background-color: #f4f8fc; margin: 0; padding: 0; }

        /* 페이지 상단 제목 / ページ上部のタイトル */
        h2 { background-color: #1976d2; color: white; margin: 0; padding: 20px; font-size: 20px; }

        /* 부서명 스타일 / 部署名のスタイル */
        h3 { color: #1976d2; margin: 30px 20px 10px; font-size: 18px; }

        /* 표 기본 스타일 / テーブルの基本スタイル */
        table { width: 95%; margin: 10px auto 30px; border-collapse: collapse; background-color: white;
                box-shadow: 0 4px 12px rgba(0,0,0,0.1); border-radius: 8px; overflow: hidden; }

        /* 표 헤더 스타일 / テーブルヘッダーのスタイル */
        th { background-color: #1976d2; color: white; padding: 12px; text-align: center;
             font-weight: bold; font-size: 14px; }

        /* 표 셀 스타일 / テーブルセルのスタイル */
        td { padding: 10px; text-align: center; border-bottom: 1px solid #ddd; font-size: 13px; }

        /* 행에 마우스를 올렸을 때 효과 / 行にマウスを乗せたときの効果 */
        tbody tr:hover { background-color: #e3f2fd; }

        /* 부서 구분선 / 部署の区切り線 */
        hr { border: 0; height: 1px; background-color: #1976d2; width: 95%; margin: 20px auto; }
    </style>
</head>
<body>

<!-- 페이지 제목 / ページタイトル -->
<h2>📇 連絡網の表示</h2>

<!-- groupedContacts(Map)의 각 부서별 데이터 반복 / groupedContacts(Map)の各部署データを繰り返し表示 -->
<c:forEach var="entry" items="${groupedContacts}">

    <!-- 부서명 표시 / 部署名を表示 -->
    <h3>${entry.key}</h3>

    <!-- 부서별 연락처 테이블 / 部署ごとの連絡先テーブル -->
    <table>
        <thead>
        <tr>
            <th>名前</th>
            <th>メール</th>
            <th>連絡先</th>
            <th>入社日</th>
            <th>職位</th>
            <th>状態タイプ</th>
        </tr>
        </thead>
        <tbody>

        <!-- 해당 부서의 모든 연락처 반복 출력 / 該当部署の全ての連絡先を繰り返し出力 -->
        <c:forEach var="contact" items="${entry.value}">
            <tr>
                <td>${contact.name}</td> <!-- 이름 / 名前 -->
                <td>${contact.email}</td> <!-- 이메일 / メール -->
                <td>${contact.phone}</td> <!-- 연락처 / 連絡先 -->
                <td>${contact.joinDateStr}</td> <!-- 입사일(포맷 완료) / 入社日（フォーマット済み） -->
                <td>${contact.position}</td> <!-- 직급 / 職位 -->
                <td>${contact.workStatus}</td> <!-- 상태 유형 / 状態タイプ -->
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <!-- 부서별 구분선 / 部署ごとの区切り線 -->
    <hr>
</c:forEach>

</body>
</html>
