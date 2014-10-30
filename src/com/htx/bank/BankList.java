package com.htx.bank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import com.hetianxia.activity.R;
import com.htx.conn.Const;
import com.htx.conn.HttpHelper;
import com.htx.pub.LoadingDialog;
import com.htx.pub.MySharedData;
import com.htx.pub.MyToast;
import com.htx.pub.PubActivity;
import com.htx.search.SiteHelper;

public class BankList extends PubActivity {

	private Button fanhui, add;
	private ListView listView; // listview控件
	private ArrayList<Map<String, String>> contentStrings = new ArrayList<Map<String, String>>(); // 数据源
	private ListViewAdpter adapter; // 适配器
	private String string = "";
	private LoadingDialog dialoga;
	private Handler mHandl = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				adapter = new ListViewAdpter(contentStrings, BankList.this);
				listView.setAdapter(adapter);
				dialoga.dismiss();
				break;
			case 2:
				dialoga.dismiss();
				startActivity(new Intent(BankList.this, AddBank.class));
				finish();
				break;
			case 3:
				// dialoga.dismiss();
				// startActivity(new Intent(BankList.this, InitBankCard.class));
				// finish();

				// 获取手机号、手机串号信息
				TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
				final String tel = tm.getLine1Number();

				if (checkPhone(tel)) {
					new Thread(new Runnable() {
						public void run() {
							getDate(tel.replace("+86", ""));
						}
					}).start();
				} else {
					dialoga.dismiss();
					startActivity(new Intent(BankList.this, InitBankCard.class));
					finish();
				}
				break;
			case 4:
				dialoga.dismiss();
				MyToast.toast(BankList.this, string, 1000);
				break;
			case 5:
				dialoga.dismiss();
				startActivity(new Intent(BankList.this, InitBankCard.class));
				finish();
				break;
			}
			super.handleMessage(msg);
		}

	};

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.banklist);
		listView = (ListView) findViewById(R.id.list);
		fanhui = (Button) findViewById(R.id.fanhui);
		add = (Button) findViewById(R.id.add);
		MySharedData.sharedata_WriteInt(BankList.this, "yinhang", 1);
		fanhui.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
		add.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent(BankList.this, AddBank.class));
				finish();
			}
		});
		dialoga = new LoadingDialog(BankList.this);
		dialoga.show();
		new Thread(new Runnable() {
			public void run() {
				initData(); // 初始化数据
			}
		}).start();
	}

	private void initData() {

		String adUserId = MySharedData.sharedata_ReadString(BankList.this,
				"UserId");
		String hash = SiteHelper.MD5(adUserId + Const.UrlHashKey);
		String url = "http://api.36936.com/ClientApi/Pos/ClientBindBank.ashx?"
				+ "userid=" + adUserId + "&hash=" + hash + "&type=bankList";

		Message message = new Message();
		try {
			String reStr = HttpHelper.getJsonData(BankList.this, url);
			JSONObject reObject = new JSONObject(reStr);
			if (reObject.getInt("status") == 1) {
				JSONObject ob = reObject.getJSONObject("result");
				if (!ob.getString("count").toString().equals("0")) {
					JSONArray list = ob.getJSONArray("list");
					for (int i = 0; i < list.length(); i++) {
						JSONObject nu = (JSONObject) list.opt(i);
						Map<String, String> map = new HashMap<String, String>();
						map.put("bankNo", nu.getString("bankNo"));
						map.put("bankName", nu.getString("bankName"));
						map.put("CardTypeName", nu.getString("CardTypeName"));
						map.put("id", nu.getString("id"));
						contentStrings.add(map);
					}
					message.what = 1;
				} else {
					message.what = 2;
				}
			} else if (reObject.getInt("status") == 2) {
				message.what = 3;
			} else {
				message.what = 4;
			}
		} catch (Exception e) {
			message.what = 2;
			e.printStackTrace();
		}
		mHandl.sendMessage(message);
	}

	private void getDate(String str) {
		String user = MySharedData
				.sharedata_ReadString(BankList.this, "UserId");
		String hash = SiteHelper.MD5(user + Const.UrlHashKey).toLowerCase();
		String url = "http://api.36936.com/ClientApi/Pos/ClientBindBank.ashx?userId="
				+ user + "&hash=" + hash + "&type=info&Mobile=" + str;

		Message message = new Message();
		try {
			String reStr = HttpHelper.getJsonData(BankList.this, url);
			JSONObject reObject = new JSONObject(reStr);
			if (reObject.getInt("status") == 1) {
				message.what = 2;
			} else {
				message.what = 5;
			}
		} catch (Exception e) {
			e.printStackTrace();
			message.what = 4;
			string = "网络异常！";
		}
		mHandl.sendMessage(message);
	}

	// 验证手机号码的正则表达式
	public static boolean checkPhone(String phone) {
		if (phone != null && !phone.equals("")) {
			return false;// 名不合法，返回false
		}
		try {
			String format = "^((13[0-9])|(15[^4,\\D])|(18[0,3-9]))\\d{8}$";
			if (phone.matches(format)) {
				return true;// 名合法，返回true
			} else {
				return false;// 名不合法，返回false
			}
		} catch (Exception e) {
			return false;
		}
	}
}
