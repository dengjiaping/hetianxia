package com.htx.model;



/**
 * 商家实体类
 */
public class Shop {
	private int id;
	private String name;    //商家名
	private String mobile;  //手机号
	private String admobile;  //手机号
	private String addtime;  //时间
	private String distance;  //距离
	private String pic;  	//图片
	private String pic1;  	//图片
	private String pic2;  	//图片
	private String address;  	//地址
	private Double x;  	//
	private Double y;  	//
	private String url;  	//url
	private String oneM;  	//oneM
	private String adTitle;  	//广告语
	


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public String getPic1() {
		return pic1;
	}


	public void setPic1(String pic1) {
		this.pic1 = pic1;
	}


	public String getPic2() {
		return pic2;
	}


	public void setPic2(String pic2) {
		this.pic2 = pic2;
	}


	public String getAdTitle() {
		return adTitle;
	}


	public void setAdTitle(String adTitle) {
		this.adTitle = adTitle;
	}


	public Double getX() {
		return x;
	}


	public void setX(Double x) {
		this.x = x;
	}


	public Double getY() {
		return y;
	}


	public void setY(Double y) {
		this.y = y;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getMobile() {
		return mobile;
	}


	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	public String getAdmobile() {
		return mobile;
	}


	public void setAdmobile(String mobile) {
		this.mobile = mobile;
	}


	public String getAddtime() {
		return addtime;
	}


	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}


	public String getDistance() {
		return distance;
	}


	public void setDistance(String distance) {
		this.distance = distance;
	}


	public String getPic() {
		//return pic;
		return  this.pic;
		
	}


	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getOneM() {
		return oneM;
	}


	public void setOneM(String oneM) {
		this.oneM = oneM;
	}

}