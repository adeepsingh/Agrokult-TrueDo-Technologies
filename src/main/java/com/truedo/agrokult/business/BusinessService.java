package com.truedo.agrokult.business;


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
import com.truedo.agrokult.personal_login.LoginMapper;
import com.truedo.agrokult.personal_login.LoginModel;

@Component
public class BusinessService {
		
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public Response save(InputStream stream, String Company_Name,String Services_Rendered, String Region, String Interests, String Company_Message, String Company_Address, String Website)
	{
		try
		{
			
			if(!BusinessValidation.message(Company_Message))
				return Response.status(Status.FORBIDDEN).type("text/plain").entity(BusinessConstants.messageError).build();
			if(!BusinessValidation.website(Website))
				return Response.status(Status.FORBIDDEN).type("text/plain").entity(BusinessConstants.websiteError).build();
			if(!BusinessValidation.company_name(Company_Name))
				return Response.status(Status.FORBIDDEN).type("text/plain").entity(BusinessConstants.companyNameError).build();
			if(!BusinessValidation.address(Company_Address))
				return Response.status(Status.FORBIDDEN).type("text/plain").entity(BusinessConstants.addressError).build();
			
			jdbcTemplate.update(BusinessConstants.add,new Object[]{Company_Name, Services_Rendered, Region, Interests, Company_Message, Company_Address, Website});
			int business_id=jdbcTemplate.queryForObject(BusinessConstants.getBusinessId,Integer.class);

			if(stream!=null)
				writeToFileWithOutputStream(stream,business_id);
			
			String filepath=BusinessConstants.image_path+(business_id)+".jpg";
			jdbcTemplate.update(BusinessConstants.insertFilePath, new Object[]{filepath,business_id});
			return Response.status(200).entity(business_id).build();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return Response.status(400).entity("Something went wrong").build();
		}
	}
	

	private void writeToFileWithOutputStream(InputStream stream, int business_id) {

	    try {
	        OutputStream out = new FileOutputStream(new File(BusinessConstants.image_path+business_id+".jpg"));
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
			String filepath=jdbcTemplate.queryForObject(BusinessConstants.serveImage,new Object[]{id},String.class);
			BufferedImage image = ImageIO.read(new File(filepath));
			 
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
		    ImageIO.write(image, "jpg", baos);
		    byte[] imageData = baos.toByteArray();
		    	    
		     return Response.ok(new ByteArrayInputStream(imageData)).build();
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return Response.status(404).type("text/plain").entity("Image Not Found").build();
		}
	}
	
	
	 public Business findOne(int id)
	 {
		 try
		{
			Business lm = jdbcTemplate.queryForObject(BusinessConstants.get,new BusinessMapper(),id);
			return lm;
		
		}catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	 }
	 
	 
	
	 
	 
	 public List<Business> findAll()
	 {
		 try
		{
			List<Business> lm = jdbcTemplate.query(BusinessConstants.getall,new BusinessMapper());
			return lm;
		
		}catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	 }
	 
	 public List<LoginModel> findUsers(int id)
	 {
		 try
		{
			String company_name=jdbcTemplate.queryForObject("Select company_name from business where business_id=?",new Object[]{id} ,String.class);
			List<LoginModel> lm = jdbcTemplate.query(BusinessConstants.getUsers,new LoginMapper(),company_name);
			return lm;
		
		}catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	 }
	 
	 
	 
	 public Response delete(int Company_Id)
	 {
		try
		{
			jdbcTemplate.update(BusinessConstants.delete, Company_Id);
			return Response.status(Status.OK).entity("Business entry deleted").build();
			
		}catch(Exception e)
		{
			e.printStackTrace();
			return Response.status(Status.BAD_REQUEST).build();
		}
	 }
	 
	 
	 public Response updateName(int id,String name)
	 {
		 try
		{
			if(!BusinessValidation.company_name(name))
				return Response.status(Status.FORBIDDEN).type("text/plain").entity(BusinessConstants.companyNameError).build();	
			
			jdbcTemplate.update(BusinessConstants.updateCompanyName,name,id);
			return Response.status(Status.OK).build();
				
		}catch(Exception e)
		{
			e.printStackTrace();
			return Response.status(Status.BAD_REQUEST).build();
		}
	 }
	 
	 
	 public Response updateServices(int id,String services)
	 {
		 try
		{
			jdbcTemplate.update(BusinessConstants.updateService,services,id);
			return Response.status(Status.OK).build();
				
		}catch(Exception e)
		{
			e.printStackTrace();
			return Response.status(Status.BAD_REQUEST).build();
		}
	 }
	 
	 public Response updateRegion(int id,String region)
	 {
		 try
		{
			jdbcTemplate.update(BusinessConstants.updateRegion,region,id);
			return Response.status(Status.OK).build();
				
		}catch(Exception e)
		{
			e.printStackTrace();
			return Response.status(Status.BAD_REQUEST).build();
		}
	 }
	 

	 public Response updateInterests(int id,String interests)
	 {
		 try
		{
			jdbcTemplate.update(BusinessConstants.updateInterests,interests,id);
			return Response.status(Status.OK).build();
				
		}catch(Exception e)
		{
			e.printStackTrace();
			return Response.status(Status.BAD_REQUEST).build();
		}
	 }
	 
	 public Response updateMessage(int id,String message)
	 {
		 try
		{
			if(!BusinessValidation.message(message))
				return Response.status(Status.FORBIDDEN).type("text/plain").entity(BusinessConstants.messageError).build();	
			
			jdbcTemplate.update(BusinessConstants.updateMessage,message,id);
			return Response.status(Status.OK).build();
				
		}catch(Exception e)
		{
			e.printStackTrace();
			return Response.status(Status.BAD_REQUEST).build();
		}
	 }
	 
	 public Response updateAddress(int id,String address)
	 {
		 try
		{
			if(!BusinessValidation.address(address))
				return Response.status(Status.FORBIDDEN).type("text/plain").entity(BusinessConstants.addressError).build();	
			
			jdbcTemplate.update(BusinessConstants.updateAddress,address,id);
			return Response.status(Status.OK).build();
				
		}catch(Exception e)
		{
			e.printStackTrace();
			return Response.status(Status.BAD_REQUEST).build();
		}
	 }
	 
	 public Response updateWebsite(int id,String website)
	 {
		 try
		{
			if(!BusinessValidation.website(website))
				return Response.status(Status.FORBIDDEN).type("text/plain").entity(BusinessConstants.websiteError).build();	
			
			jdbcTemplate.update(BusinessConstants.updateAddress,website,id);
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
			 String location=BusinessConstants.image_path+id+".jpg";
			 jdbcTemplate.update(BusinessConstants.updateImagePath,location,id);
			 writeToFileWithOutputStream(stream,id);
			 return Response.status(Status.OK).build();
		}catch(Exception e)
		{
			e.printStackTrace();
			return Response.status(Status.BAD_REQUEST).type("text/plain").entity(BusinessConstants.noUserFound).build();
		}
	 }
	
}