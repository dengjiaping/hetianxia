package com.maiduo;

import java.net.URL;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hetianxia.activity.R;
import com.maiduo.bll.AsyncImageLoader;
import com.maiduo.bll.HttpHelper;
import com.maiduo.bll.ApplicationUser;
import com.maiduo.bll.SiteHelper;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.Spanned;
import android.text.Html.ImageGetter;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.TextView;
import com.maiduo.bll.Config;
import com.shopping.Cart;

public class Product extends Activity {
	/** Called when the activity is first created. */
	private String proPicList = "";
	private String title = "", str_des = "";
	private String price = "";
	private String guige = "";
	private String styleIds = ""; // 规格id
	private String styleIdSelectValue = "";
	private String content = "";
	private String id = "";
	private String url = "";
	private String error_msg = "";
	private boolean ifEmputy = false; // 默认不是空
	private AsyncImageLoader asyImg = new AsyncImageLoader();

	private Gallery mGallery;// 图片幻灯

	private String[] picData; // 图片地址数组

	private Display display; // 这里获取屏幕尺寸
	public Spinner spinner;
	public Spinner spinner1;
	private ArrayAdapter<String> adapter;
	private ArrayAdapter<String> adapter1;
	private String txm;
	private ImageAdapter imgAdapter;
	private Spanned spanned;

	private ProgressDialog progressDialog;
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				if (progressDialog != null)
					progressDialog.dismiss();
				TextView productTitle = (TextView) findViewById(R.id.productTitle);
				productTitle.setText(title);
				TextView attr_des = (TextView) findViewById(R.id.attr_des);
				attr_des.setText(str_des);

				TextView tvPrice = (TextView) findViewById(R.id.Price);
				tvPrice.setText(price + "个");
				mGallery = (Gallery) findViewById(R.id.gallery);
				mGallery.setAdapter(imgAdapter);
				mGallery.setSelection(100);
				TextView tvContent = (TextView) findViewById(R.id.content);
				tvContent.setText(spanned);

				if (guigeData_id.size() > 0) {

					// 第一个spinner
					spinner = (Spinner) findViewById(R.id.spGuigeValue);
					spinner.setPrompt("请选择商品规格");
					// 添加适配器
					spinner.setAdapter(adapter);

					spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
						public void onItemSelected(AdapterView<?> parent,
								View view, int position, long id) {
							styleIdSelectValue = guigeData_id.get(position);
						}

						public void onNothingSelected(AdapterView<?> arg0) {

						}

					});
				}
				break;
			case 2:
				if (progressDialog != null)
					progressDialog.dismiss();
				break;
			case 3:
				if (progressDialog != null)
					progressDialog.dismiss();
				AlertDialog.Builder b = new AlertDialog.Builder(Product.this)
						.setTitle("添加成功").setMessage("商品成功添加到购物车!");
				b.setPositiveButton("去结算",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								Intent intent = new Intent();
								intent.putExtra("CartType",
										Config.ShoppingMaiduoCartType);
								intent.setClass(Product.this,
										com.shopping.Cart.class);
								startActivity(intent);
							}
						})
						.setNeutralButton("继续购物",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int whichButton) {
										dialog.dismiss();
									}
								}).show();
				break;
			case 4:
				if (progressDialog != null)
					progressDialog.dismiss();

				Toast.makeText(Product.this, error_msg, 2000).show();
				break;
			case 5:
				if (progressDialog != null)
					progressDialog.dismiss();

				Toast.makeText(Product.this, "添加失败", 2000).show();
				break;

			case 6:
				if (progressDialog != null)
					progressDialog.dismiss();

				Intent intent = new Intent();
				intent.putExtra("CartType", Config.ShoppingMaiduoCartType);
				intent.setClass(Product.this, com.shopping.Cart.class);
				startActivity(intent);
				break;

			}
		}
	};

	private String UserId;
	private String GuigeSelectedValue = ""; // 选中的规格
	private ArrayList<String> guigeData_id = new ArrayList<String>();
	private ArrayList<String> guigeData = new ArrayList<String>();

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.product);

		display = this.getWindowManager().getDefaultDisplay();// 这里获取屏幕尺寸
		// String productInfo = "";
		Intent intent = this.getIntent();
		id = intent.getStringExtra("id");

		url = "http://api.36936.com/ClientApi/PointsShop/ProductInfo.ashx?productId="
				+ id;

		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage(getString(R.string.loadingstr));
		progressDialog.show();
		new Thread(new Runnable() {
			public void run() {
				// 加载数据
				getData(url);
			}
		}).start();

		String proNumStr = "";
		for (int i = 1; i < 200; i++) {
			proNumStr += i + ",";

		}
		proNumStr += "200";
		String[] proNumData = proNumStr.split(",");
		// 第一个spinner
		spinner1 = (Spinner) findViewById(R.id.spProNum);
		// 创建适配器
		adapter1 = new ArrayAdapter<String>(Product.this,
				android.R.layout.simple_spinner_item, proNumData);
		// 设置适配器显示样式
		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner1.setPrompt("请选择商品数量");
		// 添加适配器
		spinner1.setAdapter(adapter1);
		// 加入购物车
		Button btnBuy = (Button) findViewById(R.id.proBuy);
		btnBuy.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {

				SharedPreferences sharedata = Product.this.getSharedPreferences("data", 0);
		        UserId= sharedata.getString("UserId", "");

				//
				final int proNum = Integer.parseInt(spinner1.getSelectedItem()
						.toString());

				if (proNum <= 0) { //
					Toast.makeText(Product.this, "数量不合适", 2000);
					return;
				}
				if (styleIdSelectValue.equals("")) { //
					Toast.makeText(Product.this, "请选择规格", 2000).show();
					return;
				}
				progressDialog.show();
				new Thread(new Runnable() {
					public void run() {
						// 加载数据
						String url = "http://api.36936.com/ClientApi/PointsShop/cart/Add.ashx"
								+ "?userId="
								+ UserId
								+ "&Num="
								+ proNum
								+ "&styleId="
								+ styleIdSelectValue
								+ "&productId="
								+ id
								+ "&hash="
								+ SiteHelper.MD5(UserId + Config.UrlHashKey2);
						try {
							String content = HttpHelper.getJsonData(
									Product.this, url);
							JSONObject jsonObject = new JSONObject(content);

							if (jsonObject.getInt("status") == 0) { // 如果没有成功加入购物车
								error_msg = jsonObject.getString("msg");
								Message msg = new Message();
								msg.what = 4;
								mHandler.sendMessage(msg);
							} else {
								// 成功
								Message msg = new Message();
								msg.what = 3;
								mHandler.sendMessage(msg);
							}

						} catch (JSONException e) {

							Message msg = new Message();
							msg.what = 5;
							mHandler.sendMessage(msg);
							// Toast.makeText(this,getString(R.string.loading_error),Toast.LENGTH_SHORT).show();
						} catch (Exception e) {

							Message msg = new Message();
							msg.what = 5;
							mHandler.sendMessage(msg);
						}
					}
				}).start();

			}
		});

		// 立即购买
		Button btnNowBuy = (Button) findViewById(R.id.proNowBuy);
		btnNowBuy.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {

				SharedPreferences sharedata = Product.this.getSharedPreferences("data", 0);
		        UserId= sharedata.getString("UserId", "");

				//
				final int proNum = Integer.parseInt(spinner1.getSelectedItem()
						.toString());

				if (proNum <= 0) { //
					Toast.makeText(Product.this, "数量不合适", 2000);
					return;
				}
				if (styleIdSelectValue.equals("")) { //
					Toast.makeText(Product.this, "请选择规格", 2000).show();
					return;
				}
				progressDialog.show();
				new Thread(new Runnable() {
					public void run() {
						// 加载数据
						String url = "http://api.36936.com/ClientApi/PointsShop/cart/Add.ashx"
								+ "?userId="
								+ UserId
								+ "&Num="
								+ proNum
								+ "&styleId="
								+ styleIdSelectValue
								+ "&productId="
								+ id
								+ "&hash="
								+ SiteHelper.MD5(UserId + Config.UrlHashKey2);
						try {
							String content = HttpHelper.getJsonData(
									Product.this, url);
							JSONObject jsonObject = new JSONObject(content);

							if (jsonObject.getInt("status") == 0) { // 如果没有成功加入购物车
								error_msg = jsonObject.getString("msg");
								Message msg = new Message();
								msg.what = 4;
								mHandler.sendMessage(msg);
							} else {
								// 成功
								Message msg = new Message();
								msg.what = 6;
								mHandler.sendMessage(msg);
							}

						} catch (JSONException e) {

							Message msg = new Message();
							msg.what = 5;
							mHandler.sendMessage(msg);
							// Toast.makeText(this,getString(R.string.loading_error),Toast.LENGTH_SHORT).show();
						} catch (Exception e) {

							Message msg = new Message();
							msg.what = 5;
							mHandler.sendMessage(msg);
						}
					}
				}).start();

			}
		});
	}

	private void getData(String url) {

		// 这里需要分析服务器回传的json格式数据，
		try {
			String reStr = HttpHelper.getJsonData(Product.this, url);
			JSONObject reObject = new JSONObject(reStr);

			proPicList = "http://hnbsshop.com/upLoadImages/"
					+ reObject.getString("smallImg");
			title = reObject.getString("ProName");
			price = reObject.getString("VipPrice");
			str_des = reObject.getString("Description");
			picData = proPicList.split(",");
			guige = reObject.getString("Unit");
			content = reObject.getString("ProContent");
			spanned = Html.fromHtml(content, imageGetter, null);
			imgAdapter = new ImageAdapter(Product.this);

			String reStr_gg = HttpHelper.getJsonData(Product.this,
					"http://api.36936.com/ClientApi/PointsShop/ProductStyle.ashx?productId="
							+ id);
			JSONObject reObject_gg = new JSONObject(reStr_gg);
			if (reObject_gg.getString("status").equals("1")) {
				JSONArray reArray_gg = reObject_gg.getJSONArray("list");

				for (int i = 0; i < reArray_gg.length(); i++) {
					JSONObject jsonObject_gg = (JSONObject) reArray_gg.opt(i);
					guigeData_id.add(jsonObject_gg.getString("StyleId"));
					guigeData.add(jsonObject_gg.getString("StyleName"));

				}
				if (guigeData_id.size() > 0) {
					styleIdSelectValue = guigeData_id.get(0);
				}
			}

			// 创建适配器
			adapter = new ArrayAdapter<String>(Product.this,
					android.R.layout.simple_spinner_item, guigeData);
			// 设置适配器显示样式
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

			Message msg = new Message();
			msg.what = 1;
			mHandler.sendMessage(msg);
		} catch (Exception e) {

			// e.printStackTrace();
			// Toast.makeText(this, getString(R.string.loading_error),
			// Toast.LENGTH_SHORT).show();
			Message msg = new Message();
			msg.what = 2;
			mHandler.sendMessage(msg);
		}

	}

	/*
	 * class ImageAdapter is used to control gallery source and operation.
	 */
	private class ImageAdapter extends BaseAdapter {
		private Context mContext;
		private ArrayList<String> imgList = new ArrayList<String>();

		public ImageAdapter(Context c) throws IllegalArgumentException,
				IllegalAccessException {
			mContext = c;
			for (int i = 0; i < picData.length; i++) {
				imgList.add(picData[i]);
			}
		}

		public int getCount() {

			return Integer.MAX_VALUE;
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
			int width = (int) (display.getWidth() * 0.5);
			int height = width;
			i.setLayoutParams(new Gallery.LayoutParams(width, height));
			// i.setLayoutParams(new Gallery.LayoutParams(250, 250));
			String imgsrc = Config.ProdImgPrefix
					+ imgList.get(position % imgList.size()).toString();
			asyImg.LoadImage(imgsrc, i);
			// asyImg.LoadImage(imgList.get(position).toString(),i);

			return i;
		}

	};

	ImageGetter imageGetter = new ImageGetter() {
		public Drawable getDrawable(String source) {
			Drawable drawable = null;
			URL url = null;
			try {
				url = new URL(source);
				drawable = Drawable.createFromStream(url.openStream(),
						"temp.png");
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (drawable != null) {
				int w = drawable.getIntrinsicWidth();
				int h = drawable.getIntrinsicHeight();
				// 手机屏宽
				DisplayMetrics dm = new DisplayMetrics();
				getWindowManager().getDefaultDisplay().getMetrics(dm);
				int x = dm.widthPixels * 19 / 20;
				int y = (h * x) / w;
				drawable.setBounds(0, 0, x, y);
			}
			return drawable;
		}
	};
}
