package com.hiveview.dao.localLife;

import com.hiveview.dao.BaseDao;
import com.hiveview.entity.localLife.Content;
import com.hiveview.entity.vo.localLife.ContentVo;

import java.util.List;

public interface ContentDao extends BaseDao<Content> {

    List<ContentVo> getContent(Content content);
}
