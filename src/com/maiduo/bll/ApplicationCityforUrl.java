package com.maiduo.bll;

public class ApplicationCityforUrl {

	private static ApplicationCityforUrl instance ;
	private String city="";
	private String youhuitype = "";
	private String producttype = "";
	private String area = "";
	private String sort = "";
	private String id = "";
	private ApplicationCityforUrl(){
		
	}
	public static ApplicationCityforUrl getInstance() {
		if (null == instance) {
			instance = new ApplicationCityforUrl();
		}
		return instance;
	}
	public static void setInstance(ApplicationCityforUrl instance) {
		ApplicationCityforUrl.instance = instance;
	}
	

	

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getYouhuitype() {
		return youhuitype;
	}
	public void setYouhuitype(String youhuitype) {
		this.youhuitype = youhuitype;
	}
	public String getProducttype() {
		return producttype;
	}
	public void setProducttype(String producttype) {
		this.producttype = producttype;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	
	public  void Remove() {
		instance = null;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	

}
