package com.maiduo;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.hetianxia.activity.R;
import com.maiduo.bll.AsyncImageLoader;
import com.maiduo.bll.HttpHelper;
import com.maiduo.model.Product;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;

public class ProductList extends Activity implements OnScrollListener {
	/** Called when the activity is first created. */

	private ListView listView;
	private int visibleLastIndex = 0; // 最后的可视项索引
	private int visibleItemCount; // 当前窗口可见项总数
	private int datasize = 0; // 模拟数据集的条数
	private int pagesize = 0; // 每页显示多少
	private int page = 0; // 当前第几页
	private int pageCount = 0; // 共多少页
	private ProductAdapter adapter;
	private View loadMoreView;
	private Button loadMoreButton;
	private String id;
	private Handler handler = new Handler();
	private AsyncImageLoader asyImg = new AsyncImageLoader();
	private String orderStr = "";
	private String ListUrl = "";
	private List<Product> productlist = new ArrayList<Product>();

	private ProgressDialog progressDialog;
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				if (progressDialog != null)
					progressDialog.dismiss();
				adapter = new ProductAdapter(productlist);
				listView.setAdapter(adapter);
				if (page >= pageCount) {
					loadMoreButton.setText("数据全部加载完!");
				} else {
					loadMoreButton.setText("查看更多..."); // 恢复按钮文字
				}
				break;
			case 2:
				if (progressDialog != null)
					progressDialog.dismiss();
				break;
			}
		}
	};

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.productlist);
		// ListView lvTop = (ListView)findViewById(R.id.lvTopSelect);
		initTopSelect();

		Intent intent = this.getIntent();
		id = intent.getStringExtra("id");
		// id = "1465465545";
		loadMoreView = getLayoutInflater().inflate(R.layout.loadmore, null);
		loadMoreButton = (Button) loadMoreView
				.findViewById(R.id.loadMoreButton);

		listView = (ListView) findViewById(R.id.lvProduct);
		listView.addFooterView(loadMoreView); // 设置列表底部视图

		// 首次加载提示加载中
		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage(getString(R.string.loadingstr));
		progressDialog.show();
		// mHandler.postDelayed(new Runnable() {
		// public void run() {
		// initializeAdapter();
		// listView.setAdapter(adapter);
		//
		// if(page >= pageCount){
		// loadMoreButton.setText("数据全部加载完!");
		// }else{
		// loadMoreButton.setText("查看更多..."); //恢复按钮文字
		// }
		//
		// Message msg = new Message();
		// msg.what = 1;
		// mHandler.sendMessage(msg);
		// }
		// },1000);

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
				intent.putExtra("id", adapter.getItem(arg2).getProductId() + "");
				intent.setClass(ProductList.this, com.maiduo.Product.class);
				startActivity(intent);
				// Toast.makeText(ProductList.this,
				// adapter.getItem(arg2).getId()+"",Toast.LENGTH_SHORT).show();
			}
		});

		listView.setOnScrollListener(this);

	}

	private void initTopSelect() {
	}

	public void onScrollStateChanged(AbsListView view, int scrollState) {
		int itemsLastIndex = adapter.getCount() - 1; // 数据集最后一项的索引
		int lastIndex = itemsLastIndex + 1;
		if (scrollState == OnScrollListener.SCROLL_STATE_IDLE
				&& visibleLastIndex == lastIndex && page < pageCount) {
			// 如果是自动加载,可以在这里放置异步加载数据的代码
			// Toast.makeText(this, scrollState + " " + visibleLastIndex + " " +
			// lastIndex +"数据全部加载完!", Toast.LENGTH_LONG).show();

			// 如果是自动加载,可以在这里放置异步加载数据的代码
			// Toast.makeText(this, "正在加载第 "+ page +" 页，共 "+ pageCount +" 页",
			// Toast.LENGTH_LONG).show();

			// loadMoreButton.setText("正在加载第 "+ (page+1) +" 页，共 "+ pageCount
			// +" 页"); //设置按钮文字
			handler.postDelayed(new Runnable() {
				public void run() {
					if (page >= pageCount) {
					} else {
						loadMoreData();
						adapter.notifyDataSetChanged();
					}
					if (page >= pageCount) {
						loadMoreButton.setText("数据全部加载完!");
					} else {
						loadMoreButton.setText("查看更多..."); // 恢复按钮文字
					}

					// loadMoreButton.setText("查看更多..."); //恢复按钮文字
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
		if (datasize == 0) {
			loadMoreButton.setText(getString(R.string.app_name));
		} else if (totalItemCount >= (datasize + 1)) {
			loadMoreButton.setText("数据全部加载完!"); // 恢复按钮文字
			loadMoreButton.setOnClickListener(null);

		}

	}

	/**
	 * 初始化ListView的适配器
	 */
	private void initializeAdapter() {
		List<Product> productlist = new ArrayList<Product>();
		ListUrl = "http://api.36936.com/ClientApi/PointsShop/ProductList.ashx?pid="
				+ id;
		productlist = getData(ListUrl);
	}

	/**
	 * 加载更多数据
	 */
	private void loadMoreData() {
		List<Product> productlist = new ArrayList<Product>();
		page = page + 1;
		ListUrl = "http://api.36936.com/ClientApi/PointsShop/ProductList.ashx?pid="
				+ id + "&page=" + page;
		productlist = getData(ListUrl); // 从新异步获取数据
		for (int n = 0; n < productlist.size(); n++) {
			adapter.addProductItem(productlist.get(n));
		}
	}

	private List<Product> getData(String url) {
		// String url = Config.UrlProductList+"?page="+(page+1);
		productlist = new ArrayList<Product>();
		// 这里需要分析服务器回传的json格式数据，
		try {
			String reStr = HttpHelper.getJsonData(ProductList.this, url);
			JSONObject reObject = new JSONObject(reStr);
			if (reObject.getInt("status") == 0) {
				Toast.makeText(ProductList.this, reObject.getString("msg"),
						Toast.LENGTH_SHORT).show();
				return null;
			}
			JSONObject jsonObject = reObject.getJSONObject("result");
			if (!jsonObject.isNull("nofound")) {
				Toast.makeText(this, getString(R.string.app_name),
						Toast.LENGTH_SHORT).show();
				datasize = 0;
			} else {
				datasize = Integer.parseInt(jsonObject.getString("count"));
				page = Integer.parseInt(jsonObject.getString("page"));
				pageCount = Integer.parseInt(jsonObject.getString("pageCount"));
				JSONArray jsonArray = jsonObject.getJSONArray("list");
				pagesize = jsonArray.length();
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject2 = (JSONObject) jsonArray.opt(i);
					Product product = new Product();
					product.setProId(jsonObject2.getString("proId"));
					product.setProName(jsonObject2.getString("proName"));
					product.setCategoryId(jsonObject2.getString("categoryId"));
					product.setProImg(jsonObject2.getString("proImg"));
					product.setVipPrice(jsonObject2.getString("vipPrice"));
					product.setAddDate(jsonObject2.getString("addDate"));
					product.setProductId(jsonObject2.getString("ProductId"));
					product.setDescription(jsonObject2.getString("Description"));
					productlist.add(product);
				}
			}

			Message msg = new Message();
			msg.what = 1;
			mHandler.sendMessage(msg);
		} catch (JSONException e) {
			Message msg = new Message();
			msg.what = 2;
			mHandler.sendMessage(msg);
		} catch (Exception e) {
			Message msg = new Message();
			msg.what = 2;
			mHandler.sendMessage(msg);
		}
		return productlist;
	}

	class ProductAdapter extends BaseAdapter {

		List<Product> productItems;

		public ProductAdapter(List<Product> productitems) {
			this.productItems = productitems;
		}

		public int getCount() {
			return productItems.size();
		}

		public Product getItem(int position) {
			return productItems.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View view, ViewGroup parent) {

			view = getLayoutInflater().inflate(R.layout.product_item, null);

			// 图片
			String url = productItems.get(position).getProImg();
			asyImg.LoadImage(url, (ImageView) view.findViewById(R.id.pic));
			// 标题
			TextView tvTitle = (TextView) view.findViewById(R.id.title);
			tvTitle.setText(productItems.get(position).getProName());
			// 产品
			TextView tvPrice = (TextView) view.findViewById(R.id.price);
			tvPrice.setText("权益宝：" + productItems.get(position).getVipPrice()
					+ "个");
			TextView tv_description = (TextView)view.findViewById(R.id.description);
			tv_description.setText(productItems.get(position).getDescription());

			return view;
		}

		/**
		 * 添加数据列表项
		 * 
		 * @param productitem
		 */
		public void addProductItem(Product productitem) {
			productItems.add(productitem);
		}

	}

}