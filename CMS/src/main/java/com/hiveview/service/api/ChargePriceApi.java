package main.java.com.hiveview.service.api;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.hiveview.entity.Branch;
import com.hiveview.service.activity.ActivityService;
import com.hiveview.service.sys.ZoneCityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hiveview.common.ApiConstants;
import com.hiveview.entity.activity.ActivityEx;
import com.hiveview.entity.sys.SysUser;
import com.hiveview.entity.vo.VipPackagePriceVo;
import com.hiveview.service.RedisService;

@Service
public class ChargePriceApi {

	@Autowired
	RedisService redisService;
	@Autowired
	private ZoneCityService zoneCityService;
	@Autowired
	private ActivityService activityService;

	private static final Logger DATA = LoggerFactory.getLogger("data");
	RestTemplate restTemplate = new RestTemplate();

	/**
	 * 播控 1--集团 2--分公司、营业厅 3--战区
	 *
	 * @param sysUser
	 * @return
	 */
	public List<VipPackagePriceVo> getLiveProductPacksList(SysUser sysUser) {
		List<VipPackagePriceVo> list = null;
		int roleId = sysUser.getRoleId();
		// 播控权限
		int liveRoleId;
		String cityCode = sysUser.getCityCode();
		if (1 == roleId) {
			liveRoleId = 1;
		} else if (2 == roleId || 3 == roleId) {
			liveRoleId = 2;
		} else if (4 == roleId) {
			liveRoleId = 3;
			List<Branch> braList = zoneCityService.getBranchByZone(sysUser.getZoneId());
			if (null != braList && braList.size() > 0) {
				cityCode = braList.get(0).getCityCode();
				for (int i = 1; i < braList.size(); i++) {
					cityCode = cityCode + "*" + braList.get(i).getCityCode();
				}
			}
		} else {
			return null;
		}
		String key = "LIVE_PRODUCT_PACKS_LIST_" + roleId + "_" + cityCode;
		if (redisService.exists(key)) {
			list = redisService.lRange(key, 0L, -1L, VipPackagePriceVo.class);
			if (list != null) {
				return list;
			}
		}
		String dataResult = restTemplate.getForObject(ApiConstants.LIVE_PRODUCT_PACKS_API, String.class, liveRoleId, cityCode);

		// HiveHttpResponse response =
		// HiveHttpGet.getEntity(ApiConstants.LIVE_PRODUCT_PACKS_API,
		// HiveHttpEntityType.STRING);
		if (dataResult == null) {
			DATA.info("获取直播产品包失败");
			return null;
		}
		try {
			String result = JSONObject.parseObject(dataResult).getString("result");
			if (null == result) {
				DATA.info("产品包为空 dataResult={}", dataResult);
				return null;
			}
			list = new ArrayList<VipPackagePriceVo>();
			JSONArray array = JSONObject.parseArray(result);
			Iterator<Object> iterator = array.iterator();
			while (iterator.hasNext()) {
				VipPackagePriceVo vipPackagePriceVo = new VipPackagePriceVo();
				JSONObject json = (JSONObject) iterator.next();
				vipPackagePriceVo.productId = json.getIntValue("productPackId");
				vipPackagePriceVo.productName = json.getString("productPackName");
				list.add(vipPackagePriceVo);
			}
			redisService.rPush(key, list, VipPackagePriceVo.class, 10 * 60L);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<VipPackagePriceVo> getVipChargPriceList() {
		List<VipPackagePriceVo> list = null;
		String key = "VIP_CHARGE_LIST";
		if (redisService.exists(key)) {
			list = redisService.lRange(key, 0L, -1L, VipPackagePriceVo.class);
			if (list != null) {
				return list;
			}
		}
		synchronized (key) {
			if (redisService.exists(key)) {
				list = redisService.lRange(key, 0L, -1L, VipPackagePriceVo.class);
				if (list != null) {
					return list;
				}
			}
//			HiveHttpResponse response = HiveHttpGet.getEntity(ApiConstants.CHARGE_LIST_API, HiveHttpEntityType.STRING);
//			if (response.statusCode != HttpStatus.SC_OK) {
//				DATA.info("获取vip计费信息失败");
//				return null;
//			}
			try {
				ActivityEx ex =new ActivityEx();
				list = new ArrayList<VipPackagePriceVo>();
				List<ActivityEx> l =activityService.getActivityExListWithOutPageInfo(ex);
				if(l!=null){
					for(ActivityEx e:l){
						if(e.getFlag()==0){
							continue;
						}
						VipPackagePriceVo vipPackagePriceVo = new VipPackagePriceVo();
						vipPackagePriceVo.productId = e.getProductId();
						vipPackagePriceVo.productName = e.getProductName();
						vipPackagePriceVo.chargingId = e.getChargingId();
						vipPackagePriceVo.chargingName = e.getChargingName();
						vipPackagePriceVo.chargingPrice = e.getChargingPrice();
						vipPackagePriceVo.chargingDuration = e.getChargingDuration() / 3600 / 24;
						vipPackagePriceVo.chargingPic = "";
						vipPackagePriceVo.chargingStr = vipPackagePriceVo.productId + "||" + vipPackagePriceVo.productName + "||" + vipPackagePriceVo.chargingId + "||" + vipPackagePriceVo.chargingName + "||" + vipPackagePriceVo.chargingPrice + "||" + vipPackagePriceVo.chargingDuration + "||" + vipPackagePriceVo.chargingPic;
						list.add(vipPackagePriceVo);
					}
				}
				redisService.rPush(key, list, VipPackagePriceVo.class, 3 * 60L);
				return list;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	/**
	 * 拉取直播计费的计费包
	 * 
	 * @return
	 */
	public List<VipPackagePriceVo> getLiveChargPriceList() {
		List<VipPackagePriceVo> list = new ArrayList<VipPackagePriceVo>();
		VipPackagePriceVo vipPackagePriceVo0 = new VipPackagePriceVo();
		vipPackagePriceVo0.productId = 50;
		vipPackagePriceVo0.productName = "直播包月";
		vipPackagePriceVo0.chargingId = 5050;
		vipPackagePriceVo0.chargingName = "直播包月";
		vipPackagePriceVo0.chargingPrice = 0;
		vipPackagePriceVo0.chargingDuration = 2592000;
		vipPackagePriceVo0.chargingPic = "www.baidu.com";
		vipPackagePriceVo0.chargingStr = "temp0";
		vipPackagePriceVo0.chargingDurationYear = 0;
		vipPackagePriceVo0.chargingDurationMonth = 1;
		vipPackagePriceVo0.chargingDurationDay = 0;
		list.add(vipPackagePriceVo0);
		VipPackagePriceVo vipPackagePriceVo1 = new VipPackagePriceVo();
		vipPackagePriceVo1.productId = 10;
		vipPackagePriceVo1.productName = "直播包半年";
		vipPackagePriceVo1.chargingId = 1010;
		vipPackagePriceVo1.chargingName = "直播包半年";
		vipPackagePriceVo1.chargingPrice = 0;
		vipPackagePriceVo1.chargingDuration = 15897600;
		vipPackagePriceVo1.chargingPic = "www.baidu.com";
		vipPackagePriceVo1.chargingStr = "temp1";
		vipPackagePriceVo1.chargingDurationYear = 0;
		vipPackagePriceVo1.chargingDurationMonth = 6;
		vipPackagePriceVo1.chargingDurationDay = 0;
		list.add(vipPackagePriceVo1);
		VipPackagePriceVo vipPackagePriceVo2 = new VipPackagePriceVo();
		vipPackagePriceVo2.productId = 20;
		vipPackagePriceVo2.productName = "直播包年";
		vipPackagePriceVo2.chargingId = 2020;
		vipPackagePriceVo2.chargingName = "直播包年";
		vipPackagePriceVo2.chargingPrice = 0;
		vipPackagePriceVo2.chargingDuration = 31622400;
		vipPackagePriceVo2.chargingPic = "www.baidu.com";
		vipPackagePriceVo2.chargingStr = "temp2";
		vipPackagePriceVo2.chargingDurationYear = 1;
		vipPackagePriceVo2.chargingDurationMonth = 0;
		vipPackagePriceVo2.chargingDurationDay = 0;
		list.add(vipPackagePriceVo2);
		VipPackagePriceVo vipPackagePriceVo3 = new VipPackagePriceVo();
		vipPackagePriceVo3.productId = 30;
		vipPackagePriceVo3.productName = "直播包两年";
		vipPackagePriceVo3.chargingId = 3030;
		vipPackagePriceVo3.chargingName = "直播包两年";
		vipPackagePriceVo3.chargingPrice = 0;
		vipPackagePriceVo3.chargingDuration = 63244800;
		vipPackagePriceVo3.chargingPic = "www.baidu.com";
		vipPackagePriceVo3.chargingStr = "temp3";
		vipPackagePriceVo3.chargingDurationYear = 2;
		vipPackagePriceVo3.chargingDurationMonth = 0;
		vipPackagePriceVo3.chargingDurationDay = 0;
		list.add(vipPackagePriceVo3);
		VipPackagePriceVo vipPackagePriceVo4 = new VipPackagePriceVo();
		vipPackagePriceVo4.productId = 40;
		vipPackagePriceVo4.productName = "直播包三年";
		vipPackagePriceVo4.chargingId = 4040;
		vipPackagePriceVo4.chargingName = "直播包三年";
		vipPackagePriceVo4.chargingPrice = 0;
		vipPackagePriceVo4.chargingDuration = 94867200;
		vipPackagePriceVo4.chargingPic = "www.baidu.com";
		vipPackagePriceVo4.chargingStr = "temp4";
		vipPackagePriceVo4.chargingDurationYear = 3;
		vipPackagePriceVo4.chargingDurationMonth = 0;
		vipPackagePriceVo4.chargingDurationDay = 0;
		list.add(vipPackagePriceVo4);
		return list;
	}

	/**
	 * 临时获取产品包，后期需确定直播计费包的配置再行设计
	 * 
	 * @param productId
	 * @return
	 */
	public VipPackagePriceVo getPackagePriceByProductId(int productId) {
		VipPackagePriceVo packagePrice = new VipPackagePriceVo();
		switch (productId) {
		case 50:
			packagePrice.productId = 50;
			packagePrice.productName = "直播包月";
			packagePrice.chargingId = 5050;
			packagePrice.chargingName = "直播包月";
			packagePrice.chargingPrice = 0;
			packagePrice.chargingDuration = 2592000;
			packagePrice.chargingPic = "www.baidu.com";
			packagePrice.chargingStr = "temp0";
			packagePrice.chargingDurationYear = 0;
			packagePrice.chargingDurationMonth = 1;
			packagePrice.chargingDurationDay = 0;
			break;
		case 10:
			packagePrice.productId = 10;
			packagePrice.productName = "直播包半年";
			packagePrice.chargingId = 1010;
			packagePrice.chargingName = "直播包半年";
			packagePrice.chargingPrice = 0;
			packagePrice.chargingDuration = 15897600;
			packagePrice.chargingPic = "www.baidu.com";
			packagePrice.chargingStr = "temp1";
			packagePrice.chargingDurationYear = 0;
			packagePrice.chargingDurationMonth = 6;
			packagePrice.chargingDurationDay = 0;
			break;
		case 20:
			packagePrice.productId = 20;
			packagePrice.productName = "直播包年";
			packagePrice.chargingId = 2020;
			packagePrice.chargingName = "直播包年";
			packagePrice.chargingPrice = 0;
			packagePrice.chargingDuration = 31622400;
			packagePrice.chargingPic = "www.baidu.com";
			packagePrice.chargingStr = "temp2";
			packagePrice.chargingDurationYear = 1;
			packagePrice.chargingDurationMonth = 0;
			packagePrice.chargingDurationDay = 0;
			break;
		case 30:
			packagePrice.productId = 30;
			packagePrice.productName = "直播包两年";
			packagePrice.chargingId = 3030;
			packagePrice.chargingName = "直播包两年";
			packagePrice.chargingPrice = 0;
			packagePrice.chargingDuration = 63244800;
			packagePrice.chargingPic = "www.baidu.com";
			packagePrice.chargingStr = "temp3";
			packagePrice.chargingDurationYear = 2;
			packagePrice.chargingDurationMonth = 0;
			packagePrice.chargingDurationDay = 0;
			break;
		case 40:
			packagePrice.productId = 40;
			packagePrice.productName = "直播包三年";
			packagePrice.chargingId = 4040;
			packagePrice.chargingName = "直播包三年";
			packagePrice.chargingPrice = 0;
			packagePrice.chargingDuration = 94867200;
			packagePrice.chargingPic = "www.baidu.com";
			packagePrice.chargingStr = "temp4";
			packagePrice.chargingDurationYear = 3;
			packagePrice.chargingDurationMonth = 0;
			packagePrice.chargingDurationDay = 0;
			break;
		}
		return packagePrice;
	}

}