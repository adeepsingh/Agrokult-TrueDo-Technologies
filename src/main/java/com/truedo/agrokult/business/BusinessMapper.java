package com.truedo.agrokult.business;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;


public class BusinessMapper implements RowMapper<Business>{
	
	
	public Business mapRow(ResultSet rs, int i) throws SQLException {
		
		Business bu=new Business();
		bu.setCompany_Id(rs.getInt("business_id"));
		bu.setCompany_Name(rs.getString("Company_Name"));
		bu.setServices_Rendered(rs.getString("Services_Rendered"));
		bu.setRegion(rs.getString("Region"));
		bu.setInterests(rs.getString("Interests"));
		bu.setCompany_Message(rs.getString("Company_Message"));
		bu.setCompany_Address(rs.getString("Company_Address"));
		bu.setWebsite(rs.getString("Website"));		
		bu.setStatus(rs.getInt("Status"));
		return bu;
	}
}