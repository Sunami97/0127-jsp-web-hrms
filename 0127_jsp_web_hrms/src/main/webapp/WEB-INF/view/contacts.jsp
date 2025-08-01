<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, contacts.model.ContactsDTO" %>
<%
    List<ContactsDTO> contactsList = (List<ContactsDTO>) request.getAttribute("contactsList");
%>
<html>
<head>
    <title>연락망</title>
    <style>
        body { font-family: Arial, sans-serif; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { padding: 8px; border: 1px solid #ddd; text-align: center; }
        select { padding: 5px 10px; font-size: 14px; }
        .status-box { width: 15px; height: 15px; display: inline-block; border-radius: 3px; }
        .online { background-color: green; }
        .offline { background-color: red; }
    </style>
</head>
<body>

    <h2>📇 연락망 보기</h2>

    <!-- 부서 필터 드롭다운 -->
    <label for="deptFilter">부서 선택: </label>
    <select id="deptFilter" onchange="filterByDepartment()">
        <option value="ALL">전체</option>
        <%
            Set<String> deptSet = new HashSet<>();
            for (ContactsDTO dto : contactsList) {
                deptSet.add(dto.getDepartmentName());
            }
            for (String dept : deptSet) {
        %>
        <option value="<%=dept%>"><%=dept%></option>
        <%
            }
        %>
    </select>

    <!-- 연락망 테이블 -->
    <table>
        <thead>
            <tr>
                <th>부서명</th>
                <th>이름</th>
                <th>이메일</th>
                <th>연락처</th>
                <th>입사일</th>
                <th>직급</th>
                <th>상태 유형</th>
                <th>로그인 상태</th>
            </tr>
        </thead>
        <tbody id="contactTable">
            <%
                for (ContactsDTO contact : contactsList) {
            %>
            <tr data-department="<%=contact.getDepartmentName()%>">
                <td><%=contact.getDepartmentName()%></td>
                <td><%=contact.getName()%></td>
                <td><%=contact.getEmail()%></td>
                <td><%=contact.getPhone()%></td>
                <td><%=contact.getJoinDate()%></td>
                <td><%=contact.getPosition()%></td>
                <td><%=contact.getStatusType()%></td>
                <td>
                    <span class="status-box <%=contact.getLoginStatus().equalsIgnoreCase("login") ? "online" : "offline"%>"></span>
                </td>
            </tr>
            <%
                }
            %>
        </tbody>
    </table>

    <!-- 필터링을 위한 JS -->
    <script>
        function filterByDepartment() {
            const selected = document.getElementById("deptFilter").value;
            const rows = document.querySelectorAll("#contactTable tr");

            rows.forEach(row => {
                const dept = row.getAttribute("data-department");
                if (selected === "ALL" || dept === selected) {
                    row.style.display = "";
                } else {
                    row.style.display = "none";
                }
            });
        }
    </script>

</body>
</html>
