package main.java.com.hiveview.action.api.live;

import com.hiveview.entity.Branch;
import com.hiveview.entity.Hall;
import com.hiveview.service.BranchService;
import com.hiveview.service.HallService;
import com.hiveview.service.sys.ZoneCityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * @author by wengjingchang on 2016/9/19.
 */
@Controller
@RequestMapping("/api/live")
public class LiveAreaAction {
	private static final Logger DATA = LoggerFactory.getLogger("data");
	@Autowired
	private HallService hallService;
	@Autowired
	private BranchService branchService;
	@Autowired
	private ZoneCityService zoneCityService;

	/**
	 * @param openUid
	 * @param openRole 操作用户角色：1-集团管理员,2-分公司人员,3-营业厅人员,4-战区管理员
	 * @param zoneId
	 * @param branchId
	 * @return
	 */
	@RequestMapping(value = "/branchList")
	@ResponseBody
	public List<Branch> branchList(Integer openUid, Integer openRole, Integer zoneId, Integer branchId, Integer tag) {
		List<Branch> list = new ArrayList<Branch>();
		if (tag != null && tag == 1) {
			Branch branch = new Branch();
			branch.setId(-1);
			branch.setBranchName("全部");
			list.add(branch);
		}
		try {
			List<Branch> tmp;
			if (openRole == 1) {
				tmp = branchService.getBranchList();
				if (tmp != null && tmp.size() > 0) {
					list.addAll(tmp);
				}
			} else if (openRole == 4) {
				if (zoneId == null) {
					return null;
				}
				tmp = zoneCityService.getBranchByZone(zoneId);
				if (tmp != null && tmp.size() > 0) {
					list.addAll(tmp);
				}
			} else if (openRole == 2 || openRole == 3) {
				if (branchId == null) {
					return null;
				}
				Branch branch = branchService.getBranchInfoById(branchId);
				if (branch != null) {
					list = new ArrayList<Branch>();
					list.add(branch);
				}
			} else {
				return null;
			}
		} catch (Exception e) {
			DATA.info("LiveInfoAction[branchList]异常", e);
			return null;
		}
		return list;
	}

	@RequestMapping(value = "/hallList")
	@ResponseBody
	public List<Hall> hallList(Integer branchId, Integer tag) {
		try {
			List<Hall> list = new ArrayList<Hall>();
			if (tag != null) {
				if (tag == 0) {
					Hall hall = new Hall();
					hall.setId(-1);
					hall.setHallName("-");
					list.add(hall);
				}
				if (tag == 1) {
					Hall hall = new Hall();
					hall.setId(-1);
					hall.setHallName("全部");
					list.add(hall);
				}
			}
			List<Hall> tmp = hallService.getHallList(branchId);
			if (tmp != null && tmp.size() > 0) {
				list.addAll(tmp);
			}
			return list;
		} catch (Exception e) {
			DATA.info("LiveInfoAction[hallList]异常", e);
			return null;
		}

	}

	@RequestMapping(value = "/getBranchInfo")
	@ResponseBody
	public Branch getBranchInfo(Integer branchId) {
		try {
			return branchService.getBranchInfoById(branchId);
		} catch (Exception e) {
			DATA.info("LiveInfoAction[getBranchInfo]异常", e);
			return null;
		}

	}
}