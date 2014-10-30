package cn.anycall.map;

import android.app.Activity;

import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.TransitOverlay;

public class MyTransitOverlay extends TransitOverlay {

	public MyTransitOverlay(Activity arg0, MapView arg1) {
		super(arg0, arg1);
		
	}

	@Override
	protected boolean onTap(int arg0) {
		return true;
	}



}
