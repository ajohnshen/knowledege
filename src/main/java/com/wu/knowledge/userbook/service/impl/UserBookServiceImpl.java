package com.wu.knowledge.userbook.service.impl;

import com.wu.knowledge.basedata.dictionary.dao.DictionaryMapper;
import com.wu.knowledge.book.dao.BookMapper;
import com.wu.knowledge.book.model.Book;
import com.wu.knowledge.common.constant.MyConstant;
import com.wu.knowledge.common.constant.MyErrorMsg;
import com.wu.knowledge.common.model.MyError;
import com.wu.knowledge.common.utils.MyUtils;
import com.wu.knowledge.file.dao.FileMapper;
import com.wu.knowledge.user.model.User;
import com.wu.knowledge.userbook.dao.UserBookMapper;
import com.wu.knowledge.userbook.model.UserBook;
import com.wu.knowledge.userbook.service.IUserBookService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 〈功能简述〉<br>
 * 〈用户书籍服务层实现类〉
 *
 * @create create by WSD on 2018/12/18
 */
@Service("userBookService")
public class UserBookServiceImpl implements IUserBookService {

    @Resource
    private UserBookMapper userBookMapper;
    @Resource
    private DictionaryMapper dictionaryMapper;
    @Resource
    private FileMapper fileMapper;
    @Resource
    private BookMapper bookMapper;

    //条件查询用户书籍
    @Override
    public Map<String, Object> getUserBooks(UserBook userBook, User user, Integer pageNo, Integer pageSize) {
        Map<String, Object> mapObj = new HashMap<>();
        MyError myError = new MyError();
        int pageStartNo = pageSize * pageNo;
        int pageEndNo = pageSize * (pageNo + 1);
        //条件查询出的总数
        int totalSize = userBookMapper.totalSize(userBook);
        mapObj.put("totalSize", totalSize);
        //判断下一页是否还有数据
        boolean isNext = false;
        if (totalSize > pageEndNo) {
            isNext = true;
        }
        mapObj.put("isNext", isNext);
        List<UserBook> userBooks = userBookMapper.getUserBooks(userBook,pageStartNo,pageSize);
        List<Map<String, Object>> listMap = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(userBooks)){
            for(UserBook ub : userBooks){
                Map<String, Object> mapOt = new HashMap<>();
                if (null != user && null != user.getId()) {
                    //管理员角色可查看数据
                    if (MyConstant.UserRole.UserRoleAdmin.toString().equals(dictionaryMapper.getDictionaryById(user.getRole().getId()).getCode())) {
                        //mapOt = MyUtils.getBaseResultMap(b, userMapper);

                    }
                }
                    //用户书籍id
                    mapOt.put("id", ub.getId());
                    //是否收藏
                    mapOt.put("is_collect", ub.getIs_collect());
                    //用户ID不为空则获取书籍数据
                    if (null != userBook.getUser() && null != userBook.getUser().getId()) {
                        Book b = bookMapper.getBookById(ub.getBook().getId());
                        mapOt.put("id", b.getId());
                        mapOt.put("name", b.getName());
                        mapOt.put("explain", b.getSynopsis());
                        mapOt.put("def_popularity", b.getDef_popularity());
                        mapOt.put("popularity", b.getPopularity());
                        mapOt.put("author", b.getAuthor());
                        mapOt.put("publisher", b.getAuthor());
                        if (b.getBooktype() != null) {
                            //课程类型ID
                            mapOt.put("bookType_id", b.getBooktype().getId());
                            if (null != dictionaryMapper.getDictionaryById(b.getBooktype().getId())) {
                                //融资阶段名称
                                mapOt.put("bookType_name", dictionaryMapper.getDictionaryById(b.getBooktype().getId()).getName());
                            }
                        } else {
                            mapOt.put("bookType_id", "");
                            mapOt.put("bookType_name", "");
                        }
                    }

                listMap.add(mapOt);
            }
        }
        mapObj.put("list",listMap);
        return MyUtils.setDateMap(myError,mapObj);
    }

    //创建用户书籍
    @Override
    public Map<String, Object> createUserBook(UserBook userBook) {
        Map<String, Object> mapObj = new HashMap<>();
        MyError myError = new MyError();
        //设置创建人和时间，修改人和时间
        User user =new User();
        Date date = new Date();
        userBook.setCreate_user(user);
        userBook.setWrite_user(user);
        userBook.setCreate_date(date);
        userBook.setWrite_date(date);
        int count;
        try {
            count = userBookMapper.createUserBook(userBook);
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

    //更新用户书籍
    @Override
    public Map<String, Object> updateUserBook(UserBook userBook) {
        Map<String, Object> mapObj = new HashMap<>();
        MyError myError = new MyError();
        //设置修改人和时间
        User user = new User();
        Date date = new Date();
        userBook.setWrite_user(user);
        userBook.setWrite_date(date);
        int count;
        try {
            count = userBookMapper.updateUserBook(userBook);
            if (count <= 0) {
                return MyUtils.setMyErrorMap(MyConstant.returnGeneralErrorCode, MyErrorMsg.updateError);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return MyUtils.setMyErrorMap(MyConstant.returnGeneralErrorCode, MyErrorMsg.dbError);
        }
        return MyUtils.setDateMap(myError, mapObj);
    }
}
