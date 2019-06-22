<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html lang="ko">

<head>
  <meta charset="utf-8" />
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/battle.css" />
  <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
  <script src="${pageContext.request.contextPath}/resources/battle.js"></script>
</head>

<body>
  <div id="wrap">
    <div class="top_button_section">
      <form action="do_battle">
        <input type="text" name="select1" value="${select1_idx}" />
        <input type="text" name="select2" value="${select2_idx}" />
        <input type="hidden" name="random" value="${random_number}" />
      </form>
      <button class="turn">턴 진행</button>
    </div>
    <div class="battle_section">
      <div class="player1">
        <span class="no">${select1_idx}</span>
        <h3>${select1_name}</h3>
        <span>공격력 : </span> ${select1_attackPower}<br />
        <span>공격확률 : </span> ${select1_attackRate}<br />
        <span>방어력  : </span> ${select1_defensePower}<br />
        <span>방어확률  : </span> ${select1_defenseRate}<br />
        <span>HP  : </span> ${select1_hp}<br />
      </div>
      <div class="player2">
        <span class="no">${select2_idx}</span>
        <h3>${select2_name}</h3>
        <span>공격력 : </span> ${select2_attackPower}<br />
        <span>공격확률 : </span> ${select2_attackRate}<br />
        <span>방어력  : </span> ${select2_defensePower}<br />
        <span>방어확률  : </span> ${select2_defenseRate}<br />
        <span>HP  : </span> ${select2_hp}<br />
      </div>
    </div>
  </div>
</body>

</html>