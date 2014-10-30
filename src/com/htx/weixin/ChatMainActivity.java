package com.htx.weixin;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.json.JSONObject;
import com.hetianxia.activity.R;
import com.htx.conn.Const;
import com.htx.conn.HttpHelper;
import com.htx.pub.File_Manage;
import com.htx.pub.MySharedData;
import com.htx.search.SiteHelper;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ChatMainActivity extends Activity implements OnClickListener {

	private Button mBtnSend;
	private TextView shop_name;
	private LinearLayout mBtnRcd;
	private Button mBtnBack, btn_title_popmenu1, btn_title_popmenu2,
			btn_title_popmenu3;
	private EditText mEditTextContent;
	private RelativeLayout mBottom;
	private ListView mListView;
	private ChatMsgViewAdapter mAdapter;
	private List<ChatMsgEntity> mDataArrays = new ArrayList<ChatMsgEntity>();
	private ImageView chatting_mode_btn;
	private boolean btn_vocie = false;
	private Handler mHandler = new Handler();
	private Intent intent;
	private String[] msgArray;
	private String[] dataArray;
	private int count;
	private ImageButton right_btn;
	private String titleStr = "", welcome = "", time = "", StoreId = "",
			brief = "", url = "";
	private PopMenu1 popMenu1;
	private PopMenu2 popMenu2;
	private PopMenu3 popMenu3;
	private String pubpath = "", toppath = "";
	public static ChatMainActivity instance2 = null;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shop_chat);
		// 启动activity时不自动弹出软键盘
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		instance2 = this;
		toppath = Environment.getExternalStorageDirectory() + "/.hetao/caChe/";
		initView();
	}

	public void initView() {
		right_btn = (ImageButton) findViewById(R.id.right_btn);
		mListView = (ListView) findViewById(R.id.listview);
		shop_name = (TextView) findViewById(R.id.shop_name);
		mBtnSend = (Button) findViewById(R.id.btn_send);
		mBtnRcd = (LinearLayout) findViewById(R.id.btn_rcd);
		mBtnSend.setOnClickListener(this);
		mBtnBack = (Button) findViewById(R.id.btn_back);
		btn_title_popmenu1 = (Button) findViewById(R.id.btn_title_popmenu1);
		btn_title_popmenu2 = (Button) findViewById(R.id.btn_title_popmenu2);
		btn_title_popmenu3 = (Button) findViewById(R.id.btn_title_popmenu3);
		mBottom = (RelativeLayout) findViewById(R.id.btn_bottom);
		chatting_mode_btn = (ImageView) this.findViewById(R.id.ivPopUp);
		mEditTextContent = (EditText) findViewById(R.id.et_sendmessage);
		intent = getIntent();
		if (intent.hasExtra("titleStr")) {
			titleStr = intent.getStringExtra("titleStr");
			MySharedData.sharedata_WriteString(ChatMainActivity.this,
					"titeeeetr", titleStr);
		}
		if (intent.hasExtra("welcome")) {
			welcome = intent.getStringExtra("welcome");
		}
		if (intent.hasExtra("StoreId")) {
			StoreId = intent.getStringExtra("StoreId");
			pubpath = MySharedData.sharedata_ReadString(this, "UserId") + "/"
					+ StoreId + ".txt";
			// 建立用户聊天记录（商户文件）
			File_Manage.mkfile(pubpath, ChatMainActivity.this, "1");
		}
		if (intent.hasExtra("brief")) {
			brief = intent.getStringExtra("brief");
		}
		if (intent.hasExtra("url")) {
			url = intent.getStringExtra("url");
		}
		if (intent.hasExtra("time")) {
			time = intent.getStringExtra("time");
		}
		shop_name.setText(titleStr);
		popMenu1 = new PopMenu1(this, StoreId);
		popMenu1.addItems(new String[] { "商家简介", "联系方式", "商家动态" });
		popMenu2 = new PopMenu2(this, StoreId);
		popMenu2.addItems(new String[] { "活动推荐", "最新特价", "特色商品" });
		popMenu3 = new PopMenu3(this, StoreId);
		popMenu3.addItems(new String[] { "商家评论", "分享有礼" });
		mBtnBack.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
		// 切换按钮
		chatting_mode_btn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (btn_vocie) {
					mBtnRcd.setVisibility(View.VISIBLE);
					mBottom.setVisibility(View.GONE);
					btn_vocie = false;
					chatting_mode_btn
							.setImageResource(R.drawable.chatting_setmode_msg_btn);
				} else {
					mBtnRcd.setVisibility(View.GONE);
					mBottom.setVisibility(View.VISIBLE);
					chatting_mode_btn
							.setImageResource(R.drawable.chatting_setmode_voice_btn);
					btn_vocie = true;
				}
			}
		});

		right_btn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent in = new Intent(ChatMainActivity.this,
						ShopHomeInfo.class);
				in.putExtra("StoreId", StoreId);
				in.putExtra("title", titleStr);
				in.putExtra("brief", brief);
				in.putExtra("address", "");
				in.putExtra("isFocus", "1");
				in.putExtra("logo", url);
				startActivity(in);
			}
		});
		if (intent.hasExtra("ids")) {
			String ids = intent.getStringExtra("ids");
			getDatePush(ids);
		}
		initData();
		initData2();
		btn_title_popmenu1.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				popMenu1.showAsDropDown(v);
			}
		});
		btn_title_popmenu2.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				popMenu2.showAsDropDown(v);
			}
		});
		btn_title_popmenu3.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				popMenu3.showAsDropDown(v);
			}
		});
	}

	// 写文件
	private void write(String content, String lujing) {
		try {
			File saveFile = new File(lujing);
			FileOutputStream fos = new FileOutputStream(saveFile);
			fos.write(content.getBytes());
			fos.close();
		} catch (Exception e) {
		}
	}

	private void addfile(String content, String file) throws IOException {
		FileWriter writer = new FileWriter(file, true);
		writer.write(content);
		writer.close();
	}

	public void getData(String ids) {

		try {
			JSONObject reObject = new JSONObject(ids);
			if (reObject.getInt("status") == 1) {
				JSONObject ob = reObject.getJSONObject("result");
				org.json.JSONArray reArray = ob.getJSONArray("list");

				for (int i = 0; i < reArray.length(); i++) {
					JSONObject jsonObject = (JSONObject) reArray.opt(i);
					String StoreIds = jsonObject.getString("StoreId");
					shop_name.setText(jsonObject.getString("StoreName"));
					pubpath = MySharedData.sharedata_ReadString(this, "UserId")
							+ "/" + StoreIds + ".txt";
					String ChatType = jsonObject.getString("ChatType");
					if (ChatType.equals("0")) {
						ChatMsgEntity entity = new ChatMsgEntity();
						entity.setDate(getDate());
						entity.setMsgType(1);
						entity.setLogo(url);
						entity.setText(jsonObject.getString("ChatContent"));
						mDataArrays.add(entity);
						addfile("1^" + getDate() + "^" + url + "^"
								+ jsonObject.getString("ChatContent") + "*",
								toppath + pubpath);
					}
					if (ChatType.equals("1")) {
						org.json.JSONArray aj = jsonObject
								.getJSONArray("ChatList");
						for (int j = 0; j < aj.length(); j++) {
							JSONObject jj = (JSONObject) aj.opt(j);
							ChatMsgEntity entity = new ChatMsgEntity();
							entity.setDate(getDate());
							entity.setMsgType(2);
							entity.setName(jj.getString("InfoTitle"));
							entity.setText(jj.getString("InfoContent"));
							MySharedData.sharedata_WriteString(
									ChatMainActivity.this, mListView.getCount()+"",
									jj.getString("InfoPic"));
							MySharedData.sharedata_WriteString(
									ChatMainActivity.this, "InfoUrl",
									jj.getString("InfoUrl"));
							mDataArrays.add(entity);
							addfile("11^" + getDate() + "^" + url + "^"
									+ jsonObject.getString("ChatContent") + "^"
									+ jj.getString("InfoTitle") + "^"
									+ jj.getString("InfoContent") + "^"
									+ jj.getString("InfoPic") + "^"
									+ jj.getString("InfoUrl") + "*", toppath
									+ pubpath);
						}
					}
				}
				mAdapter.notifyDataSetChanged();
				mListView.setSelection(mListView.getCount() - 1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void getDatePush(String ids) {

		right_btn.setVisibility(View.GONE);
		try {
			Log.e("meg", "|-1->> " + ids);
			JSONObject reObject = new JSONObject(ids);

			String ChatType = reObject.getString("ChatType");
			String StoreIds = reObject.getString("StoreId");
			shop_name.setText(reObject.getString("StoreName"));
			pubpath = MySharedData.sharedata_ReadString(this, "UserId") + "/"
					+ StoreIds + ".txt";
			if (ChatType.equals("0")) {
				ChatMsgEntity entity = new ChatMsgEntity();
				entity.setDate(getDate());
				entity.setMsgType(1);
				entity.setLogo(reObject.getString("StoreLogo"));
				entity.setText(reObject.getString("ChatContent"));
				mDataArrays.add(entity);
				addfile("1^" + getDate() + "^"
						+ reObject.getString("StoreName") + "^"
						+ reObject.getString("ChatContent") + "^*", toppath
						+ pubpath);
			}
			if (ChatType.equals("1")) {
				org.json.JSONObject aj = reObject.getJSONObject("ChatList");
				ChatMsgEntity entity = new ChatMsgEntity();
				entity.setDate(getDate());
				entity.setMsgType(2);
				entity.setName(aj.getString("InfoTitle"));
				entity.setText(aj.getString("InfoContent"));
				MySharedData.sharedata_WriteString(ChatMainActivity.this,
						"image_shop", aj.getString("InfoPic"));
				MySharedData.sharedata_WriteString(ChatMainActivity.this,
						"InfoUrl", aj.getString("InfoUrl"));
				mDataArrays.add(entity);
//				addfile("11^" + getDate() + "^" + aj.getString("InfoTitle")
//						+ "^" + aj.getString("InfoContent") + "^"
//						+ aj.getString("InfoPic") + "^"
//						+ aj.getString("InfoUrl") + "*", toppath + pubpath);
				addfile("11^" + getDate()+ "^"+ aj.getString("InfoPic") + "^"
						+ aj.getString("InfoUrl")  + "^" + aj.getString("InfoTitle")+ "^" + aj.getString("InfoContent")+ "*", toppath + pubpath);
			}

			popMenu1 = new PopMenu1(this, StoreIds);
			popMenu1.addItems(new String[] { "商家简介", "联系方式", "商家动态" });
			popMenu2 = new PopMenu2(this, StoreIds);
			popMenu2.addItems(new String[] { "活动推荐", "最新特价", "特色商品" });
			popMenu3 = new PopMenu3(this, StoreIds);
			popMenu3.addItems(new String[] { "商家评论", "分享有礼" });

			Log.e("meg", "|-9->>   ok!");
			mAdapter.notifyDataSetChanged();
			mListView.setSelection(mListView.getCount() - 1);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void initData() {
		if (welcome.equals("") || welcome == null) {
			msgArray = new String[] {};
			count = 0;
		} else {
			msgArray = new String[] { welcome };
			count = 1;
		}
		if (time.equals("") || time == null) {
			dataArray = new String[] {};
		} else {
			dataArray = new String[] { time };
		}
		for (int i = 0; i < count; i++) {
			ChatMsgEntity entity = new ChatMsgEntity();
			entity.setDate(dataArray[i]);
			if (i % 2 == 0) {
				entity.setMsgType(1);
			} else {
				entity.setMsgType(0);
			}
			entity.setText(msgArray[i]);
			entity.setLogo(url);
			mDataArrays.add(entity);
		}
		mAdapter = new ChatMsgViewAdapter(this, mDataArrays);
		mListView.setAdapter(mAdapter);
	}

	public void initData2() {
		File file2 = new File(toppath + pubpath);
		String content_str = "";
		try {
			if (!file2.exists()) {
				content_str = "";
			} else {
				FileInputStream inStream = new FileInputStream(toppath
						+ pubpath);
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024];
				int length = -1;
				while ((length = inStream.read(buffer)) != -1) {
					stream.write(buffer, 0, length);
				}
				stream.close();
				inStream.close();
				content_str = stream.toString();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		if (content_str.length() > 0) {
			String[] content_list = content_str.split("\\*");
			for (int i = 0; i < content_list.length; i++) {
				String[] item_list = content_list[i].split("\\^");
				ChatMsgEntity entity = new ChatMsgEntity();
				int llen = item_list.length;
				if (llen > 0) {
					entity.setMsgType(Integer.parseInt(item_list[0]));
					if (llen > 1) {
						entity.setDate(item_list[1]);
						if (llen > 2) {
							entity.setLogo(item_list[2]);
							if (llen > 3) {
								entity.setText(item_list[3]);
								if (llen > 4) {
									entity.setName(item_list[4]);
									if (llen > 5) {
										entity.setText(item_list[5]);
									}
								}
							}
						}
					}
				}
				mDataArrays.add(entity);

			}
			mAdapter = new ChatMsgViewAdapter(this, mDataArrays);
			mListView.setAdapter(mAdapter);
		}
	}

	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.btn_send:
			send();
			break;
		case R.id.btn_back:
			finish();
			break;
		}
	}

	private void send() {
		String contString = mEditTextContent.getText().toString();
		if (contString.length() > 0) {

			ChatMsgEntity entity = new ChatMsgEntity();
			entity.setDate(getDate());
			entity.setMsgType(0);
			entity.setText(contString);
			mDataArrays.add(entity);
			mAdapter.notifyDataSetChanged();
			mEditTextContent.setText("");
			mListView.setSelection(mListView.getCount() - 1);

			String user = MySharedData.sharedata_ReadString(
					ChatMainActivity.this, "UserId");
			String hash = SiteHelper.MD5(user + Const.UrlHashKey).toLowerCase();
			String url = Const.SendChat + "?userId=" + user + "&hash=" + hash
					+ "&StoreId=" + StoreId + "&Comment=" + contString;

			// 这里需要分析服务器回传的json格式数据，
			try {
				addfile("0^" + getDate() + "^^" + contString + "*", toppath
						+ pubpath);
				String reStr = HttpHelper.getJsonData(ChatMainActivity.this,
						url);
				JSONObject reObject = new JSONObject(reStr);
				if (reObject.getInt("status") == 1) {
					getData(reStr);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private String getDate() {
		Calendar c = Calendar.getInstance();

		String year = String.valueOf(c.get(Calendar.YEAR));
		String month = String.valueOf(c.get(Calendar.MONTH) + 1);
		String day = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
		String hour = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
		String mins = String.valueOf(c.get(Calendar.MINUTE));

		StringBuffer sbBuffer = new StringBuffer();
		sbBuffer.append(year + "-" + month + "-" + day + " " + hour + ":"
				+ mins);

		return sbBuffer.toString();
	}

	private static final int POLL_INTERVAL = 300;
	private Runnable mSleepTask = new Runnable() {
		public void run() {
			stop();
		}
	};
	private Runnable mPollTask = new Runnable() {
		public void run() {
			mHandler.postDelayed(mPollTask, POLL_INTERVAL);
		}
	};

	private void stop() {
		mHandler.removeCallbacks(mSleepTask);
		mHandler.removeCallbacks(mPollTask);
	}

}