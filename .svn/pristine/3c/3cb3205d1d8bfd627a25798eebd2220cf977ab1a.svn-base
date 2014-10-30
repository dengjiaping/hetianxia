package com.htx.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import com.htx.model.stact;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import com.hetianxia.activity.R;
import com.htx.bean.AuctionListBean;
import com.htx.bean.ProductDetailBean;
import com.htx.conn.BitmapDownloaderTask;
import com.htx.conn.Const;
import com.htx.conn.ImageUtil;
import com.htx.conn.NetUtil;
import com.htx.core.ATManager;
import com.htx.core.AsyncWorkHandler;
import com.htx.core.DBHelper;
import com.htx.pub.ApplicationUser;
import com.htx.pub.LoadingDialog;
import com.htx.pub.MySharedData;
import com.htx.pub.PubActivity;
import com.htx.pub.ShowWebView;

/**
 * 
 * @author lvan
 * 
 */
public class DetailActivity extends PubActivity {

	private Context _context;// 当前Activity上下文
	private Intent _intent;// 转发器
	private TextView highprice;// 最高报价
	private TextView lowprice;// 最低报价
	private TextView shops;// 商家数
	private TextView receives;// 评论数
	private ListView shops_layout;// 商家展现容器
	private ProductDetailBean detailBean;// 产品详情信息
	private DetailImageAdapter photoAdapter;// 图片适配器
	private DetialGallery detailGallery;// 图片滑动容器
	private LayoutInflater inflater;// View工厂类
	private LinearLayout detail_bt_sc;// 收藏按钮
	private LinearLayout detail_bt_fx;// 分享按钮
	private ArrayList<AuctionListBean> fileList;
	private String _coverImage;
	private Bitmap coverBitmap;
	private String coverImagePath;// 图像存储的本地路径
	private String sellUrl;
	private Uri uri;
	private String fanliUrl;
	private String userId;
	private ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
	private LinearLayout top, foot;
	private TextView detail_title, titlee;

	/** Called when the activity is first created. */

	public void onCreate(Bundle savedInstanceState) {

		setContentView(R.layout.detail);
		super.onCreate(savedInstanceState);
		init();

		if (detailBean != null && "true".equals(detailBean.getSuccess())) {
			fileList = detailBean.getAuctionList();
			if (fileList != null) {
				int size = fileList.size();
				String[] NickName = new String[size];
				String[] Link = new String[size];
				Float[] Price = new Float[size];
				for (int i = 0; i < size; i++) {
					NickName[i] = fileList.get(i).getUserNickName();
					Price[i] = Float.parseFloat(fileList.get(i).getPrice());
					Link[i] = fileList.get(i).getLink();
				}

				for (int i = 0; i < size - 1; i++) {
					for (int j = i + 1; j < size; j++) {
						if (Price[i] > Price[j]) {
							Float temp = Price[i];
							String nick = NickName[i];
							String L = Link[i];
							Price[i] = Price[j];
							NickName[i] = NickName[j];
							Link[i] = Link[j];
							Price[j] = temp;
							NickName[j] = nick;
							Link[j] = L;
						}
					}
				}
				
				for (int i = 0; i < size; i++) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("shop_name", NickName[i]);
					map.put("shop_price", Price[i] + "元");
					map.put("link", Link[i]);
					listItem.add(map);
				}

				highprice.setText((listItem.get(0).get("shop_price")) + "");
				lowprice.setText((listItem.get(size - 1).get("shop_price"))
						+ "");
				shops.setText(detailBean.getTotalCount() + "家");
				receives.setText("");
				
				// 生成适配器的Item和动态数组对应的元素
				SimpleAdapter listItemAdapter = new SimpleAdapter(this,
						listItem,// 数据源
						R.layout.shops_view,// ListItem的XML实现
						// 动态数组与ImageItem对应的子项
						new String[] { "shop_name", "shop_price" },
						// ImageItem的XML文件里面的一个ImageView,两个TextView ID
						new int[] { R.id.shop_name, R.id.shop_pric });
				// 添加并且显示
				shops_layout.setAdapter(listItemAdapter);

				Utility.setListViewHeightBasedOnChildren(shops_layout);
				// 添加点击
				shops_layout.setOnItemClickListener(new OnItemClickListener() {

					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {

						fanliUrl = NetUtil.getUrl(listItem.get(arg2).get("link").toString());

						Intent intent = new Intent();
						intent.putExtra("url", fanliUrl.toString());
						intent.setClass(DetailActivity.this, ShowWebView.class);
						startActivity(intent);
					}
				});
			}

			_coverImage = detailBean.getProductDO().getPictUrl() + "_80x80.jpg";

			if (_coverImage != null) {

				BitmapDownloaderTask task = new BitmapDownloaderTask(
						_coverImage) {

					protected Bitmap doInBackground(String... params) {
						// 首先获取首页图片，让用户先可见
						try {
							// coverBitmap =
							// ImageUtil.getBitmap(ImageUtil.getPicUrl(_coverImage,3));
							coverBitmap = ImageUtil.getBitmap(_coverImage);
							photoAdapter.addPic(coverBitmap);
						} catch (Exception e) {
							Log.e(Const.TAG,
									"DetailActivity._coverImage asyncTask|getCoverImage fail...",
									e);
						}
						return null;
					}

					protected void onPostExecute(Bitmap bm) {
						photoAdapter.notifyDataSetChanged();
						// 再慢慢获取其它图片
						for (int i = 0; i < fileList.size(); i++) {
							String image_url = fileList.get(i).getPic()
									+ "_80x80.jpg";
							BitmapDownloaderTask task = new BitmapDownloaderTask(
									image_url) {

								protected void onPostExecute(Bitmap bm) {
									if (isCancelled()) {
										bm = null;
									}
									if (bm != null) {
										photoAdapter.addPic(bm);
										photoAdapter.notifyDataSetChanged();
									}
								}
							};
							task.execute("");
						}
						// }

						if (isCancelled()) {
							bm = null;
						}

						if (bm != null) {
							photoAdapter.addPic(bm);
							photoAdapter.notifyDataSetChanged();
						}
					}
				};

				task.execute("");
			}

			_intent.putExtra("status", true);

		} else {
			Toast("信息不存在,请选择其它商品查看！", 1000);
			DetailActivity.this.finish();
		}

		// 收藏商品
		detail_bt_sc.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				boolean sdCard = android.os.Environment
						.getExternalStorageState().equals(
								android.os.Environment.MEDIA_MOUNTED);

				if (sdCard) {

					AsyncWorkHandler asyncQueryHandler = new AsyncWorkHandler() {

						public Object excute(Map<String, String> map) {

							Integer res = -1;

							if (detailBean != null
									&& "true".equals(detailBean.getSuccess())) {

								try {

									DBHelper dbHelp = new DBHelper(_context);

									boolean findIt = dbHelp
											.queryData(detailBean
													.getProductDO().getPid());

									if (findIt) {

										res = 1;// 该产品曾被收藏

									} else {
										if (coverImagePath == null
												&& coverBitmap != null) {

											String str = detailBean
													.getProductDO()
													.getPictUrl();
											int n = str.lastIndexOf("/");
											if (n != -1)
												str = str.substring(n + 1,
														str.length());

											coverImagePath = ImageUtil
													.saveToSDCard(coverBitmap,
															str);
										}

										String price = fileList.get(0)
												.getPrice();
										int n = price.indexOf(".");
										if (n != -1)
											price = price.substring(0, n);

										boolean insert_ok = dbHelp.save(
												detailBean.getProductDO()
														.getPid(), detailBean
														.getProductDO()
														.getTitle(), Integer
														.parseInt(price),
												Integer.parseInt(detailBean
														.getTotalCount()), 0,
												(coverImagePath == null ? ""
														: coverImagePath));

										if (insert_ok) {
											res = 0;
										}
									}

									dbHelp.close();
								} catch (Exception e) {
									Log.e(Const.TAG,
											"DetailActivity.detail_bt_sc|Exception:",
											e);
								}

							}

							removeDialog(Const.PROGRESSBAR_WAIT);

							return res;
						}

						public void handleMessage(Message msg) {

							String message = "收藏失败！";

							Integer res = -1;

							if (msg.obj != null) {
								res = (Integer) msg.obj;
							}

							if (res == 0) {
								message = "收藏成功！";
							} else if (res == 1) {
								message = "该商品已经收藏！";
							} else {
								message = "收藏成功！";
							}
							Toast(message, 1000);
						}
					};
					// 异步获取信息
					showDialog(Const.PROGRESSBAR_WAIT);
					asyncQueryHandler.doWork(null);
				} else {
					Toast("收藏失败,请插入存储卡！", 1000);
				}
			}

		});

		// 分享
		detail_bt_fx.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				if (detailBean != null) {

					if (coverImagePath == null && coverBitmap != null) {
						boolean sdCard = android.os.Environment
								.getExternalStorageState().equals(
										android.os.Environment.MEDIA_MOUNTED);
						if (sdCard) {
							String str = detailBean.getProductDO().getPictUrl();
							int n = str.lastIndexOf("/");
							if (n != -1)
								str = str.substring(n + 1, str.length());

							coverImagePath = ImageUtil.saveToSDCard(
									coverBitmap, str);
						} else {
							Toast("没有找到您的存储卡,您只能分享文字信息！", 1000);
						}
					}
					
					Intent intent = new Intent(Intent.ACTION_SEND);
					intent.setType("text/plain");
					intent.putExtra(Intent.EXTRA_SUBJECT, "分享");

					String spreadUrl = MySharedData.sharedata_ReadString(DetailActivity.this,
							"spreadUrl");
					if (spreadUrl.equals("")) {
						spreadUrl = "http://www.36936.com/UserRegisterManage/MiNiUserReg.aspx?uid="
								+ ApplicationUser.GetUserId();
					}
					StringBuffer s_t = new StringBuffer();
					// 商品标题+ “合天下分析结果：” + 商家数 + “家网店有买，” + 评论数 + “人发表评论。最高价为” +
					// 最高价 + “元，最低价为”+ 最低价 + “元。详情请登陆：+url。”
					s_t.append(detailBean.getProductDO().getTitle())
							.append(" 合天下分析结果：")
							.append(detailBean.getTotalCount())
							.append("家网店有买，")
							.append("")
							.append("人发表评论。")
							.append("最高价为")
							.append(fileList.get(fileList.size() - 1)
									.getPrice()).append("元，最低价为")
							.append(fileList.get(0).getPrice())
							.append("元。详情请登陆 :http://" + spreadUrl);	

					intent.putExtra(Intent.EXTRA_TEXT, s_t.toString());
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(Intent.createChooser(intent, getTitle()));
				}

			}

		});
	}

	/**
	 * 初始化所需要的信息
	 */
	private void init() {

		_context = this;
		_intent = this.getIntent();
		inflater = LayoutInflater.from(_context);
		highprice = (TextView) findViewById(R.id.detail_highprice);
		lowprice = (TextView) findViewById(R.id.detail_lowprice);
		shops = (TextView) findViewById(R.id.detail_shops);
		receives = (TextView) findViewById(R.id.detail_receives);
		shops_layout = (ListView) findViewById(R.id.detail_shops_layout);
		detail_title = (TextView) findViewById(R.id.detail_auction_title);
		titlee = (TextView) findViewById(R.id.detail_titlee);

		detail_bt_sc = (LinearLayout) findViewById(R.id.detail_bt_sc);
		detail_bt_fx = (LinearLayout) findViewById(R.id.detail_bt_fx);
		detailGallery = (DetialGallery) findViewById(R.id.detail_gallery);
		top = (LinearLayout) findViewById(R.id.top);
		foot = (LinearLayout) findViewById(R.id.foot);
		detailGallery.setUnselectedAlpha(1.1f);
		photoAdapter = new DetailImageAdapter(_context);
		detailGallery.setAdapter(photoAdapter);
		titlee.setText(stact.getName());

		Object obj = _intent.getSerializableExtra("detailBean");

		if (obj != null) {
			detailBean = (ProductDetailBean) obj;
		}
	}

	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case Const.DIALOG_YES_NO_MESSAGE:
			return new AlertDialog.Builder(_context)
					.setTitle("确定退出程序？")
					// 设置对话框的标题
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {// 设置按下表示确定按钮时按钮的text，和按钮的事件监听器

								public void onClick(DialogInterface dialog,
										int whichButton) {
									removeDialog(Const.DIALOG_YES_NO_MESSAGE);
									ATManager.exitClient(_context);
								}
							})
					.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {// 设置取消按钮的text
								// 和监听器

								public void onClick(DialogInterface dialog,
										int whichButton) {
									dialog.dismiss();
								}
							}).create();
		case Const.PROGRESSBAR_WAIT:
			LoadingDialog wait_pd = new LoadingDialog(this);
			return wait_pd;
		}

		return null;
	}

}