//package com.wu.knowledge.fileextend.service.impl;
//
//
//import com.wu.knowledge.basedata.dictionary.dao.DictionaryMapper;
//import com.wu.knowledge.common.constant.MyConstant;
//import com.wu.knowledge.common.constant.MyErrorMsg;
//import com.wu.knowledge.common.model.MyError;
//import com.wu.knowledge.common.utils.MyUtils;
//import com.wu.knowledge.fileextend.dao.FileMapper;
//import com.wu.knowledge.fileextend.model.File1;
//import com.wu.knowledge.fileextend.service.IFileService;
//import com.wu.knowledge.user.dao.UserMapper;
//import com.wu.knowledge.user.model.User;
//import org.apache.commons.collections.CollectionUtils;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//import java.io.File;
//import java.util.*;
//
///**
// * 文件服务层实现类
// * Created by WSD on 2019/3/20.
// */
//@Service("file1Service")
//public class FileServiceImpl implements IFileService {
//
//    @Resource
//    private FileMapper fileMapper;
//    //暂时未做缓存，缓存框架搭好后只有对应的服务层才能直接调用DAO
//    @Resource
//    private UserMapper userMapper;
//    @Resource
//    private DictionaryMapper dictionaryMapper;
//
//    //创建文件
//    @Override
//    public Map<String, Object> createFile(User user, File1 file) {
//        Map<String, Object> mapObj = new HashMap<>();
//        MyError myError = new MyError();
//        //设置创建人和时间，修改人和时间
//        Date date = new Date();
//        file.setCreate_user(user);
//        file.setWrite_user(user);
//        file.setCreate_date(date);
//        file.setWrite_date(date);
//        int count;
//        try {
//            count = fileMapper.createFile(file);
//            if (count <= 0) {
//                return MyUtils.setMyErrorMap(MyConstant.returnGeneralErrorCode, MyErrorMsg.createError);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return MyUtils.setMyErrorMap(MyConstant.returnGeneralErrorCode, MyErrorMsg.dbError);
//        }
//        mapObj.put("fileId", file.getId());
//        //发生异常和正常流程走完的信息返回调用不一样的工具类数据返回接口
//        return MyUtils.setDateMap(myError, mapObj);
//    }
//
//    //文件上传
//    @Override
//    public Map<String, Object> uploadFile(User user, MultipartFile file, String catalog,HttpServletRequest request) {
//        Map<String, Object> mapObj = new HashMap<>();
//        MyError myError = new MyError();
//    //    OSSClient ossClient = new OSSClient(MyConfig.getEndpoint(), MyConfig.getAccessKeyId(), MyConfig.getAccessKeySecret());
//        if (!file.getOriginalFilename().isEmpty()) {
//            try {
//
//              //  String fileRealPath = request.getSession().getServletContext().getRealPath("D:\\picture");
//                //用于上传的路径
//                StringBuilder filePath = new StringBuilder();
//                //用于存进数据库的路径
//                StringBuilder filePath1 = new StringBuilder();
//                filePath.append("D:/InstallSoftware/AppServ/www/knowledge/");
//                filePath.append(MyConstant.Catalog.getValueByKey(catalog));
//                //filePath.append(MyUtils.dateToStr(new Date(), "yyyyMMdd"));
//                //filePath.append("/");
//                //加密名字
//                String fileNameMD5 = MyUtils.getMD5(file.getOriginalFilename());
//                String fileName = file.getOriginalFilename();
//                //StringBuilder转换为String
//                String s1=filePath.toString().toLowerCase();
//                //获取后缀名
//                String fileSuffix = MyUtils.getFileSuffix(file.getOriginalFilename());
//                File fileFolder = new File(s1);
//                if(!fileFolder.exists()){
//                    fileFolder.mkdirs();
//                }
//                //上传
//                //File file1 = new File(fileFolder,fileNameMD5+fileSuffix);
//                File file1 = new File(fileFolder,fileName);
//                file.transferTo(file1);
//
//                //文件目录+当月文件名+文件名+文件后缀
//                // ossClient.putObject(MyConfig.getBucket(), filePath.toString(), file.getInputStream());
//                //创建文件对象
//                File1 f = new File1();
//                f.setActive(true);
//
//                f.setFile_nameMD5(fileNameMD5+fileSuffix);
//                f.setFile_name(file.getOriginalFilename());
//                f.setFile_size((int) file.getSize());
//                //文件路径（重要
//                filePath1.append("file/");
//                //filePath1.append(MyUtils.dateToStr(new Date(), "yyyyMMdd"));
//                //filePath1.append("/");
//                filePath1.append(fileNameMD5);
//                filePath1.append(fileSuffix);
//                f.setFile_path(filePath1.toString());
//                f.setFile_suffix(fileSuffix);
//                //TODO 文件类型解决方案
//                f.setFile_type(dictionaryMapper.getDictionaryByCode(MyConstant.FileType.FileTypeOther.toString()));
//                Map<String, Object> map = createFile(user, f);
//                if (MyConstant.returnSuccessCode != ((MyError) map.get("error")).getErr_code()) {
//                    return MyUtils.setMyErrorMap(((MyError) map.get("error")).getErr_code(), ((MyError) map.get("error")).getErr_msg());
//                }
//                mapObj = (Map<String, Object>) map.get("obj");
//            } catch (Exception e) {
//                e.printStackTrace();
//                return MyUtils.setMyErrorMap(MyConstant.returnGeneralErrorCode, MyErrorMsg.uploadFileError);
//            }
//        }
//        // 关闭OSSClient
//      //  ossClient.shutdown();
//        return MyUtils.setDateMap(myError, mapObj);
//    }
//
//    //条件查询文件
//    @Override
//    public Map<String, Object> getFiles(User user, File1 file, Integer pageNo, Integer pageSize)   {
//        Map<String, Object> mapObj = new HashMap<>();
//        MyError myError = new MyError();
//        int pageStartNo = pageSize * pageNo;
//        int pageEndNo = pageSize * (pageNo + 1);
//        //条件查询出的总数
//        int totalSize = fileMapper.totalSize(file);
//        mapObj.put("totalSize", totalSize);
//        //判断下一页是否还有数据
//        boolean isNext = false;
//        if (totalSize > pageEndNo) {
//            isNext = true;
//        }
//        mapObj.put("isNext", isNext);
//        List<File1> files = fileMapper.getFiles(file, pageStartNo, pageSize);
//        List<Map<String, Object>> listMap = new ArrayList<>();
//        if (CollectionUtils.isNotEmpty(files)) {
//            for (File1 f : files) {
//                Map<String, Object> mapOt = new HashMap<>();
//                if (null != user && null != user.getId()) {
//                    //管理员角色可查看数据
//                    if (MyConstant.UserRole.UserRoleAdmin.toString().equals(dictionaryMapper.getDictionaryById(user.getRole().getId()).getCode())) {
//                        mapOt = MyUtils.getBaseResultMap(f, userMapper);
//                    }
//                }
//                //判断是否是视频文件并封装文件信息
////                if (f.getVideo()) {
////                    MyUtils.packFileMap("file", f, mapOt, fileMapper, dictionaryMapper, true);
////                } else {
////                    MyUtils.packFileMap("file", f, mapOt, fileMapper, dictionaryMapper, false);
////                }
//                //mapOt.put("fileType",f.getFile_type().getName());
//                mapOt.put("id",f.getId());
//                mapOt.put("file_name",f.getFile_name());
//                mapOt.put("path",f.getFile_path());
//                if (f.getFile_type() != null) {
//                    //类型ID
//                    mapOt.put("fileType_id",f.getFile_type().getId());
//                    if(null!=dictionaryMapper.getDictionaryById(f.getFile_type().getId())){
//                        //类型名称
//                        mapOt.put("fileType",dictionaryMapper.getDictionaryById(f.getFile_type().getId()).getName() );
//                    }
//                }else{
//                    mapOt.put("fileType_id", "");
//                    mapOt.put("fileType","" );
//                }
//
//                listMap.add(mapOt);
//            }
//        }
//        mapObj.put("list", listMap);
//        return MyUtils.setDateMap(myError, mapObj);
//    }
//}
