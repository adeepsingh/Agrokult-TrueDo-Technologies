package com.truedo.agrokult.personal_login;

import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;


public class LoginValidate {
		
	public static boolean name(String name)
	{
		if (name.length()<3)
			return false;
		return true;
	}
	
	public static boolean phone(String phone)
	{
		try
		{
			new BigDecimal(phone);
			if(phone.length()==10)
				return true;
			return false;
		}
		catch(Exception e)
		{
			return false;
		}
	}
	
	public static boolean mail_id(String username)
	{
		
		try 
		{
			InternetAddress emailAddr = new InternetAddress(username);
		    emailAddr.validate();
		    return true;	
		} 
		catch (AddressException ex) {
			return false;
		}
	
	}
	
	public static boolean password(String password)
	{
		if(password.length()>5)
			return true;
		return false;
	}
	
	public static boolean address(String address)
	{
		if(address.length()>5)
			return true;
		return false;
	}
	
	
	public static boolean website(String website)
	{
		try
		{
			URL url=new URL(website);
			url.toURI();
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}
	
	public static boolean dob(String dob)
	{
		try
		{
			SimpleDateFormat sdf1 = new SimpleDateFormat("MM-dd-yyyy");
			sdf1.parse(dob);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}
}
