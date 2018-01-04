package com.hiveview.action.base;

import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.hiveview.pay.entity.bo.PayParams;

public class BaseAction {

	protected String PAY_REQUEST_PARAMS = "params";
	protected String PAY_REQUEST_MAC = "mac";
	protected String PAY_REQUEST_PARTNERID = "partnerid";
	
	/**
	 * 获取String类型请求参数列表
	 * @param request
	 * @return
	 * @throws Exception
	 */
	protected String getParameterString(HttpServletRequest request) {
		try {
			StringBuffer buffer = new StringBuffer("");
			InputStream is = request.getInputStream();
			byte[] b = new byte[1024];
			int len = -1;
			while ((len = is.read(b)) != -1) {
				buffer.append(new String(b, 0, len, "utf-8"));
			}
			return buffer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取HTTP请求数据
	 * @param request
	 * @return
	 * @throws Exception
	 */
	protected String getParameters(HttpServletRequest request) {
		try {
			StringBuffer buffer = new StringBuffer("");
			InputStream is = request.getInputStream();
			byte[] b = new byte[1024];
			int len = -1;
			while ((len = is.read(b)) != -1) {
				buffer.append(new String(b, 0, len, "utf-8"));
			}
			return buffer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 解析支付请求参数
	 * @param request
	 * @param beanType 参数类class
	 * @return
	 */
	protected final <T extends PayParams> T parse(HttpServletRequest request, Class<T> beanType) {
		String params = request.getParameter(PAY_REQUEST_PARAMS);
		String mac = request.getParameter(PAY_REQUEST_MAC);
		if (StringUtils.isEmpty(params)) {
			return null;
		}
		T bean = JSON.parseObject(params, beanType);
		bean.setIp(this.getRemoteAddr(request));//获取外网地址
		bean.setMac(mac);
		return bean;
	}
	
	/**
	 * 取得nginx代理之后的IP地址
	 * @param request
	 * @return
	 */
	protected String getRemoteAddr(HttpServletRequest request) {
		String ipFromNginx = request.getHeader("X-Real-IP");
		if (ipFromNginx == null || "".equals(ipFromNginx)) {
			return request.getRemoteAddr();
		} else {
			return ipFromNginx;
		}
	}
}
