package com.hiveview.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.hiveview.entity.Branch;
import com.hiveview.entity.sys.SysUser;
import com.hiveview.entity.vo.CityVo;
import com.hiveview.service.BranchService;

@Controller
@RequestMapping("/branch")
public class BranchAction {
	@Autowired
	BranchService branchService;
	
	@RequestMapping(value = "/openAdd")
	public String openAdd(HttpServletRequest request,SysUser user) throws ServletRequestBindingException { 
		request.setAttribute("user",user);
		return "branch/branch";
	}

	@RequestMapping(value ="/addBranch")
	@ResponseBody
	public String addBranch(HttpServletRequest request,Branch branch) throws ServletRequestBindingException {
		branchService.addBranch(branch);
		return  JSONObject.toJSONString(branch);
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
	
	@RequestMapping(value ="/getBranchName")
	@ResponseBody
	public JSONObject getBranchName(HttpServletRequest request){
		JSONObject object = new JSONObject();
		boolean flag=false;
		String branchName=request.getParameter("branchName");
		flag=branchService.getBranchName(branchName);
		if (flag == true) {
			object.put("success", "true");
		} else {
			object.put("success", "false");
		}
		return object;
	}

}
