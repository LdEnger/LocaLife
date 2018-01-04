package com.hiveview.service.localLife;

import com.hiveview.dao.localLife.RecommendDao;
import com.hiveview.dao.sys.ZoneCityDao;
import com.hiveview.dao.sys.ZoneTreeDao;
import com.hiveview.entity.bo.ScriptPage;
import com.hiveview.entity.localLife.AreaInfo;
import com.hiveview.entity.localLife.Recommend;
import com.hiveview.service.RedisService;
import com.hiveview.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Title：RecommendService.java
 * Description：推荐位管理
 * Company：hiveview.com
 * Author：antony
 * Email：songwenjian@btte.net
 * 2016年03月15日 下午15:34:02
 */
@Service
public class RecommendService {


    @Autowired
    RecommendDao recommendDao;

    @Autowired
    ZoneCityDao zoneCityDao;

    @Autowired
    ZoneTreeDao zoneTreeDao;

    @Autowired
    private RedisService redisService;

    @Autowired
    private flushRedisService flushRedisService;

	public ScriptPage getList(Recommend recommend) {
		List<Recommend> rows = recommendDao.getList(recommend);
		int total = recommendDao.count(recommend);
		ScriptPage scriptPage = new ScriptPage();
		scriptPage.setRows(rows);
		scriptPage.setTotal(total);
		return scriptPage;
	}

    public AreaInfo getTreeId(Integer id){
        AreaInfo bean = new AreaInfo();
        bean.setId(id);
        return zoneTreeDao.get(bean);
    }

	public boolean update(Recommend recommend) {
		boolean flag = false;
        //this.redisDelByRecommendByCityId(recommend.getId(),Integer.valueOf(recommend.getCityId()));
        /*ZoneCity zoneCity = zoneCityDao.getInfoByCity(recommend.getCityId());
        if(null != zoneCity){
            recommend.setCountryId(zoneCity.getZoneId());
            recommend.setProvinceId(zoneCity.getProvinceId());
        }*/
/*        if(recommend.getCityId() != null){
            AreaInfo areaInfoCity = this.getTreeId(Integer.valueOf(recommend.getCityId()));
            AreaInfo areaInfoPro = this.getTreeId(areaInfoCity.getFid());
            AreaInfo areaInfoCou = this.getTreeId(areaInfoPro.getFid());
            if(null != areaInfoPro){
                recommend.setProvinceId(String.valueOf(areaInfoPro.getId()));
            }
            if(null != areaInfoCou){
                recommend.setCountryId(String.valueOf(areaInfoCou.getId()));
            }
        }*/
			int count = recommendDao.update(recommend);
			if (count == 1) {
				flag = true;
				Recommend c = new Recommend();
				c = this.recommendDao.getRecommendById(recommend.getId());
				if("0".equals(c.getProvinceId()) && "0".equals(c.getCityId())){
					recommend.setProvinceId("00");
					recommend.setCityId("0000");
				}
				this.flushRedisService.flushCityByIp("recommendListCache",c.getProvinceId(),c.getCityId());
			}
		return flag;
	}

	/**
	 * 关联内容方法  如果没有关联内容 则进行关联
	 * @param recommend
	 * @return
	 */
		public boolean contentDel(Recommend recommend){
    	boolean falg = false;
		Recommend contetId = recommendDao.getRecommendById(recommend.getId());
		int con = contetId.getContentId();
		if(con == 0){
			falg = true;
			this.update1(recommend);
		}
		return falg;
	}
	public boolean update1(Recommend recommend) {
		boolean flag = false;
		//this.redisDelByRecommendByCityId(recommend.getId(),Integer.valueOf(recommend.getCityId()));
        /*ZoneCity zoneCity = zoneCityDao.getInfoByCity(recommend.getCityId());
        if(null != zoneCity){
            recommend.setCountryId(zoneCity.getZoneId());
            recommend.setProvinceId(zoneCity.getProvinceId());
        }*/
/*        if(recommend.getCityId() != null){
            AreaInfo areaInfoCity = this.getTreeId(Integer.valueOf(recommend.getCityId()));
            AreaInfo areaInfoPro = this.getTreeId(areaInfoCity.getFid());
            AreaInfo areaInfoCou = this.getTreeId(areaInfoPro.getFid());
            if(null != areaInfoPro){
                recommend.setProvinceId(String.valueOf(areaInfoPro.getId()));
            }
            if(null != areaInfoCou){
                recommend.setCountryId(String.valueOf(areaInfoCou.getId()));
            }
        }*/
		int count = recommendDao.update(recommend);
		if (count == 1) {
			flag = true;
			Recommend c = new Recommend();
			c = this.recommendDao.getRecommendById(recommend.getId());
			if("0".equals(c.getProvinceId()) && "0".equals(c.getCityId())){
				recommend.setProvinceId("00");
				recommend.setCityId("0000");
			}
			this.flushRedisService.flushCityByIp("recommendListCache",c.getProvinceId(),c.getCityId());
		}
		return flag;
	}
	public boolean del(Recommend recommend){
		boolean flag = false;
        //清除推荐位缓存
        this.redisDelByRecommendByID(recommend.getId());
        int count = recommendDao.delete(recommend);
		if(count>0){
            flag = true;
			if("0".equals(recommend.getProvinceId()) && "0".equals(recommend.getCityId())){
				recommend.setProvinceId("00");
				recommend.setCityId("0000");
			}
            this.flushRedisService.flushCityByIp("recommendListCache",recommend.getProvinceId(),recommend.getCityId());
		}
		return flag;
	}
	
	@SuppressWarnings("unchecked")
	public boolean add(Recommend recommend) {
		boolean flag = false;
		int seq = this.getMinSequence1(recommend);
		seq--;
        recommend.setSeq(seq);
        /*ZoneCity zoneCity = zoneCityDao.getInfoByCity(recommend.getCityId());
        if(null != zoneCity){
            recommend.setCountryId(zoneCity.getZoneId());
            recommend.setProvinceId(zoneCity.getProvinceId());
        }*/
        /*AreaInfo areaInfoCity = this.getTreeId(recommend.getCityId());
        AreaInfo areaInfoPro = this.getTreeId(areaInfoCity.getFid());
        AreaInfo areaInfoCou = this.getTreeId(areaInfoPro.getFid());
        if(null != areaInfoPro){
            recommend.setProvinceId(areaInfoPro.getId());
        }
        if(null != areaInfoCou){
            recommend.setCountryId(areaInfoCou.getId());
        }*/
        recommend.setOnlineType(0);
        recommend.setContentId(0);
        recommend.setTypeId(0);
        recommend.setEffective(1);
		int count = recommendDao.save(recommend);

		if(count>0){
			flag = true;
			if("0".equals(recommend.getProvinceId()) && "0".equals(recommend.getCityId())){
				recommend.setProvinceId("00");
				recommend.setCityId("0000");
			}
            this.flushRedisService.flushCityByIp("recommendListCache",recommend.getProvinceId(),recommend.getCityId());
		}
		return flag;
	}

    public int getMaxSequence(Recommend recommend){
		Integer maxSequence = recommendDao.getMaxSequence(recommend);
		if(maxSequence==null){
			maxSequence=0;
		}
		return maxSequence;
	}
	public int getMinSequence(Recommend recommend){
    	Integer minSequence = recommendDao.getMinSequence(recommend);
    	if(minSequence == null){
    		minSequence = 0;
		}
		return minSequence;
	}
	public int getMinSequence1(Recommend recommend){
		Integer minSequence = recommendDao.getMinSequence1(recommend);
		if(minSequence == null){
			minSequence = 0;
		}
		return minSequence;
	}
	public Recommend getAwardActivityBySeq(Recommend recommend){
		Recommend result = recommendDao.getBySeq(recommend);
		return result;
	}

	public Integer getBiggerSeq(Recommend recommend){
		Integer result = recommendDao.getBigger(recommend);
		return result;
	}
	
	public Integer getSmallerSeq(Recommend recommend){
		Integer result = recommendDao.getSmaller( recommend);
		return result;
	}
	

	public boolean move(Recommend recommend,int type) {//type==1的时候是上移，其他的是下移
		boolean flag = false;
        Recommend aa = this.getAwardActivityBySeq(recommend);
		Integer changeSequence = 0;
		if(type==1){
			changeSequence=this.getSmallerSeq(recommend);
		}else{
			changeSequence=this.getBiggerSeq(recommend);
		}
		Recommend ra = new Recommend();
		ra.setSeq(changeSequence);
		ra.setCityId(recommend.getCityId());
		ra.setProvinceId(recommend.getProvinceId());
		ra.setName(recommend.getName());
        Recommend biggerAa = this.getAwardActivityBySeq(ra);
		aa.setSeq(changeSequence);
		biggerAa.setSeq(recommend.getSeq());
		boolean aaFlag = this.update(aa);
		boolean biggerFlag = false;
		if(aaFlag){
			biggerFlag = this.update(biggerAa);
		}
		if(biggerFlag){
			flag = true;
		}
		return flag;
	}

	public boolean topMove(Recommend recommend){
		boolean flag = false;

		//根据顺序查询
		Recommend recommend1 = this.getAwardActivityBySeq(recommend);
		Integer changSequence = this.getMinSequence(recommend);
		recommend1.setSeq(changSequence -1 );
		boolean seqFlag = this.update(recommend1);
		if(seqFlag){
			flag = true;
		}
		return flag;
	}
    public void redisDelByRecommend(Recommend recommend){
        if(null != recommend){
            String key = Constant.RECOMMEND_LIST_CACHE+"_"+recommend.getCountryId()+"_"+recommend.getProvinceId()+"_"+recommend.getCityId();
            redisService.del(key);
        }
    }

    public void redisDelByRecommendByID(Integer id){
        Recommend recommend = recommendDao.getRecommendById(id);
        if(null != recommend){
            String key = Constant.RECOMMEND_LIST_CACHE+"_"+recommend.getCountryId()+"_"+recommend.getProvinceId()+"_"+recommend.getCityId();
            redisService.del(key);
        }
    }
    public void redisDelByRecommendByCityId(Integer id,String cityId){
        Recommend recommend = recommendDao.getRecommendById(id);
        if(null != recommend){
            if(recommend.getCityId() != cityId){
                String key = Constant.RECOMMEND_LIST_CACHE+"_"+recommend.getCountryId()+"_"+recommend.getProvinceId()+"_"+recommend.getCityId();
                redisService.del(key);
            }
        }
    }
	
}
