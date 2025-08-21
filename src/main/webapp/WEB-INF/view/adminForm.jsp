
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
</style>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/adminForm.css">
<script>
	function registerForm() {
		location.href = 'registerForm.do'
	}
</script>

</head>
<body>
	<!-- 조회할 사원의 정보를 입력하여 검색 照会する社員の情報を入力して検索 -->
	<form class="search-box" action="select.do" method="post">
		<table>
			<tr>
				<!-- 검색할 카테고리 検索するカテゴリ-->
				<td>
					<div class="search-container">
						<div class="search-dropdown">
							<select name="keyField">
								<option value="name">名前</option>
								<option value="user_id">ID</option>
								<option value="position">職位</option>
								<option value="phone">連絡先</option>
							</select>
						</div>
						<!-- 입사일 혹은 퇴사일 기간을 정해서 조회 入社日または退社日の期間を決めて照会 -->
							<input class="search-input" type="text" name="keyWord"
									placeholder="検索ワードを入力してください.">
								<div class="date-filter">
									<select name="date">
										<option value="null" selected>期間</option>
										<option value="join_date">入社日</option>
										<option value="retire_date">退社日</option>
									</select>
									<input type="date" name="sDate">~ <input type="date"
											name="sDate">
										</div> 
									</div>
										<button type="submit">照会</button>
				</td>
			</tr>
		</table>
	</form>
	<!-- 결과을 출력한 jsp페이지를 불러옴 結果を出力したjspページを読み込む -->
	<jsp:include page="/WEB-INF/view/userListForm.jsp" flush="false" />
	<!-- 사원 추가 command 호출 社員追加command呼び出し -->
	<button type="button" onclick="registerForm()">追加</button>
</body>
</html>



