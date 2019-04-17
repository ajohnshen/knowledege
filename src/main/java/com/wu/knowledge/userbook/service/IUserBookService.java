package com.wu.knowledge.userbook.service;

import com.wu.knowledge.user.model.User;
import com.wu.knowledge.userbook.model.UserBook;

import java.util.Map;

/**
 * 〈功能简述〉<br>
 * 用户书籍服务层接口
 * @create create by WSD on 2018/12/18
 */
public interface IUserBookService {
    /**
     * 条件查询用户书籍
     */
    Map<String, Object> getUserBooks(UserBook userBook, User user, Integer pageNo, Integer pageSize);

    /**
     * 添加一条用户书籍
     */
    Map<String, Object> createUserBook(UserBook userBook);

    /**
     * 更新一条用户书籍
     */
    Map<String, Object> updateUserBook(UserBook userBook);
}
