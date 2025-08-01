<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<script>
</script>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
body {
	background-color: #f8f9fa;
}

.board-table {
	width: 100%;
	table-layout: fixed;
	max-width: 900px;
	margin: auto;
	border-collapse: collapse;
	background-color: white;
	box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
}

.board-table th, .board-table td {
	padding: 12px 16px;
	border: 1px solid #dee2e6;
	text-align: center;
	height: 10px;
	line-height: 20px;
}

.board-table thead {
	background-color: #f1f3f5;
}
</style>
</head>
<body>
	<h2 style="text-align: center;">📋 사원 관리 화면</h2>
	<form method="post" name="form">${count}명있습니다.
		<table class="board-table" id="user-table">
			<thead>
				<tr>
					<th width="70" class="text-center">아이디</th>
					<th width="140" class="text-center">패스워드</th>
					<th width="70" class="text-center">이름</th>
					<th width="200" class="text-center">이메일</th>
					<th width="140" class="text-center">연락처</th>
					<th width="140" class="text-center">생년월일</th>
					<th width="120" class="text-center">입사일</th>
					<th width="120" class="text-center">퇴사일</th>
					<th width="20" class="text-center">직급</th>
					<th width="20" class="text-center">부서번호</th>
					<th width="20" class="text-center">관리자 여부</th>
					<th width="20" class="text-center">상태</th>
					<th width="20" class="text-center">근무상태</th>
					<th width="20" class="text-center">로그인상태</th>
					<th width="20" class="text-center">삭제</th>
					<th width="20" class="text-center">수정</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="user" items="${user}">
					<tr>
						<td>${user.userId}</td>
						<td>${user.password}</td>
						<td>${user.name}</td>
						<td>${user.email}</td>
						<td>${user.phone}</td>
						<td>${user.birthDate}</td>
						<td>${user.joinDate}</td>
						<td>
							<c:choose>
									<c:when test="${empty user.retireDate}">
								 		<%-- <input type="hidden" name="userId" value="${user.userId}"> --%>
   										 <button type="submit" onclick="javascript: form.action='fire.do'">퇴사</button>
   									 </c:when>
   									 <c:otherwise>
   										${user.retireDate}
   									 </c:otherwise>	
							</c:choose>
						</td>
						<td>${user.position}</td>
						<td>${user.departmentId}</td>
						<td>${user.isAdmin}</td>
						<td>${user.empStatus}</td>
						<td>${user.workStatus}</td>
						<td>${user.loginStatus}</td>
						<td><input type="checkbox" name="userId" value="${user.userId}"></td>
						<td><input type="radio" name="userId" value="${user.userId}"></td>
					</tr>
				</c:forEach>


			</tbody>
		</table>
		<button type="submit" onclick="javascript: form.action='delete.do'">삭제</button>

		<button type="submit"
			onclick="javascript: form.action='updateForm.do'">수정</button>

	</form>


</body>
</html>