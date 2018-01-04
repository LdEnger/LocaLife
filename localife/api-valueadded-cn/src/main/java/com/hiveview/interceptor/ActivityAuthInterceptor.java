package com.hiveview.interceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSON;
import com.hiveview.common.ParamConstants;
import com.hiveview.entity.bo.ApiResult;
import com.hiveview.entity.bo.ApiResultTypeEnum;
import com.hiveview.util.SHA1Utils;

public class ActivityAuthInterceptor extends HandlerInterceptorAdapter {
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String name = HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE;
		Map<?, ?> params = (Map<?, ?>) request.getAttribute(name);
		String timestamp = (String) params.get("timestamp");
		String sign = (String) params.get("sign");
		if (timestamp == null) {
			print(response, errorMsg("E001", "timestamp不能为空"));
			return false;
		}
		if (sign == null) {
			print(response, errorMsg("E001", "sign不能为空"));
			return false;
		}

		if (checkSign(timestamp, sign)) {
			print(response,
					errorMsg(ApiResultTypeEnum.ERR_2.getCode(),
							ApiResultTypeEnum.ERR_2.getType()));
			return false;
		}
		return true;
	}

	private boolean checkSign(String timestamp, String sign) {
		String[] array = new String[] { timestamp, ParamConstants.ACT_KEY };
		String input = sortString(array);
		String output = SHA1Utils.SHA1(input);
		if (output.equalsIgnoreCase(sign)) {
			return false;
		}
		return true;
	}

	private String sortString(String[] array) {
		List<String> values = new ArrayList<String>();
		for (String value : array) {
			values.add(value);
		}
		Collections.sort(values);
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < values.size(); i++) {
			buf.append(values.get(i));
		}
		return buf.toString();
	}

	private String errorMsg(String code, String desc) {
		ApiResult ar = new ApiResult();
		ar.setCode(code);
		ar.setDesc(desc);
		ar.setResult(new Object());
		String output = JSON.toJSONString(ar);
		return output;
	}

	protected void print(HttpServletResponse response, String output)
			throws IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter print = response.getWriter();
		print.write(output);
		print.flush();
		print.close();
	}

}
