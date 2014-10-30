package com.maiduo.bll;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import com.maiduo.model.*;

public class ApplicationUser extends Application {

	private  UserInfo userinfo = new UserInfo(); 
	private static ApplicationUser instance;
    private ApplicationUser()
    {
    }
	
	 //����ģʽ�л�ȡΨһ��MyApplicationʵ�� 
    public static ApplicationUser getInstance()
    {
         if(null == instance)
         {
            instance = new ApplicationUser();
         }
        return instance;             
    }
    
    
	public void Login(String userid,String password) // ��������뻹û�м��ܵ�
    {
		password = SiteHelper.MD5To16(password);
	    password = SiteHelper.MD5(userid + Config.UrlHashKey + password); // ���μ���
		
    	userinfo.setUserId(userid);
    	userinfo.setPassWrodKey(password); // }�μ��ܣ��������}�μ��ܺ������
    }
	
	public void LoginOut()
    {
		instance = null;
    }
	
	/**
	 * �жϵ�¼״̬
	 * @return
	 */
    public boolean CheckLoginStatus()
    {
      if(userinfo.getUserId() == null){
    	  return false;
      }else{
    	  return true;
      }
    }
    
    /**
     * �жϵ�¼����� �÷������ʺ�д�����Ӧ����ÿ�������д
     * @param c
     */
    public void CheckLogin(Context c)
    {
      if(!CheckLoginStatus()){ // ���û�е�¼
    	  	ApplicationActivity.getInstance().ReturnActivitySave(c);
    	  	Intent intent = new Intent();
    	  	intent.setClass(c,com.htx.user.U_Login.class);
			c.startActivity(intent);
			
      }
    }
    
//    /**
//     * �жϵ�¼����� �÷������ʺ�д�����Ӧ����ÿ�������д
//     * @param c
//     */
//    public void CheckLoginAd(Context c)
//    {
//      if(!CheckLoginStatus()){ // ���û�е�¼
//    	  	ApplicationActivity.getInstance().ReturnActivitySave(c);
//    	  	Intent intent = new Intent();
//    	  	intent.setClass(c,com.maiduo.ad.Home.class);
//			c.startActivity(intent);
//			
//      }
//    }
    
    
    public String GetUserName()
    {
      return userinfo.getUserName();
    }
    
    public String GetPassWordKey()
    {
      return userinfo.getPassWrodKey();
    }
    
    public String GetUserId()
    {
      return userinfo.getUserId();
    }
    
    public int GetOrderType()
    {
      return userinfo.getOrderType();
    }
    
    
    
    

	public String getTrueName() {
		return userinfo.getTrueName();
	}
	public void setTrueName(String trueName) {
		this.userinfo.setTrueName(trueName);
	}
	public String getCardNo() {
		return userinfo.getCardNo();
	}
	public void setCardNo(String cardNo) {
		this.userinfo.setCardNo(cardNo);
	}
	public String getBankType() {
		return userinfo.getBankType();
	}
	public void setBankType(String bankType) {
		this.userinfo.setBankType(bankType);
	}
    
    
	public String getMoney() {
		return userinfo.getMoney();
	}
	public void setMoney(String money) {
		if(money == null){
			money = "0.00";
		}
		if(money.equals("")){
			money = "0.00";
		}
		this.userinfo.setMoney(money);
	}
	
	public String getOneyAdMoney() {
		return userinfo.getOneAdMoney();
	}
	public void setOneAdMoney(String oneAdMoney) {
		if(oneAdMoney == null){
			oneAdMoney = "0.00";
		}
		if(oneAdMoney.equals("")){
			oneAdMoney = "0.00";
		}
		this.userinfo.setOneAdMoney(oneAdMoney);
	}
	public String getUserTypeName() {
		return userinfo.getUserTypeName();
	}
	public void setUserTypeName(String userTypeName) {
		this.userinfo.setUserTypeName(userTypeName);
	}
	
	
}