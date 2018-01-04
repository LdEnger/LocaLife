package com.hiveview.dao.localLife;

import com.hiveview.dao.BaseDao;
import com.hiveview.entity.localLife.Content;
import com.hiveview.entity.localLife.ContentVersion;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ContentVersionDao extends BaseDao<ContentVersion>{
	/**
	 * 
	 * @param id
	 * @return
	 */
    ContentVersion getContentVersionById(@Param("id") int id);

	/**
	 * 判重
	 * @param contentVersion
	 * @return
	 */
	Integer getVersionCount(ContentVersion contentVersion);

	/**
	 * 
	 * @param contentVersion
	 * @return
	 */
	List<ContentVersion> getList(ContentVersion contentVersion);
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
