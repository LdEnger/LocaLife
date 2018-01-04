package test.java.com.hiveview.test.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.alibaba.fastjson.JSONObject;
import com.hiveview.dao.sys.SysUserDao;
import com.hiveview.entity.sys.SysUser;
import com.hiveview.test.base.BaseTest;

public class SysUserDaoTest extends BaseTest{

	@Autowired
	SysUserDao sysUserDao;
	
	@Test
	public void addSysUserTest(){
		SysUser sysUser = new SysUser();
		sysUser.setBranchId(160);
		sysUser.setBranchName("测试分公司");
		sysUser.setCityCode("00");
		sysUser.setCityName("北京市");
		sysUser.setCreatedBy(179);
		sysUser.setIsEffective(1);
		sysUser.setPhoneNumber("123123123");
		sysUser.setProvinceCode("00");
		sysUser.setProvinceName("北京市");
		sysUser.setRoleId(2);
		sysUser.setRoleName("分公司人员");
		sysUser.setUpdatedBy(179);
		sysUser.setUserMail("111111");
		sysUser.setUserName("111111");
		sysUser.setUserPwd("11111");
		sysUserDao.addSysUser(sysUser);
	}
	
	@Test
	public void getSysUserListTest(){
		SysUser sysUser = new SysUser();
		sysUser.setRoleId(2);
		sysUser.setCityCode("1010");
		System.out.println("---->"+JSONObject.toJSONString(sysUserDao.getUserList(sysUser)));
	}
	
	@Test
	public void getCountTest(){
		SysUser sysUser = new SysUser();
		sysUser.setRoleId(1);
		sysUser.setUserMail("ad11min@hiveview.com");
		System.out.println("---->"+sysUserDao.getCount(sysUser));
	}
	
	@Test
	public void getSysUserByIdTest(){
		System.out.println(JSONObject.toJSONString(sysUserDao.getSysUserById(179)));
	}
}
