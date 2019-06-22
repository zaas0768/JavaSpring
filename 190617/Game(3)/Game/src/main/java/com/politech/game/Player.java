package com.politech.game;

public class Player {
	public int idx;
	public String id;
	public String password;
	public String name;
	public int attackPower;
	public int attackRate;
	public int defensePower;
	public int defenseRate;
	
	Player() {
	}
	Player(String id, String password, String name) {
		this.name = name;
		this.id = id;
		this.password = password;
	}
	public String toTableTagString() {
		String tagString = "";
		tagString = tagString + "<li>";
		tagString = tagString + "<span class=\"no\">";
		tagString = tagString + this.idx;
		tagString = tagString + "</span>";
		tagString = tagString + "<h3>";
		tagString = tagString + this.name;
		tagString = tagString + "</h3>";
		tagString = tagString + "<span>공격력 : </span> ";
		tagString = tagString + this.attackPower;
		tagString = tagString + "<br />";
		tagString = tagString + "<span>공격확률 : </span> ";
		tagString = tagString + this.attackRate;
		tagString = tagString + "<br />";
		tagString = tagString + "<span>방어력 : </span> ";
		tagString = tagString + this.defensePower;
		tagString = tagString + "<br />";
		tagString = tagString + "<span>방어확률 : </span> ";
		tagString = tagString + this.defenseRate;
		tagString = tagString + "<br />";
		tagString = tagString + "<button>선택</button>";
		tagString = tagString + "</li>";
		return tagString;
	}
}
