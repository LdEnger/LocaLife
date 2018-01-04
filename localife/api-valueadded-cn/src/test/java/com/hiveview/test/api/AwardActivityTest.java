package com.hiveview.test.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.hiveview.action.api.AwardActivityAction;
import com.hiveview.service.award.AwardOrderService;
import com.hiveview.test.base.BaseTest;

public class AwardActivityTest extends BaseTest {

	public String a = "cccc";
	
	@Autowired
	AwardActivityAction awardActivityAction;
	@Autowired
	AwardOrderService awardOrderService;

	public static void main(String[] args) {
		Integer a = new Integer(129);
		System.out.println(a.equals(129));
		System.out.println(a==129);
	}
	
	@Test
	public void test(){
		
	}
	
}
