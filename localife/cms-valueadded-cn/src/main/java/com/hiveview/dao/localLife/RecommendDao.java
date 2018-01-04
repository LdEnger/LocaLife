package com.hiveview.dao.localLife;

import com.hiveview.dao.BaseDao;
import com.hiveview.entity.localLife.Recommend;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RecommendDao extends BaseDao<Recommend>{
	/**
	 * 
	 * @param id
	 * @return
	 */
	Recommend getRecommendById(@Param("id") int id);
	/**
	 * 
	 * @param recommend
	 * @return
	 */

	List<Recommend> getList1(Recommend recommend);
	List<Recommend> getList(Recommend recommend);
	/**
	 * 
	 * @param recommend
	 * @return
	 */
	int count(Recommend recommend);
	/**
	 * 
	 * @param recommend
	 * @return
	 */
	int save(Recommend recommend);
	/**
	 * 
	 * @param recommend
	 * @return
	 */
	int update(Recommend recommend);
	/**
	 * 
	 * @param recommend
	 * @return
	 */
	int delete(Recommend recommend);
	/**
	 * 获取当前活动的最大的位置
	 * @return
	 */
	Integer getMaxSequence(Recommend recommend);

	/**
	 * 获取当前的最小位置
	 * @return
	 */
	Integer getMinSequence(Recommend recommend);
	Integer getMinSequence1(Recommend recommend);
	Integer getBigger(Recommend recommend);

	Integer getSmaller(Recommend recommend);
	Recommend getBySeq(Recommend recommend);
}
