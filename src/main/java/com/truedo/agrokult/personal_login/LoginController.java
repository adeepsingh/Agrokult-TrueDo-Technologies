package com.truedo.agrokult.personal_login;


import java.io.InputStream;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;


import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.truedo.agrokult.interest.Interests;
import com.truedo.agrokult.personal_login.LoginService;

@Component
@Path("/user")
public class LoginController {
	
	@Autowired
	LoginService user;
	
	
	@POST
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    public Response uploadFileWithData(
    		@FormDataParam("file") InputStream stream,
    		@FormDataParam("file") FormDataContentDisposition fileDetail,
    		@FormDataParam("mail_id") String mail_id,
    		@FormDataParam("phone_number") String phone,    		
    		@FormDataParam("password") String password,
    		@FormDataParam("first_name")String first_name,
    		@FormDataParam("middle_name")String middle_name,
    		@FormDataParam("last_name") String last_name,
    		@FormDataParam("address") String address,
    		@FormDataParam("dob") String dob,
    		@FormDataParam("company") String company,
    		@FormDataParam("title") String title,
    		@FormDataParam("website") String website,
    		@FormDataParam("realtime_location") String realtime_location,
    		@FormDataParam("interests") List<String> interests) throws Exception{
			
		
		
		return user.addUser(stream, mail_id, password, first_name, middle_name, last_name, address, dob, company, title, website, realtime_location, phone, interests);
	} 
	
	@POST
	@Path("login")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response login(@FormParam("username")String username,@FormParam("password")String password)
	{
		return user.login(username,password);
	}
	
	@POST
	@Path("interest/{id}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response login(@HeaderParam("Authorization")String Authorization,@PathParam("id")int id,@FormParam("interest")String interest)
	{
		if(!user.verify(Authorization))
			return Response.status(Status.BAD_REQUEST).type("text/plain").entity("You are not logged in. Pease log in and make sure cookies are enabled.").build();
		return user.addInterest(id, interest);
	}
	
	@GET
	@Path("/image/{id}")
	@Produces("image/jpg")
	public Response getFullImage(@HeaderParam("Authorization")String Authorization,@PathParam("id") int id) {
			if(!user.verify(Authorization))
				return Response.status(Status.BAD_REQUEST).type("text/plain").entity("You are not logged in. Pease log in and make sure cookies are enabled.").build();
			return user.getImage(id);
	}

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public LoginModel UserById(@HeaderParam("Authorization")String Authorization,@PathParam("id") int id) {
		if(!user.verify(Authorization))
			return null;
		return user.getUser(id);
	}
	
	@GET
	@Path("all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<LoginModel> AllUsers(@HeaderParam("Authorization")String Authorization) {
		if(!user.verify(Authorization))
			return null;	
		return user.getAllUsers();
	}
	
	@GET
	@Path("/interest/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Interests getInterests(@HeaderParam("Authorization")String Authorization,@PathParam("id") int id) {
			if(!user.verify(Authorization))
				return null;
			System.out.println(id);
			return user.getInterest(id);
	}
	
	
	@DELETE
	@Path("{id}")
	public Response remove(@HeaderParam("Authorization")String Authorization,@PathParam("id") int id){
		if(!user.verify(Authorization))
			return null;	
		return user.delete(id);		
	}
	
	
	@DELETE
	@Path("/interest/{id}")
	public Response remove(@HeaderParam("Authorization")String Authorization,@PathParam("id") int id,@FormParam("interest") String interest){
		if(!user.verify(Authorization))
			return null;	
		return user.deleteInterest(id, interest);		
	}
	
	
	//Possible Security Violation
	@PUT	
	@Path("password/{id}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response password(@HeaderParam("Authorization")String Authorization,@PathParam("id") int id, @FormParam("password") String password){
		if(!user.verify(Authorization))
			return null;	
		return user.updatePassword(id,password);
	}
	

	@PUT	
	@Path("name/{id}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response name(@HeaderParam("Authorization")String Authorization,@PathParam("id") int id, @FormParam("first_name") String first_name,@FormParam("middle_name") String middle_name,@FormParam("last_name") String last_name){
		if(!user.verify(Authorization))
			return Response.status(Status.BAD_REQUEST).type("text/plain").entity("You are not logged in. Pease log in and make sure cookies are enabled.").build();
		
		return user.updateName(id,first_name,middle_name,last_name);
	}
	
	@PUT	
	@Path("number/{id}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response number(@HeaderParam("Authorization")String Authorization,@PathParam("id") int id, @FormParam("number") String number){
		if(!user.verify(Authorization))
			return Response.status(Status.BAD_REQUEST).type("text/plain").entity("You are not logged in. Pease log in and make sure cookies are enabled.").build();
		
		return user.updateNumber(id,number);
	}
	
	@PUT	
	@Path("address/{id}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response address(@HeaderParam("Authorization")String Authorization,@PathParam("id") int id, @FormParam("address") String address){
		if(!user.verify(Authorization))
			return Response.status(Status.BAD_REQUEST).type("text/plain").entity("You are not logged in. Pease log in and make sure cookies are enabled.").build();
		
		return user.updateAddress(id,address);
	}
	
	@PUT	
	@Path("dob/{id}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response dob(@HeaderParam("Authorization")String Authorization,@PathParam("id") int id, @FormParam("dob") String dob){
		if(!user.verify(Authorization))
			return Response.status(Status.BAD_REQUEST).type("text/plain").entity("You are not logged in. Pease log in and make sure cookies are enabled.").build();
		
		return user.updateDOB(id,dob);
	}
	
	@PUT	
	@Path("company/{id}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response company(@HeaderParam("Authorization")String Authorization,@PathParam("id") int id, @FormParam("company") String company){
		if(!user.verify(Authorization))
			return Response.status(Status.BAD_REQUEST).type("text/plain").entity("You are not logged in. Pease log in and make sure cookies are enabled.").build();
		
		return user.updateCompany(id,company);
	}
	
	@PUT	
	@Path("title/{id}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response title(@HeaderParam("Authorization")String Authorization,@PathParam("id") int id, @FormParam("title") String title){
		if(!user.verify(Authorization))
			return Response.status(Status.BAD_REQUEST).type("text/plain").entity("You are not logged in. Pease log in and make sure cookies are enabled.").build();
		
		return user.updateTitle(id,title);
	}
	
	@PUT	
	@Path("website/{id}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response website(@HeaderParam("Authorization")String Authorization,@PathParam("id") int id, @FormParam("website") String website){
		if(!user.verify(Authorization))
			return Response.status(Status.BAD_REQUEST).type("text/plain").entity("You are not logged in. Pease log in and make sure cookies are enabled.").build();
		
		return user.updateWebsite(id,website);
	}
	
	@PUT	
	@Path("image/{id}")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response image(
			@HeaderParam("Authorization")String Authorization,
			@PathParam("id") int id,
			@FormDataParam("file") InputStream stream,
    		@FormDataParam("file") FormDataContentDisposition fileDetail
    		){
		if(!user.verify(Authorization))
			return Response.status(Status.BAD_REQUEST).type("text/plain").entity("You are not logged in. Pease log in and make sure cookies are enabled.").build();
		
		return user.updateImage(id,stream);
	}
	
	@PUT	
	@Path("realtime/{id}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response realtime_location(@HeaderParam("Authorization")String Authorization,@PathParam("id") int id, @FormParam("realtime_location") String realtime_location){
		if(!user.verify(Authorization))
			return Response.status(Status.BAD_REQUEST).type("text/plain").entity("You are not logged in. Pease log in and make sure cookies are enabled.").build();
		
		return user.updateLocation(id,realtime_location);
	}
}
