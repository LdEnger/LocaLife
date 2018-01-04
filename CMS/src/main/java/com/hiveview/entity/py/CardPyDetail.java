package main.java.com.hiveview.entity.py;

import com.hiveview.entity.Entity;

import java.io.Serializable;

public class CardPyDetail extends Entity implements Serializable{

    private Integer id;   // '主键ID'
    private String mac;   // 'mac',
    private String sn;   // 'sn',
    private String user_id;   // '用户id  盒子的 用户id',
    private Integer card_open;   // '开通方式(1 激活码  2 直冲)',
    private String card_order_py;   // '激活码',
    private Integer use_status;   // '使用状态(1 未使用 2已使用)',
    private Integer card_service;   // '服务(1 教育VIP)',
    private Integer duration;   // '时长（1 3个月 2 3个月 3 6个月 4 12个月）',
    private String create_time;   // '制卡时间',
    private String open_time;   // '开通时间',
    private String end_time;   // '结束时间   用户产品 到期的时间',
    private String effective_days_length;   // '激活码有效时长',
    private Integer branch_id;   // '分公司ID',
    private String branch_name;   // '分公司名称',
    private Integer py_id;   // '批次号',

    private String create_startTime;//查询开始时间
    private String create_endTime;//查询结束时间
    private Integer card_cancel;//卡是否注销

    public Integer getCard_cancel() {
        return card_cancel;
    }

    public void setCard_cancel(Integer card_cancel) {
        this.card_cancel = card_cancel;
    }

    public String getCreate_startTime() {
        return create_startTime;
    }

    public void setCreate_startTime(String create_startTime) {
        this.create_startTime = create_startTime;
    }

    public String getCreate_endTime() {
        return create_endTime;
    }

    public void setCreate_endTime(String create_endTime) {
        this.create_endTime = create_endTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Integer getCard_open() {
        return card_open;
    }

    public void setCard_open(Integer card_open) {
        this.card_open = card_open;
    }

    public String getCard_order_py() {
        return card_order_py;
    }

    public void setCard_order_py(String card_order_py) {
        this.card_order_py = card_order_py;
    }

    public Integer getUse_status() {
        return use_status;
    }

    public void setUse_status(Integer use_status) {
        this.use_status = use_status;
    }

    public Integer getCard_service() {
        return card_service;
    }

    public void setCard_service(Integer card_service) {
        this.card_service = card_service;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getOpen_time() {
        return open_time;
    }

    public void setOpen_time(String open_time) {
        this.open_time = open_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getEffective_days_length() {
        return effective_days_length;
    }

    public void setEffective_days_length(String effective_days_length) {
        this.effective_days_length = effective_days_length;
    }

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

    public Integer getPy_id() {
        return py_id;
    }

    public void setPy_id(Integer py_id) {
        this.py_id = py_id;
    }
}
