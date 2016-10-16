package com.epam.jmp.nosql.entities;

import java.sql.Timestamp;

public class Message {

	private int userId;
	private String text;
	private Timestamp messageDate;
	
	public Message(){
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Timestamp getMessageDate() {
		return messageDate;
	}

	public void setMessageDate(Timestamp messageDate) {
		this.messageDate = messageDate;
	}
	
	
}
