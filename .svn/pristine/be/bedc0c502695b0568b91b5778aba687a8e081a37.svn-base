package com.htx.search;

import java.util.LinkedList;
import java.util.List;
import android.app.Activity;
import android.app.Application;
import android.content.Context;

/**
 * 
 * @author lvan
 *
 */
public class ApplicationActivity extends Application {
	
	private List<Activity> activityList = new LinkedList<Activity>();
	private Context returnActivity = null;
	private static ApplicationActivity instance;
	private Context mContext;

	private ApplicationActivity() {
	}

	public static ApplicationActivity getInstance() {
		if (null == instance) {
			instance = new ApplicationActivity();
		}
		return instance;
	}

	public void addActivity(Activity activity) {
		activityList.add(activity);
	}

	public void exit() {
		for (Activity activity : activityList) {
			activity.finish();
		}
		System.exit(0);
	}

	public void ReturnActivitySave(Context activity) {
		returnActivity = activity;
	}

	public void ReturnActivityClear() {
		returnActivity = null;
	}

	public boolean ReturnActivityCheck() {
		if (returnActivity != null) {
			return true;
		} else {
			return false;
		}
	}
}
