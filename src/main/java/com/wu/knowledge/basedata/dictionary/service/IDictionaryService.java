package com.wu.knowledge.basedata.dictionary.service;

import com.wu.knowledge.basedata.dictionary.model.Dictionary;
import com.wu.knowledge.user.model.User;

import java.util.List;
import java.util.Map;

/**
 * 字典数据服务层接口
 * Created by TZ on 2018/09/14.
 */
public interface IDictionaryService {
    /**
     * 条件查询字典数据
     *
     * @param dictionary 字典数据对象
     * @param pageNo     页码(从0开始)
     * @param pageSize   查询数
     */
    Map<String, Object> getDictionaries(User user, Dictionary dictionary, Integer pageNo, Integer pageSize);

    /**
     * 增加字典
     */
    Map<String, Object> createDictionary(User user, Dictionary dictionary);

    /**
     * 更新字典
     */
    Map<String, Object> updateDictionary(User user, Dictionary dictionary);

    //
    // 字典排序
    Map<String, Object> sortDictionary(User user, List<Dictionary> dictionaries);
}
