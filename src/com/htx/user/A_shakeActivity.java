package com.htx.user;

import java.util.Random;
import org.json.JSONObject;
import com.hetianxia.activity.R;
import com.htx.conn.Const;
import com.htx.conn.HttpHelper;
import com.htx.conn.IsNetwork;
import com.htx.model.stact;
import com.htx.pub.MySharedData;
import com.htx.pub.PubActivity;
import com.htx.search.SiteHelper;
import com.htx.user.ShakeListener.OnShakeListener;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SlidingDrawer;
import android.widget.SlidingDrawer.OnDrawerCloseListener;
import android.widget.SlidingDrawer.OnDrawerOpenListener;
import android.widget.TextView;

public class A_shakeActivity extends PubActivity {

	ShakeListener mShakeListener = null;
	Vibrator mVibrator;
	private RelativeLayout mImgUp;
	private RelativeLayout mImgDn;
	private RelativeLayout mTitle;
	private String init = "2";
	private EditText edi;
	private SlidingDrawer mDrawer;
	private Button mDrawerBtn;
	private int drawer = 0;
	private TextView tt, ta;//
	private LinearLayout progressBar_ll, progressBar_l2;
	private String edii = "";

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shake_);

		mVibrator = (Vibrator) getApplication().getSystemService(
				VIBRATOR_SERVICE);

		mImgUp = (RelativeLayout) findViewById(R.id.shakeImgUp);
		mImgDn = (RelativeLayout) findViewById(R.id.shakeImgDown);
		mTitle = (RelativeLayout) findViewById(R.id.shake_title_bar);
		tt = (TextView) findViewById(R.id.tt);
		edi = (EditText) findViewById(R.id.edi);

		// 设置启动次数
		MySharedData
				.sharedata_WriteInt(A_shakeActivity.this, "htx_c", MySharedData
						.sharedata_ReadInt(A_shakeActivity.this, "htx_c") + 1);

		// 如果第一次启动
		if (MySharedData.sharedata_ReadInt(A_shakeActivity.this, "htx_c") == 1) {
			startActivity(new Intent(A_shakeActivity.this, main_bottom_bt.class));
		}
		mDrawer = (SlidingDrawer) findViewById(R.id.slidingDrawer1);
		mDrawerBtn = (Button) findViewById(R.id.handle);
		ta = (TextView) findViewById(R.id.ta);
		progressBar_ll = (LinearLayout) findViewById(R.id.progressBar_ll);
		progressBar_l2 = (LinearLayout) findViewById(R.id.progressBar_l2);

		Intent intent = getIntent();
		if (intent.getStringExtra("back") == null) {
			// startActivityForResult((new Intent(A_shakeActivity.this,
			// main_top_bt.class)), 110);
		} else {

			if (intent.getExtras().getString("tt").equals("摇一摇：激励名言")) {
				init = "3";
			}
			tt.setText(intent.getExtras().getString("tt"));
		}

		mDrawer.setOnDrawerOpenListener(new OnDrawerOpenListener() {
			public void onDrawerOpened() {

				ta.setVisibility(View.GONE);

				drawer = 1;
				mDrawerBtn.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.shake_report_dragger_down));
				TranslateAnimation titleup = new TranslateAnimation(
						Animation.RELATIVE_TO_SELF, 0f,
						Animation.RELATIVE_TO_SELF, 0f,
						Animation.RELATIVE_TO_SELF, 0f,
						Animation.RELATIVE_TO_SELF, -1.0f);

				// if (mShakeListener != null) {
				// mShakeListener.stop();
				// }
				ta.setTextColor(Color.BLUE);
				ta.setText("男人靠摇【一摇】,女人靠漂【流瓶】。马上编辑一个好玩的笑话，摇一摇分享你的快乐！");

				Animation shake = AnimationUtils.loadAnimation(
						A_shakeActivity.this, R.anim.anim);
				shake.reset();
				shake.setFillAfter(true);
				ta.startAnimation(shake);

				titleup.setDuration(200);
				titleup.setFillAfter(true);
				mTitle.startAnimation(titleup);
			}
		});

		/* 设定SlidingDrawer被关闭的事件处理 */
		mDrawer.setOnDrawerCloseListener(new OnDrawerCloseListener() {
			public void onDrawerClosed() {
				drawer = 0;

				progressBar_ll.setVisibility(View.GONE);

				mDrawerBtn.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.shake_report_dragger_up));
				TranslateAnimation titledn = new TranslateAnimation(
						Animation.RELATIVE_TO_SELF, 0f,
						Animation.RELATIVE_TO_SELF, 0f,
						Animation.RELATIVE_TO_SELF, -1.0f,
						Animation.RELATIVE_TO_SELF, 0f);

				mShakeListener.start();

				titledn.setDuration(200);
				titledn.setFillAfter(false);
				mTitle.startAnimation(titledn);
			}
		});

		mShakeListener = new ShakeListener(this);
		mShakeListener.setOnShakeListener(new OnShakeListener() {
			public void onShake() {
				progressBar_l2.setVisibility(View.GONE);
				startAnim(); // 开始 摇一摇手掌动画
				mShakeListener.stop();
				startVibrato(); // 开始 震动
				StartMu("shake_sound_male.mp3");
				new Handler().postDelayed(new Runnable() {
					public void run() {
						if (drawer == 0) {
							get(init, "0");
						} else {

							if (edi.getText().toString().equals("")) {
								ta.setVisibility(View.VISIBLE);
								ta.setText("亲，还没编辑笑话呢，您就摇我干啥？哼！");
								ta.setTextColor(Color.RED);
								Animation shakeAnim = AnimationUtils
										.loadAnimation(A_shakeActivity.this,
												R.anim.shake_y);
								ta.startAnimation(shakeAnim);
							} else {

								if (edi.getText().length() < 10) {
									ta.setTextColor(Color.RED);
									Animation shakeAnim = AnimationUtils
											.loadAnimation(
													A_shakeActivity.this,
													R.anim.shake_y);
									ta.startAnimation(shakeAnim);
									ta.setVisibility(View.VISIBLE);
									ta.setText("亲，您的笑话也忒短了吧，只有"
											+ edi.getText().toString().length()
											+ "个字？");
								} else if (edi.getText().length() > 1500) {
									ta.setTextColor(Color.RED);
									Animation shakeAnim = AnimationUtils
											.loadAnimation(
													A_shakeActivity.this,
													R.anim.shake_y);
									ta.startAnimation(shakeAnim);
									ta.setVisibility(View.VISIBLE);
									ta.setText("亲，您的笑话有点长哦！");
								} else {

									if (!edii.equals(edi.getText().toString())) {
										get(init, "10000");
									} else {
										ta.setTextColor(Color.RED);
										Animation shakeAnim = AnimationUtils
												.loadAnimation(
														A_shakeActivity.this,
														R.anim.shake_y);
										ta.startAnimation(shakeAnim);
										ta.setVisibility(View.VISIBLE);
										ta.setText("亲，您输入的和上次一样哦！");
									}
								}
							}
						}
						mVibrator.cancel();
						mShakeListener.start();
					}
				}, 2000);
			}
		});

	}

	public void startAnim() { // 定义摇一摇动画动画
		AnimationSet animup = new AnimationSet(true);
		TranslateAnimation mytranslateanimup0 = new TranslateAnimation(
				Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
				Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,
				-0.5f);
		mytranslateanimup0.setDuration(1000);
		TranslateAnimation mytranslateanimup1 = new TranslateAnimation(
				Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
				Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,
				+0.5f);
		mytranslateanimup1.setDuration(1000);
		mytranslateanimup1.setStartOffset(1000);
		animup.addAnimation(mytranslateanimup0);
		animup.addAnimation(mytranslateanimup1);
		mImgUp.startAnimation(animup);

		AnimationSet animdn = new AnimationSet(true);
		TranslateAnimation mytranslateanimdn0 = new TranslateAnimation(
				Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
				Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,
				+0.5f);
		mytranslateanimdn0.setDuration(1000);
		TranslateAnimation mytranslateanimdn1 = new TranslateAnimation(
				Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
				Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,
				-0.5f);
		mytranslateanimdn1.setDuration(1000);
		mytranslateanimdn1.setStartOffset(1000);
		animdn.addAnimation(mytranslateanimdn0);
		animdn.addAnimation(mytranslateanimdn1);
		mImgDn.startAnimation(animdn);
	}

	/**
	 * 开启声音
	 */
	protected void StartMu(String mp3) {

		try {
			AssetManager assetManager = A_shakeActivity.this.getAssets();
			AssetFileDescriptor fileDescriptor;
			fileDescriptor = assetManager.openFd(mp3);
			MediaPlayer mediaPlayer = new MediaPlayer();
			mediaPlayer
					.setDataSource(fileDescriptor.getFileDescriptor(),
							fileDescriptor.getStartOffset(),
							fileDescriptor.getLength());
			mediaPlayer.prepare();
			mediaPlayer.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void startVibrato() { // 定义震动
		mVibrator.vibrate(new long[] { 500, 200, 500, 200 }, -1); // 第一个｛｝里面是节奏数组，
																	// 第二个参数是重复次数，-1为不重复，非-1俄日从pattern的指定下标开始重复
	}

	/**
	 * 获取信息
	 */
	private void get(String i, String a) {

		if (a.equals("10000")) {

			progressBar_ll.setVisibility(View.VISIBLE);

			String usid = MySharedData.sharedata_ReadString(
					A_shakeActivity.this, "UserId").toString();
			String hash = SiteHelper.MD5(usid + Const.UrlHashKey);

			String url = "http://api.36936.com/ClientApi/ClientAddLifeWiki.ashx?userid="
					+ usid
					+ "&wikiType=2"
					+ "&wikiContent="
					+ edi.getText().toString() + "&hash=" + hash;
			
			try {

				int e = IsNetwork.isNetworkEnabled(A_shakeActivity.this);
				if (e == 0 || e == 1) {
					String reStr = HttpHelper.getJsonData(A_shakeActivity.this,
							url);
					JSONObject reObject = new JSONObject(reStr);
					if (reObject.getInt("status") == 1) {

						edii = edi.getText().toString();
						edi.setText(null);

						Animation shakeAnim = AnimationUtils.loadAnimation(
								A_shakeActivity.this, R.anim.shake_x);
						ta.setVisibility(View.VISIBLE);
						ta.startAnimation(shakeAnim);
						ta.setTextColor(Color.BLUE);
						ta.setText("亲，恭喜您，笑话已上传云端！！！");
						Log.e("AA", reStr);
					} else {
						Animation shakeAnim = AnimationUtils.loadAnimation(
								A_shakeActivity.this, R.anim.shake_x);
						ta.setVisibility(View.VISIBLE);
						ta.startAnimation(shakeAnim);
						ta.setTextColor(Color.RED);
						ta.setText("亲，您太牛B，都把笑话摇“流产”了，稍后再试试哦！");
					}
				} else {
					Animation shakeAnim = AnimationUtils.loadAnimation(
							A_shakeActivity.this, R.anim.shake_x);
					ta.setVisibility(View.VISIBLE);
					ta.startAnimation(shakeAnim);
					ta.setTextColor(Color.RED);
					ta.setText("亲，您太牛B，都把笑话摇“流产”了，稍后再试试哦！");
				}
			} catch (Exception e) {
				Animation shakeAnim = AnimationUtils.loadAnimation(
						A_shakeActivity.this, R.anim.shake_x);
				ta.setVisibility(View.VISIBLE);
				ta.startAnimation(shakeAnim);
				ta.setTextColor(Color.RED);
				ta.setText("亲，您太牛B，都把笑话摇“流产”了，稍后再试哦！");
				e.printStackTrace();
			}

		} else {
			progressBar_ll.setVisibility(View.VISIBLE);
			
			String url = "http://api.36936.com/ClientApi/ClientlifeWiki.ashx?id="
					+ getRandomString(20)
					+ "&ty="
					+ i
					+ "&userid="
					+ MySharedData.sharedata_ReadString(A_shakeActivity.this,
							"UserId");
			try {

				int e = IsNetwork.isNetworkEnabled(A_shakeActivity.this);
				if (e != 0 && e != 1) {
					progressBar_ll.setVisibility(View.GONE);
					progressBar_l2.setVisibility(View.VISIBLE);
					return;
				}

				String reStr = HttpHelper
						.getJsonData(A_shakeActivity.this, url);
				JSONObject reObject = new JSONObject(reStr);
				if (reObject.getInt("status") == 0) {
					progressBar_ll.setVisibility(View.GONE);
					progressBar_l2.setVisibility(View.VISIBLE);
					return;
				}
				if (reObject.getInt("status") == 1) {
					progressBar_ll.setVisibility(View.GONE);

					Intent intent = new Intent(A_shakeActivity.this,
							shake_show.class);

					intent.putExtra("tt", tt.getText().toString());
					intent.putExtra("abc", reObject.getString("msg"));
					stact.setAbc(reObject.getString("msg"));
					intent.putExtra("Source", reObject.getString("Source"));
					StartMu("shake_match.mp3");
					startActivityForResult(intent, 122);
					finish();
				}
			} catch (Exception e) {
				progressBar_ll.setVisibility(View.GONE);
				progressBar_l2.setVisibility(View.VISIBLE);
				e.printStackTrace();
			}
		}
	}

	public void shake_activity_back(View v) { // 标题栏 返回按钮
		this.finish();
	}

	public void linshi(View v) { // 标题栏

		if (mShakeListener != null) {
			mShakeListener.stop();
		}
		startActivityForResult((new Intent(A_shakeActivity.this,
				main_top_bt.class)), 110);
	}

	protected void onDestroy() {
		super.onDestroy();
		if (mShakeListener != null) {
			mShakeListener.stop();
		}
	}

	/**
	 * 产生随机字符串
	 * 
	 * @param length
	 * @return
	 */
	public static String getRandomString(int length) {
		// length表示生成字符串的长度
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";
		// 生成字符串从此序列中取
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	/**
	 * Handle the results from the recognition activity.
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (20 == resultCode) {
			mShakeListener.start();
			init = "1";
			tt.setTextSize(17);
			tt.setText("摇一摇：每日签到");
		} else if (21 == resultCode) {
			mShakeListener.start();
			tt.setTextSize(17);
			init = "2";
			tt.setText("摇一摇：开心一刻");
		} else if (22 == resultCode) {
			mShakeListener.start();
			tt.setTextSize(17);
			init = "3";
			tt.setText("摇一摇：激励名言");
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	public void onAttachedToWindow() {
		this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD);
		super.onAttachedToWindow();
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_HOME:
			if (mShakeListener != null) {
				mShakeListener.stop();
			}
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
