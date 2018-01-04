package main.java.com.hiveview.dao.sys;

import com.hiveview.dao.BaseDao;
import com.hiveview.entity.sys.SysRoleAuth;

public interface SysRoleAuthDao extends BaseDao<SysRoleAuth> {

	/**
	 * 添加
	 * @param sysRoleAuth
	 * @return
	 */
	public int addRoleAuth(SysRoleAuth sysRoleAuth);

	/**
	 * 删除
	 * @param roleId
	 * @return
	 */
	public int deleteRoleAuth(Integer roleId);
}
