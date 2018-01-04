package main.java.com.hiveview.entity.sys;

import java.io.Serializable;

import com.hiveview.entity.Entity;

public class SysUserSpecial extends Entity implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer userId;
	private Integer authForm;
	private String authCode;
	private String authName;
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the userId
	 */
	public Integer getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	/**
	 * @return the authForm
	 */
	public Integer getAuthForm() {
		return authForm;
	}
	/**
	 * @param authForm the authForm to set
	 */
	public void setAuthForm(Integer authForm) {
		this.authForm = authForm;
	}
	/**
	 * @return the authCode
	 */
	public String getAuthCode() {
		return authCode;
	}
	/**
	 * @param authCode the authCode to set
	 */
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}
	/**
	 * @return the authName
	 */
	public String getAuthName() {
		return authName;
	}
	/**
	 * @param authName the authName to set
	 */
	public void setAuthName(String authName) {
		this.authName = authName;
	}
	
}
