package com.hiveview.service.award;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hiveview.dao.award.AwardUserDao;
import com.hiveview.entity.po.award.AwardUser;

/**
 * 
 * @author wengjingchang
 *
 */
@Service
public class AwardUserService {
	@Autowired
	private AwardUserDao awardUserDao;

	/**
	 * 获取用户表主键ID
	 * @param userId
	 * @return
	 */
	public int getPrimaryKey(String userId) {
		AwardUser au = getByUserId(userId);
		if (au == null) {
			AwardUser awardUser = new AwardUser();
			awardUser.setUserId(userId);
			save(awardUser);
		}
		return au.getId();
	}

	public AwardUser getByUserId(String userId) {
		AwardUser awardUser = new AwardUser();
		awardUser.setUserId(userId);
		return get(awardUser);
	}

	/**
	 * 保存，判断是insert还是update
	 * 
	 * @param awardUser
	 * @return
	 */
	public int saveOrUpdate(AwardUser awardUser) {
		try {
			AwardUser au = getByUserId(awardUser.getUserId());
			if (au != null) {
				awardUser.setId(au.getId());
				return update(awardUser);
			} else {
				return save(awardUser);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * 返回主键id
	 * 
	 * @param awardUser
	 * @return
	 */
	public int update(AwardUser awardUser) {
		try {
			return awardUserDao.update(awardUser);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public int save(AwardUser awardUser) {
		try {
			return awardUserDao.save(awardUser);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public AwardUser get(AwardUser awardUser) {
		return awardUserDao.get(awardUser);
	}

	public List<AwardUser> getList(AwardUser awardUser) {
		return awardUserDao.getList(awardUser);
	}
}
