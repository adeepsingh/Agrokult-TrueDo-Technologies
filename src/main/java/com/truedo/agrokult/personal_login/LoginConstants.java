package com.truedo.agrokult.personal_login;

public class LoginConstants {
	
	public static String image_path="C:/Users/vkuma/Desktop/truedo/src/main/resources/images/user/";
	
	//registration
	public static String checkSql="Select Count(*) from login where mail_id=? and status=0";
	public static String insertSql="Insert into login(mail_id,phone_no,password,first_name,middle_name,last_name,address,dob,company,title,website,image_path,reg_timestamp,login_timestamp,realtime_location) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	public static String refreshSql="Delete from login where mail_id=?";
	public static String getRecentId="Select MAX(user_id) from login";
	public static String addInterest="Insert into interests values (?,?)";
	
	//login
	public static String FindUser="Select count(*) from login where (mail_id=? or phone_no=?) and status=1";
	public static String ObtainSavedPassword="Select password from login where (mail_id=? or phone_no=?) and status=1";
	public static String find_user_id="Select user_id from login where (mail_id=? or phone_no=?) and status=1";
	public static String find_mail_id="Select mail_id from login where user_id=? and status=1";
	public static String find_phone_no="Select phone_no from login where user_id=? and status=1";
	public static String updateTimestamp="Update login set login_timestamp=? where user_id=?";
	
	
	//decoding JWT
	public static String count_mail="Select count(*) from login where mail_id=? and password=? and status=1";
	public static String count_phone="Select count(*) from login where phone_no=? and password=? and status=1";
	
	//get queries
	public static String serveImage="Select image_path from login where user_id=? and status=1";
	public static String AllUsersQuery="Select * from login where status=1";
	public static String getUserDetails="Select * from login where user_id=? and status=1";
	
	
	//update query
	public static String updatePassword="Update login set password=? where user_id=? and status=1";
	public static String updateName="Update login set first_name=?,middle_name=?,last_name=? where user_id=? and status=1";
	public static String updateAddress="Update login set address=? where user_id=? and status=1";
	public static String updateDOB="Update login set dob=? where user_id=? and status=1";	
	public static String updateCompany="Update login set company=? where user_id=? and status=1";	
	public static String updateTitle="Update login set title=? where user_id=? and status=1";	
	public static String updateWebsite="Update login set website=? where user_id=? and status=1";	
	public static String updateRealtimeLocation="Update login set realtime_location=? where user_id=? and status=1";	
	public static String updateImage="Update login set image_path=? where user_id=? and status=1";	
	public static String updateNumber="Update login set phone_no=? where user_id=? and status=1";
	
	
	//delete query
	public static String deleteUser="Update login set status=0 where user_id=? and status=1";
	public static String deleteInterest="Delete from interests where user_id=? and interest=?";
	
	
	//errors
	public static String firstNameError="First name too short";
	public static String lastNameError="Last name too short";
	public static String addressError="Address too short";
	public static String phoneError="First name too short";
	public static String mailIdError="First name too short";
	public static String companyError="Company name too short";
	public static String passwordLengthError="Mail_id not in correct format";
	public static String websiteError="Website format not correct. Include http, www, .*";
	public static String dateOfBirth="Date of Birth should be MM-dd-yyyy";
	public static String passwordMatchError="Password does not match";
	public static String AlreadyRegistered="Either your username or phone number has already been registered";
	public static String ImageNotFound="Image Not Found";
	public static String PhoneNoConflict="Phone number already exists in our database";
	public static String noUserFound="No entry found";
}
