package cn.anycall.map;

import java.util.ArrayList;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.MKEvent;
import com.baidu.mapapi.search.MKAddrInfo;
import com.baidu.mapapi.search.MKBusLineResult;
import com.baidu.mapapi.search.MKDrivingRouteResult;
import com.baidu.mapapi.search.MKLine;
import com.baidu.mapapi.search.MKPlanNode;
import com.baidu.mapapi.search.MKPoiResult;
import com.baidu.mapapi.search.MKSearch;
import com.baidu.mapapi.search.MKSearchListener;
import com.baidu.mapapi.search.MKShareUrlResult;
import com.baidu.mapapi.search.MKSuggestionResult;
import com.baidu.mapapi.search.MKTransitRoutePlan;
import com.baidu.mapapi.search.MKTransitRouteResult;
import com.baidu.mapapi.search.MKWalkingRouteResult;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.hetianxia.activity.R;
import com.htx.action.BMapApiDemoApp;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.app.Activity;
import android.content.Intent;

public class BusPlaneSelectionActivity extends Activity implements
		BDLocationListener {
	// Intent相关参数
	public static final String GEO_PIONT_LO = "lng";
	public static final String GEO_PIONT_LA = "lat";
	public static final String GEO_PIONT_NAME = "destination";
	public static final String GEO_CITY_END = "GEO_CITY_END";
	// 搜索相关
	MKSearch mSearch = null;

	private Button home_refresh;
	// 定位相关
	boolean isFirstLoc = true;
	private LocationClient mLocClient;

	// UI相关
	TextView textView_taxiPrice,textVi;
	ListView listView_bus_selection;

	private String city,loa;
	private Intent _intent ;
	BMapApiDemoApp app = null;
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initUI();
		initData();
	}

	private void initUI() {
		setContentView(R.layout.activity_bus_plane_selection);
		 home_refresh= (Button) findViewById(R.id.home_refresh);
		textView_taxiPrice = (TextView) findViewById(R.id.textView_taxiPrice);
		textVi = (TextView)findViewById(R.id.textVi);
		listView_bus_selection = (ListView) findViewById(R.id.listView_bus_selection);
		_intent=getIntent();
		city=_intent.getStringExtra("city");
		loa=_intent.getStringExtra("loa");
	}

	private void initData() {
		home_refresh.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
		app = (BMapApiDemoApp) this.getApplication();
		// 初始化导航搜索
		mSearch = new MKSearch();
		mSearch.init(app.mBMapMan,
				new MKSearchListener() {

					public void onGetDrivingRouteResult(
							MKDrivingRouteResult res, int error) {
					}

					public void onGetTransitRouteResult(
							MKTransitRouteResult res, int error) {
						if (error == MKEvent.ERROR_ROUTE_ADDR) {

							return;
						}
						if (error != 0 || res == null) {
							// 展示搜索结果
							if(loa.equals("")){
							textView_taxiPrice.setText("距离太近，无需乘坐公交，请选择步行。");
							}else{
								textView_taxiPrice.setText("起点和终点为不同城市，请选择车载导航。");
							}
							textView_taxiPrice.setCompoundDrawables(null, null, null, null);
							textVi.setVisibility(View.GONE);
							listView_bus_selection.setVisibility(View.GONE);
							return;
						}
						// 展示搜索结果
						textView_taxiPrice.setText("    打车约需要" + res.getTaxiPrice()
								+ "元");
						BusResultAdapter adapter = new BusResultAdapter(res);
						listView_bus_selection.setAdapter(adapter);
						listView_bus_selection.setOnItemClickListener(adapter);
					}

					public void onGetWalkingRouteResult(
							MKWalkingRouteResult res, int error) {

					}

					public void onGetAddrResult(MKAddrInfo res, int error) {
					}

					public void onGetPoiResult(MKPoiResult res, int arg1,
							int arg2) {
					}

					public void onGetBusDetailResult(MKBusLineResult result,
							int iError) {
					}

					public void onGetSuggestionResult(MKSuggestionResult res,
							int arg1) {
					}

					public void onGetPoiDetailSearchResult(int type, int iError) {

					}

					public void onGetShareUrlResult(MKShareUrlResult result,
							int type, int error) {

					}
				});
		mLocClient = new LocationClient(this);
		mLocClient.registerLocationListener(this);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(1000);
		mLocClient.setLocOption(option);
		mLocClient.start();
	}

	public void onReceiveLocation(BDLocation location) {
		if (location == null)
			return;
		if (isFirstLoc) {
			isFirstLoc = false;
			// 导航起点目的地检索信息
			String endCity;
			MKPlanNode stNode;

			MKPlanNode enNode;
			// 搜索到目的地的所有公交车路线
			endCity = getIntent().getStringExtra(GEO_CITY_END);
			if (endCity == null) {
				endCity = city;
			}
			String la_str = getIntent().getStringExtra(GEO_PIONT_LA);
			String lo_str = getIntent().getStringExtra(GEO_PIONT_LO);
			String name_str = getIntent().getStringExtra(GEO_PIONT_NAME);

			//System.out.println("la_str"+la_str+"lo_str"+lo_str+"name_str"+name_str);
			enNode = new MKPlanNode();
			if (la_str.length()>0 && lo_str.length()>0) {
				GeoPoint geoPoint = new GeoPoint((int)(Double.parseDouble(la_str)*1E6), (int)(Double.parseDouble(lo_str)*1E6));
				enNode.pt = geoPoint;
				enNode.name = name_str;
			} else {
				enNode.name = "二七广场";
			}
			stNode = new MKPlanNode();
			stNode.pt = new GeoPoint((int) (location.getLatitude() * 1e6),
					(int) (location.getLongitude() * 1e6));
			mSearch.transitSearch(endCity, stNode, enNode);
		}
	}

	public void onReceivePoi(BDLocation arg0) {
		return;

	}

	class BusResultAdapter extends BaseAdapter implements OnItemClickListener {
		ArrayList<MKTransitRoutePlan> planList = new ArrayList<MKTransitRoutePlan>();

		public BusResultAdapter(MKTransitRouteResult res) {
			for (int i = 0; i < res.getNumPlan(); i++) {
				planList.add(res.getPlan(i));
			}
		}

		public int getCount() {
			return planList.size();
		}

		public Object getItem(int arg0) {

			return planList.get(arg0);
		}

		public long getItemId(int arg0) {

			return arg0;
		}

		public View getView(int arg0, View arg1, ViewGroup arg2) {
			if (arg1 == null) {
				arg1 = getLayoutInflater().inflate(
						R.layout.item_listview_busline, null);
			}
			TextView textView_busline_title, textView_info;
			textView_busline_title = (TextView) arg1
					.findViewById(R.id.textView_busline_title);
			textView_info = (TextView) arg1.findViewById(R.id.textView_info);

			PlanInfo info = new PlanInfo((MKTransitRoutePlan) getItem(arg0));
			textView_busline_title.setText(info.getTitle());
			textView_info.setText(info.getContent());
			return arg1;
		}

		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			Intent intent = new Intent(BusPlaneSelectionActivity.this,
					BusRouteDemoActivity.class);
			((BMapApiDemoApp) getApplication()).objectTransfer = getItem(arg2);
			startActivity(intent);
		}
	}
}

class PlanInfo {

	MKTransitRoutePlan plan;

	public PlanInfo(MKTransitRoutePlan plan) {
		this.plan = plan;
	}

	public String getTitle() {
		return plan.getContent().replace("_", "→");
	}

	public String getTime() {
		return ConvertTool.timeToString(plan.getTime());
	}

	public String getDistance() {
		return ConvertTool.distanceToString(plan.getDistance());
	}

	public String getContent() {
		int totalLines = plan.getNumLines();
		int totalStations = 0;
		int totalBusDistance = 0;
		for (int i = 0; i < totalLines; i++) {
			MKLine line = plan.getLine(i);
			totalStations += line.getNumViaStops();
			totalBusDistance += line.getDistance();
		}
		String walkDistance = ConvertTool.distanceToString(plan.getDistance()
				- totalBusDistance);
		return "全程" + getDistance() + "，乘车" + totalStations + "站，步行"
				+ walkDistance + "全程耗时约" + getTime();
	}
}
