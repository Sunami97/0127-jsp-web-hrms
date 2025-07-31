<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>조직도</title>
    <style>
        ul { list-style: none; margin-left: 20px; padding-left: 10px; }
        .folder { cursor: pointer; font-weight: bold; margin: 5px 0; }
        .employee-name.green { color: green; }
        .employee-name.red { color: red; }
        .hidden { display: none; }
    </style>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
        $(function() {
            $(".folder").click(function() {
                $(this).siblings("ul").first().toggle(); // 첫 번째 하위 ul 토글
            });
        });
    </script>
</head>
<body>
<h2>📁 조직도</h2>
<ul>
    <c:forEach var="deptEntry" items="${orgChartMap}">
        <li>
            <div class="folder">📂 ${deptEntry.key}</div>
            <ul class="hidden">
                <c:forEach var="posEntry" items="${deptEntry.value}">
                    <li>
                        <div class="folder">📌 ${posEntry.key}</div>
                        <ul class="hidden">
                            <c:forEach var="user" items="${posEntry.value}">
                                <li>
                                    👤 
                                    <span class="employee-name 
                                        ${user.statusType == '勤務中' ? 'green' : 'red'}">
                                        ${user.name}
                                    </span>
                                    (${user.statusType})
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
