package com.wu.knowledge.usernote.service.impl;

import com.wu.knowledge.basedata.dictionary.dao.DictionaryMapper;
import com.wu.knowledge.common.constant.MyConstant;
import com.wu.knowledge.common.constant.MyErrorMsg;
import com.wu.knowledge.common.model.MyError;
import com.wu.knowledge.common.utils.MyUtils;
import com.wu.knowledge.note.dao.NoteMapper;
import com.wu.knowledge.note.model.Note;
import com.wu.knowledge.file.dao.FileMapper;
import com.wu.knowledge.user.model.User;
import com.wu.knowledge.usernote.dao.UserNoteMapper;
import com.wu.knowledge.usernote.model.UserNote;
import com.wu.knowledge.usernote.service.IUserNoteService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 〈功能简述〉<br>
 * 〈用户笔记服务层实现类〉
 *
 * @create create by WSD on 2018/12/18
 */
@Service("userNoteService")
public class UserNoteServiceImpl implements IUserNoteService {

    @Resource
    private UserNoteMapper userNoteMapper;
    @Resource
    private DictionaryMapper dictionaryMapper;
    @Resource
    private FileMapper fileMapper;
    @Resource
    private NoteMapper noteMapper;

    //条件查询用户笔记
    @Override
    public Map<String, Object> getUserNotes(UserNote userNote, User user, Integer pageNo, Integer pageSize) {
        Map<String, Object> mapObj = new HashMap<>();
        MyError myError = new MyError();
        int pageStartNo = pageSize * pageNo;
        int pageEndNo = pageSize * (pageNo + 1);
        //条件查询出的总数
        int totalSize = userNoteMapper.totalSize(userNote);
        mapObj.put("totalSize", totalSize);
        //判断下一页是否还有数据
        boolean isNext = false;
        if (totalSize > pageEndNo) {
            isNext = true;
        }
        mapObj.put("isNext", isNext);
        List<UserNote> userNotes = userNoteMapper.getUserNotes(userNote,pageStartNo,pageSize);
        List<Map<String, Object>> listMap = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(userNotes)){
            for(UserNote un : userNotes){
                Map<String, Object> mapOt = new HashMap<>();
                if (null != user && null != user.getId()) {
                    //管理员角色可查看数据
                    if (MyConstant.UserRole.UserRoleAdmin.toString().equals(dictionaryMapper.getDictionaryById(user.getRole().getId()).getCode())) {
                        //mapOt = MyUtils.getBaseResultMap(b, userMapper);

                    }
                }
                    //用户笔记id
                    mapOt.put("id", un.getId());
                    //是否收藏
                    mapOt.put("is_collect", un.getIs_collect());
                    //用户ID不为空则获取笔记数据
                    if (null != userNote.getUser() && null != userNote.getUser().getId()) {
                        Note n = noteMapper.getNoteById(un.getNote().getId());
                        //排序
                        mapOt.put("sequence", n.getSequence());
                        //是否可用
                        mapOt.put("active", n.getActive());
                        mapOt.put("id",n.getId());
                        mapOt.put("name",n.getName());
                        mapOt.put("synopsis",n.getSynopsis());
                        mapOt.put("title",n.getTitle());
                    }

                listMap.add(mapOt);
            }
        }
        mapObj.put("list",listMap);
        return MyUtils.setDateMap(myError,mapObj);
    }

    //创建用户笔记
    @Override
    public Map<String, Object> createUserNote(UserNote userNote) {
        Map<String, Object> mapObj = new HashMap<>();
        MyError myError = new MyError();
        //设置创建人和时间，修改人和时间
        User user =new User();
        Date date = new Date();
        userNote.setCreate_user(user);
        userNote.setWrite_user(user);
        userNote.setCreate_date(date);
        userNote.setWrite_date(date);
        int count;
        try {
            count = userNoteMapper.createUserNote(userNote);
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

    //更新用户笔记
    @Override
    public Map<String, Object> updateUserNote(UserNote userNote) {
        Map<String, Object> mapObj = new HashMap<>();
        MyError myError = new MyError();
        //设置修改人和时间
        User user = new User();
        Date date = new Date();
        userNote.setWrite_user(user);
        userNote.setWrite_date(date);
        int count;
        try {
            count = userNoteMapper.updateUserNote(userNote);
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
