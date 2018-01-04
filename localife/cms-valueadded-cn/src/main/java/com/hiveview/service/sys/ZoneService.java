package com.hiveview.service.sys;

import java.util.ArrayList;
import java.util.List;

import com.hiveview.dao.sys.ZoneCityDao;
import com.hiveview.entity.NewsCombotreeBean;
import com.hiveview.entity.sys.ZoneCity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hiveview.dao.sys.ZoneDao;
import com.hiveview.entity.bo.ScriptPage;
import com.hiveview.entity.sys.Zone;

@Service
public class ZoneService {
	
	@Autowired
	ZoneDao zoneDao;
    @Autowired
    ZoneCityDao zoneCityDao;
	
	public List<Zone> getAllList(){
		return this.zoneDao.getAllList();
	}
    public List<Zone> getZoneListById(Integer id){
        return this.zoneDao.getZoneListById(id);
    }
	
	public ScriptPage getZoneList(Zone zone) {
		List<Zone> rows = zoneDao.getList(zone);
		int total = zoneDao.count(zone);
		ScriptPage scriptPage = new ScriptPage();
		scriptPage.setRows(rows);
		scriptPage.setTotal(total);
		return scriptPage;
	}
	
	public boolean add(Zone zone){
		int suc = this.zoneDao.save(zone);
		return suc > 0 ? true:false;
	}

    public int del(Zone zone){
        return zoneDao.delete(zone);
    }
	
	public boolean update(Zone zone){
		int suc = this.zoneDao.update(zone);
		return suc > 0 ? true:false;
	}
	
	public Zone getZoneById(Integer id){
		return this.zoneDao.getZoneById(id);
	}

    public List<Zone> getTreeList(){
        List<Zone> zoneList = zoneDao.getAllList();
        List<NewsCombotreeBean> comList = new ArrayList<NewsCombotreeBean>();
        if(null != zoneList){
            for(Zone z : zoneList){
                NewsCombotreeBean com = new NewsCombotreeBean();
                com.setId(z.getId());
                com.setText(z.getZoneName());
                List<ZoneCity> zoneCiytList = zoneCityDao.getCityByZone(z.getId());
                if(null != zoneCiytList){
                    for(ZoneCity zc : zoneCiytList){
                        List<NewsCombotreeBean> comList2 = new ArrayList<NewsCombotreeBean>();
                        NewsCombotreeBean  com2 = new NewsCombotreeBean();
                        com2.setId(zc.getProvinceId());
                        com2.setText(zc.getProvinceName());
                        com2.setParentId(z.getId());
                        List<ZoneCity> zoneCiytList2 = zoneCityDao.getCityByProvince(zc.getProvinceId());
                        if(null != zoneCiytList2){
                            for(ZoneCity zc2 : zoneCiytList){
                                List<NewsCombotreeBean> comList3 = new ArrayList<NewsCombotreeBean>();
                                NewsCombotreeBean  com3 = new NewsCombotreeBean();
                                com3.setId(zc2.getCityId());
                                com3.setText(zc2.getCityName());
                                com3.setParentId(zc.getProvinceId());
                            }
                        }

                    }

                }

            }
        }
        return this.zoneDao.getAllList();
    }

}
