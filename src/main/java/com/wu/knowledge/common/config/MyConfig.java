package com.wu.knowledge.common.config;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

/**
 * 配置文件类(初始化配置文件参数，)
 * Created by TZ on 2018/9/11.
 */
public class MyConfig {

    private static PropertiesConfiguration config;

    static {
        try {
            config = new PropertiesConfiguration("myconfig.properties");
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }

    /**
     * 默认页码
     **/
    public static Integer getDefaultPageNo() {
        return config.getInteger("defaultPageNo", 0);
    }

    /**
     * 默认查询条数
     */
    public static Integer getDefaultPageSize() {
        return config.getInteger("defaultPageSize", 10);
    }

    /**
     * 最大查询条数
     */
    public static Integer getMaxPageSize() {
        return config.getInteger("maxPageSize", 1000);
    }

    /**
     * 不拦截路径前缀
     */
    public static String notInterceptPrefix() {
        return config.getString("notInterceptPrefix");
    }

    /**
     * 获取文件服务器地址
     */
    public static String getFileServerUrl() {
        return config.getString("fileServerUrl");
    }

    /**
     * 短信accessKeyId
     */
    public static String getAccessKeyId() {
        return config.getString("accessKeyId");
    }

    /**
     * 短信accessKeySecret
     */
    public static String getAccessKeySecret() {
        return config.getString("accessKeySecret");
    }

    /**
     * 路演短信签名
     */
    public static String getRoadshowSignName() {
        return config.getString("roadshowSignName");
    }

    /**
     * 华转短信签名
     */
    public static String getHZSignName() {
        return config.getString("HZSignName");
    }

    /**
     * 身份验证短信模板Code
     */
    public static String getAuthenticationCode() {
        return config.getString("authenticationCode");
    }

    /**
     * 用户登录短信模板Code
     */
    public static String getUserLoginCode() {
        return config.getString("userLoginCode");
    }

    /**
     * 登录异常短信模板Code
     */
    public static String getLoginExceptionCode() {
        return config.getString("loginExceptionCode");
    }

    /**
     * 用户注册短信模板Code
     */
    public static String getUserRegisterCode() {
        return config.getString("userRegisterCode");
    }

    /**
     * 修改密码短信模板Code
     */
    public static String getChangePasswordCode() {
        return config.getString("changePasswordCode");
    }

    /**
     * 信息变更短信模板Code
     */
    public static String getInformationChangeCode() {
        return config.getString("informationChangeCode");
    }

    /**
     * 小程序咨询模板Code
     */
    public static String getMiniProgramConsultCode() {
        return config.getString("miniProgramConsultCode");
    }

    /**
     * 微信公众号报名验证码
     */
    public static String getWXApplicationEnterCode() {
        return config.getString("WXApplicationEnterCode");
    }

    /**
     * 微信公众号报名成功通知
     */
    public static String getWXApplicationEnterSuccessCode() {
        return config.getString("WXApplicationEnterSuccessCode");
    }

    /**
     * 手机验证码发送间隔(秒)
     */
    public static Integer getMobileCodeInterval() {
        return config.getInteger("mobileCodeInterval", 55);
    }

    /**
     * 手机验证码超时时间(秒)
     */
    public static Integer getMobileCodeOvertime() {
        return config.getInteger("mobileCodeOvertime", 180);
    }

    /**
     * 获取阿里云OSS区域节点
     */
    public static String getEndpoint() {
        return config.getString("endpoint");
    }

    /**
     * 获取阿里云OSS Bucket
     */
    public static String getBucket() {
        return config.getString("bucket");
    }

    /**
     * 获取路演PPT图片前缀
     */
    public static String getPPTImagePrefix() {
        return config.getString("PPTImagePrefix");
    }


}
