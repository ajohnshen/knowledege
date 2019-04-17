package com.wu.knowledge.book.dao;

import com.wu.knowledge.book.model.Book;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 〈功能简述〉<br>
 * 〈书籍Dao层接口〉
 *
 * @create create by WSD on 2018/12/18
 */
@Mapper
public interface BookMapper {

    /**
     * 通过ID查询单个书籍
     */
    Book getBookById(Integer id);

    /**
     * 条件查询书籍数量
     */
    int totalSize(Book book);

    /**
     * 添加一条书籍
     */
    int createBook(Book book);

    /**
     * 更新一条书籍
     */
    int updateBook(Book book);

    /**
     * 删除书籍
     */
    int deleteBook(Integer[] ids);

    /**
     *  条件查询书籍
     * @param book     书籍对象
     * @param pageStart 查询的起始位置(下标从0开始)
     * @param pageSize  查询的数量
     */
    List<Book> getBooks(@Param("Book") Book book, @Param("pageStart") Integer pageStart,
                                  @Param("pageSize") Integer pageSize);
}
