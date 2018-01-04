package com.hiveview.entity.po.award;

import java.io.Serializable;

import com.hiveview.entity.Entity;

public class AwardActivityType extends Entity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6940152648812121031L;

	private Integer id;
	private Integer typeCode;
	private String typeName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(Integer typeCode) {
		this.typeCode = typeCode;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
}
