<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2025-08-06
  Time: 오후 6:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html>
<head>
    <title>연차 신청 내역</title>
    <style>
        body {
            font-family: Arial, sans-serif;
        }

        table {
            border-collapse: collapse;
            width: 60%;
            margin: 30px auto;
            box-shadow: 0 0 10px rgba(0,0,255,0.1);
        }

        th, td {
            border: 1px solid #b3c6ff;
            padding: 12px;
            text-align: left;
        }

        th {
            background-color: #e6ecff;
        }

        td {
            background-color: #f9faff;
        }

        .button-area {
            text-align: right;
            margin: 20px auto;
            width: 60%;
        }

        .button-area a, .button-area form button {
            display: inline-block;
            padding: 8px 14px;
            margin-left: 8px;
            border: none;
            background-color: #3366cc;
            color: white;
            text-decoration: none;
            cursor: pointer;
            border-radius: 5px;
        }

        .button-area form {
            display: inline;
        }

        .button-area a:hover, .button-area form button:hover {
            background-color: #003399;
        }
    </style>
</head>
<body>
<table>
    <tr>
        <th>신청번호</th>
        <td>${paidLeaveData.paidLeave.leaveId}</td>
    </tr>
    <tr>
        <th>신청자</th>
        <td>${paidLeaveData.paidLeave.userId}</td>
    </tr>
    <tr>
        <th>신청일</th>
        <td>${paidLeaveData.paidLeave.startDate} ~ ${paidLeaveData.paidLeave.endDate}</td>
    </tr>
    <tr>
        <th>사용일수</th>
        <td>${paidLeaveData.paidLeave.days}일</td>
    </tr>
    <tr>
        <th>사유</th>
        <td>${paidLeaveData.paidLeave.reason}</td>
    </tr>
    <tr>
        <th>상태</th>
        <td>${paidLeaveData.paidLeave.status}</td>
    </tr>
    <tr>
        <th>승인자</th>
        <td>${paidLeaveData.paidLeave.approvedBy}</td>
    </tr>
    <tr>
        <th>승인일</th>
        <td>${paidLeaveData.paidLeave.approvedAt}</td>
    </tr>
</table>
<c:set var="pageNo" value="${empty param.pageNo ? '1' : param.pageNo}" />
    <div class="button-area">
        <a href="${pageContext.request.contextPath}/paidleave.do?pageNo=${pageNo}">목록</a>
    </div>
    <c:if test="${authUser.id == paidLeaveData.paidLeave.userId}">
        <div class="button-area">
            <a href="#">수정</a>
        </div>
    </c:if>
<c:if test="${authUser.is_admin == 'Y'}">
    <div class="button-area">
        <form action="<c:url value='/paidLeave/approve'/>" method="post">
            <input type="hidden" name="leaveId" value="${paidLeaveData.paidLeave.leaveId}" />
            <button type="submit">승인</button>
        </form>
        <form action="<c:url value='/paidLeave/reject'/>" method="post">
            <input type="hidden" name="leaveId" value="${paidLeaveData.paidLeave.leaveId}" />
            <button type="submit">거절</button>
        </form>
    </div>
</c:if>
</body>
</html>
