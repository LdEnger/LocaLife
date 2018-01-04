package main.java.com.hiveview.service.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hiveview.common.ApiConstants;
import com.hiveview.entity.vo.CityVo;
import com.hiveview.entity.vo.ProvinceVo;
import com.hiveview.service.RedisService;

@Service
public class AreasApi {
	
	@Autowired
	RedisService redisService;
	
	RestTemplate restTemplate =new RestTemplate();
	
	public List<ProvinceVo> getProvinceCodeService(){
		String PROVINCE_KEY = "area_province_all";
		List<ProvinceVo> provinceVoList = null;
		if(redisService.exists(PROVINCE_KEY)){
			provinceVoList = redisService.lRange(PROVINCE_KEY, 0L, -1L, ProvinceVo.class);	
			if(provinceVoList!=null){
				return provinceVoList;
			}
		}
		synchronized(PROVINCE_KEY ){
			if(redisService.exists(PROVINCE_KEY)){
				provinceVoList = redisService.lRange(PROVINCE_KEY, 0L, -1L, ProvinceVo.class);	
				if(provinceVoList!=null){
					return provinceVoList;
					}
			}
			try{
				String dataResult = restTemplate.getForObject(ApiConstants.API_UTIL_AREA, String.class,"00");
				JSONObject data = JSONObject.parseObject(dataResult);
				String result = data.getString("result");
				JSONArray resultArr = JSONObject.parseArray(result);
				provinceVoList = new ArrayList<ProvinceVo>();
				for(int i = 0;i<resultArr.size();i++){
					JSONObject areaOb= resultArr.getJSONObject(i);
					ProvinceVo provinceVo = new ProvinceVo();
					provinceVo.setProvinceCode(areaOb.getString("code"));
					provinceVo.setProvinceName(areaOb.getString("name"));
					provinceVo.setProvinceType(areaOb.getIntValue("type"));
					provinceVoList.add(provinceVo);
				}
				if(provinceVoList != null){
					redisService.rPush(PROVINCE_KEY, provinceVoList, ProvinceVo.class, 180L);
				}
				return provinceVoList;
			}catch(Exception e){
				e.printStackTrace();
				return provinceVoList;
			}
		}
	}
		
	String CITY_KEY = "area_province_city_";
	
	public List<CityVo> getCityCodeByProvince(String cityCode){
		String key = CITY_KEY+cityCode;
		List<CityVo> cityVoList = null;
		if(redisService.exists(key)){
			cityVoList = redisService.lRange(key, 0L, -1L, CityVo.class);
			if(cityVoList != null){
				return cityVoList;
			}
		}
		synchronized(key){
			if(redisService.exists(key)){
				cityVoList = redisService.lRange(key, 0L, -1L, CityVo.class);
				if(cityVoList != null){
					return cityVoList;
				}
			}
			try{
				String dataResult = restTemplate.getForObject(ApiConstants.API_UTIL_AREA, String.class,cityCode);
				JSONObject data = JSONObject.parseObject(dataResult);
				String result = data.getString("result");
				JSONArray resultArr = JSONObject.parseArray(result);
				cityVoList = new ArrayList<CityVo>();
				for(int i = 0;i<resultArr.size();i++){
					JSONObject areaOb= resultArr.getJSONObject(i);
					CityVo cityVo = new CityVo();
					cityVo.setCityCode(areaOb.getString("code"));
					cityVo.setCityName(areaOb.getString("name"));
					cityVo.setCityType(areaOb.getIntValue("type"));
					cityVoList.add(cityVo);
				}
				if(cityVoList != null){
					redisService.rPush(key, cityVoList, CityVo.class, 180L);
				}
				return cityVoList;
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return cityVoList;
	}
}
