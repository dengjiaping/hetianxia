package com.htx.user;

import org.json.JSONObject;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.hetianxia.activity.R;
import com.htx.conn.Const;
import com.htx.conn.HttpHelper;
import com.htx.conn.IsNetwork;
import com.htx.main.TabTest;
import com.htx.pub.ApplicationUser;
import com.htx.pub.AsyncImageLoader;
import com.htx.pub.LoadingDialog;
import com.htx.pub.MySharedData;
import com.htx.pub.PubActivity;
import com.htx.search.SiteHelper;

public class U_info extends PubActivity {

	// http://www.36936.com/WorldClient/ClientGetUserInfo.ashx?userid=100101&hash=429C9326A319DC9DCB1CDC5CB81B471C
	private Button bt;
	private TextView UU_eName, UU_username, UU_userTypeName, UU_qq, UU_userSex,
			UU_jy, UU_mobile, UU_IDc, UU_id, UU_points,UU_money,UU_sCurrent,UU_mCurrent,UU_qCurrent,UU_email,UU_add;
	private ImageView UU_Avatar, UU_empImg;
	private Button mysc_goback, btbt;

    private View infor_quanyiv,infor_xianjinv,infor_guquanv;
    private RelativeLayout infor_quanyi,infor_xianjin,infor_guquan;
    
	private AsyncImageLoader asyImg = new AsyncImageLoader();
	private LoadingDialog progressDialog;
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				progressDialog.dismiss();
				init();
				break;
			}
			super.handleMessage(msg);
		}
	};

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_infor);

		mysc_goback = (Button) findViewById(R.id.mysc_goback);
		btbt = (Button) findViewById(R.id.btbt);

		bt = (Button) findViewById(R.id.button1);

		infor_guquan = (RelativeLayout)findViewById(R.id.infor_guquan);
		infor_guquanv = (View)findViewById(R.id.infor_guquanv);
		infor_xianjin = (RelativeLayout)findViewById(R.id.infor_xianjin);
		infor_xianjinv = (View)findViewById(R.id.infor_xianjinv);
		infor_quanyi = (RelativeLayout)findViewById(R.id.infor_quanyi);
		infor_quanyiv = (View)findViewById(R.id.infor_quanyiv);
		mysc_goback.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				finish();
			}
		});

		btbt.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				ApplicationUser.SetUserId(null);
				ApplicationUser.SetUserName("");
				MySharedData.sharedata_WriteString(U_info.this, "userType", "");
				MySharedData.sharedata_WriteString(U_info.this, "loginName", "");
				MySharedData.sharedata_WriteString(U_info.this, "passWord", "");
				MySharedData.sharedata_WriteString(U_info.this, "userName", "");
				MySharedData.sharedata_WriteString(U_info.this, "UserId", "");
				startActivity(new Intent(U_info.this, TabTest.class));
				finish();
			}
		});

		if (IsNetwork.isNetworkEnabled(U_info.this) == 1
				|| IsNetwork.isNetworkEnabled(U_info.this) == 0) {
			progressDialog = new LoadingDialog(U_info.this);
			progressDialog.show();
			new Thread(new Runnable() {
				public void run() {
					// 加载数据
					getData(U_info.this);
				}
			}).start();
		} else {
			Toast("网络异常", 1000);
		}
		// 修改
		bt.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				if (IsNetwork.isNetworkEnabled(U_info.this) == 1
						|| IsNetwork.isNetworkEnabled(U_info.this) == 0) {
					Intent intent = new Intent(U_info.this,
							User_NewinfoActivity.class);
					startActivityForResult(intent, 1);
				} else {
					Toast("网络不可用", 1000);
				}
			}
		});

	}

	private void init() {

		UU_id = (TextView) findViewById(R.id.UU_id);
		UU_id.setText(MySharedData.sharedata_ReadString(U_info.this, "UU_eName"));
		UU_eName = (TextView) findViewById(R.id.UU_eName);
		UU_eName.setText(MySharedData.sharedata_ReadString(U_info.this,
				"UU_eName"));
		UU_username = (TextView) findViewById(R.id.UU_username);
		UU_username.setText(MySharedData.sharedata_ReadString(U_info.this,
				"UU_username"));
		UU_userTypeName = (TextView) findViewById(R.id.UU_userTypeName);
		UU_userTypeName.setText(MySharedData.sharedata_ReadString(U_info.this,
				"UU_userTypeName"));
		UU_qq = (TextView) findViewById(R.id.UU_qq);
		UU_qq.setText(MySharedData.sharedata_ReadString(U_info.this, "UU_qq"));
		UU_userSex = (TextView) findViewById(R.id.UU_userSex);
		UU_userSex.setText(MySharedData.sharedata_ReadString(U_info.this,
				"UU_userSex"));
		UU_mobile = (TextView) findViewById(R.id.UU_mobile);
		UU_mobile.setText(MySharedData.sharedata_ReadString(U_info.this,
				"UU_mobile"));
		UU_IDc = (TextView) findViewById(R.id.UU_IDc);
		UU_IDc.setText(MySharedData.sharedata_ReadString(U_info.this, "UU_IDc"));
		UU_jy = (TextView) findViewById(R.id.UU_jy);
		UU_jy.setText(MySharedData.sharedata_ReadString(U_info.this, "UU_jy")
				+ "分");
		UU_points = (TextView) findViewById(R.id.UU_points);
		UU_points.setText(MySharedData.sharedata_ReadString(U_info.this,
				"UU_points") + "分");


		UU_money = (TextView) findViewById(R.id.UU_money);
		UU_money.setText(MySharedData.sharedata_ReadString(U_info.this, "UU_money")+"元");
		UU_sCurrent = (TextView) findViewById(R.id.UU_sCurrent);
		UU_sCurrent.setText(MySharedData.sharedata_ReadString(U_info.this, "UU_sCurrent")+"股");
		UU_mCurrent = (TextView) findViewById(R.id.UU_mCurrent);
		UU_mCurrent.setText(MySharedData.sharedata_ReadString(U_info.this, "UU_mCurrent"));
		UU_qCurrent = (TextView) findViewById(R.id.UU_qCurrent);
		UU_qCurrent.setText(MySharedData.sharedata_ReadString(U_info.this, "UU_qCurrent"));
		UU_email = (TextView) findViewById(R.id.UU_email);
		UU_email.setText(MySharedData.sharedata_ReadString(U_info.this, "UU_email"));
		UU_add = (TextView) findViewById(R.id.UU_add);
		UU_add.setText(MySharedData.sharedata_ReadString(U_info.this, "UU_add"));
		
		String fen = MySharedData.sharedata_ReadString(U_info.this,
				"status_");
		if (fen.equals("0")) {
			infor_guquan.setVisibility(View.GONE);
			infor_guquanv.setVisibility(View.GONE);
			infor_quanyi.setVisibility(View.GONE);
			infor_quanyiv.setVisibility(View.GONE);
			infor_xianjin.setVisibility(View.GONE);
			infor_xianjinv.setVisibility(View.GONE);
		}
		else {
			infor_guquan.setVisibility(View.VISIBLE);
			infor_guquanv.setVisibility(View.VISIBLE);
			infor_quanyi.setVisibility(View.VISIBLE);
			infor_quanyiv.setVisibility(View.VISIBLE);
			infor_xianjin.setVisibility(View.VISIBLE);
			infor_xianjinv.setVisibility(View.VISIBLE);
		}
		
		UU_Avatar = (ImageView) findViewById(R.id.UU_Avatar);
		if (IsNetwork.isNetworkEnabled(U_info.this) == 1
				|| IsNetwork.isNetworkEnabled(U_info.this) == 0) {
			if (!MySharedData.sharedata_ReadString(U_info.this, "UU_Avatar")
					.equals("")) {
				asyImg.LoadImage(MySharedData.sharedata_ReadString(U_info.this,
						"UU_Avatar"), UU_Avatar);
			}
			UU_empImg = (ImageView) findViewById(R.id.UU_empImg);
			if (!MySharedData.sharedata_ReadString(U_info.this, "UU_empImg")
					.equals("")) {
				asyImg.LoadImage(MySharedData.sharedata_ReadString(U_info.this,
						"UU_empImg"), UU_empImg);
			}
		}

		UU_Avatar.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				if (IsNetwork.isNetworkEnabled(U_info.this) == 1
						|| IsNetwork.isNetworkEnabled(U_info.this) == 0) {
					Intent intent = new Intent(U_info.this,
							User_NewinfoActivity.class);
					startActivityForResult(intent, 1);
				} else {
					Toast("网络不可用", 1000);
				}
			}
		});
	}

	/**
	 * 获得数据
	 * 
	 * @param context
	 */
	public void getData(Context context) {

		String adUserId = MySharedData.sharedata_ReadString(context, "UserId");
		String hash = SiteHelper.MD5(adUserId + Const.UrlHashKey);

		String url = "http://api.36936.com/ClientApi/ClientGetUserInfo.ashx?"
				+ "userid=" + adUserId + "&hash=" + hash;

		try {
			String reStr = HttpHelper.getJsonData(context, url);
			JSONObject reObject = new JSONObject(reStr);

			if (reObject.getInt("status") == 1) {

				if (reObject.has("eName")) {
					MySharedData.sharedata_WriteString(context, "UU_eName",
							reObject.getString("eName"));
				} else {
					MySharedData.sharedata_WriteString(context, "UU_eName", "");
				}
				if (reObject.has("points")) {
					MySharedData.sharedata_WriteString(context, "UU_points",
							reObject.getString("points"));
				} else {
					MySharedData
							.sharedata_WriteString(context, "UU_points", "");
				}
				if (reObject.has("username")) {
					MySharedData.sharedata_WriteString(context, "UU_username",
							reObject.getString("username"));
				} else {
					MySharedData.sharedata_WriteString(context, "UU_username",
							"");
				}
				if (reObject.has("isBindMobile")) {
					MySharedData.sharedata_WriteString(context, "isBindMobile",
							"");
				}
				if (reObject.has("userTypeName")) {
					MySharedData.sharedata_WriteString(context,
							"UU_userTypeName",
							reObject.getString("userTypeName"));
				} else {
					MySharedData.sharedata_WriteString(context,
							"UU_userTypeName", "");
				}
				if (reObject.has("qq")) {
					MySharedData.sharedata_WriteString(context, "UU_qq",
							reObject.getString("qq"));
				} else {
					MySharedData.sharedata_WriteString(context, "UU_qq", "");
				}

				if (reObject.has("userSex")) {
					MySharedData.sharedata_WriteString(context, "UU_userSex",
							reObject.getString("userSex"));
				} else {
					MySharedData.sharedata_WriteString(context, "UU_userSex",
							"");
				}
				if (reObject.has("mobile")) {
					MySharedData.sharedata_WriteString(context, "UU_mobile",
							reObject.getString("mobile"));
				} else {
					MySharedData
							.sharedata_WriteString(context, "UU_mobile", "");
				}
				if (reObject.has("IDc")) {
					MySharedData.sharedata_WriteString(context, "UU_IDc",
							reObject.getString("IDc"));
				} else {
					MySharedData.sharedata_WriteString(context, "UU_IDc", "");
				}
				if (reObject.has("Avatar")) {
					MySharedData.sharedata_WriteString(context, "UU_Avatar",
							reObject.getString("Avatar"));
				} else {
					MySharedData
							.sharedata_WriteString(context, "UU_Avatar", "");
				}
				if (reObject.has("empImg")) {
					MySharedData.sharedata_WriteString(context, "UU_empImg",
							reObject.getString("empImg"));
				} else {
					MySharedData
							.sharedata_WriteString(context, "UU_empImg", "");
				}
				if (reObject.has("emppv")) {
					MySharedData.sharedata_WriteString(context, "UU_jy",
							reObject.getString("emppv"));
				} else {
					MySharedData.sharedata_WriteString(context, "UU_jy", "");
				}
				if (reObject.has("email")) {
					MySharedData.sharedata_WriteString(context, "UU_email",
							reObject.getString("email"));
				} else {
					MySharedData.sharedata_WriteString(context, "UU_email", "");
				}
				if (reObject.has("address")) {
					MySharedData.sharedata_WriteString(context, "UU_add",
							reObject.getString("address"));
				} else {
					MySharedData.sharedata_WriteString(context, "UU_add", "");
				}
				if (reObject.has("mCurrent")) {
					MySharedData.sharedata_WriteString(context, "UU_mCurrent",
							reObject.getString("mCurrent"));
				} else {
					MySharedData.sharedata_WriteString(context, "UU_mCurrent", "");
				}
				if (reObject.has("sCurrent")) {
					MySharedData.sharedata_WriteString(context, "UU_sCurrent",
							reObject.getString("sCurrent"));
				} else {
					MySharedData.sharedata_WriteString(context, "UU_sCurrent", "");
				}
				if (reObject.has("qCurrent")) {
					MySharedData.sharedata_WriteString(context, "UU_qCurrent",
							reObject.getString("qCurrent"));
				} else {
					MySharedData.sharedata_WriteString(context, "UU_qCurrent", "");
				}
				if (reObject.has("money")) {
					MySharedData.sharedata_WriteString(context, "UU_money",
							reObject.getString("money"));
				} else {
					MySharedData.sharedata_WriteString(context, "UU_money", "");
				}

				Message message = new Message();
				message.what = 1;
				mHandler.sendMessage(message);
			} else {
				Message message = new Message();
				message.what = 1;
				mHandler.sendMessage(message);
			}

		} catch (Exception e) {
			Message message = new Message();
			message.what = 1;
			mHandler.sendMessage(message);
			e.printStackTrace();
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		switch (requestCode) {
		case 1:
			if (IsNetwork.isNetworkEnabled(U_info.this) == 1
					|| IsNetwork.isNetworkEnabled(U_info.this) == 0) {
				progressDialog = new LoadingDialog(U_info.this);
				progressDialog.show();
				new Thread(new Runnable() {
					public void run() {
						// 加载数据
						getData(U_info.this);
					}
				}).start();
			}
			break;
		default:
			break;

		}
		super.onActivityResult(requestCode, resultCode, data);

	}

}
