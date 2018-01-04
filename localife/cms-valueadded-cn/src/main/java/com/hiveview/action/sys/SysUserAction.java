package com.hiveview.action.sys;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.hiveview.entity.sys.ZoneUser;
import com.hiveview.service.sys.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hiveview.dao.sys.SysUserDao;
import com.hiveview.entity.Branch;
import com.hiveview.entity.Hall;
import com.hiveview.entity.bo.AjaxPage;
import com.hiveview.entity.bo.Data;
import com.hiveview.entity.bo.LoginAttribute;
import com.hiveview.entity.bo.ScriptPage;
import com.hiveview.entity.sys.SysUser;
import com.hiveview.entity.sys.Zone;
import com.hiveview.service.BranchService;
import com.hiveview.service.HallService;

@Controller
@RequestMapping("/sysUser")
public class SysUserAction {
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUserDao sysUserDao;
	@Autowired
	private BranchService branchService;
	@Autowired
	private HallService hallService;
	@Autowired
	private ZoneService zoneService;
	@Autowired
	private ZoneCityService zoneCityService;
    @Autowired
    private ZoneUserService zoneUserService;

	@RequestMapping(value = "/getList")
	@ResponseBody
	public ScriptPage getSysUserList(AjaxPage ajaxPage,SysUser sysUser,HttpServletRequest req) {
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
		int flag = sysUserService.updateSysUser(user);
		if (flag > 0) {
			data.setCode(1);
            if(!StringUtils.isBlank(user.getZoneTreeIds())){
                this.addZoneTreeByUserId(user,"update");
            }
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
		int flag = sysUserService.addSysUser(user);
		if (flag > 0) {
			data.setCode(1);
            if(!StringUtils.isBlank(user.getZoneTreeIds())){
                this.addZoneTreeByUserId(user,"add");
            }
		} else if (flag == -1) {
			data.setCode(-1);
			data.setMsg("mail_same");
		} else {
			data.setCode(0);
		}
		return data;
	}

    public void addZoneTreeByUserId(SysUser sysUser,String type){
        String authIdArray[] = sysUser.getZoneTreeIds().split(",");
        List<ZoneUser> zoneUserList = new ArrayList<ZoneUser>();
        for (int authIdIndex = 0; authIdIndex < authIdArray.length; authIdIndex++) {
            ZoneUser ra = new ZoneUser();
            ra.setUserId(sysUser.getUserId());
            ra.setZoneId(new Integer(authIdArray[authIdIndex]));
            zoneUserList.add(ra);
        }
        /*** 获得要添加的权限 end ***/
        if("add".equals(type)){
            for (ZoneUser zoneUser : zoneUserList) {
                zoneUserService.add(zoneUser);
            }
        }
        if("update".equals(type)){
                zoneUserService.delete(sysUser.getUserId());
            for (ZoneUser zoneUser : zoneUserList) {
                zoneUserService.add(zoneUser);
            }
        }

    }
	
	/**
	 * 删除用户
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
		if(currentUser==null){
			return "timeout";
		}
		currentUser = zoneCityService.setZoneInfo(currentUser);
		Integer zoneId = currentUser.getZoneId();
		List<Branch> braList=null;
		if(zoneId!=-1){ //不是集团管理员
			braList = zoneCityService.getBranchByZone(zoneId);
		}else{
			braList=this.branchService.getBranchList();
		}
		Integer branchId= currentUser.getBranchId();
		if(branchId!=-1){//不是集团管理员或战区管理员
			List<Hall> hallList=this.hallService.getHallList(branchId);
			req.setAttribute("hallList", hallList);
			braList = this.branchService.getBranchListByArea(currentUser.getCityCode());
		}
		List<Zone> zoneList=this.zoneService.getAllList();
		req.setAttribute("zoneList", zoneList);
		req.setAttribute("branchList", braList);
		return "sys/user_list";
	}
}
