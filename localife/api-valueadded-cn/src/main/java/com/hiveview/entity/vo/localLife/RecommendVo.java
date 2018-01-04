package com.hiveview.entity.vo.localLife;

import com.hiveview.entity.Entity;

import java.util.Date;

public class RecommendVo {


    private Integer id;
    private Integer contentId;         //内容ID
    private String name;                //推荐位名称
    private String picUrl;              //推荐位图片
    private Integer seq;                //排序
    private Integer typeId;                //类型 1:应用  2:视频广告  3:图片广告
    private String contentIconUrl;                //内容iconUrl
    private String videoUrl;                //视频播放地址的URL
    private String panoramaUrl;                //全景图URL


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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getContentIconUrl() {
        return contentIconUrl;
    }

    public void setContentIconUrl(String contentIconUrl) {
        this.contentIconUrl = contentIconUrl;
    }
}
