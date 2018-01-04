package com.hiveview.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.hiveview.entity.Hall;
import com.hiveview.service.HallService;

@Controller
@RequestMapping("/hall")
public class HallAction {
	@Autowired
	HallService hallService;
	
	@RequestMapping(value = "/openAdd")
	public String openAdd(HttpServletRequest request,Integer branchId) throws ServletRequestBindingException {
		request.setAttribute("branchId", branchId);
		return "hall/hall";
	}

	@RequestMapping(value ="/addHall")
	@ResponseBody
	public String addHall(HttpServletRequest request,Hall hall) throws ServletRequestBindingException {
		hallService.addHall(hall);
		return  JSONObject.toJSONString(hall);
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
		Integer branchId = hall.getBranchId();
		List<Hall> hallList = hallService.getHallList(branchId);
		return hallList;
	}
	
	@RequestMapping(value ="/getHallName")
	@ResponseBody
	public JSONObject getHallName(HttpServletRequest request){
		JSONObject object = new JSONObject();
		boolean flag=false;
		String hallName=request.getParameter("hallName");
		flag=hallService.getHallName(hallName);
		if (flag == true) {
			object.put("success", "true");
		} else {
			object.put("success", "false");
		}
		return object;
	}

}
