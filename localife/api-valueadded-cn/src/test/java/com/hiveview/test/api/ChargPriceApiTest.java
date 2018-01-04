package com.hiveview.test.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.hiveview.common.ApiConstants;
import com.hiveview.entity.vo.VipPackagePriceVo;
import com.hiveview.pay.http.HiveHttpEntityType;
import com.hiveview.pay.http.HiveHttpGet;
import com.hiveview.pay.http.HiveHttpResponse;
import com.hiveview.service.api.ChargePriceApi;
import com.hiveview.test.base.BaseTest;



public class ChargPriceApiTest extends BaseTest{

	@Autowired
	ChargePriceApi chargPriceApi;
	
	@Test
	public void getChargPriceListTest(){
		String url = ApiConstants.CHARGE_LIST_API;
		HiveHttpResponse response= HiveHttpGet.getEntity(url, HiveHttpEntityType.STRING);
		System.out.println(response);
	}
	
	@Test
	public void getVipChargPriceListTest(){
		List<VipPackagePriceVo> list = chargPriceApi.getVipChargPriceList();
		System.out.println(list);
	}
}
