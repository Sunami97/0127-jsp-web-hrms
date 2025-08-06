<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>연락망 / 連絡網</title>
    <style>
        body { font-family: Arial, sans-serif; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { padding: 8px; border: 1px solid #ddd; text-align: center; }
        .status-box { width: 15px; height: 15px; display: inline-block; border-radius: 3px; }
        .online { background-color: green; }
        .offline { background-color: red; }
    </style>
</head>
<body>

<h2>📇 연락망 보기 / 連絡網の表示</h2>

<!-- 부서별 연락처 그룹(Map) 반복 -->
<!-- 部署ごとの連絡先グループ(Map)を繰り返し -->
<c:forEach var="entry" items="${groupedContacts}">
    <c:set var="departmentName" value="${entry.key}" />
    <c:set var="contactsList" value="${entry.value}" />

    <h3>${departmentName}</h3> <!-- 부서명 / 部署名 -->

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
            <!-- 부서 내 연락처 리스트 반복 -->
            <!-- 部署内の連絡先リストを繰り返し -->
            <c:forEach var="contact" items="${contactsList}">
                <tr>
                    <td>${contact.name}</td>
                    <td>${contact.email}</td>
                    <td>${contact.phone}</td>
                    <td>${contact.joinDate}</td>
                    <td>${contact.position}</td>
                    <td>${contact.statusType}</td>
                    <td>
                        <span class="status-box ${contact.loginStatus.toLowerCase() == 'login' ? 'online' : 'offline'}"></span>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <hr>
</c:forEach>

</body>
</html>
