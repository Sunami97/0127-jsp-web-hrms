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

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
            box-shadow: 0 0 10px rgba(0,0,255,0.1);
        }

        th, td {
            border: 1px solid #b3c6ff;
            padding: 12px;
            text-align: center;
        }

        th {
            background-color: #e6ecff;
        }

        td {
            background-color: #f9faff;
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

        .apply-link {
            text-align: right;
            margin: 20px 0 0;
        }

        .apply-link a {
            display: inline-block;
            padding: 8px 14px;
            border: none;
            background-color: #3366cc;
            color: white;
            text-decoration: none;
            cursor: pointer;
            border-radius: 5px;
        }

        .apply-link a:hover {
            background-color: #003399;
        }

        .pagination {
            text-align: center;
            padding: 20px 0;
        }

        .pagination a {
            margin: 0 5px;
            text-decoration: none;
            color: #3366cc;
            font-weight: bold;
        }

        .pagination a:hover {
            color: #003399;
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
</body>
</html>