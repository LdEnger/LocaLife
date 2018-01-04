package main.java.com.hiveview.entity.vo.award;

import java.util.Date;

import com.hiveview.entity.Entity;

public class AwardActivityVo extends Entity{

	private int id;//id
    private String title;//标题
    private int type;//活动类型，砸金蛋，转盘，老虎机
    private int sequence;//顺序
    private Date beginTime;//活动开始时间
    private Date endTime;//活动结束时间
    private String logoUrl;//活动LOGO图片地址
    private String bgUrl;//北京
    private String beginProfileUrl;//活动开始封面
    private String endProfileUrl;//活动结束封面
    private String endTitle;//活动结束标题
    private String endText;//活动结束说明
    private int playLimitDay;//一天抽奖上限
    private int playLimitTotal;//总过每个参与用户抽奖限制数
    private String playPromptDay;//每日提示
    private String playPromptTotal;//超过活动次数提示
    private String playPromptWin; //中奖提示
    private String playPromptLost;//未中奖提示
    private int phoneBindType;//电话绑定方式
    private int awardCodeType;//中奖码生成类型
    private int awardWinRatio;//中奖概率（多少次抽奖出一个奖品）
    private int awardWinLimit;//每日中奖上限
    private int awardWinAgain;//是否可以重复中奖
    private int awardPlayerType;//抽奖参与用户
    private Date payBeginTime;//奖品派发开始时间
    private Date payEndTime;//奖品派发结束时间
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
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
	public String getBeginProfileUrl() {
		return beginProfileUrl;
	}
	public void setBeginProfileUrl(String beginProfileUrl) {
		this.beginProfileUrl = beginProfileUrl;
	}
	public String getEndProfileUrl() {
		return endProfileUrl;
	}
	public void setEndProfileUrl(String endProfileUrl) {
		this.endProfileUrl = endProfileUrl;
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
    
}
