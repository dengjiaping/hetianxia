package cn.anycall.map;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.graphics.Typeface;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.hetianxia.activity.R;
import com.iflytek.speech.ErrorCode;
import com.iflytek.speech.ISpeechModule;
import com.iflytek.speech.InitListener;
import com.iflytek.speech.SpeechConstant;
import com.iflytek.speech.SpeechSynthesizer;
import com.iflytek.speech.SpeechUtility;
import com.iflytek.speech.SynthesizerListener;

/**
 * 语音合成组件
 * 
 */
public class TTSSupport {
	private SpeechSynthesizer mTts;
	private Queue<String> queue;
	private Context context;
	private boolean isSpeaking = false;
	private Dialog mLoadDialog;

	public TTSSupport(Context context) {
		this.context = context;
		mTts = new SpeechSynthesizer(context, mTtsInitListener);
		queue = new LinkedList<String>();
		SpeechUtility.getUtility(context).setAppid("4d6774d0");
//		if (!checkSpeechServiceInstalled()) {
//			if (NearbySearchResListActivity.Yuystate.equals("0")) {
//				showDownloadDialog();
//			}
//			return;
//		}
	}

	/**
	 * 初期化监听。
	 */
	private InitListener mTtsInitListener = new InitListener() {

		public void onInit(ISpeechModule arg0, int code) {

			if (code == ErrorCode.SUCCESS) {
				mTts.setParameter(SpeechConstant.ENGINE_TYPE, "local");
				mTts.setParameter(SpeechSynthesizer.VOICE_NAME, "xiaoyan");
				mTts.setParameter(SpeechSynthesizer.SPEED, "50");
				mTts.setParameter(SpeechSynthesizer.PITCH, "50");
				mTts.setParameter(SpeechConstant.PARAMS,
						"tts_audio_path=/sdcard/tts.pcm");
				play("");
			}
		}
	};

	private SynthesizerListener mSynthesizerListener = new SynthesizerListener() {
		
		public IBinder asBinder() {
			// TODO Auto-generated method stub
			return null;
		}
		
		public void onSpeakResumed() throws RemoteException {
			// TODO Auto-generated method stub
			
		}
		
		public void onSpeakProgress(int arg0) throws RemoteException {
			// TODO Auto-generated method stub
			
		}
		
		public void onSpeakPaused() throws RemoteException {
			// TODO Auto-generated method stub
			
		}
		
		public void onSpeakBegin() throws RemoteException {
			// TODO Auto-generated method stub
			
		}
		
		public void onCompleted(int arg0) throws RemoteException {
			// TODO Auto-generated method stub
			
		}
		
		public void onBufferProgress(int arg0) throws RemoteException {
			// TODO Auto-generated method stub
			
		}
	};
	
	/**
	 * 合成回调监听。
	 */
	private SynthesizerListener mTtsListener = new SynthesizerListener.Stub() {
		public void onBufferProgress(int progress) throws RemoteException {

		}

		public void onCompleted(int code) throws RemoteException {

		}

		public void onSpeakBegin() throws RemoteException {
			isSpeaking = true;
		}

		public void onSpeakPaused() throws RemoteException {

		}

		public void onSpeakProgress(int progress) throws RemoteException {

			if (progress == 99) {

				isSpeaking = false;
				String ttsText = queue.poll();
				if (ttsText != null) {
					mTts.startSpeaking(ttsText, mTtsListener);
				}
			}
		}

		public void onSpeakResumed() throws RemoteException {

		}
	};

	public boolean checkSpeechServiceInstalled() {
		String packageName = "com.iflytek.speechcloud";
		List<PackageInfo> packages = context.getPackageManager()
				.getInstalledPackages(0);
		for (int i = 0; i < packages.size(); i++) {
			PackageInfo packageInfo = packages.get(i);
			if (packageInfo.packageName.equals(packageName)) {
				return true;
			} else {
				continue;
			}
		}
		return false;
	}

	/**
	 * 显示下载组件提示框
	 */
	public void showDownloadDialog() {
		final Dialog dialog = new Dialog(context, R.style.dialog);

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View alertDialogView = inflater.inflate(R.layout.superman_alertdialog,
				null);
		dialog.setContentView(alertDialogView);
		Button okButton = (Button) alertDialogView.findViewById(R.id.ok);
		Button cancelButton = (Button) alertDialogView
				.findViewById(R.id.cancel);
		TextView comeText = (TextView) alertDialogView.findViewById(R.id.title);
		comeText.setTypeface(Typeface.MONOSPACE, Typeface.ITALIC);

		okButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				dialog.dismiss();
				Message message = new Message();
				message.what = 0;
				String url = SpeechUtility.getUtility(context)
						.getComponentUrl();
				// String assetsApk="SpeechService.apk";
				if (processInstall(context, url, null)) {
					if (mLoadDialog != null) {
						mLoadDialog.dismiss();
					}
				}
			}
		});
		cancelButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				NearbySearchResListActivity.Yuystate = "1";
				dialog.dismiss();
			}
		});
		dialog.show();
		WindowManager windowManager = ((Activity) context).getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
		lp.width = (int) (display.getWidth()); // 设置宽度
		dialog.getWindow().setAttributes(lp);

		return;
	}


	/**
	 * 如果服务组件没有安装，有两种安装方式。 1.直接打开语音服务组件下载页面，进行下载后安装。
	 * 2.把服务组件apk安装包放在assets中，为了避免被编译压缩，修改后缀名为mp3，然后copy到SDcard中进行安装。
	 */
	private boolean processInstall(Context context, String url, String assetsApk) {
		needReCheck = true;  
		// 直接下载方式
		ApkInstaller.openDownloadWeb(context, url);
		// 本地安装方式
		// if (!ApkInstaller.installFromAssets(context, assetsApk)) {
		// Toast.makeText(context, "安装失败", Toast.LENGTH_SHORT).show();
		// return false;
		// }
		return true;
	}

	/**
	 * 播放音频
	 * 
	 * @param text
	 */
	public void play(String text) {
			
//		if (!checkSpeechServiceInstalled()) {
//			showDownloadDialog();
//			return;
//		}
		
		queue.offer(text);

		if (!isSpeaking) {
			String ttsText = queue.poll();
			if (ttsText != null) {
				mTts.startSpeaking(ttsText, mTtsListener);
			}
		}
		
	}

	public void onDestory() {
		mTts.destory();
	}

	boolean needReCheck = false;

	public boolean needReCheckTTSServiceValidate() {
		return needReCheck;
	}

}
