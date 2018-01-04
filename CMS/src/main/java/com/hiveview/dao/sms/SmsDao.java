package main.java.com.hiveview.dao.sms;


import java.util.List;

import com.hiveview.dao.BaseDao;
import com.hiveview.entity.sms.SmsConf;
import com.hiveview.entity.sms.SmsRecord;
import com.hiveview.entity.sms.SmsSender;

public interface SmsDao extends BaseDao<SmsRecord> {
	public Integer addConf(SmsConf conf);
	public List<SmsConf>getConfList(SmsConf conf);
	public Integer getConfCount(SmsConf conf);
	public Integer updateConf(SmsConf conf); 
	
	
	public Integer addSender(SmsSender sender);
	public Integer updateSender(SmsSender sender);
	public List<SmsSender> getSenderList(SmsSender sender);
	public Integer countSender(SmsSender sender);
}
