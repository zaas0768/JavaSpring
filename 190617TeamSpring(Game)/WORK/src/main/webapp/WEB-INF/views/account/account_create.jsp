<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>계정 생성</title>
<style>
</style>
<link href="${pageContext.request.contextPath}/resources/account.css" rel="stylesheet" />
</head>
<body>
	<input type="button" class="go_home" onclick="location.href = '/com'"
		value="홈 화면으로" />
	<div class="wrap">
		<div class="create_content">
			<div class="create_sec">
				<h3>계정 생성</h3>
			</div>
			<form action="do_create_account" method="post" class="create_sec">
				<input type="text" placeholder="아이디" value="" class="create_input" required="required" name="id" />
				<input type="password" placeholder="비밀번호" value=""	class="create_input" required="required" name="password1" />
				<input type="password" placeholder="비밀번호 확인" value="" class="create_input" required="required" name="password2" />
				<input type="submit" class="create_btn" value="생성">
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