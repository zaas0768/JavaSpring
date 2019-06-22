<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html lang="ko">

<head>
  <meta charset="utf-8" />
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/list.css" />
  <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
  <script src="${pageContext.request.contextPath}/resources/list.js"></script>
</head>

<body>
  <div id="wrap">
    <div class="top_button_section">
      <a href="/game" class="top_button">목록으로</a>
    </div>
    <ul class="player_list">
      <li>
        <span class="no">${idx}</span>
        <h3>${name}</h3>
        <span>공격력 : </span> ${attackPower}<br />
        <span>공격확률 : </span> ${attackRate}<br />
        <span>방어력  : </span> ${defensePower}<br />
        <span>방어확률  : </span> ${defenseRate}<br />
        <form action="change_card">
        	<button>카드 변경</button>
        </form>
      </li>
    </ul>
  </div>
</body>

</html>