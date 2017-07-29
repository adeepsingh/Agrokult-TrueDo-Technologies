package com.truedo.agrokult;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@Path("/tables")
public class CreateTables {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	 @GET
	   public Response msg(){
		 try
		 {
			 jdbcTemplate.update("CREATE TABLE login (user_id int(11) NOT NULL AUTO_INCREMENT primary key,mail_id varchar(50) UNIQUE NOT NULL,phone_no varchar(11) UNIQUE NOT NULL,password varchar(150) DEFAULT NULL,first_name varchar(20) NOT NULL,middle_name varchar(40) DEFAULT NULL,last_name varchar(40) NOT NULL,address varchar(100) NOT NULL, dob date NOT NULL, company varchar(50) NOT NULL, title varchar(50) NOT NULL, website varchar(50) NOT NULL, image_path varchar(150) DEFAULT NULL,reg_timestamp timestamp NOT NULL DEFAULT 0, login_timestamp timestamp NOT NULL DEFAULT 0, realtime_location varchar(150) DEFAULT NULL,status tinyint(1) DEFAULT '1');");
			 jdbcTemplate.update("create table interests(user_id int not null, interest varchar(100) not null);");
			 jdbcTemplate.update(" CREATE TABLE posts (ad_id int(11) NOT NULL primary key AUTO_INCREMENT, post_id int(11) NOT NULL, post_title varchar(100) NOT NULL, category varchar(100) NOT NULL,image_url varchar(200) DEFAULT 'NO_IMAGE', content varchar(200) not null, timestamp timestamp, status tinyint(1) default 1, fulltext key content(content,post_title,category)) Engine=MyISAM;");
			 jdbcTemplate.update(" CREATE TABLE ads (ad_id int(11) NOT NULL primary key AUTO_INCREMENT, user_id int(11) NOT NULL, ad_title varchar(100) NOT NULL, category varchar(100) NOT NULL,image_url varchar(200) DEFAULT 'NO_IMAGE', content varchar(200) not null, timestamp timestamp, status tinyint(1) default 1, fulltext key content(content,ad_title,category)) Engine=MyISAM;");
			 jdbcTemplate.update("create table mail(mail_id int primary key auto_increment,user_id int not null , subject varchar(100) not null, text varchar(500) not null, timestamp timestamp);");	
			 jdbcTemplate.update("create table business(business_id int auto_increment primary key, company_name varchar(50) not null, services_rendered varchar(60) not null, region varchar(250) not null, interests varchar(250) not null, company_message varchar(250) not null, company_address varchar(250) not null,website varchar(100) not null, image_url varchar(250) default 'No Image Found', timestamp timestamp, status tinyint(1) default 1);");	
			 return Response.status(200).entity("Tables Created").build();
		 }
		 catch(Exception e)
		 {
			 return Response.status(400).entity(e.getMessage()).build();
		 }
	}
}
 