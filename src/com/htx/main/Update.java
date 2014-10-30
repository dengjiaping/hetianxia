package com.htx.main;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import com.htx.conn.Const;
import com.htx.pub.LoadingDialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Log;
import android.webkit.URLUtil;

/**
 * 
 * 版本检测，自动更新 1.通过Url检测更新 2.下载并安装更新 3.删除临时路径
 * 
 * @author TUFU
 * 
 */

public class Update {

	// 调用更新的Activity
	public Activity activity = null;
	// 控制台信息标识
	private static final String TAG = "Update";
	// 文件当前路径
	private String currentFilePath = "";
	// 安装包文件临时路径
	private String currentTempFilePath = "";
	// 获得文件扩展名字符串
	private String fileEx = "";
	// 获得文件名字符串
	private String fileNa = "";

	private LoadingDialog dialog;

	public Update(Activity activity) {
		this.activity = activity;
	}

	/**
	 * 
	 * 检测
	 */
	public void check() {
		Log.d("aa", "检测网络");
		// 检测网络
		if (!isNetworkAvailable(this.activity)) {
			return;
		}

		showUpdateDialog();
	}

	/**
	 * 
	 * 检测是否有可用网络
	 * 
	 * 
	 * @param context
	 * 
	 * @return 网络连接状态
	 */

	public static boolean isNetworkAvailable(Context context) {
		try {
			ConnectivityManager cm = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			// 获取网络信息
			NetworkInfo info = cm.getActiveNetworkInfo();
			// 返回检测的网络状态
			return (info != null && info.isConnected());
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * 
	 * 弹出对话框，选择是否需要更新版本
	 */
	public void showUpdateDialog() {

		new AlertDialog.Builder(this.activity).setTitle("“合天下”语音包")
				.setMessage("  使用语音搜索功能,\n请先下载安装语音插件！")
				.setPositiveButton("下载", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// 通过地址下载文件
						downloadTheFile(Const.UrlUserYuyin);
						// 显示更新状态，进度条
						showWaitDialog();
					}
				}).show();
	}

	/**
	 * 
	 * 显示更新状态，进度条
	 */

	public void showWaitDialog() {
		dialog = new LoadingDialog(activity);
		dialog.setCancelable(true);
		dialog.show();
	}

	/**
	 * 
	 * 截取文件名称并执行下载
	 * 
	 * 
	 * @param strPath
	 */
	private void downloadTheFile(final String strPath) {
		// 获得文件文件扩展名字符串
		fileEx = strPath.substring(strPath.lastIndexOf(".") + 1,
				strPath.length()).toLowerCase();
		// 获得文件文件名字符串
		fileNa = strPath.substring(strPath.lastIndexOf("/") + 1,
				strPath.lastIndexOf("."));
		try {
			if (strPath.equals(currentFilePath)) {
				doDownloadTheFile(strPath);
			}
			currentFilePath = strPath;
			new Thread(new Runnable() {
				public void run() {
					// TODO Auto-generated method stub
					try {

						// 执行下载
						doDownloadTheFile(strPath);
					} catch (Exception e) {
						Log.d(TAG + "1", e.getMessage(), e);
					}
				}
			}).start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * 执行新版本进行下载，并安装
	 * 
	 * 
	 * @param strPath
	 * 
	 * @throws Exception
	 */
	private void doDownloadTheFile(String strPath) throws Exception {
		// 判断strPath是否为网络地址
		if (!URLUtil.isNetworkUrl(strPath)) {
			Log.i(TAG, "服务器地址错误！");
		} else {
			URL myURL = new URL(strPath);
			URLConnection conn = myURL.openConnection();
			conn.connect();
			InputStream is = conn.getInputStream();
			if (is == null) {
				throw new RuntimeException("stream is null");
			}
			// 生成一个临时文件
			File myTempFile = File.createTempFile(fileNa, "." + fileEx);
			// 安装包文件临时路径
			currentTempFilePath = myTempFile.getAbsolutePath();
			FileOutputStream fos = new FileOutputStream(myTempFile);
			byte buf[] = new byte[128];
			do {
				int numread = is.read(buf);
				if (numread <= 0) {
					break;
				}
				fos.write(buf, 0, numread);
			} while (true);
			Log.i(TAG, "getDataSource() Download ok...");
			dialog.cancel();
			dialog.dismiss();
			// 打开文件
			openFile(myTempFile);
			try {
				is.close();
			} catch (Exception ex) {
				Log.e(TAG, "getDataSource() error: " + ex.getMessage(), ex);
			}
		}
	}

	/**
	 * 
	 * 打开文件进行安装
	 * 
	 */
	private void openFile(File f) {
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(android.content.Intent.ACTION_VIEW);
		// 获得下载好的文件类型
		String type = getMIMEType(f);
		// 打开各种类型文件
		intent.setDataAndType(Uri.fromFile(f), type);
		// 安装
		activity.startActivity(intent);
	}

	/**
	 * 
	 * 删除临时路径里的安装包
	 */

	public void delFile() {
		Log.i(TAG, "The TempFile(" + currentTempFilePath + ") was deleted.");
		File myFile = new File(currentTempFilePath);
		if (myFile.exists()) {
			myFile.delete();
		}
	}

	/**
	 * 
	 * 获得下载文件的类型
	 * 
	 * 文件名称
	 * 
	 * @return 文件类型
	 */

	private String getMIMEType(File f) {
		String type = "";
		// 获得文件名称
		String fName = f.getName();
		// 获得文件扩展名
		String end = fName
				.substring(fName.lastIndexOf(".") + 1, fName.length())
				.toLowerCase();
		if (end.equals("m4a") || end.equals("mp3") || end.equals("mid")
				|| end.equals("xmf") || end.equals("ogg") || end.equals("wav")) {
			type = "audio";
		} else if (end.equals("3gp") || end.equals("mp4")) {
			type = "video";
		} else if (end.equals("jpg") || end.equals("gif") || end.equals("png")
				|| end.equals("jpeg") || end.equals("bmp")) {
			type = "image";
		} else if (end.equals("apk")) {
			type = "application/vnd.android.package-archive";
		} else {
			type = "*";
		}
		if (end.equals("apk")) {
		} else {
			type += "/*";
		}
		return type;
	}

}
