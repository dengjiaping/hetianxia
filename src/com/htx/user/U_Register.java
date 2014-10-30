package com.htx.user;

import org.json.JSONObject;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hetianxia.activity.R;
import com.htx.conn.Const;
import com.htx.conn.HttpHelper;
import com.htx.pub.Integral;
import com.htx.pub.LoadingDialog;
import com.htx.pub.MySharedData;
import com.htx.pub.MyToast;
import com.htx.pub.PubActivity;
import com.htx.search.SiteHelper;

public class U_Register extends PubActivity {

	private EditText eT1, eT2, eT3;
	private Button Btn_ok, Btn_back;
	private LinearLayout l1, l2, View_yan;
	private View View_;
	private TextView yan;
	private String real = "false";
	private TimeCount time;
	private String et1, et2, str;
	private int l = 1;
	private LoadingDialog progressDialog;
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				l += 1;
				if (eT2.getText().toString().length() == 0) {
					Btn_ok.setText("完成");
				}
				l1.setVisibility(View.GONE);
				l2.setVisibility(View.VISIBLE);
				progressDialog.dismiss();
				break;
			case 2:
				progressDialog.dismiss();
				if (real.equals("false")) {
					View_yan.setVisibility(View.VISIBLE);
					View_.setVisibility(View.VISIBLE);
				}
				break;
			case 3:
				progressDialog.dismiss();
				break;
			case 4:
				MyToast.toast(U_Register.this, str, 1000);
				progressDialog.dismiss();
				break;
			case 5:
				progressDialog.dismiss();
				Spanned html =Html.fromHtml("");
				if (if_shu(str)) {
					html = Html.fromHtml("<font color='blue'>您的手机号已被会员号  "
							+ str + "  绑定</font>");
				}
				else {
					html = Html.fromHtml("<font color='blue'>  "
							+ str + "</font>");
				}
				eT1.setError(html);
				break;
			case 6:
				progressDialog.dismiss();
				html = Html.fromHtml("<font color='blue'> "
						+ str + " </font>");
				eT1.setError(html);
				break;
			}
		}
	};

	private boolean if_shu(String str)
	{
		 for (int i = str.length();--i>=0;){
			   if (!Character.isDigit(str.charAt(i))){
			    return false;
			   }
			  }
			  return true;

	}
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_register_);

		Btn_ok = (Button) findViewById(R.id.btn_ok);
		Btn_back = (Button) findViewById(R.id.btn_back);
		yan = (TextView) findViewById(R.id.yan);
		eT1 = (EditText) findViewById(R.id.et1);
		eT2 = (EditText) findViewById(R.id.et2);
		eT3 = (EditText) findViewById(R.id.et3);
		l1 = (LinearLayout) findViewById(R.id.lin1);
		l2 = (LinearLayout) findViewById(R.id.lin2);
		View_yan = (LinearLayout) findViewById(R.id.view_yan);
		View_ = (View) findViewById(R.id.view_);
		time = new TimeCount(60000, 1000); // 构造CountDownTimer对象
		progressDialog = new LoadingDialog(U_Register.this);
		
		// 获取手机号、手机串号信息
//		TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
//		if (tm != null) {
//			String tel = tm.getLine1Number();
//			phone = tel.replace("+86", "");
//			// eT1.setText(phone);
//		}

//		eT1.addTextChangedListener(new TextWatcher() {
//			public void onTextChanged(CharSequence s, int start, int before,
//					int count) {
//				if (phone.equals(eT1.getText().toString())) {
//					yan.setVisibility(View.GONE);
//				} else {
//					yan.setVisibility(View.VISIBLE);
//				}
//			}
//
//			public void beforeTextChanged(CharSequence s, int start, int count,
//					int after) {
//				if (phone.equals(eT1.getText().toString())) {
//					yan.setVisibility(View.GONE);
//				} else {
//					yan.setVisibility(View.VISIBLE);
//				}
//			}
//
//			public void afterTextChanged(Editable s) {
//				if (phone.equals(eT1.getText().toString())) {
//					yan.setVisibility(View.GONE);
//				} else {
//					yan.setVisibility(View.VISIBLE);
//				}
//			}
//		});

		Btn_back.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (l == 1) {
					Btn_ok.setText("下一步");
					finish();
				} else if (l == 2) {
					l -= 1;
					Btn_ok.setText("下一步");
					l1.setVisibility(View.VISIBLE);
					l2.setVisibility(View.GONE);
				}
			}
		});

		yan.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				et1 = eT1.getText().toString();
				if (et1.length() == 0) {
					Spanned html = Html
							.fromHtml("<font color='blue'>请先填写手机号码！</font>");
					eT1.setError(html);
					return;
				} else {
					if (!checkPhone(et1) || et1.equals("")) {
						Spanned html = Html
								.fromHtml("<font color='blue'>手机号码有误，请检查！</font>");
						eT1.setError(html);
					} else {
						progressDialog.show();
						new Thread(new Runnable() {
							public void run() {
//								if (phone.equals(et1)) {
//									real = "true";
//								}
								getDate1(et1, real);
								time.start();// 开始计时
							}
						}).start();
					}
				}
			}
		});

		Btn_ok.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (l == 1) {
					et1 = eT1.getText().toString();
					if (et1.length() == 0) {
						Spanned html = Html
								.fromHtml("<font color='blue'>请先填写手机号码！</font>");
						eT1.setError(html);
						return;
					} else if (!checkPhone(et1) || et1.equals("")) {
						Spanned html = Html
								.fromHtml("<font color='blue'>手机号码有误，请检查！</font>");
						eT1.setError(html);
					} else {
						Message message = new Message();
						if (real.equals("true")) {
							message.what = 1;
						} else {
							if (eT3.getText().length() == 6) {
								message.what = 1;
							} else {
								str = "请输入验证码！";
								message.what = 4;
							}
						}
						mHandler.sendMessage(message);
					}

				} else if (l == 2) {
					et2 = eT2.getText().toString();
					if (et2.length() < 6 || et2.length() > 20) {
						Spanned html = Html
								.fromHtml("<font color='blue'>密码在6-16位之间！</font>");
						eT2.setError(html);
					} else {
						progressDialog.show();
						new Thread(new Runnable() {
							public void run() {
								// 验证用户名是否可用
								getDate2(et2);
							}
						}).start();
					}
				}
			}
		});
	}

	private void getDate1(String et1, String isSelf) {
		Message message = new Message();
		try {
			String geturl = "http://api.36936.com/ClientApi/ClientRegCheckMobile.ashx?isSelf="
					+ isSelf + "&phoneNo=" + et1;
			String reStr = HttpHelper.getJsonData(U_Register.this, geturl);
			JSONObject reObject = new JSONObject(reStr);
			if (reObject.getInt("status") == 1) {
				message.what = 2;
			} else if (reObject.getInt("status") == 0) {
				message.what = 5;
				str = reObject.getString("msg");
			}
		} catch (Exception e) {
			message.what = 4;
			str = "网络异常！";
		}
		mHandler.sendMessage(message);
	}

	private void getDate2(String et2) {
		try {
			String hash = SiteHelper.MD5(et1 + Const.UrlHashKey);
			String geturl = null;
			String yanString = eT3.getText().toString();
//			if (yanString.equals("请输入验证码")) {
//				yanString = "";
//			}
			geturl = "http://api.36936.com/ClientApi/ClientMobReg.ashx?IntroId=10058699"
					+ "&userPass="
					+ et2
					+ "&phoneNo="
					+ et1
					+ "&mobCode="
					+ yanString + "&hash=" + hash;

			String reStr = HttpHelper.getJsonData(U_Register.this, geturl);
			JSONObject reObject = new JSONObject(reStr);
			if (reObject.getInt("status") == 1) {
				Integral.getIntegralData(U_Register.this, 1, true, reObject
						.getString("userId").toString());
				// 存储 默认用户信息
				MySharedData.sharedata_WriteString(U_Register.this, "userName",
						et1);
				MySharedData.sharedata_WriteString(U_Register.this, "passWord",
						et2);
				MySharedData.sharedata_WriteString(U_Register.this, "UserId",
						reObject.getString("userId").toString());
				Intent intent = new Intent();
				intent.putExtra("exit", 2);
				intent.putExtra("userid", reObject.getString("userId"));
				intent.putExtra("password", et2);
				intent.setClass(U_Register.this, U_Login.class);
				startActivity(intent);
				finish();
				Message message = new Message();
				message.what = 3;
				mHandler.sendMessage(message);
			} else {
				Message message = new Message();
				message.what = 4;
				str = reObject.getString("msg");
				mHandler.sendMessage(message);
			}
		} catch (Exception e) {
			Message message = new Message();
			message.what = 4;
			str = "网络异常！";
			mHandler.sendMessage(message);
		}
	}

	// 验证手机号码的正则表达式
	public static boolean checkPhone(String phone) {
		String format = "^((13[0-9])|(15[^4,\\D])|(18[0,2-9]))\\d{8}$";
		if (phone.matches(format)) {
			return true;// 名合法，返回true
		} else {
			return false;// 名不合法，返回false
		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) { // 按下的如果是BACK，同时没有重复
			if (l == 1) {
				Btn_ok.setText("下一步");
				finish();
			} else if (l == 2) {
				l -= 1;
				Btn_ok.setText("下一步");
				l1.setVisibility(View.VISIBLE);
				l2.setVisibility(View.GONE);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	/* 定义一个倒计时的内部类 */
	class TimeCount extends CountDownTimer {

		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
		}

		public void onFinish() {// 计时完毕时触发
			yan.setText("重新验证  ");
			yan.setClickable(true);
		}

		public void onTick(long millisUntilFinished) {// 计时过程显示
			yan.setClickable(false);
			yan.setText(millisUntilFinished / 1000 + "秒  ");
		}
	}
}
