package com.epam.jmp.nosql;

import java.sql.Timestamp;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import com.epam.jmp.nosql.entities.FriendShip;
import com.epam.jmp.nosql.entities.Message;
import com.epam.jmp.nosql.entities.Movie;
import com.epam.jmp.nosql.entities.User;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;

import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Runner {

	final static int USERS_NUMBER = 100;
	final static int USER_NAME_MAX_LENGTH = 10;
	final static int USER_MESSAGE_MAX_LENGTH = 50;
	final static int MOVIE_TITLE_MAX_LENGTH = 20;
	final static int FRIENDSIPS_MAX_NUMBER = 150;
	final static int MOVIES_MAX_NUMBER = 50;
	final static int MESSAGES_MAX_NUMBER = 100;

	final static String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
	final static String USER_BIRTHDAY_START_DATE = "1950-01-01 00:00:00";
	final static String USER_BIRTHDAY_END_DATE = "1998-10-15 00:00:00";
	final static String FRIENDSHIP_START_DATE = "1990-01-01 00:00:00";
	final static String OCT_15_2016_DATE = "2016-10-15 00:00:00";
	final static String MESSAGE_START_DATE = "1990-01-01 00:00:00";
	final static String MOVIE_WATCH_START_DATE = "2015-01-01 00:00:00";

	public static void main(String[] args) {
		
		MongoClient mongoClient = null;
		try{
		//connect to instance running on localhost:27017
		mongoClient = new MongoClient();
		MongoDatabase database = mongoClient.getDatabase("jmp");
		MongoCollection<Document> collection = database.getCollection("my_network");
		collection.drop();
				
		populateDocuments(collection);
		
		printAverageNumberOfMessagesByDayOfWeek(collection);
		printMaxNumberOfNewFriendshipsFromMonthToMonth(collection);
		printMinNumberOfWatchedMoviesByUsersWithMoreThan50Friends(collection);
	            
		}finally{
			if(mongoClient != null){
				mongoClient.close();
			}
		}
	
	}

	private static void printAverageNumberOfMessagesByDayOfWeek(MongoCollection<Document> collection) {
		/**
		 * db.my_network.aggregate([
		 *  {$unwind: "$messages"},
		 *  {$project:{"_id":0, messageDayOfWeek: {$dayOfWeek: "$messages.messageDate" }}},
		 *  {$sort:{messageDayOfWeek:-1}},
		 *  {$group:{"_id":{"day_of_week":"$messageDayOfWeek"}, "count":{"$sum":1}}}
		 * ])
		 */
		Bson unwind = new BasicDBObject("$unwind", "$messages");
		
		DBObject projectFields = new BasicDBObject("_id", 0);
		projectFields.put("messageDayOfWeek", new BasicDBObject("$dayOfWeek", "$messages.messageDate"));
		Bson project = new BasicDBObject("$project", projectFields );
		
		Bson sort = new BasicDBObject("$sort", new BasicDBObject("messageDayOfWeek", -1));
		
		DBObject groupFields = new BasicDBObject("_id", new BasicDBObject("day_of_week", "$messageDayOfWeek"));
		groupFields.put("count", new BasicDBObject("$sum", 1));
		Bson group = new BasicDBObject("$group",groupFields);
		
		AggregateIterable<Document> result = collection.aggregate( Arrays.asList(unwind, project, sort, group));
		System.out.println("\n Average number of messages by day of week: ");
		for(Document doc: result){
			System.out.println(doc.toJson());
		}
		
	}

	private static void printMaxNumberOfNewFriendshipsFromMonthToMonth(MongoCollection<Document> collection) {
		/**
		 * db.my_network.aggregate([
		 *  {$unwind: "$friendShips"},
		 *  {$project:{"_id":0, friendShipMonth: {$month: "$friendShips.friendShipDate" }}},
		 *  {$sort:{friendShipMonth:1}},
		 * {$group:{"_id":{"month":"$friendShipMonth"}, "count":{"$sum":1}}}
		 * ])
		 */
		Bson unwind = new BasicDBObject("$unwind", "$friendShips");
		
		DBObject projectFields = new BasicDBObject("_id", 0);
		projectFields.put("friendShipMonth", new BasicDBObject("$month", "$friendShips.friendShipDate"));
		Bson project = new BasicDBObject("$project", projectFields );
		
		Bson sort = new BasicDBObject("$sort", new BasicDBObject("friendShipMonth", 1));
		
		DBObject groupFields = new BasicDBObject("_id", new BasicDBObject("month", "$friendShipMonth"));
		groupFields.put("count", new BasicDBObject("$sum", 1));
		Bson group = new BasicDBObject("$group",groupFields);
		
		AggregateIterable<Document> result = collection.aggregate( Arrays.asList(unwind, project, sort, group));
		System.out.println("\n Max number of new friendships from month to month: ");
		for(Document doc: result){
			System.out.println(doc.toJson());
		}
		
	}

	private static void printMinNumberOfWatchedMoviesByUsersWithMoreThan50Friends(
			MongoCollection<Document> collection) {
		/**
		 * db.my_network.aggregate([
		 * 	{$project:{userName:1, userId:1, numberOfMovies:{$size: "$movies"}, numberOfFriends: { $size: "$friendShips" }}},
		 * 	{$match:{numberOfFriends:{$gt: 50}}},
		 * 	{$sort:{numberOfMovies:1}} 
		 * ])
		 */
		DBObject fields = new BasicDBObject("userName", 1);
		fields.put("userId", 1);
		fields.put("_id", 0);
		fields.put("numberOfMovies", new BasicDBObject("$size", "$movies"));
		fields.put("numberOfFriends", new BasicDBObject("$size", "$friendShips"));
		Bson project = new BasicDBObject("$project", fields );
		
		Bson match = new BasicDBObject("$match", new BasicDBObject("numberOfFriends",  new BasicDBObject("$gt", 10)));
		
		Bson sort = new BasicDBObject("$sort", new BasicDBObject("numberOfMovies", 1));
		
		Document result = collection.aggregate( Arrays.asList(project, match, sort)).first();
		System.out.println("\n Min number of watched movies by users with more than 50 friends: ");
		System.out.println(result==null ? "Not found" : result.toJson());
	}

	private static void populateDocuments(MongoCollection<Document> collection) {
        for (int i = 0; i < USERS_NUMBER; i++) {
        	collection.insertOne(createNewDocument(i));
        }
	}

	private static Document createNewDocument(int userId) {
		User user = createUser(userId);
		Document doc = new Document("userName", user.getUserName())
                .append("birthDate", user.getBirthDate())
                .append("userId", user.getId());

		//add friedships array 
		List<Document> friendsContainer = new ArrayList<Document>();
		for(FriendShip friendShip : createUserFriendships(userId)){
			Document item = new Document("friendId", friendShip.getUser2Id())
					.append("friendShipDate", friendShip.getFriendshipDate());
			friendsContainer.add(item);
		}
		doc.append("friendShips", friendsContainer);
		
		//add messages array 
		List<Document> messagesContainer = new ArrayList<Document>();
		for(Message message : createUserMessages(userId)){
			Document item = new Document("text", message.getText())
					.append("messageDate", message.getMessageDate());
			messagesContainer.add(item);
		}
		doc.append("messages", messagesContainer);
		
		//add movies array 
		List<Document> moviesContainer = new ArrayList<Document>();
		for(Movie movie : createUserMovies(userId)){
			Document item = new Document("title", movie.getTitle())
					.append("watchDate", movie.getWatchDate());
			moviesContainer.add(item);
		}
		doc.append("movies", moviesContainer);
		return doc;
	}
	
	
	private static User createUser(int userId) {
		User user = new User();
		user.setId(userId);
		user.setUserName(generateString(USER_NAME_MAX_LENGTH));
		long beginTime = Timestamp.valueOf(USER_BIRTHDAY_START_DATE).getTime();
		long endTime = Timestamp.valueOf(USER_BIRTHDAY_END_DATE).getTime();
		user.setBirthDate(generateTimestamp(beginTime, endTime));
		return user;
	}

	private static List <FriendShip> createUserFriendships(int userId) {
		List <FriendShip> friendShips = new ArrayList<FriendShip>();
		for(int i = 0; i < generateIntFromRange(FRIENDSIPS_MAX_NUMBER); i++){
			FriendShip friendShip = new FriendShip();
			friendShip.setUser1Id(userId);
			friendShip.setUser2Id(generateIntFromRange(USERS_NUMBER));
			long beginTime = Timestamp.valueOf(FRIENDSHIP_START_DATE).getTime();
			long endTime = Timestamp.valueOf(OCT_15_2016_DATE).getTime();
			friendShip.setFriendshipDate(generateTimestamp(beginTime, endTime));
			friendShips.add(friendShip);
		} 
		return friendShips;
	}
	
	private static List <Message> createUserMessages(int userId) {
		List <Message> messages = new ArrayList<Message>();
		for(int i = 0; i < generateIntFromRange(MESSAGES_MAX_NUMBER); i++){
			Message message = new Message();
			message.setUserId(userId);
			message.setText(generateString(USER_MESSAGE_MAX_LENGTH));
			long beginTime = Timestamp.valueOf(MESSAGE_START_DATE).getTime();
			long endTime = Timestamp.valueOf(OCT_15_2016_DATE).getTime();
			message.setMessageDate(generateTimestamp(beginTime, endTime));
			messages.add(message);
		} 
		return messages;
	}

	private static List <Movie> createUserMovies(int userId) {
		List <Movie> movies = new ArrayList<Movie>();
		for(int i = 0; i < generateIntFromRange(MOVIES_MAX_NUMBER); i++){
			Movie movie = new Movie();
			movie.setUserId(userId);
			movie.setTitle(generateString(MOVIE_TITLE_MAX_LENGTH));
			long beginTime = Timestamp.valueOf(MOVIE_WATCH_START_DATE).getTime();
			long endTime = Timestamp.valueOf(OCT_15_2016_DATE).getTime();
			movie.setWatchDate(generateTimestamp(beginTime, endTime));
			movies.add(movie);
		} 
		return movies;
	}

	private static String generateString(int charNumber){
		char[] chars = ALPHABET.toCharArray();
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < charNumber; i++) {
		    char c = chars[random.nextInt(chars.length)];
		    sb.append(c);
		}
		return sb.toString();
	}

	private static int generateIntFromRange(int range){
		return ThreadLocalRandom.current().nextInt(1, range + 1);
	}

	private static Timestamp generateTimestamp(long beginTime, long endTime) {
		long diff = endTime - beginTime + 1;
		long random = beginTime + (long) (Math.random() * diff);
		return new Timestamp(random);
	}

}
