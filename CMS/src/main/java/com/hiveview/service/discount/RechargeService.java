package main.java.com.hiveview.service.discount;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hiveview.dao.discount.RechargeDao;
import com.hiveview.entity.bo.ScriptPage;
import com.hiveview.entity.discount.Recharge;

/**
 * Title：RechargeService.java
 * Description：充值信息服务类
 * Company：hiveview.com
 * Author：韩贺鹏
 * Email：hanhepeng@btte.net 
 * 2016年3月31日 下午3:43:20
 */
@Service
public class RechargeService {
	
	@Autowired
	RechargeDao rechargeDao;
	
	/**
	 * 获取信息
	 * @param discount
	 * @return
	 */
	public ScriptPage getRechargeList(Recharge recharge) {
		List<Recharge> rows = rechargeDao.getList(recharge);
		int total = rechargeDao.count(recharge);
		ScriptPage scriptPage = new ScriptPage();
		scriptPage.setRows(rows);
		scriptPage.setTotal(total);
		return scriptPage;
	}
	
	public boolean add(Recharge recharge){
		System.out.println(recharge.getBigImg());
		int suc = this.rechargeDao.save(recharge);
		return suc > 0 ? true:false;
	}
	
	public boolean update(Recharge recharge){
		int suc = this.rechargeDao.update(recharge);
		return suc > 0 ? true:false;
	}
	
	public boolean del(Recharge recharge){
		int suc = this.rechargeDao.delete(recharge);
		return suc > 0 ? true:false;
	}
	
	/**
	 * 获取所有能用的充值金额
	 * @return
	 */
	public List<Recharge> getAllEnabledRecharge(){
		return this.rechargeDao.getAllEnabledRecharge();
	}

}
