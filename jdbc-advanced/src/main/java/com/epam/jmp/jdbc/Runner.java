package com.epam.jmp.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Runner {

	final static int usersNumber = 1000;
	final static int frienshipsNumber = 100000;
	final static int postsNumber = 100000;
	final static int likesNumber = 400000;

	public static void main(String[] args) {

		clearDB();
		createTables();
		insertUsers();
		insertFriendships();
		insertPosts();
		insertLikes();
		selectUsers();
	}

	private static void clearDB() {
		try (Connection conn = ConnectionProvider.getConnection(); Statement st = conn.createStatement();) {
			st.addBatch(Constants.SQL_DELETE_TABLE_USERS);
			st.addBatch(Constants.SQL_DELETE_TABLE_FRIENDSHIPS);
			st.addBatch(Constants.SQL_DELETE_TABLE_POSTS);
			st.addBatch(Constants.SQL_DELETE_TABLE_LIKES);
			st.executeBatch();
			System.out.println("Cleanup is Done!");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private static void createTables() {
		try (Connection conn = ConnectionProvider.getConnection(); Statement st = conn.createStatement();) {
			st.addBatch(Constants.SQL_CREATE_TABLE_USERS);
			st.addBatch(Constants.SQL_CREATE_TABLE_FRIENDSHIPS);
			st.addBatch(Constants.SQL_CREATE_TABLE_POSTS);
			st.addBatch(Constants.SQL_CREATE_TABLE_LIKES);
			st.executeBatch();
			System.out.println("Tables creation is Done!");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private static void insertUsers() {
		try (Connection conn = ConnectionProvider.getConnection(); 
				PreparedStatement ps = conn.prepareStatement(Constants.SQL_INSERT_USER);) {
			long beginTime = Timestamp.valueOf(Constants.USER_BIRTHDAY_START_DATE).getTime();
			long endTime = Timestamp.valueOf(Constants.USER_BIRTHDAY_END_DATE).getTime();
			for(int i = 0; i < usersNumber; i ++){
				ps.setString(1, generateString(15));
				ps.setString(2, generateString(20));
				ps.setTimestamp(3, generateTimestamp(beginTime, endTime));
				ps.execute();
			}
			System.out.println("Users insert is Done!");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private static void insertFriendships() {
		try (Connection conn = ConnectionProvider.getConnection(); 
				PreparedStatement ps = conn.prepareStatement(Constants.SQL_INSERT_FRIENDSHIP);) {
			long beginTime = Timestamp.valueOf(Constants.FRIENDSHIP_START_DATE).getTime();
			long endTime = Timestamp.valueOf(Constants.SEPT_9_2016_DATE).getTime();
			for(int i = 0; i < frienshipsNumber; i ++){
				ps.setInt(1, generateId(usersNumber));
				ps.setInt(2, generateId(usersNumber));
				ps.setTimestamp(3, generateTimestamp(beginTime, endTime));
				ps.execute();
			}
			System.out.println("Friendships insert is Done!");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private static void insertPosts() {
		try (Connection conn = ConnectionProvider.getConnection(); 
				PreparedStatement ps = conn.prepareStatement(Constants.SQL_INSERT_POST);) {
			long beginTime = Timestamp.valueOf(Constants.POST_START_DATE).getTime();
			long endTime = Timestamp.valueOf(Constants.SEPT_9_2016_DATE).getTime();
			for(int i = 0; i < postsNumber; i ++){
				ps.setInt(1, generateId(usersNumber));
				ps.setString(2, generateString(50));
				ps.setTimestamp(3, generateTimestamp(beginTime, endTime));
				ps.execute();
			}
			System.out.println("Posts insert is Done!");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private static void insertLikes() {
		try (Connection conn = ConnectionProvider.getConnection();
				PreparedStatement ps = conn.prepareStatement(Constants.SQL_INSERT_LIKE);) {
			long beginTime = Timestamp.valueOf(Constants.LIKE_START_DATE).getTime();
			long endTime = Timestamp.valueOf(Constants.LIKE_END_DATE).getTime();
			for (int i = 0; i < likesNumber; i++) {
				ps.setInt(1, generateId(postsNumber));
				ps.setInt(2, generateId(usersNumber));
				ps.setTimestamp(3, generateTimestamp(beginTime, endTime));
				ps.execute();
			}
			System.out.println("Likes insert is Done!");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	private static String generateString(int charNumber){
		char[] chars = Constants.alph.toCharArray();
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < charNumber; i++) {
		    char c = chars[random.nextInt(chars.length)];
		    sb.append(c);
		}
		String output = sb.toString();
		return output;
	}

	private static Timestamp generateTimestamp(long beginTime, long endTime) {
		long diff = endTime - beginTime + 1;
		long random = beginTime + (long) (Math.random() * diff);
		return new Timestamp(random);
	}
	
	private static int generateId(int maxValue){
		return ThreadLocalRandom.current().nextInt(1, maxValue + 1);
	}
	

	private static void selectUsers() {
		try (Connection conn = ConnectionProvider.getConnection();
				PreparedStatement ps = conn.prepareStatement(Constants.SQL_SELECT_USERS);) {
			Timestamp searchFrom = Timestamp.valueOf(Constants.QUERY_LIKE_START_DATE);
			Timestamp searchTo = Timestamp.valueOf(Constants.QUERY_LIKE_END_DATE);
			int friendshipsNumber = 20;
			int likesNumber = 20;
			ps.setTimestamp(1, searchFrom);
			ps.setTimestamp(2, searchTo);
			ps.setInt(3, friendshipsNumber);
			ps.setInt(4, likesNumber);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					System.out.println("User ID: " + rs.getInt(1) + " User name: " + rs.getString(2));
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
