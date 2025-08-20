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
                    <span>マイページ</span>
                </a>
            </li>
        
        <li>
            <a href="${pageContext.request.contextPath}/department.do">
                <i class="ri-organization-chart"></i>
                <span>組織図</span>
            </a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/contactList.do">
                <i class="ri-contacts-book-fill"></i>
                <span>連絡網</span>
            </a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/paidleave.do">
                <i class="ri-calendar-check-fill"></i>
                <span>年次管理</span>
            </a>
        </li>
        <% if (navIsAdmin) { %>
            <li>
                <a href="${pageContext.request.contextPath}/empManage.do">
                    <i class="ri-team-fill"></i>
                    <span>社員管理</span>
                </a>
            </li>
        <% } %>
    </ul>
</nav>



</body>
</html>