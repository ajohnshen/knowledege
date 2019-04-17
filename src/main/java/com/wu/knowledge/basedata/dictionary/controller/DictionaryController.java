package com.wu.knowledge.basedata.dictionary.controller;

import com.wu.knowledge.basedata.dictionary.dao.DictionaryMapper;
import com.wu.knowledge.basedata.dictionary.model.Dictionary;
import com.wu.knowledge.basedata.dictionary.service.IDictionaryService;
import com.wu.knowledge.common.cache.MyCache;
import com.wu.knowledge.common.constant.MyConstant;
import com.wu.knowledge.common.constant.MyErrorMsg;
import com.wu.knowledge.common.model.MyError;
import com.wu.knowledge.common.utils.MyUtils;
import com.wu.knowledge.user.model.User;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
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
 * 字典数据控制层
 * Created by TZ on 2018/09/14.
 */
@Controller
@RequestMapping("/basedata/dictionary/DictionaryController")
public class DictionaryController {

    @Resource
    private IDictionaryService dictionaryService;
    @Resource
    private DictionaryMapper dictionaryMapper;

    //增加数据字典
    @ResponseBody
    @RequestMapping("/createDictionary")
    public Object createDictionaries(
            @RequestParam(value = "loginKey", required = false) String loginKey,
            @RequestParam(value = "sequence", required = false) Integer sequence,
            @RequestParam(value = "active", required = false) Boolean active,
            @RequestParam(value = "code", required = false) String code,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "type", required = false) Integer type
    ) {
        //必填字段校验
        List<Map<String, Object>> fieldList = new LinkedList<>();
        Map<String, Object> fieldMap = new HashMap<>();
        fieldMap.put("loginKey", loginKey);
        fieldMap.put("是否可用", active);
        fieldMap.put("字典名称", name);
        fieldMap.put("字典编号", code);

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
        Dictionary dictionary = new Dictionary();
        dictionary.setSequence(sequence);
        dictionary.setActive(active);
        dictionary.setCode(code);
        dictionary.setName(name);
        Dictionary dictionary1 = new Dictionary();
        dictionary1.setId(type);
        dictionary.setType(dictionary1);
        Map<String, Object> map = dictionaryService.createDictionary(user, dictionary);
        return MyUtils.returnJson(map, (MyError) map.get("error"));
    }

    //更新字典
    @ResponseBody
    @RequestMapping("/updateDictionary")
    public Object updateDictionaries(
            @RequestParam(value = "loginKey", required = false) String loginKey,
            @RequestParam(value = "sequence", required = false) Integer sequence,
            @RequestParam(value = "id", required = false) Integer id,
            @RequestParam(value = "active", required = false) Boolean active,
            @RequestParam(value = "code", required = false) String code,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "type", required = false) Integer type
    ) {
        //必填字段校验
        List<Map<String, Object>> fieldList = new LinkedList<>();
        Map<String, Object> fieldMap = new HashMap<>();
        fieldMap.put("loginKey", loginKey);
        fieldMap.put("字典ID", id);
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
        Dictionary dictionary = new Dictionary();
        dictionary.setId(id);
        dictionary.setActive(active);
        dictionary.setCode(code);
        dictionary.setName(name);
        Dictionary dictionary1 = new Dictionary();
        dictionary1.setId(type);
        dictionary.setType(dictionary1);
        dictionary.setSequence(sequence);
        Map<String, Object> map = dictionaryService.updateDictionary(user, dictionary);
        return MyUtils.returnJson(map, (MyError) map.get("error"));
    }

    //获取数据字典
    @ResponseBody
    @RequestMapping("/getDictionaries")
    public Object getDictionaries(
            @RequestParam(value = "loginKey", required = false) String loginKey,
            @RequestParam(value = "id", required = false) Integer id,
            @RequestParam(value = "active", required = false) Boolean active,
            @RequestParam(value = "code", required = false) String code,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "typeId", required = false) Integer typeId,
            @RequestParam(value = "typeCode", required = false) String typeCode,
            @RequestParam(value = "pageNo", required = false) String pageNo,//页码从0开始
            @RequestParam(value = "pageSize", required = false) String pageSize//每页显示数
    ) {
        User user = null;
        if (null != loginKey) {
            user = MyCache.getLoginUsersMap().get(loginKey);
            if (null == user) {
                return MyUtils.setMyErrorJson(MyConstant.returnNoLoginErrorCode, MyErrorMsg.loginStatusError);
            }
        }

        Dictionary dictionary = new Dictionary();
        dictionary.setId(id);
        dictionary.setActive(active);
        dictionary.setCode(code);
        dictionary.setName(name);
        Dictionary d = new Dictionary();
        if (null != typeId) {
            d = dictionaryMapper.getDictionaryById(typeId);
        }
        if (MyUtils.StringIsNotNull(typeCode)) {
            d = dictionaryMapper.getDictionaryByCode(typeCode);
            if (null == d) {
                return MyUtils.setMyErrorJson(MyConstant.returnGeneralErrorCode, MyErrorMsg.dictionaryError);
            }
        }
        dictionary.setType(d);
        int pNo;
        int pSize;
        try {
            pNo = MyUtils.getPageNo(pageNo);
            pSize = MyUtils.getPageSize(pageSize);
        } catch (Exception e) {
            e.printStackTrace();
            return MyUtils.setMyErrorJson(MyConstant.returnGeneralErrorCode, MyErrorMsg.pagingError);
        }
        Map<String, Object> map = dictionaryService.getDictionaries(user, dictionary, pNo, pSize);
        return MyUtils.returnJson(map, (MyError) map.get("error"));
    }

    //字典排序
    @ResponseBody
    @RequestMapping("/sortDictionary")
    public Object sortHot(
            @RequestParam(value = "loginKey", required = false) String loginKey,
            @RequestParam(value = "sequenceJson", required = false) String sequenceJson
    ) {
        //必填字段校验
        List<Map<String, Object>> fieldList = new LinkedList<>();
        Map<String, Object> fieldMap = new HashMap<>();
        fieldMap.put("loginKey", loginKey);
        fieldMap.put("排序JSON数据", sequenceJson);
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
        List<Dictionary> dictionaries = new LinkedList<>();
        try {
            JSONArray array = JSONArray.fromObject(sequenceJson);
            if (array.size() > 0) {
                for (Object o : array) {
                    JSONObject json = JSONObject.fromObject(o);
                    Dictionary dictionary = new Dictionary();
                    dictionary.setId(json.getInt("id"));
                    dictionary.setSequence(json.getInt("sequence"));
                    dictionaries.add(dictionary);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return MyUtils.setMyErrorJson(MyConstant.returnGeneralErrorCode, MyErrorMsg.sortError);
        }
        Map<String, Object> map = dictionaryService.sortDictionary(user, dictionaries);
        return MyUtils.returnJson(map, (MyError) map.get("error"));
    }
}
