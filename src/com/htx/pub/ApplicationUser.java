package com.htx.pub;

import android.app.Application;
import android.content.Context;
import com.htx.model.UserInfo;

public class ApplicationUser extends Application {

	// 登录时设置 用户ID、用户名、密码
	public ApplicationUser(Context c) {
		UserInfo.setUserId(MySharedData.sharedata_ReadString(c, "UserId"));
		UserInfo.setPassWrodKey(MySharedData
				.sharedata_ReadString(c, "passWord"));
		UserInfo.setUserLoginName(MySharedData.sharedata_ReadString(c,
				"loginName"));
		UserInfo.setUserName(MySharedData.sharedata_ReadString(c, "userName"));
	}

	// 退出登录
	public static void LoginOut() {
		UserInfo.setUserId(null);
		UserInfo.setPassWrodKey(null);
	}

	public static boolean CheckLoginStatus(Context context) {
		
//		if (UserInfo.getUserId() == null
//				|| UserInfo.getUserId().toString().equals("")) {
		if(MySharedData.sharedata_ReadString(context, "UserId").equals("")||MySharedData.sharedata_ReadString(context, "UserId")==null){
			return false;
		} else {
			return true;
		}
	}

	// 检测是否登陆
	public static boolean CheckLogin( Context context) {
		if (CheckLoginStatus(context)) { // 如果登录
			return true;
		} else {
			return false;
		}
	}

	public static String GetUserId() {
		return UserInfo.getUserId();
	}

	public static void SetUserId(String userid) {
		UserInfo.setUserId(userid);
	}

	public static String GetUserName() {
		return UserInfo.getUserName();
	}

	public static void SetUserName(String username) {
		UserInfo.setUserName(username);
	}

}