package com.wu.knowledge.user.model;

import com.wu.knowledge.basedata.dictionary.model.Dictionary;
import com.wu.knowledge.common.model.BaseModel;
import com.wu.knowledge.file.model.File1;

import java.util.Date;

/**
 * 用户实体类(该用户为华转网通用用户)
 * Created by WSD on 2018/12/11.
 */
public class User extends BaseModel {
    private static final long serialVersionUID = -3389220201377333427L;
    /**
     * 用户名
     */
    private String user_name;

    /**
     * 用户信息
     */
    private String message;

    /**
     * 加密密码
     */
    private String password;

    /**
     * 手机
     */
    private String mobile;

    /**
     * 角色
     */
    private Dictionary role;

    /**
     * 性别
     */
    private Dictionary sex;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 学校
     */
    private String school;

    /**
     * 封面(文件ID)
     */
    private File1 cover;

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Dictionary getRole() {
        return role;
    }

    public void setRole(Dictionary role) {
        this.role = role;
    }

    public Dictionary getSex() {
        return sex;
    }

    public void setSex(Dictionary sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public File1 getCover() {
        return cover;
    }

    public void setCover(File1 cover) {
        this.cover = cover;
    }
}
