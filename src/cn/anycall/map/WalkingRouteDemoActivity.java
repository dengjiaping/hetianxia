package cn.anycall.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.Geometry;
import com.baidu.mapapi.map.Graphic;
import com.baidu.mapapi.map.GraphicsOverlay;
import com.baidu.mapapi.map.ItemizedOverlay;
import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.map.MKMapStatus;
import com.baidu.mapapi.map.MKMapTouchListener;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationOverlay;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.mapapi.map.Symbol;
import com.baidu.mapapi.map.MyLocationOverlay.LocationMode;
import com.baidu.mapapi.search.MKAddrInfo;
import com.baidu.mapapi.search.MKBusLineResult;
import com.baidu.mapapi.search.MKDrivingRouteResult;
import com.baidu.mapapi.search.MKPlanNode;
import com.baidu.mapapi.search.MKPoiResult;
import com.baidu.mapapi.search.MKRoute;
import com.baidu.mapapi.search.MKSearch;
import com.baidu.mapapi.search.MKSearchListener;
import com.baidu.mapapi.search.MKShareUrlResult;
import com.baidu.mapapi.search.MKStep;
import com.baidu.mapapi.search.MKSuggestionResult;
import com.baidu.mapapi.search.MKTransitRouteResult;
import com.baidu.mapapi.search.MKWalkingRouteResult;
import com.baidu.mapapi.utils.CoordinateConvert;
import com.baidu.mapapi.utils.DistanceUtil;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.baidu.platform.comapi.map.Projection;
import com.hetianxia.activity.R;
import com.htx.action.BMapApiDemoApp;
import com.htx.action.ProductAction;
import com.htx.bean.RoadInfoResultBean;
import com.htx.bean.RoadInfoSearchBean;
import com.htx.core.AsyncWorkHandler;
import com.iflytek.cloud.speech.SpeechError;
import com.iflytek.cloud.speech.SpeechListener;
import com.iflytek.cloud.speech.SpeechSynthesizer;
import com.iflytek.cloud.speech.SpeechUser;
import com.iflytek.cloud.speech.SynthesizerListener;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Message;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class WalkingRouteDemoActivity extends Activity implements
		SynthesizerListener {

	public static final String GEO_PIONT_LO = "lng";
	public static final String GEO_PIONT_LA = "lat";
	public static final String GEO_PIONT_NAME = "destination";
	public static final String GEO_CITY_END = "GEO_CITY_END";
	public static final String SEARCH_TYPE = "SEARCH_TYPE";
	// 合成对象.
	private com.iflytek.cloud.speech.SpeechSynthesizer mSpeechSynthesizer;

	private static final String[] SCALE_DESCS = { "0米", "5米", "10米", "2000公里",
			"1000公里", "500公里", "200公里", "100公里", "50公里", "25公里", "20公里",
			"10公里", "2公里", "5公里", "1公里", "500米", "200米", "100米", "50米", "20米" };
	private int maxZoomLevel;
	private int minZoomLevel;

	private MediaPlayer mediaPlayer;
	private boolean startroadinfo = true;
	private boolean gettingroadinfo = false;
	private GraphicsOverlay graphicsOverlay = null;
	private ArrayList<RoadInfoResultBean> roadList = null;
	cn.anycall.map.WalkingRouteDemoActivity.AddedItemizedOverlay mItemOverlay = null;
	private TextView roadname = null;
	private TextView roadtip = null;
	private TextView roadlen = null;
	private View popView = null;

	// GPS状态监测
	private GPSStatusNotify gpsStatusNotify;
	// HUD信息
	private TextView textView_distance_tip, textView_tip,
			textView_distance_total, textView_time, text_bili, textView_gps;
	private Button button_change2D3D, button_to_myLocation,
			button_location_mode, button_location_jia, button_location_jian,
			button_location_lukuang;
	private ImageView img_tip;
	private String total_juli, total_time;
	/**
	 * 地图对象
	 */
	private MapView mMapView;
	/**
	 * 地图控制器
	 */
	private MapController mMapController = null;
	// 定位相关
	private LocationClient mLocClient;
	private LocationData locData = null;
	private LocationMode CurrentLocationMode;
	public MyLocationListenner myListener = new MyLocationListenner();

	// 用户登录
	/**
	 * 是否首次定位
	 */
	boolean isFirstLoc = true;

	/**
	 * 定位图层
	 */
	LocationOverlay myLocationOverlay = null;
	BDLocation myLocation;
	// 导航相关

	/**
	 * 保存驾车/步行路线数据的变量，供浏览节点时使用
	 */
	MKRoute route = null;
	/**
	 * 导航图层
	 */
	MyRouteOverlay routeOverlay = null;
	/**
	 * 记录搜索的类型，区分驾车/步行和公交
	 */
	int searchType = -1;//
	// 路径规划搜索相关
	/**
	 * 搜索模块
	 */
	MKSearch mSearch = null; //

	/**
	 * 路线监听器
	 */
	RouteListener routeListener;

	/**
	 * 语音合成组件
	 */
	TTSSupport tts;
	BMapApiDemoApp app = null;
	// 全局保存导航起点目的地检索信息
	String startCity;
	MKPlanNode stNode;
	String endCity;
	MKPlanNode enNode;
	String la_str="";
	String lo_str = "";
	String name_str = "";
	@SuppressLint("ShowToast")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		initUI();
		initDate();
	}

	/**
	 * 用户登录回调监听器.
	 */
	private SpeechListener listener = new SpeechListener() {

		public void onData(byte[] arg0) {
		}

		public void onCompleted(SpeechError error) {

		}

		public void onEvent(int arg0, Bundle arg1) {
		}
	};

	/**
	 * 载入页面控件
	 */
	private void initUI() {
		setContentView(R.layout.activity_walk_route_demo);
		SpeechUser.getUser().login(WalkingRouteDemoActivity.this, null, null,
				"appid=53031af1", listener);
		mSpeechSynthesizer = com.iflytek.cloud.speech.SpeechSynthesizer
				.createSynthesizer(this);
		// 初始化语音组件
		if (tts == null || tts.needReCheck) {
			tts = new TTSSupport(WalkingRouteDemoActivity.this);
		}
		textView_distance_tip = (TextView) findViewById(R.id.textView_distance_tip);
		textView_tip = (TextView) findViewById(R.id.textView_tip);
		textView_distance_total = (TextView) findViewById(R.id.textView_distance_total);
		textView_time = (TextView) findViewById(R.id.textView_time);
		text_bili = (TextView) findViewById(R.id.walk_text_bili);
		textView_gps = (TextView) findViewById(R.id.textView_gps);
		button_change2D3D = (Button) findViewById(R.id.button_change2D3D);
		button_to_myLocation = (Button) findViewById(R.id.button_to_myLocation);
		button_location_mode = (Button) findViewById(R.id.button_location_mode);
		img_tip = (ImageView) findViewById(R.id.walk_img_tip);
		button_location_jia = (Button) findViewById(R.id.button_location_jia);
		button_location_jian = (Button) findViewById(R.id.button_location_jian);
		button_location_lukuang = (Button) findViewById(R.id.button_location_lukuang);
		ButtonDispatcher dispatcher = new ButtonDispatcher();
		button_change2D3D.setOnClickListener(dispatcher);
		button_to_myLocation.setOnClickListener(dispatcher);
		button_location_mode.setOnClickListener(dispatcher);

		mediaPlayer = MediaPlayer.create(this, R.raw.tishi);
		try {
			mediaPlayer = MediaPlayer.create(this, R.raw.tishi);
			mediaPlayer.start();
			mediaPlayer.setOnCompletionListener(new OnCompletionListener() {
				public void onCompletion(MediaPlayer mp) {
					mp.release();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 初始化合成对象.
		mSpeechSynthesizer = SpeechSynthesizer.createSynthesizer(this);
	}

	/**
	 * 载入数据
	 */
	private void initDate() {
		// 从Intent读取目的地信息
		endCity = getIntent().getStringExtra(GEO_CITY_END);

		if (endCity == null) {
			endCity = "郑州";
		}
		la_str = getIntent().getStringExtra(GEO_PIONT_LA);
		lo_str = getIntent().getStringExtra(GEO_PIONT_LO);
		name_str = getIntent().getStringExtra(GEO_PIONT_NAME);
//		System.out.println(lo_str+"lalalalal__________"+la_str+"___name"+name_str);
		enNode = new MKPlanNode();
		if (la_str.length() > 0 && lo_str.length() > 0) {
			GeoPoint geoPoint = new GeoPoint(
					(int) (Double.parseDouble(la_str) * 1E6),
					(int) (Double.parseDouble(lo_str) * 1E6));
			enNode.pt = geoPoint;
			enNode.name = name_str;
		} else {
			enNode.name = "二七广场";
		}
		searchType = getIntent().getIntExtra(SEARCH_TYPE, -1);
		if (searchType == -1) {
			searchType = 0;
		}
		// 初始化地图
		mMapView = (MapView) findViewById(R.id.bmapView);
		mMapController = mMapView.getController();
		mMapController.setZoom(19);
		mMapController.setZoomGesturesEnabled(true);
		mMapController.setRotationGesturesEnabled(false);
		mMapController.setOverlookingGesturesEnabled(false);
		mMapView.setBuiltInZoomControls(false);
		mMapView.setTraffic(true);

		// 定位初始化
		mLocClient = new LocationClient(this);
		locData = new LocationData();
		mLocClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setPriority(LocationClientOption.GpsFirst);
		option.setOpenGps(true); // 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(2000);
		mLocClient.setLocOption(option);
		mLocClient.start();

		app = (BMapApiDemoApp) this.getApplication();
		app.mLocClient = mLocClient;

		// 定位图层初始化
		myLocationOverlay = new LocationOverlay(mMapView);
		// 设置定位数据
		myLocationOverlay.setData(locData);
		// 添加定位图层
		mMapView.getOverlays().add(myLocationOverlay);
		myLocationOverlay.enableCompass();

		Drawable marker = WalkingRouteDemoActivity.this.getResources()
				.getDrawable(R.drawable.r11);
		marker.setBounds(0, 0, marker.getIntrinsicWidth(),
				marker.getIntrinsicHeight());
		mItemOverlay = new AddedItemizedOverlay(marker,
				WalkingRouteDemoActivity.this);
		mMapView.getOverlays().add(mItemOverlay);

		graphicsOverlay = new GraphicsOverlay(mMapView);
		mMapView.getOverlays().add(graphicsOverlay);

		// 修改定位数据后刷新图层生效
		mMapView.refresh();

		button_location_lukuang.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (startroadinfo == false) {
					startroadinfo = true;
					button_location_lukuang
							.setBackgroundResource(R.drawable.daoh_lukuang);
				} else {
					startroadinfo = false;
					button_location_lukuang
							.setBackgroundResource(R.drawable.daohbg_lukhui);

					mItemOverlay.removeAll();
					graphicsOverlay.removeAll();
					mMapView.refresh();
				}
			}
		});

		popView = super.getLayoutInflater().inflate(R.layout.popview_roadinfo,
				null);
		mMapView.addView(popView, new MapView.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, null,
				MapView.LayoutParams.TOP_LEFT));
		popView.setVisibility(View.GONE);

		roadname = (TextView) findViewById(R.id.region_roadname);
		roadtip = (TextView) findViewById(R.id.roadtip);
		roadlen = (TextView) findViewById(R.id.s_e_length);
		// 测试用地图点击模拟位置
		mMapView.regMapTouchListner(new MKMapTouchListener() {

			public void onMapLongClick(GeoPoint arg0) {

			}

			public void onMapDoubleClick(GeoPoint arg0) {

			}

			public void onMapClick(GeoPoint arg0) {

				// if (routeListener != null) {
				// routeListener.updateLocation(arg0);
				// }
			}
		});

		// 初始化导航搜索
		mSearch = new MKSearch();
		mSearch.init(app.mBMapMan, new MKSearchListener() {

			public void onGetDrivingRouteResult(MKDrivingRouteResult res,
					int error) {

				if (error != 0 || res == null) {
					Toast.makeText(WalkingRouteDemoActivity.this, "未找到地点",
							Toast.LENGTH_SHORT).show();
					return;
				}

				searchType = 1;
				// 清除其他图层
				if (routeOverlay != null) {
					mMapView.getOverlays().remove(routeOverlay);
				}
				routeOverlay = new MyRouteOverlay(
						WalkingRouteDemoActivity.this, mMapView);
				// 此处仅展示一个方案作为示例
				routeOverlay.setData(res.getPlan(0).getRoute(0));

				// 添加路线图层
				mMapView.getOverlays().add(routeOverlay);
				// 执行刷新使生效
				mMapView.refresh();
				// 使用zoomToSpan()绽放地图，使路线能完全显示在地图上
				// mMapView.getController().zoomToSpan(
				// routeOverlay.getLatSpanE6(),
				// routeOverlay.getLonSpanE6());
				// 移动地图到起点
				mMapView.getController().animateTo(res.getStart().pt);
				// 将路线数据保存给全局变量
				route = res.getPlan(0).getRoute(0);
				routeListener = new RouteListener(route);
			}

			public void onGetTransitRouteResult(MKTransitRouteResult res,
					int error) {
			}

			public void onGetWalkingRouteResult(MKWalkingRouteResult res,
					int error) {
				// 错误号可参考MKEvent中的定义
				if (error != 0 || res == null) {
					Toast.makeText(WalkingRouteDemoActivity.this, "当前城市未找到地点",
							Toast.LENGTH_SHORT).show();
					return;
				}

				try {
					searchType = 0;
					// 清除其他图层
					if (routeOverlay != null) {
						mMapView.getOverlays().remove(routeOverlay);
					}
					routeOverlay = new MyRouteOverlay(
							WalkingRouteDemoActivity.this, mMapView);
					// 此处仅展示一个方案作为示例
					routeOverlay.setData(res.getPlan(0).getRoute(0));

					// 添加路线图层
					mMapView.getOverlays().add(routeOverlay);
					// 执行刷新使生效
					mMapView.refresh();
					// 使用zoomToSpan()绽放地图，使路线能完全显示在地图上
					// mMapView.getController().zoomToSpan(
					// routeOverlay.getLatSpanE6(),
					// routeOverlay.getLonSpanE6());
					// 移动地图到起点
					mMapView.getController().animateTo(res.getStart().pt);
					// 将路线数据保存给全局变量
					route = res.getPlan(0).getRoute(0);
					routeListener = new RouteListener(route);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}

			public void onGetAddrResult(MKAddrInfo res, int error) {
			}

			public void onGetPoiResult(MKPoiResult res, int arg1, int arg2) {
			}

			public void onGetBusDetailResult(MKBusLineResult result, int iError) {
			}

			public void onGetSuggestionResult(MKSuggestionResult res, int arg1) {
			}

			public void onGetPoiDetailSearchResult(int type, int iError) {

			}

			public void onGetShareUrlResult(MKShareUrlResult result, int type,
					int error) {

			}
		});

		maxZoomLevel = mMapView.getMaxZoomLevel();
		minZoomLevel = mMapView.getMinZoomLevel();

		text_bili.setText(SCALE_DESCS[(int) mMapView.getZoomLevel()]);

		button_location_jia.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				mMapView.getController().zoomIn();
			}
		});

		button_location_jian.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				mMapView.getController().zoomOut();
			}
		});

	}

	/**
	 * 驾车路径规划
	 * 
	 * @param startCity
	 * @param stNode
	 * @param endCity
	 * @param enNode
	 */
	private void planeRoute(String startCity, MKPlanNode stNode,
			String endCity, MKPlanNode enNode, int searchType) {
		this.startCity = startCity;
		this.stNode = stNode;
		this.endCity = endCity;
		this.enNode = enNode;
		if (searchType == 0)
			mSearch.walkingSearch(startCity, stNode, endCity, enNode);
		else if (searchType == 1) {
			mSearch.drivingSearch(startCity, stNode, endCity, enNode);
		}
	}

	/**
	 * 定位SDK监听函数
	 */
	public class MyLocationListenner implements BDLocationListener {

		public void onReceiveLocation(BDLocation location) {
			if (location == null)
				return;

			if (startroadinfo) {
				displayRoadInfo();
			}

			if (mMapView.getZoomLevel() > minZoomLevel
					&& mMapView.getZoomLevel() < maxZoomLevel) {
				button_location_jia.setBackgroundResource(R.drawable.daoh_jia);
				button_location_jian
						.setBackgroundResource(R.drawable.daoh_jian);
			} else if (mMapView.getZoomLevel() <= minZoomLevel) {
				button_location_jian
						.setBackgroundResource(R.drawable.daoh_jianhui);
			} else if (mMapView.getZoomLevel() >= maxZoomLevel) {
				button_location_jia
						.setBackgroundResource(R.drawable.daoh_jiahui);
			}
			button_location_jian.setBackgroundResource(R.drawable.daoh_jian);
			text_bili.setText(SCALE_DESCS[(int) mMapView.getZoomLevel()]);
			myLocation = location;
			locData.latitude = location.getLatitude();
			locData.longitude = location.getLongitude();
			// 如果不显示定位精度圈，将accuracy赋值为0即可
			locData.accuracy = location.getRadius();
			// 此处可以设置 locData的方向信息, 如果定位 SDK 未返回方向信息，用户可以自己实现罗盘功能添加方向信息。
			locData.direction = location.getDerect();
			// 更新定位数据
			myLocationOverlay.setData(locData);
			// TODO 更新卫星数目
			updateSateliteNum(location);
			setDirection(location);
			// 回调导航监听器
			if (routeListener != null) {
				routeListener.updateLocation(new GeoPoint(
						(int) (locData.latitude * 1e6),
						(int) (locData.longitude * 1e6)));
			}
			// 更新图层数据执行刷新后生效
			mMapView.refresh();
			// 首次定位时，移动到定位点进行导航路径搜索
			if (isFirstLoc) {
				// 移动地图到定位点
				mMapController.animateTo(new GeoPoint(
						(int) (locData.latitude * 1e6),
						(int) (locData.longitude * 1e6)));

				myLocationOverlay.setLocationMode(LocationMode.FOLLOWING);
				CurrentLocationMode = LocationMode.FOLLOWING;
				MKPlanNode stNode = new MKPlanNode();
				stNode.pt = new GeoPoint((int) (locData.latitude * 1e6),
						(int) (locData.longitude * 1e6));
				// MKPlanNode enNode = new MKPlanNode();
				// // enNode.pt=new GeoPoint(113735376, 34785347);
				// enNode.name = "二七广场";
				planeRoute(endCity, stNode, endCity, enNode, searchType);
			}
			// 首次定位完成
			isFirstLoc = false;
		}

		public void onReceivePoi(BDLocation poiLocation) {
			if (poiLocation == null) {
				return;
			}
		}
	}

	/**
	 * 我的位置图层
	 * 
	 */
	public class LocationOverlay extends MyLocationOverlay {

		public LocationOverlay(MapView mMapView) {
			super(mMapView);

		}

	}

	protected void onPause() {
		mMapView.onPause();
		super.onPause();
	}

	protected void onResume() {
		mMapView.onResume();
		super.onResume();
		if (gpsStatusNotify == null) {
			gpsStatusNotify = new GPSStatusNotify();
		}
//		 gpsStatusNotify.checkGPSStatus(this);
	}

	protected void onDestroy() {
		// 退出时销毁定位
		if (mLocClient != null && mLocClient.isStarted()) {
			mLocClient.stop();
			mLocClient = null;
		}
		mMapView.destroy();
		// 释放语音资源
		tts.onDestory();
		if (mediaPlayer != null) {
			// mediaPlayer.reset();
			mediaPlayer.release();
		}
		super.onDestroy();
	}

	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mMapView.onSaveInstanceState(outState);

	}

	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		mMapView.onRestoreInstanceState(savedInstanceState);
	}

	/**
	 * 导航算法监听器
	 */
	private class RouteListener {

		ArrayList<MKStep> keyStepList;
		SparseArray<Boolean> arriveArray;

		int range = 80;
		MKStep nearestStep;

		int lastArrive = -1;

		private void showTost(String str) {

			if (!checkSpeechServiceInstalled()) {
				synthetizeInSilence(str);
			} else {
				tts.play(str);
			}
		}

		public RouteListener(MKRoute route) {

			total_juli = ConvertTool.distanceToString(route.getDistance());
			textView_distance_total.setText(total_juli);

			String str_time = "0分钟";
			total_time = ConvertTool.timeToString(route.getTime());
			str_time = total_time;
			if (route.getTime() > 36000) {
				str_time = str_time.substring(0, str_time.indexOf("小时")) + "小时";
			}
			textView_time.setText(str_time.replace("小时", "h").replace("分钟",
					"min"));
			keyStepList = new ArrayList<MKStep>();
			arriveArray = new SparseArray<Boolean>();

			for (int i = 0; i < route.getNumSteps(); i++) {
				keyStepList.add(route.getStep(i));
			}

		}

		boolean isRouteStarted = false;

		public boolean checkSpeechServiceInstalled() {
			String packageName = "com.iflytek.speechcloud";
			List<PackageInfo> packages = WalkingRouteDemoActivity.this
					.getPackageManager().getInstalledPackages(0);
			for (int i = 0; i < packages.size(); i++) {
				PackageInfo packageInfo = packages.get(i);
				if (packageInfo.packageName.equals(packageName)) {
					return true;
				} else {
					continue;
				}
			}
			return false;
		}

		public void updateLocation(GeoPoint location) {
			if (!isRouteStarted) {
				startRoute(location);
				isRouteStarted = true;
				return;
			}

			int index = -1;
			int minDistance = -1;
			for (int i = 0; i < keyStepList.size(); i++) {

				MKStep step = keyStepList.get(i);
				if (nearestStep == null) {
					nearestStep = step;
					index = i;
				} else {
					nearestStep = (DistanceUtil.getDistance(location,
							nearestStep.getPoint()) < DistanceUtil.getDistance(
							location, step.getPoint())) ? nearestStep : step;
					index = (nearestStep == step) ? i : index;

				}

			}
			minDistance = (int) DistanceUtil.getDistance(location,
					nearestStep.getPoint());
			if (minDistance <= range && !arriveArray.get(index, false)) {
				arriveStepPoint(index);
			} else {
				isOutOfRoutePlan(location);
			}
		}

		private void arriveStepPoint(int index) {
			lastArrive = index;
			if (index + 1 < keyStepList.size()) {

				mMapController.setCenter(route.getStep(index).getPoint());
				mMapController.setRotation(trance(route.getStep(index)
						.getPoint(), route.getStep(index + 1).getPoint()));
				String[] currentContent = route.getStep(index).getContent()
						.split("-");
				String[] nextContent = route.getStep(index + 1).getContent()
						.split("-");
				showTost("合天下提醒您" + currentContent[0] + currentContent[1] + "后"
						+ nextContent[0]);
				textView_distance_tip.setText(currentContent[1].replace("米",
						"m").replace("公里", "km"));
				textView_tip.setText("前方" + nextContent[0]);
				if (nextContent[0].indexOf("左") > -1) {
					img_tip.setBackgroundResource(R.drawable.daohbg_left);
				} else if (nextContent[0].indexOf("右") > -1) {
					img_tip.setBackgroundResource(R.drawable.daohbg_right);
				} else if (nextContent[0].indexOf("调头") > -1) {
					img_tip.setBackgroundResource(R.drawable.daohbg_diaotou);
				} else {
					img_tip.setBackgroundResource(R.drawable.daohimg_tip);
				}
				arriveArray.put(index, true);
				for (int i = 0; i < index; i++) {
					arriveArray.put(i, true);
				}

			} else {
				endRoute();
			}
		}

		private void isOutOfRoutePlan(GeoPoint location) {
			if (lastArrive != -1 && lastArrive + 1 < keyStepList.size()) {
				double planDistance = keyStepList.get(lastArrive).getDistance();

				double distanceToPre = DistanceUtil.getDistance(location,
						keyStepList.get(lastArrive).getPoint());
				double distanceToNext = DistanceUtil.getDistance(location,
						keyStepList.get(lastArrive + 1).getPoint());

				textView_distance_tip.setText(ConvertTool
						.distanceToString((int) distanceToNext));
				int ranger = 300;
				if (distanceToPre + distanceToNext > planDistance + ranger) {

					showTost("您已偏离路线,重新规划路线");
					routeListener = null;
					rePlanRoute(location);
				}
			}
		}

		private void rePlanRoute(GeoPoint location) {
			stNode = new MKPlanNode();
			stNode.pt = location;
			WalkingRouteDemoActivity.this.planeRoute(startCity, stNode,
					endCity, enNode, searchType);
		}

		private void startRoute(GeoPoint location) {
			// 准备下载语音，播放导航开始
			showTost("开始导航,本次导航全程"
					+ total_juli
					+ "耗时"
					+ total_time
					+ "。请前进"
					+ (int) DistanceUtil.getDistance(keyStepList.get(0)
							.getPoint(), location) + "米至起点道路");
			img_tip.setBackgroundResource(R.drawable.daohimg_tip);
			textView_tip.setText("起点道路");
			textView_distance_tip.setText(ConvertTool
					.distanceToString((int) DistanceUtil.getDistance(
							keyStepList.get(0).getPoint(), location)));
			mMapController.setCenter(route.getStart());
			mMapController.setRotation(trance(route.getStart(), route
					.getStep(0).getPoint()));

		}

		private void endRoute() {
			showTost("本次导航结束，谢谢使用。");
			routeListener = null;
		}

		/**
		 * 计算节点间与正北方向夹角
		 * 
		 * @param start
		 * @param end
		 * @return
		 */
		private int trance(GeoPoint start, GeoPoint end) {

			int degree = (int) (Math.atan2(
					start.getLongitudeE6() - end.getLongitudeE6(),
					start.getLatitudeE6() - end.getLatitudeE6()) * 180 / Math.PI);

			return (degree + 180) % 360;
		}
	}

	private class ButtonDispatcher implements OnClickListener {

		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.button_change2D3D:
				if (mMapView.getMapOverlooking() == 0) {
					mMapController.setOverlooking(-45);
					button_change2D3D.setText("3D");
				} else {
					mMapController.setOverlooking(0);
					button_change2D3D.setText("2D");
				}
				break;
			case R.id.button_location_mode:
				if (myLocationOverlay == null || CurrentLocationMode == null)
					return;
				if (CurrentLocationMode == LocationMode.FOLLOWING) {
					myLocationOverlay.setLocationMode(LocationMode.NORMAL);
					CurrentLocationMode = LocationMode.NORMAL;
					button_location_mode
							.setBackgroundResource(R.drawable.daoh_ziyou);

				} else {
					myLocationOverlay.setLocationMode(LocationMode.FOLLOWING);
					CurrentLocationMode = LocationMode.FOLLOWING;
					button_location_mode
							.setBackgroundResource(R.drawable.daoh_gens);
				}
				break;
			case R.id.button_to_myLocation:
				if (locData == null) {
					return;
				}

				mMapController.animateTo(new GeoPoint(
						(int) (locData.latitude * 1e6),
						(int) (locData.longitude * 1e6)));
				break;

			default:
				break;
			}
		}
	}

	protected void displayRoadInfo() {

		if (gettingroadinfo)
			return;
		gettingroadinfo = true;

		// get road info
		AsyncWorkHandler asyncQueryHandler = new AsyncWorkHandler() {

			public Object excute(Map<String, String> params) {
				return ProductAction.getRoadInfoSearchBean(params);
			}

			public void handleMessage(Message msg) {
				if (msg.obj != null) {
					RoadInfoSearchBean bean = (RoadInfoSearchBean) msg.obj;
					if ("ok".equals(bean.getState())) {
						int count = bean.getCount();
						roadList = bean.getList();
						Drawable marker = null;
						if (mItemOverlay != null) {
							if (mItemOverlay.size() > 0) {
								mItemOverlay.removeAll();
							}
						}
						graphicsOverlay.removeAll();
						for (int i = 0; i < count; i++) {
							switch (roadList.get(i).getType()) {
							case 1:
								marker = WalkingRouteDemoActivity.this
										.getResources().getDrawable(
												R.drawable.r1);
								break;
							case 2:
								marker = WalkingRouteDemoActivity.this
										.getResources().getDrawable(
												R.drawable.r2);
								break;
							case 3:
								marker = WalkingRouteDemoActivity.this
										.getResources().getDrawable(
												R.drawable.r3);
								break;
							case 4:
								marker = WalkingRouteDemoActivity.this
										.getResources().getDrawable(
												R.drawable.r4);
								break;
							case 5:
								marker = WalkingRouteDemoActivity.this
										.getResources().getDrawable(
												R.drawable.r5);
								break;
							case 6:
								marker = WalkingRouteDemoActivity.this
										.getResources().getDrawable(
												R.drawable.r6);
								break;
							case 7:
								marker = WalkingRouteDemoActivity.this
										.getResources().getDrawable(
												R.drawable.r7);
								break;
							case 8:
								marker = WalkingRouteDemoActivity.this
										.getResources().getDrawable(
												R.drawable.r8);
								break;
							case 9:
								marker = WalkingRouteDemoActivity.this
										.getResources().getDrawable(
												R.drawable.r9);
								break;
							case 10:
								marker = WalkingRouteDemoActivity.this
										.getResources().getDrawable(
												R.drawable.r10);
								break;
							case 11:
								marker = WalkingRouteDemoActivity.this
										.getResources().getDrawable(
												R.drawable.r11);
								break;
							}

							marker.setBounds(0, 0, marker.getIntrinsicWidth(),
									marker.getIntrinsicHeight());
							double lat = roadList.get(i).getLat();
							double lng = roadList.get(i).getLng();
							GeoPoint gpoint = new GeoPoint((int) (lat * 1E6),
									(int) (lng * 1E6));
							GeoPoint bpoint = CoordinateConvert
									.fromGcjToBaidu(gpoint);
							OverlayItem item = new OverlayItem(bpoint, "", "");
							item.setMarker(marker);
							mItemOverlay.addItem(item);
							mMapView.refresh();

							String strDir = roadList.get(i).getDirection();
							if (strDir != null) {
								StringTokenizer strToken = new StringTokenizer(
										strDir, "|");
								Geometry lineGeometry = new Geometry();
								GeoPoint[] linePoints = new GeoPoint[strToken
										.countTokens()];
								int cnt = 0;
								while (strToken.hasMoreElements()) {
									String strpt = strToken.nextToken();
									double xlng = Double.parseDouble(strpt
											.substring(0, strpt.indexOf("+")));
									double ylat = Double.parseDouble(strpt
											.substring(strpt.indexOf("+") + 1));

									GeoPoint gpt = new GeoPoint(
											(int) (ylat * 1E6),
											(int) (xlng * 1E6));
									GeoPoint bpt = CoordinateConvert
											.fromGcjToBaidu(gpt);
									linePoints[cnt++] = bpt;
								}
								lineGeometry.setPolyLine(linePoints);
								Symbol lineSymbol = new Symbol();
								Symbol.Color lineColor = lineSymbol.new Color();
								lineColor.red = 255;
								lineColor.green = 0;
								lineColor.blue = 0;
								lineColor.alpha = 126;
								lineSymbol.setLineSymbol(lineColor, 10);

								Graphic lineGraphic = new Graphic(lineGeometry,
										lineSymbol);

								graphicsOverlay.setData(lineGraphic);
								mMapView.refresh();
							}
						}

					}
				}

				gettingroadinfo = false;
			}

		};
		// 异步获取信息,实现两个方法excute跟onCompleteWork
		// get region and zoom
		try {

			Map<String, String> params = new HashMap<String, String>();
			params.put("zoom", "" + (int) mMapView.getZoomLevel());

			Projection pro = mMapView.getProjection();
			android.graphics.Rect rc = new android.graphics.Rect();
			mMapView.getLocalVisibleRect(rc);
			GeoPoint minp = pro.fromPixels(rc.left, rc.bottom);
			GeoPoint maxp = pro.fromPixels(rc.right, rc.top);

			int x = (int) (minp.getLongitudeE6() - mMapView.getLongitudeSpan() / 2);
			int y = (int) (minp.getLatitudeE6() - mMapView.getLatitudeSpan() / 2);
			GeoPoint gp = new GeoPoint(y, x);
			GeoPoint bp = CoordinateConvert.fromGcjToBaidu(gp);
			int x1 = bp.getLongitudeE6();
			int y1 = bp.getLatitudeE6();
			double dminx = (double) (2 * x - x1);
			double dminy = (double) (2 * y - y1);
			System.out.println("maxp" + maxp + "mMap" + mMapView);
			x = (int) (maxp.getLongitudeE6() + mMapView.getLongitudeSpan() / 2);
			y = (int) (maxp.getLatitudeE6() + mMapView.getLatitudeSpan() / 2);
			gp = new GeoPoint(y, x);
			bp = CoordinateConvert.fromGcjToBaidu(gp);
			x1 = bp.getLongitudeE6();
			y1 = bp.getLatitudeE6();
			double dmaxx = (double) (2 * x - x1);
			double dmaxy = (double) (2 * y - y1);

			int nminx = (int) (dminx * 3.6);
			int nmaxx = (int) (dmaxx * 3.6);
			int nminy = (int) (dminy * 3.6);
			int nmaxy = (int) (dmaxy * 3.6);

			params.put("minx", "" + nminx);
			params.put("miny", "" + nminy);
			params.put("maxx", "" + nmaxx);
			params.put("maxy", "" + nmaxy);

			asyncQueryHandler.doWork(params);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	class AddedItemizedOverlay extends ItemizedOverlay<OverlayItem> {
		// 属性
		private Context mContext;

		// 构造方法
		public AddedItemizedOverlay(Drawable marker, Context context) {
			super(marker, mMapView);
			this.mContext = context;
		}

		// 添加点击事件
		public boolean onTap(int i) {
			roadname.setText(roadList.get(i).getRoadname() + "("
					+ roadList.get(i).getRegion() + ")");
			roadtip.setText(roadList.get(i).getTip());
			roadlen.setText("从 " + roadList.get(i).getStart() + " 至 "
					+ roadList.get(i).getEnd() + "("
					+ roadList.get(i).getLength() + "米)");
			double lat = roadList.get(i).getLat();
			double lng = roadList.get(i).getLng();
			GeoPoint gpt = new GeoPoint((int) (lat * 1E6), (int) (lng * 1E6));
			GeoPoint bpt = CoordinateConvert.fromGcjToBaidu(gpt);
			mMapView.updateViewLayout(popView, new MapView.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, bpt,
					MapView.LayoutParams.BOTTOM_CENTER));
			popView.setVisibility(View.VISIBLE);

			return true;
		}

		public boolean onTap(GeoPoint point, MapView mapView) {
			if (popView != null)
				popView.setVisibility(View.GONE);
			super.onTap(point, mapView);
			return false;
		}
	}

	/**
	 * 卫星数目
	 */

	private void updateSateliteNum(BDLocation location) {
		if (location == null)
			return;
		// 如果连接上卫星，则设置为相应位置数据更新速度
		if (location.getSatelliteNumber() <= 0) {
			mLocClient.getLocOption().setScanSpan(2000);
		} else {
			mLocClient.getLocOption().setScanSpan(1000);
		}
		// TODO 设置TextView显示当前连接的卫星数目,此处用Log代替
		String xingnum = "0";
		if (location.getSatelliteNumber() > 0) {
			xingnum = location.getSatelliteNumber() + "";
		}
		textView_gps.setText(xingnum);
	}

	public void onBufferProgress(int arg0, int arg1, int arg2, String arg3) {
	}

	public void onCompleted(SpeechError arg0) {
		if (arg0 == null) {
		} else if (arg0 != null) {
		}
	}

	public void onSpeakBegin() {

	}

	public void onSpeakPaused() {

	}

	public void onSpeakProgress(int arg0, int arg1, int arg2) {
	}

	public void onSpeakResumed() {
	}

	private void synthetizeInSilence(String str) {

		if (null == mSpeechSynthesizer) {
			// 创建合成对象.
			mSpeechSynthesizer = com.iflytek.cloud.speech.SpeechSynthesizer
					.createSynthesizer(this);
		}

		// 设置发音人
		mSpeechSynthesizer.setParameter(
				com.iflytek.cloud.speech.SpeechConstant.VOICE_NAME, "xiaoyan");

		// 设置语速
		mSpeechSynthesizer.setParameter(
				com.iflytek.cloud.speech.SpeechConstant.SPEED, "" + 50);

		// 设置音量
		mSpeechSynthesizer.setParameter(
				com.iflytek.cloud.speech.SpeechConstant.VOLUME, "" + 50);

		// 进行语音合成.
		mSpeechSynthesizer.startSpeaking(str.replace(" ", ""), this);
	}

	/**
	 * 设置屏幕方向与当前方向角度
	 */
	private void setDirection(BDLocation location) {
		if (location == null)
			return;
		if (location.getDerect() != -1.0) {
			MKMapStatus status = mMapView.getMapStatus();
			status.rotate = (int) location.getDerect();
			mMapController.setMapStatusWithAnimation(status, 0);
		}

	}

}
