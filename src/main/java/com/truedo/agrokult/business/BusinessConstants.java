package com.truedo.agrokult.business;

public class BusinessConstants {
	
	public static String image_path="C:/Users/vkuma/Desktop/truedo/src/main/resources/images/business/";
	static String add="Insert into business(Company_Name, Services_Rendered, Region, Interests, Company_Message, Company_Address, Website) values(?,?,?,?,?,?,?)";
	public static String getBusinessId="Select MAX(business_id) from business";
	public static String insertFilePath="Update business set image_url=? where business_id=?";
	
	public static String serveImage="Select image_url from business where business_id=? and status=1";
	static String get="Select * from business where business_id=? and status=1";
	static String getUsers="Select * from login where company=? and status=1";	
	static String getall="Select * from business where status=1";
	
	static String delete="Delete from business where business_id=? and status=1";
	
	public static String updateImagePath="Update business set image_url=? where business_id=? and status=1";
	public static String updateCompanyName="Update business set company_name=? where business_id=? and status=1";
	public static String updateService="Update business set services_rendered=? where business_id=? and status=1";
	public static String updateRegion="Update business set region=? where business_id=? and status=1";
	public static String updateInterests="Update business set interests=? where business_id=? and status=1";
	public static String updateMessage="Update business set company_message=? where business_id=? and status=1";
	public static String updateAddress="Update business set company_address=? where business_id=? and status=1";
	public static String updateWebsite="Update business set website=? where business_id=? and status=1";
	
	
	
	static String messageError="Message is too short";
	static String companyNameError="Company Name is too short";
	static String websiteError="Website not in proper format. Include http";
	static String addressError="Company address is too short";
	public static String noUserFound="No entry found";

}
