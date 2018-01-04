package com.hiveview.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hiveview.entity.Branch;
import com.hiveview.entity.bo.Data;
import com.hiveview.entity.bo.OpResult;
import com.hiveview.entity.vo.CityVo;
import com.hiveview.pay.entity.OpResultTypeEnum;
import com.hiveview.service.BranchService;

@Controller
@RequestMapping("/branch")
public class BranchAction {
	@Autowired
	BranchService branchService;
	
	@RequestMapping(value ="/addBranch")
	@ResponseBody
	public OpResult addBranch(HttpServletRequest request,Branch branch)	 {
//		String branchName=request.getParameter("branchName");
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

}
