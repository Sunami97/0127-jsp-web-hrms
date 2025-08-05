<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%
    user.model.UserDTO navUser = (user.model.UserDTO) session.getAttribute("loginUser");
    boolean navIsAdmin = navUser != null && "Y".equals(navUser.getIs_admin());
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>


<nav class="side-nav">
    <ul>
            <li>
                <a href="${pageContext.request.contextPath}/myInfo.do">
                    <i class="ri-user-3-fill"></i>
                    <span>내 정보</span>
                </a>
            </li>
        
        <li>
            <a href="${pageContext.request.contextPath}/orgChart.do">
                <i class="ri-organization-chart"></i>
                <span>조직도 조회</span>
            </a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/contactList.do">
                <i class="ri-contacts-book-fill"></i>
                <span>사원 연락망</span>
            </a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/leaveManage.do">
                <i class="ri-calendar-check-fill"></i>
                <span>연차 관리</span>
            </a>
        </li>
        <% if (navIsAdmin) { %>
            <li>
                <a href="${pageContext.request.contextPath}/empManage.do">
                    <i class="ri-team-fill"></i>
                    <span>사원 관리</span>
                </a>
            </li>
        <% } %>
    </ul>
</nav>



</body>
</html>