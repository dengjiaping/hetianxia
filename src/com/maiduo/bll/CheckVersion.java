package com.maiduo.bll;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.maiduo.model.UpdataInfo;

public class CheckVersion {
	private String path = Config.UrlCheckVersion;
	private Context mContext;
	public static String welcomeImageUrl = null;
	private UpdataInfo info = new UpdataInfo();
	public static String description="";
	public static String apkUrl="";
	public static int MobileId = 0;
	public static boolean ifSartMain = true;

	private boolean getUpdataInfo() {
		try {
			// 从资源文件获取服务器 地址
			if (this.MobileId != 0) {
//				path += "?mobileId=" + this.MobileId + "&Longitude=" + Main.x + "&Latitude=" + Main.y;
			}
			info = getUpdataInfo(path);
		
			if (info.getVersionCode() <= getVersionCode()) {
				this.ifSartMain = true;
			} else {
				// 版本号不同 ,提示用户升级
				this.ifSartMain = false;
			}
		} catch (Exception e) {
			this.ifSartMain = true;
			e.printStackTrace();
		}
		return this.ifSartMain;
	}

	public CheckVersion(Context context) {
		this.mContext = context;
	}

	// 外部接口让主Activity调用
	public boolean checkUpdateInfo() {

		return getUpdataInfo();
	}


	/*
	 * 获取当前程序的版本号
	 */
	public String getVersionName() throws Exception {
		// 获取packagemanager的实例
		PackageManager packageManager = mContext.getPackageManager();
		// getPackageName()是你当前类的包名，0代表是获取版本信息
		PackageInfo packInfo = packageManager.getPackageInfo(mContext.getPackageName(), 0);
		return packInfo.versionName;
	}

	public int getVersionCode() throws Exception {
		// 获取packagemanager的实例
		PackageManager packageManager = mContext.getPackageManager();
		// getPackageName()是你当前类的包名，0代表是获取版本信息
		PackageInfo packInfo = packageManager.getPackageInfo(mContext.getPackageName(), 0);
		return packInfo.versionCode;
	}

	/**
	 * 用pull解析器解析服务器返回的xml文件 (xml封装了版本号)
	 */
	public UpdataInfo getUpdataInfo(String url) throws Exception {
		UpdataInfo info = new UpdataInfo();// 实体
		/**
		 * 这里需要分析服务器回传的json格式数据，
		 */
		JSONObject jsonObject;
		try {
			String reStr = HttpHelper.getJsonData(mContext,url);
			JSONObject reObject = new JSONObject(reStr);
			if (reObject.getInt("status") == 0) {
				Toast.makeText(mContext, reObject.getString("error"), Toast.LENGTH_SHORT).show();
				return null;
			}
			jsonObject = reObject.getJSONObject("result");
			info.setVersion(jsonObject.getString("version"));
			info.setVersionCode(jsonObject.getInt("versionCode"));
			info.setUrl(jsonObject.getString("url"));
			apkUrl = jsonObject.getString("url");
			info.setDescription(jsonObject.getString("description"));
			description = jsonObject.getString("description");
			welcomeImageUrl = jsonObject.getString("loadImg");
			MobileId = jsonObject.getInt("mobileId");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return info;
	}
}
