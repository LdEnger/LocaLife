package main.java.com.hiveview.service.localLife;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hiveview.dao.localLife.ZoneTreeDao;
import com.hiveview.entity.localLife.AreaInfo;

@Service
public class ZoneTreeService {
	
	@Autowired
	private ZoneTreeDao zoneTreeDao;

    /**
     * 保存Zone树结构
     * @param areaInfo
     * @return
     */
    public boolean add(AreaInfo areaInfo){
        int suc = this.zoneTreeDao.save(areaInfo);
        return suc > 0 ? true:false;
    }
    public int del(AreaInfo areaInfo){
        return zoneTreeDao.delete(areaInfo);
    }

    /**
     * 获取用户地理区域
     * @param areaInfo
     * @return
     */
    public List<AreaInfo> getZoneTreeList(AreaInfo areaInfo) {
        try {
            return zoneTreeDao.getZoneTreeList(areaInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 获取用户地理区域
     * @param areaInfo
     * @return
     */
	public List<AreaInfo> getZoneTreeByUserId(AreaInfo areaInfo) {
		List<AreaInfo> areaZoneTreeList = zoneTreeDao.getZoneTreeByUserId(areaInfo);
		return areaZoneTreeList;
	}

}
