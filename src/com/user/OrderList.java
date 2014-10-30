package com.user;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;

import com.hetianxia.activity.R;
import com.maiduo.bll.Config;
import com.maiduo.bll.HttpHelper;
import com.maiduo.bll.SiteHelper;
import com.maiduo.bll.ApplicationUser;
import com.shopping.Cart;
import com.shopping.Cart_After;

public class OrderList extends Activity implements OnScrollListener {
	private ListView listView;
	private int visibleLastIndex = 0; // 最后的可视项索引
	private int visibleItemCount; // 当前窗口可见项总数
	private int datasize = 0; // 模拟数据集的条数
	private int pagesize = 0; // 每页显示多少
	private int page = 0; // 当前第几页
	private int pageCount = 0; // 共多少页
	private DataAdapter adapter;
	private View loadMoreView;
	private Button loadMoreButton;
	private Handler handler = new Handler();

	private ProgressDialog progressDialog;
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				if (progressDialog != null)
					progressDialog.dismiss();
				// if(datasize > 0){
				listView.setAdapter(adapter);
				// }
				break;
			case 2:
				if (progressDialog != null)
					progressDialog.dismiss();

				break;
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_orderlist);

		// 模拟100101登录
		// ApplicationUser.getInstance().Login("100101", "123456");

		loadMoreView = getLayoutInflater().inflate(R.layout.loadmore, null);
		loadMoreButton = (Button) loadMoreView
				.findViewById(R.id.loadMoreButton);
		/*
		 * loadMoreButton.setOnClickListener(new View.OnClickListener() {
		 * 
		 * 
		 * public void onClick(View v) { loadMoreButton.setText("正在加载第 "+
		 * (page+1) +" 页，共 "+ pageCount +" 页"); //设置按钮文字 handler.postDelayed(new
		 * Runnable() { public void run() { loadMoreData();
		 * adapter.notifyDataSetChanged(); loadMoreButton.setText("查看更多...");
		 * //恢复按钮文字 } },500);
		 * 
		 * } });
		 */

		listView = (ListView) findViewById(R.id.lvList);
		listView.addFooterView(loadMoreView); // 设置列表底部视图

		// 首次加载提示加载中
		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage(getString(R.string.loadingstr));
		progressDialog.show();

		new Thread(new Runnable() {
			public void run() {
				// 加载数据

				initializeAdapter();
			}
		}).start();

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> adapterView, View view,
					int arg2, long arg3) {
				Intent intent = new Intent();
				String orderid = adapter.getItem(arg2).getInnerOrderId() + "";
				intent.putExtra("orderid", orderid);
				intent.setClass(OrderList.this, com.user.OrderDetail.class);
				startActivity(intent);
				// Toast.makeText(NewsList.this,
				// adapter.getItem(arg2).getId()+"",Toast.LENGTH_SHORT).show();
			}
		});

		listView.setOnScrollListener(this);

	}

	public void onScrollStateChanged(AbsListView view, int scrollState) {
		int itemsLastIndex = adapter.getCount() - 1; // 数据集最后一项的索引
		int lastIndex = itemsLastIndex + 1;
		if (scrollState == OnScrollListener.SCROLL_STATE_IDLE
				&& visibleLastIndex == lastIndex && page < pageCount) {
			// 如果是自动加载,可以在这里放置异步加载数据的代码
			// Toast.makeText(this, scrollState + " " + visibleLastIndex + " " +
			// lastIndex +"数据全部加载完!", Toast.LENGTH_LONG).show();
			// Toast.makeText(this, "正在加载第 "+ page +" 页，共 "+ pageCount +" 页",
			// Toast.LENGTH_LONG).show();
			loadMoreButton.setText("正在加载第 " + (page + 1) + " 页，共 " + pageCount
					+ " 页"); // 设置按钮文字
			handler.postDelayed(new Runnable() {
				public void run() {
					loadMoreData();
					adapter.notifyDataSetChanged();
					if (page >= pageCount) {
						loadMoreButton.setText("数据全部加载完!");
					} else {
						loadMoreButton.setText("查看更多..."); // 恢复按钮文字
					}
				}
			}, 500);
		}
	}

	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		this.visibleItemCount = visibleItemCount;
		visibleLastIndex = firstVisibleItem + visibleItemCount - 1;
		// System.out.println(totalItemCount + " " + datasize+ "");

		// 如果所有的记录选项等于数据集的条数，则移除列表底部视图

		if (totalItemCount >= (datasize + 1)) {
			loadMoreButton.setText("数据全部加载完!"); // 恢复按钮文字
			loadMoreButton.setOnClickListener(null);

		}

	}

	/**
	 * 初始化ListView的适配器
	 */
	private void initializeAdapter() {
		List<Data> datalist = new ArrayList<Data>();
		datalist = getData();
		adapter = new DataAdapter(datalist);
	}

	/**
	 * 加载更多数据
	 */
	private void loadMoreData() {
		List<Data> datalist = new ArrayList<Data>();
		datalist = getData(); // 从新异步获取数据
		for (int n = 0; n < datalist.size(); n++) {
			adapter.addDataItem(datalist.get(n));
		}
	}

	private List<Data> getData() {
		SharedPreferences sharedata = OrderList.this.getSharedPreferences("data", 0);
		String url = "http://api.36936.com/ClientApi/PointsShop/order/OrderList.ashx?userid="
				+ sharedata.getString("UserId", "");
		// url = SiteHelper.GetSendUrl(url); // 客户端加密网址
		List<Data> datalist = new ArrayList<Data>();
		// 这里需要分析服务器回传的json格式数据，
		try {
			String reStr = HttpHelper.getJsonData(OrderList.this, url);
			JSONObject reObject = new JSONObject(reStr);
			if (reObject.getInt("status") == 0) {
				// Toast.makeText(this,reObject.getString("error"),Toast.LENGTH_SHORT).show();

				TextView tv_ifemputy = (TextView) findViewById(R.id.ifEmputy);
				tv_ifemputy.setText("没有订单");
				tv_ifemputy.setVisibility(View.VISIBLE);

				return null;
			}
			JSONArray jsonArray = reObject.getJSONArray("list");
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject2 = (JSONObject) jsonArray.opt(i);
				Data data = new Data();
				data.setID(jsonObject2.getString("ID"));
				data.setInnerOrderId(jsonObject2.getString("InnerOrderId"));
				data.setGoodsAmount(jsonObject2.getString("GoodsAmount"));
				data.setAddDate(jsonObject2.getString("AddDate"));
				data.setStatus(jsonObject2.getString("Status"));
				data.setPayDate(jsonObject2.getString("PayDate"));
				datalist.add(data);
			}

			Message msg = new Message();
			msg.what = 1;
			mHandler.sendMessage(msg);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			Message msg = new Message();
			msg.what = 2;
			mHandler.sendMessage(msg);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			Message msg = new Message();
			msg.what = 2;
			mHandler.sendMessage(msg);
		}
		return datalist;
	}

	// 这里继承实体类
	class Data extends com.maiduo.model.Order {

	}

	// 处理数据的
	class DataAdapter extends BaseAdapter {

		List<Data> dataItems;

		public DataAdapter(List<Data> dataitems) {
			this.dataItems = dataitems;
		}

		public int getCount() {
			return dataItems.size();
		}

		public Data getItem(int position) {
			return dataItems.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View view, ViewGroup parent) {
			if (view == null) {
				view = getLayoutInflater().inflate(
						R.layout.user_orderlist_item, null);
			}

			//
			TextView tvOrderId = (TextView) view.findViewById(R.id.orderid);
			tvOrderId.setText("　订单号："
					+ dataItems.get(position).getInnerOrderId());

			TextView tvStatus = (TextView) view.findViewById(R.id.status);
			tvStatus.setText("　　状态：" + dataItems.get(position).getStatus());

			TextView tvMoney = (TextView) view.findViewById(R.id.money);
			tvMoney.setText("　　权益宝：" + dataItems.get(position).getGoodsAmount().replace(".00", ""));

			TextView tvDate = (TextView) view.findViewById(R.id.date);
			tvDate.setText("下单时间：" + dataItems.get(position).getAddDate());

			return view;
		}

		/**
		 * 添加数据列表项
		 * 
		 * @param dataitem
		 */
		public void addDataItem(Data dataitem) {
			dataItems.add(dataitem);
		}

	}

}
