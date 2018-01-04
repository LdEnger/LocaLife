package com.hiveview.dao.award;

import com.hiveview.dao.BaseDao;
import com.hiveview.entity.po.award.AwardCode;

public interface AwardCodeDao extends BaseDao<AwardCode> {
	AwardCode getUnUsedCode(AwardCode awardCode);
}
