package com.wu.knowledge.common.model;

import com.wu.knowledge.file.model.File1;
import com.wu.knowledge.user.model.User;

import java.io.Serializable;
import java.util.Date;

/**
 * 基本实体类(定义基础字段，所有实体都需要继承该实体)
 * Created by TZ on 2018/9/11.
 */
public class BaseModel implements Serializable {

    private static final long serialVersionUID = 2456174868725730429L;

    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 创建人
     */
    private User create_user;

    /**
     * 创建时间
     */
    private Date create_date;

    /**
     * 最后修改人
     */
    private User write_user;

    /**
     * 最后修改时间
     */
    private Date write_date;

    /**
     * 顺序
     */
    private Integer sequence;

    /**
     * 是否可用
     */
    private Boolean active;

    /**
     * 是否收藏
     */
    private Boolean iscollection;

    /**
     * 名称
     */
    private String name;

    /**
     * 封面(文件ID)
     */
    private File1 cover;

    /**
     * 关联用户
     */
    private User userID;

    /**
     * 简介
     */
    private String synopsis;

    /**
     * 备注
     */
    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getCreate_user() {
        return create_user;
    }

    public void setCreate_user(User create_user) {
        this.create_user = create_user;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }

    public User getWrite_user() {
        return write_user;
    }

    public void setWrite_user(User write_user) {
        this.write_user = write_user;
    }

    public Date getWrite_date() {
        return write_date;
    }

    public void setWrite_date(Date write_date) {
        this.write_date = write_date;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public Boolean getIscollection() {
        return iscollection;
    }

    public void setIscollection(Boolean iscollection) {
        this.iscollection = iscollection;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public File1 getCover() {
        return cover;
    }

    public void setCover(File1 cover) {
        this.cover = cover;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public User getUserID() {
        return userID;
    }

    public void setUserID(User userID) {
        this.userID = userID;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
