package cn.anycall.map;

import android.app.Activity;

import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.RouteOverlay;

public class MyRouteOverlay extends RouteOverlay {

	public MyRouteOverlay(Activity arg0, MapView arg1) {
		super(arg0, arg1);
		
	}

	@Override
	protected boolean onTap(int arg0) {
		return true;
	}

	

}
