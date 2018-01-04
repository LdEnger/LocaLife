package main.java.com.hiveview.dao.award;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hiveview.dao.BaseDao;
import com.hiveview.entity.po.award.Award;

@SuppressWarnings("rawtypes")
public interface AwardDao extends BaseDao {
	
	/*
	 * 奖品增加
	 */
	public int save(Award award);
	
	/*
	 * 奖品修改与删除
	 */
	public int update(Award award);
	public String selectIdsByActivityId(@Param("activityId") int activityId);
	public int deleteAward(@Param("activityId") int activityId, @Param("idList") List<Integer> idList);
	
	/*
	 * 奖品查询
	 */
	public List<Award> getList(Award award);
	
	/*
	 * 奖品删除
	 */
	public int delete(Award award);
	
	/*
	 * 为中奖码页-导入中奖码-下载模版所用
	 */
	public List<Award> selectAwardList(@Param("activityId") int activityId, @Param("awardCodeType") int awardCodeType);
	
	/*
	 * 为中奖码页-选择中奖码所用
	 */
	public List<Map<String,String>> selectAwardListByAwardCodeType(@Param("activityId") int activityId, @Param("awardCodeType") int awardCodeType);
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 获得一个Award
	 * @param award
	 * @return
	 */
	public Award get(Award award);
}
