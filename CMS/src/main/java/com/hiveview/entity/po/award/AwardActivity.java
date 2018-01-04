package main.java.com.hiveview.entity.po.award;

import java.util.Date;
import java.util.List;

import com.hiveview.entity.Entity;

public class AwardActivity extends Entity{
	
	private Integer id;
    private String title;
    private Integer type;
    private String logoUrl;
    private String bgUrl;
    private String playingBgUrl;
    private String endTitle;
    private String infoBgUrl;
    private String endText;
    private String strBeginTime;
    private String strEndTime;
    private String activityDesc;
    private Integer awardWinRatio;
    private Integer awardWinAgain;
    private Integer awardPlayerType;
    private String strPayBeginTime;
    private String strPayEndTime;
    private Integer paySum;
    private String awardRemark;
    private Integer phoneBindType;
    private Integer playLimitDay;
    private String playPromptDay;
    private Integer playLimitTotal;
    private String playPromptTotal;
    private String playPromptWin;
    private String playPromptLost;
    private String awardList;
    
    private Date beginTime;
    private Date endTime;
    private Date payBeginTime;
    private Date payEndTime;
    private Integer sequence;
    private Integer showFlag;
    
    private List awards;
    
    public Long serverTime=new Date().getTime();
    
//    private Integer awardCodeType;
//    private Integer awardWinLimit;
    
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

	public String getPlayingBgUrl() {
		return playingBgUrl;
	}

	public void setPlayingBgUrl(String playingBgUrl) {
		this.playingBgUrl = playingBgUrl;
	}

	public String getEndTitle() {
		return endTitle;
	}

	public void setEndTitle(String endTitle) {
		this.endTitle = endTitle;
	}

	public String getInfoBgUrl() {
		return infoBgUrl;
	}

	public void setInfoBgUrl(String infoBgUrl) {
		this.infoBgUrl = infoBgUrl;
	}

	public String getEndText() {
		return endText;
	}

	public void setEndText(String endText) {
		this.endText = endText;
	}

	public String getStrBeginTime() {
		return strBeginTime;
	}

	public void setStrBeginTime(String strBeginTime) {
		this.strBeginTime = strBeginTime;
	}

	public String getStrEndTime() {
		return strEndTime;
	}

	public void setStrEndTime(String strEndTime) {
		this.strEndTime = strEndTime;
	}

	public String getActivityDesc() {
		return activityDesc;
	}

	public void setActivityDesc(String activityDesc) {
		this.activityDesc = activityDesc;
	}

	public Integer getAwardWinRatio() {
		return awardWinRatio;
	}

	public void setAwardWinRatio(Integer awardWinRatio) {
		this.awardWinRatio = awardWinRatio;
	}

	public Integer getAwardWinAgain() {
		return awardWinAgain;
	}

	public void setAwardWinAgain(Integer awardWinAgain) {
		this.awardWinAgain = awardWinAgain;
	}

	public Integer getAwardPlayerType() {
		return awardPlayerType;
	}

	public void setAwardPlayerType(Integer awardPlayerType) {
		this.awardPlayerType = awardPlayerType;
	}

	public String getStrPayBeginTime() {
		return strPayBeginTime;
	}

	public void setStrPayBeginTime(String strPayBeginTime) {
		this.strPayBeginTime = strPayBeginTime;
	}

	public String getStrPayEndTime() {
		return strPayEndTime;
	}

	public void setStrPayEndTime(String strPayEndTime) {
		this.strPayEndTime = strPayEndTime;
	}

	public Integer getPaySum() {
		return paySum;
	}

	public void setPaySum(Integer paySum) {
		this.paySum = paySum;
	}

	public String getAwardRemark() {
		return awardRemark;
	}

	public void setAwardRemark(String awardRemark) {
		this.awardRemark = awardRemark;
	}

	public Integer getPhoneBindType() {
		return phoneBindType;
	}

	public void setPhoneBindType(Integer phoneBindType) {
		this.phoneBindType = phoneBindType;
	}

	public Integer getPlayLimitDay() {
		return playLimitDay;
	}

	public void setPlayLimitDay(Integer playLimitDay) {
		this.playLimitDay = playLimitDay;
	}

	public String getPlayPromptDay() {
		return playPromptDay;
	}

	public void setPlayPromptDay(String playPromptDay) {
		this.playPromptDay = playPromptDay;
	}

	public Integer getPlayLimitTotal() {
		return playLimitTotal;
	}

	public void setPlayLimitTotal(Integer playLimitTotal) {
		this.playLimitTotal = playLimitTotal;
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

	public String getAwardList() {
		return awardList;
	}

	public void setAwardList(String awardList) {
		this.awardList = awardList;
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

//	public Integer getAwardCodeType() {
//		return awardCodeType;
//	}
//
//	public void setAwardCodeType(Integer awardCodeType) {
//		this.awardCodeType = awardCodeType;
//	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public Integer getShowFlag() {
		return showFlag;
	}

	public void setShowFlag(Integer showFlag) {
		this.showFlag = showFlag;
	}

	public List getAwards() {
		return awards;
	}

	public void setAwards(List awards) {
		this.awards = awards;
	}

//	public Integer getAwardWinLimit() {
//		return awardWinLimit;
//	}
//
//	public void setAwardWinLimit(Integer awardWinLimit) {
//		this.awardWinLimit = awardWinLimit;
//	}
	
}
