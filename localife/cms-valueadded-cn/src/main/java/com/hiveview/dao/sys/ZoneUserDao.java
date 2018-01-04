package com.hiveview.dao.sys;

import com.hiveview.dao.BaseDao;
import com.hiveview.entity.localLife.AreaInfo;
import com.hiveview.entity.sys.ZoneUser;

import java.util.List;

public interface ZoneUserDao extends BaseDao<ZoneUser> {

    List<ZoneUser> getZoneByUserId(Integer userId);

    /**
     * 删除
     * @param userId
     * @return
     */
    public int deleteByUserID(Integer userId);
	
}
