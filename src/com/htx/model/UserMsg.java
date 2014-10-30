package com.htx.model;
/**
 * 用户短消息实体类
 * @author dong
 *
 */
public class UserMsg {

	private String id;    //短信记录主键ID
	private String title;   //短信标题
	private String msgContent;    //短信内容
	private String addTime;    //短信的时间
	private String isAcc;    //是否结算
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMsgContent() {
		return msgContent;
	}
	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}
	public String getAddTime() {
		return addTime;
	}
	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}
	
	public UserMsg(){
		
	}
	public String getIsAcc() {
		return isAcc;
	}
	public void setIsAcc(String isAcc) {
		this.isAcc = isAcc;
	}
}
