package com.hiveview.dao.localLife;

import com.hiveview.dao.BaseDao;
import com.hiveview.entity.localLife.Label;
import com.hiveview.entity.vo.localLife.LabelContentVo;

import java.util.List;

public interface LabelDao extends BaseDao<Label> {

	List<LabelContentVo> getLabelContentList(Label label);
}
