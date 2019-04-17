package com.wu.knowledge.incubator.dao;

import com.wu.knowledge.incubator.model.Incubator;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 〈功能简述〉<br>
 * 〈孵化器Dao层接口〉
 *
 * @create create by WSD on 2018/12/18
 */
@Mapper
public interface IncubatorMapper {

    /**
     * 通过ID查询单个工位或办公室
     */
    Incubator getIncubatorById(Integer id);

    /**
     * 条件查询工位或办公室数量
     */
    int totalSize(Incubator incubator);

    /**
     * 添加一条孵化
     */
    int createIncubator(Incubator incubator);

    /**
     * 更新一条孵化
     */
    int updateIncubator(Incubator incubator);

    /**
     *  条件查询工位或办公室
     * @param incubator     孵化对象
     * @param pageStart 查询的起始位置(下标从0开始)
     * @param pageSize  查询的数量
     */
    List<Incubator> getIncubators(@Param("Incubator") Incubator incubator, @Param("pageStart") Integer pageStart,
                                  @Param("pageSize") Integer pageSize);
}
