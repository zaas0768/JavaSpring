<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
  <style>
  </style>
  <link href="${pageContext.request.contextPath}/resources/login_in.css" rel="stylesheet" />
</head>
<body>
<input type="button" class="go_home" onclick = "location.href = '/com'" value="홈 화면으로" />
 <div class="wrap">
    <div class="login_content">
      <div class="logo_sec">
        <h3>로그인 해주세요</h3>
      </div>
     <form class="login_sec" action="do_login" method="post">
		<input type="text" placeholder="아이디를 입력해주세요" class="login_input" required="required" name="id" />
  		<input type="password" placeholder="비밀번호를 입력해주세요" class="login_input" required="required" name="password" />
  	 	<input type="submit" class="login_btn" value="Login" />
  	 	<input type="button" class="go_href" onclick = "location.href = 'search_id'" value="아이디 찾기" />
  	 	<input type="button" class="go_href" onclick = "location.href = 'search_pw'" value="비밀번호 찾기" />
  	 	<input type="button" class="go_href" onclick = "location.href = 'check_identify'" value="계정 생성" />
	 </form>
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