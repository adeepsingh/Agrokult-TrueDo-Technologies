package com.truedo.agrokult.personal_login;


import java.sql.Date;
import java.sql.Timestamp;

public class LoginModel {
	//create table login(user_id int primary key auto_increment, mail_id varchar(50) unique not null, phone_no varchar(11) unique not null, password varchar(150) not null, first_name varchar(20) not null, middle_name varchar(40), last_name varchar(40) not null, address varchar(100) not null, dob date not null, company varchar(50) not null, title varchar(50) not null, website varchar(50) not null, image_path varchar(150) not null, reg_timestamp timestamp not null default 0, login_timestamp timestamp not null default 0, realtime_location varchar(150) not null, status tinyint(1) default 1);
	int user_id; //primary key
	String mail_id; //unique
	String phone_no; //unique
	String password; 
	String first_name;
	String middle_name;
	String last_name;
	String address;
	Date dob;
	String company;
	String title;
	String website;
	String image_path;
	Timestamp reg_timestamp;
	Timestamp login_timestamp;
	String realtime_location;
	boolean status;
	
	
	
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getMail_id() {
		return mail_id;
	}
	public void setMail_id(String mail_id) {
		this.mail_id = mail_id;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhone_no() {
		return phone_no;
	}
	public void setPhone_no(String phone_no) {
		this.phone_no = phone_no;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getMiddle_name() {
		return middle_name;
	}
	public void setMiddle_name(String middle_name) {
		this.middle_name = middle_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getImage_path() {
		return image_path;
	}
	public void setImage_path(String image_path) {
		this.image_path = image_path;
	}
	public Timestamp getReg_timestamp() {
		return reg_timestamp;
	}
	public void setReg_timestamp(Timestamp reg_timestamp) {
		this.reg_timestamp = reg_timestamp;
	}
	public Timestamp getLogin_timestamp() {
		return login_timestamp;
	}
	public void setLogin_timestamp(Timestamp login_timestamp) {
		this.login_timestamp = login_timestamp;
	}
	public String getRealtime_location() {
		return realtime_location;
	}
	public void setRealtime_location(String realtime_location) {
		this.realtime_location = realtime_location;
	}
	
}
