package com.htx.pub;

import java.util.Stack;

import android.app.Activity;
import android.content.Context;

/**
 * 应用程序Activity管理类
 * @author  lvan
 * 
 * @使用方法 
 * Activity启动时，在的onCreate方法里面，将该Activity实例添加到AppManager的堆栈 
 * 		ActivityManager.getAppManager().addActivity(this);
 * 需要退出程序时，调用 
 * 		ActivityManager.getAppManager().AppExit(this);
 * 
 */
public class ActivityManager {
	
	private static Stack<Activity> activityStack;
	private static ActivityManager instance;
	
	private ActivityManager(){}
	/**
	 * 单一实例
	 */
	public static ActivityManager getAppManager(){
		if(instance==null){
			instance=new ActivityManager();
		}
		return instance;
	}
	/**
	 * 添加Activity到堆栈
	 */
	public void addActivity(Activity activity){
		if(activityStack==null){
			activityStack=new Stack<Activity>();
		}
		activityStack.add(activity);
	}
	/**
	 * 获取当前Activity（堆栈中最后一个压入的）
	 */
	public Activity currentActivity(){
		Activity activity=activityStack.lastElement();
		return activity;
	}
	/**
	 * 结束指定的Activity
	 */
	public void finishActivity(Activity activity){
		if(activity!=null){
			activityStack.remove(activity);
			activity.finish();
			activity=null;
		}
	}
	/**
	 * 结束指定类名的Activity
	 */
	public void finishActivity(Class<?> cls){
		for (Activity activity : activityStack) {
			if(activity.getClass().equals(cls) ){
				finishActivity(activity);
			}
		}
	}
	/**
	 * 结束所有Activity
	 */
	public void finishAllActivity(){
		for (int i = 0, size = activityStack.size(); i < size; i++){
            if (null != activityStack.get(i)){
            	activityStack.get(i).finish();
            }
	    }
		activityStack.clear();
	}
	/**
	 * 退出应用程序
	 */
	public void AppExit(Context context) {
		try {
			finishAllActivity();
			System.exit(0);
		} catch (Exception e) {
			
		}
	}
}