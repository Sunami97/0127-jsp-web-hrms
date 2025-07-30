<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>조직도</title>
<style>
	ul, li { list-style: none; cursor: pointer; margin-left: 10px; }
	.hidden { display: none; }
	.toggle::before { content: '▶ '; }
	.expanded::before { content: '▼ '; }
</style>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
	$(document).ready(function() {
		$(".toggle").click(function() {
			$(this).toggleClass("expanded");
			$(this).siblings("ul").toggleClass("hidden");			
		});
	});
</script>
</head>
<body>
<form action="department.do" method="post">
<h2>조직도</h2>
<ul>
	<c:forEach var="dept" items="${orgChartList}">
		<li>
			<div class="toggle">${dept.departmentName}</div>
			<ul class="hidden">
				<c:forEach var="pos" items="${dept.positions}">
					<li>
						<div class="toggle">${pos.position}</div>
						<ul class="hidden">
							<c:forEach var="user" items="${pos.users}">
								<li>${user.name} (${user.userId})</li>
							</c:forEach>
						</ul>
					</li>
				</c:forEach>
			</ul>
		</li>
	</c:forEach>
</ul>
</form>
</body>
</html>