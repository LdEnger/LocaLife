package main.java.com.hiveview.action.agio;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hiveview.common.ParamConstants;
import com.hiveview.entity.Branch;
import com.hiveview.entity.activity.Activity;
import com.hiveview.entity.agio.AgioPackage;
import com.hiveview.entity.agio.AgioPackageBatch;
import com.hiveview.entity.agio.AgioPackageConf;
import com.hiveview.entity.agio.AgioPackagePool;
import com.hiveview.entity.agio.AgioPackageView;
import com.hiveview.entity.bo.AjaxPage;
import com.hiveview.entity.bo.Data;
import com.hiveview.entity.bo.LoginAttribute;
import com.hiveview.entity.bo.ScriptPage;
import com.hiveview.entity.sys.SysUser;
import com.hiveview.entity.sys.Zone;
import com.hiveview.service.BranchService;
import com.hiveview.service.agio.AgioPackageConfService;
import com.hiveview.service.card.CardService;
import com.hiveview.service.sys.SysUserService;
import com.hiveview.service.sys.ZoneCityService;
import com.hiveview.service.sys.ZoneService;

/**
 * Title：折扣配置
 * Description：
 * Company：hiveview.com
 * Author：徐浩波
 * Email：xuhaobo@hiveview.com 
 * 2016-12-12上午10:01:42
 */
@Controller
@RequestMapping("/agiopackage")
public class AgioPackageConfAction {
	
	@Autowired
	private AgioPackageConfService agioPackageConfService;
	@Autowired
	private ZoneCityService zoneCityService;
	
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private ZoneService zoneService;
	@Autowired
	private BranchService branchService;
	@Autowired
	private CardService cardService;
//	private static final Logger DATA = LoggerFactory.getLogger("data");
	
	@RequestMapping(value = "/getList")
	@ResponseBody
	public ScriptPage getActivityList(AjaxPage ajaxPage,AgioPackageConf conf,HttpServletRequest req) {
		ScriptPage scriptPage = null;
		Object obj = req.getSession().getAttribute(LoginAttribute.ATTRIBUTE_USER);
		SysUser currentUser = (SysUser) obj;
		Integer roleId = currentUser.getRoleId();
		try {
			conf.copy(ajaxPage);
			scriptPage = agioPackageConfService.getAgioPackageConfList(conf,roleId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return scriptPage;
	}
	
	@RequestMapping(value = "/getPoolList")
	@ResponseBody
	public ScriptPage getPoolList(AjaxPage ajaxPage,AgioPackagePool pool,HttpServletRequest req) {
		ScriptPage scriptPage = null;
		try {
			pool.copy(ajaxPage);
			scriptPage = agioPackageConfService.getPoolList(pool);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return scriptPage;
	}
	
	@RequestMapping(value = "/getViewList")
	@ResponseBody
	public ScriptPage getViewList(AjaxPage ajaxPage,AgioPackageView view,HttpServletRequest req) {
		ScriptPage scriptPage = null;
		Object obj = req.getSession().getAttribute(LoginAttribute.ATTRIBUTE_USER);
		SysUser currentUser = (SysUser) obj;
		Integer zoneId = currentUser.getZoneId();
		Integer	branchId= currentUser.getBranchId();
		List<Branch> braList = null;
		String inbranchIds ="";
		if (ParamConstants.ZONE_ROLE == currentUser.getRoleId()) { // 战区管理员
			braList = zoneCityService.getBranchByZone(zoneId);
			if(braList!=null){
				for(Branch b:braList){
					inbranchIds +=b.getId()+",";
				}
			}
			if(!inbranchIds.equals("")){
				inbranchIds =inbranchIds.substring(0,inbranchIds.length()-1);
				view.setQueryBranchIds(inbranchIds);
			}else{
				if(branchId.equals(-1)){
					view.setQueryBranchIds("-2");
				}
			}
		}else if(ParamConstants.BRANCH_ROLE == currentUser.getRoleId()||ParamConstants.HALL_ROLE == currentUser.getRoleId()){
			view.setQueryBranchIds(branchId+"");
		}
		try {
			view.copy(ajaxPage);
			scriptPage = agioPackageConfService.getViewList(view);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return scriptPage;
	}
	
	@RequestMapping(value = "/getAgioList")
	@ResponseBody
	public ScriptPage getAgioList(AjaxPage ajaxPage,AgioPackage pkg,HttpServletRequest req) {
		ScriptPage scriptPage = null;
		Object obj = req.getSession().getAttribute(LoginAttribute.ATTRIBUTE_USER);
		SysUser currentUser = (SysUser) obj;
		if(ParamConstants.ZONE_ROLE == currentUser.getRoleId()){
			pkg.setZoneId(currentUser.getZoneId());
		}
		try {
			pkg.copy(ajaxPage);
			scriptPage = agioPackageConfService.getAgioList(pkg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return scriptPage;
	}
	@RequestMapping(value = "/getAgioBatchList")
	@ResponseBody
	public ScriptPage getAgioBatchList(AjaxPage ajaxPage,AgioPackageBatch batch,HttpServletRequest req) {
		ScriptPage scriptPage = null;
		Object obj = req.getSession().getAttribute(LoginAttribute.ATTRIBUTE_USER);
		SysUser currentUser = (SysUser) obj;
		if(ParamConstants.ZONE_ROLE == currentUser.getRoleId()){
			batch.setZoneId(currentUser.getZoneId());
		}
		try {
			batch.copy(ajaxPage);
			scriptPage = agioPackageConfService.getAgioBatchList(batch);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return scriptPage;
	}
	@RequestMapping(value = "/agioBatchAdd")
	@ResponseBody
	public Data agioBatchAdd(final AgioPackageBatch batch,HttpServletRequest req) {
		Object obj = req.getSession().getAttribute(LoginAttribute.ATTRIBUTE_USER);
		SysUser currentUser = (SysUser) obj;
		batch.setZoneId(currentUser.getZoneId());
		batch.setBranchId(currentUser.getBranchId());
		batch.setHallId(currentUser.getHallId());
		batch.setOpUserInfo(currentUser.getUserMail());
		batch.setState(0);
		batch.setText("上传成功！");
		Data data = new Data();
			try {
				boolean bool = agioPackageConfService.addAgioBatch(batch);
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
	
	@RequestMapping(value = "/agioAdd")
	@ResponseBody
	public Data agioAdd(final AgioPackage pkg,HttpServletRequest req) {
		Object obj = req.getSession().getAttribute(LoginAttribute.ATTRIBUTE_USER);
		SysUser currentUser = (SysUser) obj;
		Integer userId =currentUser.getUserId();
		pkg.setUpdateBy(userId);
		pkg.setCreateBy(userId);
		pkg.setZoneId(currentUser.getZoneId());
		pkg.setBranchId(currentUser.getBranchId());
		pkg.setCreateTime(new Date());
		Data data = new Data();
			try {
				boolean bool = agioPackageConfService.addPkg(pkg);
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
	
	@RequestMapping(value = "/agioUpdate")
	@ResponseBody
	public Data agioUpdate(final AgioPackage pkg,HttpServletRequest req) {
		Object obj = req.getSession().getAttribute(LoginAttribute.ATTRIBUTE_USER);
		SysUser currentUser = (SysUser) obj;
		Integer userId =currentUser.getUserId();
		pkg.setUpdateBy(userId);
		Data data = new Data();
			try {
				boolean bool = agioPackageConfService.updatePkg(pkg);
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
	
	@RequestMapping(value = "/poolAdd")
	@ResponseBody
	public Data poolAdd(final AgioPackagePool pool) {
		Data data = new Data();
			try {
				String bool = agioPackageConfService.addPool(pool);
				if ("".equals(bool)){
					data.setCode(1);
				}else{
					data.setCode(0);
					data.setMsg(bool);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return data;
		} 
	
	
	
	@RequestMapping(value = "/add")
	@ResponseBody
	public Data add(final AgioPackageConf conf) {
		Data data = new Data();
			try {
				String bool = agioPackageConfService.add(conf);
				if ("".equals(bool)){
					data.setCode(1);
				}else{
					data.setCode(0);
					data.setMsg(bool);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return data;
		} 
	
	
	@RequestMapping(value = "/update")
	@ResponseBody
	public Data update(final AgioPackageConf conf) {
		Data data = new Data();
			try {
				String bool = agioPackageConfService.update(conf);
				if ("".equals(bool)){
					data.setCode(1);
				}else{
					data.setCode(0);
					data.setMsg(bool);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return data;
		} 
	
	
	@RequestMapping(value="/show")
	public String show(HttpServletRequest req){
		Object obj = req.getSession().getAttribute(LoginAttribute.ATTRIBUTE_USER);
		SysUser currentUser = (SysUser) obj;
		if(currentUser==null){
			return "timeout";
		}
		return "agio/agioconflist";
	}
	@RequestMapping(value="/poolShow")
	public String poolShow(HttpServletRequest req){
		Object obj = req.getSession().getAttribute(LoginAttribute.ATTRIBUTE_USER);
		SysUser currentUser = (SysUser) obj;
		if(currentUser==null){
			return "timeout";
		}
		//战区信息从城市和战区关联表里查询
		currentUser = zoneCityService.setZoneInfo(currentUser);
		Integer	branchId= currentUser.getBranchId();
		req.setAttribute("currentUser", currentUser);
//		Integer	branchId= currentUser.getBranchId();
		List<Branch> braList = null;
		String inbranchIds ="";
		Integer zoneId = currentUser.getZoneId();
		if (ParamConstants.ZONE_ROLE == currentUser.getRoleId()) { // 战区管理员
			braList = zoneCityService.getBranchByZone(zoneId);
			if(braList!=null){
				for(Branch b:braList){
					inbranchIds +=b.getId()+",";
				}
			}
		}
		try{
			Map<String, String> map =new HashMap<String, String>();
			
			if(!inbranchIds.equals("")){
				inbranchIds =inbranchIds.substring(0,inbranchIds.length()-1);
				map.put("branchs", inbranchIds);
			}else{
				map.put("branchs", branchId+"");
			}
			List<SysUser> list = sysUserService.getUserByBranchIds(map);
			req.setAttribute("userList",list);
			List<Zone> zoneList=this.zoneService.getAllList();
			req.setAttribute("zoneList", zoneList);
			AgioPackageConf conf =new AgioPackageConf();
			conf.setPageSize(10000);
			conf.setPageNo(1);
			conf.setState("1");
			ScriptPage scriptPage = agioPackageConfService.getAgioPackageConfList(conf,1);
			req.setAttribute("agioList", scriptPage.getRows());
			List<Branch> branchList =branchService.getBranchList();
			req.setAttribute("branchList", branchList);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "agio/agiopoollist";
	}
	@RequestMapping(value="/viewShow")
	public String viewShow(HttpServletRequest req){
		Object obj = req.getSession().getAttribute(LoginAttribute.ATTRIBUTE_USER);
		SysUser currentUser = (SysUser) obj;
		if(currentUser==null){
			return "timeout";
		}
		AgioPackageConf conf =new AgioPackageConf();
		conf.setPageSize(10000);
		conf.setPageNo(1);
		ScriptPage scriptPage = agioPackageConfService.getAgioPackageConfList(conf,1);
		req.setAttribute("agioList", scriptPage.getRows());
		List<Branch> branchList =branchService.getBranchList();
		req.setAttribute("branchList", branchList);
		return "agio/agioviewlist";
	}
	@RequestMapping(value="/agioShow")
	public String agioShow(HttpServletRequest req){
		Object obj = req.getSession().getAttribute(LoginAttribute.ATTRIBUTE_USER);
		SysUser currentUser = (SysUser) obj;
		if(currentUser==null){
			return "timeout";
		}
		try {
			currentUser = zoneCityService.setZoneInfo(currentUser);
			Integer	branchId= currentUser.getBranchId();
			req.setAttribute("currentUser", currentUser);
			List<Branch> braList = null;
			String inbranchIds ="";
			Integer zoneId = currentUser.getZoneId();
			if (ParamConstants.ZONE_ROLE == currentUser.getRoleId()) { // 战区管理员
				braList = zoneCityService.getBranchByZone(zoneId);
				if(braList!=null){
					for(Branch b:braList){
						inbranchIds +=b.getId()+",";
					}
				}
			}
			Map<String, String> map =new HashMap<String, String>();
			
			if(!inbranchIds.equals("")){
				inbranchIds =inbranchIds.substring(0,inbranchIds.length()-1);
				map.put("branchs", inbranchIds);
			}else{
				map.put("branchs", branchId+"");
			}
			List<SysUser> list = sysUserService.getUserByBranchIds(map);
			req.setAttribute("userList",list);
			List<Zone> zoneList=this.zoneService.getAllList();
			req.setAttribute("zoneList", zoneList);
			AgioPackageConf conf =new AgioPackageConf();
			conf.setPageSize(10000);
			conf.setPageNo(1);
			ScriptPage scriptPage = agioPackageConfService.getAgioPackageConfList(conf,1);
			
			AgioPackageView query =new AgioPackageView();
			if (ParamConstants.BRANCH_ROLE == currentUser.getRoleId()){
				query.setQueryBranchIds(branchId+"");
			}else{
				query.setQueryBranchIds("-2");
			}
			query.setPageSize(100000);
			query.setPageNo(1);
			ScriptPage viewPage =agioPackageConfService.getViewList(query);
			List<AgioPackageConf> agioList =new ArrayList<AgioPackageConf>();
			@SuppressWarnings("unchecked")
			List<AgioPackageConf> confList =scriptPage.getRows();
			@SuppressWarnings("unchecked")
			List<AgioPackageView> viewList =viewPage.getRows();
			// 过滤，使添加麦币包时只找有对应充值的折扣
			for(AgioPackageConf cf:confList){
				for(AgioPackageView view:viewList){
					if(view.getPackageConfId()==cf.getId()){
						agioList.add(cf);
					}
				}
			}
			req.setAttribute("agioList", agioList);
			List<Branch> branchList =new ArrayList<Branch>();
			if(ParamConstants.GROUP_ROLE != currentUser.getRoleId()){
				//非集团管理员
				branchList =zoneCityService.getBranchByZone(zoneId);
			}else{
				//集团管理员
				branchList =branchService.getBranchList();
			}
			req.setAttribute("branchList", branchList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "agio/agiolist";
	}
	@RequestMapping(value="/agioBacthShow")
	public String agioBatchShow(HttpServletRequest req){
		Object obj = req.getSession().getAttribute(LoginAttribute.ATTRIBUTE_USER);
		SysUser currentUser = (SysUser) obj;
		if(currentUser==null){
			return "timeout";
		}
		try {
			currentUser = zoneCityService.setZoneInfo(currentUser);
			Integer	branchId= currentUser.getBranchId();
			req.setAttribute("currentUser", currentUser);
			List<Branch> braList = null;
			String inbranchIds ="";
			Integer zoneId = currentUser.getZoneId();
			if (ParamConstants.ZONE_ROLE == currentUser.getRoleId()) { // 战区管理员
				braList = zoneCityService.getBranchByZone(zoneId);
				if(braList!=null){
					for(Branch b:braList){
						inbranchIds +=b.getId()+",";
					}
				}
			}
			Map<String, String> map =new HashMap<String, String>();
			
			if(!inbranchIds.equals("")){
				inbranchIds =inbranchIds.substring(0,inbranchIds.length()-1);
				map.put("branchs", inbranchIds);
			}else{
				map.put("branchs", branchId+"");
			}
			List<SysUser> list = sysUserService.getUserByBranchIds(map);
			req.setAttribute("userList",list);
			List<Zone> zoneList=this.zoneService.getAllList();
			req.setAttribute("zoneList", zoneList);
			Integer hallId = currentUser.getHallId();
			List<Activity> actList = cardService.getBackAgioList(currentUser.getRoleId(), zoneId, branchId, hallId); //
			req.setAttribute("agioList", actList);
			List<Branch> branchList =new ArrayList<Branch>();
			if(ParamConstants.GROUP_ROLE != currentUser.getRoleId()){
				//非集团管理员
				branchList =zoneCityService.getBranchByZone(zoneId);
			}else{
				//集团管理员
				branchList =branchService.getBranchList();
			}
			req.setAttribute("branchList", branchList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "agio/agiobacthlist";
	}
}
