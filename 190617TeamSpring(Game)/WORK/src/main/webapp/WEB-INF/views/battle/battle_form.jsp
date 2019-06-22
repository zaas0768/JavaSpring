<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Battle</title>
</head>
<body>
	<div class="myCharacter" id="myCharacter" style="float:left;">
	<img src="resources/images/${my_character}.png" height="350px" style="display: block;">
	체력
	<br />
	<progress id="myHp" value="${my_current_hp}" max="${my_max_hp}"></progress>
	<br />
	공격게이지
	<br />
	<progress id="myGauge" value="${my_first_gauge}" max="100"></progress>
	</div>
	<div style="float:left; right:-50%;">
	<span id="buttonAppear">
	<input type="button" id="turn" value="턴 진행" onclick="turn()" />
	</span>
	</div>
	<div class="enemyCharacter" id="enemyCharacter" style="float:right;">
	<img src="resources/images/${enemy_character}.png" height="350px" style="display: block;">
	체력
	<br />
	<progress id="enemyHp" value="${enemy_current_hp}" max="${enemy_max_hp}"></progress>
	<br />
	공격게이지
	<br />
	<progress id="enemyGauge" value="${enemy_first_gauge}" max="100"></progress>
	</div>
</body>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script>

	var DELAY_SEC = 50;
	
	$("#turn").hide();
	
	var myMaxHp = '${my_max_hp}';
	var enemyMaxHp = '${enemy_max_hp}';
	
	var inputGaugeList = '${gauge_list}';
	var splitResult = inputGaugeList.split("|");
	var myGauge = [];
	var enemyGauge = [];
	
	for (var i = 0; i < splitResult.length; i++) {
		myGauge.push(splitResult[i].split("&")[0]);
		enemyGauge.push(splitResult[i].split("&")[1]);
	}
	
	function gaugeUp(i) {
		if (i < splitResult.length) {
			setTimeout(function() {
				$("#myGauge").val(myGauge[i]);
				$("#enemyGauge").val(enemyGauge[i]);
				i++;
				gaugeUp(i);
			}, i == 0 ? 0 : DELAY_SEC);
		} else {
			$("#turn").click();
		}
	}
	
	function hpDown(i, j, giveDamage, receiveDamage) {
		if (i < giveDamage || j < receiveDamage) {
			setTimeout(function() {
				if (i < giveDamage) {
					$("#enemyHp").val(Number($("#enemyHp").val() - 1));
					i++;
				}
				if (j < receiveDamage) {
					$("#myHp").val(Number($("#myHp").val() - 1));
					j++;
				}
				hpDown(i, j, giveDamage, receiveDamage);
			}, i == 0 || j == 0 ? 0 : DELAY_SEC);
		}
	}
	
	gaugeUp(0);
	
	function wiggle(myelement) { 
	    $(myelement) 
	    .animate({'left':(-10)+'px'},100) 
	    .animate({'left':(+20)+'px'},100) 
	    .animate({'left':(-10)+'px'},100); 
	}; 
	
	function turn() {
		$.ajax({
		  url: `do_attack`,
		  type: 'POST',
		  data: {},
		  success: function(response) {
			  if (response.result == 'success') {
				  if (response.battleResult == 'going') {
					var giveDamage = Number(response.giveDamage);
					var receiveDamage = Number(response.receiveDamage);
					
					if (giveDamage > 0) {
						wiggle("#enemyCharacter");
					}
					if (receiveDamage > 0) {
						wiggle("#myCharacter");
					}	
					
					hpDown(0, 0, giveDamage, receiveDamage);
					
					inputGaugeList = response.gaugeList;
					splitResult = inputGaugeList.split("|");
					myGauge = [];
					enemyGauge = [];
					for (var i = 0; i < splitResult.length; i++) {
						myGauge.push(splitResult[i].split("&")[0]);
						enemyGauge.push(splitResult[i].split("&")[1]);
					}
					gaugeUp(0);
					
				  } else if (response.battleResult == 'tie') {
					  $("#myHp").val(0);
					  $("#enemyHp").val(0);
					  setTimeout(function() {
						  alert("비겼습니다.");
						  location.href = '/com';
					  }, 100);
				  } else if (response.battleResult == 'win') {
					  $("#enemyHp").val(0);
					  setTimeout(function() {
						  alert("이겼습니다.");
						  location.href = '/com';
					  }, 100);
				  } else if (response.battleResult == 'lose') {
					  $("#myHp").val(0);
					  setTimeout(function() {
						  alert("졌습니다.");
						  location.href = '/com';
					  }, 100);
				  } else {
					  console.log("Battle Result Exception");
				  }
			  } else if (response.result == 'Session Problem') {
				  console.log("Session Problem");
			  } else {
				  console.log("Result Exception");
			  }
		  }
		});
	}
	
</script>
</html>

