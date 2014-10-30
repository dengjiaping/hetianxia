package com.htx.action;

import com.htx.model.UpdataInfo;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

public class CheckVersion {
	/**
	 * 检查版本是否需要升级
	 * 
	 * @return ifUpdate
	 */
	public static boolean checkersion(Context context) {

		boolean bool = false;
		try {
			// 需升级
			if (UpdataInfo.getVersionCode() > LocalVersion(context)) {
				bool = true;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		
		return bool;
	}

	/**
	 * 检查当前版本
	 * 
	 * @return ifUpdate
	 * @throws NameNotFoundException
	 */
	public static int LocalVersion(Context context) throws NameNotFoundException {

		int LocalVersionCode = 1;

		// getPackageName()是你当前类的包名，0代表是获取版本信息
		PackageManager packageManager = context.getPackageManager();
		PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
		LocalVersionCode = packInfo.versionCode;
		return LocalVersionCode;
	}

}
