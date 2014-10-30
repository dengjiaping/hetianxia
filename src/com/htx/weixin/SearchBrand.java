package com.htx.weixin;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.json.JSONArray;
import org.json.JSONObject;
import com.hetianxia.activity.R;
import com.htx.ad.ConnUrl;
import com.htx.conn.Const;
import com.htx.conn.HttpHelper;
import com.htx.main.TabTest;
import com.htx.pub.LoadingDialog;
import com.htx.pub.MySharedData;
import com.htx.pub.MyToast;
import com.htx.pub.PubUserActivity;
import com.htx.search.SiteHelper;
import com.zijunlin.Zxing.Demo.CaptureActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class SearchBrand extends PubUserActivity {

	private Button btn_sao,btn_sou;
	private ListView lv;	
	private LoadingDialog dialoga;
	private DataAdapter adapter;
	private Context _context;
	private LayoutInflater inflater;
	private View footer;
	private int page = 0;
	private TextView tView;
	private int ifirst = 0;
	private int  pagecount = 0;
	private EditText edt_sousuo;
	private RelativeLayout rLayout1;
	private String typestr = "1";
	private String key_str = "";
	private LinearLayout layout,rLayout2;
	private TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8,tv9,tv10,tv11;

	ArrayList<ShopHome_Bean> dataArray = new ArrayList<ShopHome_Bean>();
	private Handler mHandl = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				dialoga.dismiss();
				initListView();

//				adapter.notifyDataSetChanged();
				break;
			case 2:
				dialoga.dismiss();
				MyToast.toast(SearchBrand.this, "网络异常", 1000);
				break;
			}
			super.handleMessage(msg);
		}

	};
	
	public void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.nearbysearchbrand);
		super.onCreate(savedInstanceState);
		dialoga = new LoadingDialog(SearchBrand.this);
		_context = this;
		inflater = LayoutInflater.from(_context);
		footer = inflater.inflate(R.layout.search_footer2, null);
		tView = (TextView)footer.findViewById(R.id.more_receive);
		rLayout1 = (RelativeLayout)findViewById(R.id.nearbybrand_rl1);
		rLayout2 = (LinearLayout)findViewById(R.id.nearbybrand_rl2);

		if (getIntent().getExtras().get("type").equals("1")) {
			rLayout1.setVisibility(View.VISIBLE);
			rLayout2.setVisibility(View.GONE);
		}
		else if (getIntent().getExtras().get("type").equals("2")) {
			rLayout1.setVisibility(View.GONE);
			rLayout2.setVisibility(View.VISIBLE);
		}
		
		if(getIntent().getExtras().get("key") != null)
		{
			key_str = getIntent().getExtras().get("key").toString();
		}
		
		init();
		onclick();
		
		dialoga.show();
		new Thread(new Runnable() {
			public void run() {
				// 得到目录
				getData1("0", "1");
			}
		}).start();
		
	}
	
	private void init()
	{
		btn_sao = (Button)findViewById(R.id.nearbysearchbrand_saobtn);
		btn_sou = (Button)findViewById(R.id.nearbysearchbrand_saobtn2);
		lv = (ListView)findViewById(R.id.nearbysearchbrand_lv);
		edt_sousuo = (EditText)findViewById(R.id.nearby_edt_sousuo2);
		layout = (LinearLayout)findViewById(R.id.nearby_brandno_layout);
//		pb = findViewById(R.id.pull_to_load_footer_progressbar);
//		lv.addFooterView(pb);
		lv.addFooterView(footer);
		
		tv1 = (TextView)findViewById(R.id.nearbrand_tv1);
		tv2 = (TextView)findViewById(R.id.nearbrand_tv2);
		tv3 = (TextView)findViewById(R.id.nearbrand_tv3);
		tv4 = (TextView)findViewById(R.id.nearbrand_tv4);
		tv5 = (TextView)findViewById(R.id.nearbrand_tv5);
		tv6 = (TextView)findViewById(R.id.nearbrand_tv6);
		tv7 = (TextView)findViewById(R.id.nearbrand_tv7);
		tv8 = (TextView)findViewById(R.id.nearbrand_tv8);
		tv9 = (TextView)findViewById(R.id.nearbrand_tv9);
		tv10 = (TextView)findViewById(R.id.nearbrand_tv10);
		tv11 = (TextView)findViewById(R.id.nearbrand_tv11);
		
	}
	
	private void onclick()
	{
		btn_sao.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {

				startActivity(new Intent(SearchBrand.this,
						CaptureActivity.class));
			}
		});
		
		btn_sou.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SearchBrand.this,SearchBrand.class);
				intent.putExtra("type", "2");
				intent.putExtra("key", edt_sousuo.getText().toString());
				if (edt_sousuo.getText().toString().length()>0) {
					startActivity(intent);
					finish();
				}
				else {
					Toast("请输入您要搜索的品牌", 3000);
				}
			}
		});
		tv1.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SearchBrand.this,SearchBrand.class);
				intent.putExtra("type", "2");
				intent.putExtra("key", "福景园");
				startActivity(intent);
				finish();
			}
		});
		tv2.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SearchBrand.this,SearchBrand.class);
				intent.putExtra("type", "2");
				intent.putExtra("key", "金字烩面");
				startActivity(intent);
				finish();
			}
		});
		tv3.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SearchBrand.this,SearchBrand.class);
				intent.putExtra("type", "2");
				intent.putExtra("key", "好想你");
				startActivity(intent);
				finish();
			}
		});
		tv4.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SearchBrand.this,SearchBrand.class);
				intent.putExtra("type", "2");
				intent.putExtra("key", "大骨头");
				startActivity(intent);
				finish();
			}
		});
		tv5.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SearchBrand.this,SearchBrand.class);
				intent.putExtra("type", "2");
				intent.putExtra("key", "西普德");
				startActivity(intent);
				finish();
			}
		});
		tv6.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SearchBrand.this,SearchBrand.class);
				intent.putExtra("type", "2");
				intent.putExtra("key", "萧记");
				startActivity(intent);
				finish();
			}
		});
		tv7.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SearchBrand.this,SearchBrand.class);
				intent.putExtra("type", "2");
				intent.putExtra("key", "杨记牛肉拉面");
				startActivity(intent);
				finish();
			}
		});
		tv8.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SearchBrand.this,SearchBrand.class);
				intent.putExtra("type", "2");
				intent.putExtra("key", "粥鼎记");
				startActivity(intent);
				finish();
			}
		});
		tv9.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SearchBrand.this,SearchBrand.class);
				intent.putExtra("type", "2");
				intent.putExtra("key", "好想你");
				startActivity(intent);
				finish();
			}
		});
		tv10.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SearchBrand.this,SearchBrand.class);
				intent.putExtra("type", "2");
				intent.putExtra("key", "时光假日酒店");
				startActivity(intent);
				finish();
			}
		});
		tv11.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SearchBrand.this,SearchBrand.class);
				intent.putExtra("type", "2");
				intent.putExtra("key", "歌乐咖啡店");
				startActivity(intent);
				finish();
			}
		});
		
	}
	


	private void getData1(String cid, String isFocus) {
		String url = "http://api.36936.com/ClientApi/Pos/getStoreMainList.ashx?";
		String adUserId = MySharedData.sharedata_ReadString(this, "UserId");
		String hash = SiteHelper.MD5(adUserId + Const.UrlHashKey);
		if (pagecount != 0) {
			if(pagecount>page)
			{
				page++;
			}
		}
		else {
			page++;
		}
		url += "&userId=" + adUserId+"&l=10&page="+page+"&x="+TabTest.latDouble+"&y="+TabTest.lonDouble+"&k="+URLEncoder.encode("福");
		if (key_str.length()>0) {
			url += "&key="+key_str;
		}
		
		//list1 = new ArrayList<HashMap<String, String>>();
//		dataArray = new ArrayList<ShopHome_Bean>();
		Message message = new Message();
		try {
			String reStr = HttpHelper.getJsonData(SearchBrand.this, url);
			JSONObject reObject = new JSONObject(reStr);
			if (reObject.getInt("status") == 1) {
				JSONObject ob = reObject.getJSONObject("result");
				pagecount = Integer.parseInt(ob.getString("pageCount"));
				JSONArray reArray = ob.getJSONArray("list");
				for (int i = 0; i < reArray.length(); i++) {
					JSONObject jsonObject = (JSONObject) reArray.opt(i);
					ShopHome_Bean bean = new ShopHome_Bean(jsonObject.getString("id")
							, jsonObject.getString("title")
							, jsonObject.getString("brief")
							, jsonObject.getString("address")
							, jsonObject.getString("isFocus")
							, jsonObject.getString("logo")
							, jsonObject.getString("busNum"));
					dataArray.add(bean);
				}
				message.what = 1;
			} else {
				message.what = 1;
			}

		} catch (Exception e) {
			e.printStackTrace();
			message.what = 2;
		}
		mHandl.sendMessage(message);
	}

	private void initListView() {
		if (dataArray.size()>0) {
			
			layout.setVisibility(View.GONE);
			
			if (ifirst==0) {
				adapter = new DataAdapter(dataArray);
				lv.setAdapter(adapter);
			}
			else {
				adapter.notifyDataSetChanged();
			}
		

		tView.setVisibility(View.GONE);
		
		lv.setOnScrollListener(new OnScrollListener() {

			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
					if (view.getLastVisiblePosition() == view.getCount() - 1) {
						if(pagecount>page)
						{
							tView.setVisibility(View.VISIBLE);
							ifirst = 1;
//							dialoga.show();
							new Thread(new Runnable() {
								public void run() {
									// 得到目录
									getData1("0", "1");
								}
							}).start();
						}
					}
				}
				
			}
			
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				
			}
		});
		// 添加点击
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

					if (dataArray.get(arg2).getBusNum().equals("1") ) {
		
							if (dataArray.get(arg2).getBusNum().equals("1")) {
								Intent intent = new Intent(SearchBrand.this,
								ShopHomeInfo.class);
								intent.putExtra("StoreId", dataArray.get(arg2).getId());
								intent.putExtra("title", dataArray.get(arg2).getTitle());
								intent.putExtra("address", dataArray.get(arg2).getAddress());
								intent.putExtra("brief", dataArray.get(arg2).getBrief());
								intent.putExtra("isFocus", dataArray.get(arg2).getIsFocus());
								intent.putExtra("logo", dataArray.get(arg2).getLogo());
								startActivity(intent);
							}
							else {
								Intent intent = new Intent(SearchBrand.this,SearchShop.class);
								intent.putExtra("StoreId", dataArray.get(arg2).getId());
								startActivity(intent);
							}
					}
					else {
						
						Intent intent = new Intent(SearchBrand.this,
								SearchShop.class);
						intent.putExtra("url", dataArray.get(arg2).getLogo());
						intent.putExtra("titleStr", dataArray.get(arg2).getTitle());
						intent.putExtra("welcome", "");
						intent.putExtra("StoreId", dataArray.get(arg2).getId());
						intent.putExtra("brief", dataArray.get(arg2).getBrief());
						SimpleDateFormat formatter = new SimpleDateFormat(
								"yyyy-MM-dd   HH:mm");
						Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
						String str = formatter.format(curDate);
						intent.putExtra("time", str);
						startActivity(intent);
						
					}
			
			}
		});
		}
		else {
			lv.setVisibility(View.GONE);
			layout.setVisibility(View.VISIBLE);
		}
	};


	// 处理数据的
	class DataAdapter extends BaseAdapter {

		ArrayList<ShopHome_Bean> list1;

		public DataAdapter(ArrayList<ShopHome_Bean> list1) {
			this.list1 = list1;
		}

		public int getCount() {
			return list1.size();
		}

		public ShopHome_Bean getItem(int position) {
			return list1.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View view, ViewGroup parent) {

			view = getLayoutInflater().inflate(R.layout.shophomeitem2, null);

			// 图片
			final String url = list1.get(position).getLogo();

			final ImageView iv = (ImageView) view.findViewById(R.id.imageView1);

			if (!url.equals("")) {
				
				iv.setImageBitmap(ConnUrl.useTheImage(SearchBrand.this, url));
			}

			LinearLayout layout = (LinearLayout)view.findViewById(R.id.layout_bg);
			layout.setBackgroundResource(R.drawable.brand_bg1);
			if (position%4==0) {
				layout.setBackgroundResource(R.drawable.brand_bg4);
			}
			else if (position%4==1) {
				layout.setBackgroundResource(R.drawable.brand_bg1);
			}
			else if (position%4==2) {
				layout.setBackgroundResource(R.drawable.brand_bg3);
			}
			else if (position%4==3) {
				layout.setBackgroundResource(R.drawable.brand_bg2);
			}
			// 商家名称
			TextView tvTitle = (TextView) view.findViewById(R.id.tv1);
			tvTitle.setText(list1.get(position).getTitle());

			// 广告语
			TextView tvAdTitle = (TextView) view.findViewById(R.id.tv2);
			tvAdTitle.setText(list1.get(position).getBrief());

			String isFocus = list1.get(position).getIsFocus();
			//ImageView iviv = (ImageView) view.findViewById(R.id.iviv);
			if (isFocus.equals("1")) {
				//iviv.setImageResource(R.drawable.guanz_17);
			}
			return view;
		}

		/**
		 * 添加数据列表项
		 * 
		 * @param dataitem
		 */
		public void addDataItem(ShopHome_Bean dataitem) {
			list1.add(dataitem);
		}

	}

}