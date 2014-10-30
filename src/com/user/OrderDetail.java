package com.user;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.hetianxia.activity.R;
import com.maiduo.bll.AsyncImageLoader;
import com.maiduo.bll.Config;
import com.maiduo.bll.HttpHelper;
import com.maiduo.bll.SiteHelper;
import com.maiduo.bll.ApplicationUser;
import com.maiduo.model.DefaultSlide;
import com.maiduo.model.Order;
import com.maiduo.model.Product;
import com.shopping.Cart_After;

public class OrderDetail extends Activity {
	private ProductAdapter adapter;

	private Order order = new Order();
	private AsyncImageLoader asyImg = new AsyncImageLoader();
	List<Product> productlist = new ArrayList<Product>();

	private String userid = "";
	private String orderid = "";
	private ProgressDialog progressDialog;
	private ListView gridView;

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				if (progressDialog != null)
					progressDialog.dismiss();

				TextView tvOrderId = (TextView) findViewById(R.id.tvOrderId);
				tvOrderId.setText("　订单号：" + order.getInnerOrderId());

				TextView tvMoney = (TextView) findViewById(R.id.tvMoney);
				tvMoney.setText("　　权益宝：" + order.getGoodsAmount());
				TextView tvDate = (TextView) findViewById(R.id.tvDate);
				tvDate.setText("下单日期：" + order.getAddDate());
				final TextView tvStatus = (TextView) findViewById(R.id.tvStatus);
				tvStatus.setText("　　状态：" + order.getStatus());
				if (order.getStatus().equals("待付款")) {
					TextView tvgopay = (TextView) findViewById(R.id.tvgopay);
					tvgopay.setVisibility(View.VISIBLE);
					tvgopay.setOnClickListener(new OnClickListener() {

						public void onClick(View v) {
							new PopupWindows(OrderDetail.this, tvStatus);
						}
					});
				}

				adapter = new ProductAdapter(productlist);
				gridView.setAdapter(adapter);
				gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					public void onItemClick(AdapterView<?> adapterView,
							View view, int arg2, long arg3) {

						Intent intent = new Intent();
						intent.putExtra("id", adapter.getItem(arg2).getProId()
								+ "");
						intent.setClass(OrderDetail.this,
								com.maiduo.Product.class);
						startActivity(intent);
						// Toast.makeText(ProductList.this,
						// adapter.getItem(arg2).getId()+"",Toast.LENGTH_SHORT).show();
					}
				});
				break;
			case 2:
				if (progressDialog != null)
					progressDialog.dismiss();
				break;

			case 5:
				if (progressDialog != null)
					progressDialog.dismiss();

				Toast.makeText(OrderDetail.this, "交易成功", 3000).show();
				Intent intent = new Intent(OrderDetail.this, OrderList.class);
				startActivity(intent);
				finish();
				break;
			case 6:
				if (progressDialog != null)
					progressDialog.dismiss();
				Toast.makeText(OrderDetail.this, "交易失败", 3000).show();

				Intent intent2 = new Intent(OrderDetail.this, OrderList.class);
				startActivity(intent2);
				finish();
				break;
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_orderdetail);

		Intent intent = this.getIntent();
		orderid = intent.getStringExtra("orderid");

		// orderid = "";

		userid = ApplicationUser.getInstance().GetUserId();

		new Thread(new Runnable() {
			public void run() {
				// 加载数据

				getData();//

			}
		}).start();

		// TextView tvJifen = (TextView) findViewById(R.id.tvJifen);
		// tvJifen.setText("　　积分："+ order.getGoodsAmount() + "分");

		TextView tvFreight = (TextView) findViewById(R.id.tvFreight);
		// if(order.getIsFreightDelyPay() == 1){
		// tvFreight.setText("建议运费：￥"+ order.getFreight());
		// }else{
		// tvFreight.setText("　　运费：￥"+ order.getFreight());
		// }

		// TextView tvShouhuoName = (TextView) findViewById(R.id.tvShouhuoName);
		// tvShouhuoName.setText("　收货人："+ order.getShouhuoName());
		//
		// TextView tvAddress = (TextView) findViewById(R.id.tvAddress);
		// tvAddress.setText("收货地址："+ order.getAddress());
		//
		// TextView tvUserTel = (TextView) findViewById(R.id.tvUserTel);
		// tvUserTel.setText("　手机号："+ order.getUserTel());

		// 填充GridView
		gridView = (ListView) findViewById(R.id.gvProList);
		// adapter = new ProductAdapter(productlist);
		// gridView.setAdapter(adapter);

	}

	private void getData() {
		String url = "http://api.36936.com/ClientApi/PointsShop/order/OrderInfo.ashx?orderId="
				+ orderid + "&userId=" + userid;
		// url = SiteHelper.GetSendUrl(url); // 客户端加密网址
		// 这里需要分析服务器回传的json格式数据，
		try {
			String reStr = HttpHelper.getJsonData(OrderDetail.this, url);
			JSONObject reObject = new JSONObject(reStr);
			if (reObject.getInt("status") == 0) {
				// Toast.makeText(this,reObject.getString("error"),Toast.LENGTH_SHORT).show();
				return;
			}
			// JSONObject jsonObject = reObject.getJSONObject("result");

			// proTitle = jsonObject.getString("protitle");
			order.setInnerOrderId(reObject.getString("InnerOrderId"));
			order.setGoodsAmount(reObject.getString("GoodsAmount"));
			order.setAddDate(reObject.getString("AddDate"));
			order.setReceiveName(reObject.getString("ReceiveName"));
			// order.set(jsonObject.getString("address"));
			// order.setUserTel(jsonObject.getString("usertel"));
			order.setStatus(reObject.getString("Status"));
			//
			// order.setOrderJifen(jsonObject.getString("orderJifen"));
			// order.setFreight(jsonObject.getString("freight"));
			// order.setIsFreightDelyPay(jsonObject.getInt("IsFreightDelyPay"));//0现付
			// 1到付

			// 商品列表
			JSONArray jsonArray1 = reObject.getJSONArray("list");
			for (int i = 0; i < jsonArray1.length(); i++) {
				JSONObject jsonObject2 = (JSONObject) jsonArray1.opt(i);
				Product product = new Product();
				product.setProId(jsonObject2.getString("productId"));
				product.setProName(jsonObject2.getString("ProName"));
				// product.setProImg(jsonObject2.getString("smallImg"));
				productlist.add(product);
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
			Message msg = new Message();
			msg.what = 2;
			mHandler.sendMessage(msg);
		}

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
			view = getLayoutInflater().inflate(R.layout.griditem, null);
			/*
			 * //图片 TextView tvTitle = (TextView)view.findViewById(R.id.pic);
			 * tvTitle.setText(productItems.get(position).getPic());
			 */
			// System.out.println(productItems.get(position).getPic());

			// String url = productItems.get(position).getProImg();
			// asyImg.LoadImage( url, (ImageView)view.findViewById(R.id.pic));
			ImageView img = (ImageView) view.findViewById(R.id.pic);
			img.setVisibility(View.GONE);
			// 标题
			TextView tvTitle = (TextView) view.findViewById(R.id.title);
			tvTitle.setText(productItems.get(position).getProName());
			// +"￥："+productItems.get(position).getPrice()

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

	private class PopupWindows extends PopupWindow {

		public PopupWindows(Context mContext, View parent) {

			View view = View
					.inflate(mContext, R.layout.item_popupwindows, null);
			view.startAnimation(AnimationUtils.loadAnimation(mContext,
					R.anim.fade_ins));
			LinearLayout ll_popup = (LinearLayout) view
					.findViewById(R.id.ll_popup);
			ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,
					R.anim.push_bottom_in_2));

			setWidth(LayoutParams.FILL_PARENT);
			setHeight(LayoutParams.FILL_PARENT);
			setBackgroundDrawable(new BitmapDrawable());
			setFocusable(true);
			setOutsideTouchable(true);
			setContentView(view);
			showAtLocation(parent, Gravity.BOTTOM, 0, 0);
			update();

			TextView tv_money = (TextView) view.findViewById(R.id.money);
			tv_money.setText("权益宝：" + order.getGoodsAmount() + "个");
			Button bt3 = (Button) view
					.findViewById(R.id.item_popupwindows_cancel);
			bt3.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					dismiss();
					// String uId =
					// MySharedData.sharedata_ReadString(Sub_Jh.this,
					// "uid");
					// Intent intent_jh = new Intent(Sub_Jh.this,
					// JiaoHuanList.class);
					// intent_jh.putExtra("uid", uId);
					// startActivity(intent_jh);
					// finish();
				}
			});

			Button bt_submit = (Button) view
					.findViewById(R.id.item_popupwindows_submit);
			bt_submit.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					// TODO Auto-generated method stub

					new Thread(new Runnable() {
						public void run() {
							// 加载数据

							String UserId = ApplicationUser.getInstance()
									.GetUserId();
							String hash = SiteHelper.MD5(UserId
									+ Config.UrlHashKey2);
							String url = "http://api.36936.com/ClientApi/PointsShop/order/PayOrer.ashx"
									+ "?userId="
									+ UserId
									+ "&oid="
									+ order.getInnerOrderId() + "&hash=" + hash;

							try {
								String reStr = HttpHelper.getJsonData(
										OrderDetail.this, url);
								JSONObject reObject = new JSONObject(reStr);
								if (reObject.getInt("status") == 1) {
									// if(reObject.getString("msg").equals("没有找到商品")){
									//
									// }
									Message msg = new Message();
									msg.what = 5;
									mHandler.sendMessage(msg);
								} else {

									Message msg = new Message();
									msg.what = 6;
									mHandler.sendMessage(msg);
								}

							} catch (JSONException e) {
								// TODO Auto-generated catch block
								// e.printStackTrace();
								// Toast.makeText(this,getString(R.string.loading_error),Toast.LENGTH_SHORT).show();
								Message msg = new Message();
								msg.what = 6;
								mHandler.sendMessage(msg);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								// e.printStackTrace();
								// Toast.makeText(this,getString(R.string.loading_error),Toast.LENGTH_SHORT).show();
								Message msg = new Message();
								msg.what = 6;
								mHandler.sendMessage(msg);
							}
						}
					}).start();
				}
			});

		}
	}
}
