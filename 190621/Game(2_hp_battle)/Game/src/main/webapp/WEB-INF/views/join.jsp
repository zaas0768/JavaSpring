<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html lang="ko">

<head>
<title>rune</title>
<meta charset="utf-8" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/join.css" />
</head>

<body>
  <div class="wrap">
    <div class="join_content">
      <div class="logo_sec">
        <h3>회원 가입</h3>
      </div>
      <form class="join_sec" action="do_join" method="post">
        <label>아이디</label>
        <input type="text" placeholder="아이디" required="required" name="id" />
        <label>패스워드</label>
        <input type="password" placeholder="패스워드" required="required" name="password" />
        <label>이름</label>
        <input type="text" placeholder="이름" required="required" name="name" />
        <input type="submit" class="join_btn" value="확인" />
      </form>
    </div>
  </div>
</body>

</html>