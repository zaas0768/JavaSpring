package com.politech.student;

public class People {
	public int idx;
	public String name;
	public String favoriteColor;
	
	People() {
	}
	People(String name, String favoriteColor) {
		this.name = name;
		this.favoriteColor = favoriteColor;
	}
	public String toTableTagString() {
		String tagString = "";
		tagString = tagString + "<tr>";
		tagString = tagString + "<td>";
		tagString = tagString + this.idx;
		tagString = tagString + "</td>";
		tagString = tagString + "<td>";
		tagString = tagString + this.name;
		tagString = tagString + "</td>";
		tagString = tagString + "<td>";
		tagString = tagString + this.favoriteColor;
		tagString = tagString + "</td>";
		tagString = tagString + "</tr>";
		return tagString;
	}
}
