package com.wu.knowledge.incubator.service;

import com.wu.knowledge.incubator.model.Incubator;

import java.util.Map;

/**
 * 〈功能简述〉<br>
 * 孵化器服务层接口
 * @create create by WSD on 2018/12/18
 */
public interface IIncubatorService {
    /**
     * 条件查询工位或办公室
     */
    Map<String, Object> getIncubators(Incubator incubator, Integer pageNo, Integer pageSize);

    /**
     * 添加一条孵化
     */
    Map<String, Object> createIncubator(Incubator incubator);

    /**
     * 更新一条孵化
     */
    Map<String, Object> updateIncubator(Incubator incubator);
}
