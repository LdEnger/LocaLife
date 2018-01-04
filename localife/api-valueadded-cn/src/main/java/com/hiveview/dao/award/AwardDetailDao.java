package com.hiveview.dao.award;

import java.util.List;
import java.util.Map;

import com.hiveview.dao.BaseDao;
import com.hiveview.entity.po.award.AwardDetail;
import com.hiveview.entity.vo.api.AwardDetailVo;

public interface AwardDetailDao extends BaseDao<AwardDetail> {
	List<AwardDetail> getListByActivityId(Integer ActivityId);

	AwardDetailVo getAwardDetail(Map<String, Object> map);

	int updateCurrentAmount(AwardDetail awardDetail);
}
