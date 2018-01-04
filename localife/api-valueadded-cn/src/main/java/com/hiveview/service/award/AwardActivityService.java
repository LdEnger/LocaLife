package com.hiveview.service.award;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.hiveview.common.ApiConstants;
import com.hiveview.common.ParamConstants;
import com.hiveview.dao.award.AwardActivityDao;
import com.hiveview.entity.po.award.AwardActivity;
import com.hiveview.entity.po.award.AwardPlay;
import com.hiveview.entity.vo.api.AwardActivityHomeVo;
import com.hiveview.entity.vo.api.AwardActivityVo;
import com.hiveview.entity.vo.api.AwardGameInfoVo;
import com.hiveview.pay.http.HiveHttpEntityType;
import com.hiveview.pay.http.HiveHttpPost;
import com.hiveview.pay.http.HiveHttpResponse;
import com.hiveview.service.RedisService;
import com.hiveview.util.DateUtilThread;

/**
 * 
 * @author wengjingchang
 *
 */
@Service
public class AwardActivityService {
	@Autowired
	private AwardActivityDao awardActivityDao;

	@Autowired
	private AwardPlayService awardPlayService;

	@Autowired
	private RedisService redisService;

	public AwardGameInfoVo getGameInfo(Integer activityId) {
		return awardActivityDao.getGameInfo(activityId);
	}

	/**
	 * 进行中活动/未开始活动(活动开始时间升序)+已结束活动(活动结束时间降序),已下线(不显示)不返回数据
	 * 
	 * @return
	 */
	public List<AwardActivityVo> getActivityHomePageList() {
		String key = ParamConstants.RED_KY_ACT_HOME_PAGE_LIST;
		long expire = 5 * 60L;
		if (redisService.exists(key)) {
			return redisService.lRange(key, 0L, -1L, AwardActivityVo.class);
		} else {
			List<AwardActivityVo> list = new ArrayList<AwardActivityVo>();
			List<AwardActivityVo> online = awardActivityDao.getActivityHomeListOnline();
			if (online != null && online.size() > 0) {
				list.addAll(online);
			}
			List<AwardActivityVo> offline = awardActivityDao.getActivityHomeListOffline();
			if (offline != null && offline.size() > 0) {
				list.addAll(offline);
			}
			if (list != null && list.size() > 0) {
				redisService.rPush(key, list, AwardActivityVo.class, expire);
			}
			return list;
		}
	}

	public AwardActivityHomeVo getActivityHomePage(Integer activityId) {
		return awardActivityDao.getActivityHomePage(activityId);
	}

	public AwardActivity getById(int activityId) {
		AwardActivity awardActivity = new AwardActivity();
		awardActivity.setId(activityId);
		return awardActivityDao.get(awardActivity);
	}

	public List<AwardActivity> getList(AwardActivity awardActivity) {
		return awardActivityDao.getList(awardActivity);
	}

	/**
	 * 判断用户参与资格
	 * 
	 * @param activityId
	 * @param userId
	 * @return
	 */
	public String checkActivity(AwardActivity ay, String userId, Integer isVip) {
		String desc = null;
		desc = checkActivityDate(ay);
		if (desc != null) {
			return desc;
		}
		desc = checkUserGroups(ay, userId, isVip);
		if (desc != null) {
			return desc;
		}
		desc = checkDrawTimes(ay, userId);
		if (desc != null) {
			return desc;
		}
		return desc;
	}

	/**
	 * 判断活动期限 在活动期限外返回true
	 * 
	 * @return
	 */
	public String checkActivityDate(AwardActivity ay) {
		Date begin = ay.getBeginTime();
		Date end = ay.getEndTime();
		Date now = new Date();
		if (now.compareTo(begin) < 0) {
			return "本次活动未开始";
		}
		if (now.compareTo(end) > 0) {
			return "活动已截止,请关注下次活动";
		}
		return null;
	}

	/**
	 * 判断是否绑定手机 需要绑定手机返回true
	 * 
	 * @return
	 */
	public String checkBindPhone(AwardActivity ay) {
		if (ay.getPhoneBindType() == 2) {
			return "需要绑定手机";
		}
		return null;
	}

	/**
	 * 判断活动适用人群
	 * 
	 * @return
	 */
	public String checkUserGroups(AwardActivity ay, String userId, Integer isVip) {
		// 需要取用户vip信息，消费数据
		int type = ay.getAwardPlayerType();
		if (type == 2) {
			// 判断是不是vip用户
			if (!isVip.equals(1)) {
				return "本活动只有vip用户可以参加";
			}
			return null;
		} else if (type == 3) {
			// 判断消费用户
			String begin = DateUtilThread.date2Full(ay.getPayBeginTime());
			String end = DateUtilThread.date2Full(ay.getPayEndTime());
			Map<String, String> map = new HashMap<String, String>();
			map.put("userid", userId);
			map.put("beginTime", begin);
			map.put("endTime", end);
			HiveHttpResponse resp = HiveHttpPost.postMap(ApiConstants.ACT_CONSUME_API, map, HiveHttpEntityType.STRING);
			if (resp.statusCode != HttpStatus.SC_OK || StringUtils.isEmpty(resp.entityString)) {
				return "用户消费数据获取失败，请稍后再试";
			}
			String code = JSONObject.parseObject(resp.entityString).getString("code");
			if (StringUtils.isEmpty(code) || !code.equals("000")) {
				return "用户消费数据获取失败，请稍后再试";
			}
			Long sum = JSONObject.parseObject(resp.entityString).getLong("result");
			if (sum == null) {
				return "用户消费数据获取失败，请稍后再试";
			}
			if (ay.getPaySum() == 0) {
				return null;
			}
			if (sum < ay.getPaySum()) {
				return "本次活动只有在" + begin + "至" + end + "消费超过" + ay.getPaySum() / 100 + "元的用户有资格抽取奖品";
			}
			return null;
		}
		return null;
	}

	/**
	 * 抽奖次数限定 </br> 总抽奖次数 = 每天总抽奖次数 * 总活动时间
	 * 
	 * @return
	 */
	public String checkDrawTimes(AwardActivity ay, String userId) {
		// 每天总抽奖次数
		int playLimitDay = ay.getPlayLimitDay();
		// 总抽奖次数
		int playLimitTotal = ay.getPlayLimitTotal();
		if (playLimitDay == 0) {
			return null;
		}
		Integer activityId = ay.getId();
		// 用户总参与次数
		AwardPlay ap = new AwardPlay();
		ap.setActivityId(activityId);
		ap.setUserId(userId);
		List<AwardPlay> listTotal = awardPlayService.getList(ap);
		if (listTotal == null) {
			return null;
		}
		if (listTotal.size() >= playLimitTotal) {
			return ay.getPlayPromptTotal();
		}

		// 当天用户参与次数
		Date now = new Date();
		AwardPlay awardPlay = new AwardPlay();
		awardPlay.setActivityId(activityId);
		awardPlay.setUserId(userId);
		awardPlay.setPlayTimeYMD(now);
		List<AwardPlay> list = awardPlayService.getList(awardPlay);
		if (list == null) {
			return null;
		}
		if (list.size() >= playLimitDay) {
			return ay.getPlayPromptDay();
		}
		return null;
	}

	public List<AwardActivityVo> getActivityInfoList(AwardActivity t) {
		List<AwardActivityVo> list = awardActivityDao.getActivityInfoList(t);
		return list;
	}
}
