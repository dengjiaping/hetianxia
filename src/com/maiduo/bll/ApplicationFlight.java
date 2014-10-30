package com.maiduo.bll;
/**
 * ��Ʊ������Ҫ�Ĺ�������  by myx 20120816
 */
import android.app.Application;
import com.maiduo.model.*;

import java.util.ArrayList;
import java.util.List;

public class ApplicationFlight extends Application  {

	//��ѯ
	private FlightSearch search = new FlightSearch();
    
	//����
    private List<FlightInfo> flight = new ArrayList<FlightInfo>();

    //�˻���
	private List<PassengInfo> pass = new ArrayList<PassengInfo>();
    
    private String orderName;	//Ԥ��������
    private String orderMobile;	//Ԥ�����ֻ���
    
    private int bookTime = 1;// ��Ʊʱ��   1ֱ�ӳ�Ʊ    2�ݻ���Ʊ
	
    private String userId = "";// �����
	
   

	public static void setInstance(ApplicationFlight instance) {
		ApplicationFlight.instance = instance;
	}


	private static ApplicationFlight instance;
    private ApplicationFlight()
    {
    }
	
	 //����ģʽ�л�ȡΨһ��MyApplicationʵ�� 
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