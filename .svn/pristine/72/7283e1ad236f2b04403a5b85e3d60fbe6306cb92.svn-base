package com.htx.search;

import java.util.HashMap;
import java.util.Map;
import com.hetianxia.activity.R;
import com.htx.action.ProductAction;
import com.htx.bean.ProductDetailBean;
import com.htx.conn.Const;
import com.htx.core.ATManager;
import com.htx.core.AsyncWorkHandler;
import com.htx.model.stact;
import com.htx.pub.LoadingDialog;
import com.htx.pub.PubGroupActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.LocalActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 
 * @author Ivan
 */
public class DetailActivityGroup extends PubGroupActivity {

	private LinearLayout container = null;
	private Button goback;
	private Context context;
	private TextView detail_tab1;
	private TextView detail_tab2;
	private TextView detail_tab3;
	private LocalActivityManager localActivityManager;
	private ProductDetailBean detailBean;
//	private TextView detail_title;
	private Intent _intent;
	private String pid;
	private String keyworld;

	private void resumeActivity(Activity activity, Class<?> cls,
			String activityId) {

		boolean status = false;

		if (activity != null) {
			status = activity.getIntent().getBooleanExtra("status", false);
		}

		Log.d(Const.TAG, "DetailActivityGroup.resumeActivity|activity="
				+ activity + ",activityId=" + activityId + ",status=" + status);

		if (activity != null && status) {
			View activityView = activity.getWindow().getDecorView();
			if (container != null) {
				container.removeAllViews();
				container.addView(activityView);
			}
		} else {
			Intent it = new Intent(this, cls);
			container.addView(getLocalActivityManager().startActivity(
					activityId, it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
					.getDecorView());
		}

	}

	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.detail_module);

		ATManager.addActivity(this);

		context = this;

		_intent = this.getIntent();

		localActivityManager = getLocalActivityManager();

		pid = _intent.getStringExtra("product_id");
		keyworld = _intent.getStringExtra("keyworld");

		goback = (Button) findViewById(R.id.detail_goback);

//		detail_title = (TextView) findViewById(R.id.detail_title);

		goback.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				DetailActivityGroup.this.finish();
			}
		});

		container = (LinearLayout) findViewById(R.id.detailBody);

		detail_tab1 = (TextView) findViewById(R.id.detail_tab1);
		detail_tab2 = (TextView) findViewById(R.id.detail_tab2);
		detail_tab3 = (TextView) findViewById(R.id.detail_tab3);

		if(!net()){
			Toast("无网络！", 2000);
			return;
		}
		
		
		// 第一次载入
		if (localActivityManager.getActivity("DetailActivity") == null) {

			AsyncWorkHandler asyncQueryHandler = new AsyncWorkHandler() {

				public Object excute(Map<String, String> map) {
					detailBean = ProductAction.getProductDetail(map);
					return null;
				}

				public void handleMessage(Message msg) {
					if (detailBean != null
							&& detailBean.getProductDO() != null
							&& detailBean.getProductDO().getTitle() != null
							&& !"".equals(detailBean.getProductDO().getTitle()
									.trim())) {
//						detail_title.setText(detailBean.getProductDO()
//								.getTitle());
						
						stact.setName(detailBean.getProductDO().getTitle().toString());
						
					}
					Intent it = new Intent(context, DetailActivity.class);
					Bundle mBundel = new Bundle();
					mBundel.putSerializable("detailBean", detailBean);
					it.putExtras(mBundel);
					container
							.addView(
									getLocalActivityManager()
											.startActivity(
													"DetailActivity",
													it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
											.getDecorView(),
									LayoutParams.FILL_PARENT,
									LayoutParams.FILL_PARENT);
					removeDialog(Const.PROGRESSBAR_WAIT);
				}

			};
			// 异步获取信息
			showDialog(Const.PROGRESSBAR_WAIT);
			Map<String, String> param = new HashMap<String, String>();
			param.put("product_id", pid);
			param.put("n", keyworld);
			asyncQueryHandler.doWork(param);
		}

		
		// tab1
		detail_tab1.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				Activity forward_activity = localActivityManager
						.getActivity("DetailActivity");

				container.removeAllViews();

				if (forward_activity != null) {
					
					resumeActivity(forward_activity, DetailActivity.class,
							"DetailActivity");
				} else {
					Intent it = new Intent();
					it.setClass(context, DetailActivity.class);

					it.putExtra("product_id", pid);
					container.addView(
							localActivityManager
									.startActivity(
											"DetailActivity",
											it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
									.getDecorView(), LayoutParams.FILL_PARENT,
							LayoutParams.FILL_PARENT);
				}
				detail_tab1.setBackgroundResource(R.drawable.detail_tab_select);
				detail_tab2.setBackgroundResource(R.drawable.detail_tab_def);
				detail_tab3.setBackgroundResource(R.drawable.detail_tab_def);
			}
		});

		// tab2
		detail_tab2.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Activity forward_activity = localActivityManager
						.getActivity("ReceiveActivity");

				container.removeAllViews();

				if (forward_activity != null) {
					resumeActivity(forward_activity, ReceiveActivity.class,
							"ReceiveActivity");
				} else {
					Intent it = new Intent();
					it.setClass(context, ReceiveActivity.class);
					it.putExtra("product_id", pid);
					it.putExtra("keyworld", keyworld);
					container.addView(localActivityManager.startActivity(
							"ReceiveActivity",
							it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
							.getDecorView());
				}
				detail_tab1.setBackgroundResource(R.drawable.detail_tab_def);
				detail_tab2.setBackgroundResource(R.drawable.detail_tab_select);
				detail_tab3.setBackgroundResource(R.drawable.detail_tab_def);
			}
		});

		// tab3
		detail_tab3.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Activity forward_activity = localActivityManager
						.getActivity("ShopActivity");

				container.removeAllViews();

				if (forward_activity != null) {
					resumeActivity(forward_activity, ShopActivity.class,
							"ShopActivity");
				} else {
					Intent it = new Intent();
					it.setClass(context, ShopActivity.class);
					it.putExtra("product_id", pid);
					it.putExtra("keyworld", keyworld);
					container.addView(localActivityManager.startActivity(
							"ShopActivity",
							it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
							.getDecorView());
				}
				detail_tab1.setBackgroundResource(R.drawable.detail_tab_def);
				detail_tab2.setBackgroundResource(R.drawable.detail_tab_def);
				detail_tab3.setBackgroundResource(R.drawable.detail_tab_select);
			}
		});
	}

	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case Const.PROGRESSBAR_WAIT:
			LoadingDialog wait_pd = new LoadingDialog(this);
			return wait_pd;
		case Const.DIALOG_YES_NO_MESSAGE:
			return new AlertDialog.Builder(context)
					.setTitle("加入收藏？")
					// 设置对话框的标题
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {// 设置按下表示确定按钮时按钮的text，和按钮的事件监听器

								public void onClick(DialogInterface dialog,
										int whichButton) {
									removeDialog(Const.DIALOG_YES_NO_MESSAGE);
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
		}

		return null;
	}

	
	/**
	 * 判断网络状态
	 * 
	 * @return netSataus
	 */
	public boolean net() {
		ConnectivityManager cwjManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		cwjManager.getActiveNetworkInfo();
		boolean netSataus = false;
		if (cwjManager.getActiveNetworkInfo() != null) {
			netSataus = cwjManager.getActiveNetworkInfo().isAvailable();
		}
		Log.e("网络是否可用：", netSataus + "");
		return netSataus;
	}
	
}
