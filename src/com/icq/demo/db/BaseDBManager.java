package com.icq.demo.db;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

/**
 * 数据库管理工具
 * @author Alan
 */
public class BaseDBManager {
	private final int BUFFER_SIZE = 1024; // 占用字符串
	private String DB_NAME = null; // 数据库名称
	public String PACKAGE_NAME = null; // 包名
	private int DB_RESOURCE=0; //资源指向
	
	public  String DB_PATH = null;
	
	private SQLiteDatabase database;
	private Context context;
	private File file = null;
	public BaseDBManager(Context context,String dbname,String apppackage,int dbResources) {
		this.context = context;
		this.DB_NAME=dbname;
		this.PACKAGE_NAME=apppackage;
		this.DB_PATH="/data"+ Environment.getDataDirectory().getAbsolutePath() + "/"+ PACKAGE_NAME;
		this.DB_RESOURCE=dbResources;
	}

	/**
	 * 打开数据库
	 */
	public void openDatabase() {
		this.database = this.openDatabase(DB_PATH + "/" + DB_NAME);
	}

	/**
	 * 获取数据库
	 * 
	 * @return
	 */
	public SQLiteDatabase getDatabase() {
		return this.database;
	}

	/**
	 * 根据指定路径打开数据库
	 * 
	 * @param dbfile
	 * @return
	 */
	private SQLiteDatabase openDatabase(String dbfile) {
		try {
			file = new File(dbfile);
			if (!file.exists()) {
				InputStream is = context.getResources().openRawResource(DB_RESOURCE);
				if (is != null) {
					Log.e("是否读成功", "成功！");
				} else {
				}
				FileOutputStream fos = new FileOutputStream(dbfile);
				if (is != null) {
					Log.e("error", "fosnull");
				} else {
				}
				byte[] buffer = new byte[BUFFER_SIZE];
				int count = 0;
				while ((count = is.read(buffer)) > 0) {
					fos.write(buffer, 0, count);
					fos.flush();
				}
				fos.close();
				is.close();
			}
			database = SQLiteDatabase.openOrCreateDatabase(dbfile, null);
			return database;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			Log.e("error exception：", "exception " + e.toString());
		}
		return null;
	}

	public void closeDatabase() {
		if (this.database != null)
			this.database.close();
	}
}