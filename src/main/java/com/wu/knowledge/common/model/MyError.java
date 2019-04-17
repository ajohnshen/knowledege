package com.wu.knowledge.common.model;


import com.wu.knowledge.common.constant.MyConstant;

import java.io.Serializable;

/**
 * 错误信息实体类(封装了错误编号和错误信息)
 * Created by TZ on 2018/9/11.
 */
public class MyError implements Serializable {

    private static final long serialVersionUID = 41231753486458549L;
    /**
     * 错误编号（0表示正确，1表示用户未登陆，2表示系统错误）
     */
    private Integer err_code = MyConstant.returnSuccessCode;
    /**
     * 错误消息
     */
    private String err_msg = "";


    public String getErr_msg() {
        return err_msg;
    }

    public void setErr_msg(String err_msg) {
        this.err_msg = err_msg;
    }

    public Integer getErr_code() {
        return err_code;
    }

    public void setErr_code(Integer err_code) {
        this.err_code = err_code;
    }

    @Override
    public String toString() {
        return "MyError [err_msg=" + err_msg + ", err_code=" + err_code + "]";
    }

}
