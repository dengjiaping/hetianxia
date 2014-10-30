package com.htx.model;

/**
 * 用户短消息实体类
 * 
 * @author dong
 * 
 */
public class Apps {

	private String id; // 主键ID
	private String appName; // 客户端标题
	private String appType; // 类型
	private String appImg; // 图标
	private String appUrl; // 下载地址
	private String appSize; // 大小
	private String appVsNum; // 版本
	private String appDownNum; // 下载次数
	private String appContent; // 描述
	private String appJosnData; // 已安装的应用的列表数据
	private String appPackageName; // 包名
	private String appActivityName; // 已安装的应用的列表数据
	
	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getAppType() {
		return appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

	public String getAppImg() {
		return appImg;
	}

	public void setAppImg(String appImg) {
		this.appImg = appImg;
	}

	public String getAppUrl() {
		return appUrl;
	}

	public void setAppUrl(String appUrl) {
		this.appUrl = appUrl;
	}

	public String getAppSize() {
		return appSize;
	}

	public void setAppSize(String appSize) {
		this.appSize = appSize;
	}

	public String getAppVsNum() {
		return appVsNum;
	}

	public void setAppVsNum(String appVsNum) {
		this.appVsNum = appVsNum;
	}

	public String getAppDownNum() {
		return appDownNum;
	}

	public void setAppDownNum(String appDownNum) {
		this.appDownNum = appDownNum;
	}

	public String getAppContent() {
		return appContent;
	}

	public void setAppContent(String appContent) {
		this.appContent = appContent;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAppPackageName() {
		return appPackageName;
	}

	public void setAppPackageName(String appPackageName) {
		this.appPackageName = appPackageName;
	}

	public String getAppActivityName() {
		return appActivityName;
	}

	public void setAppActivityName(String appActivityName) {
		this.appActivityName = appActivityName;
	}
}
