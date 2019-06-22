<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메인페이지</title>
 <link href="${pageContext.request.contextPath}/resources/yes_session_form.css"
	rel="stylesheet" />
</head>
<body>
 <div class="wrap">
    <div class="login_content">
      <div class="logo_sec">
        <center><h3>Start Game</h3></center>
      </div>
	<center>
		<input type="button" class="yes_session" onclick = "location.href = 'select_enemy_form'" value="전투하기" />
		<br /><br />
		${select_result}
		<br /><br />
		<input type="button" class="yes_session" onclick = "location.href = 'logout'" value="로그아웃" />
	</center>
	    </div>
  </div>
</body>
<script>
	var message = "${message}";
	var url = "${url}";
	if (message != "") {
		alert(message);
	}
	if (url != "") {
		document.location.href = url;
	}
</script>
</html>