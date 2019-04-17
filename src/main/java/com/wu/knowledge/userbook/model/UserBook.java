package com.wu.knowledge.userbook.model;

import com.wu.knowledge.book.model.Book;
import com.wu.knowledge.common.model.BaseModel;
import com.wu.knowledge.user.model.User;

/**
 * 〈功能简述〉<br>
 * 〈用户书籍实体类〉
 *
 * @create create by WSD on 2019/2/25
 */
public class UserBook extends BaseModel {

    /**
     * 关联用户
     */
    private User user;

    /**
     * 关联书籍
     */
    private Book book;

    /**
     * 是否收藏
     */
    private Boolean is_collect;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Boolean getIs_collect() {
        return is_collect;
    }

    public void setIs_collect(Boolean is_collect) {
        this.is_collect = is_collect;
    }
}
