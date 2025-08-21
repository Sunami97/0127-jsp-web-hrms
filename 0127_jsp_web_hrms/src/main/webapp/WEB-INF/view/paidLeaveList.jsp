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
    <title>有給休暇申請</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <link href="https://cdn.jsdelivr.net/npm/remixicon@3.5.0/fonts/remixicon.css" rel="stylesheet">
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
            margin-top: 100px;
            margin-bottom: 20px;
        }

        .paid-leave-table table {
            width: 90%;
            margin: 0 auto 30px;
            border-collapse: collapse;
            background-color: #fff;
            border-radius: 8px;
            overflow: hidden;
        }

        .paid-leave-table th,
        .paid-leave-table td {
            border: 1px solid #79BAF2;
            padding: 12px;
            text-align: center;
        }

        .paid-leave-table th {
            background-color: #e6ecff;
            color: #2E83F2;
        }

        .paid-leave-table td {
            background-color: #f9faff;
        }

        .paid-leave-table tr:hover {
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
            width: 90%;
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
    <h2>有給休暇申請リスト</h2>
    <div class="paid-leave-table">
        <table>
            <thead>
            <tr>
                <th>申請番号</th>
                <th>申請者</th>
                <th>休暇期間</th>
                <th>使用日数</th>
                <th>状態</th>
                <th>申請日</th>
                <th>承認者</th>
                <th>承認日</th>
            </tr>
            </thead>
            <tbody>
            <!-- データがない場合の表示 -->
            <c:if test="${paidLeavePage.hasNoPaidLeaves()}">
                <tr>
                    <td colspan="8">申請履歴がありません。</td>
                </tr>
            </c:if>

            <!-- 有給休暇データのループ表示 -->
            <c:forEach var="leave" items="${paidLeavePage.content}">
                <tr onclick="goToDetail(${leave.leaveId}, ${paidLeavePage.currentPage})">
                    <td>${leave.leaveId}</td>
                    <td>${leave.userId}</td>
                    <td>${leave.startDate} ~ ${leave.endDate}</td>
                    <td>${leave.days}日</td>
                    <td>
                        <c:choose>
                            <c:when test="${leave.status == '申請中'}">
                                <span class="pending">申請中</span>
                            </c:when>
                            <c:when test="${leave.status == '承認'}">
                                <span class="approved">承認</span>
                            </c:when>
                            <c:when test="${leave.status == '却下'}">
                                <span class="rejected">却下</span>
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
    </div>

    <!-- 新規申請リンク -->
    <div class="apply-link">
        <a href="paidleave/write.do">有給休暇申請</a>
    </div>

    <!-- ページネーション -->
    <c:if test="${paidLeavePage.hasPaidLeaves()}">
        <div class="pagination">
            <c:if test="${paidLeavePage.startPage > 5}">
                <a href="paidleave.do?pageNo=${paidLeavePage.startPage - 5}">[前へ]</a>
            </c:if>
            <c:forEach var="pNo" begin="${paidLeavePage.startPage}" end="${paidLeavePage.endPage}">
                <a href="paidleave.do?pageNo=${pNo}">[${pNo}]</a>
            </c:forEach>
            <c:if test="${paidLeavePage.endPage < paidLeavePage.totalPages}">
                <a href="paidleave.do?pageNo=${paidLeavePage.startPage + 5}">[次へ]</a>
            </c:if>
        </div>
    </c:if>
    
    <%@ include file="common/footer.jsp" %>
</body>
</html>