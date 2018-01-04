/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package com.hiveview.interceptor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hiveview.entity.localLife.AreaInfo;
import com.hiveview.entity.sys.*;
import com.hiveview.service.sys.*;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.hiveview.service.shiro.AuthenticationToken;
import com.hiveview.service.shiro.Principal;
import com.hiveview.service.shiro.RSAService;

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

    @Autowired
    SysUserService sysUserService;
    @Autowired
    ZoneService zoneService;
    @Autowired
    ZoneCityService zoneCityService;
    @Autowired
    ZoneUserService zoneUserService;
    @Autowired
    ZoneTreeService zoneTreeService;

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
        //---------------------城市权限----start---------------------
        SysUser sysUser = sysUserService.getSysUserById(p.getId());
        if(1 == roleId){
            AreaInfo areaInfo = new AreaInfo();
            areaInfo.setLevel(1);
            List<AreaInfo> zoneCountryList = zoneTreeService.getZoneTreeByUserId(areaInfo);
            hsession.setAttribute("zoneCountryList", zoneCountryList);
            areaInfo.setLevel(2);
            List<AreaInfo> zoneProvinceList = zoneTreeService.getZoneTreeByUserId(areaInfo);
            hsession.setAttribute("zoneProvinceList", zoneProvinceList);
            areaInfo.setLevel(3);
            List<AreaInfo> zoneCityList = zoneTreeService.getZoneTreeByUserId(areaInfo);
            hsession.setAttribute("zoneCityList", zoneCityList);
        }
        if(5 == roleId){
            //第一级
            AreaInfo areaInfo = new AreaInfo();
            areaInfo.setLevel(1);
            areaInfo.setUserId(p.getId());
            List<AreaInfo> zoneCountryList = zoneTreeService.getZoneTreeByUserId(areaInfo);
            hsession.setAttribute("zoneCountryList", zoneCountryList);
            //第二级
            areaInfo.setLevel(2);
            areaInfo.setUserId(p.getId());
            List<AreaInfo> zoneProvinceList = zoneTreeService.getZoneTreeByUserId(areaInfo);
            hsession.setAttribute("zoneProvinceList", zoneProvinceList);
            //第三级
            areaInfo.setLevel(3);
            areaInfo.setUserId(p.getId());
            List<AreaInfo> zoneCityList = zoneTreeService.getZoneTreeByUserId(areaInfo);
            hsession.setAttribute("zoneCityList", zoneCityList);
        }
        //---------------------城市权限----end---------------------
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
				menuMap.get(sa.getPid()).add(sa);
			}
		}
		hsession.setAttribute("topMenues", topMenues);
		hsession.setAttribute("menuMap", menuMap);
		
		return super.onLoginSuccess(token, subject, servletRequest, servletResponse);
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