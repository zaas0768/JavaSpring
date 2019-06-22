package com.myspring.character;

public class Character {
	public String id;
	public int hp;
	public int atk;
	public int def;
	public int atkRate;
	public int defRate;
	public int speed;
	public String job;

	private int atkGauge;
	private int currentHp;
	
	public Character() {

	}
	
	public Character(String id, int hp, int atk, int def, int atkRate, int defRate, int speed, String job) {
		this.id = id;
		this.hp = hp;
		this.atk = atk;
		this.def = def;
		this.atkRate = atkRate;
		this.defRate = defRate;
		this.speed = speed;
		this.job = job;
	}

	public String getKeyNumber() {
		return this.id;
	}

	public void setKeyNumber(String keyNumber) {
		this.id = keyNumber;
	}

	public int getHp() {
		return this.hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getAtk() {
		return this.atk;
	}

	public void setAtk(int atk) {
		this.atk = atk;
	}

	public int getDef() {
		return this.def;
	}

	public void setDef(int def) {
		this.def = def;
	}

	public int getAtkRate() {
		return this.atkRate;
	}

	public void setAtkRate(int atkRate) {
		this.atkRate = atkRate;
	}

	public int getDefRate() {
		return this.defRate;
	}

	public void setDefRate(int defRate) {
		this.defRate = defRate;
	}

	public int getSpeed() {
		return this.speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	public String getJob() {
		return this.job;
	}

	public void setJob(String job) {
		this.job = job;
	}
	
	public int getAtkGauge() {
		return this.atkGauge;
	}

	public void setAtkGauge(int atkGauge) {
		this.atkGauge = atkGauge;
	}
	
	public int getCurrentHp() {
		return this.currentHp;
	}

	public void setCurrentHp(int currentHp) {
		this.currentHp = currentHp;
	}
	
	public String toTableTagString() {
		String tagString = "";
		tagString += "<tr>";
		tagString += "<td>";
		tagString += "<input type=\"radio\" name=\"id\" value="+ this.id +">";
		tagString += "</td>";
		tagString += "<td>";
		tagString += this.id;
		tagString += "</td>";
		tagString += "<td>";
		tagString += "<img src=\"resources/images/mini_" + this.job + ".png\" height=\"50px\" style=\"display: block; margin: 0px auto;\">";
		tagString += "</td>";
		tagString += "<td>";
		tagString += this.hp;
		tagString += "</td>";
		tagString += "<td>";
		tagString += this.atk;
		tagString += "</td>";
		tagString += "<td>";
		tagString += this.def;
		tagString += "</td>";
		tagString += "<td>";
		tagString += this.atkRate;
		tagString += "</td>";
		tagString += "<td>";
		tagString += this.defRate;
		tagString += "</td>";
		tagString += "<td>";
		tagString += this.speed;
		tagString += "</td>";
		tagString += "</tr>";
		return tagString;
	}
	
	@Override
	public String toString() {
		return this.id;	
	}
	
}
