package main.java.com.hiveview.action;

import java.io.File;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hiveview.common.EnvConstants;
import com.hiveview.entity.bo.Data;
import com.hiveview.entity.bo.LoginAttribute;
import com.hiveview.entity.card.Card;
import com.hiveview.entity.live.LiveOrder;
import com.hiveview.entity.sys.SysUser;
import com.hiveview.service.ExcelService;
import com.hiveview.service.award.AwardActivityService;
import com.hiveview.util.DateUtil;

@Controller
@RequestMapping("/excel")
public class ExcelAction extends EnvConstants {

	@Autowired
	ExcelService excelService;
	@Autowired
	AwardActivityService awardActivityService;

	private static ResourceBundle R = ResourceBundle.getBundle(ENV_VER + "_api");

	public static String UPLOAD_PATH = R.getString("upload.path");
	public static String FILE_PATH = R.getString("file.path");

	/**
	 * 导入Excel
	 * 
	 * @param req
	 * @param file
	 * @param activityId
	 * @param activityName
	 * @return
	 */
	@RequestMapping(value = "/uploadExcel")
	@ResponseBody
	public String upLoadExcel(HttpServletRequest req, @RequestParam(value = "file", required = false) MultipartFile file) {
		Object obj = req.getSession().getAttribute(LoginAttribute.ATTRIBUTE_USER);
		SysUser currentUser = (SysUser) obj;
		String result = excelService.upExcelServer(file, UPLOAD_PATH, currentUser.getUserName());
		if ("error".equals(result)) {
			return null;
		} 
		return result;
	}

	/**
	 * 保存excel信息入库
	 * 
	 * @param req
	 * @param excelPath
	 * @param activityId
	 * @param activityName
	 * @return
	 */
	@RequestMapping(value = "/saveExcel")
	@ResponseBody
	public Data saveExcel(HttpServletRequest req, String excelPath, Integer activityId, String activityName,Integer autoActiveTimeLength,String cardType) {
		Data data = new Data();
		try {
			data = excelService.excelHandleWorker(req, excelPath, activityId, activityName,autoActiveTimeLength,cardType);
			return data;
		} catch (Exception e) {
			e.printStackTrace();
		}
		data.setCode(0);
		return data;
	}

	/**
	 * 导出excel
	 * 
	 * @param req
	 * @param excelPath
	 * @return
	 */
	@RequestMapping(value = "/downloadExcel")
	@ResponseBody
	public Data downloadExcel(HttpServletRequest req, Card card) {
		Data data = new Data();
		try {
			int suc = excelService.createExcelFile(req, UPLOAD_PATH + card.getExcelName(),card);
			if (1 == suc) {
				data.setCode(excelService.downLoad(FILE_PATH + card.getExcelName() + ".xls"));
				data.setMsg(FILE_PATH);
			} else {
				data.setCode(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	/**
	 * 导出excel
	 * 
	 * @param req
	 * @param excelPath
	 * @return
	 */
	@RequestMapping(value = "/exportExcel")
	@ResponseBody
	public Data exportExcel(String excelName,int id) {
		Data data = new Data();
		try {
			int suc = excelService.createAwardCodeExcelFile(UPLOAD_PATH + excelName,id);
			if (1 == suc) {
				data.setCode(excelService.downLoad(FILE_PATH + excelName + ".xls"));
				data.setMsg(FILE_PATH);
			} else {
				data.setCode(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	@RequestMapping(value = "/exportModel")
	@ResponseBody
	public Data exportModel(int id) {
		Data data = new Data();
		try {
			String path=DateUtil.getFilePath();
			String uploadPath=UPLOAD_PATH+path;
			String filePath=FILE_PATH+path;
			File file = new File(uploadPath);
			if (!file.exists() && !file.isDirectory()) {
				file.mkdirs();
			}
			
			int num = (int)(1+Math.random()*(10000));
			String excelName = "中奖码模版"+'-'+String.valueOf(num)+"-"+System.currentTimeMillis();
			String excelUploadPath = uploadPath + excelName + ".xls";// 存储地址
			String excelFilePath = filePath + excelName + ".xls";// 访问地址
			
			int suc = excelService.createAwardCodeModelExcelFile(excelUploadPath, id);
			if (1 == suc) {
				data.setCode(excelService.downLoad(excelFilePath));
				data.setMsg(excelFilePath);
			}else if(suc==2){
				data.setCode(2);
			} else {
				data.setCode(0);
			}
		} catch (Exception e) {
			data.setCode(0);
			e.printStackTrace();
		}
		return data;
	}

	@RequestMapping(value = "/exportUserExcel")
	@ResponseBody
	public Data exportUserExcel(String excelName,int id) {
		Data data = new Data();
		try {
			int suc = excelService.createAwardUserExcelFile(UPLOAD_PATH + excelName,id);
			if (1 == suc) {
				data.setCode(excelService.downLoad(FILE_PATH + excelName + ".xls"));
				data.setMsg(FILE_PATH);
			} else {
				data.setCode(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}
	/**
	 * 导出excel
	 * 
	 * @param req
	 * @param excelPath
	 * @return
	 */
	@RequestMapping(value = "/liveExcel")
	@ResponseBody
	public Data liveExcel(HttpServletRequest req, LiveOrder order) {
		Data data = new Data();
		try {
			String fileName =order.getBranchId()+"_"+order.getSubmitTime()+"_"+order.getOpenTime();
			int result =excelService.downLoad(FILE_PATH + fileName + ".xls");
			if(0==result){
				int suc = excelService.createLiveExcelFile(req, UPLOAD_PATH + fileName,order);
				if (1 == suc) {
					data.setCode(excelService.downLoad(FILE_PATH + fileName + ".xls"));
					data.setMsg(FILE_PATH + fileName + ".xls");
				} else {
					data.setCode(0);
				}
			}else{
				data.setCode(1);
				data.setMsg(FILE_PATH + fileName + ".xls");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}
	/**
	 * 导出excel
	 * 
	 * @param req
	 * @param excelPath
	 * @return
	 */
	@RequestMapping(value = "/bossExcel")
	@ResponseBody
	public Data bossExcel(HttpServletRequest req, String start,String end,String type) {
		Data data = new Data();
		try {
			String fileName =System.currentTimeMillis()+"";
			int suc = excelService.createBossExcelFile(req, UPLOAD_PATH + fileName,start,end,type);
			if (1 == suc) {
				data.setCode(excelService.downLoad(FILE_PATH + fileName + ".xls"));
				data.setMsg(FILE_PATH+ fileName + ".xls");
			} else {
				data.setCode(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}
	/**
	 * 导出excel
	 * 
	 * @param req
	 * @param excelPath
	 * @return
	 */
	@RequestMapping(value = "/countExcel")
	@ResponseBody
	public Data countExcel(HttpServletRequest req, Card card) {
		Data data = new Data();
		card.setPageSize(100000);
		card.setPageNo(1);
		try {
			String activationTime =card.getActivationTime();
			if(activationTime!=null&&activationTime.length()==10){
				activationTime = DateUtil.dateToString(DateUtil.getMotherTargetDate(DateUtil.stringToDate(activationTime, 0), 1));
				card.setActivationTime(activationTime);
			}
			String fileName =System.currentTimeMillis()+"vip";
			int suc = excelService.createCountExcelFile(req, UPLOAD_PATH + fileName,card);
			if (1 == suc) {
				data.setCode(excelService.downLoad(FILE_PATH + fileName + ".xls"));
				data.setMsg(FILE_PATH + fileName + ".xls");
			} else {
				data.setCode(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}
}
