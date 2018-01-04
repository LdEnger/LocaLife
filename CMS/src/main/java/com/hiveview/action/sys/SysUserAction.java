package main.java.com.hiveview.action.sys;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.hiveview.common.ParamConstants;
import com.hiveview.dao.sys.SysUserDao;
import com.hiveview.entity.Branch;
import com.hiveview.entity.Hall;
import com.hiveview.entity.bo.AjaxPage;
import com.hiveview.entity.bo.Data;
import com.hiveview.entity.bo.LoginAttribute;
import com.hiveview.entity.bo.ScriptPage;
import com.hiveview.entity.localLife.ZoneUser;
import com.hiveview.entity.sys.SysUser;
import com.hiveview.entity.sys.SysUserSpecial;
import com.hiveview.entity.sys.Zone;
import com.hiveview.service.BranchService;
import com.hiveview.service.HallService;
import com.hiveview.service.localLife.ZoneUserService;
import com.hiveview.service.sys.SysAuthService;
import com.hiveview.service.sys.SysUserService;
import com.hiveview.service.sys.ZoneCityService;
import com.hiveview.service.sys.ZoneService;

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
	@Autowired
	private SysAuthService sysAuthService;

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
			sysUser.copy(ajaxPage);
			scriptPage = sysUserService.getSysUserList(sysUser,currentUser.getRoleId());
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
			//保存特殊权限相关逻辑
			if(user.getAuthMap()!=null&&!"".equals(user.getAuthMap())){
				SysUserSpecial special =new SysUserSpecial();
				special.setUserId(user.getUserId());
				sysAuthService.deleteAuthSpecialByUserId(special);
				String p [] =user.getAuthMap().split(",");
				for(String pp:p){
					SysUserSpecial sp =buildSpecial(pp,currentUser,user.getUserId());
					sysAuthService.saveAuthSpecial(sp);
				}
			}else{
				SysUserSpecial special =new SysUserSpecial();
				special.setUserId(user.getUserId());
				sysAuthService.deleteAuthSpecialByUserId(special);
			}
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
	
	@RequestMapping("/getSpecialListByUserId")
	public Data getSpecialListByUserId(Integer userId){
		Data data = new Data();
		SysUserSpecial special =new SysUserSpecial();
		special.setUserId(userId);
		List<SysUserSpecial> list = sysAuthService.getAuthSpectialList(special);
		data.setMsg(JSONObject.toJSONString(list));
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
			//保存本地生活相关信息
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
	
	/**
	 * 概述：
	 * 返回值：SysUserSpecial
	 * 作者：徐浩波  xuhaobo@hiveview.com 
	 * 创建日期：2017-1-13下午5:39:10
	 */
	private SysUserSpecial buildSpecial(String pp, SysUser currentUser,Integer user) {
		SysUserSpecial sp=new SysUserSpecial();
		sp.setAuthCode(pp);
		sp.setAuthName(ParamConstants.authMap.get(pp));
		sp.setAuthForm(currentUser.getUserId());
		sp.setUserId(user);
		return sp;
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
		req.setAttribute("daoAuthMap", ParamConstants.authMap);
		return "sys/user_list";
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
}
