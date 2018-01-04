package com.hiveview.dao.award;

import com.hiveview.dao.BaseDao;
import com.hiveview.entity.po.award.AwardOrder;
import com.hiveview.entity.vo.api.AwardDiscountInfoVo;

public interface AwardOrderDao extends BaseDao<AwardOrder> {
	AwardDiscountInfoVo getAwardDiscountInfo(AwardOrder m);
}
