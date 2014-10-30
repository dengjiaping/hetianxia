package com.htx.user;

import java.net.URLEncoder;
import java.security.MessageDigest;
import org.json.JSONObject;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.InputType;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import com.hetianxia.activity.R;
import com.htx.conn.Const;
import com.htx.conn.IsNetwork;
import com.htx.main.TabTest;
import com.htx.pub.ApplicationUser;
import com.htx.pub.File_Manage;
import com.htx.pub.Integral;
import com.htx.pub.LoadingDialog;
import com.htx.pub.MySharedData;
import com.htx.pub.PubActivity;
import com.htx.search.ApplicationActivity;
import com.htx.conn.HttpHelper;
import com.htx.search.SiteHelper;

/**
 * 
 * @author lvan
 * 
 */
public class U_Login extends PubActivity {

	private EditText tv_username, tv_password;
	private TextView toptv;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_login);

		toptv = (TextView) findViewById(R.id.toptv);
		tv_username = (EditText) findViewById(R.id.username_ed);
		tv_password = (EditText) findViewById(R.id.password_ed);

		Intent intent = getIntent();

		String ifAutoEnter = MySharedData.sharedata_ReadString(this,
				"cb_ifAutoEnter");
		String loginName = MySharedData.sharedata_ReadString(this, "loginName");
		String UserId = MySharedData.sharedata_ReadString(this, "UserId");
		String passWord = MySharedData.sharedata_ReadString(this, "passWord");
		String userName = MySharedData.sharedata_ReadString(this, "userName");
		ApplicationUser.SetUserId(UserId);

		if (intent.getIntExtra("toptv", 0) == 1) {
			toptv.setText("切换用户");
		}

		if (intent.getIntExtra("exit", 0) == 0) {
			if (ifAutoEnter.equals("t") && !UserId.equals("")) {
				ApplicationUser.SetUserName(userName);
				startActivity(new Intent(U_Login.this, TabTest.class));
				this.finish();
			}
		} else if (intent.getIntExtra("exit", 0) == 2) {
			ApplicationUser.SetUserName(userName);
			tv_username.setText(UserId);
			tv_password.setText(passWord);
			login();
		} else {
			tv_username.setText(loginName);
			tv_password.setText(passWord);
		}

		CheckBox cb_ifshowpassword = (CheckBox) findViewById(R.id.ifShowPassword);
		cb_ifshowpassword
				.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {

					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {

						EditText passwordEditText = (EditText) findViewById(R.id.password_ed);

						if (isChecked) {// 如果选中是显示密码
							passwordEditText
									.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
						} else {
							passwordEditText
									.setInputType(InputType.TYPE_CLASS_TEXT
											| InputType.TYPE_TEXT_VARIATION_PASSWORD);
						}
					}
				});

		// 忘记密码
		Button btnWangmima = (Button) findViewById(R.id.btnWangmima);
		btnWangmima.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 保存userid到变量，打电话看广告要用
				MySharedData
						.sharedata_WriteString(U_Login.this, "userName", "");
				MySharedData.sharedata_WriteString(U_Login.this, "UserId", "");
				Intent intent = new Intent(U_Login.this, U_MissPassword.class);
				startActivity(intent);
			}
		});

		// 注册
		Button btnZhuche = (Button) findViewById(R.id.btnZhuche);
		btnZhuche.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(U_Login.this,
						U_Register.class);
				Log.e("AA", "||------------>>>>    1");
				startActivity(intent);
				finish();
			}
		});

		Button btnLogin = (Button) findViewById(R.id.btnLogin);
		btnLogin.setOnClickListener(new MyButtonListener());
	}

	// 设置三种类型参数分别为String,Integer,String
	class PageTask extends AsyncTask<String, Integer, String> {
		// 可变长的输入参数，与AsyncTask.exucute()对应
		LoadingDialog dialog = new LoadingDialog(U_Login.this);

		public PageTask(Context context) {
			// 首次加载提示加载中
			dialog.show();
		}

		protected String doInBackground(String... params) {
			String reStr = "";
			try {
				reStr = HttpHelper.getJsonData(U_Login.this, params[0]);

			} catch (Exception e) {
				e.printStackTrace();

			}

			return reStr;
		}

		protected void onCancelled() {
			super.onCancelled();
		}

		protected void onPostExecute(String result) {

			// 返回HTML页面的内容
			dialog.dismiss();
			try {
				JSONObject reObject = new JSONObject(result);

				if (reObject.getInt("status") == 0) {
					Toast(reObject.getString("msg"), 1000);
					return;
				} else if (reObject.getInt("status") == 1) {

					MySharedData.sharedata_WriteString(U_Login.this,
							"use", tv_username.getText().toString());
					
					String userid = reObject.getString("userId");
					Integral.getIntegralData(U_Login.this, 2, true, userid);
					
					//建立用户聊天记录文件
					File_Manage.mkfile(userid, U_Login.this,"0");
					
					// 用户类型
					String userType = reObject.getString("userType");
					MySharedData.sharedata_WriteString(U_Login.this,
							"userType", userType);

					// 保存推广短连接
					MySharedData.sharedata_WriteString(U_Login.this,
							"spreadUrl", reObject.getString("spreadUrl"));

					ApplicationUser.SetUserId(userid);
					String userName = reObject.getString("userName");
					
					ApplicationUser.SetUserName(userName);
					if (ApplicationActivity.getInstance().ReturnActivityCheck()) {
						ApplicationActivity.getInstance().ReturnActivityClear();
						U_Login.this.finish();
					} else {
						// 保存userid到变量，打电话看广告要用
						MySharedData.sharedata_WriteString(U_Login.this,
								"userName", userName);
						MySharedData.sharedata_WriteString(U_Login.this,
								"UserId", userid);
						Intent intent = new Intent(U_Login.this,TabTest.class);
						startActivity(intent);
						finish();
					}
				} else {
					Toast("网络不给力！", 1000);
				}
			} catch (Exception e) {
				Toast("网络不给力！", 1000);
				e.printStackTrace();
			}

		}

		protected void onPreExecute() {
			// 任务启动，可以在这里显示一个对话框，这里简单处理
			// message.setText(R.string.task_started);

		}

		protected void onProgressUpdate(Integer... values) {
			// 更新进度
			// message.setText(values[0]);
		}
	}

	class MyButtonListener implements OnClickListener {

		public void onClick(View v) {
			
			login();
		}
	}


	public void login() {
		tv_username = (EditText) findViewById(R.id.username_ed);
		tv_password = (EditText) findViewById(R.id.password_ed);
		String username = tv_username.getText() + "";
		String password = tv_password.getText() + "";
		
		if (username.length() == 0) {
			Spanned html = Html.fromHtml("<font color='blue'>用户名不能为空！</font>");
			tv_username.setError(html);

			return;
		}
		if (password.length() == 0) {
			Spanned html = Html.fromHtml("<font color='blue'>用户名不能为空！</font>");
			tv_password.setError(html);
			return;
		}

		CheckBox cb_ifAutoEnter = (CheckBox) findViewById(R.id.ifAutoEnter);
		if (cb_ifAutoEnter.isChecked()) {// 如果选中是记住账号
			MySharedData.sharedata_WriteString(U_Login.this, "loginName",
					username);
			MySharedData.sharedata_WriteString(U_Login.this, "passWord",
					password);
			MySharedData.sharedata_WriteString(U_Login.this,
					"cb_ifAutoEnter", "t");
		} else {
			MySharedData.sharedata_WriteString(U_Login.this, "loginName",
					"");
			MySharedData
					.sharedata_WriteString(U_Login.this, "passWord", "");
			MySharedData.sharedata_WriteString(U_Login.this,
					"cb_ifAutoEnter", "");

		}

		/*
		 * Login.this.userName = username; Login.this.passWord = password;
		 */
		username = URLEncoder.encode(username).toUpperCase(); // 这里的编码是为了防止用户名是汉字登录出错的情况
		password = SiteHelper.MD5(password).toLowerCase();
		String hash = MD5(username + Const.UrlHashKey)
				.toLowerCase();
		String url = Const.UrlLogin + "?loginName=" + username
				+ "&passWord=" + password + "&hash=" + hash;
		if (IsNetwork.isNetworkEnabled(U_Login.this) == 0
				|| IsNetwork.isNetworkEnabled(U_Login.this) == 1) {
			PageTask task = new PageTask(U_Login.this);
			task.execute(url);
		} else {
			Toast("网络不可用！", 1000);
		}
	}

	
	//MD5加密，32位  
    public static String MD5(String str)  
    {  
        MessageDigest md5 = null;  
        try  
        {  
            md5 = MessageDigest.getInstance("MD5"); 
        }catch(Exception e)  
        {  
            e.printStackTrace();  
            return "";  
        }  
          
        char[] charArray = str.toCharArray();  
        byte[] byteArray = new byte[charArray.length];  
          
        for(int i = 0; i < charArray.length; i++)  
        {  
            byteArray[i] = (byte)charArray[i];  
        }  
        byte[] md5Bytes = md5.digest(byteArray);  
          
        StringBuffer hexValue = new StringBuffer();  
        for( int i = 0; i < md5Bytes.length; i++)  
        {  
            int val = ((int)md5Bytes[i])&0xff;  
            if(val < 16)  
            {  
                hexValue.append("0");  
            }  
            hexValue.append(Integer.toHexString(val));  
        }  
        return hexValue.toString();  
    }
	
	
}
