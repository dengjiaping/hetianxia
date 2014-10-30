package com.htx.bean;

import java.util.ArrayList;

public class ReceiveBean {
	
	private String totalCount;
	private String success;
	private String returnCount;
	private String resultMessage;
	private String resultCode;
	private ArrayList<AuthBean> comments;
	
	public String getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
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
	public ArrayList<AuthBean> getComments() {
		return comments;
	}
	public void setComments(ArrayList<AuthBean> comments) {
		this.comments = comments;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

}
