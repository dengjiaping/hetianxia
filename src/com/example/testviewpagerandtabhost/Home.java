package com.example.testviewpagerandtabhost;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hetianxia.activity.R;
import com.maiduo.model.DefaultSlide;
import com.maiduo.model.News;
import com.maiduo.model.Product;
import com.maiduo.model.Product_home;
import com.maiduo.bll.*;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class Home extends Activity {
	/** Called when the activity is first created. */
	private AsyncImageLoader asyImg = new AsyncImageLoader();
	private Gallery mGallery;// 图片幻灯
	private ProductAdapter adapter;
	private List<Product_home> productlist = new ArrayList<Product_home>();
	// private String proTitle = "";
	private ArrayList<DefaultSlide> SlideArray = new ArrayList<DefaultSlide>();
	private Display display; // 这里获取屏幕尺寸
	private ProgressDialog progressDialog;

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				// if (progressDialog != null)
				// progressDialog.dismiss();

				mGallery = (Gallery) findViewById(R.id.gallery);
				try {
					mGallery.setAdapter(new ImageAdapter(Home.this));
					Random random = new Random();
					int i = Math.abs(random.nextInt() + 10);
					mGallery.setSelection(i++, true);
					new Thread(new Runnable() {
						public void run() {
							while (true) {
								mHandler.sendEmptyMessage(2);
								try {
									Thread.sleep(3000);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
						}
					}).start();

				} catch (Exception e) {
				} 
				mGallery.setSpacing(15);
				mGallery.setOnItemClickListener(new OnItemClickListener() {
					public void onItemClick(AdapterView parent, View v,
							int position, long id) {

//						final String urlstr = SlideArray.get(
//								position % SlideArray.size()).getUrl();
						Intent intent = new Intent();
						intent.putExtra("url", "http://mp.weixin.qq.com/s?__biz=MjM5MzY4NjU2MA==&mid=200942772&idx=1&sn=b6c5159a5d0bdb97ae1b9c0c75c2974b#rd");
						intent.setClass(Home.this, ShowWebView.class);
						startActivity(intent);

					}
				});
				mGallery.setOnItemSelectedListener(new OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {

					}

					public void onNothingSelected(AdapterView<?> arg0) {

					}
				});
				mGallery.setOnFocusChangeListener(new OnFocusChangeListener() {
					public void onFocusChange(View v, boolean arg1) {

					}
				});

				// 填充GridView
				ListView gridView = (ListView) findViewById(R.id.homeGrid);
				adapter = new ProductAdapter(productlist);
				gridView.setAdapter(adapter);
				gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					public void onItemClick(AdapterView<?> adapterView,
							View view, int arg2, long arg3) {
						Intent intent = new Intent();
						intent.putExtra("id", adapter.getItem(arg2).getPid()
								+ "");
						intent.setClass(Home.this, com.maiduo.Product.class);
						startActivity(intent);
					}
				});
				
				mGallery.setFocusable(true);

				break;
			case 2:
				mGallery.onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null);
				break;
			}
		}
	};

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.maiduo_home);

		SharedPreferences.Editor sharedataEditor = getSharedPreferences("data", 0).edit();
		sharedataEditor.putString("UserId", "100101");
		sharedataEditor.commit();
		display = this.getWindowManager().getDefaultDisplay();// 这里获取屏幕尺寸

		if (NetWorkStatus() == false) {
			finish();
		} else {

			new Thread(new Runnable() {
				public void run() {

					getData("http://api.36936.com/ClientApi/PointsShop/Productindex.ashx");// 获取数据
				}
			}).start();
		}

	}

	private void getData(String url) {

		List<News> newslist = new ArrayList<News>();
		// 这里需要分析服务器回传的json格式数据，
		try {
			JSONObject jsonObject = new JSONObject(HttpHelper.getJsonData(
					Home.this, url));
			JSONArray jsonArray = jsonObject.getJSONArray("slidelist");
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject2 = (JSONObject) jsonArray.opt(i);
				DefaultSlide ds = new DefaultSlide();
				ds.setTitle(jsonObject2.getString("title"));
				// ds.setPic(jsonObject2.getString("pic"));
				ds.setPic("http://image.369518.com/SitePublic/2013/3/9/8e84d26838.jpg");
				ds.setUrl(jsonObject2.getString("url"));
				SlideArray.add(ds);
			}
			// proTitle = jsonObject.getString("protitle");
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

	/*
	 * class ImageAdapter is used to control gallery source and operation.
	 */
	private class ImageAdapter extends BaseAdapter {
		private Context mContext;

		public ImageAdapter(Context c) throws IllegalArgumentException,
				IllegalAccessException {
			mContext = c;
		}

		public int getCount() {

			return Integer.MAX_VALUE;
			// return imgList.size();
		}

		public Object getItem(int position) {

			return position;
		}

		public long getItemId(int position) {

			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {

			ImageView i = new ImageView(mContext);
			// 从imgList取得图片ID
			i.setScaleType(ImageView.ScaleType.FIT_XY);
			int width = (int) (display.getWidth() * 0.95);
			int height = (int) (width * 170 / 410);
			i.setLayoutParams(new Gallery.LayoutParams(width, height));
			i.setImageResource(R.drawable.loading);
			String imgsrc = SlideArray.get(position % SlideArray.size())
					.getPic();
			asyImg.LoadImage(imgsrc, i);
			return i;
		}

	};

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
	 * 判断是否联网
	 * 
	 * @return
	 */
	private boolean NetWorkStatus() {

		boolean netSataus = false;
		ConnectivityManager cwjManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

		cwjManager.getActiveNetworkInfo();

		if (cwjManager.getActiveNetworkInfo() != null) {
			netSataus = cwjManager.getActiveNetworkInfo().isAvailable();
		}
		return netSataus;
	}

}