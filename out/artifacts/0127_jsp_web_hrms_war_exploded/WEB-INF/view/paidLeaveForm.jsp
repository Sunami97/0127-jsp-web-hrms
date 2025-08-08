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
<%
    String userId = (String) session.getAttribute("userId");
    java.util.Date now = new java.util.Date();
    request.setAttribute("userId", userId);
%>
<html>
<head>
    <title>연차 신청</title>
</head>
<body>
<h2>연차 신청서</h2>
<p><strong>사용자 ID:</strong> ${userId}</p>
<!-- ✅ 일반 사용자용 연차 신청 폼 -->
<c:if test="${userId != 'admin01' && userId != 'manager02'}">
    <form action="write.do" method="post">
        <input type="hidden" name="userId" value="${userId}" />
        <input type="hidden" name="status" value="신청중" />
        <input type="hidden" name="appliedAt" value="<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(now) %>" />

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
</c:if>

<!-- ✅ 관리자용 신청 목록 -->
<c:if test="${userId == 'admin01' || userId == 'manager02'}">
    <c:choose>
        <c:when test="${empty pendingList}">
            <p>승인 대기 중인 신청이 없습니다.</p>
        </c:when>
        <c:otherwise>
            <c:forEach var="request" items="${pendingList}">
                <div style="border:1px solid gray; margin:10px; padding:10px;">
                    <p><strong>신청자:</strong> ${request.userId}</p>
                    <p><strong>신청일:</strong> ${request.appliedAt}</p>
                    <p><strong>연차 기간:</strong> ${request.startDate} ~ ${request.endDate}</p>
                    <p><strong>사용 일수:</strong> ${request.days}</p>
                    <p><strong>사유:</strong> ${request.reason}</p>

                    <form action="updateStatus.do" method="post" style="display:inline;">
                        <input type="hidden" name="requestId" value="${request.id}" />
                        <input type="hidden" name="approvedAt" value="<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(now) %>" />
                        <button type="submit" name="status" value="승인">승인</button>
                        <button type="submit" name="status" value="반려">반려</button>
                    </form>
                </div>
            </c:forEach>
        </c:otherwise>
    </c:choose>
</c:if>

</body>
</html>
