package com.wu.knowledge.file.dao;


import com.wu.knowledge.file.model.File1;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 文件DAO层接口
 * Created by WSD on 2018/11/30.
 */
@Mapper
public interface FileMapper {

    /**
     * 创建文件
     */
    int createFile(File1 file);

    /**
     * 通过ID查询单个文件
     */
    File1 getFileById(Integer id);

    /**
     * 条件查询文件数量
     */
    int totalSize(File1 file);

    /**
     * 条件查询文件
     *
     * @param file      文件对象
     * @param pageStart 查询的起始位置(下标从0开始)
     * @param pageSize  查询的数量
     */
    List<File1> getFiles(@Param("File") File1 file, @Param("pageStart") Integer pageStart,
                         @Param("pageSize") Integer pageSize);

}
