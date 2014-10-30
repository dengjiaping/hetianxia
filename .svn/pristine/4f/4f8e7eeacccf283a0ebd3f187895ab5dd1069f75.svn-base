package com.zijunlin.Zxing.Demo;

import java.io.IOException;
import java.util.Vector;
import org.json.JSONObject;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.hetianxia.activity.R;
import com.htx.action.BarcodeInfoActivity;
import com.htx.conn.Const;
import com.htx.conn.HttpHelper;
import com.htx.pub.LoadingDialog;
import com.htx.pub.MySharedData;
import com.htx.pub.PubActivity;
import com.htx.search.SiteHelper;
import com.htx.user.U_Main;
import com.htx.weixin.ShopHomeInfo;
import com.zijunlin.Zxing.Demo.camera.CameraManager;
import com.zijunlin.Zxing.Demo.decoding.CaptureActivityHandler;
import com.zijunlin.Zxing.Demo.decoding.InactivityTimer;
import com.zijunlin.Zxing.Demo.view.ViewfinderView;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class CaptureActivity extends PubActivity implements Callback {

	private CaptureActivityHandler handler;
	private ViewfinderView viewfinderView;
	private boolean hasSurface;
	private Vector<BarcodeFormat> decodeFormats;
	private String characterSet;
	private InactivityTimer inactivityTimer;
	private MediaPlayer mediaPlayer;
	private boolean playBeep;
	private static final float BEEP_VOLUME = 0.10f;
	private boolean vibrate;
	private ImageButton shoushu, shoudiantong, back;
	private int isOpen = 0;
	private String string = "";
	private LoadingDialog dialoga;
	private String StoreId = "", title = "", address = "", brief = "",
			logo = "";
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				Intent intent = new Intent(CaptureActivity.this,
						ShopHomeInfo.class);
				intent.putExtra("StoreId", StoreId);
				intent.putExtra("title", title);
				intent.putExtra("address", address);
				intent.putExtra("brief", brief);
				intent.putExtra("isFocus", "0");
				intent.putExtra("logo", logo);
				startActivity(intent);
				finish();
				dialoga.dismiss();
				break;
			case 2:
				dialoga.dismiss();
				Toast(string, 1500);
				break;
			}
			super.handleMessage(msg);
		}
	};

	/** Called when the activity is first created. */

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.capture);
		CameraManager.init(getApplication());

		viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
		hasSurface = false;
		inactivityTimer = new InactivityTimer(this);
		dialoga = new LoadingDialog(CaptureActivity.this);

//		addShortcut();

		back = (ImageButton) findViewById(R.id.back);
		back.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});

		shoushu = (ImageButton) findViewById(R.id.shoushu);
		shoushu.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent(CaptureActivity.this, U_Main.class));
			}
		});

		shoudiantong = (ImageButton) findViewById(R.id.shoudiantong);
		shoudiantong.setBackgroundResource(R.drawable.main_big_btn_bg);
		shoudiantong.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				switch (isOpen) {
				case 0:
					shoudiantong.setImageResource(R.drawable.main_big_btn_sel);
					CameraManager.setTorch(true);
					isOpen = 1;
					break;
				case 1:
					CameraManager.setTorch(false);
					shoudiantong.setImageResource(R.drawable.main_big_btn_bg);
					isOpen = 0;
					break;
				}
			}
		});
	}

	public void onResume() {
		super.onResume();
		SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
		SurfaceHolder surfaceHolder = surfaceView.getHolder();
		if (hasSurface) {
			initCamera(surfaceHolder);
		} else {
			surfaceHolder.addCallback(this);
			surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		}
		decodeFormats = null;
		characterSet = null;

		playBeep = true;
		AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
		if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
			playBeep = false;
		}
		initBeepSound();
		vibrate = true;
	}

	public void onPause() {
		super.onPause();
		if (handler != null) {
			handler.quitSynchronously();
			handler = null;
		}
		CameraManager.get().closeDriver();
	}

	protected void onDestroy() {
		inactivityTimer.shutdown();
		super.onDestroy();
	}

	private void initCamera(SurfaceHolder surfaceHolder) {

		if (surfaceHolder == null) {
			throw new IllegalStateException("No SurfaceHolder provided");
		}
		if (CameraManager.isOpen()) {
			Log.w("",
					"initCamera() while already open -- late SurfaceView callback?");
			return;
		}

		try {
			CameraManager.get().openDriver(surfaceHolder);
		} catch (IOException ioe) {
			return;
		} catch (RuntimeException e) {
			return;
		}
		if (handler == null) {
			handler = new CaptureActivityHandler(this, decodeFormats,
					characterSet);
		}
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	public void surfaceCreated(SurfaceHolder holder) {
		if (!hasSurface) {
			hasSurface = true;
			initCamera(holder);
		}

	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		hasSurface = false;

	}

	public ViewfinderView getViewfinderView() {
		return viewfinderView;
	}

	public Handler getHandler() {
		return handler;
	}

	public void drawViewfinder() {
		viewfinderView.drawViewfinder();

	}

	public void handleDecode(Result obj, Bitmap barcode) {
		inactivityTimer.onActivity();
		viewfinderView.drawResultBitmap(barcode);
		playBeepSoundAndVibrate();

		final String keyworld = obj.getText().toString();
		if (keyworld != null && !"".equals(keyworld)) {
			if (keyworld
					.contains("http://36936.com/m/s/?sid=")) {
				dialoga.show();
				new Thread(new Runnable() {
					public void run() {
						String adUserId = MySharedData.sharedata_ReadString(
								CaptureActivity.this, "UserId");
						String hash = SiteHelper.MD5(adUserId
								+ Const.UrlHashKey);

						String Url = "http://api.36936.com/ClientApi/Pos/getStoreDetail.ashx?"
								+ "&userid="
								+ adUserId
								+ "&hash="
								+ hash
								+ "&StoreId="
								+ keyworld.substring(
										keyworld.lastIndexOf("=") + 1,
										keyworld.length());

						Message message = new Message();
						try {
							String reStr = HttpHelper.getJsonData(
									CaptureActivity.this, Url);
							JSONObject reObject = new JSONObject(reStr);
							if (reObject.getInt("status") == 1) {
								JSONObject object = reObject
										.getJSONObject("result");
								StoreId = object.getString("id");
								title = object.getString("title");
								address = object.getString("address");
								brief = object.getString("brief");
								logo = object.getString("Logo");
								message.what = 1;
							} else {
								string = reObject.getString("msg");
								message.what = 2;
							}
						} catch (Exception e) {
							string = "网络异常！";
							message.what = 2;
							e.printStackTrace();
						}
						mHandler.sendMessage(message);
					}
				}).start();
			} else {
				Intent it = new Intent(CaptureActivity.this,
						BarcodeInfoActivity.class);
				it.putExtra("barcode", keyworld);
				startActivity(it);
			}
		}
	}

	private void initBeepSound() {
		if (playBeep && mediaPlayer == null) {

			setVolumeControlStream(AudioManager.STREAM_MUSIC);
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setOnCompletionListener(beepListener);

			AssetFileDescriptor file = getResources().openRawResourceFd(
					R.raw.beep);
			try {
				mediaPlayer.setDataSource(file.getFileDescriptor(),
						file.getStartOffset(), file.getLength());
				file.close();
				mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
				mediaPlayer.prepare();
			} catch (IOException e) {
				mediaPlayer = null;
			}
		}
	}

	private static final long VIBRATE_DURATION = 200L;

	private void playBeepSoundAndVibrate() {
		if (playBeep && mediaPlayer != null) {
			mediaPlayer.start();
		}
		if (vibrate) {
			Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
			vibrator.vibrate(VIBRATE_DURATION);
		}
	}

	/**
	 * When the beep has finished playing, rewind to queue up another one.
	 */
	private final OnCompletionListener beepListener = new OnCompletionListener() {
		public void onCompletion(MediaPlayer mediaPlayer) {
			mediaPlayer.seekTo(0);
		}
	};

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_VOLUME_DOWN:
			CameraManager.setTorch(false);
			return true;
		case KeyEvent.KEYCODE_VOLUME_UP:
			CameraManager.setTorch(true);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

//	/**
//	 * 为程序创建桌面快捷方式
//	 */
//	private void addShortcut() {
//		// 创建快捷方式的Intent
//		Intent shortcutintent = new Intent(
//				"com.android.launcher.action.INSTALL_SHORTCUT");
//		// 不允许重复创建
//		shortcutintent.putExtra("duplicate", false);
//		shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "扫你妹");
//		// 快捷图片
//		Parcelable icon = Intent.ShortcutIconResource.fromContext(
//				getApplicationContext(), R.drawable.hyzx_36);
//		shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
//		
//		// 点击快捷图片，运行的程序主入口
//		shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent(
//				getApplicationContext(), CaptureActivity.class));
//		// 发送广播。OK
//		sendBroadcast(shortcutintent);
//	}
}