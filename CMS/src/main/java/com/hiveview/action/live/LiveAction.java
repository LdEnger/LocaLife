package main.java.com.hiveview.action.live;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.hiveview.common.ParamConstants;
import com.hiveview.service.sys.ZoneCityService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hiveview.action.base.BaseAction;
import com.hiveview.entity.Branch;
import com.hiveview.entity.Hall;
import com.hiveview.entity.bo.AjaxPage;
import com.hiveview.entity.bo.LoginAttribute;
import com.hiveview.entity.bo.OpResult;
import com.hiveview.entity.bo.ScriptPage;
import com.hiveview.entity.live.LiveOrder;
import com.hiveview.entity.sys.SysUser;
import com.hiveview.entity.vo.VipPackagePriceVo;
import com.hiveview.pay.entity.OpResultTypeEnum;
import com.hiveview.service.BranchService;
import com.hiveview.service.HallService;
import com.hiveview.service.live.LiveService;

@Controller
@RequestMapping("/live")
public class LiveAction extends BaseAction {

	@Autowired
	private BranchService branchService;
	@Autowired
	private HallService hallService;
	@Autowired
	private LiveService liveService;
	@Autowired
	private ZoneCityService zoneCityService;

	@RequestMapping(value = "/show")
	public String showLive(HttpServletRequest request) {
		Object obj = request.getSession().getAttribute(LoginAttribute.ATTRIBUTE_USER);
		SysUser currentUser = (SysUser) obj;
		if (currentUser == null) {
			return "timeout";
		}
		request.setAttribute("currentUser", currentUser);
		List<Branch> braList = branchService.getBranchList();
		Integer branchId = currentUser.getBranchId();
		if (branchId != null) {
			List<Hall> hallList = hallService.getHallList(branchId);
			request.setAttribute("hallList", hallList);
		}
		request.setAttribute("branchList", braList);
		return "live/live";
	}
	@RequestMapping(value = "/show1")
	public String showLive1(HttpServletRequest request) {
		Object obj = request.getSession().getAttribute(LoginAttribute.ATTRIBUTE_USER);
		SysUser currentUser = (SysUser) obj;
		if (currentUser == null) {
			return "timeout";
		}
		request.setAttribute("currentUser", currentUser);
		List<Branch> braList = branchService.getBranchList();
		Integer branchId = currentUser.getBranchId();
		if (branchId != null) {
			List<Hall> hallList = hallService.getHallList(branchId);
			request.setAttribute("hallList", hallList);
		}
		request.setAttribute("branchList", braList);
		return "live/live1";
	}

	@RequestMapping(value = "/getLiveList")
	@ResponseBody
	public ScriptPage getLiveList(AjaxPage ajaxPage, LiveOrder liveOrder, HttpServletRequest request) {
		Object obj = request.getSession().getAttribute(LoginAttribute.ATTRIBUTE_USER);
		SysUser currentUser = (SysUser) obj;
		if (null == currentUser) {
			return null;
		}
		// 判断是不是战区管理员
		if (currentUser.getRoleId().equals(ParamConstants.ZONE_ROLE)) {
			// 查询战区下所有分公司
			currentUser = zoneCityService.setZoneInfo(currentUser);
			liveOrder.setOpenZoneId(currentUser.getZoneId());
		}
		liveOrder.copy(ajaxPage);
		return liveService.getLiveList(liveOrder);
	}

	@RequestMapping(value = "/open")
	@ResponseBody
	public OpResult openLive(HttpServletRequest request, LiveOrder liveOrder) {
		Object obj = request.getSession().getAttribute(LoginAttribute.ATTRIBUTE_USER);
		SysUser currentUser = (SysUser) obj;
		return liveService.openLiveService(liveOrder, currentUser);
	}

	@RequestMapping(value = "/renewLive")
	@ResponseBody
	public OpResult renewLive(HttpServletRequest req, LiveOrder liveOrder) {
		Object obj = req.getSession().getAttribute(LoginAttribute.ATTRIBUTE_USER);
		SysUser currentUser = (SysUser) obj;
		return liveService.renewLive(currentUser, liveOrder);
	}

	@RequestMapping(value = "/refundLive")
	@ResponseBody
	public OpResult refundLive(HttpServletRequest req, LiveOrder liveOrder) {
		Object obj = req.getSession().getAttribute(LoginAttribute.ATTRIBUTE_USER);
		SysUser currentUser = (SysUser) obj;
		return liveService.refundLive(liveOrder, currentUser);
	}

	@RequestMapping(value = "queryStatus")
	@ResponseBody
	public OpResult queryStatus(HttpServletRequest request, LiveOrder liveOrder) {
		if (StringUtils.isEmpty(liveOrder.getLiveOrderId())) {
			return new OpResult(OpResultTypeEnum.MSGERR);
		}
		return liveService.queryStatus(liveOrder);
	}

	@RequestMapping(value = "/batchOpenLive")
	@ResponseBody
	public OpResult batchOpenLive(HttpServletRequest request) throws Exception {
		String token = request.getParameter("token");
		if (!"529fe709f2c09b0bca10c220d41ceebc".equals(token)) {
			return new OpResult(OpResultTypeEnum.MSGERR);
		}
		System.out.println("——————直播批量开通操作开始——————" + new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
		OpResult op = liveService.BatchOpenLive();
		System.out.println("——————直播批量开通操作结束——————" + new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
		return op;
	}

	@RequestMapping(value = "/deleteLiveOrder")
	@ResponseBody
	public OpResult deleteLiveOrder(HttpServletRequest req) {
		String token = req.getParameter("token");
		String orderId = req.getParameter("orderId");
		if (!"529fe709f2c09b0bca10c220d41ceebc".equals(token)) {
			return new OpResult(OpResultTypeEnum.MSGERR, "token验证失败");
		}
		if (null == orderId || "".equals(orderId)) {
			return new OpResult(OpResultTypeEnum.MSGERR, "orderId不能为空");
		}
		return liveService.deleteLiveOrder(orderId);
	}

	@RequestMapping(value = "/computeTimeDifference")
	@ResponseBody
	public OpResult computeTimeDifference(HttpServletRequest req) {
		String endTime = req.getParameter("endTime");
		if (endTime == null) {
			return new OpResult(OpResultTypeEnum.SYSERR, "计算日期失败");
		}
		String date = liveService.getYearMonthDay(endTime);
		return new OpResult(OpResultTypeEnum.SUCC, date);
	}

	@RequestMapping(value = "/computeEndDate")
	@ResponseBody
	public OpResult computeEndDate(HttpServletRequest req) {
		String year = req.getParameter("year");
		String month = req.getParameter("month");
		String day = req.getParameter("day");
		year = (year == null || "".equals(year)) ? "0" : year;
		month = (month == null || "".equals(month)) ? "0" : month;
		day = (day == null || "".equals(day)) ? "0" : day;
		String date = liveService.getEndDate(Integer.valueOf(year), Integer.valueOf(month), Integer.valueOf(day));
		return new OpResult(OpResultTypeEnum.SUCC, date);
	}

	/**
	 * 获取分公司列表
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/getBranchList")
	@ResponseBody
	public List<Branch> getBranchList(HttpServletRequest req) {
		Object obj = req.getSession().getAttribute(LoginAttribute.ATTRIBUTE_USER);
		SysUser currentUser = (SysUser) obj;
		if (null == currentUser) {
			return null;
		}
		currentUser = zoneCityService.setZoneInfo(currentUser);
		return liveService.getBranchList(currentUser);
	}

	/**
	 * 获取营业厅列表
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/getHallList")
	@ResponseBody
	public List<Hall> getHallList(HttpServletRequest req, Integer branchId) {
		Object obj = req.getSession().getAttribute(LoginAttribute.ATTRIBUTE_USER);
		SysUser currentUser = (SysUser) obj;
		if (null == currentUser) {
			return null;
		}
		currentUser = zoneCityService.setZoneInfo(currentUser);
		List<Hall> list = liveService.getHallList(currentUser);
		if (branchId != null && branchId > 0) {
			List<Hall> relist = new ArrayList<Hall>();
			if(!ParamConstants.HALL_ROLE.equals(currentUser.getRoleId())){
				Hall topHall = new Hall();
				topHall.setBranchId(branchId);
				topHall.setId(-1);
				topHall.setHallName("-");
				relist.add(topHall);
			}
			if (list != null && list.size() > 0) {
				for (Hall hall : list) {
					if (hall.getBranchId().equals(branchId)) {
						relist.add(hall);
					}
				}
			}
			return relist;
		}
		return list;
	}

	/**
	 * 获取产品包列表
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/getProductList")
	@ResponseBody
	public List<VipPackagePriceVo> getProductList(HttpServletRequest req) {
		Object obj = req.getSession().getAttribute(LoginAttribute.ATTRIBUTE_USER);
		SysUser currentUser = (SysUser) obj;
		if (null == currentUser) {
			return null;
		}
		currentUser = zoneCityService.setZoneInfo(currentUser);
		return liveService.getProductList(currentUser);
	}
}