package com.htx.conn;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import java.net.URI;  
import java.net.URISyntaxException;  

import android.util.Log;

public class HttpManager {
	
	private HttpClient httpclient;
	private HttpPost httpPost;
	private HttpGet  httpGet;
	private HttpResponse httpResponse;
	
	private String url;

	public HttpManager(String url_in) {
		if(url_in.equals("http://www.yeezhao.com/mobilefile/index.json")){
			return ;
		}
		init(url_in);
	}
	
	/**
	 * 初始化参数
	 * @param url
	 */
	private void init(String url_in){
		this.url = url_in;
		
		Log.d(Const.TAG, "HttpManager.init|url="+url_in);
		
		try {
			httpclient = new DefaultHttpClient();
		    httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, Const.TIMEOUT_15);//请求超时
		    httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, Const.TIMEOUT_15); //读取超时
		    Log.d(Const.TAG, "HttpManager.init||url="+url);
		    httpPost = new HttpPost(url);
		    httpGet = new HttpGet(url);
		    Log.e("A---HttpManager---A", url);
		    httpGet.addHeader("Accept-Encoding", "gzip");
		}catch (Exception e) {
			httpPost.abort();
			Log.e(Const.TAG, "网络连接不正常!");
        }
	}
	
	
	
	
	/**
	 * 提交HTTP请求
	 * @param params 请求参数
	 * @return
	 */
	public String submitRequest(List<NameValuePair> params){
		
		String res_str = null;
		
		if(httpclient!=null && httpGet!=null && !httpGet.isAborted()){
			
			//设置httpPost请求参数及编码
			try {
			//	httpGet.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
				String str = EntityUtils.toString(new UrlEncodedFormEntity(params, HTTP.UTF_8));
				httpGet.setURI(new URI(httpGet.getURI().toString() + "?" + str));
				Log.d("AAA", "HttpManager.init|||url="+httpGet.getURI().toString() + "?" + str);
				Log.d("AAA", "HttpManager.init|||url="+httpGet.getURI().toString() + "?" + str);
				Log.d("AAA", "HttpManager.init|||url="+httpGet.getURI().toString() + "?" + str);
				Log.d("AAA", "HttpManager.init|||url="+httpGet.getURI().toString() + "?" + str);
				Log.d("AAA", "HttpManager.init|||url="+httpGet.getURI().toString() + "?" + str);
			} catch (UnsupportedEncodingException e1) {
				Log.e(Const.TAG, "输入参数编码有问题:UnsupportedEncodingException");
				return res_str;
			} catch (IOException e) {  
	            e.printStackTrace();  
	        } catch (URISyntaxException e) {  
	            e.printStackTrace();  
	        }
			
			//第二步，使用execute方法发送HTTP GET请求，并返回HttpResponse对象
			try {
				httpResponse =httpclient.execute(httpGet);
			} catch (ClientProtocolException e) {
				Log.e(Const.TAG, "服务器无应答:ClientProtocolException");
				return res_str;
			} catch (IOException e) {
				Log.e(Const.TAG, "服务器无应答:IOException");
				return res_str;
			} catch (Exception e) { 
				Log.e(Const.TAG, "服务器无应答:OtherException"); 
				return res_str;
	        }
			
			if(httpResponse!=null && (httpResponse.getStatusLine().getStatusCode() == 200)){
			
				//第三步，使用getEntity方法活得返回结果
			    try {			
			    	HttpEntity httpEntity = httpResponse.getEntity();
			    	
			    	if (httpEntity.getContentEncoding()!=null &&httpEntity.getContentEncoding().getValue()!=null && httpEntity.getContentEncoding().getValue().contains("gzip")) 
			    	{
			    	    	InputStream is= httpEntity.getContent();
			    	        is= new GZIPInputStream(is);
			    	        BufferedReader br = new BufferedReader(new InputStreamReader(is, HTTP.UTF_8));
			    	        String line = null;
			    	        StringBuffer sb = new StringBuffer();
			    	        while((line = br.readLine())!=null) 
			    	        {
			    	        	sb.append(line);
			    	        }
			    	        res_str = sb.toString();
			    	} 
			    	else 
			    	{
			    	    	// 获取服务器响应字符串
			    		res_str = EntityUtils.toString(httpEntity, HTTP.UTF_8);
			    	}
				//	res_str = EntityUtils.toString(httpResponse.getEntity(),HTTP.UTF_8);
				} catch (ParseException e) {
					Log.e(Const.TAG, "返回结果转换异常:ParseException");
					e.printStackTrace();
				}catch (IOException e) {
					Log.e(Const.TAG, "返回结果输出异常:IOException");
				}catch (Exception e) {
					Log.e(Const.TAG, "返回结果异常:OtherException");
					
		        }
			}
			
			httpGet.abort();
			
			httpclient.getConnectionManager().closeExpiredConnections();
			
		}
		
		Log.d(Const.TAG, "HttpManager.res_str="+res_str);
		
		return res_str;
	}
	
	
	/**
	 * 获取返回内容长度
	 * @return
	 */
	public long getContentLength(){
		
		long len = -1;
				
		if(httpclient!=null && httpPost!=null && !httpPost.isAborted()){
			
			//设置httpPost请求参数及编码
			try {
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			} catch (UnsupportedEncodingException e1) {
				Log.e(Const.TAG, "getContentLength 输入参数编码有问题:UnsupportedEncodingException");
				return len;
			}
			
			//第二步，使用execute方法发送HTTP POST请求，并返回HttpResponse对象
			try {
				httpResponse =httpclient.execute(httpPost);
			} catch (ClientProtocolException e) {
				Log.e(Const.TAG, "getContentLength 服务器无应答:ClientProtocolException");
			} catch (IOException e) {
				Log.e(Const.TAG, "getContentLength 服务器无应答:IOException");
			} catch (Exception e) { 
				Log.e(Const.TAG, "getContentLength 服务器无应答:OtherException"); 
	        }
			
			if(httpResponse!=null && (httpResponse.getStatusLine().getStatusCode() == 200)){
			
				//第三步，使用getEntity方法活得返回结果
			    try {
			    	HttpEntity httpEntity = httpResponse.getEntity();
			    	len = httpEntity.getContentLength();
				} catch (Exception e) {
					Log.e(Const.TAG, "getContentLength 返回结果异常:OtherException"); 
				}
			}
			
			httpPost.abort();
			
			httpclient.getConnectionManager().closeExpiredConnections();
			
		}
		
		Log.d(Const.TAG, "HttpManager.getContentLength|len="+len);
		
		return len;
	}
}
