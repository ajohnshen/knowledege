package com.wu.knowledge.user.service.impl;

import com.wu.knowledge.basedata.dictionary.dao.DictionaryMapper;
import com.wu.knowledge.basedata.dictionary.model.Dictionary;
import com.wu.knowledge.common.cache.MyCache;
import com.wu.knowledge.common.constant.MyConstant;
import com.wu.knowledge.common.constant.MyErrorMsg;
import com.wu.knowledge.common.model.MyError;
import com.wu.knowledge.common.utils.MyUtils;
import com.wu.knowledge.file.dao.FileMapper;
import com.wu.knowledge.user.dao.UserLogMapper;
import com.wu.knowledge.user.dao.UserMapper;
import com.wu.knowledge.user.model.User;
import com.wu.knowledge.user.model.UserLog;
import com.wu.knowledge.user.service.IUserService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 用户服务层实现类
 * Created by TZ on 2018/9/11.
 */
@Service("userService")
public class UserServiceImpl implements IUserService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private DictionaryMapper dictionaryMapper;
    @Resource
    private FileMapper fileMapper;
    @Resource
    private UserLogMapper userLogMapper;

    //创建用户
    @Override
    public Map<String, Object> createUser(User user, User operationUser) {
        Map<String, Object> mapObj = new HashMap<>();
        MyError myError = new MyError();
        //设置创建人和时间，修改人和时间
        Date date = new Date();
        user.setCreate_user(operationUser);
        user.setWrite_user(operationUser);
        user.setCreate_date(date);
        user.setWrite_date(date);
        int count;
        try {
            count = userMapper.createUser(user);
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
    //更新用户
    @Override
    public Map<String, Object> updateUser(User user, User operationUser) {
        Map<String, Object> mapObj = new HashMap<>();
        MyError myError = new MyError();
        //设置修改人和时间
        Date date = new Date();
        user.setWrite_user(operationUser);
        user.setWrite_date(date);
        int count;
        try {
            count = userMapper.updateUser(user);
            if (count <= 0) {
                return MyUtils.setMyErrorMap(MyConstant.returnGeneralErrorCode, MyErrorMsg.updateError);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return MyUtils.setMyErrorMap(MyConstant.returnGeneralErrorCode, MyErrorMsg.dbError);
        }
        return MyUtils.setDateMap(myError, mapObj);
    }
    //条件查询用户
    @Override
    public Map<String, Object> getUsers(User user, User selectUser, Integer pageNo, Integer pageSize) {
        Map<String, Object> mapObj = new HashMap<>();
        MyError myError = new MyError();
        int pageStartNo = pageSize * pageNo;
        int pageEndNo = pageSize * (pageNo + 1);
        //条件查询出的总数
        int totalSize = userMapper.totalSize(selectUser);
        mapObj.put("totalSize", totalSize);
        //判断下一页是否还有数据
        boolean isNext = false;
        if (totalSize > pageEndNo) {
            isNext = true;
        }
        mapObj.put("isNext", isNext);
        List<User> users = userMapper.getUsers(selectUser, pageStartNo, pageSize);
        List<Map<String, Object>> listMap = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(users)) {
            for (User u : users) {
                Map<String, Object> mapOt = new HashMap<>();
                if (null != user && null != user.getId()) {
                    //管理员角色可查看数据
                    if (MyConstant.UserRole.UserRoleAdmin.toString().equals(dictionaryMapper.getDictionaryById(user.getRole().getId()).getCode())) {
                        mapOt = MyUtils.getBaseResultMap(u, userMapper);

                        /*//审核时间
                        mapOt.put("examine_date", MyUtils.dateToStr(u.getExamine_date()));
                        //最后登录IP
                        mapOt.put("last_ip", u.getLast_ip());
                        //最后登录时间
                        mapOt.put("last_date", MyUtils.dateToStr(u.getLast_date()));*/
                    }
                }
                //用户ID
                mapOt.put("id", u.getId());
                //用户名
                mapOt.put("user_name", u.getUser_name());
                //手机
                mapOt.put("mobile", u.getMobile());
                //角色(必填字段))

                Dictionary role = dictionaryMapper.getDictionaryById(u.getRole().getId());
                mapOt.put("role", role.getCode());
                mapOt.put("role_name", role.getName());
                //性别(非必填字段)
                if (null != u.getSex()) {
                    Dictionary sex = dictionaryMapper.getDictionaryById(u.getSex().getId());
                    mapOt.put("sex", sex.getCode());
                    mapOt.put("sexId", sex.getId());
                    mapOt.put("sexName", sex.getName());
                } else {
                    mapOt.put("sex", "");
                    mapOt.put("sexId", "");
                    mapOt.put("sexName", "");
                }
                //头像信息
                if(u.getCover() != null){
                    mapOt.put("headPath","../knowledge/"+fileMapper.getFileById(u.getCover().getId()).getFile_path());
                }
                //邮箱
                mapOt.put("email", u.getEmail());
                //姓名
                mapOt.put("name", u.getName());
                //学校
                    mapOt.put("school",u.getSchool());

                listMap.add(mapOt);
            }
        }
        mapObj.put("list", listMap);
        return MyUtils.setDateMap(myError, mapObj);
    }

    //用户注册
    @Transactional(rollbackFor = java.lang.Exception.class)
    @Override
    public Map<String, Object> register(User user) throws Exception {
        Map<String, Object> mapObj = new HashMap<>();
        MyError myError = new MyError();
        int count;
        try {
            count = userMapper.createUser(user);
            if (count <= 0) {
                return MyUtils.setMyErrorMap(MyConstant.returnGeneralErrorCode, MyErrorMsg.mobileIsRegisterError);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return MyUtils.setMyErrorMap(MyConstant.returnGeneralErrorCode, MyErrorMsg.mobileIsRegisterError);
        }
        User u = new User();
        u.setId(user.getId());
        u.setCreate_user(user);
        u.setWrite_user(user);
        u.setWrite_date(user.getWrite_date());

        try {
            count = userMapper.updateUser(u);
            if (count <= 0) {
                throw new Exception(MyErrorMsg.dbError);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(MyErrorMsg.dbError);
        }
        return MyUtils.setDateMap(myError, mapObj);
    }

    //用户登录(密码)
    @Override
    public Map<String, Object> login(User user,String message,HttpServletRequest request) {
        Map<String, Object> mapObj = new HashMap<>();
        MyError myError = new MyError();
        List<User> users = loginCheck(user, message);
        if (users.isEmpty()) {
            //验证失败返回
            return MyUtils.setMyErrorMap(MyConstant.returnNoLoginErrorCode, MyErrorMsg.loginError);
        } else {
            User u = users.get(0);
            if (null == u) {
                return MyUtils.setMyErrorMap(MyConstant.returnNoLoginErrorCode, MyErrorMsg.loginError);
            }
            user.setId(u.getId());
            user.setWrite_user(u.getWrite_user());
            //更新用户登录信息
            int count;
            try {
                count = userMapper.updateUser(user);
                if (count <= 0) {
                    return MyUtils.setMyErrorMap(MyConstant.returnNoLoginErrorCode, MyErrorMsg.dbError);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return MyUtils.setMyErrorMap(MyConstant.returnNoLoginErrorCode, MyErrorMsg.dbError);
            }
            user = u;
        }
        //生成或更新loginKey
        String loginKey = MyUtils.createLoginKey(user);
        mapObj.put("loginKey", loginKey);
        //创建用户登录记录
        //createUserLog(loginKey,request);

        return MyUtils.setDateMap(myError, mapObj);
    }

    @Override
    public Map<String, Object> modifyPassword(User user, String password) {
        return null;
    }

    //创建用户登录记录
    @Override
    public Map<String, Object> createUserLog(String loginKey, HttpServletRequest request) {
        Map<String, Object> mapObj = new HashMap<>();
        MyError myError = new MyError();
        String userIP = request.getRemoteAddr();
        User operationUser = MyCache.getLoginUsersMap().get(loginKey);

        //设置创建人和时间，修改人和时间
        UserLog userLog = new UserLog();
        Date date = new Date();
        userLog.setCreate_user(operationUser);
        userLog.setCreate_date(date);
        userLog.setLogin_ip(userIP);
        int count;
        try {
            count = userLogMapper.createUserLog(userLog);
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

    @Override
    public Map<String, Object> getUserLogs(UserLog userLog, User user, Integer pageNo, Integer pageSize) {
        return null;
    }

    //校验用户名、手机号、邮箱
    public List<User> loginCheck(User user, String message) {
        int i = 1;
        List<User> users = null;
        switch (i) {
            case 1:
                user.setMobile(message);
                users = userMapper.login(user);
                if (users.isEmpty()) {
                    i++;
                } else {
                    return users;
                }
            case 2:
                user.setUser_name(message);
                user.setMobile(null);
                users = userMapper.login(user);
                if (users.isEmpty()) {
                    i++;
                } else {
                    return users;
                }
            case 3:
                user.setEmail(message);
                user.setUser_name(null);
                users = userMapper.login(user);
                return users;
        }
        return users;
    }
}