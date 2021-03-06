package com.hiveview.test.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.alibaba.fastjson.JSONObject;
import com.hiveview.dao.BranchDao;
import com.hiveview.entity.Branch;
import com.hiveview.test.base.BaseTest;

public class BranchDaoTest extends BaseTest{

	@Autowired
	BranchDao branchDao;
	
	@Test
	public void addBranchTest(){
		Branch branch =new Branch();
		branch.setBranchName("测试分公司");
		branch.setProvinceCode("00");
		branch.setProvinceName("北京市");
		branch.setCityCode("00");
		branch.setCityName("北京市");
		branchDao.addBranch(branch);
	}
	
	@Test
	public void getBranchName(){
		Branch branch =new Branch();
		branch.setBranchName("测试分公司");
		System.out.println(JSONObject.toJSONString(branchDao.getBranchName(branch)));
	}
}
