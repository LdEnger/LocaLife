package com.hiveview.service.sys;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hiveview.common.ParamConstants;
import com.hiveview.dao.BranchDao;
import com.hiveview.dao.HallDao;
import com.hiveview.dao.sys.SysRoleDao;
import com.hiveview.dao.sys.SysUserDao;
import com.hiveview.dao.sys.SysUserSessionDao;
import com.hiveview.entity.Branch;
import com.hiveview.entity.bo.ScriptPage;
import com.hiveview.entity.sys.SysUser;
import com.hiveview.entity.sys.ZoneCity;

@Service
public class SysUserService {

	@Autowired
	private SysUserDao sysUserDao;
	@Autowired
	private SysUserSessionDao sysUserSessionDao;
	@Autowired
	private HallDao hallDao;
	@Autowired
	private BranchDao branchDao;
	@Autowired
	private SysRoleDao srDao;
	@Autowired
	private ZoneCityService zoneCityService;
	@Autowired
	private ZoneService zoneService;
	

	/**
	 * 获取用户信息
	 * @param userId
	 * @return
	 */
	public SysUser getSysUserById(int userId) {
		return sysUserDao.getSysUserById(userId);
	}

	/**
	 * 登录
	 * @param sysUser
	 * @return
	 */
	public SysUser getLoginInfo(SysUser sysUser) {
		try {
			sysUser.setUserMail(sysUser.getUserMail().trim());
			sysUser = sysUserDao.getLoginInfo(sysUser);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (sysUser == null) {
			sysUser = new SysUser();
			sysUser.setUserId(-1);
		}
		return sysUser;
	}

	/**
	 * 获取系统用户列表
	 * @param sysUser
	 * @return
	 */
	public ScriptPage getSysUserList(SysUser sysUser) {
		List<SysUser> rows = sysUserDao.getUserList(sysUser);
		Integer roleId = sysUser.getRoleId();
		if(ParamConstants.ZONE_ROLE==roleId){ //当前查询者为战区管理员
			Integer zoneId = sysUser.getZoneId(); 
			List<Branch> branchList = zoneCityService.getBranchByZone(zoneId); //获取该战区管理员所辖所有分公司的人
			for (Branch branch : branchList) {
				SysUser su = new SysUser();
				su.setBranchId(branch.getId());
				List<SysUser> cond = sysUserDao.getUserList(su);
				for (SysUser sysUser2 : cond) {
					rows.add(sysUser2);
				}
			}
		}
		for (SysUser tmp : rows) {
		//战区信息从城市和战区关联表里查询
		if (ParamConstants.GROUP_ROLE != tmp.getRoleId()) {
				try {
					String cityCode = tmp.getCityCode();
					Integer cityId = Integer.valueOf(cityCode);
					ZoneCity zc = zoneCityService.getInfoByCity(cityId);
                    if(null != zc){
                        Integer zoneId = zc.getZoneId();
                        String zoneName = zoneService.getZoneById(zoneId).getZoneName();
                        tmp.setZoneId(zoneId);
                        tmp.setZoneName(zoneName);
                    }
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else{
			tmp.setZoneId(-1);
			tmp.setZoneName("-");
		}
		}
		int total = sysUserDao.getCount(sysUser);
		ScriptPage scriptPage = new ScriptPage();
		scriptPage.setRows(rows);
		scriptPage.setTotal(total);
		return scriptPage;
	}

	/**
	 * 修改用户
	 * @param user
	 * @return
	 */
	public int updateSysUser(SysUser user) {
		String pass = user.getUserPwd();
		if (pass.length() == 32) { //md5加密后的密码，相当于没做过改动,不对其进行更新
			user.setUserPwd(null);
		}
		if (user.getUserMail() == null) {
			return sysUserDao.updateSysUser(user);
		} else {
			SysUser u = new SysUser();
			u.setUserMail(user.getUserMail());
			u.setRoleId(1);//做修改时候临时更改权限
			if (sysUserDao.getCount(u) != 1) {
				user.setUpdatedTime(new Date());
				return sysUserDao.updateSysUser(user);
			} else {
				return -1;
			}
		}
	}

	/**
	 * 添加用户
	 * @param user
	 * @return
	 */
	public int addSysUser(SysUser user) {
		SysUser u = new SysUser();
		u.setUserMail(user.getUserMail());
		if (sysUserDao.getCount(u) == 0) {
			user.setCreatedTime(new Date());
			user.setUpdatedTime(new Date());
			return sysUserDao.addSysUser(user);
		} else {
			return -1;
		}
	}
	
	public SysUser idDefaultUser(SysUser user){
		if(user.getRoleId()==1){
			user.setProvinceCode("10");
			user.setProvinceName("北京市");
			user.setCityCode("1010");
			user.setCityName("北京");
			return user;
		}
		return user;
	}

	public int getCount(SysUser user) {
		return sysUserDao.getCount(user);
	}

	/**
	 * 删除用户
	 * @param userId
	 * @return
	 */
	public boolean dropAccountByMail(Integer userId) {
		try {
			int suc = this.sysUserDao.dropAccountByMail(userId);
			return suc > 0 ? true:false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public List<SysUser> getUserList(SysUser condition){
		return this.sysUserDao.getUserList(condition);
	}
}
