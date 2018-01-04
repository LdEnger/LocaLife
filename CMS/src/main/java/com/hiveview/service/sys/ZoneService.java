package main.java.com.hiveview.service.sys;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hiveview.dao.sys.ZoneDao;
import com.hiveview.entity.bo.ScriptPage;
import com.hiveview.entity.sys.Zone;

@Service
public class ZoneService {
	
	@Autowired
	ZoneDao zoneDao;
	
	public List<Zone> getAllList(){
		return this.zoneDao.getAllList();
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
	
	public boolean update(Zone zone){
		int suc = this.zoneDao.update(zone);
		return suc > 0 ? true:false;
	}
	
	public Zone getZoneById(Integer id){
		return this.zoneDao.getZoneById(id);
	}

}
