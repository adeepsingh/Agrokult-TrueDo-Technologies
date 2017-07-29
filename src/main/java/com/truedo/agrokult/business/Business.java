package com.truedo.agrokult.business;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Business {
	private int Company_Id;
	private String Company_Name;
	private String Services_Rendered;
	private String Region;
	private String Interests;
	private String Company_Message;
	private String Company_Address;
	private String Website;
	private String ImagePath;
	private int Status;
	
	public int getCompany_Id() {
		return Company_Id;
	}
	
	public void setCompany_Id(int Company_Id) {
		this.Company_Id = Company_Id;
	}
	
	public String getCompany_Name() {
		return Company_Name;
	}
	
	public void setCompany_Name(String Company_Name) {
		this.Company_Name = Company_Name;
	}
	
	public String getServices_Rendered() {
		return Services_Rendered;
	}
	
	public void setServices_Rendered(String Services_Rendered) {
		this.Services_Rendered = Services_Rendered;
	}
	
	public String getRegion() {
		return Region;
	}
	
	public void setRegion(String Region) {
		this.Region = Region;
	}
	
	public String getInterests()	{
		return Interests;
	}
	
	public void setInterests(String Interests)	{
		this.Interests = Interests;
	}
	
	public String getCompany_Message() {
		return Company_Message;
	}
	
	public void setCompany_Message(String Company_Message) {
		this.Company_Message = Company_Message;
	}
	
	public String getCompany_Address() {
		return Company_Address;
	}
	
	public void setCompany_Address(String Company_Address) {
		this.Company_Address = Company_Address;
	}
	
	public String getWebsite() {
		return Website;
	}
	
	public void setWebsite(String Website) {
		this.Website = Website;
	}
	
	public String getImagePath() {
		return ImagePath;
	}
	
	public void setImagePath(String ImagePath) {
		this.ImagePath = ImagePath;
	}
	
	public int getStatus() {
		return Status;
	}
	
	public void setStatus(int Status) {
		this.Status = Status;
	}
}
