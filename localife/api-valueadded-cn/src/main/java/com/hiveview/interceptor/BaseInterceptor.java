package com.hiveview.interceptor;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSON;
import com.hiveview.pay.entity.bo.OpResult;

/**
 * Title：拦截器基类
 * Description：
 * Company：hiveview.com
 * Author：郝伟革 
 * Email：haoweige@hiveview.com 
 * Mar 17, 2014
 */
public class BaseInterceptor extends HandlerInterceptorAdapter {

	/**
	 * 输出信息
	 * @param response
	 * @param output 信息
	 * @throws IOException
	 */
	protected void print(HttpServletResponse response, String output) throws IOException {
		PrintWriter print = response.getWriter();
		print.write(output);
		print.flush();
		print.close();
	}

	/**
	 * 输出响应结果 
	 * @param response
	 * @param opResult
	 * @throws IOException 
	 */
	protected void print(HttpServletResponse response, OpResult opResult) throws IOException {
		String output = JSON.toJSONString(opResult);
		this.print(response, output);
	}

}
