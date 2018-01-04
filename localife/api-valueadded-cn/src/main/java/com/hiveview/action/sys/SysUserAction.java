package com.hiveview.action.sys;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hiveview.dao.sys.SysUserDao;
import com.hiveview.entity.Branch;
import com.hiveview.entity.Hall;
import com.hiveview.entity.bo.AjaxPage;
import com.hiveview.entity.bo.Data;
import com.hiveview.entity.bo.LoginAttribute;
import com.hiveview.entity.bo.OpResult;
import com.hiveview.entity.bo.ScriptPage;
import com.hiveview.entity.sys.SysUser;
import com.hiveview.entity.vo.ComboVo;
import com.hiveview.service.BranchService;
import com.hiveview.service.HallService;
import com.hiveview.service.sys.SysUserService;

@Controller
@RequestMapping("/sysUser")
public class SysUserAction {
	private static final Logger DATA = LoggerFactory.getLogger("data");
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUserDao sysUserDao;
	@Autowired
	private BranchService bs;
	@Autowired
	private HallService hs;

	@RequestMapping(value = "/getList")
	@ResponseBody
	public ScriptPage getSysUserList(AjaxPage ajaxPage, SysUser sysUser, HttpServletRequest req) {
		ScriptPage scriptPage = null;
		try {
			Object obj = req.getSession().getAttribute(LoginAttribute.ATTRIBUTE_USER);
			if (null == obj) {
				return scriptPage;
			}
			SysUser currentUser = (SysUser) obj;
			sysUser.setRoleId(currentUser.getRoleId());
			sysUser.setCityCode(currentUser.getCityCode());
			sysUser.copy(ajaxPage);
			scriptPage = sysUserService.getSysUserList(sysUser);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return scriptPage;
	}

	@RequestMapping("/updateSysUserById")
	@ResponseBody
	public Data updateSysUserById(SysUser user, HttpServletRequest req) {
		Data data = new Data();
		Object obj = req.getSession().getAttribute(LoginAttribute.ATTRIBUTE_USER);
		if (null == obj) {
			data.setCode(0);
			return data;
		}
		SysUser currentUser = (SysUser) obj;
		user.setUpdatedBy(currentUser.getUserId());
		user = sysUserService.idDefaultUser(user);
		int flag = sysUserService.updateSysUser(user);
		if (flag > 0) {
			data.setCode(1);
		} else if (flag == -1) {
			data.setCode(-1);
			data.setMsg("mail_same");
		} else {
			data.setCode(0);
		}
		return data;
	}

	@RequestMapping("/addSysUser")
	@ResponseBody
	public Data addSysUser(SysUser user, HttpServletRequest req) {
		Data data = new Data();
		Object obj = req.getSession().getAttribute(LoginAttribute.ATTRIBUTE_USER);
		if (null == obj) {
			data.setCode(0);
			return data;
		}
		SysUser currentUser = (SysUser) obj;
		user.setCreatedBy(currentUser.getUserId());
		user = sysUserService.idDefaultUser(user);
		// user.setRoleId(currentUser.getRoleId());
		int flag = sysUserService.addSysUser(user);
		if (flag > 0) {
			data.setCode(1);
		} else if (flag == -1) {
			data.setCode(-1);
			data.setMsg("mail_same");
		} else {
			data.setCode(0);
		}
		return data;
	}

	/**
	 * 删除用户
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/del")
	@ResponseBody
	public Data del(Integer userId) {
		Data data = new Data();
		try {
			boolean bool = this.sysUserService.dropAccountByMail(userId);
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
	public String show(HttpServletRequest req) {
		Object obj = req.getSession().getAttribute(LoginAttribute.ATTRIBUTE_USER);
		SysUser currentUser = (SysUser) obj;
		req.setAttribute("currentUser", currentUser);
		if (currentUser == null) {
			return "timeout";
		}
		Integer branchId = currentUser.getBranchId();
		List<Branch> braList = null;
		if (branchId != null) {
			List<Hall> hallList = this.hs.getHallList(branchId);
			req.setAttribute("hallList", hallList);
			braList = this.bs.getBranchListByArea(currentUser.getCityCode());
		} else {
			braList = this.bs.getBranchList();
		}
		req.setAttribute("branchList", braList);
		return "sys/user_list";
	}

	@RequestMapping(value = "/getCombo/{type}/{value}")
	@ResponseBody
	public OpResult getCombo(@PathVariable String type, @PathVariable String value) {
		OpResult opResult = new OpResult();
		List<ComboVo> result = null;
		try {
			result = this.sysUserService.getCombo(type, value);
			opResult.setResult(result);
		} catch (Exception e) {
			e.printStackTrace();
			opResult.setResult(result);
			DATA.info(e.getMessage());
		}
		return opResult;
	}
}
