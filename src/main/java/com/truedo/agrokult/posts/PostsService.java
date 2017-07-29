package com.truedo.agrokult.posts;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.imageio.ImageIO;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


@Component
public class PostsService implements PostsServiceInterface{

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	
	public Response addPost(int user_id, String post_title, String interest,InputStream stream, String content)
	{
		try
		{
			if(!PostsValidation.category(interest))
				return Response.status(Status.FORBIDDEN).type("text/plain").entity(PostsConstants.CategoryError).build();
			if(!PostsValidation.content(content))
				return Response.status(Status.FORBIDDEN).type("text/plain").entity(PostsConstants.ContentError).build();
			if(!PostsValidation.title(post_title))
				return Response.status(Status.FORBIDDEN).type("text/plain").entity(PostsConstants.TitleError).build();
			
		
			jdbcTemplate.update(PostsConstants.insertSql, new Object[]{user_id,post_title, interest,content});
			int post_id=jdbcTemplate.queryForObject(PostsConstants.getRecentId,Integer.class);
			if(stream!=null)
				writeToFileWithOutputStream(stream,post_id);
			
			String filepath=PostsConstants.image_path+(post_id)+".jpg";
			jdbcTemplate.update(PostsConstants.insertFilePath, new Object[]{filepath,post_id});
			
			return Response.status(200).entity(post_id).build();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return Response.status(400).entity("Something Went Wrong").build();
		}
	}
	
	
	//saving image on a location
		private void writeToFileWithOutputStream(InputStream stream, int post_id) {

        try {
            OutputStream out = new FileOutputStream(new File(PostsConstants.image_path+post_id+".jpg"));
            int read = 0;
            byte[] bytes = new byte[1024];
            	
            while ((read = stream.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.flush();
            out.close();
            
        } catch (IOException e) {

            e.printStackTrace();
            
        }
    }
		
		//serving images to url
		public Response getImage(int id)
		{
			try
			{
				String filepath=jdbcTemplate.queryForObject(PostsConstants.serveImage,new Object[]{id},String.class);
				BufferedImage image = ImageIO.read(new File(filepath));
				 
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
			    ImageIO.write(image, "jpg", baos);
			    byte[] imageData = baos.toByteArray();
			    	    
			     return Response.ok(new ByteArrayInputStream(imageData)).build();
			
			}
			catch(Exception e)
			{
				e.printStackTrace();
				return Response.status(404).type("text/plain").entity(PostsConstants.ImageNotFound).build();
			}
		}
		
		
		public Posts getPost(int id)
		 {
			 try
			{
				Posts lm=jdbcTemplate.queryForObject(PostsConstants.getPostDetails,new PostsMapper(),id);
				return lm;
			
			}catch(Exception e)
			{
				e.printStackTrace();
				return null;
			}
		 }
		

		 public List<Posts> getAllPosts()
		 {
			 try
			{
				List<Posts> lm=jdbcTemplate.query(PostsConstants.AllPostsQuery,new PostsMapper());
				return lm;
			
			}catch(Exception e)
			{
				e.printStackTrace();
				return null;
			}
		 }
		 
		 public List<Posts> getPostsByCategory(String cat)
		 {
			 try
			{
				List<Posts> lm=jdbcTemplate.query(PostsConstants.getPostByCategory,new PostsMapper(),cat);
				return lm;
			
			}catch(Exception e)
			{
				e.printStackTrace();
				return null;
			}
		 }

		 
		 public List<Posts> getPostsByUser(int id)
		 {
			 try
			{
				List<Posts> lm=jdbcTemplate.query(PostsConstants.getPostByUser,new PostsMapper(),id);
				System.out.println(id);
				return lm;
			
			}catch(Exception e)
			{
				e.printStackTrace();
				return null;
			}
		 }
		 
		 
		 public List<Posts> getPostsByFilter(String query)
		 {
			 try
			{
				List<Posts> lm=jdbcTemplate.query(PostsConstants.getPostByQuery,new PostsMapper(),query);
				return lm;
			
			}catch(Exception e)
			{
				e.printStackTrace();
				return null;
			}
		 }
		 
		 public Response delete(int id)
		 {
			try
			{
				jdbcTemplate.update(PostsConstants.deleteUser,id);
				return Response.status(Status.OK).type("text/plain").entity("Post deleted").build();
				
			}catch(Exception e)
			{
				e.printStackTrace();
				return Response.status(Status.BAD_REQUEST).type("text/plain").entity(PostsConstants.noUserFound).build();
			}
		 }
		 
		 public Response updateTitle(int id,String title)
		 {
			 try
			{
				if(!PostsValidation.title(title))
					return Response.status(Status.FORBIDDEN).type("text/plain").entity(PostsConstants.TitleError).build();	
				
				jdbcTemplate.update(PostsConstants.updateTitle,title,id);
				return Response.status(Status.OK).build();
					
			}catch(Exception e)
			{
				e.printStackTrace();
				return Response.status(Status.BAD_REQUEST).build();
			}
		 }
		 
		 public Response updateCategory(int id,String category)
		 {
			 try
			{
				if(!PostsValidation.category(category))
					return Response.status(Status.FORBIDDEN).type("text/plain").entity(PostsConstants.CategoryError).build();	
				
				jdbcTemplate.update(PostsConstants.updateCategory,category,id);
				return Response.status(Status.OK).build();
					
			}catch(Exception e)
			{
				e.printStackTrace();
				return Response.status(Status.BAD_REQUEST).build();
			}
		 }
		 
		 public Response updateContent(int id,String content)
		 {
			 try
			{
				if(!PostsValidation.content(content))
					return Response.status(Status.FORBIDDEN).type("text/plain").entity(PostsConstants.ContentError).build();	
				
				jdbcTemplate.update(PostsConstants.updateCategory,content,id);
				return Response.status(Status.OK).build();
					
			}catch(Exception e)
			{
				e.printStackTrace();
				return Response.status(Status.BAD_REQUEST).build();
			}
		 }
		 
		 public Response updateImage(int id,InputStream stream)
		 {
			 try
			{
				 String location=PostsConstants.image_path+id+".jpg";
				 jdbcTemplate.update(PostsConstants.updateImagePath,location,id);
				 writeToFileWithOutputStream(stream,id);
				 return Response.status(Status.OK).build();
			}catch(Exception e)
			{
				e.printStackTrace();
				return Response.status(Status.BAD_REQUEST).type("text/plain").entity(PostsConstants.noUserFound).build();
			}
		 }
		
	
}
