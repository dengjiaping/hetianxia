package com.maiduo.model;

public class FlightSearch {

	private String outCityName;
	private String toCityName;
	private String outCityCode;
	private String toCityCode;
	private String outDate;
	private String toDate;
	private int flyOneOrTwo = 1;// 单程 还是 往返   1单程   2往返 
	public String getOutCityName() {
		return outCityName;
	}
	public void setOutCityName(String outCityName) {
		this.outCityName = outCityName;
	}
	public String getToCityName() {
		return toCityName;
	}
	public void setToCityName(String toCityName) {
		this.toCityName = toCityName;
	}
	public String getOutCityCode() {
		return outCityCode;
	}
	public void setOutCityCode(String outCityCode) {
		this.outCityCode = outCityCode;
	}
	public String getToCityCode() {
		return toCityCode;
	}
	public void setToCityCode(String toCityCode) {
		this.toCityCode = toCityCode;
	}
	public String getOutDate() {
		return outDate;
	}
	public void setOutDate(String outDate) {
		this.outDate = outDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public int getFlyOneOrTwo() {
		return flyOneOrTwo;
	}
	public void setFlyOneOrTwo(int flyOneOrTwo) {
		this.flyOneOrTwo = flyOneOrTwo;
	}
	
	
}
