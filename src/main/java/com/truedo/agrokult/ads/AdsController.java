package com.truedo.agrokult.ads;

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
import com.truedo.agrokult.personal_login.LoginService;


@Component
@Path("/ad")
public class AdsController {
	
	@Autowired
	AdsService ads;
	
	@Autowired 
	LoginService user;
	
	@POST
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    public Response uploadFileWithData(
    		@HeaderParam("Authorization") String Authorization,
    		@FormDataParam("file") InputStream stream,
    		@FormDataParam("file") FormDataContentDisposition fileDetail,
    		@FormDataParam("user_id") int user_id,
    		@FormDataParam("ad_title") String ad_title,    		
    		@FormDataParam("category") String category,
    		@FormDataParam("content")String content)
    		
    {
		if(!user.verify(Authorization))
			return Response.status(Status.BAD_REQUEST).type("text/plain").entity("You are not logged in. Pease log in and make sure cookies are enabled.").build();
		return ads.addAd(user_id, ad_title, category, stream, content);
    }
    
	
	@GET
	@Path("/image/{id}")
	@Produces("image/jpg")
	public Response getFullImage(@HeaderParam("Authorization")String Authorization,@PathParam("id") int id) {
			if(!user.verify(Authorization))
				return Response.status(Status.BAD_REQUEST).type("text/plain").entity("You are not logged in. Pease log in and make sure cookies are enabled.").build();
			return ads.getImage(id);
	}
	
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Ads AdById(@HeaderParam("Authorization")String Authorization,@PathParam("id") int id) {
		if(!user.verify(Authorization))
			return null;
		return ads.getAd(id);
	}
	
	@GET
	@Path("all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Ads> AllAds(@HeaderParam("Authorization")String Authorization) {
		if(!user.verify(Authorization))
			return null;	
		return ads.getAllAds();
	}
	

	@DELETE
	@Path("{id}")
	public Response remove(@HeaderParam("Authorization")String Authorization,@PathParam("id") int id){
		if(!user.verify(Authorization))
			return null;	
		return ads.delete(id);		
	}
	
	@GET
	@Path("category/{cat}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Ads> AdsByCategory(@HeaderParam("Authorization")String Authorization, @PathParam("cat") String cat) {
		if(!user.verify(Authorization))
			return null;	
		return ads.getAdsByCategory(cat);
	}
	
	@GET
	@Path("user/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Ads> AdsByUser(@HeaderParam("Authorization")String Authorization, @PathParam("id") int id) {
		if(!user.verify(Authorization))
			return null;	
		return ads.getAdsByUser(id);
	}
	
	@GET
	@Path("filter/{query}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Ads> AdsByFilter(@HeaderParam("Authorization")String Authorization, @PathParam("query") String query) {
		if(!user.verify(Authorization))
			return null;	
		System.out.println(query);
		return ads.getAdsByFilter(query);
	}
	
	@PUT	
	@Path("title/{id}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response title(@HeaderParam("Authorization")String Authorization,@PathParam("id") int id, @FormParam("title") String title){
		if(!user.verify(Authorization))
			return Response.status(Status.BAD_REQUEST).type("text/plain").entity("You are not logged in. Pease log in and make sure cookies are enabled.").build();
		
		return ads.updateTitle(id,title);
	}
	
	@PUT	
	@Path("category/{id}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response category(@HeaderParam("Authorization")String Authorization,@PathParam("id") int id, @FormParam("category") String category){
		if(!user.verify(Authorization))
			return Response.status(Status.BAD_REQUEST).type("text/plain").entity("You are not logged in. Pease log in and make sure cookies are enabled.").build();
		
		return ads.updateCategory(id,category);
	}
	
	@PUT	
	@Path("content/{id}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response content(@HeaderParam("Authorization")String Authorization,@PathParam("id") int id, @FormParam("content") String content){
		if(!user.verify(Authorization))
			return Response.status(Status.BAD_REQUEST).type("text/plain").entity("You are not logged in. Pease log in and make sure cookies are enabled.").build();
		
		return ads.updateContent(id,content);
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
		
		return ads.updateImage(id,stream);
	}
	
}
