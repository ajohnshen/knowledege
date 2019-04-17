package com.wu.knowledge.user.service;

import com.wu.knowledge.user.model.User;
import com.wu.knowledge.user.model.UserLog;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 用户服务层接口
 * Created by WSD on 2018/12/11.
 */
public interface IUserService {

    /**
     * 创建用户
     *
     * @param user          待创建的用户实体对象
     * @param operationUser 对用户进行操作的用户实体对象
     */
    Map<String, Object> createUser(User user, User operationUser);

    /**
     * 更新用户
     *
     * @param user          待创建的用户实体对象
     * @param operationUser 对用户进行操作的用户实体对象
     */
    Map<String, Object> updateUser(User user, User operationUser);

    /**
     * 条件查询用户
     *
     * @param user     用户对象
     * @param pageNo   页码(从0开始)
     * @param pageSize 查询数
     */
    Map<String, Object> getUsers(User user, User selectUser, Integer pageNo, Integer pageSize);

    /**
     * 用户注册
     *
     * @param user 用户对象
     */
    Map<String, Object> register(User user) throws Exception;

    /**
     * 用户登录(密码/验证码登录方式通用)
     * (目前与管理员登录接口是通用的
     * 数据库的查询字段一旦存在用户最后登录时间或最后登录IP的查询这个方法将存在风险！！！）
     *
     * @param user 用户对象
     */
    Map<String, Object> login(User user,String message, HttpServletRequest request);

    /**
     * 用户修改密码
     */
    Map<String, Object> modifyPassword(User user, String password);

    /**
     * 创建用户登录记录
     *
     */
    Map<String, Object> createUserLog(String loginKey, HttpServletRequest request);
    /**
     * 条件查询用户登录记录
     *
     * @param pageNo   页码(从0开始)
     * @param pageSize 查询数 User selectUser,
     */
    Map<String, Object> getUserLogs(UserLog userLog, User user, Integer pageNo, Integer pageSize);
}
