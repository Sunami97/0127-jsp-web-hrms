<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2025-08-06
  Time: 오후 12:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>연차 신청</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            padding: 20px;
        }

        h2 {
            display: inline-block;
        }

        .apply-link {
            float: right;
            margin-top: 10px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        th, td {
            border: 1px solid #ccc;
            padding: 10px;
            text-align: center;
        }

        th {
            background-color: #f5f5f5;
        }

        tr:hover {
            background-color: #f0f8ff;
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

        .pagination {
            text-align: center;
            padding: 20px 0;
        }

        .pagination a {
            margin: 0 5px;
            text-decoration: none;
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
            <th>신청자</th>
            <th>시작일</th>
            <th>종료일</th>
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
                <td>${leave.userId}</td>
                <td>${leave.startDate}</td>
                <td>${leave.endDate}</td>
                <td>${leave.days}</td>
                <td>
                    <c:choose>
                        <c:when test="${leave.status == '신청중'}">
                            <span class="pending">신청중</span>
                        </c:when>
                        <c:when test="${leave.status == '승인'}">
                            <span class="approved">승인</span>
                        </c:when>
                        <c:when test="${leave.status == '반려'}">
                            <span class="rejected">반려</span>
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
        <a href="paidleave/write.do">[연차신청하기]</a>
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
</body>
</html>