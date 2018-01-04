package com.hiveview.entity.bo;

import java.io.Serializable;

/**
 * 接口统一返回模型
 * 
 * @author wengjingchang
 *
 */
public class ApiResult implements Serializable {
	private static final long serialVersionUID = -5268557513325081029L;
	/**
	 * 系统响应状态码
	 */
	private String code = ApiResultTypeEnum.SUC.getCode();
	/**
	 * 系统响应描述
	 */
	private String desc = ApiResultTypeEnum.SUC.getType();
	/**
	 * 系统响应时间
	 */
	private Long time = System.currentTimeMillis();
	/**
	 * 系统响应返回值
	 */
	private Object result;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}
}
