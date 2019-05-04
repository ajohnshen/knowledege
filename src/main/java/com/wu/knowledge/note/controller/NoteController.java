package com.wu.knowledge.note.controller;

import com.wu.knowledge.basedata.dictionary.model.Dictionary;
import com.wu.knowledge.common.cache.MyCache;
import com.wu.knowledge.common.constant.MyConstant;
import com.wu.knowledge.common.constant.MyErrorMsg;
import com.wu.knowledge.common.model.MyError;
import com.wu.knowledge.common.utils.MyUtils;
import com.wu.knowledge.file.model.File1;
import com.wu.knowledge.note.model.Note;
import com.wu.knowledge.note.service.INoteService;
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
 * 〈记事本控制器〉
 *
 * @create create by WSD on 2018/1/28
 */
@Controller
@RequestMapping("/note/NoteController")
public class NoteController {

    @Resource
    private INoteService noteService;

    //创建记事本
    @ResponseBody
    @RequestMapping("/createNote")
    private Object createNote(
            @RequestParam(value = "loginKey", required = false) String loginKey,
            @RequestParam(value = "iscollection", required = false) Boolean iscollection,
            @RequestParam(value = "userID", required = false) Integer userID,
            @RequestParam(value = "sequence", required = false) Integer sequence,
            @RequestParam(value = "synopsis", required = false) String synopsis,
            @RequestParam(value = "cover", required = false) Integer cover,
            @RequestParam(value = "notetype", required = false) Integer notetype,
            @RequestParam(value = "title", required = false) String title
            ){
        //必填字段校验
        List<Map<String, Object>> fieldList = new LinkedList<>();
        Map<String, Object> fieldMap = new HashMap<>();
        fieldMap.put("记事本标题", title);
        fieldMap.put("loginKey", loginKey);
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
        Note note = new Note();
        note.setIscollection(iscollection);
        note.setSequence(sequence);
        note.setTitle(title);
        note.setSynopsis(synopsis);
        note.setTitle(title);
        //关联用户
        //User u = new User();
       // u.setId(userID);
        //note.setUserID(u);
        note.setUserID(user);
        //关联文件
        File1 fileNote = new File1();
        fileNote.setId(cover);
        //note.setCover(fileNote);
        //关联字典
        Dictionary notetypeDic = new Dictionary();
        notetypeDic.setId(notetype);
        note.setNotetype(notetypeDic);
        
        Map<String, Object> map = noteService.createNote(note);
        return MyUtils.returnJson(map, (MyError) map.get("error"));
    }

    //更新记事本
    @ResponseBody
    @RequestMapping("/updateNote")
    private Object updateNote(
            @RequestParam(value = "loginKey", required = false) String loginKey,
            @RequestParam(value = "id", required = false) Integer id,
            @RequestParam(value = "iscollection", required = false) Boolean iscollection,
            @RequestParam(value = "userID", required = false) Integer userID,
            @RequestParam(value = "sequence", required = false) Integer sequence,
            @RequestParam(value = "synopsis", required = false) String synopsis,
            @RequestParam(value = "cover", required = false) Integer cover,
            @RequestParam(value = "notetype", required = false) Integer notetype,
            @RequestParam(value = "title", required = false) String title
    ){
        //必填字段校验
        List<Map<String, Object>> fieldList = new LinkedList<>();
        Map<String, Object> fieldMap = new HashMap<>();
        fieldMap.put("loginKey", loginKey);
        fieldMap.put("记事本ID", id);
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
        Note note = new Note();
        note.setId(id);
        note.setIscollection(iscollection);
        note.setSequence(sequence);
        note.setSynopsis(synopsis);
        note.setTitle(title);
        //关联用户
        User u = new User();
        u.setId(userID);
        note.setUserID(u);
        //关联文件
        File1 fileNote = new File1();
        fileNote.setId(cover);
        //note.setCover(fileNote);
        //关联字典
        Dictionary notetypeDic = new Dictionary();
        notetypeDic.setId(notetype);
        note.setNotetype(notetypeDic);

        Map<String, Object> map = noteService.updateNote(note);
        return MyUtils.returnJson(map, (MyError) map.get("error"));
    }

    //条件查询记事本
    @ResponseBody
    @RequestMapping("/getNotes")
    private Object getNotes(
            @RequestParam(value = "loginKey", required = false) String loginKey,
            @RequestParam(value = "id", required = false) Integer id,
            @RequestParam(value = "iscollection", required = false) Boolean iscollection,
            @RequestParam(value = "userID", required = false) Integer userID,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "synopsis", required = false) String synopsis,
            @RequestParam(value = "notetypeIDs", required = false) String notetypeIDs,
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
        Note note = new Note();
        note.setId(id);
        note.setIscollection(iscollection);
        note.setTitle(title);
        note.setSynopsis(synopsis);
        //关联用户
        User u = new User();
        u.setId(userID);
        note.setUserID(u);
        try {
            //课程类型Id
            note.setNotetypeIDs(MyUtils.arrayChange(notetypeIDs));
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
        map = noteService.getNotes(note,user,pNo,pSize);
        return MyUtils.returnJson(map, (MyError) map.get("error"));
    }

    //删除笔记
    @ResponseBody
    @RequestMapping("/deleteNote")
    public Object deleteNote(
            @RequestParam(value = "loginKey", required = false) String loginKey,
            @RequestParam(value = "ids", required = false) String ids
    ) {
        //必填字段校验
        List<Map<String, Object>> fieldList = new LinkedList<>();
        Map<String, Object> fieldMap = new HashMap<>();
        fieldMap.put("loginKey", loginKey);
        fieldMap.put("笔记id", ids);
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
        Map<String, Object> map = noteService.deleteNote(Ids);
        return MyUtils.returnJson(map, (MyError) map.get("error"));
    }
}
