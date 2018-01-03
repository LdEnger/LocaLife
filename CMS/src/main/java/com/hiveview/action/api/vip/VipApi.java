package main.java.com.hiveview.action.api.vip;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hiveview.entity.Branch;
import com.hiveview.entity.activity.ActivityEx;
import com.hiveview.entity.bo.OpResult;
import com.hiveview.entity.card.Card;
import com.hiveview.entity.count.IncomeRecord;
import com.hiveview.entity.live.LiveOrder;
import com.hiveview.service.BranchService;
import com.hiveview.service.activity.ActivityService;
import com.hiveview.service.card.CardService;
import com.hiveview.service.count.CountService;
import com.hiveview.service.live.LiveService;

@Controller
@RequestMapping("/api/vip")
public class VipApi {
	@Autowired
	BranchService branchService;
	@Autowired
	CountService countService;
	@Autowired
	CardService cardService;
	@Autowired
	private ActivityService activityService;
	@Autowired
	private LiveService liveService;

	/**
	 * 对外提供所有分公司的信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getBranchList")
	@ResponseBody
	public List<Branch> getBranchList() {
		List<Branch> branchList = this.branchService.getBranchList();
		return branchList;
	}

	/**
	 * 获取某段时间内激活(queryMethod=1)/注销(queryMethod=3)卡券明细，分公司生成(queryMethod=2)/卡券的总收入情况
	 * 
	 * @param queryMethod
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	@RequestMapping(value = "/getCardList/{queryMethod}-{beginTime}-{endTime}", method = RequestMethod.GET)
	@ResponseBody
	public OpResult getCardListInfo(@PathVariable Integer queryMethod, @PathVariable String beginTime, @PathVariable String endTime,String page) {
		if (1 != queryMethod && 3 != queryMethod&& 4 != queryMethod) {
			return this.setReturn("999","Unrecognized queryMethod.",null);
		}
		if(StringUtils.isEmpty(beginTime)||StringUtils.isEmpty(endTime)){
			return this.setReturn("999","Time period is required.",null);
		}
		int pageIndex =1;
		if(StringUtils.isEmpty(page)){
			return this.setReturn("999","page period is required.",null);
		}else{
			try {
				pageIndex =Integer.parseInt(page);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		Card card = new Card();
		card.setPageSize(100);
		card.setPageNo(pageIndex);
		card.setQueryMethod(queryMethod);
		//FIXME 借用字段问题 需修改,且迁移至API
		card.setActivationStopTime(endTime);
		card.setActivationTime(beginTime);
		if(2 == queryMethod){
			List<IncomeRecord> incomeList = this.countService.getIncomeInfo(card);
			return this.setReturn("000","success",incomeList);
		}else{
			List<Card> cardList = this.countService.getCardListInfo(card);
			return this.setReturn("000","success",cardList);
		}
	}
	
	@RequestMapping(value = "/getActivityExList", method = RequestMethod.GET)
	@ResponseBody
	public OpResult getActivityExList(){
		List<ActivityEx> list  =activityService.getActivityExListWithOutPageInfo(new ActivityEx());
		return this.setReturn("000", "success", list);
	}
	
	
	@RequestMapping(value = "/getLiveList", method = RequestMethod.GET)
	@ResponseBody
	public OpResult getLiveList(String mac,String sn){
		if(com.hiveview.util.StringUtils.isEmpty(mac)&&com.hiveview.util.StringUtils.isEmpty(sn)){
			return this.setReturn("111", "sorry ,must one for query", null);
		}
		LiveOrder liveOrder =new LiveOrder();
		liveOrder.setMac(mac);
		liveOrder.setSn(sn);
		List<LiveOrder> list  =liveService.getLiveList(liveOrder).getRows();
		return this.setReturn("000", "success", list);
	}
	
	@RequestMapping(value = "/getVipList", method = RequestMethod.GET)
	@ResponseBody
	public OpResult getVipList(String mac,String sn){
		Card card =new Card();
		if(com.hiveview.util.StringUtils.isEmpty(mac)){
			return this.setReturn("111", "sorry no mac input", null);
		}
		card.setTerminalMac(mac);
		card.setTerminalSn(sn);
		card.setStatus(2);
		card.setCardType("vip");
		List<LiveOrder> list  =cardService.getCardList(card).getRows();
		return this.setReturn("000", "success", list);
	}
	private OpResult setReturn(String code, String desc,Object result){
		OpResult opResult = new OpResult();
		opResult.setCode(code);
		opResult.setDesc(desc);
		opResult.setResult(result);
		return opResult;
	}

}
