package main.java.com.hiveview.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.hiveview.entity.vo.CityVo;
import com.hiveview.entity.vo.ProvinceVo;
import com.hiveview.service.api.AreasApi;

@Service
public class AreasService {
	
	@Autowired
	AreasApi areasApi;
	
	public List<ProvinceVo> getProvinceAll(){
		return areasApi.getProvinceCodeService();
	}
	
	public List<CityVo> getCityByProvinceCode(String code){
		if(StringUtils.isEmpty(code)){
			return null;
		}
		return areasApi.getCityCodeByProvince(code);
	}
}
