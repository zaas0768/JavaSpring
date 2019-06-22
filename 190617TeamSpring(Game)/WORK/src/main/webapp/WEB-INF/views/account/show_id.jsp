<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>아이디 찾기</title>
</head>
<link href="${pageContext.request.contextPath}/resources/check_identify.css" rel="stylesheet" />
<body>
	<center>
		<br />
		<h2>당신의 아이디는 : ${id} 입니다.</h2>
		<br />
		<input type="button" class="go_home" onclick = "location.href = '/com'" value="홈 화면으로" />
		<input type="button" class="go_home2" onclick = "location.href = 'login'" value="로그인 페이지 가기" />
		<input type="button" class="go_home" onclick = "location.href = 'search_pw'" value="비밀번호 찾기" />
	</center>
</body>
</html>