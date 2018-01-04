package com.hiveview.service.localLife;

import com.hiveview.dao.localLife.ContentDao;
import com.hiveview.dao.localLife.LabelContentDao;
import com.hiveview.dao.localLife.LabelDao;
import com.hiveview.dao.localLife.RecommendDao;
import com.hiveview.dao.sys.ZoneCityDao;
import com.hiveview.dao.sys.ZoneTreeDao;
import com.hiveview.entity.bo.ScriptPage;
import com.hiveview.entity.localLife.*;
import com.hiveview.service.RedisService;
import com.hiveview.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Title：ContentService.java
 * Description：本地生活 内容
 * Company：hiveview.com
 * Author：swj
 * Email：songwenjian@btte.net
 * 2016年06月22日 下午15:34:02
 */
@Service
public class ContentService {

    @Autowired
    ContentDao contentDao;

    @Autowired
    RecommendDao recommendDao;

    @Autowired
    LabelContentDao labelContentDao;

    @Autowired
    LabelDao labelDao;

    @Autowired
    ZoneCityDao zoneCityDao;

    @Autowired
    ZoneTreeDao zoneTreeDao;

    @Autowired
    private RedisService redisService;


    @Autowired
    private flushRedisService flushRedisService;
	
	//获取内容
	public ScriptPage getList(Content content) {
		List<Content> rows = contentDao.getList(content);
		int total = contentDao.count(content);
		ScriptPage scriptPage = new ScriptPage();
		scriptPage.setRows(rows);
		scriptPage.setTotal(total);
		return scriptPage;
	}

    //获取推荐内容
    public ScriptPage getContentByRecommendID(Content content) {
        List<Content> rows = contentDao.getContentByRecommendID(content);
        int total = contentDao.getCountByRecommendID(content);
        ScriptPage scriptPage = new ScriptPage();
        scriptPage.setRows(rows);
        scriptPage.setTotal(total);
        return scriptPage;
    }

    //获取标签内容
    public ScriptPage getContentByLabelID(Content content) {
        List<Content> rows = null;
        int total = 0;
        if( 1 == content.getLabelExist()){
            rows = contentDao.getContentByLabelID1(content);
            total = contentDao.getCountByLabelID1(content);
        }
        if( 0 == content.getLabelExist()){
            rows = contentDao.getContentByLabelID0(content);
            total = contentDao.getCountByLabelID0(content);
        }
        ScriptPage scriptPage = new ScriptPage();
        scriptPage.setRows(rows);
        scriptPage.setTotal(total);
        return scriptPage;
    }

	public boolean update(Content content) {
		boolean flag = false;
        /*ZoneCity zoneCity = zoneCityDao.getInfoByCity(content.getCityId());
        if(null != zoneCity){
            content.setCountryId(zoneCity.getZoneId());
            content.setProvinceId(zoneCity.getProvinceId());
        }*/
/*        if(null != content.getCityId()){
            AreaInfo areaInfoCity = this.getTreeId(Integer.valueOf(content.getCityId()));
            AreaInfo areaInfoPro = this.getTreeId(areaInfoCity.getFid());
            AreaInfo areaInfoCou = this.getTreeId(areaInfoPro.getFid());
            if(null != areaInfoPro){
                content.setProvinceId(String.valueOf(areaInfoPro.getId()));
            }
            if(null != areaInfoCou){
                content.setCountryId(String.valueOf(areaInfoCou.getId()));
            }
        }*/
		int count = contentDao.update(content);
		if(count==1){
			flag = true;
            //删除内容缓存
            if("0".equals(content.getProvinceId()) && "0".equals(content.getCityId())) {
                content.setProvinceId("00");
                content.setCityId("0000");
            }
            this.flushRedisService.flushCityByIp("contentCache",content.getProvinceId(),content.getCityId());
            this.flushRedisService.flushCityByIp("recommendListCache",content.getProvinceId(),content.getCityId());
            this.flushRedisService.flushCityByIp("labelListCache",content.getProvinceId(),content.getCityId());
            //删除推荐缓存
            Recommend r = new Recommend();
            r.setContentId(content.getId());
            List<Recommend> recommendList = recommendDao.getList(r);
            if(null != recommendList){
                for(Recommend recommend : recommendList){
                    if("0".equals(content.getProvinceId()) && "0".equals(content.getCityId())) {
                        content.setProvinceId("00");
                        content.setCityId("0000");
                    }
                    this.flushRedisService.flushCityByIp("contentCache",content.getProvinceId(),content.getCityId());
                    this.flushRedisService.flushCityByIp("recommendListCache",content.getProvinceId(),content.getCityId());
                    this.flushRedisService.flushCityByIp("labelListCache",content.getProvinceId(),content.getCityId());
                }
            }
            //删除标签缓存
            LabelContent l = new LabelContent();
            l.setContentId(content.getId());
            List<LabelContent> labelContentList =  labelContentDao.getList(l);
            if(null != labelContentList){
                for(LabelContent labelContent : labelContentList){
                    Label label = labelDao.getLabelById(labelContent.getLabelId());
                    if(null != label){
                        if("0".equals(content.getProvinceId()) && "0".equals(content.getCityId())) {
                            content.setProvinceId("00");
                            content.setCityId("0000");
                        }
                        this.flushRedisService.flushCityByIp("contentCache",content.getProvinceId(),content.getCityId());
                        this.flushRedisService.flushCityByIp("recommendListCache",content.getProvinceId(),content.getCityId());
                        this.flushRedisService.flushCityByIp("labelListCache",content.getProvinceId(),content.getCityId());
                    }
                }
            }
		}
		return flag;
	}
	
	public boolean del(Content content){
		boolean flag = false;

        //删除内容
        int count = contentDao.delete(content);
        if(count>0){
            flag = true;
            if("0".equals(content.getProvinceId()) && "0".equals(content.getCityId())){
                content.setProvinceId("00");
                content.setCityId("0000");
            }
            //删除内容缓存
            if("0".equals(content.getProvinceId()) && "0".equals(content.getCityId())) {
                content.setProvinceId("00");
                content.setCityId("0000");
            }
            this.flushRedisService.flushCityByIp("contentCache",content.getProvinceId(),content.getCityId());
            this.flushRedisService.flushCityByIp("recommendListCache",content.getProvinceId(),content.getCityId());
            this.flushRedisService.flushCityByIp("labelListCache",content.getProvinceId(),content.getCityId());
            //删除关联推荐
            Recommend r = new Recommend();
            r.setContentId(content.getId());
            List<Recommend> recommendList = recommendDao.getList1(r);
            if(null != recommendList){
                for(Recommend recommend : recommendList){

                    recommend.setContentId(0);
                    recommend.setTypeId(0);
                    int updateCount = recommendDao.update(recommend);
                    if(updateCount>0){
                   /*     String key = Constant.RECOMMEND_LIST_CACHE+"_"+recommend.getCountryId()+"_"+recommend.getProvinceId()+"_"+recommend.getCityId();
                        redisService.del(key);*/
                        if("0".equals(content.getProvinceId()) && "0".equals(content.getCityId())) {
                            content.setProvinceId("00");
                            content.setCityId("0000");
                        }
                        this.flushRedisService.flushRedisByContentId("contentCache",content.getId());
                        this.flushRedisService.flushCityByIp("recommendListCache",content.getProvinceId(),content.getCityId());
                        this.flushRedisService.flushCityByIp("labelListCache",content.getProvinceId(),content.getCityId());
                    }
                }
            }
            //删除关联标签
            LabelContent l = new LabelContent();
            l.setContentId(content.getId());
            List<LabelContent> labelContentList =  labelContentDao.getList(l);
            if(null != labelContentList){
                for(LabelContent labelContent : labelContentList){
                    int deleteCount = labelContentDao.delete(labelContent);
                    if(deleteCount>0){
                        Label label = labelDao.getLabelById(labelContent.getLabelId());
                        if(null != label){
                       /*     String key = Constant.LABEL_LIST_CACHE+"_"+label.getCountryId()+"_"+label.getProvinceId()+"_"+label.getCityId();
                            redisService.del(key);*/
                            if("0".equals(content.getProvinceId()) && "0".equals(content.getCityId())){
                                content.setProvinceId("00");
                                content.setCityId("0000");
                            }
                            this.flushRedisService.flushRedisByContentId("contentCache",content.getId());
                            this.flushRedisService.flushCityByIp("recommendListCache",content.getProvinceId(),content.getCityId());
                            this.flushRedisService.flushCityByIp("labelListCache",content.getProvinceId(),content.getCityId());
                        }
                    }
                }
            }
        }
		return flag;
	}

    public AreaInfo getTreeId(Integer id){
        AreaInfo bean = new AreaInfo();
        bean.setId(id);
        return zoneTreeDao.get(bean);
    }
	
	@SuppressWarnings("unchecked")
	public boolean add(Content content) {
		boolean flag = false;
        //ZoneCity zoneCity = zoneCityDao.getInfoByCity(content.getCityId());
        /*AreaInfo areaInfoCity = this.getTreeId(content.getCityId());
        AreaInfo areaInfoPro = this.getTreeId(areaInfoCity.getFid());
        AreaInfo areaInfoCou = this.getTreeId(areaInfoPro.getFid());
        if(null != areaInfoPro){
            content.setProvinceId(areaInfoPro.getId());
        }
        if(null != areaInfoCou){
            content.setCountryId(areaInfoCou.getId());
        }*/
        content.setEffective(1);
        content.setOnlineType(0);
		int activityId = contentDao.save(content);
		if(activityId>0){
			flag = true;
            if("0".equals(content.getProvinceId()) && "0".equals(content.getCityId())) {
                content.setProvinceId("00");
                content.setCityId("0000");
            }
            this.flushRedisService.flushCityByIp("contentCache",content.getProvinceId(),content.getCityId());
            this.flushRedisService.flushCityByIp("recommendListCache",content.getProvinceId(),content.getCityId());
            this.flushRedisService.flushCityByIp("labelListCache",content.getProvinceId(),content.getCityId());
		}
		return flag;
	}

    public void redisDelByConstantId(Integer id){
        String key = Constant.CONTENT_CACHE+"_"+id;
        redisService.del(key);
    }

}
