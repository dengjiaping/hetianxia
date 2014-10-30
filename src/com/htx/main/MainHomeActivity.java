package com.htx.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import org.json.JSONArray;
import org.json.JSONObject;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;
import cn.anycall.map.DownloadActivity;
import cn.anycall.map.NearbySearchActivity;
import com.hetianxia.activity.R;
import com.htx.ad.ConnUrl;
import com.htx.bank.BankList;
import com.htx.conn.HttpHelper;
import com.htx.pub.ActivityManager;
import com.htx.pub.ApplicationUser;
import com.htx.pub.MySharedData;
import com.htx.pub.PubActivity;
import com.htx.pub.WebViews;
import com.htx.search.searchHomeActivity;
import com.htx.user.A_shakeActivity;
import com.htx.user.U_Login;
import com.htx.weixin.SearchGuan;
import com.maiduo.bll.AsyncImageLoader;
import com.maiduo.model.Product_home;
import com.zijunlin.Zxing.Demo.CaptureActivity;

public class MainHomeActivity extends PubActivity implements OnClickListener {

	private SildeShowWidget mViewFlipper;
	private int imageCount = 4;
	private ArrayList<Bitmap> imageSource;
	private int index = 0;
	private RelativeLayout sildeshowLayout;
	private TextView txtTitle[];
	private List<Product_home> productlist = new ArrayList<Product_home>();
	private AsyncImageLoader asyImg = new AsyncImageLoader();
	private ListView gridView ;
	private String JSONURL = "http://api.36936.com/ClientApi/ClientAd.ashx?Type=SlideAd";
	private ImageView c_idea1, c_idea2, c_idea3, c_idea4, c_idea5, c_idea6,
			c_idea7, c_idea8, c_idea9;
	private List<String> imagestr = new ArrayList<String>();
	private List<String> urlstr = new ArrayList<String>();
	private ProductAdapter adapter;
	private  View mheader ;

	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 100:
				changePointSelect(index);
				break;
			case 101:
				break;
			case 1:// 填充GridView
				
				adapter = new ProductAdapter(productlist);
				gridView.setAdapter(adapter);
				gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					public void onItemClick(AdapterView<?> adapterView,
							View view, int arg2, long arg3) {
						Intent intent = new Intent();
						intent.putExtra("id", adapter.getItem(arg2).getPid()
								+ "");
						intent.setClass(MainHomeActivity.this, com.maiduo.Product.class);
						startActivity(intent);
					}
				});
				break;
			}
			super.handleMessage(msg);
		}
	};

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mian_home);
		
		 mheader = getLayoutInflater().inflate(R.layout.mian_home_header, null);
		  gridView = (ListView) findViewById(R.id.sy_list);
		  gridView.addHeaderView(mheader);
		String xystr = "113.42,34.44";
		if (TabTest.latDouble ==0) {
			
		}
		else {
			xystr = TabTest.lonDouble+","+TabTest.latDouble;
		}
		JSONURL = JSONURL +"&xy="+xystr;
		sildeshowLayout = (RelativeLayout) mheader.findViewById(R.id.sildeshow_layout);
		imageSource = new ArrayList<Bitmap>();
		try {
			String reStr = HttpHelper.getJsonData(MainHomeActivity.this, JSONURL);
			JSONObject reObject = new JSONObject(reStr);
			if (reObject.getInt("status") == 1) {
				JSONObject ob = reObject.getJSONObject("result");
				JSONArray reArray = ob.getJSONArray("list");
				for (int i = 0; i < reArray.length(); i++) {
					JSONObject jsonObject = (JSONObject) reArray.opt(i);
					urlstr.add(jsonObject.getString("url"));
					imagestr.add(jsonObject.getString("pic"));
					imageSource.add( ConnUrl.useTheImage(MainHomeActivity.this, jsonObject.getString("pic")));
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		imageSource.add(R.drawable.silde1);
//		imageSource.add(R.drawable.silde2);
//		imageSource.add(R.drawable.silde3);
//		imageSource.add(R.drawable.silde4);

		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT);
		params.alignWithParent = true;
		mViewFlipper = new SildeShowWidget(this, imageSource,urlstr);
		mViewFlipper.setLayoutParams(params);
		sildeshowLayout.addView(mViewFlipper);

		imageCount = imageSource.size();
		mViewFlipper.startSildeShow();

		txtTitle = new TextView[imageCount];
		initview();
		mDisplayViewIDThread.start();

		new Thread(new Runnable() {
			public void run() {

				getData("http://api.36936.com/ClientApi/PointsShop/Productindex.ashx");// 获取数据
			}
		}).start();
	}


	private void getData(String url) {

		List<News> newslist = new ArrayList<News>();
		// 这里需要分析服务器回传的json格式数据，
		try {
			JSONObject jsonObject = new JSONObject(HttpHelper.getJsonData(
					MainHomeActivity.this, url));
			// 获取产品信息
			JSONArray jsonArray1 = jsonObject.getJSONArray("prolist");
			for (int i = 0; i < jsonArray1.length(); i++) {
				JSONObject jsonObject2 = (JSONObject) jsonArray1.opt(i);
				Product_home product = new Product_home();
				product.setPid(Integer.parseInt(jsonObject2.getString("ProductId")));
				product.setPro_name(jsonObject2.getString("pro_name"));
				product.setPrice(jsonObject2.getString("price"));
				product.setSmallimg(jsonObject2.getString("smallimg"));
				productlist.add(product);
			}

			Message msg = new Message();
			msg.what = 1;
			mHandler.sendMessage(msg);

		} catch (Exception e) {
			Toast.makeText(this, getString(R.string.loading_error),
					Toast.LENGTH_SHORT).show();
		}

	}

	
	public void initview() {
		LinearLayout l = (LinearLayout) mheader.findViewById(R.id.sildeshow_title_point_layout);
		for (int i = 0; i < imageCount; i++) {
			LayoutParams params = new LayoutParams(
					ViewGroup.LayoutParams.WRAP_CONTENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
			params.setMargins(4, 4, 4, 4);
			txtTitle[i] = new TextView(this);
			txtTitle[i].setHeight(8);
			txtTitle[i].setWidth(8);
			Drawable drawtxt = this.getResources().getDrawable(
					R.drawable.sildeshow_title_point);
			txtTitle[i].setBackgroundDrawable(drawtxt);
			txtTitle[i].setLayoutParams(params);
			l.addView(txtTitle[i]);
			txtTitle[i].setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Toast("sdfs", 3000);
				}
			});
		}

		c_idea1 = (ImageView) mheader.findViewById(R.id.c_idea1);
		c_idea1.setTag(1);
		c_idea1.setOnClickListener(this);
		c_idea2 = (ImageView) mheader.findViewById(R.id.c_idea2);
		c_idea2.setTag(2);
		c_idea2.setOnClickListener(this);
//		c_idea3 = (ImageView) findViewById(R.id.c_idea3);
//		c_idea3.setTag(3);
//		c_idea3.setOnClickListener(this);
		c_idea4 = (ImageView) mheader.findViewById(R.id.c_idea4);
		c_idea4.setTag(4);
		c_idea4.setOnClickListener(this);
//		c_idea5 = (ImageView) findViewById(R.id.c_idea5);
//		c_idea5.setTag(5);
//		c_idea5.setOnClickListener(this);
		c_idea6 = (ImageView) mheader.findViewById(R.id.c_idea6);
		c_idea6.setTag(6);
		c_idea6.setOnClickListener(this);
//		c_idea7 = (ImageView) findViewById(R.id.c_idea7);
//		c_idea7.setTag(7);
//		c_idea7.setOnClickListener(this);
		c_idea8 = (ImageView) mheader.findViewById(R.id.c_idea8);
		c_idea8.setTag(8);
		c_idea8.setOnClickListener(this);
//		c_idea9 = (ImageView) findViewById(R.id.c_idea9);
//		c_idea9.setTag(9);
//		c_idea9.setOnClickListener(this);
	}

	private void changePointSelect(int index) {
		for (int i = 0; i < imageCount; i++) {
			if (i == index) {
				txtTitle[i].setSelected(true);
			} else {
				txtTitle[i].setSelected(false);
			}
		}
	}

	Thread mDisplayViewIDThread = new Thread(new Runnable() {

		public void run() {
			while (true) {
				if (index != mViewFlipper.getDisplayedChild()) {
					index = mViewFlipper.getDisplayedChild();
					Message msg = new Message();
					msg.what = 100;
					mHandler.sendMessage(msg);
				}
			}
		}
	});

	class News {
		int resid;
		String title;
	}

	/**
	 * 多按钮监听
	 */
	public void onClick(View v) {
		if (ApplicationUser.CheckLoginStatus(MainHomeActivity.this)) {
			int tag = (Integer) v.getTag();
			switch (tag) {
			case 1:
				startActivity(new Intent(MainHomeActivity.this, SearchGuan.class));
				break;
			case 2:// 搜索编辑框
				startActivity(new Intent(MainHomeActivity.this,
						searchHomeActivity.class));
				break;
			case 3:// "条形码二维码"
				startActivity(new Intent(MainHomeActivity.this,
						CaptureActivity.class));
				break;
			case 4://
				startActivity(new Intent(MainHomeActivity.this, BankList.class));
				break;
			case 5:// 话费充值
//				String url = "http://r.m.taobao.com/m3?p=mm_32843449_4088662_14286555&c=1561";
				String url = "http://wvs.m.taobao.com";
				Intent intenat = new Intent(MainHomeActivity.this,
						WebViews.class);
				intenat.putExtra("url", url);
				startActivity(intenat);
				break;
			case 6://
				startActivity(new Intent(MainHomeActivity.this,
						A_shakeActivity.class));
				break;
			case 7://
				startActivity(new Intent(MainHomeActivity.this,
						NearbySearchActivity.class));
				break;
			case 8:
				String url31 = "http://click.linktech.cn/? m=tdianping&a=A100137234&l=99999&l_cd1=0&l_cd2=1&u_id="
						+ MySharedData.sharedata_ReadString(this, "UserId")
						+ "&tu=http%3A%2F%2Fwap.dianping.com%2Ftuan";
				Intent inten31 = new Intent(MainHomeActivity.this,
						WebViews.class);
				inten31.putExtra("url", url31);
				startActivity(inten31);
				break;
			case 9:
				Intent intentt = new Intent();
				intentt.setClassName("com.hetianxia.daohang", "cn.anycall.map.LuActivity");
				Intent intent1 = new Intent(MainHomeActivity.this,DownloadActivity.class);
				if (getPackageManager().resolveActivity(intentt, 0) ==null ) {
					startActivity(intent1);
				}
				else {
					startActivity(intentt);
				}
				
				break;
			}
		} else {
			startActivity(new Intent(MainHomeActivity.this, U_Login.class));
		}
	}

	class ProductAdapter extends BaseAdapter {
		List<Product_home> productItems;

		public ProductAdapter(List<Product_home> productitems) {
			this.productItems = productitems;
		}

		public int getCount() {
			return productItems.size();
		}

		public Product_home getItem(int position) {
			return productItems.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View view, ViewGroup parent) {
			view = getLayoutInflater().inflate(R.layout.griditem, null);

			TextView tvPec = (TextView) view.findViewById(R.id.pec);
			tvPec.setText("权益宝 "
					+ productItems.get(position).getPrice()
							.replaceAll(".00", "") + "个");
			// 图片
			String url = productItems.get(position).getSmallimg();
			asyImg.LoadImage(url, (ImageView) view.findViewById(R.id.pic));
			// 标题
			TextView tvTitle = (TextView) view.findViewById(R.id.title);
			tvTitle.setText(productItems.get(position).getPro_name());
			// +"￥："+productItems.get(position).getPrice()

			return view;
		}

		/**
		 * 添加数据列表项
		 * 
		 * @param productitem
		 */
		public void addProductItem(Product_home productitem) {
			productItems.add(productitem);
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
