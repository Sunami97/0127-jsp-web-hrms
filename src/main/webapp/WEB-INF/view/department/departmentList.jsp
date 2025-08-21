<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
    <title>조직도 / 組織図</title>

<style>
/* 🇰🇷 기본 바디 설정 / 🇯🇵 基本のボディ設定 */
body {
    font-family: Arial, sans-serif;
    font-size: 14px;
    margin: 0;
    background-color: #f4f8fc;
}

/* 🇰🇷 상단 헤더 바 / 🇯🇵 上部のヘッダーバー */
.header-bar {
    background-color: #1976d2;
    color: white;
    padding: 15px 20px;
    font-size: 18px;
    font-weight: bold;
}

/* 🇰🇷 내용 컨테이너 / 🇯🇵 コンテンツコンテナ */
.container {
    background-color: white;
    margin: 20px auto;
    padding: 20px;
    max-width: 800px;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
    border-radius: 8px;
}

/* 🇰🇷 리스트 스타일 / 🇯🇵 リストスタイル */
ul {
    list-style: none;
    margin-left: 20px;
    padding-left: 10px;
}

li {
    margin: 5px 0;
    position: relative;
}

/* 🇰🇷 토글용 체크박스 숨김 / 🇯🇵 トグル用チェックボックスを非表示に */
input[type="checkbox"] {
    display: none;
}

/* 🇰🇷 폴더/직급 이름 라벨 / 🇯🇵 フォルダ/役職名のラベル */
input[type="checkbox"] + label {
    cursor: pointer;
    font-weight: bold;
    display: inline-block;
    padding: 5px;
    transition: background-color 0.2s;
}

/* 🇰🇷 마우스 오버 시 하이라이트 / 🇯🇵 ホバー時にハイライト */
input[type="checkbox"] + label:hover {
    background-color: #e3f2fd;
    border-radius: 4px;
}

/* 🇰🇷 기본적으로 하위 항목 숨김 / 🇯🇵 デフォルトでは子要素を非表示 */
input[type="checkbox"] ~ ul {
    display: none;
}

/* 🇰🇷 체크되면 하위 항목 표시 / 🇯🇵 チェックされると子要素を表示 */
input[type="checkbox"]:checked ~ ul {
    display: block;
}

/* 🇰🇷 직원 이름 스타일 / 🇯🇵 社員名のスタイル */
.employee-name {
    font-weight: normal;
    position: relative;
    display: inline-block;
}

/* 🇰🇷 직원 이름 마우스 오버 효과 / 🇯🇵 社員名ホバー時の効果 */
.employee-name:hover {
    background-color: #e3f2fd;
    border-radius: 4px;
}

/* 🇰🇷 근무 중인 경우 파란색 / 🇯🇵 勤務中の場合は青色 */
.icon-blue {
    color: #1976d2;
}

/* 🇰🇷 근무 중이 아닌 경우 회색 / 🇯🇵 勤務中でない場合はグレー */
.icon-gray {
    color: #757575;
}

/* 🇰🇷 툴팁 텍스트 / 🇯🇵 ツールチップのテキスト */
.employee-name[data-status]:hover::after {
    content: attr(data-status); /* 상태 텍스트 표시 / 状態テキストを表示 */
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

<!-- 🇰🇷 헤더 바 / 🇯🇵 ヘッダーバー -->
<div class="header-bar">📁 조직도 보기 / 組織図の表示</div>

<!-- 🇰🇷 조직도 전체 컨테이너 / 🇯🇵 組織図全体のコンテナ -->
<div class="container">
    <ul>
        <!-- 🇰🇷 부서 반복 출력 / 🇯🇵 部署の繰り返し表示 -->
        <c:forEach var="deptEntry" items="${orgChartMap}">
            <li>
                <!-- 🇰🇷 부서 토글 체크박스 / 🇯🇵 部署のトグル用チェックボックス -->
                <input type="checkbox" id="dept-${deptEntry.key.hashCode()}" />
                <label for="dept-${deptEntry.key.hashCode()}">📂 ${deptEntry.key}</label>
                <ul>
                    <!-- 🇰🇷 직급 반복 출력 / 🇯🇵 役職の繰り返し表示 -->
                    <c:forEach var="posEntry" items="${deptEntry.value}">
                        <li>
                            <!-- 🇰🇷 직급 토글 체크박스 / 🇯🇵 役職のトグル用チェックボックス -->
                            <input type="checkbox" id="pos-${posEntry.key.hashCode()}-${deptEntry.key.hashCode()}" />
                            <label for="pos-${posEntry.key.hashCode()}-${deptEntry.key.hashCode()}">📌 ${posEntry.key}</label>
                            <ul>
                                <!-- 🇰🇷 직원 반복 출력 / 🇯🇵 社員の繰り返し表示 -->
                                <c:forEach var="user" items="${posEntry.value}">
                                    <li>
                                        <!-- 🇰🇷 직원 이름 및 상태 표시 / 🇯🇵 社員名と状態表示 -->
                                        <span class="employee-name ${user.workStatus == '勤務中' ? 'icon-blue' : 'icon-gray'}"
                                              data-status="${user.workStatus}">
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

</body>
