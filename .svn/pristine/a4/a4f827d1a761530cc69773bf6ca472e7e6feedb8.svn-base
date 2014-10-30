package com.htx.user;

import java.net.URLEncoder;
import java.security.MessageDigest;

import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import android.widget.Toast;

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
public class U_Baoxian extends PubActivity {

	private EditText tv_username, tv_phone,tv_cid;
	private TextView toptv;
	private String userid_str="";
	private String warning_str = "";

	private LoadingDialog progressDialog;
	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				progressDialog.dismiss();
				Toast.makeText(U_Baoxian.this, "成功领取保险！！", 3000).show();
				finish();
				break;
			case 2:
				progressDialog.dismiss();
				Toast.makeText(U_Baoxian.this, warning_str, 3000).show();
				
				break;
			case 3:
				progressDialog.dismiss();
				Toast.makeText(U_Baoxian.this, "网络连接失败！", 3000).show();
				break;
			}
			super.handleMessage(msg);
		}
	};
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_baoxian);

		toptv = (TextView) findViewById(R.id.toptv);
		tv_username = (EditText) findViewById(R.id.username_ed);
		tv_phone = (EditText) findViewById(R.id.phone_ed);
		tv_cid = (EditText)findViewById(R.id.Cid_ed);
		
		Button btnLogin = (Button) findViewById(R.id.btnLogin);
		btnLogin.setOnClickListener(new MyButtonListener());
	}

	class MyButtonListener implements OnClickListener {

		public void onClick(View v) {

			progressDialog = new LoadingDialog(U_Baoxian.this);
			progressDialog.show();
			new Thread(new Runnable() {
				public void run() {
					// 加载数据
					login();
				}
			}).start();
		}
	}


	public void login() {
		
		String username = tv_username.getText() + "";
		String phone_str = tv_phone.getText() + "";
		String cid_str = tv_cid.getText() + "";
		
		if (username.length() == 0) {
			Spanned html = Html.fromHtml("<font color='blue'>真实姓名不能为空！</font>");
			tv_username.setError(html);

			return;
		}
		if (cid_str.length() == 0) {
			Spanned html = Html.fromHtml("<font color='blue'>身份证号不能为空！</font>");
			tv_cid.setError(html);
			return;
		}
		if (phone_str.length() == 0) {
			Spanned html = Html.fromHtml("<font color='blue'>手机号不能为空！</font>");
			tv_phone.setError(html);
			return;
		}

		
		/*
		 * Login.this.userName = username; Login.this.passWord = password;
		 */
		username = URLEncoder.encode(username).toUpperCase(); // 这里的编码是为了防止用户名是汉字登录出错的情况

		userid_str = MySharedData.sharedata_ReadString(U_Baoxian.this, "UserId");
		String hash = MD5(userid_str + Const.UrlHashKey)
				.toLowerCase();
		String url = "http://api.36936.com/ClientApi/ClientaddSafe.ashx?";
		url = url+"userId="+userid_str+"&userName="+username+"&idCard="+cid_str+"&mobile="+phone_str+"&hash="+hash;
		if (IsNetwork.isNetworkEnabled(U_Baoxian.this) == 0
				|| IsNetwork.isNetworkEnabled(U_Baoxian.this) == 1) {
			
			String reStr = HttpHelper.getJsonData(U_Baoxian.this, url);
			try {
				JSONObject reObject = new JSONObject(reStr);
				warning_str = reObject.getString("msg");
				if (reObject.getInt("status") == 1) {
					
					Message message = new Message();
					message.what = 1;
					mHandler.sendMessage(message);
					
				} else {

					Message message = new Message();
					message.what = 2;
					mHandler.sendMessage(message);
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				Message message = new Message();
				message.what = 2;
				mHandler.sendMessage(message);
				e.printStackTrace();
			}

			
		} else {
			Message message = new Message();
			message.what = 3;
			mHandler.sendMessage(message);
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
