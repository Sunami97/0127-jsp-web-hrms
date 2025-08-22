<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
request.setCharacterEncoding("UTF-8");
String userI = request.getParameter("updateId");
%>

<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/adminRegister.css">
<title>Insert title here</title>
<script type="text/javascript">
//퇴사일 설정과 제출 기능 退社日の設定と提出機能
function fire(){

	var con = document.getElementById('dateForm');
	if(con.style.display == "none"){
		
		con.style.display = "block";
		event.preventDefault();
		
	} else{
		if(document.getElementById('dateForm').value){
		let form = document.getElementById("updateForm");
		form.action = 'fire.do';
		form.mothod = 'post';
		const a = confirm("本当に退社処理しますか?");
			if(a){
				alert("処理しました");
				form.submit();
			} else{
				alert("キャンセルしました");
				event.preventDefault();
			}
		} else {
			alert("日付を設定してください");
			event.preventDefault();
		}
	}
}
/* 수정 알림창 및 제출 기능 修正通知ウィンドウと提出機能 */
function update() {

		const aws = confirm("本当に修正しますか？");
		if (aws) {

			const form = document.getElementById('updateForm')
			form.action = "update.do";
			form.method = "post";
			form.submit();

		} else {

			event.preventDefault();

		}

}
</script>
</head>
<body>
	<form id="updateForm" method="post">
		<div>
			<div class="container">
				<h2>社員情報修正ページ</h2>
				<input type="hidden" name="userId" value="${user.userId }">
				* 名前 <br><input type="text" name="name" value="${user.name}"><br><br> 
				* メール<br><input type="text" name="email" value="${user.email}"><br><br>
				* 連絡先<br><input type="text" name="phone" value="${user.phone}"><br><br> 
				* 職位 <br><select name="position">
							<c:forEach var="pos" items="${positions}">
								<option value="${pos}"<c:if test="${user.position eq pos}">selected</c:if>>${pos}</option>
							</c:forEach>
						  </select><br><br>
				<!-- 部署 -->
				* 部署 <br><select name="departmentId">
							<c:forEach var="entry" items="${departments}">
								<option value="${entry.key}"<c:if test="${user.departmentId eq entry.key}">selected</c:if>>${entry.value}</option>
							</c:forEach>
						  </select><br><br>
			    <!-- 管理者権限 -->
				* 管理者 権限<br> N 
							<input type="radio"name="isAdmin" value="N"<c:if test="${user.isAdmin eq 'N'}">checked</c:if> /> 
							   Y 
							<input type="radio" name="isAdmin" value="Y"<c:if test="${user.isAdmin eq 'Y'}">checked</c:if> /> 
							<br><br>
				<!-- 状態 -->
				* 状態 <br><select name="empStatus">
							<c:forEach var="emp" items="${empStatuss}">
								<option value="${emp}"<c:if test="${user.empStatus eq emp}">selected</c:if>>${emp}</option>
							</c:forEach>
					      </select> <br><br>
							<!-- 만약 퇴사일이 없다면 퇴사버튼 활성화, 버튼을 클릭했을때 지정한 날짜로 퇴사일을 업데이트
										もし退社日がない場合ボタン活性化、ボタンをクリックした時に指定した日に退社日をアップデート -->	
						<input type="date" name="date" id="dateForm" style="display:none">	
							<c:if test="${empty user.retireDate }">				
								<button type="submit" onclick="fire()">退社</button>	 
							</c:if>	     
								<button type="submit" onclick="update()">修正</button>
			</div>
		</div>
	</form>
</body>
</html>