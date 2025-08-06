<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2025-07-30
  Time: 오후 2:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>연차 신청</title>
</head>
<body>
    <h2>연차 신청서</h2>
    <p><strong>사용자 ID:</strong> ${sessionScope.loginUser.user_id}</p>
    <form action="write.do" method="post">
        <input type="hidden" name="userId" value="${userId}" />
        <input type="hidden" name="status" value="신청중" />
        <input type="hidden" name="appliedAt" value="<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()) %>" />

        <label>연차 시작일:</label>
        <input type="date" name="startDate" value="${param.startDate}" required /><br/><br/>

        <label>연차 종료일:</label>
        <input type="date" name="endDate" value="${param.endDate}" required /><br/><br/>

        <label>사용 일수 (예: 0.5, 1):</label>
        <input type="number" name="days" step="0.5" min="0.5" value="${param.days}" required /><br/><br/>

        <label>신청 사유:</label><br/>
        <textarea name="reason" rows="4" cols="50" required>${param.reason}</textarea><br/><br/>

        <label for="approvedBy">승인자 선택</label>
        <select name="approvedBy" id="approvedBy" required>
            <option value="">-- 승인자 선택 --</option>
            <c:forEach var="admin" items="${adminnames}">
                <option value="${admin}">${admin}</option>
            </c:forEach>
        </select><br/><br/>

        <input type="submit" value="신청하기" />
    </form>
</body>
</html>
