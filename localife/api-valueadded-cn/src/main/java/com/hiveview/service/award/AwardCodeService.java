package com.hiveview.service.award;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hiveview.dao.award.AwardCodeDao;
import com.hiveview.entity.po.award.AwardActivity;
import com.hiveview.entity.po.award.AwardCode;

/**
 * 
 * @author wengjingchang
 *
 */
@Service
public class AwardCodeService {
	@Autowired
	private AwardCodeDao awardCodeDao;

	public List<AwardCode> getList(AwardCode awardCode) {
		return awardCodeDao.getList(awardCode);
	}
	
	public int count(AwardCode awardCode) {
		return awardCodeDao.count(awardCode);
	}
	
	public List<AwardCode> getListByDetailId(Integer detailId) {
		AwardCode awardCode = new AwardCode();
		awardCode.setDetailId(detailId);
		return getList(awardCode);
	}
	
	public int countByDetailId(Integer detailId) {
		AwardCode awardCode = new AwardCode();
		awardCode.setDetailId(detailId);
		return count(awardCode);
	}
	
	/**
	 * 取未使用中奖码
	 * @param detailId
	 * @param acceptFlag
	 * @return
	 */
	public AwardCode getUnUsedCode(int detailId,int acceptFlag){
		AwardCode ac = new AwardCode();
		ac.setDetailId(detailId);
		ac.setAcceptFlag(acceptFlag);
		return awardCodeDao.getUnUsedCode(ac);
	}
	
	public AwardCode getListByDetailIdAndCode(int detailId,String awardCode){
		AwardCode ac = new AwardCode();
		ac.setDetailId(detailId);
		ac.setAwardCode(awardCode); 
		return awardCodeDao.get(ac);
	}
	
	public int update(AwardCode awardCode){
		return awardCodeDao.update(awardCode);
	}
	
	/**
	 * 判断中奖码是否为空
	 * @return
	 */
	public boolean isAwardCodeNull(AwardActivity at){
		boolean flag = false;
		
		return flag;
	}
}
