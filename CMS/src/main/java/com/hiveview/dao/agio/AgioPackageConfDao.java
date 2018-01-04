package main.java.com.hiveview.dao.agio;


import java.util.List;

import com.hiveview.dao.BaseDao;
import com.hiveview.entity.agio.AgioPackage;
import com.hiveview.entity.agio.AgioPackageBatch;
import com.hiveview.entity.agio.AgioPackageBatchTask;
import com.hiveview.entity.agio.AgioPackageConf;
import com.hiveview.entity.agio.AgioPackagePool;
import com.hiveview.entity.agio.AgioPackageView;

public interface AgioPackageConfDao extends BaseDao<AgioPackageConf> {
	public List<AgioPackagePool> getPoolList(AgioPackagePool pool);
	public Integer getPoolCount(AgioPackagePool pool);
	
	public Integer savePool(AgioPackagePool pool);
//	public Integer updatePool(AgioPackagePool pool);//NOT FIXED IN AgioPackage.xml  
	
	public List<AgioPackageView> getViewList(AgioPackageView view);
	public Integer getViewConut(AgioPackageView view);
	
	
	public List<AgioPackage> getAgioPackageList(AgioPackage pkg);
	public Integer getAgioPackageCount(AgioPackage pkg);
	public Integer saveAgioPackage(AgioPackage pkg);
	public Integer updateAgioPackage(AgioPackage pkg);
	
	
	public List<AgioPackageBatch> getAgioPackageBatchList(AgioPackageBatch batch);
	public Integer getAgioPackageBatchCount(AgioPackageBatch batch);
	public Integer saveAgioPackageBatch(AgioPackageBatch batch);
	public Integer updateAgioPackageBatch(AgioPackageBatch batch);
	
	
	public List<AgioPackageBatchTask> getAgioPackageBatchTaskList(AgioPackageBatchTask task);
	public Integer saveAgioPackageBatchTask(AgioPackageBatchTask task);

	
	
}
