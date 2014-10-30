package com.maiduo.model;


/**
 * 首页图片幻灯实体类
 * @author Andy.Chen
 * @mail Chenjunjun.ZJ@gmail.com
 *
 */
public class DefaultSlide {
	private String title;//标题
	private String pic;  //图片地址
	private String url;  //链接
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public DefaultSlide(){
		
	}

}