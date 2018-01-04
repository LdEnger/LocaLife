package main.java.com.hiveview.dao.sys;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hiveview.dao.BaseDao;
import com.hiveview.entity.sys.SysUser;
import com.hiveview.entity.sys.ZoneCity;

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
	
	List<SysUser> getUserByList(@Param("list") List<ZoneCity> list, @Param("skipNo") Integer skipNo, @Param("pageSize") Integer pageSize);
	
	int getCountByList(List<ZoneCity> list);
	/**
	 * 概述：查询符合分公司条件的人员ID、姓名信息
	 * 返回值：List<SysUser>
	 * 作者：徐浩波  xuhaobo@hiveview.com 
	 * 创建日期：2016-12-6下午11:34:02
	 */
    public List<SysUser> getUserByBranchIds(Map<String, String> map);
}
