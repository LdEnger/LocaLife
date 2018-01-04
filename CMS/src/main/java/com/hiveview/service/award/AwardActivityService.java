package main.java.com.hiveview.service.award;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hiveview.dao.award.AwardActivityDao;
import com.hiveview.dao.award.AwardCodeDao;
import com.hiveview.dao.award.AwardDao;
import com.hiveview.entity.bo.ScriptPage;
import com.hiveview.entity.po.award.Award;
import com.hiveview.entity.po.award.AwardActivity;
import com.hiveview.entity.po.award.AwardCode;
import com.hiveview.util.DateUtil;

/**
 * Title：AwardActivityService.java
 * Description：活动管理服务类
 * Company：hiveview.com
 * Author：周恩至
 * Email：zhouenzhi@btte.net 
 * 2016年03月15日 下午15:34:02
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class AwardActivityService {

	@Autowired
	AwardActivityDao awardActivityDao;
	@Autowired
	AwardDao awardDao;
	@Autowired
	AwardCodeDao awardCodeDao;
	
	
	public synchronized void add(AwardActivity awardActivity) {
		String strBeginTime=awardActivity.getStrBeginTime();
		String strEndTime=awardActivity.getStrEndTime();
		String strPayBeginTime=awardActivity.getStrPayBeginTime();
		String strPayEndTime=awardActivity.getStrPayEndTime();
		if(!StringUtils.isEmpty(strBeginTime)){
			awardActivity.setBeginTime(DateUtil.date2Str(strBeginTime));
		}
		if(!StringUtils.isEmpty(strEndTime)){
			awardActivity.setEndTime(DateUtil.date2Str(strEndTime));
		}
		if(!StringUtils.isEmpty(strPayBeginTime)){
			awardActivity.setPayBeginTime(DateUtil.date2Str(strPayBeginTime));
		}
		if(!StringUtils.isEmpty(strPayEndTime)){
			awardActivity.setPayEndTime(DateUtil.date2Str(strPayEndTime));
		}
		int sequence=awardActivityDao.getMaxSequence();
		awardActivity.setSequence(sequence);
		awardActivity.setShowFlag(2);
		awardActivityDao.save(awardActivity);
		int activityId=awardActivity.getId();
		List<Award> awardList=this.getAwardList(awardActivity.getAwardList());
		for(Award award:awardList){
			award.setActivityId(activityId);
			awardDao.save(award);
			int awardCodeType=award.getAwardCodeType();
			if(awardCodeType==1){
				int detailId=award.getId();
				String strDate = DateUtil.dateToMin(new Date(), "yyyyMMddHHmm");
				for(int i=0;i<award.getAwardAmount();i++){
					AwardCode awardCode=new AwardCode();
					awardCode.setDetailId(detailId);
					awardCode.setAwardCodeType(awardCodeType);
					awardCode.setAwardCode("ZD"+detailId+"ABC"+i+strDate);
					awardCode.setAcceptFlag(2);
					awardCodeDao.add(awardCode);
				}
			}
		}
	}
	private List<Award> getAwardList(String awardList){
		List<Award> list = new ArrayList<Award>();
		if(!StringUtils.isEmpty(awardList)){
			JSONArray ja = JSON.parseArray(awardList);
			Iterator<Object> it = ja.iterator();
			while (it.hasNext()) {
				JSONObject record = (JSONObject) it.next();
				Award award = new Award();
				award.setId(record.getIntValue("id"));
				award.setAwardType(record.getString("awardType"));
				award.setAwardName(record.getString("awardName"));
				award.setAwardPicUrl(record.getString("awardPicUrl"));
				award.setAwardIconUrl(record.getString("awardIconUrl"));
				award.setAwardDesc(record.getString("awardDesc"));
				award.setAvailableBeginTime(DateUtil.date3Str(record.getString("availableBeginTime")));
				award.setAvailableEndTime(DateUtil.date3Str(record.getString("availableEndTime")));
				award.setAwardAmount(record.getIntValue("awardAmount"));
				award.setAwardCurrentAmount(award.getAwardAmount());
				award.setWinLimitDay(record.getIntValue("winLimitDay"));
				award.setAwardCodeType(record.getIntValue("awardCodeType"));
				award.setAwardProperty(record.getIntValue("awardProperty"));
				award.setAwardQrcodeFlag(record.getIntValue("awardQrcodeFlag"));
				award.setUserInfoType(record.getString("userInfoType"));
				award.setAwardVipDuration(record.getIntValue("awardVipDuration"));
				String awardVideoId=record.getString("awardVideoId");
				if(!StringUtils.isEmpty(awardVideoId)){
					String[] args=awardVideoId.split(",");
					award.setAwardVideoId(Integer.parseInt(args[0]));
					award.setAwardVideoPartnerId(args[1]);
					award.setAwardPrice(Integer.parseInt(args[2]));
					if(args[3]!=null && (!args[3].equals("null"))){
						award.setAwardDisVipDuration(Integer.parseInt(args[3]));
					}
				}
				award.setAwardVideoName(record.getString("awardVideoName"));
				award.setAwardMlAmount(record.getIntValue("awardMlAmount"));
				award.setAwardVideoType(record.getString("awardVideoType"));
				award.setAwardDiscount(record.getIntValue("awardDiscount"));
				award.setAwardPayUrl(record.getString("awardPayUrl"));
				list.add(award);
			}
		}
		return list;
	}
	
	public synchronized void update(AwardActivity awardActivity) {
		String strBeginTime=awardActivity.getStrBeginTime();
		String strEndTime=awardActivity.getStrEndTime();
		String strPayBeginTime=awardActivity.getStrPayBeginTime();
		String strPayEndTime=awardActivity.getStrPayEndTime();
		if(!StringUtils.isEmpty(strBeginTime)){
			awardActivity.setBeginTime(DateUtil.date2Str(strBeginTime));
		}
		if(!StringUtils.isEmpty(strEndTime)){
			awardActivity.setEndTime(DateUtil.date2Str(strEndTime));
		}
		if(!StringUtils.isEmpty(strPayBeginTime)){
			awardActivity.setPayBeginTime(DateUtil.date2Str(strPayBeginTime));
		}
		if(!StringUtils.isEmpty(strPayEndTime)){
			awardActivity.setPayEndTime(DateUtil.date2Str(strPayEndTime));
		}
		Integer activityId=awardActivity.getId();
		if(activityId==null || activityId<=0){
			throw new RuntimeException("更新awardActivity失败！");
		}
		awardActivityDao.update(awardActivity);
		
		String ids=awardDao.selectIdsByActivityId(activityId);
		List<Integer> idList=new ArrayList<Integer>();
		for(String id:ids.split(",")){
			idList.add(Integer.parseInt(id));
		}
		
		List<Award> awardList=this.getAwardList(awardActivity.getAwardList());
		for(Award award:awardList){
			Integer id=award.getId();
			if(id!=null && id>0){
				awardDao.update(award);
				idList.remove(id);
				/*hll-2016-07-14修改
				int awardCodeType=award.getAwardCodeType();
				if(awardCodeType==1){
					int detailId=id;
					String strDate = DateUtil.dateToMin(new Date(), "yyyyMMddHHmm");
					int oldAwardAmount=awardCodeDao.selectAwardCodeCountByDetailId(detailId);
					int nowAwardAmount=award.getAwardAmount();
					if(nowAwardAmount>oldAwardAmount){
						for(int i=oldAwardAmount;i<nowAwardAmount;i++){
							AwardCode awardCode=new AwardCode();
							awardCode.setDetailId(detailId);
							awardCode.setAwardCodeType(awardCodeType);
							awardCode.setAwardCode("ZD"+detailId+"ABC"+i+strDate);
							awardCode.setAcceptFlag(2);
							awardCodeDao.add(awardCode);
						}
					}
				}*/
			}
			else{
				award.setActivityId(activityId);
				awardDao.save(award);
				int awardCodeType=award.getAwardCodeType();
				if(awardCodeType==1){
					int detailId=award.getId();
					String strDate = DateUtil.dateToMin(new Date(), "yyyyMMddHHmm");
					for(int i=0;i<award.getAwardAmount();i++){
						AwardCode awardCode=new AwardCode();
						awardCode.setDetailId(detailId);
						awardCode.setAwardCodeType(awardCodeType);
						awardCode.setAwardCode("ZD"+detailId+"ABC"+i+strDate);
						awardCode.setAcceptFlag(2);
						awardCodeDao.add(awardCode);
					}
				}
			}
		}
		if(idList.size()>0){
			awardDao.deleteAward(activityId, idList);
		}
	}
	
	public ScriptPage getList(AwardActivity awardActivity) {
		ScriptPage scriptPage = new ScriptPage();
		List<AwardActivity> awardActivityList = awardActivityDao.getList(awardActivity);
		for (AwardActivity temp : awardActivityList) {
			Award award = new Award();
			award.setActivityId(temp.getId());
			List<Award> awards = awardDao.getList(award);
			temp.setAwards(awards);
		}
		int total = awardActivityDao.count(awardActivity);
		scriptPage.setRows(awardActivityList);
		scriptPage.setTotal(total);
		return scriptPage;
	}
	
	public void del(AwardActivity awardActivity){
		Integer activityId=awardActivity.getId();
		if(activityId==null || activityId<=0){
			throw new RuntimeException("删除awardActivity失败！");
		}
		String detailIds=awardDao.selectIdsByActivityId(activityId);
		for(String detailId:detailIds.split(",")){
			awardCodeDao.deleteAwardCodeByDetailId(Integer.parseInt(detailId));
		}
		Award award=new Award();
		award.setActivityId(activityId);
		awardDao.delete(award);
		awardActivityDao.delete(awardActivity);
	}
	
	public synchronized void move(int sequence,int type) {
		//type==1的时候是上移，其他的是下移
		AwardActivity awardActivity = awardActivityDao.getBySeq(sequence);
		Integer toSequence=0;
		if(type==1){
			toSequence=awardActivityDao.getBigger(sequence);
		}
		else{
			toSequence=awardActivityDao.getSmaller(sequence);
		}
		awardActivity.setSequence(toSequence);
		
		AwardActivity awardActivity2 = awardActivityDao.getBySeq(toSequence);
		awardActivity2.setSequence(sequence);
		
		if(awardActivity.getId()<=0 || awardActivity2.getId()<=0){
			throw new RuntimeException("上下移失败！");
		}
		awardActivityDao.update(awardActivity);
		awardActivityDao.update(awardActivity2);
	}
	public synchronized void updateShowFlagById(AwardActivity awardActivity){
		Integer activityId=awardActivity.getId();
		if(activityId==null || activityId<=0){
			throw new RuntimeException("更新awardActivity失败,上下线失败！");
		}
		awardActivityDao.update(awardActivity);
	}
	
	public List<Map<String,String>> selectAwardActivityType(){
		return awardActivityDao.selectAwardActivityType();
	}
	
	//以下为其它模块所用
	/*
	 * 中奖码页调用
	 */
	public AwardActivity getAwardActivityById(int activityId) {
		AwardActivity aa =awardActivityDao.getAwardActivityById(activityId);
		return aa;
	}
	
}
