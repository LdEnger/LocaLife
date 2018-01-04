package com.hiveview.service.sys;

import com.hiveview.dao.sys.ZoneTreeDao;
import com.hiveview.dao.sys.ZoneUserDao;
import com.hiveview.entity.localLife.AreaInfo;
import com.hiveview.entity.sys.ZoneUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZoneUserService {
	
	@Autowired
	private ZoneUserDao zoneUserDao;

    /**
     * 保存Zone树结构
     * @param zoneUser
     * @return
     */
    public boolean add(ZoneUser zoneUser){
        int suc = this.zoneUserDao.save(zoneUser);
        return suc > 0 ? true:false;
    }

    public boolean delete(Integer userId){
        int suc = this.zoneUserDao.deleteByUserID(userId);
        return suc > 0 ? true:false;
    }

    /**
     * @param userId
     * @return
     */
    public List<ZoneUser> getZoneByUserId(Integer userId){
        List<ZoneUser> zoneUserList=null;
        try {
            zoneUserList = this.zoneUserDao.getZoneByUserId(userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return zoneUserList;
    }

}
