package com.wu.knowledge.user.controller;

import com.wu.knowledge.basedata.dictionary.dao.DictionaryMapper;
import com.wu.knowledge.basedata.dictionary.model.Dictionary;
import com.wu.knowledge.common.cache.MyCache;
import com.wu.knowledge.common.constant.MyConstant;
import com.wu.knowledge.common.constant.MyErrorMsg;
import com.wu.knowledge.common.model.MyError;
import com.wu.knowledge.common.utils.MyUtils;
import com.wu.knowledge.file.model.File1;
import com.wu.knowledge.user.dao.UserMapper;
import com.wu.knowledge.user.model.User;
import com.wu.knowledge.user.service.IUserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.*;

/**
 * 〈功能简述〉<br>
 * 〈用户控制层〉
 *
 * Created by WSD on 2018/12/11.
 */
@RestController
@RequestMapping("/user/UserController")
public class UserController {

    @Resource
    private IUserService userService;
    @Resource
    private UserMapper userMapper;
    @Resource
    private DictionaryMapper dictionaryMapper;

    //创建用户
    @RequestMapping("/createUser")
    public Object createUser(
            @RequestParam(value = "loginKey", required = false) String loginKey,
            @RequestParam(value = "active", required = false) Boolean active,//是否可用
            @RequestParam(value = "sequence", required = false) Integer sequence,//顺序
            @RequestParam(value = "user_name", required = false) String user_name,
            @RequestParam(value = "password", required = false) String password,
            @RequestParam(value = "sex", required = false) String sex,
            @RequestParam(value = "mobile", required = false) String mobile,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "cover", required = false) Integer cover,
            @RequestParam(value = "role", required = false) String role,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "school", required = false) String school
    ){
        //必填字段校验
        List<Map<String, Object>> fieldList = new LinkedList<>();
        Map<String, Object> fieldMap = new HashMap<>();
        //fieldMap.put("loginKey", loginKey);
        fieldMap.put("是否可用", active);
        fieldMap.put("用户名", user_name);
        fieldMap.put("密码", password);
        fieldMap.put("手机", mobile);
        fieldList.add(fieldMap);
        try {
            MyUtils.fieldCheck(fieldList);
        } catch (Exception e) {
            e.printStackTrace();
            return MyUtils.setMyErrorJson(MyConstant.returnGeneralErrorCode, e.getMessage());
        }
        User operationUser = null;
        /*User operationUser = MyCache.getLoginUsersMap().get(loginKey);
        if (null == operationUser) {
            return MyUtils.setMyErrorJson(MyConstant.returnNoLoginErrorCode, MyErrorMsg.loginStatusError);
        }*/
        User user = new User();
        user.setActive(active);
        user.setSequence(sequence);
        user.setUser_name(user_name);
        //对密码进行MD5加密
        user.setPassword(MyUtils.getMD5(password));
        user.setMobile(mobile);
        user.setName(name);
        user.setEmail(email);
        user.setSchool(school);
        //用户角色
        if (null != role) {
            //校验用户角色
            try {
                Dictionary roleDictionary = MyUtils.dictionaryCheck(role, MyUtils.enumStrSplit(MyConstant.UserRole.class.getName()), dictionaryMapper);
                user.setRole(roleDictionary);
            } catch (Exception e) {
                e.printStackTrace();
                return MyUtils.setMyErrorJson(MyConstant.returnGeneralErrorCode, e.getMessage());
            }
        } else {
            //默认设为普通用户
            user.setRole(dictionaryMapper.getDictionaryByCode(MyConstant.UserRole.UserRoleOrdinary.toString()));
        }
        //用户性别
        if (null != sex) {
            //校验用户性别
            try {
                Dictionary sexDictionary = MyUtils.dictionaryCheck(sex, MyUtils.enumStrSplit(MyConstant.Sex.class.getName()), dictionaryMapper);
                user.setSex(sexDictionary);
            } catch (Exception e) {
                e.printStackTrace();
                return MyUtils.setMyErrorJson(MyConstant.returnGeneralErrorCode, e.getMessage());
            }
        } else {
            //默认设为男
            user.setSex(dictionaryMapper.getDictionaryByCode(MyConstant.Sex.SexMan.toString()));
        }
        File1 coverFile = new File1();
        coverFile.setId(cover);
        user.setCover(coverFile);

        Map<String, Object> map = userService.createUser(user,operationUser);
        return MyUtils.returnJson(map, (MyError) map.get("error"));
    }

    //更新用户
    @RequestMapping("/updateUser")
    public Object updateUser(
            @RequestParam(value = "loginKey", required = false) String loginKey,
            @RequestParam(value = "id", required = false) Integer id,
            @RequestParam(value = "active", required = false) Boolean active,//是否可用
            @RequestParam(value = "sequence", required = false) Integer sequence,//顺序
            @RequestParam(value = "user_name", required = false) String user_name,
            @RequestParam(value = "password", required = false) String password,
            @RequestParam(value = "sex", required = false) Integer sex,
            @RequestParam(value = "mobile", required = false) String mobile,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "cover", required = false) Integer cover,
            @RequestParam(value = "role", required = false) String role,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "school", required = false) String school
    ){
        //必填字段校验
        List<Map<String, Object>> fieldList = new LinkedList<>();
        Map<String, Object> fieldMap = new HashMap<>();
        fieldMap.put("loginKey", loginKey);
        fieldMap.put("id", "用户id");
        fieldList.add(fieldMap);
        try {
            MyUtils.fieldCheck(fieldList);
        } catch (Exception e) {
            e.printStackTrace();
            return MyUtils.setMyErrorJson(MyConstant.returnGeneralErrorCode, e.getMessage());
        }
        //User operationUser = null;
        User operationUser = MyCache.getLoginUsersMap().get(loginKey);
        if (null == operationUser) {
            return MyUtils.setMyErrorJson(MyConstant.returnNoLoginErrorCode, MyErrorMsg.loginStatusError);
        }
        User user = new User();
        user.setId(id);
        user.setActive(active);
        user.setSequence(sequence);
        user.setUser_name(user_name);
        //对密码进行MD5加密
        if(MyUtils.StringIsNotNull(password)){
            user.setPassword(MyUtils.getMD5(password));
        }
        user.setMobile(mobile);
        user.setName(name);
        user.setEmail(email);
        user.setSchool(school);
        //用户角色
        if (MyUtils.StringIsNotNull(role)) {
            //校验用户角色
            try {
                Dictionary roleDictionary = MyUtils.dictionaryCheck(role, MyUtils.enumStrSplit(MyConstant.UserRole.class.getName()), dictionaryMapper);
                user.setRole(roleDictionary);
            } catch (Exception e) {
                e.printStackTrace();
                return MyUtils.setMyErrorJson(MyConstant.returnGeneralErrorCode, e.getMessage());
            }
        } else {
            //默认设为普通用户
            user.setRole(dictionaryMapper.getDictionaryByCode(MyConstant.UserRole.UserRoleOrdinary.toString()));
        }
        //用户性别
        if (sex != null) {
            //校验用户性别
            try {
                Dictionary sexDictionary = MyUtils.dictionaryCheck(sex, MyUtils.enumStrSplit(MyConstant.Sex.class.getName()), dictionaryMapper);
                user.setSex(sexDictionary);
            } catch (Exception e) {
                e.printStackTrace();
                return MyUtils.setMyErrorJson(MyConstant.returnGeneralErrorCode, e.getMessage());
            }
        } else {
            //默认设为男
            user.setSex(dictionaryMapper.getDictionaryByCode(MyConstant.Sex.SexMan.toString()));
        }
        File1 coverFile = new File1();
        coverFile.setId(cover);
        user.setCover(coverFile);

        Map<String, Object> map = userService.updateUser(user,operationUser);
        return MyUtils.returnJson(map, (MyError) map.get("error"));
    }

    //获取用户
    @ResponseBody
    @RequestMapping("/getUsers")
    public Object getUsers(
            @RequestParam(value = "loginKey", required = false) String loginKey,
            @RequestParam(value = "id", required = false) Integer id,
            @RequestParam(value = "user_name", required = false) String user_name,
            @RequestParam(value = "mobile", required = false) String mobile,
            @RequestParam(value = "role", required = false) Integer role,
            @RequestParam(value = "sex", required = false) Integer sex,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "pageNo", required = false) String pageNo,
            @RequestParam(value = "pageSize", required = false) String pageSize
    ) {
        User operationUser = null;
        if (MyUtils.StringIsNotNull(loginKey)) {
            operationUser = MyCache.getLoginUsersMap().get(loginKey);
            if (null == operationUser) {
                return MyUtils.setMyErrorJson(MyConstant.returnNoLoginErrorCode, MyErrorMsg.loginStatusError);
            }
        }
        User user = new User();
        user.setId(id);
        //用户名称
        if (MyUtils.StringIsNotNull(user_name)) {
            String nameT = user_name.replace(" ", "");
            user.setUser_name(nameT);
        }
        user.setMobile(mobile);
        if (null != role) {
            //校验用户角色
            try {
                Dictionary roleDictionary = MyUtils.dictionaryCheck(role, MyUtils.enumStrSplit(MyConstant.UserRole.class.getName()), dictionaryMapper);
                user.setRole(roleDictionary);
            } catch (Exception e) {
                e.printStackTrace();
                return MyUtils.setMyErrorJson(MyConstant.returnGeneralErrorCode, e.getMessage());
            }
        }
        if (null != sex) {
            //校验用户性别
            try {
                Dictionary sexDictionary = MyUtils.dictionaryCheck(sex, MyUtils.enumStrSplit(MyConstant.Sex.class.getName()), dictionaryMapper);
                user.setSex(sexDictionary);
            } catch (Exception e) {
                e.printStackTrace();
                return MyUtils.setMyErrorJson(MyConstant.returnGeneralErrorCode, e.getMessage());
            }
        }
        user.setEmail(email);
        int pNo;
        int pSize;
        try {
            pNo = MyUtils.getPageNo(pageNo);
            pSize = MyUtils.getPageSize(pageSize);
        } catch (Exception e) {
            e.printStackTrace();
            return MyUtils.setMyErrorJson(MyConstant.returnGeneralErrorCode, MyErrorMsg.pagingError);
        }
        Map<String, Object> map = userService.getUsers(operationUser, user, pNo, pSize);
        return MyUtils.returnJson(map, (MyError) map.get("error"));
    }

    //用户注册
    @ResponseBody
    @RequestMapping("/register")
    public Object register(
            @RequestParam(value = "mobile", required = false) String mobile,//手机号（必填）
            @RequestParam(value = "password", required = false) String password,//用户密码（必填）
            HttpServletRequest request
    ) {
        //必填字段校验
        List<Map<String, Object>> fieldList = new LinkedList<>();
        Map<String, Object> fieldMap = new HashMap<>();
        fieldMap.put("手机号", mobile);
        fieldMap.put("密码", password);
        fieldList.add(fieldMap);
        try {
            MyUtils.fieldCheck(fieldList);
        } catch (Exception e) {
            e.printStackTrace();
            return MyUtils.setMyErrorJson(MyConstant.returnGeneralErrorCode, e.getMessage());
        }
        Date now = new Date();
        String userIP = request.getRemoteAddr();

        User u = new User();
        u.setCreate_date(now);
        u.setWrite_date(now);
        u.setActive(true);
        u.setUser_name(MyUtils.createUsername());
        u.setMobile(mobile);
        u.setPassword(MyUtils.getMD5(password));
        /*u.setLast_ip(userIP);
        u.setLast_date(now);*/
        Dictionary role = dictionaryMapper.getDictionaryByCode(MyConstant.UserRole.UserRoleOrdinary.toString());
        u.setRole(role);
        Map<String, Object> map;
        try {
            map = userService.register(u);
        } catch (Exception e) {
            e.printStackTrace();
            return MyUtils.setMyErrorJson(MyConstant.returnGeneralErrorCode, e.getMessage());
        }

        return MyUtils.returnJson(map, (MyError) map.get("error"));
    }

    //密码登录(登录成功返回loginKey)
    @ResponseBody
    @RequestMapping("/loginByPassword")
    public Object loginByPassword(
            @RequestParam(value = "message", required = false) String message,//用户信息（必填）
            @RequestParam(value = "password", required = false) String password,//用户密码（必填）
            HttpServletRequest request
    ) {
        //必填字段校验
        List<Map<String, Object>> fieldList = new LinkedList<>();
        Map<String, Object> fieldMap = new HashMap<>();
        fieldMap.put("用户信息", message);
        fieldMap.put("用户密码", password);
        fieldList.add(fieldMap);
        try {
            MyUtils.fieldCheck(fieldList);
        } catch (Exception e) {
            e.printStackTrace();
            return MyUtils.setMyErrorJson(MyConstant.returnGeneralErrorCode, e.getMessage());
        }
        Date now = new Date();
        User u = new User();
        u.setWrite_date(now);
        u.setActive(true);
        u.setMessage(message);
        u.setPassword(MyUtils.getMD5(password));

        Map<String, Object> map = userService.login(u,message, request);
        return MyUtils.returnJson(map, (MyError) map.get("error"));
    }

    /*//后台管理员登录
    @ResponseBody
    @RequestMapping("/adminLogin")
    public Object adminLogin(
            @RequestParam(value = "user_name", required = false) String user_name,//用户名（必填）
            @RequestParam(value = "password", required = false) String password,//用户密码（必填）
            HttpServletRequest request
    ) {
        //必填字段校验
        List<Map<String, Object>> fieldList = new LinkedList<>();
        Map<String, Object> fieldMap = new HashMap<>();
        fieldMap.put("用户名", user_name);
        fieldMap.put("用户密码", password);
        fieldList.add(fieldMap);
        try {
            MyUtils.fieldCheck(fieldList);
        } catch (Exception e) {
            e.printStackTrace();
            return MyUtils.setMyErrorJson(MyConstant.returnGeneralErrorCode, e.getMessage());
        }
        //验证管理员角色信息
        Date now = new Date();
        User u = new User();
        u.setWrite_date(now);
        u.setActive(true);
        u.setUser_name(user_name);
        u.setPassword(MyUtils.getMD5(password));

        Map<String, Object> map = userService.login(u, request);
        return MyUtils.returnJson(map, (MyError) map.get("error"));
    }*/

    //退出登录
    @ResponseBody
    @RequestMapping("/exitLogin")
    public Object exitLogin(
            @RequestParam(value = "loginKey", required = false) String loginKey
    ) {
        //必填字段校验
        List<Map<String, Object>> fieldList = new LinkedList<>();
        Map<String, Object> fieldMap = new HashMap<>();
        fieldMap.put("loginKey", loginKey);
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
        //删除对应loginKey
        MyUtils.deleteLoginKey(user, loginKey);
        return MyUtils.returnJson(new HashMap<String, Object>(), new MyError());
    }

    //通过loginKey获取角色信息
    @ResponseBody
    @RequestMapping("/getUserByLoginKey")
    public Object getUserByLoginKey(
            @RequestParam(value = "loginKey", required = false) String loginKey
    ) {
        //必填字段校验
        List<Map<String, Object>> fieldList = new LinkedList<>();
        Map<String, Object> fieldMap = new HashMap<>();
        fieldMap.put("loginKey", loginKey);
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
        User u = new User();
        u.setId(user.getId());
        Map<String, Object> map = userService.getUsers(null, u, 0, 1);
        return MyUtils.returnJson(map, (MyError) map.get("error"));
    }
}
