package com.hiveview.entity.vo.api;

public class AwardActivityHomeVo {
	private Integer isRight;
	private String rightDesc;
	private Integer activityId;
	private String title;
	private Integer type;
	private String playingBgUrl;
	private Integer phoneBindType;

	public Integer getIsRight() {
		return isRight;
	}

	public void setIsRight(Integer isRight) {
		this.isRight = isRight;
	}

	public String getRightDesc() {
		return rightDesc;
	}

	public void setRightDesc(String rightDesc) {
		this.rightDesc = rightDesc;
	}

	public Integer getActivityId() {
		return activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getPhoneBindType() {
		return phoneBindType;
	}

	public void setPhoneBindType(Integer phoneBindType) {
		this.phoneBindType = phoneBindType;
	}

	public String getPlayingBgUrl() {
		return playingBgUrl;
	}

	public void setPlayingBgUrl(String playingBgUrl) {
		this.playingBgUrl = playingBgUrl;
	}
}
