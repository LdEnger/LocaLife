package main.java.com.hiveview.service.liveTask;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.alibaba.fastjson.JSONObject;
import com.hiveview.common.ParamConstants;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hiveview.dao.BranchDao;
import com.hiveview.dao.live.LiveOrderDao;
import com.hiveview.dao.liveTask.LiveTaskDao;
import com.hiveview.dao.liveTask.LiveTaskDetailDao;
import com.hiveview.entity.Branch;
import com.hiveview.entity.bo.OpResult;
import com.hiveview.entity.bo.ScriptPage;
import com.hiveview.entity.live.LiveOrder;
import com.hiveview.entity.liveTask.LiveTask;
import com.hiveview.entity.liveTask.LiveTaskDetail;
import com.hiveview.entity.sys.SysUser;
import com.hiveview.entity.vo.UserVo;
import com.hiveview.pay.entity.OpResultTypeEnum;
import com.hiveview.service.api.LiveApi;
import com.hiveview.service.api.PassportApi;
import com.hiveview.service.live.LiveOrderBuildService;

@Service
public class LiveTaskService {

	private static final Logger DATA = LoggerFactory.getLogger("data");

	static final int INIT_STATUS = 1;
	static final int SUCC_STATUS = 2;
	static final int ERR_STATUS = 3;
	static final int REFUND_STATUS = 4;
	static AtomicInteger ATOMIC = new AtomicInteger(1000);

	@Autowired
	LiveApi liveApi;
	@Autowired
	LiveTaskDao taskDao;
	@Autowired
	BranchDao branchDao;
	@Autowired
	PassportApi passportApi;
	@Autowired
	LiveOrderDao liveOrderDao;
	@Autowired
	LiveTaskDetailDao liveTaskDetailDao;
	@Autowired
	LiveOrderBuildService liveOrderBuildService;

	public ScriptPage getList(LiveTask task) {
		List<LiveTask> rows = taskDao.getListByZone(task);
		int total = taskDao.countByZone(task);
		ScriptPage scriptPage = new ScriptPage();
		scriptPage.setRows(rows);
		scriptPage.setTotal(total);
		return scriptPage;
	}

	/**
	 * 批量导入excel开通直播
	 * 
	 * @author liumingwei@btte.net
	 * @date 2016年5月11日
	 * @param liveOrder
	 * @param sysUser
	 * @param excelPath
	 * @param liveTask
	 * @return
	 */
	public OpResult batchOpenLiveForExcel(LiveOrder liveOrder, SysUser sysUser, String excelPath, LiveTask liveTask) {
		List<LiveOrder> list = null;
		try {
			InputStream inputStream = new FileInputStream(excelPath);
			list = this.batchOpenLivereadXml(inputStream, excelPath);
		} catch (Exception e) {
			e.printStackTrace();
			return new OpResult(OpResultTypeEnum.SYSERR, "解析Excel异常,请按照模板上传附件！");
		}
		if (list == null || list.isEmpty()) {
			return new OpResult(OpResultTypeEnum.SYSERR, "请检查Excel模板及数据！");
		}
		return this.batchOpenService(list, sysUser, liveOrder, liveTask);
	}

	public OpResult batchOpenService(List<LiveOrder> list, SysUser sysUser, LiveOrder liveOrder, LiveTask liveTask) {
		String executeResult = null;
		String taskId = null;
		try {
			if (sysUser == null) {
				DATA.info("batchOpenLiveService error:无法获取操作人员信息");
				return new OpResult(OpResultTypeEnum.SYSERR, "请登录后操作");
			}
			if (ParamConstants.GROUP_ROLE.equals(sysUser.getRoleId()) || ParamConstants.ZONE_ROLE.equals(sysUser.getRoleId())) {
				Branch branch = branchDao.getBranchInfoById(liveOrder.getBranchId());
				if (branch != null) {
					sysUser.setCityCode(branch.getCityCode());
					sysUser.setCityName(branch.getCityName());
					sysUser.setBranchId(branch.getId());
					sysUser.setBranchName(branch.getBranchName());
					sysUser.setProvinceCode(branch.getProvinceCode());
					sysUser.setProvinceName(branch.getProvinceName());
				}
			}
			String closeTime = liveOrder.getCloseTime();
			List<LiveTaskDetail> resultLit = new ArrayList<LiveTaskDetail>();
			taskId = this.getLiveTaskId();
			boolean flag = false;
			String runMeassage = "正在执行...";
			LiveTask newLiveTask = this.buildLiveTask(liveOrder, liveTask, sysUser, runMeassage, taskId, 1);
			taskDao.save(newLiveTask);
			for (LiveOrder excelData : list) {
				flag = true;
				String mac = excelData.getMac().trim();
				String sn = excelData.getSn().trim();
				mac = mac.replaceAll(":", "");
				sn = sn.replaceAll(":", "");
				LiveTaskDetail liveTaskDetail = new LiveTaskDetail();
				UserVo user = passportApi.getUserInfo(mac, sn);
				if (user == null) {
					liveTaskDetail = this.buildLiveTaskDetail(taskId, mac, sn, 1, "无法获取盒子信息", null);
					resultLit.add(liveTaskDetail);
					DATA.info("batchOpenLiveService 无法获取盒子信息 , mac={},sn={}", new Object[] { mac, sn });
					continue;
				}
				String liveOrderId = this.generateLiveOrderId();
				LiveOrder newLiveOrder = liveOrderBuildService.buildLiveOrder(sysUser, user, liveOrder, liveOrderId, INIT_STATUS, 0);
				int countExist = liveOrderDao.countExist(newLiveOrder);
				if (countExist > 0) {
					liveTaskDetail = this.buildLiveTaskDetail(taskId, mac, sn, 2, "该盒子已开通该产品包,不能重复开通", null);
					resultLit.add(liveTaskDetail);
					DATA.info("batchOpenLiveService 该盒子已开通该产品包,不能重复开通 , mac={},sn={}", new Object[] { mac, sn });
					continue;
				}
				liveOrder.setCloseTime(new DateTime(closeTime).toString("yyyy-MM-dd") + " " + new DateTime().toString("HH:mm:ss"));
				String result = liveApi.openLiveService(sysUser, user, liveOrder, liveOrderId, "0", null);
				if (!"success".equals(result)) {
					liveTaskDetail = this.buildLiveTaskDetail(taskId, mac, sn, 3, result, null);
					resultLit.add(liveTaskDetail);
					DATA.info("batchOpenLiveService " + result + ", mac={},sn={},liveOrderId={}", new Object[] { mac, sn, liveOrderId });
					continue;
				}
				int insert = liveOrderDao.save(newLiveOrder);
				if (insert != 1) {
					liveTaskDetail = this.buildLiveTaskDetail(taskId, mac, sn, 4, "数据库异常", null);
					resultLit.add(liveTaskDetail);
					DATA.info("batchOpenLiveService 数据库异常,插入数据失败 , mac={},sn={},liveOrderId={}", new Object[] { mac, sn, liveOrderId });
					continue;
				}
				liveOrderDao.updateLiveOrderStstus(SUCC_STATUS, liveOrderId, newLiveOrder.getSubmitTime(), liveOrder.getCloseTime());
			}
			if (flag) {
				executeResult = "成功：" + (list.size() - resultLit.size()) + ",失败：" + resultLit.size();
				newLiveTask.setExecuteResult(executeResult);
				taskDao.update(newLiveTask);
				DATA.info("batchOpenLiveService executeResult={},taskId={}", new Object[] { executeResult, taskId });
				for (LiveTaskDetail liveTaskDetail : resultLit) {
					liveTaskDetailDao.save(liveTaskDetail);
				}
			}
		} catch (Exception e) {
			DATA.info("批量开通失败！" + e);
			e.printStackTrace();
			if (taskId != null) {
				taskDao.delete(taskId);
			}
			return new OpResult(OpResultTypeEnum.SYSERR, "开通失败！");
		}
		return new OpResult(OpResultTypeEnum.SUCC, executeResult);
	}

	/**
	 * 
	 * 
	 * @author liumingwei@btte.net
	 * @date 2016年5月16日
	 * @param taskId
	 * @param mac
	 * @param sn
	 * @param resultType
	 * @param resuleName
	 * @param orderId
	 *            预留存订单号，可传null
	 * @return
	 */
	private LiveTaskDetail buildLiveTaskDetail(String taskId, String mac, String sn, int resultType, String resuleName, String orderId) {
		LiveTaskDetail liveTaskDetail = new LiveTaskDetail();
		liveTaskDetail.setTaskId(taskId);
		liveTaskDetail.setMac(mac);
		liveTaskDetail.setSn(sn);
		liveTaskDetail.setOrderId(orderId);
		liveTaskDetail.setOpenResultType(resultType);
		liveTaskDetail.setOpenResultName(resuleName);
		return liveTaskDetail;
	}

	private LiveTask buildLiveTask(LiveOrder liveOrder, LiveTask liveTask, SysUser sysUser, String executeResult, String taskId,Integer taskType) {
		LiveTask newLiveTask = new LiveTask();
		newLiveTask.setTaskId(taskId);
		newLiveTask.setTaskName(liveTask.getTaskName());
		newLiveTask.setTaskDesc(liveTask.getTaskDesc());
		newLiveTask.setProductId(liveOrder.getProductId() + "");
		newLiveTask.setProductName(liveOrder.getProductName());
		newLiveTask.setBranchId(sysUser.getBranchId() + "");
		newLiveTask.setBranchName(sysUser.getBranchName());
		newLiveTask.setHallId(liveTask.getHallId() + "");
		newLiveTask.setHallName(liveTask.getHallName());
		newLiveTask.setOpenId(sysUser.getUserId() + "");
		newLiveTask.setOpenName(sysUser.getUserName());
		newLiveTask.setOpenProvince(sysUser.getProvinceCode());
		newLiveTask.setOpenProvincename(sysUser.getProvinceName());
		newLiveTask.setOpenCity(sysUser.getCityCode());
		newLiveTask.setOpenCityname(sysUser.getCityName());
		newLiveTask.setOpenTime(new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
		newLiveTask.setCloseTime(liveOrder.getCloseTime());
		newLiveTask.setSubmitTime(new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
		newLiveTask.setExecuteResult(executeResult);
		newLiveTask.setTaskType(taskType);
		return newLiveTask;
	}

	/**
	 * 解析Excel
	 * 
	 * @param input
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public List<LiveOrder> batchOpenLivereadXml(InputStream input, String fileName) throws IOException {
		boolean isE2007 = false; // 判断是否是excel2007格式
		if (fileName.endsWith("xlsx")) {
			isE2007 = true;
		}
		List<LiveOrder> list = new ArrayList<LiveOrder>();
		Workbook wb = null;
		// 根据文件格式(2003或者2007)来初始化
		if (isE2007) {
			wb = new XSSFWorkbook(input);
		} else {
			wb = new HSSFWorkbook(input);
		}
		Sheet sheet = wb.getSheetAt(0); // 获得第一个表单
		if (sheet == null) {
			return list;
		}
		Row fastRow = sheet.getRow(0);// 获取第一行标题
		if (fastRow == null) {
			return list;
		}
		Cell mac = fastRow.getCell(0);
		Cell sn = fastRow.getCell(1);
		if (!"mac".equalsIgnoreCase(mac.toString()) || !"sn".equalsIgnoreCase(sn.toString())) {
			return list;
		}
		for (int j = 1; j <= sheet.getLastRowNum(); j++) {
			Row row = sheet.getRow(j);
			if (row == null || "".equals(row)) {
				continue;
			}
			if (row.getCell(0) == null || "".equals(row.getCell(0))) {
				continue;
			}
			if (row.getCell(1) == null || "".equals(row.getCell(1))) {
				continue;
			}
			Cell macCell = row.getCell(0);
			Cell snCell = row.getCell(1);
			LiveOrder liveOrder = new LiveOrder();
			liveOrder.setMac(getValue(macCell));
			liveOrder.setSn(getValue(snCell));
			list.add(liveOrder);
		}
		return list;
	}

	/**
	 * 获取Cell的值
	 * 
	 * @param cell
	 * @return
	 */
	private static String getValue(Cell cell) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		DecimalFormat decimalFormat = new DecimalFormat("#");
		int cellType = cell.getCellType();
		String cellValue = null;
		switch (cellType) {
		case Cell.CELL_TYPE_STRING: // 文本
			cellValue = cell.getStringCellValue();
			break;
		case Cell.CELL_TYPE_NUMERIC: // 数字、日期
			if (DateUtil.isCellDateFormatted(cell)) {
				cellValue = simpleDateFormat.format(cell.getDateCellValue()); // 日期型
			}
			else {
				cellValue = decimalFormat.format(cell.getNumericCellValue()); // 数字
			}
			break;
		case Cell.CELL_TYPE_BOOLEAN: // 布尔型
			cellValue = String.valueOf(cell.getBooleanCellValue());
			break;
		case Cell.CELL_TYPE_BLANK: // 空白
			cellValue = cell.getStringCellValue();
			break;
		case Cell.CELL_TYPE_ERROR: // 错误
			cellValue = "错误";
			break;
		case Cell.CELL_TYPE_FORMULA: // 公式
			cellValue = "错误";
			break;
		default:
			cellValue = "错误";
		}
		return cellValue;
	}

	/**
	 * 生成订单号
	 * 
	 * @author liumingwei@btte.net
	 * @date 2016年5月10日
	 * @return
	 */
	private String generateLiveOrderId() {
		int orderAtomic = ATOMIC.incrementAndGet();
		String orderTime = new DateTime().toString("yyyyMMddHHmmssSSS");
		String orderId = "1001" + orderTime + orderAtomic;
		return orderId;
	}

	/**
	 * 生成任务id
	 * 
	 * @author liumingwei@btte.net
	 * @date 2016年5月10日
	 * @return
	 */
	private String getLiveTaskId() {
		int orderAtomic = ATOMIC.incrementAndGet();
		String orderTime = new DateTime().toString("yyyyMMddHHmmss");
		String orderId = orderTime + orderAtomic;
		return orderId;
	}

	/**
	 * 获取批量开通任务失败明细
	 * 
	 * @author liumingwei@btte.net
	 * @date 2016年6月6日
	 * @param detailTask
	 * @return
	 */
	public ScriptPage getTaskDetailList(LiveTaskDetail detailTask) {
		List<LiveTaskDetail> rows = liveTaskDetailDao.getTaskDetailListByTaskId(detailTask);
		int total = liveTaskDetailDao.count(detailTask);
		ScriptPage scriptPage = new ScriptPage();
		scriptPage.setRows(rows);
		scriptPage.setTotal(total);
		return scriptPage;
	}

	/**
	 * 批量导入excel 续费
	 *
	 * @author liumingwei@btte.net
	 * @date 2016年5月11日
	 * @param liveOrder
	 * @param sysUser
	 * @param excelPath
	 * @param liveTask
	 * @return
	 */
	public OpResult batchRenewLiveForExcel(LiveOrder liveOrder, SysUser sysUser, String excelPath, LiveTask liveTask) {
		List<LiveOrder> list = null;
		try {
			InputStream inputStream = new FileInputStream(excelPath);
			list = this.batchOpenLivereadXml(inputStream, excelPath);
		} catch (Exception e) {
			e.printStackTrace();
			return new OpResult(OpResultTypeEnum.SYSERR, "解析Excel异常,请按照模板上传附件！");
		}
		if (list == null || list.isEmpty()) {
			return new OpResult(OpResultTypeEnum.SYSERR, "请检查Excel模板及数据！");
		}
		return this.batchRenewService(list, sysUser, liveOrder, liveTask);
	}

	/**
	 * 续费
	 * @param list
	 * @param sysUser
	 * @param liveOrder
	 * @param liveTask
	 * @return
	 */
	public OpResult batchRenewService(List<LiveOrder> list, SysUser sysUser, LiveOrder liveOrder, LiveTask liveTask) {
		String executeResult = null;
		String taskId = null;
		try {
			if (sysUser == null) {
				DATA.info("batchRenewService error:无法获取操作人员信息");
				return new OpResult(OpResultTypeEnum.SYSERR, "请登录后操作");
			}
			if (ParamConstants.GROUP_ROLE.equals(sysUser.getRoleId()) || ParamConstants.ZONE_ROLE.equals(sysUser.getRoleId())) {
				Branch branch = branchDao.getBranchInfoById(liveOrder.getBranchId());
				if (branch != null) {
					sysUser.setCityCode(branch.getCityCode());
					sysUser.setCityName(branch.getCityName());
					sysUser.setBranchId(branch.getId());
					sysUser.setBranchName(branch.getBranchName());
					sysUser.setProvinceCode(branch.getProvinceCode());
					sysUser.setProvinceName(branch.getProvinceName());
				}
			}
			List<LiveTaskDetail> resultLit = new ArrayList<LiveTaskDetail>();
			taskId = this.getLiveTaskId();
			boolean flag = false;
			String runMeassage = "正在执行...";
			LiveTask newLiveTask = this.buildLiveTask(liveOrder, liveTask, sysUser, runMeassage, taskId, 2);
			taskDao.save(newLiveTask);
			for (LiveOrder excelData : list) {
				flag = true;
				String mac = excelData.getMac().trim();
				String sn = excelData.getSn().trim();
				mac = mac.replaceAll(":", "");
				sn = sn.replaceAll(":", "");
				LiveTaskDetail liveTaskDetail = new LiveTaskDetail();
				UserVo user = passportApi.getUserInfo(mac, sn);
				if (user == null) {
					liveTaskDetail = this.buildLiveTaskDetail(taskId, mac, sn, 1, "无法获取盒子信息", null);
					resultLit.add(liveTaskDetail);
					DATA.info("batchRenewService 无法获取盒子信息 , mac={},sn={}", new Object[] { mac, sn });
					continue;
				}
				String liveOrderId = this.generateLiveOrderId();
				LiveOrder renewLiveOrder = liveOrderBuildService.buildLiveOrder(sysUser, user, liveOrder, liveOrderId, INIT_STATUS, 1);
				String result = liveApi.renewLiveService(user, liveOrder, liveOrderId);
				if ("error".equals(result)) {
					liveTaskDetail = this.buildLiveTaskDetail(taskId, mac, sn, 3, "续费接口异常", null);
					resultLit.add(liveTaskDetail);
					DATA.info("[batchRenewService] renewLiveMessage:Message={},mac={},sn={}", new Object[] { "续费接口异常", renewLiveOrder.getMac(), renewLiveOrder.getSn() });
					continue;
				}
				JSONObject responseJson = JSONObject.parseObject(result);
				String success = responseJson.getString("success");
				if (!"true".equals(success)) {
					String message = responseJson.getString("message");
					liveTaskDetail = this.buildLiveTaskDetail(taskId, mac, sn, 3, message, null);
					resultLit.add(liveTaskDetail);
					DATA.info("[batchRenewService] renewLiveMessage:Message={},mac={},sn={}", new Object[] { message, renewLiveOrder.getMac(), renewLiveOrder.getSn() });
					continue;
				}
				int insert = liveOrderDao.save(renewLiveOrder);
				if (insert != 1) {
					liveTaskDetail = this.buildLiveTaskDetail(taskId, mac, sn, 4, "数据库异常", null);
					resultLit.add(liveTaskDetail);
					DATA.info("batchRenewService 数据库异常,插入数据失败 , mac={},sn={},liveOrderId={}", new Object[] { mac, sn, liveOrderId });
					continue;
				}
				JSONObject resultJson = JSONObject.parseObject(responseJson.getString("result"));
				String startTime = resultJson.getString("startTime");
				String endTime = resultJson.getString("endTime");
				int update = liveOrderDao.updateLiveOrderStstus(SUCC_STATUS, liveOrderId, startTime, endTime);
				newLiveTask.setCloseTime(endTime);
				taskDao.updateCloseTime(newLiveTask);
				if (update != 1) {
					liveTaskDetail = this.buildLiveTaskDetail(taskId, mac, sn, 4, "续费,数据库异常", null);
					resultLit.add(liveTaskDetail);
					DATA.info("[batchRenewService] renewLiveMessage:更新订单状态失败,mac={},sn={},liveOrderId={}", new Object[] { renewLiveOrder.getMac(), renewLiveOrder.getSn(), liveOrderId });
					continue;
				}
			}
			if (flag) {
				executeResult = "成功：" + (list.size() - resultLit.size()) + ",失败：" + resultLit.size();
				newLiveTask.setExecuteResult(executeResult);
				taskDao.update(newLiveTask);
				DATA.info("batchRenewService executeResult={},taskId={}", new Object[] { executeResult, taskId });
				for (LiveTaskDetail liveTaskDetail : resultLit) {
					liveTaskDetailDao.save(liveTaskDetail);
				}
			}
		} catch (Exception e) {
			DATA.info("批量续费失败！" + e);
			e.printStackTrace();
			if (taskId != null) {
				taskDao.delete(taskId);
			}
			return new OpResult(OpResultTypeEnum.SYSERR, "开通失败！");
		}
		return new OpResult(OpResultTypeEnum.SUCC, executeResult);
	}
}
