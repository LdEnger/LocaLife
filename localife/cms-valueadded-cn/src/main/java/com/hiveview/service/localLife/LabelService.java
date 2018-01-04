package com.hiveview.service.localLife;

import com.hiveview.dao.localLife.LabelContentDao;
import com.hiveview.dao.localLife.LabelDao;
import com.hiveview.dao.sys.ZoneCityDao;
import com.hiveview.dao.sys.ZoneTreeDao;
import com.hiveview.entity.bo.ScriptPage;
import com.hiveview.entity.localLife.AreaInfo;
import com.hiveview.entity.localLife.Label;
import com.hiveview.entity.localLife.LabelContent;
import com.hiveview.service.RedisService;
import com.hiveview.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Title：LabelService.java
 * Description：标签管理
 * Company：hiveview.com
 * Author：antony
 * Email：songwenjian@btte.net
 * 2016年06月23日 下午15:34:02
 */
@Service
public class LabelService {

    @Autowired
    LabelDao labelDao;

    @Autowired
    LabelContentDao labelContentDao;

    @Autowired
    ZoneCityDao zoneCityDao;

    @Autowired
    private RedisService redisService;

    @Autowired
    ZoneTreeDao zoneTreeDao;


    @Autowired
    private flushRedisService flushRedisService;
	
	//获取标签
	public ScriptPage getList(Label label) {
		List<Label> rows = labelDao.getList(label);
		int total = labelDao.count(label);
		ScriptPage scriptPage = new ScriptPage();
		scriptPage.setRows(rows);
		scriptPage.setTotal(total);
		return scriptPage;
	}

	public boolean updateLabelContent(LabelContent labelContent) {
		boolean flag = false;
		int count = labelContentDao.update(labelContent);
		if(count==1){
			flag = true;
            this.redisDelByLabelId(labelContent.getLabelId());
		}
		return flag;
	}
    public boolean update(Label label) {
        boolean flag = false;
        this.redisDelByLabelCityId(label.getId(),label.getCityId());
        /*ZoneCity zoneCity = zoneCityDao.getInfoByCity(label.getCityId());
        if(null != zoneCity){
            label.setCountryId(zoneCity.getZoneId());
            label.setProvinceId(zoneCity.getProvinceId());
        }*/
       /* if(label.getCityId() != null){
            AreaInfo areaInfoCity = this.getTreeId(label.getCityId());
            AreaInfo areaInfoPro = this.getTreeId(areaInfoCity.getFid());
            AreaInfo areaInfoCou = this.getTreeId(areaInfoPro.getFid());
            if(null != areaInfoPro){
                label.setProvinceId(areaInfoPro.getId());
            }
            if(null != areaInfoCou){
                label.setCountryId(areaInfoCou.getId());
            }
        }*/
        int count = labelDao.update(label);
        if(count==1){
            flag = true;
            if("0".equals(label.getProvinceId()) && "0".equals(label.getCityId())){
                label.setProvinceId("00");
                label.setCityId("0000");
            }
            this.flushRedisService.flushCityByIp("labelListCache",label.getProvinceId(),label.getCityId());
        }
        return flag;
    }
	
	public boolean del(Label label){
		boolean flag = false;
        //清除标签缓存

        int count = labelDao.delete(label);
		if(count>0){
            flag = true;
            if("0".equals(label.getProvinceId()) && "0".equals(label.getCityId())) {
                label.setProvinceId("00");
                label.setCityId("0000");
            }
                this.flushRedisService.flushCityByIp("labelListCache",label.getProvinceId(),label.getCityId());
		}
		return flag;
	}

    public boolean delLabelContent(LabelContent labelContent){
        boolean flag = false;
        int count = labelContentDao.delete(labelContent);
        if(count>0){
            flag = true;
            this.redisDelByLabelId(labelContent.getLabelId());
        }
        return flag;
    }

    public AreaInfo getTreeId(Integer id){
        AreaInfo bean = new AreaInfo();
        bean.setId(id);
        return zoneTreeDao.get(bean);
    }
	
	@SuppressWarnings("unchecked")
	public boolean add(Label label) {
		boolean flag = true;
		int seq = this.getMinSequence1(label);
		seq--;
        label.setSeq(seq);
        label.setState(1);
        label.setOnlineType(0);
        /*ZoneCity zoneCity = zoneCityDao.getInfoByCity(label.getCityId());
        if(null != zoneCity){
            label.setCountryId(zoneCity.getZoneId());
            label.setProvinceId(zoneCity.getProvinceId());
        }*/
       /* AreaInfo areaInfoCity = this.getTreeId(label.getProvinceId());
        AreaInfo areaInfoPro = this.getTreeId(areaInfoCity.getFid());
        AreaInfo areaInfoCou = this.getTreeId(areaInfoPro.getFid());
        if(null != areaInfoPro){
            label.setProvinceId(areaInfoPro.getId());
        }
        if(null != areaInfoCou){
            label.setCountryId(areaInfoCou.getId());
        }*/

		int activityId = labelDao.save(label);
		if(activityId>0){
			flag = true;
            if("0".equals(label.getProvinceId()) && "0".equals(label.getCityId())){
                label.setProvinceId("00");
                label.setCityId("0000");
            }
            this.flushRedisService.flushCityByIp("labelListCache",label.getProvinceId(),label.getCityId());
		}
		return flag;
	}

    @SuppressWarnings("unchecked")
    public boolean addLabelContent(LabelContent labelContent) {
        boolean flag = false;
        int seq = this.getLabelContentMaxSequence();
        seq++;
        labelContent.setSeq(seq);
        int count = labelContentDao.save(labelContent);
        if(count>0){
            flag = true;
            LabelContent labelContent1 = new LabelContent();
            labelContent1.setId(labelContent.getId());
            labelContent1.setLabelId(labelContent.getLabelId());
            labelContent1 = this.labelContentDao.getCtiyIdAndProvinceID1(labelContent1);
            if("0".equals(labelContent1.getProvinceId()) && "0".equals(labelContent1.getCityId())){
                labelContent1.setProvinceId("00");
                labelContent1.setCityId("0000");
            }
            this.flushRedisService.flushCityByIp("labelListCache",labelContent1.getProvinceId(),labelContent1.getCityId());
        }
        return flag;
    }

	public int getMaxSequence(Label label){
		Integer maxSequence = labelDao.getMaxSequence(label);
		if(maxSequence==null){
			maxSequence=0;
		}
		return maxSequence;
	}
	public int getMinSequence(Label label){
        Integer minSequence = labelDao.getMinSequence(label);
        if(minSequence==null){
            minSequence=0;
        }
        return minSequence;
    }
    public int getMinSequence1(Label label){
        Integer minSequence = labelDao.getMinSequence1(label);
        if(minSequence==null){
            minSequence=0;
        }
        return minSequence;
    }
    public int getLabelContentMaxSequence( ){
        Integer maxSequence = labelContentDao.getMaxSequence();
        if(maxSequence==null){
            maxSequence=0;
        }
        return maxSequence;
    }

    public Label getAwardActivityBySeq(Label label){
        Label result = labelDao.getBySeq(label);
        return result;
    }

	public LabelContent getLabelContentBySeq(int seq,int labelId){
		LabelContent result = labelContentDao.getBySeq(seq,labelId);
		return result;
	}

	public Integer getBiggerSeq(Label label){
		Integer result = labelDao.getBigger(label);
		return result;
	}
	
	public Integer getSmallerSeq(Label label){
		Integer result = labelDao.getSmaller(label);
		return result;
	}
    public Integer getLabelContentBiggerSeq(int seq,int labelId){
        Integer result = labelContentDao.getBigger(seq,labelId);
        return result;
    }

    public Integer getLabelContentSmallerSeq(int seq,int labelId){
        Integer result = labelContentDao.getSmaller(seq,labelId);
        return result;
    }
	
	public boolean move(Label label,int type) {//type==1的时候是上移，其他的是下移
		boolean flag = false;
        Label aa = this.getAwardActivityBySeq(label);
		Integer changeSequence = 0;
		if(type==1){
			changeSequence=this.getSmallerSeq(label);
		}else if(type == 3){
		    changeSequence = this.getMinSequence(label);
            Label biggerAa = this.getAwardActivityBySeq(label);
            biggerAa.setSeq(changeSequence -1);
            boolean seqFals = this.update(biggerAa);
            if(seqFals){
                flag = true;
            }
            return flag;
        } else {
			changeSequence=this.getBiggerSeq(label);
		}
		Label la = new Label();
		la.setName(label.getName());
		la.setSeq(changeSequence);
		la.setCityId(label.getCityId());
        la.setProvinceId(label.getProvinceId());
        Label biggerAa = this.getAwardActivityBySeq(la);
		aa.setSeq(changeSequence);
		biggerAa.setSeq(label.getSeq());
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

    public boolean moveLabelContent(int seq,int type,int labelId) {//type==1的时候是上移，type==2的时候是下移,3是置顶
        boolean flag = false;
        LabelContent aa = this.getLabelContentBySeq(seq,labelId);
        Integer changeSequence = 0;
        if(type==1){
            changeSequence=this.getLabelContentBiggerSeq(seq,labelId);
        }else if(type == 3){
            Integer contentSeq= this.getLabelContentMaxSequence();
            LabelContent biggerAa = this.getLabelContentBySeq(seq,labelId);
            biggerAa.setSeq(contentSeq + 1);
            boolean seqFlag = this.updateLabelContent(biggerAa);
             if (seqFlag){
                 flag = true;
             }
             return flag;
        }else {
            changeSequence=this.getLabelContentSmallerSeq(seq,labelId);
        }
        LabelContent biggerAa = this.getLabelContentBySeq(changeSequence,labelId);
        aa.setSeq(changeSequence);
        biggerAa.setSeq(seq);
        boolean aaFlag = this.updateLabelContent(aa);
        boolean biggerFlag = false;
        if(aaFlag){
            biggerFlag = this.updateLabelContent(biggerAa);
        }
        if(biggerFlag){
            flag = true;
        }
        return flag;
    }

    public void redisDelByLabelId(Integer id){
        Label label = labelDao.getLabelById(id);
        if(null != label){
            if("0".equals(label.getProvinceId()) && "0".equals(label.getCityId())){
                label.setProvinceId("00");
                label.setCityId("0000");
            }
            this.flushRedisService.flushCityByIp("labelListCache",label.getProvinceId(),label.getCityId());
        }
    }

    public void redisDelByLabelCityId(Integer id,String cityId){
        Label label = labelDao.getLabelById(id);
        if(null != label){
            if(label.getCityId() != cityId){
                if("0".equals(label.getProvinceId()) && "0".equals(label.getCityId())){
                    label.setProvinceId("00");
                    label.setCityId("0000");
                }
                this.flushRedisService.flushCityByIp("labelListCache",label.getProvinceId(),label.getCityId());
            }
        }
    }

    public void redisDelByLabel(Label label){
        if(null != label){
            if("0".equals(label.getProvinceId()) && "0".equals(label.getCityId())){
                label.setProvinceId("00");
                label.setCityId("0000");
            }
            this.flushRedisService.flushCityByIp("labelListCache",label.getProvinceId(),label.getCityId());
        }
    }
	
}
