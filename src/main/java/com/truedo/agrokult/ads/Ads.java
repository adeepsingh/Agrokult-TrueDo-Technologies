package com.truedo.agrokult.ads;

import java.sql.Timestamp;

public class Ads {
	int ads_id;
	int user_id;
	String ads_title;
	String category;
	String image_url;
	String content;
	Timestamp timestamp;
	public int getAds_id() {
		return ads_id;
	}
	public void setAds_id(int ads_id) {
		this.ads_id = ads_id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getAds_title() {
		return ads_title;
	}
	public void setAds_title(String ads_title) {
		this.ads_title = ads_title;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getImage_url() {
		return image_url;
	}
	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	
	
}
