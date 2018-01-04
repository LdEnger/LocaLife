package com.hiveview.entity.localLife;

import com.hiveview.entity.Entity;

public class ContentVersionScreenshot extends Entity{

	private Integer id;
    private Integer versionId;                    //内容ID
    private String screenshotUrl;            //应用截图URL
    private Integer seq;            //排序



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVersionId() {
        return versionId;
    }

    public void setVersionId(Integer versionId) {
        this.versionId = versionId;
    }

    public String getScreenshotUrl() {
        return screenshotUrl;
    }

    public void setScreenshotUrl(String screenshotUrl) {
        this.screenshotUrl = screenshotUrl;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }
}
