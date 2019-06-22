<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>아이디 찾기</title>
<style>
</style>
<link href="${pageContext.request.contextPath}/resources/account.css" rel="stylesheet" />
</head>
<body>
	<input type="button" class="go_home" onclick="location.href = '/com'" value="홈 화면으로" />
	<div class="wrap">
		<div class="create_content">
			<div class="create_sec">
				<h3>아이디 찾기</h3>
			</div>
			<form action="do_search_id" method="post" class="create_sec">
				<input type="text" placeholder="주민번호 6자리" value="" class="create_input" required="required" name="key_number" />
				<input type="submit" class="search_btn" value="확인">
				<input type="button" class="search_btn2" onclick = "location.href = 'search_pw'" value="비밀번호 찾기" />
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