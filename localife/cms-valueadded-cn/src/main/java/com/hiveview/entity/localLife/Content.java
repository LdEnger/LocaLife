package com.hiveview.entity.localLife;

import com.hiveview.entity.Entity;
import com.singularsys.jep.functions.Str;

import java.util.Date;
import java.util.List;

public class Content extends Entity{

	private Integer id;
    private String name;                //内容名称
    private String countryId;         //国家ID
    private String provinceId;        //省ID
    private String provinceName;       //省份名称
    private String cityId;             //市ID
    private String cityName;             //市名称
    private Integer typeId;             //类型 1:应用  2:视频广告  3:图片广告
    private String developerName;      //开发商
    private String category;            //应用类别
    private String describe;            //内容提要/视频介绍
    private String picUrl;              //应用图标/视频图标
    private String panoramaUrl;        //全景图片
    private String videoUrl;           //视频URL
    private String mark;           //包名-标记
    private Integer isEffective;      //0:无效    1：有效
    private Integer onlineType;        //0:下线   1：上线
    private Date createdTime;          //创建时间
    private Integer createdBy;          //创建人id
    private Date updatedTime;          //更新时间
    private Integer updatedBy;         //更新人id
    private String describes;

    private Integer state;
    private Integer recommendId;         //推荐ID
    private Integer recommendExist;         //查询是否包含当前内容  0：不包含  1：包含


    private Integer labelId;         //标签ID
    private Integer labelExist;         //查询是否包含当前内容  0：不包含  1：包含
    private Integer labelSeq;         //标签排序
    private Integer userId;

    private String zoneIDs;

    public String getDescribes() {
        return describes;
    }

    public void setDescribes(String describes) {
        this.describes = describes;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getZoneIDs() {
        return zoneIDs;
    }

    public void setZoneIDs(String zoneIDs) {
        this.zoneIDs = zoneIDs;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getLabelSeq() {
        return labelSeq;
    }

    public void setLabelSeq(Integer labelSeq) {
        this.labelSeq = labelSeq;
    }

    public Integer getLabelId() {
        return labelId;
    }

    public void setLabelId(Integer labelId) {
        this.labelId = labelId;
    }

    public Integer getLabelExist() {
        return labelExist;
    }

    public void setLabelExist(Integer labelExist) {
        this.labelExist = labelExist;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getRecommendExist() {
        return recommendExist;
    }

    public void setRecommendExist(Integer recommendExist) {
        this.recommendExist = recommendExist;
    }

    public Integer getRecommendId() {
        return recommendId;
    }

    public void setRecommendId(Integer recommendId) {
        this.recommendId = recommendId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public Integer getIsEffective() {
        return isEffective;
    }

    public void setIsEffective(Integer isEffective) {
        this.isEffective = isEffective;
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

    public Integer getEffective() {
        return isEffective;
    }

    public void setEffective(Integer effective) {
        isEffective = effective;
    }

    public Integer getOnlineType() {
        return onlineType;
    }

    public void setOnlineType(Integer onlineType) {
        this.onlineType = onlineType;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }
}
