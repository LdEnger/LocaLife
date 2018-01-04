package main.java.com.hiveview.util;


import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * Title：资源初始化
 * Description：
 * Company：hiveview.com
 * Author：徐浩波
 * Email：xuhaobo@hiveview.com 
 * 2016-12-6下午2:26:58
 */
@SuppressWarnings("serial")
public class InitServlet extends HttpServlet {
	

	public InitServlet() {
		super();
	}
	public void destroy() {
		super.destroy(); 
	}
	public void init(){
		try {
			super.init();
			ServletContext servletContext = this.getServletContext();
			System.err.println("servletContext:"+servletContext);
			//        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			servletContext.setAttribute("js_version", System.currentTimeMillis());
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}

}
