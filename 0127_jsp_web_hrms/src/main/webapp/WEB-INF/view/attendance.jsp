<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ include file="/WEB-INF/view/common/header.jsp" %>
<%@ include file="/WEB-INF/view/common/nav.jsp" %>

<%
    // Handler가 세팅해주는 값들
    Boolean hasOpen = (Boolean) request.getAttribute("hasOpen");
    if (hasOpen == null) hasOpen = false;
%>
<link rel="stylesheet" type="text/css" href="css/style.css">

<link href="https://cdn.jsdelivr.net/npm/remixicon@3.5.0/fonts/remixicon.css" rel="stylesheet">
<style>

	:root{
    /* 프로젝트 값에 맞게 조정 (예: 72px / 260px) */
    --header-h: 60px;   
    --sidebar-w: 300px; 
    --gap: 16px;
  }
  	
  /* 전용 컨테이너/테이블 스타일 */
  .att-container{
    max-width: 1100px;
    box-sizing: border-box;

    /* 헤더를 피하기 위한 위쪽 여백 */
    padding-top: calc(var(--header-h)- 30px;	);

    /* 좌측 고정 사이드바를 피하기 위한 왼쪽 마진 */
    margin-left: calc(var(--sidebar-w) + var(--gap));

    /* 수평 여백/안쪽 패딩은 취향대로 */
    padding-left: var(--gap);
    padding-right: var(--gap);
    margin-right: var(--gap);
    margin-bottom: 24px; /* 하단 여백 */
  }
  .att-top { margin-top: 60px; display: flex; align-items: center; gap: 12px; margin-bottom: 16px; }
  .att-btn { padding: 10px 16px; border-radius: 8px; border: 1px solid #ddd; cursor: pointer; }
  .att-btn-in { background: #f5faff; }
  .att-badge {
    display:inline-block; padding:6px 10px; border-radius:999px; font-size:12px; margin-left:8px;
  }
  .att-card { background:#fff; border:1px solid #eee; border-radius:12px; box-shadow:0 2px 8px rgba(0,0,0,.04); }
  .att-card-hd { padding:16px; border-bottom:1px solid #f0f0f0; font-weight:600; }
  .att-card-bd { padding:16px; }
  .att-table { width:100%; border-collapse: collapse; }
  .att-table thead th { text-align:left; padding:10px; border-bottom:1px solid #eee; background:#fafafa; position: sticky; top: 0; z-index: 1; }
  .att-table tbody tr:nth-child(odd)  { background: #fcfcfc; }
  .att-table tbody tr:hover           { background: #f5faff; }
  .att-table tbody td { padding:10px; border-bottom:1px solid #f5f5f5; }
  .att-out-btn { padding:6px 10px; border-radius:6px; border:1px solid #ddd; background:#fff7f5; cursor:pointer; }
  .att-out-btn[disabled] { opacity:.5; cursor: not-allowed; }
</style>

<div class="att-container">
  <!-- 상단: 출근 버튼 -->
  <div class="att-top">
    <form id="formIn" method="post" action="${pageContext.request.contextPath}/attendance.do">
      <input type="hidden" name="action" value="in"/>
      <input type="hidden" name="page" value="${currentPage}"/>
      <button type="submit" id="btnIn" class="att-btn att-btn-in" <%= hasOpen ? "disabled" : "" %>>出勤</button>
    </form>

    <!-- 현재 상태 뱃지 -->
    <span class="att-badge"
          style="background:<%= hasOpen ? "#e8f5e9" : "#fff3e0" %>;
                 color:<%= hasOpen ? "#2e7d32" : "#ef6c00" %>;
                 border:1px solid <%= hasOpen ? "#c8e6c9" : "#ffe0b2" %>;">
      現状: <b><%= hasOpen ? "勤務中" : "退勤" %></b>
    </span>
  </div>

  <!-- 하단: 출퇴근 내역 (최신순, 모든 컬럼 + 우측에 퇴근 버튼) -->
  <div class="att-card">
    <div class="att-card-hd">出退勤内訳</div>
    <div class="att-card-bd">
      <c:choose>
        <c:when test="${empty history}">
          <div style="color:#888;">表示する内訳がありません.</div>
        </c:when>
        <c:otherwise>
          <div style="overflow:auto;">
            <table class="att-table">
              <thead>
                <tr>
                  
                  <th>社員名</th>
                  <th>勤務状態</th>
                  <th>出勤時間</th>
                  <th>退勤時間</th>
                  <th>現在</th>
                  <th></th>
                </tr>
              </thead>
              <tbody>
                <c:forEach var="r" items="${history}">
                  <tr>
                    
                    <td><c:out value="${r.userName}" /></td>
                    <td><c:out value="${r.current ? r.statusType : '-'}" /></td>
                    <td><fmt:formatDate value="${r.statusStart}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                    <td>
                      <c:choose>
                        <c:when test="${r.statusEnd != null}">
                          <fmt:formatDate value="${r.statusEnd}" pattern="yyyy-MM-dd HH:mm:ss" />
                        </c:when>
                        <c:otherwise>-</c:otherwise>
                      </c:choose>
                    </td>
                    <td><c:out value="${r.current ? '出勤' : '退勤'}" /></td>
                    <td>
                      <form method="post" action="${pageContext.request.contextPath}/attendance.do" style="display:inline;">
                        <input type="hidden" name="action" value="out"/>
                        <input type="hidden" name="statusId" value="${r.statusId}"/>
                        <input type="hidden" name="page" value="${currentPage}"/>
                        <button type="submit" class="att-out-btn" <c:if test="${!r.current}">disabled</c:if>>退勤</button>
                      </form>
                    </td>
                  </tr>
                </c:forEach>
              </tbody>
            </table>
          </div>
        </c:otherwise>
      </c:choose>
    </div>
  </div>
</div>

<style>
  .att-paging { display:flex; gap:6px; align-items:center; justify-content:center; margin-top:14px; }
  .att-page-btn, .att-page-num {
    display:inline-block; padding:6px 10px; border:1px solid #ddd; border-radius:6px; background:#fff; text-decoration:none; color:#333;
  }
  .att-page-num.active { background:#f5faff; border-color:#aac6ff; font-weight:600; }
  .att-page-btn[aria-disabled="true"] { opacity:.5; pointer-events:none; }
</style>

<div class="att-paging">
  <a class="att-page-btn" href="${pageContext.request.contextPath}/attendance.do?page=${currentPage - 1}"
     aria-disabled="${!hasPrev}">前</a>

  <c:forEach var="p" begin="${startPage}" end="${endPage}">
    <a class="att-page-num ${p == currentPage ? 'active' : ''}"
       href="${pageContext.request.contextPath}/attendance.do?page=${p}">${p}</a>
  </c:forEach>

  <a class="att-page-btn" href="${pageContext.request.contextPath}/attendance.do?page=${currentPage + 1}"
     aria-disabled="${!hasNext}">次</a>
</div>

<script>
  // 출근/퇴근 알림
  (function(){
    var formIn = document.getElementById('formIn');
    var btnIn  = document.getElementById('btnIn');
    if (btnIn) {
      btnIn.addEventListener('click', function(e){
        alert('出勤します');
      });
    }
    // 각 행의 퇴근 버튼은 form submit 직전에 alert
    document.addEventListener('submit', function(e){
      var form = e.target;
      if (form && form.tagName === 'FORM') {
        var actionField = form.querySelector('input[name="action"]');
        if (actionField && actionField.value === 'out') {
          alert('退勤します');
        }
      }
    }, true);
  })();
</script>

<%@ include file="/WEB-INF/view/common/footer.jsp" %>
