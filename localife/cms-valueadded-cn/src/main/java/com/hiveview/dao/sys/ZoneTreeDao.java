package com.hiveview.dao.sys;

import com.hiveview.dao.BaseDao;
import com.hiveview.entity.localLife.AreaInfo;
import com.hiveview.entity.sys.ZoneCity;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ZoneTreeDao extends BaseDao<AreaInfo> {
	
    /**
     * 获取区域
     * @param areaInfo
     * @return
     */
    public List<AreaInfo> getZoneTreeList(AreaInfo areaInfo);

    /**
     * 获取区域
     * @param areaInfo
     * @return
     */
    public List<AreaInfo> getZoneTreeByUserId(AreaInfo areaInfo);
}
