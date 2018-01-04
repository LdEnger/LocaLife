package main.java.com.hiveview.entity.agio;

import java.io.Serializable;
import java.util.Date;

import com.hiveview.entity.Entity;

/**
 * Title：批量充值操作
 * Company：hiveview.com
 * Author：徐浩波
 * Email：xuhaobo@hiveview.com 
 * 2017-01-03 15:36
 */
public class AgioPackageBatch extends Entity implements Serializable{

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String fileUrl;
	private Integer branchId;
	private Integer zoneId;
	private Integer hallId;
	private Integer pkgId;
	private Integer state; //0:初始状态；1：开通中；2：开通完成
	private String opUserInfo;
	private String text;
	private String remark;
	private String downloadUrl;
	private String fileName;
	private Date createTime;
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the fileUrl
	 */
	public String getFileUrl() {
		return fileUrl;
	}
	/**
	 * @param fileUrl the fileUrl to set
	 */
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	/**
	 * @return the branchId
	 */
	public Integer getBranchId() {
		return branchId;
	}
	/**
	 * @param branchId the branchId to set
	 */
	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}
	/**
	 * @return the zoneId
	 */
	public Integer getZoneId() {
		return zoneId;
	}
	/**
	 * @param zoneId the zoneId to set
	 */
	public void setZoneId(Integer zoneId) {
		this.zoneId = zoneId;
	}
	/**
	 * @return the hallId
	 */
	public Integer getHallId() {
		return hallId;
	}
	/**
	 * @param hallId the hallId to set
	 */
	public void setHallId(Integer hallId) {
		this.hallId = hallId;
	}
	/**
	 * @return the pkgId
	 */
	public Integer getPkgId() {
		return pkgId;
	}
	/**
	 * @param pkgId the pkgId to set
	 */
	public void setPkgId(Integer pkgId) {
		this.pkgId = pkgId;
	}
	/**
	 * @return the state
	 */
	public Integer getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(Integer state) {
		this.state = state;
	}
	/**
	 * @return the opUserInfo
	 */
	public String getOpUserInfo() {
		return opUserInfo;
	}
	/**
	 * @param opUserInfo the opUserInfo to set
	 */
	public void setOpUserInfo(String opUserInfo) {
		this.opUserInfo = opUserInfo;
	}
	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}
	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}
	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * @return the downloadUrl
	 */
	public String getDownloadUrl() {
		return downloadUrl;
	}
	/**
	 * @param downloadUrl the downloadUrl to set
	 */
	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}
	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}
	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	



}
