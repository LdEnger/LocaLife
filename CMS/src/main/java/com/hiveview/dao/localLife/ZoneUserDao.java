package main.java.com.hiveview.dao.localLife;

import java.util.List;

import com.hiveview.dao.BaseDao;
import com.hiveview.entity.localLife.ZoneUser;
import com.hiveview.entity.sys.ZoneCity;

public interface ZoneUserDao extends BaseDao<ZoneUser> {

    List<ZoneUser> getZoneByUserId(Integer userId);

    /**
     * 删除
     * @param userId
     * @return
     */
    public int deleteByUserID(Integer userId);

    List<ZoneCity> getZone(Integer userId);
}
