package main.java.com.hiveview.service.award;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.hiveview.dao.award.AwardCodeDao;
import com.hiveview.dao.award.AwardDao;
import com.hiveview.entity.bo.ScriptPage;
import com.hiveview.entity.po.award.AwardCode;

/**
 * Title：AwardCodeService.java
 * Description：中奖码
 * Company：hiveview.com
 * Author：周恩至
 * Email：zhouenzhi@btte.net 
 * 2016年03月19日 上午11:42:02
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class AwardCodeService {
	
	@Autowired
	AwardCodeDao awardCodeDao;
	@Autowired
	AwardDao awardDao;
	
	public ScriptPage getList(AwardCode awardCode) {
		ScriptPage scriptPage = new ScriptPage();
		List<AwardCode> rows = awardCodeDao.getList(awardCode);
		int total = awardCodeDao.count(awardCode);
		scriptPage.setRows(rows);
		scriptPage.setTotal(total);
		return scriptPage;
	}
	
	public int excelHandleWorker(HttpServletRequest req, String codeExcelPath){
		FileInputStream fileInputStream=null;
		List<AwardCode> awardCodeList=new ArrayList<AwardCode>();
		try {
			fileInputStream=new FileInputStream(codeExcelPath);
			Workbook workbook=WorkbookFactory.create(fileInputStream);
			Sheet sheet=workbook.getSheetAt(0);
			int lastRowNum=sheet.getLastRowNum();
			if(lastRowNum<1){
				return 5;//excel表没有数据
			}
			Row row=null;
			int detailId=0;
			String awardCode="";
			Map<String,Object> awardCodeMap=new HashMap<String,Object>();
			AwardCode tempAwardCode=null;
			for(int i=1;i<=lastRowNum;i++){
				row=sheet.getRow(i);
				try {
					detailId=(int)Double.parseDouble(getCellValue(row.getCell(0)));
				} catch (Exception e) {
					// TODO: handle exception
					return 6;//ID已被修改
				}
				awardCode=getCellValue(row.getCell(1));
				if(StringUtils.isEmpty(awardCode)){
					return 4;//有空中奖码
				}
				awardCodeMap.put(awardCode, awardCode);
				
				tempAwardCode=new AwardCode();
				tempAwardCode.setDetailId(detailId);
				tempAwardCode.setAwardCode(awardCode);
				tempAwardCode.setAwardCodeType(2);//中奖码生成为2
				tempAwardCode.setAcceptFlag(2);//中奖状态为2
				awardCodeList.add(tempAwardCode);
				
			}
			if(awardCodeMap.size()<lastRowNum){
				return 3;//有重复中奖码
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}finally{
			try {
				if(fileInputStream!=null){
					fileInputStream.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for(AwardCode awardCode2:awardCodeList){
			awardCodeDao.add(awardCode2);
		}
		return 1;
	}
	private String getCellValue(Cell cell) {
		String cellValue="";
		if(cell==null){
			return cellValue;
		}
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_BLANK:
			cellValue=cell.getStringCellValue();
			break;
		case Cell.CELL_TYPE_NUMERIC:
			if(DateUtil.isCellDateFormatted(cell)){
				cellValue=new SimpleDateFormat("yyyy-MM-dd").format(cell.getDateCellValue());
			}
			else{
				cellValue=String.valueOf(cell.getNumericCellValue());
			}
			break;
		case Cell.CELL_TYPE_STRING:
			cellValue=cell.getStringCellValue();
			break;
		case Cell.CELL_TYPE_BOOLEAN:
			cellValue=String.valueOf(cell.getBooleanCellValue());
			break;
		default:
			break;
		}
		return cellValue;
	}
	
	public List<Map<String,String>> selectAwardListByAwardCodeType(int activityId,int awardCodeType){
		return awardDao.selectAwardListByAwardCodeType(activityId, awardCodeType);
	}
	public int deleteAwardCodeByDetailId(int detailId){
		return awardCodeDao.deleteAwardCodeByDetailId(detailId);
	}
	
	
	
//	public List<Award> getAwardList(int activityId,int awardCodeType){
//		Award award = new Award();
//		award.setActivityId(activityId);
//		award.setAwardCodeType(awardCodeType);
//		List<Award> awards = awardDao.getList(award);
//		return awards;
//	}
//	
//	public boolean add(AwardCode awardCode){
//		boolean flag = false;
//		int count = awardCodeDao.add(awardCode);
//		if(count>0){
//			flag = true;
//		}
//		return flag;
//	}
	
}
