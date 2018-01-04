package main.java.com.hiveview.entity.py;

import com.hiveview.entity.Entity;

import java.io.Serializable;

public class CardPy extends Entity implements Serializable {
    private Integer  id;   //'批次号'
    private Integer  card_open;   //'开通方式(1 激活码  2 直冲)'
    private Integer  card_service;   //'服务(1 教育VIP)'
    private Integer  duration;   //'时长'
    private String  create_time;   //'创建时间'
    private Integer creator_id;   //创建者id
    private String  creator_name;   //'创建者'
    private String  effective_days_length;   //'激活码有效时长'
    private Integer  branch_id;   //'分公司ID'
    private String  branch_name;   // '分公司名称'
    private Integer card_num;//教育卡数量
    private int success_card; //添加成功卡数量
    private int error_card; //添加失败数量

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCard_open() {
        return card_open;
    }

    public void setCard_open(Integer card_open) {
        this.card_open = card_open;
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

    public Integer getCreator_id() {
        return creator_id;
    }

    public void setCreator_id(Integer creator_id) {
        this.creator_id = creator_id;
    }

    public String getCreator_name() {
        return creator_name;
    }

    public void setCreator_name(String creator_name) {
        this.creator_name = creator_name;
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

    public Integer getCard_num() {
        return card_num;
    }

    public void setCard_num(Integer card_num) {
        this.card_num = card_num;
    }

    public int getSuccess_card() {
        return success_card;
    }

    public void setSuccess_card(int success_card) {
        this.success_card = success_card;
    }

    public int getError_card() {
        return error_card;
    }

    public void setError_card(int error_card) {
        this.error_card = error_card;
    }
}
