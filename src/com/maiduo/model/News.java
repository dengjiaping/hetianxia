package com.maiduo.model;


/**
 * ����ʵ����
 * @author Andy.Chen
 * @mail Chenjunjun.ZJ@gmail.com
 *
 */
public class News {
	private int id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	private String title;    //����
	private String content;  //����
	private String addtime;  //ʱ��
	private String check;// �Ƿ�ѡ�� 
	
	
	public String getCheck() {
		return check;
	}
	public void setCheck(String check) {
		this.check = check;
	}
	public String getAddtime() {
		return addtime;
	}
	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public News(){
		
	}

}