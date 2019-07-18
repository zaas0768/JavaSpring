package com.pjs.finaltest;

public class Student {
	   public int idx;
	   public String name;
	   public String middleScore;
	   public String finalScore;
	   
	   
	   public Student() {
	      
	   }
	   
	   public Student(String name, String middleScore, String finalScore) {
	      this.name=name;
	      this.middleScore=middleScore;
	      this.finalScore =finalScore;
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
			tagString = tagString + this.middleScore;
			tagString = tagString + "</td>";
			tagString = tagString + "<td>";
			tagString = tagString + this.finalScore;
			tagString = tagString + "</td>";
			tagString = tagString + "<td>";
			tagString = tagString + "<a href ='modify?idx="+this.idx+"'>수정하기</a>";
			tagString = tagString + "</td>";
			tagString = tagString + "</tr>";
			return tagString;
		}
	}