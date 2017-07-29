package com.truedo.agrokult.posts;

import java.text.SimpleDateFormat;

public class PostsValidation {
	
	
	public static boolean title(String title)
	{
		if(title.length()<5)
			return false;
		return true;
	}
	
	public static boolean category(String category)
	{
		if(category.length()<2)
			return false;
		return true;
	}
	
	public static boolean content(String content)
	{
		if(content.length()<10)
			return false;
		return true;
	}
	
	public static boolean dob(String date)
	{
		try
		{
			SimpleDateFormat sdf1 = new SimpleDateFormat("MM-dd-yyyy");
			sdf1.parse(date);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}
}
