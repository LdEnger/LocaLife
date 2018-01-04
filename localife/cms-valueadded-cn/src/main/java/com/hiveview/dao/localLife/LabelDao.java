package com.hiveview.dao.localLife;

import com.hiveview.dao.BaseDao;
import com.hiveview.entity.localLife.Label;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LabelDao extends BaseDao<Label>{
	/**
	 * 
	 * @param id
	 * @return
	 */
	Label getLabelById(@Param("id") int id);
	/**
	 * 
	 * @param label
	 * @return
	 */
	List<Label> getList(Label label);
	/**
	 * 
	 * @param label
	 * @return
	 */
	int count(Label label);
	/**
	 * 
	 * @param label
	 * @return
	 */
	int save(Label label);
	/**
	 * 
	 * @param label
	 * @return
	 */
	int update(Label label);
	/**
	 * 
	 * @param label
	 * @return
	 */
	int delete(Label label);

	/**
	 * 获取当前活动的最大的位置
	 * @return
	 */
	Integer getMaxSequence(Label label);

	/**
	 * 获取最小顺序
	 * @return
	 */
	Integer getMinSequence(Label label);
	Integer getMinSequence1(Label label);
	Integer getBigger(Label label);

	Integer getSmaller(Label label);
	Label getBySeq(Label label);
}
