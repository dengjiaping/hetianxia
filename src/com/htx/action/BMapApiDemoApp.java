package com.htx.action;

import com.baidu.location.LocationClient;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.map.MKEvent;
//import com.baidu.mapapi.BMapManager;
//import com.baidu.mapapi.MKGeneralListener;
//import com.baidu.mapapi.map.MKEvent;
import android.app.Application;
import android.content.Context;
import android.os.Environment;

/**
 * 
 * @author lvan
 * 
 */
public class BMapApiDemoApp extends Application {

	private static BMapApiDemoApp mDemoApp;
	public static BMapApiDemoApp getInstance() {
		return mDemoApp;
	}
	Context context;
	//private static BMapApiDemoApp mInstance = null;
	private String filePath;
	public LocationClient mLocClient;
	// 百度MapAPI的管理类
	public BMapManager mBMapMan = null;
	// 授权Key
	public String mStrKey = "FWQbpnznxNL5nSi3KxhjFDp1";
	//public String mStrKey = "ktxLXdeU4iEsVjUnFAyddKms";
	
	boolean m_bKeyRight = true; // 授权Key正确，验证通过

	public Object objectTransfer;
	
	// 常用事件监听，用来处理通常的网络错误，授权验证错误等
	public static class MyGeneralListener implements MKGeneralListener {

		public void onGetNetworkState(int iError) {
			if (iError == MKEvent.ERROR_NETWORK_CONNECT) {
			} else if (iError == MKEvent.ERROR_NETWORK_DATA) {
			}
		}

		public void onGetPermissionState(int iError) {
			// 非零值表示key验证未通过
			if (iError != 0) {
				BMapApiDemoApp.getInstance().m_bKeyRight = false;
			} else {
				BMapApiDemoApp.getInstance().m_bKeyRight = true;
			}
		}
	}

	public void onCreate() {
		super.onCreate();
		context = this;
		mDemoApp = this;
		initEngineManager(this);
		//mDemoApp = this;
		mBMapMan = new BMapManager(context);
		mBMapMan.init(this.mStrKey, new MyGeneralListener());
		
//		CrashHandler crashHandler = CrashHandler.getInstance();
//		crashHandler.init(getApplicationContext());
	}

	public void initEngineManager(Context context) {
		if (mBMapMan == null) {
			mBMapMan = new BMapManager(context);
		}

		if (!mBMapMan.init(mStrKey, new MyGeneralListener())) {
		}
	}

	public static boolean hasSDcard() {
		return Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState());
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	// 建议在您app的退出之前调用mapadpi的destroy()函数，避免重复初始化带来的时间消耗
//	public void onTerminate() {
//		if (mBMapMan != null) {
//			mBMapMan.destroy();
//			mBMapMan = null;
//		}
//		super.onTerminate();
//	}

}
