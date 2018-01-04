package com.hiveview.entity.vo.localLife;

public class LogoVo {


    private Integer id;
    private String name;                //LOGO名称
    private String logoUrl;              //logo地址

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

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }
}
