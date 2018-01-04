package main.java.com.hiveview.dao.award;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hiveview.dao.BaseDao;
import com.hiveview.entity.po.award.AwardCode;

@SuppressWarnings("rawtypes")
public interface AwardCodeDao extends BaseDao {
	
	/*
	 * 中奖码增加
	 */
	public int add(AwardCode awardCode);
	
//	public int selectAwardCodeCountByDetailId(@Param("detailId")int detailId);
	
	/*
	 * 中奖码查询
	 */
	public List<AwardCode> getList(AwardCode awardCode);
	public int count(AwardCode awardCode);
	
	/*
	 * 中奖码删除
	 */
	public int deleteAwardCodeByDetailId(@Param("detailId") int detailId);
	
//	public AwardCode get(AwardCode awardCode);
	
}
