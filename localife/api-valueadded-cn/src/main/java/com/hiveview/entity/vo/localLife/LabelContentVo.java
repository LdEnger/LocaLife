package com.hiveview.entity.vo.localLife;

import java.util.Date;
import java.util.List;

public class LabelContentVo {

    private String labelname;                //标签名称
    private List<ContentVo> contentList;


    public String getLabelname() {
        return labelname;
    }

    public void setLabelname(String labelname) {
        this.labelname = labelname;
    }

    public List<ContentVo> getContentList() {
        return contentList;
    }

    public void setContentList(List<ContentVo> contentList) {
        this.contentList = contentList;
    }
}
