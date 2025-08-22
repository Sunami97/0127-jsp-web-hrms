<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="common/header.jsp"%>
<%@ include file="common/nav.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/adminRegister.css">
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
<link href="https://cdn.jsdelivr.net/npm/remixicon@3.5.0/fonts/remixicon.css" rel="stylesheet">
<style type="text/css">
.input[type=date] {display ='none';
	
}
</style>
<script type="text/javascript">
	//사원삭제 알림창 및 제출 기능 社員削除通知ウィンドウと提出機能
	function deleteDo() {

		const aws = confirm("本当に削除しますか？");
		if (aws) {

			const form = document.getElementById('userInfo');
			form.action = "delete.do";
			form.method = "post";
			form.submit();

		} else {

			event.preventDefault();

		}

	}
	//사원 수정 페이지로 이동 社員修正ページへ移動
	function updateForm() {

		const form = document.getElementById('userInfo');
		form.action = "updateForm.do";
		form.method = "post";
		form.submit();

	}
</script>
</head>
<body>
	<!-- 사원을 정보입력 폼 社員を追加するための情報入力フォーム -->
	<form method="post" id="userInfo" class="register-form">
		<div>
			<div class="container">
				<h2>${user.name}様の詳細ページ</h2>

				<label>* ID <br>
				<input type="text" name="userId" value="${user.userId}" readonly><br>
				<br></label> <label>* 名前 <br>
				<input type="text" name="name" value="${user.name}" readonly><br>
				<br></label> <label>* メール <br>
				<input type="text" name="email" value="${user.email}" readonly><br>
				<br></label> <label>* 連絡先 <br>
				<input type="text" name="phone" value="${user.phone}" readonly><br>
				<br></label> <label>* 生年月日 <br>
				<input type="date" name="birthDate" value="${user.birthDate}"
					readonly><br>
				<br></label> <label>* 入社日 <br>
				<input type="date" name="joinDate" value="${user.joinDate}" readonly><br>
				<br></label> <label>* 職位 <br>
				<input type="text" name="position" value="${user.position}" readonly><br>
				<br></label> <label>* 部署 <br>
				<input type="text" name="departmentId"
					value="${user.departmentName}" readonly><br>
				<br></label> <label>* 管理者 権限 <br>
				<input type="text" name="isAdmin" value="${user.isAdmin}" value="N"
					readonly> <br>
				<br></label> <label>* 状態 <br>
				<input type="text" name="empStatus" value="${user.empStatus}"
					readonly> <br>
				<br></label> <label>* 勤務状態 <br>
				<input type="text" name="workStatus" value="${user.workStatus}"
					readonly> <br>
				<br></label>


				<!-- 사원 삭제 社員を削除  -->
				<button type="submit" class="admin-button" onclick="deleteDo()">削除</button>
				<!-- 사원수정페이지로 이동 社員修正ページに移動 -->
				<button type="submit" class="admin-button" onclick="updateForm()">修正</button>


			</div>
		</div>
	</form>
	<%@ include file="common/footer.jsp"%>
</body>
</html>