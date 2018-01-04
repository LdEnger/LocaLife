package com.hiveview.dao.sys;

import java.util.List;

import com.hiveview.dao.BaseDao;
import com.hiveview.entity.sys.SysUser;
import com.hiveview.entity.vo.ComboVo;

public interface SysUserDao extends BaseDao<SysUser> {

	/**
	 * 查询用户信息列表
	 * @param condition 当skipNo=-1时，获取符合条件的全部结果集；否则返回请求页结果集
	 * @return
	 */
	public List<SysUser> getUserList(SysUser condition);
	

	/**
	 * 通过Id获取系统用户信息
	 * @param sysUser
	 * @return
	 */
	public SysUser getSysUserById(int userId);

	/**
	 * 登录
	 * @param sysUser
	 * @return
	 */
	public SysUser getLoginInfo(SysUser sysUser);

	/**
	 * 保存系统用户信息
	 * @param sysUser
	 * @return
	 */
	public int addSysUser(SysUser sysUser);

	/**
	 * 更新系统用户信息
	 * @param sysUser
	 * @return
	 */
	public int updateSysUser(SysUser sysUser);

	/**
	 * 查询数量
	 * @param sysUser
	 * @return
	 */
	public int getCount(SysUser sysUser);
	
	/**
	 * 删除账户
	 * @param userMail
	 * @return
	 */
	public int dropAccountByMail(Integer userId);

	List<ComboVo> getBranchs(String value);
}
