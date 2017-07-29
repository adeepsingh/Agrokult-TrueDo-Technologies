package com.truedo.agrokult.ads;

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
public class AdsService  implements AdsServiceInterface{

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	
	public Response addAd(int user_id, String ad_title, String interest,InputStream stream, String content)
	{
		try
		{
			if(!AdsValidation.category(interest))
				return Response.status(Status.FORBIDDEN).type("text/plain").entity(AdsConstants.CategoryError).build();
			if(!AdsValidation.content(content))
				return Response.status(Status.FORBIDDEN).type("text/plain").entity(AdsConstants.ContentError).build();
			if(!AdsValidation.title(ad_title))
				return Response.status(Status.FORBIDDEN).type("text/plain").entity(AdsConstants.TitleError).build();
			
		
			jdbcTemplate.update(AdsConstants.insertSql, new Object[]{user_id,ad_title, interest,content});
			int ad_id=jdbcTemplate.queryForObject(AdsConstants.getRecentId,Integer.class);
			if(stream!=null)
				writeToFileWithOutputStream(stream,ad_id);
			
			String filepath=AdsConstants.image_path+(ad_id)+".jpg";
			jdbcTemplate.update(AdsConstants.insertFilePath, new Object[]{filepath,ad_id});
			
			return Response.status(200).entity(ad_id).build();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return Response.status(400).entity("Something Went Wrong").build();
		}
	}
	
	
	//saving image on a location
		private void writeToFileWithOutputStream(InputStream stream, int ad_id) {

        try {
            OutputStream out = new FileOutputStream(new File(AdsConstants.image_path+ad_id+".jpg"));
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
				String filepath=jdbcTemplate.queryForObject(AdsConstants.serveImage,new Object[]{id},String.class);
				BufferedImage image = ImageIO.read(new File(filepath));
				 
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
			    ImageIO.write(image, "jpg", baos);
			    byte[] imageData = baos.toByteArray();
			    	    
			     return Response.ok(new ByteArrayInputStream(imageData)).build();
			
			}
			catch(Exception e)
			{
				e.printStackTrace();
				return Response.status(404).type("text/plain").entity(AdsConstants.ImageNotFound).build();
			}
		}
		
		
		public Ads getAd(int id)
		 {
			 try
			{
				Ads lm=jdbcTemplate.queryForObject(AdsConstants.getAdDetails,new AdsMapper(),id);
				return lm;
			
			}catch(Exception e)
			{
				e.printStackTrace();
				return null;
			}
		 }
		

		 public List<Ads> getAllAds()
		 {
			 try
			{
				List<Ads> lm=jdbcTemplate.query(AdsConstants.AllAdsQuery,new AdsMapper());
				return lm;
			
			}catch(Exception e)
			{
				e.printStackTrace();
				return null;
			}
		 }
		 
		 public List<Ads> getAdsByCategory(String cat)
		 {
			 try
			{
				List<Ads> lm=jdbcTemplate.query(AdsConstants.getAdByCategory,new AdsMapper(),cat);
				return lm;
			
			}catch(Exception e)
			{
				e.printStackTrace();
				return null;
			}
		 }

		 
		 public List<Ads> getAdsByUser(int id)
		 {
			 try
			{
				List<Ads> lm=jdbcTemplate.query(AdsConstants.getAdByUser,new AdsMapper(),id);
				System.out.println(id);
				return lm;
			
			}catch(Exception e)
			{
				e.printStackTrace();
				return null;
			}
		 }
		 
		 
		 public List<Ads> getAdsByFilter(String query)
		 {
			 try
			{
				List<Ads> lm=jdbcTemplate.query(AdsConstants.getAdByQuery,new AdsMapper(),query);
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
				jdbcTemplate.update(AdsConstants.deleteUser,id);
				return Response.status(Status.OK).type("text/plain").entity("Ad deleted").build();
				
			}catch(Exception e)
			{
				e.printStackTrace();
				return Response.status(Status.BAD_REQUEST).type("text/plain").entity(AdsConstants.noUserFound).build();
			}
		 }
		 
		 public Response updateTitle(int id,String title)
		 {
			 try
			{
				if(!AdsValidation.title(title))
					return Response.status(Status.FORBIDDEN).type("text/plain").entity(AdsConstants.TitleError).build();	
				
				jdbcTemplate.update(AdsConstants.updateTitle,title,id);
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
				if(!AdsValidation.category(category))
					return Response.status(Status.FORBIDDEN).type("text/plain").entity(AdsConstants.CategoryError).build();	
				
				jdbcTemplate.update(AdsConstants.updateCategory,category,id);
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
				if(!AdsValidation.content(content))
					return Response.status(Status.FORBIDDEN).type("text/plain").entity(AdsConstants.ContentError).build();	
				
				jdbcTemplate.update(AdsConstants.updateCategory,content,id);
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
				 String location=AdsConstants.image_path+id+".jpg";
				 jdbcTemplate.update(AdsConstants.updateImagePath,location,id);
				 writeToFileWithOutputStream(stream,id);
				 return Response.status(Status.OK).build();
			}catch(Exception e)
			{
				e.printStackTrace();
				return Response.status(Status.BAD_REQUEST).type("text/plain").entity(AdsConstants.noUserFound).build();
			}
		 }
		
	
}
