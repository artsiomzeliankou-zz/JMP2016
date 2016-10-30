package com.epam.jmp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

@Path("users")
public class UserService {

	private static final Map<String, User> users = new HashMap<String, User>();
	private static final File baseDir = new File(System.getProperty("java.io.tmpdir"));
	
	// add initial user data
	static{
		User user = new User();
		user.setFirstName("test_user_FirstName");
		user.setLastName("test_user_LastName");
		user.setLogin("test_user_Login");
		user.setEmail("test_user@mail.com");
		users.put(user.getLogin(), user);
	}
	
    @GET
    @Path("test")
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "Hello User!";
    }
    
 // CREATE USER FROM XML
    /**
     * valid xml:
     * <user>
	 * 	<email>test@test.com</email>
	 * 	<firstName>test</firstName>
	 * 	<lastName>test</lastName>
	 * 	<login>test</login>
	 * </user>
     */
 	@POST
 	@Path("create")
 	@Consumes(MediaType.APPLICATION_XML)
 	public Response createUser(@Valid User newUser) {

 		String key = newUser.getLogin(); 
 		users.put(key, newUser);
		return Response.ok().entity(key).build();

 	}
 	
 // CREATE USER FROM JSON
 	/**
 	 * {
	 * 	"email": "test@test.com",
	 * 	"firstName": "test",
	 * 	"lastName": "test",
	 * 	"login": "test"
	 * }
 	 */
  	@POST
  	@Path("createj")
  	@Consumes(MediaType.APPLICATION_JSON)
  	public Response createjUser(@Valid User newUser) {

  		String key = newUser.getLogin(); 
  		users.put(key, newUser);
 		return Response.ok().entity(key).build();

  	}
 	
 // DELETE USER
 	@DELETE
 	@Path("{login}")
 	public Response deleteUser(@PathParam("login") String userKey) {

 		User user = users.get(userKey);
 		if(user != null){
 			users.remove(userKey);	
 			return Response.ok().build();
 		}
 		return Response.status(Status.NOT_FOUND).build();
 	}
 	
 // GET ALL USERS
	@GET
	@Path("allUsers")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllUsers() {
		
		List<User> usersList = new ArrayList<User>(users.values());
		UsersResponceList responceList = new UsersResponceList();
		responceList.setList(usersList);
		return Response.ok(responceList).build();
		
	}
	
	// GET USER
	@GET
	@Path("{login}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUser(@PathParam("login") String userKey) {

		User user = users.get(userKey);
		if (user != null) {
			return Response.ok().entity(user).build();
		}
		return Response.status(Status.NOT_FOUND).build();

	}
	
 // UPDATE USER
	@PUT
	@Path("update")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateUser(@Valid User user) {

		String key = user.getLogin(); 
		User existingUser = users.get(key);
 		if(existingUser != null){
 			users.put(key, user);
 			return Response.ok().build();
 		}
 		return Response.status(Status.NOT_FOUND).build();

	}
	
 // UPLOAD USER LOGO
	@POST
	@Path("uploadLogo")
	@Consumes({MediaType.MULTIPART_FORM_DATA})
	public Response uploadLogo(@FormDataParam("file") InputStream fileInputStream,
			@FormDataParam("file") FormDataContentDisposition fileMetaData) {
		
		String filePath = baseDir + File.separator + fileMetaData.getFileName();
		
		try(OutputStream out = new FileOutputStream(new File(filePath));) {
			byte [] attachmentBytes = IOUtils.toByteArray(fileInputStream);
			out.write(attachmentBytes);
		} catch (IOException e) {
			return Response.serverError().entity(e.getMessage()).type(MediaType.TEXT_PLAIN).build();
		}
		return Response.status(Status.OK).build();
	}
	
 
 // GET USER LOGO
	@GET
	@Path("logo/{fileName}")
	@Produces("image/png")
	public Response getLogo(@PathParam("fileName") String name) {

		String fileName = name + ".png";
		File file = new File(baseDir + File.separator + fileName);
		if (file != null && file.isFile()) {
			return Response.ok((Object) file).header("content-disposition", "attachment; filename = " + fileName + "\"").build();
		}
		return Response.status(Status.NOT_FOUND).build();
	}
	
 // GET ALL LOGO LIST
	@GET
	@Path("logos")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllLogo() {

		List<String> list = new ArrayList<String>();
		for(File file: baseDir.listFiles()){
			list.add(file.getName());
		}
		LogoResponceList responceList = new LogoResponceList();
		responceList.setList(list);
		return Response.ok(responceList).build();
	}
		
}
