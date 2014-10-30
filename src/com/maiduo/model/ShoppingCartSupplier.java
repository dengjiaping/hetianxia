package com.maiduo.model;

import java.util.List;

/**
 * 购物车中每个商家数据
 * @author Administrator
 *
 */
public class ShoppingCartSupplier {
	private String shopName ;
	private double allPrice ;
	private double allJifen ;
	private double allRegADRate ;
	private int supplierId;
	public double getAllRegADRate() {
		return allRegADRate;
	}
	public void setAllRegADRate(double allRegADRate) {
		this.allRegADRate = allRegADRate;
	}

	private List<ShoppingCartProductList> list;
	
	public List<ShoppingCartProductList> getList() {
		return list;
	}
	public void setList(List<ShoppingCartProductList> product_list) {
		this.list = product_list;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public double getAllPrice() {
		return allPrice;
	}
	public void setAllPrice(double allprice2) {
		allPrice = allprice2;
	}
	public double getAllJifen() {
		return allJifen;
	}
	public void setAllJifen(double allJifen) {
		this.allJifen = allJifen;
	}
	public int getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(int supplierId) {
		this.supplierId = supplierId;
	}
	
	public ShoppingCartSupplier(){
		
	}
	
	

}
