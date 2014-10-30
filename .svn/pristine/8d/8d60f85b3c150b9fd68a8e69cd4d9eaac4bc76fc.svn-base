package com.htx.pub;

import java.io.File;
import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import com.htx.conn.Const;

public class File_Manage {

	/**
	 * 初始化SD文件夹
	 */
	public static void mkdir(Context context) {

		// 如果SD卡存在
		if (Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED)) {
			// SD卡根目录
			String ROOT_PATH = Environment.getExternalStorageDirectory()
					.getPath();
			if (getAvailaleSize() > 1) {
				File SDRootPath = new File(ROOT_PATH + Const.SDRootPath);
				File SDImagePath = new File(ROOT_PATH + Const.SDImagePath);
				File SDCaChePath = new File(ROOT_PATH + Const.SDCaChePath);
				File SDFilePath = new File(ROOT_PATH + Const.SDFilePath);
				
				try {
					// 如果文件夹不存在
					if (!SDRootPath.exists()) {
						// 按照指定的路径创建文件夹
						SDRootPath.mkdir();
					}
					if (!SDImagePath.exists()) {
						// 按照指定的路径创建文件夹
						SDImagePath.mkdir();
					}
					if (!SDCaChePath.exists()) {
						// 按照指定的路径创建文件夹
						SDCaChePath.mkdir();
					}
					if (!SDFilePath.exists()) {
						// 按照指定的路径创建文件夹
						SDFilePath.mkdir();
					}
				} catch (Exception e) {
				}
			} else {
				MyToast.toast(context,"对不起，您的内存空间不足！", 2000);
			}
		}
	}

	/**
	 * 检测SD卡剩余空间
	 * 
	 * @return availableBlocks * blockSize/1024/1024
	 */
	public static long getAvailaleSize() {

		File root = Environment.getExternalStorageDirectory();
		StatFs stat = new StatFs(root.getPath());
		long blockSize = stat.getBlockSize();
		long availCount = stat.getAvailableBlocks();
		return (availCount * blockSize) / 1024 / 1024; // 获取可用大小(单位 M)
	}

	public static void mkfile(String str_key,Context context,String type)
	{

		// 如果SD卡存在
		if (Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED)) {
			// SD卡根目录
			String ROOT_PATH = Environment.getExternalStorageDirectory()
					.getPath();
			if (getAvailaleSize() > 1) {
				File SDFilePath = new File(ROOT_PATH +  "/.hetao/caChe/"+str_key);
				if (type.equals("0")) {
					try {
						// 如果文件夹不存在
						if (!SDFilePath.exists()) {
							// 按照指定的路径创建文件夹
							SDFilePath.mkdir();
						}
					} catch (Exception e) {
					}
				}
				else {
					File ufile = new File(ROOT_PATH +  "/.hetao/caChe/"+str_key);
					if (! ufile.exists()) {
						try {
							ufile.createNewFile();
						} catch (Exception e) {
							// TODO: handle exception
						}
					}
					
				}
			} else {
				MyToast.toast(context,"对不起，您的内存空间不足！", 2000);
			}
		}
	}
}
