package com.hiveview.dao;

import java.util.List;

import com.hiveview.entity.Branch;

public interface BranchDao {
	
	//添加分公司
	int addBranch(Branch branch);
	
	//获取分公司列表
	List<Branch> getBranchListByArea(String cityCode);
	
	//获取分公司列表
	List<Branch> getBranchList();
	
	Branch getBranchName(Branch branch);
	
	String getBranchById(Integer id);

}
