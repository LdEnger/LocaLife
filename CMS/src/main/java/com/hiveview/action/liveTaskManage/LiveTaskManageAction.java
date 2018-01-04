package main.java.com.hiveview.action.liveTaskManage;

import java.net.URLEncoder;
import java.text.MessageFormat;

import javax.servlet.http.HttpServletRequest;

import com.hiveview.common.ApiConstants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.hiveview.entity.bo.LoginAttribute;
import com.hiveview.entity.liveManage.api.UserInfo;
import com.hiveview.entity.sys.SysUser;

@Controller
@RequestMapping("/liveTaskCross")
public class LiveTaskManageAction {

	@RequestMapping(value = "/show")
	public String showLive(HttpServletRequest request) throws Exception {
		Object obj = request.getSession().getAttribute(LoginAttribute.ATTRIBUTE_USER);
		SysUser currentUser = (SysUser) obj;
		if (currentUser == null) {
			return "timeout";
		}
		UserInfo info = new UserInfo();
		info.setOpenUid(currentUser.getUserId());
		info.setOpenName(currentUser.getUserName());
		info.setOpenRole(currentUser.getRoleId());
		info.setZoneId(currentUser.getZoneId());
		info.setZoneName(currentUser.getZoneName());
		info.setBranchId(currentUser.getBranchId());
		info.setBranchName(currentUser.getBranchName());
		info.setHallId(currentUser.getHallId());
		info.setHallName(currentUser.getHallName());
		String data = JSON.toJSONString(info);
		data = URLEncoder.encode(data, "UTF-8");
		String url = ApiConstants.LIVE_NEW_BATCHVIEW_URL;
		url = MessageFormat.format(url, data);
		return "redirect:" + url;
	}

}
