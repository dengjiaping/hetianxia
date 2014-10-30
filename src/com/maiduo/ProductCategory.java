package com.maiduo;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

import com.hetianxia.activity.R;
import com.maiduo.bll.HttpHelper;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class ProductCategory extends Activity {
	private ListView listView;
	private DataAdapter adapter;
	private String id;
	private ArrayList<String> list = new ArrayList<String>();
	private ArrayList<String> list_id = new ArrayList<String>();

	private ProgressDialog progressDialog;
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:

				adapter = new DataAdapter(list);
				listView.setAdapter(adapter);
				listView.setOnItemClickListener(new OnItemClickListener() {

					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {

						Intent intent = new Intent();
						// intent.putExtra("id",list_id.get(arg2));
						intent.putExtra("id", id);
						intent.setClass(ProductCategory.this, ProductList.class);
						startActivity(intent);
					}
				});
				break;
			case 2:

				break;
			}
			super.handleMessage(msg);
		}
	};

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.product_category);
		// super.InitFoot(0);

		Intent intent = this.getIntent();
		id = intent.getStringExtra("id");
		listView = (ListView) findViewById(R.id.lvCategory);
		new Thread(new Runnable() {
			public void run() {
				// �������
				getData(ProductCategory.this);
			}
		}).start();

	}

	public void getData(Context context) {

		String url = "http://api.36936.com/ClientApi/PointsShop/ProductType.ashx?Pid="
				+ id;

		try {
			String reStr = HttpHelper.getJsonData(ProductCategory.this, url);
			JSONObject reObject = new JSONObject(reStr);

			JSONArray reArray = reObject.getJSONArray("list");

			if (reObject.getInt("status") == 1) {

				list = new ArrayList<String>();

				for (int i = 0; i < reArray.length(); i++) {
					JSONObject jsonObject = (JSONObject) reArray.opt(i);
					list.add(jsonObject.getString("Name"));
					list_id.add(jsonObject.getString("ID"));
				}
				Message message = new Message();
				message.what = 1;
				mHandler.sendMessage(message);
			} else {
				Message message = new Message();
				message.what = 2;
				mHandler.sendMessage(message);
			}
		} catch (Exception e) {
			Message message = new Message();
			message.what = 2;
			mHandler.sendMessage(message);
			e.printStackTrace();
		}
	}

	class DataAdapter extends BaseAdapter {

		List<String> list;

		public DataAdapter(List<String> list) {
			this.list = list;
		}

		public int getCount() {
			return list.size();
		}

		public String getItem(int position) {
			return list.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View view, ViewGroup parent) {

			view = getLayoutInflater().inflate(R.layout.category_item, null);
			// ͼƬ

			TextView tv_time = (TextView) view.findViewById(R.id.categoryTitle);
			tv_time.setText(list.get(position).toString());

			return view;
		}

	}

}
