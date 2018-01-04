package main.java.com.hiveview.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hiveview.entity.Hall;
import com.hiveview.entity.bo.Data;
import com.hiveview.entity.bo.OpResult;
import com.hiveview.entity.sys.SysUser;
import com.hiveview.pay.entity.OpResultTypeEnum;
import com.hiveview.service.HallService;
import com.hiveview.service.sys.SysUserService;

@Controller
@RequestMapping("/hall")
public class HallAction {
	@Autowired
	private HallService hallService;
	@Autowired
	private SysUserService sysUserService;
	
	@RequestMapping(value ="/addHall")
	@ResponseBody
	public OpResult addHall(HttpServletRequest request,Hall hall) {
		String hallName = hall.getHallName();
		int result =hallService.getHallName(hallName);
		if(result > 0){
			return new OpResult(OpResultTypeEnum.DBERR,"营业厅已存在");
		}
		int addResult = hallService.addHall(hall);
		if(addResult != 1){
			return new OpResult(OpResultTypeEnum.DBERR,"添加分营业厅失败");
		}
		return  new OpResult(OpResultTypeEnum.SUCC,"添加成功");
	}
	
	/**
	 * 获取分公司列表
	 * @param request
	 * @return
	 * @throws ServletRequestBindingException
	 */
	@RequestMapping(value ="/getHallList")
	@ResponseBody
	public List<Hall> getHallList(HttpServletRequest request,Hall hall) throws ServletRequestBindingException {
		List<Hall> hallList=null;
		try {
			Integer branchId = hall.getBranchId();
			hallList = hallService.getHallList(branchId);
			for (Hall tmp : hallList) {
				SysUser user = new SysUser();
				user.setHallId(tmp.getId());
				List<SysUser> tmpList  = sysUserService.getUserList(user);
				if(tmpList.size()>0){
					tmp.setContainUser(true);
				}else{
					tmp.setContainUser(false);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hallList;
	}
	
	@RequestMapping(value = "/del")
	@ResponseBody
	public Data del(final Hall hall) {
		Data data = new Data();
			try {
				boolean bool = hallService.del(hall);
				if (bool){
					data.setCode(1);
				}else{
					data.setCode(0);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return data;
		} 
}
