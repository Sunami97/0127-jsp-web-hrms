<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
<title>로그인 페이지</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>
	人事管理 プログラム
	<div class="main-container">
   
    <form action="login.do" method="post">
        <label for="user_id"><i class="fa fa-user"></i></label>
        <input type="text" id="user_id" name="user_id" placeholder="ID" required /><br><br>
        <label for="password"><i class="fa fa-lock"></i></label>
        <input type="password" id="password" name="password" placeholder="PASSWORD" required /><br><br>
        <button type="submit">SIGN IN</button>
        <%
    String msg = (String) request.getAttribute("msg");
    if (msg != null) {
%>
    <div style="color:red;"><%=msg%></div>
<%
    }
%>
    </form>
</div>  
</body>
</html>