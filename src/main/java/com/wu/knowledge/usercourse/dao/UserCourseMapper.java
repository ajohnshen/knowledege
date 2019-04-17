package com.wu.knowledge.usercourse.dao;


import com.wu.knowledge.usercourse.model.UserCourse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 〈功能简述〉<br>
 * 〈用户课程Dao层接口〉
 *
 * @create create by WSD on 2018/12/18
 */
@Mapper
public interface UserCourseMapper {

    /**
     * 通过ID查询单个工位或办公室
     */
    UserCourse getUserCourseById(Integer id);

    /**
     * 条件查询用户课程
     */
    int totalSize(UserCourse userCourse);

    /**
     * 添加一条用户课程
     */
    int createUserCourse(UserCourse userCourse);

    /**
     * 更新一条用户课程
     */
    int updateUserCourse(UserCourse userCourse);

    /**
     *  条件查询用户课程
     * @param userCourse     用户课程对象
     * @param pageStart 查询的起始位置(下标从0开始)
     * @param pageSize  查询的数量
     */
    List<UserCourse> getUserCourses(@Param("UserCourse") UserCourse userCourse, @Param("pageStart") Integer pageStart,
                                @Param("pageSize") Integer pageSize);
}
