package com.hiveview.dao.localLife;

import com.hiveview.dao.BaseDao;
import com.hiveview.entity.localLife.Content;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ContentDao extends BaseDao<Content>{
	/**
	 * 
	 * @param id
	 * @return
	 */
	Content getContentById(@Param("id") int id);
	/**
	 * 
	 * @param content
	 * @return
	 */
	List<Content> getList(Content content);
    /**
     *
     * @param content
     * @return
     */
    List<Content> getContentByRecommendID(Content content);
    /**
     *
     * @param content
     * @return
     */
    int getCountByRecommendID(Content content);

    /**
     *
     * @param content
     * @return
     */
    List<Content> getContentByLabelID1(Content content);
    /**
     *
     * @param content
     * @return
     */
    int getCountByLabelID1(Content content);
    /**
     *
     * @param content
     * @return
     */
    List<Content> getContentByLabelID0(Content content);
    /**
     *
     * @param content
     * @return
     */
    int getCountByLabelID0(Content content);

	/**
	 * 
	 * @param content
	 * @return
	 */
	int count(Content content);

	/**
	 * 
	 * @param content
	 * @return
	 */
	int save(Content content);
	/**
	 * 
	 * @param content
	 * @return
	 */
	int update(Content content);
	/**
	 * 
	 * @param content
	 * @return
	 */
	int delete(Content content);
	/**
	 * 获取当前活动的最大的位置
	 * @return
	 */
	Integer getMaxSequence();
	/**
	 * 获取上一个
	 * @param sequence
	 * @return
	 */
	Integer getBigger(@Param("sequence") int sequence);
	/**
	 * 获取下一个
	 * @param sequence
	 * @return
	 */
	Integer getSmaller(@Param("sequence") int sequence);
}
