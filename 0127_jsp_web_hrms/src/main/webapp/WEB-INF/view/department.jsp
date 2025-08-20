<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>組織図</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
<style>
/* 조직도 컨테이너 / 組織図コンテナ */
.org-container {
	background-color: #fff;
	margin: 100px auto 60px 220px; /* nav, header 고려 여백 / nav, header の余白考慮 */
	padding: 20px;
	max-width: 900px;
	box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
	border-radius: 8px;
	position: relative; /* 버튼 위치를 컨테이너 기준으로 */
}

/* 전체 열기/닫기 버튼 / 全体開閉ボタン */
.toggle-btn {
	position: absolute;
	top: 10px;
	right: 10px;
	padding: 6px 12px;
	background-color: #1976d2;
	color: #fff;
	border: none;
	border-radius: 6px;
	cursor: pointer;
	font-size: 14px;
}
.toggle-btn:hover {
	background-color: #1565c0;
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

/* 직원 이름 스타일 / 社員名スタイル */
.employee-name {
	font-weight: normal;
	position: relative;
	display: inline-block;
}

.employee-name:hover {
	background-color: #e3f2fd;
	border-radius: 4px;
}

/* 로그인 중 / ログイン中 */
.icon-blue {
	color: #1976d2;
}

/* 로그아웃 상태 / ログアウト状態 */
.icon-gray {
	color: #757575;
}

/* 툴팁 (status_type 표시) / ツールチップ (status_type 表示) */
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
		<h2>📁 組織図の表示</h2>
		<!-- 전체 열기/닫기 버튼 / 全体開閉ボタン -->
		<button type="button" class="toggle-btn" onclick="toggleAll()">全て閉じる</button>

		<ul>
			<c:forEach var="deptEntry" items="${orgChartMap}">
				<li>
					<!-- 모든 트리가 처음부터 열려있도록 checked 추가 / すべてのツリーを最初から開いた状態にするため checked を追加 -->
					<input type="checkbox" id="dept-${deptEntry.key.hashCode()}" checked />
					<label for="dept-${deptEntry.key.hashCode()}">📂 ${deptEntry.key}</label>
					<ul>
						<c:forEach var="posEntry" items="${deptEntry.value}">
							<li>
								<input type="checkbox" id="pos-${posEntry.key.hashCode()}-${deptEntry.key.hashCode()}" checked />
								<label for="pos-${posEntry.key.hashCode()}-${deptEntry.key.hashCode()}">📌 ${posEntry.key}</label>
								<ul>
									<c:forEach var="user" items="${posEntry.value}">
										<li>
											<span
												class="employee-name ${user.statusType == '勤務中' ? 'icon-blue' : 'icon-gray'}"
												data-status="${user.statusType}">
												👤 ${user.name}
											</span>
										</li>
									</c:forEach>
								</ul>
							</li>
						</c:forEach>
					</ul>
				</li>
			</c:forEach>
		</ul>
	</div>

<script>
/* 전체 열기/닫기 제어 함수 / 全体開閉制御関数 */
let allOpen = true; // 현재 상태를 기억 / 現在の状態を記憶

function toggleAll() {
	const checkboxes = document.querySelectorAll('.org-container input[type="checkbox"]');
	checkboxes.forEach(cb => cb.checked = !allOpen);
	
	// 버튼 텍스트 변경 / ボタンテキスト変更
	const btn = document.querySelector('.toggle-btn');
	btn.textContent = allOpen ? "全て開く" : "全て閉じる";

	allOpen = !allOpen; // 상태 토글 / 状態を反転
}
</script>

</body>
</html>
