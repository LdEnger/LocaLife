/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package main.java.com.hiveview.interceptor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.hiveview.common.ParamConstants;
import com.hiveview.entity.sys.SysAuth;
import com.hiveview.entity.sys.SysUserSpecial;
import com.hiveview.service.shiro.AuthenticationToken;
import com.hiveview.service.shiro.Principal;
import com.hiveview.service.shiro.RSAService;
import com.hiveview.service.sys.SysAuthService;

/**
 * shiro
 * @author hiveview
 *
 */
public class AuthenticationFilter extends FormAuthenticationFilter {
	@Autowired
	RSAService rsaService;
	
	@Autowired
	SysAuthService sysAuthService;

	/** 默认"加密密码"参数名称 */
	private static final String DEFAULT_EN_PASSWORD_PARAM = "enPassword";

	/** 默认"验证码"参数名称 */
	private static final String DEFAULT_KAPTCHA_PARAM = "kaptcha";

	/** "加密密码"参数名称 */
	private String enPasswordParam = DEFAULT_EN_PASSWORD_PARAM;

	/** "验证码"参数名称 */
	private String kaptcha = DEFAULT_KAPTCHA_PARAM;
	
	@Override
	protected org.apache.shiro.authc.AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) {
		String username = getUsername(servletRequest);
		String password = getPassword(servletRequest);
		String kaptcha = getKaptcha(servletRequest);
		boolean rememberMe = isRememberMe(servletRequest);
		String host = getHost(servletRequest);
		return new AuthenticationToken(username, password, kaptcha, rememberMe, host);
	}

	@Override
	protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		String requestType = request.getHeader("X-Requested-With");
		if (requestType != null && requestType.equalsIgnoreCase("XMLHttpRequest")) {
			response.addHeader("loginStatus", "accessDenied");
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
			return false;
		}
		return super.onAccessDenied(request, response);
	}

	@Override
	protected boolean onLoginSuccess(org.apache.shiro.authc.AuthenticationToken token, Subject subject, ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
		Session session = subject.getSession();
		Map<Object, Object> attributes = new HashMap<Object, Object>();
		Collection<Object> keys = session.getAttributeKeys();
		for (Object key : keys) {
			attributes.put(key, session.getAttribute(key));
		}
		session.stop();
		session = subject.getSession();
		for (Entry<Object, Object> entry : attributes.entrySet()) {
			session.setAttribute(entry.getKey(), entry.getValue());
		}
		Principal p = (Principal)subject.getPrincipal();
		Integer roleId = p.getRoleId();
		HttpServletRequest request = (HttpServletRequest)servletRequest;
		HttpSession hsession = request.getSession();
		hsession.setAttribute("roleId", roleId);
		//add by xuhaobo 区分是分公司用户（包含营业厅用户），还是战区用户或集团管理员用户
		// 只有isBranchUser 才能制卡，可以添加活动包或者麦币包。可以对卡进行激活等操作
		// 非isBranchUser用户之可以查
		if(roleId.equals(ParamConstants.BRANCH_ROLE)||roleId.equals(ParamConstants.HALL_ROLE)){
			hsession.setAttribute("isBranchUser", 1);
		}else{
			hsession.setAttribute("isBranchUser", 0);
		}
		SysUserSpecial special =new SysUserSpecial();
		special.setUserId(p.getId());
		List<SysUserSpecial> specialList =sysAuthService.getAuthSpectialList(special);
		//神级用户，掌管一切权限，但是不一定有所以操作---------------------
		int isGod =0;
		if(specialList!=null){
			for(SysUserSpecial l:specialList){
				if(l.getAuthCode().equals("isGod")){
					isGod =1;
					specialList.remove(l);
					break;
				}
			}
		}
		hsession.setAttribute("isGod_User", isGod);
		//神级用户结束-------------------------------------
		List<SysAuth> authes = sysAuthService.getLeftAuth(roleId);
		List<SysAuth> topMenues = new ArrayList<SysAuth>();
		HashMap<Integer, List<SysAuth>> menuMap = new HashMap<Integer, List<SysAuth>>();
		for (SysAuth sa : authes) {
			if (sa.getPid() == 0) {
				topMenues.add(sa);
			} else {
				if (!menuMap.containsKey(sa.getPid())) {
					menuMap.put(sa.getPid(), new ArrayList<SysAuth>());
				}
				if(canAddMenu(specialList, sa)){
					menuMap.get(sa.getPid()).add(sa);
				}
				
			}
		}
		hsession.setAttribute("topMenues", topMenues);
		hsession.setAttribute("menuMap", menuMap);
		
		return super.onLoginSuccess(token, subject, servletRequest, servletResponse);
	}
	
	private boolean canAddMenu(List<SysUserSpecial> specialList,SysAuth sa){
		 Map<String, String> specialMap =ParamConstants.authMap;
		 Set<String> specialSet =specialMap.keySet();
		 boolean isSpecialAuth =false;
		 //先判断是不是特殊权限
		 for(String specialKey:specialSet){
			 if(sa.getAuthAction().equals(specialKey)){
				 isSpecialAuth =true;
				 break;
			 }
		 }
		 if(isSpecialAuth){
			 //若是特殊权限，判断当前人员是否有该权限
			 if(specialList==null){
				 specialList =new ArrayList<SysUserSpecial>();
			 }
			 boolean haveSpecialAuth =false;
			 for(SysUserSpecial special:specialList){
				 if(special.getAuthCode().equals(sa.getAuthAction())){
					 haveSpecialAuth =true;
					 break;
				 }
			 }
			 //当前人没有特殊权限，则不将菜单添加进去
			 if(!haveSpecialAuth){
				 return false;
			 }
		 }
		 
		 
		return true;
	}
	@Override
	protected String getPassword(ServletRequest servletRequest) {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		String password = rsaService.decryptParameter(enPasswordParam, request);
		rsaService.removePrivateKey(request);
		//String password = "";
		return password;
	}

	/**
	 * 获取验证码
	 * 
	 * @param servletRequest
	 *            ServletRequest
	 * @return 验证码
	 */
	protected String getKaptcha(ServletRequest servletRequest) {
		return WebUtils.getCleanParam(servletRequest, kaptcha);
	}

	/**
	 * 获取"加密密码"参数名称
	 * 
	 * @return "加密密码"参数名称
	 */
	public String getEnPasswordParam() {
		return enPasswordParam;
	}

	/**
	 * 设置"加密密码"参数名称
	 * 
	 * @param enPasswordParam
	 *            "加密密码"参数名称
	 */
	public void setEnPasswordParam(String enPasswordParam) {
		this.enPasswordParam = enPasswordParam;
	}

	public String getKaptcha() {
		return kaptcha;
	}

	public void setKaptcha(String kaptcha) {
		this.kaptcha = kaptcha;
	}

}