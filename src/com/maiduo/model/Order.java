package com.maiduo.model;


/**
 * 订单实体类
 * @author 
 * @mail 
 *
 */
public class Order {
	private String ID;
	private String UserId;
	private String ReceiveName;
	private String InnerOrderId;
	private String Status;
	private String GoodsAmount;
	private String PayDate;
	private String AddDate;
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getUserId() {
		return UserId;
	}
	public void setUserId(String userId) {
		UserId = userId;
	}
	public String getReceiveName() {
		return ReceiveName;
	}
	public void setReceiveName(String receiveName) {
		ReceiveName = receiveName;
	}
	public String getInnerOrderId() {
		return InnerOrderId;
	}
	public void setInnerOrderId(String innerOrderId) {
		InnerOrderId = innerOrderId;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public String getGoodsAmount() {
		return GoodsAmount;
	}
	public void setGoodsAmount(String goodsAmount) {
		GoodsAmount = goodsAmount;
	}
	public String getPayDate() {
		return PayDate;
	}
	public void setPayDate(String payDate) {
		PayDate = payDate;
	}
	public String getAddDate() {
		return AddDate;
	}
	public void setAddDate(String addDate) {
		AddDate = addDate;
	}

}