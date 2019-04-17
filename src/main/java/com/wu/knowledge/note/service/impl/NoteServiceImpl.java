package com.wu.knowledge.note.service.impl;

import com.wu.knowledge.basedata.dictionary.dao.DictionaryMapper;
import com.wu.knowledge.common.constant.MyConstant;
import com.wu.knowledge.common.constant.MyErrorMsg;
import com.wu.knowledge.common.model.MyError;
import com.wu.knowledge.common.utils.MyUtils;
import com.wu.knowledge.note.dao.NoteMapper;
import com.wu.knowledge.note.model.Note;
import com.wu.knowledge.note.service.INoteService;
import com.wu.knowledge.user.model.User;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 〈功能简述〉<br>
 * 〈记事本服务层实现类〉
 *
 * @create create by WSD on 2018/12/18
 */
@Service("noteService")
public class NoteServiceImpl implements INoteService {

    @Resource
    private NoteMapper noteMapper;
    @Resource
    private DictionaryMapper dictionaryMapper;

    //条件查询记事本
    @Override
    public Map<String, Object> getNotes(Note note, User user, Integer pageNo, Integer pageSize) {
        Map<String, Object> mapObj = new HashMap<>();
        MyError myError = new MyError();
        int pageStartNo = pageSize * pageNo;
        int pageEndNo = pageSize * (pageNo + 1);
        //条件查询出的总数
        int totalSize = noteMapper.totalSize(note);
        mapObj.put("totalSize", totalSize);
        //判断下一页是否还有数据
        boolean isNext = false;
        if (totalSize > pageEndNo) {
            isNext = true;
        }
        mapObj.put("isNext", isNext);
        List<Note> notes = noteMapper.getNotes(note,pageStartNo,pageSize);
        List<Map<String, Object>> listMap = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(notes)){
            for(Note n : notes){
                Map<String, Object> mapOt = new HashMap<>();
                if (null != user && null != user.getId()) {
                    //管理员角色可查看数据
                    if (MyConstant.UserRole.UserRoleAdmin.toString().equals(dictionaryMapper.getDictionaryById(user.getRole().getId()).getCode())) {
                        //mapOt = MyUtils.getBaseResultMap(n, userMapper);
                    }
                }

                if (n.getNotetype() != null) {
                    //笔记类型ID
                    mapOt.put("notetype_id", n.getNotetype().getId());
                    if (null != dictionaryMapper.getDictionaryById(n.getNotetype().getId())) {
                        //类型名称
                        mapOt.put("notetype_name", dictionaryMapper.getDictionaryById(n.getNotetype().getId()).getName());
                    }
                } else {
                    mapOt.put("notetype_id", "");
                    mapOt.put("notetype_name", "");
                }
                //排序
                mapOt.put("sequence", n.getSequence());
                //是否可用
                mapOt.put("iscollection", n.getIscollection());
                mapOt.put("id",n.getId());
                mapOt.put("title",n.getTitle());
                mapOt.put("synopsis",n.getSynopsis());
                //修改时间
                mapOt.put("update_date", MyUtils.dateToStr(n.getWrite_date()));
                //记录时间
                mapOt.put("create_date", MyUtils.dateToStr(n.getCreate_date()));
                listMap.add(mapOt);
            }
        }
        mapObj.put("list",listMap);
        return MyUtils.setDateMap(myError,mapObj);
    }

    //创建记事本
    @Override
    public Map<String, Object> createNote(Note note) {
        Map<String, Object> mapObj = new HashMap<>();
        MyError myError = new MyError();
        //设置创建人和时间，修改人和时间
        User user =new User();
        Date date = new Date();
        note.setCreate_user(user);
        note.setWrite_user(user);
        note.setCreate_date(date);
        note.setWrite_date(date);
        int count;
        try {
            count = noteMapper.createNote(note);
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

    //更新记事本
    @Override
    public Map<String, Object> updateNote(Note note) {
        Map<String, Object> mapObj = new HashMap<>();
        MyError myError = new MyError();
        //设置修改人和时间
        User user = new User();
        Date date = new Date();
        note.setWrite_user(user);
        note.setWrite_date(date);
        int count;
        try {
            count = noteMapper.updateNote(note);
            if (count <= 0) {
                return MyUtils.setMyErrorMap(MyConstant.returnGeneralErrorCode, MyErrorMsg.updateError);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return MyUtils.setMyErrorMap(MyConstant.returnGeneralErrorCode, MyErrorMsg.dbError);
        }
        return MyUtils.setDateMap(myError, mapObj);
    }

    //删除笔记
    @Override
    public Map<String, Object> deleteNote(Integer[] ids) {
        Map<String, Object> mapObj = new HashMap<>();
        MyError myError = new MyError();
        int count = noteMapper.deleteNote(ids);
        if (count < 1) {
            return MyUtils.setMyErrorMap(MyConstant.returnGeneralErrorCode, MyErrorMsg.deleteError);
        }
        return MyUtils.setDateMap(myError, mapObj);
    }
}
