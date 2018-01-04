/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package main.java.com.hiveview.service.shiro;

import java.util.concurrent.atomic.AtomicInteger;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.pam.UnsupportedTokenException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.hiveview.entity.sys.SysUser;
import com.hiveview.service.sys.SysAuthService;
import com.hiveview.service.sys.SysUserService;

/**
 * 权限认证
 * @author hiveview
 *
 */
public class AuthenticationRealm extends AuthorizingRealm {

//	@Resource(name = "captchaServiceImpl")
//	private CaptchaService captchaService;

	@Autowired
	private SysUserService sysUserService;

	@Autowired
	SysAuthService sysAuthService;

	private Ehcache passwordRetryCache;
	
	public AuthenticationRealm() {
		CacheManager cacheManager = CacheManager.newInstance(CacheManager.class.getClassLoader().getResource("ehcache.xml"));
		passwordRetryCache = cacheManager.getCache("passwordRetryCache");
	}
	
	/**
	 * 获取认证信息
	 * 
	 * @param token
	 *            令牌
	 * @return 认证信息
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(org.apache.shiro.authc.AuthenticationToken token) {
		AuthenticationToken authenticationToken = (AuthenticationToken) token;

		String username = authenticationToken.getUsername();
		char[] pwdChars = authenticationToken.getPassword();

		if (username == null || pwdChars == null)
			throw new UnknownAccountException();
		
		String password = new String(authenticationToken.getPassword());
		Element element = passwordRetryCache.get(username);
		if (element == null) {
			element = new Element(username, new AtomicInteger(0));
			passwordRetryCache.put(element);
		}
		
		AtomicInteger retryCount = (AtomicInteger)element.getObjectValue();
		int rc = retryCount.incrementAndGet();
		//访问错误次数达到4次时开启验证码
		Session session = SecurityUtils.getSubject().getSession();
		if (rc >= 4) {
			session.setAttribute("kaptchaEbabled", true);
		}
		
		//大于5次时 校验验证码
		if (rc >= 5){
			String kaptcha = authenticationToken.getKaptcha();
			Object kaptchaValue = session.getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
			//验证码错误
			if (StringUtils.isEmpty(kaptcha) || !kaptcha.equals(kaptchaValue.toString()))
				throw new UnsupportedTokenException();
		}

		SysUser user = new SysUser();
		user.setUserMail(username.trim());
		user.setUserPwd(password);
		SysUser currentUser = sysUserService.getLoginInfo(user);
		//用户名或密码错误
		if (currentUser == null || currentUser.getUserId() == -1)
			throw new UnknownAccountException();
		
		session.setAttribute("currentUser", currentUser);
		passwordRetryCache.remove(username);
		session.setAttribute("kaptchaEbabled", false);
		
		return new SimpleAuthenticationInfo(new Principal(currentUser.getUserId(), username, currentUser.getRoleId()), password, getName());
		
	}

	/**
	 * 获取授权信息
	 * 
	 * @param principals
	 *            principals
	 * @return 授权信息
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		Principal principal = (Principal) principals.fromRealm(getName()).iterator().next();
		if (principal != null) {
			Integer roleId = principal.getRoleId();
			SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
			authorizationInfo.addStringPermission(roleId + "");
			return authorizationInfo;
		}
		return null;
	}

}