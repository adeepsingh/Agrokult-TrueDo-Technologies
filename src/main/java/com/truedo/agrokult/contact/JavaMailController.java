package com.truedo.agrokult.contact;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.truedo.agrokult.personal_login.LoginService;

@Component
@Path("/mail")
public class JavaMailController {

	@Autowired
	JavaMail mail;


	@Autowired
	LoginService user;
	
	@POST
	@Path("{id}")
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
    public Response uploadFileWithData(
    		@HeaderParam("Authorization") String Authorization,
    		@PathParam("id") int id,    		
    		@FormParam("subject") String subject,    		
    		@FormParam("text") String text)
    		
    {
		if(!user.verify(Authorization))
			return Response.status(Status.BAD_REQUEST).type("text/plain").entity("You are not logged in. Pease log in and make sure cookies are enabled.").build();
		System.out.println(id);
		return mail.sendSimpleMessage(id,subject, text);
    }
}
