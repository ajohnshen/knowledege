package com.wu.knowledge.course.model;

import com.wu.knowledge.basedata.dictionary.model.Dictionary;
import com.wu.knowledge.common.model.BaseModel;

/**
 * 〈功能简述〉<br>
 * 〈课程实体类〉
 *
 * @create create by WSD on 2019/1/28
 */
public class Course extends BaseModel {

    /**
     * 备注
     */
    private String remark;
    /**
     * 课程类型
     */
    private Dictionary coursetype;

    /**
     * 课程类型数组
     */
    private Integer[] coursetypeIDs;

    /**
     *  课程老师
     */
    private String teacher;

    /**
     *  上课时间
     */
    private String coursetime;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Dictionary getCoursetype() {
        return coursetype;
    }

    public void setCoursetype(Dictionary coursetype) {
        this.coursetype = coursetype;
    }

    public Integer[] getCoursetypeIDs() {
        return coursetypeIDs;
    }

    public void setCoursetypeIDs(Integer[] coursetypeIDs) {
        this.coursetypeIDs = coursetypeIDs;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getCoursetime() {
        return coursetime;
    }

    public void setCoursetime(String coursetime) {
        this.coursetime = coursetime;
    }
}
