package com.hiveview.dao.localLife;

import com.hiveview.dao.BaseDao;
import com.hiveview.entity.localLife.LabelContent;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LabelContentDao extends BaseDao<LabelContent>{
	/**
	 * 
	 * @param id
	 * @return
	 */
	LabelContent getAwardActivityById(@Param("id") int id);
	/**
	 * 
	 * @param labelContent
	 * @return
	 */
	List<LabelContent> getList(LabelContent labelContent);

	/**
	 * 根据 lableId 查询省份城市ID
	 * @param labelContent
	 * @return
	 */
	LabelContent getCtiyIdAndProvinceID1(LabelContent  labelContent);


	/**
	 * 
	 * @param labelContent
	 * @return
	 */
	int count(LabelContent labelContent);
	/**
	 * 
	 * @param labelContent
	 * @return
	 */
	int save(LabelContent labelContent);
	/**
	 * 
	 * @param labelContent
	 * @return
	 */
	int update(LabelContent labelContent);
	/**
	 * 
	 * @param labelContent
	 * @return
	 */
	int delete(LabelContent labelContent);
    /**
     *
     * @param labelContent
     * @return
     */
    int deleteLabelContent(LabelContent labelContent);
	/**
	 * 获取当前活动的最大的位置
	 * @return
	 */
	Integer getMaxSequence();


	Integer getMinSequence();
	/**
	 * 获取上一个
	 * @param seq
	 * @return
	 */
	Integer getBigger(@Param("seq") int seq,@Param("labelId") int labelId);
	/**
	 * 获取下一个
	 * @param seq
	 * @return
	 */
	Integer getSmaller(@Param("seq") int seq,@Param("labelId") int labelId);
	LabelContent getBySeq(@Param("seq") int seq,@Param("labelId") int labelId);
}
