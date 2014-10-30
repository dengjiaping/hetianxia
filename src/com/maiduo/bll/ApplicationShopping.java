package com.maiduo.bll;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.hetianxia.activity.R;
import com.maiduo.model.*;

public class ApplicationShopping extends Application {

	private int supplierId ;
	private int cartType;
	private  int address; 
	private  int areaId; 
	private  int sendType; 
	private  int sendTypeWuliuId; // �ͻ�ʽ������˾id
	private  int payType; 
	private  int guestbook; 
	private String freight;//�˷�
	private int freightPayType;//�˷�֧����ʽ    0�ָ�    1����
	private String memo = "" ;//����
	private String orderId ; // ������
	private String allPrice;
	private int postMethod;//�ͻ�ʽ ���ؿؼ��е� ��wuliuid�õ��ġ���ʱ�ò�����
	private String payPrice;// Ҫ֧���Ľ��
	private String payJifen;// Ҫ֧���Ļ��
	private String serialId; // ��ˮ��
	


	private static ApplicationShopping instance;
    private ApplicationShopping()
    {
    }
	
	 //����ģʽ�л�ȡΨһ��MyApplicationʵ�� 
    public static ApplicationShopping getInstance()
    {
         if(null == instance)
         {
            instance = new ApplicationShopping();
         }
        return instance;             
    }

	public static void setInstance(ApplicationShopping instance) {
		ApplicationShopping.instance = instance;
	}
	/**
	 * ��ʼ�����
	 */
	public void initData(){
		instance = new ApplicationShopping();
	}
	
	
	
	public int getPostMethod() {
		return postMethod;
	}

	public void setPostMethod(int postMethod) {
		this.postMethod = postMethod;
	}

	public int getSendTypeWuliuId() {
		return sendTypeWuliuId;
	}

	public void setSendTypeWuliuId(int sendTypeWuliuId) {
		
		this.sendTypeWuliuId = sendTypeWuliuId;
		getDataSendTypeWuliu();
	}
	
	public void setSendTypeWuliuIdNoGetData(int sendTypeWuliuId) {
		
		this.sendTypeWuliuId = sendTypeWuliuId;
		
	}

	public int getFreightPayType() {
		return freightPayType;
	}

	public void setFreightPayType(int freightPayType) {
		
		this.freightPayType = freightPayType;
		getDataSetFreightPayType();
	}
	
	public void setFreightPayTypeNoGetData(int freightPayType) {
		
		this.freightPayType = freightPayType;
		//getDataSetFreightPayType();
	}
	
	
	
	public String getAllPrice() {
		return allPrice;
	}

	public void setAllPrice(String allPrice) {
		this.allPrice = allPrice;
	}

	public String getJifen() {
		return jifen;
	}

	public void setJifen(String jifen) {
		this.jifen = jifen;
	}


	private String jifen;

	


	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getFreight() {
		return freight;
	}

	public void setFreight(String freight) {
		this.freight = freight;
	}

	public int getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(int supplierId) {
		this.supplierId = supplierId;
	}

	public int getCartType() {
		return cartType;
	}

	public void setCartType(int cartType) {
		this.cartType = cartType;
	}

	public int getAddress() {
		return address;
	}

	public void setAddress(int address) {
		
		this.address = address;
		getDataSetAddress();// ���ﱣ����ʱ�ջ���Ϣ
	}
	
	public void setAddressNoGetData(int address) {
		this.address = address;
	}

	public int getAreaId() {
		
		return areaId;
	}

	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}
	public int getSendType() {
		return sendType;
	}

	public void setSendType(int sendType) {
		
		
		this.sendType = sendType;
	}

	public int getPayType() {
		return payType;
	}

	public void setPayType(int payType) {
		this.payType = payType;
	}

	public int getGuestbook() {
		return guestbook;
	}

	public void setGuestbook(int guestbook) {
		this.guestbook = guestbook;
	}
	
	

	
	
	
	public String getPayPrice() {
		return payPrice;
	}

	public void setPayPrice(String payPrice) {
		this.payPrice = payPrice;
	}

	public String getPayJifen() {
		return payJifen;
	}

	public void setPayJifen(String payJifen) {
		this.payJifen = payJifen;
	}

	public String getSerialId() {
		return serialId;
	}

	public void setSerialId(String serialId) {
		this.serialId = serialId;
	}

	/**
	 * �����˷�֧����ʽ    0�ָ�    1����
	 * @return
	 */
	private  boolean getDataSetFreightPayType() {
		boolean reValue = false;
		String url = Config.UrlShoppingActionSave+"?act=saveSuggestFreight&userid="+ ApplicationUser.getInstance().GetUserId() +"&isFreightDelyPay="+ freightPayType +"&supplierId="+ ApplicationShopping.getInstance().getSupplierId() + "&cartType="+ ApplicationShopping.getInstance().getCartType();
		url = SiteHelper.GetSendUrl(url); // ������ַ
		//������Ҫ���������ش���json��ʽ��ݣ�
        try {
        	String reStr = HttpHelper.getJsonData(ApplicationShopping.this,url);
        	JSONObject reObject = new JSONObject(reStr);
        	if(reObject.getInt("status") == 0){
        		Toast.makeText(this,reObject.getString("error"),Toast.LENGTH_SHORT).show();
        		return reValue;
        	}
    
		} catch (JSONException e) {
			Toast.makeText(this,getString(R.string.loading_error),Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			Toast.makeText(this,getString(R.string.loading_error),Toast.LENGTH_SHORT).show();
		} 
		return reValue;
	}
	
	
	/**
	 * ������ʱ�ͻ���Ϣ  ������˾��
	 * @return
	 */
	private  boolean getDataSendTypeWuliu() {
		boolean reValue = false;
		String url = Config.UrlShoppingActionSave+"?act=saveReceiveTypeWuliu&userid="+ ApplicationUser.getInstance().GetUserId() +"&receiveId="+ sendType +"&wuliuId="+ sendTypeWuliuId +"&supplierId="+ ApplicationShopping.getInstance().getSupplierId() + "&cartType="+ ApplicationShopping.getInstance().getCartType();
		url = SiteHelper.GetSendUrl(url); // ������ַ
		//������Ҫ���������ش���json��ʽ��ݣ�
        try {
        	String reStr = HttpHelper.getJsonData(ApplicationShopping.this,url);
        	JSONObject reObject = new JSONObject(reStr);
        	if(reObject.getInt("status") == 0){
        		Toast.makeText(this,reObject.getString("error"),Toast.LENGTH_SHORT).show();
        		return reValue;
        	}
    
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Toast.makeText(this,getString(R.string.loading_error),Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Toast.makeText(this,getString(R.string.loading_error),Toast.LENGTH_SHORT).show();
		} 
		return reValue;
	}

	
	/**
	 * ������ʱ�ջ���Ϣ
	 * @return
	 */
	private  boolean getDataSetAddress() {
		boolean reValue = false;
		String url = Config.UrlShoppingAddressList+"?act=SaveAddressByList&userid="+ ApplicationUser.getInstance().GetUserId() +"&addressId="+ address +"&supplierId="+ ApplicationShopping.getInstance().getSupplierId() + "&cartType="+ ApplicationShopping.getInstance().getCartType();
		url = SiteHelper.GetSendUrl(url); // ������ַ
		//������Ҫ���������ش���json��ʽ��ݣ�
        try {
        	String reStr = HttpHelper.getJsonData(ApplicationShopping.this,url);
        	JSONObject reObject = new JSONObject(reStr);
        	if(reObject.getInt("status") == 0){
        		Toast.makeText(this,reObject.getString("error"),Toast.LENGTH_SHORT).show();
        		return reValue;
        	}
    
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Toast.makeText(this,getString(R.string.loading_error),Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Toast.makeText(this,getString(R.string.loading_error),Toast.LENGTH_SHORT).show();
		} 
		return reValue;
	}


    
    
    
    
     
}