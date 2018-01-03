package main.java.com.hiveview.action.api.live;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hiveview.action.base.BaseAction;
import com.hiveview.common.ParamConstants;
import com.hiveview.entity.bo.OpResult;
import com.hiveview.entity.bo.ScriptPage;
import com.hiveview.entity.live.LiveOrder;
import com.hiveview.entity.sys.SysUser;
import com.hiveview.entity.vo.ProductPackageVo;
import com.hiveview.entity.vo.VipPackagePriceVo;
import com.hiveview.pay.encryption.DES;
import com.hiveview.pay.entity.OpResultTypeEnum;
import com.hiveview.service.live.LiveService;
import com.hiveview.service.sys.SysUserService;
import com.hiveview.util.SHA1Utils;

/**
 * 直播开通接口
 * 
 * @author wengjingchang
 *
 */
@Controller
@RequestMapping("/api/live")
public class LiveApiAction extends BaseAction {
	private static final Logger DATA = LoggerFactory.getLogger("data");
	@Autowired
	LiveService liveService;
	@Autowired
	private SysUserService sysUserService;

	/**
	 * 开通接口 包含续费
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/open", method = RequestMethod.POST)
	@ResponseBody
	public OpResult openLive(HttpServletRequest request) {
		OpResult op = new OpResult();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = getParameterMap(request);
			String sign = map.get("sign");
			map.remove("sign");
			map.put("key", ParamConstants.LIVE_SHA1_KEY);
			if (!checkSign(map, sign)) {
				DATA.info("直播开通接口,通鉴权失败 openLive map={} ,sign={} ", new Object[] { map, sign });
				op.setCode("001");
				op.setDesc("鉴权失败");
				return op;
			}
			SysUser sysUser = new SysUser();
			sysUser.setUserMail(map.get("userMail"));
			String userPwd = DES.decrypt(map.get("userPwd"), ParamConstants.LIVE_DES_KEY);
			sysUser.setUserPwd(userPwd);
			sysUser = sysUserService.getLoginInfoByCache(sysUser);
			if (sysUser == null || sysUser.getUserId() == null || sysUser.getUserId() == 0) {
				DATA.info("直播开通接口,用户信息错误 openLive map={},mail={}", new Object[] { map, map.get("userMail") });
				op.setCode("002");
				op.setDesc("用户信息错误");
				return op;
			}
			//判断开通还是续费
			LiveOrder liveOrder = new LiveOrder();
			liveOrder.setMac(map.get("mac").replaceAll(":", "").toUpperCase());
			liveOrder.setSn(map.get("sn"));
			liveOrder.setProductId(Integer.valueOf(map.get("productId")));
			liveOrder.setProductName(map.get("productName"));

			String closeTime = map.get("closeTime");
			int ymd[] = com.hiveview.util.DateUtil.getYMDArray(new DateTime().toString("yyyy-MM-dd"), closeTime);
			
			ScriptPage scriptPage = liveService.getLiveList(liveOrder);
			if(scriptPage.getTotal() !=null && scriptPage.getTotal().compareTo(0)>0 ){
				// 续费
				LiveOrder tmp = (LiveOrder)scriptPage.getRows().get(0);
				liveOrder.setBranchId(tmp.getBranchId());
				
				liveOrder.setChargingDurationYear(ymd[0]);
				liveOrder.setChargingDurationMonth(ymd[1]);
				liveOrder.setChargingDurationDay(ymd[2]);
				liveOrder.setBranchId(sysUser.getBranchId());
				liveOrder.setBranchName(sysUser.getBranchName());
				liveOrder.setHallId(sysUser.getHallId());
				liveOrder.setHallName(sysUser.getHallName());
				op = liveService.renewLive(sysUser, liveOrder);
			} else {
				// 开通
				liveOrder.setCloseTime(closeTime);
				liveOrder.setChargingDurationYear(ymd[0]);
				liveOrder.setChargingDurationMonth(ymd[1]);
				liveOrder.setChargingDurationDay(ymd[2]);
				liveOrder.setBranchId(sysUser.getBranchId());
				liveOrder.setBranchName(sysUser.getBranchName());
				liveOrder.setHallId(sysUser.getHallId());
				liveOrder.setHallName(sysUser.getHallName());
				op = liveService.openLiveService(liveOrder, sysUser);
			}
		} catch (Exception e) {
			DATA.info("直播开通接口异常 map={},Exception={}", new Object[] { map, e });
			op = new OpResult(OpResultTypeEnum.SYSERR, "直播开通接口异常");
		}
		return op;
	}

	@RequestMapping(value = "/rapInfo", method = RequestMethod.GET)
	@ResponseBody
	public OpResult rapInfo() {
		int flag = liveService.rapInfo();
		return new OpResult(flag);
	}

	@RequestMapping(value = "/getProductList", method = RequestMethod.POST)
	@ResponseBody
	public OpResult getProductList(HttpServletRequest request) {
		OpResult op = new OpResult();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = getParameterMap(request);
			String sign = map.get("sign");
			map.remove("sign");
			map.put("key", ParamConstants.LIVE_SHA1_KEY);
			if (!checkSign(map, sign)) {
				DATA.info("获取产品包列表接口,鉴权失败 openLive map={} ,sign={} ", new Object[] { map, sign });
				op.setCode("001");
				op.setDesc("鉴权失败");
				return op;
			}
			SysUser sysUser = new SysUser();
			sysUser.setUserMail(map.get("userMail"));
			String userPwd = DES.decrypt(map.get("userPwd"), ParamConstants.LIVE_DES_KEY);
			sysUser.setUserPwd(userPwd);
			sysUser = sysUserService.getLoginInfoByCache(sysUser);
			if (sysUser == null || sysUser.getUserId() == null || sysUser.getUserId() == 0) {
				DATA.info("获取产品包列表接口,用户信息错误 getProductList map={},mail={}", new Object[] { map, map.get("userMail") });
				op.setCode("002");
				op.setDesc("用户信息错误");
				return op;
			}
			List<VipPackagePriceVo> list = liveService.getProductList(sysUser);
			if (list != null && list.size() > 0) {
				List<ProductPackageVo> productPackages = new ArrayList<ProductPackageVo>();
				for (VipPackagePriceVo vipPackagePriceVo : list) {
					ProductPackageVo v = new ProductPackageVo();
					v.setProductId(vipPackagePriceVo.getProductId());
					v.setProductName(vipPackagePriceVo.getProductName());
					productPackages.add(v);
				}
				op.setResult(productPackages);
			}
		} catch (Exception e) {
			DATA.info("直播开通接口异常 map={},Exception={}", new Object[] { map, e });
			op = new OpResult(OpResultTypeEnum.SYSERR, "产品包列表接口异常");
		}
		return op;
	}

	/**
	 * 相同返回true
	 * 
	 * @param map
	 * @param sign
	 * @return
	 */
	private boolean checkSign(Map<String, String> map, String sign) {
		List<String> list = new ArrayList<String>();
		for (Entry<String, String> entry : map.entrySet()) {
			list.add(entry.getKey());
		}
		StringBuffer buf = new StringBuffer();
		Collections.sort(list);
		for (int i = 0; i < list.size(); i++) {
			buf.append(map.get(list.get(i)));
		}
		String output = SHA1Utils.SHA1(buf.toString());
		if (output.equalsIgnoreCase(sign)) {
			return true;
		}
		return false;
	}
}
