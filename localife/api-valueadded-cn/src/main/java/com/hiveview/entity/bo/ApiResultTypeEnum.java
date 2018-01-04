package com.hiveview.entity.bo;

/**
 * @author wengjingchang
 *
 */
public enum ApiResultTypeEnum {
	SUC("N000", "调用成功"), ERR("E000", "系统错误"), ERR_1("E001", "参数不能为空"), ERR_2(
			"E002", "鉴权失败"), ERR_NULL("E003", "获取数据为空");

	private String code;
	private String type;

	private ApiResultTypeEnum(String code, String type) {
		this.code = code;
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
