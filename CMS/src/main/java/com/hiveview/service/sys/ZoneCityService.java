package main.java.com.hiveview.service.sys;

import java.util.ArrayList;
import java.util.List;

import com.hiveview.dao.HallDao;
import com.hiveview.entity.Hall;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hiveview.common.ParamConstants;
import com.hiveview.dao.BranchDao;
import com.hiveview.dao.sys.ZoneCityDao;
import com.hiveview.entity.Branch;
import com.hiveview.entity.bo.ScriptPage;
import com.hiveview.entity.sys.SysUser;
import com.hiveview.entity.sys.ZoneCity;

@Service
public class ZoneCityService {
	
	@Autowired
	private ZoneCityDao zoneCityDao;
	@Autowired
	private BranchDao branchDao;
	@Autowired
	private HallDao hallDao;
	
	public ScriptPage getZoneCityList(ZoneCity zoneCity) {
		List<ZoneCity> rows = zoneCityDao.getList(zoneCity);
		for (ZoneCity zc : rows) {
			Integer cityId = zc.getCityId();
			List<Branch> tmpList = branchDao.getBranchListByArea(cityId.toString());
			if(tmpList.size()>0){
				zc.setContainBranch(true);
			}else{
				zc.setContainBranch(false);
			}
		}
		int total = zoneCityDao.count(zoneCity);
		ScriptPage scriptPage = new ScriptPage();
		scriptPage.setRows(rows);
		scriptPage.setTotal(total);
		return scriptPage;
	}
	
	/**
	 * 向战区添加城市
	 * @param zoneCity
	 * @return
	 */
	public boolean add(ZoneCity zoneCity){
		int suc = this.zoneCityDao.save(zoneCity);
		return suc > 0 ? true:false;
	}
	
	
	/**
	 * 
	 * @param zoneCity
	 * @return
	 */
	public ZoneCity getInfoByCity(Integer cityId){
		return this.zoneCityDao.getInfoByCity(cityId);
	}
	
	/**
	 * 删除一条关联
	 * @param zoneCity
	 * @return
	 */
	public boolean del(ZoneCity zoneCity){
		int suc = this.zoneCityDao.delete(zoneCity);
		return suc > 0 ? true:false;
	}
	
	/**
	 * 根据战区获取城市列表
	 * @param zoneId
	 * @return
	 */
	public List<ZoneCity> getCityByZone(Integer zoneId){
		if(zoneId==0||zoneId==null){
			return null;
		}
		List<ZoneCity> cityList=null;
		try {
			cityList = this.zoneCityDao.getCityByZone(zoneId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cityList;
	}
	
	/**
	 * 获取战区下所有的分公司
	 * @param zoneId
	 * @return
	 */
	public List<Branch> getBranchByZone(Integer zoneId){
		List<ZoneCity> cityList = this.zoneCityDao.getCityByZone(zoneId);
		List<Branch> branchList = new ArrayList<Branch>();
		for (ZoneCity zoneCity : cityList) {
			Integer cityId = zoneCity.getCityId();
			String cityCode = cityId.toString();
			List<Branch> tmpList = this.branchDao.getBranchListByArea(cityCode);
			for (Branch branch : tmpList) {
				branchList.add(branch);
			}
		}
		return branchList;
	}

	/**
	 * 获取战区下所有的营业厅
	 * @param zoneId
	 * @return
	 */
	public List<Hall> getHallByZone(Integer zoneId) {
		List<Hall> halllist = new ArrayList<Hall>();
		List<ZoneCity> cityList = this.zoneCityDao.getCityByZone(zoneId);
		List<Branch> branchList = new ArrayList<Branch>();
		for (ZoneCity zoneCity : cityList) {
			Integer cityId = zoneCity.getCityId();
			String cityCode = cityId.toString();
			List<Branch> tmpList = this.branchDao.getBranchListByArea(cityCode);
			for (Branch branch : tmpList) {
				branchList.add(branch);
			}
		}
		if (branchList.size() > 0) {
			for (Branch branch : branchList) {
				List<Hall> temp = hallDao.getHallList(branch.getId());
				halllist.addAll(temp);
			}
		}
		return halllist;
	}
	
	/**
	 * 设置人物的战区信息
	 * @param sysUser
	 * @return
	 */
	public SysUser setZoneInfo(SysUser sysUser){
		if (ParamConstants.GROUP_ROLE != sysUser.getRoleId()) {
			try {
				String cityCode = sysUser.getCityCode();
				Integer cityId = Integer.valueOf(cityCode);
				ZoneCity zc = this.getInfoByCity(cityId);
				sysUser.setZoneId(zc.getZoneId());
				sysUser.setZoneName(zc.getZoneName());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			sysUser.setZoneId(-1);
			sysUser.setZoneName("-");
		}
		return sysUser;
	}
	
}
