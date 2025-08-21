<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
  request.setCharacterEncoding("UTF-8");
  String userI = request.getParameter("updateId");
%>

<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/adminRegister.css">
<title>Insert title here</title>
</head>
<body>
	<form action="update.do" method="post">
		<div>
	 		<div class="container">
		メール<br><input type="text" name="email"><br><br>
		連絡先<br><input type="text" name="phone"><br><br>
		職位	<br><select name="position">
					<option value="社員">社員</option>
					<option value="主任">主任</option>
					<option value="係長">係長</option>
					<option value="課長">課長</option>
					<option value="次長">次長</option>
					<option value="部長">部長</option>
					<option value="常務">常務</option>
					<option value="専務">専務</option>
					<option value="取締役">取締役</option>
					<option value="代表取締役">代表取締役</option>
					<option value="会長">会長</option>
					<option value="社長">社長</option>
				</select><br><br>			
		部署	<br><select name="departmentId">
					<option value="1">総務部</option>
					<option value="2">人事課</option>
					<option value="3">経理部</option>
					<option value="4">財務部</option>
					<option value="5">営業部</option>
					<option value="6">販売部</option>
					<option value="7">マーケティング部</option>
					<option value="8">開発部</option>
					<option value="9">技術部</option>
					<option value="10">情報システム部</option>
					<option value="11">生産部</option>
					<option value="12">品質管理部</option>
					<option value="13">法務部</option>
					<option value="14">企画部</option>
				</select><br><br>			
		管理者 権限  <br>N<input type="radio" name="isAdmin" checked="checked" value="N">
				  Y<input type="radio" name="isAdmin" value="Y"><br><br>
		状態		<br><select name="empStatus">
					<option value="在職">在職</option>
					<option value="休職">休職</option>
					<option value="退職">退職</option>
				</select><br><br>	
				<input type="hidden" name="updateId" value="<%=userI %>">
		<button type="submit">修整</button>
		</div>
		</div>
	</form>
</body>
</html>