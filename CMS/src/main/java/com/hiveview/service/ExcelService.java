package main.java.com.hiveview.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpStatus;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.hiveview.common.ParamConstants;
import com.hiveview.dao.activity.ActivityDao;
import com.hiveview.dao.award.AwardCodeDao;
import com.hiveview.dao.award.AwardDao;
import com.hiveview.dao.award.AwardPlayDao;
import com.hiveview.dao.card.CardDao;
import com.hiveview.dao.live.LiveOrderDao;
import com.hiveview.entity.activity.Activity;
import com.hiveview.entity.bo.Data;
import com.hiveview.entity.bo.LoginAttribute;
import com.hiveview.entity.card.BossReport;
import com.hiveview.entity.card.Card;
import com.hiveview.entity.count.CountRecord;
import com.hiveview.entity.live.LiveOrder;
import com.hiveview.entity.po.award.Award;
import com.hiveview.entity.po.award.AwardCode;
import com.hiveview.entity.po.award.AwardPlay;
import com.hiveview.entity.sys.SysUser;
import com.hiveview.pay.http.HiveHttpEntityType;
import com.hiveview.pay.http.HiveHttpGet;
import com.hiveview.pay.http.HiveHttpResponse;
import com.hiveview.service.card.CardService;
import com.hiveview.service.count.CountService;
import com.hiveview.service.sys.ZoneCityService;

@Service
public class ExcelService {

	@Autowired
	CardDao cardDao;
	@Autowired
	CardService cardService;
	@Autowired
	ActivityDao activityDao;
	@Autowired
	AwardCodeDao awardCodeDao;
	@Autowired
	AwardPlayDao awardPlayDao;
	@Autowired
	AwardDao awardDao;
	@Autowired
	private ZoneCityService zoneCityService;
	
	@Autowired
	private CountService countService;

	private static final Logger DATA = LoggerFactory.getLogger("data");
	private static final Logger LOG = LoggerFactory.getLogger(ExcelService.class);

	/**
	 * @param file
	 * @param upload_path
	 * @param userName
	 * @return
	 */
	public String upExcelServer(MultipartFile file, String upload_path, String userName) {
		String fileName = file.getOriginalFilename();
		String name = fileName.substring(0, fileName.indexOf("."));
		String type = fileName.substring(fileName.lastIndexOf("."), fileName.length());
		File uploadFile = new File(upload_path);
		String date = new DateTime().toString("yyyyMMddHHmmss");
		// 如果文件夹不存在则创建
		if (!uploadFile.exists() && !uploadFile.isDirectory()) {
			uploadFile.mkdir();
		}
		String excelPath = upload_path + name + "_" + date + "_" + userName + type;
		FileOutputStream out = null;
		InputStream in = null;
		byte[] buffer = new byte[1024];
		try {
			out = new FileOutputStream(excelPath);
			in = file.getInputStream();
			int len = buffer.length;
			while ((len = in.read(buffer)) != -1) {
				out.write(buffer, 0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
					return "error";
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
					return "error";
				}
			}
		}
		return excelPath;
	}

	/**
	 * 活动卡excel入库tb_card
	 * 
	 * @param req
	 * @param excelPath
	 * @param activityId
	 * @param activityName
	 * @return
	 */
	public Data excelHandleWorker(HttpServletRequest req, String excelPath, Integer activityId, String activityName,
			Integer autoActiveTimeLength,String cardType) {
		Object obj = req.getSession().getAttribute(LoginAttribute.ATTRIBUTE_USER);
		SysUser currentUser = (SysUser) obj;
		InputStream inputStream = null;
		int plan = 0, suc = 0, reNew = 0, noPara = 0, badMacOrSn = 0, repeatOrderNum = 0;
		String reportLog = "";// 总上传报告
		String reNewInfo = ""; // 重复的明细
		String badMacOrSnInfo = ""; // mac\sn不合法的明细
		String repeatOrderNumInfo = "";// 订单号重复明细
		try {
			inputStream = new FileInputStream(excelPath);
			Workbook wb = WorkbookFactory.create(inputStream);
			Sheet readsheet = wb.getSheetAt(0);
			int rowLen = readsheet.getLastRowNum();
			for (int i = 1; i <= rowLen; i++) {
				Row row = readsheet.getRow(i);
				// 当前行为空
				if (this.isBlankRow(row)) {
					continue;
				}
				try {
					Card card = new Card();
					card.setCardType(cardType);
					card.setSource(4);
					if (row.getCell(0) != null) {
						row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
						Integer uid = null;
						try {
							uid = Integer.parseInt(row.getCell(0).getStringCellValue().trim());
						} catch (Exception ec) {
							return this.cardService.retRes(5, "导入的excel第一列格式错误");
						}
						card.setUid(uid);
					}
					plan = plan + 1; // 总计划数量+1
					String userNum = null;
					String orderNum = null;
					String phone="";
					if (ParamConstants.BJ_BRANCH.equals(currentUser.getBranchId()) && (row.getCell(3) == null)) {
						noPara = noPara + 1; // 北京分公司缺少用户号数量+1
						continue;
					}
					String mac = "";
					String sn = "";
					if (row.getCell(1) != null) {
						row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
						mac = row.getCell(1).getStringCellValue().trim();
					}
					if (row.getCell(2) != null) {
						row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
						sn = row.getCell(2).getStringCellValue().trim();
					}
					
					if(StringUtils.isEmpty(mac) && StringUtils.isEmpty(sn)){
						//mac sn 都不录入属于合法录入
					}else{
						// 判定mac sn合法性
						if (!this.cardService.isRealUser(mac, sn)) {
							// 北京分公司mac，sn可以传空
							if (!StringUtils.isEmpty(mac) || !StringUtils.isEmpty(sn)
									|| !ParamConstants.BJ_BRANCH.equals(currentUser.getBranchId())) {
								badMacOrSn = badMacOrSn + 1; // mac,sn信息不合法数量+1
								if (ParamConstants.BJ_BRANCH.equals(currentUser.getBranchId())) {
									badMacOrSnInfo = badMacOrSnInfo + orderNum + ",";
								} else {
									badMacOrSnInfo = badMacOrSnInfo + sn + ",";
								}
								continue;
							}
						}
					}
					if (row.getCell(3) != null) {
						row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
						userNum = row.getCell(3).getStringCellValue().trim();
					}
					if (row.getCell(4) != null) {
						row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
						orderNum = row.getCell(4).getStringCellValue().trim();
					}
					if (row.getCell(5) != null) {
						row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
						phone = row.getCell(5).getStringCellValue().trim();
					}
					card = this.getCard(currentUser, mac, sn, phone,activityId, activityName, autoActiveTimeLength, userNum,
							orderNum,cardType);
					Integer code = cardService.add(card).getCode();
					switch (code) {
					case 1:
						suc = suc + 1;
						break;
					case 2:
						noPara = noPara + 1;
						break;
					case 3:
						repeatOrderNum = repeatOrderNum + 1;
						repeatOrderNumInfo = repeatOrderNumInfo + userNum + ",";
						break;
					case 4:
						reNew = reNew + 1;
						suc = suc + 1;
						reNewInfo = reNewInfo + userNum + ",";
						break;
					default:
						break;
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			reportLog = this.getReport(plan, suc, reNew, badMacOrSn, noPara, repeatOrderNum, reNewInfo, badMacOrSnInfo,
					repeatOrderNumInfo);
		} catch (Exception e) {
			e.printStackTrace();
			return this.cardService.retRes(0, "输入流异常");
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException ioe) {
					LOG.info("################STREAM CLOSE ERROR#################");
					ioe.printStackTrace();
					return this.cardService.retRes(2, "流关闭异常");
				}
			}
		}
		DATA.info(reportLog);
		// System.out.println(reportLog);
		return this.cardService.retRes(1, reportLog);
	}

	// 构造Card
	private Card getCard(SysUser user, String mac, String sn, String phone ,Integer activityId, String activityName,
			Integer autoActiveTimeLength, String userNum, String orderNum,String cardType) {
		Card card = new Card();
		card.setTerminalMac(mac);
		card.setTerminalSn(sn);
		card.setBranchName(user.getBranchName());
		card.setHallName(user.getHallName());
		card.setCardType(cardType);
		if(cardType.equals("vip")){
			Activity cond = this.activityDao.getActivityById(activityId);
			card.setEffectiveTimeLength(cond.getDuration());
		}
		card.setCardFromBranch(user.getBranchId());
		card.setCardFromHall(user.getHallId());
		card.setCreatorName(user.getUserName());
		card.setActivityId(activityId);
		card.setActivityName(activityName);
		card.setAutoActiveTimeLength(autoActiveTimeLength);
		card.setCityId(Integer.valueOf(user.getCityCode()));
		card.setUserNum(userNum);
		card.setOrderNum(orderNum);
		card.setPhone(phone);
		return card;
	}

	// 总结上传报告
	private String getReport(Integer plan, Integer suc, Integer reNew, Integer badMacOrSn, Integer noPara,
			Integer repeatOrderNum, String reNewInfo, String badMacOrSnInfo, String repeatOrderNumInfo) {
		System.out.println("参数打印：plan=" + plan + ",suc=" + suc + ",reNew=" + reNew + ",badMacOrSn=" + badMacOrSn
				+ ",noPara=" + noPara + ",repeatOrderNum=" + repeatOrderNum);
		String result = "本次计划上传" + plan + "张卡券，实际" + suc + "张上传成功。";
		if (reNew != 0) {
			result = result + "其中" + reNew + "张系统认为是续费用户，用户号如下：" + reNewInfo + "；";
		}
		if (plan != suc) {
			result = result + (plan - suc) + "张失败：";
			if (badMacOrSn != 0) {
				result = result + badMacOrSn + "张mac/sn不合法，如下：" + badMacOrSnInfo + "；";
			}
			if (noPara != 0) {
				result = result + noPara + "张因为没有用户号；";
			}
			if (repeatOrderNum != 0) {
				result = result + repeatOrderNum + "张业务单号重复，用户号如下：" + repeatOrderNumInfo;
			}
		}
		return result;
	}

	/**
	 * 下载Excel
	 * 
	 * @param req
	 * @param excelPath
	 * @param activityName
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public int createExcelFile(HttpServletRequest req, String excelPath, Card card) {
		Object obj = req.getSession().getAttribute(LoginAttribute.ATTRIBUTE_USER);
		SysUser currentUser = (SysUser) obj;
		Integer zoneId = currentUser.getZoneId();
		boolean isHoriz = false;
		if (ParamConstants.HORIZ_ZONE.equals(zoneId)) {
			isHoriz = true;
		}
		String creatorName = currentUser.getUserName();
		FileOutputStream fos = null;
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet("活动卡券表__" + creatorName);
		XSSFRow row = sheet.createRow((int) 0);
		XSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		int index = 0;
		XSSFCell cell = row.createCell((short) index);
		cell.setCellValue("用户名");
		cell.setCellStyle(style);
		cell = row.createCell((short) ++index);
		cell.setCellValue("卡生成员");
		cell.setCellStyle(style);
		cell = row.createCell((short) ++index);
		cell.setCellValue("活动名");
		cell.setCellStyle(style);
		if (isHoriz) {
			cell = row.createCell((short) ++index);
			cell.setCellValue("卡号");
			cell.setCellStyle(style);
		}
		cell = row.createCell((short) ++index);
		cell.setCellValue("激活码");
		cell.setCellStyle(style);
		cell = row.createCell((short) ++index);
		cell.setCellValue("卡状态");
		cell.setCellStyle(style);
		cell = row.createCell((short) ++index);
		cell.setCellValue("卡生成时间");
		cell.setCellStyle(style);
		cell = row.createCell((short) ++index);
		cell.setCellValue("激活时间");
		cell.setCellStyle(style);
		cell = row.createCell((short) ++index);
		cell.setCellValue("注销时间");
		cell.setCellStyle(style);
		cell = row.createCell((short) ++index);
		cell.setCellValue("MAC");
		cell.setCellStyle(style);
		cell = row.createCell((short) ++index);
		cell.setCellValue("SN");
		cell.setCellStyle(style);
		cell = row.createCell((short) ++index);
		cell.setCellValue("有效时长（天）");
		cell.setCellStyle(style);
		cell = row.createCell((short) ++index);
		cell.setCellValue("用户号");
		cell.setCellStyle(style);
		cell = row.createCell((short) ++index);
		cell.setCellValue("业务单号");
		cell.setCellStyle(style);
		List<Card> list = cardDao.getList(card);
		try {
			for (int i = 0; i < list.size(); i++) {
				index = 0;
				row = sheet.createRow((int) i + 1);
				Card cardConvert = (Card) list.get(i);
				if (cardConvert.getUid() != null) {
					row.createCell((short) index).setCellValue(cardConvert.getUid());
				}else{
					row.createCell((short) index).setCellValue("");
				}
				if (cardConvert.getBranchName() != null && cardConvert.getHallName() != null
						&& cardConvert.getCreatorName() != null) {
					row.createCell((short) ++index).setCellValue(cardConvert.getBranchName() + "/"
							+ cardConvert.getHallName() + "/" + cardConvert.getCreatorName());
				}else{
					row.createCell((short) ++index).setCellValue("");
				}
				if (cardConvert.getActivityName() != null) {
					row.createCell((short) ++index).setCellValue(cardConvert.getActivityName());
				}else{
					row.createCell((short) ++index).setCellValue("");
				}
				if (isHoriz) {
					if (cardConvert.getCardNumber() != null) {
						row.createCell((short) ++index).setCellValue(cardConvert.getCardNumber());
					}else{
						row.createCell((short) ++index).setCellValue("");
					}
				}
				if (cardConvert.getActivationCode() != null) {
					row.createCell((short) ++index).setCellValue(cardConvert.getActivationCode());
				}else{
					row.createCell((short) ++index).setCellValue("");
				}

				Integer status = cardConvert.getStatus();
				// 1：未激活 2：已激活 3:激活失败 4：已注销 5：已过期
				++index;
				if (1 == status) {
					row.createCell((short) index).setCellValue("未激活");
				} else if (2 == status) {
					row.createCell((short) index).setCellValue("已激活");
				} else if (3 == status) {
					row.createCell((short) index).setCellValue("激活失败");
				} else if (4 == status) {
					row.createCell((short) index).setCellValue("已注销");
				} else if (5 == status) {
					row.createCell((short) index).setCellValue("已过期");
				}else{
					row.createCell((short) index).setCellValue("");
				}
				if (cardConvert.getCreateTime() != null) {
					row.createCell((short) ++index).setCellValue(cardConvert.getCreateTime());
				}else{
					row.createCell((short) ++index).setCellValue("");
				}
				if (cardConvert.getActivationTime() != null) {
					row.createCell((short) ++index).setCellValue(cardConvert.getActivationTime());
				}else{
					row.createCell((short) ++index).setCellValue("");
				}
				if (cardConvert.getCancelTime() != null) {
					row.createCell((short) ++index).setCellValue(cardConvert.getCancelTime().toLocaleString());
				}else{
					row.createCell((short) ++index).setCellValue("");
				}
				if (cardConvert.getTerminalMac() != null) {
					row.createCell((short) ++index).setCellValue(cardConvert.getTerminalMac());
				}else{
					row.createCell((short) ++index).setCellValue("");
				}
				if (cardConvert.getTerminalSn() != null) {
					row.createCell((short) ++index).setCellValue(cardConvert.getTerminalSn());
				}else{
					row.createCell((short) ++index).setCellValue("");
				}
				if (cardConvert.getEffectiveTimeLength() != null) {
					row.createCell((short) ++index).setCellValue(cardConvert.getEffectiveTimeLength() / 86400);
				}else{
					row.createCell((short) ++index).setCellValue("");
				}
				if (cardConvert.getUserNum() != null) {
					row.createCell((short) ++index).setCellValue(cardConvert.getUserNum());
				}else{
					row.createCell((short) ++index).setCellValue("");
				}
				if (cardConvert.getOrderNum() != null) {
					row.createCell((short) ++index).setCellValue(cardConvert.getOrderNum());
				}else{
					row.createCell((short) ++index).setCellValue("");
				}
			}
			LOG.info("执行完循环");
			fos = new FileOutputStream(excelPath + ".xls");
			wb.write(fos);
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
			DATA.info("2、系统提示：Excel文件导出失败，原因：" + e.toString());
			return 0;
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
		return 1;
	}

	public int downLoad(String url) {
		LOG.info("下载文件:  " + url);
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

	@SuppressWarnings("deprecation")
	public int createAwardCodeExcelFile(String creatorName, int id) {
		FileOutputStream fos = null;
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("活动中奖码__");
		HSSFRow row = sheet.createRow((int) 0);
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		HSSFCell cell = row.createCell((short) 0);
		cell.setCellValue("ID");
		cell.setCellStyle(style);
		cell = row.createCell((short) 1);
		cell.setCellValue("中奖码");
		cell.setCellStyle(style);
		cell = row.createCell((short) 2);
		cell.setCellValue("奖品名称");
		cell.setCellStyle(style);
		cell = row.createCell((short) 3);
		cell.setCellValue("奖品类型");
		cell.setCellStyle(style);
		cell = row.createCell((short) 4);
		cell.setCellValue("领取者姓名");
		cell.setCellStyle(style);
		cell = row.createCell((short) 5);
		cell.setCellValue("领取者手机号");
		cell.setCellStyle(style);
		cell = row.createCell((short) 6);
		cell.setCellValue("大麦通行证");
		cell.setCellStyle(style);
		cell = row.createCell((short) 7);
		cell.setCellValue("中奖时间");
		cell.setCellStyle(style);
		cell = row.createCell((short) 8);
		cell.setCellValue("到期时间");
		cell.setCellStyle(style);
		cell = row.createCell((short) 9);
		cell.setCellValue("中奖状态");
		cell.setCellStyle(style);
		cell = row.createCell((short) 10);
		cell.setCellValue("邮寄信息");
		cell.setCellStyle(style);
		AwardCode awardCode = new AwardCode();
		/*
		 * hll:增加源码
		 */
		awardCode.setActivityId(id);

		List<AwardCode> list = awardCodeDao.getList(awardCode);
		try {
			for (int i = 0; i < list.size(); i++) {
				row = sheet.createRow((int) i + 1);
				AwardCode awardCodeConvert = (AwardCode) list.get(i);
				if (awardCodeConvert.getId() != 0) {
					row.createCell((short) 0).setCellValue(awardCodeConvert.getId());
				}
				if (awardCodeConvert.getAwardCode() != null) {
					row.createCell((short) 1).setCellValue(awardCodeConvert.getAwardCode());
				}
				if (awardCodeConvert.getAwardName() != null) {
					row.createCell((short) 2).setCellValue(awardCodeConvert.getAwardName());
				}
				if (awardCodeConvert.getAwardType() != null) {
					row.createCell((short) 3).setCellValue(awardCodeConvert.getAwardType());
				}
				if (awardCodeConvert.getRealName() != null) {
					row.createCell((short) 4).setCellValue(awardCodeConvert.getRealName());
				}
				if (awardCodeConvert.getUserPhone() != null) {
					row.createCell((short) 5).setCellValue(awardCodeConvert.getUserPhone());
				}
				if (awardCodeConvert.getMac() != null) {
					row.createCell((short) 6).setCellValue(awardCodeConvert.getMac());
				}
				if (awardCodeConvert.getPlayTime() != null) {
					row.createCell((short) 7).setCellValue(awardCodeConvert.getPlayTime());
				}
				if (awardCodeConvert.getAvailableEndTime() != null) {
					row.createCell((short) 8).setCellValue(awardCodeConvert.getAvailableEndTime());
				}

				Integer acceptFlag = awardCodeConvert.getAcceptFlag();
				// 1：中奖2：未中奖
				if (1 == acceptFlag) {
					row.createCell((short) 9).setCellValue("中奖");
				} else if (2 == acceptFlag) {
					row.createCell((short) 9).setCellValue("未中奖");
				}
				if (awardCodeConvert.getUserAddress() != null) {
					row.createCell((short) 10).setCellValue(awardCodeConvert.getUserAddress());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			DATA.info("1、系统提示：Excel文件导出失败，原因：" + e.toString());
			return 0;
		}
		try {
			fos = new FileOutputStream(creatorName + ".xls");
			wb.write(fos);
			fos.close();
			if (fos != null) {
				DATA.info("excel下载成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			DATA.info("2、系统提示：Excel文件导出失败，原因：" + e.toString());
			return 0;
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
		return 1;
	}

	@SuppressWarnings("deprecation")
	public int createAwardUserExcelFile(String creatorName, int id) {
		FileOutputStream fos = null;
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("参与用户名单__");
		HSSFRow row = sheet.createRow((int) 0);
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		HSSFCell cell = row.createCell((short) 0);
		cell.setCellValue("ID");
		cell.setCellStyle(style);
		cell = row.createCell((short) 1);
		cell.setCellValue("大麦通行证");
		cell.setCellStyle(style);
		cell = row.createCell((short) 2);
		cell.setCellValue("姓名");
		cell.setCellStyle(style);
		cell = row.createCell((short) 3);
		cell.setCellValue("手机号");
		cell.setCellStyle(style);
		cell = row.createCell((short) 4);
		cell.setCellValue("参与时间");
		cell.setCellStyle(style);
		cell = row.createCell((short) 5);
		cell.setCellValue("是否中奖");
		cell.setCellStyle(style);

		/*
		 * hll:注释源码 cell = row.createCell((short) 6); cell.setCellValue("是否中奖");
		 * cell.setCellStyle(style);
		 */
		AwardPlay awardPlay = new AwardPlay();

		/*
		 * hll:增加源码
		 */
		awardPlay.setActivityId(id);

		/*
		 * hll:注释源码 List<AwardPlay> list = awardPlayDao.getList(awardPlay);
		 */

		/*
		 * hll:增加源码
		 */
		List<AwardPlay> list = awardPlayDao.getPlayerList(awardPlay);
		try {
			for (int i = 0; i < list.size(); i++) {
				row = sheet.createRow((int) i + 1);
				AwardPlay awardPlayConvert = (AwardPlay) list.get(i);
				if (awardPlayConvert.getId() != 0) {
					row.createCell((short) 0).setCellValue(awardPlayConvert.getId());
				}
				if (awardPlayConvert.getMac() != null) {
					row.createCell((short) 1).setCellValue(awardPlayConvert.getMac());
				}
				if (awardPlayConvert.getRealName() != null) {
					row.createCell((short) 2).setCellValue(awardPlayConvert.getRealName());
				}
				if (awardPlayConvert.getUserPhone() != null) {
					row.createCell((short) 3).setCellValue(awardPlayConvert.getUserPhone());
				}
				if (awardPlayConvert.getPlayTime() != null) {
					row.createCell((short) 4).setCellValue(awardPlayConvert.getPlayTime());
				}
				String awardCode = awardPlayConvert.getAwardCode();
				// 1：中奖2：未中奖
				if (awardCode != null && !"".equals(awardCode)) {
					row.createCell((short) 5).setCellValue("中奖");
				} else {
					row.createCell((short) 5).setCellValue("未中奖");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			DATA.info("1、系统提示：Excel文件导出失败，原因：" + e.toString());
			return 0;
		}
		try {
			fos = new FileOutputStream(creatorName + ".xls");
			wb.write(fos);
			fos.close();
			if (fos != null) {
				DATA.info("excel下载成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			DATA.info("2、系统提示：Excel文件导出失败，原因：" + e.toString());
			return 0;
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
		return 1;
	}

	public int createAwardCodeModelExcelFile(String creatorName, int id) {
		FileOutputStream fileOutputStream = null;
		int awardCodeTotal = 0;
		try {
			fileOutputStream = new FileOutputStream(creatorName);
			String sheetname = "中奖码";
			HSSFWorkbook hssfWorkbook = new HSSFWorkbook();

			HSSFSheet hssfSheet = hssfWorkbook.createSheet(sheetname);

			HSSFCellStyle hssfCellStyle = hssfWorkbook.createCellStyle();
			hssfCellStyle.setAlignment(CellStyle.ALIGN_CENTER);

			HSSFRow hssfRow = null;
			HSSFCell hssfCell = null;
			hssfRow = hssfSheet.createRow(0);
			hssfCell = hssfRow.createCell(0, Cell.CELL_TYPE_STRING);
			hssfCell.setCellValue("ID");
			hssfCell.setCellStyle(hssfCellStyle);

			hssfCell = hssfRow.createCell(1, Cell.CELL_TYPE_STRING);
			hssfCell.setCellValue("中奖码");
			hssfCell.setCellStyle(hssfCellStyle);

			hssfCell = hssfRow.createCell(2, Cell.CELL_TYPE_STRING);
			hssfCell.setCellValue("奖品名称");
			hssfCell.setCellStyle(hssfCellStyle);

			hssfCell = hssfRow.createCell(3, Cell.CELL_TYPE_STRING);
			hssfCell.setCellValue("奖品类别（几等奖）");
			hssfCell.setCellStyle(hssfCellStyle);

			hssfCell = hssfRow.createCell(4, Cell.CELL_TYPE_STRING);
			hssfCell.setCellValue("奖品说明");
			hssfCell.setCellStyle(hssfCellStyle);

			int tempAwardAmount = 0;
			int rownum = 1;
			for (Award tempAward : awardDao.selectAwardList(id, 2)) {
				tempAwardAmount = tempAward.getAwardAmount();// 获取需要生成的中奖码数量（已生成的中奖码除外）
				if (tempAwardAmount > 0) {
					for (int i = 0; i < tempAwardAmount; i++) {
						hssfRow = hssfSheet.createRow(rownum);
						rownum++;
						hssfCell = hssfRow.createCell(0, Cell.CELL_TYPE_STRING);
						hssfCell.setCellValue((int) tempAward.getId());
						hssfCell.setCellStyle(hssfCellStyle);

						hssfCell = hssfRow.createCell(1, Cell.CELL_TYPE_STRING);
						hssfCell.setCellValue("");
						hssfCell.setCellStyle(hssfCellStyle);

						hssfCell = hssfRow.createCell(2, Cell.CELL_TYPE_STRING);
						hssfCell.setCellValue(tempAward.getAwardName());
						hssfCell.setCellStyle(hssfCellStyle);

						hssfCell = hssfRow.createCell(3, Cell.CELL_TYPE_STRING);
						hssfCell.setCellValue(tempAward.getAwardType());
						hssfCell.setCellStyle(hssfCellStyle);

						hssfCell = hssfRow.createCell(4, Cell.CELL_TYPE_STRING);
						hssfCell.setCellValue(tempAward.getAwardDesc());
						hssfCell.setCellStyle(hssfCellStyle);
						awardCodeTotal++;
					}
				}
			}

			hssfWorkbook.write(fileOutputStream);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		} finally {
			try {
				if (fileOutputStream != null) {
					fileOutputStream.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (awardCodeTotal <= 0) {
			return 2;
		}
		return 1;
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
	 * 概述：
	 * 返回值：int
	 * 作者：徐浩波  xuhaobo@hiveview.com 
	 * 创建日期：2017-1-9上午9:54:58
	 */
	@Autowired
	private LiveOrderDao liveOrderDao;
	public int createLiveExcelFile(HttpServletRequest req, String excelPath, LiveOrder order) {
		Object obj = req.getSession().getAttribute(LoginAttribute.ATTRIBUTE_USER);
		SysUser currentUser = (SysUser) obj;
		order.setBranchId(currentUser.getBranchId());
		order.setPageSize(50000);
		order.setPageNo(1);
		List<LiveOrder> list = liveOrderDao.getLiveListByZone(order);
		FileOutputStream fos = null;
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet("开通直播__" + currentUser.getBranchId());
		XSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(XSSFCellStyle.ALIGN_LEFT);
		XSSFRow titleRow = sheet.createRow(0);
		int titleIndex =0;
		titleRow.createCell(titleIndex).setCellValue("直播订购关系");titleIndex++;
		titleRow.createCell(titleIndex).setCellValue("订单状态");titleIndex++;
		titleRow.createCell(titleIndex).setCellValue("状态名称");titleIndex++;
		titleRow.createCell(titleIndex).setCellValue("记录状态");titleIndex++;
		titleRow.createCell(titleIndex).setCellValue("产品id");titleIndex++;
		titleRow.createCell(titleIndex).setCellValue("产品名称");titleIndex++;
		titleRow.createCell(titleIndex).setCellValue("计费id");titleIndex++;
		titleRow.createCell(titleIndex).setCellValue("计费名称");titleIndex++;
		titleRow.createCell(titleIndex).setCellValue("计费价格");titleIndex++;
		titleRow.createCell(titleIndex).setCellValue("计费时长");titleIndex++;
		titleRow.createCell(titleIndex).setCellValue("图片路径");titleIndex++;
		titleRow.createCell(titleIndex).setCellValue("提交时间");titleIndex++;
		titleRow.createCell(titleIndex).setCellValue("开通时间");titleIndex++;
		titleRow.createCell(titleIndex).setCellValue("关闭时间");titleIndex++;
		titleRow.createCell(titleIndex).setCellValue("开通省");titleIndex++;
		titleRow.createCell(titleIndex).setCellValue("开通省");titleIndex++;
		titleRow.createCell(titleIndex).setCellValue("开通市");titleIndex++;
		titleRow.createCell(titleIndex).setCellValue("开通市");titleIndex++;
		titleRow.createCell(titleIndex).setCellValue("分公司ID");titleIndex++;
		titleRow.createCell(titleIndex).setCellValue("分公司名称");titleIndex++;
		titleRow.createCell(titleIndex).setCellValue("营业厅ID");titleIndex++;
		titleRow.createCell(titleIndex).setCellValue("营业厅名称");titleIndex++;
		titleRow.createCell(titleIndex).setCellValue("开通人员id");titleIndex++;
		titleRow.createCell(titleIndex).setCellValue("开通人员name");titleIndex++;
		titleRow.createCell(titleIndex).setCellValue("用户id");titleIndex++;
		titleRow.createCell(titleIndex).setCellValue("用户名称");titleIndex++;
		titleRow.createCell(titleIndex).setCellValue("mac");titleIndex++;
		titleRow.createCell(titleIndex).setCellValue("sn");titleIndex++;
		titleRow.createCell(titleIndex).setCellValue("设备码");titleIndex++;
		titleRow.createCell(titleIndex).setCellValue("时长-年");titleIndex++;
		titleRow.createCell(titleIndex).setCellValue("时长-月");titleIndex++;
		titleRow.createCell(titleIndex).setCellValue("时长-日");titleIndex++;
		titleRow.createCell(titleIndex).setCellValue("订单类型");titleIndex++;
		titleRow.createCell(titleIndex).setCellValue("订单类型");titleIndex++;
		titleRow.createCell(titleIndex).setCellValue("修改者");titleIndex++;
		titleRow.createCell(titleIndex).setCellValue("修改时间");titleIndex++;
		titleRow.createCell(titleIndex).setCellValue("战区");titleIndex++;
		titleRow.createCell(titleIndex).setCellValue("战区");titleIndex++;
		try {
			for (int i = 1; i < list.size(); i++) {
				int index = 0;
				XSSFRow row = sheet.createRow(i);
				LiveOrder live = list.get(i);
				row.createCell(index).setCellValue(live.getLiveOrderId()==null?"":live.getLiveOrderId());index++;
				row.createCell(index).setCellValue(live.getStatus()==null?"":live.getStatus()+"");index++;
				row.createCell(index).setCellValue(live.getStatusName()==null?"":live.getStatusName());index++;
				row.createCell(index).setCellValue(live.getRecordStatus()==null?"":live.getRecordStatus()+"");index++;
				row.createCell(index).setCellValue(live.getProductId()==null?"":live.getProductId()+"");index++;
				row.createCell(index).setCellValue(live.getProductName()==null?"":live.getProductName());index++;
				row.createCell(index).setCellValue(live.getChargingId()==null?"":live.getChargingId()+"");index++;
				row.createCell(index).setCellValue(live.getChargingName()==null?"":live.getChargingName()+"");index++;
				row.createCell(index).setCellValue(live.getChargingPrice()==null?"":live.getChargingPrice()+"");index++;
				row.createCell(index).setCellValue(live.getChargingDuration()==null?"":live.getChargingDuration()+"");index++;
				row.createCell(index).setCellValue(live.getChargingImg()==null?"":live.getChargingImg());index++;
				row.createCell(index).setCellValue(live.getSubmitTime()==null?"":live.getSubmitTime());index++;
				row.createCell(index).setCellValue(live.getOpenTime()==null?"":live.getOpenTime());index++;
				row.createCell(index).setCellValue(live.getCloseTime()==null?"":live.getCloseTime());index++;
				row.createCell(index).setCellValue(live.getOpenProvince()==null?"":live.getOpenProvince());index++;
				row.createCell(index).setCellValue(live.getOpenProvinceName()==null?"":live.getOpenProvinceName());index++;
				row.createCell(index).setCellValue(live.getOpenCity()==null?"":live.getOpenCity());index++;
				row.createCell(index).setCellValue(live.getOpenCityName()==null?"":live.getOpenCityName());index++;
				row.createCell(index).setCellValue(live.getBranchId()==null?"":live.getBranchId()+"");index++;
				row.createCell(index).setCellValue(live.getBranchName()==null?"":live.getBranchName());index++;
				row.createCell(index).setCellValue(live.getHallId()==null?"":live.getHallId()+"");index++;
				row.createCell(index).setCellValue(live.getHallName()==null?"":live.getHallName());index++;
				row.createCell(index).setCellValue(live.getOpenuid()==null?"":live.getOpenuid()+"");index++;
				row.createCell(index).setCellValue(live.getOpenname()==null?"":live.getOpenname());index++;
				row.createCell(index).setCellValue(live.getUid()==null?"":live.getUid()+"");index++;
				row.createCell(index).setCellValue(live.getUname()==null?"":live.getUname());index++;
				row.createCell(index).setCellValue(live.getMac()==null?"":live.getMac());index++;
				row.createCell(index).setCellValue(live.getSn()==null?"":live.getSn());index++;
				row.createCell(index).setCellValue(live.getDevicecode()==null?"":live.getDevicecode());index++;
				row.createCell(index).setCellValue(live.getChargingDurationYear()==0?0:live.getChargingDurationYear());index++;
				row.createCell(index).setCellValue(live.getChargingDurationMonth()==0?0:live.getChargingDurationMonth());index++;
				row.createCell(index).setCellValue(live.getChargingDurationDay()==0?0:live.getChargingDurationDay());index++;
				row.createCell(index).setCellValue(live.getOrderType()==null?"":live.getOrderType()+"");index++;
				row.createCell(index).setCellValue(live.getOrderTypeName()==null?"":live.getOrderTypeName());index++;
				row.createCell(index).setCellValue(live.getLastUser()==null?"":live.getLastUser());index++;
				row.createCell(index).setCellValue(live.getLastTime()==null?"":live.getLastTime());index++;
				row.createCell(index).setCellValue(live.getOpenZoneId()==null?"":live.getOpenZoneId()+"");index++;
				row.createCell(index).setCellValue(live.getOpenZoneName()==null?"":live.getOpenZoneName());index++;
				
			}
			LOG.info("开通直播__执行完循环");
			fos = new FileOutputStream(excelPath + ".xls");
			wb.write(fos);
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
			DATA.info("2、系统提示：Excel文件导出失败，原因：" + e.toString());
			return 0;
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
		return 1;
	}
	/**
	 * 概述：
	 * 返回值：int
	 * 作者：徐浩波  xuhaobo@hiveview.com 
	 * 创建日期：2017-1-9上午9:54:58
	 */
	public int createCountExcelFile(HttpServletRequest req, String excelPath, Card order) {
		List<CountRecord> list = cardDao.getCountInfo(order);
		FileOutputStream fos = null;
		XSSFWorkbook wb = new XSSFWorkbook();
		String tail ="";
		switch (order.getQueryMethod()) {
		case 1:
			tail ="按激活时间查询";
			break;
		case 2:
			tail ="按生效时间查询";
			break;
		case 3:
			tail ="按开通时间查询";
			break;

		default:
			tail ="按开通时间查询";
			break;
		}
		XSSFSheet sheet = wb.createSheet("vip开通统计__"+tail);
		XSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(XSSFCellStyle.ALIGN_LEFT);
		XSSFRow row1 = sheet.createRow(0);
		row1.createCell(0).setCellValue("分公司");
		row1.createCell(1).setCellValue("开通总数");
		try {
			for (int i = 0; i <list.size(); i++) {
				int index = 0;
				XSSFRow row = sheet.createRow(i+1);
				CountRecord live = list.get(i);
				String branchName =live.getBranchName();
				if(branchName ==null){
					branchName="";
				}else if(branchName.equals("-1")){
					branchName="集团";
				}
				row.createCell(index).setCellValue(branchName);index++;
				row.createCell(index).setCellValue(live.getTotal());index++;
			}
			LOG.info("vip开通统计_执行完循环");
			fos = new FileOutputStream(excelPath + ".xls");
			wb.write(fos);
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
			DATA.info("2、系统提示：Excel文件导出失败，原因：" + e.toString());
			return 0;
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
		return 1;
	}

	/**
	 * 概述：
	 * 返回值：int
	 * 作者：徐浩波  xuhaobo@hiveview.com 
	 * 创建日期：2017-4-13下午3:45:53
	 */
	public int createBossExcelFile(HttpServletRequest req, String excelPath, String start, String end,String type) {
		
		List<BossReport> list =new ArrayList<BossReport>();
		String sheetName_ ="";
		if(type.equals("boss")){
			list =cardService.getBossReport(start, end);
			sheetName_ ="boss开通统计__";
		}else if(type.equals("city")){
			list =cardService.getBossCityReport(start, end);
			sheetName_ ="boss开通按地市__";
		}		else if(type.equals("vip")){
			list =countService.getBossReport(start, end);
			sheetName_ ="开卡统计__";
		}
		FileOutputStream fos = null;
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet(sheetName_+start+end);
		XSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(XSSFCellStyle.ALIGN_LEFT);
		
		try {
			for (int i = 0; i <list.size(); i++) {
				int index = 0;
				XSSFRow row = sheet.createRow(i);
				BossReport live = list.get(i);
				String branchName =live.getBranchName();
				if(branchName ==null){
					branchName="";
				}
				row.createCell(index).setCellValue(branchName);index++;
				Map<Integer, BossReport> map =live.getMap();
				for(Integer itme:map.keySet()){
					BossReport temp = map.get(itme);
					row.createCell(index).setCellValue(temp.getProductName());index++;
				}
			}
			LOG.info("boss开通统计_执行完循环");
			fos = new FileOutputStream(excelPath + ".xls");
			wb.write(fos);
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
			DATA.info("2、系统提示：Excel文件导出失败，原因：" + e.toString());
			return 0;
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
		return 1;
		
	}
}
