package com.wu.knowledge.course.service;

import com.wu.knowledge.course.model.Course;
import com.wu.knowledge.user.model.User;

import java.util.Map;

/**
 * 〈功能简述〉<br>
 * 课程服务层接口
 * @create create by WSD on 2018/12/18
 */
public interface ICourseService {
    /**
     * 条件查询工位或办公室
     */
    Map<String, Object> getCourses(Course course, User user, Integer pageNo, Integer pageSize);

    /**
     * 添加一条孵化
     */
    Map<String, Object> createCourse(Course course);

    /**
     * 更新一条孵化
     */
    Map<String, Object> updateCourse(Course course);

    /**
     * 删除课程
     */
    Map<String, Object> deleteCourse(Integer[] ids);
}
