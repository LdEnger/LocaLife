package main.java.com.hiveview.dao.card;

import java.util.List;
import java.util.Map;

import com.hiveview.dao.BaseDao;
import com.hiveview.entity.card.BossOrderNew;
import com.hiveview.entity.card.ExcelFile;
import com.hiveview.entity.card.Product;

public interface BossDao extends BaseDao<ExcelFile> {
	public int saveProduct(Product product);
	public int updateProduct(Product product);
	public List<Product> getProductList(Product product);
	public int countPorduct(Product product);
	
	
	public int saveBossOrderNew(BossOrderNew order);
	public int updateBossOrderNew(BossOrderNew order);
	public List<BossOrderNew> getBossOrderNewList(BossOrderNew order);
	public int countBossOrderNew(BossOrderNew order);
	public BossOrderNew getBossOrderById(Integer id);
	
	public List<BossOrderNew> getBossReport(Map<String, String> map);
	public List<BossOrderNew> getBossCityReport(Map<String, String> map);
}
