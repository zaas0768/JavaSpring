<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>계정 생성</title>
<style>
</style>
<link href="${pageContext.request.contextPath}/resources/check_identify.css"
	rel="stylesheet" />
</head>
<body>
	<input type="button" class="go_home" onclick = "location.href = '/com'" value="홈 화면으로" />
	<div class="center">
		<form action="duplicate_check" method="post">
			<center>
				<input type="text" id="keyNumber" name="key_number" placeholder="주민번호 6자리" class="a" required="required" />
				<input type="submit" class="btn" value="중복검사" />
			</center>
		</form>
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