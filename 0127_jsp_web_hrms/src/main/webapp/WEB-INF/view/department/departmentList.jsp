<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
    <title>조직도 / 組織図</title>

<style>
/* 🇰🇷 전체 페이지 기본 설정 / 🇯🇵 ページ全体の基本設定 */
body {
    font-family: Arial, sans-serif;
    font-size: 14px;
    margin: 0;
    background-color: #f4f8fc;
}

/* 🇰🇷 상단 파란색 헤더 바 / 🇯🇵 上部の青いヘッダーバー */
.header-bar {
    background-color: #1976d2;
    color: white;
    padding: 15px 20px;
    font-size: 18px;
    font-weight: bold;
}

/* 🇰🇷 내용 박스 / 🇯🇵 コンテンツボックス */
.container {
    background-color: white;
    margin: 20px auto;
    padding: 20px;
    max-width: 800px;
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
}

/* 🇰🇷 폴더 스타일 / 🇯🇵 フォルダスタイル */
.folder {
    cursor: pointer;
    font-weight: bold;
    padding: 5px;
    display: inline-block;
    transition: background-color 0.2s;
}

.folder:hover {
    background-color: #e3f2fd;
    border-radius: 4px;
}

/* 🇰🇷 직원 이름 스타일 / 🇯🇵 社員名スタイル */
.employee-name {
    font-weight: normal;
    position: relative; /* 툴팁 기준 위치 설정 */
}

/* 🇰🇷 마우스 오버 시 배경 강조 / 🇯🇵 ホバー時の背景強調 */
.employee-name:hover {
    background-color: #e3f2fd;
    border-radius: 4px;
}

/* 🇰🇷 상태가 '근무중'인 경우 파란색 / 🇯🇵 勤務中の場合は青色 */
.icon-blue {
    color: #1976d2;
}

/* 🇰🇷 상태가 '근무중 아님'일 경우 회색 / 🇯🇵 勤務中でない場合はグレー */
.icon-gray {
    color: #757575;
}

/* 🇰🇷 툴팁 스타일 (기본 숨김) / 🇯🇵 ツールチップのスタイル（デフォルト非表示） */
.tooltip {
    display: none;
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

/* 🇰🇷 툴팁 화살표 / 🇯🇵 ツールチップの矢印 */
.tooltip::after {
    content: '';
    position: absolute;
    bottom: -6px;
    left: 10px;
    border-width: 6px;
    border-style: solid;
    border-color: #333 transparent transparent transparent;
}
</style>

<!-- 🇰🇷 jQuery 포함 / 🇯🇵 jQuery 読み込み -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<script>
    $(function () {
        // 🇰🇷 폴더 클릭 시 하위 항목 표시/숨기기 / 🇯🇵 フォルダクリックで子要素を表示/非表示
        $(".folder").click(function () {
            $(this).next("ul").toggle();
        });

        // 🇰🇷 직원 이름에 마우스를 올리면 툴팁 표시 / 🇯🇵 社員名にマウスを乗せるとツールチップ表示
        $(".employee-name").hover(function () {
            $(this).find(".tooltip").fadeIn(200);
        }, function () {
            $(this).find(".tooltip").fadeOut(200);
        });
    });
</script>
</head>

<body>

    <!-- 🇰🇷 헤더바 / 🇯🇵 ヘッダーバー -->
    <div class="header-bar">📁 조직도 보기 / 組織図の表示</div>

    <!-- 🇰🇷 조직도 표시 컨테이너 / 🇯🇵 組織図の表示コンテナ -->
    <div class="container">
        <ul>
            <!-- 🇰🇷 부서 반복 출력 / 🇯🇵 部署の繰り返し表示 -->
            <c:forEach var="deptEntry" items="${orgChartMap}">
                <li>
                    <span class="folder">📂 ${deptEntry.key}</span>
                    <ul class="hidden">
                        <!-- 🇰🇷 직급 반복 / 🇯🇵 職位の繰り返し -->
                        <c:forEach var="posEntry" items="${deptEntry.value}">
                            <li>
                                <span class="folder">📌 ${posEntry.key}</span>
                                <ul class="hidden">
                                    <!-- 🇰🇷 직원 반복 / 🇯🇵 社員の繰り返し -->
                                    <c:forEach var="user" items="${posEntry.value}">
                                        <li>
                                            <span class="employee-name ${user.workStatus == '勤務中' ? 'icon-blue' : 'icon-gray'}">
                                                👤 ${user.name}
                                                <!-- 🇰🇷 상태를 말풍선으로 표시 / 🇯🇵 状態を吹き出しで表示 -->
                                                <c:if test="${not empty user.workStatus}">
                                                    <span class="tooltip">${user.workStatus}</span>
                                                </c:if>
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

</body>
