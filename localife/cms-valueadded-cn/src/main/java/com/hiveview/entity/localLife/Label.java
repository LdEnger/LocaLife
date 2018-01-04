package com.hiveview.entity.localLife;

import com.hiveview.entity.Entity;

import java.util.Date;

public class Label extends Entity{

	private Integer id;
    private Integer contentId;         //内容ID
    private String countryId;         //国家ID
    private String provinceId;        //省ID
    private String provinceName;        //省份名称
    private String cityId;             //市ID
    private String name;                //标签名称
    private Integer state;      //0:无效    1：有效
    private Integer onlineType;        //0:下线   1：上线
    private Integer seq;                //排序
    private Date createdTime;          //创建时间
    private Integer createdBy;          //创建人id
    private Date updatedTime;          //更新时间
    private Integer updatedBy;         //更新人id
    private String cityName;         //城市
    private Integer userId;

    private String zoneIDs;


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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getOnlineType() {
        return onlineType;
    }

    public void setOnlineType(Integer onlineType) {
        this.onlineType = onlineType;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
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

    public Integer getContentId() {
        return contentId;
    }

    public void setContentId(Integer contentId) {
        this.contentId = contentId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
