package com.truedo.agrokult.contact;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class JavaMail {
	
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	 @Autowired
	public JavaMailSender emailSender;

	 
	 public Response sendSimpleMessage( int id, String subject, String text) {
		try
		{
			 String mail_id=jdbcTemplate.queryForObject("Select mail_id from login where user_id=?",new Object[]{id},String.class);
	        jdbcTemplate.update("Insert into mail(user_id,subject,text) values(?,?,?);",new Object[]{id,subject,text});
			
			SimpleMailMessage message = new SimpleMailMessage(); 
	        message.setTo("vkumar111223@gmail.com"); 
	        message.setSubject(subject); 
	        message.setText("Query received from "+mail_id+"\nSubject: "+subject+"\n\n\n\n"+text);
	        emailSender.send(message);

			return Response.status(200).entity("Mail sent").build();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return Response.status(400).entity("Something went wrong").build();
		}
		
    }
}
