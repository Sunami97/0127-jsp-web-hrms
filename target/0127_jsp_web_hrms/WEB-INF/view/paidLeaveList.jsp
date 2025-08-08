<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2025-08-06
  Time: 오후 12:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="common/header.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>연차 신청</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f5f8ff;
            color: #333;
            padding: 20px;
        }

        h2 {
            color: #2E83F2;
            text-align: center;
            margin-bottom: 20px;
        }

        table {
            width: 60%;
            margin: 0 auto 30px;
            border-collapse: collapse;
            background-color: #fff;
            border-radius: 8px;
            overflow: hidden;
        }

        th, td {
            border: 1px solid #79BAF2;
            padding: 12px;
            text-align: center;
        }

        th {
            background-color: #e6ecff;
            color: #2E83F2;
        }

        td {
            background-color: #f9faff;
        }

        tr:hover {
            background-color: #e4f0ff;
            cursor: pointer;
        }

        .pending {
            color: orange;
            font-weight: bold;
        }

        .approved {
            color: green;
            font-weight: bold;
        }

        .rejected {
            color: red;
            font-weight: bold;
        }

        .apply-link {
            width: 60%;
            margin: 0 auto 30px;
            text-align: right;
        }

        .apply-link a {
            display: inline-block;
            padding: 8px 14px;
            background-color: #2E83F2;
            color: white;
            border-radius: 5px;
            text-decoration: none;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        .apply-link a:hover {
            background-color: #3071F2;
        }

        .pagination {
            width: 60%;
            margin: 0 auto 40px;
            text-align: center;
        }

        .pagination a {
            margin: 0 6px;
            text-decoration: none;
            font-weight: bold;
            color: #2E83F2;
            padding: 6px 12px;
            border-radius: 4px;
            transition: background-color 0.3s ease;
        }

        .pagination a:hover {
            background-color: #3071F2;
            color: white;
        }

    </style>
    <script>
        function goToDetail(id, pageNo) {
            window.location.href = 'paidleave/read.do?no=' + id + '&pageNo=' + pageNo;
        }
    </script>
</head>

<body>
    <h2>연차 신청 리스트</h2>
    <table>
        <thead>
        <tr>
            <th>신청번호</th>
            <th>신청자</th>
            <th>신청일</th>
            <th>사용일수</th>
            <th>상태</th>
            <th>신청일</th>
            <th>승인자</th>
            <th>승인일</th>
        </tr>
        </thead>
        <tbody>
        <c:if test="${paidLeavePage.hasNoPaidLeaves()}">
            <tr>
                <td colspan="8">신청 내역이 없습니다.</td>
            </tr>
        </c:if>
        <c:forEach var="leave" items="${paidLeavePage.content}">
            <tr onclick="goToDetail(${leave.leaveId}, ${paidLeavePage.currentPage})">
                <td>${leave.leaveId}</td>
                <td>${leave.userId}</td>
                <td>${leave.startDate} ~ ${leave.endDate}</td>
                <td>${leave.days}일</td>
                <td>
                    <c:choose>
                        <c:when test="${leave.status == '신청중'}">
                            <span class="pending">신청중</span>
                        </c:when>
                        <c:when test="${leave.status == '승인'}">
                            <span class="approved">승인</span>
                        </c:when>
                        <c:when test="${leave.status == '거절'}">
                            <span class="rejected">거절</span>
                        </c:when>
                        <c:otherwise>
                            ${leave.status}
                        </c:otherwise>
                    </c:choose>
                </td>
                <td>${leave.appliedAt}</td>
                <td>${leave.approvedBy}</td>
                <td>${leave.approvedAt}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div class="apply-link">
        <a href="paidleave/write.do">연차신청</a>
    </div>
    <c:if test="${paidLeavePage.hasPaidLeaves()}">
        <div class="pagination">
            <c:if test="${paidLeavePage.startPage > 5}">
                <a href="paidleave.do?pageNo=${paidLeavePage.startPage - 5}">[이전]</a>
            </c:if>
            <c:forEach var="pNo" begin="${paidLeavePage.startPage}" end="${paidLeavePage.endPage}">
                <a href="paidleave.do?pageNo=${pNo}">[${pNo}]</a>
            </c:forEach>
            <c:if test="${paidLeavePage.endPage < paidLeavePage.totalPages}">
                <a href="paidleave.do?pageNo=${paidLeavePage.startPage + 5}">[다음]</a>
            </c:if>
        </div>
    </c:if>
    <%@ include file="common/footer.jsp" %>
</body>
</html>