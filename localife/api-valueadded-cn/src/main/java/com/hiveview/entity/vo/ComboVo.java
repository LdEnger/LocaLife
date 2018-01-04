package com.hiveview.entity.vo;

/**
 * Created by IntelliJ IDEA.
 * User:  valjean
 * Date:  2016/4/26
 * Time:  16:36
 * To change this template use File | Settings | File Templates.
 */
public class ComboVo {
    private String text;
    private String value;
    private String proName;
    private String proCode;
    private String cityName;
    private String cityCode;

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getProCode() {
        return proCode;
    }

    public void setProCode(String proCode) {
        this.proCode = proCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
