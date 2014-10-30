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
//	 * 在用点击MENU按钮之后，会调用该方法，我们可以在这个方法当中加入自己的按钮控件
//	 */
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// TODO Auto-generated method stub
//		menu.add(0, menu1, 1, "给自己注册");
//		menu.add(0, menu2, 2, "给别人注册");
//		return super.onCreateOptionsMenu(menu);
//	}
//
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
////		if (item.getItemId() == menu1) { // 附近商家
////			Intent intent = new Intent();
////			intent.setClass(Register.this, com.jifen.shop.ShopCategoryMap.class);
////			startActivity(intent);
////		} else if (item.getItemId() == menu2) {// 积分商城
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
//		 * 此处调用基本统计代码
//		 */
////		StatService.onResume(this);
//	}
//
//	public void onPause() {
//		super.onPause();
//
//		/**
//		 * 此处调用基本统计代码
//		 */
////		StatService.onPause(this);
//	}
//
//	/**
//	 * 返回后清除
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
//		requestWindowFeature(Window.FEATURE_NO_TITLE); // 去掉默认标题
//		ApplicationActivity.getInstance().addActivity(this); // 退出时用的，这里记录到堆栈里
//	}
//
//	public void openLoading() {
//		final ProgressDialog dialog = ProgressDialog.show(Register.this, "",
//				"正在加载中...", true);
//		handler_loading = new Handler() {
//			public void handleMessage(Message msg) {
//				dialog.dismiss(); 
//			}
//		};
//	}
//
//	public void closeLoading() {
//		handler_loading.sendEmptyMessage(0); // 告诉handler
//	}
//
//	// 弹出提示
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
//				intent.setClass(Register.this, com.user.RegisterActivity.class); // 自己注册
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
//				intent.setClass(Register.this, com.user.HelpReg.class);// 给别人注册
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
