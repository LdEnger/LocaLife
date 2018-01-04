package com.hiveview.service.localLife;

import com.hiveview.dao.localLife.ContentDao;
import com.hiveview.entity.localLife.Content;
import com.hiveview.entity.vo.localLife.ContentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 
 * @author Antony
 *
 */
@Service
public class ContentService {
    @Autowired
    private ContentDao contentDao;


	public List<ContentVo> getContent(Content content) {
		return contentDao.getContent(content);
	}
}
