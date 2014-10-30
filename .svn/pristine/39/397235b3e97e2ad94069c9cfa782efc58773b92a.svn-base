package com.htx.weixin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONObject;
import com.hetianxia.activity.R;
import com.htx.conn.Const;
import com.htx.conn.HttpHelper;
import com.htx.pub.LoadingDialog;
import com.htx.pub.MySharedData;
import com.htx.pub.MyToast;
import com.htx.search.SiteHelper;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

public class StoreComment extends Activity {
	private ListView listView;
	private Button back ,ping;
//	List<HashMap<String, String>> items = new ArrayList<HashMap<String, String>>();
	private int visibleLastIndex = 0; // 最后的可视项索引
	private int visibleItemCount; // 当前窗口可见项总数
	private int page = 1; // 当前页数
	private int totalPage = 1; // 总页数
	private int totalResult = 0; // 总个数
	private Intent in;
	private String StoreId;
	private int showCount = 0;// 单页显示个数
	private ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
	private ListViewAdapter adapter;
	private View loadMoreView;
	private int count = 0; // 当前页面应显示数目
	private ProgressBar loadMoreButton;
	private LoadingDialog dialoga;
	private String string = "";
	private String strcontent ="";
	private String data = "";
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				dialoga.dismiss();
//				initAdapter();
				listView.setAdapter(adapter);// 自动为id是list的ListView设置适配器
				if (totalResult < 16) {
					loadMoreButton.setVisibility(View.GONE);
					return;
				}
				break;
			case 2:
				dialoga.dismiss();
				MyToast.toast(StoreComment.this, string,1000);
				break;
			case 3:
				adapter.notifyDataSetChanged(); // 数据集变化后,通知adapter
				listView.setSelection(visibleLastIndex - visibleItemCount + 1);
				break;
			}
			super.handleMessage(msg);
		}
	};

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.homeletter);

	 	in = getIntent();
		StoreId = in.getStringExtra("StoreId");
	
		data = in.getStringExtra("data");
		System.out.println("data_________"+data);
		back = (Button) findViewById(R.id.back);
		ping = (Button) findViewById(R.id.ping);
		listView = (ListView) findViewById(R.id.list);
		loadMoreView = getLayoutInflater().inflate(R.layout.load_more, null);
		loadMoreButton = (ProgressBar) loadMoreView
				.findViewById(R.id.loadMoreButton);
		listView.addFooterView(loadMoreView); // 设置列表底部视图
		dialoga = new LoadingDialog(StoreComment.this);

		if (data != null) {
			if (data.length()>0) {
				ping.setVisibility(View.GONE);
			}
		}
		dialoga.show();
		new Thread(new Runnable() {
			public void run() {	
				if (data != null) {
					if (data.length()>0) {
						GetData2();
					}
					else {
						GetData();
					}
				}
				else {
					GetData();
				}
				
			}
		}).start();

		//listView.setOnScrollListener(StoreComment.this); // 添加滑动监听

		back.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
		
		ping.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(StoreComment.this,
						shop_toSpeak.class);
				intent.putExtra("StoreId",
						StoreId);

				startActivityForResult(intent, 4322);
			}
		});
	}

	private void GetData2()
	{
		
		Message message = new Message();
		try {
			JSONObject reObject = new JSONObject(data);
			if (reObject.getInt("status") == 1) {
				JSONObject ob = reObject.getJSONObject("result");
				
				if(ob.has("review")){
				JSONArray rearry = ob.getJSONArray("review");

				list = new ArrayList<HashMap<String, String>>();
				for (int i = 0; i < rearry.length(); i++) {
					JSONObject jsonObject = (JSONObject) rearry.opt(i);
					HashMap<String, String> map = new HashMap<String, String>();
					map.put("AddTime", jsonObject.getString("date"));
					map.put("UserName", jsonObject.getString("user_name"));
					map.put("Comment", jsonObject.getString("content"));
					map.put("Reply", "");
					map.put("ReplyTime", "");
					list.add(map);
				}
				adapter = new ListViewAdapter(list, this);
				
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

		mHandler.sendMessage(message);
		
	}
	
	private void GetData() {

		String user = MySharedData.sharedata_ReadString(StoreComment.this, "UserId");
		String hash = SiteHelper.MD5(user + Const.UrlHashKey).toLowerCase();
		String url ="http://api.36936.com/ClientApi/Pos/getStoreComment.ashx?userId=" + user + "&hash=" + hash
				+ "&StoreId=" + StoreId +"&page=" + page;
		System.out.println("url_______"+url);
		Message message = new Message();
		try {
			String reStr = HttpHelper.getJsonData(StoreComment.this, url);
			JSONObject reObject = new JSONObject(reStr);
			if (reObject.getInt("status") == 1) {
				page += 1;
				JSONObject mPage = reObject.getJSONObject("result");
				totalPage = Integer.parseInt(mPage.getString("pageCount"));
				totalResult = Integer.parseInt(mPage.getString("count"));
				showCount =15;
				JSONArray reArray = mPage.getJSONArray("list");
				
				list = new ArrayList<HashMap<String, String>>();
				for (int i = 0; i < reArray.length(); i++) {
					JSONObject jsonObject = (JSONObject) reArray.opt(i);
					HashMap<String, String> map = new HashMap<String, String>();
					map.put("AddTime", jsonObject.getString("AddTime"));
					map.put("UserName", jsonObject.getString("UserName"));
					map.put("Comment", jsonObject.getString("Comment"));
					map.put("Reply", jsonObject.getString("Reply"));
					map.put("ReplyTime", jsonObject.getString("ReplyTime"));
					list.add(map);

				}
				adapter = new ListViewAdapter(list, this);
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
		mHandler.sendMessage(message);

	}

	/*
	private void GetMoreData() {

		String user = MySharedData.sharedata_ReadString(StoreComment.this, "UserId");
		String hash = SiteHelper.MD5(user + Const.UrlHashKey).toLowerCase();
		String url ="http://api.36936.com/ClientApi/Pos/getStoreComment.ashx?userId=" + user + "&hash=" + hash
				+ "&StoreId=" + StoreId +"&page=" + page;
		Message message = new Message();
		try {
			String reStr = HttpHelper.getJsonData(StoreComment.this, url);
			JSONObject reObject = new JSONObject(reStr);
			if (reObject.getInt("status") == 1) {
				page += 1;
				JSONObject mPage = reObject.getJSONObject("result");
				totalPage = Integer.parseInt(mPage.getString("pageCount"));
				totalResult = Integer.parseInt(mPage.getString("count"));
				showCount =15;
				JSONArray reArray = mPage.getJSONArray("list");

				list = new ArrayList<HashMap<String, String>>();
				for (int i = 0; i < reArray.length(); i++) {

					JSONObject jsonObject = (JSONObject) reArray.opt(i);
					HashMap<String, String> map = new HashMap<String, String>();
					map.put("AddTime", jsonObject.getString("AddTime"));
					map.put("UserName", jsonObject.getString("UserName"));
					map.put("Comment", jsonObject.getString("Comment"));
					map.put("Reply", jsonObject.getString("Reply"));
					map.put("ReplyTime", jsonObject.getString("ReplyTime"));
					list.add(map);

				}
				adapter = new ListViewAdapter(list, this);
				
				message.what = 3;
			} else {
				message.what = 2;
				string = reObject.getString("msg");
			}
		} catch (Exception e) {
			e.printStackTrace();
			message.what = 2;
			string = "网络异常！";
		}
		
		mHandler.sendMessage(message);

	}
*/
	/**
	 * 初始化适配器
	 */
//	private void initAdapter() {
//
//		if (totalResult < 1) {
//			return;
//		}
//
//		if (totalResult < showCount) {
//			count = totalResult;
//		} else {
//			count = showCount;
//		}
//
//		for (int i = 0; i < count; i++) {
//			HashMap<String, String> map = new HashMap<String, String>();
//			map.put("AddTime", list.get(i).get("AddTime"));
//			map.put("UserName", list.get(i).get("UserName"));
//			map.put("Comment", list.get(i).get("Comment"));
//			map.put("Reply", list.get(i).get("Reply"));
//			map.put("ReplyTime", list.get(i).get("ReplyTime"));
//			items.add(map);
//		}
//		adapter = new ListViewAdapter(items, this);
//	}

	/**
	 * 滑动时被调用
	 */

	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		this.visibleItemCount = visibleItemCount;
		visibleLastIndex = firstVisibleItem + visibleItemCount - 1;
		// 如果是自动加载,可以在这里放置异步加载数据的代码
		loadMoreButton.setVisibility(View.VISIBLE);
	}

	/**
	 * 滑动状态改变时被调用
	 */
/*
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		int itemsLastIndex = adapter.getCount() - 1; // 数据集最后一项的索引
		int lastIndex = itemsLastIndex + 1; // 加上底部的loadMoreView项
		if (scrollState == OnScrollListener.SCROLL_STATE_IDLE
				&& visibleLastIndex == lastIndex) {

			if (page > totalPage) {
				loadMoreButton.setVisibility(View.GONE);
				return;
			}

			GetMoreData();
		}
	}
	*/
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case 4322:
				if (data !=null) {

					strcontent = data.getStringExtra("content");
					//goback.setText(strCity);
					if (strcontent != null || strcontent.length()>0) {
						totalResult = totalResult+1;
						System.out.println("content___________"+strcontent);
						SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd  hh:mm:ss");
						HashMap<String, String> map = new HashMap<String, String>();
						map.put("AddTime", sdf.format(new java.util.Date()));
						map.put("UserName", MySharedData.sharedata_ReadString(StoreComment.this, "userName"));
						map.put("Comment", strcontent);
						map.put("Reply", "");
						map.put("ReplyTime", "");
						list.add(map);
						for (int i = (list.size()-1); i >0; i--) {
							list.set(i, list.get(i-1));
						}
						list.set(0, map);
						adapter = new ListViewAdapter(list, this);
						listView.setAdapter(adapter);
					}
				}
			
		}
	}
}
