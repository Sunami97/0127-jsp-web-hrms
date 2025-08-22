<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2025-07-30
  Time: 오후 2:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="common/header.jsp"%>
<%@ include file="common/nav.jsp"%>
<html>
<head>
<title>有給休暇申請</title>
<link
	href="https://cdn.jsdelivr.net/npm/remixicon@3.5.0/fonts/remixicon.css"
	rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="<c:url value='/css/style.css'/>">
	<link href="https://cdn.jsdelivr.net/npm/remixicon@3.5.0/fonts/remixicon.css" rel="stylesheet">
<style>
body {
	font-family: Arial, sans-serif;
	background-color: #f5f8ff;
	color: #333;
}

.container {
	background-color: #fff;
	margin: 30px auto 60px 220px;
	/* nav, header 고려 여백 / nav, header の余白考慮 */
	padding: 20px;
	box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
	border-radius: 8px;
	position: relative; /* 버튼 위치를 컨테이너 기준으로 */
}

h2 {
	text-align: center;
	color: #2E83F2;
	margin-top: 100px;
}

#paidLeaveform {
	width: 60%;
	margin: 30px auto;
	margin-bottom: 100px;
	padding: 20px;
	background-color: #ffffff;
	border: 1px solid #79BAF2;
	border-radius: 8px;
}

label {
	display: block;
	margin-top: 15px;
	font-weight: bold;
	color: #2E83F2;
}

input[type="text"], input[type="date"], input[type="number"], textarea,
	select {
	width: 100%;
	padding: 10px;
	margin-top: 6px;
	border: 1px solid #79BAF2;
	border-radius: 5px;
	box-sizing: border-box;
	background-color: #f9faff;
}

textarea {
	resize: none;
}

input[type="submit"] {
	display: block;
	margin: 25px auto 0;
	padding: 10px 20px;
	background-color: #2E83F2;
	color: white;
	border: none;
	border-radius: 5px;
	font-size: 15px;
	cursor: pointer;
}

input[type="submit"]:hover {
	background-color: #3071F2;
}

p {
	text-align: center;
	font-size: 14px;
	color: #666;
}

.date-card {
	border: 1px solid #79BAF2;
	border-radius: 6px;
	background: #f9faff;
	padding: 10px 15px;
	margin: 8px 0;
	display: flex;
	justify-content: space-between;
	align-items: center;
}

.date-label {
	font-weight: bold;
	color: #2E83F2;
}

.date-options label {
	margin-left: 10px;
	color: #333;
	font-size: 14px;
}
</style>
</head>
<body>
	<div class="container">
	<h2>有給休暇申請書</h2>
	<p>
		<strong>ユーザーID:</strong> ${sessionScope.loginUser.user_id}
	</p>
	<form id="paidLeaveform" action="write.do" method="post">
		<input type="hidden" name="name" value="${name}" /> <input
			type="hidden" name="status" value="申請中" /> <input type="hidden"
			name="appliedAt"
			value="<%=new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date())%>" />

		<label>有給休暇開始日:</label> <input type="date" id="startDate"
			name="startDate" value="${param.startDate}" required /><br />
		<br /> <label>有給休暇終了日:</label> <input type="date" id="endDate"
			name="endDate" value="${param.endDate}" required /><br />
		<br />

		<c:if test="${errors.dateError}">
			<p style="color: red;">終了日は開始日以降でなければなりません。</p>
		</c:if>

		<div id="halfDayContainer"></div>

		<label>使用日数（例: 0.5、1）:</label> <input type="number" name="days"
			id="days" readonly /><br />
		<br />

		<script>
            const startDateInput = document.getElementById("startDate");
            const endDateInput = document.getElementById("endDate");
            const container = document.getElementById("halfDayContainer");
            const daysInput = document.getElementById("days");

            // 날짜별 오전/오후 체크박스 생성
            function createHalfDayUI() {
                container.innerHTML = '';
                const start = new Date(startDateInput.value);
                const end = new Date(endDateInput.value);

                if (!start || !end || end < start) return;

                for (let d = new Date(start); d <= end; d.setDate(d.getDate() + 1)) {
                    d.setHours(0,0,0,0);
                    const yyyy = d.getFullYear();
                    const mm = String(d.getMonth() + 1).padStart(2, '0');
                    const dd = String(d.getDate()).padStart(2, '0');
                    const dateStr = `\${yyyy}-\${mm}-\${dd}`;

                    const div = document.createElement('div');
                    div.style.marginBottom = "5px";
                    div.innerHTML = `
                      <div class="date-card">
                        <div class="date-label">\${dateStr}</div>
                        <div class="date-options">
                          <label><input type="checkbox" class="halfDay" data-date="\${dateStr}" data-value="0.5"> 午前</label>
                          <label><input type="checkbox" class="halfDay" data-date="\${dateStr}" data-value="0.5"> 午後</label>
                        </div>
                      </div>
                    `;
                    container.appendChild(div);
                }

                // 체크박스 이벤트
                document.querySelectorAll('.halfDay').forEach(cb => {
                    cb.addEventListener('change', updateDays);
                });

                updateDays();
            }

            // 사용일수 계산
            function updateDays() {
                const start = new Date(startDateInput.value);
                const end = new Date(endDateInput.value);

                if (!start || !end || !startDateInput.value || !endDateInput.value) {
                    daysInput.value = "";
                    return;
                }

                const diffTime = end - start;
                const fullDays = diffTime / (1000 * 60 * 60 * 24) + 1;
                let total = fullDays;

                // 체크된 AM/PM 수 만큼 0.5씩 차감
                const checked = document.querySelectorAll('.halfDay:checked');
                let halfCount = checked.length;

                total = fullDays - (halfCount * 0.5);
                if (total < 0) total = 0; // 음수 방지
                daysInput.value = total;
            }

            // 이벤트
            startDateInput.addEventListener("change", function() {
                endDateInput.min = this.value;
                if (endDateInput.value && endDateInput.value < this.value) {
                    endDateInput.value = this.value;
                }
                createHalfDayUI();
            });

            endDateInput.addEventListener("change", createHalfDayUI);
        </script>

		<label>申請理由:</label><br />
		<textarea name="reason" rows="4" cols="50" required>${param.reason}</textarea>
		<br />
		<br /> <label for="approvedBy">承認者選択</label> <select name="approvedBy"
			id="approvedBy" required>
			<option value="">-- 承認者を選択してください --</option>
			<c:forEach var="admin" items="${adminnames}">
				<option value="${admin}">${admin}</option>
			</c:forEach>
		</select><br />
		<br /> <input type="submit" value="申請" />
	</form>
	</div>
	<%@ include file="common/footer.jsp"%>
</body>
</html>
