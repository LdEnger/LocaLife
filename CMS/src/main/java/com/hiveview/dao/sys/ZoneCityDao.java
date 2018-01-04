package main.java.com.hiveview.dao.sys;

import java.util.List;

import com.hiveview.dao.BaseDao;
import com.hiveview.entity.sys.ZoneCity;

public interface ZoneCityDao extends BaseDao<ZoneCity> {
	
	ZoneCity getInfoByCity(Integer cityId); // 
	
	List<ZoneCity> getCityByZone(Integer zoneId); //根据战区获取城市列表

}
