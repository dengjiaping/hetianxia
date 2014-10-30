package com.maiduo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.json.JSONObject;

import com.maiduo.bll.ApplicationShopping;
import com.maiduo.bll.Config;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;


public class Qiandai extends Activity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
/*        try {
		     Class.forName("com.qiandai.qdpayplugin.api.PayService");
	     } catch (ClassNotFoundException e) {
	     // TODO Auto-generated catch block   
	    	 Toast("请先安装钱袋宝服务");
	    	 QDInstatllApp(); 
	    	 return;
	     }*/
        
     // 取mobileId
	/*	SharedPreferences sharedata = getSharedPreferences("data", 0);  
        int mobileId = sharedata.getInt("qiandai", 0);  
        if(mobileId == 0){
        	
        	 Toast("请先安装钱袋宝服务");
	    	 QDInstatllApp(); 
        
	        SharedPreferences.Editor sharedataEditor = getSharedPreferences("data", 0).edit();  
	        sharedataEditor.putInt("qiandai",1);  
	        sharedataEditor.commit(); 
        
        }*/
        
        

        
        if(!checkApkExist(this,"com.qiandai.qdpayplugin")){
        	Toast.makeText(this,"请先安装钱袋宝服务",Toast.LENGTH_SHORT).show();
        	QDInstatllApp(); 
        	this.finish();
        	//QDPay();
        }else{
        	QDPay();
        }
		
        
	}
	
	public boolean checkApkExist(Context context, String packageName) {
		if (packageName == null || "".equals(packageName))
		return false;
		try {
			ApplicationInfo info = context.getPackageManager().getApplicationInfo(packageName,PackageManager.GET_UNINSTALLED_PACKAGES);
			return true;
		} catch (NameNotFoundException e) {
		return false;
		}
	}
	
	
	/**
	 * 安装APP
	 * 
	 * @author Administrator
	 * 
	 */
	public void QDInstatllApp() {

		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(Intent.ACTION_VIEW);
		String type = "application/vnd.android.package-archive";
		try {
			InputStream is = getClass().getResourceAsStream(
					"/assets/qdPayPlugin.apk");
			FileOutputStream fos = Qiandai.this.openFileOutput(
					"qdPayPlugin.apk", Context.MODE_PRIVATE
							+ Context.MODE_WORLD_READABLE);
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = is.read(buffer)) != -1) {
				fos.write(buffer, 0, len);
			}
			fos.flush();
			is.close();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		File f = new File(Qiandai.this.getFilesDir().getPath()
				+ "/qdPayPlugin.apk");
		intent.setDataAndType(Uri.fromFile(f), type);
		//Qiandai.this.startActivityForResult(intent, 1);
		Qiandai.this.startActivity(intent);

	}

	/**
	 * 支付
	 * 
	 * @author Administrator
	 * 
	 */
	public void QDPay() {
		
		Intent intent = new Intent("com.qiandai.qdpayplugin.api.PayService");
		String requestStr = null;
		String arg = "{" +
				"\"appSign\":\""+ Config.ShoppingPayTypeBankAppsign  +"\"," +
				"\"appOrderid\":\"20120605010201666\"," +
				"\"payMoney\":0.01," +
				//"\"payMoney\":"+ ApplicationShopping.getInstance().getPayPrice() +"," +
				//"\"displayInfo\":[]," +
				"\"displayInfo\":[{title:\"标题1\",content:\"买多网\"}]," +
				"\"addInfo\":\"买多网\"," +
				"\"payeeNo\":\"maiduo\"," +
				"\"payeeName\":\"买多网\"," +
				"\"payeePhone\":\"\"," +
				"\"payeeEmail\":\"\"," +
				"\"payeeSign\":\"10010\"," +
				"\"payerNo\":\"\"," +
				"\"payerName\":\"\"," +
				"\"payerPhone\":\"\"," +
				"\"payerEmail\":\"\"," +
				"\"payerSign\":\"\"," +
				"\"isInputMoney\":false," +
				"\"navBtns\":{\"rightBtn\":\"\",\"leftBtn\":\"\"}," +
				"\"backurl\":\"http://ms.maiduo.com/Shopping/BackQiandai.ashx\"" +
				"}";
		requestStr = "{action:\"qd_pay\",reqParam:" + arg + "}";
		System.out.println("requestStr:" + requestStr);
		intent.putExtra("request", requestStr);
		startActivityForResult(intent, 0);
	

	}

	/**
	 * 查询余额
	 * 
	 * @author Administrator
	 * 
	 */
	class QDqueryListener implements OnClickListener {
		public void onClick(View v) {
			Intent intent = new Intent("com.qiandai.qdpayplugin.api.PayService");
			String requestStr = null;
			String arg = "{" +
					"\"appSign\":\"373C6F7E-2A32-4C84-A53A-329395ECD30B\"," +
					"\"appOrderid\":\"\"," +
					"\"addInfo\":\"附加信息\"," +
					"\"payeeNo\":\"unicom_demo\"," +
					"\"payeeName\":\"名字\"," +
					"\"payeePhone\":\"\"," +
					"\"payeeEmail\":\"\"," +
					"\"payeeSign\":\"10010\""+
					"}";
			requestStr = "{action:\"qd_query\",reqParam:" + arg + "}";
			System.out.println("requestStr:" + requestStr);
			intent.putExtra("request", requestStr);
			startActivityForResult(intent, 0);
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		try {
			String result = data.getExtras().getString("response");
			JSONObject json = new JSONObject(result);
			Message msg=new Message();
			msg.what=1;
			msg.obj=result;
			hander.sendMessage(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
	/**
	 * 对话框
	 * @param title
	 * @param msg
	 */
	private void showDialog(String title,String msg){
		AlertDialog.Builder builder = new Builder(this);
		builder.setMessage(msg);
		builder.setTitle(title);
		builder.create().show();
	}
	
	private Handler hander=new Handler(Looper.getMainLooper()){
		@Override
		public void handleMessage(Message msg) {
			
			switch (msg.what) {
			case 1:
				showDialog("中间件回调客户端", msg.obj.toString());
				break;
			default:
				break;
			}
		}
	};
	
}
