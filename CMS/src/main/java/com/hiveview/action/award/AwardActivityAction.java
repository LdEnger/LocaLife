package main.java.com.hiveview.action.award;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hiveview.entity.bo.AjaxPage;
import com.hiveview.entity.bo.Data;
import com.hiveview.entity.bo.ScriptPage;
import com.hiveview.entity.po.award.AwardActivity;
import com.hiveview.service.award.AwardActivityService;

@Controller
@RequestMapping("/awardActivity")
public class AwardActivityAction {
	
//	private static final Logger DATA = LoggerFactory.getLogger("data");
	
	@Autowired
	AwardActivityService awardActivityService;
	
	@RequestMapping(value="/show")
	public String show(){
		return "award/awardActivity";
	}
	
	@RequestMapping(value = "/add")
	@ResponseBody
	public Data add(AwardActivity awardActivity) {
		Data data=new Data();
		try {
			awardActivityService.add(awardActivity);
		} catch (Exception e) {
			// TODO: handle exception
			data.setCode(0);
			data.setMsg(e.getMessage());
			e.printStackTrace();
		}
		return data;
	}
	
	@RequestMapping(value = "/update")
	@ResponseBody
	public Data update(AwardActivity awardActivity) {
		Data data=new Data();
		try {
			awardActivityService.update(awardActivity);
		} catch (Exception e) {
			// TODO: handle exception
			data.setCode(0);
			data.setMsg(e.getMessage());
			e.printStackTrace();
		}
		return data;
	}
	
	@RequestMapping(value = "/getList")
	@ResponseBody
	public ScriptPage getList(AjaxPage ajaxPage,AwardActivity awardActivity) {
		ScriptPage scriptPage = null;
		try {
			awardActivity.copy(ajaxPage);
			scriptPage = awardActivityService.getList(awardActivity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return scriptPage;
	}
	
	@RequestMapping(value = "/del")
	@ResponseBody
	public Data del(AwardActivity awardActivity) {
		Data data=new Data();
		try {
			awardActivityService.del(awardActivity);
		} catch (Exception e) {
			// TODO: handle exception
			data.setCode(0);
			data.setMsg(e.getMessage());
			e.printStackTrace();
		}
		return data;
	}
	
	/**
	 * 
	 * @param sequence 当前要移动的活动的排序序号
	 * @param type 此参数==1时，是上移。==2时是下移
	 * @return
	 */
	@RequestMapping(value = "/move")
	@ResponseBody
	public Data up(int sequence,int type) {
		Data data=new Data();
		try {
			awardActivityService.move(sequence, type);
		} catch (Exception e) {
			// TODO: handle exception
			data.setCode(0);
			data.setMsg(e.getMessage());
			e.printStackTrace();
		}
		return data;
	}
	
	/**
	 * @description 上下线
	 * @param awardActivity
	 * @return
	 */
	@RequestMapping(value="/updateShowFlagById")
	@ResponseBody
	public Data updateShowFlagById(AwardActivity awardActivity){
		Data data=new Data();
		try {
			awardActivityService.updateShowFlagById(awardActivity);
		} catch (Exception e) {
			// TODO: handle exception
			data.setCode(0);
			data.setMsg(e.getMessage());
			e.printStackTrace();
		}
		return data;
	}
	
	/**
	 * @description 获取活动类型
	 * @return
	 */
	@RequestMapping(value="/selectAwardActivityType")
	@ResponseBody
	public List<Map<String,String>> selectAwardActivityType(){
		List<Map<String,String>> list=new ArrayList<Map<String,String>>();
		try {
			list=awardActivityService.selectAwardActivityType();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}
	
}

