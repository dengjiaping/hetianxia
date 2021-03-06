package com.htx.main;

import java.util.Timer;
import java.util.TimerTask;
import cn.anycall.map.NearbySearchActivity;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.testviewpagerandtabhost.Home;
import com.example.testviewpagerandtabhost.MainActivity;
import com.hetianxia.activity.R;
import com.htx.action.BMapApiDemoApp;
import com.htx.action.CheckVersion;
import com.htx.pub.ActivityManager;
import com.htx.pub.ApplicationUser;
import com.htx.service.UpdateServicez;
import com.htx.user.U_Login;
import com.htx.user.U_Main;
import com.htx.weixin.SearchGuan;
import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class TabTest extends TabActivity {
	private RadioGroup group;
	private TabHost tabHost;
	BMapApiDemoApp app = null;
	private LocationClient mLocClient;
	private MyLocationListener mLocationListener;
	public static double latDouble=0,lonDouble=0;

	protected void onDestroy() {
		if (mLocClient != null && mLocClient.isStarted()) {
			if (mLocationListener != null) {
				mLocClient.unRegisterLocationListener(mLocationListener);
			}

			mLocClient.stop();
			mLocClient = null;
		}

		super.onDestroy();
	}

	private static final String TAB_0 = "tab0";
	private static final String TAB_1 = "tab1";
	private static final String TAB_2 = "tab2";
	private static final String TAB_3 = "tab3";
	private static final String TAB_4 = "tab4";

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.maintabs);
		group = (RadioGroup) findViewById(R.id.main_radio);
		tabHost = getTabHost();
		tabHost.addTab(tabHost.newTabSpec(TAB_0).setIndicator(TAB_0)
				.setContent(new Intent(this, MainHomeActivity.class)));
		tabHost.addTab(tabHost.newTabSpec(TAB_1).setIndicator(TAB_1)
				.setContent(new Intent(this, NearbySearchActivity.class)));
		tabHost.addTab(tabHost.newTabSpec(TAB_2).setIndicator(TAB_2)
				.setContent(new Intent(this, MainActivity.class)));
		tabHost.addTab(tabHost.newTabSpec(TAB_3).setIndicator(TAB_3)
				.setContent(new Intent(this, SearchGuan.class)));
		tabHost.addTab(tabHost.newTabSpec(TAB_4).setIndicator(TAB_4)
				.setContent(new Intent(this, U_Main.class)));
		group.getChildAt(0).setBackgroundResource(R.drawable.home_btn_bg_n);
		group.getChildAt(1).setBackgroundColor(Color.parseColor("#00000000"));
		group.getChildAt(2).setBackgroundColor(Color.parseColor("#00000000"));
		group.getChildAt(3).setBackgroundColor(Color.parseColor("#00000000"));
		group.getChildAt(4).setBackgroundColor(Color.parseColor("#00000000"));
		
		group.getChildAt(1).setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (ApplicationUser.CheckLoginStatus(TabTest.this)) {
					tabHost.setCurrentTabByTag(TAB_1);
					group.getChildAt(1).setBackgroundResource(R.drawable.home_btn_bg_n);
					group.getChildAt(2).setBackgroundColor(Color.parseColor("#00000000"));
					group.getChildAt(0).setBackgroundColor(Color.parseColor("#00000000"));
					group.getChildAt(3).setBackgroundColor(Color.parseColor("#00000000"));
					group.getChildAt(4).setBackgroundColor(Color.parseColor("#00000000"));
					
				} else {
					startActivity(new Intent(TabTest.this, U_Login.class));
					tabHost.setCurrentTabByTag(TAB_1);
					group.getChildAt(0).setBackgroundResource(R.drawable.home_btn_bg_n);
					group.getChildAt(1).setBackgroundColor(Color.parseColor("#00000000"));
					group.getChildAt(2).setBackgroundColor(Color.parseColor("#00000000"));
					group.getChildAt(3).setBackgroundColor(Color.parseColor("#00000000"));
					group.getChildAt(4).setBackgroundColor(Color.parseColor("#00000000"));
				}
			}
		});
		group.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.radio_button0:
					tabHost.setCurrentTabByTag(TAB_0);
					group.getChildAt(0).setBackgroundResource(R.drawable.home_btn_bg_n);
					group.getChildAt(1).setBackgroundColor(Color.parseColor("#00000000"));
					group.getChildAt(2).setBackgroundColor(Color.parseColor("#00000000"));
					group.getChildAt(3).setBackgroundColor(Color.parseColor("#00000000"));
					group.getChildAt(4).setBackgroundColor(Color.parseColor("#00000000"));
					break;
//				case R.id.radio_button1:
//					if (ApplicationUser.CheckLoginStatus(TabTest.this)) {
//						Intent intent = new Intent();
//						intent.setClassName("com.hetianxia.daohang", "cn.anycall.map.NearbySearchActivity");
//						if (getPackageManager().resolveActivity(intent, 0) ==null ) {
//							Intent intent2 = new Intent(TabTest.this,DownloadActivity.class);
//							try {
//
//								startActivity(intent2);
//							} catch (Exception e) {
//								// TODO: handle exception
//							}
//						}
//						else {
//							startActivity(intent);
//						}
//					} else {
//						startActivity(new Intent(TabTest.this, U_Login.class));
//						tabHost.setCurrentTabByTag(TAB_0);
//						group.getChildAt(0).setBackgroundResource(R.drawable.home_btn_bg_n);
//						group.getChildAt(1).setBackgroundColor(Color.parseColor("#00000000"));
//						group.getChildAt(2).setBackgroundColor(Color.parseColor("#00000000"));
//						group.getChildAt(3).setBackgroundColor(Color.parseColor("#00000000"));
//					}
//					break;
				case R.id.radio_button2:
					if (ApplicationUser.CheckLoginStatus(TabTest.this)) {
						tabHost.setCurrentTabByTag(TAB_2);
						group.getChildAt(2).setBackgroundResource(R.drawable.home_btn_bg_n);
						group.getChildAt(1).setBackgroundColor(Color.parseColor("#00000000"));
						group.getChildAt(0).setBackgroundColor(Color.parseColor("#00000000"));
						group.getChildAt(3).setBackgroundColor(Color.parseColor("#00000000"));
						group.getChildAt(4).setBackgroundColor(Color.parseColor("#00000000"));
					} else {
						startActivity(new Intent(TabTest.this, U_Login.class));
						tabHost.setCurrentTabByTag(TAB_0);
						group.getChildAt(0).setBackgroundResource(R.drawable.home_btn_bg_n);
						group.getChildAt(1).setBackgroundColor(Color.parseColor("#00000000"));
						group.getChildAt(2).setBackgroundColor(Color.parseColor("#00000000"));
						group.getChildAt(3).setBackgroundColor(Color.parseColor("#00000000"));
						group.getChildAt(4).setBackgroundColor(Color.parseColor("#00000000"));
					}
					break;
				case R.id.radio_button3:
					if (ApplicationUser.CheckLoginStatus(TabTest.this)) {
						tabHost.setCurrentTabByTag(TAB_3);
						group.getChildAt(3).setBackgroundResource(R.drawable.home_btn_bg_n);
						group.getChildAt(1).setBackgroundColor(Color.parseColor("#00000000"));
						group.getChildAt(2).setBackgroundColor(Color.parseColor("#00000000"));
						group.getChildAt(0).setBackgroundColor(Color.parseColor("#00000000"));
						group.getChildAt(4).setBackgroundColor(Color.parseColor("#00000000"));
					} else {
						startActivity(new Intent(TabTest.this, U_Login.class));
						tabHost.setCurrentTabByTag(TAB_0);
						group.getChildAt(0).setBackgroundResource(R.drawable.home_btn_bg_n);
						group.getChildAt(1).setBackgroundColor(Color.parseColor("#00000000"));
						group.getChildAt(2).setBackgroundColor(Color.parseColor("#00000000"));
						group.getChildAt(3).setBackgroundColor(Color.parseColor("#00000000"));
						group.getChildAt(4).setBackgroundColor(Color.parseColor("#00000000"));
					}
					break;
				case R.id.radio_button4:
					if (ApplicationUser.CheckLoginStatus(TabTest.this)) {
						tabHost.setCurrentTabByTag(TAB_4);
						group.getChildAt(4).setBackgroundResource(R.drawable.home_btn_bg_n);
						group.getChildAt(1).setBackgroundColor(Color.parseColor("#00000000"));
						group.getChildAt(2).setBackgroundColor(Color.parseColor("#00000000"));
						group.getChildAt(0).setBackgroundColor(Color.parseColor("#00000000"));
						group.getChildAt(3).setBackgroundColor(Color.parseColor("#00000000"));
					} else {
						startActivity(new Intent(TabTest.this, U_Login.class));
						tabHost.setCurrentTabByTag(TAB_0);
						group.getChildAt(0).setBackgroundResource(R.drawable.home_btn_bg_n);
						group.getChildAt(1).setBackgroundColor(Color.parseColor("#00000000"));
						group.getChildAt(2).setBackgroundColor(Color.parseColor("#00000000"));
						group.getChildAt(3).setBackgroundColor(Color.parseColor("#00000000"));
						group.getChildAt(4).setBackgroundColor(Color.parseColor("#00000000"));
					}
					break;
				default:
					break;
				}
			}
		});

		// 初始化
		init();
		// 升级检测
		if (CheckVersion.checkersion(this)) {

			new AlertDialog.Builder(this)
					.setTitle("新版本提醒")
					.setMessage("恭喜您，“合天下”客户端 \n可以升级了，你确定要升级吗?")
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									Intent intent = new Intent(
											UpdateServicez.START);
									intent.putExtra("downPath",
											"http://d.36936.com/htx.apk");
									intent.putExtra("apkName", "合天下");
									startService(intent); // 如果先调用startService
								}
							})
					.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									// 取消按钮事件
								}
							}).show();
		}
	}

	private void init() {
		// 设置GPS
		mLocClient = new LocationClient(this.getApplicationContext());
		mLocationListener = new MyLocationListener();
		mLocClient.registerLocationListener(mLocationListener);
		LocationClientOption option = new LocationClientOption();
		option.disableCache(true);
		option.setOpenGps(true);// 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(1000);
		option.setIsNeedAddress(true);
		option.setPriority(LocationClientOption.GpsFirst);
		mLocClient.setLocOption(option);
		mLocClient.start();

		app = (BMapApiDemoApp) this.getApplication();
		app.mLocClient = mLocClient;

	}

	/**
	 * 判断网络状态
	 * 
	 * @return netSataus
	 */
	public boolean net() {
		ConnectivityManager cwjManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		cwjManager.getActiveNetworkInfo();
		boolean netSataus = false;
		if (cwjManager.getActiveNetworkInfo() != null) {
			netSataus = cwjManager.getActiveNetworkInfo().isAvailable();
		}
		Log.e("网络是否可用：", netSataus + "");
		return netSataus;
	}

	public class MyLocationListener implements BDLocationListener {

		public void onReceiveLocation(BDLocation location) {
			if (location == null)
				return;
			latDouble = location.getLatitude();
			lonDouble = location.getLongitude();
		}

		public void onReceivePoi(BDLocation poiLocation) {
			if (poiLocation == null) {
				return;
			}
		}
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
				Toast.makeText(this, "再按一次将退出", 1500);
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
