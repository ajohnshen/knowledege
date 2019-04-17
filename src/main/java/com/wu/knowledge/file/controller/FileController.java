package com.wu.knowledge.file.controller;


import com.wu.knowledge.basedata.dictionary.dao.DictionaryMapper;
import com.wu.knowledge.basedata.dictionary.model.Dictionary;
import com.wu.knowledge.common.cache.MyCache;
import com.wu.knowledge.common.constant.MyConstant;
import com.wu.knowledge.common.constant.MyErrorMsg;
import com.wu.knowledge.common.model.MyError;
import com.wu.knowledge.common.utils.MyUtils;
import com.wu.knowledge.file.dao.FileMapper;
import com.wu.knowledge.file.model.File1;
import com.wu.knowledge.file.service.IFileService;
import com.wu.knowledge.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 文件控制层
 * Created by WSD on 2018/11/30.
 */
@Controller
@RequestMapping("/file/FileController")
public class FileController {

    @Resource
    private IFileService fileService;
    @Resource
    private DictionaryMapper dictionaryMapper;
    @Resource
    private FileMapper fileMapper;
    //文件上传
    @ResponseBody
    @RequestMapping("/uploadFile")
    public Object uploadFile(
            HttpServletRequest request,
            @RequestParam(value = "loginKey", required = false) String loginKey,
            @RequestParam(value = "catalog", required = false) String catalog,//文件存储所在目录（字典数据）
            @RequestParam(value = "file", required = false) MultipartFile file
    ) {
        //必填字段校验
        List<Map<String, Object>> fieldList = new LinkedList<>();
        Map<String, Object> fieldMap = new HashMap<>();
        fieldMap.put("loginKey", loginKey);
        fieldMap.put("文件所在目录", catalog);
        fieldMap.put("上传文件", file);
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
        //关联用户
        User u = new User();
        //u.setId(userID);
        //file.setUserID(user);.
        //文件目录校验
        try {
            MyUtils.dictionaryCheck(
                    catalog, MyUtils.enumStrSplit(MyConstant.Catalog.class.getName()), dictionaryMapper
            );
        } catch (Exception e1) {
            e1.printStackTrace();
            return MyUtils.setMyErrorJson(MyConstant.returnGeneralErrorCode, e1.getMessage());
        }
        Map<String, Object> map = fileService.uploadFile(user, file, catalog, request);
        return MyUtils.returnJson(map, (MyError) map.get("error"));
    }

    //文件下载相关代码
    @ResponseBody
    @RequestMapping("/download")
    public Object downloadFile(HttpServletRequest request, HttpServletResponse response,
                               @RequestParam(value = "fileId", required = false) String fileId) throws Exception{
        //必填字段校验
        List<Map<String, Object>> fieldList = new LinkedList<>();
        Map<String, Object> fieldMap = new HashMap<>();
        fieldMap.put("文件ID", fileId);
        fieldList.add(fieldMap);
        try {
            MyUtils.fieldCheck(fieldList);
        } catch (Exception e) {
            e.printStackTrace();
            return MyUtils.setMyErrorJson(MyConstant.returnGeneralErrorCode, e.getMessage());
        }
        //String fileName = "11.png";// 设置文件名，根据业务需要替换成要下载的文件名
        int a = Integer.parseInt(fileId);
        File1 f = fileMapper.getFileById(a);
        String fileName = f.getFile_name();
        String path = f.getFile_path();

        if (fileName != null) {
            //设置文件路径(下载的路径)  D:\InstallSoftware\AppServ\www\knowledge\knowledge\file
            String realPath = "D://InstallSoftware//AppServ//www//knowledge//knowledge//file";
            File file = new File(realPath , fileName);
            if (file.exists()) {
                response.setContentType("application/force-download");// 设置强制下载不打开
                response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);// 设置文件名
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                    System.out.println("success");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return null;
    }

    //获取文件
    @ResponseBody
    @RequestMapping("/getFiles")
    public Object getFiles(
            @RequestParam(value = "loginKey", required = false) String loginKey,
            @RequestParam(value = "id", required = false) Integer id,
            @RequestParam(value = "active", required = false) Boolean active,
            @RequestParam(value = "file_name", required = false) String file_name,
            @RequestParam(value = "file_type", required = false) Integer file_type,
            @RequestParam(value = "video", required = false) Boolean video,
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
        //必填字段校验
        List<Map<String, Object>> fieldList = new LinkedList<>();
        Map<String, Object> fieldMap = new HashMap<>();
        fieldList.add(fieldMap);
        try {
            MyUtils.fieldCheck(fieldList);
        } catch (Exception e) {
            e.printStackTrace();
            return MyUtils.setMyErrorJson(MyConstant.returnGeneralErrorCode, e.getMessage());
        }
        File1 file = new File1();
        file.setId(id);
        file.setActive(active);
        file.setFile_name(file_name);
        Dictionary d = new Dictionary();
        d.setId(file_type);
        file.setFile_type(d);
        file.setVideo(video);
        int pNo;
        int pSize;
        try {
            pNo = MyUtils.getPageNo(pageNo);
            pSize = MyUtils.getPageSize(pageSize);
        } catch (Exception e) {
            e.printStackTrace();
            return MyUtils.setMyErrorJson(MyConstant.returnGeneralErrorCode, MyErrorMsg.pagingError);
        }
        Map<String, Object> map = fileService.getFiles(user, file, pNo, pSize);
        return MyUtils.returnJson(map, (MyError) map.get("error"));
    }

}
