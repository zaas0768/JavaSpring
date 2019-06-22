<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호 변경</title>
<link href="${pageContext.request.contextPath}/resources/account.css"
	rel="stylesheet" />
</head>
<body>
<input type="button" class="go_home" onclick = "location.href = '/com'" value="홈 화면으로" />
 <div class="wrap">
    <div class="create_content">
      <div class="create_sec">
        <h3>비밀번호 변경</h3>
      </div>
     <form class="create_sec" action="do_update_pw" method="post">
		<input type="password" placeholder="비밀번호" class="create_input" required="required" name="password1" />
  		<input type="password" placeholder="비밀번호 확인" class="create_input" required="required" name="password2" />
  	 	<input type="submit" class="update_btn" value="변경" />
	 </form>
    </div>
  </div>
</body>
</html>