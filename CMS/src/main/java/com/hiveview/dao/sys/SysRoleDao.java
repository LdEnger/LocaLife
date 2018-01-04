package main.java.com.hiveview.dao.sys;

import java.util.List;

import com.hiveview.dao.BaseDao;
import com.hiveview.entity.sys.SysRole;

public interface SysRoleDao extends BaseDao<SysRole> {

	/**
	 * 查找所有的角色
	 * @param condition 当skipNo=-1时，获取符合条件的全部结果集；否则返回请求页结果集
	 * @return
	 */
	public List<SysRole> getSysRoleList(SysRole condition);
	
	/**
	* 正序获得无效权限中最高的角色ID
	* @return
	*/ 
	public Integer getNotEffectiveRoleId();

	/**
	 * 修改系统角色
	 * @param sysRole
	 * @return
	 */
	public int updateSysRole(SysRole sysRole);

	/**
	 * 添加系统角色
	 * @param sysRole
	 * @return
	 */
	public int addSysRole(SysRole sysRole);

	/**
	 * 查询数量
	 * @param sysRole
	 * @return
	 */
	public int getCount(SysRole sysRole);

	/**
	 * 删除
	 * @param roleId
	 * @return
	 */
	public int deleteRole(Integer roleId);
	
	public String getRoleById(Integer sysRoleId);
	
	/**
	 * 验证是否具有批量权限
	 * @param sysRoleId
	 * @return
	 */
	public Integer getRoleBatch(Integer sysRoleId);
}
