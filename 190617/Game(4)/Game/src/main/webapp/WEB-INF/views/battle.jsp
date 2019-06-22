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
      <form>
        <input type="text" name="select1" />
        <input type="text" name="select2" />
      </form>
      <button class="turn">턴 진행</button>
    </div>
    <div class="battle_section">
      <div class="player1">
        <span class="no">1</span>
        <h3>ì´ë¦</h3>
        <span>ê³µê²©ë ¥ : </span> 100<br />
        <span>ë°©ì´ë ¥ : </span> 100<br />
        <span>ê³µê²©íë¥  : </span> 100<br />
        <span>ë°©ì´íë¥  : </span> 100<br />
      </div>
      <div class="player2">
        <span class="no">2</span>
        <h3>ì´ë¦</h3>
        <span>ê³µê²©ë ¥ : </span> 100<br />
        <span>ë°©ì´ë ¥ : </span> 100<br />
        <span>ê³µê²©íë¥  : </span> 100<br />
        <span>ë°©ì´íë¥  : </span> 100<br />
      </div>
    </div>
  </div>
</body>

</html>