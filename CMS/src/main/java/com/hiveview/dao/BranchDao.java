package main.java.com.hiveview.dao;

import java.util.List;

import com.hiveview.entity.Branch;
import com.hiveview.entity.card.BranchBossCity;

public interface BranchDao extends BaseDao<Branch>{

	// 添加分公司
	int addBranch(Branch branch);

	// 获取分公司列表
	List<Branch> getBranchListByArea(String cityCode);

	// 获取分公司列表
	List<Branch> getBranchList();

	int getBranchName(String branchName);

	String getBranchById(Integer id);

	Branch getBranchInfoById(Integer id);
	
	public Integer updateBranch(Branch branch);
	
	
	int addBranchBossCity(BranchBossCity city);
	int deleteBranchBossCityByBranchId(BranchBossCity city);
	List<BranchBossCity> getBossCityByCityName(BranchBossCity city);
}
