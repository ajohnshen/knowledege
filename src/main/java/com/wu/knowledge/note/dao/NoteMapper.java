package com.wu.knowledge.note.dao;

import com.wu.knowledge.note.model.Note;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 〈功能简述〉<br>
 * 〈记事本Dao层接口〉
 *
 * @create create by WSD on 2018/12/18
 */
@Mapper
public interface NoteMapper {

    /**
     * 通过ID查询笔记
     */
    Note getNoteById(Integer id);

    /**
     * 条件查询笔记数量
     */
    int totalSize(Note note);

    /**
     * 添加一条笔记
     */
    int createNote(Note note);

    /**
     * 更新一条笔记
     */
    int updateNote(Note note);

    /**
     * 删除笔记
     */
    int deleteNote(Integer[] ids);

    /**
     *  条件查询笔记
     * @param note     笔记对象
     * @param pageStart 查询的起始位置(下标从0开始)
     * @param pageSize  查询的数量
     */
    List<Note> getNotes(@Param("Note") Note note, @Param("pageStart") Integer pageStart,
                        @Param("pageSize") Integer pageSize);
}
