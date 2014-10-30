package com.htx.user;

import org.json.JSONObject;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import com.hetianxia.activity.R;
import com.htx.conn.Const;
import com.htx.conn.HttpHelper;
import com.htx.pub.MySharedData;
import com.htx.pub.MyToast;
import com.htx.pub.PubActivity;
import com.htx.search.SiteHelper;

public class U_toSpeak extends PubActivity {

	static EditText editText1;
	static EditText editText2;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.u_to_speak);

		editText1 = (EditText) findViewById(R.id.editText1);
//		editText2 = (EditText) findViewById(R.id.editText2);

		Button button1 = (Button) findViewById(R.id.button1);
		Button bbt = (Button) findViewById(R.id.bbt);

		bbt.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				getData(U_toSpeak.this);
			}
		});

		button1.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				finish();
			}
		});
	}

	/**
	 * 
	 * @param url
	 */
	public static void getData(Context context) {

		String adUserId = MySharedData.sharedata_ReadString(context, "UserId");
		String hash = SiteHelper.MD5(adUserId + Const.UrlHashKey);
		String content = editText1.getText().toString();
		String url = "http://api.36936.com/ClientApi/ClientAddMobAppOpinion.ashx?&userId="
				+ adUserId + "&hash=" + hash + "&content=" + content;

		try {
			String reStr = HttpHelper.getJsonData(context, url);
			JSONObject reObject = new JSONObject(reStr);
			
			if (reObject.getInt("status") == 1) {
				((Activity) context).finish();
				MyToast.toast(context, "提交成功！", 1000);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
