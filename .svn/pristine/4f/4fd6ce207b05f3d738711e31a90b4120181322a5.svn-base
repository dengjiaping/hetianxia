package com.htx.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;
import com.htx.action.ProductAction;
import com.htx.bean.AuctionListBean;
import com.htx.conn.Const;
import com.htx.conn.HttpHelper;
import com.htx.conn.NetUtil;
import com.htx.core.AsyncWorkHandler;
import com.htx.pub.ApplicationUser;
import com.htx.pub.LoadingDialog;
import com.htx.pub.PubActivity;
import com.htx.pub.ShowWebView;
import com.htx.user.U_Login;
import com.hetianxia.activity.R;
import com.htx.bean.ProductDetailBean;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 
 * @author lvan
 * 
 * @todo
 * 
 */
public class ShopActivity extends PubActivity {

	private Context _context;
	private TextView shops_count;
	private ListView shops_listview;
	private DetailShopsAdapter shopsAdapter;
	private LayoutInflater inflater;
	private View footer;
	private Intent _intent;
	private String pid;
	private String keyworld;
	private int curpage = 1;// 当前页
	private int pages = 1;// 共多少页
	private ArrayList<AuctionListBean> fileList;
	private String sellUrl;
	private Uri uri;
	private String fanliUrl;
	private String userId;

	/** Called when the activity is first created. */

	public void onCreate(Bundle savedInstanceState) {

		setContentView(R.layout.shops);
		super.onCreate(savedInstanceState);

		_context = this;
		_intent = this.getIntent();
		inflater = LayoutInflater.from(_context);
		footer = inflater.inflate(R.layout.shops_footer, null);
		shops_listview = (ListView) findViewById(R.id.shops_listview);
		shops_listview.addFooterView(footer);
		shops_count = (TextView) findViewById(R.id.shops_count);
		pid = _intent.getStringExtra("product_id");
		keyworld = _intent.getStringExtra("keyworld");

		AsyncWorkHandler asyncQueryHandler = new AsyncWorkHandler() {

			public Object excute(Map<String, String> map) {
				return ProductAction.getProductShops(map);
			}

			public void handleMessage(Message msg) {
				if (msg.obj != null) {
					ProductDetailBean bean = (ProductDetailBean) msg.obj;
					if ("true".equals(bean.getSuccess())) {
						pages = ((Integer.parseInt(bean.getTotalCount())) % 12 == 0) ? Integer
								.parseInt(bean.getTotalCount()) / 12 : Integer
								.parseInt(bean.getTotalCount()) / 12 + 1;
						shops_count.setText(bean.getTotalCount() + "家");
						fileList = bean.getAuctionList();
						shopsAdapter = new DetailShopsAdapter(_context,
								fileList);
						if (shopsAdapter != null) {
							shops_listview.setAdapter(shopsAdapter);
						}
						_intent.putExtra("status", true);
					}
				}
				removeDialog(Const.PROGRESSBAR_WAIT);
			}
		};
		// 异步获取信息
		showDialog(Const.PROGRESSBAR_WAIT);
		Map<String, String> param = new HashMap<String, String>();
		param.put("product_id", pid);
		param.put("p", "1");
		param.put("n", keyworld);
		asyncQueryHandler.doWork(param);

		shops_listview.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {

				boolean re = ApplicationUser.CheckLogin(ShopActivity.this);
				if (!re) {
					Toast("请先登录", 1000);
					Intent intent1 = new Intent(ShopActivity.this, U_Login.class);
					startActivity(intent1);
					return;
				}
				userId = ApplicationUser.GetUserId();
				sellUrl = NetUtil.getUrl(fileList.get(position).getLink());
				fanliUrl = "http://api.36936.com/ClientApi/ClientGetShopUrl.ashx?url="
						+ sellUrl;
				try {
					String reStr = HttpHelper.getJsonData(ShopActivity.this,
							fanliUrl);
					JSONObject reObject = new JSONObject(reStr);

					String toUrl = reObject.getString("linkUrl");
					String newtoUrl = toUrl.replace("[USERID]", userId);
					if (toUrl.length() != 0) {
						uri = Uri.parse(newtoUrl);
					} else {
						uri = Uri.parse(sellUrl);
					}
					if (uri != null) {

						// Intent it = new Intent(Intent.ACTION_VIEW, uri);
						// startActivity(it);
						
						Intent intent = new Intent();
						intent.putExtra("url", uri.toString());
						intent.setClass(ShopActivity.this, ShowWebView.class);
						startActivity(intent);

					} else {
						Toast("链接地址不正确,请选择其它商品查看！", 1000);
					}

				} catch (Exception e) {
					// e.printStackTrace();
					Toast("网络异常", 1000);
				}
			}
		});

		footer.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (curpage < pages) {
					int cur = curpage + 1;
					AsyncWorkHandler asyncQueryHandler = new AsyncWorkHandler() {

						public Object excute(Map<String, String> map) {
							return ProductAction.getProductShops(map);
						}

						public void handleMessage(Message msg) {
							if (msg.obj != null) {
								ProductDetailBean bean = (ProductDetailBean) msg.obj;
								if ("true".equals(bean.getSuccess())) {
									ArrayList<AuctionListBean> tmpList = bean
											.getAuctionList();
									if (tmpList != null && tmpList.size() > 0) {
										curpage++;
										for (AuctionListBean shopBean : tmpList) {
											fileList.add(shopBean);
										}
										Log.d(Const.TAG,
												"ShopActivity.AsyncWork|curpage="
														+ curpage + ",pages="
														+ pages
														+ ",fileList.size="
														+ fileList.size());
										shopsAdapter.notifyDataSetChanged();
									}
								}
							}
							removeDialog(Const.PROGRESSBAR_WAIT);
						}
					};
					// 异步获取信息
					showDialog(Const.PROGRESSBAR_WAIT);
					Map<String, String> param = new HashMap<String, String>();
					param.put("product_id", pid);
					param.put("p", "" + cur);
					param.put("n", keyworld);
					asyncQueryHandler.doWork(param);
				} else {
					Toast("已经到最后一页", 1000);
				}
			}
		});

	}

	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case Const.PROGRESSBAR_WAIT:
			LoadingDialog wait_pd = new LoadingDialog(this);
			return wait_pd;
		}
		return null;
	}
}