package com.epam.jmp.nosql.entities;

import java.sql.Timestamp;

public class User {

	private int id;
	private String userName;
	private Timestamp birthDate;
	
	public User (){
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Timestamp getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Timestamp birthDate) {
		this.birthDate = birthDate;
	}
}
