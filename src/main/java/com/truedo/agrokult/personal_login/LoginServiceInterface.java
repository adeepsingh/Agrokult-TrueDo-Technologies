package com.truedo.agrokult.personal_login;

import java.io.InputStream;

import java.util.List;

import javax.ws.rs.core.Response;

import com.truedo.agrokult.interest.Interests;

public interface LoginServiceInterface {
	
	public Response addUser(InputStream stream, String mail_id, String pass, String first_name, String middle_name, String last_name, String address, String dob, String company, String title, String website, String realtime_location,String phone, List<String> interests);
	public Response login(String username, String password);
	public Response addInterest(int id, String interest);
	
	
	
	public Response getImage(int id);
	public LoginModel getUser(int id);
	public List<LoginModel> getAllUsers();
	 public Interests getInterest(int id);
	
	
	
	public Response updatePassword(int id,String password);
	public Response updateName(int id,String first_name, String middle_name, String last_name);
	public Response updateNumber(int id,String number);
	public Response updateAddress(int id,String address);
	public Response updateDOB(int id,String dob);
	public Response updateCompany(int id,String company);
	public Response updateTitle(int id,String title);
	public Response updateWebsite(int id,String website);
	public Response updateLocation(int id,String location);
	public Response updateImage(int id,InputStream stream);

	
	
	public Response delete(int id);
	public Response deleteInterest(int id,String interest);
}
