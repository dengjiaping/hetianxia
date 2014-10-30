package com.htx.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import com.hetianxia.activity.R;
import com.htx.pub.MySharedData;
import com.htx.pub.PubUserActivity;

public class AppsManage extends PubUserActivity {

	private GridView gridView;
	private List<HashMap<String, String>> listData;
	private Button btn;
	private TextView toptv;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.app_main);

		// 初始化 应用列表数据
		initAppDate();
	}

	/**
	 * 初始化应用数据
	 */
	private void initAppDate() {


		gridView = (GridView) findViewById(R.id.mainGrid);
		btn = (Button) findViewById(R.id.button);
		toptv = (TextView) findViewById(R.id.toptv);
		
		toptv.setText("生活应用管理");
		btn.setText("返回");
		btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				setResult(RESULT_OK, new Intent());
				finish();// 此处一定要调用finish()方法
			}
		});

		final List<HashMap<String, String>> list = getimageList();
		gridView.setAdapter(new MyAdpater(this, list));

		gridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				StartOneApp.StartApk(AppsManage.this,
						listData.get(arg2).get("apkName"), listData.get(arg2)
								.get("downPath"),
						listData.get(arg2).get("packageName"),
						listData.get(arg2).get("ActivityName"));
			}
		});
	}

	private List<HashMap<String, String>> getimageList() {

		listData = new ArrayList<HashMap<String, String>>();
		try {
			String appJosnData = MySharedData.sharedata_ReadString(
					AppsManage.this, "appJosnData");
			JSONArray reJSONArray = new JSONArray(appJosnData);
			if (!reJSONArray.equals("")) {
				for (int i = 0; i < reJSONArray.length(); i++) {
					JSONObject jsonObject = (JSONObject) reJSONArray.opt(i);
					HashMap hash = new HashMap();
					hash.put("apkName", jsonObject.getString("apkName"));
					hash.put("imagePath", jsonObject.getString("imagePath"));
					hash.put("downPath", jsonObject.getString("downPath"));
					hash.put("packageName", jsonObject.getString("packageName"));
					hash.put("ActivityName",
							jsonObject.getString("ActivityName"));
					listData.add(hash);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Toast("网络超时", 1000);
		}

		return listData;
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (event.getAction() == KeyEvent.ACTION_DOWN
				&& KeyEvent.KEYCODE_BACK == keyCode) {
			// intent为A传来的带有Bundle的intent，当然也可以自己定义新的Bundle
			setResult(RESULT_OK, new Intent());
			finish();// 此处一定要调用finish()方法
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
