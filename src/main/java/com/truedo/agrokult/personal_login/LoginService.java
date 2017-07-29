package com.truedo.agrokult.personal_login;

import javax.imageio.ImageIO;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.truedo.agrokult.CreateJsonToken;
import com.truedo.agrokult.interest.InterestMapper;
import com.truedo.agrokult.interest.Interests;
import com.truedo.agrokult.personal_login.LoginServiceInterface;

import io.jsonwebtoken.Jwts;


@Component
public class LoginService implements LoginServiceInterface{
	@Autowired
	JdbcTemplate jdbcTemplate;

	
	
	//get current time and date
	private Timestamp getTimestamp()
	{
		java.util.Date date=new java.util.Date();
		return (new Timestamp(date.getTime()));
	}
		
		
		
		
		
	// for inserting a new user
	public Response addUser(InputStream stream, String mail_id, String pass, String first_name, String middle_name, String last_name, String address, String dob, String company, String title, String website, String realtime_location,String phone, List<String> interests)
	{
		try
		{
			
			//validate the fields
			if(!LoginValidate.name(first_name))
				return Response.status(Status.FORBIDDEN).type("text/plain").entity(LoginConstants.firstNameError).build();
			if(!LoginValidate.name(last_name))
				return Response.status(Status.FORBIDDEN).type("text/plain").entity(LoginConstants.lastNameError).build();
			if(!LoginValidate.name(company))
				return Response.status(Status.FORBIDDEN).type("text/plain").entity(LoginConstants.companyError).build();
			
			if(!LoginValidate.password(pass))
				return Response.status(Status.FORBIDDEN).type("text/plain").entity(LoginConstants.passwordLengthError).build();
			if(!LoginValidate.mail_id(mail_id))
				return Response.status(Status.FORBIDDEN).type("text/plain").entity(LoginConstants.mailIdError).build();
			if(!LoginValidate.address(address))
				return Response.status(Status.FORBIDDEN).type("text/plain").entity(LoginConstants.addressError).build();
			if(!LoginValidate.phone(phone))
				return Response.status(Status.FORBIDDEN).type("text/plain").entity(LoginConstants.phoneError).build();
			if(!LoginValidate.website(website))
				return Response.status(Status.FORBIDDEN).type("text/plain").entity(LoginConstants.websiteError).build();
			if(!LoginValidate.dob(dob))
				return Response.status(Status.FORBIDDEN).type("text/plain").entity(LoginConstants.dateOfBirth).build();
			
			//check if a user entry already exists with inactive status (status=0). if so ,delete it and make a new entry
			int count=jdbcTemplate.queryForObject(LoginConstants.checkSql,new Object[]{mail_id},Integer.class);
			if(count!=0)
			{
				jdbcTemplate.update(LoginConstants.refreshSql,mail_id);
			}
			
			//hashed the password using BCrypt
			String password=PasswordEncrypt.hash(pass);
			
			//parsing date string to sql date
			SimpleDateFormat sdf1 = new SimpleDateFormat("MM-dd-yyyy");
			java.util.Date date = sdf1.parse(dob);
			Date sqlStartDate = new Date(date.getTime());
			String image_path=LoginConstants.image_path+phone+".jpg";
			
			//save to a particular location. See login constants class to check the location
			if(!writeToFileWithOutputStream(stream,phone))
				return Response.status(Status.NOT_ACCEPTABLE).type("text/plain").entity("Image not found").build();
			
			
			jdbcTemplate.update(LoginConstants.insertSql, new Object[]{mail_id,phone,password,first_name,middle_name,last_name,address,sqlStartDate,company,title,website,image_path,getTimestamp(),getTimestamp(),realtime_location});
			
			
			//use id and authorization token and send as response
			int id=jdbcTemplate.queryForObject(LoginConstants.getRecentId,Integer.class);
			
			//ADD interests to interests table
			for (String interest: interests) 
				addInterest(id,interest);
			
			return Response.status(Status.CREATED).type("text/plain").entity(id).header("Authorization", CreateJsonToken.addAuthentication(mail_id,phone,password)).build();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return Response.status(Status.CONFLICT).type("text/plain").entity(LoginConstants.AlreadyRegistered).build();
		}
	}
	
	
	
	public Response addInterest(int id, String interest)
	{
		try
		{
			jdbcTemplate.update(LoginConstants.addInterest,id,interest);
			return Response.status(Status.OK).build();
		}
		catch(Exception e)
		{
			return Response.status(Status.BAD_REQUEST).build();
		}
	}
	
	
	
	
	
	
	
	public Response login(String username, String password)
	{
		try
		{
			
			int count=jdbcTemplate.queryForObject(LoginConstants.FindUser,new Object[]{username,username},Integer.class);
			if(count==0)
				return Response.status(404).type("text/plain").entity("Username not valid").build();
			else
			{
				String pass=jdbcTemplate.queryForObject(LoginConstants.ObtainSavedPassword,new Object[]{username,username},String.class);
				BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
				if(passwordEncoder.matches(password, pass))
				{
					int id=jdbcTemplate.queryForObject(LoginConstants.find_user_id,new Object[]{username,username},Integer.class);
					String mail_id=jdbcTemplate.queryForObject(LoginConstants.find_mail_id,new Object[]{id},String.class);
					String phone=jdbcTemplate.queryForObject(LoginConstants.find_phone_no,new Object[]{id},String.class);
					jdbcTemplate.update(LoginConstants.updateTimestamp,getTimestamp(),id);
					return Response.status(Status.ACCEPTED).header("Authorization",CreateJsonToken.addAuthentication(mail_id,phone,pass)).type("text/plain").entity(id).build();
				}
				else
				{
					return Response.status(Status.FORBIDDEN).type("text/plain").entity(LoginConstants.passwordMatchError).build();
				}
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	
	
	
	//saving image on a location
	private boolean writeToFileWithOutputStream(InputStream stream, String phone_no) {

        try {
            OutputStream out = new FileOutputStream(new File(LoginConstants.image_path+phone_no+".jpg"));
            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = stream.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.flush();
            out.close();
            return true;
        } catch (IOException e) {

            e.printStackTrace();
            return false;
        }
    }
	
	
	
	
	//serving images to url
	public Response getImage(int id)
	{
		try
		{
			String filepath=jdbcTemplate.queryForObject(LoginConstants.serveImage,new Object[]{id},String.class);
			BufferedImage image = ImageIO.read(new File(filepath));
			 
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
		    ImageIO.write(image, "jpg", baos);
		    byte[] imageData = baos.toByteArray();
		    	    // uncomment line below to send streamed
		     return Response.ok(new ByteArrayInputStream(imageData)).build();
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return Response.status(404).type("text/plain").entity(LoginConstants.ImageNotFound).build();
		}
	}
	
	
	
	
	
	
	
	
	 public LoginModel getUser(int id)
	 {
		 try
		{
			LoginModel lm=jdbcTemplate.queryForObject(LoginConstants.getUserDetails,new LoginMapper(),id);
			return lm;
		
		}catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	 }
	 
	 
	 public List<LoginModel> getAllUsers()
	 {
		 try
		{
			List<LoginModel> lm=jdbcTemplate.query(LoginConstants.AllUsersQuery,new LoginMapper());
			return lm;
		
		}catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	 }
	 
	 
	 public Interests getInterest(int id)
	 {
		 try
		 {
			 List<String> interests=jdbcTemplate.query("Select interest from interests where user_id=?", new Object[]{id},new InterestMapper());
			 Interests i=new Interests();
			 i.setUser_id(id);
			 i.setInterest(interests);
			 return i;
		 }
		 catch(Exception e)
		 {
			 e.printStackTrace();
			 return null;
		 }
		 
	 }
	 
	 
	 
	 
	 public Response delete(int id)
	 {
		try
		{
			jdbcTemplate.update(LoginConstants.deleteUser,id);
			return Response.status(Status.OK).type("text/plain").entity("User deleted").build();
			
		}catch(Exception e)
		{
			e.printStackTrace();
			return Response.status(Status.BAD_REQUEST).type("text/plain").entity(LoginConstants.noUserFound).build();
		}
	 }
	 
	 
	 public Response deleteInterest(int id, String interest)
		{
			try
			{
				jdbcTemplate.update(LoginConstants.deleteInterest,id,interest);
				return Response.status(Status.OK).type("text/plain").entity("Deleted").build();
			}
			catch(Exception e)
			{
				return Response.status(Status.BAD_REQUEST).type("text/plain").entity("Not Found").build();
			}
		}
	 
	 
	 
	 
	 
	
	 
	 
	 
	 //Possible security violation
	 public Response updatePassword(int id,String password)
	 {
		 try
		{
			if(!LoginValidate.password(password))
				return Response.status(Status.FORBIDDEN).type("text/plain").entity(LoginConstants.passwordLengthError).build();	
			password=PasswordEncrypt.hash(password);
			jdbcTemplate.update(LoginConstants.updatePassword,password,id);
			return Response.status(Status.OK).build();
				
		}catch(Exception e)
		{
			e.printStackTrace();
			return Response.status(Status.BAD_REQUEST).build();
		}
	 }
	 
	 
	 
	 
	 
	 
	 
	 
	 public Response updateName(int id,String first_name, String middle_name, String last_name)
	 {
		 try
		{
			if(!LoginValidate.name(first_name))
				return Response.status(Status.FORBIDDEN).type("text/plain").entity(LoginConstants.firstNameError).build();	
			
			if(!LoginValidate.name(last_name))
				return Response.status(Status.FORBIDDEN).type("text/plain").entity(LoginConstants.lastNameError).build();	
			
			jdbcTemplate.update(LoginConstants.updateName,first_name,middle_name,last_name,id);
			return Response.status(Status.OK).build();
				
		}catch(Exception e)
		{
			e.printStackTrace();
			return Response.status(Status.BAD_REQUEST).build();
		}
	 }
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 public Response updateNumber(int id,String number)
	 {
		 try
		{
			if(!LoginValidate.phone(number))
				return Response.status(Status.FORBIDDEN).type("text/plain").entity(LoginConstants.phoneError).build();	
			
			jdbcTemplate.update(LoginConstants.updateNumber,number,id);
			return Response.status(Status.OK).build();
				
		}catch(Exception e)
		{
			e.printStackTrace();
			return Response.status(Status.BAD_REQUEST).type("text/plain").entity(LoginConstants.PhoneNoConflict).build();
		}
	 }
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 public Response updateAddress(int id,String address)
	 {
		 try
		{
			if(!LoginValidate.address(address))
				return Response.status(Status.FORBIDDEN).type("text/plain").entity(LoginConstants.addressError).build();	
			
			jdbcTemplate.update(LoginConstants.updateAddress,address,id);
			return Response.status(Status.OK).build();
				
		}catch(Exception e)
		{
			e.printStackTrace();
			return Response.status(Status.BAD_REQUEST).type("text/plain").entity(LoginConstants.noUserFound).build();
		}
	 }
	 
	 
	 
	 
	 
	 
	 
	 
	 public Response updateDOB(int id,String dob)
	 {
		 try
		{
			if(!LoginValidate.dob(dob))
				return Response.status(Status.FORBIDDEN).type("text/plain").entity(LoginConstants.dateOfBirth).build();	
			
			SimpleDateFormat sdf1 = new SimpleDateFormat("MM-dd-yyyy");
			java.util.Date date = sdf1.parse(dob);
			Date sqlStartDate = new Date(date.getTime());
			jdbcTemplate.update(LoginConstants.updateDOB,sqlStartDate,id);
			return Response.status(Status.OK).build();
				
		}catch(Exception e)
		{
			e.printStackTrace();
			return Response.status(Status.BAD_REQUEST).type("text/plain").entity(LoginConstants.noUserFound).build();
		}
	 }
	 
	 
	 
	 
	 
	 
	 
	 
	 public Response updateCompany(int id,String company)
	 {
		 try
		{
			if(!LoginValidate.name(company))
				return Response.status(Status.FORBIDDEN).type("text/plain").entity(LoginConstants.companyError).build();	
			
			jdbcTemplate.update(LoginConstants.updateCompany,company,id);
			return Response.status(Status.OK).build();
				
		}catch(Exception e)
		{
			e.printStackTrace();
			return Response.status(Status.BAD_REQUEST).type("text/plain").entity(LoginConstants.noUserFound).build();
		}
	 }
	 
	 
	 
	 
	 
	 
	 
	 
	 public Response updateTitle(int id,String title)
	 {
		 try
		{
			jdbcTemplate.update(LoginConstants.updateTitle,title,id);
			return Response.status(Status.OK).build();
				
		}catch(Exception e)
		{
			e.printStackTrace();
			return Response.status(Status.BAD_REQUEST).type("text/plain").entity(LoginConstants.noUserFound).build();
		}
	 }
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 public Response updateWebsite(int id,String website)
	 {
		 try
		{
			 if(!LoginValidate.website(website))
					return Response.status(Status.FORBIDDEN).type("text/plain").entity(LoginConstants.websiteError).build();	
			
			 jdbcTemplate.update(LoginConstants.updateWebsite,website,id);
			 return Response.status(Status.OK).build();
				
		}catch(Exception e)
		{
			e.printStackTrace();
			return Response.status(Status.BAD_REQUEST).type("text/plain").entity(LoginConstants.noUserFound).build();
		}
	 }
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 public Response updateLocation(int id,String location)
	 {
		 try
		{
			 jdbcTemplate.update(LoginConstants.updateWebsite,location,id);
			 return Response.status(Status.OK).build();
				
		}catch(Exception e)
		{
			e.printStackTrace();
			return Response.status(Status.BAD_REQUEST).type("text/plain").entity(LoginConstants.noUserFound).build();
		}
	 }
	 
	 
	 
	 
	 
	 
	 
	 
	 public Response updateImage(int id,InputStream stream)
	 {
		 try
		{
			 String phone_no=jdbcTemplate.queryForObject(LoginConstants.find_phone_no,new Object[]{id},String.class);
			 String location=LoginConstants.image_path+phone_no+".jpg";
			 jdbcTemplate.update(LoginConstants.updateImage,location,id);
			 writeToFileWithOutputStream(stream,phone_no);
			 return Response.status(Status.OK).build();
		}catch(Exception e)
		{
			e.printStackTrace();
			return Response.status(Status.BAD_REQUEST).type("text/plain").entity(LoginConstants.noUserFound).build();
		}
	 }
	 
	 
	 
	 
	 
	 static final String SECRET = "trueDo_agrokult";
	 static final String TOKEN_PREFIX = "Bearer";
	 static final String HEADER_STRING = "Authorization";

	 public boolean verify(String Authorization)
	 {
		 try
			 {
			 if(Authorization==null || !Authorization.startsWith(TOKEN_PREFIX))
				 return false;
			else {
			      
			      String user = Jwts.parser()
			          .setSigningKey(SECRET)
			          .parseClaimsJws(Authorization.replace(TOKEN_PREFIX, ""))
			          .getBody()
			          .getSubject();
			      String details[]=user.split("~");
			      int count=jdbcTemplate.queryForObject(LoginConstants.count_mail,new Object[]{details[0],details[2]},Integer.class);
			      System.out.println(user);
			     
			      if(count==1)
			      {
			    	  int count1=jdbcTemplate.queryForObject(LoginConstants.count_phone,new Object[]{details[1],details[2]},Integer.class);
				      if(count1==0)
				    	  return false;
			    	  return true;
			      }
			      else
			      {
			    	  return false;
			      }
			 }
		 }
		 catch(Exception e)
		 {
			 return false;
		 }
	 }
	 
}