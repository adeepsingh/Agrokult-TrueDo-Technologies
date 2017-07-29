package com.truedo.agrokult.business;

import java.net.URL;

public class BusinessValidation {

	public static boolean message(String message)
	{
		if(message.length()<5)
			return false;
		return true;
	}
	
	public static boolean company_name(String company_name)
	{
		if(company_name.length()<3)
			return false;
		return true;
	}
	
	public static boolean address(String address)
	{
		if(address.length()<10)
			return false;
		return true;
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
}
