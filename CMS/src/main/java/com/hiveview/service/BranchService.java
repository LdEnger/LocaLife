package main.java.com.hiveview.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hiveview.dao.BranchDao;
import com.hiveview.entity.Branch;
import com.hiveview.entity.bo.ScriptPage;
import com.hiveview.entity.card.BranchBossCity;

@Service
public class BranchService {

	@Autowired
	BranchDao branchDao;

	// 添加分公司
	public int addBranch(Branch branch) {
		int result =branchDao.addBranch(branch);
		return result;
	}
	
	/**
	 * 作者：徐浩波  xuhaobo@hiveview.com 
	 * 创建日期：2016-12-23下午1:50:49
	 */
	
	public int updateBranch(Branch branch) {
		branch.setBossBranchNew("xuhaobo");
		branch.setBossBranchOld("xuhaobo");
		int result = branchDao.updateBranch(branch);
		return result;
	}
	public String doBranchCityHandler(Branch branch) {
		String newBoss =branch.getBossBranchNew();
		String oldBoss =branch.getBossBranchOld();
		BranchBossCity city =new BranchBossCity();
		city.setBranchId(branch.getId());
		boolean needUpdete =false;
		StringBuffer buffer0=new StringBuffer();
		if(newBoss!=null&&newBoss.trim().length()>0){
			city.setBossType(1);
			branchDao.deleteBranchBossCityByBranchId(city);
			String temp [] =newBoss.split("\\|");
			for(String t:temp){
				city.setCityName(t);
				try {
					int i =branchDao.addBranchBossCity(city);
					if(i==1){
						buffer0.append(t).append("|");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if(buffer0.toString().endsWith("|")){
				buffer0 = new StringBuffer(buffer0.substring(0, buffer0.length()-1));
				needUpdete =true;
			}
		}
		StringBuffer buffer1=new StringBuffer();
		if(oldBoss!=null&&oldBoss.trim().length()>0){
			city.setBossType(2);
			branchDao.deleteBranchBossCityByBranchId(city);
			String temp [] =oldBoss.split("\\|");
			for(String t:temp){
				city.setCityName(t);
				try {
					int i =branchDao.addBranchBossCity(city);
					if(i==1){
						buffer1.append(t).append("|");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if(buffer1.toString().endsWith("|")){
				buffer1 = new StringBuffer(buffer1.substring(0, buffer1.length()-1));
				needUpdete =true;
			}
		}
		if(needUpdete){
			branch.setBossBranchNew(buffer0.toString());
			branch.setBossBranchOld(buffer1.toString());
			branchDao.updateBranch(branch);
		}
		return "已保存后的结果为准，如果保存不进去，说明有重复";
	}

	// 获取区域内分公司名单
	public List<Branch> getBranchListByArea(String cityCode) {
		return branchDao.getBranchListByArea(cityCode);
	}

	// 获取全部分公司名单
	public List<Branch> getBranchList() {
		return branchDao.getBranchList();
	}

	public int getBranchName(String branchName) {
		return branchDao.getBranchName(branchName);
	}
	
	public boolean del(Branch branch){
		int suc = this.branchDao.delete(branch);
		return suc > 0 ? true:false;
	}

	public Branch getBranchInfoById(int branchId){
		return branchDao.getBranchInfoById(branchId);
	}

	/**
	 * 概述：查询列表
	 * 返回值：ScriptPage
	 * 作者：徐浩波  xuhaobo@hiveview.com 
	 * 创建日期：2016-12-23上午10:33:48
	 */
	public ScriptPage getBranchList(Branch branch) {
		List<Branch> rows = new ArrayList<Branch>();
		int total = 0;
		rows = branchDao.getList(branch);
		total = branchDao.count(branch);
		ScriptPage scriptPage = new ScriptPage();
		scriptPage.setRows(rows);
		scriptPage.setTotal(total);
		return scriptPage;
	}

}
