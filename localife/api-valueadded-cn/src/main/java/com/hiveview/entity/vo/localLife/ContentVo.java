package com.hiveview.entity.vo.localLife;

import com.hiveview.entity.Entity;

import java.util.Date;
import java.util.List;

public class ContentVo {

	private Integer contentId;
    private String contentName;                //内容名称
    private Integer typeId;             //类型 1:应用  2:视频广告  3:图片广告
    private String developerName;      //开发商
    private String category;            //应用类别
    private String describe;            //内容提要/视频介绍
    private String picUrl;              //应用图标/视频图标
    private String mark;              //包名
    private String panoramaUrl;        //全景图片
    private String videoUrl;           //视频URL
    private List<ContentVersionVo> versionList;  //应用版本

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public List<ContentVersionVo> getVersionList() {
        return versionList;
    }

    public void setVersionList(List<ContentVersionVo> versionList) {
        this.versionList = versionList;
    }

    public Integer getContentId() {
        return contentId;
    }

    public void setContentId(Integer contentId) {
        this.contentId = contentId;
    }

    public String getContentName() {
        return contentName;
    }

    public void setContentName(String contentName) {
        this.contentName = contentName;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getDeveloperName() {
        return developerName;
    }

    public void setDeveloperName(String developerName) {
        this.developerName = developerName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getPanoramaUrl() {
        return panoramaUrl;
    }

    public void setPanoramaUrl(String panoramaUrl) {
        this.panoramaUrl = panoramaUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
}
