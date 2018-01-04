package main.java.com.hiveview.dao.award;

import java.util.List;

import com.hiveview.dao.BaseDao;
import com.hiveview.entity.po.award.AwardPlay;

@SuppressWarnings("rawtypes")
public interface AwardPlayDao extends BaseDao {
	
	/*
	 * 参与用户查询
	 */
	public List<AwardPlay> getPlayerList(AwardPlay awardPlay);
	public int count(AwardPlay awardPlay);
	
	//供倒出excle模块调用
//	public List<AwardPlay> selectPlayerList(AwardPlay awardPlay);
	
//	public List<AwardPlay> getList(AwardPlay awardPlay);
	
}
