package main.java.com.hiveview.service.count;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.hiveview.dao.BranchDao;
import com.hiveview.dao.card.CardDao;
import com.hiveview.entity.Branch;
import com.hiveview.entity.bo.ScriptPage;
import com.hiveview.entity.card.BossOrderNew;
import com.hiveview.entity.card.BossReport;
import com.hiveview.entity.card.Card;
import com.hiveview.entity.count.CountRecord;
import com.hiveview.entity.count.IncomeRecord;
import com.hiveview.entity.vo.VipPackagePriceVo;
import com.hiveview.service.RedisService;
import com.hiveview.service.api.ChargePriceApi;
import com.hiveview.util.DateUtil;

@Service
public class CountService {
	@Autowired
	CardDao cardDao;
	
	@Autowired
	RedisService redisService;
	
	@Autowired
	private ChargePriceApi chargePriceApi;
	
	@Autowired
	BranchDao branchDao;
	
	public Integer count(Card card){
		return this.cardDao.count(card);
	}
	
	
	/**
	 * 获取统计信息（以分公司为单位）
	 * @param card
	 * @return
	 */
	public ScriptPage getCountRecordList(Card card) {
		if(card.getQueryMethod()==null){
			card.setQueryMethod(3);//默认按开通时间查询
		}
		List<CountRecord> rows = this.cardDao.getCountInfo(card);
		int total =this.cardDao.getCountTotal(card);
		ScriptPage scriptPage = new ScriptPage();
		scriptPage.setRows(rows);
		scriptPage.setTotal(total);
		return scriptPage;
	}
	
	/**
	 * 获取分公司收入情况（给财务提供接口）
	 * @param card
	 * @return
	 */
	public List<IncomeRecord> getIncomeInfo(Card card){
		return this.cardDao.getIncomeInfo(card);
	}
	
	/**
	 * 获取卡券明细（给财务提供接口）
	 * @param card
	 * @return
	 */
	public List<Card> getCardListInfo(Card card){
		return this.cardDao.getCardListInfo(card);
	}
	
	/**
	 * 获取统计信息（以分公司为单位）
	 * @param card
	 * @return
	 */
	public List<CountRecord> getCountInfo(Card card){
		return this.cardDao.getCountInfo(card);
	}
	
	/**
	 * 概述：
	 * 返回值：List<BossReport>
	 * 作者：徐浩波  xuhaobo@hiveview.com 
	 * 创建日期：2017-4-12上午10:36:09
	 */
	public List<BossReport> getBossReport(String startDate, String endDate) {
		List<BossReport> reList =new ArrayList<BossReport>();
		String key   ="card"+startDate+"_"+endDate;
		String result =redisService.get(key);
		if(result!=null &&!"".equals(result)){
			reList =JSONObject.parseArray(result, BossReport.class);
			return reList;
		}
		Map<String, String> map =new HashMap<String, String>();
		map.put("start", startDate);
		endDate = DateUtil.dateToString(DateUtil.getMotherTargetDate(DateUtil.stringToDate(endDate, 0), 1));
		map.put("end", endDate);
		List<BossOrderNew> list =cardDao.getLikeBossReport(map);
		List<VipPackagePriceVo> prodcutList = chargePriceApi.getVipChargPriceList();
		BossReport title =new BossReport();
		Map <Integer, BossReport>tMap =new HashMap<Integer, BossReport>();
		BossReport temp1 =new BossReport();
		temp1.setProductName("无法关联");
		tMap.put(0, temp1);
		for(VipPackagePriceVo vo:prodcutList){
			BossReport temp =new BossReport();
			temp.setProductName(vo.getProductName());
			tMap.put(vo.getProductId(), temp);
		}
		title.setBranchId(-2);
		title.setBranchName("分公司");
		title.setMap(tMap);
		reList.add(title);
		for(BossOrderNew order:list){
			BossReport report =null;
			for(BossReport r:reList){
				if(order.getBranchId()!=null&&r.getBranchId()!=null&&r.getBranchId().equals(order.getBranchId())){
					report =r;
					reList.remove(r);
					break;
				}
			}
			if(report== null){
				report =new BossReport();
				if(order.getBranchId()==null||order.getBranchId().equals(-1)){
					continue;
				}
				report.setBranchId(order.getBranchId());
				Branch branch =branchDao.getBranchInfoById(order.getBranchId());
				if(branch!=null){
					report.setBranchName(branch.getBranchName());
				}else{
					report.setBranchName("未知"+order.getBranchId());
				}
			}
			Map <Integer, BossReport> tempMap =getBossBodyMap(list,tMap,order.getBranchId());
			report.setMap(tempMap);
			reList.add(report);
		}
		
		String cacheString =JSONObject.toJSONString(reList);
		redisService.set(key, cacheString, 3600);
		return reList;
	}

	private Map<Integer, BossReport> getBossBodyMap(List<BossOrderNew> list, Map<Integer, BossReport> tMap,Integer branchId) {
		Map<Integer, BossReport> rmap=new HashMap<Integer, BossReport>();
		Set<Integer> tset =tMap.keySet();
		for(Integer tproduct:tset){
			int num =0;
			for(BossOrderNew order:list){
				if(order.getBranchId()!=null&&order.getBranchId().equals(branchId)){
					Integer productId =0;
					if(order.getActivityId()!=null){
						productId =order.getActivityId();
					}
					if(productId.equals(tproduct)){
						num = order.getExcelId();
					}
				}else{
					continue;
				}
			}
			BossReport report =new BossReport();
			report.setNum(num);
			report.setProductName(num+"");
			rmap.put(tproduct, report);
		}
		
		return rmap;
	}

}
