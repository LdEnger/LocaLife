package com.hiveview.action;

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
import com.hiveview.service.ExcelService;

@Controller
@RequestMapping("/excel")
public class ExcelAction extends EnvConstants {
	
	@Autowired
	ExcelService excelService;

	private static ResourceBundle R = ResourceBundle.getBundle(ENV_VER + "_api");

	public static String UPLOAD_PATH = R.getString("upload.path");
	public static String FILE_PATH = R.getString("file.path");

	/**
	 * 导入Excel
	 * @param req
	 * @param file
	 * @param activityId
	 * @param activityName
	 * @return
	 */
	@RequestMapping(value = "/uploadExcel")
	public String upLoadExcel(HttpServletRequest req,@RequestParam(value = "file", required = false) MultipartFile file) {
		int upCode = excelService.upExcelServer(file);
		if (upCode == 1) {
			String excelPath = "" + file.getOriginalFilename();
			return excelPath;
		}else{
			return null;
		}
	}
	
	/**
	 * 保存excel信息入库
	 * @param req
	 * @param excelPath
	 * @param activityId
	 * @param activityName
	 * @return
	 */
	@RequestMapping(value = "/saveExcel")
	@ResponseBody
	public int saveExcel(HttpServletRequest req,String excelPath,Integer activityId,String activityName) {
		try {
			int suc = excelService.excelHandleWorker(req,excelPath,activityId,activityName);
			return suc;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	
	/**
	 * 导出excel
	 * @param req
	 * @param excelPath
	 * @return
	 */
	@RequestMapping(value = "/downloadExcel")
	@ResponseBody
	public Data downloadExcel(HttpServletRequest req,String excelName) {
		Data data = new Data();
		try {
			int suc = excelService.createExcelFile(req,UPLOAD_PATH+excelName);
			if(1==suc){
				data.setCode(excelService.downLoad(FILE_PATH+excelName+".xls"));
				data.setMsg(FILE_PATH);
			}else{
				data.setCode(0);				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}
}
