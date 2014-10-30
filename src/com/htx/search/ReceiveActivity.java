package com.htx.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.hetianxia.activity.R;
import com.htx.action.ProductAction;
import com.htx.bean.AuthBean;
import com.htx.bean.ReceiveBean;
import com.htx.conn.Const;
import com.htx.core.AsyncWorkHandler;
import com.htx.pub.LoadingDialog;
import com.htx.pub.PubActivity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 
 * @author lvan
 *
 */
public class ReceiveActivity extends PubActivity {

	private Context _context;
	private TextView receive_count;
	private ListView receive_listview;
	private ReceiveAdapter receiveAdapter;
	private LayoutInflater inflater;
	private View footer;
	private Intent _intent;
	private String pid;
	private String keyworld;
	private int curpage = 1;// 当前页
	private int pages = 1;// 共多少页
	private int curcount = 0;
	private int counts = 0;

	/** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) {

		setContentView(R.layout.receive);
		super.onCreate(savedInstanceState);

		_context = this;
		_intent = this.getIntent();

		inflater = LayoutInflater.from(_context);
		footer = inflater.inflate(R.layout.receive_footer, null);
		receive_listview = (ListView) findViewById(R.id.receive_listview);
		receive_listview.addFooterView(footer);

		pid = _intent.getStringExtra("product_id");
		keyworld = _intent.getStringExtra("keyworld");

		receive_count = (TextView) findViewById(R.id.receive_count);

		// 第一次拉取数据(异步)
		showDialog(Const.PROGRESSBAR_WAIT);
		Map<String, String> param = new HashMap<String, String>();
		param.put("product_id", pid);
		param.put("p", "0");
		param.put("n", keyworld);
		AsyncWorkHandler asyncQueryHandler = new AsyncWorkHandler() {

			public Object excute(Map<String, String> map) {
				return ProductAction.getProductReceive(map);
			}

			public void handleMessage(Message msg) {
				if (msg.obj != null) {
					ReceiveBean bean = (ReceiveBean) msg.obj;
					if ("true".equals(bean.getSuccess())) {
						int count = Integer.parseInt(bean.getTotalCount());
						counts = count;
						curcount = Integer.parseInt(bean.getReturnCount());
						receive_count.setText(count + "条");
						ArrayList<AuthBean> fileList = bean.getComments();
						receiveAdapter = new ReceiveAdapter(_context);
						receiveAdapter.setReceiveList(fileList);
						if (receiveAdapter != null) {
							receive_listview.setAdapter(receiveAdapter);
						}
						_intent.putExtra("status", true);
					}
				}
				removeDialog(Const.PROGRESSBAR_WAIT);
			}
		};
		asyncQueryHandler.doWork(param);

		// 查看更多
		footer.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (curcount < counts) {
					int cur = curcount;
					AsyncWorkHandler asyncQueryHandler = new AsyncWorkHandler() {

						public Object excute(Map<String, String> map) {
							return ProductAction.getProductReceive(map);
						}

						public void handleMessage(Message msg) {
							if (msg.obj != null) {
								ReceiveBean bean = (ReceiveBean) msg.obj;
								if ("true".equals(bean.getSuccess())) {
									ArrayList<AuthBean> tmpList = bean
											.getComments();
									if (tmpList != null && tmpList.size() > 0) {
										curcount += tmpList.size();
										receiveAdapter.containsList(tmpList);
										receiveAdapter.notifyDataSetChanged();
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