package com.truedo.agrokult.ads;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AdsMapper implements RowMapper<Ads> {
	@Override
	public Ads mapRow(ResultSet rs, int i) throws SQLException{
		Ads p=new Ads();
		p.setAds_id(rs.getInt("ad_id"));
		p.setUser_id(rs.getInt("user_id"));
		p.setUser_id(rs.getInt("user_id"));
		p.setAds_title(rs.getString("ad_title"));
		p.setCategory(rs.getString("category"));
		p.setContent(rs.getString("content"));
		p.setTimestamp(rs.getTimestamp("timestamp"));
		
		return p;
	}
}
