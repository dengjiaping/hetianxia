package com.htx.user;

import org.json.JSONObject;
import com.hetianxia.activity.R;
import com.htx.conn.Const;
import com.htx.pub.MyToast;
import com.htx.pub.PubActivity;
import com.htx.conn.HttpHelper;
import com.htx.search.SiteHelper;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * 
 * @author lvan
 *
 */
public class U_MissPassword extends PubActivity {

	private EditText name_et, email_et;
	private Button button_ok;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		HideStatusBar();
		setContentView(R.layout.use_miss_password);

		name_et = (EditText) findViewById(R.id.name_et);
		email_et = (EditText) findViewById(R.id.email_et);
		button_ok = (Button) findViewById(R.id.button_ok);
		
		button_ok.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
//				if (name_et.getText().toString().equals("")) {
//					Spanned html = Html.fromHtml("<font color='blue'>请先输入会员号！</font>");
//					name_et.setError(html);
//				} else if (name_et.getText().toString().length() < 5
//						|| name_et.getText().toString().length() > 18) {
//					Spanned html = Html.fromHtml("<font color='blue'>请先输入正确的会员号！</font>");
//					name_et.setError(html);
//				} else 
				if (email_et.getText().toString().equals("")) {
					Spanned html = Html.fromHtml("<font color='blue'>请先输入您的电话号码！</font>");
					email_et.setError(html);
				} else if (!checkPhone(email_et.getText().toString())) {
					Spanned html = Html.fromHtml("<font color='blue'>请先输入正确的电话号码！</font>");
					email_et.setError(html);
				} else {
					urlEmail(name_et.getText().toString(), email_et.getText()
							.toString());
				}
			}
		});
	}

	public static boolean checkPhone(String email) {
		String format = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
		if (email.matches(format)) {
			return true;// 名合法，返回true
		} else {
			return false;// 名不合法，返回false
		}
	}

	public void urlEmail(String name, String email) {

		String hash = SiteHelper.MD5(email + Const.UrlHashKey);
		try {
			String url = "http://api.36936.com/ClientApi/ClientReplyUserPass.ashx?";
			url += ("userid=" + name + "&phoneNo=" + email+"&hash="+hash+"&ism=true");
			String reStr = HttpHelper.getJsonData(this,url);
			JSONObject reObject = new JSONObject(reStr);
			if (reObject.getInt("status") == 1) {
				MyToast.toast(U_MissPassword.this, "新密码已发送，请注意查收短信。",1500);
				finish();
			} else
				Toast(reObject.getString("msg"),1500);
		} catch (Exception e) {
			Toast("网络异常，请重试！",1500);
		}
	}

	// 隐藏标题栏
	private void HideStatusBar() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
		Window myWindow = this.getWindow();
		myWindow.setFlags(flag, flag);
	}
}