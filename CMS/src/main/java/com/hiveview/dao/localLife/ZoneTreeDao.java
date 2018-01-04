package main.java.com.hiveview.dao.localLife;

import java.util.List;

import com.hiveview.dao.BaseDao;
import com.hiveview.entity.localLife.AreaInfo;

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
