package com.wu.knowledge.fileextend.service;

import com.wu.knowledge.fileextend.model.File1;
import com.wu.knowledge.user.model.User;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 文件服务层接口
 * Created by WSD on 2019/3/20.
 */
public interface IFileExtendService {

    /**
     * 创建文件
     *
     * @param user 用户对象
     * @param file 文件对象
     */
    Map<String, Object> createFile(User user, File1 file);
    /**
     * 文件上传
     */
    Map<String, Object> uploadFile(User user, MultipartFile file, String catalog, HttpServletRequest request);

    /**
     * 条件查询文件
     *
     * @param file     文件对象
     * @param pageNo   页码(从0开始)
     * @param pageSize 查询数
     */
    Map<String, Object> getFiles(User user, File1 file, Integer pageNo, Integer pageSize);
}
