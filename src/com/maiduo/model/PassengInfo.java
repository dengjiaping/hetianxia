package com.maiduo.model;
//乘客信息
public class PassengInfo {

	private String name;
	private String cardNo;
	private int type;// 乘客类型   1成人   2儿童    3婴儿
	private int airYW;//航空意外险数量  0不买   1买一份  
	private int farePrice; // 票价
	private int taxPrice; // 基建
	private int fuelPrice;// 燃油
	private int userMoney;//
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getAirYW() {
		return airYW;
	}
	public void setAirYW(int airYW) {
		this.airYW = airYW;
	}
	public int getFarePrice() {
		return farePrice;
	}
	public void setFarePrice(int farePrice) {
		this.farePrice = farePrice;
	}
	public int getTaxPrice() {
		return taxPrice;
	}
	public void setTaxPrice(int taxPrice) {
		this.taxPrice = taxPrice;
	}
	public int getFuelPrice() {
		return fuelPrice;
	}
	public void setFuelPrice(int fuelPrice) {
		this.fuelPrice = fuelPrice;
	}
	public int getUserMoney() {
		return userMoney;
	}
	public void setUserMoney(int userMoney) {
		this.userMoney = userMoney;
	}
	
	
}
