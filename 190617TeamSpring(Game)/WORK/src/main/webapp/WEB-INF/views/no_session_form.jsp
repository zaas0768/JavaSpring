<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메인페이지</title>
  <style>

  </style>
  <link href="${pageContext.request.contextPath}/resources/no_session_form.css"
	rel="stylesheet" />
</head>
<body>
 <div class="wrap">
    <div class="login_content">
      <div class="logo_sec">
        <center><h3>Spring Game Project</h3></center>
      </div>
<center>
	<input type="button" class="no_session" onclick = "location.href = 'login'" value="로그인" />
	<input type="button" class="no_session" onclick = "location.href = 'check_identify'" value="계정생성" />
	<input type="button" class="no_session_id_pw" onclick = "location.href = 'search_id'" value="아이디 찾기" /> 
	<input type="button" class="no_session_id_pw" onclick = "location.href = 'search_pw'" value="비밀번호 찾기" /> 
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