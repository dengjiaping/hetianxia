package cn.anycall.map;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.search.MKAddrInfo;
import com.baidu.mapapi.search.MKBusLineResult;
import com.baidu.mapapi.search.MKDrivingRouteResult;
import com.baidu.mapapi.search.MKPoiResult;
import com.baidu.mapapi.search.MKSearch;
import com.baidu.mapapi.search.MKSearchListener;
import com.baidu.mapapi.search.MKShareUrlResult;
import com.baidu.mapapi.search.MKSuggestionResult;
import com.baidu.mapapi.search.MKTransitRouteResult;
import com.baidu.mapapi.search.MKWalkingRouteResult;
import com.hetianxia.activity.R;
import com.htx.action.BMapApiDemoApp;
import com.htx.pub.AsyncImageLoader;
import com.htx.user.U_toSpeak;
import com.htx.weixin.ShopHomeInfo;
import com.htx.weixin.StoreComment;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class Info_heyue_Views extends Activity {

	private ImageView img_info;
	private TextView tv_name,tv_add,tv_walkdh,tv_busdh,tv_cardh,tv_phone,tv_content,tv_morecontent,tv_fankui,tv_jiucuo,tv_zf,tv_youhui,tv_moreyouhui;
	private String str_imgurl="",str_phone="",str_name="",str_addr="",str_zf;
	private String str_index="",str_lat="",str_lng="",str_destination="",str_city="",str_loa="",str_Discount="",loc_lat="",loc_lng="";
	private String adUserId="" ;
	private Button btn_back,btn_gz;
	private LinearLayout layout;
	// 定位相关
	private LocationClient mLocClient;
	private LocationData locData = null;
	public MyLocationListenner myListener = new MyLocationListenner();

	private BMapApiDemoApp app;
	MKSearch mMKSearch;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.info_heyue_view);
		
		initUI();
		data();
		click();
	}

	private void initUI() {
		// 定位初始化
		mLocClient = new LocationClient(Info_heyue_Views.this);
		locData = new LocationData();
		mLocClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setPriority(LocationClientOption.GpsFirst);
		option.setOpenGps(true); // 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(2000);
		mLocClient.setLocOption(option);
		mLocClient.start();

		app = (BMapApiDemoApp) (Info_heyue_Views.this).getApplication();
		app.mLocClient = mLocClient;
		mMKSearch = new MKSearch();
		mMKSearch.init(app.mBMapMan, new MKSearchListener() {

			public void onGetPoiResult(MKPoiResult result, int type, int iError) {
			}

			public void onGetAddrResult(MKAddrInfo result, int iError) {
			}

			public void onGetDrivingRouteResult(MKDrivingRouteResult result,
					int iError) {
			}

			public void onGetTransitRouteResult(MKTransitRouteResult result,
					int iError) {
			}

			public void onGetWalkingRouteResult(MKWalkingRouteResult result,
					int iError) {
			}

			public void onGetBusDetailResult(MKBusLineResult arg0, int arg1) {
			}

			public void onGetPoiDetailSearchResult(int arg0, int arg1) {
			}

			public void onGetSuggestionResult(MKSuggestionResult arg0, int arg1) {
			}

			public void onGetShareUrlResult(MKShareUrlResult arg0, int arg1,
					int arg2) {
			}
		});

		str_index = this.getIntent().getStringExtra("index");
		str_lat = this.getIntent().getStringExtra("lat");
		str_lng = this.getIntent().getStringExtra("lng");
		loc_lat = this.getIntent().getStringExtra("loc_lat");
		loc_lng = this.getIntent().getStringExtra("loc_lng");
		str_destination = this.getIntent().getStringExtra("destination");
		str_city = this.getIntent().getStringExtra("city");
		str_loa = this.getIntent().getStringExtra("loa");
		adUserId = this.getIntent().getStringExtra("StoreId");
		str_addr =  this.getIntent().getStringExtra("address");
		str_name =  this.getIntent().getStringExtra("title");
		str_imgurl =  this.getIntent().getStringExtra("logo");
		str_phone = this.getIntent().getStringExtra("phone");
		str_zf = this.getIntent().getStringExtra("price");
		str_Discount = this.getIntent().getStringExtra("Discount");
		
		btn_back = (Button)findViewById(R.id.button2);
		tv_name = (TextView)findViewById(R.id.info_tv_name);
		img_info = (ImageView)findViewById(R.id.info_img);
		tv_add = (TextView)findViewById(R.id.info_tv_add);
		tv_walkdh = (TextView)findViewById(R.id.info_tv_walkdh);
		tv_busdh = (TextView)findViewById(R.id.info_tv_busdh);
		tv_cardh = (TextView)findViewById(R.id.info_tv_cardh);
		tv_phone = (TextView)findViewById(R.id.info_tv_phone);
		tv_content = (TextView)findViewById(R.id.info_view_tvcontent);
		tv_morecontent = (TextView)findViewById(R.id.info_tv_morecontent);
		tv_jiucuo = (TextView)findViewById(R.id.info_tv_jiucuo);
		tv_fankui = (TextView)findViewById(R.id.info_tv_fankui);
		tv_zf = (TextView)findViewById(R.id.info_tv_zf);
		layout = (LinearLayout)findViewById(R.id.info_ll_pl);
		tv_youhui = (TextView)findViewById(R.id.info_tvyouhui);
		tv_moreyouhui = (TextView)findViewById(R.id.info_tv_moreyouhui);
		btn_gz = (Button)findViewById(R.id.info_btn_gz);
	}

	private void click()
	{
		btn_gz.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {

				Intent intent = new Intent(Info_heyue_Views.this,ShopHomeInfo.class);
				intent.putExtra("StoreId", adUserId);
				intent.putExtra("brief", str_destination);
				intent.putExtra("address", str_addr);
				intent.putExtra("isFocus","1");
				intent.putExtra("logo", str_imgurl);
				startActivity(intent);
			}
		});
		tv_moreyouhui.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				tv_moreyouhui.setVisibility(View.GONE);
			}
		});
		layout.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
					Intent intent = new Intent(Info_heyue_Views.this,StoreComment.class);
					intent.putExtra("titleStr", "商家评论");
					intent.putExtra("StoreId", adUserId);
					startActivity(intent);
			}
		});
		tv_phone.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Uri uri = Uri.parse("tel:"
						+ str_phone);
				Intent intent = new Intent(Intent.ACTION_DIAL, uri);
				startActivity(intent);
			}
		});
		btn_back.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		tv_morecontent.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
					tv_morecontent.setVisibility(View.GONE);
			}
		});
		tv_walkdh.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				/*
				Intent intent = new Intent(Info_heyue_Views.this,WalkingRouteDemoActivity.class);
				intent.putExtra("index", str_index);
				intent.putExtra(
						"lat",
						str_lat);
				intent.putExtra(
						"lng",
						str_lng);
				intent.putExtra("destination", str_destination);
				startActivity(intent);
				*/
			}
		});
		tv_cardh.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				Intent intent = new Intent(Info_heyue_Views.this,DriveRouteDemoActivity.class);
//				intent.putExtra("index", str_index);
//				intent.putExtra(
//						"lat",
//						str_lat);
//				intent.putExtra(
//						"lng",
//						str_lng);
//				intent.putExtra("destination", str_destination);
//				startActivity(intent);
				
/*
				com.baidu.nplatform.comapi.basestruct.GeoPoint geoPoint = CoordinateTransformUtil.transferBD09ToGCJ02(Double.parseDouble(str_lng),Double.parseDouble(str_lat));
				com.baidu.nplatform.comapi.basestruct.GeoPoint geoPoint2 = CoordinateTransformUtil.transferBD09ToGCJ02(locData.longitude,locData.latitude);
				double sslat = (double)(geoPoint2.getLatitudeE6())/100000;
				double sslon = (double)(geoPoint2.getLongitudeE6())/100000;

				double eelat = (double)(geoPoint.getLatitudeE6())/100000;
				double eelon = (double)(geoPoint.getLongitudeE6())/100000;
				
					BaiduNaviManager.getInstance().launchNavigator(Info_heyue_Views.this, 
						sslat, sslon,"", 
						eelat, eelon,str_addr,
						NE_RoutePlan_Mode.ROUTE_PLAN_MOD_MIN_TIME, 		 //算路方式
						true, 									   		 //真实导航
						BaiduNaviManager.STRATEGY_FORCE_ONLINE_PRIORITY, //在离线策略
						new OnStartNavigationListener() {				 //跳转监听
							
							
							public void onJumpToNavigator(Bundle configParams) {
								Intent intent = new Intent(Info_heyue_Views.this, BNavigatorActivity.class);
								intent.putExtras(configParams);
						        startActivity(intent);
							}
							
							public void onJumpToDownloader() {
							}
						});
						*/
			}
		});
		tv_busdh.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/*
				Intent intent = new Intent(Info_heyue_Views.this,BusPlaneSelectionActivity.class);
				intent.putExtra("index", str_index);
				intent.putExtra(
						"lat",
						str_lat);
				intent.putExtra(
						"lng",
						str_lng);
				intent.putExtra("destination", str_destination);
				intent.putExtra("city", str_city);
				intent.putExtra("loa", str_loa);
				startActivity(intent);
				*/
			}
		});
		tv_jiucuo.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(Info_heyue_Views.this,U_toSpeak.class));
			}
		});
		tv_fankui.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(Info_heyue_Views.this,U_toSpeak.class));
			}
		});
	}
	private void data()
	{
		AsyncImageLoader imageLoader = new AsyncImageLoader();
		imageLoader.LoadBackImage(str_imgurl, img_info);
		tv_add.setText("地址："+str_addr);
		tv_name.setText(str_name);
		tv_phone.setText(str_phone);
		tv_zf.setText(str_zf);
		if (str_Discount != null ) {
			if (str_Discount.length()>0) {
				tv_youhui.setText(str_Discount);
				if (tv_youhui.getLineCount() <= 3) {
					tv_moreyouhui.setVisibility(View.GONE);
				}
			}
			else {
				tv_youhui.setText("合天下会员"+str_zf);
				tv_moreyouhui.setVisibility(View.GONE);
			}
		}
		else {
			tv_youhui.setText("合天下会员"+str_zf);
			tv_moreyouhui.setVisibility(View.GONE);
		}
		if (str_destination.length()>0) {
			tv_content.setText(str_destination);
		}
		else {
			tv_content.setText("暂无简介");
		}
		if (tv_content.getLineCount() <= 3) {
			tv_morecontent.setVisibility(View.GONE);
		}
	}
	
	
/*
	private void initDate() {
	
			String reStr = HttpHelper.getJsonData(Info_heyue_Views.this, jsonurl);
			System.out.println("restr__________"+reStr);
			JSONObject reObject = new JSONObject(reStr);
				JSONObject ob = reObject.getJSONObject("result");
				if(ob.has("image")){
				str_imgurl = ob.getString("image");
				}
				if(ob.has("addr")){
				str_addr = ob.getString("addr");
				}
				if(ob.has("description")){
				str_description = ob.getString("description");
				}
				if(ob.has("phone")){
				str_phone = ob.getString("phone");
				}
				if(ob.has("name")){
				str_name = ob.getString("name");
				}
				if(ob.has("environment_rating")){
				str_environment_rating = ob.getString("environment_rating");
				}
				if(ob.has("service_rating")){
				str_service_rating = ob.getString("service_rating");
				}
				if(ob.has("taste_rating")){
				str_taste_rating = ob.getString("taste_rating");
				}
				if(ob.has("review")){
				JSONArray rearry = ob.getJSONArray("review");
				JSONObject jsonObject = (JSONObject) rearry.opt(0);
				if(ob.has("content")){
				str_pl = jsonObject.getString("content");
				}
				if(ob.has("user_name")){
				str_plname = jsonObject.getString("user_name");
				}
				if(ob.has("date")){
				str_pltime = jsonObject.getString("date");
				}
				}

	}
	*/
	

	/**
	 * 定位SDK监听函数
	 */
	public class MyLocationListenner implements BDLocationListener {

		public void onReceiveLocation(BDLocation location) {
			if (location == null)
				return;

			locData.latitude = location.getLatitude();
			locData.longitude = location.getLongitude();
			// 如果不显示定位精度圈，将accuracy赋值为0即可
			locData.accuracy = location.getRadius();
			// 此处可以设置 locData的方向信息, 如果定位 SDK 未返回方向信息，用户可以自己实现罗盘功能添加方向信息。
			locData.direction = location.getDerect();
			
		}

		public void onReceivePoi(BDLocation poiLocation) {
			if (poiLocation == null) {
				return;
			}
		}
	}


}