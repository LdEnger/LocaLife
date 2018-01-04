package com.hiveview.entity.localLife;

import java.util.Date;

public class DeviceInfo {
	
	private String area_id;
	private Integer auth_id;
	private String city_id;
	private Integer country_id;
	private Integer device_state;
	private Date create_time;
	private Date update_time;
	private Date stringTime;
	private String device_mac;
	private String device_sn;
	private String hardware_name;
	private String producer_name;
	private String resultex;

    public String getArea_id() {
        return area_id;
    }

    public void setArea_id(String area_id) {
        this.area_id = area_id;
    }

    public Integer getAuth_id() {
        return auth_id;
    }

    public void setAuth_id(Integer auth_id) {
        this.auth_id = auth_id;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public Integer getCountry_id() {
        return country_id;
    }

    public void setCountry_id(Integer country_id) {
        this.country_id = country_id;
    }

    public Integer getDevice_state() {
        return device_state;
    }

    public void setDevice_state(Integer device_state) {
        this.device_state = device_state;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    public Date getStringTime() {
        return stringTime;
    }

    public void setStringTime(Date stringTime) {
        this.stringTime = stringTime;
    }

    public String getDevice_mac() {
        return device_mac;
    }

    public void setDevice_mac(String device_mac) {
        this.device_mac = device_mac;
    }

    public String getDevice_sn() {
        return device_sn;
    }

    public void setDevice_sn(String device_sn) {
        this.device_sn = device_sn;
    }

    public String getHardware_name() {
        return hardware_name;
    }

    public void setHardware_name(String hardware_name) {
        this.hardware_name = hardware_name;
    }

    public String getProducer_name() {
        return producer_name;
    }

    public void setProducer_name(String producer_name) {
        this.producer_name = producer_name;
    }

    public String getResultex() {
        return resultex;
    }

    public void setResultex(String resultex) {
        this.resultex = resultex;
    }
}
