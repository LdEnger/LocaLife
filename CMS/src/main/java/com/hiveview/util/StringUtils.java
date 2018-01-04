package main.java.com.hiveview.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.FastDateFormat;

public class StringUtils {


	public static String mapToLink(Map<String, String> map) {
		if (map == null) {
			return "";
		}
		List<String> keys = new ArrayList<String>(map.keySet());
		Collections.sort(keys);
		String prestr = "";
		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = map.get(key);
			if (StringUtils.isNotEmpty(value)) {//去除空值
				prestr = prestr + "&" + key + "=" + value;
			}
		}
		return prestr.substring(1);
	}

	public static Map<String, String> linkToMap(String link) {
		if (link == null) {
			return null;
		}
		Map<String, String> map = new HashMap<String, String>();
		String[] entrys = link.split("&");
		for (String entry : entrys) {
			String[] _entry = entry.split("=");
			if (_entry.length == 2) {
				map.put(_entry[0], _entry[1]);
			}
		}
		return map;
	}

	/**
	 * 转成键值对字符串
	 * 
	 * @param params
	 * @return
	 */
	public static String toQueryString(Map<String, Object> params) {
		StringBuffer buffer = new StringBuffer();
		Iterator<String> iterator = params.keySet().iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			Object val = params.get(key);
			buffer.append("&").append(key).append("=").append(val);
		}
		return buffer.substring(1);
	}
	
	public static boolean isEmpty(String string) {
		if (string == null || string.trim().length() == 0) {
			return true;
		}
		return false;
	}

	public static boolean isNotEmpty(String string) {
		if (string != null && string.length() > 0) {
			return true;
		}
		return false;
	}
	
	public static String getRealTimeString(){
		FastDateFormat format= FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		return format.format(calendar.getTime());	
	}

	public static void main(String[] args) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("H", "1");
		map.put("a", "2");
		map.put("i", "3");
		map.put("g", "4");
		map.put("T", "");
		map.put("E", "5");
		map.put("S", null);
		map.put("t", "6");
		System.out.println(StringUtils.mapToLink(map));
	}
}
