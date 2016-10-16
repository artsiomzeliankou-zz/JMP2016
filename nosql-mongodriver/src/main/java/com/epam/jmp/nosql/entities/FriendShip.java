package com.epam.jmp.nosql.entities;

import java.sql.Timestamp;

public class FriendShip {

	private int user1Id;
	private int user2Id;
	private Timestamp friendshipDate;
	
	public FriendShip(){
	}

	public int getUser1Id() {
		return user1Id;
	}

	public void setUser1Id(int user1Id) {
		this.user1Id = user1Id;
	}

	public int getUser2Id() {
		return user2Id;
	}

	public void setUser2Id(int user2Id) {
		this.user2Id = user2Id;
	}

	public Timestamp getFriendshipDate() {
		return friendshipDate;
	}

	public void setFriendshipDate(Timestamp friendshipDate) {
		this.friendshipDate = friendshipDate;
	}

	
}
