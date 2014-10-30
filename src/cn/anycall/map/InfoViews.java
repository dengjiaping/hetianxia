package cn.anycall.map;

import org.json.JSONArray;
import org.json.JSONObject;
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
import com.htx.conn.HttpHelper;
import com.htx.pub.AsyncImageLoader;
import com.htx.pub.LoadingDialog;
import com.htx.pub.MyToast;
import com.htx.user.U_toSpeak;
import com.htx.weixin.StoreComment;
import com.htx.weixin.Uplun;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class InfoViews extends Activity {

	private String  jsonurl = "http://api.36936.com/ClientApi/Guide/ClientGetBdInfo.ashx?";
	private RatingBar ratbar;
	private LoadingDialog dialoga;
	private ImageView img_info;
	private TextView tv_name,tv_pf,tv_hj,tv_fw,tv_zh,tv_add,tv_walkdh,tv_busdh,tv_cardh,tv_phone,tv_content,tv_morecontent,tv_wyp,tv_pl,tv_pltime,tv_morepl,tv_fankui,tv_jiucuo;
	private String string = "";
	private String str_imgurl="",str_phone="",str_name="",str_addr=""
			,str_description="",str_overall_rating="",str_service_rating="",str_taste_rating=""
			,str_environment_rating="",str_pl="",str_pltime="";
	private String str_index="",str_lat="",str_lng="",str_destination="",str_city="",str_loa="",loc_lat="",loc_lng="";
	private boolean if_content = true;
	private String adUserId="",reStr="" ;
	private Button btn_back;
	// 定位相关
	private LocationClient mLocClient;
	private LocationData locData = null;
	public MyLocationListenner myListener = new MyLocationListenner();

	private BMapApiDemoApp app;
	MKSearch mMKSearch;
	
	private Handler mHandl = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				data();
				dialoga.dismiss();
				break;
			case 2:
				dialoga.dismiss();
				MyToast.toast(InfoViews.this, string, 1000);
				break;
			}
			super.handleMessage(msg);
		}

	};
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.info_view);
		
		initUI();
		dialoga = new LoadingDialog(InfoViews.this);
		dialoga.show();	new Thread(new Runnable() {
			public void run() {
				// 得到目录
				initDate();
			}
		}).start();
		click();
	}

	private void initUI() {
		
		// 定位初始化
				mLocClient = new LocationClient(InfoViews.this);
				locData = new LocationData();
				mLocClient.registerLocationListener(myListener);
				LocationClientOption option = new LocationClientOption();
				option.setPriority(LocationClientOption.GpsFirst);
				option.setOpenGps(true); // 打开gps
				option.setCoorType("bd09ll"); // 设置坐标类型
				option.setScanSpan(2000);
				mLocClient.setLocOption(option);
				mLocClient.start();

				app = (BMapApiDemoApp) (InfoViews.this).getApplication();
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
		adUserId = this.getIntent().getStringExtra("uid");
		
		btn_back = (Button)findViewById(R.id.button2);
		tv_name = (TextView)findViewById(R.id.info_tv_name);
		ratbar = (RatingBar)findViewById(R.id.poirating);
		img_info = (ImageView)findViewById(R.id.info_img);
		tv_pf = (TextView)findViewById(R.id.info_tv_pf);
		tv_fw = (TextView)findViewById(R.id.info_tv_fw);
		tv_hj = (TextView)findViewById(R.id.info_tv_hj);
		tv_zh = (TextView)findViewById(R.id.info_tv_zh);
		tv_add = (TextView)findViewById(R.id.info_tv_add);
		tv_walkdh = (TextView)findViewById(R.id.info_tv_walkdh);
		tv_busdh = (TextView)findViewById(R.id.info_tv_busdh);
		tv_cardh = (TextView)findViewById(R.id.info_tv_cardh);
		tv_phone = (TextView)findViewById(R.id.info_tv_phone);
		tv_content = (TextView)findViewById(R.id.info_view_tvcontent);
		tv_morecontent = (TextView)findViewById(R.id.info_tv_morecontent);
		tv_wyp = (TextView)findViewById(R.id.info_tv_wyp);
		tv_pl = (TextView)findViewById(R.id.info_tv_pl);
		tv_pltime = (TextView)findViewById(R.id.info_tv_pltime);
		tv_morepl = (TextView)findViewById(R.id.info_tv_morepl);
		tv_jiucuo = (TextView)findViewById(R.id.info_tv_jiucuo);
		tv_fankui = (TextView)findViewById(R.id.info_tv_fankui);
		
	}
	

	private void initDate() {
		//String hash = SiteHelper.MD5(adUserId + Const.UrlHashKey);
		jsonurl = jsonurl+"uid="+adUserId;
		System.out.println("url__________"+jsonurl);
		Message message = new Message();
		try {
			reStr = HttpHelper.getJsonData(InfoViews.this, jsonurl);
			System.out.println("restr__________"+reStr);
			JSONObject reObject = new JSONObject(reStr);
			if (reObject.getInt("status") == 1) {
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
				if(ob.has("overall_rating")){
				str_overall_rating = ob.getString("overall_rating");
				}
				if(ob.has("service_rating")){
				str_service_rating = ob.getString("service_rating");
				}
				if(ob.has("taste_rating")){
				str_taste_rating = ob.getString("taste_rating");
				}
				if(ob.has("review")){
				JSONArray rearry = ob.getJSONArray("review");
				if(rearry.length()>0)
				{
					JSONObject jsonObject = (JSONObject) rearry.opt(0);
					if(jsonObject.has("content")){
					str_pl = jsonObject.getString("content");
					}
					if(jsonObject.has("date")){
					str_pltime = jsonObject.getString("date");
					}
					
				}
				
				}
				message.what = 1;
			} else {
				message.what = 2;
				string = reObject.getString("msg");
			}
		} catch (Exception e) {
			e.printStackTrace();
			message.what = 2;
			string = "网络异常！";
		}

		mHandl.sendMessage(message);
	}
	
	private void click()
	{
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
		tv_morepl.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(InfoViews.this,StoreComment.class);
				intent.putExtra("titleStr", "商家评论");
				intent.putExtra("StoreId", adUserId);
				intent.putExtra("data", reStr);
				startActivity(intent);
			}
		});
		tv_morecontent.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (if_content) {
					tv_morecontent.setVisibility(View.GONE);
				}
			}
		});
		tv_walkdh.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/*
				Intent intent = new Intent(InfoViews.this,WalkingRouteDemoActivity.class);
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
				
				
				Bundle bundle = new Bundle();
				Intent it = new Intent();
				it.setClassName("com.hetianxia.daohang", "com.htx.boot.BootActivity");
				bundle.putString("type", "1");
				bundle.putString("index", str_index+ "");
				bundle.putString("lng", str_lng+"");
				bundle.putString("lat", str_lat+"");
				bundle.putString("destination", str_destination);
				it.putExtras(bundle);
				if (getPackageManager().resolveActivity(it, 0) ==null ) {
					Intent intent2 = new Intent(InfoViews.this,DownloadActivity.class);
					startActivity(intent2);
			
				}
				else {
					startActivity(it);
				}
			}
		});
		tv_cardh.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				Intent intent = new Intent(InfoViews.this,DriveRouteDemoActivity.class);
//				intent.putExtra("index", str_index);
//				intent.putExtra(
//						"lat",
//						str_lat);
//				intent.putExtra(
//						"lng",
//						str_lng);
//				intent.putExtra("destination", str_destination);
//				startActivity(intent);
				

				Bundle bundle  =new Bundle();
				Intent intent = new Intent();
				intent.setClassName("com.hetianxia.daohang", "com.htx.boot.BootActivity");
				bundle.putString("type", "3");
				bundle.putString("lng", str_lng+"");
				bundle.putString("lat", str_lat+"");
				bundle.putString("loc_lng", locData.longitude+"");
				bundle.putString("loc_lat", locData.latitude+"");
				bundle.putString("destination", str_destination);
				intent.putExtras(bundle);
				
				if (getPackageManager().resolveActivity(intent, 0) ==null ) {
					Intent intent2 = new Intent(InfoViews.this,DownloadActivity.class);
						startActivity(intent2);
				}
				else {
					startActivity(intent);
				}
			}
		});
		tv_busdh.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/*
				Intent intent = new Intent(InfoViews.this,BusPlaneSelectionActivity.class);
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
				
				Bundle bundle = new Bundle();
				Intent intent = new Intent();
				intent.setClassName("com.hetianxia.daohang", "com.htx.boot.BootActivity");
				bundle.putString("type", "2");
				bundle.putString("lng", str_lng+"");
				bundle.putString("lat", str_lat+"");
				bundle.putString("destination", str_destination);
				bundle.putString("city", str_city);
				bundle.putString("loa", str_loa);
				intent.putExtras(bundle);
				if (getPackageManager().resolveActivity(intent, 0) ==null ) {
					Intent intent2 = new Intent(InfoViews.this,DownloadActivity.class);

						startActivity(intent2);
				}
				else {
					startActivity(intent);
				}
			}
		});
		tv_jiucuo.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				startActivity(new Intent(InfoViews.this,U_toSpeak.class));
			}
		});
		tv_fankui.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				startActivity(new Intent(InfoViews.this,U_toSpeak.class));
			}
		});
		tv_wyp.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent intent = new Intent(InfoViews.this,Uplun.class);
				startActivity(intent);
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
		if (str_description.length()>0) {
			tv_content.setText(str_description);
		}
		else {
			tv_content.setText("暂无简介");
		}
		if (str_description.length()==0) {
			tv_morecontent.setVisibility(View.GONE);
		}
		if (str_overall_rating != null) {
			if (str_overall_rating.length()>0) {
				ratbar.setRating(Float.parseFloat(str_overall_rating));
				tv_pf.setText("评分："+str_overall_rating);
			}
			else {
				str_overall_rating = "2.5";
				ratbar.setRating(Float.parseFloat(str_overall_rating));
				tv_pf.setText("评分："+str_overall_rating);
			}
		}
		else {
			str_overall_rating = "2.5";
			ratbar.setRating(Float.parseFloat(str_overall_rating));
			tv_pf.setText("评分："+str_overall_rating);
		}
		if (str_service_rating.length()>0) {
			tv_fw.setText("服务:"+str_service_rating);
		}
		else {
			tv_fw.setText("服务:2.5");
		}
		if (str_environment_rating.length()>0) {
			tv_hj.setText("环境:"+str_environment_rating);
		}
		else {
			tv_hj.setText("环境:2.5");
		}
		if (str_taste_rating.length()>0) {
			tv_zh.setText("综合:"+str_taste_rating);
		}
		else {
			tv_zh.setText("综合:2.5");
		}
		System.out.println("str_pl________"+str_pl);
		if (str_pl.length()>0) {
			tv_pl.setText(str_pl);
		
		}else {
			tv_pl.setText("暂无评论");
			tv_morepl.setVisibility(View.GONE);
		}
		tv_pltime.setText(str_pltime);
	}

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