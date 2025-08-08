<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2025-08-06
  Time: 오후 6:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags" %>
<%@ include file="common/header.jsp" %>
<%
    user.model.UserDTO User = (user.model.UserDTO) session.getAttribute("loginUser");
    boolean isAdmin = User != null && "Y".equals(user.getIs_admin());
%>
<!DOCTYPE html>
<html>
<head>
    <title>연차 신청 내역</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f5f8ff;
            color: #333;
            padding: 20px;
        }

        table {
            border-collapse: collapse;
            width: 60%;
            margin: 30px auto;
            background-color: #fff;
            border: 1px solid #79BAF2;
            border-radius: 8px;
        }

        th, td {
            border: 1px solid #79BAF2;
            padding: 12px;
            text-align: left;
        }

        th {
            background-color: #e6ecff;
            color: #2E83F2;
        }

        td {
            background-color: #f9faff;
        }

        .button-area {
            width: 60%;
            margin: 20px auto;
        }

        .button-area.center {
            text-align: center;
        }

        .button-area.right {
            text-align: right;
        }

        .button-area a, .button-area form button {
            display: inline-block;
            padding: 8px 14px;
            margin-left: 8px;
            border: none;
            background-color: #2E83F2;
            color: white;
            text-decoration: none;
            cursor: pointer;
            border-radius: 5px;
            font-size: 14px;
        }

        .button-area a:hover, .button-area form button:hover {
            background-color: #3071F2;
        }

        .button-area form {
            display: inline;
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
        <td>${paidLeaveData.paidLeave.userId}, ${loginUser.position}</td>
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

<div class="button-area center">
    <a href="${pageContext.request.contextPath}/paidleave.do?pageNo=${pageNo}">목록</a>
</div>

<c:if test="${loginUser.user_id == paidLeaveData.paidLeave.userId}">
    <div class="button-area center">
        <a href="#">삭제</a>
    </div>
</c:if>

<c:if test="${loginUser.is_admin == 'Y'}">
    <div class="button-area right">
        <form action="<c:url value='/paidleave/status.do'/>" method="post">
            <input type="hidden" name="leaveId" value="${paidLeaveData.paidLeave.leaveId}">
            <input type="hidden" name="status" value="승인" />
            <button type="submit">승인</button>
        </form>
        <form action="<c:url value='/paidleave/status.do'/>" method="post">
            <input type="hidden" name="leaveId" value="${paidLeaveData.paidLeave.leaveId}">
            <input type="hidden" name="status" value="거절" />
            <button type="submit">거절</button>
        </form>
    </div>
</c:if>

<%@ include file="common/footer.jsp" %>
</body>
</html>