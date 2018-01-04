package main.java.com.hiveview.entity.card;

import java.io.Serializable;
import java.util.Date;

import com.hiveview.entity.Entity;


public class ExcelFile extends Entity implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id; //
	private String fileName;
	private String path;
	private int type;//1.新boss产品类 2.新boss开通类 3.老boss产品类4老boss开通类 99错误类型的数据
	private int status;//执行处理任务状态1未开始， 2正在进行 3 已完成
	private int times;//执行任务次数
	private String msg;//说明
	private Date ctime;//添加时间
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}
	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}
	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}
	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}
	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}
	/**
	 * @return the times
	 */
	public int getTimes() {
		return times;
	}
	/**
	 * @param times the times to set
	 */
	public void setTimes(int times) {
		this.times = times;
	}
	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}
	/**
	 * @param msg the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}
	/**
	 * @return the ctime
	 */
	public Date getCtime() {
		return ctime;
	}
	/**
	 * @param ctime the ctime to set
	 */
	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}
}
