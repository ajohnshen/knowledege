package com.wu.knowledge.user.dao;

import com.wu.knowledge.user.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户DAO层接口
 * @create create by WSD on 2019/1/16
 */
@Mapper
public interface UserMapper {

    /**
     * 用户登录(查询登录信息)
     */
    List<User> login(User user);

    /**
     * 创建用户
     */
    int createUser(User user);

    /**
     * 更新用户
     */
    int updateUser(User user);

    /**
     * 通过ID查询单个用户
     */
    User getUserById(Integer id);

    /**
     * 通过用户名查询单个用户
     */
    User getUserByName(String name);

    /**
     * 通过用户名查询多个用户
     */
    List<User> getUserByNames(String name);

    /**
     * 查询所有用户
     */
    List<User> getAllUsers();

    /**
     * 条件查询用户数量
     */
    int totalSize(User user);

    /**
     * 条件查询用户
     *
     * @param user      用户对象
     * @param pageStart 查询的起始位置(下标从0开始)
     * @param pageSize  查询的数量
     */
    List<User> getUsers(@Param("User") User user, @Param("pageStart") Integer pageStart,
                        @Param("pageSize") Integer pageSize);
}
