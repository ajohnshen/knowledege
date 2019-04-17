package com.wu.knowledge.usercourse.service.impl;

import com.wu.knowledge.basedata.dictionary.dao.DictionaryMapper;
import com.wu.knowledge.course.dao.CourseMapper;
import com.wu.knowledge.course.model.Course;
import com.wu.knowledge.common.constant.MyConstant;
import com.wu.knowledge.common.constant.MyErrorMsg;
import com.wu.knowledge.common.model.MyError;
import com.wu.knowledge.common.utils.MyUtils;
import com.wu.knowledge.file.dao.FileMapper;
import com.wu.knowledge.user.model.User;
import com.wu.knowledge.usercourse.dao.UserCourseMapper;
import com.wu.knowledge.usercourse.model.UserCourse;
import com.wu.knowledge.usercourse.service.IUserCourseService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 〈功能简述〉<br>
 * 〈用户课程服务层实现类〉
 *
 * @create create by WSD on 2018/12/18
 */
@Service("userCourseService")
public class UserCourseServiceImpl implements IUserCourseService {

    @Resource
    private UserCourseMapper userCourseMapper;
    @Resource
    private DictionaryMapper dictionaryMapper;
    @Resource
    private FileMapper fileMapper;
    @Resource
    private CourseMapper courseMapper;

    //条件查询用户课程
    @Override
    public Map<String, Object> getUserCourses(UserCourse userCourse, User user, Integer pageNo, Integer pageSize) {
        Map<String, Object> mapObj = new HashMap<>();
        MyError myError = new MyError();
        int pageStartNo = pageSize * pageNo;
        int pageEndNo = pageSize * (pageNo + 1);
        //条件查询出的总数
        int totalSize = userCourseMapper.totalSize(userCourse);
        mapObj.put("totalSize", totalSize);
        //判断下一页是否还有数据
        boolean isNext = false;
        if (totalSize > pageEndNo) {
            isNext = true;
        }
        mapObj.put("isNext", isNext);
        List<UserCourse> userCourses = userCourseMapper.getUserCourses(userCourse,pageStartNo,pageSize);
        List<Map<String, Object>> listMap = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(userCourses)){
            for(UserCourse ub : userCourses){
                Map<String, Object> mapOt = new HashMap<>();
                if (null != user && null != user.getId()) {
                    //管理员角色可查看数据
                    if (MyConstant.UserRole.UserRoleAdmin.toString().equals(dictionaryMapper.getDictionaryById(user.getRole().getId()).getCode())) {
                        //mapOt = MyUtils.getBaseResultMap(b, userMapper);

                    }
                }
                    //用户课程id
                    mapOt.put("id", ub.getId());
                    //是否收藏
                    mapOt.put("is_collect", ub.getIs_collect());
                    //用户ID不为空则获取课程数据
                    if (null != userCourse.getUser() && null != userCourse.getUser().getId()) {
                        Course c = courseMapper.getCourseById(ub.getCourse().getId());
                        mapOt.put("id", c.getId());
                        mapOt.put("name", c.getName());
                        mapOt.put("synopsis", c.getSynopsis());
                        mapOt.put("remark", c.getRemark());
                        //课程类型
                        //mapOt.put("coursetype",c.getCoursetype().getId());
                        if (c.getCoursetype() != null) {
                            //课程类型ID
                            mapOt.put("coursetype_id", c.getCoursetype().getId());
                            if (null != dictionaryMapper.getDictionaryById(c.getCoursetype().getId())) {
                                //融资阶段名称
                                mapOt.put("coursetype_name", dictionaryMapper.getDictionaryById(c.getCoursetype().getId()).getName());
                            }
                        } else {
                            mapOt.put("coursetype_id", "");
                            mapOt.put("coursetype_name", "");
                        }
                    }

                listMap.add(mapOt);
            }
        }
        mapObj.put("list",listMap);
        return MyUtils.setDateMap(myError,mapObj);
    }

    //创建用户课程
    @Override
    public Map<String, Object> createUserCourse(UserCourse userCourse) {
        Map<String, Object> mapObj = new HashMap<>();
        MyError myError = new MyError();
        //设置创建人和时间，修改人和时间
        User user =new User();
        Date date = new Date();
        userCourse.setCreate_user(user);
        userCourse.setWrite_user(user);
        userCourse.setCreate_date(date);
        userCourse.setWrite_date(date);
        int count;
        try {
            count = userCourseMapper.createUserCourse(userCourse);
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

    //更新用户课程
    @Override
    public Map<String, Object> updateUserCourse(UserCourse userCourse) {
        Map<String, Object> mapObj = new HashMap<>();
        MyError myError = new MyError();
        //设置修改人和时间
        User user = new User();
        Date date = new Date();
        userCourse.setWrite_user(user);
        userCourse.setWrite_date(date);
        int count;
        try {
            count = userCourseMapper.updateUserCourse(userCourse);
            if (count <= 0) {
                return MyUtils.setMyErrorMap(MyConstant.returnGeneralErrorCode, MyErrorMsg.updateError);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return MyUtils.setMyErrorMap(MyConstant.returnGeneralErrorCode, MyErrorMsg.dbError);
        }
        return MyUtils.setDateMap(myError, mapObj);
    }
}
