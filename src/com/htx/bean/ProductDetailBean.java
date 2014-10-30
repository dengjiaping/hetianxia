package com.htx.bean;

import java.io.Serializable;
import java.util.ArrayList;

import android.util.Log;

import com.htx.model.ProductListBean;

public class ProductDetailBean implements Serializable{
	
	private static final long serialVersionUID = -7160210544600464481L;
	
	private String version;
	private String totalCount;
	private String success;
	private String searchType;
	private String returnCount;
	private String resultMessage;
	private String resultCode;
	private ProductListBean productDO;
	private String offset;
	private ArrayList<AuctionListBean> auctionList;
	
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
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
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
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
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public ProductListBean getProductDO() {
		return productDO;
	}
	public void setProductDO(ProductListBean productDO) {
		this.productDO = productDO;
	}
	public String getOffset() {
		return offset;
	}
	public void setOffset(String offset) {
		this.offset = offset;
	}

	public ArrayList<AuctionListBean> getAuctionList() {
		
		Log.e("WW", "|+|+|+|----->  "+auctionList);
		Log.e("WW", "|+|+|+|----->  "+auctionList);
		Log.e("WW", "|+|+|+|----->  "+auctionList);
		
		return auctionList;
	}
	public void setAuctionList(ArrayList<AuctionListBean> auctionList) {
		this.auctionList = auctionList;
	}
}
