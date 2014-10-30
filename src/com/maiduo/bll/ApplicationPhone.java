package com.maiduo.bll;

import android.app.Application;

public class ApplicationPhone extends Application{

	private String Province;

	private String City;

	private String Corp;
	private String Phonenumber;
	private String Price;
	private String PayPassword;

	public String getProvince() {
		return Province;
	}

	public void setProvince(String province) {
		Province = province;
	}

	public String getCity() {
		return City;
	}

	public void setCity(String city) {
		City = city;
	}

	public String getCorp() {
		return Corp;
	}

	public void setCorp(String corp) {
		Corp = corp;
	}
	
	private static ApplicationPhone instance;

	public ApplicationPhone() {

	}

	// 单例模式中获取唯一的MyApplication实例
	public static ApplicationPhone getInstance() {
		if (null == instance) {
			instance = new ApplicationPhone();
		}
		return instance;
	}

	public void LoginOut() {
		instance = null;
	}

	public String getPhonenumber() {
		return Phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.Phonenumber = phonenumber;
	}

	public String getPrice() {
		return Price;
	}

	public void setPrice(String price) {
		Price = price;
	}

	public String getPayPassword() {
		return PayPassword;
	}

	public void setPayPassword(String payPassword) {
		PayPassword = payPassword;
	}
    
}
