<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>Select Enemy</title>

<link href="${pageContext.request.contextPath}/resources/select_enemy_form.css"
	rel="stylesheet" />
</head>
<body>
	<input type="button" class="go_home" onclick="location.href = '/com'" value="홈 화면으로" />
	<input type="button" class="go_home" onclick="location.href = 'select_enemy_form'" value="리스트 갱신" />
	<form action="battle_form" method="post" onSubmit="return checkEnemy()">
		<table>
			<tr>
				<th>선택</th>
				<th>아이디</th>
				<th>캐릭터</th>
				<th>체력</th>
				<th>공격력</th>
				<th>방어력</th>
				<th>명중률</th>
				<th>방어율</th>
				<th>공격속도</th>
			</tr>
			${select_result}
		</table>
		<input type="submit" class="go_home" value="선전포고">
	</form>
</body>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script>
	function checkEnemy() {
		var checkBox = $("input[name=id]");
		var isChecked = false;
		for (var i = 0; i < checkBox.length; i++) {
			if (checkBox[i].checked == true) {
				isChecked = true;
				break;
			}
		}
		if (isChecked) {
			return true;
		} else {
			alert("대전상대를 선택해주세요.");
			return false;
		}
	}
</script>

</html>