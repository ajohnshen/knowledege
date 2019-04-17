package com.wu.knowledge.usercourse.service;

import com.wu.knowledge.user.model.User;
import com.wu.knowledge.usercourse.model.UserCourse;

import java.util.Map;

/**
 * 〈功能简述〉<br>
 * 用户课程服务层接口
 * @create create by WSD on 2018/12/18
 */
public interface IUserCourseService {
    /**
     * 条件查询用户课程
     */
    Map<String, Object> getUserCourses(UserCourse userCourse, User user, Integer pageNo, Integer pageSize);

    /**
     * 添加一条用户课程
     */
    Map<String, Object> createUserCourse(UserCourse userCourse);

    /**
     * 更新一条用户课程
     */
    Map<String, Object> updateUserCourse(UserCourse userCourse);
}
