package main.java.com.hiveview.service.localLife;

import java.util.List;

import com.hiveview.entity.sys.ZoneCity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hiveview.dao.localLife.ZoneUserDao;
import com.hiveview.entity.localLife.ZoneUser;

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

    public List<ZoneCity> getZone(Integer userId) {
        List<ZoneCity> zoneUserList=null;
        try {
            zoneUserList = this.zoneUserDao.getZone(userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return zoneUserList;
    }
}
