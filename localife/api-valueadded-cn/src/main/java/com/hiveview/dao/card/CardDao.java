package com.hiveview.dao.card;

import java.util.List;

import com.hiveview.dao.BaseDao;
import com.hiveview.entity.card.Card;

public interface CardDao extends BaseDao<Card> {
	
	Card getCardByActivityCode(String activityCode);
	
	List<Card> getUnsucOrderList();
	
	int changeOrderStatus(String cardOrderId);
	
	Card getRepeatCard(Card card);//获取已经存在的活动卡
	
	 int cardExpired(Integer id);//将活动卡状态置为已过期
	 
	 List<Card> getNonactivatedCard(Integer activityId);//获取所有活动过期未激活的活动卡
	 
	 Card getCardById(Integer id);
	 
	 Card getCardByOrderId(String cardOrderId);//根据卡订单号查询

}
