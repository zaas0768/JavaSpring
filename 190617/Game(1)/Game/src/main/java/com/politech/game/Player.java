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
		tagString = tagString + "<tr>";
		tagString = tagString + "<td>";
		tagString = tagString + this.idx;
		tagString = tagString + "</td>";
		tagString = tagString + "<td>";
		tagString = tagString + this.id;
		tagString = tagString + "</td>";
		tagString = tagString + "<td>";
		tagString = tagString + this.name;
		tagString = tagString + "</td>";
		tagString = tagString + "</tr>";
		return tagString;
	}
}
