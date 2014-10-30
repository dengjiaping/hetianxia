package cn.anycall.map;

public class ConvertTool {
	public static String distanceToString(int distance) {
		StringBuffer sb = new StringBuffer();
		if (distance / 1000 != 0) {
			sb.append((distance / 1000) + "." + (distance % 1000 / 100) + "km");
		} else {
			sb.append(distance + "m");
		}
		return sb.toString();
	}

	public static String timeToString(int time) {
		StringBuffer sb = new StringBuffer();
		if (time / 3600 != 0) {
			sb.append(time / 3600 + "小时");
		}
		if (time % 3600 / 60 != 0) {
			sb.append(time % 3600 / 60 + "分钟");
		}
		return sb.toString();
	}
}
