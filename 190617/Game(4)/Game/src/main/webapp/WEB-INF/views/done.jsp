<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html lang="ko">

<head>
  <meta charset="utf-8" />
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/done.css" />
  <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
</head>

<body>
  <div id="wrap">
    <div class="top_button_section">
      <a href="/game/" class="top_button">목록으로</a>
    </div>
    <p>
      ${message}
    </p>
  </div>
</body>

</html>