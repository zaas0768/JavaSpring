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
        <span class="no">1</span>
        <h3>ì´ë¦</h3>
        <span>ê³µê²©ë ¥ : </span> 100<br />
        <span>ë°©ì´ë ¥ : </span> 100<br />
        <span>ê³µê²©íë¥  : </span> 100<br />
        <span>ë°©ì´íë¥  : </span> 100<br />
        <button>ì¹´ë ë³ê²½</button>
      </li>
    </ul>
  </div>
</body>

</html>