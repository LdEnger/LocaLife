package com.hiveview.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;


/**
 * IP工具类
 * @author maxiaoqiang
 *
 */

public class IPUtil {
	
	public static void main(String[] args) {
		String IPString = "255.255.255.255";
		System.out.println("IP:" + IPString + "是IP：" + isIp(IPString));
		System.out.println("IP2LONG==> IP:" + IPString + " LONG:" + ipToLong(IPString));
		Long longString = 1020157951l;
		System.out.println("LONG2IP==> IP:" + longToIP(longString) + " LONG:" + longString);
	}
	
	/**
	 * IP地址转换成十进制整数
	 * @param strIp:IP地址
	 * @return
	 */
	public static long ipToLong(String strIp) {
		long[] ip = new long[4];
		// 先找到IP地址字符串中.的位置
		int position1 = strIp.indexOf(".");
		int position2 = strIp.indexOf(".", position1 + 1);
		int position3 = strIp.indexOf(".", position2 + 1);
		// 将每个.之间的字符串转换成整型
		ip[0] = Long.parseLong(strIp.substring(0, position1));
		ip[1] = Long.parseLong(strIp.substring(position1 + 1, position2));
		ip[2] = Long.parseLong(strIp.substring(position2 + 1, position3));
		ip[3] = Long.parseLong(strIp.substring(position3 + 1));
		return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];
	}

	/**
	 * 将十进制整数形式转换成ip地址
	 * @param longIp：long
	 * @return
	 */
	public static String longToIP(long longIp) {
		StringBuffer sb = new StringBuffer("");
		// 直接右移24位
		sb.append(String.valueOf((longIp >>> 24)));
		sb.append(".");
		// 将高8位置0，然后右移16位
		sb.append(String.valueOf((longIp & 0x00FFFFFF) >>> 16));
		sb.append(".");
		// 将高16位置0，然后右移8位
		sb.append(String.valueOf((longIp & 0x0000FFFF) >>> 8));
		sb.append(".");
		// 将高24位置0
		sb.append(String.valueOf((longIp & 0x000000FF)));
		return sb.toString();
	}

	/**
	 * 判断是否是合法IP地址
	 * @param ipAddress ip地址
	 * @return true：合法	false：不合法
	 */
	public static boolean isIp(String ipAddress){
		//String test = "([1-9]|[1-9]//d|1//d{2}|2[0-1]//d|22[0-3])(//.(//d|[1-9]//d|1//d{2}|2[0-4]//d|25[0-5])){3}";
		String test = "^(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])$";
		Pattern pattern = Pattern.compile(test);
		Matcher matcher = pattern.matcher(ipAddress);
		return matcher.matches();
	}
	
	/**
	 * 获取IP地址
	 * @param request
	 * @return
	 */
	/*public static String getIpAddress(HttpServletRequest request) {
		String ipFromNginx = request.getHeader("X-Forwarded-For");
		if (StringUtils.isBlank(ipFromNginx)) {
			ipFromNginx = request.getHeader("Proxy-Client-IP");
		}
		if (StringUtils.isBlank(ipFromNginx)) {
			ipFromNginx = request.getHeader("WL-Proxy-Client-IP");
		}
		if (StringUtils.isBlank(ipFromNginx)) {
			ipFromNginx = request.getRemoteAddr();
		}
		int idx = ipFromNginx.indexOf(",");
		return idx > 0 ? ipFromNginx.substring(0, idx) : ipFromNginx;
	}*/
	public static String getIpAddress(HttpServletRequest request) {
			String ip = request.getHeader("X-Forwarded-For");
			if (!checkIp(ip)) {
				ip = request.getHeader("Proxy-Client-IP");
			}
			if (!checkIp(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");
			}
			if (!checkIp(ip)) {
				ip = request.getRemoteAddr();// 
			}
			return ip;	
	}
	
	/**
	 * 判断是否是IP地址
	 * @param ip
	 * @return
	 */
	private static boolean checkIp(String ip) {
		if (ip == null || ip.length() == 0 || "unkown".equalsIgnoreCase(ip)) {
			return false;
		}
		return true;
	}
	
	
	
}
