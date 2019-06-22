<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<style>
	form {margin: 0 auto; padding: 10px; background: #eee;}
	label {display: inline-block; width: 200px; text-align: center;}
	input {display: inline-block; width: 300px;}
	form span {display: block; padding: 10px;}
	input[type="submit"] {display: inline-block; margin: 5px 0px 0px 140px;}
</style>
</head>
<body>
	<form action="/spring1/input" method="get">
		<span><label>이름</label><input type="text" name="name" /></span>
		<span><label>중간고사점수</label><input type="number" name="middle_score" /></span>
		<span><label>기말고사점수</label><input type="number" name="final_score" /></span>
		<input type="submit" value="데이터 전송" />
	</form>

</body>
</html>