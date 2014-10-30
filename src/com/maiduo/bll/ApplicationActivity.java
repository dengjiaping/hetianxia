package com.maiduo.bll;
/**
 * 保存activity数据。退出时用的
 * 
 * 页面创建时 MyApplication.getInstance().addActivity(this);
 * 退出整个程序时 MyApplication.getInstance().exit();
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
     //单例模式中获取唯一的MyApplication实例 
     public static ApplicationActivity getInstance()
     {
          if(null == instance)
          {
             instance = new ApplicationActivity();
          }
         return instance;             
     }
     //添加Activity到容器中
     public void addActivity(Activity activity)
     {
    	/* 
    	 * 现在还没找到更好的判断方法，先注释了
    	 * for(Activity ac:activityList)
         {
           if(ac.toString().split("@")[0].equals(activity.toString().split("@")[0])){
        	   return;
           }
         }*/
    	 
    	 
        activityList.add(activity);
     }
     
     //遍历所有Activity并finish
     public void exit()
     {
         for(Activity activity:activityList)
         {
           activity.finish();
         }
         //System.exit(0);
    }
     
     // 下面三个方法主要用户登录后返回之前Activity。
     /**
      * 保存要返回的Activity
      * @param activity
      */
     public void ReturnActivitySave(Context activity){
    	 returnActivity = activity;
     }
     /**
      * 清楚保存的返回Activity
      */
     public void ReturnActivityClear(){
    	 returnActivity = null;
     }
     /**
      * 检测是否有Actity需要返回
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
