package com.htx.conn;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.htx.core.CacheManager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class ImageUtil {
	
	
	/**
	 * 将Bitmap对象存储为本地图片-通用
	 * 返回SD卡存储路径
	 */
    public static String saveToSDCard(Bitmap coverBitmap,String _filename){
    	
    	String localPicPath = null;
    	if(coverBitmap!=null){
			
			FileUtils fu = new FileUtils();
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			//得到输出流
			coverBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
			
			InputStream ins = new ByteArrayInputStream(baos.toByteArray());
			
			File file = fu.writeFile2SDFromInput(Const.SD_DIR, _filename, ins);
			
			localPicPath = file.getPath();
			
			Log.d(Const.TAG, "ImageUtil.getLocalUrl|filename="+_filename+",localPicPath="+localPicPath);
		}
    	return localPicPath;
    	
    }
    
    
	/**
	 * 格式化图片URL，用于获取不同分辨率的图片URL地址，仅限本软件使用
	 * @param thePic
	 * @param isBig
	 * @return
	 */
	public static String getPicUrl(String thePic,int dp){
		
		StringBuffer res = new StringBuffer();
		
		String fileName = "";
		String fileType = "";
		
		if(thePic!=null && !"".equals(thePic)){
			
			int position = thePic.lastIndexOf(".");
			
			if(position!=-1){
				fileName = thePic.substring(0, position);
				fileType = thePic.substring(position, thePic.length());
			}
		
		}
		
		if(dp==1){
			res.append(fileName).append(CacheManager.getInstance().getPic_s_option()).append(fileType);
		}else if(dp==2){
			res.append(fileName).append(CacheManager.getInstance().getPic_m_option()).append(fileType);
		}else{
			res.append(fileName).append(CacheManager.getInstance().getPic_b_option()).append(fileType);
		}
		
		return res.toString();
	}
	
	
	/**
	 * 获取网络图片-通用
	 * @param urlpath
	 * @return
	 */
	public static Bitmap getBitmap(String urlpath){
		Bitmap res_bitMap = null;
		if(urlpath!=null && !"".equals(urlpath)){
			try {
				byte[] _b = getUrlBytes(urlpath);
				res_bitMap = BitmapFactory.decodeByteArray(_b, 0, _b.length);  
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return res_bitMap;
	}
	
	/**
	* 加载本地图片-通用
	* @param url
	* @return
	*/
	public static Bitmap getLoacalBitmap(String urlpath) {
		try {
			FileInputStream fis = new FileInputStream(urlpath);
			return BitmapFactory.decodeStream(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Bitmap转换为byte[]
	 * @param bm
	 * @return
	 */
	public static  byte[] bitmap2Bytes(Bitmap bm){
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();  
	} 
	/**
	 * 获取指定路径的Byte[]数据-通用
	 * @param urlpath
	 * @return byte[]
	 * @throws Exception
	 */
    public static byte[] getUrlBytes(String urlpath) throws Exception {
    	InputStream in_s = getUrlInputStream(urlpath);
        return readStream(in_s);
    }
	
	/**
	 * 获取指定路径的InputStream数据-通用
	 * @param urlpath
	 * @return byte[]
	 * @throws Exception
	 */
    public static InputStream getUrlInputStream(String urlpath) throws Exception {  
        URL url = new URL(urlpath);  
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
        conn.setRequestMethod("GET");  
        //conn.setRequestMethod("POST");  
        conn.setConnectTimeout(Const.TIMEOUT_15);//10秒超时
        if(conn.getResponseCode()==HttpURLConnection.HTTP_OK){//返回码200等于返回成功
            InputStream inputStream=conn.getInputStream();  
            return inputStream;
        }
        return null;  
    }  
    
    /**
     * 从InputStream中读取数据-通用
     * @param inStream
     * @return byte[]
     * @throws Exception
     */
    public static byte[] readStream(InputStream inStream) throws Exception {  
        ByteArrayOutputStream outstream=new ByteArrayOutputStream();  
        byte[] buffer=new byte[128];  
        int len=-1;  
        while((len=inStream.read(buffer)) !=-1){  
            outstream.write(buffer, 0, len);  
        }
        outstream.close();  
        inStream.close();
        return outstream.toByteArray();  
    }  
	    
}
