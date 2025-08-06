<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- JSP 페이지 설정: UTF-8 인코딩 및 JSTL Core 태그 라이브러리 사용 -->
<!-- JSP ページ設定：UTF-8 エンコーディングおよび JSTL Core タグライブラリの使用 -->

<html>
<head>
    <title>조직도 / 組織図</title>

    <style>
        body {
            font-family: Arial, sans-serif; /* 폰트 통일 / フォント統一 */
            font-size: 14px; /* 글자 크기 / フォントサイズ */
            margin: 20px;
        }

        ul {
            list-style: none; /* 기본 목록 기호 제거 / デフォルト記号削除 */
            margin-left: 20px;
            padding-left: 10px;
        }

        li {
            margin: 5px 0;
        }

        .folder {
            cursor: pointer; /* 클릭 가능한 폴더 / クリック可能フォルダ */
            font-weight: bold;
            padding: 5px;
            display: inline-block;
        }

        .employee-name {
            font-weight: normal;
        }

        /* 근무중인 사람 - 파란색 / 勤務中の人は青 */
        .icon-blue {
            color: blue;
        }

        /* 근무중이 아닌 사람 - 빨간색 / 勤務中ではない人は赤 */
        .icon-red {
            color: red;
        }

        .hidden {
            display: none; /* 기본적으로 숨김 / デフォルトで非表示 */
        }
    </style>

    <!-- jQuery 포함 / jQuery 読み込み -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
        // 폴더 클릭 시 하위 목록 토글 / フォルダクリックで子要素表示切替
        $(function () {
            $(".folder").click(function () {
                $(this).next("ul").toggle(); // 다음 ul을 토글 / 次の ul をトグル
            });
        });
    </script>
</head>

<body>

    <h2>📁 조직도 보기 / 組織図の表示</h2>

    <ul>
        <!-- 1단계: 부서 루프 / 部署ループ -->
        <c:forEach var="deptEntry" items="${orgChartMap}">
            <li>
                <!-- 부서명 표시 / 部署名表示 -->
                <span class="folder">📂 ${deptEntry.key}</span>

                <ul class="hidden">
                    <!-- 2단계: 직급 루프 / 職位ループ -->
                    <c:forEach var="posEntry" items="${deptEntry.value}">
                        <li>
                            <span class="folder">📌 ${posEntry.key}</span>

                            <ul class="hidden">
                                <!-- 3단계: 사용자 루프 / ユーザーループ -->
                                <c:forEach var="user" items="${posEntry.value}">
                                    <li>
                                        <span class="employee-name ${user.workStatus == '勤務中' ? 'icon-blue' : 'icon-black'}">
                                            👤 ${user.name}
                                        </span>
                                        <!-- 상태가 있으면 괄호로 출력 / 状態がある場合カッコで表示 -->
                                        <c:if test="${not empty user.workStatus}">
                                            (${user.workStatus})
                                        </c:if>
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
