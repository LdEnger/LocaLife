package main.java.com.hiveview.action.award;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hiveview.entity.bo.AjaxPage;
import com.hiveview.entity.bo.Data;
import com.hiveview.entity.bo.ScriptPage;
import com.hiveview.entity.po.award.AwardActivity;
import com.hiveview.entity.po.award.AwardCode;
import com.hiveview.service.award.AwardActivityService;
import com.hiveview.service.award.AwardCodeService;

@Controller
@RequestMapping("/awardCode")
public class AwardCodeAction {
	
	@Autowired
	AwardCodeService awardCodeService;
	@Autowired
	AwardActivityService awardActivityService;
	
	@RequestMapping(value="/show")
	public String show(HttpServletRequest req){
		int activityId = Integer.parseInt(req.getParameter("id"));
		AwardActivity aa=awardActivityService.getAwardActivityById(activityId);
		req.setAttribute("awardActivity", aa);
//		if(aa.getShowFlag()==2 && (aa.getBeginTime().getTime()>aa.serverTime || aa.getEndTime().getTime()<aa.serverTime)){
		if(aa.getShowFlag()==2 && aa.getBeginTime().getTime()>aa.serverTime){
			req.setAttribute("isShow", true);
		}
		else{
			req.setAttribute("isShow", false);
		}
		return "award/awardCode";
	}
	
	@RequestMapping(value = "/getList")
	@ResponseBody
	public ScriptPage getPlayList(AjaxPage ajaxPage,AwardCode awardCode) {
		ScriptPage scriptPage = null;
		try {
			awardCode.copy(ajaxPage);
			scriptPage = awardCodeService.getList(awardCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return scriptPage;
	}
	
	@RequestMapping(value = "/saveExcel")
	@ResponseBody
	public int saveExcel(HttpServletRequest req,String codeExcelPath) {
		try {
			int suc = awardCodeService.excelHandleWorker(req,codeExcelPath);
			return suc;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	@RequestMapping(value="/selectAwardListByAwardCodeType")
	@ResponseBody
	public List<Map<String,String>> selectAwardListByAwardCodeType(int activityId){
		List<Map<String,String>> list=new ArrayList<Map<String,String>>();
		try {
			list=awardCodeService.selectAwardListByAwardCodeType(activityId, 2);
			Map<String,String> map=new HashMap<String, String>();
			map.put("id", "-1");
			map.put("text", "请选择");
			list.add(0, map);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}
	@RequestMapping(value="/deleteAwardCodeByDetailId")
	@ResponseBody
	public Data deleteAwardCodeByDetailId(int detailId){
		Data data=new Data();
		try {
			awardCodeService.deleteAwardCodeByDetailId(detailId);
		} catch (Exception e) {
			// TODO: handle exception
			data.setCode(0);
			data.setMsg("awardCode/deleteAwardCodeByDetailId");
			e.printStackTrace();
		}
		return data;
	}
	
}
