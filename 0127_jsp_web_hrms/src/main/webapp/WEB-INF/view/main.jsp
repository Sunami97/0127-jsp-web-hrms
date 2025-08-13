<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="common/header.jsp" %>
<%@ include file="common/nav.jsp" %>

<%
    user.model.UserDTO User = (user.model.UserDTO) session.getAttribute("loginUser");
    boolean isAdmin = User != null && "Y".equals(user.getIs_admin());
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8"> 
<title>메인</title>
<link rel="stylesheet" type="text/css" href="css/style.css">

<link href="https://cdn.jsdelivr.net/npm/remixicon@3.5.0/fonts/remixicon.css" rel="stylesheet">
</head>
<body>
<div class="menu-container">
        <a href="myInfo.do" class="menu-box">
            <i class="ri-user-3-fill"></i>
            <span>マイページ</span>
        </a>
    
    <a href="department.do" class="menu-box">
        <i class="ri-organization-chart"></i>
        <span>組織図</span>
    </a>
    <a href="contactList.do" class="menu-box">
        <i class="ri-contacts-book-fill"></i>
        <span>連絡網</span>
    </a>
    <a href="leaveManage.do" class="menu-box">
        <i class="ri-calendar-check-fill"></i>
        <span>年次管理</span>
    </a>
    <% if (isAdmin) { %>
        <a href="empManage.do" class="menu-box">
            <i class="ri-team-fill"></i>
            <span>社員管理</span>
        </a>
    <% } %>
</div>
<%@ include file="common/footer.jsp" %>
</body>
</html>