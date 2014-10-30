package com.htx.user;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONObject;

import com.hetianxia.activity.R;
import com.htx.app.App_Main;
import com.htx.bank.BankList;
import com.htx.conn.Const;
import com.htx.conn.HttpHelper;
import com.htx.main.DownloadActivity;
import com.htx.pub.ActivityManager;
import com.htx.pub.ApplicationUser;
import com.htx.pub.Integral;
import com.htx.pub.LoadingDialog;
import com.htx.pub.MySharedData;
import com.htx.pub.PubActivity;
import com.htx.pub.WebViews;
import com.htx.search.SiteHelper;
import com.htx.taobao.WebActivity;
import com.zijunlin.Zxing.Demo.CaptureActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * @author lvan
 * 
 */
public class U_Main extends PubActivity implements View.OnClickListener {

	// private String urla = "null";
	static final int RG_REQUEST = 0;// 二维码
	private LinearLayout ll1, ll4, ll6, ll8, ll7, ll9, ll10, ll11, ll12,ll99,
			yaoyiyao, yonghu, download_btn;
	private Button button1, fanhui;
	private String userType;
	private String spreadUrl;
	private String str;

	public void onResume() {
		super.onResume();
		TextView tvYH = (TextView) findViewById(R.id.tvYH);
		tvYH.setText(" (" + MySharedData.sharedata_ReadString(this, "use")
				+ ")");
	}

	private LoadingDialog progressDialog;
	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				progressDialog.dismiss();
				break;
			case 2:
				progressDialog.dismiss();
				Toast.makeText(U_Main.this, "您已领过保险", 3000).show();
				break;
			case 3:
				progressDialog.dismiss();
				Intent intent = new Intent(U_Main.this,U_Baoxian.class);
				startActivity(intent);
				break;
			}
			super.handleMessage(msg);
		}
	};
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_main);

		TextView wyzc = (TextView) findViewById(R.id.wyzc);
		userType = MySharedData.sharedata_ReadString(U_Main.this, "userType");
		if (userType.equals("1") || userType.equals("2")
				|| userType.equals("6")) {
			wyzc.setText("代人注册");
		}

		progressDialog = new LoadingDialog(U_Main.this);
		progressDialog.show();
		new Thread(new Runnable() {
			public void run() {
				// 加载数据
				getData(U_Main.this);
			}
		}).start();

		ll1 = (LinearLayout) findViewById(R.id.img1);
		ll4 = (LinearLayout) findViewById(R.id.img4);
		ll6 = (LinearLayout) findViewById(R.id.img6);
		ll7 = (LinearLayout) findViewById(R.id.img7);
		ll8 = (LinearLayout) findViewById(R.id.img8);
		ll9 = (LinearLayout) findViewById(R.id.img9);
		ll10 = (LinearLayout) findViewById(R.id.img10);
		ll11 = (LinearLayout) findViewById(R.id.img11);
		ll12 = (LinearLayout) findViewById(R.id.img12);
		ll99 = (LinearLayout)findViewById(R.id.img99);

		yaoyiyao = (LinearLayout) findViewById(R.id.yaoyiyao);
		yonghu = (LinearLayout) findViewById(R.id.yonghu);
		fanhui = (Button) findViewById(R.id.fanhui);
		button1 = (Button) findViewById(R.id.button1);
		download_btn = (LinearLayout) findViewById(R.id.download_btn);

		ll1.setOnClickListener(this);
		ll1.setTag(1);
		ll4.setOnClickListener(this);
		ll4.setTag(4);
		ll6.setOnClickListener(this);
		ll6.setTag(6);
		ll7.setOnClickListener(this);
		ll7.setTag(7);
		ll8.setOnClickListener(this);
		ll8.setTag(8);
		ll9.setOnClickListener(this);
		ll9.setTag(9);
		ll10.setOnClickListener(this);
		ll10.setTag(10);
		ll11.setOnClickListener(this);
		ll11.setTag(11);
		ll12.setOnClickListener(this);
		ll12.setTag(12);
		ll99.setOnClickListener(this);
		ll99.setTag(13);
		yaoyiyao.setOnClickListener(this);
		yaoyiyao.setTag(101);
		yonghu.setOnClickListener(this);
		yonghu.setTag(102);
		fanhui.setOnClickListener(this);
		fanhui.setTag(103);
		button1.setOnClickListener(this);
		button1.setTag(105);
		download_btn.setOnClickListener(this);
		download_btn.setTag(104);

		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DATE);
		if (MySharedData.sharedata_ReadInt(U_Main.this, "day") == day) {
			button1.setVisibility(View.GONE);
		}

		// if (MySharedData.sharedata_ReadInt(U_Main.this, "yinhang") == 0) {
		// startActivity(new Intent(U_Main.this, BankList.class));
		// }

	}

	protected void getData2(Context context)
	{
		String adUserId = MySharedData.sharedata_ReadString(context, "UserId");
		String url_str = "http://api.36936.com/ClientApi/ClientCheckSafe.ashx?userId="+adUserId; 
		try {

			String reStr = HttpHelper.getJsonData(context, url_str);
			JSONObject reObject = new JSONObject(reStr);
			if (reObject.getInt("status") == 1) {
				Message message = new Message();
				message.what = 3;
				mHandler.sendMessage(message);
			}
			else {
				Message message = new Message();
				message.what = 2;
				mHandler.sendMessage(message);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	protected void getData(Context context) {

		String adUserId = MySharedData.sharedata_ReadString(context, "UserId");
		String hash = SiteHelper.MD5(adUserId + Const.UrlHashKey);

		String url = "http://api.36936.com/ClientApi/Bonus/ClientBonus.ashx?Type=Account"
				+ "&userid=" + adUserId + "&hash=" + hash;

		try {
			String reStr = HttpHelper.getJsonData(context, url);
			JSONObject reObject = new JSONObject(reStr);

			if (reObject.getInt("status") == 1) {
				MySharedData.sharedata_WriteString(context, "status_",
						reObject.getString("IsBonus"));

				MySharedData.sharedata_WriteString(context, "WordSpecial",
						reObject.getString("WordSpecial"));

				if (reObject.has("UrlMoney")) {
					MySharedData.sharedata_WriteString(context, "UrlMoney",
							reObject.getString("UrlMoney"));
				}
				if (reObject.has("UrlStock")) {
					MySharedData.sharedata_WriteString(context, "UrlStock",
							reObject.getString("UrlStock"));
				}
			} else if (reObject.getInt("status") == 0) {
				MySharedData.sharedata_WriteString(context, "status_",
						reObject.getString("IsBonus"));
				MySharedData.sharedata_WriteString(context, "UrlDef",
						reObject.getString("UrlDef"));
			}

			Message message = new Message();
			message.what = 1;
			mHandler.sendMessage(message);
		} catch (Exception e) {
			Message message = new Message();
			message.what = 1;
			mHandler.sendMessage(message);
			e.printStackTrace();
		}
	}

	public void onClick(View v) {
		int tag = (Integer) v.getTag();
		switch (tag) {
		case 1:
			break;
		case 2:
			break;
		case 3:
			break;
		case 4:
			startActivity(new Intent(U_Main.this, CaptureActivity.class));
			break;
		// case 5:
		// startActivity(new Intent(U_Main.this, WebActivity.class));
		// // startActivity(new Intent(U_Main.this, MyScActivity.class));
		// break;
		case 6:
			// startActivity(new Intent(U_Main.this, MyAdList.class));
			String url31 = "http://click.linktech.cn/? m=tdianping&a=A100137234&l=99999&l_cd1=0&l_cd2=1&u_id="
					+ MySharedData.sharedata_ReadString(this, "UserId")
					+ "&tu=http%3A%2F%2Fwap.dianping.com%2Ftuan";
			Intent inten31 = new Intent(U_Main.this, WebViews.class);
			inten31.putExtra("url", url31);
			startActivity(inten31);

			break;
		case 7:
			break;
		case 8:
			String fen = MySharedData.sharedata_ReadString(U_Main.this,
					"status_");
			str = MySharedData.sharedata_ReadString(U_Main.this, "share__");
			spreadUrl = "http://m.36936.com/p/?u="
					+ ApplicationUser.GetUserId();
			if (fen.equals("0")) {
				Intent intent = new Intent(Intent.ACTION_SEND);
				intent.setType("text/plain");
				intent.putExtra(Intent.EXTRA_SUBJECT, "分享");

				intent.putExtra(Intent.EXTRA_TEXT, str + spreadUrl);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivityForResult(
						Intent.createChooser(intent, getTitle()), 119);
			} else {
				final String[] items = { "现金分红", "股票分红" };
				
					new AlertDialog.Builder(U_Main.this).setTitle("选择分享方式")
					.setItems(items, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int item) {
							Intent intent = new Intent(Intent.ACTION_SEND);
							intent.setType("text/plain");
							intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
							if(items[item].equals("现金分红")){
								spreadUrl = "http://m.36936.com/p/?u="
										+ ApplicationUser.GetUserId()+"&b=1";
							}else {
								spreadUrl = "http://m.36936.com/p/?u="
										+ ApplicationUser.GetUserId()+"&b=2";
							}
							str = MySharedData.sharedata_ReadString(
									U_Main.this, "WordSpecial");

							intent.putExtra(Intent.EXTRA_TEXT, str
									+ spreadUrl);
							intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							startActivityForResult(Intent.createChooser(
									intent, getTitle()), 119);
						}
					}).show();// 显示对话框
			}
			break;
		case 9:
			// String url6 =
			// "http://r.m.taobao.com/m3?p=mm_32843449_4088662_14286555&c=1561";
			String url6 = "http://wvs.m.taobao.com";
			Intent intenat = new Intent(U_Main.this, WebActivity.class);
			intenat.putExtra("url", url6);
			startActivity(intenat);
			break;
		case 10:
			// startActivity(new Intent(U_Main.this, WebActivity.class));
			Intent intentt = new Intent();
			intentt.setClassName("com.hetianxia.daohang",
					"cn.anycall.map.LuActivity");
			if (getPackageManager().resolveActivity(intentt, 0) == null) {
				Intent intent2 = new Intent(U_Main.this, DownloadActivity.class);
				try {

					startActivity(intent2);
				} catch (Exception e) {
					// TODO: handle exception
				}
			} else {
				startActivity(intentt);
			}
			break;
		case 11:
			startActivity(new Intent(U_Main.this, App_Main.class));
			break;
		case 12:
			startActivity(new Intent(U_Main.this, BankList.class));
			break;
		case 13:
			progressDialog = new LoadingDialog(U_Main.this);
			progressDialog.show();
			new Thread(new Runnable() {
				public void run() {
					// 加载数据
					getData2(U_Main.this);
				}
			}).start();
			break;
		case 101:
			startActivity(new Intent(U_Main.this, A_shakeActivity.class));
			break;
		case 102:
			Intent intent2 = new Intent(U_Main.this, U_info.class);
			startActivity(intent2);
			break;
		case 103:
			// startActivity(new Intent(U_Main.this, about.class));
			// finish();
			break;
		case 104:
			startActivity(new Intent(U_Main.this, U_info.class));
			break;
		case 105:
			String adUserId = MySharedData.sharedata_ReadString(U_Main.this,
					"UserId");
			Integral.getIntegralData(U_Main.this, 6, true, adUserId);
			Calendar cal = Calendar.getInstance();
			int day = cal.get(Calendar.DATE);
			MySharedData.sharedata_WriteInt(U_Main.this, "day", day);

			button1.setVisibility(View.GONE);
			break;
		default:
			break;
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == RG_REQUEST) {

			if (resultCode == 132) {
				// tv.setVisibility(View.GONE);
			}
			if (resultCode == 133) {
				/*
				 * String mString = (String)
				 * data.getCharSequenceExtra("touserid"); EditText giveEditText
				 * = (EditText) findViewById(R.id.gUserid);
				 * 
				 * giveEditText.setText(mString);
				 */
			}
		}

		if (requestCode == 119) {
			// Jy_User_add.getData(U_Main.this, 2);
		}

		super.onActivityResult(requestCode, resultCode, data);

	}

	/**
	 * 推出提醒
	 */
	private static Boolean isExit = false;
	private static Boolean hasTask = false;
	Timer tExit = new Timer();
	TimerTask task = new TimerTask() {
		public void run() {
			isExit = false;
			hasTask = true;
		}
	};

	/**
	 * 退出
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		System.out.println("TabHost_Index.java onKeyDown");
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (isExit == false) {
				isExit = true;
				Toast.makeText(this, "再按一次将退出", 1500).show();
				if (!hasTask) {
					tExit.schedule(task, 2000);
				}
			} else {
				ActivityManager.getAppManager().AppExit(this);
			}
		}
		return false;
	}

}
