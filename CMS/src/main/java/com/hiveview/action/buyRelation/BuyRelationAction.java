package main.java.com.hiveview.action.buyRelation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hiveview.action.base.BaseAction;
import com.hiveview.service.BuyService;

@Controller
@RequestMapping("/buyRelation")
public class BuyRelationAction extends BaseAction{

	@Autowired
	BuyService buyService;
	
//	private static final Logger DATA = LoggerFactory.getLogger("data");
	
//	@RequestMapping(value="/free",method = RequestMethod.POST)
//	@ResponseBody
//	public OpResult AddBuyRelation(HttpServletRequest request){
//		String parameters = super.getParameters(request);
//		try {
//			parameters = URLDecoder.decode(parameters, "UTF-8");
//			Map<String, String> parametersMap = StringUtils.linkToMap(parameters);
//			if(parametersMap.isEmpty()){
//				DATA.info("[buyRelation] parametersMap is null");
//				return new OpResult(OpResultTypeEnum.MSGERR);
//			}
//			String sign = this.getSigntoparameters(parametersMap);
//			if(!sign.equals(parametersMap.get("sign"))){
//				DATA.info("[buyRelation] sign is error:sign={},sign_={},parametersMap={}",new Object[]{sign,parametersMap.get("sign"),parametersMap});
//				return new OpResult(OpResultTypeEnum.UNSAFE,"签名错误");
//			}
//			return buyService.buyOrder(parametersMap);
//		} catch (Exception e) {
//			e.printStackTrace();
//			DATA.info("[buyRelation]system is error:parameters={}",new Object[]{super.getParameters(request)});
//			return new OpResult(OpResultTypeEnum.SYSERR,"系统处理异常");
//		}
//	}
//	
//	/**
//	* 生成sign
//	* @param parametersMap
//	* @return
//	*/ 
//	private String getSigntoparameters(Map<String, String> parametersMap){
//		if(parametersMap== null){
//			return "";
//		}
//		Map<String,String> map = new HashMap<String,String>();
//		map.put("uid", parametersMap.get("uid"));
//		map.put("mac", parametersMap.get("mac"));
//		map.put("sn", parametersMap.get("sn"));
//		map.put("chargingId", parametersMap.get("chargingId"));
//		map.put("chargingPrice", parametersMap.get("chargingPrice"));
//		map.put("chargingDuration", parametersMap.get("chargingDuration"));
//		map.put("chargingName", parametersMap.get("chargingName"));
//		map.put("devicecode", parametersMap.get("devicecode"));
//		map.put("uname", parametersMap.get("uname"));
//		return DMPayHelper.toSignForNotify(map, ParamConstants.VIP_PARTNER_KEY);
//	}
}
