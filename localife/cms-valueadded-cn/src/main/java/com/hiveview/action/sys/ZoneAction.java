package com.hiveview.action.sys;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hiveview.entity.bo.AjaxPage;
import com.hiveview.entity.bo.Data;
import com.hiveview.entity.bo.LoginAttribute;
import com.hiveview.entity.bo.ScriptPage;
import com.hiveview.entity.sys.SysUser;
import com.hiveview.entity.sys.Zone;
import com.hiveview.service.sys.ZoneService;

@Controller
@RequestMapping("/zone")
public class ZoneAction {
	
	@Autowired
	ZoneService zoneService;
	
	@RequestMapping(value = "/getAllZone", produces = { "application/json;charset=UTF-8" })
	public List<Zone> getAllZone(){
		return this.zoneService.getAllList();
	}
	
	@RequestMapping(value = "/show")
	public String show(HttpServletRequest req){
		Object obj = req.getSession().getAttribute(LoginAttribute.ATTRIBUTE_USER);
		SysUser currentUser = (SysUser) obj;
		if(currentUser==null){
			return "timeout";
		}
		return "sys/zone";
	}
	
	@RequestMapping(value = "/getList")
	@ResponseBody
	public ScriptPage getZoneList(Zone zone, AjaxPage ajaxPage) {
		ScriptPage scriptPage = null;
		try {
			zone.copy(ajaxPage);
			scriptPage = zoneService.getZoneList(zone);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return scriptPage;
	}
	
	@RequestMapping(value = "/add")
	@ResponseBody
	public Data add(final Zone zone) {
		Data data = new Data();
			try {
				boolean bool = zoneService.add(zone);
				if (bool){
					data.setCode(1);
				}else{
					data.setCode(0);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return data;
		} 
	
	@RequestMapping(value = "/update")
	@ResponseBody
	public Data update(final Zone zone) {
		Data data = new Data();
			try {
				boolean bool = zoneService.update(zone);
				if (bool){
					data.setCode(1);
				}else{
					data.setCode(0);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return data;
		} 
}
