package com.maiduo.model;

import com.maiduo.bll.Config;


public class ProductHuanLing {

	private String id;
	private String name;
	private String pic;
	private String jifen;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPic() {
		if(this.pic.indexOf(Config.ProdImgPrefix) > -1){
			return this.pic;
		}else{
			return Config.ProdImgPrefix + this.pic;
		}
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	
	
	public ProductHuanLing(){
		
	}
	public String getJifen() {
		return jifen;
	}
	public void setJifen(String jifen) {
		this.jifen = jifen;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}