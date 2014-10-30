package com.maiduo.model;


/**
 * 送货方式 物流公司
 * @author Andy.Chen
 * @mail
 *
 */
public class ShoppingSendCompany {
	private int id;
	
	private String title;    //标题
	private String freight;  //运费
	private int postMethod;// 送货方式
	
	public int getPostMethod() {
		return postMethod;
	}
	public void setPostMethod(int postMethod) {
		this.postMethod = postMethod;
	}
	public int getId() {
		return id;
	}
	public String getFreight() {
		return freight;
	}
	public void setFreight(String freight) {
		this.freight = freight;
	}
	public void setId(int id) {
		this.id = id;
	}

	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	
	public ShoppingSendCompany(){
		
	}

}