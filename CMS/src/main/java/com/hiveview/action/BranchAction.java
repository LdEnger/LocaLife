package main.java.com.hiveview.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hiveview.common.ParamConstants;
import com.hiveview.entity.Branch;
import com.hiveview.entity.bo.AjaxPage;
import com.hiveview.entity.bo.Data;
import com.hiveview.entity.bo.LoginAttribute;
import com.hiveview.entity.bo.OpResult;
import com.hiveview.entity.bo.ScriptPage;
import com.hiveview.entity.sys.SysUser;
import com.hiveview.entity.vo.CityVo;
import com.hiveview.pay.entity.OpResultTypeEnum;
import com.hiveview.service.BranchService;
import com.hiveview.service.sys.ZoneCityService;
import com.hiveview.util.StringUtils;

@Controller
@RequestMapping("/branch")
public class BranchAction {
	@Autowired
	BranchService branchService;
	
	@Autowired
	private ZoneCityService zoneCityService;
	
	@RequestMapping(value ="/addBranch")
	@ResponseBody
	public OpResult addBranch(Branch branch)	 {
		if(StringUtils.isEmpty(branch.getBranchName())){
			return new OpResult(OpResultTypeEnum.DBERR,"分公司名称不能为空");
		}
		int result = branchService.getBranchName(branch.getBranchName());
		if(result > 0){
			return new OpResult(OpResultTypeEnum.DBERR,"分公司已存在");
		}
		int addResult = branchService.addBranch(branch);
		if(addResult != 1){
			return new OpResult(OpResultTypeEnum.DBERR,"添加分公司失败");
		}
		return  new OpResult(OpResultTypeEnum.SUCC,"添加成功");
	}
	
	@RequestMapping(value ="/updateBranch")
	@ResponseBody
	public OpResult updateBranch(Branch branch)	 {
		if(StringUtils.isEmpty(branch.getBranchName())){
			return new OpResult(OpResultTypeEnum.DBERR,"分公司名称不能为空");
		}
//		先不验证
//		int result = branchService.getBranchName(branch.getBranchName());
//		if(result > 0){
//			return new OpResult(OpResultTypeEnum.DBERR,"分公司已存在");
//		}
		int addResult = branchService.updateBranch(branch);
		if(addResult != 1){
			return new OpResult(OpResultTypeEnum.DBERR,"修改分公司失败");
		}
		return  new OpResult(OpResultTypeEnum.SUCC,"修改成功");
	}
	@RequestMapping(value ="/updateBranch1")
	@ResponseBody
	public OpResult updateBranch1(Branch branch)	 {
		if(StringUtils.isEmpty(branch.getBranchName())){
			return new OpResult(OpResultTypeEnum.DBERR,"分公司名称不能为空");
		}
		String addResult = branchService.doBranchCityHandler(branch);
		return  new OpResult(OpResultTypeEnum.SUCC,addResult);
	}
	
	/**
	 * 获取分公司列表
	 * @param request
	 * @return
	 * @throws ServletRequestBindingException
	 */
	@RequestMapping(value ="/getBranchList")
	@ResponseBody
	public List<Branch> getBranchList(HttpServletRequest request,CityVo cityVo) throws ServletRequestBindingException {
		List<Branch> branchList = branchService.getBranchListByArea(cityVo.getCityCode());
		return branchList;
	}
	
	@RequestMapping(value = "/del")
	@ResponseBody
	public Data del(final Branch branch) {
		Data data = new Data();
			try {
				boolean bool = branchService.del(branch);
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
	
	
	@RequestMapping(value="/show")
	public String show(HttpServletRequest req){
		Object obj = req.getSession().getAttribute(LoginAttribute.ATTRIBUTE_USER);
		SysUser currentUser = (SysUser) obj;
		if(currentUser==null){
			return "timeout";
		}
		currentUser = zoneCityService.setZoneInfo(currentUser);
		req.setAttribute("currentUser", currentUser);
		return "sys/branchList";
	}
	
	@RequestMapping(value = "/getList")
	@ResponseBody
	public ScriptPage getSmsRecordList(AjaxPage ajaxPage,Branch branch,HttpServletRequest req) {
		ScriptPage scriptPage = null;
		Object obj = req.getSession().getAttribute(LoginAttribute.ATTRIBUTE_USER);
		SysUser currentUser = (SysUser) obj;
		if (ParamConstants.GROUP_ROLE != currentUser.getRoleId()) { // 不是集团管理员不让操作
			return new ScriptPage();
		}
		try {
			branch.copy(ajaxPage);
			scriptPage = branchService.getBranchList(branch);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return scriptPage;
	}
	
	@RequestMapping(value = "/getAllList")
	@ResponseBody
	public List<Branch> getAllList(Branch branch,HttpServletRequest req) {
		List<Branch> list = new ArrayList<Branch>();
		try {
			list = branchService.getBranchList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
