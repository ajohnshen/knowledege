package com.wu.knowledge.basedata.dictionary.model;

import com.wu.knowledge.common.model.BaseModel;

/**
 * 字典数据实体类(常量在数据库的存储载体)
 * Created by TZ on 2018/9/11.
 */
public class Dictionary extends BaseModel {
    private static final long serialVersionUID = 8947135683360049666L;
    /**
     * 编号
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 字典数据类型(本身也是字典数据)
     */
    private Dictionary type;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Dictionary getType() {
        return type;
    }

    public void setType(Dictionary type) {
        this.type = type;
    }
}
