<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="user.model.UserDTO" %>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8"> 
<title>메인</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="common/header.jsp" %>
환영합니다, <%=user.getName()%>님!
로그인성공
<%@ include file="common/footer.jsp" %>
</body>
</html>