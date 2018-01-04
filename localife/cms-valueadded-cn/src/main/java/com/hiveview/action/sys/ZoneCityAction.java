package com.hiveview.action.sys;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hiveview.entity.Branch;
import com.hiveview.entity.Hall;
import com.hiveview.entity.bo.AjaxPage;
import com.hiveview.entity.bo.Data;
import com.hiveview.entity.bo.ScriptPage;
import com.hiveview.entity.sys.ZoneCity;
import com.hiveview.service.HallService;
import com.hiveview.service.sys.ZoneCityService;

@Controller
@RequestMapping(value = "/zoneCity")
public class ZoneCityAction {
	
	@Autowired
	private ZoneCityService zoneCityService;
	@Autowired
	private HallService hallService;
	
	
	@RequestMapping(value = "/getList")
	@ResponseBody
	public ScriptPage getZoneList(ZoneCity zoneCity, AjaxPage ajaxPage) {
		ScriptPage scriptPage = null;
		try {
			zoneCity.copy(ajaxPage);
			scriptPage = zoneCityService.getZoneCityList(zoneCity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return scriptPage;
	}
	
	@RequestMapping(value = "/addCityToZone")
	@ResponseBody
	public Data add(final ZoneCity zoneCity) {
		Data data = new Data();
		if (zoneCity == null) {
			data.setCode(0);
			return data;
		}
		try {
			Integer cityId = zoneCity.getCityId();
			if (cityId == null || cityId == 0) {
				data.setCode(0);
				return data;
			}
			// 查询该城市是否已经存在于战区
			ZoneCity tmp = this.zoneCityService.getInfoByCity(cityId);
			if (tmp != null) {
				System.out.println("cityId=" + tmp.getCityId() + ",zoneId="+ tmp.getZoneId());
				data.setCode(2);
				return data;
			}
			boolean bool = zoneCityService.add(zoneCity);
			if (bool) {
				data.setCode(1);
			} else {
				data.setCode(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}
	
	@RequestMapping(value = "/del")
	@ResponseBody
	public Data del(final ZoneCity zoneCity) {
		Data data = new Data();
			try {
				boolean bool = zoneCityService.del(zoneCity);
				if (bool){
					data.setCode(1);
				}else{
					data.setCode(0);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return data;
		} 
	
	@RequestMapping(value = "/getCityByZone")
	@ResponseBody
	public List<ZoneCity> getCityByZone(Integer zoneId){
		if(zoneId==0||zoneId==null){
			return null;
		}
		 List<ZoneCity> cityList=null;
		try {
			cityList = this.zoneCityService.getCityByZone(zoneId);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return cityList;
	}
    @RequestMapping(value = "/getCityByProvince")
    @ResponseBody
    public List<ZoneCity> getCityByProvince(Integer provinceId){
         if(provinceId==0||provinceId==null){
            return null;
        }
        List<ZoneCity> cityList=null;
        try {
            cityList = this.zoneCityService.getCityByProvince(provinceId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cityList;
    }
    @RequestMapping(value = "/getCityByCityId")
    @ResponseBody
    public List<ZoneCity> getCityByCityId(Integer cityId){
        if(cityId==0||cityId==null){
            return null;
        }
        List<ZoneCity> cityList=null;
        try {
            cityList = this.zoneCityService.getCityByProvince(cityId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cityList;
    }
	
	@RequestMapping(value = "/getBranchByZone")
	@ResponseBody
	public List<Branch> getBranchByZone(Integer zoneId){
		if(zoneId==0||zoneId==null){
			return null;
		}
		 List<Branch> branchList=null;
		try {
			branchList = this.zoneCityService.getBranchByZone(zoneId);
			for (Branch branch : branchList) {
				List<Hall> tmpList = hallService.getHallList(branch.getId());
				if( tmpList.size()>0){
					branch.setContainHall(true);
				}else{
					branch.setContainHall(false);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return branchList;
	}
	
	@RequestMapping(value = "/addZoneCity")
	@ResponseBody
	public Data addZoneCity(){
		Data data = new Data();
		try{
			List<ZoneCity> citylist = zoneCityService.getChinaZoneCity();
			if(citylist != null && citylist.size()>0){
				for(ZoneCity zoneCityList : citylist){
					zoneCityList.setZoneId(100);
					zoneCityList.setZoneName("中国");
					this.add(zoneCityList);
				}
			}
			data.setMsg("success");
		}catch(Exception e){
			e.printStackTrace();
			data.setMsg("fail");
		}
		return data;
	}
	

}
