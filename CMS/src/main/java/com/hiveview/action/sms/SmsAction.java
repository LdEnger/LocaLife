package main.java.com.hiveview.action.sms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hiveview.common.ParamConstants;
import com.hiveview.download.MD5;
import com.hiveview.entity.Branch;
import com.hiveview.entity.bo.AjaxPage;
import com.hiveview.entity.bo.Data;
import com.hiveview.entity.bo.LoginAttribute;
import com.hiveview.entity.bo.ScriptPage;
import com.hiveview.entity.sms.SmsConf;
import com.hiveview.entity.sms.SmsRecord;
import com.hiveview.entity.sms.SmsSender;
import com.hiveview.entity.sys.SysUser;
import com.hiveview.service.BranchService;
import com.hiveview.service.sms.SmsService;
import com.hiveview.service.sys.SysUserService;
import com.hiveview.service.sys.ZoneCityService;

/**
 * Title：ActivityAction.java
 * Description：折扣配置
 * Company：hiveview.com
 * Author：haobo56
 * Email：xuhaobo@xuhaobo.cn
 * 2015年11月26日 下午7:34:18
 */
@Controller
@RequestMapping("/sms")
public class SmsAction {
	
	@Autowired
	private SmsService smsService;
	@Autowired
	private ZoneCityService zoneCityService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private BranchService branchService;
	
//	private static final Logger DATA = LoggerFactory.getLogger("data");
	
	@RequestMapping(value = "/getList")
	@ResponseBody
	public ScriptPage getSmsRecordList(AjaxPage ajaxPage,SmsRecord sms,HttpServletRequest req) {
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
		}
		if(!inbranchIds.equals("")){
			inbranchIds =inbranchIds.substring(0,inbranchIds.length()-1);
			sms.setBranchName(inbranchIds);
		}else{
			sms.setBranchName(branchId+"");
		}
		try {
			sms.copy(ajaxPage);
			scriptPage = smsService.getSmsRecordList(sms);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return scriptPage;
	}
	
	
	@RequestMapping(value="/show")
	public String show(HttpServletRequest req){
		Object obj = req.getSession().getAttribute(LoginAttribute.ATTRIBUTE_USER);
		SysUser currentUser = (SysUser) obj;
		if(currentUser==null){
			return "timeout";
		}
//		//战区信息从城市和战区关联表里查询
		currentUser = zoneCityService.setZoneInfo(currentUser);
		req.setAttribute("currentUser", currentUser);
		Integer	branchId= currentUser.getBranchId();
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
//		DATA.info("ActivityDisplayInitSessionParam：userId={},roleId={}", new Object[] {currentUser.getUserId(), currentUser.getRoleId()});
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
		}catch(Exception e){
//			DATA.info("获取计费包异常");
			e.printStackTrace();
		}
		return "sms/smsList";
	}
	/**
	 * 概述：短信配置页面
	 * 返回值：String
	 * 作者：徐浩波  xuhaobo@hiveview.com 
	 * 创建日期：2016-12-7下午11:24:04
	 */
	@RequestMapping(value="/confShow")
	public String confShow(HttpServletRequest req){
		Object obj = req.getSession().getAttribute(LoginAttribute.ATTRIBUTE_USER);
		SysUser currentUser = (SysUser) obj;
		if(currentUser==null){
			return "timeout";
		}
//		//战区信息从城市和战区关联表里查询
		currentUser = zoneCityService.setZoneInfo(currentUser);
		req.setAttribute("currentUser", currentUser);
		int is_god = (int)req.getSession().getAttribute("isGod_User");
		if(ParamConstants.BRANCH_ROLE==currentUser.getRoleId()||is_god==1){
			//分公司权限的人或者神级用户才能改
			req.setAttribute("canedit", 1);
		}else{
			req.setAttribute("canedit", 0);
		}
		List<Branch> branchList =new ArrayList<Branch>();
		branchList =branchService.getBranchList();
		req.setAttribute("branchList", branchList);
		return "sms/smsconfList";
	}
	
	@RequestMapping(value = "/getConfList")
	@ResponseBody
	public ScriptPage getConfList(AjaxPage ajaxPage,HttpServletRequest req,SmsConf conf) {
		ScriptPage scriptPage = null;
		Object obj = req.getSession().getAttribute(LoginAttribute.ATTRIBUTE_USER);
		SysUser currentUser = (SysUser) obj;
		Integer	branchId= currentUser.getBranchId();
		SmsConf sms =new SmsConf();
		if (ParamConstants.BRANCH_ROLE == currentUser.getRoleId()) {
			// 分公司管理用户
			sms.setText1(branchId+"");
		}else{
			//其他用户根据查询条件获得分公司ID
			sms.setText1(conf.getBranchId()+"");
		}
		try {
			sms.copy(ajaxPage);
			scriptPage = smsService.getSmsConfList(sms);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return scriptPage;
	}
	/**
	 * 概述：添加短信配置
	 * 返回值：Data
	 * 作者：徐浩波  xuhaobo@hiveview.com 
	 * 创建日期：2016-12-7下午11:57:49
	 */
	@RequestMapping(value = "/daoConf")
	@ResponseBody
	public Data daoConf(final SmsConf conf,HttpServletRequest req) {
		Object obj = req.getSession().getAttribute(LoginAttribute.ATTRIBUTE_USER);
		SysUser currentUser = (SysUser) obj;
		Integer	updateBy= currentUser.getUserId();
		Data data = new Data();
		conf.setUpdateBy(updateBy);
		int id =conf.getId();
		int flg =0;
		if (id==-1) {//新增
			flg = smsService.addSmsConf(conf);
		}else{
			flg = smsService.updateSmsConf(conf);
		}
		if (flg==0) {
			data.setCode(0);
			data.setMsg("更新失败");
			return data;
		}
		return data;
	}
	
	/**
	 * 概述：发短信配置
	 * 返回值：String
	 * 作者：徐浩波  xuhaobo@hiveview.com 
	 * 创建日期：2017-2-13下午5:06:11
	 */
	
	@RequestMapping(value="/showSender")
	public String showSender(HttpServletRequest req){
		Object obj = req.getSession().getAttribute(LoginAttribute.ATTRIBUTE_USER);
		SysUser currentUser = (SysUser) obj;
		if(currentUser==null){
			return "timeout";
		}
		if (ParamConstants.ZONE_ROLE == currentUser.getRoleId()) { 
			return "exception";
		}
		List<Branch> branchList =new ArrayList<Branch>();
		branchList =branchService.getBranchList();
		req.setAttribute("branchList", branchList);
		return "sms/smsSender";
	}
	@RequestMapping(value = "/getSenderList")
	@ResponseBody
	public ScriptPage getSenderList(AjaxPage ajaxPage,HttpServletRequest req,SmsSender sender) {
		ScriptPage scriptPage = null;
		try {
			sender.copy(ajaxPage);
			scriptPage = smsService.getSmsSenderList(sender);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return scriptPage;
	}
	@RequestMapping(value = "/testSender")
	@ResponseBody
	public Data testSender(final SmsSender conf,HttpServletRequest req) {
		int flg =smsService.testSender(conf);
		Data data =new Data();
		data.setCode(flg);
		if (flg==0) {
			data.setCode(0);
			data.setMsg("更新失败");
			return data;
		}
		return data;
	}
	
	@RequestMapping(value = "/addSender")
	@ResponseBody
	public Data addSender(final SmsSender conf,HttpServletRequest req) {
		Object obj = req.getSession().getAttribute(LoginAttribute.ATTRIBUTE_USER);
		SysUser currentUser = (SysUser) obj;
		Integer userId =currentUser.getUserId();
		String pwd =conf.getPwd();
		String md5 =MD5.md5(pwd);
		conf.setMd5(md5);
		conf.setUpdateBy(userId);
		int flg =smsService.saveSmsSender(conf);
		Data data =new Data();
		if (flg==0) {
			data.setCode(0);
			data.setMsg("添加失败");
			return data;
		}
		return data;
	}
	@RequestMapping(value = "/updateSender")
	@ResponseBody
	public Data updateSender(final SmsSender conf,HttpServletRequest req) {
		Object obj = req.getSession().getAttribute(LoginAttribute.ATTRIBUTE_USER);
		SysUser currentUser = (SysUser) obj;
		Integer userId =currentUser.getUserId();
		String pwd =conf.getPwd();
		String md5 =MD5.md5(pwd);
		conf.setMd5(md5);
		conf.setUpdateBy(userId);
		int flg =smsService.updateSmsSender(conf);
		Data data =new Data();
		if (flg==0) {
			data.setCode(0);
			data.setMsg("更新失败");
			return data;
		}
		return data;
	}
	
}
