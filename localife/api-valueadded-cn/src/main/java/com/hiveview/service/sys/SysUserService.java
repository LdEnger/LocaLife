package com.hiveview.service.sys;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hiveview.dao.BranchDao;
import com.hiveview.dao.HallDao;
import com.hiveview.dao.sys.SysRoleDao;
import com.hiveview.dao.sys.SysUserDao;
import com.hiveview.dao.sys.SysUserSessionDao;
import com.hiveview.entity.bo.ScriptPage;
import com.hiveview.entity.sys.SysUser;
import com.hiveview.entity.vo.ComboVo;

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
	private SysRoleDao srDao;;
	

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
	 * @param isEffective
	 * @return
	 */
	public ScriptPage getSysUserList(SysUser sysUser) {
		List<SysUser> rows = sysUserDao.getUserList(sysUser);
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

    public List<ComboVo> getCombo(String type, String value) {
        /**
         * 1.集团 group
         * 2.公司 branch
         * 3.营业厅 hall
         * 4.战区  subarea
         */
        List<ComboVo> result = new ArrayList<ComboVo>();
        if(StringUtils.equalsIgnoreCase("group",type)){
            result = sysUserDao.getBranchs("-1");
        }else if(StringUtils.equalsIgnoreCase("branch", type)) {
            result = sysUserDao.getBranchs(value);
        }/*else if (StringUtils.equalsIgnoreCase("hall", type)) {
//            result = sysUserDao.getHall();
        }else if (StringUtils.equalsIgnoreCase("subarea", type)) {
//            result = sysUserDao.getSubarea();
        }*/


        return result;
    }

}
