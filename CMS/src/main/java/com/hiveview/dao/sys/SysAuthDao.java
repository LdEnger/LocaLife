package main.java.com.hiveview.dao.sys;

import java.util.List;

import com.hiveview.dao.BaseDao;
import com.hiveview.entity.sys.SysAuth;
import com.hiveview.entity.sys.SysUserSpecial;

public interface SysAuthDao extends BaseDao<SysAuth> {

	/**
	 * 获得页面左侧菜单
	 * @param pid
	 * @return
	 */
	public List<SysAuth> getSysAuthByPid(int roleId);

	/**
	 * 获取权限列表
	 * @param condition 当skipNo=-1时，获取符合条件的全部结果集；否则返回请求页结果集
	 * @return
	 */
	public List<SysAuth> getSysAuthList(SysAuth condition);

	/**
	 * 修改权限
	 * @param sysAuth
	 * @return
	 */
	public int updateSysAuth(SysAuth sysAuth);

	/**
	 * 添加权限
	 * @param sysAuth
	 * @return
	 */
	public int addSysAuth(SysAuth sysAuth);

	/**
	 * 获得数量 
	 * @param sysAuth
	 * @return
	 */
	public int getCount(SysAuth sysAuth);

	public List<SysAuth> getLeftAuth(Integer roleId);

	public List<SysAuth> getParentAuth();
	
	public List<SysAuth> getParentAuthByRole(Integer roleId);
	
	public int delAuth(Integer authId);
	
	/**
	 * 概述：获取特殊功能列表
	 * 返回值：List<SysUserSpecial>
	 * 作者：徐浩波  xuhaobo@hiveview.com 
	 * 创建日期：2017-1-13下午2:26:11
	 */
	public List<SysUserSpecial> getAuthSpectialList(SysUserSpecial special);
	/**
	 * 概述：保存特殊功能
	 * 返回值：int
	 * 作者：徐浩波  xuhaobo@hiveview.com 
	 * 创建日期：2017-1-13下午2:27:27
	 */
	public int saveAuthSpecial(SysUserSpecial special);
	/**
	 * 概述：删除一个人名下的特殊权限
	 * 返回值：int
	 * 作者：徐浩波  xuhaobo@hiveview.com 
	 * 创建日期：2017-1-13下午2:28:30
	 */
	public int deleteAuthSpecialByUserId(SysUserSpecial special);
}
