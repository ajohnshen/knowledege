package com.wu.knowledge.book.service;

import com.wu.knowledge.book.model.Book;
import com.wu.knowledge.user.model.User;

import java.util.Map;

/**
 * 〈功能简述〉<br>
 * 书籍服务层接口
 * @create create by WSD on 2018/12/18
 */
public interface IBookService {
    /**
     * 条件查询工位或办公室
     */
    Map<String, Object> getBooks(Book book, User user, Integer pageNo, Integer pageSize);

    /**
     * 添加一条书籍
     */
    Map<String, Object> createBook(Book book);

    /**
     * 更新一条书籍
     */
    Map<String, Object> updateBook(Book book);

    /**
     * 删除书籍
     */
    Map<String, Object> deleteBook(Integer[] ids);
}
