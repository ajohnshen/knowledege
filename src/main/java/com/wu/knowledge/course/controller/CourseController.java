package com.wu.knowledge.course.controller;

import com.wu.knowledge.basedata.dictionary.model.Dictionary;
import com.wu.knowledge.common.cache.MyCache;
import com.wu.knowledge.common.constant.MyConstant;
import com.wu.knowledge.common.constant.MyErrorMsg;
import com.wu.knowledge.common.model.MyError;
import com.wu.knowledge.common.utils.MyUtils;
import com.wu.knowledge.course.model.Course;
import com.wu.knowledge.course.service.ICourseService;
import com.wu.knowledge.user.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 〈功能简述〉<br>
 * 〈课程控制器〉
 *
 * @create create by WSD on 2018/12/18
 */
@Controller
@RequestMapping("/course/CourseController")
public class CourseController {

    @Resource
    private ICourseService courseService;

    //创建课程
    @ResponseBody
    @RequestMapping("/createCourse")
    private Object createCourse(
            @RequestParam(value = "loginKey", required = false) String loginKey,
            @RequestParam(value = "iscollection", required = false) Boolean iscollection,
            @RequestParam(value = "userID", required = false) Integer userID,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "sequence", required = false) Integer sequence,
            @RequestParam(value = "synopsis", required = false) String synopsis,
            @RequestParam(value = "coursetype", required = false) Integer coursetype,
            @RequestParam(value = "teacher", required = false) String teacher,
            @RequestParam(value = "coursetime", required = false) String coursetime,
            @RequestParam(value = "remark", required = false) String remark
            ){
        //必填字段校验
        List<Map<String, Object>> fieldList = new LinkedList<>();
        Map<String, Object> fieldMap = new HashMap<>();
        fieldMap.put("课程名称", name);
        //fieldMap.put("关联用户ID", userID);
        fieldList.add(fieldMap);
        try {
            MyUtils.fieldCheck(fieldList);
        } catch (Exception e) {
            e.printStackTrace();
            return MyUtils.setMyErrorJson(MyConstant.returnGeneralErrorCode, e.getMessage());
        }
        User user = MyCache.getLoginUsersMap().get(loginKey);
        if (null == user) {
            return MyUtils.setMyErrorJson(MyConstant.returnNoLoginErrorCode, MyErrorMsg.loginStatusError);
        }
        Course course = new Course();
        course.setIscollection(iscollection);
        course.setSequence(sequence);
        course.setName(name);
        course.setSynopsis(synopsis);
        course.setRemark(remark);
        course.setTeacher(teacher);
        course.setCoursetime(coursetime);
        //关联用户
        User u = new User();
        u.setId(userID);
        //course.setUserID(u);
        course.setUserID(user);
        //关联字典数据
        Dictionary coursetypeDic = new Dictionary();
        coursetypeDic.setId(coursetype);
        course.setCoursetype(coursetypeDic);

        Map<String, Object> map = courseService.createCourse(course);
        return MyUtils.returnJson(map, (MyError) map.get("error"));
    }

    //更新课程
    @ResponseBody
    @RequestMapping("/updateCourse")
    private Object updateCourse(
            @RequestParam(value = "loginKey", required = false) String loginKey,
            @RequestParam(value = "id", required = false) Integer id,
            @RequestParam(value = "iscollection", required = false) Boolean iscollection,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "userID", required = false) Integer userID,
            @RequestParam(value = "sequence", required = false) Integer sequence,
            @RequestParam(value = "synopsis", required = false) String synopsis,
            @RequestParam(value = "coursetype", required = false) Integer coursetype,
            @RequestParam(value = "teacher", required = false) String teacher,
            @RequestParam(value = "coursetime", required = false) String coursetime,
            @RequestParam(value = "remark", required = false) String remark
    ){
        //必填字段校验
        List<Map<String, Object>> fieldList = new LinkedList<>();
        Map<String, Object> fieldMap = new HashMap<>();
        fieldMap.put("课程ID", id);
        fieldList.add(fieldMap);
        try {
            MyUtils.fieldCheck(fieldList);
        } catch (Exception e) {
            e.printStackTrace();
            return MyUtils.setMyErrorJson(MyConstant.returnGeneralErrorCode, e.getMessage());
        }
        User user = MyCache.getLoginUsersMap().get(loginKey);
        if (null == user) {
            return MyUtils.setMyErrorJson(MyConstant.returnNoLoginErrorCode, MyErrorMsg.loginStatusError);
        }
        Course course = new Course();
        course.setId(id);
        course.setIscollection(iscollection);
        course.setSequence(sequence);
        course.setName(name);
        course.setSynopsis(synopsis);
        course.setRemark(remark);
        course.setTeacher(teacher);
        course.setCoursetime(coursetime);
        //关联用户
        User u = new User();
        u.setId(userID);
        course.setUserID(u);
        //关联字典数据
        Dictionary coursetypeDic = new Dictionary();
        coursetypeDic.setId(coursetype);
        course.setCoursetype(coursetypeDic);

        Map<String, Object> map = courseService.updateCourse(course);
        return MyUtils.returnJson(map, (MyError) map.get("error"));
    }

    //条件查询课程
    @ResponseBody
    @RequestMapping("/getCourses")
    private Object getCourses(
            @RequestParam(value = "loginKey", required = false) String loginKey,
            @RequestParam(value = "id", required = false) Integer id,
            @RequestParam(value = "iscollection", required = false) Boolean iscollection,
            @RequestParam(value = "userID", required = false) Integer userID,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "synopsis", required = false) String synopsis,
            @RequestParam(value = "coursetypeIDs", required = false) String coursetypeIDs,
            @RequestParam(value = "coursetype", required = false) Integer coursetype,
            @RequestParam(value = "pageNo", required = false) String pageNo,//页码从0开始
            @RequestParam(value = "pageSize", required = false) String pageSize//每页显示数
    ){

        User user = null;
        if (MyUtils.StringIsNotNull(loginKey)) {
            user = MyCache.getLoginUsersMap().get(loginKey);
            if (null == user) {
                return MyUtils.setMyErrorJson(MyConstant.returnNoLoginErrorCode, MyErrorMsg.loginStatusError);
            }
        }
        Map<String, Object> map;
        Course course = new Course();
        course.setId(id);
        course.setIscollection(iscollection);
        course.setName(name);
        course.setSynopsis(synopsis);
        //关联用户
        User u = new User();
        u.setId(userID);
        course.setUserID(u);
        //关联字典数据
        Dictionary coursetypeDic = new Dictionary();
        coursetypeDic.setId(coursetype);
        course.setCoursetype(coursetypeDic);
        try {
            //课程类型Id
            course.setCoursetypeIDs(MyUtils.arrayChange(coursetypeIDs));
        } catch (Exception e1) {
            e1.printStackTrace();
            return MyUtils.setMyErrorJson(MyConstant.returnGeneralErrorCode, e1.getMessage());
        }
        //分页数据
        int pNo;
        int pSize;
        try {
            pNo = MyUtils.getPageNo(pageNo);
            pSize = MyUtils.getPageSize(pageSize);
        } catch (Exception e1) {
            e1.printStackTrace();
            return MyUtils.setMyErrorJson(MyConstant.returnGeneralErrorCode, MyErrorMsg.pagingError);
        }
        map = courseService.getCourses(course,user,pNo,pSize);
        return MyUtils.returnJson(map, (MyError) map.get("error"));
    }

    //删除课程
    @ResponseBody
    @RequestMapping("/deleteCourse")
    public Object deleteCourse(
            @RequestParam(value = "loginKey", required = false) String loginKey,
            @RequestParam(value = "ids", required = false) String ids
    ) {
        //必填字段校验
        List<Map<String, Object>> fieldList = new LinkedList<>();
        Map<String, Object> fieldMap = new HashMap<>();
        fieldMap.put("loginKey", loginKey);
        fieldMap.put("课程id", ids);
        fieldList.add(fieldMap);
        try {
            MyUtils.fieldCheck(fieldList);
        } catch (Exception e) {
            e.printStackTrace();
            return MyUtils.setMyErrorJson(MyConstant.returnGeneralErrorCode, e.getMessage());
        }
        User user = null;
        if (MyUtils.StringIsNotNull(loginKey)) {
            user = MyCache.getLoginUsersMap().get(loginKey);
            if (null == user) {
                return MyUtils.setMyErrorJson(MyConstant.returnNoLoginErrorCode, MyErrorMsg.loginStatusError);
            }
        }
        Integer[] Ids;
        try {
            Ids = MyUtils.arrayChange(ids);
        } catch (Exception e) {
            e.printStackTrace();
            return MyUtils.setMyErrorJson(MyConstant.returnGeneralErrorCode, e.getMessage());
        }
        Map<String, Object> map = courseService.deleteCourse(Ids);
        return MyUtils.returnJson(map, (MyError) map.get("error"));
    }
}
