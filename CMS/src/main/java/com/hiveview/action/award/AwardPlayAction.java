package main.java.com.hiveview.action.award;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hiveview.entity.bo.AjaxPage;
import com.hiveview.entity.bo.ScriptPage;
import com.hiveview.entity.po.award.AwardActivity;
import com.hiveview.entity.po.award.AwardPlay;
import com.hiveview.service.award.AwardActivityService;
import com.hiveview.service.award.AwardPlayService;

@Controller
@RequestMapping("/awardPlay")
public class AwardPlayAction {
	
	@Autowired
	AwardPlayService awardPlayService;
	@Autowired
	AwardActivityService awardActivityService;
	
	@RequestMapping(value="/show")
	public String show(HttpServletRequest req){
		int activityId = Integer.parseInt(req.getParameter("id"));
		AwardActivity aa=awardActivityService.getAwardActivityById(activityId);
		req.setAttribute("awardActivity", aa);
		return "award/awardPlay";
	}
	
	@RequestMapping(value = "/getPlayList")
	@ResponseBody
	public ScriptPage getPlayList(AjaxPage ajaxPage,AwardPlay awardPlay) {
		ScriptPage scriptPage = null;
		try {
			awardPlay.copy(ajaxPage);
			scriptPage = awardPlayService.getPlayList(awardPlay);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return scriptPage;
	}
	
}
