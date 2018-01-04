package com.hiveview.service.sys;

import java.util.ArrayList;
import java.util.List;

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
	
	public ScriptPage getZoneCityList(ZoneCity zoneCity) {
		List<ZoneCity> rows = zoneCityDao.getList(zoneCity);
		for (ZoneCity zoneCity2 : rows) {
			Integer cityId = zoneCity2.getCityId();
			List<Branch> tmpList = branchDao.getBranchListByArea(cityId.toString());
			if(tmpList.size()>0){
				zoneCity2.setContainBranch(true);
			}else{
				zoneCity2.setContainBranch(false);
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

    public int deleteAll(ZoneCity zoneCity){
        return zoneCityDao.deleteAll(zoneCity);
    }
	
	/**
	 * 根据战区获取城市列表
	 * @param zoneId
	 * @return
	 */
	public List<ZoneCity> getCityByZone(Integer zoneId){
		List<ZoneCity> cityList=null;
		try {
			cityList = this.zoneCityDao.getCityByZone(zoneId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cityList;
	}

    /**
     * 根据省区域获取城市列表
     * @param provinceId
     * @return
     */
    public List<ZoneCity> getCityByProvince(Integer provinceId){
        List<ZoneCity> cityList=null;
        try {
            cityList = this.zoneCityDao.getCityByProvince(provinceId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cityList;
    }

    /**
     * 获取所有城市列表
     * @return
     */
    public List<ZoneCity> getAllCity(){
        List<ZoneCity> cityList=null;
        try {
            cityList = this.zoneCityDao.getAllList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cityList;
    }

    /**
     * 根据城市ID获取城市列表
     * @param cityId
     * @return
     */
    public List<ZoneCity> getCityByCityId(Integer cityId){
        List<ZoneCity> cityList=null;
        try {
            cityList = this.zoneCityDao.getCityByCityId(cityId);
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
	
	/**
	 * 从zonetree查询省事信息
	 * @param sysUser
	 * @return
	 */
	public List<ZoneCity> getChinaZoneCity(){
		List<ZoneCity> cityList=null;
        try {
            cityList = this.zoneCityDao.getChinaZoneCity();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cityList;
	}
	
	
	
}
