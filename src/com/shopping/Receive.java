package com.shopping;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hetianxia.activity.R;
import com.icq.demo.beans.Item;
import com.maiduo.bll.ApplicationShopping;
import com.maiduo.bll.ApplicationUser;
import com.maiduo.bll.Config;
import com.maiduo.bll.HttpHelper;
import com.maiduo.bll.SiteHelper;
import com.maiduo.model.DefaultSlide;

public class Receive extends Activity{

	private String UserId;
	private int supplierId;
	private int cartType;
	
	private String addressInfo;
	private String sendTypeName;
	private String freightPayTypeName;
	private String payTypeName;
	private String memoStr;
	private String sheng="",shi="",xian="";
	
	private TextView tvAddress;
	private TextView tvSendType;
	private TextView tvFreightPayType;
	private TextView tvPayType;
	private TextView tvMemo;
	private LinearLayout ll_ssx;
	private EditText edt_name,edt_jiedao,edt_phone;

	private ProgressDialog progressDialog;
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				if (progressDialog != null)
					progressDialog.dismiss();
				break;
			}
		}
	};
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_receive);
        
        //ģ��100101��¼
        //ApplicationUser.getInstance().Login("100114", "ok@2011235");
        // �жϵ�¼
        ApplicationUser.getInstance().CheckLogin(this);
        UserId = ApplicationUser.getInstance().GetUserId();
        
        Intent intent = this.getIntent();
        supplierId = intent.getIntExtra("supplierId",0);
        cartType = ApplicationShopping.getInstance().getCartType();
        
        // ����ǵ�һ�δӹ��ﳵ��4����ʼ�����ﳵ��������
        int initData = intent.getIntExtra("initData",0);
        if(initData == 1){ 
        	ApplicationShopping.getInstance().initData();
        	ApplicationShopping.getInstance().setCartType(cartType);
        }
        
        if(ApplicationShopping.getInstance().getSupplierId() == 0 ){
        	ApplicationShopping.getInstance().setSupplierId(supplierId);
        }
        
        edt_name = (EditText)findViewById(R.id.edt_name);
        edt_jiedao = (EditText)findViewById(R.id.edt_jiedao);
        edt_phone = (EditText)findViewById(R.id.edt_phone);
        
        ll_ssx = (LinearLayout)findViewById(R.id.ssx_ll);
        ll_ssx.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
	        	  Intent intent = new Intent();
	        	  intent.setClass(Receive.this,com.maiduo.ListViewShowActivity.class);
	              startActivityForResult(intent, 100);  
			}
		});
        
        //�ͻ�ʽ
        tvSendType = (TextView)findViewById(R.id.sendType);
        tvSendType.setOnClickListener(new TextView.OnClickListener()
        {
          public void onClick(View v)
          {
        	  Intent intent = new Intent();
        	  intent.setClass(Receive.this,com.maiduo.ListViewShowActivity.class);
              startActivityForResult(intent, 100);  
//              
          }
        });
        
        //�˷�֧����ʽ
        tvFreightPayType = (TextView)findViewById(R.id.tvFreightPayType);
        tvFreightPayType.setOnClickListener(new TextView.OnClickListener()
        {
          public void onClick(View v)
          {
        	  Intent intent = new Intent();
        	  intent.setClass(Receive.this,com.maiduo.ListViewShowActivity.class);
              startActivityForResult(intent, 100);  
              
          }
        });
        if(cartType == Config.ShoppingMaiduoCartType){ // ���������̳ǣ������˷�֧����ʽ
        	tvFreightPayType.setVisibility(View.GONE);
        }
        
       
       
        //֧����ʽ
        tvPayType = (TextView)findViewById(R.id.payType);
        tvPayType.setOnClickListener(new TextView.OnClickListener()
        {
          public void onClick(View v)
          {
        	  Intent intent = new Intent();
        	  intent.setClass(Receive.this,com.maiduo.ListViewShowActivity.class);
              startActivityForResult(intent, 100);  
              
          }
        });
        
        
        //����
        tvMemo = (TextView)findViewById(R.id.memo);
        tvMemo.setOnClickListener(new TextView.OnClickListener()
        {
          public void onClick(View v)
          {
//        	  Intent intent = new Intent();
//              intent.setClass(Receive.this,com.shopping.Other.class);
//              startActivity(intent);
              
          }
        });
        
        Button btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new Button.OnClickListener()
        {
          public void onClick(View v)
          {
        	  String str_name = edt_name.getText().toString();
        	  String str_jiedao = edt_jiedao.getText().toString();
        	  String str_phone = edt_phone.getText().toString();
        	  
        	  if (str_name.length() == 0 ) {
					Spanned html = Html
							.fromHtml("<font color='blue'>����д�ջ��ˣ�</font>");
					edt_name.setError(html);
					return;
        	  }
        	  if (str_jiedao.length() == 0 ) {
					Spanned html = Html
							.fromHtml("<font color='blue'>����д�ֵ5�ַ��</font>");
					edt_jiedao.setError(html);
					return;
        	  }
        	  if (str_phone.length() == 0 ) {
					Spanned html = Html
							.fromHtml("<font color='blue'>����д�ֻ���룡</font>");
					edt_phone.setError(html);
					return;
        	  }

	            Intent data=new Intent();  
	            data.putExtra("name", edt_name.getText().toString());  
	            data.putExtra("phone", edt_phone.getText().toString());  
	            data.putExtra("add",edt_jiedao.getText().toString());  
	            data.putExtra("sheng",sheng);  
	            data.putExtra("shi",shi);  
	            data.putExtra("xian",xian);  
	            setResult(101, data);  
	            finish(); 
             
          }
        });
        
	}
	
	public void onResume(){
    	super.onResume();
    	if(ApplicationShopping.getInstance() == null){
    		return;
    	}
    	// �״μ�����ʾ������
//        progressDialog = new ProgressDialog(this);   
//        progressDialog.setMessage(getString(R.string.loadingstr));   
//        progressDialog.show(); 
//        mHandler.postDelayed(new Runnable() {   
//            public void run() {   
//        	
//
//            	getData();
//                
//                tvAddress.setText("�ջ��ˣ�\n" + addressInfo);
//                tvSendType.setText("�ͻ�ʽ��\n" + sendTypeName + "   �˷ѣ���"+ApplicationShopping.getInstance().getFreight());
//                tvFreightPayType.setText("�˷�֧����ʽ��\n" + freightPayTypeName );
//                tvPayType.setText("֧����ʽ��\n" + payTypeName);
//                tvMemo.setText("���ԣ�\n" + ApplicationShopping.getInstance().getMemo());
//
//
//
//        	
//        	Message msg = new Message();
//        		msg.what = 1;
//        		mHandler.sendMessage(msg);
//            }   
//        },1000); 
    
    }
	
	
	
	
	private  void getData() {
		String url = Config.UrlShoppingReceive+"?userid="+ApplicationUser.getInstance().GetUserId() +"&supplierId="+ ApplicationShopping.getInstance().getSupplierId() +"&cartType="+ ApplicationShopping.getInstance().getCartType() ;
		
		if(ApplicationShopping.getInstance().getAddress() >0 ){
			url += "&addressId="+ApplicationShopping.getInstance().getAddress();
		}
		if(ApplicationShopping.getInstance().getSendType() > 0 ){
			url += "&sendTypeId="+ApplicationShopping.getInstance().getSendType();
		}
		if(ApplicationShopping.getInstance().getPayType() >0 ){
			url += "&payTypeId="+ApplicationShopping.getInstance().getPayType();
		}
		if(ApplicationShopping.getInstance().getFreightPayType() > 0 ){
			url += "&freightPayTypeId="+ApplicationShopping.getInstance().getFreightPayType();
		}
		if(ApplicationShopping.getInstance().getSendTypeWuliuId() > 0 ){
			url += "&sendTypeWuliuId="+ApplicationShopping.getInstance().getSendTypeWuliuId();
		}
		
		
		url = SiteHelper.GetSendUrl(url); // ������ַ
		//������Ҫ���������ش���json��ʽ��ݣ�
        try {
        	String re = HttpHelper.getJsonData(Receive.this,url);
        	JSONObject reObject = new JSONObject(re);
        	if(reObject.getInt("status") == 0){
        		if(reObject.getString("error").indexOf("û��") > -1){
//        			Intent intent = new Intent();
//                    intent.setClass(Receive.this,com.shopping.AddressAdd.class);
//                    startActivity(intent);
//                    Receive.this.finish();
        		}
        		
        		Toast.makeText(this,reObject.getString("error"),Toast.LENGTH_SHORT).show();
        		return;
        	}
        	JSONObject jsonObject = reObject.getJSONObject("result");
        	addressInfo = jsonObject.getString("addressInfo");
        	sendTypeName = jsonObject.getString("sendTypeName");
        	freightPayTypeName = jsonObject.getString("freightPayTypeName");
        	payTypeName = jsonObject.getString("payTypeName");
        	
        	//�������
        	ApplicationShopping.getInstance().setAddressNoGetData(jsonObject.getInt("addressValue"));
        	//ApplicationShopping.getInstance().setAreaId(jsonObject.getInt("areaId"));
        	ApplicationShopping.getInstance().setSendType(jsonObject.getInt("sendTypeValue"));
        	if(cartType == Config.ShoppingJifenCartType){//����ǻ���̳ǣ��������ָ����ǵ���
        		ApplicationShopping.getInstance().setFreightPayType(jsonObject.getInt("freightPayTypeValue"));
        	}
        	ApplicationShopping.getInstance().setPayType(jsonObject.getInt("payTypeValue"));
        	ApplicationShopping.getInstance().setFreight(jsonObject.getString("freight"));
        	ApplicationShopping.getInstance().setSendTypeWuliuIdNoGetData(jsonObject.getInt("sendWuliuId"));
			
        } catch (JSONException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Toast.makeText(this,getString(R.string.loading_error),Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Toast.makeText(this,getString(R.string.loading_error),Toast.LENGTH_SHORT).show();
		} 

	}
	@Override  
    protected void onActivityResult(int requestCode, int resultCode, Intent data)  
    {  
        //���Ը�ݶ���������4����Ӧ�Ĳ���  
        if(100==resultCode)  
        {  
        	sheng =data.getExtras().getString("sheng");
        	shi =data.getExtras().getString("shi");
        	xian =data.getExtras().getString("xian");
        	tvSendType.setText(sheng);
        	tvFreightPayType.setText(shi);
        	tvPayType.setText(xian);
        }  
        super.onActivityResult(requestCode, resultCode, data);  
    }  
}
