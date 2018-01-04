package com.hiveview.entity.vo.api;

import java.util.List;
import java.util.Map;

public class AwardGameInfoVo {
	private Integer activityId;// int
	private Integer type;// int
	private String infoBgUrl;
	private List<Map<?, ?>> icons;// varchar

	public Integer getActivityId() {
		return activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getInfoBgUrl() {
		return infoBgUrl;
	}

	public void setInfoBgUrl(String infoBgUrl) {
		this.infoBgUrl = infoBgUrl;
	}

	public List<Map<?, ?>> getIcons() {
		return icons;
	}

	public void setIcons(List<Map<?, ?>> icons) {
		this.icons = icons;
	}
}
