package com.htx.boot;

import java.io.File;
import java.util.List;
import com.htx.action.GetWelcomeSet;
import com.htx.conn.Const;
import com.htx.main.TabTest;
import com.htx.pub.ApplicationUser;
import com.htx.pub.File_Manage;
import com.htx.pub.MySharedData;
import com.htx.pub.PubActivity;
import com.htx.service.AdService;
import com.htx.service.MessageService;
import com.htx.user.about;
import android.app.ActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Parcelable;
import android.os.StatFs;
import com.hetianxia.activity.R;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * @author lvan
 * 
 * @todo 引导页面
 * 
 */
public class BootActivity extends PubActivity implements OnViewChangeListener {

	private MyScrollLayout mScrollLayout;
	private ImageView[] imgs;
	private int count;
	private int currentItem;
	private Button startBtn;
	private Handler mHandler = new Handler();
	// onResume时注册此listener，onPause时需要Remove
	private LinearLayout pointLLayout;

	// private BMapApiDemoApp app = null;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.boot);
        
		mScrollLayout = (MyScrollLayout) findViewById(R.id.ScrollLayout);
		pointLLayout = (LinearLayout) findViewById(R.id.llayout);
		startBtn = (Button) findViewById(R.id.startBtn);

		mHandler.postDelayed(new Runnable() {
			public void run() {
				Intent intent = getIntent();
				if (intent.getStringExtra("110") == null
						|| !intent.getStringExtra("110").endsWith("110")) {
					initView();
				} else {
					count = mScrollLayout.getChildCount();
					imgs = new ImageView[count];
					for (int i = 0; i < count; i++) {
						imgs[i] = (ImageView) pointLLayout.getChildAt(i);
						imgs[i].setEnabled(true);
						imgs[i].setTag(i);
					}
					currentItem = 0;
					imgs[currentItem].setEnabled(false);
					mScrollLayout.SetOnViewChangeListener(BootActivity.this);

					startBtn.setOnClickListener(new OnClickListener() {
						public void onClick(View v) {
							// 跳转页面
							startActivity(new Intent(BootActivity.this,
									about.class));
						}
					});
				}
			}
		}, 1000);
	}

	/**
	 * 初始化 数据
	 * 
	 * @author Ivan
	 */
	private void initView() {

		// 设置启动次数
		MySharedData
				.sharedata_WriteInt(BootActivity.this, "htx_ru1", MySharedData
						.sharedata_ReadInt(BootActivity.this, "htx_ru1") + 1);

		// 如果第一次启动
		if (MySharedData.sharedata_ReadInt(BootActivity.this, "htx_ru1") == 1) {

			mScrollLayout.setVisibility(View.VISIBLE);
			pointLLayout.setVisibility(View.VISIBLE);

			count = mScrollLayout.getChildCount();
			imgs = new ImageView[count];
			for (int i = 0; i < count; i++) {
				imgs[i] = (ImageView) pointLLayout.getChildAt(i);
				imgs[i].setEnabled(true);
				imgs[i].setTag(i);
			}
			currentItem = 0;
			imgs[currentItem].setEnabled(false);
			mScrollLayout.SetOnViewChangeListener(this);


			Intent addShortCut = new Intent(  
	                "com.android.launcher.action.INSTALL_SHORTCUT");  
	        // 加载app名  
	        String title = getResources().getString(R.string.app_name);  
	        // 加载app的logo  
	        Parcelable icon = Intent.ShortcutIconResource.fromContext(  
	                BootActivity.this, R.drawable.iii);  
	        //点击快捷方式后操作Intent,快捷方式建立后，再次启动该程序  
	        Intent intent = new Intent(BootActivity.this, BootActivity.class);  
	        //设置快捷方式的标题  
	        addShortCut.putExtra(Intent.EXTRA_SHORTCUT_NAME, title);  
	        //设置快捷方式的图标  
	        addShortCut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);  
	        //设置快捷方式对应的Intent  
	        addShortCut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent);  
	        //发送广播添加快捷方式  
	        sendBroadcast(addShortCut);  
	        
			startBtn.setOnClickListener(onClick);
			// 第二次以后 启动要执行的程序
		} else {
			loadData();
		}
	}

	/**
	 * 跳转
	 * 
	 * @author Ivan
	 */
	private View.OnClickListener onClick = new View.OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.startBtn:
				// 第一次运行时要加载的数据
				firstLoadData();

				break;
			}
		}
	};

	public void OnViewChange(int position) {
		setcurrentPoint(position);
	}

	private void setcurrentPoint(int position) {
		if (position < 0 || position > count - 1 || currentItem == position) {
			return;
		}
		imgs[currentItem].setEnabled(true);
		imgs[position].setEnabled(false);
		currentItem = position;
	}

	/**
	 * 第一次启动时 要加载的数据
	 * 
	 * @author Ivan
	 */
	private void firstLoadData() {

		// 加载手机屏宽
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		// 保存手机屏宽到sharedata
		MySharedData.sharedata_WriteLong(BootActivity.this, "widthPixels",
				dm.widthPixels);
		// 保存手机屏宽到sharedata
		MySharedData.sharedata_WriteInt(BootActivity.this, "WwW",
				dm.widthPixels);
		MySharedData.sharedata_WriteLong(BootActivity.this, "heightPixels",
				dm.heightPixels);
//		new Thread(new Runnable() {
//			public void run() {
//				createShortCut(); // 初始化数据
//			}
//		}).start();
		loadData();

	}

	/**
	 * 加载缓存数据
	 * 
	 * @author Ivan
	 */
	private void loadData() {

		// 判断自动升级
		GetWelcomeSet getWelcomeSet = new GetWelcomeSet(BootActivity.this);
		getWelcomeSet.getImageUrlData(Const.APKURL+MySharedData.sharedata_ReadString(BootActivity.this,
				"UserId"));

		// 加载广告目录文件
//		getAdsData();
		// 初始化SD目录
		File_Manage.mkdir(BootActivity.this);
		new Thread(new Runnable() {
			public void run() {

				// 加载登录状态
				try {
					new ApplicationUser(BootActivity.this);
				} catch (Exception e) {
					e.printStackTrace();
				}

				// 检查看广告的服务是否启动
				checkAdServiceIsStart();
				// 检查消息服务是否启动
				checkServiceIsStart();
				// 定位当前位置
				// mLocation();
				// 跳转页面
				startActivity(new Intent(BootActivity.this, TabTest.class));
				finish();
			}
		}).start();
	}

	/**
	 * 检查消息服务是否启动，如果没有启动则启动
	 */
	private void checkServiceIsStart() {
		ActivityManager mActivityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		List<ActivityManager.RunningServiceInfo> mServiceList = mActivityManager
				.getRunningServices(30);
		// 消息服务名字
		final String musicClassName = "com.htx.service.MessageService";
		boolean b = MessageServiceIsStart(mServiceList, musicClassName);
		if (!b) {// 未启动则启动
			Intent myIntent = new Intent(this, MessageService.class);
			this.startService(myIntent);
		}
	}

	/**
	 * 检查广告服务是否启动，如果没有启动则启动
	 */
	private void checkAdServiceIsStart() {
		ActivityManager mActivityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		List<ActivityManager.RunningServiceInfo> mServiceList = mActivityManager
				.getRunningServices(30);
		// 消息服务名字
		final String musicClassName = "com.htx.service.AdService";
		boolean b = MessageServiceIsStart(mServiceList, musicClassName);

		if (!b) {// 未启动则启动
			Intent myIntent = new Intent(this, AdService.class);
			this.startService(myIntent);
		}
	}

	// 通过Service的类名来判断是否启动某个服务
	private boolean MessageServiceIsStart(
			List<ActivityManager.RunningServiceInfo> mServiceList,
			String className) {
		for (int i = 0; i < mServiceList.size(); i++) {
			if (className.equals(mServiceList.get(i).service.getClassName())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 初始化SD文件夹
	 */
	private void mkdir() {

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
				Toast("对不起，您的内存空间不足！", 2000);
				finish();
			}
		}
	}

	/**
	 * 检测SD卡剩余空间
	 * 
	 * @return availableBlocks * blockSize/1024/1024
	 */
	public long getAvailaleSize() {

		File root = Environment.getExternalStorageDirectory();
		StatFs stat = new StatFs(root.getPath());
		long blockSize = stat.getBlockSize();
		long availCount = stat.getAvailableBlocks();
		return (availCount * blockSize) / 1024 / 1024; // 获取可用大小(单位 M)
	}

//	/**
//	 * 获取广告数据
//	 * 
//	 * @param x
//	 * @param y
//	 */
//	public void getAdsData() {
//
//		String adUserId = MySharedData.sharedata_ReadString(BootActivity.this,
//				"UserId");
//		// String x = MySharedData.sharedata_ReadString(BootActivity.this, "x");
//		// String y = MySharedData.sharedata_ReadString(BootActivity.this, "y");
//
//		double x = 0.0;// 经度
//		double y = 0.0;// 纬度
//
//		String hash = SiteHelper.MD5(adUserId + Const.UrlHashKey);
//		String url = Const.UrlAdGetAdList + "?userid=" + adUserId + "&hash="
//				+ hash + "&addressids=" + x + "*" + y;
//		ConnUrl.getData(this, url);
//
//	}

	// private void mLocation() {
	// app = (BMapApiDemoApp) this.getApplication();
	// if (app.mBMapMan == null) {
	// app.mBMapMan = new BMapManager(getApplication());
	// app.mBMapMan.init(app.mStrKey,
	// new BMapApiDemoApp.MyGeneralListener());
	// }
	// // 注册定位事件
	// mLocationListener = new LocationListener() {
	// public void onLocationChanged(Location location) {
	//
	// double x = 0.0;// 经度
	// double y = 0.0;// 纬度
	//
	// if (location != null) {
	//
	// if (ifMapShow == false) {
	// y = location.getLongitude();
	// x = location.getLatitude();
	//
	// MySharedData.sharedata_WriteString(BootActivity.this,
	// "x", x + "");
	// MySharedData.sharedata_WriteString(BootActivity.this,
	// "y", y + "");
	//
	// Log.i("AA", x + "   :  " + y);
	// ifMapShow = true;
	// // 注销
	// app.mBMapMan.getLocationManager().removeUpdates(
	// mLocationListener);
	// app.mBMapMan.stop();
	// }
	// }
	// }
	// };
	//
	// app.mBMapMan.getLocationManager().requestLocationUpdates(
	// mLocationListener);
	// app.mBMapMan.start();
	// }

//	public void createShortCut() {
//		// 创建快捷方式的Intent
//		Intent shortcutintent = new Intent(
//				"com.android.launcher.action.INSTALL_SHORTCUT");
//		// 不允许重复创建
//		shortcutintent.putExtra("duplicate", false);
//		// 需要现实的名称
//		shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_NAME,
//				getString(R.string.app_name));
//		// 快捷图片
//		Parcelable icon = Intent.ShortcutIconResource.fromContext(
//				getApplicationContext(), R.drawable.iii);
//		shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
//		// 点击快捷图片，运行的程序主入口
//		shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent(
//				getApplicationContext(), TabTest.class));
//		// 发送广播。OK
//		sendBroadcast(shortcutintent);
//	}
}