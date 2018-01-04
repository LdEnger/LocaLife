package com.hiveview.action.sys;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hiveview.entity.bo.AjaxPage;
import com.hiveview.entity.bo.Data;
import com.hiveview.entity.bo.LoginAttribute;
import com.hiveview.entity.bo.ScriptPage;
import com.hiveview.entity.sys.SysAuth;
import com.hiveview.entity.sys.SysUser;
import com.hiveview.service.sys.SysAuthService;

@Controller
@RequestMapping("/sysAuth")
public class SysAuthAction {

	static Logger LOG = LoggerFactory.getLogger(SysAuthAction.class);

	@Autowired
	private SysAuthService sysAuthService;

	/**
	 * 修改权限
	 * @param roleId
	 * @return
	 */
	@RequestMapping(value = "/getAuthByRoleId", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public ScriptPage getAuthByRoleId(int roleId) {
		ScriptPage scriptPage = new ScriptPage();
		List<SysAuth> sysAuthList = sysAuthService.getSysAuthByRoleId(roleId);
		scriptPage.setRows(sysAuthList);
		scriptPage.setTotal(sysAuthList.size());
		return scriptPage;
	}

	@RequestMapping(value = "/getAllAuthToTree", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public ScriptPage getAllAuthToTree() {
		ScriptPage scriptPage = new ScriptPage();
		List<SysAuth> sysAuthList = sysAuthService.getSysAuthByRoleId(-1);
		scriptPage.setRows(sysAuthList);
		return scriptPage;
	}

	@RequestMapping(value = "/getParentAuth", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public ScriptPage getParentAuth() {
		ScriptPage scriptPage = new ScriptPage();
		List<SysAuth> sysAuthList = sysAuthService.getParentAuth();
		scriptPage.setRows(sysAuthList);
		return scriptPage;
	}

	@RequestMapping(value = "/getSysAuthList", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public ScriptPage getSysAuthList(SysAuth sysAuth, AjaxPage ajaxPage) {
		sysAuth.copy(ajaxPage);
		return sysAuthService.getSysAuthList(sysAuth);
	}

	@RequestMapping("/updateSysAuth")
	@ResponseBody
	public Data updateSysAuth(SysAuth sysAuth) {
		Data data = new Data();
		int i = sysAuthService.updateSysAuth(sysAuth);
		if (i > 0) {
			data.setCode(1);
		} else {
			data.setCode(0);
		}
		return data;
	}

	@RequestMapping("/addSysAuth")
	@ResponseBody
	public Data addSysAuth(SysAuth sysAuth) {
		Data data = new Data();
		if (sysAuthService.addSysAuth(sysAuth) > 0) {
			data.setCode(1);
		} else {
			data.setCode(0);
		}
		return data;
	}

	@RequestMapping(value = "/getLeftAuth")
	@ResponseBody
	public ScriptPage getLeftAuth(HttpServletRequest req) {
		ScriptPage scriptPage = new ScriptPage();
		try {
			Object obj = req.getSession().getAttribute(LoginAttribute.ATTRIBUTE_USER);
			if (null != obj) {
				SysUser user = (SysUser) obj;
				scriptPage.setRows(sysAuthService.getLeftAuth(user.getRoleId()));
			} 
			return scriptPage;
		} catch (Exception e) {
			e.printStackTrace();
			return scriptPage;
		}
	}
	
	@RequestMapping(value = "/del")
	@ResponseBody
	public Data del(Integer authId) {
		Data data = new Data();
			try {
				boolean bool = this.sysAuthService.delAuth(authId);
				if (bool)
					data.setCode(1);
				else
					data.setCode(0);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return data;
		} 

	@RequestMapping(value = "/show")
	public String show() {
		return "sys/auth_list";
	}

}
