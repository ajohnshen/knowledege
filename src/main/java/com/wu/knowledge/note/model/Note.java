package com.wu.knowledge.note.model;

import com.wu.knowledge.basedata.dictionary.model.Dictionary;
import com.wu.knowledge.common.model.BaseModel;

/**
 * 〈功能简述〉<br>
 * 〈笔记本〉
 *
 * @create create by WSD on 2019/1/28
 */
public class Note extends BaseModel {

    /**
     * 标题
     */
    private String title;

    /**
     * 笔记本类型
     */
    private Dictionary notetype;

    /**
     * 笔记本类型数组
     */
    private Integer[] notetypeIDs;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Dictionary getNotetype() {
        return notetype;
    }

    public void setNotetype(Dictionary notetype) {
        this.notetype = notetype;
    }

    public Integer[] getNotetypeIDs() {
        return notetypeIDs;
    }

    public void setNotetypeIDs(Integer[] notetypeIDs) {
        this.notetypeIDs = notetypeIDs;
    }
}
