package com.htx.service;

import org.json.JSONObject;
import com.hetianxia.activity.R;
import com.htx.conn.Const;
import com.htx.pub.MySharedData;
import com.htx.conn.HttpHelper;
import com.htx.search.SiteHelper;
import com.htx.weixin.ChatMainActivity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * 获取优惠信息的后台服务
 * 
 * @author Ivan
 * 
 */
public class MessageService extends Service {
	/** 标题栏消息管理对象 */
	private static NotificationManager mNM;
	/** 逻辑线程对象 */
	private LogicThread logicThread;

	/**
	 * 启动方法
	 */
	public void onCreate() {
		mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE); // 创建消息管理对象
		logicThread = new LogicThread(); // 启动逻辑线程线程
	}

	/**
	 * 获取消息数据
	 * 
	 */
	public void getData() {
		String user = MySharedData.sharedata_ReadString(MessageService.this,
				"UserId");
		String hash = SiteHelper.MD5(user + Const.UrlHashKey).toLowerCase();
		String url = Const.getStoreChat + "?userId=" + user + "&hash=" + hash;

		// 这里需要分析服务器回传的json格式数据，
		try {
			Log.e("meg", "|--->> " + url);
			String reStr = HttpHelper.getJsonData(this, url);
			JSONObject reObject = new JSONObject(reStr);
			Log.e("meg", "|-+->> " + reStr);
			if (reObject.getInt("status") == 1) {
				int imsg = MySharedData.sharedata_ReadInt(this, "imsg");
				if (imsg == 0) {
					MySharedData.sharedata_WriteInt(this, "imsg", 1);
				} else {
					MySharedData.sharedata_WriteInt(this, "imsg", imsg + 1);
				}

				if (reObject.getInt("status") == 1) {
					JSONObject ob = reObject.getJSONObject("result");
					org.json.JSONArray reArray = ob.getJSONArray("list");
					for (int i = 0; i < reArray.length(); i++) {
						String re = reArray.opt(i).toString();
						showNotification("恭喜，您有新消息！", re ,i);
					}}
//				showNotification("恭喜，您有新消息！", reStr);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

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
	 * 逻辑线程 实现：每隔5分钟发送一次当前位置信息给服务器，并获得服务器端的优惠信息反馈，并进行对应的逻辑处理
	 * 
	 * @author Ivan
	 */
	private class LogicThread extends Thread {
		public LogicThread() {
			start();
		}

		private boolean isRun = true;

		public void run() {
			try {
				final int TIME = 1 * 60 * 1000; //
				while (isRun) {
					Thread.sleep(TIME);
//					if (MySharedData.sharedata_ReadInt(MessageService.this,
//							"isMSN") == 1) {
						getData();
//					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 显示提示信息
	 * 
	 * @param text
	 *            提示信息
	 */
	@SuppressWarnings("deprecation")
	public void showNotification(String text, String ids,int i) {

		// 参数依次是：图标、提示信息内容，当前时间
		Notification notification = new Notification(R.drawable.iii, text,
				System.currentTimeMillis());
		notification.defaults = Notification.DEFAULT_SOUND;// 默认的声音
		notification.flags = Notification.FLAG_AUTO_CANCEL;
		// 打开的Activity对象
		Intent intent = new Intent(this, ChatMainActivity.class);
		System.out.println("ids___________"+ids);
		intent.putExtra("ids", ids);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_NEW_TASK);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				intent, PendingIntent.FLAG_UPDATE_CURRENT); //
		// 这个参数为了解决重复问题
		notification.setLatestEventInfo(this, this.getText(R.string.app_name),
				text, contentIntent);
		// 显示提示信息
		mNM.notify(i, notification);
	}
}
