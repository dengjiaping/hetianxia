package com.maiduo.model;
//�˿���Ϣ
public class PassengInfo {

	private String name;
	private String cardNo;
	private int type;// �˿�����   1����   2��ͯ    3Ӥ��
	private int airYW;//��������������  0����   1��һ��  
	private int farePrice; // Ʊ��
	private int taxPrice; // ����
	private int fuelPrice;// ȼ��
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
