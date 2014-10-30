package com.htx.pub;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 
 * @author Ivan
 * 
 * @todo 操作MySharedPreferences类
 * 
 */
public class MySharedData {

	// SharedPreferences 数据库名字
	private static String date = "htf"; 

	/**
	 * 存储数据到SharedPreferences中
	 * 
	 * @param key
	 * @param value
	 */
	public static void sharedata_WriteString(Context context,String key, String value) {
		SharedPreferences.Editor sharedataEditor = context.getSharedPreferences(date, 0).edit();
		sharedataEditor.putString(key, value);
		sharedataEditor.commit();
	}

	/**
	 * 读取SharedPreferences中需要的数据
	 * 
	 * @param key
	 * @return value
	 */
	public static String sharedata_ReadString(Context context,String key) {
		SharedPreferences sharedata = context.getSharedPreferences(date, 0);
		return sharedata.getString(key, "");
	}

	/**
	 * 存储数据到SharedPreferences中
	 * 
	 * @param key
	 * @param value
	 */
	public static void sharedata_WriteInt(Context context,String key, int value) {
		SharedPreferences.Editor sharedataEditor = context.getSharedPreferences(date, 0).edit();
		sharedataEditor.putInt(key, value);
		sharedataEditor.commit();
	}

	/**
	 * 读取SharedPreferences中需要的数据
	 * 
	 * @param key
	 * @return value
	 */
	public static int sharedata_ReadInt(Context context,String key) {
		SharedPreferences sharedata = context.getSharedPreferences(date, 0);
		return sharedata.getInt(key, 0);
	}
	
	/**
	 * 存储数据到SharedPreferences中
	 * 
	 * @param key
	 * @param value
	 */
	public static void sharedata_Writefloat(Context context,String key, float value) {
		SharedPreferences.Editor sharedataEditor = context.getSharedPreferences(date, 0).edit();
		sharedataEditor.putFloat(key, value);
		sharedataEditor.commit();
	}

	/**
	 * 读取SharedPreferences中需要的数据
	 * 
	 * @param key
	 * @return value
	 */
	public static float sharedata_Readfloat(Context context,String key) {
		SharedPreferences sharedata = context.getSharedPreferences(date, 0);
		return sharedata.getFloat(key, 0);
	}
	
	/**
	 * 存储数据到SharedPreferences中
	 * 
	 * @param key
	 * @param value
	 */
	public static void sharedata_WriteLong(Context context,String key, long value) {
		SharedPreferences.Editor sharedataEditor = context.getSharedPreferences(date, 0).edit();
		sharedataEditor.putLong(key, value);
		sharedataEditor.commit();
	}

	/**
	 * 读取SharedPreferences中需要的数据
	 * 
	 * @param key
	 * @return value
	 */
	public static long sharedata_ReadLong(Context context,String key) {
		SharedPreferences sharedata = context.getSharedPreferences(date, 0);
		return sharedata.getLong(key, 0);
	}
}
