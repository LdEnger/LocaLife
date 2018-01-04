package com.hiveview.dao.localLife;

import com.hiveview.dao.BaseDao;
import com.hiveview.entity.localLife.Content;
import com.hiveview.entity.localLife.Logo;

import java.util.List;

public interface LogoDao extends BaseDao<Logo> {

    List<Logo> getLogoList(Logo logo);
}
