package com.hiveview.action.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hiveview.common.ParamConstants;
import com.hiveview.util.SHA1Utils;

@Controller
@RequestMapping(value = "/api/doc")
public class ActivityApiDocAction {
	@RequestMapping(value = "/show")
	public ModelAndView show() {
		Long times = System.currentTimeMillis();
		String timestamp = times.toString();
		String[] array = new String[] { timestamp, ParamConstants.ACT_KEY };
		String input = sortString(array);
		String sign = SHA1Utils.SHA1(input);
		Map<String, String> m = new HashMap<String, String>();
		m.put("timestamp", timestamp);
		m.put("sign", sign);
		ModelAndView mv = new ModelAndView("api/award_api", m);
		return mv;
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
}
