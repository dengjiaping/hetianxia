package com.maiduo.model;

public class Address {
	private int id;  //���
	private String receiveName; //�ջ���
	private String mobile; //�ֻ���
	private String province; //ʡ
	private String city; //����
	private String county; //��	
	private String address;//�ֵ���ַ
	private String ZipCode;//�ʱ�
	public String getZipCode() {
		return ZipCode;
	}
	public void setZipCode(String zipCode) {
		ZipCode = zipCode;
	}

	private boolean ifDefault; // �Ƿ���Ĭ���ջ���ַ
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
	 * ���캯��
	 */
	public  Address(){ 
		
	}
	
	
	
}
