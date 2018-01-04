package main.java.com.hiveview.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.hiveview.pay.entity.OpResultTypeEnum;
import com.hiveview.pay.entity.bo.OpResult;

/**
 * Title：白名单拦截器
 * Description：URL路径中包含/open/部分的请求均会被此拦截器拦截，其他请求放过
 * Company：hiveview.com
 * Author：郝伟革 
 * Email：haoweige@hiveview.com 
 * Mar 21, 2014
 */
public class WhitelistInterceptor extends BaseInterceptor {

	private Logger LOG = LoggerFactory.getLogger(WhitelistInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String URI = request.getRequestURI();
		if (URI.contains("/paymentNotify") || URI.contains("/notifyCallback")) {
			return super.preHandle(request, response, handler);//支付通知请求
		}
		if (URI.contains("/webPay") || URI.contains("/open/")) {
			//网页支付请求||开放接口请求
			return super.preHandle(request, response, handler);
		}
		if (URI.contains("/orderQuery.json")) {
			return super.preHandle(request, response, handler);
		}
		String serverip = request.getParameter("serverip");
		if (StringUtils.isEmpty(serverip)) {//缺少serverip
			LOG.error("serverip is NULL");
			this.print(response, new OpResult(OpResultTypeEnum.MSGERR, "serverip cann't be NULL"));
			return false;
		}
		return super.preHandle(request, response, handler);
	}

	/**
	 * <div style="color:red">
	 * 	<li>限定只有白名单内的ip才有访问权限</li>
	 *  <li>支持匹配192.168.0.*这样的网段ip捆绑添加至白名单中</li>
	 * </div>
	 * @param serverip
	 * @return
	 */
}
