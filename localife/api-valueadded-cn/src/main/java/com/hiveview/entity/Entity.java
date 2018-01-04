package com.hiveview.entity;

import com.hiveview.entity.bo.AjaxPage;

public class Entity extends Pagin {

	public void copy(AjaxPage ajaxPage) {
		this.setPageSize(ajaxPage.getPageSize());
		this.setPageNo(ajaxPage.getPageNo());
	}
}
