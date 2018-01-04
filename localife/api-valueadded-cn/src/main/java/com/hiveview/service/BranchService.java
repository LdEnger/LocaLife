package com.hiveview.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hiveview.dao.BranchDao;
import com.hiveview.entity.Branch;

@Service
public class BranchService {
	
	@Autowired
	BranchDao branchDao;
	
	//添加分公司
	public int addBranch(Branch branch){
		return branchDao.addBranch(branch);
	}
	
	//获取区域内分公司名单
	public List<Branch> getBranchListByArea(String cityCode){
		return branchDao.getBranchListByArea(cityCode);
	}
	
	//获取全部分公司名单
	public List<Branch> getBranchList(){
		return branchDao.getBranchList();
	}
	
	public boolean getBranchName(String branchName) {
		Branch branch = new Branch();
		branch.setBranchName(branchName);
		Branch branchs = branchDao.getBranchName(branch);
		if (branchs != null) {
			if (branchs.getId() > 0) {
				return true;
			}
		}
		return false;
	}
}
