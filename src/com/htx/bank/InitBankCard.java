package com.htx.bank;

import org.json.JSONObject;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import com.hetianxia.activity.R;
import com.htx.conn.Const;
import com.htx.conn.HttpHelper;
import com.htx.pub.LoadingDialog;
import com.htx.pub.MySharedData;
import com.htx.pub.MyToast;
import com.htx.pub.PubActivity;
import com.htx.search.SiteHelper;

public class InitBankCard extends PubActivity{

	
	private LoadingDialog dialoga;
	private String string = "";
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				dialoga.dismiss();
				startActivity(new Intent(InitBankCard.this, AddBank.class));
				finish();
				break;
			case 2:
				dialoga.dismiss();
				MyToast.toast(InitBankCard.this, string,1000);
				break;
			}
			super.handleMessage(msg);
		}
	};
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.initbankcard);
		
		final EditText et =(EditText)findViewById(R.id.et);
		et.setText(MySharedData.sharedata_ReadString(InitBankCard.this,
				"UU_mobile"));
		Button bt =(Button)findViewById(R.id.bt);
		Button fanhui =(Button)findViewById(R.id.fanhui);
		fanhui.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
		dialoga = new LoadingDialog(InitBankCard.this);
		bt.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				dialoga.show();
				new Thread(new Runnable() {
					public void run() {
						getDate(et.getText().toString());
					}
				}).start();
			}
		});
	}
	
	private void getDate(String str) {
		String user = MySharedData.sharedata_ReadString(InitBankCard.this, "UserId");
		String hash = SiteHelper.MD5(user + Const.UrlHashKey).toLowerCase();
		String url ="http://api.36936.com/ClientApi/Pos/ClientBindBank.ashx?userId=" + user + "&hash=" + hash
				+ "&type=info&Mobile=" + str ;
		
		Message message = new Message();
		try {
			String reStr = HttpHelper.getJsonData(InitBankCard.this, url);
			JSONObject reObject = new JSONObject(reStr);
			if (reObject.getInt("status") == 1) {
				message.what = 1;
			} else {
				message.what = 2;
				string = reObject.getString("msg");
			}
		} catch (Exception e) {
			e.printStackTrace();
			message.what = 2;
			string = "网络异常！";
		}
		mHandler.sendMessage(message);
	}
}
