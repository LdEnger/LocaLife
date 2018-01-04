package com.hiveview.common;

import com.singularsys.jep.functions.Str;

import java.util.ResourceBundle;

public class ApiConstants extends EnvConstants {

	private static ResourceBundle R = ResourceBundle.getBundle(ENV_VER + "_api");

    //上传图片路径与地址
	public static String UPLOAD_PATH = R.getString("upload.path");
	public static String WEBPATH = R.getString("file.path");
	//域名常量
    public static String PASSPORT_URL = R.getString("service.passport");//用户系统
	public static String API_UTIL = R.getString("service.util");//api.util地区信息
	public static String API_UTIL_US = R.getString("service.util.us");//api.util地区信息


/*	public static String API_UTIL = R.getString("service.util");//api.util地区信息*/
	//接口常量
    public static String USER_API = PASSPORT_URL + "api/user/getUserInfo.json";
	public static String API_UTIL_AREA_US = API_UTIL_US+"api/ip/getAreaCodeFormIpDb/{0}.json";
	public static String API_UTIL_AREA = API_UTIL_US+"api/ip/getAreaCodeFormIpDb/{0}.json";

	public static String API_ZONE_TREE_PID = API_UTIL_US+"api/ip/getAreaCodeFormIpDb/";

	public static void main(String[] args) {
		System.out.println("UPLOAD_PATH=>" + UPLOAD_PATH);
		System.out.println("API_UTIL_AREA=>"+API_UTIL_AREA);
		System.out.println("API_UTIL_AREA_US=>" + API_UTIL_AREA_US);
	}
}
