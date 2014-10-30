package com.maiduo.bll;
/**
 * ����activity���ݡ��˳�ʱ�õ�
 * 
 * ҳ�洴��ʱ MyApplication.getInstance().addActivity(this);
 * �˳���������ʱ MyApplication.getInstance().exit();
 * 
 */
import java.util.LinkedList;
import java.util.List;




import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;

public class ApplicationActivity extends Application {

private List<Activity> activityList = new LinkedList<Activity>(); 
private Context returnActivity = null;
private static ApplicationActivity instance;
private Context mContext;
    private ApplicationActivity()
    {
    }
     //����ģʽ�л�ȡΨһ��MyApplicationʵ�� 
     public static ApplicationActivity getInstance()
     {
          if(null == instance)
          {
             instance = new ApplicationActivity();
          }
         return instance;             
     }
     //���Activity��������
     public void addActivity(Activity activity)
     {
    	/* 
    	 * ���ڻ�û�ҵ����õ��жϷ�������ע����
    	 * for(Activity ac:activityList)
         {
           if(ac.toString().split("@")[0].equals(activity.toString().split("@")[0])){
        	   return;
           }
         }*/
    	 
    	 
        activityList.add(activity);
     }
     
     //��������Activity��finish
     public void exit()
     {
         for(Activity activity:activityList)
         {
           activity.finish();
         }
         //System.exit(0);
    }
     
     // ��������������Ҫ�û���¼�󷵻�֮ǰActivity��
     /**
      * ����Ҫ���ص�Activity
      * @param activity
      */
     public void ReturnActivitySave(Context activity){
    	 returnActivity = activity;
     }
     /**
      * �������ķ���Activity
      */
     public void ReturnActivityClear(){
    	 returnActivity = null;
     }
     /**
      * ����Ƿ���Actity��Ҫ����
      * @return
      */
     public boolean ReturnActivityCheck(){
    	 if(returnActivity != null){
    		 return true;
    	 }else{
    		 return false;
    	 }
     }
}
