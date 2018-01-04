package main.java.com.hiveview.service.cardPack;

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
import com.hiveview.dao.cardPack.CardPackDao;
import com.hiveview.entity.Branch;
import com.hiveview.entity.activity.Activity;
import com.hiveview.entity.activity.ActivityOrder;
import com.hiveview.entity.agio.AgioPackageBatch;
import com.hiveview.entity.agio.AgioPackageBatchTask;
import com.hiveview.entity.bo.Data;
import com.hiveview.entity.bo.LoginAttribute;
import com.hiveview.entity.bo.OpResult;
import com.hiveview.entity.bo.ScriptPage;
import com.hiveview.entity.card.*;
import com.hiveview.entity.cardpack.CardPack;
import com.hiveview.entity.cardpack.ExportCardPack;
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
import com.hiveview.service.card.CardBuildService;
import com.hiveview.service.sms.SmsService;
import com.hiveview.service.sys.ZoneCityService;
import com.hiveview.util.*;
import com.hiveview.util.DateUtil;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.http.HttpStatus;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Title：CardService.java Description：卡券服务类 Company：hiveview.com Author：韩贺鹏
 * Email：hanhepeng@btte.net 2015年12月2日 下午3:50:20
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
public class CardPackService {

	@Autowired
	private CardPackDao cardPackDao;


	@Autowired
	private SmsService smsService;

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
	 * 获取大麦卡列表
	 * 
	 * @param card
	 * @return
	 */
	public ScriptPage getCardList(CardPack card) {
		List<CardPack> rows = new ArrayList<CardPack>();
		int total = 0;
		rows = cardPackDao.getList(card);
		total = cardPackDao.count(card);
		ScriptPage scriptPage = new ScriptPage();
		scriptPage.setRows(rows);
		scriptPage.setTotal(total);
		return scriptPage;
	}

	//爱奇艺制卡   单条插入 速度太慢
	/**
	 * @param card
	 * @return
	 */
	public int qyadd(CardPack card){
		int result =0;
		Date d = new Date();
		String time = DateUtil.dateToMin(d, "yyyy-MM-dd HH:mm:ss");
		card.setCreate_time(time);
		card.setCreate_status(1);//设置制卡状态
		int  cardNum=card.getCardNum();
		try {
			for(int i=0;i<cardNum;i++) {
				String dm_card_val = getVerificationCode(card.getCard_provider() + "");
				card.setDm_card_val(dm_card_val);
				int suc = this.cardPackDao.qyadd(card);
				result+=1;
			}
//			DATA.info("批量制卡 成功  数量 "+card.getCardNum());
			return result;
//			return suc > 0 ? true : false;
		}catch (Exception e){
			DATA.error("批量制卡失败"+e.toString());
			return 0;
			//记录日志
		}
	}
	//爱奇艺制卡 批量插入
	/**
	 * @param card
	 * @return
	 */
	public int qyaddList(CardPack card){
		int result =0;
		Date d = new Date();
		String time = DateUtil.dateToMin(d, "yyyy-MM-dd HH:mm:ss");

		int  cardNum=card.getCardNum();
		List<CardPack> parm =new ArrayList<CardPack>();
		Integer card_provider=card.getCard_provider();//卡类型
		Integer if_vip=card.getIf_vip();//是否vip
		Integer vip_days=card.getVip_days();// 获取 vip 的月数
		Integer renew_num=card.getRenew_num();// 获取卡的 续费次数
		Integer creater_id=card.getCreator_id(); //操作用户iD
		String creater_name=card.getCreator_name();//操作用户名
		Integer branch_id=card.getBranch_id();//分公司id
		String branch_name=card.getBranch_name();//分公司名

		try {
			for(int i=0;i<cardNum;i++) {
				CardPack cardParm=new CardPack();
				String dm_card_val = getVerificationCode(card_provider + "");
				cardParm.setDm_card_val(dm_card_val);
				cardParm.setIf_vip(if_vip);
				cardParm.setVip_days(vip_days);
				cardParm.setRenew_num(renew_num);
				cardParm.setCard_provider(card_provider);
				cardParm.setCreate_time(time);
				cardParm.setCreate_status(1);//设置制卡状态
				cardParm.setCreator_id(creater_id);
				cardParm.setCreator_name(creater_name);
				cardParm.setBranch_id(branch_id);
				cardParm.setBranch_name(branch_name);
				parm.add(cardParm);
			}
			result=this.cardPackDao.qyaddList(parm);
//			DATA.info("批量制卡 成功  数量 "+card.getCardNum());
			return result;
//			return suc > 0 ? true : false;
		}catch (Exception e){
			DATA.error("批量制卡失败"+e.toString());
			return 0;
			//记录日志
		}
	}

	//腾讯制卡
	/**
	 * @param card
	 * @return
	 */
	public int txadd(CardPack card){
		System.out.println("进入了腾讯制卡");
		int result =0;
		Date d = new Date();
		String time = DateUtil.dateToMin(d, "yyyy-MM-dd HH:mm:ss");
		card.setCreate_time(time);
		int  cardNum=card.getCardNum();
		try {
			result = this.cardPackDao.updateTxcard(card);
			return result;
		}catch (Exception e){
			return 0;
			//记录日志
		}
	}

	//t腾讯制卡 批量插入
	/**
	 * @param card
	 * @return
	 */
	public int txaddList(CardPack card){
		int result =0;
		Date d = new Date();
		String time = DateUtil.dateToMin(d, "yyyy-MM-dd HH:mm:ss");

		int  cardNum=card.getCardNum();
		List<CardPack> parm =new ArrayList<CardPack>();
		Integer card_provider=card.getCard_provider();//卡类型
		Integer if_vip=card.getIf_vip();//是否vip
		Integer vip_days=card.getVip_days();// 获取 vip 的月数
		Integer renew_num=card.getRenew_num();// 获取卡的 续费次数
		Integer creater_id=card.getCreator_id(); //操作用户iD
		String creater_name=card.getCreator_name();//操作用户名
		Integer branch_id=card.getBranch_id();//分公司id
		String branch_name=card.getBranch_name();//分公司名

		try {
			for(int i=0;i<cardNum;i++) {
				CardPack cardParm=new CardPack();
				String dm_card_val = getVerificationCode(card_provider + "");
				cardParm.setDm_card_val(dm_card_val);
				cardParm.setIf_vip(if_vip);
				cardParm.setVip_days(vip_days);
				cardParm.setRenew_num(renew_num);
				cardParm.setCard_provider(card_provider);
				cardParm.setCreate_time(time);
				cardParm.setCreate_status(1);//设置制卡状态
				cardParm.setCreator_id(creater_id);
				cardParm.setCreator_name(creater_name);
				cardParm.setBranch_id(branch_id);
				cardParm.setBranch_name(branch_name);
				parm.add(cardParm);
			}
			result=this.cardPackDao.qyaddList(parm);//业务都一样，只是提供方不一样，就调用qyaddList方法入库
//			DATA.info("批量制卡 成功  数量 "+card.getCardNum());
			return result;
//			return suc > 0 ? true : false;
		}catch (Exception e){
			DATA.error("批量制卡失败"+e.toString());
			return 0;
			//记录日志
		}
	}


	/**
	 * 生成导出文件   大麦卡列表
	 * @param req
	 * @param excelPath
	 * @param exportCard
	 * @return
	 */
	public ExportCardPack createCardPackExcelFile(HttpServletRequest req, String excelPath,ExportCardPack exportCard){
		ExportCardPack res=new ExportCardPack();
		Integer maxId=0;
		DATA.info(" 导出数量 "+exportCard.getExport_cardNum()+"  导出月数 "+exportCard.getExport_renewNum());
		ArrayList<CardPack> allCardInfo= cardPackDao.getExportAllList(exportCard);
		DATA.info("查询出阿里的数量 "+ allCardInfo.size());


		FileOutputStream fos = null;
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet("大麦卡列表");
		XSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(XSSFCellStyle.ALIGN_LEFT);
		XSSFRow titleRow = sheet.createRow(0);
		int titleIndex =0;
		titleRow.createCell(titleIndex).setCellValue("大麦激活码");titleIndex++;
		titleRow.createCell(titleIndex).setCellValue("第三方");titleIndex++;
		titleRow.createCell(titleIndex).setCellValue("次数");titleIndex++;
		titleRow.createCell(titleIndex).setCellValue("大麦VIP");titleIndex++;
		titleRow.createCell(titleIndex).setCellValue("VIP时长(月)");titleIndex++;

		try {
			for (int i = 1; i <= allCardInfo.size(); i++) {
				int index = 0;
				XSSFRow row = sheet.createRow(i);
				CardPack card = allCardInfo.get(i-1);
				row.createCell(index).setCellValue(card.getDm_card_val()==null?"":card.getDm_card_val());index++;
				row.createCell(index).setCellValue(card.getCard_provider()==0?"腾讯包年卡":"爱奇艺连续包月卡");index++;
				row.createCell(index).setCellValue(card.getRenew_num());index++;
				row.createCell(index).setCellValue(card.getIf_vip()==0?"否":"是");index++;
				row.createCell(index).setCellValue(card.getVip_days());index++;
				maxId=card.getId();
//				System.out.println("id最终是  "+ maxId);
			}
			res.setMaxId(maxId);
			fos = new FileOutputStream(excelPath + ".xls");
			wb.write(fos);
			fos.close();
			DATA.info("导出 大麦卡列表 文件创建 完成");
		} catch (Exception e) {
			e.printStackTrace();
			DATA.info("3、系统提示：大麦卡 Excel文件导出失败，原因：" + e.toString());
			res.setCode(0);
			return res;
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					DATA.info("流关闭异常" + e.toString());
					e.printStackTrace();
				}
			}
		}

		return res;
	}

	/**
	 * 对导出的数据进行更新
	 * @param exportCard
	 * @return
	 */
	public int updateExportCardPack(ExportCardPack exportCard){
		int res=0;
		res=cardPackDao.updateExportCard(exportCard);
		return res;
	}
	/**
	 * 获取可以导出爱奇艺卡数量
	 * @param exportCard
	 * @return
	 */
	public int getStorgeNum(ExportCardPack exportCard){
		int res=0;
		res=cardPackDao.getStorgeNum(exportCard);
		return res;
	}

	/**
	 * 测试现在路径是否可用
	 * @param url
	 * @return
	 */
	public int downLoad(String url) {
		DATA.info("下载文件:  " + url);
		try {
			for (int i = 1; i < 5; i++) {
				HiveHttpResponse response = HiveHttpGet.getEntity(url, HiveHttpEntityType.STRING);
				if (response.statusCode == HttpStatus.SC_OK) {
					return 1;
				}
				Thread.sleep(500);// 文件在服务器上同步存在时差，此处轮询三遍，若超过一定时限则代表文件同步有问题
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return 0;
	}





	/**
	 * 生成卡密
	 *@card_provider 卡提供者
	 * @return 17位字符串
	 */
	public String getVerificationCode(String card_provider) {
		if(null==card_provider||"".equalsIgnoreCase(card_provider)){
			card_provider="0";//默认腾讯
		}
		return  this.getRandom(5) + " " + this.getRandom(5) + " " + this.getRandom(5) + " "
				+ this.getRandom(5) + " " +card_provider;
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

}
