package com.maiduo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

import com.hetianxia.activity.R;
import com.maiduo.bll.HttpHelper;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.SimpleAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Category extends Activity {
	private ListView listView;
	private DataAdapter adapter;
	private ArrayList<String> list = new ArrayList<String>();
	private ArrayList<String> list_id = new ArrayList<String>();
	SimpleAdapter simpleAdapter;
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
						String id = list_id.get(arg2);
						intent.putExtra("id", id);
						intent.setClass(Category.this, ProductCategory.class);
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
		setContentView(R.layout.category);

		listView = (ListView) findViewById(R.id.lvCategory);
		new Thread(new Runnable() {
			public void run() {
				// �������
				getData(Category.this);
			}
		}).start();
	}

	// ������ݵ�
	class DataAdapter extends BaseAdapter {

		List<String> list1;

		public DataAdapter(List<String> list1) {
			this.list1 = list1;
		}

		public int getCount() {
			return list1.size();
		}

		public String getItem(int position) {
			return list1.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View view, ViewGroup parent) {

			view = getLayoutInflater()
					.inflate(R.layout.category_big_item, null);
			// ͼƬ

			TextView tv_time = (TextView) view.findViewById(R.id.title);
			tv_time.setText(list1.get(position).toString());

			return view;
		}

	}

	public void getData(Context context) {

		String url = "http://api.36936.com/ClientApi/PointsShop/ProductType.ashx";

		try {
			String reStr = HttpHelper.getJsonData(Category.this, url);
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

}
