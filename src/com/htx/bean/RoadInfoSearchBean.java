package com.htx.bean;

import java.util.ArrayList;

public class RoadInfoSearchBean {	
	
	private String state;//
	private int count;
	private ArrayList<RoadInfoResultBean> list;//
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public ArrayList<RoadInfoResultBean> getList() {
		return list;
	}
	public void setList(ArrayList<RoadInfoResultBean> list) {
		this.list = list;
	}

}
