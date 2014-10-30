package com.htx.main;

import java.util.ArrayList;
import com.hetianxia.activity.R;
import com.htx.pub.PubActivity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class listActivity extends PubActivity {

	private static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;
	private ListView mList;
	private ArrayList<String> matches;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		HideStatusBar();
		setContentView(R.layout.list);

		mList = (ListView) findViewById(R.id.list1);

		startVoiceRecognitionActivity();

		mList.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

				Intent intent = new Intent();
				intent.putExtra("result", matches.get(arg2));
				listActivity.this.setResult(RESULT_OK, intent);
				listActivity.this.finish();
			}
		});
	}

	/**
	 * Fire an intent to start the speech recognition activity.
	 */
	private void startVoiceRecognitionActivity() {
		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "《合天下》语音搜索");
		startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
	}

	/**
	 * Handle the results from the recognition activity.
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == VOICE_RECOGNITION_REQUEST_CODE && resultCode == RESULT_OK) {
			
			matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
			mList.setAdapter(new ArrayAdapter<String>(this, R.layout.list_sp_item, matches));//android.R.layout.simple_list_item_1
		}else{
			finish();
		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	// 隐藏标题栏
	private void HideStatusBar() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
		Window myWindow = this.getWindow();
		myWindow.setFlags(flag, flag);
	}

}
