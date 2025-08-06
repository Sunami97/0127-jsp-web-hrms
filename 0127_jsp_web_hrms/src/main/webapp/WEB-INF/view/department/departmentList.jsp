<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- JSP 페이지 설정: UTF-8 인코딩 및 JSTL Core 태그 라이브러리 사용 -->
<!-- JSP ページ設定：UTF-8 エンコーディングおよび JSTL Core タグライブラリの使用 -->

<html>
<head>
<title>조직도</title> <!-- 組織図 -->

<style>
/* 스타일 정의 */
/* スタイル定義 */
ul {
	list-style: none; /* 기본 목록 기호 제거 */ /* デフォルトのリスト記号を削除 */
	margin-left: 20px; /* 좌측 여백 */ /* 左側の余白 */
	padding-left: 10px; /* 내부 좌측 여백 */ /* 内側の左余白 */
}

.folder {
	cursor: pointer; /* 마우스 커서가 손가락 모양으로 변경 */ /* マウスカーソルをポインターに変更 */
	font-weight: bold; /* 폴더 텍스트 굵게 */ /* フォルダのテキストを太字にする */
	margin: 5px 0;
}

.employee-name.green {
	color: green;
} /* 근무 중인 직원 이름 초록색 */ /* 勤務中の社員名は緑色 */
.employee-name.red {
	color: red;
} /* 기타 상태 직원 이름 빨간색 */ /* その他の状態の社員名は赤色 */
.hidden {
	display: none;
} /* 기본적으로 숨겨진 요소 */ /* デフォルトで非表示の要素 */
</style>

<!-- jQuery 포함 -->
<!-- jQuery の読み込み -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
	// 폴더 클릭 시 하위 목록 보여주기/숨기기 기능
	// フォルダをクリックしたときに子リストを表示／非表示にする機能
	$(function() {
		$(".folder").click(function() {
			$(this).siblings("ul").first().toggle(); // 같은 계층에서 첫 번째 ul을 토글
			// 同じ階層内の最初の ul 要素をトグル
		});
	});
</script>
</head>

<body>
	<h2>📁 조직도</h2> <!-- 📁 組織図 -->

	<ul>
		<!-- 1단계: 부서 루프 -->
		<!-- 第1段階：部署のループ -->
		<c:forEach var="deptEntry" items="${orgChartMap}">
			<li>
				<!-- 부서 이름 출력 -->
				<!-- 部署名の表示 -->
				<div class="folder">📂 ${deptEntry.key}</div>
				<!-- 부서 하위의 직책 목록 (기본은 숨김) -->
				<!-- 部署の下位にある職位のリスト（デフォルトは非表示） -->
				<ul class="hidden">
					<!-- 2단계: 직책 루프 -->
					<!-- 第2段階：職位のループ -->
					<c:forEach var="posEntry" items="${deptEntry.value}">
						<li>
							<!-- 직책 이름 출력 -->
							<!-- 職位名の表示 -->
							<div class="folder">📌 ${posEntry.key}</div>
							<!-- 직책 하위의 사용자 목록 (기본은 숨김) -->
							<!-- 職位の下位にあるユーザーリスト（デフォルトは非表示） -->
							<ul class="hidden">
								<!-- 3단계: 사용자 루프 -->
								<!-- 第3段階：ユーザーのループ -->
								<c:forEach var="user" items="${posEntry.value}">
									<li>👤
										<!-- 근무 상태에 따라 색상 분기 -->
										<!-- 勤務状態によって色分け -->
										<span class="employee-name 
                                        ${user.statusType == '勤務中' ? 'green' : 'red'}">
											${user.name} </span> (${user.statusType})
										<!-- 근무 상태 표시 -->
										<!-- 勤務状態の表示 -->
									</li>
								</c:forEach>
							</ul>
						</li>
					</c:forEach>
				</ul>
			</li>
		</c:forEach>
	</ul>
</body>
</html>
