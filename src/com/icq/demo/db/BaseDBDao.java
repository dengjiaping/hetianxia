package com.icq.demo.db;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hetianxia.activity.R;
import com.icq.demo.beans.Item;

public class BaseDBDao {
	private static String dbname="icq_db";
	private static GYMDatabase mdb;
	private static SQLiteDatabase db;
	private static String apppackage="com.example.testviewpagerandtabhost";
	private static int dbRec=R.raw.icq_db;

    /**
     * 鑾峰彇鐪�
     * @param context
     * @return
     */
	public static List<Item> getProvinces(Context context){
		mdb = new GYMDatabase(context, dbname, apppackage, dbRec);
		mdb.openDatabase();
		db = mdb.getDatabase();
		List<Item> list = new ArrayList<Item>();
		try {
			String sql = "select * from province";
			Cursor cursor = db.rawQuery(sql, null);
			cursor.moveToFirst();
			for (int i = 0; i < cursor.getCount(); i++) {
				String code = cursor.getString(cursor
						.getColumnIndex("ProvinceID"));
				String name = cursor.getString(cursor
						.getColumnIndex("ProvinceName"));
				Item myListItem = new Item();
				myListItem.setName(name);
				myListItem.setId(code);
				list.add(myListItem);
				cursor.moveToNext();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		mdb.closeDatabase();
		db.close();
		
		return list;
	}
	

	/**
	 * 鏍规嵁鐪佺骇id鑾峰彇鍩庡競鍒楄〃
	 * @param pid
	 * @param context
	 * @return
	 */
	public static List<Item> getCitys(String pid,Context context) {
		mdb = new GYMDatabase(context, dbname, apppackage, dbRec);
		mdb.openDatabase();
		db = mdb.getDatabase();
		List<Item> list = new ArrayList<Item>();

		try {
			String sql = "select * from city where ProvinceID='" + pid
					+ "'";
			Cursor cursor = db.rawQuery(sql, null);
			cursor.moveToFirst();
			for (int i = 0; i < cursor.getCount(); i++) {
				String code = cursor.getString(cursor.getColumnIndex("CityID"));
				String name = cursor.getString(cursor.getColumnIndex("CityName"));
				Item myListItem = new Item();
				myListItem.setName(name);
				myListItem.setId(code);
				list.add(myListItem);
				cursor.moveToNext();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		mdb.closeDatabase();
		db.close();

		return list;
	}
	/**
	 * 鏍规嵁鍩庡競id鑾峰彇鍩庨晣
	 * @param cid
	 * @param context
	 * @return
	 */
	public static List<Item> getCountrys(String cid,Context context) {
		mdb = new GYMDatabase(context, dbname, apppackage, dbRec);
		mdb.openDatabase();
		db = mdb.getDatabase();
		List<Item> list = new ArrayList<Item>();

		try {
			String sql = "select * from district where CityID='" + cid
					+ "'";
			Cursor cursor = db.rawQuery(sql, null);
			cursor.moveToFirst();
			for (int i = 0; i < cursor.getCount(); i++) {
				String code = cursor.getString(cursor.getColumnIndex("DistrictID"));
				String name = cursor.getString(cursor.getColumnIndex("DistrictName"));
				Item myListItem = new Item();
				myListItem.setName(name);
				myListItem.setId(code);
				list.add(myListItem);
				cursor.moveToNext();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		mdb.closeDatabase();
		db.close();

		return list;
	}
	

}
