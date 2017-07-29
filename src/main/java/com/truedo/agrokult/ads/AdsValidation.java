package com.truedo.agrokult.ads;

public class AdsValidation {
	
	
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
}
