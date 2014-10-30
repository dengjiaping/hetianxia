package com.htx.weixin;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.json.JSONObject;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.hetianxia.activity.R;
import com.htx.conn.Const;
import com.htx.conn.HttpHelper;
import com.htx.pub.ApplicationUser;
import com.htx.pub.AsyncImageLoader;
import com.htx.pub.LoadingDialog;
import com.htx.pub.MySharedData;
import com.htx.pub.MyToast;
import com.htx.pub.PubActivity;
import com.htx.search.SiteHelper;

public class ShopHomeInfo extends PubActivity {

	private LinearLayout storeComment;
	private Button show1;
	private Button btn, isBtn;
	private LoadingDialog dialoga;
	private Intent in;
	private ImageView logo;
	private TextView brief, title, youhui, bus, phone;
	private String string = "";
	private ImageButton back, menu;
	private String isFocus = "", brie , BusWay , imagurl, Discount, Contact, address,
			titleStr, StoreId;

	private Handler mHandl = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				dialoga.dismiss();
				break;
			case 2:
				dialoga.dismiss();
				MyToast.toast(ShopHomeInfo.this, string, 1000);
				break;
			case 3:
				dialoga.dismiss();
				isBtn.setVisibility(View.VISIBLE);
				show1.setVisibility(View.VISIBLE);
				btn.setVisibility(View.GONE);
				
				Intent intent = new Intent(ShopHomeInfo.this,
						ChatMainActivity.class);
				intent.putExtra("url", imagurl);
				intent.putExtra("titleStr", titleStr);
				intent.putExtra("welcome", string);
				intent.putExtra("StoreId", StoreId);

				intent.putExtra("brief", brie);
				SimpleDateFormat formatter = new SimpleDateFormat(
						"yyyy-MM-dd   HH:mm");
				Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
				String str = formatter.format(curDate);
				intent.putExtra("time", str);
				startActivity(intent);
				finish();
				break;
			case 4:
				dialoga.dismiss();
				isBtn.setVisibility(View.GONE);
				show1.setVisibility(View.GONE);
				btn.setVisibility(View.VISIBLE);

				Intent intent2 = new Intent(ShopHomeInfo.this,
						SearchGuan.class);
				if (ChatMainActivity.instance2 !=null) {
					ChatMainActivity.instance2.finish();
				}
				
				startActivity(intent2);
				finish();
				break;
			case 5:
				title.setText(titleStr);
				brief.setText(brie);
				youhui.setText(Discount);
				bus.setText(BusWay);
				phone.setText(Contact);
				AsyncImageLoader imageLoader = new AsyncImageLoader();
				imageLoader.LoadBackImage(imagurl, logo);
				if (isFocus.equals("1")) {
					isBtn.setVisibility(View.VISIBLE);
					show1.setVisibility(View.VISIBLE);
					btn.setVisibility(View.GONE);
				} else {
					isBtn.setVisibility(View.GONE);
					show1.setVisibility(View.GONE);
					btn.setVisibility(View.VISIBLE);
				}
				dialoga.dismiss();
				break;
			}
			super.handleMessage(msg);
		}

	};

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shop_home_info);

		in = getIntent();
		StoreId = in.getStringExtra("StoreId");
		storeComment = (LinearLayout) findViewById(R.id.storeComment);
		show1 = (Button) findViewById(R.id.show1);
		back = (ImageButton) findViewById(R.id.back);
		menu = (ImageButton) findViewById(R.id.menu);

		dialoga = new LoadingDialog(ShopHomeInfo.this);

		init();

		back.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
		menu.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_SEND);
				intent.setType("text/plain");
				intent.putExtra(Intent.EXTRA_SUBJECT, "分享");

				String spreadUrl = MySharedData.sharedata_ReadString(
						ShopHomeInfo.this, "spreadUrl");
				if (spreadUrl.equals("")) {
					spreadUrl = "http://www.36936.com/UserRegisterManage/MiNiUserReg.aspx?uid="
							+ ApplicationUser.GetUserId();
				}
				String str = "“合天下”手机客户端，一搜便知全网最低价，购物省钱，接电话、摇笑话都能赚钱；“消费导航”精确定位，帮你迅速到达目的地！已有上百万人在使用，好评率98%以上，全国都在疯转，还有积分大派送哦！快快安装吧!"
						+ spreadUrl;

				intent.putExtra(Intent.EXTRA_TEXT, str);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivityForResult(
						Intent.createChooser(intent, getTitle()), 119);
			}
		});
		show1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(ShopHomeInfo.this,
						ChatMainActivity.class);
				intent.putExtra("url", imagurl);
				intent.putExtra("titleStr", titleStr);
				intent.putExtra("welcome", string);
				intent.putExtra("StoreId", StoreId);
				intent.putExtra("brief", intent.getStringExtra("brief"));
				SimpleDateFormat formatter = new SimpleDateFormat(
						"yyyy-MM-dd   HH:mm");
				Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
				String str = formatter.format(curDate);
				intent.putExtra("time", str);
				startActivity(intent);
			}
		});

		storeComment.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(ShopHomeInfo.this,
						StoreComment.class);
				intent.putExtra("titleStr", titleStr);
				intent.putExtra("StoreId",
						StoreId);
				startActivity(intent);
			}
		});
		btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				dialoga.show();
				new Thread(new Runnable() {
					public void run() {
						getData("0");
					}
				}).start();
			}
		});
		isBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				dialoga.show();
				new Thread(new Runnable() {
					public void run() {
						getData("1");
					}
				}).start();
			}
		});

	}

	/**
	 * 初始化数据
	 */
	private void init() {

		// 图片
		logo = (ImageView) findViewById(R.id.logo);
		// 商家名称
		title = (TextView) findViewById(R.id.title);
		// 广告语
		brief = (TextView) findViewById(R.id.brief);
		youhui = (TextView) findViewById(R.id.youhui);
		phone = (TextView) findViewById(R.id.phone);
		bus = (TextView) findViewById(R.id.bus);
		btn = (Button) findViewById(R.id.btn);
		isBtn = (Button) findViewById(R.id.isBtn);
		dialoga.show();
		new Thread(new Runnable() {
			public void run() {
				getData(ShopHomeInfo.this, StoreId);
			}
		}).start();

	}

	/**
	 * 关注和取消关注
	 */
	private void getData(String DoType) {

		String url = "http://api.36936.com/ClientApi/Pos/FocusStore.ashx?";
		String adUserId = MySharedData.sharedata_ReadString(this, "UserId");
		String hash = SiteHelper.MD5(adUserId + Const.UrlHashKey);
		url += "&userId=" + adUserId + "&hash=" + hash + "&StoreId=" + StoreId
				+ "&DoType=" + DoType;

		Message message = new Message();
		try {
			String reStr = HttpHelper.getJsonData(ShopHomeInfo.this, url);
			JSONObject reObject = new JSONObject(reStr);
			if (reObject.getInt("status") == 1) {
				if (DoType.equals("0")) {
					message.what = 3;
					string = reObject.getString("msg");
				} else {
					message.what = 4;
				}
			} else {
				message.what = 2;
				string = reObject.getString("msg");
			}
		} catch (Exception e) {
			e.printStackTrace();
			message.what = 2;
			string = "网络异常！";
		}
		mHandl.sendMessage(message);
	}

	/**
	 * 获得数据
	 * 
	 * @param context
	 */
	public void getData(Context context, String StoreId) {

		String adUserId = MySharedData.sharedata_ReadString(context, "UserId");
		String hash = SiteHelper.MD5(adUserId + Const.UrlHashKey);

		String url = "http://api.36936.com/ClientApi/Pos/getStoreDetail.ashx?"
				+ "userid=" + adUserId + "&hash=" + hash + "&StoreId="
				+ StoreId;
		String reStr = HttpHelper.getJsonData(context, url);
		Message message = new Message();
		try {
			JSONObject reObject = new JSONObject(reStr);
			if (reObject.getInt("status") == 1) {
				JSONObject ob = reObject.getJSONObject("result");

				if (ob.has("title")) {
					// 商家名称
					titleStr = ob.getString("title");
				}
				if (ob.has("address")) {
					address = ob.getString("address");
				}
				if (ob.has("Contact")) {
					Contact = ob.getString("Contact");
				}
				if (ob.has("Discount")) {
					Discount = ob.getString("Discount");
				}
				if (ob.has("Logo")) {
					// 图片
					imagurl = ob.getString("Logo");
				}
				if (ob.has("BusWay")) {
					BusWay = ob.getString("BusWay");
				}
				if (ob.has("brief")) {
					brie = ob.getString("brief");
				}
				if (ob.has("isFocus")) {
					isFocus = ob.getString("isFocus");
				}
				message.what = 5;
				Log.e("AAA", "))----->>  0");
			} else {
				message.what = 1;
			}
		} catch (Exception e) {
			message.what = 1;
		}
		mHandl.sendMessage(message);
	}
}
