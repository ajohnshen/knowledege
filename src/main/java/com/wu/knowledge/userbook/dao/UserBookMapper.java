package com.wu.knowledge.userbook.dao;


import com.wu.knowledge.userbook.model.UserBook;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


import java.util.List;

/**
 * 〈功能简述〉<br>
 * 〈用户书籍Dao层接口〉
 *
 * @create create by WSD on 2018/12/18
 */
@Mapper
public interface UserBookMapper {

    /**
     * 通过ID查询单个工位或办公室
     */
    UserBook getUserBookById(Integer id);

    /**
     * 条件查询用户书籍
     */
    int totalSize(UserBook userBook);

    /**
     * 添加一条用户书籍
     */
    int createUserBook(UserBook userBook);

    /**
     * 更新一条用户书籍
     */
    int updateUserBook(UserBook userBook);

    /**
     *  条件查询用户书籍
     * @param userBook     用户书籍对象
     * @param pageStart 查询的起始位置(下标从0开始)
     * @param pageSize  查询的数量
     */
    List<UserBook> getUserBooks(@Param("UserBook") UserBook userBook, @Param("pageStart") Integer pageStart,
                                @Param("pageSize") Integer pageSize);
}
