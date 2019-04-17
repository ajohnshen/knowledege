package com.wu.knowledge.user.dao;
import com.wu.knowledge.user.model.UserLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户记录DAO层接口
 * @create create by WSD on 2019/1/16
 */
@Mapper
public interface UserLogMapper {
    /**
     * 条件查询用户数量
     */
    int totalSize(UserLog userLog);
    /**
     * 创建用户登录记录
     */
    int createUserLog(UserLog userLog);
    /**
     * 查询用户登录记录
     */
    List<UserLog> getUserLogs(@Param("UserLog") UserLog userLog, @Param("pageStart") Integer pageStart,
                              @Param("pageSize") Integer pageSize);
}
