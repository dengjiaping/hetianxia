package com.htx.search;

import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.util.regex.Pattern;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

/**
 * 
 * @author lvan
 *
 */
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
	 * md5���� ���ش�д
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
	  * �õ���λС��ļ۸�?
	  * @param price
	  * @return
	  */
	 public static double GetFormatPrice(double price){
		//�۸�Ҫ������λС��
		DecimalFormat priceFormat = new DecimalFormat("0.00");
		return Double.parseDouble(priceFormat.format(price)) ;
	 }
	 
	 public static double GetFormatPrice(String price){
		//�۸�Ҫ������λС��
		DecimalFormat priceFormat = new DecimalFormat("0.00");
		return Double.parseDouble(priceFormat.format(price)) ;
	 }
	 
	 public static String GetStrFormatPrice(double price){
		DecimalFormat priceFormat = new DecimalFormat("0.00");
		return "��"+priceFormat.format(price) ;
	 }
	 
	 

	 /*
	  * �õ���ָ��?
	  */
	 public static String GetStrFormatJifen(double jifen){
		DecimalFormat priceFormat = new DecimalFormat("0.00");
		return priceFormat.format(jifen) + "��" ;
	 }
	 
	 /**
	  * ֻ�õ���λС�������?
	  * @param price
	  * @return
	  */
	 public static String GetStrFormatPrice00(double price){
			DecimalFormat priceFormat = new DecimalFormat("0.00");
			return priceFormat.format(price) ;
	 }
	 
	 /**
	  * �ж��Ƿ�װ��������
	  * @param context
	  * @param packageName
	  * @return
	  */
	 public static boolean checkApkExist(Context context, String packageName) {
			ApplicationInfo info = null;
			try {
				 info = context.getPackageManager().getApplicationInfo(packageName,PackageManager.GET_UNINSTALLED_PACKAGES);
			} catch (NameNotFoundException e) {
				
			}
			if(info == null){
				return false;
	        }else{
	        	return true;
	        }
	}
	 /**
	  * �ж��Ƿ�Ϊ����
	  * @param str
	  * @return
	  */
	public static boolean isNumeric(String str){ 
		 Pattern pattern = Pattern.compile("[0-9]*"); 
		 return pattern.matcher(str).matches(); 
	} 
	
	
	


}
