package com.maiduo.bll;
/**
 * 机票订购需要的公共变量  by myx 20120816
 */
import android.app.Application;
import com.maiduo.model.*;

import java.util.ArrayList;
import java.util.List;

public class ApplicationFlight extends Application  {

	//查询
	private FlightSearch search = new FlightSearch();
    
	//航班
    private List<FlightInfo> flight = new ArrayList<FlightInfo>();

    //乘机人
	private List<PassengInfo> pass = new ArrayList<PassengInfo>();
    
    private String orderName;	//预订人姓名
    private String orderMobile;	//预订人手机号
    
    private int bookTime = 1;// 出票时间   1直接出票    2暂缓出票
	
    private String userId = "";// 代理号
	
   

	public static void setInstance(ApplicationFlight instance) {
		ApplicationFlight.instance = instance;
	}


	private static ApplicationFlight instance;
    private ApplicationFlight()
    {
    }
	
	 //单例模式中获取唯一的MyApplication实例 
    public static ApplicationFlight getInstance()
    {
         if(null == instance)
         {
            instance = new ApplicationFlight();
         }
        return instance;          
        
    }
    
    
   
    public FlightSearch getSearch() {
		return search;
	}

	public void setSearch(FlightSearch search) {
		this.search = search;
	}



	public List<FlightInfo> getFlight() {
		return flight;
	}

	public void setFlight(List<FlightInfo> flight) {
		this.flight = flight;
	}

	public List<PassengInfo> getPass() {
		return pass;
	}

	public void setPass(List<PassengInfo> pass) {
		this.pass = pass;
	}

	
	
	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public String getOrderMobile() {
		return orderMobile;
	}

	public void setOrderMobile(String orderMobile) {
		this.orderMobile = orderMobile;
	}

	public void Remove()
    {
		instance = null;
    }
	
	public void RemoveFlightInfo()
    {
		flight = new ArrayList<FlightInfo>();
    }
	
	public void RemoveSearch()
    {
		search = null;
    }
	
	

	public int getBookTime() {
		return bookTime;
	}

	public void setBookTime(int bookTime) {
		this.bookTime = bookTime;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}


	
	
    
    
     
}