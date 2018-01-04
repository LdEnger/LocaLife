package com.hiveview.service.localLife;

import com.hiveview.dao.localLife.LabelDao;
import com.hiveview.entity.localLife.Label;
import com.hiveview.entity.vo.localLife.LabelContentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 
 * @author wengjingchang
 *
 */
@Service
public class LabelService {
    @Autowired
    private LabelDao labelDao;


	public List<LabelContentVo> getLabelContentList(Label label) {
		List<LabelContentVo> list = labelDao.getLabelContentList(label);
		return list;
	}
}
