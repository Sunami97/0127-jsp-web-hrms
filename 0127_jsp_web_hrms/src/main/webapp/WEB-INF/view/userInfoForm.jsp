<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/adminRegister.css">
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
.input[type=date]{
	display ='none';
}
</style>
<script type="text/javascript">
function deleteDo(){
	
	const aws = confirm("本当に削除しますか？");
	if(aws){
		
		const form = document.getElementById('userInfo');
		form.action = "delete.do";
		form.method = "post";
		form.submit();
		
	}else{
		
		event.preventDefault();
		
	}
	
}
function updateForm(){
	
		const form = document.getElementById('userInfo');
		form.action = "updateForm.do";
		form.method = "post";
		form.submit();
	
}
</script>
</head>
<body>	<!-- 사원을 추가하기 위한 정보입력 폼 社員を追加するための情報入力フォーム -->
	<form action="insert.do" method="post" id="userInfo">
		<div>
	 		<div class="container">
				<h2>${user.name}様の詳細ページ</h2>
				
				<label>* ID <br><input type="text" name="userId" value="${user.userId}" readonly><br><br></label>
				<label>* 名前 <br><input type="text" name="name" value="${user.name}" readonly><br><br></label>
				<label>* メール <br><input type="text" name="email" value="${user.email}" readonly><br><br></label>
				<label>* 連絡先 <br><input type="text" name="phone" value="${user.phone}" readonly><br><br></label>
				<label>* 生年月日 <br><input type="date" name="birthDate" value="${user.birthDate}" readonly><br><br></label>
				<label>* 入社日 <br><input type="date" name="joinDate" value="${user.joinDate}" readonly><br><br></label>
				<label>* 職位 <br><input type="text" name="position" value="${user.position}" readonly><br><br></label>	
				<label>* 部署 <br><input type="text" name="departmentId" value="${user.departmentName}" readonly><br><br></label>
				<label>* 管理者 権限 <br><input type="text" name="isAdmin" value="${user.isAdmin}" value="N" readonly>
						 		<br><br></label>
				<label>* 状態 <br><input type="text" name="empStatus" value="${user.empStatus}" readonly>
						<br><br></label>
				<label>* 勤務状態	<br><input type="text" name="workStatus" value="${user.workStatus}" readonly>
							<br><br></label>
							
						
						<!-- 위에서 체크박스에 선택한 사원 삭제 上でチェックボックスに選択した社員を削除  -->
						<button type="submit" onclick="deleteDo()">削除</button>
						<!-- 위에서 라디오에 선택한 사원의 정보를 수정하는 페이지로 이동 ラジオで選択した社員の情報を修正するページに移動 -->
						<button type="submit" onclick="updateForm()">修整</button>
						
					
						</div>
					</div>	
	</form>
</body>
</html>