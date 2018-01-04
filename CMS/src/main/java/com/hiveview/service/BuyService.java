package main.java.com.hiveview.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hiveview.dao.activity.ActivityOrderDao;
import com.hiveview.service.api.VipOrderApi;
import com.hiveview.service.card.CardService;

@Service
public class BuyService {
	
	@Autowired
	ActivityOrderDao activityOrderDao;
	@Autowired
	VipOrderApi vipOrderApi;
	@Autowired
	CardService cardService;
	
	public static final int  ORDER_CARD=0;//免费VIP包类型
	
	public static final int  ORDER_SUBMIT=1;//下单
	public static final String  ORDER_SUBMIT_STRING="下单";
	public static final int  ORDER_ACTIVITY=2;//激活
	public static final String  ORDER_ACTIVITY_STRING="激活";
	
//	private static final Logger DATA = LoggerFactory.getLogger("data");
	
	/**
	* 创建领取免费vip订购关系
	* @param parametersMap
	* @return
	*/ 
//	public OpResult buyOrder(Map<String,String> parametersMap){
//		String chargingId = parametersMap.get("chargingId");
//		String chargingName = parametersMap.get("chargingName");
//		String chargingPrice = parametersMap.get("chargingPrice");
//		String chargingDuration = parametersMap.get("chargingDuration");
//		String chargingImg = parametersMap.get("chargingImg");
//		String uid = parametersMap.get("uid");
//		String uname = parametersMap.get("uname");
//		String mac = parametersMap.get("mac");
//		String sn = parametersMap.get("sn");
//		String devicecode = parametersMap.get("devicecode");
//		
//		if(StringUtils.isEmpty(chargingId)||StringUtils.isEmpty(chargingName)||StringUtils.isEmpty(chargingPrice)||StringUtils.isEmpty(chargingDuration)
//				||StringUtils.isEmpty(uid)||StringUtils.isEmpty(mac)||StringUtils.isEmpty(sn)||StringUtils.isEmpty(devicecode)){
//			DATA.info("[buyRelation]paramError:chargingId={},chargingName={},chargingPrice={},chargingDuration={},uid={},mac={},sn={}"
//					,new Object[]{chargingId,chargingName,chargingPrice,chargingDuration,uid,mac,sn});
//			return new OpResult(OpResultTypeEnum.MSGERR,"必要参数缺失");
//		}
//		
//		String activityOrderId = vipOrderApi.orderSubmit(mac, sn, Integer.parseInt(uid),devicecode,ORDER_CARD,chargingDuration,null);
//		if("exist".equals(activityOrderId)){
//			return new OpResult(OpResultTypeEnum.SUCC, "不能重复领取");
//		}
//		ActivityOrder activityOrder = new ActivityOrder(Integer.parseInt(chargingId),Integer.parseInt(chargingPrice),chargingName,
//				Integer.parseInt(chargingDuration),chargingImg,activityOrderId,ORDER_CARD,Integer.parseInt(uid),uname,mac,sn,devicecode,
//				ORDER_SUBMIT,StringUtils.getRealTimeString());
//		
//		if(activityOrderDao.save(activityOrder)!=1){
//			DATA.info("[buyRelation]submitError:activityOrder={}",new Object[]{activityOrder});
//			return new OpResult(OpResultTypeEnum.SYSERR,"下单失败");
//		}
//		String cardPwd=null;
//		return cardService.activationCard(mac,sn,activityOrder,cardPwd);
//	}
	
}
