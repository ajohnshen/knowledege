package com.wu.knowledge.usernote.service;

import com.wu.knowledge.user.model.User;
import com.wu.knowledge.usernote.model.UserNote;

import java.util.Map;

/**
 * 〈功能简述〉<br>
 * 用户笔记服务层接口
 * @create create by WSD on 2018/12/18
 */
public interface IUserNoteService {
    /**
     * 条件查询用户笔记
     */
    Map<String, Object> getUserNotes(UserNote userNote, User user, Integer pageNo, Integer pageSize);

    /**
     * 添加一条用户笔记
     */
    Map<String, Object> createUserNote(UserNote userNote);

    /**
     * 更新一条用户笔记
     */
    Map<String, Object> updateUserNote(UserNote userNote);
}
