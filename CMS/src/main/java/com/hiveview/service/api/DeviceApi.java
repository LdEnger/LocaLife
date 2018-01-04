package main.java.com.hiveview.service.api;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.hiveview.common.ApiConstants;

/**
 * 设备接口
 * 
 * @author huilet
 *
 */
@Service
public class DeviceApi {
	public String getDeviceModel(String mac, String sn) {
		//
		JSONObject jo = getDeviceInfo(mac, sn);
		if (jo != null) {
			return jo.getString("hardWareName");
		}
		return "DM1001";
	}

	private JSONObject getDeviceInfo(String mac, String sn) {
		try {
			RestTemplate restTemplate = new RestTemplate();
			String dataResult = restTemplate.getForObject(ApiConstants.DEVICE_GET_API, String.class, mac, sn);
			if (!StringUtils.isEmpty(dataResult)) {
				JSONObject dataJo = JSONObject.parseObject(dataResult);
				if (dataJo != null) {
					JSONObject objJo = JSONObject.parseObject(dataJo.getString("obj"));
					return objJo;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
