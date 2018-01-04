package com.hiveview.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.hiveview.service.ExcelService;
import com.hiveview.test.base.BaseTest;


public class ExcelServiceTest extends BaseTest{

	@Autowired
	ExcelService service;
	
	String url="http://api.activity.pthv.gitv.tv/upload/cardInfo_1451115176989.xls";
	
	@Test
	public void downLoadServiceTest(){
		System.out.println(service.downLoad(url));
	}
	
}
