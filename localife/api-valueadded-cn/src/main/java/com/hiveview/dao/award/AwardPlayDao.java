package com.hiveview.dao.award;

import java.util.List;
import java.util.Map;

import com.hiveview.dao.BaseDao;
import com.hiveview.entity.po.award.AwardPlay;
import com.hiveview.entity.vo.api.AwardDetailPayVo;
import com.hiveview.entity.vo.api.AwardPlayVo;

public interface AwardPlayDao extends BaseDao<AwardPlay> {
	int acceptAward(String userId);

	List<AwardPlayVo> getAwardList(Map<String,Object> map);
	
	AwardDetailPayVo getAwardDetailForPay(AwardPlay ap);
	
	int updateAcceptFlag(AwardPlay ap);
	
	int winLimitDayCount(AwardPlay ap);
}
