package com.epam.jmp.nosql.entities;

import java.sql.Timestamp;

public class Movie {

	private int userId;
	private String title;
	private Timestamp watchDate;
	
	public Movie(){
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Timestamp getWatchDate() {
		return watchDate;
	}

	public void setWatchDate(Timestamp watchDate) {
		this.watchDate = watchDate;
	}
	
	
}
