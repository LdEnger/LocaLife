package com.hiveview.dao.localLife;

import com.hiveview.dao.BaseDao;
import com.hiveview.entity.localLife.Recommend;
import com.hiveview.entity.vo.localLife.RecommendVo;

import java.util.List;

public interface RecommendDao extends BaseDao<Recommend> {

	List<RecommendVo> getRecommendList(Recommend recommend);
}
