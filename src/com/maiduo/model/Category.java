package com.maiduo.model;


/**
 * ����ʵ����
 * @author Andy.Chen
 * @mail Chenjunjun.ZJ@gmail.com
 *
 */
public class Category {
	private int id;
	private String title;    //����
	private String img;  //ͼƬ
	private String type;  //����
	
	public int getId() {
		return id;
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



	public String getImg() {
		return img;
	}



	public void setImg(String img) {
		this.img = img;
	}



	public String getType() {
		return type;
	}



	public void setType(String type) {
		this.type = type;
	}



	

	
	public Category(){
		
	}

}