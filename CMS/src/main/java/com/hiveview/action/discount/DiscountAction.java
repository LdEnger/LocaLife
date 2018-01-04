package main.java.com.hiveview.action.discount;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hiveview.entity.bo.AjaxPage;
import com.hiveview.entity.bo.Data;
import com.hiveview.entity.bo.ScriptPage;
import com.hiveview.entity.discount.Discount;
import com.hiveview.entity.discount.Recharge;
import com.hiveview.service.discount.DiscountService;
import com.hiveview.service.discount.RechargeService;

/**
 * Title：DiscountAction.java
 * Description：充送管理类
 * Company：hiveview.com
 * Author：韩贺鹏
 * Email：hanhepeng@btte.net 
 * 2016年3月30日 下午2:56:48
 */
@Controller
@RequestMapping("/discount")
public class DiscountAction {
	
	@Autowired
	DiscountService discountService;
	@Autowired
	RechargeService rechargeService;
	
	@	RequestMapping(value="/show")
	public String show(HttpServletRequest req){
		List<Recharge> rechargeList = this.rechargeService.getAllEnabledRecharge();
		req.setAttribute("rechargeList", rechargeList);
		return "discount/discount_detail";
	}
	
	@RequestMapping(value = "/getList")
	@ResponseBody
	public ScriptPage getDiscountList(AjaxPage ajaxPage,Discount discount) {
		ScriptPage scriptPage = null;
		try {
			discount.copy(ajaxPage);
			scriptPage = discountService.getDiscountList(discount);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return scriptPage;
	}
	
	@RequestMapping(value = "/add")
	@ResponseBody
	public Data add(final Discount discount) {
		Data data = new Data();
			try {
				boolean bool = discountService.add(discount);
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
	public Data update(final Discount discount) {
		Data data = new Data();
			try {
				boolean bool = discountService.update(discount);
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
	public Data del(final Discount discount) {
		Data data = new Data();
			try {
				boolean bool = discountService.del(discount);
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
