<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%@ page import="user.model.UserDTO" %>
<%
    UserDTO user = (UserDTO) session.getAttribute("loginUser");
    if (user == null) {
        response.sendRedirect("../index.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메인 페이지</title>

</head>
<body>
	<div class="header">
    <div class="header-left">
        <a href="main.do" class="logo">人事管理システム</a>
    </div>
    <div class="header-right">
        <span><%= user.getName() %> 様、歓迎します.</span>
        <form action="logout.do" method="post" style="display:inline;">
            <button type="submit" class="logout-btn">ログアウト</button>
        </form>
    </div>
</div>
</body>
</html>