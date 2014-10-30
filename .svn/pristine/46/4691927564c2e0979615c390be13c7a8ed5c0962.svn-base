package com.htx.action;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.util.Log;
import cn.anycall.map.HtxShopBean;
import cn.anycall.map.POISearchBean;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.htx.bean.AuctionListBean;
import com.htx.bean.AuctionListBeanNew;
import com.htx.bean.AuthBeanNew;
import com.htx.bean.BarcodeInfoBean;
import com.htx.bean.HotProductBean;
import com.htx.bean.ProductDetailBean;
import com.htx.bean.ProductDetailBeanNew;
import com.htx.bean.ReceiveBean;
import com.htx.bean.ReceiveBeanNew;
import com.htx.bean.RoadInfoSearchBean;
import com.htx.bean.SearchBean;
import com.htx.core.GetPOIInfo;
import com.htx.core.GetRoadInfo;
import com.htx.core.SignValue;
import com.htx.conn.Const;
import com.htx.conn.HttpManager;
import com.htx.core.GetBarcodeInfo;
import com.htx.model.ProductListBean;
import com.htx.bean.AuthBean;

/**
 * 
 * @author lvan
 *
 */
public class ProductAction {
	
	public static String imsi = "204046330839890";
	public static String imei = "a000003377e7b4";
    
	
	/**
	 * 搜索产品
	 * @param map
	 * @return
	 */
	public static SearchBean getSearchBean(Map<String, String> map){
		
		Log.d(Const.TAG, "ProductAction.getSearchBean|map="+map+",keyworld="+URLEncoder.encode(map.get("n")));
		
		SearchBean bean = null;
		
		InetAddress serverAddr = null;
	  	Socket socket = null;

	  	String str = "";
	  	String strcon = "search#"+map.get("p")+"#"+map.get("n")+"#none";
	  	
	  	try {
		    	serverAddr = InetAddress.getByName("hetianxia888.eicp.net");
	  			

		    	socket = new Socket(serverAddr, 21389);
	  	
		    	PrintWriter out = new PrintWriter( new BufferedWriter( new OutputStreamWriter(socket.getOutputStream())),true);
		    	out.println(strcon);
		    	out.flush();
		    	
		    	BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "GBK"));
	            str = in.readLine();          
	            socket.close();
		    	
	  	} catch(Exception e) {
	  	}
	  	
	  	if(str!=null && !"".equals(str)){
			Gson gson = new Gson();
			try{
				bean = gson.fromJson(str,new TypeToken<SearchBean>(){}.getType());
			}catch(JsonParseException e){
				Log.e(Const.TAG, "ProductAction.getSearchBean|JsonParseException",e);
			}
		}
		
		return bean;
		
	}
	
	
	
	/**
	 * 根据产品ID获取详情
	 * @param map
	 * @return
	 */
	public static ProductDetailBean getProductDetail(Map<String, String> map){

		Log.d(Const.TAG, "ProductAction.getProductDetail|map="+map);
		
		ProductDetailBean bean = null;

		ProductDetailBeanNew newbean = null;
		InetAddress serverAddr = null;
	  	Socket socket = null;

	  	String str = "";
	  	String strcon = "detail#"+map.get("product_id");
	  	
	  	try {
		    	serverAddr = InetAddress.getByName("hetianxia888.eicp.net");	  			

		    	socket = new Socket(serverAddr, 21389);
	  	
		    	PrintWriter out = new PrintWriter( new BufferedWriter( new OutputStreamWriter(socket.getOutputStream())),true);
		    	out.println(strcon);
		    	out.flush();
		    	
		    	BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "GBK"));
	            str = in.readLine();          
	            socket.close();
		    	
	  	} catch(Exception e) {
	  	}
	  	
	  	if(str!=null && !"".equals(str)){
			Gson gson = new Gson();
			try{
				newbean = gson.fromJson(str,new TypeToken<ProductDetailBeanNew>(){}.getType());
			}catch(JsonParseException e){
				Log.e(Const.TAG, "ProductAction.getDetailBean|JsonParseException",e);
			}
		}
	  	
	  	if(newbean != null)
	  	{
	  		bean = new ProductDetailBean();
	  		bean.setVersion("");
	  		bean.setTotalCount(newbean.getB2cTotalhits());
	  		bean.setReturnCount(newbean.getreturnB2cCount());
	  		bean.setSuccess(newbean.getResultMessage().equals("success")?"true":"false");
	  		bean.setSearchType("detail");
	  		bean.setResultMessage(newbean.getResultMessage());
	  		bean.setResultCode(newbean.getResultCode());
	  		bean.setOffset("");
	  		bean.setProductDO(new ProductListBean());
	  		bean.getProductDO().setBbCount("");
	  		bean.getProductDO().setCatId(newbean.getCatid());
	  		bean.getProductDO().setCatIdPath("");
	  		bean.getProductDO().setCmtCount(newbean.getCmtCount());
	  		bean.getProductDO().setCmtScore(newbean.getCmtScore());
	  		bean.getProductDO().setLwQuantity("");
	  		bean.getProductDO().setPictUrl(newbean.getPictUrl());
	  		bean.getProductDO().setPid(newbean.getEpid());
	  		bean.getProductDO().setPrice(newbean.getPrice());
	  		bean.getProductDO().setPriceRank("");
	  		bean.getProductDO().setProperty(newbean.getProperties());
	  		bean.getProductDO().setPropListStr("");
	  		bean.getProductDO().setSellerCount(newbean.getSellCount());
	  		bean.getProductDO().setSpuId("");
	  		bean.getProductDO().setStaticScore("");
	  		bean.getProductDO().setTitle(newbean.getTitle());
	  		bean.getProductDO().setYunfei("");
	  		
	  		bean.setAuctionList(new ArrayList<AuctionListBean>());
	  		for(AuctionListBeanNew spbean : newbean.getB2cresult()) {

	  			AuctionListBean tmpAuction = new AuctionListBean();
	  			tmpAuction.setCategory("");
	  			tmpAuction.setComUrlWapUrl("");
	  			tmpAuction.setEpid(newbean.getEpid());
	  			tmpAuction.setKuaijie("");
	  			tmpAuction.setLink(spbean.getLink());
	  			tmpAuction.setLinkWapUrl("");
	  			tmpAuction.setLoc(spbean.getLoc());
	  			tmpAuction.setNid(spbean.getNid());
	  			tmpAuction.setPic(spbean.getLogo());
	  			tmpAuction.setPrice(spbean.getPrice());
	  			tmpAuction.setPriceHistory("");
	  			tmpAuction.setRealPostFee(spbean.getPostFee());
	  			tmpAuction.setSales(spbean.getSales());
	  			tmpAuction.setTitle(newbean.getTitle());
	  			tmpAuction.setUserId(spbean.getUserId());
	  			tmpAuction.setUserNickName(spbean.getUserNickName());
	  			tmpAuction.setUserType(spbean.getUserType());
	  			
	  			bean.getAuctionList().add(tmpAuction);
	  		}
	  		
	  	}
		
		return bean;
		
	}
	
	/**
	 * 根据产品ID获取评论
	 * @param map
	 * @return
	 */
	public static ReceiveBean getProductReceive(Map<String, String> map){

		Log.d(Const.TAG, "ProductAction.getProductReceive|map="+map);
		
		ReceiveBean bean = null;		

		ReceiveBeanNew newbean = null;
		InetAddress serverAddr = null;
	  	Socket socket = null;

	  	String str = "";
	  	String strcon = "comment#" + map.get("p") + "#" + map.get("product_id");
	  	
	  	try {
		    	serverAddr = InetAddress.getByName("hetianxia888.eicp.net");	  			

		    	socket = new Socket(serverAddr, 21389);
	  	
		    	PrintWriter out = new PrintWriter( new BufferedWriter( new OutputStreamWriter(socket.getOutputStream())),true);
		    	out.println(strcon);
		    	out.flush();
		    	
		    	BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "GBK"));
	            str = in.readLine();          
	            socket.close();
		    	
	  	} catch(Exception e) {
	  	}
	  	
	  	if(str!=null && !"".equals(str)){
			Gson gson = new Gson();
			try{
				newbean = gson.fromJson(str,new TypeToken<ReceiveBeanNew>(){}.getType());
			}catch(JsonParseException e){
				Log.e(Const.TAG, "ProductAction.getReceiveBean|JsonParseException",e);
			}
		}
	  	
	  	if(newbean != null)
	  	{
	  		bean = new ReceiveBean();
	  		bean.setResultCode(newbean.getResultCode());
	  		bean.setResultMessage(newbean.getResultMessage());
	  		bean.setReturnCount(newbean.getReturnCount());
	  		bean.setSuccess(newbean.getResultMessage().equals("success")?"true":"false");
	  		bean.setTotalCount(newbean.getTotalCount());
	  		
	  		bean.setComments(new ArrayList<AuthBean>());
	  		for(AuthBeanNew spbean : newbean.getComments()) {

	  			AuthBean tmpComment = new AuthBean();
	  			tmpComment.setAuthor("");
	  			tmpComment.setBad("");
	  			tmpComment.setCtime("");
	  			tmpComment.setGood("");
	  			tmpComment.setScore(spbean.getScore());
	  			tmpComment.setSummary(spbean.getSummary());
	  			tmpComment.setUrl("");
	  			tmpComment.setUrlName("");
	  			
	  			bean.getComments().add(tmpComment);
	  		}
	  	}
		
		return bean;		
	}
	
	/**
	 * 根据产品ID获取商家列表
	 * @param map
	 * @return
	 */
	public static ProductDetailBean getProductShops(Map<String, String> map){

		Log.d(Const.TAG, "ProductAction.getProductShops|map="+map);

		ProductDetailBean bean = null;

		ProductDetailBeanNew newbean = null;
		InetAddress serverAddr = null;
	  	Socket socket = null;

	  	String str = "";
	  	String strcon = "detail#"+map.get("product_id");
	  	
	  	try {
		    	serverAddr = InetAddress.getByName("hetianxia888.eicp.net");	  			

		    	socket = new Socket(serverAddr, 21389);
	  	
		    	PrintWriter out = new PrintWriter( new BufferedWriter( new OutputStreamWriter(socket.getOutputStream())),true);
		    	out.println(strcon);
		    	out.flush();
		    	
		    	BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "GBK"));
	            str = in.readLine();          
	            socket.close();
		    	
	  	} catch(Exception e) {
	  	}
	  	
	  	if(str!=null && !"".equals(str)){
			Gson gson = new Gson();
			try{
				newbean = gson.fromJson(str,new TypeToken<ProductDetailBeanNew>(){}.getType());
			}catch(JsonParseException e){
				Log.e(Const.TAG, "ProductAction.getDetailBean|JsonParseException",e);
			}
		}
	  	
	  	if(newbean != null)
	  	{
	  		bean = new ProductDetailBean();
	  		bean.setVersion("");
	  		bean.setTotalCount(newbean.getB2cTotalhits());
	  		bean.setReturnCount(newbean.getreturnB2cCount());
	  		bean.setSuccess(newbean.getResultMessage().equals("success")?"true":"false");
	  		bean.setSearchType("detail");
	  		bean.setResultMessage(newbean.getResultMessage());
	  		bean.setResultCode(newbean.getResultCode());
	  		bean.setOffset("");
	  		bean.setProductDO(new ProductListBean());
	  		bean.getProductDO().setBbCount("");
	  		bean.getProductDO().setCatId(newbean.getCatid());
	  		bean.getProductDO().setCatIdPath("");
	  		bean.getProductDO().setCmtCount(newbean.getCmtCount());
	  		bean.getProductDO().setCmtScore(newbean.getCmtScore());
	  		bean.getProductDO().setLwQuantity("");
	  		bean.getProductDO().setPictUrl(newbean.getPictUrl());
	  		bean.getProductDO().setPid(newbean.getEpid());
	  		bean.getProductDO().setPrice(newbean.getPrice());
	  		bean.getProductDO().setPriceRank("");
	  		bean.getProductDO().setProperty(newbean.getProperties());
	  		bean.getProductDO().setPropListStr("");
	  		bean.getProductDO().setSellerCount(newbean.getSellCount());
	  		bean.getProductDO().setSpuId("");
	  		bean.getProductDO().setStaticScore("");
	  		bean.getProductDO().setTitle(newbean.getTitle());
	  		bean.getProductDO().setYunfei("");
	  		
	  		bean.setAuctionList(new ArrayList<AuctionListBean>());
	  		for(AuctionListBeanNew spbean : newbean.getB2cresult()) {

	  			AuctionListBean tmpAuction = new AuctionListBean();
	  			tmpAuction.setCategory("");
	  			tmpAuction.setComUrlWapUrl("");
	  			tmpAuction.setEpid(newbean.getEpid());
	  			tmpAuction.setKuaijie("");
	  			tmpAuction.setLink(spbean.getLink());
	  			tmpAuction.setLinkWapUrl("");
	  			tmpAuction.setLoc(spbean.getLoc());
	  			tmpAuction.setNid(spbean.getNid());
	  			tmpAuction.setPic(spbean.getLogo());
	  			tmpAuction.setPrice(spbean.getPrice());
	  			tmpAuction.setPriceHistory("");
	  			tmpAuction.setRealPostFee(spbean.getPostFee());
	  			tmpAuction.setSales(spbean.getSales());
	  			tmpAuction.setTitle(newbean.getTitle());
	  			tmpAuction.setUserId(spbean.getUserId());
	  			tmpAuction.setUserNickName(spbean.getUserNickName());
	  			tmpAuction.setUserType(spbean.getUserType());
	  			
	  			bean.getAuctionList().add(tmpAuction);
	  		}
	  		
	  	}
		
		return bean;
	}
	
	public static BarcodeInfoBean getBarcodeInformation(Map<String, String> map){
		
		Log.d(Const.TAG, "ProductAction.getBarcodeInformation|map="+map);
	
		BarcodeInfoBean bean = null;
	
//		List<NameValuePair> params = new ArrayList<NameValuePair>();
	
		String barcode = map.get("keyword");
		
//		params.add(new BasicNameValuePair("keyword", barcode));
		
//		String str = new HttpManager("http://setup.3533.com/ean/index").submitRequest(params);
		String str = GetBarcodeInfo.getinfo(barcode);
		if(str == null)
			return null;
		
		str = "{" + str;
		String s = "";
		
		try {   
	        JSONObject jsonObj = new JSONObject(str);   
	        if(jsonObj == null)
	        	return null;
	        
	        
	        s = jsonObj.toString();
	
	    } catch (JSONException e) {   
	        System.out.println("Json parse error");   
	        e.printStackTrace();   
	    }   
	
		if(s!=null && !"".equals(s)){
			Gson gson = new Gson();
			try{
				bean = gson.fromJson(s,new TypeToken<BarcodeInfoBean>(){}.getType());
			}catch(JsonParseException e){
				Log.e(Const.TAG, "ProductAction.getProductDetail|JsonParseException",e);
			}
		}
		
		return bean;
	}


	public static String getPriceInfo(String loc, String barcode, String ref){
		String str = GetBarcodeInfo.getmartprice(loc, barcode, ref);
		if(str == null)
			return null;
		return str;
	}
	
	
	public static POISearchBean getPOISearchBean(Context context, Map<String, String> map){
		
		POISearchBean bean = null;

		String query = map.get("query");
		String location = map.get("location");
		String radius = map.get("radius");
		String page_num = map.get("page_num");
		
		String str = GetPOIInfo.getinfo(context,query, location, radius, page_num);
		if(str == null)
			return null;

		if(str!=null && !"".equals(str)){
			Gson gson = new Gson();
			try{
				bean = gson.fromJson(str,new TypeToken<POISearchBean>(){}.getType());
			}catch(JsonParseException e){
			}
		}
		
		return bean;
	}
	
	public static HtxShopBean getHtxShop(Map<String, String> map){
		HtxShopBean bean = null;
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		
		params.add(new BasicNameValuePair("x", map.get("lng")));
		params.add(new BasicNameValuePair("y", map.get("lat")));
		params.add(new BasicNameValuePair("radius", map.get("radius")));
		
		String s = new HttpManager("http://www.36936.com/WorldClient/Guide/ClientRangeShop.ashx").submitRequest(params);
				
		if(s!=null && !"".equals(s)){
			s = s.replace("\" ", "\"");
			Gson gson = new Gson();
			try{
				bean = gson.fromJson(s,new TypeToken<HtxShopBean>(){}.getType());
			}catch(JsonParseException e){
				Log.e(Const.TAG, "ProductAction.getHtxShop|JsonParseException",e);
			}
		}
		return bean;
	}
	
	public static RoadInfoSearchBean getRoadInfoSearchBean(Map<String, String> map){
		
		RoadInfoSearchBean bean = null;

		String zoom = map.get("zoom");
		String minx = map.get("minx");
		String miny = map.get("miny");
		String maxx = map.get("maxx");
		String maxy = map.get("maxy");
		
		String str = GetRoadInfo.getinfo(zoom, minx, miny, maxx, maxy);
		if(str == null)
			return null;
		
		if(str!=null && !"".equals(str)){
			Gson gson = new Gson();
			try{
				bean = gson.fromJson(str,new TypeToken<RoadInfoSearchBean>(){}.getType());
			}catch(JsonParseException e){
			}
		}
		
		return bean;
	}
	
	
}
