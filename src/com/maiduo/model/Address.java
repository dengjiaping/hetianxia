package com.maiduo.model;

public class Address {
	private int id;  //编号
	private String receiveName; //收货人
	private String mobile; //手机号
	private String province; //省
	private String city; //城市
	private String county; //县	
	private String address;//街道地址
	private String ZipCode;//邮编
	public String getZipCode() {
		return ZipCode;
	}
	public void setZipCode(String zipCode) {
		ZipCode = zipCode;
	}

	private boolean ifDefault; // 是否是默认收货地址
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getReceiveName() {
		return receiveName;
	}
	public void setReceiveName(String receiveName) {
		this.receiveName = receiveName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public boolean isIfDefault() {
		return ifDefault;
	}
	public void setIfDefault(boolean ifDefault) {
		this.ifDefault = ifDefault;
	}
	
	/**
	 * 构造函数
	 */
	public  Address(){ 
		
	}
	
	
	
}
