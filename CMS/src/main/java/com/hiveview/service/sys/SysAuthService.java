package main.java.com.hiveview.service.sys;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hiveview.dao.sys.SysAuthDao;
import com.hiveview.entity.bo.ScriptPage;
import com.hiveview.entity.sys.SysAuth;
import com.hiveview.entity.sys.SysUserSpecial;

@Service
public class SysAuthService {

	@Autowired
	private SysAuthDao sysAuthMapper;

	/**
	 * 获取用户权限
	 * @param pid
	 * @return
	 */
	public List<SysAuth> getSysAuthByRoleId(int roleId) {
		try {
			if (roleId != -1) {
				return sysAuthMapper.getSysAuthByPid(roleId);
			} else {
				SysAuth condition = new SysAuth();
				return sysAuthMapper.getSysAuthList(condition);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取所有权限
	 * @return
	 */
	public ScriptPage getSysAuthList(SysAuth sysAuth) {
		try {
			List<SysAuth> rows = sysAuthMapper.getSysAuthList(sysAuth);
			int total = sysAuthMapper.getCount(sysAuth);
			ScriptPage scriptPage = new ScriptPage();
			scriptPage.setRows(rows);
			scriptPage.setTotal(total);
			return scriptPage;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 修改系统权限
	 * @param sysAuth
	 * @return
	 */
	public int updateSysAuth(SysAuth sysAuth) {
		try {
			sysAuth.setUpdatedTime(new Timestamp(System.currentTimeMillis()));
			return sysAuthMapper.updateSysAuth(sysAuth);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * 添加系统权限
	 * @param sysAuth
	 * @return
	 */
	public int addSysAuth(SysAuth sysAuth) {
		try {
			sysAuth.setCreatedTime(new Timestamp(System.currentTimeMillis()));
			sysAuth.setUpdatedTime(new Timestamp(System.currentTimeMillis()));
			return sysAuthMapper.addSysAuth(sysAuth);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * 逻辑删除系统权限
	 * @param authId
	 * @return
	 */
	public int deleteSysAuth(int authId) {
		try {
			SysAuth auth = new SysAuth();
			auth.setAuthId(authId);
			auth.setIsEffective(0);
			return sysAuthMapper.updateSysAuth(auth);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * 查询数量
	 * @param sysAuth
	 * @return
	 */
	public int getCount(SysAuth sysAuth) {
		try {
			return sysAuthMapper.getCount(sysAuth);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public List<SysAuth> getLeftAuth(Integer roleId) {
		return sysAuthMapper.getLeftAuth(roleId);
	}

	public List<SysAuth> getParentAuth() {
		return sysAuthMapper.getParentAuth();
	}
	
	public List<SysAuth> getParentAuthByRole(Integer roleId){
		return sysAuthMapper.getParentAuthByRole(roleId);
	}
	
	/**
	 * 删除权限
	 * @param authId
	 * @return
	 */
	public boolean delAuth(Integer authId) {
		try {
			int suc = this.sysAuthMapper.delAuth(authId);
			return suc > 0 ? true:false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	/**
	 * 概述：获取特殊功能列表
	 * 返回值：List<SysUserSpecial>
	 * 作者：徐浩波  xuhaobo@hiveview.com 
	 * 创建日期：2017-1-13下午2:26:11
	 */
	public List<SysUserSpecial> getAuthSpectialList(SysUserSpecial special){
		return sysAuthMapper.getAuthSpectialList(special);
	}
	/**
	 * 概述：保存特殊功能
	 * 返回值：int
	 * 作者：徐浩波  xuhaobo@hiveview.com 
	 * 创建日期：2017-1-13下午2:27:27
	 */
	public int saveAuthSpecial(SysUserSpecial special){
		return sysAuthMapper.saveAuthSpecial(special);
	}
	/**
	 * 概述：删除一个人名下的特殊权限
	 * 返回值：int
	 * 作者：徐浩波  xuhaobo@hiveview.com 
	 * 创建日期：2017-1-13下午2:28:30
	 */
	public int deleteAuthSpecialByUserId(SysUserSpecial special){
		return sysAuthMapper.deleteAuthSpecialByUserId(special);
	}
}
