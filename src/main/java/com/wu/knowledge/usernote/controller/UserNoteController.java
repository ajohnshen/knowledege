package com.wu.knowledge.usernote.controller;

import com.wu.knowledge.common.cache.MyCache;
import com.wu.knowledge.common.constant.MyConstant;
import com.wu.knowledge.common.constant.MyErrorMsg;
import com.wu.knowledge.common.model.MyError;
import com.wu.knowledge.common.utils.MyUtils;
import com.wu.knowledge.note.model.Note;
import com.wu.knowledge.user.model.User;
import com.wu.knowledge.usernote.model.UserNote;
import com.wu.knowledge.usernote.service.IUserNoteService;
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
 * 〈用户笔记控制器〉
 *
 * @create create by WSD on 2018/12/18
 */
@Controller
@RequestMapping("/userNote/UserNoteController")
public class UserNoteController {

    @Resource
    private IUserNoteService userNoteService;

    //创建用户笔记
    @ResponseBody
    @RequestMapping("/createUserNote")
    private Object createUserNote(
            @RequestParam(value = "loginKey", required = false) String loginKey,
            @RequestParam(value = "user", required = false) Integer user,
            @RequestParam(value = "note", required = false) Integer note,
            @RequestParam(value = "is_collect", required = false) Boolean is_collect
            ){
        //必填字段校验
        List<Map<String, Object>> fieldList = new LinkedList<>();
        Map<String, Object> fieldMap = new HashMap<>();
        //fieldMap.put("loginKey", loginKey);
        fieldMap.put("关联笔记", note);
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
        UserNote userNote = new UserNote();
        userNote.setIs_collect(is_collect);
        //关联笔记
        Note b = new Note();
        b.setId(note);
        userNote.setNote(b);
        //关联用户
        User u = new User();
        u.setId(user);
        userNote.setUser(u);

        Map<String, Object> map = userNoteService.createUserNote(userNote);
        return MyUtils.returnJson(map, (MyError) map.get("error"));
    }

    //更新用户笔记
    @ResponseBody
    @RequestMapping("/updateUserNote")
    private Object updateUserNote(
            @RequestParam(value = "loginKey", required = false) String loginKey,
            @RequestParam(value = "id", required = false) Integer id,
            @RequestParam(value = "user", required = false) Integer user,
            @RequestParam(value = "note", required = false) Integer note,
            @RequestParam(value = "is_collect", required = false) Boolean is_collect
    ){
        //必填字段校验
        List<Map<String, Object>> fieldList = new LinkedList<>();
        Map<String, Object> fieldMap = new HashMap<>();
        fieldMap.put("用户笔记ID", id);
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
        UserNote userNote = new UserNote();
        userNote.setId(id);
        userNote.setIs_collect(is_collect);
        //关联笔记
        Note b = new Note();
        b.setId(note);
        userNote.setNote(b);
        //关联用户
        User u = new User();
        u.setId(user);
        userNote.setUser(u);

        Map<String, Object> map = userNoteService.updateUserNote(userNote);
        return MyUtils.returnJson(map, (MyError) map.get("error"));
    }

    //条件查询用户笔记
    @ResponseBody
    @RequestMapping("/getUserNotes")
    private Object getUserNotes(
            @RequestParam(value = "loginKey", required = false) String loginKey,
            @RequestParam(value = "id", required = false) Integer id,
            @RequestParam(value = "user", required = false) Integer user,
            @RequestParam(value = "note", required = false) Integer note,
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
        UserNote userNote = new UserNote();
        userNote.setId(id);
        userNote.setIs_collect(is_collect);
        //关联笔记
        Note b = new Note();
        b.setId(note);
        userNote.setNote(b);
        //关联用户
        User u = new User();
        u.setId(user);
        userNote.setUser(u);
       
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
        map = userNoteService.getUserNotes(userNote,us,pNo,pSize);
        return MyUtils.returnJson(map, (MyError) map.get("error"));
    }
}
