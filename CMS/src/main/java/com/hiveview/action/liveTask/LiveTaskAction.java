package main.java.com.hiveview.action.liveTask;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.hiveview.common.ParamConstants;
import com.hiveview.service.sys.ZoneCityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hiveview.entity.Branch;
import com.hiveview.entity.Hall;
import com.hiveview.entity.bo.AjaxPage;
import com.hiveview.entity.bo.LoginAttribute;
import com.hiveview.entity.bo.OpResult;
import com.hiveview.entity.bo.ScriptPage;
import com.hiveview.entity.live.LiveOrder;
import com.hiveview.entity.liveTask.LiveTask;
import com.hiveview.entity.liveTask.LiveTaskDetail;
import com.hiveview.entity.sys.SysUser;
import com.hiveview.pay.entity.OpResultTypeEnum;
import com.hiveview.service.BranchService;
import com.hiveview.service.HallService;
import com.hiveview.service.liveTask.LiveTaskService;

@Controller
@RequestMapping(value = "/liveTask")
public class LiveTaskAction {

	@Autowired
	private BranchService branchService;
	@Autowired
	private HallService hallService;
	@Autowired
	private LiveTaskService liveTaskService;
	@Autowired
	private ZoneCityService zoneCityService;

	@RequestMapping("/show")
	public String show(HttpServletRequest request) {
		Object obj = request.getSession().getAttribute(LoginAttribute.ATTRIBUTE_USER);
		SysUser currentUser = (SysUser) obj;
		if (currentUser == null) {
			return "timeout";
		}
		if (1 == currentUser.getRoleId()) {
			List<Branch> braList = branchService.getBranchList();
			Integer branchId = currentUser.getBranchId();
			if (branchId != null) {
				List<Hall> hallList = hallService.getHallList(branchId);
				request.setAttribute("hallList", hallList);
			}
			request.setAttribute("branchList", braList);
		}
		return "liveTask/liveTaskList";
	}

	@RequestMapping("getLiveTaskList")
	@ResponseBody
	public ScriptPage getList(AjaxPage ajaxPage, LiveTask task, HttpServletRequest request) {
		Object obj = request.getSession().getAttribute(LoginAttribute.ATTRIBUTE_USER);
		SysUser currentUser = (SysUser) obj;
		if (null == currentUser) {
			return null;
		}
		// 判断是不是战区管理员
		if (currentUser.getRoleId().equals(ParamConstants.ZONE_ROLE)) {
			// 查询战区下所有分公司
			currentUser.setBranchId(null);
			currentUser = zoneCityService.setZoneInfo(currentUser);
			task.setOpenZoneId(currentUser.getZoneId());
		}
		task.copy(ajaxPage);
		return liveTaskService.getList(task);
	}

	@RequestMapping(value = "/batchOpenLiveTask")
	@ResponseBody
	public OpResult batchOpenTask(HttpServletRequest req, LiveOrder liveOrder, LiveTask liveTask) {
		Object obj = req.getSession().getAttribute(LoginAttribute.ATTRIBUTE_USER);
		SysUser currentUser = (SysUser) obj;
		String excelPath = req.getParameter("excelPath");
		if (excelPath == null || (excelPath.indexOf(".xls") < 0 && excelPath.indexOf(".xlsx") < 0)) {
			return new OpResult(OpResultTypeEnum.SYSERR, "上传文件不能为空!");
		}
		return liveTaskService.batchOpenLiveForExcel(liveOrder, currentUser, excelPath, liveTask);
	}

	/**
	 * 批量续费
	 * @param req
	 * @param liveOrder
	 * @param liveTask
	 * @return
	 */
	@RequestMapping(value = "/batchRenewLiveTask")
	@ResponseBody
	public OpResult batchRenewLiveTask(HttpServletRequest req, LiveOrder liveOrder, LiveTask liveTask) {
		Object obj = req.getSession().getAttribute(LoginAttribute.ATTRIBUTE_USER);
		SysUser currentUser = (SysUser) obj;
		String excelPath = req.getParameter("excelPath");
		if (excelPath == null || (excelPath.indexOf(".xls") < 0 && excelPath.indexOf(".xlsx") < 0)) {
			return new OpResult(OpResultTypeEnum.SYSERR, "上传文件不能为空!");
		}
		return liveTaskService.batchRenewLiveForExcel(liveOrder, currentUser, excelPath, liveTask);
	}

	@RequestMapping("/getLiveTaskDetailList")
	@ResponseBody
	public ScriptPage getLiveTaskDetailList(AjaxPage ajaxPage, LiveTaskDetail detailTask) {
		detailTask.copy(ajaxPage);
		return liveTaskService.getTaskDetailList(detailTask);
	}
}
