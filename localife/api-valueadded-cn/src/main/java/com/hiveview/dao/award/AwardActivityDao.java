package com.hiveview.dao.award;

import java.util.List;

import com.hiveview.dao.BaseDao;
import com.hiveview.entity.po.award.AwardActivity;
import com.hiveview.entity.vo.api.AwardActivityHomeVo;
import com.hiveview.entity.vo.api.AwardActivityVo;
import com.hiveview.entity.vo.api.AwardGameInfoVo;

public interface AwardActivityDao extends BaseDao<AwardActivity> {
	List<AwardActivityVo> getActivityHomePageList(AwardActivity t);

	List<AwardActivityVo> getActivityHomeListOnline();

	List<AwardActivityVo> getActivityHomeListOffline();

	AwardActivityHomeVo getActivityHomePage(Integer activityId);

	List<AwardActivityVo> getActivityInfoList(AwardActivity t);

	AwardGameInfoVo getGameInfo(Integer activityId);
}
