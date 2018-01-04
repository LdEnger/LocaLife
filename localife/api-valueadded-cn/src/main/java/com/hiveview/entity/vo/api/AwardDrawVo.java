package com.hiveview.entity.vo.api;

/**
 * 抽奖结果
 * 
 * @author wengjingchang
 *
 */
public class AwardDrawVo {
	private Integer isWin;// int
	private Integer detailId;// int
	private String awardCode;
	private Integer awardProperty;// Int
	private String awardName;// varchar
	private String awardType;
	private Integer playLeftTimes;// int
	private String playPrompt;

	public Integer getIsWin() {
		return isWin;
	}

	public void setIsWin(Integer isWin) {
		this.isWin = isWin;
	}

	public Integer getDetailId() {
		return detailId;
	}

	public void setDetailId(Integer detailId) {
		this.detailId = detailId;
	}

	public Integer getAwardProperty() {
		return awardProperty;
	}

	public void setAwardProperty(Integer awardProperty) {
		this.awardProperty = awardProperty;
	}

	public String getAwardName() {
		return awardName;
	}

	public void setAwardName(String awardName) {
		this.awardName = awardName;
	}

	public String getPlayPrompt() {
		return playPrompt;
	}

	public void setPlayPrompt(String playPrompt) {
		this.playPrompt = playPrompt;
	}

	public Integer getPlayLeftTimes() {
		return playLeftTimes;
	}

	public void setPlayLeftTimes(Integer playLeftTimes) {
		this.playLeftTimes = playLeftTimes;
	}

	public String getAwardType() {
		return awardType;
	}

	public void setAwardType(String awardType) {
		this.awardType = awardType;
	}

	public String getAwardCode() {
		return awardCode;
	}

	public void setAwardCode(String awardCode) {
		this.awardCode = awardCode;
	}

}
