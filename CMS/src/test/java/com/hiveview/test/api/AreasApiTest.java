package test.java.com.hiveview.test.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.alibaba.fastjson.JSONObject;
import com.hiveview.entity.vo.CityVo;
import com.hiveview.entity.vo.ProvinceVo;
import com.hiveview.service.api.AreasApi;
import com.hiveview.test.base.BaseTest;

public class AreasApiTest extends BaseTest{

	@Autowired
	AreasApi areasApi;
	
	@Test
	public void getProvinceCodeServiceTest(){
		List<ProvinceVo> list = areasApi.getProvinceCodeService();
		System.out.println(JSONObject.toJSONString(list));
	}
	
	@Test
	public void getCityCodeByProvinceTest(){
		List<CityVo> list = areasApi.getCityCodeByProvince("20");
		System.out.println(JSONObject.toJSONString(list));
	}
	
}
