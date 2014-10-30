package com.htx.bean;

import java.io.Serializable;
import java.util.ArrayList;

import android.util.Log;

import com.htx.model.ProductListBean;

public class ProductDetailBeanNew implements Serializable{
	
	private static final long serialVersionUID = -7160210544600464481L;
	
	private String title;
	private String pictUrl;
	private String epid;
	private String cmtScore;
	private String cmtCount;
	private String b2cTotalhits;
	private String returnB2cCount;
	private String price;
	private String sellCount;
	private String properties;
	private String catid;
	private ArrayList<AuctionListBeanNew> b2cresult;
	private String resultMessage;
	private String resultCode;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPictUrl() {
		return pictUrl;
	}
	public void setPictUrl(String pictUrl) {
		this.pictUrl = pictUrl;
	}
	public String getEpid() {
		return epid;
	}
	public void setEpid(String epid) {
		this.epid = epid;
	}
	public String getCmtScore() {
		return cmtScore;
	}
	public void setCmtScore(String cmtScore) {
		this.cmtScore = cmtScore;
	}
	public String getCmtCount() {
		return cmtCount;
	}
	public void setCmtCount(String cmtCount) {
		this.cmtCount = cmtCount;
	}
	
	public String getB2cTotalhits() {
		return b2cTotalhits;
	}
	public void setB2cTotalhits(String b2cTotalhits) {
		this.b2cTotalhits = b2cTotalhits;
	}
	
	public String getreturnB2cCount() {
		return returnB2cCount;
	}
	public void setreturnB2cCount(String returnB2cCount) {
		this.returnB2cCount = returnB2cCount;
	}
	
	public String getPrice() {
		return price;
	}
	
	public void setPrice(String price) {
		this.price = price;
	}
	
	
	public String getProperties() {
		return properties;
	}
	
	public void setProperties(String properties) {
		this.properties = properties;
	}
	
	public String getSellCount() {
		return sellCount;
	}
	
	public void setSellCount(String sellCount) {
		this.sellCount = sellCount;
	}
	
	public String getCatid() {
		return catid;
	}
	
	public void setCatId(String catid) {
		this.catid = catid;
	}
	
	public String getResultMessage() {
		return resultMessage;
	}
	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public ArrayList<AuctionListBeanNew> getB2cresult() {
		
		return b2cresult;
	}
	public void setB2cresult(ArrayList<AuctionListBeanNew> b2cresult) {
		this.b2cresult = b2cresult;
	}
}
