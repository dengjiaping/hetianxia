package com.maiduo.model;


/**
 * 新闻实体类
 * @author Andy.Chen
 * @mail Chenjunjun.ZJ@gmail.com
 *
 */
public class Category {
	private int id;
	private String title;    //标题
	private String img;  //图片
	private String type;  //类型
	
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