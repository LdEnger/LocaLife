package com.hiveview.dao.sys;

import java.util.List;

import com.hiveview.dao.BaseDao;
import com.hiveview.entity.sys.Zone;

public interface ZoneDao extends BaseDao<Zone>{
	
	List<Zone> getAllList();

    List<Zone> getZoneListById(Integer id);
	
	Zone getZoneById(Integer id);

}
