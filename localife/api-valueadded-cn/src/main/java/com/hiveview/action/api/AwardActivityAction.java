package com.hiveview.action.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hiveview.common.ApiConstants;
import com.hiveview.entity.bo.ApiResult;
import com.hiveview.entity.bo.ApiResultTypeEnum;
import com.hiveview.entity.po.award.AwardActivity;
import com.hiveview.entity.po.award.AwardDetail;
import com.hiveview.entity.po.award.AwardOrder;
import com.hiveview.entity.po.award.AwardPlay;
import com.hiveview.entity.po.award.AwardUser;
import com.hiveview.entity.vo.api.AwardActivityHomeVo;
import com.hiveview.entity.vo.api.AwardActivityVo;
import com.hiveview.entity.vo.api.AwardAddressStatusVo;
import com.hiveview.entity.vo.api.AwardDetailPayVo;
import com.hiveview.entity.vo.api.AwardDetailVo;
import com.hiveview.entity.vo.api.AwardDiscountInfoVo;
import com.hiveview.entity.vo.api.AwardDrawVo;
import com.hiveview.entity.vo.api.AwardGameInfoVo;
import com.hiveview.entity.vo.api.AwardPlayVo;
import com.hiveview.service.award.AwardActivityService;
import com.hiveview.service.award.AwardCodeService;
import com.hiveview.service.award.AwardDetailService;
import com.hiveview.service.award.AwardOrderService;
import com.hiveview.service.award.AwardPlayService;
import com.hiveview.service.award.AwardUserService;

/**
 * 抽奖活动接口
 * 
 * @author wengjingchang
 *
 */
@Controller
@RequestMapping(value = "/api/award")
public class AwardActivityAction {
	private static final Logger DATA = LoggerFactory.getLogger("data");

	@Autowired
	private AwardActivityService awardActivityService;
	@Autowired
	private AwardPlayService awardPlayService;
	@Autowired
	private AwardDetailService awardDetailService;
	@Autowired
	private AwardCodeService awardCodeService;
	@Autowired
	private AwardUserService awardUserService;
	@Autowired
	private AwardOrderService awardOrderService;

	/**
	 * 1.获取所有抽奖活动基础信息列表
	 * 
	 * 获取列表信息后，同时判断活动是否过期，用户是否有参与资格
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/getActivityHomePageList/{userId}/{isVip}/{timestamp}/{sign}", method = RequestMethod.GET)
	@ResponseBody
	public ApiResult getActivityHomePageList(@PathVariable String timestamp, @PathVariable String userId, @PathVariable Integer isVip, @PathVariable String sign) {
		ApiResult op = new ApiResult();
		try {
			List<AwardActivityVo> list = awardActivityService.getActivityHomePageList();
			if (list == null) {
				op.setCode(ApiResultTypeEnum.ERR_NULL.getCode());
				op.setDesc("活动数据获取为空");
				return op;
			}
			AwardUser awardUser = awardUserService.getByUserId(userId);
			if (awardUser == null) {
				awardUser = new AwardUser();
				awardUser.setUserId(userId);
				awardUserService.save(awardUser);
			}
			for (int i = 0; i < list.size(); i++) {
				AwardActivity ay = awardActivityService.getById(list.get(i).getActivityId());
				String desc = awardActivityService.checkActivity(ay, userId, isVip);
				if (desc != null) {
					list.get(i).setIsRight(2);
					list.get(i).setRightDesc(desc);
				} else {
					list.get(i).setIsRight(1);
					list.get(i).setRightDesc("");
				}
			}
			op.setResult(list);
		} catch (Exception e) {
			DATA.info("获取所有抽奖活动基础信息列表,接口异常", e);
			op.setCode(ApiResultTypeEnum.ERR.getCode());
			op.setDesc(ApiResultTypeEnum.ERR.getType());
		}
		return op;
	}

	/**
	 * 2.获取单个活动首页信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/getActivityHomePage/{activityId}/{userId}/{isVip}/{timestamp}/{sign}", method = RequestMethod.GET)
	@ResponseBody
	public ApiResult getActivityHomePage(@PathVariable Integer activityId, @PathVariable String userId, @PathVariable Integer isVip, @PathVariable Long timestamp, @PathVariable String sign) {
		ApiResult op = new ApiResult();
		try {
			AwardActivityHomeVo v = new AwardActivityHomeVo();
			// 判断是否拥有资格参与活动
			AwardActivity ay = awardActivityService.getById(activityId);
			if (ay == null) {
				op.setCode(ApiResultTypeEnum.ERR_NULL.getCode());
				op.setDesc("活动数据获取为空");
				return op;
			}
			String desc = awardActivityService.checkActivity(ay, userId, isVip);
			if (desc != null) {
				v.setIsRight(2);
				v.setRightDesc(desc);
				op.setResult(v);
				return op;
			}
			v = awardActivityService.getActivityHomePage(activityId);
			if (v == null) {
				op.setCode(ApiResultTypeEnum.ERR_NULL.getCode());
				op.setDesc("获取单个活动首页信息为空");
				return op;
			}
			v.setIsRight(1);
			op.setResult(v);
		} catch (Exception e) {
			DATA.info("获取单个活动首页信息,接口异常", e);
			op.setCode(ApiResultTypeEnum.ERR.getCode());
			op.setDesc(ApiResultTypeEnum.ERR.getType());
		}
		return op;
	}

	/**
	 * 3.获取用户所有奖品（折扣卷）信息列表
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/getAwardList/{userId}/{awardPropertys}/{timestamp}/{sign}", method = RequestMethod.GET)
	@ResponseBody
	public ApiResult getAwardList(@PathVariable String userId, @PathVariable String awardPropertys, @PathVariable Long timestamp, @PathVariable String sign) {
		ApiResult op = new ApiResult();
		try {
			List<AwardPlayVo> list = null;
			if (awardPropertys == null || awardPropertys.length() < 1) {
				op.setCode(ApiResultTypeEnum.ERR_1.getCode());
				op.setDesc(ApiResultTypeEnum.ERR_1.getType());
				return op;
			}
			List<String> tmp = Arrays.asList(awardPropertys.split(","));
			List<Integer> awardPropertyList = new ArrayList<Integer>();
			for (int i = 0; i < tmp.size(); i++) {
				// 99 为查询全部
				if (tmp.get(i).equals("99")) {
					awardPropertyList = null;
					break;
				}
				awardPropertyList.add(Integer.valueOf(tmp.get(i)));
			}
			list = awardPlayService.getAwardList(userId, awardPropertyList);
			if (list == null || list.size() == 0) {
				op.setCode(ApiResultTypeEnum.ERR_NULL.getCode());
				op.setDesc("获取用户所有奖品（折扣卷）信息列表为空");
				return op;
			}
			Date now = new Date();
			for (AwardPlayVo v : list) {
				if (v.getAwardPrice() != null) {
					v.setAwardPrice(v.getAwardPrice() / 100f);
				}
				if (v.getOrderStatus().equals(2)) {
					continue;
				}
				if (now.compareTo(v.getAvailableEndTime()) > 0) {
					v.setOrderStatus(3);
				}
			}
			op.setResult(list);
		} catch (Exception e) {
			DATA.info("获取用户所有奖品（折扣卷）信息列表,接口异常", e);
			op.setCode(ApiResultTypeEnum.ERR.getCode());
			op.setDesc(ApiResultTypeEnum.ERR.getType());
		}
		return op;
	}

	/**
	 * 4.获取用户某一奖品详情信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/getAwardDetail/{userId}/{detailId}/{awardCode}/{timestamp}/{sign}", method = RequestMethod.GET)
	@ResponseBody
	public ApiResult getAwardDetail(@PathVariable String userId, @PathVariable Integer detailId, @PathVariable String awardCode, @PathVariable Long timestamp, @PathVariable String sign) {
		ApiResult op = new ApiResult();
		try {
			AwardDetailVo adv = awardDetailService.getAwardDetail(userId, detailId, awardCode);
			if (adv == null) {
				op.setCode(ApiResultTypeEnum.ERR_NULL.getCode());
				op.setDesc(ApiResultTypeEnum.ERR_NULL.getType());
				return op;
			}
			Date now = new Date();
			if (!adv.getOrderStatus().equals(2)) {
				if (now.compareTo(adv.getAvailableEndTime()) > 0) {
					adv.setOrderStatus(3);
				}
			}
			if (adv.getAwardPrice() != null) {
				adv.setAwardPrice(adv.getAwardPrice() / 100f);
			}
			AwardDetail ad = awardDetailService.getById(adv.getDetailId());
			String url = ApiConstants.ACT_URL + "award/awardMail/show.html?userId=" + userId + "&awardCode=" + awardCode + "&detailId=" + ad.getId() + "&userInfoType=" + ad.getUserInfoType();
			adv.setQrCodeUrl(url);
			op.setResult(adv);
		} catch (Exception e) {
			DATA.info("获取用户某一奖品详情信息,接口异常", e);
			op.setCode(ApiResultTypeEnum.ERR.getCode());
			op.setDesc(ApiResultTypeEnum.ERR.getType());
		}
		return op;
	}

	/**
	 * 5.抽奖接口 </br> play表,中奖-1,未中奖-2 </br> 订单表,已发送-1,未发送-2
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/drawAward/{userId}/{userName}/{mac}/{sn}/{deviceCode}/{activityId}/{timestamp}/{sign}", method = RequestMethod.GET)
	@ResponseBody
	public ApiResult drawAward(@PathVariable String userId, @PathVariable String userName, @PathVariable String mac, @PathVariable String sn, @PathVariable String deviceCode, @PathVariable int activityId, @PathVariable Long timestamp, @PathVariable String sign) {
		ApiResult op = new ApiResult();
		if (StringUtils.isEmpty(userId)) {
			op.setCode(ApiResultTypeEnum.ERR_NULL.getCode());
			op.setDesc("用户ID为空");
			return op;
		}
		AwardDrawVo adv = new AwardDrawVo();
		try {
			AwardActivity at = awardActivityService.getById(activityId);
			if (at == null) {
				op.setCode(ApiResultTypeEnum.ERR_NULL.getCode());
				op.setDesc("活动数据为空");
				return op;
			}
			List<AwardDetail> ad = awardDetailService.getListByActivityId(at.getId());
			if (ad == null) {
				op.setCode(ApiResultTypeEnum.ERR_NULL.getCode());
				op.setDesc("奖品数据为空");
				return op;
			}
			for (AwardDetail awardDetail : ad) {
				int count = awardCodeService.countByDetailId(awardDetail.getId());
				if (count == 0) {
					op.setCode(ApiResultTypeEnum.ERR_NULL.getCode());
					op.setDesc("中奖码数据为空");
					return op;
				}
			}
			String playPrompt = awardActivityService.checkDrawTimes(at, userId);
			// 总抽奖次数或每日抽奖次数用完
			if (playPrompt != null) {
				adv.setIsWin(2);
				adv.setPlayLeftTimes(0);
				adv.setPlayPrompt(playPrompt);
				op.setResult(adv);
				return op;
			}
			AwardPlay ap = new AwardPlay();
			ap.setMac(mac);
			ap.setSn(sn);
			ap.setDeviceCode(deviceCode);
			ap.setActivityId(activityId);
			ap.setUserId(userId);
			ap.setUserName(userName);
			ap.setAwardCode("-1");// 默认
			ap.setPlayTime(new Date());
			ap.setAcceptFlag(2);// 未中奖
			// 保存参与记录,此时状态是未中奖
			int flag = awardPlayService.savePlay(ap);
			if (flag != 1) {
				op.setCode(ApiResultTypeEnum.ERR.getCode());
				op.setDesc(ApiResultTypeEnum.ERR.getType());
				return op;
			}
			// 判断是否重复中奖
			adv = awardPlayService.checkWinAgain(at, ap);
			if (adv != null) {
				op.setResult(adv);
				return op;
			}
			// 对用户表操作,为了取得用户表主键作为随机种子的一部分
			int key = awardUserService.getPrimaryKey(userId);
			adv = awardPlayService.drawAward(key, at, ap);
			if (adv.getIsWin().equals(1)) {
				awardOrderService.awardOrdering(ap, adv.getDetailId());
			}
			op.setResult(adv);
		} catch (Exception e) {
			DATA.info("抽奖接口,接口异常", e);
			op.setCode(ApiResultTypeEnum.ERR.getCode());
			op.setDesc(ApiResultTypeEnum.ERR.getType());
		}
		return op;
	}

	/**
	 * 7.获取游戏基本信息接口
	 * 
	 * @param activityId
	 * @param userId
	 * @param type
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/getGameInfo/{activityId}/{userId}/{type}/{timestamp}/{sign}", method = RequestMethod.GET)
	@ResponseBody
	public ApiResult getGameInfo(@PathVariable Integer activityId, @PathVariable String userId, @PathVariable Integer type, @PathVariable Long timestamp, @PathVariable String sign) {
		ApiResult op = new ApiResult();
		try {
			AwardGameInfoVo agv = new AwardGameInfoVo();
			agv = awardActivityService.getGameInfo(activityId);
			if (agv == null) {
				op.setCode(ApiResultTypeEnum.ERR_NULL.getCode());
				op.setDesc("获取游戏基本信息接口为空");
				return op;
			}
			op.setResult(agv);
		} catch (Exception e) {
			DATA.info("获取游戏基本信息接口,接口异常", e);
			op.setCode(ApiResultTypeEnum.ERR.getCode());
			op.setDesc(ApiResultTypeEnum.ERR.getType());
		}
		return op;
	}

	/**
	 * 8.获取支付奖品相关校验信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/getAwardDetailForPay/{detailId}/{userId}/{awardCode}/{timestamp}/{sign}", method = RequestMethod.GET)
	@ResponseBody
	public ApiResult getAwardDetailForPay(@PathVariable Integer detailId, @PathVariable String userId, @PathVariable String awardCode, @PathVariable Long timestamp, @PathVariable String sign) {
		ApiResult op = new ApiResult();
		AwardPlay ap = new AwardPlay();
		try {
			ap.setUserId(userId);
			ap.setDetailId(detailId);
			ap.setAwardCode(awardCode);
			AwardDetailPayVo adv = awardPlayService.getAwardDetailForPay(ap);
			if (adv == null) {
				op.setCode(ApiResultTypeEnum.ERR_NULL.getCode());
				op.setDesc("获取支付奖品相关校验信息为空");
				return op;
			}
			op.setResult(adv);
		} catch (Exception e) {
			DATA.info("获取支付奖品相关校验信息,接口异常", e);
			op.setCode(ApiResultTypeEnum.ERR.getCode());
			op.setDesc(ApiResultTypeEnum.ERR.getType());
		}
		return op;
	}

	/**
	 * 9.获取邮寄地址是否填写
	 * 
	 * @param detailId
	 * @param userId
	 * @param awardCode
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/getAwardAddressStatus/{detailId}/{userId}/{awardCode}/{timestamp}/{sign}", method = RequestMethod.GET)
	@ResponseBody
	public ApiResult getAwardAddressStatus(@PathVariable Integer detailId, @PathVariable String userId, @PathVariable String awardCode, @PathVariable Long timestamp, @PathVariable String sign) {
		ApiResult op = new ApiResult();
		try {
			AwardAddressStatusVo v = new AwardAddressStatusVo();
			AwardOrder t = new AwardOrder();
			t.setAwardDetailId(detailId);
			t.setAwardUserId(userId);
			t.setAwardCode(awardCode);
			AwardOrder ao = awardOrderService.get(t);
			if (ao == null) {
				op.setCode(ApiResultTypeEnum.ERR_NULL.getCode());
				op.setDesc("获取中奖订单信息为空");
				return op;
			}
			AwardDetail ad = new AwardDetail();
			v.setAddressStatus(ao.getOrderStatus());
			// 1:是,2:否
			if (ao.getOrderStatus().equals(2)) {
				ad = awardDetailService.getById(detailId);
				if (ad == null) {
					op.setCode(ApiResultTypeEnum.ERR_NULL.getCode());
					op.setDesc("获取奖品信息为空");
					return op;
				}
				String url = ApiConstants.ACT_URL + "award/awardMail/show.html?userId=" + userId + "&awardCode=" + awardCode + "&detailId=" + ad.getId() + "&userInfoType=" + ad.getUserInfoType();
				v.setQrCodeUrl(url);
			}
			op.setResult(v);
		} catch (Exception e) {
			DATA.info("获取邮寄地址是否填写,接口异常", e);
			op.setCode(ApiResultTypeEnum.ERR.getCode());
			op.setDesc(ApiResultTypeEnum.ERR.getType());
		}
		return op;
	}

	/**
	 * 10.获取折扣卷支付相关信息
	 * 
	 * @param activityId
	 * @param userId
	 * @param type
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/getPayInfo/{detailId}/{userId}/{awardCode}/{timestamp}/{sign}", method = RequestMethod.GET)
	@ResponseBody
	public ApiResult getPayInfo(@PathVariable Integer detailId, @PathVariable String userId, @PathVariable String awardCode, @PathVariable Long timestamp, @PathVariable String sign) {
		ApiResult op = new ApiResult();
		try {
			AwardDiscountInfoVo v = new AwardDiscountInfoVo();
			AwardOrder o = new AwardOrder();
			o.setAwardDetailId(detailId);
			o.setAwardUserId(userId);
			o.setAwardCode(awardCode);
			v = awardOrderService.getAwardDiscountBaseInfo(o);
			if (v == null) {
				op.setCode(ApiResultTypeEnum.ERR_NULL.getCode());
				op.setDesc("获取折扣卷支付基本信息为空");
				return op;
			}
			if (v.getAwardPrice() == null) {
				op.setCode(ApiResultTypeEnum.ERR_NULL.getCode());
				op.setDesc("获取折扣卷奖品金额为空");
				return op;
			}
			v = awardOrderService.getAwardDiscountProcessInfo(v);
			op.setResult(v);
		} catch (Exception e) {
			DATA.info("获取支付相关信息,接口异常", e);
			op.setCode(ApiResultTypeEnum.ERR.getCode());
			op.setDesc(ApiResultTypeEnum.ERR.getType());
		}
		return op;
	}
}
