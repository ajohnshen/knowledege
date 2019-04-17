package com.wu.knowledge.book.controller;

import com.wu.knowledge.basedata.dictionary.model.Dictionary;
import com.wu.knowledge.book.model.Book;
import com.wu.knowledge.book.service.IBookService;
import com.wu.knowledge.common.cache.MyCache;
import com.wu.knowledge.common.constant.MyConstant;
import com.wu.knowledge.common.constant.MyErrorMsg;
import com.wu.knowledge.common.model.MyError;
import com.wu.knowledge.common.utils.MyUtils;
import com.wu.knowledge.file.model.File1;
import com.wu.knowledge.user.model.User;
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
 * 〈书籍控制器〉
 *
 * @create create by WSD on 2018/12/18
 */
@Controller
@RequestMapping("/book/BookController")
public class BookController {

    @Resource
    private IBookService bookService;

    //创建书籍
    @ResponseBody
    @RequestMapping("/createBook")
    private Object createBook(
            @RequestParam(value = "loginKey", required = false) String loginKey,
            @RequestParam(value = "iscollection", required = false) Boolean iscollection,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "userID", required = false) Integer userID,
            @RequestParam(value = "sequence", required = false) Integer sequence,
            @RequestParam(value = "synopsis", required = false) String synopsis,
            @RequestParam(value = "cover", required = false) Integer cover,
            @RequestParam(value = "booktype", required = false) Integer booktype,
            @RequestParam(value = "popularity", required = false) Integer popularity,
            @RequestParam(value = "def_popularity", required = false) Integer def_popularity,
            @RequestParam(value = "author", required = false) String author,
            @RequestParam(value = "remark", required = false) String remark,
            @RequestParam(value = "readresponse", required = false) String readresponse,
            @RequestParam(value = "publisher", required = false) String publisher
            ){
        //必填字段校验
        List<Map<String, Object>> fieldList = new LinkedList<>();
        Map<String, Object> fieldMap = new HashMap<>();
        //fieldMap.put("关联用户ID", userID);
        fieldMap.put("书籍名称", name);
        fieldMap.put("loginKey", loginKey);
        fieldList.add(fieldMap);
        try {
            MyUtils.fieldCheck(fieldList);
        } catch (Exception e) {
            e.printStackTrace();
            return MyUtils.setMyErrorJson(MyConstant.returnGeneralErrorCode, e.getMessage());
        }
        User user = MyCache.getLoginUsersMap().get(loginKey);
        if (null == user) {
            return MyUtils.setMyErrorJson(MyConstant.returnNoLoginErrorCode, MyErrorMsg.loginStatusError);
        }
        Book book = new Book();
        book.setIscollection(iscollection);
        book.setSequence(sequence);
        book.setName(name);
        book.setSynopsis(synopsis);
        book.setDef_popularity(def_popularity);
        book.setPopularity(popularity);
        book.setAuthor(author);
        book.setPublisher(publisher);
        book.setRemark(remark);
        book.setReadresponse(readresponse);
        //关联用户
        User u = new User();
        u.setId(userID);
        //book.setUserID(u);
        book.setUserID(user);
        //关联文件
        File1 fileBook = new File1();
        fileBook.setId(cover);
        book.setCover(fileBook);
        //关联字典数据
        Dictionary booktypeDic = new Dictionary();
        booktypeDic.setId(booktype);
        book.setBooktype(booktypeDic);
        
        Map<String, Object> map = bookService.createBook(book);
        return MyUtils.returnJson(map, (MyError) map.get("error"));
    }

    //更新书籍
    @ResponseBody
    @RequestMapping("/updateBook")
    private Object updateBook(
            @RequestParam(value = "loginKey", required = false) String loginKey,
            @RequestParam(value = "id", required = false) Integer id,
            @RequestParam(value = "userID", required = false) Integer userID,
            @RequestParam(value = "iscollection", required = false) Boolean iscollection,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "sequence", required = false) Integer sequence,
            @RequestParam(value = "synopsis", required = false) String synopsis,
            @RequestParam(value = "cover", required = false) Integer cover,
            @RequestParam(value = "booktype", required = false) Integer booktype,
            @RequestParam(value = "popularity", required = false) Integer popularity,
            @RequestParam(value = "def_popularity", required = false) Integer def_popularity,
            @RequestParam(value = "author", required = false) String author,
            @RequestParam(value = "remark", required = false) String remark,
            @RequestParam(value = "readresponse", required = false) String readresponse,
            @RequestParam(value = "publisher", required = false) String publisher
    ){
        //必填字段校验
        List<Map<String, Object>> fieldList = new LinkedList<>();
        Map<String, Object> fieldMap = new HashMap<>();
        fieldMap.put("书籍ID", id);
        fieldMap.put("loginKey", loginKey);
        fieldList.add(fieldMap);
        try {
            MyUtils.fieldCheck(fieldList);
        } catch (Exception e) {
            e.printStackTrace();
            return MyUtils.setMyErrorJson(MyConstant.returnGeneralErrorCode, e.getMessage());
        }
        User user = MyCache.getLoginUsersMap().get(loginKey);
        if (null == user) {
            return MyUtils.setMyErrorJson(MyConstant.returnNoLoginErrorCode, MyErrorMsg.loginStatusError);
        }
        Book book = new Book();
        book.setId(id);
        book.setIscollection(iscollection);
        book.setSequence(sequence);
        book.setName(name);
        book.setSynopsis(synopsis);
        book.setDef_popularity(def_popularity);
        book.setPopularity(popularity);
        book.setAuthor(author);
        book.setPublisher(publisher);
        book.setRemark(remark);
        book.setReadresponse(readresponse);
        //关联用户
        User u = new User();
        u.setId(userID);
        book.setUserID(u);
        //关联文件
        File1 fileBook = new File1();
        fileBook.setId(cover);
        book.setCover(fileBook);
        //关联字典数据
        Dictionary booktypeDic = new Dictionary();
        booktypeDic.setId(booktype);
        book.setBooktype(booktypeDic);

        Map<String, Object> map = bookService.updateBook(book);
        return MyUtils.returnJson(map, (MyError) map.get("error"));
    }

    //条件查询书籍
    @ResponseBody
    @RequestMapping("/getBooks")
    private Object getBooks(
            @RequestParam(value = "loginKey", required = false) String loginKey,
            @RequestParam(value = "id", required = false) Integer id,
            @RequestParam(value = "userID", required = false) Integer userID,
            @RequestParam(value = "iscollection", required = false) Boolean iscollection,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "synopsis", required = false) String synopsis,
            @RequestParam(value = "booktypeIDs", required = false) String booktypeIDs,
            @RequestParam(value = "pageNo", required = false) String pageNo,//页码从0开始
            @RequestParam(value = "pageSize", required = false) String pageSize//每页显示数
    ){
        User user = null;
        if (MyUtils.StringIsNotNull(loginKey)) {
            user = MyCache.getLoginUsersMap().get(loginKey);
            if (null == user) {
                return MyUtils.setMyErrorJson(MyConstant.returnNoLoginErrorCode, MyErrorMsg.loginStatusError);
            }
        }
        Map<String, Object> map;
        Book book = new Book();
        book.setId(id);
        book.setIscollection(iscollection);
        book.setName(name);
        book.setSynopsis(synopsis);
        //关联用户
        User u = new User();
        u.setId(userID);
        book.setUserID(u);
        try {
            //书籍类型Id
            book.setBooktypeIDs(MyUtils.arrayChange(booktypeIDs));
        } catch (Exception e1) {
            e1.printStackTrace();
            return MyUtils.setMyErrorJson(MyConstant.returnGeneralErrorCode, e1.getMessage());
        }
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
        map = bookService.getBooks(book,user,pNo,pSize);
        return MyUtils.returnJson(map, (MyError) map.get("error"));
    }

    //删除书籍
    @ResponseBody
    @RequestMapping("/deleteBook")
    public Object deleteBook(
            @RequestParam(value = "loginKey", required = false) String loginKey,
            @RequestParam(value = "ids", required = false) String ids
    ) {
        //必填字段校验
        List<Map<String, Object>> fieldList = new LinkedList<>();
        Map<String, Object> fieldMap = new HashMap<>();
        fieldMap.put("loginKey", loginKey);
        fieldMap.put("书籍id", ids);
        fieldList.add(fieldMap);
        try {
            MyUtils.fieldCheck(fieldList);
        } catch (Exception e) {
            e.printStackTrace();
            return MyUtils.setMyErrorJson(MyConstant.returnGeneralErrorCode, e.getMessage());
        }
        User user = null;
        if (MyUtils.StringIsNotNull(loginKey)) {
            user = MyCache.getLoginUsersMap().get(loginKey);
            if (null == user) {
                return MyUtils.setMyErrorJson(MyConstant.returnNoLoginErrorCode, MyErrorMsg.loginStatusError);
            }
        }
        Integer[] Ids;
        try {
            Ids = MyUtils.arrayChange(ids);
        } catch (Exception e) {
            e.printStackTrace();
            return MyUtils.setMyErrorJson(MyConstant.returnGeneralErrorCode, e.getMessage());
        }
        Map<String, Object> map = bookService.deleteBook(Ids);
        return MyUtils.returnJson(map, (MyError) map.get("error"));
    }
}
