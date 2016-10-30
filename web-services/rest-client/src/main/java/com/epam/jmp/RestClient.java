package com.epam.jmp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;


public class RestClient {

	private static final String BASE_URL = "http://localhost:8487/user-service/rest";
	private static final User baseUser = new User("test_user_FirstName", "test_user_LastName", "test_user_Login", "test_user@mail.com");
	private static final String LOGO_PATH = "D:/test";
	private static final String LOGO_FILE_NAME = "EPAM_Logo.png";
	private static final String LOGO_NAME = "EPAM_Logo";
			
	public static void main(String[] args) {
		new RestClient().demo();
	}

	private void demo() {
		Client client = ClientBuilder.newClient();
		WebTarget usersWebTarget = client.target(BASE_URL).path("users");

		getAllUsers(usersWebTarget);
		getAllLogo(usersWebTarget);
		getUser(usersWebTarget);
		createUser(usersWebTarget);
		updateUser(usersWebTarget);
		deleteUser(usersWebTarget);
		uploadLogo();
		getLogo(usersWebTarget);
		
	}

 // GET ALL USERS
	private void getAllUsers(WebTarget usersWebTarget) {
		WebTarget webTarget = usersWebTarget.path("allUsers");
		Invocation.Builder invocationBuilder = webTarget.request();
		Response response = invocationBuilder.get();
		if(response.getStatus() == Response.Status.OK.getStatusCode()){
			System.out.println(response.readEntity(String.class));
		}
	}

 // GET ALL LOGO LIST
	private void getAllLogo(WebTarget usersWebTarget) {
		WebTarget webTarget = usersWebTarget.path("logos");
		Invocation.Builder invocationBuilder = webTarget.request();
		Response response = invocationBuilder.get();
		if(response.getStatus() == Response.Status.OK.getStatusCode()){
			System.out.println(response.readEntity(String.class));
		}
	}
	
 // GET USER
	private void getUser(WebTarget usersWebTarget) {
		WebTarget webTarget = usersWebTarget.path(baseUser.getLogin());
		Invocation.Builder invocationBuilder = webTarget.request();
		Response response = invocationBuilder.get();
		if(response.getStatus() == Response.Status.OK.getStatusCode()){
			System.out.println(response.readEntity(String.class));
		}
	}

 // CREATE USER FROM XML
	private void createUser(WebTarget usersWebTarget) {
		User testUser = new User("John", "Walker", "johny", "johny@mail.com");
		WebTarget webTarget = usersWebTarget.path("create");
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_XML);
		Response response = invocationBuilder.post(Entity.entity(testUser, MediaType.APPLICATION_XML));
		if(response.getStatus() == Response.Status.OK.getStatusCode()){
			System.out.println(response.readEntity(String.class));
		}
	}
	
 // UPDATE USER
	private void updateUser(WebTarget usersWebTarget) {
		User testUser = new User("Richard", "Hennessey", "johny", "rich@mail.com");
		WebTarget webTarget = usersWebTarget.path("update");
		Invocation.Builder invocationBuilder = webTarget.request();
		Response response = invocationBuilder.put(Entity.entity(testUser, MediaType.APPLICATION_JSON));
		System.out.println(response.getStatus());
	}
	
 // DELETE USER
	private void deleteUser(WebTarget usersWebTarget) {
		WebTarget webTarget = usersWebTarget.path("johny");
		Invocation.Builder invocationBuilder = webTarget.request();
		Response response = invocationBuilder.delete();
		System.out.println(response.getStatus());
	}
	
 // UPLOAD USER LOGO
	private void uploadLogo() {

		File fileToUpload = new File(LOGO_PATH + File.separator + LOGO_FILE_NAME);
		if (fileToUpload != null && fileToUpload.isFile()) {
			Client client = ClientBuilder.newBuilder().register(MultiPartFeature.class).build();
			WebTarget webTarget = client.target(BASE_URL).path("users").path("uploadLogo");

			FileDataBodyPart filePart = new FileDataBodyPart("file", fileToUpload);
			filePart.setContentDisposition(FormDataContentDisposition.name("file").fileName(LOGO_FILE_NAME).build());

			MultiPart multipartEntity = new FormDataMultiPart().bodyPart(filePart);
			Response response = webTarget.request().post(Entity.entity(multipartEntity, MediaType.MULTIPART_FORM_DATA));
			System.out.println(response.getStatus());
			response.close();
		}
	}
	
 // GET USER LOGO
	private void getLogo(WebTarget usersWebTarget) {
		
		WebTarget webTarget = usersWebTarget.path("logo").path(LOGO_NAME);
		Response response = webTarget.request().get();
		if(response.getStatus() == Response.Status.OK.getStatusCode()){
			InputStream in = response.readEntity(InputStream.class);
            if (in != null) {
            	String fileName = LOGO_NAME + "_new.png";
                File file = new File(LOGO_PATH + File.separator + fileName);
                try(OutputStream out = new FileOutputStream(file);) {
        			byte [] attachmentBytes = IOUtils.toByteArray(in);
        			out.write(attachmentBytes);
        		} catch (IOException e) {
        			e.printStackTrace();
        		}
                System.out.println("Result size:" + file.length());
            }
		}
		
	}
}
