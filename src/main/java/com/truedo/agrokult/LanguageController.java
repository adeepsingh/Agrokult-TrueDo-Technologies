package com.truedo.agrokult;

import java.util.Locale;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
@Path("/language")
public class LanguageController {

	@Autowired
	private MessageSource messageSource;
	 
	 @GET
	   public String msg(@HeaderParam("Accept-Language") Locale locale, @HeaderParam("msg")String msg){
	     
		 return messageSource.getMessage(msg,null,locale);
	   }
}
