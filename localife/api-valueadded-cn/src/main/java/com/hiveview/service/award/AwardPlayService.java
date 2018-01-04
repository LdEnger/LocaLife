package com.hiveview.service.award;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hiveview.dao.award.AwardPlayDao;
import com.hiveview.entity.po.award.AwardActivity;
import com.hiveview.entity.po.award.AwardCode;
import com.hiveview.entity.po.award.AwardDetail;
import com.hiveview.entity.po.award.AwardPlay;
import com.hiveview.entity.vo.api.AwardDetailPayVo;
import com.hiveview.entity.vo.api.AwardDrawVo;
import com.hiveview.entity.vo.api.AwardPlayVo;

/**
 * 
 * @author wengjingchang
 *
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class AwardPlayService {
	@Autowired
	private AwardPlayDao awardPlayDao;
	@Autowired
	private AwardDetailService awardDetailService;
	@Autowired
	private AwardActivityService awardActivityService;
	@Autowired
	private AwardUserService awardUserService;
	@Autowired
	private AwardCodeService awardCodeService;
	@Autowired
	private AwardOrderService awardOrderService;

	/**
	 * 获取参与详细列表
	 * 
	 * @param ap
	 * @return
	 */
	public AwardDetailPayVo getAwardDetailForPay(AwardPlay ap) {
		return awardPlayDao.getAwardDetailForPay(ap);
	}

	/**
	 * 判断是否重复中奖
	 * @param at
	 * @param ap
	 * @return
	 */
	public AwardDrawVo checkWinAgain(AwardActivity at, AwardPlay ap) {
		AwardDrawVo adv = new AwardDrawVo();
		int again = at.getAwardWinAgain();
		if (again == 2) {
			AwardPlay tmp = new AwardPlay();
			tmp.setUserId(ap.getUserId());
			tmp.setActivityId(at.getId());
			//中奖
			tmp.setAcceptFlag(1);
			int count = count(tmp);
			if (count > 0) {
				adv.setIsWin(2);
				adv.setPlayLeftTimes(countPlayLeftTimes(at, ap));
				adv.setPlayPrompt(at.getPlayPromptLost());
				return adv;
			}
		}
		return null;
	}
	
	/**
	 * 抽奖
	 * 
	 * @param key
	 *            用户主键id做随机种子
	 * @param ap
	 * @return
	 */
	public AwardDrawVo drawAward(int key,AwardActivity at,AwardPlay ap) {
		AwardDrawVo adv = new AwardDrawVo();
		Integer activityId = at.getId();
		// 判断是否中奖,未中奖返回数据
		if (!isWin(key, at.getAwardWinRatio())) {
			adv.setIsWin(2);
			adv.setPlayLeftTimes(countPlayLeftTimes(at, ap));
			adv.setPlayPrompt(at.getPlayPromptLost());
			return adv;
		}
		// 中奖，判断奖项
		AwardDetail dl = getRandomPrize(activityId, key);
		if (dl == null) {
			adv.setIsWin(2);
			adv.setPlayLeftTimes(countPlayLeftTimes(at, ap));
			adv.setPlayPrompt(at.getPlayPromptLost());
			return adv;
		}
		// 中奖后，更新play中信息
		ap.setDetailId(dl.getId());
		ap.setAwardName(dl.getAwardName());
		ap.setAwardType(dl.getAwardType());
		AwardCode acs = awardCodeService.getUnUsedCode(dl.getId(), 2);
		ap.setAwardCode(acs.getAwardCode());
		// 更新中奖码状态为已分配
		acs.setAcceptFlag(1);
		awardCodeService.update(acs);
		ap.setAcceptFlag(1);
		update(ap);
		// 当前奖品数量减1
		int amount = dl.getAwardCurrentAmount() - 1;
		dl.setAwardCurrentAmount(amount);
		// 更新当前奖品数量
		awardDetailService.updateCurrentAmount(dl);
		adv.setIsWin(1);
		adv.setDetailId(dl.getId());
		adv.setAwardCode(acs.getAwardCode());
		adv.setAwardName(dl.getAwardName());
		adv.setAwardType(dl.getAwardType());
		adv.setAwardProperty(dl.getAwardProperty());
		adv.setPlayLeftTimes(countPlayLeftTimes(at, ap));
		adv.setPlayPrompt(at.getPlayPromptWin());
		return adv;
	}

	/**
	 * 计算剩余次数
	 * 每日抽取次数 - 每日参与次数
	 * @param at
	 * @param ap
	 * @return
	 */
	private Integer countPlayLeftTimes(AwardActivity at, AwardPlay ap) {
		// 当前剩余次数
		int playLeftTimes = 0;
		// 每日抽取次数
		int playLimitDay = at.getPlayLimitDay();
		if (playLimitDay == 0) {
			return null;
		}
		AwardPlay tmp = new AwardPlay();
		tmp.setUserId(ap.getUserId());
		tmp.setActivityId(at.getId());
		tmp.setPlayTimeYMD(new Date());
		int count = count(tmp);
		playLeftTimes = playLimitDay - count;
		return playLeftTimes;
	}

	/**
	 * 领奖
	 * 
	 * @param userId
	 * @return
	 */
	public int acceptAward(String userId, int detailId, String awardCode) {
		AwardPlay ap = getByUserIdAndDetailId(userId, detailId, awardCode);
		if (ap == null || ap.getAcceptFlag() == null) {
			return -1;
		}
		// 已领取，返回1
		if (ap.getAcceptFlag().equals(1)) {
			return 1;
		}
		ap.setAcceptFlag(1);
		awardPlayDao.update(ap);
		return 1;
	}

	public AwardPlay getByUserIdAndDetailId(String userId, int detailId,
			String awardCode) {
		AwardPlay awardPlay = new AwardPlay();
		awardPlay.setUserId(userId);
		awardPlay.setDetailId(detailId);
		awardPlay.setAwardCode(awardCode);
		return awardPlayDao.get(awardPlay);
	}

	/**
	 * 保存参与记录
	 * 
	 * @param awardUserId
	 * @param awardName
	 * @param activityId
	 * @return
	 */
	public int savePlay(AwardPlay awardPlay) {
		return awardPlayDao.save(awardPlay);
	}

	/**
	 * 是否中奖
	 * 
	 * @param awardUserId
	 *            用户表主键作为随机种子一部分
	 * @param awardWinRatio
	 *            中奖比率
	 * @return
	 */
	private boolean isWin(long awardUserKey, int awardWinRatio) {
		// 随机种子
		long seed = System.currentTimeMillis() + awardUserKey;
		Random random = new Random(seed);
		int r = random.nextInt(awardWinRatio);
		if (r == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 判断奖项
	 * 
	 * @param awards
	 *            key奖项名称，value奖项数量
	 * @param awardUserId
	 *            用户表主键作为随机种子一部分
	 * @return
	 */
	private synchronized AwardDetail getRandomPrize(Integer activityId,
			long awardUserId) {
		List<AwardDetail> list = awardDetailService.getListByActivityId(activityId);
		if (list == null || list.size() < 1) {
			return null;
		}
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getWinLimitDay() > 0) {
				AwardPlay tmp = new AwardPlay();
				tmp.setDetailId(list.get(i).getId());
				tmp.setPlayTime(new Date());
				int count = awardPlayDao.winLimitDayCount(tmp);
				if (count > list.get(i).getWinLimitDay()) {
					list.remove(i);
				}
			}
		}
		if (list == null || list.size() == 0) {
			return null;
		}
		
		int total = 0;
		HashMap<AwardDetail, Integer> details = new HashMap<AwardDetail, Integer>();
		for (AwardDetail ad : list) {
			details.put(ad, ad.getAwardCurrentAmount());
			total = total + ad.getAwardCurrentAmount();
		}
		if (total == 0) {
			return null;
		}
		long seed = System.currentTimeMillis() + awardUserId;
		Random random = new Random(seed);
		int nextInt = random.nextInt(total) + 1;
		int i = 0;
		for (Entry<AwardDetail, Integer> entry : details.entrySet()) {
			int[] array = { i, i + entry.getValue() };
			i = i + entry.getValue();
			if (compareSize(array, nextInt)) {
				return entry.getKey();
			}
		}
		return null;
	}

	/**
	 * 注意比较区间为(n,m]
	 * 
	 * @param array
	 * @param nextInt
	 * @return
	 */
	private boolean compareSize(int[] array, int nextInt) {
		if (nextInt > array[0] && nextInt <= array[1]) {
			return true;
		}
		return false;
	}

	public List<AwardPlay> getList(AwardPlay awardPlay) {
		return awardPlayDao.getList(awardPlay);
	}

	public List<AwardPlay> getListByUserId(String userId) {
		AwardPlay awardPlay = new AwardPlay();
		awardPlay.setUserId(userId);
		return awardPlayDao.getList(awardPlay);
	}

	public List<AwardPlayVo> getAwardList(String userId, List<Integer> awardPropertyList) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("awardPropertyList", awardPropertyList);
		return awardPlayDao.getAwardList(map);
	}

	public AwardPlay get(AwardPlay awardPlay) {
		return awardPlayDao.get(awardPlay);
	}
	
	public AwardPlay getById(Integer id){
		AwardPlay awardPlay = new AwardPlay();
		awardPlay.setId(id);
		return get(awardPlay);
	}

	public int count(AwardPlay awardPlay) {
		return awardPlayDao.count(awardPlay);
	}
	
	public int update(AwardPlay awardPlay){
		return awardPlayDao.update(awardPlay);
	}

	/**
	 * 用户填写通讯地址后，标记为领取状态
	 * 
	 * @param awardCode
	 * @param userId
	 * @param acceptFlag
	 * @return
	 */
	public int updateAcceptFlag(String awardCode, String userId,
			Integer acceptFlag) {
		AwardPlay awardPlay = new AwardPlay();
		awardPlay.setAcceptFlag(acceptFlag);
		awardPlay.setUserId(userId);
		awardPlay.setAwardCode(awardCode);
		return awardPlayDao.updateAcceptFlag(awardPlay);
	}
	
	
	public int dizhi(){
		return 0;
	}
}
