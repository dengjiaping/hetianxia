package com.maiduo.model;

/**
 * 新闻实体类
 * @author Andy.Chen
 * @mail Chenjunjun.ZJ@gmail.com
 *
 */
public class ShoppingCartProductList {


	private String ID; 
	private String ProId; 
	private String ProName; 
	private String StyleId; 
	private String StyleName; 
	private String ProNum; 
	private String ShopPrice; 
	private String priceSum; 
	private String Unit; 
	private String ProImg; 
	private String SupplierId; 
	private String productId;
	public String getID() {
		return ID;
	}
	public String getPriceSum() {
		return priceSum;
	}
	public void setPriceSum(String priceSum) {
		this.priceSum = priceSum;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getProId() {
		return ProId;
	}
	public void setProId(String proId) {
		ProId = proId;
	}
	public String getProName() {
		return ProName;
	}
	public void setProName(String proName) {
		ProName = proName;
	}
	public String getStyleId() {
		return StyleId;
	}
	public void setStyleId(String styleId) {
		StyleId = styleId;
	}
	public String getStyleName() {
		return StyleName;
	}
	public void setStyleName(String styleName) {
		StyleName = styleName;
	}
	public String getProNum() {
		return ProNum;
	}
	public void setProNum(String proNum) {
		ProNum = proNum;
	}
	public String getShopPrice() {
		return ShopPrice;
	}
	public void setShopPrice(String shopPrice) {
		ShopPrice = shopPrice;
	}
	public String getUnit() {
		return Unit;
	}
	public void setUnit(String unit) {
		Unit = unit;
	}
	public String getProImg() {
		return ProImg;
	}
	public void setProImg(String proImg) {
		ProImg = proImg;
	}
	public String getSupplierId() {
		return SupplierId;
	}
	public void setSupplierId(String supplierId) {
		SupplierId = supplierId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	} 

}