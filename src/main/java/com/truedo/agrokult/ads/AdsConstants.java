package com.truedo.agrokult.ads;

public class AdsConstants {
	
	public static String image_path="C:/Users/vkuma/Desktop/truedo/src/main/resources/images/ads/";
	public static String getRecentId="Select MAX(ad_id) from ads";
	public static String insertSql="Insert into ads(user_id,ad_title,category,content) values(?,?,?,?)";
	public static String insertFilePath="Update ads set image_url=? where ad_id=?";
	
	public static String serveImage="Select image_url from ads where ad_id=? and status=1";
	public static String getAdDetails="Select * from ads where ad_id=? and status=1";
	public static String AllAdsQuery="Select * from ads where status=1";
	public static String getAdByCategory="Select * from ads where category=? and status=1";
	public static String getAdByUser="Select * from ads where user_id=? and status=1";
	public static String getAdByQuery="Select * from ads where match(category,ad_title,content) against(? IN BOOLEAN MODE) and status=1";
	
	
	public static String updateTitle="Update ads set ad_title=? where ad_id=? and status=1";
	public static String updateCategory="Update ads set category=? where ad_id=? and status=1";
	public static String updateContent="Update ads set content=? where ad_id=? and status=1";
	public static String updateImagePath="Update ads set image_url=? where ad_id=? and status=1";
	

	public static String CategoryError="Category name is too short";
	public static String ContentError="Content is too short";
	public static String TitleError="Title is too short";
	public static String ImageNotFound="Image Not Found";
	
	public static String deleteUser="Update ads set status=0 where ad_id=? and status=1";
	public static String noUserFound="No entry found";

}
