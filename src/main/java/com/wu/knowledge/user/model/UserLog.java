package com.wu.knowledge.user.model;

import com.wu.knowledge.common.model.BaseModel;

/**
 * 〈功能简述〉<br>
 * 〈用户记录〉
 *
 * @create create by WSD on 2018/12/20
 */
public class UserLog extends BaseModel {
    private static final long serialVersionUID = 2036832796638351539L;

    /**
     *用户登录ip
     */
    private String login_ip;

    public String getLogin_ip() {
        return login_ip;
    }

    public Integer[] getCreate_userIDs() {
        return create_userIDs;
    }

    public void setCreate_userIDs(Integer[] create_userIDs) {
        this.create_userIDs = create_userIDs;
    }

    /**
     *用户id数组
     */
    private Integer[]  create_userIDs;

    public void setLogin_ip(String login_ip) {
        this.login_ip = login_ip;
    }
}
