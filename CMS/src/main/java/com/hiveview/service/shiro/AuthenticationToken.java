/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package main.java.com.hiveview.service.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * 登录令牌
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
public class AuthenticationToken extends UsernamePasswordToken {

	private static final long serialVersionUID = 5898441540965086534L;

	/** 验证码 */
	private String kaptcha;

	/**
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @param captchaId
	 *            验证码ID
	 * @param kaptcha
	 *            验证码
	 * @param rememberMe
	 *            记住我
	 * @param host
	 *            IP
	 */
	public AuthenticationToken(String username, String password, String kaptcha, boolean rememberMe, String host) {
		super(username, password, rememberMe, host);
		this.kaptcha = kaptcha;
	}


	public String getKaptcha() {
		return kaptcha;
	}

	public void setKaptcha(String kaptcha) {
		this.kaptcha = kaptcha;
	}

}