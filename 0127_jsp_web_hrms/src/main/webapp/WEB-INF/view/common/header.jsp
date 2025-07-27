<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="user.model.UserDTO" %>

<%
    UserDTO user = (UserDTO) session.getAttribute("loginUser");
    if (user == null) {
        response.sendRedirect("../WEb-INF/view/main.jsp");
        return;
    }
%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

</head>
<body>
<div class="header">
    <div class="header-left">
        <a href="main.do" class="logo">인사관리시스템</a>
    </div>
    <div class="header-right">
        <span><%= user.getName() %>님 환영합니다.</span>
        <form action="logout.do" method="post" style="display:inline;">
            <button type="submit" class="logout-btn">로그아웃</button>
        </form>
    </div>
</div>
</body>
</html>