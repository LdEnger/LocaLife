package com.hiveview.service.localLife;

import com.hiveview.dao.localLife.LogoDao;
import com.hiveview.dao.sys.ZoneCityDao;
import com.hiveview.dao.sys.ZoneTreeDao;
import com.hiveview.entity.bo.ScriptPage;
import com.hiveview.entity.localLife.AreaInfo;
import com.hiveview.entity.localLife.Logo;
import com.hiveview.service.RedisService;
import com.hiveview.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Title：LogoService.java
 * Description：Logo管理
 * Company：hiveview.com
 * Author：antony
 * Email：songwenjian@btte.net
 * 2016年03月15日 下午15:34:02
 */
@Service
public class LogoService {

    @Autowired
    LogoDao logoDao;

    @Autowired
    ZoneCityDao zoneCityDao;

    @Autowired
    ZoneTreeDao zoneTreeDao;

    @Autowired
    private RedisService redisService;

    @Autowired
    private flushRedisService flushRedisService;


	//获取Logo列表
	public ScriptPage getList(Logo logo) {
		List<Logo> rows = logoDao.getList(logo);
		int total = logoDao.count(logo);
		ScriptPage scriptPage = new ScriptPage();
		scriptPage.setRows(rows);
		scriptPage.setTotal(total);
		return scriptPage;
	}

    //查询此logo是否存在
    public int getTotal(Logo logo) {
        int total = logoDao.count(logo);
        return total;
    }

	public boolean update(Logo logo) {
		boolean flag = false;
        //this.redisDelByLabelByCityId(logo.getId(),Integer.valueOf(logo.getCityId()));
        /*ZoneCity zoneCity = zoneCityDao.getInfoByCity(logo.getCityId());
        if(null != zoneCity){
            logo.setCountryId(zoneCity.getZoneId());
            logo.setProvinceId(zoneCity.getProvinceId());
        }*/
  /*      if(logo.getCityId() != null){
            AreaInfo areaInfoCity = this.getTreeId(Integer.valueOf(logo.getCityId()));
            AreaInfo areaInfoPro = this.getTreeId(areaInfoCity.getFid());
            AreaInfo areaInfoCou = this.getTreeId(areaInfoPro.getFid());
            if(null != areaInfoPro){
                logo.setProvinceId(String.valueOf(areaInfoPro.getId()));
            }
            if(null != areaInfoCou){
                logo.setCountryId(String.valueOf(areaInfoCou.getId()));
            }
        }*/
        int count = logoDao.update(logo);
		if(count == 1){
			flag = true;
			if("0".equals(logo.getProvinceId()) && "0".equals(logo.getCityId())){
			    logo.setProvinceId("00");
                logo.setCityId("0000");
            }
            this.flushRedisService.flushCityByIp("logoListCache",logo.getProvinceId(),logo.getCityId());
		}
		return flag;
	}
	
	public boolean del(Logo logo){
		boolean flag = false;
        //清除Logo缓存
        this.redisDelByLabelById(logo.getId());
        int count = logoDao.delete(logo);
		if(count>0){
			flag = true;
            if("0".equals(logo.getProvinceId()) && "0".equals(logo.getCityId())){
                logo.setProvinceId("00");
                logo.setCityId("0000");
            }
			this.flushRedisService.flushCityByIp("logoListCache",logo.getProvinceId(),logo.getCityId());
		}
		return flag;
	}
	public Logo getLogoById(Integer logo){
	    return  this.logoDao.getLogoById(logo);
    }

	@SuppressWarnings("unchecked")
	public boolean add(Logo logo) {
		boolean flag = false;
        /*ZoneCity zoneCity = zoneCityDao.getInfoByCity(logo.getCityId());
        if(null != zoneCity){
            logo.setCountryId(zoneCity.getZoneId());
            logo.setProvinceId(zoneCity.getProvinceId());
        }*/
        /*AreaInfo areaInfoCity = this.getTreeId(logo.getCityId());
        AreaInfo areaInfoPro = this.getTreeId(areaInfoCity.getFid());
        AreaInfo areaInfoCou = this.getTreeId(areaInfoPro.getFid());
        if(null != areaInfoPro){
            logo.setProvinceId(areaInfoPro.getId());
        }
        if(null != areaInfoCou){
            logo.setCountryId(areaInfoCou.getId());
        }*/
        logo.setOnlineType(0);
        logo.setEffective(1);
		int count = logoDao.save(logo);
        if(count>0){
           flag = true;
            if("0".equals(logo.getProvinceId()) && "0".equals(logo.getCityId())){
                logo.setProvinceId("00");
                logo.setCityId("0000");
            }
           this.flushRedisService.flushCityByIp("logoListCache",logo.getProvinceId(),logo.getCityId());
        }
		return flag;
	}

    public AreaInfo getTreeId(Integer id){
        AreaInfo bean = new AreaInfo();
        bean.setId(id);
        return zoneTreeDao.get(bean);
    }
	
    public void redisDelByLabel(Logo logo){
        if(null != logo){
            String key = Constant.LOGO_LIST_CACHE+"_"+logo.getCountryId()+"_"+logo.getProvinceId()+"_"+logo.getCityId();
            redisService.del(key);
        }
    }

    public void redisDelByLabelById(Integer id){
        Logo logo = logoDao.getLogoById(id);
        if(null != logo){
            String key = Constant.LOGO_LIST_CACHE+"_"+logo.getCountryId()+"_"+logo.getProvinceId()+"_"+logo.getCityId();
            redisService.del(key);
        }
    }

    public void redisDelByLabelByCityId(Integer id,String cityId){
        Logo logo = logoDao.getLogoById(id);
        if(null != logo){
            if(logo.getCityId() != cityId){
                String key = Constant.LOGO_LIST_CACHE+"_"+logo.getCountryId()+"_"+logo.getProvinceId()+"_"+logo.getCityId();
                redisService.del(key);
            }
        }
    }
	
}
