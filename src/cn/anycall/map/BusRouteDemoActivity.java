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
import com.baidu.mapapi.map.MKMapTouchListener;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationOverlay;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.mapapi.map.Symbol;
import com.baidu.mapapi.map.MyLocationOverlay.LocationMode;
import com.baidu.mapapi.search.MKPlanNode;
import com.baidu.mapapi.search.MKTransitRoutePlan;
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
import com.iflytek.cloud.speech.SpeechUser;
import com.iflytek.cloud.speech.SynthesizerListener;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Message;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.TextView;

public class BusRouteDemoActivity extends Activity implements
SynthesizerListener {

	public static final String GEO_PIONT_LO = "lng";
	public static final String GEO_PIONT_LA = "lat";
	public static final String GEO_PIONT_NAME = "destination";
	public static final String GEO_CITY_END = "GEO_CITY_END";
	public static final String SEARCH_TYPE = "SEARCH_TYPE";
	// 合成对象.
	private com.iflytek.cloud.speech.SpeechSynthesizer mSpeechSynthesizer;
	
    private static final String[] SCALE_DESCS = { "0米","5米","10米","2000公里", "1000公里", "500公里", "200公里",  
        "100公里", "50公里", "25公里", "20公里", "10公里", "2公里", "5公里", "1公里", "500米",  
       "200米", "100米", "50米", "20米" }; 
	private int maxZoomLevel;
	private int minZoomLevel;

	private MediaPlayer mediaPlayer ;
	private boolean startroadinfo = true;
	private boolean gettingroadinfo = false;
	private GraphicsOverlay graphicsOverlay = null;
	private ArrayList<RoadInfoResultBean> roadList = null;
	AddedItemizedOverlay mItemOverlay = null;
	private TextView roadname = null;
	private TextView roadtip = null;
	private TextView roadlen = null;
	private View popView = null;
	
	// GPS状态监测
	private GPSStatusNotify gpsStatusNotify;
	// HUD信息
	private TextView  textView_tip,text_bili;
	private Button button_change2D3D, button_to_myLocation,
			button_location_mode,button_location_jia,button_location_jian,button_location_lukuang;
	//private ImageView img_tip;
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
	 * 保存公交路线数据的变量
	 */
	MKTransitRoutePlan plan = null;
	/**
	 * 导航图层
	 */
	MyTransitOverlay transitOverlay = null;

	/**
	 * 路线监听器
	 */
	RouteListener routeListener;

	/**
	 * 语音合成组件
	 */
	TTSSupport tts;

	BMapApiDemoApp app = null;
	
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
		setContentView(R.layout.activity_bus_route_demo);
		SpeechUser.getUser().login(BusRouteDemoActivity.this, null, null, "appid=53031af1", listener);
		mSpeechSynthesizer = com.iflytek.cloud.speech.SpeechSynthesizer
				.createSynthesizer(this);
		//textView_distance_tip = (TextView) findViewById(R.id.textView_distance_tip);
		textView_tip = (TextView) findViewById(R.id.textView_tip);
		//textView_distance_total = (TextView) findViewById(R.id.textView_distance_total);
		//textView_time = (TextView) findViewById(R.id.textView_time);
		text_bili = (TextView)findViewById(R.id.bus_text_bili);
		button_change2D3D = (Button) findViewById(R.id.button_change2D3D);
		button_to_myLocation = (Button) findViewById(R.id.button_to_myLocation);
		button_location_mode = (Button) findViewById(R.id.button_location_mode);
		//img_tip = (ImageView)findViewById(R.id.bus_img_tip);
		button_location_jia = (Button)findViewById(R.id.button_location_jia);
		button_location_jian = (Button)findViewById(R.id.button_location_jian);
		button_location_lukuang = (Button) findViewById(R.id.button_location_lukuang);
		ButtonDispatcher dispatcher = new ButtonDispatcher();
		button_change2D3D.setOnClickListener(dispatcher);
		button_to_myLocation.setOnClickListener(dispatcher);
		button_location_mode.setOnClickListener(dispatcher);

		if (tts == null || tts.needReCheck) {
			tts = new TTSSupport(BusRouteDemoActivity.this);
		}
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
	}

	/**
	 * 载入数据
	 */
	private void initDate() {
		
		// 从Intent读取目的地信息
		plan = (MKTransitRoutePlan) ((BMapApiDemoApp) getApplication()).objectTransfer;
		
		// 初始化地图
		mMapView = (MapView) findViewById(R.id.bmapView);
		mMapController = mMapView.getController();
		mMapController.setZoom(19);
		mMapController.setZoomGesturesEnabled(false);
		mMapController.setRotationGesturesEnabled(false);
		mMapController.setOverlookingGesturesEnabled(false);
		mMapView.setBuiltInZoomControls(false);
		mMapView.setTraffic(true);
		
		// 导航图层初始化
		transitOverlay = new MyTransitOverlay(this, mMapView);
		transitOverlay.setData(plan);
		mMapView.getOverlays().add(transitOverlay);
		// 定位初始化
		mLocClient = new LocationClient(this);
		locData = new LocationData();
		mLocClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setPriority(LocationClientOption.GpsFirst);
		option.setOpenGps(true);// 打开gps
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
		
		Drawable marker = BusRouteDemoActivity.this.getResources()
				.getDrawable(R.drawable.r11);
		marker.setBounds(0, 0, marker.getIntrinsicWidth(),
				marker.getIntrinsicHeight());
		mItemOverlay = new AddedItemizedOverlay(marker,
				BusRouteDemoActivity.this);
		mMapView.getOverlays().add(mItemOverlay);

		graphicsOverlay = new GraphicsOverlay(mMapView);
		mMapView.getOverlays().add(graphicsOverlay);

		// 修改定位数据后刷新图层生效
		mMapView.refresh();

		button_location_lukuang.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (startroadinfo == false) {
					startroadinfo = true;
					button_location_lukuang.setBackgroundResource(R.drawable.daoh_lukuang);
				} else {
					startroadinfo = false;
					button_location_lukuang.setBackgroundResource(R.drawable.daohbg_lukhui);

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
		routeListener = new RouteListener(plan);

		maxZoomLevel = mMapView.getMaxZoomLevel();
		minZoomLevel = mMapView.getMinZoomLevel();

		text_bili.setText(SCALE_DESCS[(int) mMapView.getZoomLevel()]);
		button_location_jia.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mMapView.getController().zoomIn();
				if (mMapView.getZoomLevel()>minZoomLevel && mMapView.getZoomLevel()<maxZoomLevel) {
					button_location_jia.setBackgroundResource(R.drawable.daoh_jia);
				}else {
					button_location_jia.setBackgroundResource(R.drawable.daoh_jiahui);
				}
				button_location_jian.setBackgroundResource(R.drawable.daoh_jian);
				text_bili.setText(SCALE_DESCS[(int) mMapView.getZoomLevel()]);
			}
		});
		
		button_location_jian.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mMapView.getController().zoomOut();
				if (mMapView.getZoomLevel()>minZoomLevel && mMapView.getZoomLevel()<maxZoomLevel) {
					button_location_jian.setBackgroundResource(R.drawable.daoh_jian);
				}else {
					button_location_jian.setBackgroundResource(R.drawable.daoh_jianhui);
				}

				button_location_jia.setBackgroundResource(R.drawable.daoh_jia);

				text_bili.setText(SCALE_DESCS[(int) mMapView.getZoomLevel()]);
			}
		});
	
	}

	/**
	 * 定位SDK监听函数
	 */
	public class MyLocationListenner implements BDLocationListener {

		public void onReceiveLocation(BDLocation location) {
			if (location == null)
				return;
			
			if(startroadinfo){
				displayRoadInfo();
				}
			
			myLocation = location;
			locData.latitude = location.getLatitude();
			locData.longitude = location.getLongitude();
			// 如果不显示定位精度圈，将accuracy赋值为0即可
			locData.accuracy = location.getRadius();
			// 此处可以设置 locData的方向信息, 如果定位 SDK 未返回方向信息，用户可以自己实现罗盘功能添加方向信息。
			locData.direction = location.getDerect();
			// 更新定位数据
			myLocationOverlay.setData(locData);
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
//		gpsStatusNotify.checkGPSStatus(this);
	}

	
	protected void onDestroy() {
		// 退出时销毁定位
		if (mLocClient != null)
			mLocClient.stop();
		mMapView.destroy();
		// 释放语音资源
		tts.onDestory();
		if (mediaPlayer != null) {
			//mediaPlayer.reset();
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

		MKTransitRoutePlan plan;
		PlanInfo info;
		boolean isRouteStarted = false;
		int range = 30;

		private void showTost(String str) {

			if (!checkSpeechServiceInstalled()) {
				synthetizeInSilence(str);
			} else {
				tts.play(str);
			}
		}

		public RouteListener(MKTransitRoutePlan plan) {
			this.plan = plan;
			info = new PlanInfo(plan);
			textView_tip.setText(info.getContent());
		}

		public void updateLocation(GeoPoint location) {

			if (!isRouteStarted) {
				isRouteStarted = true;
				startRoute(location);
			} else if (DistanceUtil.getDistance(plan.getEnd(), new GeoPoint(
					location.getLatitudeE6(), location.getLongitudeE6())) < range) {
				endRoute();
			}
		}

		private void startRoute(GeoPoint location) {
			// 准备下载语音，播放导航开始
			showTost("开始导航，" + info.getContent());

		}

		private void endRoute() {
			showTost("本次导航已结束，谢谢使用。");
			routeListener = null;
		}
		

		public boolean checkSpeechServiceInstalled() {
			String packageName = "com.iflytek.speechcloud";
			List<PackageInfo> packages = BusRouteDemoActivity.this
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
					button_location_mode.setBackgroundResource(R.drawable.daoh_ziyou);

				} else {
					myLocationOverlay.setLocationMode(LocationMode.FOLLOWING);
					CurrentLocationMode = LocationMode.FOLLOWING;
					button_location_mode.setBackgroundResource(R.drawable.daoh_gens);
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
							if (mItemOverlay.size()>0) {
								mItemOverlay.removeAll();
							}
						}
						graphicsOverlay.removeAll();
						for (int i = 0; i < count; i++) {
							switch (roadList.get(i).getType()) {
							case 1:
								marker = BusRouteDemoActivity.this
										.getResources().getDrawable(
												R.drawable.r1);
								break;
							case 2:
								marker = BusRouteDemoActivity.this
										.getResources().getDrawable(
												R.drawable.r2);
								break;
							case 3:
								marker = BusRouteDemoActivity.this
										.getResources().getDrawable(
												R.drawable.r3);
								break;
							case 4:
								marker = BusRouteDemoActivity.this
										.getResources().getDrawable(
												R.drawable.r4);
								break;
							case 5:
								marker = BusRouteDemoActivity.this
										.getResources().getDrawable(
												R.drawable.r5);
								break;
							case 6:
								marker = BusRouteDemoActivity.this
										.getResources().getDrawable(
												R.drawable.r6);
								break;
							case 7:
								marker = BusRouteDemoActivity.this
										.getResources().getDrawable(
												R.drawable.r7);
								break;
							case 8:
								marker = BusRouteDemoActivity.this
										.getResources().getDrawable(
												R.drawable.r8);
								break;
							case 9:
								marker = BusRouteDemoActivity.this
										.getResources().getDrawable(
												R.drawable.r9);
								break;
							case 10:
								marker = BusRouteDemoActivity.this
										.getResources().getDrawable(
												R.drawable.r10);
								break;
							case 11:
								marker = BusRouteDemoActivity.this
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
		Map<String, String> params = new HashMap<String, String>();
		params.put("zoom", "" + (int) mMapView.getZoomLevel());

		Projection pro = mMapView.getProjection();
		android.graphics.Rect rc = new android.graphics.Rect();
		mMapView.getLocalVisibleRect(rc);
		GeoPoint minp = pro.fromPixels(rc.left, rc.bottom);
		GeoPoint maxp = pro.fromPixels(rc.right, rc.top);

		int x = minp.getLongitudeE6() - mMapView.getLongitudeSpan() / 2;
		int y = minp.getLatitudeE6() - mMapView.getLatitudeSpan() / 2;
		GeoPoint gp = new GeoPoint(y, x);
		GeoPoint bp = CoordinateConvert.fromGcjToBaidu(gp);
		int x1 = bp.getLongitudeE6();
		int y1 = bp.getLatitudeE6();
		double dminx = (double) (2 * x - x1);
		double dminy = (double) (2 * y - y1);

		x = maxp.getLongitudeE6() + mMapView.getLongitudeSpan() / 2;
		y = maxp.getLatitudeE6() + mMapView.getLatitudeSpan() / 2;
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
	}

	class AddedItemizedOverlay extends ItemizedOverlay<OverlayItem> {

		// 构造方法
		public AddedItemizedOverlay(Drawable marker, Context context) {
			super(marker, mMapView);
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


	public void onBufferProgress(int arg0, int arg1, int arg2, String arg3) {		
	}

	public void onCompleted(SpeechError arg0) {		
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
}
