package com.truedo.agrokult.posts;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class PostsMapper implements RowMapper<Posts> {
	@Override
	public Posts mapRow(ResultSet rs, int i) throws SQLException{
		Posts p=new Posts();
		p.setPost_id(rs.getInt("post_id"));
		p.setUser_id(rs.getInt("user_id"));
		p.setUser_id(rs.getInt("user_id"));
		p.setPost_title(rs.getString("post_title"));
		p.setCategory(rs.getString("category"));
		p.setContent(rs.getString("content"));
		p.setTimestamp(rs.getTimestamp("timestamp"));
		
		return p;
	}
}
