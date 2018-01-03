package main.java.com.hiveview.action.award;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hiveview.common.ApiConstants;
import com.hiveview.entity.bo.AjaxPage;
import com.hiveview.entity.po.award.AwardVideo;
import com.hiveview.service.award.AwardVideoService;

@Controller
@RequestMapping("/video")
public class VideoAction {

	@Autowired
	AwardVideoService awardVideoService;

	@RequestMapping(value = "/getBlueVideoList")
	@ResponseBody
	public List<HashMap<String, String>> getList(AjaxPage ajaxPage, AwardVideo awardVideo) {
		String url = ApiConstants.BLUE_URL + "blue-ray/api/j/albumList/1-1000.json";
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		try {
			list = awardVideoService.getBlueOrVipVideoList(url, 1);// 1的是后是极清首映的
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@RequestMapping(value = "/getVipVideoList")
	@ResponseBody
	public List<HashMap<String, String>> getVipVideoList(AjaxPage ajaxPage, AwardVideo awardVideo) {
		/*hll:注释源码
		String url = ApiConstants.VIP_URL + "api/open/vip/vipPackage/getVipPackageList/1.0.json";
		*/
		/*
		 * hll:增加源码
		 */
		String url = ApiConstants.VIP_URL + "api/open/vip/vipPackage/getVipPackageList_V2/1-1.0.json";
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		try {
			list = awardVideoService.getBlueOrVipVideoList(url, 2);// 2的时候是国内VIP的
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
