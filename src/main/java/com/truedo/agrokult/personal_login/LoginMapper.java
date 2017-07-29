package com.truedo.agrokult.personal_login;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.truedo.agrokult.personal_login.LoginModel;



public class LoginMapper implements RowMapper<LoginModel>{
	
	@Override
	public LoginModel mapRow(ResultSet rs, int i) throws SQLException {
		
		LoginModel lm=new LoginModel();
		lm.setUser_id(rs.getInt("user_id"));
		lm.setMail_id(rs.getString("mail_id"));
		lm.setPhone_no(rs.getString("phone_no"));
		lm.setFirst_name(rs.getString("first_name"));
		lm.setMiddle_name(rs.getString("middle_name"));
		lm.setLast_name(rs.getString("last_name"));
		lm.setAddress(rs.getString("address"));
		lm.setDob(rs.getDate("dob"));
		lm.setCompany(rs.getString("company"));
		lm.setWebsite(rs.getString("website"));
		lm.setTitle(rs.getString("title"));
		lm.setReg_timestamp(rs.getTimestamp("reg_timestamp"));
		lm.setLogin_timestamp(rs.getTimestamp("login_timestamp"));
		lm.setRealtime_location(rs.getString("realtime_location"));
		lm.setStatus(rs.getBoolean("status"));
		
		return lm;
	}
}