package com.htx.bean;

import java.util.ArrayList;

public class ReceiveBeanNew {
	
	private String totalCount;
	private String returnCount;
	private String resultMessage;
	private String resultCode;
	private ArrayList<AuthBeanNew> comments;
	
	public String getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}
	public String getReturnCount() {
		return returnCount;
	}
	public void setReturnCount(String returnCount) {
		this.returnCount = returnCount;
	}
	public String getResultMessage() {
		return resultMessage;
	}
	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}
	public ArrayList<AuthBeanNew> getComments() {
		return comments;
	}
	public void setComments(ArrayList<AuthBeanNew> comments) {
		this.comments = comments;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

}
