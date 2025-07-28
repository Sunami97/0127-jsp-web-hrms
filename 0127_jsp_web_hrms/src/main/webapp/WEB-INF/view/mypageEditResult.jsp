<%@ page contentType="text/html; charset=UTF-8" %>
<%
    String msg = (String)request.getAttribute("msg");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>개인정보 수정 결과</title>
</head>
<body>
    <h2><%= msg %></h2>
    <a href="<%=request.getContextPath()%>/mypage.do">내 정보로 돌아가기</a>
</body>
</html>
