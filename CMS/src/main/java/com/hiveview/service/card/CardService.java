package main.java.com.hiveview.service.card;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.http.HttpStatus;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.csvreader.CsvReader;
import com.hiveview.common.ApiConstants;
import com.hiveview.common.EnvConstants;
import com.hiveview.common.ParamConstants;
import com.hiveview.dao.BranchDao;
import com.hiveview.dao.activity.ActivityDao;
import com.hiveview.dao.activity.ActivityOrderDao;
import com.hiveview.dao.agio.AgioPackageConfDao;
import com.hiveview.dao.card.BossDao;
import com.hiveview.dao.card.CardDao;
import com.hiveview.entity.Branch;
import com.hiveview.entity.activity.Activity;
import com.hiveview.entity.activity.ActivityOrder;
import com.hiveview.entity.agio.AgioPackageBatch;
import com.hiveview.entity.agio.AgioPackageBatchTask;
import com.hiveview.entity.bo.Data;
import com.hiveview.entity.bo.LoginAttribute;
import com.hiveview.entity.bo.OpResult;
import com.hiveview.entity.bo.ScriptPage;
import com.hiveview.entity.card.BossOrderNew;
import com.hiveview.entity.card.BossReport;
import com.hiveview.entity.card.BranchBossCity;
import com.hiveview.entity.card.Card;
import com.hiveview.entity.card.ExcelFile;
import com.hiveview.entity.card.Product;
import com.hiveview.entity.sys.SysUser;
import com.hiveview.entity.sys.ZoneCity;
import com.hiveview.entity.vo.UserVo;
import com.hiveview.entity.vo.VipPackagePriceVo;
import com.hiveview.job.AgioJobMeta;
import com.hiveview.pay.entity.OpResultTypeEnum;
import com.hiveview.pay.http.HiveHttpEntityType;
import com.hiveview.pay.http.HiveHttpGet;
import com.hiveview.pay.http.HiveHttpPost;
import com.hiveview.pay.http.HiveHttpResponse;
import com.hiveview.service.RedisService;
import com.hiveview.service.activity.ActivityService;
import com.hiveview.service.api.ChargePriceApi;
import com.hiveview.service.api.DeviceApi;
import com.hiveview.service.api.PassportApi;
import com.hiveview.service.api.VipOrderApi;
import com.hiveview.service.sms.SmsService;
import com.hiveview.service.sys.ZoneCityService;
import com.hiveview.util.DMPayHelper;
import com.hiveview.util.DateUtil;
import com.hiveview.util.FileUtil;
import com.hiveview.util.HttpUtils;
import com.hiveview.util.StringUtils;

/**
 * Title：CardService.java Description：卡券服务类 Company：hiveview.com Author：韩贺鹏
 * Email：hanhepeng@btte.net 2015年12月2日 下午3:50:20
 */
@Service
public class CardService {

	@Autowired
	private CardDao cardDao;
	@Autowired
	private ActivityDao activityDao;
	@Autowired
	private ActivityOrderDao activityOrderDao;
	@Autowired
	private PassportApi passportApi;
	@Autowired
	private VipOrderApi vipOrderApi;
	@Autowired
	private CardBuildService cardBuildService;
	@Autowired
	private ActivityService activityService;
	@Autowired
	private ZoneCityService zoneCityService;
	@Autowired
	BranchDao branchDao;

	@Autowired
	private SmsService smsService;
	@Autowired
	private BossDao bossDao;
	
	@Autowired
	private DeviceApi deviceApi;
	@Autowired
	private ChargePriceApi chargePriceApi;
	
	@Autowired
	RedisService redisService;

	AtomicInteger cardNumberAi = new AtomicInteger(0);

	private static final Logger DATA = LoggerFactory.getLogger("data");
	private static final String rootPath = "/data/ops/app/ftp/zz/";

	public static final int ORDER_SUBMIT = 1;// 下单
	public static final String ORDER_SUBMIT_STRING = "已下单";
	public static final int ORDER_ACTIVITY = 2;// 激活
	public static final String ORDER_ACTIVITY_STRING = "已激活";
	public static final int ORDER_REFUND = 4;// 退订
	public static final String ORDER_REFUND_STRING = "已退订";
	public static final int ORDER_CARD = 2;// 促销卡类型

	/**
	 * 获取卡券列表
	 * 
	 * @param card
	 * @return
	 */
	public ScriptPage getCardList(Card card) {
		List<Card> rows = new ArrayList<Card>();
		int total = 0;
		if (card != null && card.getZoneId() != null && card.getZoneId() != 0 && card.getCardFromBranch() == null
				&& card.getCardFromHall() == null) { // 查询整个战区的卡券
			Integer zoneId = card.getZoneId();
			List<ZoneCity> cityList = this.zoneCityService.getCityByZone(zoneId); // 获取整个战区下辖所有城市
			if(cityList!=null){
				String citys ="";
				for(ZoneCity city:cityList){
					citys+=city.getCityId()+",";
				}
				if(citys.endsWith(",")){
					citys =citys.substring(0, citys.length()-1);
					card.setCitys(citys);
				}
			}
			rows = cardDao.getCardByList(cityList, card.getSkipNo(), card.getPageSize(), card.getCardType());
			total = cardDao.getCountByList(cityList);
		} 
		rows = cardDao.getList(card);
		total = cardDao.count(card);
		ScriptPage scriptPage = new ScriptPage();
		scriptPage.setRows(rows);
		scriptPage.setTotal(total);
		return scriptPage;
	}

	/**
	 * 批量生成卡
	 * 
	 * @param card
	 * @param batchSize
	 * @return
	 */
	public Data addBatchForHoriz(Card card, int batchSize) {
		Activity cond = this.activityDao.getActivityById(card.getActivityId());
		int suc = 0;
		for (int i = 0; i < batchSize; i++) {
			try {
				Card cad = new Card();
				cad.setActivityId(card.getActivityId());
				cad.setActivityName(card.getActivityName());
				cad.setCreatorName(card.getCreatorName());
				cad.setCardFromHall(card.getCardFromHall());
				cad.setHallName(card.getHallName());
				cad.setCardFromBranch(card.getCardFromBranch());
				cad.setBranchName(card.getBranchName());
				cad.setAutoActiveTimeLength(card.getAutoActiveTimeLength());
				cad.setCityId(card.getCityId());
				cad.setTerminalMac("");
				cad.setTerminalSn("");
				String activationCode = this.getVerificationCode();
				cad.setActivationCode(activationCode);
				cad.setCardNumber(this.getCardNum());

				cad.setEffectiveTimeLength(cond.getDuration());
				cad.setCardType(card.getCardType());
				int rst = this.cardDao.save(cad);
				suc = suc + rst;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Data data = new Data();
		data.setCode(0);
		data.setMsg("成功生成" + suc + "张卡");
		return data;
	}

	/**
	 * 添加卡券
	 * 
	 * @param card
	 * @return Data code：0:添加失败, 1:添加成功, 2:北京分公司没有传业务单号或用户号, 3:北京分公司业务单号重复,
	 *         4:北京分公司用户号重复
	 */
	public Data add(Card card) {
		boolean flg = false;
		boolean sendSmsFlag = false;
		// 北京分公司
		if (ParamConstants.BJ_BRANCH.equals(card.getCardFromBranch())) {
			// 用户号或订单号为空直接返回
			if (StringUtils.isEmpty(card.getUserNum())) {
				return retRes(2, "北京分公司必须传入用户号！");
			}
			Card tmp = new Card();
			tmp.setOrderNum(card.getOrderNum());
			// 业务单号重复，直接返回
			if (!StringUtils.isEmpty(card.getOrderNum()) && cardDao.getList(tmp).size() > 0) {
				return retRes(3, "添加失败，业务单号重复！");
			}
			tmp.setOrderNum(null);
			tmp.setUserNum(card.getUserNum());
			// 用户号重复，标记为续费用户
			if (cardDao.getList(tmp).size() > 0) {
				flg = true;
			}
		}
		if (StringUtils.isEmpty(card.getTerminalMac()) && StringUtils.isEmpty(card.getTerminalSn())
				&& StringUtils.isNotEmpty(card.getPhone()) && card.getCardType().equals("vip")) {
			sendSmsFlag = true;
		}
		if (!StringUtils.isEmpty(card.getTerminalMac())) {
			card.setTerminalMac(card.getTerminalMac().replaceAll(":", "").toUpperCase());
		}
		if (!StringUtils.isEmpty(card.getTerminalSn())) {
			card.setTerminalSn(card.getTerminalSn().toUpperCase());
		}
		String activationCode = this.getVerificationCode();
		card.setActivationCode(activationCode);
		card.setCardNumber(this.getCardNum());
		if(card.getCardType().equals("vip")){
			Activity cond = this.activityDao.getActivityById(card.getActivityId());
			card.setEffectiveTimeLength(cond.getDuration());
		}else if(card.getCardType().equals("agio")){
			card.setEffectiveTimeLength(10240);
			String url =ApiConstants.AGIO_URL_DELAGIO;
			Map<String, String> map =new HashMap<String, String>();
			map.put("pkgId", "agio-"+card.getActivityId());//活动ID
			map.put("branchId", card.getCardFromBranch()+"");
			map.put("hallId", card.getCardFromHall()+"");
			map.put("opUserInfo", card.getCreatorName());
			map.put("userNum", card.getUserNum());
			map.put("orderNum", card.getOrderNum()+"");
			map.put("mac", card.getTerminalMac());
			map.put("sn", card.getTerminalSn());
			map.put("remark", System.currentTimeMillis()+card.getCreatorName());
			String json =HttpUtils.httpPostString(url, map);
			DATA.info(""+url+"?"+map+":"+json);
			OpResult op =new OpResult(OpResultTypeEnum.BUNSHORT,"调用接口失败");
			Data data =new Data();
			data.setCode(0);
			data.setMsg("调用接口失败");
			if(!"".equals(json)){
				op =JSONObject.parseObject(json,OpResult.class);
				if(op.getCode().equals(OpResultTypeEnum.SUCC.getCode())){
					data.setCode(1);
					data.setMsg("生成成功");
				}else{
					data.setMsg(op.getDesc());
				}
			}
			return data;
		}
		
		int suc = this.cardDao.save(card);
		if (sendSmsFlag) {
			smsService.sendSms(card);
		}
		if (suc > 0) {
			return flg ? retRes(4, card.getActivationCode()) : retRes(1, card.getActivationCode());
		} else {
			return retRes(0, "添加失败！");
		}
	}

	public Data retRes(Integer code, String msg) {
		Data data = new Data();
		data.setCode(code);
		data.setMsg(msg);
		return data;
	}

	/**
	 * 编辑卡券
	 * 
	 * @param card
	 * @return
	 */
	public boolean update(Card card) {
		int suc = this.cardDao.update(card);
		return suc > 0 ? true : false;
	}

	/**
	 * 查看mac,sn的合法性
	 * 
	 * @param mac
	 * @param sn
	 * @return
	 */
	public boolean isRealUser(String mac, String sn) {
		boolean flg = false;
		UserVo user = passportApi.getUserInfo(mac, sn);
		if (user != null) {
			flg = true;
		}
		return flg;
	}

	/**
	 * 注销卡券
	 * 
	 * @param orderId
	 * @param userId
	 * @return
	 */
	@Deprecated
	public OpResult cancelCard(String parameters) {
		try {
			parameters = URLDecoder.decode(parameters, "UTF-8");
			Map<String, String> parametersMap = StringUtils.linkToMap(parameters);
			DATA.info("[cancelCard]parametersMap={}", new Object[] { parametersMap.toString() });
			if (parametersMap.isEmpty() || StringUtils.isEmpty(parametersMap.get("userId"))
					|| StringUtils.isEmpty(parametersMap.get("orderId"))) {
				return new OpResult(OpResultTypeEnum.MSGERR);
			}
			Map<String, String> map = new HashMap<String, String>();
			map.put("userId", parametersMap.get("userId"));
			map.put("orderId", parametersMap.get("orderId"));
			String link = DMPayHelper.toLinkForNotify(map, ParamConstants.VIP_PARTNER_KEY);
			if (link.equals(parameters)) {
				HiveHttpResponse response = HiveHttpPost.postString(ApiConstants.VIP_CANCEL_API, link,
						HiveHttpEntityType.STRING);
				if (response.statusCode != HttpStatus.SC_OK || StringUtils.isEmpty(response.entityString)) {
					DATA.info("[CancellationResponse] StatusCodeError:statusCode={},parametersMap={}",
							new Object[] { response.statusCode, parametersMap.toString() });
					return new OpResult(OpResultTypeEnum.SYSERR);
				}
				JSONObject result = JSONObject
						.parseObject(JSONObject.parseObject(response.entityString).getString("data"));
				if (!"1".equals(result.getString("result"))) {
					DATA.info("[CancellationResponse] NotifyResultError:result={},parametersMap={}",
							new Object[] { result, parametersMap.toString() });
					return new OpResult(OpResultTypeEnum.SYSERR);
				}
				// 卡状态置为已注销
				Card cond = this.cardDao.getCardByOrderId(parametersMap.get("orderId"));
				if (cond != null) {
					cond.setStatus(4);
					cond.setCardOrderStatus(4);
					try {
						this.cardDao.update(cond);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				ActivityOrder activityOrder = new ActivityOrder();
				activityOrder.activityOrderStatus = ORDER_REFUND;
				activityOrder.activityOrderStatusName = ORDER_REFUND_STRING;
				activityOrder.activityOrderId = parametersMap.get("orderId");
				activityOrderDao.update(activityOrder);
				return new OpResult(OpResultTypeEnum.SUCC);
			}
			DATA.info("[CancelResponse]Unsafe:link={},parameters={}", new Object[] { link, parameters });
			return new OpResult(OpResultTypeEnum.UNSAFE);
		} catch (Exception e) {
			e.printStackTrace();
			DATA.info("[CancelResponse]systemError:parameters={}", new Object[] { parameters });
			return new OpResult(OpResultTypeEnum.SYSERR);
		}
	}
	/**
	 * 注销卡券
	 * 
	 * @param orderId
	 * @param userId
	 * @return
	 */
	public OpResult cancelCardinUse(String parameters) {
		try {
			parameters = URLDecoder.decode(parameters, "UTF-8");
			Map<String, String> parametersMap = StringUtils.linkToMap(parameters);
			DATA.info("[cancelCard]parametersMap={}", new Object[] { parametersMap.toString() });
			if (parametersMap.isEmpty() || StringUtils.isEmpty(parametersMap.get("userId"))
					|| StringUtils.isEmpty(parametersMap.get("orderId"))) {
				return new OpResult(OpResultTypeEnum.MSGERR);
			}
			Map<String, String> map = new HashMap<String, String>();
			map.put("userId", parametersMap.get("userId"));
			map.put("orderId", parametersMap.get("orderId"));
			String link = DMPayHelper.toLinkForNotify(map, ParamConstants.VIP_PARTNER_KEY);
			if (link.equals(parameters)) {
				//本地验证通过，开始正式退单
				Card cond = this.cardDao.getCardByOrderId(parametersMap.get("orderId"));
				if(cond.getCardOrderStatus()!=2){
					//未激活的卡,点注销，直接消掉，
					if (cond != null) {
						cond.setStatus(4);
						cond.setCardOrderStatus(4);
						try {
							this.cardDao.update(cond);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					ActivityOrder activityOrder = new ActivityOrder();
					activityOrder.activityOrderStatus = ORDER_REFUND;
					activityOrder.activityOrderStatusName = ORDER_REFUND_STRING;
					activityOrder.activityOrderId = parametersMap.get("orderId");
					activityOrderDao.update(activityOrder);
					return new OpResult(OpResultTypeEnum.SUCC);
				}else{
					String mac =cond.getTerminalMac();
					String sn =cond.getTerminalSn();
					String model =deviceApi.getDeviceModel(mac, sn);
					String vip32Url =ApiConstants.VIP32_URL;
					String tempurl =vip32Url+"api/open/special/templet/getTemplet/"+model+"-"+mac+"-"+sn+"-1.0.json";
					String exiturl =vip32Url+"api/open/special/vipOrder/refundFreeOrder.json";
					HiveHttpResponse res = HiveHttpGet.getEntity(tempurl, HiveHttpEntityType.STRING);
					String templetId = "1";
					if (res != null) {
						String entityString = res.entityString;
						System.out.println(entityString);
						DATA.info(sn+"--->"+entityString);
						if (entityString != null) {
							JSONObject jo = JSONObject.parseObject(entityString);
							templetId = jo.getJSONObject("data").getJSONObject("result").getString("templetId");
						}
					}
					Map<String,String> cmap = new HashMap<String,String>();
					cmap.put("templateId", templetId);
					cmap.put("orderId", cond.getCardOrderId());
					cmap.put("userId", cond.getUid()+"");
					cmap.put("versionNum", cond.getVersions());
					String clink =  DMPayHelper.toLinkForNotifyWithKey(cmap, "863b4ec37d93eb96276ca74d04edf66f");
					System.out.println(clink);
					DATA.info(sn+"--->"+clink);
					HiveHttpResponse response = HiveHttpPost.postString(exiturl, clink, HiveHttpEntityType.STRING);
					DATA.info(sn+"--->"+response.statusCode+"||"+response.entityString);
					
					if (response.statusCode != HttpStatus.SC_OK || StringUtils.isEmpty(response.entityString)) {
						DATA.info("[CancellationResponse] StatusCodeError:statusCode={},parametersMap={}",
								new Object[] { response.statusCode, parametersMap.toString() });
						return new OpResult(OpResultTypeEnum.SYSERR);
					}
					JSONObject result = JSONObject
							.parseObject(JSONObject.parseObject(response.entityString).getString("data"));
					if (!"N000000".equals(result.getString("code"))) {
						DATA.info("[CancellationResponse] NotifyResultError:result={},parametersMap={}",
								new Object[] { result, parametersMap.toString() });
						return new OpResult(OpResultTypeEnum.SYSERR);
					}
					// 卡状态置为已注销
					
					if (cond != null) {
						cond.setStatus(4);
						cond.setCardOrderStatus(4);
						try {
							this.cardDao.update(cond);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					ActivityOrder activityOrder = new ActivityOrder();
					activityOrder.activityOrderStatus = ORDER_REFUND;
					activityOrder.activityOrderStatusName = ORDER_REFUND_STRING;
					activityOrder.activityOrderId = parametersMap.get("orderId");
					activityOrderDao.update(activityOrder);
					return new OpResult(OpResultTypeEnum.SUCC);
				}
				
			}
			DATA.info("[CancelResponse]Unsafe:link={},parameters={}", new Object[] { link, parameters });
			return new OpResult(OpResultTypeEnum.UNSAFE);
		} catch (Exception e) {
			e.printStackTrace();
			DATA.info("[CancelResponse]systemError:parameters={}", new Object[] { parameters });
			return new OpResult(OpResultTypeEnum.SYSERR);
		}
	}

	/**
	 * 生成卡密
	 * 
	 * @return 23位字符串
	 */
	public String getVerificationCode() {
		String year = "2016";// new DateTime().toString("yyyy");
		return year + " " + this.getRandom(4) + " " + this.getRandom(5) + " " + this.getRandom(5) + " "
				+ this.getRandom(5);
	}

	public String getCardNum() {
		String year = new DateTime().toString("HHmmddMMyy");

		int serial = cardNumberAi.incrementAndGet();

		String number = serial + "";

		if (number.length() > 4) {

			cardNumberAi = new AtomicInteger(0);

		}
		while (number.length() < 4) {
			number = "0" + number;
		}
		return "SP" + year + number;
	}

	/**
	 * 生成相应位数的随机数
	 * 
	 * @param size
	 *            长度
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
	 * 
	 * @return
	 */
	public List<Card> getUnsucOrderList() {
		return this.cardDao.getUnsucOrderList();
	}

	/**
	 * 将存在卡订单号，卡订单状态为0的置为1
	 * 
	 * @param cardOrderId
	 * @return
	 */
	public boolean changeOrderStatus(String cardOrderId) {
		int suc = this.cardDao.changeOrderStatus(cardOrderId);
		return suc > 0 ? true : false;
	}

	public Card getCardByActivityCode(String activityCode) {
		return this.cardDao.getCardByActivityCode(activityCode);
	}

	/**
	 * 根据卡Id获取卡券
	 * 
	 * @param id
	 * @return
	 */
	public Card getCardById(Integer id) {
		return this.cardDao.getCardById(id);
	}

	/**
	 * 删除卡券
	 * 
	 * @param id
	 * @return
	 */
	public boolean delCard(Integer id) {
		int suc = this.cardDao.delete(id);
		return suc > 0 ? true : false;
	}

	public List<Activity> getBackActivityList(Integer roleId, Integer cityId, Integer branchId, Integer hallId) {
		List<Activity> actList = this.getActivityListBySection(1, null, null, null, 1); // 集团管理员创建的活动
		// 分配各角色返回的活动
		try {
			//
			if (ParamConstants.GROUP_ROLE == roleId) {
				actList = activityService.getAllList();// 当前用户是集团管理员，获取所有的活动
				return actList;
			}
			ZoneCity zoneCity = zoneCityService.getInfoByCity(cityId); // 获取该城市对应的战区信息
			List<ZoneCity> zoneCityList = zoneCityService.getCityByZone(zoneCity.getZoneId()); // 获取该战区下所有城市
			//
			if (ParamConstants.ZONE_ROLE == roleId) {
				for (ZoneCity zoneCity2 : zoneCityList) {
					List<Activity> zList = this.getActivityListBySection(null, zoneCity2.getCityId(), null, null, 1); // 该战区下所有城市生成的活动,
					for (Activity zActivity : zList) {
						actList.add(zActivity);
					}
				}
			}
			//
			if (ParamConstants.BRANCH_ROLE == roleId) {
				List<Activity> bList = this.getActivityListBySection(null, null, branchId, null, 1); // 该分公司下所有活动
				for (Activity bActivity : bList) {
					actList.add(bActivity);
				}
				for (ZoneCity zoneCity2 : zoneCityList) {
					List<Activity> zList = this.getActivityListBySection(null, zoneCity2.getCityId(), null, null, 1); // 该战区下所有城市生成的活动,
					for (Activity activity : zList) {
						if (ParamConstants.ZONE_ROLE == activity.getOperatorRoleId()) {// 该战区管理员创建的活动
							actList.add(activity);
						}
					}
				}
			}
			//
			if (ParamConstants.HALL_ROLE == roleId) {
				List<Activity> hList = this.getActivityListBySection(roleId, cityId, branchId, hallId, 1);// 该营业厅创建的活动
				for (Activity hActivity : hList) {
					actList.add(hActivity);
				}
				List<Activity> bList = this.getActivityListBySection(null, cityId, branchId, -1, 1); // 该分公司人员创建的活动
				for (Activity bActivity : bList) {
					actList.add(bActivity);
				}
				for (ZoneCity zoneCity2 : zoneCityList) {
					List<Activity> zList = this.getActivityListBySection(null, zoneCity2.getCityId(), null, null, 1); // 该战区下所有城市生成的活动,
					for (Activity activity : zList) {
						if (ParamConstants.ZONE_ROLE == activity.getOperatorRoleId()) {// 该战区管理员创建的活动
							actList.add(activity);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return actList;
	}

	/**
	 * 根据部门信息获取创建的活动
	 * 
	 * @param zoneId
	 * @param branchId
	 * @param hallId
	 * @return
	 */
	List<Activity> getActivityListBySection(Integer operatorRoleId, Integer cityId, Integer branchId, Integer hallId,
			Integer status) {
		Activity activity = new Activity();
		activity.setOperatorRoleId(operatorRoleId);
		activity.setCityId(cityId);
		activity.setBranchId(branchId);
		activity.setHallId(hallId);
		activity.setStatus(status);
		return activityService.getActivity(activity);
	}

	/**
	 * 定时任务，每天自动激活符合条件的卡券
	 * 
	 * @return
	 */
	public Data autoInvalidCardService() {
		int total = 0; // 应该自动失效的数量
		int count = 0;// 成功失效的数量
		Data data = new Data();
		Card tmp = new Card();
		tmp.setStatus(1); // 未激活
		tmp.setDelStatus(0); // 没有被删除
		tmp.setAutoActiveTimeLength(1);
		tmp.setCardType("vip");
		List<Card> cardList = this.cardDao.getList(tmp);
		for (Card card : cardList) {
			// 应该自动失效的日期
			Timestamp autoActiveTime = this.timestampAdd(Timestamp.valueOf(card.getCreateTime()),
					card.getAutoActiveTimeLength());//
			// 系统当前时间
			Timestamp currentTime = new Timestamp(System.currentTimeMillis());
			// 符合自动激活条件
			if (currentTime.after(autoActiveTime)) {
				total += 1;
				card.setStatus(5);// 已失效
				int suc = this.cardDao.update(card);
				count += suc > 0 ? 1 : 0;
			}
		}
//		System.out.println("本次任务执行结果：共" + total + "张卡券符合自动失效条件，实际" + count + "张置为失效。");
		data.setMsg("本次任务执行结果：共" + total + "张卡券符合自动失效条件，实际" + count + "张置为失效。");
		return data;
	}

	/**
	 * 时间戳类型加天数，返回计算结果
	 * 
	 * @param timestamp
	 *            开始时间
	 * @param duration
	 *            时长，天
	 * @return
	 */
	public Timestamp timestampAdd(Timestamp timestamp, int duration) {
		Date beginDate = timestamp;
		Calendar cal = Calendar.getInstance();
		cal.setTime(beginDate);
		cal.add(Calendar.DATE, duration);
		Date endDate = cal.getTime();
		Timestamp endTime = new Timestamp(endDate.getTime());
		return endTime;
	}

	/**
	 * 激活VIP促销卡新方法
	 * 
	 * @param mac
	 * @param sn
	 * @param card
	 * @return
	 */
	@Deprecated
	public OpResult useCard(String mac, String sn, Card card) {
		UserVo user = this.passportApi.getUserInfo(mac, sn);
		DATA.info("[userInfo]mac={},sn={},user={}", new Object[] { mac, sn, JSONObject.toJSONString(user) });
		if (user == null) {
			return new OpResult(OpResultTypeEnum.SYSERR, "无法获取用户信息");
		}
		Activity activity = this.activityDao.getActivityById(card.getActivityId());
		if (activity == null) {
			return new OpResult(OpResultTypeEnum.SYSERR, "未找到相应活动");
		}
		String result = this.vipOrderApi
				.orderActivation(this.createOrder(user, activity, card.getActivationCode(), mac, sn));
		DATA.info("[activeVipCardResult]mac={},sn={},result={}", new Object[] { mac, sn, result });
		if ("error".equals(result)) {
			return new OpResult(OpResultTypeEnum.SYSERR, "系统处理异常");
		}
		JSONObject jsonData = JSONObject.parseObject(result);
		if (jsonData == null
				|| (!"000".equals(jsonData.getString("code")) && !"E000008".equals(jsonData.getString("code")))) {
			return new OpResult(OpResultTypeEnum.SYSERR, "系统处理异常");
		}
		if ("E000008".equals(jsonData.getString("code"))) {
			return new OpResult(OpResultTypeEnum.SYSERR, "该卡券已被使用");
		}
		String orderId = "";
		if (jsonData.getString("result") != null
				&& JSONObject.parseObject(jsonData.getString("result")).getString("orderId") != null) {
			orderId = JSONObject.parseObject(jsonData.getString("result")).getString("orderId");
		}
		Timestamp effectTime = null;
		Timestamp invalidTime = null;
		if (StringUtils.isNotEmpty(jsonData.getString("startDate"))
				&& StringUtils.isNotEmpty(jsonData.getString("endDate"))) {
			try {
				effectTime = Timestamp.valueOf(jsonData.getString("startDate"));
				invalidTime = Timestamp.valueOf(jsonData.getString("endDate"));
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
		}
		card.setStatus(2);
		card.setUid(user.getId());
		card.setCardOrderId(orderId);
		card.setCardOrderStatus(2);
		card.setTerminalMac(mac);
		card.setTerminalSn(sn);
		card.setEffectTime(effectTime);
		card.setInvalidTime(invalidTime);
		try {
			this.cardDao.update(card);
		} catch (Exception uE) {
			uE.printStackTrace();
		}
		ActivityOrder activityOrder = new ActivityOrder(activity, card, ORDER_CARD, ORDER_ACTIVITY,
				StringUtils.getRealTimeString());
		try {
			this.activityOrderDao.save(activityOrder);
		} catch (Exception sE) {
			sE.printStackTrace();
		}
		return new OpResult(OpResultTypeEnum.SUCC, "激活成功");
	}

	private Map<String, String> createOrder(UserVo user, Activity activity, String cardPwd, String mac, String sn) {
		Map<String, String> parametersMap = new HashMap<String, String>();
		parametersMap.put("orderType", "2");
		parametersMap.put("userId", user.getId() + "");
		parametersMap.put("mac", mac);
		parametersMap.put("sn", sn);
		parametersMap.put("userName", user.getUserName());
		parametersMap.put("devicecode", user.getDevicecode());
		parametersMap.put("chargingId", cardPwd);// chargingId字段只是为了判断重复参与，故此处传cardPwd判重
		parametersMap.put("chargingName", activity.getChargingName());
		parametersMap.put("chargingPrice", activity.getPrice() + "");
		parametersMap.put("chargingDuration", activity.getDuration() + "");
		return parametersMap;
	}

	// ##########################################以下为老激活接口###############################
	// ##########################################以下为老激活接口###############################
	// ##########################################以下为老激活接口###############################

	/**
	 * 备注： 由于播控的saveOrderV3接口线上一直没有更新， 获取不到订单号，影响财务相关数据 为满足财务相关需求，暂时恢复使用老接口
	 * 待播控上线新版本后，废弃该接口
	 * 
	 * @param mac
	 * @param sn
	 * @param cardPwd
	 * @return
	 */
	@Deprecated
	public OpResult useCard(String mac, String sn, String cardPwd) {
		UserVo user = passportApi.getUserInfo(mac, sn);
		DATA.info("[userInfo]mac={},sn={},user={}", new Object[] { mac, sn, JSONObject.toJSONString(user) });
		if (user == null) {
			return new OpResult(OpResultTypeEnum.SYSERR, "无法获取用户信息");
		}
		// 下单获取vip订单号
		String partnerOrderId = vipOrderApi.orderSubmit(mac, sn, user.getId(), user.getDevicecode(), ORDER_CARD, "",
				null);// 后台可以不传duration，updateOrder时候传
		DATA.info("[submitVipOrder] submitResp:partnerOrderId={},user={}",
				new Object[] { partnerOrderId, JSONObject.toJSONString(user) });
		if ("exist".equals(partnerOrderId)) {
			return new OpResult(OpResultTypeEnum.SYSERR, "不能重复激活");
		}
		if (StringUtils.isEmpty(partnerOrderId) || "error".equals(partnerOrderId)) {
			return new OpResult(OpResultTypeEnum.SYSERR, "获取vip订单失败");
		}
		Card card = this.cardDao.getCardByActivityCode(cardPwd);
		if (card == null) {
			return new OpResult(OpResultTypeEnum.SYSERR, "没有该活动卡" + cardPwd);
		}
		card = cardBuildService.buildSubmitCard(card, mac, sn, partnerOrderId, user.getId(), user.getUserName(),
				user.getDevicecode());
		boolean result = this.update(card);// 更改为提交下单状态
		if (!result) {
			DATA.info("[submitVipOrder] CardBindingError:cardPwd={},partnerOrderId={}",
					new Object[] { card.getActivationCode(), partnerOrderId });
			return new OpResult(OpResultTypeEnum.SYSERR, "活动卡绑定失败");
		}
		Activity activity = activityDao.getActivityById(card.getActivityId());
		if (activity == null) {
			DATA.info("[activityNotFound] :activityId={}", new Object[] { card.getActivityId() });
			return new OpResult(OpResultTypeEnum.SYSERR, "找不到该活动");
		}
		ActivityOrder activityOrder = new ActivityOrder(activity, card, ORDER_CARD, ORDER_SUBMIT,
				StringUtils.getRealTimeString());
		if (activityOrderDao.save(activityOrder) != 1) {
			DATA.info("[submitVipOrder] InsertError:partnerOrderId={}", new Object[] { partnerOrderId });
			return new OpResult(OpResultTypeEnum.SYSERR, "下单失败");
		}
		DATA.info("[submitVipOrder] submit success :mac={},sn={},uid={},partnerOrderId={}",
				new Object[] { mac, sn, user.getId(), partnerOrderId });
		// 激活订单
		return this.activationCard(mac, sn, activityOrder, cardPwd);
	}
	public OpResult useCardForAgio(String mac, String sn, Card cardPwd,HttpServletRequest request) {
		Object obj = request.getSession().getAttribute(LoginAttribute.ATTRIBUTE_USER);
		SysUser currentUser = (SysUser) obj;
		String userId =currentUser.getUserId()+currentUser.getUserName()+currentUser.getPhoneNumber();
		// 战区信息从城市和战区关联表里查询
		String url =ApiConstants.AGIO_URL_DELAGIO;
		Map<String, String> map =new HashMap<String, String>();
		map.put("pkgId", "agio-"+cardPwd.getActivityId());//活动ID
		map.put("branchId", cardPwd.getCardFromBranch()+"");
		map.put("hallId", cardPwd.getCardFromHall()+"");
		map.put("opUserInfo", cardPwd.getCreatorName());
		map.put("userNum", cardPwd.getUserNum());
		map.put("orderNum", cardPwd.getOrderNum()+"");
		map.put("mac", mac);
		map.put("sn", sn);
		map.put("remark", System.currentTimeMillis()+userId);
		map.put("cardPwd", cardPwd.getActivationCode());
		String json =HttpUtils.httpPostString(url, map);
		DATA.info(url+"?"+map+":"+json);
		OpResult op =new OpResult(OpResultTypeEnum.BUNSHORT,"调用接口失败");
		if(!"".equals(json)){
			op =JSONObject.parseObject(json,OpResult.class);
		}
		return op;
	}
	/**
	 * 激活vip订单
	 * 
	 * @param cardPwd
	 * @param partnerOrderId
	 * @return
	 */
	public OpResult activationCard(String mac, String sn, ActivityOrder activityOrder, String cardPwd) {
		OpResult op = new OpResult();
		String result = vipOrderApi.orderActivation(mac, sn, activityOrder);
		if (StringUtils.isEmpty(result)) {
			DATA.info("[NotifyResponse] ResponseEntityError : activityOrderId={},result={}",
					new Object[] { activityOrder.activityOrderId, result });
			return new OpResult(OpResultTypeEnum.SYSERR, "返回值为空");
		}
		JSONObject jsonObject = JSONObject.parseObject(result);
		if (jsonObject == null) {
			DATA.info("[NotifyResponse] JSONObjectResultParseObjectError : activityOrderId={},result={}",
					new Object[] { activityOrder.activityOrderId, result });
			return new OpResult(OpResultTypeEnum.SYSERR, "返回值为空");
		}
		JSONObject jsonData = JSONObject.parseObject(jsonObject.getString("data"));
		if (jsonData == null) {
			DATA.info("[NotifyResponse] JSONObjectDataParseObjectError : activityOrderId={},result={}",
					new Object[] { activityOrder.activityOrderId, result });
			return new OpResult(OpResultTypeEnum.SYSERR, "返回值data为空");
		}
		if ("E000000".equals(jsonData.getString("code"))) {
			DATA.info("[NotifyResponse] failUpdate : activityOrderId={},result={}",
					new Object[] { activityOrder.activityOrderId, result });
			return new OpResult(OpResultTypeEnum.SYSERR, "订单更新失败");
		}
		Timestamp effectTime = null;
		Timestamp invalidTime = null;
		if (StringUtils.isNotEmpty(jsonData.getString("startDate"))
				&& StringUtils.isNotEmpty(jsonData.getString("endDate"))) {
			try {
				effectTime = Timestamp.valueOf(jsonData.getString("startDate"));
				invalidTime = Timestamp.valueOf(jsonData.getString("endDate"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// 若是活动卡，将卡置为激活状态
		if (cardPwd != null) {
			Card cond = this.cardDao.getCardByActivityCode(cardPwd);
			cond.setStatus(2);
			cond.setCardOrderStatus(2);
			cond.setTerminalMac(mac);
			cond.setTerminalSn(sn);
			cond.setEffectTime(effectTime);
			cond.setInvalidTime(invalidTime);
			try {
				this.cardDao.update(cond);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		DATA.info("[NotifyResponse] success:activityOrderId={},mac={},sn={}",
				new Object[] { activityOrder.activityOrderId, mac, sn });
		activityOrder.activityOrderStatus = ORDER_ACTIVITY;
		activityOrder.activityOrderStatusName = ORDER_ACTIVITY_STRING;
		activityOrder.activityTime = StringUtils.getRealTimeString();
		if (activityOrderDao.update(activityOrder) != 1) {
			DATA.info("[NotifyResponse] OrderUpdateError:activityOrderId={}",
					new Object[] { activityOrder.activityOrderId });
		}
		op.setResult(activityOrder);
		return op;
	}

	/**
	 * 概述：返回在线的计费包
	 * 返回值：List<Activity>
	 * 作者：徐浩波  xuhaobo@hiveview.com 
	 * 创建日期：2016-12-18上午11:47:21
	 */
	public List<Activity> getBackAgioList(Integer roleId, Integer zoneId, Integer branchId, Integer hallId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("roleId", roleId);
		map.put("zoneId", zoneId);
		map.put("branchId", branchId);
		map.put("hallId", hallId);
		return cardDao.getAgioConvertActivity(map);
	}
	
	@Autowired
	private AgioPackageConfDao agioPackageConfDao;
	private static ResourceBundle R = ResourceBundle.getBundle(EnvConstants.ENV_VER + "_api");

	public static String UPLOAD_PATH = R.getString("upload.path");
	public static String FILE_PATH = R.getString("file.path");

	public String doAgioBacthTask(){
		//获取当前未处理的批量任务.
		AgioPackageBatch batch =new AgioPackageBatch();
		batch.setState(0);
		batch.setPageSize(5);//一次取5个任务
		batch.setPageNo(1);
		List<AgioPackageBatch> rows = agioPackageConfDao.getAgioPackageBatchList(batch);
		if(rows==null){
			rows =new ArrayList<AgioPackageBatch>();
		}
		StringBuffer buffer =new StringBuffer();
		AgioJobMeta.flag =1;//开始任务
		AgioJobMeta.beginTime =new Date();
		AgioJobMeta.msg.put("1", "开始执行任务,有"+rows.size()+"条任务");
		buffer.append("开始执行任务,有"+rows.size()+"条任务");
		//循环去激活
		int counter =0;
		try {
			for(AgioPackageBatch task:rows){
				if(doAgioBacthTaskWithTaskInfo(task)){
					counter++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			AgioJobMeta.flag =0;//重置任务
			AgioJobMeta.beginTime =null;
			AgioJobMeta.msg.clear();
		}
		buffer.append("处理成功"+counter+"条任务");
		//生成报告去日志
		return buffer.toString();
	}
	public boolean doAgioBacthTaskWithTaskInfo(AgioPackageBatch batch){
		String  fileName =System.currentTimeMillis()+"";
		String downloadUrl ="";
		
		//读取文件
		if(batch!=null&&batch.getState()==0&&batch.getFileUrl()!=null){
			//更新为正处理
			batch.setState(1);
			batch.setText("开始处理。。"+new Date());
			agioPackageConfDao.updateAgioPackageBatch(batch);
			//遍历录入数据库
			//遍历开通
			InputStream inputStream = null;
			try {
				Map<String,String> boxMap =new HashMap<String,String>();
				inputStream = new FileInputStream(batch.getFileUrl());
				Workbook wb = WorkbookFactory.create(inputStream);
				Sheet readsheet = wb.getSheetAt(0);
				int rowLen = readsheet.getLastRowNum();
				for (int i = 1; i <= rowLen; i++) {
					Row row = readsheet.getRow(i);
					// 当前行为空
					if (this.isBlankRow(row)) {
						continue;
					}
					AgioPackageBatchTask task =new AgioPackageBatchTask();
					Cell cell = row.getCell(0);
					if(cell!=null){
						row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
						String mac =row.getCell(0).getStringCellValue();
						task.setMac(mac);
					}else{
						continue;
					}
					
					cell =row.getCell(2);
					if(cell!=null){
						row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
						String userNum =row.getCell(2).getStringCellValue();
						task.setUserNum(userNum);
					}else{
						task.setUserNum("");
					}
					cell =row.getCell(3);
					if(cell!=null){
						row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
						String orderNum =row.getCell(3).getStringCellValue();
						task.setOrderNum(orderNum);
					}else{
						task.setOrderNum("");
					}
					
					cell =row.getCell(4);
					if(cell!=null){
						row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
						String phone =row.getCell(4).getStringCellValue();
						task.setPhone(phone);
					}else{
						task.setPhone("");
					}
					
					
					cell = row.getCell(1);
					if(cell!=null){
						row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
						String sn =row.getCell(1).getStringCellValue();
						task.setSn(sn);
						if(boxMap.get(sn)!=null){
							task.setState(3);//失败
							task.setMsg("数据重复");
							task.setBacthId(batch.getId());
							agioPackageConfDao.saveAgioPackageBatchTask(task);
							continue;
						}
						boxMap.put(sn, "1");
					}else{
						continue;
					}
					task.setState(0);
					task.setBacthId(batch.getId());
					// 不验证mac sn是否合法，接口API验证了， 如果不合法，接口返回mac 不正确的提示
					//去激活
					Card card =this.buildAgioCard(batch,task);
					Data data =this.add(card);
					//更新任务状态
					if(data.getCode()==1){
						task.setState(1);//激活成功
						task.setMsg("激活成功！");
					}else{
						task.setState(2);//激活失败
						task.setMsg(data.getMsg());
					}
					agioPackageConfDao.saveAgioPackageBatchTask(task);
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				if (inputStream != null) {
					try {
						inputStream.close();
					} catch (IOException ioe) {
						ioe.printStackTrace();
					}
				}
			}
			//生成下载文件
			AgioPackageBatchTask query = new AgioPackageBatchTask();
			query.setBacthId(batch.getId());
			List<AgioPackageBatchTask> exportList =agioPackageConfDao.getAgioPackageBatchTaskList(query);
			DATA.info("导出麦币激活情况 total"+exportList.size());
			FileOutputStream fos = null;
			XSSFWorkbook wb = new XSSFWorkbook();
			XSSFSheet sheet = wb.createSheet("导出麦币激活情况" + batch.getFileName());
			XSSFCellStyle style = wb.createCellStyle();
			style.setAlignment(XSSFCellStyle.ALIGN_LEFT);
			try {
				for (int i = 0; i < exportList.size(); i++) {
					int index = 0;
					XSSFRow row = sheet.createRow(i);
					AgioPackageBatchTask live = exportList.get(i);
					row.createCell(index).setCellValue(live.getMac()==null?"":live.getMac());index++;
					row.createCell(index).setCellValue(live.getSn()==null?"":live.getSn()+"");index++;
					row.createCell(index).setCellValue(live.getUserNum()==null?"":live.getUserNum()+"");index++;
					row.createCell(index).setCellValue(live.getOrderNum()==null?"":live.getOrderNum());index++;
					row.createCell(index).setCellValue(live.getPhone()==null?"":live.getPhone());index++;
					row.createCell(index).setCellValue(live.getMsg()==null?"":live.getMsg());index++;
					int state =live.getState();
					if(state==0){
						row.createCell(index).setCellValue("待激活");
					}else if(state==1){
						row.createCell(index).setCellValue("成功");
					}else if(state==2){
						row.createCell(index).setCellValue("激活失败");
					}else{
						row.createCell(index).setCellValue("失败");
					}
				}
				DATA.info("导出麦币激活情况 执行完循环");
				fos = new FileOutputStream(UPLOAD_PATH + fileName + ".xls");
				wb.write(fos);
				fos.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (fos != null) {
					try {
						fos.close();
					} catch (IOException e) {
						DATA.info("流关闭异常" + e.toString());
						e.printStackTrace();
					}
				}
				downloadUrl =FILE_PATH+fileName+".xls";
				//更新下载地址,更新任务说明 //设置任务状态
				if(!downloadUrl.equals("")){
					batch.setDownloadUrl(downloadUrl);
					batch.setState(2);//开通完成
					batch.setText("已上传完成，请下载详单");
					agioPackageConfDao.updateAgioPackageBatch(batch);
				}
			}
		}else{
			DATA.debug("wrong agioBacth"+ToStringBuilder.reflectionToString(batch)+"不能导入");
		}
		
		//没有开通失败的情况，如果开通失败，需要重开
		
		
		return true;
	}
	/**
	 * 概述：组装调用api的麦币卡信息
	 * 返回值：Card
	 * 作者：徐浩波  xuhaobo@hiveview.com 
	 * 创建日期：2017-1-16下午3:13:47
	 */
	private Card buildAgioCard(AgioPackageBatch batch, AgioPackageBatchTask task) {
		Card card =new Card();
		card.setActivityId(batch.getPkgId());
		card.setCardFromBranch(batch.getBranchId());
		card.setCardFromHall(batch.getHallId());
		card.setCreatorName(batch.getOpUserInfo());
		card.setTerminalMac(task.getMac());
		card.setTerminalSn(task.getSn());
		card.setUserNum(task.getUserNum());
		card.setOrderNum(task.getOrderNum());
		card.setCardType("agio");
		return card;
	}

	public boolean isBlankRow(Row row) {
		if (row == null)
			return true;
		boolean result = true;
		for (int i = row.getFirstCellNum(); i < 10; i++) {
			Cell cell = row.getCell(i);
			String value = "";
			if (cell != null) {
				switch (cell.getCellType()) {
				case Cell.CELL_TYPE_STRING:
					value = cell.getStringCellValue();
					break;
				case Cell.CELL_TYPE_NUMERIC:
					value = String.valueOf((int) cell.getNumericCellValue());
					break;
				case Cell.CELL_TYPE_BOOLEAN:
					value = String.valueOf(cell.getBooleanCellValue());
					break;
				case Cell.CELL_TYPE_FORMULA:
					value = String.valueOf(cell.getCellFormula());
					break;
				// case Cell.CELL_TYPE_BLANK:
				// break;
				default:
					break;
				}
				if (!value.trim().equals("")) {
					result = false;
					break;
				}
			}
		}
		return result;
	}
	/**
	 * 概述：迁移boss的excel文件到指定目录下。 并记录到文件表
	 * 返回值：int
	 * 作者：徐浩波  xuhaobo@hiveview.com 
	 * 创建日期：2017-3-18下午6:02:33
	 */
	public int moveFile(){
		int count =0;
		String rootFilePath ="/data/ops/app/ftp/upload/";
		String uploadFilePath ="/data/ops/app/ftp/";
		String yearpath =DateUtil.getFilePathFormaterByDay();
		String newbossTargetPath =uploadFilePath+"zz/"+yearpath+"upload/";
		String oldbossTargetPath =uploadFilePath+"zz/"+yearpath;
		File rootFile =new File(rootFilePath);
		File[] files =rootFile.listFiles();
		if(files==null){
			return 0;
		}
		
		for(File file:files){
			String fileName =file.getName();
			if(!file.isDirectory()){
				String path = file.getPath();
				int type =0;
				if(fileName.indexOf("product")>-1){
					type =1;//新boss产品包类文件
				}else if(fileName.indexOf("customer")>-1){
					type =2;//新boss开通类文件
				}else{
					type =99;
				}
				ExcelFile exfile =new ExcelFile();
				exfile.setFileName(fileName);
				exfile.setPath(yearpath+"upload/"+fileName);
				exfile.setType(type);
				exfile.setStatus(1);
				exfile.setTimes(0);
				exfile.setMsg("转移成功");
				exfile.setCtime(new Date());
				FileUtil.copyFile(path, newbossTargetPath+fileName, true);
				file.delete();
				bossDao.save(exfile);
				count++;
			}
		}
		
		File uploadFile =new File(uploadFilePath);
		files =uploadFile.listFiles();
		if(files==null){
			return 0;
		}
		for(File file:files){

			String fileName =file.getName();
			if(!file.isDirectory()){
				//文件，老boss上传的
				String path = file.getPath();
				int type =4;
				String names [] =fileName.split("_");
				if(names.length>3){
					if(names[2].equals("1")){
						type=3;
					}
				}
				ExcelFile exfile =new ExcelFile();
				exfile.setFileName(fileName);
				exfile.setPath(yearpath+fileName);
				exfile.setType(type);
				exfile.setStatus(1);
				exfile.setTimes(0);
				exfile.setMsg("转移成功");
				exfile.setCtime(new Date());
				FileUtil.copyFile(path, oldbossTargetPath+fileName, true);
				file.delete();
				bossDao.save(exfile);
				count++;
			}
		
		}
		return count;
	}
	/**
	 * 概述：处理boss的文件任务。
	 * 返回值：void
	 * 作者：徐浩波  xuhaobo@hiveview.com 
	 * 创建日期：2017-3-18下午6:03:24
	 */
	public void doBossTask(int from,String date){
		String today =DateUtil.dateToString(new Date());
		if(!date.equals("")){
			today =date;
		}
		ExcelFile file = new ExcelFile();
		file.setMsg(today);
		file.setStatus(1);//查未处理的
		List<ExcelFile> rows =bossDao.getList(file);
		if(rows!=null){
			for(ExcelFile f:rows){
				int type =f.getType();
				if(type==1){
					// "新boss产品类";
					if(from==1){
						doNewBossProductTask(f);
					}
				}else if(type==2){
					// "新boss开通类";
					if(from ==2){
						if(f.getStatus()==1){
							doNewBossOrderTask(f);
						}	
					}
					
				}else if(type==3){
					// "boss产品类";
					if(from==1){
					doOldBossProductTask(f);}
				}else if(type==4){
					// "老boss开通类";
					if(from==2){
						if(f.getStatus()==1){
							doOldBossOrderTask(f);
						}
					}
				}else{
					// "未知类型";
					f.setMsg("未知类型文件，不处理");
					f.setStatus(3);
					int t =f.getTimes();
					t =t+1;
					f.setTimes(t);
					bossDao.update(f);
				}
			}
		}
	}

	/**
	 * 概述：处理老boss订单数据
	 * 返回值：void
	 * 作者：徐浩波  xuhaobo@hiveview.com 
	 * 创建日期：2017-3-22上午9:58:29
	 */
	public void doOldBossOrderTask(ExcelFile f) {

		
		String msg ="处理完成";
		try {
//			List<Branch> branchList =branchDao.getBranchList();
			List<BranchBossCity> branchList =branchDao.getBossCityByCityName(null);
			//生成CsvReader对象，以，为分隔符，GBK编码方式
	        CsvReader r = new CsvReader(rootPath+f.getPath(), ',',Charset.forName("GBK"));
	        //读取表头,此处不需要
	        r.readHeaders();
	        int length =r.getHeaders().length;
	        if(length!=19){
	        	msg ="表头不对，不处理";
	        }else{
	        	while (r.readRecord()) {
	        		//按列名读取这条记录的值
		        	BossOrderNew order =new BossOrderNew();
		        	order.setOrderId(r.get(0));
		        	order.setCustomerId(r.get(1));
		        	order.setCustomerName(r.get(2));
		        	order.setProductId(r.get(3));
		        	String productName=r.get(4);
		        	order.setProductName(productName);
		        	order.setAmount(r.get(5));
		        	order.setCtime(r.get(6));
		        	order.setCuser(r.get(7));
		        	order.setCgroup(r.get(8));
		        	order.setStartTime(r.get(9));
		        	order.setEndTime(r.get(10));
		        	order.setCityName(r.get(11));
		        	order.setCityId(r.get(12));
		        	order.setPhone(r.get(13));
		        	order.setMac(r.get(14));
		        	order.setSn(r.get(15));
		        	order.setOrderType(r.get(16));
		        	order.setOrderTypeId(r.get(17));
		        	order.setServiceFlag(r.get(18));
		        	//老boss 第二参数2
		        	order.setBranchId(getBranchByCityName(r.get(11), 2, branchList));
		        	order.setStatus(1);
		        	order.setMsg("初始状态");
		        	order.setBossType(2);
		        	order.setExcelId(f.getId());
		        	order.setActivityId(getActivityIdByProductName(productName, 2));
		        	try {
		        		bossDao.saveBossOrderNew(order);
					} catch (Exception e) {
						DATA.debug("丢失数据"+r.getRawRecord());
					}
		        }
	        }
	        //逐条读取记录，直至读完
	        
	        r.close();
	        
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			f.setStatus(3);
    		f.setMsg(msg);
    		f.setTimes(1);
    		bossDao.update(f);
		}
		
	
	
		
	}

	/**
	 * 概述：处理老boss套餐数据
	 * 返回值：void
	 * 作者：徐浩波  xuhaobo@hiveview.com 
	 * 创建日期：2017-3-22上午9:58:24
	 */
	public void doOldBossProductTask(ExcelFile f) {

		String msg ="处理完成";
		try {
//			List<Branch> branchList =branchDao.getBranchList();
			List<BranchBossCity> branchList =branchDao.getBossCityByCityName(null);
			//生成CsvReader对象，以，为分隔符，GBK编码方式
	        CsvReader r = new CsvReader(rootPath+f.getPath(), ',',Charset.forName("GBK"));
	        //读取表头,此处不需要
	        r.readHeaders();
	        int length =r.getHeaders().length;
	        if(length!=7){
	        	msg ="表头不对，不处理";
	        }else{
		        //逐条读取记录，直至读完
		        while (r.readRecord()) {
		            //按列名读取这条记录的值
		        	Product product = new Product();
		        	product.setProductId(r.get(0));
		        	product.setCityName(r.get(1));
		        	product.setCityId(r.get(2));
		        	product.setProductName(r.get(3));
		        	product.setDuration(r.get(4));
		        	product.setServiceFlag(r.get(5));
		        	product.setCtime(r.get(6));
		        	product.setType(2);
		        	//老boss 第二个参数传2
		        	product.setBranchId(getBranchByCityName(r.get(1), 2, branchList));
		        	try {
		        		bossDao.saveProduct(product);
					} catch (Exception e) {
						e.printStackTrace();
					}
		        	
		        }
	        }
	        r.close();
	        f.setStatus(3);
    		f.setMsg(msg);
    		f.setTimes(1);
    		bossDao.update(f);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	
		
	}

	/**
	 * 概述：处理新boss订单数据
	 * 返回值：void
	 * 作者：徐浩波  xuhaobo@hiveview.com 
	 * 创建日期：2017-3-22上午9:58:21
	 */
	public void doNewBossOrderTask(ExcelFile f) {

		String msg ="处理完成";
		try {
//			List<Branch> branchList =branchDao.getBranchList();
			List<BranchBossCity> branchList =branchDao.getBossCityByCityName(null);
			//生成CsvReader对象，以，为分隔符，GBK编码方式
	        CsvReader r = new CsvReader(rootPath+f.getPath(), ',',Charset.forName("GBK"));
	        //读取表头,此处不需要
	        r.readHeaders();
	        int length =r.getHeaders().length;
	        if(length!=19){
	        	msg ="表头不对，不处理";
	        }else{
	        	while (r.readRecord()) {
		            //按列名读取这条记录的值
		        	BossOrderNew order =new BossOrderNew();
		        	order.setOrderId(r.get(0));
		        	order.setCustomerId(r.get(1));
		        	order.setCustomerName(r.get(2));
		        	order.setProductId(r.get(3));
		        	String productName=r.get(4);
		        	order.setProductName(productName);
		        	order.setAmount(r.get(5));
		        	order.setCtime(r.get(6));
		        	order.setCuser(r.get(7));
		        	order.setCgroup(r.get(8));
		        	order.setStartTime(r.get(9));
		        	order.setEndTime(r.get(10));
		        	order.setCityName(r.get(11));
		        	order.setCityId(r.get(12));
		        	order.setPhone(r.get(13));
		        	order.setMac(r.get(14));
		        	order.setSn(r.get(15));
		        	order.setOrderType(r.get(16));
		        	order.setOrderTypeId(r.get(17));
		        	order.setServiceFlag(r.get(18));
		        	//新boss 第二参数1
		        	order.setBranchId(getBranchByCityName(r.get(11), 1, branchList));
		        	order.setStatus(1);
		        	order.setMsg("初始状态");
		        	order.setBossType(1);
		        	order.setExcelId(f.getId());
					order.setActivityId(getActivityIdByProductName(productName, 1));
		        	try {
		        		bossDao.saveBossOrderNew(order);
					} catch (Exception e) {
						DATA.debug("丢失数据"+r.getRawRecord());
					}
		        }
	        }
	        //逐条读取记录，直至读完
	        
	        r.close();
	        f.setStatus(3);
    		f.setMsg(msg);
    		f.setTimes(1);
    		bossDao.update(f);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	
	}

	/**
	 * 概述：处理新boss套餐信息文件
	 * 返回值：void
	 * 作者：徐浩波  xuhaobo@hiveview.com 
	 * 创建日期：2017-3-22上午9:56:52
	 */
	public void doNewBossProductTask(ExcelFile f) {
		String msg ="处理完成";
		try {
//			List<Branch> branchList =branchDao.getBranchList();
			List<BranchBossCity> branchList =branchDao.getBossCityByCityName(null);
			//生成CsvReader对象，以，为分隔符，GBK编码方式
	        CsvReader r = new CsvReader(rootPath+f.getPath(), ',',Charset.forName("GBK"));
	        //读取表头,此处不需要
	        r.readHeaders();
	        int length =r.getHeaders().length;
	        if(length!=7){
	        	msg ="表头不对，不处理";
	        }else{
	        	 //逐条读取记录，直至读完
		        while (r.readRecord()) {
		            //按列名读取这条记录的值
		        	Product product = new Product();
		        	product.setProductId(r.get(0));
		        	product.setCityName(r.get(1));
		        	product.setCityId(r.get(2));
		        	product.setProductName(r.get(3));
		        	product.setDuration(r.get(4));
		        	product.setServiceFlag(r.get(5));
		        	product.setCtime(r.get(6));
		        	product.setType(1);
		        	//新boss 第二参数1
		        	product.setBranchId(getBranchByCityName(r.get(1), 1, branchList));
		        	try {
		        		bossDao.saveProduct(product);
					} catch (Exception e) {
						e.printStackTrace();
					}
		        	
		        }
	        }
	        r.close();
	        f.setStatus(3);
    		f.setMsg(msg);
    		f.setTimes(1);
    		bossDao.update(f);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private int getActivityIdByProductName(String productName,int type){
		int result = 0;
		Product qp =new Product();
		qp.setProductName(productName);
		qp.setType(type);
		List<Product> l =bossDao.getProductList(qp);
		if(l!=null&&l.size()>0){
			Product p  =l.get(0);
			if(p.getActivityId()!=null){
				result =p.getActivityId();
			}
		}
		return result;
	}
	private int getBranchByCityName(String cityName,int type,List<BranchBossCity> list){
		int branchId =0;
		if(list!=null){
			for(BranchBossCity b:list){
				if(type==b.getBossType()&&b.getCityName().equals(cityName)){
						branchId =b.getBranchId();
						break;
				}
			}
		}
		return branchId;
	}

	/**
	 * 概述：boss上传excel文件列表
	 * 返回值：ScriptPage
	 * 作者：徐浩波  xuhaobo@hiveview.com 
	 * 创建日期：2017-3-21下午3:23:04
	 */
	public ScriptPage getExcelFileList(ExcelFile file) {

		List<ExcelFile> rows = new ArrayList<ExcelFile>();
		rows = bossDao.getList(file);
		int total = bossDao.count(file);
		ScriptPage scriptPage = new ScriptPage();
		scriptPage.setRows(rows);
		scriptPage.setTotal(total);
		return scriptPage;
	
	}
	/**
	 * 概述：套餐包列表
	 * 返回值：ScriptPage
	 * 作者：徐浩波  xuhaobo@hiveview.com 
	 * 创建日期：2017-3-21下午3:23:04
	 */
	public ScriptPage getProductList(Product product) {

		List<Product> rows = new ArrayList<Product>();
		rows = bossDao.getProductList(product);
		int total = bossDao.countPorduct(product);
		ScriptPage scriptPage = new ScriptPage();
		scriptPage.setRows(rows);
		scriptPage.setTotal(total);
		return scriptPage;
	
	}
	/**
	 * 概述：boss订单详情列表
	 * 返回值：ScriptPage
	 * 作者：徐浩波  xuhaobo@hiveview.com 
	 * 创建日期：2017-3-21下午3:23:04
	 */
	public ScriptPage getBossOrderNewList(BossOrderNew order) {
		List<BossOrderNew> rows = new ArrayList<BossOrderNew>();
		rows = bossDao.getBossOrderNewList(order);
		int total = bossDao.countBossOrderNew(order);
		ScriptPage scriptPage = new ScriptPage();
		scriptPage.setRows(rows);
		scriptPage.setTotal(total);
		return scriptPage;
	
	}

	/**
	 * 概述：
	 * 返回值：OpResult
	 * 作者：徐浩波  xuhaobo@hiveview.com 
	 * 创建日期：2017-3-27上午11:10:24
	 */
	public OpResult useCardInUse(String mac, String sn, String cardPwd, String model) {
		String url =ApiConstants.AGIO_URL;
		url = url+"card/activation.json";
		//String url = "http://localhost:8080/activity_20151125_init/card/activation.json";
		Map<String,String> map = new HashMap<String,String>();
		map.put("cardPwd", cardPwd);//
		map.put("mac", mac);
		map.put("sn", sn);
		String link =  DMPayHelper.toLinkForNotify(map, ParamConstants.VIP_PARTNER_KEY);
		DATA.debug(link);
		HiveHttpResponse response = HiveHttpPost.postString(url, link, HiveHttpEntityType.STRING);
		DATA.debug(response.statusCode+"--->"+response.entityString);
		OpResult op =new OpResult(OpResultTypeEnum.SYSERR, "通讯异常");
		if(response.statusCode==200){
			//通信正常
			JSONObject json =JSONObject.parseObject(response.entityString);
			String code = json.getString("code");
			String desc =json.getString("desc");
			op.setCode(code);
			op.setDesc(desc);
		}
		return op;
	}
	public int updateProduct(Product product){
		return bossDao.updateProduct(product);
	}
	
	public int updateBossOrder(BossOrderNew order){
		return bossDao.updateBossOrderNew(order);
	}
	/**
	 * 概述：发短信
	 * 返回值：String
	 * 作者：徐浩波  xuhaobo@hiveview.com 
	 * 创建日期：2017-4-6下午8:12:20
	 */
	public String sendBossSms(String activityCode){
		Card card =cardDao.getCardByActivityCode(activityCode);
		if(card!=null){
			smsService.sendSms(card);
		}else{
			return "找不到卡";
		}
		return "1";
	}
	/**
	 * 概述：提卡操作
	 * 返回值：Data
	 * 作者：徐浩波  xuhaobo@hiveview.com 
	 * 创建日期：2017-4-6下午8:00:46
	 */
	public Data getActivityCodeByOrderId(Integer orderId){
		BossOrderNew order =bossDao.getBossOrderById(orderId);
		if(order.getActivityCode()!=null&&order.getActivityCode().length()>0){
			return retRes(1,order.getActivityCode());
		}
		Data data =new Data();
		data.setCode(1);
		data.setMsg("系统异常");
		try {
			order =bossModelTOCard(order, 0);//提卡密操作不激活
			if(order.getActivityCode()!=null&&order.getActivityCode().length()>0){
				data =retRes(1,order.getActivityCode());
			}else{
				data =retRes(1, order.getMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}
	/**
	 * 概述：boss Order转card，获取激活码
	 * type =0 产码  type =1 macsn必须在，产码并激活
	 * 返回值：String
	 * 作者：徐浩波  xuhaobo@hiveview.com 
	 * 创建日期：2017-4-5下午8:42:48
	 */
	public BossOrderNew bossModelTOCard(BossOrderNew order ,int type){
		if(order==null){
			return null;
		}
		int branchId = order.getBranchId();
		
		if(branchId ==0){
			order.setStatus(5);
			order.setMsg("无分公司");
			bossDao.updateBossOrderNew(order);
			return order;
		}
		int activityId =order.getActivityId();
		String activityName ="";
		if(activityId ==0){
			order.setStatus(5);
			order.setMsg("无套餐");
			bossDao.updateBossOrderNew(order);
			return order;
		}else{
			Activity activity =activityDao.getActivityById(activityId);
			activityName =activity.getActivityName();
		}
		String mac =order.getMac();
		String sn =order.getSn();
		if(type==1){
			//产码并激活的，mac,sn必须存在，且正确
			if(StringUtils.isEmpty(mac)||StringUtils.isEmpty(sn)){
				order.setStatus(5);
				order.setMsg("mac,sn缺失");
				bossDao.updateBossOrderNew(order);
				return order;
			}
			if(mac.length()!=12||sn.length()!=17){
				order.setStatus(5);
				order.setMsg("mac,sn长度不对");
				bossDao.updateBossOrderNew(order);
				return order;
			}
		}
		
		String phone =order.getPhone();
		if(phone.length()>10){
			phone =phone.substring(0, 11);
		}
		int autoActiveTimeLength =365;
		int cardFromHall =-1;
		String hallName ="-";
		Branch branch = branchDao.getBranchInfoById(branchId);
		String branchName =branch.getBranchName();
		
		String cityId =branch.getCityCode();
		String cardType ="vip";
		String creatorName =order.getCuser();
		String orderNum =order.getOrderId();
		int source =4;//boss导入
		String userNum =order.getCustomerName();
		
		Card card =new Card();
		card.setActivityId(activityId);
		card.setActivityName(activityName);
		card.setAutoActiveTimeLength(autoActiveTimeLength);
		card.setBranchName(branchName);
		card.setCardFromBranch(branchId);
		card.setCardFromHall(cardFromHall);
		card.setCardType(cardType);
		card.setCityId(Integer.parseInt(cityId));
		card.setCreatorName(branchName+creatorName);
		card.setHallName(hallName);
		card.setOrderNum(orderNum);
		card.setPhone(phone);
		card.setSource(source);
		if(type==0){
			//产码的，忽略mac ,sn
			card.setTerminalMac("");
			card.setTerminalSn("");
		}else{
			card.setTerminalMac(mac);
			card.setTerminalSn(sn);
		}
		card.setUserNum(userNum);
		if(order.getActivityCode()==null||order.getActivityCode().length()==0){
			Data data =add(card);
			if(data.getCode()==1||data.getCode()==4){
				String activityCode = data.getMsg();
				order.setActivityCode(activityCode);
				order.setStatus(2);
				order.setMsg("已产码");
				bossDao.updateBossOrderNew(order);
			}else{
				order.setStatus(5);
				order.setMsg(data.getMsg());
				bossDao.updateBossOrderNew(order);
			}
		}
		
		if(type==1){
			String model =deviceApi.getDeviceModel(mac, sn);
			OpResult result =useCardInUse(mac, sn, order.getActivityCode(), model);
			if(result.getCode().equals("000")){
				order.setStatus(2);
				order.setMsg("已产码");
				bossDao.updateBossOrderNew(order);
			}else{
				order.setStatus(5);
				order.setMsg(result.getDesc());
				bossDao.updateBossOrderNew(order);
			}
		}
		return order;
	}

	/**
	 * 概述：根据分公司，计费包维度统计boss开通情况
	 * 返回值：List<BossReport>
	 * 作者：徐浩波  xuhaobo@hiveview.com 
	 * 创建日期：2017-4-12上午10:36:09
	 */
	public List<BossReport> getBossReport(String startDate, String endDate) {
		List<BossReport> reList =new ArrayList<BossReport>();
		String key   ="boss"+startDate+"_"+endDate;
		String result =redisService.get(key);
		if(result!=null &&!"".equals(result)){
			reList =JSONObject.parseArray(result, BossReport.class);
			return reList;
		}
		Map<String, String> map =new HashMap<String, String>();
		map.put("start", startDate);
		endDate = DateUtil.dateToString(DateUtil.getMotherTargetDate(DateUtil.stringToDate(endDate, 0), 1));
		map.put("end", endDate);
		List<BossOrderNew> list =bossDao.getBossReport(map);
		List<VipPackagePriceVo> prodcutList = chargePriceApi.getVipChargPriceList();
		BossReport title =new BossReport();
		Map <Integer, BossReport>tMap =new HashMap<Integer, BossReport>();
		BossReport temp1 =new BossReport();
		temp1.setProductName("无法关联");
		tMap.put(0, temp1);
		for(VipPackagePriceVo vo:prodcutList){
			BossReport temp =new BossReport();
			temp.setProductName(vo.getProductName());
			tMap.put(vo.getProductId(), temp);
		}
		title.setBranchId(-1);
		title.setBranchName("分公司");
		title.setMap(tMap);
		reList.add(title);
		for(BossOrderNew order:list){
			BossReport report =null;
			for(BossReport r:reList){
				if(r.getBranchId().equals(order.getBranchId())){
					report =r;
					reList.remove(r);
					break;
				}
			}
			if(report== null){
				report =new BossReport();
				report.setBranchId(order.getBranchId());
				Branch branch =branchDao.getBranchInfoById(order.getBranchId());
				if(branch!=null){
					report.setBranchName(branch.getBranchName());
				}else{
					report.setBranchName("未知");
				}
			}
			Map <Integer, BossReport> tempMap =getBossBodyMap(list,tMap,order.getBranchId());
			report.setMap(tempMap);
			reList.add(report);
		}
		
		String cacheString =JSONObject.toJSONString(reList);
		redisService.set(key, cacheString, 1000);
		return reList;
	}

	private Map<Integer, BossReport> getBossBodyMap(List<BossOrderNew> list, Map<Integer, BossReport> tMap,Integer branchId) {
		Map<Integer, BossReport> rmap=new HashMap<Integer, BossReport>();
		Set<Integer> tset =tMap.keySet();
		for(Integer tproduct:tset){
			int num =0;
			for(BossOrderNew order:list){
				if(order.getBranchId().equals(branchId)){
					Integer productId =0;
					if(order.getActivityId()!=null){
						productId =order.getActivityId();
					}
					if(productId.equals(tproduct)){
						num = order.getExcelId();
					}
				}else{
					continue;
				}
			}
			BossReport report =new BossReport();
			report.setNum(num);
			report.setProductName(num+"");
			rmap.put(tproduct, report);
		}
		
		return rmap;
	}
	public int doEditBossProductJob(Integer excelId){
		int result =0;
		ExcelFile file = new ExcelFile();
		file.setId(excelId);
		file.setStatus(2);
		file.setMsg("正在处理");
		file.setTimes(file.getTimes()+1);
		try {
			bossDao.update(file);
			BossOrderNew query =new BossOrderNew();
			query.setExcelId(excelId);
			List<BossOrderNew> list =bossDao.getBossOrderNewList(query);
			if(list!=null&&list.size()>0){
//				List<Branch> branchList =branchDao.getBranchList();
				List<BranchBossCity> branchList =branchDao.getBossCityByCityName(null);
				for(BossOrderNew order:list){
					order.setBranchId(getBranchByCityName(order.getCityName(), order.getBossType(), branchList));
		        	order.setActivityId(getActivityIdByProductName(order.getProductName(), order.getBossType()));
		        	updateBossOrder(order);
				}
			}
			result=1;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			file.setStatus(3);file.setMsg("已完成");bossDao.update(file);
		}
		return result;
		
	}

	/**
	 * 概述：
	 * 返回值：Data
	 * 作者：徐浩波  xuhaobo@hiveview.com 
	 * 创建日期：2017-4-19下午5:15:23
	 */
	public Data getTotalTxt() {
		Data data =new Data();
		List<Integer> list =cardDao.getTotalTxt();
		String msg ="";
		
		if(list!=null&&list.size()==3){
			int a = list.get(0);
			int b = list.get(1);
			int c = list.get(2);
			double d =(b*1.0)/(a*1.0);
			double e =(c*1.0)/(a*1.0);
			DecimalFormat df1 = new DecimalFormat("##.00%");
			DecimalFormat df2 = new DecimalFormat("##.00%");
			String f =df2.format(e);
			msg ="总计导入数据"+a+",\n其中mac,sn数据长度准确的有"+b+"个\b，占比百分之"
			+df1.format(d)+",\n长度符合规格的sn去重数"+c+",\n占比百分之"+f+"\n理论最大开通率百分之"+f;
		}
		data.setMsg(msg);
		return data;
	}
	
	/**
	 * 概述：根据地市，计费包维度统计boss开通情况
	 * 返回值：List<BossReport>
	 * 作者：徐浩波  xuhaobo@hiveview.com 
	 * 创建日期：2017-4-12上午10:36:09
	 */
	public List<BossReport> getBossCityReport(String startDate, String endDate) {
		List<BossReport> reList =new ArrayList<BossReport>();
		String key   ="bosscity"+startDate+"_"+endDate;
		String result =redisService.get(key);
		if(result!=null &&!"".equals(result)){
			reList =JSONObject.parseArray(result, BossReport.class);
			return reList;
		}
		Map<String, String> map =new HashMap<String, String>();
		map.put("start", startDate);
		endDate = DateUtil.dateToString(DateUtil.getMotherTargetDate(DateUtil.stringToDate(endDate, 0), 1));
		map.put("end", endDate);
		List<BossOrderNew> list =bossDao.getBossCityReport(map);
		List<VipPackagePriceVo> prodcutList = chargePriceApi.getVipChargPriceList();
		BossReport title =new BossReport();
		Map <Integer, BossReport>tMap =new HashMap<Integer, BossReport>();
		BossReport temp1 =new BossReport();
		temp1.setProductName("无法关联");
		tMap.put(0, temp1);
		for(VipPackagePriceVo vo:prodcutList){
			BossReport temp =new BossReport();
			temp.setProductName(vo.getProductName());
			tMap.put(vo.getProductId(), temp);
		}
		title.setBranchId(-1);
		title.setBranchName("地市名称");
		title.setMap(tMap);
		reList.add(title);
		for(BossOrderNew order:list){
			BossReport report =null;
			for(BossReport r:reList){
				if(r.getBranchName().equals(order.getCityName())){
					report =r;
					reList.remove(r);
					break;
				}
			}
			String branchName =order.getCityName();
			if(report== null){
				report =new BossReport();
				report.setBranchId(order.getBranchId());
				if(branchName!=null&&!"".equals(branchName)){
					report.setBranchName(branchName);
				}else{
					report.setBranchName("未知");
				}
			}
			Map <Integer, BossReport> tempMap =getBossBodyMap(list,tMap,branchName);
			report.setMap(tempMap);
			reList.add(report);
		}
		
		String cacheString =JSONObject.toJSONString(reList);
		redisService.set(key, cacheString, 1000);
		return reList;
	}
	private Map<Integer, BossReport> getBossBodyMap(List<BossOrderNew> list, Map<Integer, BossReport> tMap,String cityName) {
		Map<Integer, BossReport> rmap=new HashMap<Integer, BossReport>();
		Set<Integer> tset =tMap.keySet();
		for(Integer tproduct:tset){
			int num =0;
			for(BossOrderNew order:list){
				if(order.getCityName()!=null&&order.getCityName().equals(cityName)){
					Integer productId =0;
					if(order.getActivityId()!=null){
						productId =order.getActivityId();
					}
					if(productId.equals(tproduct)){
						num = order.getExcelId();
					}
				}else{
					continue;
				}
			}
			BossReport report =new BossReport();
			report.setNum(num);
			report.setProductName(num+"");
			rmap.put(tproduct, report);
		}
		
		return rmap;
	}
}
