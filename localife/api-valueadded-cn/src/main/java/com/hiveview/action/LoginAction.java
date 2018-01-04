package com.hiveview.action;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.hiveview.entity.sys.SysUser;
import com.hiveview.service.shiro.RSAService;
import com.hiveview.service.sys.SysAuthService;
import com.hiveview.service.sys.SysUserService;

@Controller
@RequestMapping("")
public class LoginAction {

	@Autowired
	SysUserService sysUserService;

	@Autowired
	SysAuthService sysAuthService;

	@Autowired
	RSAService rsaService;

	@RequestMapping(value = "/plogin")
	public String plogin(HttpServletRequest req, SysUser sysUser) {
		return "redirect:http://api.valueadded.global.domybox.com/api/localLife/show.html";
        //return "redirect:http://127.0.0.1:8080";
		/*
		 * Subject currentUser = SecurityUtils.getSubject(); boolean b =
		 * currentUser.isAuthenticated(); SysUser user =
		 * (SysUser)currentUser.getSession().getAttribute("currentUser"); if
		 * (b&&user!=null) { return "index"; }else{ RSAPublicKey publicKey =
		 * rsaService.generateKey(req); String modulus = new
		 * String(Base64.encodeBase64(publicKey.getModulus().toByteArray()));
		 * String exponent = new
		 * String(Base64.encodeBase64(publicKey.getPublicExponent
		 * ().toByteArray())); String loginFailure = (String)
		 * req.getAttribute(FormAuthenticationFilter
		 * .DEFAULT_ERROR_KEY_ATTRIBUTE_NAME); req.setAttribute("modulus",
		 * modulus); req.setAttribute("exponent", exponent);
		 * if(loginFailure!=null
		 * &&loginFailure.indexOf("UnknownAccountException")>-1){
		 * req.setAttribute("logfail", "账号或密码错误"); } else
		 * if(loginFailure!=null&&
		 * loginFailure.indexOf("UnsupportedTokenException")>-1){
		 * req.setAttribute("logfail", "验证码错误"); }else{
		 * req.setAttribute("logfail", loginFailure); } }
		 * 
		 * return "partnerLogin";
		 */
	}

	@RequestMapping(value = "/forget")
	public String forget(HttpServletRequest request) {
		return "forget";
	}

	@RequestMapping(value = "/welcome")
	public String welcome(HttpServletRequest request) {
		return "welcome";
	}

	@RequestMapping(value = "/plogout")
	public String exit(HttpServletRequest request) {
		//return "redirect:http://cms.activity.pthv.gitv.tv";
		return "redirect:http://127.0.0.1:8080";
		// Subject currentUser = SecurityUtils.getSubject();
		// currentUser.logout();
		// request.getSession().removeAttribute(LoginAttribute.ATTRIBUTE_USER);
		// request.getSession().invalidate();
		// RSAPublicKey publicKey = rsaService.generateKey(request);
		// String modulus = new
		// String(Base64.encodeBase64(publicKey.getModulus().toByteArray()));
		// String exponent = new
		// String(Base64.encodeBase64(publicKey.getPublicExponent().toByteArray()));
		// String loginFailure = (String)
		// request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
		// request.setAttribute("modulus", modulus);
		// request.setAttribute("exponent", exponent);
		// request.setAttribute("message", loginFailure);
		// return "partnerLogin";
	}

	@RequestMapping(value = "/unauthorized")
	public String unauthorized(HttpServletRequest request) {
		return "unauthorized";
	}

	@Autowired
	private Producer captchaProducer;

	@RequestMapping(value = "/captcha-image")
	public ModelAndView getKaptchaImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();

		response.setDateHeader("Expires", 0);
		response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");
		response.setHeader("Pragma", "no-cache");
		response.setContentType("image/jpeg");

		String capText = captchaProducer.createText();
		session.setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);

		java.awt.image.BufferedImage bi = captchaProducer.createImage(capText);
		ServletOutputStream out = response.getOutputStream();
		ImageIO.write(bi, "jpg", out);
		try {
			out.flush();
		} finally {
			out.close();
		}
		return null;
	}
}
