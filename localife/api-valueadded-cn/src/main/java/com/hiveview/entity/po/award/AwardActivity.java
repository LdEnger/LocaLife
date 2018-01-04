package com.hiveview.entity.po.award;

import java.io.Serializable;
import java.util.Date;

import com.hiveview.entity.Entity;

public class AwardActivity extends Entity implements Serializable {
	private static final long serialVersionUID = -5180335543280497307L;
	private Integer id;
	private String title;
	private Integer type;
	private int sequence;
	private Date beginTime;
	private Date endTime;
	private String logoUrl;
	private String bgUrl;
	private String infoBgUrl;
	private String playingBgUrl;
	private String endTitle;
	private String endText;
	private int playLimitDay;
	private int playLimitTotal;
	private String playPromptDay;// 每日提示
	private String playPromptTotal;// 超过活动次数提示
	private String playPromptWin; // 中奖提示
	private String playPromptLost;// 未中奖提示
	private int phoneBindType;// 电话绑定方式
	private int awardCodeType;//
	private int awardWinRatio;
	private int awardWinLimit;
	private int awardWinAgain;
	private int awardPlayerType;
	private Date payBeginTime;
	private Date payEndTime;
	private int paySum;
	private String awardRemark;
	private String activityDesc;
	private Integer showFlag;
	//限制日期
	private Date limitEndDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public String getBgUrl() {
		return bgUrl;
	}

	public void setBgUrl(String bgUrl) {
		this.bgUrl = bgUrl;
	}

	public String getEndTitle() {
		return endTitle;
	}

	public void setEndTitle(String endTitle) {
		this.endTitle = endTitle;
	}

	public String getEndText() {
		return endText;
	}

	public void setEndText(String endText) {
		this.endText = endText;
	}

	public int getPlayLimitDay() {
		return playLimitDay;
	}

	public void setPlayLimitDay(int playLimitDay) {
		this.playLimitDay = playLimitDay;
	}

	public int getPlayLimitTotal() {
		return playLimitTotal;
	}

	public void setPlayLimitTotal(int playLimitTotal) {
		this.playLimitTotal = playLimitTotal;
	}

	public String getPlayPromptDay() {
		return playPromptDay;
	}

	public void setPlayPromptDay(String playPromptDay) {
		this.playPromptDay = playPromptDay;
	}

	public String getPlayPromptTotal() {
		return playPromptTotal;
	}

	public void setPlayPromptTotal(String playPromptTotal) {
		this.playPromptTotal = playPromptTotal;
	}

	public String getPlayPromptWin() {
		return playPromptWin;
	}

	public void setPlayPromptWin(String playPromptWin) {
		this.playPromptWin = playPromptWin;
	}

	public String getPlayPromptLost() {
		return playPromptLost;
	}

	public void setPlayPromptLost(String playPromptLost) {
		this.playPromptLost = playPromptLost;
	}

	public int getPhoneBindType() {
		return phoneBindType;
	}

	public void setPhoneBindType(int phoneBindType) {
		this.phoneBindType = phoneBindType;
	}

	public int getAwardCodeType() {
		return awardCodeType;
	}

	public void setAwardCodeType(int awardCodeType) {
		this.awardCodeType = awardCodeType;
	}

	public int getAwardWinRatio() {
		return awardWinRatio;
	}

	public void setAwardWinRatio(int awardWinRatio) {
		this.awardWinRatio = awardWinRatio;
	}

	public int getAwardWinLimit() {
		return awardWinLimit;
	}

	public void setAwardWinLimit(int awardWinLimit) {
		this.awardWinLimit = awardWinLimit;
	}

	public int getAwardWinAgain() {
		return awardWinAgain;
	}

	public void setAwardWinAgain(int awardWinAgain) {
		this.awardWinAgain = awardWinAgain;
	}

	public int getAwardPlayerType() {
		return awardPlayerType;
	}

	public void setAwardPlayerType(int awardPlayerType) {
		this.awardPlayerType = awardPlayerType;
	}

	public Date getPayBeginTime() {
		return payBeginTime;
	}

	public void setPayBeginTime(Date payBeginTime) {
		this.payBeginTime = payBeginTime;
	}

	public Date getPayEndTime() {
		return payEndTime;
	}

	public void setPayEndTime(Date payEndTime) {
		this.payEndTime = payEndTime;
	}

	public int getPaySum() {
		return paySum;
	}

	public void setPaySum(int paySum) {
		this.paySum = paySum;
	}

	public String getInfoBgUrl() {
		return infoBgUrl;
	}

	public void setInfoBgUrl(String infoBgUrl) {
		this.infoBgUrl = infoBgUrl;
	}

	public String getPlayingBgUrl() {
		return playingBgUrl;
	}

	public void setPlayingBgUrl(String playingBgUrl) {
		this.playingBgUrl = playingBgUrl;
	}

	public String getAwardRemark() {
		return awardRemark;
	}

	public void setAwardRemark(String awardRemark) {
		this.awardRemark = awardRemark;
	}

	public String getActivityDesc() {
		return activityDesc;
	}

	public void setActivityDesc(String activityDesc) {
		this.activityDesc = activityDesc;
	}

	public Integer getShowFlag() {
		return showFlag;
	}

	public void setShowFlag(Integer showFlag) {
		this.showFlag = showFlag;
	}

	public Date getLimitEndDate() {
		return limitEndDate;
	}

	public void setLimitEndDate(Date limitEndDate) {
		this.limitEndDate = limitEndDate;
	}
}
