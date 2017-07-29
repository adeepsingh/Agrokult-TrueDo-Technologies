package com.truedo.agrokult.ads;

import java.io.InputStream;
import java.util.List;

import javax.ws.rs.core.Response;

public interface AdsServiceInterface {

	
	public Response addAd(int user_id, String ad_title, String interest,InputStream stream, String content);
	
	public Response getImage(int id);
	public Ads getAd(int id);
	public List<Ads> getAllAds();
	 public List<Ads> getAdsByUser(int id);
	 public List<Ads> getAdsByFilter(String query);
	 public List<Ads> getAdsByCategory(String cat);
	 
	 
	 public Response delete(int id);
	 
	 public Response updateTitle(int id,String title);		
	 public Response updateCategory(int id,String category);
	 public Response updateContent(int id,String content);
	 public Response updateImage(int id,InputStream stream);
									
	
		
}
