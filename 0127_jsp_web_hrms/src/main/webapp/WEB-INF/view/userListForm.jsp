<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
input[type="date"] {
  appearance: none;
  -webkit-appearance: none;
  -moz-appearance: none;

  padding: 4px 8px;             /* 안쪽 여백 줄임 */
  font-size: 12px;              /* 글자 크기 줄임 */
  border: 1px solid #ccc;
  border-radius: 4px;
  background-color: #fff;
  color: #333;
  width: 120px;                 /* 전체 너비 줄임 */
  box-sizing: border-box;
}

input[type="date"]:focus {
  border-color: #007bff;
  outline: none;
  box-shadow: 0 0 0 2px rgba(0, 123, 255, 0.2);
  cursor: pointer;
}
.body {
	background-color: #f8f9fa;
	margin: 0;
}

.board-table {
	width: 100%;
	table-layout: auto; /* 셀 내용에 맞게 너비 자동 조절 */
	border-collapse: collapse;
}

.board-table th {
	display:fixed;
	color:#f2f2f2;
	background-color: #79baf2;
	white-space: nowrap;
	padding: 10px 20px;
	border: 1px solid #3071f2;
	text-align: center;
	height: auto;
	line-height: 30px;
	overflow: hidden;
	text-overflow: ellipsis;
}

.board-table td {
	padding: 10px 20px;
	border: 1px solid #dee2e6;
	text-align: center;
	white-space: normal; /* 줄바꿈 허용 */
	word-break: break-word; /* 단어 길면 강제로 자르기 */
	line-height: 30px;
}

.board-table thead {
	background-color: #f1f3f5;
}
 

</style>
</head>
<body>
	<h2 style="text-align: center;">📋社員管理画面</h2>
	<form method="post" name="form" id="form" action="infoForm.do">
	<!-- 조회한 총 수 照会した総数 -->
		照会${count}件
		<table class="board-table" id="user-table">
			<thead>
				<tr>
					<!-- 사원 정보 속성 社員情報属性 -->
					<th class="text-center">ID</th>
					<th class="text-center">名前</th>
					<th class="text-center">メール</th>
					<th class="text-center">連絡先</th>
					<th class="text-center">生年月日</th>
					<th class="text-center">入社日</th>
					<th class="text-center">退社日</th>
					<th class="text-center">職位</th>
					<th class="text-center">部署</th>
					<th class="text-center">管理者 権限</th>
					<th class="text-center">状態</th>
					<th class="text-center">勤務状態</th>
					<!-- <th class="text-center">ログイン状態</th> -->
				</tr>
			</thead>
			<tbody>
				<!-- 조회한 사원 별 정보 출력 照会した社員別情報の出力 -->
				<c:forEach var="user" items="${user}">
					<tr>
						<td>
						<a href="infoForm.do?userId=${user.userId}">${user.userId}</a></td>
						<td>${user.name}</td>
						<td>${user.email}</td>
						<td>${user.phone}</td>
						<td>${user.birthDate}</td>
						<td>${user.joinDate}</td>
						<td>
							 <c:choose>
								<c:when test="${empty user.retireDate}">								
										
								</c:when>
								<c:otherwise>
   										${user.retireDate}
   									 </c:otherwise>
							</c:choose>
						</td>
						<td>${user.position}</td>
						<td>${user.departmentName}</td>
						<td>${user.isAdmin}</td>
						<td>${user.empStatus}</td>
						<td>${user.workStatus}</td>
						<%-- <td>${user.loginStatus}</td> --%>
					</tr>
				</c:forEach>


			</tbody>
		</table>

		

	</form>


</body>
</html>