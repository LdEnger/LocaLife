package com.hiveview.service.sys;

import com.hiveview.common.ParamConstants;
import com.hiveview.dao.BranchDao;
import com.hiveview.dao.sys.ZoneCityDao;
import com.hiveview.dao.sys.ZoneTreeDao;
import com.hiveview.entity.Branch;
import com.hiveview.entity.bo.ScriptPage;
import com.hiveview.entity.localLife.AreaInfo;
import com.hiveview.entity.sys.SysUser;
import com.hiveview.entity.sys.ZoneCity;
import com.hiveview.entity.sys.ZoneUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        try {
            return zoneTreeDao.getZoneTreeByUserId(areaInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



}
