package com.wu.knowledge.note.service;

import com.wu.knowledge.note.model.Note;
import com.wu.knowledge.user.model.User;

import java.util.Map;

/**
 * 〈功能简述〉<br>
 * 记事本服务层接口
 * @create create by WSD on 2018/12/18
 */
public interface INoteService {
    /**
     * 条件查询工位或办公室
     */
    Map<String, Object> getNotes(Note note, User user, Integer pageNo, Integer pageSize);

    /**
     * 添加一条笔记
     */
    Map<String, Object> createNote(Note note);

    /**
     * 更新一条笔记
     */
    Map<String, Object> updateNote(Note note);

    /**
     * 删除笔记
     */
    Map<String, Object> deleteNote(Integer[] ids);
}
