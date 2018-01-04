package test.java.com.hiveview.test.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.hiveview.dao.card.CardDao;
import com.hiveview.entity.card.Card;
import com.hiveview.test.base.BaseTest;

public class CardDaoTest extends BaseTest{

	@Autowired
	CardDao dao;
	
	@Test
	public void getCardByActivityCodeTest(){
		String activationCode="2016 874 1478 9619 3056";
		Card card  = dao.getCardByActivityCode(activationCode);
		System.out.println(card);
	}
	
	@Test
	public void updateTest(){
		String activationCode="3333 333 3333 3333 3333";
		Card card  = dao.getCardByActivityCode(activationCode);
		card.setId(80);
		card.setTerminalMac("11111111111");
		card.setTerminalSn("11111111111");
		card.setUid(10086);
		card.setDevicecode("222222");
		card.setCardOrderId("aaaaaaa");
		System.out.println(dao.update(card));
	}
}
