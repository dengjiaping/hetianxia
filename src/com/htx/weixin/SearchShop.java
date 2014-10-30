package com.htx.weixin;

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
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;

public class SearchShop extends PubUserActivity {

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
				MyToast.toast(SearchShop.this, "网络异常", 1000);
				break;
			}
			super.handleMessage(msg);
		}

	};

	public void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.nearbysearchshop);
		super.onCreate(savedInstanceState);
		dialoga = new LoadingDialog(SearchShop.this);
		_context = this;
		inflater = LayoutInflater.from(_context);
		footer = inflater.inflate(R.layout.search_footer2, null);
		tView = (TextView)footer.findViewById(R.id.more_receive);
		
		init();
		
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
		lv = (ListView)findViewById(R.id.nearbysearchshop_lv);
		lv.addFooterView(footer);
	}

	private void getData1(String cid, String isFocus) {
		String url = "http://api.36936.com/ClientApi/Pos/getStoreList.ashx?";
		String adUserId = MySharedData.sharedata_ReadString(this, "UserId");
		String bid_str = getIntent().getStringExtra("StoreId");
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
		url += "userId=" + adUserId+"&bid="+bid_str+"&l=10&page="+page+"&x="+TabTest.latDouble+"&y="+TabTest.lonDouble;
		//list1 = new ArrayList<HashMap<String, String>>();
//		dataArray = new ArrayList<ShopHome_Bean>();
		Message message = new Message();
		try {
			String reStr = HttpHelper.getJsonData(SearchShop.this, url);
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
							, jsonObject.getString("ReturnRatio"),"");
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
				
				if (dataArray.get(arg2).getIsFocus().equals("1")) {

					Intent intent = new Intent(SearchShop.this,
							ChatMainActivity.class);
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
				else {
					Intent intent = new Intent(SearchShop.this,
							ShopHomeInfo.class);
					intent.putExtra("StoreId", dataArray.get(arg2).getId());
					intent.putExtra("title", dataArray.get(arg2).getTitle());
					intent.putExtra("address", dataArray.get(arg2).getAddress());
					intent.putExtra("brief", dataArray.get(arg2).getBrief());
					intent.putExtra("isFocus", dataArray.get(arg2).getIsFocus());
					intent.putExtra("logo", dataArray.get(arg2).getLogo());
					startActivity(intent);
					
				}
			}
		});
		}
		else {
			lv.setVisibility(View.GONE);
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

			view = getLayoutInflater().inflate(R.layout.shophomeitem4, null);

			// 图片
			final String url = list1.get(position).getLogo();

			final ImageView iv = (ImageView) view.findViewById(R.id.imageView1);

			if (!url.equals("")) {
				
				iv.setImageBitmap(ConnUrl.useTheImage(SearchShop.this, url));
			}

			LinearLayout layout = (LinearLayout)view.findViewById(R.id.shoplayout);
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
			tvAdTitle.setText("优惠：最高享受全单直返"+list1.get(position).getReturnRatio()+"%");

			String isFocus = list1.get(position).getIsFocus();
		//	ImageView iviv = (ImageView) view.findViewById(R.id.iviv);
			if (isFocus.equals("1")) {
			//	iviv.setImageResource(R.drawable.guanz_17);
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