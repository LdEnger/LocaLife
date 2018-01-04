package com.hiveview.service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpStatus;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hiveview.dao.activity.ActivityDao;
import com.hiveview.dao.card.CardDao;
import com.hiveview.entity.activity.Activity;
import com.hiveview.entity.bo.LoginAttribute;
import com.hiveview.entity.card.Card;
import com.hiveview.entity.sys.SysUser;
import com.hiveview.pay.http.HiveHttpEntityType;
import com.hiveview.pay.http.HiveHttpGet;
import com.hiveview.pay.http.HiveHttpResponse;
import com.hiveview.service.card.CardService;

@Service
public class ExcelService{
	
	@Autowired
	CardDao cardDao;
	@Autowired
	CardService cardService;
	@Autowired
	ActivityDao activityDao;
	
	private static final Logger DATA = LoggerFactory.getLogger("data");
	private static final Logger LOG = LoggerFactory.getLogger(ExcelService.class);
	/**
	 * @param file
	 * @return
	 */
	public int upExcelServer(MultipartFile file) {
		String fileName = file.getOriginalFilename();
		String excelPath =""+ fileName;
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
			return 0;
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
					return 2;
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
					return 2;
				}
			}
		}
		return 1;
	}


	/**
	 * 活动卡excel入库tb_card
	 * @param req
	 * @param excelPath
	 * @param activityId
	 * @param activityName
	 * @return
	 */
	public int excelHandleWorker(HttpServletRequest req,String excelPath,Integer activityId,String activityName) {
		Object obj = req.getSession().getAttribute(LoginAttribute.ATTRIBUTE_USER);
		SysUser currentUser = (SysUser) obj;
		String creatorName=currentUser.getUserName();
		Integer cardFromHall = currentUser.getHallId();
		Integer cardFromBranch = currentUser.getBranchId();
		String hallName=currentUser.getHallName();
		String branchName=currentUser.getBranchName();
		//
		Activity cond = this.activityDao.getActivityById(activityId);
		
		
		
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(excelPath);
			Workbook wb = WorkbookFactory.create(inputStream);
			Sheet readsheet = wb.getSheetAt(0);
			int rowLen = readsheet.getLastRowNum();
				for (int i = 1; i <= rowLen; i++) {
					Row row = readsheet.getRow(i);
					//当前行为空
					if (row == null) {
						continue;
					}
						try {
							Card card=new Card();
							
							if(row.getCell(0)!=null){
					            row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
						      	card.setUid(Integer.parseInt(row.getCell(0).getStringCellValue().trim()));
						     }
							
//							card.setUid(Integer.parseInt(row.getCell(0).getStringCellValue().trim())); 
							card.setTerminalMac(row.getCell(1).getStringCellValue().trim());
							card.setTerminalSn(row.getCell(2).getStringCellValue().trim());
							card.setBranchName(branchName);
							card.setHallName(hallName);
							card.setEffectiveTimeLength(cond.getDuration());
							card.setCardFromBranch(cardFromBranch);
							card.setCardFromHall(cardFromHall);
							card.setCreatorName(creatorName);
							card.setActivityId(activityId);
							card.setActivityName(activityName);
							card.setActivationCode(cardService.getVerificationCode());
							cardDao.save(card);
						} catch (Exception e) {
							e.printStackTrace();
						}
				}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
					return 2;
				}
			}
		}
		return 1;
	}
	
	/**
	 * 下载Excel
	 * @param req
	 * @param excelPath
	 * @param activityName
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public int createExcelFile(HttpServletRequest req,String excelPath) {
		Object obj = req.getSession().getAttribute(LoginAttribute.ATTRIBUTE_USER);
		SysUser currentUser = (SysUser) obj;
		String creatorName=currentUser.getUserName();
		Integer cardFromHall=currentUser.getHallId();
		Integer cardFromBranch = currentUser.getBranchId();
		FileOutputStream fos = null;
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("活动卡券表__"+creatorName);
		HSSFRow row = sheet.createRow((int) 0);
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

		HSSFCell cell = row.createCell((short) 0);
		cell.setCellValue("用户名");
		cell.setCellStyle(style);
		cell = row.createCell((short) 1);
		cell.setCellValue("卡生成员");
		cell.setCellStyle(style);
		cell = row.createCell((short) 2);
		cell.setCellValue("活动名");
		cell.setCellStyle(style);
		cell = row.createCell((short) 3);
		cell.setCellValue("激活码");
		cell.setCellStyle(style);
		cell = row.createCell((short) 4);
		cell.setCellValue("卡状态");
		cell.setCellStyle(style);
		cell = row.createCell((short) 5);
		cell.setCellValue("卡生成时间");
		cell.setCellStyle(style);
		cell = row.createCell((short) 6);
		cell.setCellValue("激活时间");
		cell.setCellStyle(style);
		cell = row.createCell((short) 7);
		cell.setCellValue("注销时间");
		cell.setCellStyle(style);
		cell = row.createCell((short) 8);
		cell.setCellValue("MAC");
		cell.setCellStyle(style);
		cell = row.createCell((short) 9);
		cell.setCellValue("SN");
		cell.setCellStyle(style);
		cell = row.createCell((short) 10);
		cell.setCellValue("有效时长（天）");
		cell.setCellStyle(style);
		Card card = new Card();
		if(cardFromBranch !=null){
			card.setCardFromBranch(cardFromBranch);
		}
		if(cardFromHall!=null){
			card.setCardFromHall(cardFromHall);
		}
		List<Card> list = cardDao.getList(card);
		try {
			for (int i = 0; i < list.size(); i++) {
				row = sheet.createRow((int) i + 1);
				Card cardConvert = (Card) list.get(i);
				if(cardConvert.getUid() !=null){
					row.createCell((short) 0).setCellValue(cardConvert.getUid());
				}
				if(cardConvert.getBranchName() !=null&&cardConvert.getHallName()!=null&&cardConvert.getCreatorName()!=null){
					row.createCell((short) 1).setCellValue(cardConvert.getBranchName()+"/"+cardConvert.getHallName()+"/"+cardConvert.getCreatorName());
				}
				if(cardConvert.getActivityName() !=null){
					row.createCell((short) 2).setCellValue(cardConvert.getActivityName());
				}
				if(cardConvert.getActivationCode() !=null){
					row.createCell((short) 3).setCellValue(cardConvert.getActivationCode());
				}
				
				Integer status= cardConvert.getStatus();
				//1：未激活 2：已激活 3:激活失败 4：已注销 5：已过期
				if(1==status){
					row.createCell((short) 4).setCellValue("未激活");
				}else if(2==status){
					row.createCell((short) 4).setCellValue("已激活");
				}else if(3==status){
					row.createCell((short) 4).setCellValue("激活失败");
				}else if(4==status){
					row.createCell((short) 4).setCellValue("已注销");
				}else if(5==status){
					row.createCell((short) 4).setCellValue("已过期");
				}
				if(cardConvert.getCreateTime() !=null){
					row.createCell((short) 5).setCellValue(cardConvert.getCreateTime().toLocaleString());
				}
				if(cardConvert.getActivationTime() !=null){
					row.createCell((short) 6).setCellValue(cardConvert.getActivationTime().toLocaleString());
				}
				if(cardConvert.getCancelTime() !=null){
					row.createCell((short) 7).setCellValue(cardConvert.getCancelTime().toLocaleString());
				}
				if(cardConvert.getTerminalMac() !=null){
					row.createCell((short) 8).setCellValue(cardConvert.getTerminalMac());
				}
				if(cardConvert.getTerminalSn() !=null){
					row.createCell((short) 9).setCellValue(cardConvert.getTerminalSn());
				}
				if(cardConvert.getEffectiveTimeLength() !=null){
					row.createCell((short) 10).setCellValue(cardConvert.getEffectiveTimeLength()/86400);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			DATA.info("1、系统提示：Excel文件导出失败，原因："+ e.toString());
			return 0;
		}
		try {
			fos = new FileOutputStream(excelPath + ".xls");
			wb.write(fos);
			fos.close();
			if(fos!=null){
				DATA.info("excel下载成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			DATA.info("2、系统提示：Excel文件导出失败，原因："+ e.toString());
			return 0;
		} finally{
			if(fos != null){
				try {
					fos.close();
				} catch (IOException e) {
					DATA.info("流关闭异常"+ e.toString());
					e.printStackTrace();
				}
			}
		}
		return 1;
	}
	
	public int downLoad(String url){
		LOG.info("下载文件:  "+ url);
			try {
				for(int i=1;i<4;i++){
					HiveHttpResponse response= HiveHttpGet.getEntity(url, HiveHttpEntityType.STRING);
					if(response.statusCode==HttpStatus.SC_OK){
						return 1;	
					}
					Thread.sleep(500);//文件在服务器上同步存在时差，此处轮询三遍，若超过一定时限则代表文件同步有问题
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return 0;
	}
}
