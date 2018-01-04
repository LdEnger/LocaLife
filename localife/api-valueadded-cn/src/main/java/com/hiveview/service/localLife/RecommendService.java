package com.hiveview.service.localLife;

import com.hiveview.dao.localLife.RecommendDao;
import com.hiveview.entity.localLife.Recommend;
import com.hiveview.entity.vo.localLife.RecommendVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 
 * @author Antony
 *
 */
@Service
public class RecommendService {
    @Autowired
    private RecommendDao recommendDao;


    /*@Cacheable(value = "getRecommendListCache", key = "#countryId+'_'+#areaId+'_'+#cityId")*/
    public List<RecommendVo> getRecommendList(Integer countryId,String areaId,String cityId) {
        Recommend recommend = new Recommend();
        recommend.setCountryId(countryId);
        recommend.setProvinceId(areaId);
        recommend.setCityId(cityId);
		List<RecommendVo> list = recommendDao.getRecommendList(recommend);
		return list;
	}
}
