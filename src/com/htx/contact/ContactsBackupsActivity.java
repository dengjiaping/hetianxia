package com.htx.contact;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.RawContacts;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.hetianxia.activity.R;
import com.htx.conn.Const;
import com.htx.pub.ApplicationUser;
import com.htx.pub.LoadingDialog;
import com.htx.pub.MySharedData;
import com.htx.pub.PubActivity;
import com.htx.conn.HttpHelper;
import com.htx.search.SiteHelper;

public class ContactsBackupsActivity extends PubActivity {

	private LinearLayout renewButton, btnyun;
	private LinearLayout backupButton;
	private TextView u_tv, l_tv, btn_qmsg;
	// 本地联系人字符串
	private String userListStr;
	// 本地联系人变化字符串
	private String userListStrb = "";
	private int a = 0;
	private int ai = 0;
	// 本地联系人个数
	private int b_a = 0;
	// 本地联系人变化个数
	private int b_b = 0;
	// 网络联系人个数
	private int i_a = 0;
	// 网络联系人变化个数
	private int i_b = 0;

	// 退出时，状态判断
	private int exit = 0;

	private LoadingDialog progressDialog;
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				if (i_a != -1) {
					u_tv.setText("网络联系人共有" + i_a + "人");
				} else {
					u_tv.setText("网络联系人共有(网络异常)人");
				}
				break;
			case 2:
				progressDialog.dismiss();
				break;
			case 3:
				yunAlertDialog();
				break;

			case 4:
				huiAlertDialog();
				break;

			case 5:
				l_tv.setText("本机联系人共有" + (b_a + b_b) + "人");
				break;
			case 6:
				u_tv.setText("网络联系人共有" + (i_b + i_a) + "人");
				break;
			case 7:
				progressDialog.tv(msg.getData().getString("text"));
				break;
			case 8:
				userListStr = MySharedData.sharedata_ReadString(
						ContactsBackupsActivity.this, "cc_str");
				b_a = MySharedData.sharedata_ReadInt(
						ContactsBackupsActivity.this, "cc_int");
				l_tv.setText("本机联系人共有" + b_a + "人");
				break;
			}
			super.handleMessage(msg);
		}
	};

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contacts_backup);

		btn_qmsg = (TextView) findViewById(R.id.btn_qmsg);
		u_tv = (TextView) findViewById(R.id.u);
		l_tv = (TextView) findViewById(R.id.l);

		// 恢复到本地
		renewButton = (LinearLayout) findViewById(R.id.btnRenew);
		// 备份到云端
		backupButton = (LinearLayout) findViewById(R.id.btnBackup);

		// 自动备份
		btnyun = (LinearLayout) findViewById(R.id.btnyun);
		init();

		btnyun.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (a % 2 == 0) {
					btn_qmsg.setBackgroundDrawable(getResources().getDrawable(
							R.drawable.btn_qmsg_close));
				} else {
					btn_qmsg.setBackgroundDrawable(getResources().getDrawable(
							R.drawable.btn_qmsg_open));
				}
				a += 1;
			}
		});

		// 监听备份到云端
		backupButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				AlertDialog.Builder b = new AlertDialog.Builder(
						ContactsBackupsActivity.this).setTitle("温馨提示")
						.setMessage("手机联系人即将备份到云端，在备份期间请勿取消备份，否则可能导致联系人数据丢失！");

				b.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								progressDialog.tv("正在备份到云端 …");
								progressDialog.show();
								new Thread(new Runnable() {
									public void run() {
										// "正在为您备份到云端，请稍后...");
										exit = -1;
										getPhoneContacts();
										exit = 0;
										Message message = new Message();
										message.what = 2;
										mHandler.sendMessage(message);
										Message message1 = new Message();
										message1.what = 3;
										mHandler.sendMessage(message1);
									}
								}).start();
							}
						});
				b.setNegativeButton("取消",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								// 取消按钮事件
							}
						}).show();

			}
		});

		// 监听恢复到本地
		renewButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				AlertDialog.Builder b = new AlertDialog.Builder(
						ContactsBackupsActivity.this).setTitle("温馨提示")
						.setMessage("云端联系人即将恢复到手机上，在恢复期间请勿取消，否则可能导致联系人数据丢失！");

				b.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								progressDialog.tv("正在恢复到本地 …");
								progressDialog.show();
								new Thread(new Runnable() {
									public void run() {
										// "正在为您备份到云端，请稍后...");
										exit = -1;
										getDataForHuifu();
										exit = 0;
										Message message = new Message();
										message.what = 2;
										mHandler.sendMessage(message);
										Message message1 = new Message();
										message1.what = 4;
										mHandler.sendMessage(message1);
									}
								}).start();
							}
						});
				b.setNegativeButton("取消",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								// 取消按钮事件
							}
						}).show();
			}
		});
	}

	/**
	 * 初始化
	 */
	private void init() {

		progressDialog = new LoadingDialog(ContactsBackupsActivity.this);
		progressDialog.show();
		new Thread(new Runnable() {
			public void run() {
				String hash = SiteHelper.MD5(ApplicationUser.GetUserId()
						+ Const.UrlHashKey);
				String urlq = Const.UrlContactsHuifuo + "?userid="
						+ ApplicationUser.GetUserId() + "&hash=" + hash;
				try {
					String reStr = HttpHelper.getJsonData(
							ContactsBackupsActivity.this, urlq);
					JSONObject reObject;
					reObject = new JSONObject(reStr);

					if (reObject.getInt("status") == 1) {
						i_a = Integer.parseInt(reObject.getString("msg"));
					}

				} catch (Exception e) {
					i_a = -1;
					e.printStackTrace();
				}

				Message message = new Message();
				message.what = 1;
				mHandler.sendMessage(message);
			}
		}).start();

		b_a = MySharedData.sharedata_ReadInt(ContactsBackupsActivity.this,
				"cc_int");
		l_tv.setText("本机联系人共有" + b_a + "人");
		userListStr = MySharedData.sharedata_ReadString(
				ContactsBackupsActivity.this, "cc_str");
		progressDialog.dismiss();
	}

	/**
	 * 备份数据
	 */
	private void getPhoneContacts() {
		String hash = SiteHelper.MD5(ApplicationUser.GetUserId()
				+ Const.UrlHashKey);
		String url = Const.UrlContactsUp;
		HttpPost httpRequest = new HttpPost(url);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("userid", ApplicationUser.GetUserId()
				.toString()));

		String userList = userListStr.replaceAll("\\+", "|");
		if (userList.indexOf("|") > -1) {
			userList = userList.substring(0, userList.length() - 1);
		}
		params.add(new BasicNameValuePair("userList", userList));
		params.add(new BasicNameValuePair("hash", hash));// gb2312 HTTP.UTF_8

		try {
			httpRequest.setEntity(new UrlEncodedFormEntity(params, "gb2312"));
			HttpResponse httpResponse = new DefaultHttpClient()
					.execute(httpRequest);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				String strResult = EntityUtils.toString(httpResponse
						.getEntity());

				JSONObject reObject = new JSONObject(strResult);
				if (reObject.getInt("status") == 0) {
					Toast(reObject.getString("msg"), 1000);
					return;
				} else {
					i_b = Integer.parseInt(reObject.getString("num"));
				}

			} else {
				Toast("网络异常！", 1000);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 恢复联系人
	 */
	private void getDataForHuifu() {

		// 先把所有人的联系方式保存起来，用来判断重复
		String hash = SiteHelper.MD5(ApplicationUser.GetUserId()
				+ Const.UrlHashKey);
		String url = Const.UrlContactsHuifu + "?userid="
				+ ApplicationUser.GetUserId() + "&hash=" + hash;
		// 这里需要分析服务器回传的json格式数据，
		try {
			String reStr = HttpHelper.getJsonData(this, url);
			JSONObject reObject = new JSONObject(reStr);
			if (reObject.getInt("status") == 0) {
				Toast(reObject.getString("msg"), 1000);
				return;
			}
			JSONArray jsonArray = reObject.getJSONArray("list");
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject2 = (JSONObject) jsonArray.opt(i);
				String userName = jsonObject2.getString("u");
				String mobile = jsonObject2.getString("m");
				if (userListStr.indexOf(userName + "," + mobile) == -1) { // 如果没有，才添加数据
					b_b += 1;
					insertContacts(userName, mobile);
					userListStrb += userName + "," + mobile + "+";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Toast("网络超时", 1000);
		}

	}

	/**
	 * 往手机中插入通讯录
	 * 
	 * @param name
	 * @param number
	 */
	private void insertContacts(String name, String number) {
		
		ContentResolver resolver = getContentResolver();// 得到content调用者
		ContentValues values = new ContentValues();
		Uri contentUri = resolver.insert(RawContacts.CONTENT_URI, values);
		long rawContactID = ContentUris.parseId(contentUri);// 解析到插入下一条的ID
		// 每一步需要完成什么事情
		if (!name.equals("")) {
			values.clear();
			values.put(android.provider.ContactsContract.Data.RAW_CONTACT_ID,
					rawContactID);// 确动哪个联系人
			values.put(android.provider.ContactsContract.Data.MIMETYPE,
					StructuredName.CONTENT_ITEM_TYPE);
			values.put(StructuredName.GIVEN_NAME, name);
			resolver.insert(android.provider.ContactsContract.Data.CONTENT_URI,
					values);
			Message msg = new Message();
			msg.what = 7;
			Bundle bundle = new Bundle();
			ai +=1;
			bundle.putString("text", "正在恢复第" + ai + "个…"); // 往Bundle中存放数据
			msg.setData(bundle);// mes利用Bundle传递数据
			mHandler.sendMessage(msg);
		}

		if (!number.equals("")) {
			values.clear();
			values.put(android.provider.ContactsContract.Data.RAW_CONTACT_ID,
					rawContactID);
			values.put(android.provider.ContactsContract.Data.MIMETYPE,
					Phone.CONTENT_ITEM_TYPE);
			values.put(Phone.NUMBER, number);
			resolver.insert(android.provider.ContactsContract.Data.CONTENT_URI,
					values);
		}

	}

	/**
	 * 备份到云端后的对话框
	 */
	private void yunAlertDialog() {

		if (i_b != 0) {

			AlertDialog.Builder b = new AlertDialog.Builder(this).setTitle(
					"备份成功").setMessage("已经帮您备份到云端了, 备份了 " + i_b + " 个联系人");

			Message message1 = new Message();
			message1.what = 6;
			mHandler.sendMessage(message1);
			b.setPositiveButton("知道了", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
				}
			}).show();
		} else {
			AlertDialog.Builder b = new AlertDialog.Builder(this).setTitle(
					"温馨提示").setMessage("恭喜你，您的通讯录已经和服务器同步。");
			b.setPositiveButton("知道了", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
				}
			}).show();
		}
	}

	/**
	 * 恢复后的对话框
	 */
	private void huiAlertDialog() {
		if (b_b != 0) {
			AlertDialog.Builder b = new AlertDialog.Builder(this).setTitle(
					"恢复成功").setMessage("为您恢复了 " + b_b + " 个联系人");
			String ReadStr = MySharedData.sharedata_ReadString(
					ContactsBackupsActivity.this, "cc_str");
			MySharedData.sharedata_WriteString(ContactsBackupsActivity.this,
					"cc_str", (ReadStr + "+" + userListStrb));
			Log.d("SASASA", ReadStr + "+" + userListStrb);
			MySharedData.sharedata_WriteInt(ContactsBackupsActivity.this,
					"cc_int", (b_b + b_a));

			Message message1 = new Message();
			message1.what = 5;
			mHandler.sendMessage(message1);
			b.setPositiveButton("知道了", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
				}
			}).show();
		} else {
			AlertDialog.Builder b = new AlertDialog.Builder(this).setTitle(
					"温馨提示").setMessage("恭喜你，您的通讯录已经和服务器同步。");
			b.setPositiveButton("知道了", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
				}
			}).show();
		}
	}

	/**
	 * 退出
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		System.out.println("TabHost_Index.java onKeyDown");
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (exit == 0) {
				finish();
			} else if (exit == -1) {
				Toast("联系人正在同步，请勿返回！", 1500);
			}
		}
		return false;
	}

	/**
	 * 读取联系人数据
	 * 
	 * @param context
	 */
	public void getContact(Context context) {
		// 存储联系人信息
		String str = "";
		// 联系人数目
		int e = 0;
		// 获得所有的联系人
		Cursor cur = getContentResolver().query(
				ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
		// 循环遍历
		if (cur.moveToFirst()) {
			int idColumn = cur.getColumnIndex(ContactsContract.Contacts._ID);

			int displayNameColumn = cur
					.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
			do {
				// 获得联系人的ID号
				String contactId = cur.getString(idColumn);
				// 获得联系人姓名
				String disPlayName = cur.getString(displayNameColumn);
				if (disPlayName == null) {
					disPlayName = "未知";
				}
				str += disPlayName;
				// 查看该联系人有多少个电话号码。如果没有这返回值为0
				int phoneCount = cur
						.getInt(cur
								.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
				if (phoneCount > 0) {
					// 获得联系人的电话号码
					Cursor phones = getContentResolver().query(
							ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
							null,
							ContactsContract.CommonDataKinds.Phone.CONTACT_ID
									+ " = " + contactId, null, null);
					int i = 0;
					String phoneNumber;
					if (phones.moveToFirst()) {
						do {
							i++;
							phoneNumber = phones
									.getString(phones
											.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
							if (i == 1)
								str = str + "," + phoneNumber + "+";
						} while (phones.moveToNext());
					}
				} else {
					str = str + "," + "" + "+";
				}
				e += 1;
			} while (cur.moveToNext());

			str = str.replaceAll(" ", "").replaceAll("\n", "")
					.replaceAll("\t", "").replaceAll("\r", "");
		}

		MySharedData.sharedata_WriteString(context, "cc_str", str);
		MySharedData.sharedata_WriteInt(context, "cc_int", e);
		onDestroy();
		Log.e("联系人个数---", e + "");
	}

	public void onRestart() {
		super.onRestart();
		progressDialog.tv("正在为您加载…");
		progressDialog.show();
		new Thread(new Runnable() {
			public void run() {
				getContact(ContactsBackupsActivity.this);
				Message message1 = new Message();
				message1.what = 2;
				mHandler.sendMessage(message1);
				Message me = new Message();
				me.what = 8;
				mHandler.sendMessage(me);
			}
		}).start();

	}

}