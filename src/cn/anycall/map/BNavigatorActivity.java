package cn.anycall.map;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.baidu.navisdk.BaiduNaviManager;
import com.baidu.navisdk.comapi.mapcontrol.BNMapController;
import com.baidu.navisdk.comapi.routeplan.BNRoutePlaner;
import com.baidu.navisdk.comapi.setting.BNSettingManager;
import com.baidu.navisdk.comapi.tts.BNTTSPlayer;
import com.baidu.navisdk.comapi.tts.BNavigatorTTSPlayer;
import com.baidu.navisdk.comapi.tts.IBNTTSPlayerListener;
import com.baidu.navisdk.model.datastruct.LocData;
import com.baidu.navisdk.model.datastruct.SensorData;
import com.baidu.navisdk.ui.routeguide.BNavigator;
import com.baidu.navisdk.ui.routeguide.IBNavigatorListener;
import com.baidu.navisdk.ui.widget.RoutePlanObserver;
import com.baidu.navisdk.ui.widget.RoutePlanObserver.IJumpToDownloadListener;
import com.baidu.nplatform.comapi.map.MapGLSurfaceView;
import com.hetianxia.activity.R;
import com.htx.pub.MySharedData;

public class BNavigatorActivity extends Activity {

	private int conut = 0;
	String[] ttstext = { "合天下"};
	private RelativeLayout layout;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 创建NmapView
		MapGLSurfaceView nMapView = BaiduNaviManager.getInstance()
				.createNMapView(this);
		
		// 创建导航视图
		View navigatorView = BNavigator.getInstance().init(
				BNavigatorActivity.this, getIntent().getExtras(), nMapView);

		layout = new RelativeLayout(this);
		String ttst=MySharedData.sharedata_ReadString(BNavigatorActivity.this, "aabusName");
		if(!ttst.equals("")){
			String [] ttsts = ttst.split("，");
			ttstext =ttsts;
		}
		
		RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		lp1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		lp1.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		lp1.rightMargin=0;
		lp1.topMargin=0;
		navigatorView.setId(1);
		navigatorView.setLayoutParams(lp1);		 
		layout.addView(navigatorView);
		
		ImageView logoView = new ImageView(this);
		logoView.setImageResource(R.drawable.man_logo);//设置图片
		RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		lp2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);//与父容器的左侧对齐
		lp2.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);//与父容器的上侧对齐
		lp2.rightMargin=10;
		lp2.bottomMargin=110;
		logoView.setId(2);//设置这个View 的id 
		logoView.setLayoutParams(lp2);//设置布局参数
		layout.addView(logoView);//RelativeLayout添加子View
		
		// 填充视图
		//setContentView(navigatorView);
		setContentView(layout);
		BNavigator.getInstance().setListener(mBNavigatorListener);
		BNavigator.getInstance().startNav();


		BNSettingManager.getInstance(this).setsNaviRealHistoryITS(true);
		BNSettingManager.getInstance(this).setNaviAutoUpdateNewData(true);
		// 初始化TTS. 开发者也可以使用独立TTS模块，不用使用导航SDK提供的TTS
		BNTTSPlayer.initPlayer();
		// 设置TTS播放回调
		BNavigatorTTSPlayer.setTTSPlayerListener(new IBNTTSPlayerListener() {

			public int playTTSText(String arg0, int arg1) {
				conut++;
				int ttst = new Random().nextInt(ttstext.length);
				// 开发者可以使用其他TTS的API
				if (conut == 2) {
					return BNTTSPlayer.playTTSText(arg0, arg1);
				} else if (conut == 3) {
					return BNTTSPlayer.playTTSText(arg0, arg1);
				} else {
					return BNTTSPlayer.playTTSText(ttstext[ttst] + "提醒您，"
							+ arg0, arg1);
				}
			}

			public void phoneHangUp() {
				// 手机挂断
			}

			public void phoneCalling() {
				// 通话中
			}

			public int getTTSState() {
				// 开发者可以使用其他TTS的API,
				return BNTTSPlayer.getTTSState();
			}
		});

		BNRoutePlaner.getInstance().setObserver(
				new RoutePlanObserver(this, new IJumpToDownloadListener() {

					public void onJumpToDownloadOfflineData() {

					}
				}));

	}

	private IBNavigatorListener mBNavigatorListener = new IBNavigatorListener() {

		public void onYawingRequestSuccess() {
			// TODO 偏航请求成功
		}

		public void onYawingRequestStart() {
			// TODO 开始偏航请求
		}

		public void onPageJump(int jumpTiming, Object arg) {
			// TODO 页面跳转回调
			if (IBNavigatorListener.PAGE_JUMP_WHEN_GUIDE_END == jumpTiming) {
				finish();
			} else if (IBNavigatorListener.PAGE_JUMP_WHEN_ROUTE_PLAN_FAIL == jumpTiming) {
				finish();
			}
		}

		public void notifyGPSStatusData(int arg0) {
		}

		public void notifyLoacteData(LocData arg0) {
			System.out.println("_________定位________"+String.valueOf(arg0.latitude));
			System.out.println("_________定位________"+String.valueOf(arg0.longitude));
		}

		public void notifyNmeaData(String arg0) {
		}

		public void notifySensorData(SensorData arg0) {
		}

		public void notifyStartNav() {
			BaiduNaviManager.getInstance().dismissWaitProgressDialog();
		}

		public void notifyViewModeChanged(int arg0) {
		}

	};

	@Override
	public void onResume() {
		BNavigator.getInstance().resume();
		super.onResume();
		BNMapController.getInstance().onResume();
		 conut = 1;
	};
	@Override
	public void onPause() {
		BNavigator.getInstance().pause();
		super.onPause();
		BNMapController.getInstance().onPause();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		BNavigator.getInstance().onConfigurationChanged(newConfig);
		super.onConfigurationChanged(newConfig);
	}

	public void onBackPressed() {
		BNavigator.getInstance().onBackPressed();
	}

	@Override
	public void onDestroy() {
		BNavigator.getInstance().destory();
		BNRoutePlaner.getInstance().setObserver(null);
		super.onDestroy();
		BNMapController.getInstance().destory();
	}
}
