package com.hiveview.dao;

import java.util.List;

import com.hiveview.entity.Hall;

public interface HallDao extends BaseDao<Hall>{
	
	List<Hall> getList();

	//通过分公司获取营业厅列表
	List<Hall> getHallList(Integer branchId);
	
	int getHallName(String hall);
	
	String getHallById(Integer hallId);
}
