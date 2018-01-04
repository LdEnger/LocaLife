package com.hiveview.service.localLife;

import com.hiveview.dao.localLife.ContentDao;
import com.hiveview.dao.localLife.LogoDao;
import com.hiveview.entity.localLife.Content;
import com.hiveview.entity.localLife.Logo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 
 * @author wengjingchang
 *
 */
@Service
public class LogoService {
    @Autowired
    private LogoDao logoDao;


	public List<Logo> getLogoList(Logo logo) {
		return logoDao.getLogoList(logo);
	}
}
