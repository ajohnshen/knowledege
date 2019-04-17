package com.wu.knowledge.book.service.impl;

import com.wu.knowledge.basedata.dictionary.dao.DictionaryMapper;
import com.wu.knowledge.book.dao.BookMapper;
import com.wu.knowledge.book.model.Book;
import com.wu.knowledge.book.service.IBookService;
import com.wu.knowledge.common.constant.MyConstant;
import com.wu.knowledge.common.constant.MyErrorMsg;
import com.wu.knowledge.common.model.MyError;
import com.wu.knowledge.common.utils.MyUtils;
import com.wu.knowledge.file.dao.FileMapper;
import com.wu.knowledge.user.model.User;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 〈功能简述〉<br>
 * 〈书籍服务层实现类〉
 *
 * @create create by WSD on 2018/12/18
 */
@Service("bookService")
public class BookServiceImpl implements IBookService {

    @Resource
    private BookMapper bookMapper;
    @Resource
    private DictionaryMapper dictionaryMapper;
    @Resource
    private FileMapper fileMapper;

    //条件查询书籍
    @Override
    public Map<String, Object> getBooks(Book book, User user, Integer pageNo, Integer pageSize) {
        Map<String, Object> mapObj = new HashMap<>();
        MyError myError = new MyError();
        int pageStartNo = pageSize * pageNo;
        int pageEndNo = pageSize * (pageNo + 1);
        //条件查询出的总数
        int totalSize = bookMapper.totalSize(book);
        mapObj.put("totalSize", totalSize);
        //判断下一页是否还有数据
        boolean isNext = false;
        if (totalSize > pageEndNo) {
            isNext = true;
        }
        mapObj.put("isNext", isNext);
        List<Book> books = bookMapper.getBooks(book,pageStartNo,pageSize);
        List<Map<String, Object>> listMap = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(books)){
            for(Book b : books){
                Map<String, Object> mapOt = new HashMap<>();
                if (null != user && null != user.getId()) {
                    //管理员角色可查看数据
                    if (MyConstant.UserRole.UserRoleAdmin.toString().equals(dictionaryMapper.getDictionaryById(user.getRole().getId()).getCode())) {
                        //mapOt = MyUtils.getBaseResultMap(b, userMapper);
                    }
                }
                mapOt.put("id",b.getId());
                mapOt.put("name",b.getName());
                //排序
                mapOt.put("sequence", b.getSequence());
                //是否收藏
                mapOt.put("iscollection", b.getIscollection());
                mapOt.put("synopsis",b.getSynopsis());
                mapOt.put("def_popularity",b.getDef_popularity());
                mapOt.put("popularity",b.getPopularity());
                mapOt.put("author",b.getAuthor());
                mapOt.put("publisher",b.getPublisher());
                mapOt.put("remark",b.getRemark());
                mapOt.put("readresponse",b.getReadresponse());
                if (b.getBooktype() != null) {
                    //类型ID
                    mapOt.put("bookType_id",b.getBooktype().getId());
                    if(null!=dictionaryMapper.getDictionaryById(b.getBooktype().getId())){
                        //书籍类型名称
                        mapOt.put("bookType_name",dictionaryMapper.getDictionaryById(b.getBooktype().getId()).getName() );
                    }
                }else{
                    mapOt.put("bookType_id", "");
                    mapOt.put("bookType_name","" );
                }
                //书籍封面
                //MyUtils.packFileMap("cover", b.getCover(), mapOt, fileMapper, dictionaryMapper, false);
                if(b.getCover() != null){
                    mapOt.put("logoPath","../knowledge/"+fileMapper.getFileById(b.getCover().getId()).getFile_path());
                    mapOt.put("indexLogoPath","knowledge/"+fileMapper.getFileById(b.getCover().getId()).getFile_path());
                }

                listMap.add(mapOt);
            }
        }
        mapObj.put("list",listMap);
        return MyUtils.setDateMap(myError,mapObj);
    }

    //创建书籍
    @Override
    public Map<String, Object> createBook(Book book) {
        Map<String, Object> mapObj = new HashMap<>();
        MyError myError = new MyError();
        //设置创建人和时间，修改人和时间
        User user =new User();
        Date date = new Date();
        book.setCreate_user(user);
        book.setWrite_user(user);
        book.setCreate_date(date);
        book.setWrite_date(date);
        int count;
        try {
            count = bookMapper.createBook(book);
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

    //更新书籍
    @Override
    public Map<String, Object> updateBook(Book book) {
        Map<String, Object> mapObj = new HashMap<>();
        MyError myError = new MyError();
        //设置修改人和时间
        User user = new User();
        Date date = new Date();
        book.setWrite_user(user);
        book.setWrite_date(date);
        int count;
        try {
            count = bookMapper.updateBook(book);
            if (count <= 0) {
                return MyUtils.setMyErrorMap(MyConstant.returnGeneralErrorCode, MyErrorMsg.updateError);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return MyUtils.setMyErrorMap(MyConstant.returnGeneralErrorCode, MyErrorMsg.dbError);
        }
        return MyUtils.setDateMap(myError, mapObj);
    }

    //删除书籍
    @Override
    public Map<String, Object> deleteBook(Integer[] ids) {
        Map<String, Object> mapObj = new HashMap<>();
        MyError myError = new MyError();
        int count = bookMapper.deleteBook(ids);
        if (count < 1) {
            return MyUtils.setMyErrorMap(MyConstant.returnGeneralErrorCode, MyErrorMsg.deleteError);
        }
        return MyUtils.setDateMap(myError, mapObj);
    }
}
