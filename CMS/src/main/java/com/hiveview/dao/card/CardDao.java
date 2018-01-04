package main.java.com.hiveview.dao.card;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hiveview.dao.BaseDao;
import com.hiveview.entity.activity.Activity;
import com.hiveview.entity.card.BossOrderNew;
import com.hiveview.entity.card.Card;
import com.hiveview.entity.count.CountRecord;
import com.hiveview.entity.count.IncomeRecord;
import com.hiveview.entity.sys.ZoneCity;

public interface CardDao extends BaseDao<Card> {
	
	Card getCardByActivityCode(String activityCode);
	
	List<Card> getUnsucOrderList();
	
	int changeOrderStatus(String cardOrderId);
	
	Card getRepeatCard(Card card);//获取已经存在的活动卡
	
	 int cardExpired(Integer id);//将活动卡状态置为已过期
	 
	 List<Card> getNonactivatedCard(Integer activityId);//获取所有活动过期未激活的活动卡
	 
	 Card getCardById(Integer id);
	 
	 Card getCardByOrderId(String cardOrderId);//根据卡订单号查询
	 
	 int delete(Integer id);//删除
	 
	 List<CountRecord> getCountInfo(Card card); //获取统计信息（以分公司为单位）
	 
	 int getCountTotal(Card card);

	 List<Card> getCardListInfo(Card card); //获取统计明细（给财务提供接口）
	 
	 List<IncomeRecord> getIncomeInfo(Card card); //获取各分公司生成卡券总和（给财务提供接口）;
	 
	List<Card> getCardByList(@Param("list") List<ZoneCity> list, @Param("skipNo") Integer skipNo, @Param("pageSize") Integer pageSize, @Param("cardType") String cardType);
	
	int getCountByList(List<ZoneCity> list);
	
	 List<Card> getCardByOrderNum(String orderNum);
	 
	 List<Activity> getAgioConvertActivity(Map<String, Object> map);
	 
	 public List<BossOrderNew> getLikeBossReport(Map<String, String> map);
	 
	 public List<Integer> getTotalTxt();
	 
}
