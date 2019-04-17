package com.wu.knowledge.course.service.impl;

import com.wu.knowledge.basedata.dictionary.dao.DictionaryMapper;
import com.wu.knowledge.common.constant.MyConstant;
import com.wu.knowledge.common.constant.MyErrorMsg;
import com.wu.knowledge.common.model.MyError;
import com.wu.knowledge.common.utils.MyUtils;
import com.wu.knowledge.course.dao.CourseMapper;
import com.wu.knowledge.course.model.Course;
import com.wu.knowledge.course.service.ICourseService;
import com.wu.knowledge.user.model.User;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 〈功能简述〉<br>
 * 〈课程服务层实现类〉
 *
 * @create create by WSD on 2018/12/18
 */
@Service("courseService")
public class CourseServiceImpl implements ICourseService {

    @Resource
    private CourseMapper courseMapper;
    @Resource
    private DictionaryMapper dictionaryMapper;

    //条件查询课程
    @Override
    public Map<String, Object> getCourses(Course course, User user, Integer pageNo, Integer pageSize) {
        Map<String, Object> mapObj = new HashMap<>();
        MyError myError = new MyError();
        int pageStartNo = pageSize * pageNo;
        int pageEndNo = pageSize * (pageNo + 1);
        //条件查询出的总数
        int totalSize = courseMapper.totalSize(course);
        mapObj.put("totalSize", totalSize);
        //判断下一页是否还有数据
        boolean isNext = false;
        if (totalSize > pageEndNo) {
            isNext = true;
        }
        mapObj.put("isNext", isNext);
        List<Course> courses = courseMapper.getCourses(course, pageStartNo, pageSize);
        List<Map<String, Object>> listMap = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(courses)) {
            for (Course c : courses) {
                Map<String, Object> mapOt = new HashMap<>();
                if (null != user && null != user.getId()) {
                    //管理员角色可查看数据
                    if (MyConstant.UserRole.UserRoleAdmin.toString().equals(dictionaryMapper.getDictionaryById(user.getRole().getId()).getCode())) {

                    }
                }
                mapOt.put("id", c.getId());
                //排序
                mapOt.put("sequence", c.getSequence());
                //是否收藏
                mapOt.put("iscollection", c.getIscollection());
                mapOt.put("name", c.getName());
                mapOt.put("synopsis", c.getSynopsis());
                mapOt.put("remark", c.getRemark());
                mapOt.put("teacher", c.getTeacher());
                mapOt.put("coursetime", c.getCoursetime());
                //课程类型
                if (c.getCoursetype() != null) {
                    //课程类型ID
                    mapOt.put("coursetype_id", c.getCoursetype().getId());
                    if (null != dictionaryMapper.getDictionaryById(c.getCoursetype().getId())) {
                        //类型名称
                        mapOt.put("coursetype_name", dictionaryMapper.getDictionaryById(c.getCoursetype().getId()).getName());
                    }
                } else {
                    mapOt.put("coursetype_id", "");
                    mapOt.put("coursetype_name", "");
                }

                listMap.add(mapOt);
            }
        }
        mapObj.put("list", listMap);
        return MyUtils.setDateMap(myError, mapObj);
    }

    //创建课程
    @Override
    public Map<String, Object> createCourse(Course course) {
        Map<String, Object> mapObj = new HashMap<>();
        MyError myError = new MyError();
        //设置创建人和时间，修改人和时间
        User user = new User();
        Date date = new Date();
        course.setCreate_user(user);
        course.setWrite_user(user);
        course.setCreate_date(date);
        course.setWrite_date(date);
        int count;
        try {
            count = courseMapper.createCourse(course);
            if (count <= 0) {
                return MyUtils.setMyErrorMap(MyConstant.returnGeneralErrorCode, MyErrorMsg.createError);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return MyUtils.setMyErrorMap(MyConstant.returnGeneralErrorCode, MyErrorMsg.dbError);
        }
        //发生异常和正常流程走完的信息返回调用不一样的工具类数据返回接口
        return MyUtils.setDateMap(myError, mapObj);
    }

    //更新课程
    @Override
    public Map<String, Object> updateCourse(Course course) {
        Map<String, Object> mapObj = new HashMap<>();
        MyError myError = new MyError();
        //设置修改人和时间
        User user = new User();
        Date date = new Date();
        course.setWrite_user(user);
        course.setWrite_date(date);
        int count;
        try {
            count = courseMapper.updateCourse(course);
            if (count <= 0) {
                return MyUtils.setMyErrorMap(MyConstant.returnGeneralErrorCode, MyErrorMsg.updateError);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return MyUtils.setMyErrorMap(MyConstant.returnGeneralErrorCode, MyErrorMsg.dbError);
        }
        return MyUtils.setDateMap(myError, mapObj);
    }

    //删除课程
    @Override
    public Map<String, Object> deleteCourse(Integer[] ids) {
        Map<String, Object> mapObj = new HashMap<>();
        MyError myError = new MyError();
        int count = courseMapper.deleteCourse(ids);
        if (count < 1) {
            return MyUtils.setMyErrorMap(MyConstant.returnGeneralErrorCode, MyErrorMsg.deleteError);
        }
        return MyUtils.setDateMap(myError, mapObj);
    }
}
