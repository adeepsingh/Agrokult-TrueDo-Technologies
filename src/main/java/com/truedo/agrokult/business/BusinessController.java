package com.truedo.agrokult.business;


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

import com.truedo.agrokult.personal_login.LoginModel;
import com.truedo.agrokult.personal_login.LoginService;


@Component
@Path("/business")
public class BusinessController {
	
	@Autowired private BusinessService businessAPIService;	
	@Autowired private LoginService user;	
	
	
	
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response add(
			@HeaderParam("Authorization") String Authorization,
			@FormDataParam("file") InputStream stream,
    		@FormDataParam("file") FormDataContentDisposition fileDetail,
    		@FormDataParam("company_name") final String Company_Name,
    		@FormDataParam("services") final String Services_Rendered,
    		@FormDataParam("region") final String Region,
    		@FormDataParam("interests") final String Interest,
    		@FormDataParam("message") final String Company_Message,
    		@FormDataParam("address") final String Company_Address,
    		@FormDataParam("website") final String Website
    		)
	{	
		if(!user.verify(Authorization))
			return Response.status(Status.BAD_REQUEST).type("text/plain").entity("You are not logged in. Pease log in and make sure cookies are enabled.").build();
		return businessAPIService.save(stream,Company_Name, Services_Rendered, Region, Interest, Company_Message, Company_Address, Website);
	}
	
	@GET
	@Path("/image/{id}")
	@Produces("image/jpg")
	public Response getFullImage(@HeaderParam("Authorization")String Authorization,@PathParam("id") int id) {
			if(!user.verify(Authorization))
				return Response.status(Status.BAD_REQUEST).type("text/plain").entity("You are not logged in. Pease log in and make sure cookies are enabled.").build();
			return businessAPIService.getImage(id);
	}
	
	@GET
	@Path("all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Business> getAll(@HeaderParam("Authorization") String Authorization){
		if(!user.verify(Authorization))
			return null;
		return this.businessAPIService.findAll();
	}
	
	
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Business getOne(@HeaderParam("Authorization") String Authorization,@PathParam("id") int Company_Id){
		if(!user.verify(Authorization))
			return null;
		return (Business)this.businessAPIService.findOne(Company_Id);
	}
	
	@GET
	@Path("users/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<LoginModel> getUsers(@HeaderParam("Authorization") String Authorization,@PathParam("id") int Company_Id){
		if(!user.verify(Authorization))
			return null;
		return businessAPIService.findUsers(Company_Id);
	}
	
	
	@DELETE
	@Path("/{id}")
	public Response remove(@HeaderParam("Authorization") String Authorization,@PathParam("Company_Id") int Company_Id){
		if(!user.verify(Authorization))
			return null;
		return this.businessAPIService.delete(Company_Id);		
	}
	
	
	@PUT	
	@Path("name/{id}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response name(@HeaderParam("Authorization")String Authorization,@PathParam("id") int id, @FormParam("name") String name){
		if(!user.verify(Authorization))
			return Response.status(Status.BAD_REQUEST).type("text/plain").entity("You are not logged in. Pease log in and make sure cookies are enabled.").build();
		
		return businessAPIService.updateName(id,name);
	}
	
	@PUT	
	@Path("services/{id}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response services(@HeaderParam("Authorization")String Authorization,@PathParam("id") int id, @FormParam("services") String services){
		if(!user.verify(Authorization))
			return Response.status(Status.BAD_REQUEST).type("text/plain").entity("You are not logged in. Pease log in and make sure cookies are enabled.").build();
		
		return businessAPIService.updateServices(id,services);
	}
	
	@PUT	
	@Path("region/{id}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response region(@HeaderParam("Authorization")String Authorization,@PathParam("id") int id, @FormParam("region") String region){
		if(!user.verify(Authorization))
			return Response.status(Status.BAD_REQUEST).type("text/plain").entity("You are not logged in. Pease log in and make sure cookies are enabled.").build();
		
		return businessAPIService.updateRegion(id,region);
	}
	
	@PUT	
	@Path("interests/{id}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response interests(@HeaderParam("Authorization")String Authorization,@PathParam("id") int id, @FormParam("interests") String interests){
		if(!user.verify(Authorization))
			return Response.status(Status.BAD_REQUEST).type("text/plain").entity("You are not logged in. Pease log in and make sure cookies are enabled.").build();
		
		return businessAPIService.updateInterests(id,interests);
	}
	
	@PUT	
	@Path("message/{id}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response message(@HeaderParam("Authorization")String Authorization,@PathParam("id") int id, @FormParam("message") String message){
		if(!user.verify(Authorization))
			return Response.status(Status.BAD_REQUEST).type("text/plain").entity("You are not logged in. Pease log in and make sure cookies are enabled.").build();
		
		return businessAPIService.updateMessage(id,message);
	}
	
	@PUT	
	@Path("address/{id}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response address(@HeaderParam("Authorization")String Authorization,@PathParam("id") int id, @FormParam("address") String address){
		if(!user.verify(Authorization))
			return Response.status(Status.BAD_REQUEST).type("text/plain").entity("You are not logged in. Pease log in and make sure cookies are enabled.").build();
		
		return businessAPIService.updateAddress(id,address);
	}
	
	@PUT	
	@Path("website/{id}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response website(@HeaderParam("Authorization")String Authorization,@PathParam("id") int id, @FormParam("website") String website){
		if(!user.verify(Authorization))
			return Response.status(Status.BAD_REQUEST).type("text/plain").entity("You are not logged in. Pease log in and make sure cookies are enabled.").build();
		
		return businessAPIService.updateWebsite(id,website);
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
		return businessAPIService.updateImage(id,stream);
	}
	
}