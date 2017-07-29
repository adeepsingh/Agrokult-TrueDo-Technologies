package com.truedo.agrokult.posts;

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
@Path("/post")
public class PostsController {
	
	@Autowired
	PostsService posts;
	
	@Autowired 
	LoginService user;
	
	@POST
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    public Response uploadFileWithData(
    		@HeaderParam("Authorization") String Authorization,
    		@FormDataParam("file") InputStream stream,
    		@FormDataParam("file") FormDataContentDisposition fileDetail,
    		@FormDataParam("user_id") int user_id,
    		@FormDataParam("post_title") String post_title,    		
    		@FormDataParam("category") String category,
    		@FormDataParam("content")String content)
    		
    {
		if(!user.verify(Authorization))
			return Response.status(Status.BAD_REQUEST).type("text/plain").entity("You are not logged in. Pease log in and make sure cookies are enabled.").build();
		return posts.addPost(user_id, post_title, category, stream, content);
    }
    
	
	@GET
	@Path("/image/{id}")
	@Produces("image/jpg")
	public Response getFullImage(@HeaderParam("Authorization")String Authorization,@PathParam("id") int id) {
			if(!user.verify(Authorization))
				return Response.status(Status.BAD_REQUEST).type("text/plain").entity("You are not logged in. Pease log in and make sure cookies are enabled.").build();
			return posts.getImage(id);
	}
	
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Posts PostById(@HeaderParam("Authorization")String Authorization,@PathParam("id") int id) {
		if(!user.verify(Authorization))
			return null;
		return posts.getPost(id);
	}
	
	@GET
	@Path("all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Posts> AllPosts(@HeaderParam("Authorization")String Authorization) {
		if(!user.verify(Authorization))
			return null;	
		return posts.getAllPosts();
	}
	

	@DELETE
	@Path("{id}")
	public Response remove(@HeaderParam("Authorization")String Authorization,@PathParam("id") int id){
		if(!user.verify(Authorization))
			return null;	
		return posts.delete(id);		
	}
	
	@GET
	@Path("category/{cat}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Posts> PostsByCategory(@HeaderParam("Authorization")String Authorization, @PathParam("cat") String cat) {
		if(!user.verify(Authorization))
			return null;	
		return posts.getPostsByCategory(cat);
	}
	
	@GET
	@Path("user/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Posts> PostsByUser(@HeaderParam("Authorization")String Authorization, @PathParam("id") int id) {
		if(!user.verify(Authorization))
			return null;	
		return posts.getPostsByUser(id);
	}
	
	@GET
	@Path("filter/{query}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Posts> PostsByFilter(@HeaderParam("Authorization")String Authorization, @PathParam("query") String query) {
		if(!user.verify(Authorization))
			return null;	
		System.out.println(query);
		return posts.getPostsByFilter(query);
	}
	
	@PUT	
	@Path("title/{id}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response title(@HeaderParam("Authorization")String Authorization,@PathParam("id") int id, @FormParam("title") String title){
		if(!user.verify(Authorization))
			return Response.status(Status.BAD_REQUEST).type("text/plain").entity("You are not logged in. Pease log in and make sure cookies are enabled.").build();
		
		return posts.updateTitle(id,title);
	}
	
	@PUT	
	@Path("category/{id}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response category(@HeaderParam("Authorization")String Authorization,@PathParam("id") int id, @FormParam("category") String category){
		if(!user.verify(Authorization))
			return Response.status(Status.BAD_REQUEST).type("text/plain").entity("You are not logged in. Pease log in and make sure cookies are enabled.").build();
		
		return posts.updateCategory(id,category);
	}
	
	@PUT	
	@Path("content/{id}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response content(@HeaderParam("Authorization")String Authorization,@PathParam("id") int id, @FormParam("content") String content){
		if(!user.verify(Authorization))
			return Response.status(Status.BAD_REQUEST).type("text/plain").entity("You are not logged in. Pease log in and make sure cookies are enabled.").build();
		
		return posts.updateContent(id,content);
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
		
		return posts.updateImage(id,stream);
	}
	
}
