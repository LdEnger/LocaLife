package main.java.com.hiveview.service.agio;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.hiveview.common.ApiConstants;
import com.hiveview.dao.agio.AgioPackageConfDao;
import com.hiveview.entity.agio.AgioPackage;
import com.hiveview.entity.agio.AgioPackageBatch;
import com.hiveview.entity.agio.AgioPackageConf;
import com.hiveview.entity.agio.AgioPackagePool;
import com.hiveview.entity.agio.AgioPackageView;
import com.hiveview.entity.bo.ScriptPage;
import com.hiveview.service.RedisService;
import com.hiveview.util.HttpUtils;




@Service
public class AgioPackageConfService {

	@Autowired
	AgioPackageConfDao agioPackageConfDao;
	@Autowired
	RedisService redisService;
	private static final Logger DATA = LoggerFactory.getLogger("data");
	/**
	 * 概述：
	 * 返回值：ScriptPage
	 * 作者：徐浩波  xuhaobo@hiveview.com 
	 * 创建日期：2016-12-1下午5:11:22
	 */
	public ScriptPage getAgioPackageConfList(AgioPackageConf conf, Integer roleId) {
		List<AgioPackageConf> rows = new ArrayList<AgioPackageConf>();
		int total = 0;
		rows = agioPackageConfDao.getList(conf);
		total = agioPackageConfDao.count(conf);
		ScriptPage scriptPage = new ScriptPage();
		scriptPage.setRows(rows);
		scriptPage.setTotal(total);
		return scriptPage;
	}

	/**
	 * 概述：
	 * 返回值：boolean
	 * 作者：徐浩波  xuhaobo@hiveview.com 
	 * 创建日期：2016-12-1下午5:11:59
	 */
	public String add(AgioPackageConf conf) {
		String str ="";
		AgioPackageConf query =new AgioPackageConf();
		query.setPageSize(10000);
		query.setPageNo(1);
		List<AgioPackageConf> list =agioPackageConfDao.getList(query);
		for(AgioPackageConf db:list){
			if(db.getAgioValue().equals(conf.getAgioValue())){
				return "添加折扣值重复";
			}
		}
		int i =agioPackageConfDao.save(conf);
		
		return i>0?str:"数据库库错误";
	}

	/**
	 * 概述：
	 * 返回值：boolean
	 * 作者：徐浩波  xuhaobo@hiveview.com 
	 * 创建日期：2016-12-1下午5:12:14
	 */
	public String update(AgioPackageConf conf) {
		String str ="";
		AgioPackageConf query =new AgioPackageConf();
		query.setPageSize(10000);
		query.setPageNo(1);
		List<AgioPackageConf> list =agioPackageConfDao.getList(query);
		for(AgioPackageConf db:list){
			if(db.getAgioValue().equals(conf.getAgioValue())&&!db.getId().equals(conf.getId())){
				return "添加折扣值重复";
			}
		}
		int i =agioPackageConfDao.update(conf);
		return i>0?str:"数据库库错误";
	}

	/**
	 * 概述：麦币充值记录列表
	 * 返回值：ScriptPage
	 * 作者：徐浩波  xuhaobo@hiveview.com 
	 * 创建日期：2016-12-14上午10:41:43
	 */
	public ScriptPage getPoolList(AgioPackagePool pool) {
		List<AgioPackagePool> rows = new ArrayList<AgioPackagePool>();
		int total = 0;
		rows = agioPackageConfDao.getPoolList(pool);
		total = agioPackageConfDao.getPoolCount(pool);
		ScriptPage scriptPage = new ScriptPage();
		scriptPage.setRows(rows);
		scriptPage.setTotal(total);
		return scriptPage;
	}

	/**
	 * 概述：充值给分公司
	 * 返回值：boolean
	 * 作者：徐浩波  xuhaobo@hiveview.com 
	 * 创建日期：2016-12-14下午4:41:57
	 */
	public String addPool(AgioPackagePool pool) {
		String msg ="";
		try {
			String url =ApiConstants.AGIO_URL_ADDAGIO;
			Map<String, String> map =new HashMap<String, String>();
			map.put("branchId", pool.getBranchId()+"");
			map.put("totalAmmount", pool.getAmount()+"");
			map.put("packageConfId", pool.getPackageConfId()+"");
			String json =HttpUtils.httpPostString(url, map);
			DATA.info(url+"?"+map+":"+json);
			JSONObject obj =JSONObject.parseObject(json);
			String code =obj.getString("result");
			if(code!=null && code.equals("true")){
				agioPackageConfDao.savePool(pool);
			}else{
				msg ="更新麦币池失败，请重试";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return msg;
	}

	/**
	 * 概述：
	 * 返回值：ScriptPage
	 * 作者：徐浩波  xuhaobo@hiveview.com 
	 * 创建日期：2016-12-15上午10:08:03
	 */
	public ScriptPage getViewList(AgioPackageView view) {
		List<AgioPackageView> rows = new ArrayList<AgioPackageView>();
		int total = 0;
		rows = agioPackageConfDao.getViewList(view);
		total = agioPackageConfDao.getViewConut(view);
		ScriptPage scriptPage = new ScriptPage();
		scriptPage.setRows(rows);
		scriptPage.setTotal(total);
		return scriptPage;
	}

	/**
	 * 概述：
	 * 返回值：ScriptPage
	 * 作者：徐浩波  xuhaobo@hiveview.com 
	 * 创建日期：2016-12-17上午11:05:32
	 */
	public ScriptPage getAgioList(AgioPackage pkg) {
		List<AgioPackage> rows = new ArrayList<AgioPackage>();
		int total = 0;
		rows = agioPackageConfDao.getAgioPackageList(pkg);
		total = agioPackageConfDao.getAgioPackageCount(pkg);
		ScriptPage scriptPage = new ScriptPage();
		scriptPage.setRows(rows);
		scriptPage.setTotal(total);
		return scriptPage;
	}
	/**
	 * 概述：批量冲麦币查询
	 * 返回值：ScriptPage
	 * 作者：徐浩波  xuhaobo@hiveview.com 
	 * 创建日期：2016-12-17上午11:05:32
	 */
	public ScriptPage getAgioBatchList(AgioPackageBatch batch) {
		List<AgioPackageBatch> rows = new ArrayList<AgioPackageBatch>();
		int total = 0;
		rows = agioPackageConfDao.getAgioPackageBatchList(batch);
		total = agioPackageConfDao.getAgioPackageBatchCount(batch);
		ScriptPage scriptPage = new ScriptPage();
		scriptPage.setRows(rows);
		scriptPage.setTotal(total);
		return scriptPage;
	}

	/**
	 * 概述：
	 * 返回值：String
	 * 作者：徐浩波  xuhaobo@hiveview.com 
	 * 创建日期：2016-12-17上午11:10:22
	 */
	public Boolean addPkg(AgioPackage pkg) {
		int i =agioPackageConfDao.saveAgioPackage(pkg);
		return i>0?true:false;
	}

	/**
	 * 概述：
	 * 返回值：boolean
	 * 作者：徐浩波  xuhaobo@hiveview.com 
	 * 创建日期：2016-12-17上午11:14:00
	 */
	public boolean updatePkg(AgioPackage pkg) {
		int i =agioPackageConfDao.updateAgioPackage(pkg);
		redisService.del("agiopackage_"+pkg.getId());
		return i>0?true:false;
	}

	/**
	 * 概述：批量开通麦币任务
	 * 返回值：boolean
	 * 作者：徐浩波  xuhaobo@hiveview.com 
	 * 创建日期：2017-1-4下午6:47:19
	 */
	public boolean addAgioBatch(AgioPackageBatch batch) {
		int i =agioPackageConfDao.saveAgioPackageBatch(batch);
		return i>0?true:false;
	}
	
	
}
