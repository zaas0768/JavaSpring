package com.myspring.account;

public class Account {
	public String keyNumber;
	public String id;
	public String password;
	
	public Account(){
	}
	
	public Account(String keyNumber, String id, String password){
		this.keyNumber = keyNumber;
		this.id = id;
		this.password = password;
	}
	
}
