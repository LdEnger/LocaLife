package com.hiveview.dao.award;

import com.hiveview.dao.BaseDao;
import com.hiveview.entity.po.award.AwardUser;

public interface AwardUserDao extends BaseDao<AwardUser> {
	AwardUser getByUserId(String userId);
}
