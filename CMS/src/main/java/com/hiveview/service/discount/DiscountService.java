package main.java.com.hiveview.service.discount;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hiveview.dao.discount.DiscountDao;
import com.hiveview.entity.bo.ScriptPage;
import com.hiveview.entity.discount.Discount;

/**
 * Title：DiscountService.java
 * Description：充送管理服务类
 * Company：hiveview.com
 * Author：韩贺鹏
 * Email：hanhepeng@btte.net 
 * 2016年3月30日 下午2:57:35
 */
@Service
public class DiscountService {

	@Autowired
	DiscountDao discountDao;
	
	/**
	 * 获取信息
	 * @param discount
	 * @return
	 */
	public ScriptPage getDiscountList(Discount discount) {
		List<Discount> rows = discountDao.getList(discount);
		int total = discountDao.count(discount);
		ScriptPage scriptPage = new ScriptPage();
		scriptPage.setRows(rows);
		scriptPage.setTotal(total);
		return scriptPage;
	}
	
	public boolean add(Discount discount){
		int suc = this.discountDao.save(discount);
		return suc > 0 ? true:false;
	}
	
	public boolean update(Discount discount){
		int suc = this.discountDao.update(discount);
		return suc > 0 ? true:false;
	}
	
	public boolean del(Discount discount){
		int suc = this.discountDao.delete(discount);
		return suc > 0 ? true:false;
	}
}
