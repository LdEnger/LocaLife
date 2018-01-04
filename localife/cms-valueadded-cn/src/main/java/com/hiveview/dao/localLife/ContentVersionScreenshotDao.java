package com.hiveview.dao.localLife;

import com.hiveview.dao.BaseDao;
import com.hiveview.entity.localLife.Content;
import com.hiveview.entity.localLife.ContentVersion;
import com.hiveview.entity.localLife.ContentVersionScreenshot;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ContentVersionScreenshotDao extends BaseDao<ContentVersionScreenshot>{
	/**
	 * 
	 * @param id
	 * @return
	 */
	Content getAwardActivityById(@Param("id") int id);
	/**
	 * 
	 * @param contentVersionScreenshot
	 * @return
	 */
	List<ContentVersionScreenshot> getList(ContentVersionScreenshot contentVersionScreenshot);
	/**
	 * 
	 * @param contentVersionScreenshot
	 * @return
	 */
	int count(ContentVersionScreenshot contentVersionScreenshot);
	/**
	 * 
	 * @param contentVersionScreenshot
	 * @return
	 */
	int save(ContentVersionScreenshot contentVersionScreenshot);
	/**
	 * 
	 * @param contentVersionScreenshot
	 * @return
	 */
	int update(ContentVersionScreenshot contentVersionScreenshot);
	/**
	 * 
	 * @param contentVersionScreenshot
	 * @return
	 */
	int delete(ContentVersionScreenshot contentVersionScreenshot);
	/**
	 * 获取当前活动的最大的位置
	 * @return
	 */
	Integer getMaxSequence();
	/**
	 * 获取上一个
	 * @param seq
	 * @return
	 */
	Integer getBigger(@Param("seq") int seq);
	/**
	 * 获取下一个
	 * @param seq
	 * @return
	 */
	Integer getSmaller(@Param("seq") int seq);
	ContentVersionScreenshot getBySeq(@Param("seq") int seq);
}
