package com.hiveview.dao.sys;

import java.util.List;

import com.hiveview.dao.BaseDao;
import com.hiveview.entity.sys.ZoneCity;
import org.springframework.stereotype.Repository;

@Repository
public interface ZoneCityDao extends BaseDao<ZoneCity> {
	
	ZoneCity getInfoByCity(Integer cityId); // 
	
	List<ZoneCity> getCityByZone(Integer zoneId); //根据战区获取城市列表

	List<ZoneCity> getCityByProvince(Integer provinceId); //根据省区域获取城市列表

	List<ZoneCity> getCityByCityId(Integer cityId); //根据省区域获取城市列表

    List<ZoneCity> getAllList();

    int deleteAll(ZoneCity zoneCity);
    
    public List<ZoneCity> getChinaZoneCity();
}
