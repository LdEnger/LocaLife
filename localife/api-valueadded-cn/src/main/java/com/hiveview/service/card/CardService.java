package com.hiveview.service.card;

import java.net.URLDecoder;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.hiveview.common.ApiConstants;
import com.hiveview.common.ParamConstants;
import com.hiveview.dao.activity.ActivityDao;
import com.hiveview.dao.activity.ActivityOrderDao;
import com.hiveview.dao.card.CardDao;
import com.hiveview.entity.activity.Activity;
import com.hiveview.entity.activity.ActivityOrder;
import com.hiveview.entity.bo.Data;
import com.hiveview.entity.bo.OpResult;
import com.hiveview.entity.bo.ScriptPage;
import com.hiveview.entity.card.Card;
import com.hiveview.entity.vo.UserVo;
import com.hiveview.pay.entity.OpResultTypeEnum;
import com.hiveview.pay.http.HiveHttpEntityType;
import com.hiveview.pay.http.HiveHttpPost;
import com.hiveview.pay.http.HiveHttpResponse;
import com.hiveview.service.api.PassportApi;
import com.hiveview.service.api.VipOrderApi;
import com.hiveview.util.DMPayHelper;
import com.hiveview.util.StringUtils;

/**
 * Title：CardService.java
 * Description：卡券服务类
 * Company：hiveview.com
 * Author：韩贺鹏
 * Email：hanhepeng@btte.net 
 * 2015年12月2日 下午3:50:20
 */
@Service
public class CardService {
	
	@Autowired
	CardDao cardDao;
	@Autowired
	ActivityDao activityDao;
	@Autowired
	ActivityOrderDao activityOrderDao;
	@Autowired
	PassportApi passportApi;
	@Autowired
	VipOrderApi vipOrderApi;
	@Autowired
	CardBuildService cardBuildService;
	
	private static final Logger DATA = LoggerFactory.getLogger("data");
	
	public static final int  ORDER_SUBMIT=1;//下单
	public static final String  ORDER_SUBMIT_STRING="已下单";
	public static final int  ORDER_ACTIVITY=2;//激活
	public static final String  ORDER_ACTIVITY_STRING="已激活";
	public static final int  ORDER_REFUND=4;//退订
	public static final String  ORDER_REFUND_STRING="已退订";
	public static final int  ORDER_CARD=2;//促销卡类型
	
	/**
	* 获取卡券列表
	* @param card
	* @return
	*/ 
	public ScriptPage getCardList(Card card) {
		List<Card> rows = cardDao.getList(card);
		//判定卡券是否过期，待办项
//		for (Card card2 : rows) {
//			
//		}
		
		int total = cardDao.count(card);
		ScriptPage scriptPage = new ScriptPage();
		scriptPage.setRows(rows);
		scriptPage.setTotal(total);
		return scriptPage;
	}
	
	/**
	* 添加卡券
	* @param card
	* @return
	*/ 
	public boolean add(Card card){
		String activationCode=this.getVerificationCode();
		card.setActivationCode(activationCode);
		Activity cond = this.activityDao.getActivityById(card.getActivityId());
		card.setEffectiveTimeLength(cond.getDuration());
		int suc = this.cardDao.save(card);
		return suc > 0 ? true:false;
	}
	
	
	/**
	* 编辑卡券
	* @param card
	* @return
	*/ 
	public boolean update(Card card){
		int suc = this.cardDao.update(card);
		return suc > 0 ? true:false;
	}
	
	/**
	* 使用卡券
	* @param cardPwd
	* @return
	*/ 
	public OpResult useCard(String mac,String sn,String cardPwd){
		//获取用户信息
		UserVo user = passportApi.getUserInfo(mac, sn);
		DATA.info("[submitVipOrder] user is null:mac={},sn={},user={}",new Object[]{mac,sn,JSONObject.toJSONString(user)});
		if(user == null){
			return new OpResult(OpResultTypeEnum.SYSERR,"无法获取用户信息");
		}
		//下单获取vip订单号
		String partnerOrderId = vipOrderApi.orderSubmit(mac, sn,user.getId(),user.getDevicecode(),ORDER_CARD,"",null);//后台可以不传duration，updateOrder时候传
		DATA.info("[submitVipOrder] submitResp:partnerOrderId={},user={}",new Object[]{partnerOrderId,JSONObject.toJSONString(user)});
		if(StringUtils.isEmpty(partnerOrderId)){
			return new OpResult(OpResultTypeEnum.SYSERR,"获取vip订单失败");
		}
		Card card = this.cardDao.getCardByActivityCode(cardPwd);
		if(card==null){
			return new OpResult(OpResultTypeEnum.SYSERR,"没有该活动卡"+cardPwd);
		}
		card = cardBuildService.buildSubmitCard(card, mac, sn, partnerOrderId,user.getId(),user.getUserName(),user.getDevicecode());
		boolean result = this.update(card);//更改为提交下单状态
		if(!result){
			DATA.info("[submitVipOrder] CardBindingError:cardPwd={},partnerOrderId={}",new Object[]{card.getActivationCode(),partnerOrderId});
			return new OpResult(OpResultTypeEnum.SYSERR,"活动卡绑定失败");
		}
		Activity activity = activityDao.getActivityById(card.getActivityId());
		if(activity==null){
			DATA.info("[submitVipOrder] :activityId={}",new Object[]{card.getActivityId()});
			return new OpResult(OpResultTypeEnum.SYSERR,"找不到该活动");
		}
		ActivityOrder activityOrder = new ActivityOrder(activity,card,ORDER_CARD,ORDER_SUBMIT,StringUtils.getRealTimeString());
		if(activityOrderDao.save(activityOrder)!=1){
			DATA.info("[submitVipOrder] InsertError:partnerOrderId={}",new Object[]{partnerOrderId});
			return new OpResult(OpResultTypeEnum.SYSERR,"下单失败");
		}
		DATA.info("[submitVipOrder] submit success :mac={},sn={},uid={},partnerOrderId={}",new Object[]{mac,sn,user.getId(),partnerOrderId});
		//激活订单
		return this.activationCard(mac,sn,activityOrder,cardPwd);
	}
	
	/**
	* 激活vip订单
	* @param cardPwd
	* @param partnerOrderId
	* @return
	*/ 
	public OpResult activationCard(String mac,String sn,ActivityOrder activityOrder,String cardPwd){
		OpResult op = new OpResult();
		String result = vipOrderApi.orderActivation(mac, sn, activityOrder);
		if(!"success".equals(result)){
			DATA.info("[NotifyResponse] PartnerNotifyResponse Error:activityOrderId={},result={}",new Object[]{activityOrder.activityOrderId,result});
			return new OpResult(OpResultTypeEnum.SYSERR,"通知激活失败");
		}
		//若是活动卡，将卡置为激活状态
		if(cardPwd!=null){
			Card cond = this.cardDao.getCardByActivityCode(cardPwd);
			cond.setStatus(2);
			cond.setCardOrderStatus(2);
			cond.setTerminalMac(mac);
			cond.setTerminalSn(sn);
			int suc=this.cardDao.update(cond);
			if(suc>0){
				
			}else{
				DATA.info("[NotifyResponse] the card is activited, but the the status failed to change :mac={},sn={},cardPwd={}",new Object[]{mac,sn,cardPwd});
			}
		}		
		DATA.info("[NotifyResponse] success:activityOrderId={},mac={},sn={}",new Object[]{activityOrder.activityOrderId,mac,sn});
		activityOrder.activityOrderStatus=ORDER_ACTIVITY;
		activityOrder.activityOrderStatusName=ORDER_ACTIVITY_STRING;
		activityOrder.activityTime = StringUtils.getRealTimeString();
		if(activityOrderDao.update(activityOrder)!=1){
			DATA.info("[NotifyResponse] OrderUpdateError:activityOrderId={}",new Object[]{activityOrder.activityOrderId});
		}
		op.setResult(activityOrder);
		return op;
	}
	
	/**
	 * 注销卡券
	 * @param orderId
	 * @param userId
	 * @return
	 */
	public OpResult cancelCard(String parameters){
		try {
			parameters = URLDecoder.decode(parameters, "UTF-8");
			Map<String, String> parametersMap = StringUtils.linkToMap(parameters);
			if(parametersMap.isEmpty()){
				return new OpResult(OpResultTypeEnum.MSGERR);
			}
			Map<String, String> map = new HashMap<String,String>();
			String orderId = parametersMap.get("orderId");
			String userId =parametersMap.get("userId");
			map.put("userId",userId);
			map.put("orderId", orderId);
			String link = DMPayHelper.toLinkForNotify(map, ParamConstants.VIP_PARTNER_KEY);
			if(link.equals(parameters)){
				HiveHttpResponse response = HiveHttpPost.postString(ApiConstants.VIP_CANCEL_API, link, HiveHttpEntityType.STRING);
				if(response.statusCode != HttpStatus.SC_OK||StringUtils.isEmpty(response.entityString)){
					DATA.info("[CancellationResponse] statusCodeError:orderId={},userId={},statusCode={}",new Object[]{orderId,userId,response.statusCode});
					return new OpResult(OpResultTypeEnum.SYSERR);
				}
				JSONObject  result = JSONObject.parseObject(JSONObject.parseObject(response.entityString).getString("data"));
				if(!"1".equals(result.getString("result"))){
					DATA.info("[CancellationResponse] notifyResultError:orderId={},userId={},result={}",new Object[]{orderId,userId,result});
					return new OpResult(OpResultTypeEnum.SYSERR);
				}
				//卡状态置为已注销
				Card cond = this.cardDao.getCardByOrderId(orderId);
				if(cond!=null){
					cond.setStatus(4);
					cond.setCardOrderStatus(4);
					int suc = this.cardDao.update(cond);
					if(suc>0){
						
					}else{
						DATA.info("[CancellationResponse] the card is cancel, but the status failed to change:orderId={},userId={}",new Object[]{orderId,userId});
					}
				}else{
						DATA.info("[CancellationResponse] the card is cancel, but the card was failed to query by orderId:orderId={},userId={}",new Object[]{orderId,userId});
				}
				ActivityOrder activityOrder = new ActivityOrder();
				activityOrder.activityOrderStatus = ORDER_REFUND;
				activityOrder.activityOrderStatusName = ORDER_REFUND_STRING;
				activityOrder.activityOrderId = orderId;
				activityOrderDao.update(activityOrder);
				return new OpResult(OpResultTypeEnum.SUCC);
			}
			DATA.info("[CancellationResponse]:link={},parameters={}",new Object[]{link,parameters});
			return new OpResult(OpResultTypeEnum.UNSAFE);
		} catch (Exception e) {
			e.printStackTrace();
			DATA.info("[CancellationResponse]systemError:parameters={}",new Object[]{parameters});
			return new OpResult(OpResultTypeEnum.SYSERR);
		}
	}
	
	/**
	* 生成卡密
	* @return 23位字符串
	*/ 
	public String getVerificationCode(){
		return 2016+" "+this.getRandom(4)+" "+this.getRandom(5)+" "+this.getRandom(5)+" "+this.getRandom(5);
	}
	
	/**
	* 生成相应位数的随机数
	* @param size 长度
	* @return
	*/ 
	private String getRandom(int size) {
		Random random = new Random();
		int seed = (int) (Math.pow(10, size - 1));
		String number = random.nextInt(seed) + "";
		while (number.length() < size - 1) {
			number = random.nextInt(10) + "" + number;
		}
		return number;
	}
	
	/**
	 * 获取所有卡订单状态为0，且存在卡订单号的卡信息
	 * @return
	 */
	public List<Card> getUnsucOrderList(){
		return this.cardDao.getUnsucOrderList();
	}
	
	/**
	 * 将存在卡订单号，卡订单状态为0的置为1
	 * @param cardOrderId
	 * @return
	 */
	public boolean changeOrderStatus(String cardOrderId){
		int suc = this.cardDao.changeOrderStatus(cardOrderId);
		return suc > 0 ? true:false;
	}
	
	/**
	 * 用于排除同一用户同一活动同时参与两次
	 * @param card
	 * @return
	 */
	public Card getRepeatCard(Card card){
		return this.cardDao.getRepeatCard(card);
	}
	
	public Card getCardByActivityCode(String activityCode){
		return this.cardDao.getCardByActivityCode(activityCode);
	}
	
	/**
	 * 根据卡Id获取卡券
	 * @param id
	 * @return
	 */
	public Card getCardById(Integer id){
		return this.cardDao.getCardById(id);
	}
	
	/**
	 * 将卡状态置为过期
	 * @param id
	 * @return
	 */
	public boolean cardExpired(Integer id){
		int suc = this.cardDao.cardExpired(id);
		return suc > 0 ? true:false;
	} 
	
	/**
	 * 获取未激活的卡
	 * @return
	 */
	public List<Card> getNonactivatedCard(Integer activityId){
		return this.cardDao.getNonactivatedCard(activityId);
	}
	
	
	/**
	 * 把过期活动置为已过期
	 * @return
	 */
	public Data expiredActivityRun(){
		Data newData = new Data();
		List<Activity>  activityList= this.activityDao.getEffectiveActivity();//获取所有未过期活动
		StringBuffer expiredLog = new StringBuffer("");//执行日志
		int total = 0;//过期的活动数
		int count = 0;//成功置为过期状态的活动数
		for (Activity activity : activityList) {
			Integer activityId=activity.getId();
			Timestamp beginTime  = activity.getInsertTime();//开始时间
			int duration = activity.getDuration();//时长，秒
			Timestamp endTime =this.timestampAdd(beginTime, duration);//结束时间
			Timestamp currentTime = new Timestamp(System.currentTimeMillis()); //系统当前时间
			  if (currentTime.after(endTime)){  //已过期
				  total+=1;
				  //将活动置为过期下线状态
				  activity.setStatus(0);//活动置为下线
				  activity.setIsEffective(0);//活动置为过期
				  int suc=this.activityDao.update(activity);
				  if(suc>0){
					  count+=1;
					  DATA.info("活动已置为过期下线：activityId={}",new Object[]{activityId});
				  }else{
					  DATA.info("活动置为过期下线状态失败：activityId={}",new Object[]{activityId});
				  }
				  //将关联的卡未激活的置为过期
				 Data cardData= this.expiredCardRun(activityId);
				 expiredLog.append(cardData.getMsg());
			  }
		  }
		newData.setMsg("定时任务：本次查询共有"+total+"个活动过期，其中"+count+"个成功置为已过期状态。与之关联的活动卡执行过期操作日志："+expiredLog);
		return newData;
	}
	
	/**
	 * 把已过期的活动关联的未激活的卡置为已过期状态
	 * @param activityId
	 * @return
	 */
	public Data expiredCardRun(Integer activityId) {
		Data cData = new Data();
		List<Card> cardList = this.getNonactivatedCard(activityId);
		int total = 0;
		int size =cardList.size(); 
		for (Card card : cardList) {
			boolean bol = this.cardExpired(card.getId());// 关联活动的未激活的卡置为已过期
			if (bol) {
				total += 1;
			} else {
				DATA.info("活动卡置为过期状态失败：cardId={}", new Object[] { card.getId() });
			}
		}
		cData.setMsg("过期活动：activityId=>"+activityId+"下未激活的卡共有"+size+"张，实际有"+total+"张成功置为已过期状态；");
		return cData;
	}
	
	
	/**
	 * 时间戳类型加秒数，返回计算结果
	 * @param timestamp 开始时间
	 * @param duration 时长，秒
	 * @return
	 */
	public Timestamp timestampAdd(Timestamp timestamp,int duration){
		Date beginDate  =timestamp;
		Calendar cal = Calendar.getInstance();
		cal.setTime(beginDate);
		cal.add(Calendar.DATE, duration/86400-1);
		Date endDate = cal.getTime();
		Timestamp endTime =new Timestamp(endDate.getTime());
		return endTime;
	}
	
}
