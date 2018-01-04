package com.hiveview.entity.localLife;

import com.hiveview.entity.Entity;

public class AreaInfo extends Entity {
	
	private Integer id;
	private Integer fid;
	private Integer is_effective;
	private String str;
	private Integer type;
    private Integer level;
    private Integer userId;


	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getFid() {
		return fid;
	}
	public void setFid(Integer fid) {
		this.fid = fid;
	}
	public Integer getIs_effective() {
		return is_effective;
	}
	public void setIs_effective(Integer is_effective) {
		this.is_effective = is_effective;
	}
	public String getStr() {
		return str;
	}
	public void setStr(String str) {
		this.str = str;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
