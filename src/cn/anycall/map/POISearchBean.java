package cn.anycall.map;

import java.util.ArrayList;

public class POISearchBean {
	
	private int status;//
	private String message;//
	private int total;
	private ArrayList<POIResultBean> results;//
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public ArrayList<POIResultBean> getResults() {
		return results;
	}
	public void setResults(ArrayList<POIResultBean> results) {
		this.results = results;
	}

}
