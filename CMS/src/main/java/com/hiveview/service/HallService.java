package main.java.com.hiveview.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hiveview.dao.HallDao;
import com.hiveview.entity.Hall;

@Service
public class HallService {
	
	@Autowired
	HallDao hallDao;
	
	public int addHall(Hall hall){
		return hallDao.save(hall);
	}
	
	//获取营业厅名单
	public List<Hall> getHallList(Integer branchId){
		return hallDao.getHallList(branchId);
	}
	
	public int getHallName(String hallName) {
		return hallDao.getHallName(hallName);
	}
	
	//获取全部营业厅
	public List<Hall> getList(){
		return hallDao.getList();
	}
	
	public boolean del(Hall hall){
		int suc = this.hallDao.delete(hall);
		return suc > 0 ? true:false;
	}

}
