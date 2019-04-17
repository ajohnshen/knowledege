package com.wu.knowledge.basedata.dictionary.service.impl;

import com.wu.knowledge.basedata.dictionary.dao.DictionaryMapper;
import com.wu.knowledge.basedata.dictionary.model.Dictionary;
import com.wu.knowledge.basedata.dictionary.service.IDictionaryService;
import com.wu.knowledge.common.constant.MyConstant;
import com.wu.knowledge.common.constant.MyErrorMsg;
import com.wu.knowledge.common.model.MyError;
import com.wu.knowledge.common.utils.MyUtils;
import com.wu.knowledge.user.dao.UserMapper;
import com.wu.knowledge.user.model.User;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 字典数据服务层实现类
 * Created by TZ on 2018/09/14.
 */
@Service("dictionaryService")
public class DictionaryServiceImpl implements IDictionaryService {

    @Resource
    private DictionaryMapper dictionaryMapper;
    @Resource
    private UserMapper userMapper;
    //暂时未做缓存，缓存框架搭好后只有对应的服务层才能直接调用DAO

    //条件查询字典数据
    @Override
    public Map<String, Object> getDictionaries(User user, Dictionary dictionary, Integer pageNo, Integer pageSize) {
        Map<String, Object> mapObj = new HashMap<>();
        MyError myError = new MyError();
        int pageStartNo = pageSize * pageNo;
        int pageEndNo = pageSize * (pageNo + 1);
        //条件查询出的总数
        int totalSize = dictionaryMapper.totalSize(dictionary);
        mapObj.put("totalSize", totalSize);
        //判断下一页是否还有数据
        boolean isNext = false;
        if (totalSize > pageEndNo) {
            isNext = true;
        }
        mapObj.put("isNext", isNext);
        List<Dictionary> dictionaries = dictionaryMapper.getDictionaries(dictionary, pageStartNo, pageSize);
        List<Map<String, Object>> listMap = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(dictionaries)) {
            for (Dictionary d : dictionaries) {
                Map<String, Object> mapOt = new HashMap<>();
                if (null != user && null != user.getId()) {
                    //管理员角色可查看数据
                    if (MyConstant.UserRole.UserRoleAdmin.toString().equals(dictionaryMapper.getDictionaryById(user.getRole().getId()).getCode())) {
                        mapOt = MyUtils.getBaseResultMap(d,userMapper);
                    }
                }
                mapOt.put("id", d.getId());
                //编号
                mapOt.put("code", d.getCode());
                //名称
                mapOt.put("name", d.getName());
                //字典数据
                if (d.getType() != null) {
                    //字典数据类型ID
                    mapOt.put("typeId", d.getType().getId());
                    //字典数据类型名称
                    mapOt.put("typeName", dictionaryMapper.getDictionaryById(d.getType().getId()).getName());
                    //字典数据类型编号
                    mapOt.put("typeCode", dictionaryMapper.getDictionaryById(d.getType().getId()).getCode());
                } else {
                    mapOt.put("typeId", "");
                    mapOt.put("typeName", "");
                    mapOt.put("typeCode", "");
                }
                listMap.add(mapOt);
            }
        }
        mapObj.put("list", listMap);
        return MyUtils.setDateMap(myError, mapObj);
    }

    //增加字典
    public Map<String, Object> createDictionary(User user, Dictionary dictionary) {
        Map<String, Object> mapObj = new HashMap<>();
        MyError myError = new MyError();
        //设置创建人和时间，修改人和时间
        Date date = new Date();
        dictionary.setCreate_user(user);
        dictionary.setWrite_user(user);
        dictionary.setCreate_date(date);
        dictionary.setWrite_date(date);
        int count;
        try {
            count = dictionaryMapper.createDictionary(dictionary);
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
    //更新字典

    public Map<String, Object> updateDictionary(User user, Dictionary dictionary) {
        Map<String, Object> mapObj = new HashMap<>();
        MyError myError = new MyError();
        //设置修改人和时间
        Date date = new Date();
        dictionary.setWrite_user(user);
        dictionary.setWrite_date(date);
        int count;
        try {
            count = dictionaryMapper.updateDictionary(dictionary);
            if (count <= 0) {
                return MyUtils.setMyErrorMap(MyConstant.returnGeneralErrorCode, MyErrorMsg.updateError);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return MyUtils.setMyErrorMap(MyConstant.returnGeneralErrorCode, MyErrorMsg.dbError);
        }
        return MyUtils.setDateMap(myError, mapObj);
    }

    //字典排序
    @Override
    public Map<String, Object> sortDictionary(User user, List<Dictionary> dictionaries) {
        Map<String, Object> mapObj = new HashMap<>();
        MyError myError = new MyError();
        //设置修改人和修改时间
        Date date = new Date();
        for (Dictionary dictionary : dictionaries) {
            dictionary.setWrite_date(date);
            dictionary.setWrite_user(user);
            int count;
            try {
                count = dictionaryMapper.updateDictionary(dictionary);
                if (count <= 0) {
                    return MyUtils.setMyErrorMap(MyConstant.returnGeneralErrorCode, MyErrorMsg.updateError);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return MyUtils.setMyErrorMap(MyConstant.returnGeneralErrorCode, MyErrorMsg.dbError);
            }
        }
        return MyUtils.setDateMap(myError, mapObj);
    }
}
