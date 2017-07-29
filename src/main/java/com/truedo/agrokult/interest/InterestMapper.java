package com.truedo.agrokult.interest;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;


public class InterestMapper implements RowMapper<String> {
	@Override
	public String mapRow(ResultSet rs, int i) throws SQLException{
		
	
		
		return rs.getString("interest");
	}
}
