package main.java.com.hiveview.entity.bo;

import java.util.List;

@SuppressWarnings("rawtypes")
public class Data {

	/**
	 * 操作代码 1:成功 ,0:失败
	 */
	private Integer code=1;
	
	/**
	 * categories X轴信息
	 */
	private List categories;
	
	private List<DataSeries> series;

	private String msg;
	
	public Data() {}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public List getCategories() {
		return categories;
	}

	public void setCategories(List categories) {
		this.categories = categories;
	}

	public List<DataSeries> getSeries() {
		return series;
	}

	public void setSeries(List<DataSeries> series) {
		this.series = series;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
