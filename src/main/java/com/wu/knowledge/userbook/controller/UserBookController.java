package com.wu.knowledge.userbook.controller;

import com.wu.knowledge.book.model.Book;
import com.wu.knowledge.common.cache.MyCache;
import com.wu.knowledge.common.constant.MyConstant;
import com.wu.knowledge.common.constant.MyErrorMsg;
import com.wu.knowledge.common.model.MyError;
import com.wu.knowledge.common.utils.MyUtils;
import com.wu.knowledge.user.model.User;
import com.wu.knowledge.userbook.model.UserBook;
import com.wu.knowledge.userbook.service.IUserBookService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 〈功能简述〉<br>
 * 〈用户书籍控制器〉
 *
 * @create create by WSD on 2018/12/18
 */
@Controller
@RequestMapping("/userBook/UserBookController")
public class UserBookController {

    @Resource
    private IUserBookService userBookService;

    //创建用户书籍
    @ResponseBody
    @RequestMapping("/createUserBook")
    private Object createUserBook(
            @RequestParam(value = "loginKey", required = false) String loginKey,
            @RequestParam(value = "user", required = false) Integer user,
            @RequestParam(value = "book", required = false) Integer book,
            @RequestParam(value = "is_collect", required = false) Boolean is_collect
            ){
        //必填字段校验
        List<Map<String, Object>> fieldList = new LinkedList<>();
        Map<String, Object> fieldMap = new HashMap<>();
        //fieldMap.put("loginKey", loginKey);
        fieldMap.put("关联书籍", book);
        fieldMap.put("关联用户", user);
        fieldList.add(fieldMap);
        try {
            MyUtils.fieldCheck(fieldList);
        } catch (Exception e) {
            e.printStackTrace();
            return MyUtils.setMyErrorJson(MyConstant.returnGeneralErrorCode, e.getMessage());
        }
        User user1 = null;
        /*User user = MyCache.getLoginUsersMap().get(loginKey);
        if (null == user) {
            return MyUtils.setMyErrorJson(MyConstant.returnNoLoginErrorCode, MyErrorMsg.loginStatusError);
        }*/
        UserBook userBook = new UserBook();
        userBook.setIs_collect(is_collect);
        //关联书籍
        Book b = new Book();
        b.setId(book);
        userBook.setBook(b);
        //关联用户
        User u = new User();
        u.setId(user);
        userBook.setUser(u);

        Map<String, Object> map = userBookService.createUserBook(userBook);
        return MyUtils.returnJson(map, (MyError) map.get("error"));
    }

    //更新用户书籍
    @ResponseBody
    @RequestMapping("/updateUserBook")
    private Object updateUserBook(
            @RequestParam(value = "loginKey", required = false) String loginKey,
            @RequestParam(value = "id", required = false) Integer id,
            @RequestParam(value = "user", required = false) Integer user,
            @RequestParam(value = "book", required = false) Integer book,
            @RequestParam(value = "is_collect", required = false) Boolean is_collect
    ){
        //必填字段校验
        List<Map<String, Object>> fieldList = new LinkedList<>();
        Map<String, Object> fieldMap = new HashMap<>();
        fieldMap.put("用户书籍ID", id);
        fieldList.add(fieldMap);
        try {
            MyUtils.fieldCheck(fieldList);
        } catch (Exception e) {
            e.printStackTrace();
            return MyUtils.setMyErrorJson(MyConstant.returnGeneralErrorCode, e.getMessage());
        }
        User user1 = null;
        /*User user = MyCache.getLoginUsersMap().get(loginKey);
        if (null == user) {
            return MyUtils.setMyErrorJson(MyConstant.returnNoLoginErrorCode, MyErrorMsg.loginStatusError);
        }*/
        UserBook userBook = new UserBook();
        userBook.setId(id);
        userBook.setIs_collect(is_collect);
        //关联书籍
        Book b = new Book();
        b.setId(book);
        userBook.setBook(b);
        //关联用户
        User u = new User();
        u.setId(user);
        userBook.setUser(u);

        Map<String, Object> map = userBookService.updateUserBook(userBook);
        return MyUtils.returnJson(map, (MyError) map.get("error"));
    }

    //条件查询用户书籍
    @ResponseBody
    @RequestMapping("/getUserBooks")
    private Object getUserBooks(
            @RequestParam(value = "loginKey", required = false) String loginKey,
            @RequestParam(value = "id", required = false) Integer id,
            @RequestParam(value = "user", required = false) Integer user,
            @RequestParam(value = "book", required = false) Integer book,
            @RequestParam(value = "is_collect", required = false) Boolean is_collect,
            @RequestParam(value = "pageNo", required = false) String pageNo,//页码从0开始
            @RequestParam(value = "pageSize", required = false) String pageSize//每页显示数
    ){
        User us = null;
        if (MyUtils.StringIsNotNull(loginKey)) {
            us = MyCache.getLoginUsersMap().get(loginKey);
            if (null == user) {
                return MyUtils.setMyErrorJson(MyConstant.returnNoLoginErrorCode, MyErrorMsg.loginStatusError);
            }
        }
        Map<String, Object> map;
        UserBook userBook = new UserBook();
        userBook.setId(id);
        userBook.setIs_collect(is_collect);
        //关联书籍
        Book b = new Book();
        b.setId(book);
        userBook.setBook(b);
        //关联用户
        User u = new User();
        u.setId(user);
        userBook.setUser(u);
       
        //分页数据
        int pNo;
        int pSize;
        try {
            pNo = MyUtils.getPageNo(pageNo);
            pSize = MyUtils.getPageSize(pageSize);
        } catch (Exception e1) {
            e1.printStackTrace();
            return MyUtils.setMyErrorJson(MyConstant.returnGeneralErrorCode, MyErrorMsg.pagingError);
        }
        map = userBookService.getUserBooks(userBook,us,pNo,pSize);
        return MyUtils.returnJson(map, (MyError) map.get("error"));
    }
}
