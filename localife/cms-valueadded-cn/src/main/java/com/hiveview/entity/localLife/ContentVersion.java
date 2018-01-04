package com.hiveview.entity.localLife;

import com.hiveview.entity.Entity;

import java.util.Date;

public class ContentVersion extends Entity{

	private Integer id;
    private Integer contentId;                    //内容ID
    private String versionNumber;                //版本号
    private String versionDescribe;             //版本介绍

    public Integer getPanDuan() {
        return panDuan;
    }

    public void setPanDuan(Integer panDuan) {
        this.panDuan = panDuan;
    }

    private String versionSize;      //版本大小
    private String downloadUrl;            //视频下载URL
    private Date updatedTime;            //应用更新日期
    private String updatedBy;        //更新人
    private Integer panDuan;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getContentId() {
        return contentId;
    }

    public void setContentId(Integer contentId) {
        this.contentId = contentId;
    }

    public String getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(String versionNumber) {
        this.versionNumber = versionNumber;
    }

    public String getVersionDescribe() {
        return versionDescribe;
    }

    public void setVersionDescribe(String versionDescribe) {
        this.versionDescribe = versionDescribe;
    }

    public String getVersionSize() {
        return versionSize;
    }

    public void setVersionSize(String versionSize) {
        this.versionSize = versionSize;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
}
