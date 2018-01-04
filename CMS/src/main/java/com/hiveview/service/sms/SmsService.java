package main.java.com.hiveview.service.sms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.hiveview.common.EnvConstants;
import com.hiveview.dao.sms.SmsDao;
import com.hiveview.entity.bo.ScriptPage;
import com.hiveview.entity.card.Card;
import com.hiveview.entity.sms.SmsConf;
import com.hiveview.entity.sms.SmsRecord;
import com.hiveview.entity.sms.SmsSender;
import com.hiveview.util.HttpUtils;
/**
 * Description：短信相关服务
 * Company：hiveview.com
 * Author：徐浩波
 * Email：xuhaobo@hiveview.com 
 * 2016-12-5下午4:45:24
 */
@Service
public class SmsService {
	private static final Logger DATA = LoggerFactory.getLogger("data");
	@Autowired
	private SmsDao smsDao;
	@SuppressWarnings("unchecked")
	public void sendSms(Card card){
		int branchId =card.getCardFromBranch();
		SmsConf conf =new SmsConf();
		conf.setText1(""+branchId);
		ScriptPage page = this.getSmsConfList(conf);
		List<SmsConf> sms =page.getRows();
		if(sms!=null&&sms.size()>0){
			String args1=card.getActivationCode();
			String args2 ="180";
			//暂时不需要取变量--add  by  赵晨
//			if(card.getAutoActiveTimeLength()!=null){
//				args2=card.getAutoActiveTimeLength()+"";
//			}
			
			String content ="";
			content =sms.get(0).toString().replaceAll("args_1", args1).replaceAll("args_2", args2);
			int state =1;
			if(!EnvConstants.PROFILE.equals("final")||sms.get(0).getState()==0){
				//测试环境不发短信
				DATA.info(sms.get(0).getState()+"<---短信配置状态--测试环境不发短信"+ToStringBuilder.reflectionToString(card));
				System.out.println(ToStringBuilder.reflectionToString(card));
			}else{
				//正式环境发短信
				state =send(card.getPhone(), content, branchId);
				DATA.info(sms.get(0).getState()+"<--"+card.getPhone()+content);
			}
			//发送完成记录发送数据//
			SmsRecord record =new SmsRecord(card,state,sms.get(0).getId());
			smsDao.save(record);
		}else{
			DATA.info("短信模板没配置"+ToStringBuilder.reflectionToString(card));
		}
		
	}
	
	/**
	 * 概述：短信发送记录查询
	 * 返回值：ScriptPage
	 * 作者：徐浩波  xuhaobo@hiveview.com 
	 * 创建日期：2016-12-6下午10:16:14
	 */
	public ScriptPage getSmsRecordList(SmsRecord sms) {
		List<SmsRecord> rows = new ArrayList<SmsRecord>();
		int total = 0;
		rows = smsDao.getList(sms);
		total = smsDao.count(sms);
		ScriptPage scriptPage = new ScriptPage();
		scriptPage.setRows(rows);
		scriptPage.setTotal(total);
		return scriptPage;
	}
	/**
	 * 概述：获取短信配置列表
	 * 返回值：ScriptPage
	 * 作者：徐浩波  xuhaobo@hiveview.com 
	 * 创建日期：2016-12-7下午11:25:23
	 */
	public ScriptPage getSmsConfList(SmsConf sms) {
		List<SmsConf> rows = new ArrayList<SmsConf>();
		int total = 0;
		rows = smsDao.getConfList(sms);
		total = smsDao.getConfCount(sms);
		ScriptPage scriptPage = new ScriptPage();
		scriptPage.setRows(rows);
		scriptPage.setTotal(total);
		return scriptPage;
	}
	/**
	 * 概述：
	 * 返回值：int
	 * 作者：徐浩波  xuhaobo@hiveview.com 
	 * 创建日期：2016-12-8上午12:01:21
	 */
	public int addSmsConf(SmsConf conf) {
		int flag =0;
		try {
			flag =smsDao.addConf(conf);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	/**
	 * 概述：
	 * 返回值：int
	 * 作者：徐浩波  xuhaobo@hiveview.com 
	 * 创建日期：2016-12-8上午12:02:31
	 */
	public int updateSmsConf(SmsConf conf) {
		int flag =0;
		try {
			flag =smsDao.updateConf(conf);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	/**
	 * 概述：发短信配置查询列表
	 * 返回值：ScriptPage
	 * 作者：徐浩波  xuhaobo@hiveview.com 
	 * 创建日期：2017-2-13下午6:58:57
	 */
	public ScriptPage getSmsSenderList(SmsSender sender) {
		List<SmsSender> rows = new ArrayList<SmsSender>();
		int total = 0;
		rows = smsDao.getSenderList(sender);
		total = smsDao.countSender(sender);
		ScriptPage scriptPage = new ScriptPage();
		scriptPage.setRows(rows);
		scriptPage.setTotal(total);
		return scriptPage;
	}
	/**
	 * 概述：测试发短信
	 * 返回值：int
	 * 作者：徐浩波  xuhaobo@hiveview.com 
	 * 创建日期：2017-2-13下午8:10:35
	 */
	public  int testSender(SmsSender conf) {
		SmsConf sms =new SmsConf();
		sms.setText1(""+conf.getId());
		ScriptPage page = this.getSmsConfList(sms);
		List<SmsConf> smss =page.getRows();
		String content ="";
		if(smss!=null&&smss.size()>0){
			String args1="测试码";
			String args2 ="180";
			SmsConf c =smss.get(0);
			if(c.getState()==0){
				return 3;
			}
			content =c.toString().replaceAll("args_1", args1).replaceAll("args_2", args2);
		}
		return send(conf.getSender(),content,conf.getId());
	}
	/**
	 * 概述：正式的发短信方法
	 * 返回值：int
	 * 作者：徐浩波  xuhaobo@hiveview.com 
	 * 创建日期：2017-2-13下午8:12:36
	 */
	public int send(String phones, String content,Integer branchId){
		SmsSender query =new SmsSender();
		query.setBranchId(branchId);
		List<SmsSender> list1 =smsDao.getSenderList(query);
		int i =0;
		if(list1!=null&&list1.size()>0){
			SmsSender sender =list1.get(0);
			Map<String, String> params = new HashMap<String, String>();
			params.put("username",sender.getUser());
			params.put("pwd", sender.getMd5());//密码需MD5 32 小写
			params.put("msg", content+sender.getSignature());//必须带签名  签名就是这个【大麦盒子】
			params.put("charSetStr", "utf");
			params.put("p", phones);
			String result =HttpUtils.httpPostString(sender.getSender(), params);
//			System.err.println(params);
//			System.out.println(result);
			//result ={"status":100,"count":1,"list":[{"p":"15901180445","mid":"eb1275acd6f560e0"}]}
			//http://api.app2e.com/getBalance.api.php?username=domybox-bj&pwd=dde9a620bf081f6bcf5a32a0260068ef查余额
			//LOG TO SMS  SEND AND GIVE STATE FOR SMSRECORD
			if(!"".equals(result)){
				JSONObject json =JSONObject.parseObject(result);
				String status =json.getString("status");
				if("100".equals(status)){
					i=1;
				}
			}
			DATA.info("sendInfo{},returnInfo{}",params,result);
		}else{
			DATA.info("SendSms no Sender",branchId,phones,content);
		}
	
		return i; 
	}
	/**
	 * 概述：添加发送短信配置
	 * 顺便初始化短信内容，但是需要手工去上线短信内容模板
	 * 返回值：int
	 * 作者：徐浩波  xuhaobo@hiveview.com 
	 * 创建日期：2017-2-14下午4:34:46
	 */
	public int saveSmsSender(SmsSender sender){
		int i=0;
		try {	
				sender.setInitSmsConf(1);	
			 	i=smsDao.addSender(sender);
				SmsConf conf =new SmsConf();
				conf.setBranchId(sender.getBranchId());
				conf.setState(0);//默认初始化不生效，需要去激活
				conf.setText1("【大麦盒子】尊敬的用户，您本次缴费获得大麦影视尊享年包。激活码：");
				conf.setText2("，激活码有效期");
				conf.setText3("天。操作办法：1在大麦盒子首页按遥控器“向下键”。2在“我的桌面”选择“大麦vip”，输入激活码后点击“完成”后，点击”确认”。3确认开通信息后点确定，即享服务。如有疑问，请致电客服96007或关注微信号damai600804查阅完整教程。退订回复TN");
				conf.setArgs1("args_1");
				conf.setArgs2("args_2");
				conf.setUpdateBy(0);
				this.smsDao.addConf(conf);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return i;
	}
	public int updateSmsSender(SmsSender sender){
		return smsDao.updateSender(sender);
	}
	
	
	
}
