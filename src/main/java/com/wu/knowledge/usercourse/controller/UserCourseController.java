package com.wu.knowledge.usercourse.controller;

import com.wu.knowledge.course.model.Course;
import com.wu.knowledge.common.cache.MyCache;
import com.wu.knowledge.common.constant.MyConstant;
import com.wu.knowledge.common.constant.MyErrorMsg;
import com.wu.knowledge.common.model.MyError;
import com.wu.knowledge.common.utils.MyUtils;
import com.wu.knowledge.user.model.User;
import com.wu.knowledge.usercourse.model.UserCourse;
import com.wu.knowledge.usercourse.model.UserCourse;
import com.wu.knowledge.usercourse.service.IUserCourseService;
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
 * 〈用户课程控制器〉
 *
 * @create create by WSD on 2018/12/18
 */
@Controller
@RequestMapping("/userCourse/UserCourseController")
public class UserCourseController {

    @Resource
    private IUserCourseService userCourseService;

    //创建用户课程
    @ResponseBody
    @RequestMapping("/createUserCourse")
    private Object createUserCourse(
            @RequestParam(value = "loginKey", required = false) String loginKey,
            @RequestParam(value = "user", required = false) Integer user,
            @RequestParam(value = "course", required = false) Integer course,
            @RequestParam(value = "is_collect", required = false) Boolean is_collect
            ){
        //必填字段校验
        List<Map<String, Object>> fieldList = new LinkedList<>();
        Map<String, Object> fieldMap = new HashMap<>();
        //fieldMap.put("loginKey", loginKey);
        fieldMap.put("关联课程", course);
        fieldMap.put("关联用户", user);
        fieldList.add(fieldMap);
        try {
            MyUtils.fieldCheck(fieldList);
        } catch (Exception e) {
            e.printStackTrace();
            return MyUtils.setMyErrorJson(MyConstant.returnGeneralErrorCode, e.getMessage());
        }
        User user1 = null;
        /*User user = MyCache.getLoginUsersMap().get(loginKey);
        if (null == user) {
            return MyUtils.setMyErrorJson(MyConstant.returnNoLoginErrorCode, MyErrorMsg.loginStatusError);
        }*/
        UserCourse userCourse = new UserCourse();
        userCourse.setIs_collect(is_collect);
        //关联课程
        Course b = new Course();
        b.setId(course);
        userCourse.setCourse(b);
        //关联用户
        User u = new User();
        u.setId(user);
        userCourse.setUser(u);

        Map<String, Object> map = userCourseService.createUserCourse(userCourse);
        return MyUtils.returnJson(map, (MyError) map.get("error"));
    }

    //更新用户课程
    @ResponseBody
    @RequestMapping("/updateUserCourse")
    private Object updateUserCourse(
            @RequestParam(value = "loginKey", required = false) String loginKey,
            @RequestParam(value = "id", required = false) Integer id,
            @RequestParam(value = "user", required = false) Integer user,
            @RequestParam(value = "course", required = false) Integer course,
            @RequestParam(value = "is_collect", required = false) Boolean is_collect
    ){
        //必填字段校验
        List<Map<String, Object>> fieldList = new LinkedList<>();
        Map<String, Object> fieldMap = new HashMap<>();
        fieldMap.put("用户课程ID", id);
        fieldList.add(fieldMap);
        try {
            MyUtils.fieldCheck(fieldList);
        } catch (Exception e) {
            e.printStackTrace();
            return MyUtils.setMyErrorJson(MyConstant.returnGeneralErrorCode, e.getMessage());
        }
        User user1 = null;
        /*User user = MyCache.getLoginUsersMap().get(loginKey);
        if (null == user) {
            return MyUtils.setMyErrorJson(MyConstant.returnNoLoginErrorCode, MyErrorMsg.loginStatusError);
        }*/
        UserCourse userCourse = new UserCourse();
        userCourse.setId(id);
        userCourse.setIs_collect(is_collect);
        //关联课程
        Course b = new Course();
        b.setId(course);
        userCourse.setCourse(b);
        //关联用户
        User u = new User();
        u.setId(user);
        userCourse.setUser(u);

        Map<String, Object> map = userCourseService.updateUserCourse(userCourse);
        return MyUtils.returnJson(map, (MyError) map.get("error"));
    }

    //条件查询用户课程
    @ResponseBody
    @RequestMapping("/getUserCourses")
    private Object getUserCourses(
            @RequestParam(value = "loginKey", required = false) String loginKey,
            @RequestParam(value = "id", required = false) Integer id,
            @RequestParam(value = "user", required = false) Integer user,
            @RequestParam(value = "course", required = false) Integer course,
            @RequestParam(value = "is_collect", required = false) Boolean is_collect,
            @RequestParam(value = "pageNo", required = false) String pageNo,//页码从0开始
            @RequestParam(value = "pageSize", required = false) String pageSize//每页显示数
    ){
        User us = null;
        if (MyUtils.StringIsNotNull(loginKey)) {
            us = MyCache.getLoginUsersMap().get(loginKey);
            if (null == user) {
                return MyUtils.setMyErrorJson(MyConstant.returnNoLoginErrorCode, MyErrorMsg.loginStatusError);
            }
        }
        Map<String, Object> map;
        UserCourse userCourse = new UserCourse();
        userCourse.setId(id);
        userCourse.setIs_collect(is_collect);
        //关联课程
        Course b = new Course();
        b.setId(course);
        userCourse.setCourse(b);
        //关联用户
        User u = new User();
        u.setId(user);
        userCourse.setUser(u);
       
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
        map = userCourseService.getUserCourses(userCourse,us,pNo,pSize);
        return MyUtils.returnJson(map, (MyError) map.get("error"));
    }
}
