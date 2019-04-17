package com.wu.knowledge.basedata.dictionary.dao;

import com.wu.knowledge.basedata.dictionary.model.Dictionary;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 字典数据DAO层接口
 * Created by TZ on 2018/09/14.
 */
@Mapper
public interface DictionaryMapper {
    /**
     * 通过ID查询单个字典数据
     */
    Dictionary getDictionaryById(Integer id);

    /**
     * 通过编号查询单个字典数据
     */
    Dictionary getDictionaryByCode(String code);

    /**
     * 条件查询字典数据数量
     */
    int totalSize(Dictionary dictionary);

    /**
     * 条件查询字典数据
     *
     * @param dictionary 字典数据对象
     * @param pageStart  查询的起始位置(下标从0开始)
     * @param pageSize   查询的数量
     */
    List<Dictionary> getDictionaries(@Param("Dictionary") Dictionary dictionary, @Param("pageStart") Integer pageStart,
                                     @Param("pageSize") Integer pageSize);

    /**
     * 增加字典
     */
    int createDictionary(Dictionary dictionary);

    /**
     * 更新字典
     */
    int updateDictionary(Dictionary dictionary);
}
