package com.truedo.agrokult.posts;

import java.io.InputStream;
import java.util.List;

import javax.ws.rs.core.Response;

public interface PostsServiceInterface {

	
	public Response addPost(int user_id, String post_title, String interest,InputStream stream, String content);
	
	public Response getImage(int id);
	public Posts getPost(int id);
	public List<Posts> getAllPosts();
	 public List<Posts> getPostsByUser(int id);
	 public List<Posts> getPostsByFilter(String query);
	 public List<Posts> getPostsByCategory(String cat);
	 
	 public Response delete(int id);
	 
	 public Response updateTitle(int id,String title);		
	 public Response updateCategory(int id,String category);
	 public Response updateContent(int id,String content);
	 public Response updateImage(int id,InputStream stream);
									
	
		
}
