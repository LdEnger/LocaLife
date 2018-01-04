package main.java.com.hiveview.entity.vo;

import java.io.Serializable;

public class ProductPackageVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7044639998099788459L;

	private int productId;// 活动id
	private String productName;// 活动名称

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

}
