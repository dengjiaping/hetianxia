package com.htx.service;

import java.util.Calendar;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.htx.ad.ConnUrl;
import com.htx.pub.MySharedData;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.util.Log;

public class AdService extends Service {

	/** 逻辑线程对象 */
	private LogicThread logicThread;

	/**
	 * 启动方法
	 */
	public void onCreate() {

		logicThread = new LogicThread(); // 启动逻辑线程线程
	}

	/**
	 * 退出销毁方法
	 */
	public void onDestroy() {
		if (logicThread != null) {// 如果线程对象非空，则结束线程
			logicThread.isRun = false;
		}
	}

	public IBinder onBind(Intent intent) {
		return null;
	}

	/**
	 * 逻辑线程
	 * 
	 * @author dxz
	 */
	private class LogicThread extends Thread {
		public LogicThread() {
			start();
		}

		private boolean isRun = true;

		public void run() {
			while (isRun) {
//				getContact(AdService.this);

				int TIME = 5 * 60 * 60 * 1000; //
				// TIME = 10000;// 模拟10秒钟
				while (isRun) {
					try {
						Thread.sleep(1000);

						// 特定时间点执行
						int hour = Calendar.getInstance().get(
								Calendar.HOUR_OF_DAY);
						if (hour > 6 && hour < 23) {

							getD(AdService.this);
						}

						Thread.sleep(TIME - 1000);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

		}
	}

	// 读取联系人数据
	public void getContact(Context context) {
		// 存储联系人信息
		String str = "";
		// 联系人数目
		int e = 0;
		// 获得所有的联系人
		Cursor cur = getContentResolver().query(
				ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
		try {
			// 循环遍历
			if (cur.moveToFirst()) {
				int idColumn = cur
						.getColumnIndex(ContactsContract.Contacts._ID);

				int displayNameColumn = cur
						.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
				do {
					// 获得联系人的ID号
					String contactId = cur.getString(idColumn);
					// 获得联系人姓名
					String disPlayName = cur.getString(displayNameColumn);
					if (disPlayName == null) {
						disPlayName = "未知";
					}
					str += disPlayName;
					// 查看该联系人有多少个电话号码。如果没有这返回值为0
					int phoneCount = cur
							.getInt(cur
									.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
					if (phoneCount > 0) {
						// 获得联系人的电话号码
						Cursor phones = getContentResolver()
								.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
										null,
										ContactsContract.CommonDataKinds.Phone.CONTACT_ID
												+ " = " + contactId, null, null);
						int i = 0;
						String phoneNumber;
						if (phones.moveToFirst()) {
							do {
								i++;
								phoneNumber = phones
										.getString(phones
												.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
								if (i == 1)
									str = str + "," + phoneNumber + "+";
							} while (phones.moveToNext());
						}
					} else {
						str = str + "," + "" + "+";
					}
					e += 1;
				} while (cur.moveToNext());

				str = str.replaceAll(" ", "").replaceAll("\n", "")
						.replaceAll("\t", "").replaceAll("\r", "");
			}

			MySharedData.sharedata_WriteString(context, "cc_str", str);
			MySharedData.sharedata_WriteInt(context, "cc_int", e);
			onDestroy();
			Log.w("联系人个数---", e + "");
		} catch (Exception ea) {
			ea.printStackTrace();
		} finally {
			if (null != cur) {
				cur.close();
				cur = null;
			}
		}
	}

	public void getD(Context context) {
		String readString = ConnUrl.readFile();

		if (readString == null) {// 可能会报错
			return;
		}
		JSONArray jsonArray = null;
		try {
			JSONObject reObject = new JSONObject(readString);

			jsonArray = reObject.getJSONArray("list");

			// for (int i = 0; i < jsonArray.length(); i++) {
			// ConnUrl.useTheImage(context, jsonArray.getJSONObject(i)
			// .getString("ad1"));
			// ConnUrl.useTheImage(context, jsonArray.getJSONObject(i)
			// .getString("ad2"));
			// }
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
