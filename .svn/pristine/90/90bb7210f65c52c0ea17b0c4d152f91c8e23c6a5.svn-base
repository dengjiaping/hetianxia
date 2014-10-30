package com.htx.bank;

import org.json.JSONObject;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.Html;
import android.text.Selection;
import android.text.Spanned;
import android.text.TextWatcher;
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

public class AddBank extends PubActivity {

	private LoadingDialog dialoga;
	private EditText et;
	private String string = "";
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				dialoga.dismiss();
				break;
			case 2:
				dialoga.dismiss();
				Spanned html = Html.fromHtml("<font color='blue'>" + string
						+ "</font>");
				et.setError(html);
				break;
			case 3:
				dialoga.dismiss();
				MyToast.toast(AddBank.this, string, 1000);
				break;
			}
			super.handleMessage(msg);
		}
	};

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addbank);
		dialoga = new LoadingDialog(AddBank.this);

		et = (EditText) findViewById(R.id.et);
		bankCardNumAddSpace(et);
		Button bt = (Button) findViewById(R.id.bt);
		Button fanhui = (Button) findViewById(R.id.fanhui);
		fanhui.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});

		bt.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (et.length() < 14 || et.length() > 26) {
					Spanned html = Html
							.fromHtml("<font color='blue'>银行卡填写有误，请核对！</font>");
					et.setError(html);
					return;
				}
				dialoga.show();
				new Thread(new Runnable() {
					public void run() {
						initData(et.getText().toString()); // 初始化数据
					}
				}).start();
			}
		});
	}

	private void initData(String et) {

		et = et.replaceAll(" ", "");
		String adUserId = MySharedData.sharedata_ReadString(AddBank.this,
				"UserId");
		String hash = SiteHelper.MD5(adUserId + Const.UrlHashKey);
		String url = "http://api.36936.com/ClientApi/Pos/ClientBindBank.ashx?"
				+ "userid="
				+ adUserId
				+ "&hash="
				+ hash
				+ "&type=bind&BankNo="
				+ et;

		Message message = new Message();
		try {
			String reStr = HttpHelper.getJsonData(AddBank.this, url);
			JSONObject reObject = new JSONObject(reStr);
			if (reObject.getInt("status") == 1) {
				message.what = 1;
				startActivity(new Intent(AddBank.this, BankList.class));
				finish();
			} else {
				message.what = 2;
				string = reObject.getString("msg");
			}
		} catch (Exception e) {
			message.what = 3;
			string = "网络异常";
			e.printStackTrace();
		}
		mHandler.sendMessage(message);
	}

	/**
	 * 银行卡四位加空格
	 * 
	 * @param mEditText
	 */
	protected void bankCardNumAddSpace(final EditText mEditText) {
		mEditText.addTextChangedListener(new TextWatcher() {
			int beforeTextLength = 0;
			int onTextLength = 0;
			boolean isChanged = false;

			int location = 0;// 记录光标的位置
			private char[] tempChar;
			private StringBuffer buffer = new StringBuffer();
			int konggeNumberB = 0;

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				beforeTextLength = s.length();
				if (buffer.length() > 0) {
					buffer.delete(0, buffer.length());
				}
				konggeNumberB = 0;
				for (int i = 0; i < s.length(); i++) {
					if (s.charAt(i) == ' ') {
						konggeNumberB++;
					}
				}
			}

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				onTextLength = s.length();
				buffer.append(s.toString());
				if (onTextLength == beforeTextLength || onTextLength <= 3
						|| isChanged) {
					isChanged = false;
					return;
				}
				isChanged = true;
			}

			public void afterTextChanged(Editable s) {
				if (isChanged) {
					location = mEditText.getSelectionEnd();
					int index = 0;
					while (index < buffer.length()) {
						if (buffer.charAt(index) == ' ') {
							buffer.deleteCharAt(index);
						} else {
							index++;
						}
					}

					index = 0;
					int konggeNumberC = 0;
					while (index < buffer.length()) {
						if ((index == 4 || index == 9 || index == 14 || index == 19)) {
							buffer.insert(index, ' ');
							konggeNumberC++;
						}
						index++;
					}

					if (konggeNumberC > konggeNumberB) {
						location += (konggeNumberC - konggeNumberB);
					}

					tempChar = new char[buffer.length()];
					buffer.getChars(0, buffer.length(), tempChar, 0);
					String str = buffer.toString();
					if (location > str.length()) {
						location = str.length();
					} else if (location < 0) {
						location = 0;
					}

					mEditText.setText(str);
					Editable etable = mEditText.getText();
					Selection.setSelection(etable, location);
					isChanged = false;
				}
			}
		});
	}

}
