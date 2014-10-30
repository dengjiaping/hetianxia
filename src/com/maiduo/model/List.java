package com.maiduo.model;

public class List {
	private int id;
	private String Title;
	private double Price;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public double getPrice() {
		return Price;
	}
	@Override
	public String toString() {
		return "List [id=" + id + ", Title=" + Title + ", Price=" + Price + "]";
	}
	public void setPrice(double price) {
		Price = price;
	}
	public List(){
		
	}
	
}
