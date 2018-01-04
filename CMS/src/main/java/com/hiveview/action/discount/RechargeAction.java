package main.java.com.hiveview.action.discount;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hiveview.entity.bo.AjaxPage;
import com.hiveview.entity.bo.Data;
import com.hiveview.entity.bo.ScriptPage;
import com.hiveview.entity.discount.Recharge;
import com.hiveview.service.discount.RechargeService;

/**
 * Title：RechargeAction.java
 * Description：充值信息管理类
 * Company：hiveview.com
 * Author：韩贺鹏
 * Email：hanhepeng@btte.net 
 * 2016年3月31日 下午3:49:10
 */
@Controller
@RequestMapping("/recharge")
public class RechargeAction {
	@Autowired
	RechargeService rechargeService;
	
	@	RequestMapping(value="/show")
	public String show(HttpServletRequest req){
		return "discount/recharge_detail";
	}
	
	@RequestMapping(value = "/getList")
	@ResponseBody
	public ScriptPage getRechargeList(AjaxPage ajaxPage,Recharge recharge) {
		ScriptPage scriptPage = null;
		try {
			recharge.copy(ajaxPage);
			scriptPage = rechargeService.getRechargeList(recharge);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return scriptPage;
	}
	
	@RequestMapping(value = "/add")
	@ResponseBody
	public Data add(final Recharge recharge) {
		Data data = new Data();
			try {
				boolean bool = rechargeService.add(recharge);
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
	
	@RequestMapping(value = "/update")
	@ResponseBody
	public Data update(final Recharge recharge) {
		Data data = new Data();
			try {
				boolean bool = rechargeService.update(recharge);
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
	
	@RequestMapping(value = "/del")
	@ResponseBody
	public Data del(final Recharge recharge) {
		Data data = new Data();
			try {
				boolean bool = rechargeService.del(recharge);
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
