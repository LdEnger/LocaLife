package com.hiveview.entity.vo.localLife;

import java.sql.Timestamp;
import java.util.List;

public class ContentVersionVo {

    private String versionNumber;                //版本号
    private String versionDescribe;             //版本介绍
    private String versionSize;      //版本大小
    private String downloadUrl;            //视频下载URL
    private String updatedTime;            //应用更新日期
    private List<ContentVersionScreenshotVo> screenshotList;


    public String getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(String updatedTime) {
        this.updatedTime = updatedTime;
    }

    public List<ContentVersionScreenshotVo> getScreenshotList() {
        return screenshotList;
    }

    public void setScreenshotList(List<ContentVersionScreenshotVo> screenshotList) {
        this.screenshotList = screenshotList;
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
}
