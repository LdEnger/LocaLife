package com.hiveview.entity;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Antony
 * Date: 16-6-4
 * Time: 下午2:54
 * To change this template use File | Settings | File Templates.
 */
public class NewsCombotreeBean {

    public int id;
    public int parentId;
    public String text;
    public List<NewsCombotreeBean> children;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<NewsCombotreeBean> getChildren() {
        return children;
    }

    public void setChildren(List<NewsCombotreeBean> children) {
        this.children = children;
    }
}
