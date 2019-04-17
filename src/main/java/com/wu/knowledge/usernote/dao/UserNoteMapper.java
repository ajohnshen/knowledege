package com.wu.knowledge.usernote.dao;


import com.wu.knowledge.usernote.model.UserNote;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 〈功能简述〉<br>
 * 〈用户笔记Dao层接口〉
 *
 * @create create by WSD on 2018/12/18
 */
@Mapper
public interface UserNoteMapper {

    /**
     * 通过ID查询单个工位或办公室
     */
    UserNote getUserNoteById(Integer id);

    /**
     * 条件查询用户笔记
     */
    int totalSize(UserNote userNote);

    /**
     * 添加一条用户笔记
     */
    int createUserNote(UserNote userNote);

    /**
     * 更新一条用户笔记
     */
    int updateUserNote(UserNote userNote);

    /**
     *  条件查询用户笔记
     * @param userNote     用户笔记对象
     * @param pageStart 查询的起始位置(下标从0开始)
     * @param pageSize  查询的数量
     */
    List<UserNote> getUserNotes(@Param("UserNote") UserNote userNote, @Param("pageStart") Integer pageStart,
                                    @Param("pageSize") Integer pageSize);
}
