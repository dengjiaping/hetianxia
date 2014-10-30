package com.htx.app;

import java.io.File;
import java.util.Calendar;
import com.htx.conn.Const;
import com.htx.pub.MySharedData;
import com.htx.pub.MyToast;
import com.htx.search.SiteHelper;
import com.htx.service.UpdateServicez;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Environment;

/**
 * 启动一个app
 * 
 * @author Ivan
 * 
 */
public class StartOneApp {

	static Context context;

	/**
	 * 启动
	 */
	public static void StartApk(final Context c, final String apkName,
			final String downPath, final String packageName,
			final String ActivityName) {
		if (apkName == null || downPath == null || downPath == null
				|| packageName == null || ActivityName == null) {
			return;
		}
		context = c;

		if (!SiteHelper.checkApkExist(context, packageName)) {

			File file = new File(Environment.getExternalStorageDirectory(),
					Const.SDCaChePath + "/" + apkName + ".apk");
			if (file.exists()) {
				// installApk(context, file);
				file.delete();
				return;
			}

			AlertDialog.Builder builder = new Builder(context);
			builder.setMessage("您将安装 《" + apkName + "》 手机客户端，您确定要安装吗？");
			builder.setTitle("温馨提示");
			builder.setPositiveButton("确认",
					new android.content.DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							if (!NetWorkStatus(context)) {
								MyToast.toast(context, "访问网络失败！", 1000);
								return;
							} else {
								dialog.dismiss();
								Intent intent = new Intent(UpdateServicez.START);
								intent.putExtra("downPath", downPath);
								intent.putExtra("apkName", apkName);
								c.startService(intent); // 如果先调用startService
							}
						}
					});

			builder.setNegativeButton("取消",
					new android.content.DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});

			builder.create().show();

		} else {

			Calendar cal = Calendar.getInstance();
			int day = cal.get(Calendar.DATE);
			if (!MySharedData.sharedata_ReadString(context, day + apkName)
					.equals(day + apkName)) {
				
//				String adUserId = MySharedData.sharedata_ReadString(context, "UserId");
//				Integral.getIntegralData(context, 3, true,adUserId);

//				Jy_User_add.getData(context, 2);
				MySharedData.sharedata_WriteString(context, day + apkName, day
						+ apkName);
			}

			ComponentName componentName = new ComponentName(packageName,
					ActivityName);
			Intent intent = new Intent();
			intent.setComponent(componentName);
			intent.setAction(Intent.ACTION_VIEW);
			context.startActivity(intent);

		}
	}

	/**
	 * 判断是否联网
	 * 
	 * @return
	 */
	private static boolean NetWorkStatus(Context context) {

		boolean netSataus = false;
		ConnectivityManager cwjManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		cwjManager.getActiveNetworkInfo();

		if (cwjManager.getActiveNetworkInfo() != null) {
			netSataus = cwjManager.getActiveNetworkInfo().isAvailable();
		}
		return netSataus;
	}
}
