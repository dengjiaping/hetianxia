package com.icq.demo.beans;

import java.io.Serializable;

/**
 * Des:联动下拉框实体类
 * @author Alan
 * 2014-7-25
 */
public class Item  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8895531464782412950L;
	private String id;
	private String name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

}
