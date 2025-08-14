<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../common/header.jsp"%>
<%@ include file="../common/nav.jsp"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>조직도 / 組織図</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
<style>
/* 조직도 컨테이너 */
.org-container {
	background-color: #fff;
	margin: 100px auto 60px 220px; /* nav, header 고려 여백 */
	padding: 20px;
	max-width: 900px;
	box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
	border-radius: 8px;
}

ul {
	list-style: none;
	margin-left: 20px;
	padding-left: 10px;
}

li {
	margin: 5px 0;
	position: relative;
}

input[type="checkbox"] {
	display: none;
}

input[type="checkbox"]+label {
	cursor: pointer;
	font-weight: bold;
	display: inline-block;
	padding: 5px;
	transition: background-color 0.2s;
}

input[type="checkbox"]+label:hover {
	background-color: #e3f2fd;
	border-radius: 4px;
}

input[type="checkbox"] ~ ul {
	display: none;
}

input[type="checkbox"]:checked ~ ul {
	display: block;
}

.employee-name {
	font-weight: normal;
	position: relative;
	display: inline-block;
}

.employee-name:hover {
	background-color: #e3f2fd;
	border-radius: 4px;
}

.icon-blue {
	color: #1976d2;
}

.icon-gray {
	color: #757575;
}

.employee-name[data-status]:hover::after {
	content: attr(data-status);
	position: absolute;
	top: -30px;
	left: 0;
	background-color: #333;
	color: #fff;
	padding: 4px 8px;
	font-size: 12px;
	border-radius: 4px;
	white-space: nowrap;
	z-index: 100;
}

.employee-name[data-status]:hover::before {
	content: '';
	position: absolute;
	top: -8px;
	left: 10px;
	border-width: 6px;
	border-style: solid;
	border-color: transparent transparent #333 transparent;
}
</style>
</head>
<body>

	<div class="org-container">
		<h2>📁 조직도 보기 / 組織図の表示</h2>
		<ul>
			<c:forEach var="deptEntry" items="${orgChartMap}">
				<li><input type="checkbox"
					id="dept-${deptEntry.key.hashCode()}" /> <label
					for="dept-${deptEntry.key.hashCode()}">📂 ${deptEntry.key}</label>
					<ul>
						<c:forEach var="posEntry" items="${deptEntry.value}">
							<li><input type="checkbox"
								id="pos-${posEntry.key.hashCode()}-${deptEntry.key.hashCode()}" />
								<label
								for="pos-${posEntry.key.hashCode()}-${deptEntry.key.hashCode()}">📌
									${posEntry.key}</label>
								<ul>
									<c:forEach var="user" items="${posEntry.value}">
										<li><span
											class="employee-name ${user.workStatus == '勤務中' ? 'icon-blue' : 'icon-gray'}"
											data-status="${user.workStatus}"> 👤 ${user.name} </span></li>
									</c:forEach>
								</ul></li>
						</c:forEach>
					</ul></li>
			</c:forEach>
		</ul>
	</div>

	<%@ include file="../common/footer.jsp"%>
</body>
</html>
