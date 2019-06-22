package com.myspring.com;

public class select_enemy_form {
	
	public String name;
	public int strikingPower;
	public int defensivePower;
	public int attackProbability;
	public int probabilityDefense;
	public int hp;
	public int speedAttack;
	
	select_enemy_form() {
	}
	select_enemy_form(String name, int hp, int strikingPower, int defensivePower, int attackProbability, int probabilityDefense, int speedAttack) {
		this.name = name;
		this.hp = hp;
		this.strikingPower = strikingPower;
		this.defensivePower = defensivePower;
		this.attackProbability = attackProbability;
		this.probabilityDefense = probabilityDefense;
		this.speedAttack = speedAttack;
	}
	
	public String toTableTagString() {
		String tagString = "";
		tagString = tagString + "<tr>";
		tagString = tagString + "<td>";
		tagString = tagString + "<input type=\"radio\" name=\"choice\">";
		tagString = tagString + "</td>";
		tagString = tagString + "<td>";
		tagString = tagString + this.name;
		tagString = tagString + "</td>";
		tagString = tagString + "<td>";
		tagString = tagString + this.strikingPower;
		tagString = tagString + "</td>";
		tagString = tagString + "<td>";
		tagString = tagString + this.defensivePower;
		tagString = tagString + "</td>";
		tagString = tagString + "<td>";
		tagString = tagString + this.attackProbability;
		tagString = tagString + "</td>";
		tagString = tagString + "<td>";
		tagString = tagString + this.probabilityDefense;
		tagString = tagString + "</td>";
		tagString = tagString + "<td>";
		tagString = tagString + this.speedAttack;
		tagString = tagString + "</td>";
		tagString = tagString + "<td>";
		tagString = tagString + this.hp;
		tagString = tagString + "</td>";
		tagString = tagString + "</tr>";
		return tagString;
	}
}
