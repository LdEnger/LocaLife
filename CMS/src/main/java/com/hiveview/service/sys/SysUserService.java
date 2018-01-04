package main.java.com.hiveview.service.sys;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hiveview.common.ApiConstants;
import com.hiveview.common.ParamConstants;
import com.hiveview.dao.BranchDao;
import com.hiveview.dao.HallDao;
import com.hiveview.dao.sys.SysRoleDao;
import com.hiveview.dao.sys.SysUserDao;
import com.hiveview.dao.sys.SysUserSessionDao;
import com.hiveview.entity.bo.ScriptPage;
import com.hiveview.entity.sys.SysUser;
import com.hiveview.entity.sys.ZoneCity;
import com.hiveview.service.RedisService;

@Service
public class SysUserService {
	private static final Logger DATA = LoggerFactory.getLogger("data");
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
	@Autowired
	private RedisService redisService;
	
	/**
	 * 获取用户信息
	 * 
	 * @param userId
	 * @return
	 */
	public SysUser getSysUserById(int userId) {
		return sysUserDao.getSysUserById(userId);
	}

	/**
	 * 登录
	 * 
	 * @param sysUser
	 * @return
	 */
	public SysUser getLoginInfo(SysUser sysUser) {
		try {
			sysUser.setUserMail(sysUser.getUserMail().trim());
			sysUser = sysUserDao.getLoginInfo(sysUser);
			//添加战区信息
			sysUser = zoneCityService.setZoneInfo(sysUser);
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
	 * 登录 带缓存
	 * @param sysUser
	 * @return
	 */
	public SysUser getLoginInfoByCache(SysUser sysUser) {
		if (sysUser.getUserMail() == null) {
			DATA.info("获取用户信息异常 SysUser userMail为空");
			return null;
		}
		String key = ApiConstants.REDIS_KEY_API_LIVE_OPEN + sysUser.getUserMail().trim();
		try {
			if (redisService.exists(key)) {
				sysUser = redisService.get(key, SysUser.class);
				return sysUser;
			}
			sysUser.setUserMail(sysUser.getUserMail().trim());
			sysUser = sysUserDao.getLoginInfo(sysUser);
			if (sysUser == null) {
				// sysUser为空时不缓存
				sysUser = new SysUser();
			}
			redisService.set(key, sysUser, 1 * 60 * 60L, SysUser.class);// 缓存一小时
		} catch (Exception e) {
			DATA.info("获取用户信息异常 Exception={}", new Object[] { e });
			sysUser = null;
		}
		return sysUser;
	}

	/**
	 * 获取系统用户列表
	 * 
	 * @param isEffective
	 * @return
	 */
	public ScriptPage getSysUserList(SysUser sysUser, Integer roleId) {
		List<SysUser> rows = new ArrayList<SysUser>();
		int total = 0;
		Integer zoneId = sysUser.getZoneId();
		if (sysUser.getBranchId() == null && sysUser.getHallId() == null&&(zoneId != null || ParamConstants.GROUP_ROLE != roleId)) {
			List<ZoneCity> zoneCityList = zoneCityService.getCityByZone(zoneId);
			rows = sysUserDao.getUserByList(zoneCityList,sysUser.getSkipNo(),sysUser.getPageSize());
			total = sysUserDao.getCountByList(zoneCityList);
		} else {
			rows = sysUserDao.getUserList(sysUser);
			total = sysUserDao.getCount(sysUser);
		}
		for (SysUser tmp : rows) {
			// 战区信息从城市和战区关联表里查询
			if (ParamConstants.GROUP_ROLE != tmp.getRoleId()&&ParamConstants.LOCALLIFE_ROLE != tmp.getRoleId()&&ParamConstants.SHBST_ROLE != tmp.getRoleId()) {
				try {
					String cityCode = tmp.getCityCode();
					Integer cityId = Integer.valueOf(cityCode);
					ZoneCity zc = zoneCityService.getInfoByCity(cityId);
					String zoneName = zoneService.getZoneById(zc.getZoneId()).getZoneName();
					tmp.setZoneId(zc.getZoneId());
					tmp.setZoneName(zoneName);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				tmp.setZoneId(-1);
				tmp.setZoneName("-");
			}
		}
		ScriptPage scriptPage = new ScriptPage();
		scriptPage.setRows(rows);
		scriptPage.setTotal(total);
		return scriptPage;
	}

	/**
	 * 修改用户
	 * 
	 * @param user
	 * @return
	 */
	public int updateSysUser(SysUser user) {
		String pass = user.getUserPwd();
		if (pass.length() == 32) { // md5加密后的密码，相当于没做过改动,不对其进行更新
			user.setUserPwd(null);
		}
		if (user.getUserMail() == null) {
			return sysUserDao.updateSysUser(user);
		} else {
			SysUser u = new SysUser();
			u.setUserMail(user.getUserMail());
			u.setRoleId(1);// 做修改时候临时更改权限
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
	 * 
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

	public SysUser idDefaultUser(SysUser user) {
		if (user.getRoleId() == 1) {
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
	 * 
	 * @param userId
	 * @return
	 */
	public boolean dropAccountByMail(Integer userId) {
		try {
			int suc = this.sysUserDao.dropAccountByMail(userId);
			return suc > 0 ? true : false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<SysUser> getUserList(SysUser condition) {
		return this.sysUserDao.getUserList(condition);
	}
	 public List<SysUser> getUserByBranchIds(Map<String, String> map){
		 return sysUserDao.getUserByBranchIds(map);
	 }
}
