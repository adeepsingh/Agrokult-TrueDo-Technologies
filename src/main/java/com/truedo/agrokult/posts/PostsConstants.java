package com.truedo.agrokult.posts;

public class PostsConstants {
	
	public static String image_path="C:/Users/vkuma/Desktop/truedo/src/main/resources/images/posts/";
	public static String getRecentId="Select MAX(post_id) from posts";
	public static String insertSql="Insert into posts(user_id,post_title,category,content) values(?,?,?,?)";
	public static String insertFilePath="Update posts set image_url=? where post_id=?";
	
	public static String serveImage="Select image_url from posts where post_id=? and status=1";
	public static String getPostDetails="Select * from posts where post_id=? and status=1";
	public static String AllPostsQuery="Select * from posts where status=1";
	public static String getPostByCategory="Select * from posts where category=? and status=1";
	public static String getPostByUser="Select * from posts where user_id=? and status=1";
	public static String getPostByQuery="Select * from posts where match(category,post_title,content) against(? IN BOOLEAN MODE) and status=1";
	
	
	public static String updateTitle="Update posts set post_title=? where post_id=? and status=1";
	public static String updateCategory="Update posts set category=? where post_id=? and status=1";
	public static String updateContent="Update posts set content=? where post_id=? and status=1";
	public static String updateImagePath="Update posts set image_url=? where post_id=? and status=1";
	

	public static String CategoryError="Category name is too short";
	public static String ContentError="Content is too short";
	public static String TitleError="Title is too short";
	public static String ImageNotFound="Image Not Found";
	
	public static String deleteUser="Update posts set status=0 where post_id=? and status=1";
	public static String noUserFound="No entry found";

}
