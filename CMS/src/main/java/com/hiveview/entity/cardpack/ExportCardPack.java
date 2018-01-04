package main.java.com.hiveview.entity.cardpack;

import com.hiveview.entity.Entity;

import java.io.Serializable;


public class ExportCardPack   implements  Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 操作代码 1:成功 ,0:失败
	 */
	private Integer code=1;			//状态码   1成功   0失败
	private String  msg;			//反馈信息
	private String  download_url;  //下载地址
	private Integer export_cardNum;//要导出的大麦卡 数量
	private Integer export_cardProvider;//'卡提供方    0 腾讯连续包月卡  1 爱奇艺包月卡',
	private Integer export_renewNum; //  卡 可续费的次数
	private String export_hiddenUserName;// 用户名称
	private String export_hiddenUserId; // 操作CMS 用户的 用户id   暂时没使用
	private String export_hiddenRoleId ; //操作用户的 角色ID		 暂时没使用
	private Integer maxId;//最大 id  用于更新导出 信息

	private String export_name;// 记录操作用户的 用户名
	private String export_id;// 记录操作用户的   用户id
	private String export_version;// 记录操作用户的  导出版本  为文件名称

	private Integer branch_id;// 分公司ID
	private String  branch_name; //分公司名


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

	public String getExport_name() {
		return export_name;
	}

	public void setExport_name(String export_name) {
		this.export_name = export_name;
	}

	public String getExport_id() {
		return export_id;
	}

	public void setExport_id(String export_id) {
		this.export_id = export_id;
	}

	public String getExport_version() {
		return export_version;
	}

	public void setExport_version(String export_version) {
		this.export_version = export_version;
	}

	public Integer getMaxId() {
		return maxId;
	}

	public void setMaxId(Integer maxId) {
		this.maxId = maxId;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getExport_hiddenUserName() {
		return export_hiddenUserName;
	}

	public void setExport_hiddenUserName(String export_hiddenUserName) {
		this.export_hiddenUserName = export_hiddenUserName;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getDownload_url() {
		return download_url;
	}

	public void setDownload_url(String download_url) {
		this.download_url = download_url;
	}

	public String getExport_hiddenUserId() {
		return export_hiddenUserId;
	}

	public void setExport_hiddenUserId(String export_hiddenUserId) {
		this.export_hiddenUserId = export_hiddenUserId;
	}

	public String getExport_hiddenRoleId() {
		return export_hiddenRoleId;
	}

	public void setExport_hiddenRoleId(String export_hiddenRoleId) {
		this.export_hiddenRoleId = export_hiddenRoleId;
	}

	public Integer getExport_cardNum() {
		return export_cardNum;
	}

	public void setExport_cardNum(Integer export_cardNum) {
		this.export_cardNum = export_cardNum;
	}

	public Integer getExport_cardProvider() {
		return export_cardProvider;
	}

	public void setExport_cardProvider(Integer export_cardProvider) {
		this.export_cardProvider = export_cardProvider;
	}

	public Integer getExport_renewNum() {
		return export_renewNum;
	}

	public void setExport_renewNum(Integer export_renewNum) {
		this.export_renewNum = export_renewNum;
	}
}
