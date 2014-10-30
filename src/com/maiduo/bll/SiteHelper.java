package com.maiduo.bll;

import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.text.format.Time;



public class SiteHelper {
	public static boolean IfNotEmputy(String str){
		str = (str + "0").replace("0","").replace(".",""); 
		if(str != "" && str != null){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 得到新的请求地址
	 * @param url
	 * @return
	 */
	public static String GetSendUrl(String url){
		String re = "";
		if(ApplicationUser.getInstance().CheckLoginStatus()){
			SimpleDateFormat   sDateFormat   =   new   SimpleDateFormat("yyyy-MM-dd,HH:mm:ss");      
			//String   date   =   "2012-07-24,16:41:38";   
			String   date   =   sDateFormat.format(new   java.util.Date()); 
			String md5url =  url + "&hashdate="+ date +"&passwordkey=" + ApplicationUser.getInstance().GetPassWordKey();
			re =  url + "&hashdate="+ date ;
			re = re + "&hashkey=" + MD5(md5url); 
		}else{
			re = url;
		}
		return re;
	}
	
	/**
	 * 广告用的加密
	 * @param url
	 * @return
	 */
	public static String GetSendUrlAd(String url){
		String re = "";
		if(ApplicationUser.getInstance().CheckLoginStatus()){
			SimpleDateFormat   sDateFormat   =   new   SimpleDateFormat("yyyy-MM-dd,HH:mm:ss");      
			//String   date   =   "2012-07-24,16:41:38";   
			String   date   =   sDateFormat.format(new   java.util.Date()); 
			//String md5url =  url + "&hashdate="+ date +"&passwordkey=" + ApplicationUser.getInstance().GetPassWordKey();
			String md5url =  url + "&hashdate="+ date ;
			re = md5url + "&hashkey=" + MD5(Config.UrlHashKey+MD5(md5url)); 
		}else{
			re = url;
		}
		return re;
		
	}
	
	
	
	
	/**
	 * md5加密 返回大写
	 * @param str
	 * @return
	 */
	public static String MD5(String str){ 
		MessageDigest md5 = null; 
		try{ 
			md5 = MessageDigest.getInstance("MD5"); 
		}catch(Exception e){ 
			e.printStackTrace(); 
		return ""; 
		} 
		char[] charArray = str.toCharArray(); 
		byte[] byteArray = new byte[charArray.length]; 
		for(int i = 0; i < charArray.length; i++){ 
			byteArray[i] = (byte)charArray[i]; 
		} 
		byte[] md5Bytes = md5.digest(byteArray); 
		StringBuffer hexValue = new StringBuffer(); 
		for( int i = 0; i < md5Bytes.length; i++) 
		{ 
			int val = ((int)md5Bytes[i])&0xff; 
			if(val < 16) 
			{ 
			hexValue.append("0"); 
			} 
			hexValue.append(Integer.toHexString(val)); 
		} 
		return hexValue.toString().toUpperCase(); 
	} 
	
	 public static String Change32Md5To16Md5(String key)
     {
         return key.substring(8, 24);
     }
	 
	 public static String MD5To16(String str){ 
		return Change32Md5To16Md5(MD5(str));
	 }
	 
	 /**
	  * 得到两位小数的价格
	  * @param price
	  * @return
	  */
	 public static double GetFormatPrice(double price){
		//价格要保留两位小数
		DecimalFormat priceFormat = new DecimalFormat("0.00");
		return Double.parseDouble(priceFormat.format(price)) ;
	 }
	 
	 public static double GetFormatPrice(String price){
		//价格要保留两位小数
		DecimalFormat priceFormat = new DecimalFormat("0.00");
		return Double.parseDouble(priceFormat.format(price)) ;
	 }
	 
	 public static String GetStrFormatPrice(double price){
		DecimalFormat priceFormat = new DecimalFormat("0.00");
		return "￥"+priceFormat.format(price) ;
	 }
	 
	 

	 /*
	  * 得到积分格式
	  */
	 public static String GetStrFormatJifen(double jifen){
		DecimalFormat priceFormat = new DecimalFormat("0.00");
		return priceFormat.format(jifen) + "分" ;
	 }
	 
	 /**
	  * 只得到两位小数的数字
	  * @param price
	  * @return
	  */
	 public static String GetStrFormatPrice00(double price){
			DecimalFormat priceFormat = new DecimalFormat("0.00");
			return priceFormat.format(price) ;
	 }
	 
	 /**
	  * 判断是否安装了这个软件
	  * @param context
	  * @param packageName
	  * @return
	  */
	 public static boolean checkApkExist(Context context, String packageName) {
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
	  * 判断是否为数字
	  * @param str
	  * @return
	  */
	public static boolean isNumeric(String str){ 
		 Pattern pattern = Pattern.compile("[0-9]*"); 
		 return pattern.matcher(str).matches(); 
	} 

	
}
