package com.hiveview.service.card;

import org.springframework.stereotype.Service;

import com.hiveview.entity.card.Card;

@Service
public class CardBuildService {
	
	/**
	* 构建后台激活卡对象用于下单，后台激活的时候已有卡卷的卡密、mac、sn信息，则根据数据库的mac、sn为主进行操作
	* @param card
	* @param mac
	* @param sn
	* @param partnerOrderId
	* @param cardOrderStatus
	* @return
	*/ 
	public Card buildSubmitCard(Card card,String mac,String sn,String partnerOrderId,Integer uid,String userName,String devicecode){
		card.setTerminalMac(mac);
		card.setTerminalSn(sn);
		card.setCardOrderId(partnerOrderId);
		card.setStatus(1);
		card.setCardOrderStatus(1);
		card.setDelStatus(0);
		card.setUserName(userName);
		card.setUid(uid);
		card.setDevicecode(devicecode);
		return card;
	}
	
	/**
	* 构建激活活动卡对象用于激活
	* @param partnerOrderId
	* @return
	*/ 
	public Card buildActivityCard(String activityOrderId){
		Card card = new Card();
		card.setCardOrderId(activityOrderId);
		card.setStatus(2);
		card.setCardOrderStatus(2);
		card.setDelStatus(0);
		return null;
	}
}
