<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2025-08-06
  Time: 오후 6:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="common/header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags" %>
<%
    user.model.UserDTO User = (user.model.UserDTO) session.getAttribute("loginUser");
    boolean isAdmin = User != null && "Y".equals(user.getIs_admin());
%>
<!DOCTYPE html>
<html>
<head>
    <title>有給休暇申請詳細</title>
      <link rel="stylesheet" type="text/css" href="<c:url value='/css/style.css'/>">
    <link href="https://cdn.jsdelivr.net/npm/remixicon@3.5.0/fonts/remixicon.css" rel="stylesheet">
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
            margin-top: 100px;
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
        <th>申請番号</th>
        <td>${paidLeaveData.paidLeave.leaveId}</td>
    </tr>
    <tr>
        <th>申請者</th>
        <td>${paidLeaveData.paidLeave.userId}, ${loginUser.position}</td>
    </tr>
    <tr>
        <th>申請日</th>
        <td>${paidLeaveData.paidLeave.startDate} ~ ${paidLeaveData.paidLeave.endDate}</td>
    </tr>
    <tr>
        <th>使用日数</th>
        <td>${paidLeaveData.paidLeave.days}日</td>
    </tr>
    <tr>
        <th>理由</th>
        <td>${paidLeaveData.paidLeave.reason}</td>
    </tr>
    <tr>
        <th>状態</th>
        <td>${paidLeaveData.paidLeave.status}</td>
    </tr>
    <tr>
        <th>承認者</th>
        <td>${paidLeaveData.paidLeave.approvedBy}</td>
    </tr>
    <tr>
        <th>承認日</th>
        <td>${paidLeaveData.paidLeave.approvedAt}</td>
    </tr>
</table>

<!-- ページ番号設定（パラメータが空なら1に設定） -->
<c:set var="pageNo" value="${empty param.pageNo ? '1' : param.pageNo}" />

<div class="button-area center">
    <a href="${pageContext.request.contextPath}/paidleave.do?pageNo=${pageNo}">一覧</a>
    <c:if test="${loginUser.user_id == paidLeaveData.paidLeave.userId
                  && paidLeaveData.paidLeave.status != '承認'}">
        <a href="delete.do?leaveId=${paidLeaveData.paidLeave.leaveId}"
           onclick="return confirm('本当に削除しますか？');">削除</a>
    </c:if>
</div>

<!-- 管理者向け承認・却下ボタン -->
<c:if test="${loginUser.is_admin == 'Y'}">
    <div class="button-area right">
        <!-- 承認ボタン -->
        <form action="<c:url value='/paidleave/status.do'/>" method="post">
            <input type="hidden" name="leaveId" value="${paidLeaveData.paidLeave.leaveId}">
            <input type="hidden" name="status" value="承認" />
            <button type="submit">承認</button>
        </form>
        <!-- 却下ボタン -->
        <form action="<c:url value='/paidleave/status.do'/>" method="post">
            <input type="hidden" name="leaveId" value="${paidLeaveData.paidLeave.leaveId}">
            <input type="hidden" name="status" value="却下" />
            <button type="submit">却下</button>
        </form>
    </div>
</c:if>

<%@ include file="common/footer.jsp" %>
</body>
</html>