package com.maiduo.model;


/**
 * �ͻ���ʽ ������˾
 * @author Andy.Chen
 * @mail
 *
 */
public class ShoppingSendCompany {
	private int id;
	
	private String title;    //����
	private String freight;  //�˷�
	private int postMethod;// �ͻ���ʽ
	
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