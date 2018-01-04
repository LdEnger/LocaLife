package main.java.com.hiveview.entity.cardpack;

import com.hiveview.entity.Entity;

import java.io.Serializable;


public class CardPack extends Entity implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Integer id; // 主键id
	private String dm_card_val;      // '大麦卡密',
	private String source_card_val; // '来源卡的卡密-目前只有 腾讯',
	private String card_provider_id;//'卡密来源表的 id   腾讯卡就有这个字段  其他类型无',
	private int card_provider; //'卡提供方    0 腾讯连续包月卡  1 爱奇艺包月卡',
	private int if_vip; // '0 不开vip  1 开vip',
	private Integer vip_days; //'开通vip 的天数  ',
	private int status; // '0 未使用     1 已使用    ',
	private int extract_status; //提取状态  0未提出   1已提取
	private int create_status; //制卡状态  0未制卡   1已制卡
	private String create_time; // '卡密 生成时间',
	private String open_time; // '卡密开通时间 ',
	private String end_time; // '卡密的 到期时间 '
	private String update_time;  // 卡密的更新时间
	private int renew_num; //卡 可续费的次数
	private int renewed_num;// 卡 已经续费的次数
	private String mac;//'开通的 盒子mac ',
	private String sn;//开通的 sn',
	private String user_id;// 盒子 用户id


	private Integer creator_id; //批量生产卡的 操作用户id
	private String  creator_name;//批量生产卡的 操作用户名

	private Integer export_id;   //导出者 id
	private String  export_name; //导出者姓名

	private Integer branch_id;//分公司id
	private String  branch_name;//分公司名

	private Integer cardNum;//大麦卡数量 用于新增
	private String companyName;//分公司名称



	public String createStopTime; //卡生成时间,用于查询
	public String activationStopTime; //激活时间，用于查询

	private String create_startTime;// 制卡 时间  开始    用于查询
	private String create_endTime; //制卡时间  结束 用于查询

	private String open_startTime;// 开通 时间  开始 用于查询
	private String open_endTime; //开通时间  结束 用于查询

	private String select_renew_num; //开通时间  结束 用于查询

	public String citys;//分公司列表 用于查询
	private String versions ;//3.2版本标识

	public String getSelect_renew_num() {
		return select_renew_num;
	}

	public void setSelect_renew_num(String select_renew_num) {
		this.select_renew_num = select_renew_num;
	}

	public String getOpen_startTime() {
		return open_startTime;
	}

	public void setOpen_startTime(String open_startTime) {
		this.open_startTime = open_startTime;
	}

	public String getOpen_endTime() {
		return open_endTime;
	}

	public void setOpen_endTime(String open_endTime) {
		this.open_endTime = open_endTime;
	}

	public String getCreate_startTime() {
		return create_startTime;
	}

	public void setCreate_startTime(String create_startTime) {
		this.create_startTime = create_startTime;
	}

	public String getCreate_endTime() {
		return create_endTime;
	}

	public void setCreate_endTime(String create_endTime) {
		this.create_endTime = create_endTime;
	}

	public Integer getBranch_id() {
		return branch_id;
	}

	public void setBranch_id(Integer branch_id) {
		this.branch_id = branch_id;
	}

	public String getBranch_name() {
		return branch_name;
	}

	public void setBranch_name(String branch_name) {
		this.branch_name = branch_name;
	}

	public Integer getExport_id() {
		return export_id;
	}

	public void setExport_id(Integer export_id) {
		this.export_id = export_id;
	}

	public String getExport_name() {
		return export_name;
	}

	public void setExport_name(String export_name) {
		this.export_name = export_name;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Integer getCreator_id() {
		return creator_id;
	}

	public void setCreator_id(Integer creator_id) {
		this.creator_id = creator_id;
	}

	public String getCreator_name() {
		return creator_name;
	}

	public void setCreator_name(String creator_name) {
		this.creator_name = creator_name;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDm_card_val() {
		return dm_card_val;
	}

	public void setDm_card_val(String dm_card_val) {
		this.dm_card_val = dm_card_val;
	}

	public String getSource_card_val() {
		return source_card_val;
	}

	public void setSource_card_val(String source_card_val) {
		this.source_card_val = source_card_val;
	}

	public String getCard_provider_id() {
		return card_provider_id;
	}

	public void setCard_provider_id(String card_provider_id) {
		this.card_provider_id = card_provider_id;
	}

	public int getCard_provider() {
		return card_provider;
	}

	public void setCard_provider(int card_provider) {
		this.card_provider = card_provider;
	}

	public int getIf_vip() {
		return if_vip;
	}

	public void setIf_vip(int if_vip) {
		this.if_vip = if_vip;
	}

	public Integer getVip_days() {
		return vip_days;
	}

	public void setVip_days(Integer vip_days) {
		this.vip_days = vip_days;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getOpen_time() {
		return open_time;
	}

	public void setOpen_time(String open_time) {
		this.open_time = open_time;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getCreateStopTime() {
		return createStopTime;
	}

	public void setCreateStopTime(String createStopTime) {
		this.createStopTime = createStopTime;
	}

	public String getActivationStopTime() {
		return activationStopTime;
	}

	public void setActivationStopTime(String activationStopTime) {
		this.activationStopTime = activationStopTime;
	}

	public String getCitys() {
		return citys;
	}

	public void setCitys(String citys) {
		this.citys = citys;
	}

	public String getVersions() {
		return versions;
	}

	public void setVersions(String versions) {
		this.versions = versions;
	}

	public int getExtract_status() {
		return extract_status;
	}

	public void setExtract_status(int extract_status) {
		this.extract_status = extract_status;
	}

	public int getCreate_status() {
		return create_status;
	}

	public void setCreate_status(int create_status) {
		this.create_status = create_status;
	}

	public Integer getCardNum() {
		return cardNum;
	}

	public void setCardNum(Integer cardNum) {
		this.cardNum = cardNum;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public int getRenew_num() {
		return renew_num;
	}

	public void setRenew_num(int renew_num) {
		this.renew_num = renew_num;
	}

	public int getRenewed_num() {
		return renewed_num;
	}

	public void setRenewed_num(int renewed_num) {
		this.renewed_num = renewed_num;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
}
