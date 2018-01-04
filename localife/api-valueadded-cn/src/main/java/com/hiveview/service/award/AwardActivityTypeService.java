package com.hiveview.service.award;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hiveview.dao.award.AwardActivityTypeDao;
import com.hiveview.entity.po.award.AwardActivityType;

/**
 * 
 * @author wengjingchang
 *
 */
@Service
public class AwardActivityTypeService {
	@Autowired
	AwardActivityTypeDao activityTypeDao;

	public List<AwardActivityType> getListAll() {
		AwardActivityType t = new AwardActivityType();
		return activityTypeDao.getList(t);
	}

	public List<AwardActivityType> getList(AwardActivityType t) {
		return activityTypeDao.getList(t);
	}
}
