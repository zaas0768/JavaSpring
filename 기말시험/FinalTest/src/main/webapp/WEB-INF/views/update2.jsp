<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!doctype html>
<html lang="ko">

<head>
<title>update</title>
<meta charset="utf-8" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/update.css" />
</head>

<body>
	<div class="wrap">
		<div class="update_content">
			<div class="logo_sec">
				<h3>데이터 수정</h3>
			</div>
			<form class="update_sec" action="update_data" method="get">
				<input type="hidden" name="idx" value="${idx_value}" />
				<label>이름</label>
				<input type="text" placeholder="이름입력" name="name" value="${name_value}" />
				<label>중간고사 점수</label>
				<input type="number" placeholder="중간고사 점수" name="middleScore" value="${middleScore_value}"/>
				<label>기말고사 점수</label>
				<input type="number" placeholder="기말고사 점수" name="finalScore" value="${finalScore_value}"/>
				<input type="submit" class="update_btn" value="수정 완료" />
			</form>
		</div>
	</div>
</body>

</html>