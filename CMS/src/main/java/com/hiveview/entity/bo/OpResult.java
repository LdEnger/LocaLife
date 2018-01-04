package main.java.com.hiveview.entity.bo;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;
import com.hiveview.pay.entity.OpResultTypeEnum;

/**
 * Title：MVC统一返回模型
 * Description：
 * Company：hiveview.com
 * Author：郝伟革 
 * Email：haoweige@hiveview.com 
 * Mar 13, 2014
 */
public class OpResult implements Serializable {

	protected static final long serialVersionUID = 1L;
	/**
	 * 系统响应状态码
	 */
	protected String code = OpResultTypeEnum.SUCC.getCode();
	/**
	 * 系统响应描述
	 */
	protected String desc = OpResultTypeEnum.SUCC.getTypeName();
	/**
	 * 系统响应时间
	 */
	protected Long time = System.currentTimeMillis();
	/**
	 * 系统响应返回值
	 */
	protected Object result;

	public OpResult() {
	}

	public OpResult(Object result) {
		this.result = result;
	}

	public OpResult(OpResultTypeEnum opResultType) {
		this.code = opResultType.getCode();
		this.desc = opResultType.getTypeName();
	}

	public OpResult(OpResultTypeEnum opResultType, String exMessage) {
		this.code = opResultType.getCode();
		this.desc = exMessage;
	}

	public OpResult(OpResultTypeEnum opResultType, String exMessage, Object result) {
		this.code = opResultType.getCode();
		this.desc = exMessage;
		this.result = result;
	}

	/**
	 * 操作结果是否成功
	 * @return
	 */
	public boolean isSuccess() {
		String _code = OpResultTypeEnum.SUCC.getCode();
		return _code.equals(this.code);
	}

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

	public String toString() {
		return JSONObject.toJSONString(this);
	}

}

