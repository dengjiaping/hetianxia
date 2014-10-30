package com.htx.bean;

import java.util.ArrayList;

import com.htx.model.ProductListBean;

public class SearchBean {
	
	private String version;
	
	private String totalCount;
	
	private String success;
	
	private String sellerIdAndNameList;
	
	private String searchType;
	
	private String returnCount;
	
	private String resultMessage;
	
	private String resultCode;
	
	private ArrayList<PropertyPathBean> propertyPath;
	
	private ArrayList<PropListBean> propList;
	
	private ArrayList<ProductListBean> productList;
	
	private ArrayList<CatPathBean> catPath;
	
	private ArrayList<CatListBean> catList;
	
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

	public String getSellerIdAndNameList() {
		return sellerIdAndNameList;
	}

	public void setSellerIdAndNameList(String sellerIdAndNameList) {
		this.sellerIdAndNameList = sellerIdAndNameList;
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

	public ArrayList<PropertyPathBean> getPropertyPath() {
		return propertyPath;
	}

	public void setPropertyPath(ArrayList<PropertyPathBean> propertyPath) {
		this.propertyPath = propertyPath;
	}
	
	public ArrayList<PropListBean> getPropList() {
		return propList;
	}

	public void setPropList(ArrayList<PropListBean> propList) {
		this.propList = propList;
	}
	
	public ArrayList<ProductListBean> getProductList() {
		return productList;
	}

	public void setProductList(ArrayList<ProductListBean> productList) {
		this.productList = productList;
	}
	
	public ArrayList<CatPathBean> getCatPath() {
		return catPath;
	}

	public void setCatPath(ArrayList<CatPathBean> catPath) {
		this.catPath = catPath;
	}
	
	public ArrayList<CatListBean> getCatList() {
		return catList;
	}

	public void setCatList(ArrayList<CatListBean> catList) {
		this.catList = catList;
	}
	
	public ArrayList<AuctionListBean> getAuctionList() {
		return auctionList;
	}

	public void setAuctionList(ArrayList<AuctionListBean> auctionList) {
		this.auctionList = auctionList;
	}

}
