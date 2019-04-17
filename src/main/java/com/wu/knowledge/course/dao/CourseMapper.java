package com.wu.knowledge.course.dao;

import com.wu.knowledge.course.model.Course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 〈功能简述〉<br>
 * 〈课程Dao层接口〉
 *
 * @create create by WSD on 2018/12/18
 */
@Mapper
public interface CourseMapper {

    /**
     * 通过ID查询单个工位或办公室
     */
    Course getCourseById(Integer id);

    /**
     * 条件查询工位或办公室数量
     */
    int totalSize(Course course);

    /**
     * 添加一条课程
     */
    int createCourse(Course course);

    /**
     * 更新一条课程
     */
    int updateCourse(Course course);

    /**
     * 删除课程
     */
    int deleteCourse(Integer[] ids);

    /**
     *  条件查询课程
     * @param course     课程对象
     * @param pageStart 查询的起始位置(下标从0开始)
     * @param pageSize  查询的数量
     */
    List<Course> getCourses(@Param("Course") Course course, @Param("pageStart") Integer pageStart,
                        @Param("pageSize") Integer pageSize);
}
