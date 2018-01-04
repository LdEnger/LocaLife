package main.java.com.hiveview.dao.award;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hiveview.dao.BaseDao;
import com.hiveview.entity.activity.Activity;
import com.hiveview.entity.po.award.AwardActivity;

public interface AwardActivityDao extends BaseDao<Activity>{
	
	/*
	 * 活动增加
	 */
	Integer getMaxSequence();
	int save(AwardActivity awardActivity);
	
	/*
	 * 活动修改
	 */
	int update(AwardActivity awardActivity);
	
	/*
	 * 活动查询
	 */
	List<AwardActivity> getList(AwardActivity awardActivity);
	int count(AwardActivity awardActivity);
	
	/*
	 * 活动删除
	 */
	int delete(AwardActivity awardActivity);
	
	/*
	 * 活动上下移
	 */
	AwardActivity getBySeq(@Param("sequence") int sequence);
	Integer getBigger(@Param("sequence") int sequence);
	Integer getSmaller(@Param("sequence") int sequence);
	
	/*
	 * 获取活动类型
	 */
	List<Map<String,String>> selectAwardActivityType();
	
	//以下为其它模块所用
	/*
	 * 中奖码页调用
	 */
	AwardActivity getAwardActivityById(@Param("id") int id);
	
}
