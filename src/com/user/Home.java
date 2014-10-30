package com.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.hetianxia.activity.R;
import com.htx.user.U_info;
import com.maiduo.Product;
import com.maiduo.bll.ApplicationUser;
import com.maiduo.bll.Config;
import com.maiduo.bll.HttpHelper;
import com.maiduo.bll.SiteHelper;

/*
 * ��ʾ���ﳵ����ּ�¼�����׼�¼������̳ǹ��ﳵ������̳ǹ��ﳵ
 */
public class Home extends Activity  implements View.OnClickListener{

	private LinearLayout zhongxin_qianming, zhongxin_dizhi, zhongxin_fenxiang,
	zhongxin_gengxin, zhongxin_about;
	
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_home);

		// �жϵ�¼
//		ApplicationUser.getInstance().CheckLogin(Home.this);
//		String UserId = ApplicationUser.getInstance().GetUserId();

		zhongxin_qianming = (LinearLayout) findViewById(R.id.zhongxin_qianming);
		zhongxin_qianming.setOnClickListener((OnClickListener) this);
		zhongxin_qianming.setTag(2);
		zhongxin_dizhi = (LinearLayout) findViewById(R.id.zhongxin_dizhi);
		zhongxin_dizhi.setOnClickListener((OnClickListener) this);
		zhongxin_dizhi.setTag(3);
		zhongxin_fenxiang = (LinearLayout) findViewById(R.id.zhongxin_fenxiang);
		zhongxin_fenxiang.setOnClickListener((OnClickListener) this);
		zhongxin_fenxiang.setTag(4);
		zhongxin_gengxin = (LinearLayout) findViewById(R.id.zhongxin_gengxin);
		zhongxin_gengxin.setOnClickListener((OnClickListener) this);
		zhongxin_gengxin.setTag(5);
		zhongxin_about = (LinearLayout) findViewById(R.id.zhongxin_about);
		zhongxin_about.setOnClickListener((OnClickListener) this);
		zhongxin_about.setTag(6);
	}

	
	public void onClick(View v) {
		int tag = (Integer) v.getTag();
		switch (tag) {
		case 2://��������
			startActivity(new Intent(Home.this, U_info.class));
			break;
		case 3://����
			startActivity(new Intent(Home.this, com.user.OrderList.class));
			break;
		case 4://���ﳵ
			Intent intent = new Intent();
			intent.putExtra("CartType", Config.ShoppingMaiduoCartType);
			intent.setClass(Home.this, com.shopping.Cart.class);
			startActivity(intent);
			break;
		case 5:
//			startActivity(new Intent(Home.this, com.shopping.Address.class));
			break;
		case 6:
			Intent intent1=new Intent(Intent.ACTION_SEND);
		      intent1.setType("text/plain");
		      intent1.putExtra(Intent.EXTRA_SUBJECT, "分享给好友");
		      intent1.putExtra(Intent.EXTRA_TEXT, "权益商城权益商城权益商城权益商城权益商城权益商城权益商城权益商城权益商城权益商城");
		      startActivity(Intent.createChooser(intent1, getTitle()));
			break;
		default:
			break;
		}
		
	}


}
