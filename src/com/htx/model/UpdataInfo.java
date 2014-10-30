package com.htx.model;

/**
 * 
 * @author 杜云飞
 * 
 * @todo 检测服务器客户端版本号时 ，返回数据
 * 
 */
public class UpdataInfo {

	// apk名字
	public static String versionName = "";
	// apk下载地址
	public static String apkUrl = "";
	// apk新版本说明
	public static String description = "";
	// 服务器上本软件的版本号
	public static int versionCode = 1;
	

	private String name;    //

	public static int getVersionCode() {
		return versionCode;
	}

	public static void setVersionCode(int versionCode) {
		UpdataInfo.versionCode = versionCode;
	}

	public static String getVersionName() {
		return versionName;
	}

	public static void setVersionName(String versionName) {
		UpdataInfo.versionName = versionName;
	}

	public static String getApkUrl() {
		return apkUrl;
	}

	public static void setApkUrl(String apkUrl) {
		UpdataInfo.apkUrl = apkUrl;
	}

	public static String getDescription() {
		return description;
	}

	public static void setDescription(String description) {
		UpdataInfo.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
}
