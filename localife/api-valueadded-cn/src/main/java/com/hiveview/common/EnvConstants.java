package com.hiveview.common;

import java.util.ResourceBundle;

public class EnvConstants {

	private static final String PROFILE = ResourceBundle.getBundle("env").getString("pro");
	
	protected static final String ENV_ROOT = "conf/";
	protected static final String PRO_VER = PROFILE+"/"+PROFILE;
	protected static final String ENV_VER = ENV_ROOT + PRO_VER;

	public static void main(String[] args) {
		System.out.println("当前环境版本   " + ENV_VER);
	}
}
