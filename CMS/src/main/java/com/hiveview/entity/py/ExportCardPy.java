package main.java.com.hiveview.entity.py;

import java.io.Serializable;

public class ExportCardPy implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 操作代码 1:成功 ,0:失败
     */
    private Integer code=1;			//状态码   1成功   0失败
    private String  msg;			//反馈信息
    private String  download_url;  //下载地址
    private Integer py_id;//批次号

    public Integer getPy_id() {
        return py_id;
    }

    public void setPy_id(Integer py_id) {
        this.py_id = py_id;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDownload_url() {
        return download_url;
    }

    public void setDownload_url(String download_url) {
        this.download_url = download_url;
    }
}
