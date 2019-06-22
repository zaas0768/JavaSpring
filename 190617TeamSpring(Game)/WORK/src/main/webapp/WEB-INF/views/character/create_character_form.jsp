<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>캐릭터 생성</title>
</head>
<style>
</style>
<link href="${pageContext.request.contextPath}/resources/create_character.css" rel="stylesheet" />
<body>
	<input type="button" class="go_home" onclick="location.href = '/com'"
		value="홈 화면으로" />
	<br>
	<br>
	<form action="do_create_character" method="post" onSubmit="return formCheck()">

		<div name="jobSection" class="job">
			<h1>직업선택</h1>
			<div style="float: left;">
				<label>
				<input type="radio" name="job" value="warrior">
				<img src="resources/images/warrior.png" height="350px" style="display: block;">
				<br />
				전사
				</label>
			</div>
			<div style="float: left; right: -50%">
				<label>
				<input type="radio" name="job" value="magician">
				<img src="resources/images/magician.png" height="350px" style="display: block;">
				<br />
				마법사
				</label>
			</div>
			<div style="float: right;">
				<label>
				<input type="radio" name="job" value="fighter">
				<img src="resources/images/fighter.png" height="350px" style="display: block;">
				<br />
				격투가
				</label>
			</div>
		</div>
		
		<div name="statusSection" class="status">
			<h1>스탯선택</h1>
			<div class="status">
				<label>체 력 </label>
				<span id="hp"></span>
				<input type="number" name="hp" class="status" style="text-align: center; width: 30px" readonly />
				<input type="button" name="up" value="+" />
				<input type="button" name="down" value="-" />
			</div>
			<div class="status">
				<label>공 격 력 </label>
				<span id="atk"></span>
				<input type="number" name="atk" class="status" style="text-align: center; width: 30px" readonly />
				<input type="button" name="up" value="+" />
				<input type="button" name="down" value="-" />
			</div>
			<div class="status">
				<label>방 어 력 </label>
				<span id="def"></span>
				<input type="number" name="def" class="status" style="text-align: center; width: 30px" readonly />
				<input type="button" name="up" value="+" />
				<input type="button" name="down" value="-" />
			</div>
			<div class="status">
				<label>명 중 률 </label>
				<span id="atkRate"></span>
				<input type="number" name="atk_rate" class="status" style="text-align: center; width: 30px" readonly />
				<input type="button" name="up" value="+" />
				<input type="button" name="down" value="-" />
			</div>
			<div class="status">
				<label>방 어 율 </label>
				<span id="defRate"></span>
				<input type="number" name="def_rate" class="status" style="text-align: center; width: 30px" readonly />
				<input type="button" name="up" value="+" />
				<input type="button" name="down" value="-" />
			</div>
			<div class="status">
				<label>공격속도 </label>
				<span id="speed"></span>
				<input type="number" name="speed" class="status" style="text-align: center; width: 30px" readonly />
				<input type="button" name="up" value="+" />
				<input type="button" name="down" value="-" />
			</div>
			<br />
			<label>남은 스탯 </label>
			<input type="number" name="remain_status" style="text-align: center; width: 30px" readonly />
			<br />
		</div>
		<input type="submit" value="생성" />
	</form>
	<script src="http://code.jquery.com/jquery-latest.min.js"></script>
	<script>
		var message = "${message}";
		var url = "${url}";
		if (message != "") {
			alert(message);
		}
		if (url != "") {
			document.location.href = url;
		}
	
		var MAX_STATUS = 20;
		var totalStat = MAX_STATUS;
		
		$("input[class=status]").val(0);
		$("input[name=remain_status]").val(totalStat);

		$("input[name=up]").click(function() {
			if (jobSelected()) {
				var selectedIndex = $("input[name=up]").index(this);
				if (totalStat > 0) {
					var statusValue = Number($("input[class=status]:eq(" + selectedIndex + ")").val()) + 1;
					$("input[class=status]:eq(" + selectedIndex + ")").val(statusValue);
					totalStat--;
					$("input[name=remain_status]").val(totalStat);
					if (!statusUpdate()) {
						$("input[class=status]:eq(" + selectedIndex + ")").val(statusValue - 1);
						totalStat++;
						$("input[name=remain_status]").val(totalStat);
					}
				}
			} else {
				alert("직업을 선택해주세요.");
				return;
			}
		})

		$("input[name=down]").click(function() {
			if (jobSelected()) {
				var selectedIndex = $("input[name=down]").index(this);
				if ((totalStat < MAX_STATUS) && (Number($("input[class=status]:eq(" + selectedIndex + ")").val()) > 0)) {
					var statusValue = Number($("input[class=status]:eq(" + selectedIndex + ")").val()) - 1;
					$("input[class=status]:eq(" + selectedIndex + ")").val(statusValue);
					totalStat++;
					$("input[name=remain_status]").val(totalStat);
					if (!statusUpdate()) {
						$("input[class=status]:eq(" + selectedIndex + ")").val(statusValue + 1);
						totalStat--;
						$("input[name=remain_status]").val(totalStat);
					}
				}
			} else {
				alert("직업을 선택해주세요.");
				return;
			}
		})
		
		$("input[name=job]").click(function() {			
			if (!statusUpdate()) {
				for (var i = 0; i < $("input[class=status]").length; i++) {
					$("input[class=status]:eq(" + i + ")").val(0);
				}
				totalStat = MAX_STATUS;
				$("input[name=remain_status]").val(totalStat);	
				statusUpdate();
			}
		})
		
		function jobSelected() {
			var jobSelectBox = $("input[name=job]");
			var selectedJob = -1;
			for (var i = 0; i < jobSelectBox.length; i++) {
				if (jobSelectBox[i].checked == true) {
					selectedJob = i;
					break;
				}
			}

			if (selectedJob == -1) {
				return false;
			} else if (!(selectedJob == 0 || selectedJob == 1 || selectedJob == 2)) {
				console.log("Unknown Error, Selected Job : " + selectedJob);
				return false;
			} else {
				return true;
			}
		}
		
		function statusUpdate() {			
			var jobSelectBox = $("input[name=job]");
			var selectedJob = -1;
			for (var i = 0; i < jobSelectBox.length; i++) {
				if (jobSelectBox[i].checked == true) {
					selectedJob = i;
					break;
				}
			}

			if (selectedJob == -1) {
				console.log("Job Is Not Selected");
				return false;
			} else if (!(selectedJob == 0 || selectedJob == 1 || selectedJob == 2)) {
				console.log("Unknown Error, Selected Job : " + selectedJob);
				return false;
			}
			
			var status = {
				0:[120, 8, 10, 40, 70, 7],
				1:[80, 16, 4, 60, 30, 10],
				2:[100, 10, 8, 50, 50, 9]
			}
			
			var weight = [5, 1, 1, 2, 2, 1];
			
			var hp = status[selectedJob][0] + (weight[0] * Number($("input[class=status]:eq(0)").val()));
			var atk = status[selectedJob][1] + (weight[1] * Number($("input[class=status]:eq(1)").val()));
			var def = status[selectedJob][2] + (weight[2] * Number($("input[class=status]:eq(2)").val()));
			var atkRate = status[selectedJob][3] + (weight[3] * Number($("input[class=status]:eq(3)").val()));
			var defRate = status[selectedJob][4] + (weight[4] * Number($("input[class=status]:eq(4)").val()));
			var speed = status[selectedJob][5] + (weight[5] * Number($("input[class=status]:eq(5)").val()));
			
			if (atkRate > 100 || defRate > 100) {
				alert("스텟이 최대치를 초과하였습니다.");
				return false;
			}
			
			$("#hp").html(hp);
			$("#atk").html(atk);
			$("#def").html(def);
			$("#atkRate").html(atkRate);
			$("#defRate").html(defRate);
			$("#speed").html(speed);
			
			return true;
		}

		function formCheck() {
			var statResult = 0
			for (var i = 0; i < $("input[class=status]").length; i++) {
				statResult += Number($("input[class=status]:eq(" + i + ")").val());
			}
			var jobSelectBox = $("input[name=job]");
			var selectedJob = -1
			for (var i = 0; i < jobSelectBox.length; i++) {
				if (jobSelectBox[i].checked == true) {
					selectedJob = i;
					break;
				}
			}
			if (statResult < MAX_STATUS) {
				alert("스탯분배를 완료해주세요.");
				return false;
			} else if (statResult > MAX_STATUS) {
				alert("사기는 안대영^^");
				location.reload();
				return false;
			} else if (!jobSelected()) {
				alert("직업을 선택해주세요.");
				return false;
			} else {
				return true;
			}
		}
	</script>
</body>

</html>