package main.java.com.hiveview.service.sys;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hiveview.dao.sys.SysRoleAuthDao;
import com.hiveview.dao.sys.SysRoleDao;
import com.hiveview.entity.sys.SysRole;
import com.hiveview.entity.sys.SysRoleAuth;

@Service
public class SysRoleService {

	@Autowired
	private SysRoleDao sysRoleDao;
	@Autowired
	private SysRoleAuthDao sysRoleAuthDao;

	/**
	 * 查询
	 * @param sysRole
	 * @return
	 */
	public List<SysRole> getSysRoleList(SysRole sysRole) {
		try {
			return sysRoleDao.getSysRoleList(sysRole);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public int getSysRoleAuth(){
		Integer sysRoleId= sysRoleDao.getNotEffectiveRoleId();
		if(sysRoleId == null ){
			return 0;
		}
		return sysRoleId;
	}
	
	/**
	 * 修改用户
	 * @param sysRole
	 * @return
	 */
	public int updateSysRole(SysRole sysRole, final List<SysRoleAuth> list) {
		try {
			sysRole.setUpdatedTime(new Date());
			if (sysRoleAuthDao.deleteRoleAuth(sysRole.getRoleId()) >= 0) {
				/** 新开一个线程 添加关联一股信息-->>角色与权限**/
				Thread thread = new Thread(new Runnable() {
					public void run() {
						for (SysRoleAuth roleAuth : list) {
							sysRoleAuthDao.addRoleAuth(roleAuth);
						}
					}
				});
				thread.start();
			}
			return sysRoleDao.updateSysRole(sysRole);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * 添加系统角色
	 * @param sysRole
	 * @return
	 */
	public int addSysRole(SysRole sysRole, final String sysRoleAuth) {
		try {
			sysRole.setIsEffective(1);
			sysRole.setCreatedTime(new Date());
			sysRole.setUpdatedTime(new Date());
			if (sysRoleDao.addSysRole(sysRole) > 0) {
				/*** 获得要添加的权限 start ***/
				String authIdArray[] = sysRoleAuth.split(",");
				final List<SysRoleAuth> roleAuthlist = new ArrayList<SysRoleAuth>();
				for (int authIdIndex = 0; authIdIndex < authIdArray.length; authIdIndex++) {
					SysRoleAuth ra = new SysRoleAuth();
					ra.setRoleId(sysRole.getRoleId());
					ra.setAuthId(new Integer(authIdArray[authIdIndex]));
					roleAuthlist.add(ra);
				}
				for (SysRoleAuth roleAuth : roleAuthlist) {
					sysRoleAuthDao.addRoleAuth(roleAuth);
				}
				/*** 获得要添加的权限 end ***/
			} else {
				System.out.println("权限设置失败!!!");
			}
			return Integer.parseInt(sysRole.getRoleId().toString());
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * 查询数量
	 * @param sysRole
	 * @return
	 */
	public int getCount(SysRole sysRole) {
		try {
			return sysRoleDao.getCount(sysRole);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * 删除 
	 * @param roleId
	 * @return
	 */
	public int deleteRole(Integer roleId) {
		try {
			sysRoleAuthDao.deleteRoleAuth(roleId);
			return sysRoleDao.deleteRole(roleId);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	/**
	 * 验证该角色是否具有批量权限
	 * @param roleId
	 * @return
	 */
	public boolean getBatchRole(Integer roleId){
		int suc = this.sysRoleDao.getRoleBatch(roleId);
		return suc > 0 ? true : false;
	}
}
