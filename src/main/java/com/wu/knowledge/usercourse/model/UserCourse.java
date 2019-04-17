package com.wu.knowledge.usercourse.model;

import com.wu.knowledge.common.model.BaseModel;
import com.wu.knowledge.course.model.Course;
import com.wu.knowledge.user.model.User;

/**
 * 〈功能简述〉<br>
 * 〈用户课程实体类〉
 *
 * @create create by WSD on 2019/2/27
 */
public class UserCourse extends BaseModel {
    /**
     * 关联用户
     */
    private User user;

    /**
     * 关联课程
     */
    private Course course;

    /**
     * 是否收藏
     */
    private Boolean is_collect;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Boolean getIs_collect() {
        return is_collect;
    }

    public void setIs_collect(Boolean is_collect) {
        this.is_collect = is_collect;
    }
}
