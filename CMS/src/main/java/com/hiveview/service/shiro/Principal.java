/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package main.java.com.hiveview.service.shiro;

import java.io.Serializable;

/**
 * 身份信息
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
public class Principal implements Serializable {

	private static final long serialVersionUID = 1L;

	/** ID */
	private Integer id;

	/** 用户名 */
	private String username;
	
	private Integer roleId;

	/**
	 * @param id
	 *            ID
	 * @param username
	 *            用户名
	 */
	public Principal(Integer id, String username,Integer roleId) {
		this.id = id;
		this.username = username;
		this.roleId = roleId;
	}

	/**
	 * 获取ID
	 * 
	 * @return ID
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * 设置ID
	 * 
	 * @param id
	 *            ID
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 获取用户名
	 * 
	 * @return 用户名
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * 设置用户名
	 * 
	 * @param username
	 *            用户名
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	@Override
	public String toString() {
		return username;
	}

}