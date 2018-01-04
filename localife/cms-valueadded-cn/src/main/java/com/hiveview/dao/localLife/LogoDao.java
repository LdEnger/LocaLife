package com.hiveview.dao.localLife;

import com.hiveview.dao.BaseDao;
import com.hiveview.entity.localLife.Logo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LogoDao extends BaseDao<Logo>{
	/**
	 * 
	 * @param id
	 * @return
	 */
	Logo getLogoById(@Param("id") int id);
	/**
	 * 
	 * @param logo
	 * @return
	 */
	List<Logo> getList(Logo logo);
	/**
	 * 
	 * @param logo
	 * @return
	 */
	int count(Logo logo);

	/**
	 * 
	 * @param logo
	 * @return
	 */
	int save(Logo logo);
	/**
	 * 
	 * @param logo
	 * @return
	 */
	int update(Logo logo);
    /**
     *
     * @param logo
     * @return
     */
    int delete(Logo logo);

}
