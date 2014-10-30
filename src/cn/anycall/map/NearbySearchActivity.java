package cn.anycall.map;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import com.hetianxia.activity.R;
import com.htx.action.BMapApiDemoApp;
import com.htx.conn.Const;
import com.htx.main.Update;
import com.htx.main.listActivity;
import com.htx.pub.ActivityManager;
import com.htx.pub.CityActivity;
import com.htx.pub.LoadingDialog;
import com.htx.pub.Locationm;
import com.htx.pub.PubUserActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Environment;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.Toast;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.navisdk.BaiduNaviManager;
import com.baidu.navisdk.BNaviEngineManager.NaviEngineInitListener;
import com.baidu.navisdk.util.verify.BNKeyVerifyListener;

public class NearbySearchActivity extends PubUserActivity {

	private Context _context;
	private Button goback;
	private Button rangebtn;
	private ImageView food;
	private Button zhongcanbtn;
	private Button xiaochikuaicanbtn;
	private Button huoguobtn;
	private Button chuancaibtn;
	private Button xicanbtn;
	private Button kfcbtn;
	private Button mcdonaldbtn;
	private ImageView hotel;
	private Button kuaijiejiudianbtn;
	private Button xingjijiudianbtn;
	private Button dujiacunbtn;
	private Button qingnianlvsebtn;
	private Button lvguanbtn;
	private Button zhaodaisuobtn;
	private ImageView entertailment;
	private Button dianyinyuanbtn;
	private Button ktvbtn;
	private Button wangbabtn;
	private Button jiubabtn;
	private Button kafeitingbtn;
	private Button gongyuanbtn;
	private ImageView life;
	private Button chaoshibtn;
	private Button yaodianbtn;
	private Button yiyuanbtn;
	private Button atmbtn;
	private Button yinhangbtn;
	private Button cesuobtn;
	private ImageView main_speak;
	private String strCity = "", mlocat = "";
	private ImageView traffic;
	private Button gongjiaozhanbtn;
	private Button jiayouzhanbtn;
	private String loa = "";
	private Button tingchechangbtn;
	private Button chepiaodaishoubtn;
	private Button poisearchbtn;
	private EditText poisearchcontent;
	private Button zizhucan_btn, bianlidian_btn, gongyu_btn, meirong_btn,
			qichezhan_btn, dekeshi_btn, liaoyangyuan_btn, xiyuanmo_btn,
			lifadian_btn, xuexiao_btn, jingju_btn, huochezhan_btn, jichang_btn,
			sdian_btn, qichexiuli_btn, fuzhuangdian_btn, jiazheng_btn;
	private ImageView img_qita;
	private Button hunsha_btn,zhubao_btn,yishupin_btn,guanggaoyinshua_btn
	,lvyou_btn,fangchan_btn,jinrong_btn,zhongjie_btn,guwancheng_btn;

	private MyLocationListener mLocationListener = new MyLocationListener();
	private String query = null;
	BMapApiDemoApp app = null;
	private boolean isSearching = false;

	private String[] ranges = new String[] { "500米", "1000米", "2000米", "5000米",
			"全城" };

	public static NearbySearchActivity instance = null;

	private final static String ACCESS_KEY = "FWQbpnznxNL5nSi3KxhjFDp1";
	public void onResume() {
		app.mLocClient.registerLocationListener(mLocationListener);
		super.onResume();
	}

	public void onPause() {
		super.onPause();
	}

	public void onDestroy() {
		super.onDestroy();
	}

	private String getSdcardDir() {
		if (Environment.getExternalStorageState().equalsIgnoreCase(
				Environment.MEDIA_MOUNTED)) {
			return Environment.getExternalStorageDirectory().toString();
		}
		return null;
	}

	private NaviEngineInitListener mNaviEngineInitListener = new NaviEngineInitListener() {
		public void engineInitSuccess() {
			
		}

		public void engineInitStart() {
			
		}

		public void engineInitFail() {
			
		}
	};

	private BNKeyVerifyListener mKeyVerifyListener = new BNKeyVerifyListener() {
		public void onVerifySucc() {
		}

		public void onVerifyFailed(int arg0, String arg1) {
		}
	};
	/** Called when the activity is first created. */

	public void onCreate(Bundle savedInstanceState) {

		setContentView(R.layout.nearbysearch);
		super.onCreate(savedInstanceState);

		instance = this;
		_context = this;

		// 初始化导航引擎
		BaiduNaviManager.getInstance().initEngine(NearbySearchActivity.this, getSdcardDir(),
				mNaviEngineInitListener, "FWQbpnznxNL5nSi3KxhjFDp1", mKeyVerifyListener);

		app = (BMapApiDemoApp) this.getApplication();
		goback = (Button) findViewById(R.id.nearbysearch_goback);
		rangebtn = (Button) findViewById(R.id.search_range_btn);
		food = (ImageView) findViewById(R.id.foodimg);
		zhongcanbtn = (Button) findViewById(R.id.zhongcan_btn);
		xiaochikuaicanbtn = (Button) findViewById(R.id.xiaochikuaican_btn);
		huoguobtn = (Button) findViewById(R.id.huoguo_btn);
		chuancaibtn = (Button) findViewById(R.id.chuancai_btn);
		xicanbtn = (Button) findViewById(R.id.xican_btn);
		kfcbtn = (Button) findViewById(R.id.kfc_btn);
		mcdonaldbtn = (Button) findViewById(R.id.mcdonald_btn);
		main_speak = (ImageView) findViewById(R.id.main_speak);
		hotel = (ImageView) findViewById(R.id.hotelimg);
		kuaijiejiudianbtn = (Button) findViewById(R.id.kuaijiejiudian_btn);
		xingjijiudianbtn = (Button) findViewById(R.id.xingjijiudian_btn);
		dujiacunbtn = (Button) findViewById(R.id.dujiacun_btn);
		qingnianlvsebtn = (Button) findViewById(R.id.qingnianlvse_btn);
		lvguanbtn = (Button) findViewById(R.id.lvguan_btn);
		zhaodaisuobtn = (Button) findViewById(R.id.zhaodaisuo_btn);
		entertailment = (ImageView) findViewById(R.id.entertailmentimg);
		dianyinyuanbtn = (Button) findViewById(R.id.dianyinyuan_btn);
		ktvbtn = (Button) findViewById(R.id.ktv_btn);
		wangbabtn = (Button) findViewById(R.id.wangba_btn);
		jiubabtn = (Button) findViewById(R.id.jiuba_btn);
		kafeitingbtn = (Button) findViewById(R.id.kafeiting_btn);
		gongyuanbtn = (Button) findViewById(R.id.gongyuan_btn);
		life = (ImageView) findViewById(R.id.lifeimg);
		chaoshibtn = (Button) findViewById(R.id.chaoshi_btn);
		yaodianbtn = (Button) findViewById(R.id.yaodian_btn);
		yiyuanbtn = (Button) findViewById(R.id.yiyuan_btn);
		atmbtn = (Button) findViewById(R.id.atm_btn);
		yinhangbtn = (Button) findViewById(R.id.yinhang_btn);
		cesuobtn = (Button) findViewById(R.id.cesuo_btn);
		traffic = (ImageView) findViewById(R.id.trafficimg);
		gongjiaozhanbtn = (Button) findViewById(R.id.gongjiaozhan_btn);
		jiayouzhanbtn = (Button) findViewById(R.id.jiayouzhan_btn);
		tingchechangbtn = (Button) findViewById(R.id.tingchechang_btn);
		chepiaodaishoubtn = (Button) findViewById(R.id.chepiaodaishou_btn);
		poisearchbtn = (Button) findViewById(R.id.poi_search_bt);
		poisearchcontent = (EditText) findViewById(R.id.poi_search_content);
		app.mLocClient.registerLocationListener(mLocationListener);
		// /////////////////////////////////////////////////////
		zizhucan_btn = (Button) findViewById(R.id.zizhucan_btn);
		dekeshi_btn = (Button) findViewById(R.id.dekeshi_btn);
		liaoyangyuan_btn = (Button) findViewById(R.id.liaoyangyuan_btn);
		bianlidian_btn = (Button) findViewById(R.id.bianlidian_btn);
		fuzhuangdian_btn = (Button) findViewById(R.id.fuzhuangdian_btn);
		gongyu_btn = (Button) findViewById(R.id.gongyu_btn);
		meirong_btn = (Button) findViewById(R.id.meirong_btn);
		xiyuanmo_btn = (Button) findViewById(R.id.xiyuanmo_btn);
		lifadian_btn = (Button) findViewById(R.id.lifadian_btn);
		xuexiao_btn = (Button) findViewById(R.id.xuexiao_btn);
		jingju_btn = (Button) findViewById(R.id.jingju_btn);
		jiazheng_btn = (Button) findViewById(R.id.jiazheng_btn);
		huochezhan_btn = (Button) findViewById(R.id.huochezhan_btn);
		jichang_btn = (Button) findViewById(R.id.jichang_btn);
		qichexiuli_btn = (Button) findViewById(R.id.qichexiuli_btn);
		sdian_btn = (Button) findViewById(R.id.sdian_btn);
		qichezhan_btn = (Button) findViewById(R.id.qichezhan_btn);

		img_qita = (ImageView)findViewById(R.id.qita);
		hunsha_btn = (Button)findViewById(R.id.hunsha_btn);
		zhubao_btn = (Button)findViewById(R.id.zhubao_btn);
		yishupin_btn = (Button)findViewById(R.id.yishupin_btn);
		guanggaoyinshua_btn = (Button)findViewById(R.id.guanggaoyinshua_btn);
		lvyou_btn = (Button)findViewById(R.id.lvyou_btn);
		fangchan_btn = (Button)findViewById(R.id.fangchan_btn);
		jinrong_btn = (Button)findViewById(R.id.jinrong_btn);
		zhongjie_btn = (Button)findViewById(R.id.zhongjie_btn);
		guwancheng_btn = (Button)findViewById(R.id.guwancheng_btn);
		
		goback.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				startActivityForResult(new Intent(NearbySearchActivity.this,
						CityActivity.class), 4321);
			}
		});

		main_speak.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (net()) {
					PackageManager pm = getPackageManager();
					List<ResolveInfo> activities = pm
							.queryIntentActivities(new Intent(
									RecognizerIntent.ACTION_RECOGNIZE_SPEECH),
									0);
					if (activities.size() != 0) {
						if (v.getId() == R.id.main_speak) {
							startActivityForResult(new Intent(
									NearbySearchActivity.this,
									listActivity.class), 4321);
						}
					} else {
						Update update = new Update(NearbySearchActivity.this);
						update.check();
					}
				} else
					Toast("无网络", 1500);
			}
		});

		rangebtn.setOnClickListener(new RangeClickListener());
		food.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				query = "美食";
				isSearching = true;
				showDialog(Const.PROGRESSBAR_WAIT);
			}
		});
		qichezhan_btn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				query = "汽车站";
				isSearching = true;
				showDialog(Const.PROGRESSBAR_WAIT);
			}
		});
		sdian_btn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				query = "4S店";
				isSearching = true;
				showDialog(Const.PROGRESSBAR_WAIT);
			}
		});
		qichexiuli_btn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				query = "汽车修理";
				isSearching = true;
				showDialog(Const.PROGRESSBAR_WAIT);
			}
		});
		jichang_btn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				query = "机场";
				isSearching = true;
				showDialog(Const.PROGRESSBAR_WAIT);
			}
		});
		huochezhan_btn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				query = "火车站";
				isSearching = true;
				showDialog(Const.PROGRESSBAR_WAIT);
			}
		});
		jingju_btn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				query = "警局";
				isSearching = true;
				showDialog(Const.PROGRESSBAR_WAIT);
			}
		});
		jiazheng_btn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				query = "家政";
				isSearching = true;
				showDialog(Const.PROGRESSBAR_WAIT);
			}
		});
		xuexiao_btn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				query = "学校";
				isSearching = true;
				showDialog(Const.PROGRESSBAR_WAIT);
			}
		});

		lifadian_btn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				query = "理发店";
				isSearching = true;
				showDialog(Const.PROGRESSBAR_WAIT);
			}
		});
		xiyuanmo_btn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				query = "洗浴按摩";
				isSearching = true;
				showDialog(Const.PROGRESSBAR_WAIT);
			}
		});
		meirong_btn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				query = "美容院";
				isSearching = true;
				showDialog(Const.PROGRESSBAR_WAIT);
			}
		});
		gongyu_btn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				query = "公寓";
				isSearching = true;
				showDialog(Const.PROGRESSBAR_WAIT);
			}
		});

		bianlidian_btn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				query = "农家院";
				isSearching = true;
				showDialog(Const.PROGRESSBAR_WAIT);
			}
		});
		fuzhuangdian_btn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				query = "服装店";
				isSearching = true;
				showDialog(Const.PROGRESSBAR_WAIT);
			}
		});

		liaoyangyuan_btn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				query = "疗养院";
				isSearching = true;
				showDialog(Const.PROGRESSBAR_WAIT);
			}
		});

		zhongcanbtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				query = "中餐";
				isSearching = true;
				showDialog(Const.PROGRESSBAR_WAIT);
			}
		});

		zizhucan_btn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				query = "自助餐";
				isSearching = true;
				showDialog(Const.PROGRESSBAR_WAIT);
			}
		});

		xiaochikuaicanbtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				query = "小吃";
				isSearching = true;
				showDialog(Const.PROGRESSBAR_WAIT);
			}
		});
		huoguobtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				query = "火锅";
				isSearching = true;
				showDialog(Const.PROGRESSBAR_WAIT);
			}
		});
		chuancaibtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				query = "川菜";
				isSearching = true;
				showDialog(Const.PROGRESSBAR_WAIT);
			}
		});
		xicanbtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				query = "西餐";
				isSearching = true;
				showDialog(Const.PROGRESSBAR_WAIT);
			}
		});
		kfcbtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				query = "肯德基";
				isSearching = true;
				showDialog(Const.PROGRESSBAR_WAIT);
			}
		});
		mcdonaldbtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				query = "麦当劳";
				isSearching = true;
				showDialog(Const.PROGRESSBAR_WAIT);
			}
		});
		dekeshi_btn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				query = "德克士";
				isSearching = true;
				showDialog(Const.PROGRESSBAR_WAIT);
			}
		});

		hotel.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				query = "住宿";
				isSearching = true;
				showDialog(Const.PROGRESSBAR_WAIT);
			}
		});
		kuaijiejiudianbtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				query = "快捷酒店";
				isSearching = true;
				showDialog(Const.PROGRESSBAR_WAIT);
			}
		});
		xingjijiudianbtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				query = "星级酒店";
				isSearching = true;
				showDialog(Const.PROGRESSBAR_WAIT);
			}
		});
		dujiacunbtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				query = "度假村";
				isSearching = true;
				showDialog(Const.PROGRESSBAR_WAIT);
			}
		});
		qingnianlvsebtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				query = "旅社";
				isSearching = true;
				showDialog(Const.PROGRESSBAR_WAIT);
			}
		});
		lvguanbtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				query = "旅馆";
				isSearching = true;
				showDialog(Const.PROGRESSBAR_WAIT);
			}
		});
		zhaodaisuobtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				query = "招待所";
				isSearching = true;
				showDialog(Const.PROGRESSBAR_WAIT);
			}
		});

		entertailment.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				query = "休闲娱乐";
				isSearching = true;
				showDialog(Const.PROGRESSBAR_WAIT);
			}
		});
		dianyinyuanbtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				query = "电影院";
				isSearching = true;
				showDialog(Const.PROGRESSBAR_WAIT);
			}
		});
		ktvbtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				query = "KTV";
				isSearching = true;
				showDialog(Const.PROGRESSBAR_WAIT);
			}
		});
		wangbabtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				query = "网吧";
				isSearching = true;
				showDialog(Const.PROGRESSBAR_WAIT);
			}
		});
		jiubabtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				query = "酒吧";
				isSearching = true;
				showDialog(Const.PROGRESSBAR_WAIT);
			}
		});
		kafeitingbtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				query = "咖啡厅";
				isSearching = true;
				showDialog(Const.PROGRESSBAR_WAIT);
			}
		});
		gongyuanbtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				query = "公园";
				isSearching = true;
				showDialog(Const.PROGRESSBAR_WAIT);
			}
		});

		life.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				query = "生活服务";
				isSearching = true;
				showDialog(Const.PROGRESSBAR_WAIT);
			}
		});
		chaoshibtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				query = "超市";
				isSearching = true;
				showDialog(Const.PROGRESSBAR_WAIT);
			}
		});
		yaodianbtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				query = "药店";
				isSearching = true;
				showDialog(Const.PROGRESSBAR_WAIT);
			}
		});
		yiyuanbtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				query = "医院";
				isSearching = true;
				showDialog(Const.PROGRESSBAR_WAIT);
			}
		});
		atmbtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				query = "ATM";
				isSearching = true;
				showDialog(Const.PROGRESSBAR_WAIT);
			}
		});
		yinhangbtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				query = "银行";
				isSearching = true;
				showDialog(Const.PROGRESSBAR_WAIT);
			}
		});
		cesuobtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				query = "厕所";
				isSearching = true;
				showDialog(Const.PROGRESSBAR_WAIT);
			}
		});

		traffic.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				query = "交通设施";
				isSearching = true;
				showDialog(Const.PROGRESSBAR_WAIT);
			}
		});
		gongjiaozhanbtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				query = "公交站";
				isSearching = true;
				showDialog(Const.PROGRESSBAR_WAIT);
			}
		});
		jiayouzhanbtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				query = "加油站";
				isSearching = true;
				showDialog(Const.PROGRESSBAR_WAIT);
			}
		});
		tingchechangbtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				query = "停车场";
				isSearching = true;
				showDialog(Const.PROGRESSBAR_WAIT);
			}
		});
		chepiaodaishoubtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				query = "车票代售";
				isSearching = true;
				showDialog(Const.PROGRESSBAR_WAIT);
			}
		});

		poisearchbtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				query = poisearchcontent.getText().toString();
				if (query == null || query.equals(""))
					return;
				isSearching = true;
				showDialog(Const.PROGRESSBAR_WAIT);
			}
		});


		img_qita = (ImageView)findViewById(R.id.qita);
		hunsha_btn = (Button)findViewById(R.id.hunsha_btn);
		zhubao_btn = (Button)findViewById(R.id.zhubao_btn);
		yishupin_btn = (Button)findViewById(R.id.yishupin_btn);
		guanggaoyinshua_btn = (Button)findViewById(R.id.guanggaoyinshua_btn);
		lvyou_btn = (Button)findViewById(R.id.lvyou_btn);
		fangchan_btn = (Button)findViewById(R.id.fangchan_btn);
		jinrong_btn = (Button)findViewById(R.id.jinrong_btn);
		zhongjie_btn = (Button)findViewById(R.id.zhongjie_btn);
		guwancheng_btn = (Button)findViewById(R.id.guwancheng_btn);
		

		img_qita.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				query = "其它";
				isSearching = true;
				showDialog(Const.PROGRESSBAR_WAIT);
			}
		});
		hunsha_btn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				query = "婚纱摄影";
				isSearching = true;
				showDialog(Const.PROGRESSBAR_WAIT);
			}
		});
		zhubao_btn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				query = "珠宝";
				isSearching = true;
				showDialog(Const.PROGRESSBAR_WAIT);
			}
		});
		yishupin_btn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				query = "艺术品";
				isSearching = true;
				showDialog(Const.PROGRESSBAR_WAIT);
			}
		});
		guanggaoyinshua_btn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				query = "广告印刷";
				isSearching = true;
				showDialog(Const.PROGRESSBAR_WAIT);
			}
		});
		lvyou_btn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				query = "旅游";
				isSearching = true;
				showDialog(Const.PROGRESSBAR_WAIT);
			}
		});
		fangchan_btn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				query = "房产";
				isSearching = true;
				showDialog(Const.PROGRESSBAR_WAIT);
			}
		});
		jinrong_btn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				query = "金融";
				isSearching = true;
				showDialog(Const.PROGRESSBAR_WAIT);
			}
		});
		zhongjie_btn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				query = "中介公司";
				isSearching = true;
				showDialog(Const.PROGRESSBAR_WAIT);
			}
		});
		guwancheng_btn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				query = "古玩城";
				isSearching = true;
				showDialog(Const.PROGRESSBAR_WAIT);
			}
		});
		
		app.mLocClient.registerLocationListener(mLocationListener);
	}

	private String getRange() {
		String ret = "500";
		String str = rangebtn.getText().toString();

		if (str.equals("500米"))
			ret = "500";
		if (str.equals("1000米"))
			ret = "1000";
		if (str.equals("2000米"))
			ret = "2000";
		if (str.equals("5000米"))
			ret = "5000";
		if (str.equals("全城"))
			ret = "30000";

		return ret;
	}

	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case Const.PROGRESSBAR_WAIT:
			LoadingDialog wait_pd = new LoadingDialog(this);
			// wait_pd.tv("正在定位中....");
			return wait_pd;
		}
		return null;
	}

	class RangeClickListener implements OnClickListener {

		public void onClick(View v) {
			new AlertDialog.Builder(NearbySearchActivity.this).setTitle("选择范围")
					.setItems(ranges, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							switch (which) {
							case 0:
								rangebtn.setText("500米");
								break;
							case 1:
								rangebtn.setText("1000米");
								break;
							case 2:
								rangebtn.setText("2000米");
								break;
							case 3:
								rangebtn.setText("5000米");
								break;
							case 4:
								rangebtn.setText("全城");
								break;
							}

							dialog.dismiss();
						}
					}).show();
		}
	}

	public class MyLocationListener implements BDLocationListener {
		public void onReceiveLocation(BDLocation location) {
			if (location == null || isSearching == false)
				return;
			String lat = Double.toString(location.getLatitude());
			String lng = Double.toString(location.getLongitude());
			String addr = location.getAddrStr();
			removeDialog(Const.PROGRESSBAR_WAIT);
			isSearching = false;
			if (mlocat.equals("")) {
				String[] cit = Locationm.getDataRegeocoding(
						NearbySearchActivity.this, lat + "," + lng).split(",");
				if (cit.length > 1) {
					strCity = cit[1].replace("市", "");
					goback.setText(strCity);
				}

			} else {
				String[] locat = mlocat.split(",");
				lat = locat[0];
				lng = locat[1];
			}
			Intent it = new Intent(_context, NearbySearchResListActivity.class);
			it.putExtra("range", getRange());
			it.putExtra("type", query);
			it.putExtra("lat", lat);
			it.putExtra("loa", loa);
			it.putExtra("lng", lng);
			it.putExtra("addr", addr);
			it.putExtra("city", strCity);
			startActivity(it);
		}

		public void onReceivePoi(BDLocation poiLocation) {
			if (poiLocation == null) {
				return;
			}
		}
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

	/**
	 * Handle the results from the recognition activity.
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case 4321:
			strCity = data.getStringExtra("strCity");
			mlocat = data.getStringExtra("locat");
			loa = "1";
			goback.setText(strCity);
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