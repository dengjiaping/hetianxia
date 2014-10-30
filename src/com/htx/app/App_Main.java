package com.htx.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import com.hetianxia.activity.R;
import com.htx.pub.LoadingDialog;
import com.htx.pub.MySharedData;
import com.htx.pub.PubUserActivity;

/**
 * 
 * @author lvan
 * 
 */
public class App_Main extends PubUserActivity {

	// private String url =
	// "http://www.36936.com/WorldClient/ClientMobApplyName.ashx";
	private GridView gridView;
	private List<HashMap<String, String>> listData;
	private Button btn;
	private LoadingDialog progressDialog;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.app_main);

//		if (UserInfo.getUserId() == null
//				|| UserInfo.getUserId().toString().equals("")) {
//			finish();
//		}
		
		// 首次加载提示加载中
		progressDialog = new LoadingDialog(App_Main.this);
		progressDialog.show();

		new Thread(new Runnable() {
			public void run() {
				// getDate();
				getAllApp();
			}
		}).start();

		// init();
		// 初始化 应用列表数据
		initAppDate();

		// PackageManager p = getPackageManager();
		// p.setComponentEnabledSetting(getComponentName(),
		// PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
		// PackageManager.DONT_KILL_APP);
	}

	// protected void getDate() {
	// String reStr = HttpHelper.getJsonData(App_Main.this, url);
	// reStr = reStr.replaceAll(" ", "");
	//
	// try {
	// JSONObject reObject = new JSONObject(reStr);
	// if (reObject.getInt("status") == 1) {
	// JSONObject jsonObject = reObject.getJSONObject("result");
	// app_ol_all =jsonObject.toString();
	// }
	// } catch (JSONException e) {
	// e.printStackTrace();
	// }
	//
	// }

	/**
	 * 初始化应用数据
	 */
	private void initAppDate() {

		String appJosnData = MySharedData.sharedata_ReadString(App_Main.this,
				"app_my");
		if (appJosnData.equals("") || appJosnData == null) {
			String youxin = "有信,http://www.36936.com/WorldClient/android/ico.gif,http://www.36936.com/WorldClient/Youxin.apk,com.yx,com.yx.Splash";
			MySharedData.sharedata_WriteString(App_Main.this, "app_my", youxin);
		}
		gridView = (GridView) findViewById(R.id.mainGrid);
		btn = (Button) findViewById(R.id.button);

		btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				startActivityForResult(new Intent(App_Main.this,
						AppsManage.class), 1011);
			}
		});

		final List<HashMap<String, String>> list = getimageList();
		gridView.setAdapter(new MyAdpater(this, list));
		gridView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if ((list.size() - 1) == arg2) {
					
				} else {
					StartOneApp.StartApk(App_Main.this,
							listData.get(arg2).get("apkName"),
							listData.get(arg2).get("downPath"),
							listData.get(arg2).get("packageName"), listData
									.get(arg2).get("ActivityName"));
				}
			}
		});
		progressDialog.dismiss();
		Log.e("WW", "--------------5");
	}

	private List<HashMap<String, String>> getimageList() {

		listData = new ArrayList<HashMap<String, String>>();
		try {
			String app_my = MySharedData.sharedata_ReadString(App_Main.this,
					"app_my");
			Log.e("AWAWAWA--88--", app_my);
			String[] app_mylist = app_my.split("\\+");
			for (int i = 0; i < app_mylist.length; i++) {
				if (app_mylist[i].indexOf(",") >= 0) {
					Log.e("AWAWAWA----", app_mylist[i]);
					String[] NameAndTel = app_mylist[i].split(",");
					HashMap hash = new HashMap();
					hash.put("apkName", NameAndTel[0]);
					hash.put("imagePath", NameAndTel[1]);
					hash.put("downPath", NameAndTel[2]);
					hash.put("packageName", NameAndTel[3]);
					hash.put("ActivityName", NameAndTel[4]);
					listData.add(hash);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Toast("网络超时", 1000);
		}

		HashMap hashl = new HashMap();
		hashl.put("apkName", "添加应用");
		hashl.put("imagePath",
				"http://www.36936.com/WorldClient/android/app.gif");
		listData.add(hashl);
		return listData;
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) { // resultCode为回传的标记，我在B中回传的是RESULT_OK
		case RESULT_OK:
			initAppDate();
			break;
		default:
			break;
		}
	}

	private void getAllApp() {
		String result = "";
		List<PackageInfo> packages = getPackageManager()
				.getInstalledPackages(0);
		for (PackageInfo i : packages) {
			if ((i.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
				result += i.applicationInfo.loadLabel(getPackageManager())
						.toString() + "+";
			}
		}

		MySharedData.sharedata_WriteString(App_Main.this, "app_all",
				result.substring(0, result.length() - 1));
	}
}
