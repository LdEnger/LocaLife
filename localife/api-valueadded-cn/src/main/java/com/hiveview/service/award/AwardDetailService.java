package com.hiveview.service.award;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hiveview.dao.award.AwardDetailDao;
import com.hiveview.entity.po.award.AwardDetail;
import com.hiveview.entity.vo.api.AwardDetailVo;

/**
 * 
 * @author wengjingchang
 *
 */
@Service
public class AwardDetailService {
	@Autowired
	private AwardDetailDao awardDetailDao;
	@Autowired
	private AwardActivityService awardActivityService;

	public AwardDetailVo getAwardDetail(String userId, Integer detailId, String awardCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("detailId", detailId);
		map.put("awardCode", awardCode);
		return awardDetailDao.getAwardDetail(map);
	}

	public AwardDetail getById(int detailId) {
		AwardDetail awardDetail = new AwardDetail();
		awardDetail.setId(detailId);
		return awardDetailDao.get(awardDetail);
	}

	public List<AwardDetail> getList(AwardDetail awardDetail) {
		return awardDetailDao.getList(awardDetail);
	}

	public List<AwardDetail> getListByActivityId(Integer activityId) {
		AwardDetail awardDetail = new AwardDetail();
		awardDetail.setActivityId(activityId);
		return awardDetailDao.getList(awardDetail);
	}
	
	/**
	 * 更新当前奖品数量
	 * @param awardDetail
	 * @return
	 */
	public int updateCurrentAmount(AwardDetail awardDetail){
		return awardDetailDao.updateCurrentAmount(awardDetail);
	}
}
