//package com.maiduo.bll;
//
//import com.example.testviewpagerandtabhost.R;
//
//import android.app.Activity;
//import android.app.ProgressDialog;
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.Window;
//import android.widget.Button;
//import android.widget.Toast;
//
//public class Register extends Activity {
//
//	private static final int menu1 = 1;
//	private static final int menu2 = 2;
//	private Handler handler_loading;
//
//	/**
//	 * ���õ��MENU��ť֮�󣬻���ø÷��������ǿ���������������м����Լ��İ�ť�ؼ�
//	 */
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// TODO Auto-generated method stub
//		menu.add(0, menu1, 1, "���Լ�ע��");
//		menu.add(0, menu2, 2, "������ע��");
//		return super.onCreateOptionsMenu(menu);
//	}
//
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
////		if (item.getItemId() == menu1) { // �����̼�
////			Intent intent = new Intent();
////			intent.setClass(Register.this, com.jifen.shop.ShopCategoryMap.class);
////			startActivity(intent);
////		} else if (item.getItemId() == menu2) {// �����̳�
////			Intent intent = new Intent();
////			intent.setClass(Register.this, com.jifen.product.Home.class);
////			startActivity(intent);
////		}
//			return super.onOptionsItemSelected(item);
//		
//	}
//
//	public void onResume() {
//		super.onResume();
//
//		/**
//		 * �˴����û���ͳ�ƴ���
//		 */
////		StatService.onResume(this);
//	}
//
//	public void onPause() {
//		super.onPause();
//
//		/**
//		 * �˴����û���ͳ�ƴ���
//		 */
////		StatService.onPause(this);
//	}
//
//	/**
//	 * ���غ����
//	 * 
//	 * @param savedInstanceState
//	 */
//	public void onDestroy(Bundle savedInstanceState) {
//		Register.this.finish();
//	}
//
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE); // ȥ��Ĭ�ϱ���
//		ApplicationActivity.getInstance().addActivity(this); // �˳�ʱ�õģ������¼����ջ��
//	}
//
//	public void openLoading() {
//		final ProgressDialog dialog = ProgressDialog.show(Register.this, "",
//				"���ڼ�����...", true);
//		handler_loading = new Handler() {
//			public void handleMessage(Message msg) {
//				dialog.dismiss(); 
//			}
//		};
//	}
//
//	public void closeLoading() {
//		handler_loading.sendEmptyMessage(0); // ����handler
//	}
//
//	// ������ʾ
//	public void Toast(String str) {
//		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
//	}
//
//	public void InitTop(int n) {
//		Button btn1 = (Button) findViewById(R.id.menu_own);
//		btn1.setOnClickListener(new Button.OnClickListener() {
//			public void onClick(View v) {
//
//				Intent intent = new Intent();
//				intent.setClass(Register.this, com.user.RegisterActivity.class); // �Լ�ע��
//				startActivity(intent);
//			}
//		});
//		if (n == 1) {
//			btn1.setBackgroundColor(getResources().getColor(R.color.user_register));
//		}
//		Button btn2 = (Button) findViewById(R.id.menu_other);
//		btn2.setOnClickListener(new Button.OnClickListener() {
//			public void onClick(View v) {
//
//				Intent intent = new Intent();
//				intent.setClass(Register.this, com.user.HelpReg.class);// ������ע��
//				startActivity(intent);
//			}
//		});
//		if (n == 2) {
//			btn2.setBackgroundColor(getResources().getColor(R.color.user_register));
//		}
//
//	}
//
//}
