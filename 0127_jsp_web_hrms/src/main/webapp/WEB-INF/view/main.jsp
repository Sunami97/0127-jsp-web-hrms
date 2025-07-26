<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="user.model.UserDTO" %>
<%
    UserDTO user = (UserDTO) session.getAttribute("loginUser");
    if(user == null) {
        response.sendRedirect("index.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메인</title>
</head>
<body>
환영합니다, <%=user.getName()%>님!
로그인성공
</body>
</html>