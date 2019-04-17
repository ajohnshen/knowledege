package com.wu.knowledge.common.utils;


import com.aliyun.oss.OSSClient;
import com.wu.knowledge.basedata.dictionary.dao.DictionaryMapper;
import com.wu.knowledge.basedata.dictionary.model.Dictionary;

import com.wu.knowledge.common.cache.MyCache;
import com.wu.knowledge.common.config.MyConfig;
import com.wu.knowledge.common.constant.MyConstant;
import com.wu.knowledge.common.constant.MyErrorMsg;
import com.wu.knowledge.common.model.BaseModel;
import com.wu.knowledge.common.model.MyError;
import com.wu.knowledge.file.dao.FileMapper;
import com.wu.knowledge.file.model.File1;
import com.wu.knowledge.user.dao.UserMapper;
import com.wu.knowledge.user.model.User;
import net.sf.json.JSONObject;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 基础工具类
 * Created by TZ on 2018/9/11.
 */
public class MyUtils {

//    private static OSSClient ossClient = new OSSClient(MyConfig.getEndpoint(), MyConfig.getAccessKeyId(), MyConfig.getAccessKeySecret());

    /**
     * 检测字符串是否为空，
     *
     * @param str 待检测字符串
     * @return 返回true表示不为空，false表示为空
     */
    public static boolean StringIsNotNull(String str) {
        boolean flag = false;
        if (null != str && !"".equals(str.trim())) {
            flag = true;
        }
        return flag;
    }

    /**
     * 获取查询条数（默认10）
     */
    public static int getPageSize(String pageSize) throws Exception {
        int pSize = MyConfig.getDefaultPageSize();
        if (MyUtils.StringIsNotNull(pageSize)) {
            try {
                pSize = Integer.parseInt(pageSize);
            } catch (Exception e) {
                throw new Exception("pageSizeError");
            }
        }
        return pSize;
    }

    /**
     * 获取页码（第一页从0开始，默认为0）
     */
    public static int getPageNo(String PageNo) throws Exception {
        int pNo = MyConfig.getDefaultPageNo();
        if (MyUtils.StringIsNotNull(PageNo)) {
            try {
                pNo = Integer.parseInt(PageNo);
            } catch (Exception e) {
                throw new Exception("PageNoError");
            }
        }
        return pNo;
    }

    /**
     * 设置错误信息(返回Map)
     * （一般用于‘服务层’程序异常捕捉后的数据返回）
     *
     * @param err_code 错误编号
     * @param msg      错误信息
     */
    public static Map<String, Object> setMyErrorMap(Integer err_code, String msg) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> mapObj = new HashMap<>();
        MyError myError = new MyError();
        myError.setErr_code(err_code);
        myError.setErr_msg(msg);
        map.put("obj", mapObj);
        map.put("error", myError);
        return map;
    }

    /**
     * 封装数据返回格式(返回Map)
     * （一般用于‘服务层’方法最后一行的数据返回）
     *
     * @param myError 错误信息类
     * @param mapObj  已封装数据对象
     */
    public static Map<String, Object> setDateMap(MyError myError, Map<String, Object> mapObj) {
        Map<String, Object> map = new HashMap<>();
        map.put("obj", mapObj);
        map.put("error", myError);
        return map;
    }

    /**
     * 设置错误信息(返回Json)
     * （一般用于‘控制层’异常捕捉后的数据返回）
     *
     * @param err_code 错误编号
     * @param msg      错误信息
     */
    public static String setMyErrorJson(Integer err_code, String msg) {
        Map<String, Object> map = setMyErrorMap(err_code, msg);
        return MyUtils.returnJson(map, (MyError) map.get("error"));
    }

    /**
     * 封装BaseModel类字段
     *
     * @param baseModel 基础类
     */
    public static Map<String, Object> getBaseResultMap(BaseModel baseModel, UserMapper userMapper) {
        Map<String, Object> map = new HashMap<>();
        if (null != baseModel) {
            map.put("id", baseModel.getId());
            //TODO 待缓存完善再做封装
//            String createUserName = cache.getUserCache(baseModel.getCreate_user().getId()).getUser_name();
//            map.put("createUserName", createUserName);
//            String writeUserName = cache.getUserCache(baseModel.getWrite_user().getId()).getUser_name();
//            map.put("writeUserName", writeUserName);
            map.put("create_date", MyUtils.dateToStr(baseModel.getCreate_date()));
            map.put("write_date", MyUtils.dateToStr(baseModel.getWrite_date()));
            map.put("createUserName", userMapper.getUserById(baseModel.getCreate_user().getId()).getUser_name());
            map.put("writeUserName", userMapper.getUserById(baseModel.getWrite_user().getId()).getUser_name());

            Integer sequence = baseModel.getSequence();
            if (sequence != null) {
                map.put("sequence", sequence);
            } else {
                map.put("sequence", "");
            }
            Boolean active = baseModel.getActive();
            if (active != null) {
                map.put("active", active);
            } else {
                map.put("active", "");
            }
        }
        return map;
    }

    /**
     * 获取MD5加密后的数据
     *
     * @param pwd 待加密的密码
     */
    public static String getMD5(String pwd) {
        // 用于加密的字符
        char md5String[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F'};
        try {
            // 使用平台的默认字符集将此 String 编码为 byte序列，并将结果存储到一个新的 byte数组中
            byte[] btInput = pwd.getBytes();
            // 信息摘要是安全的单向哈希函数，它接收任意大小的数据，并输出固定长度的哈希值。
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // MessageDigest对象通过使用 update方法处理数据， 使用指定的byte数组更新摘要
            mdInst.update(btInput);
            // 摘要更新之后，通过调用digest（）执行哈希计算，获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) { // i = 0
                byte byte0 = md[i]; // 95
                str[k++] = md5String[byte0 >>> 4 & 0xf]; // 5
                str[k++] = md5String[byte0 & 0xf]; // F
            }
            // 返回经过加密后的字符串
            return new String(str).toLowerCase();
        } catch (Exception e) {
            return MyErrorMsg.programError;
        }
    }

    /**
     * 时间转换为字符串(有年月时分)
     *
     * @param date 待转换时间对象
     */
    public static String dateToStr(Date date) {
        String strDate = "";
        if (null != date) {
            SimpleDateFormat sdf = new SimpleDateFormat(MyConstant.dateFormat);
            strDate = sdf.format(date);
        }
        return strDate;
    }

    /**
     * 时间转换为字符串(有年月无时分)
     *
     * @param date 待转换时间对象
     */
    public static String dateToStrNotHour(Date date) {
        String strDate = "";
        if (null != date) {
            SimpleDateFormat sdf = new SimpleDateFormat(
                    MyConstant.dateNotHourFormat);
            strDate = sdf.format(date);
        }
        return strDate;
    }

    /**
     * 时间转换为字符串(无年月有时分)
     *
     * @param date 待转换时间对象
     */
    public static String dateHour(Date date) {
        String strDate = "";
        if (null != date) {
            SimpleDateFormat sdf = new SimpleDateFormat(
                    MyConstant.dateHour);
            strDate = sdf.format(date);
        }
        return strDate;
    }

    /**
     * 时间转换为字符串(自定义)
     *
     * @param date   待转换时间对象
     * @param adfStr 时间格式
     */
    public static String dateToStr(Date date, String adfStr) {
        String strDate = "";
        if (null != date) {
            SimpleDateFormat sdf = new SimpleDateFormat(adfStr);
            strDate = sdf.format(date);
        }
        return strDate;
    }

    /**
     * 字符串转换为时间(有年月时分)
     *
     * @param str 待转换字符串
     */
    public static Date strToDate(String str) throws ParseException {
        DateFormat df = new SimpleDateFormat(MyConstant.dateFormat);
        if (MyUtils.StringIsNotNull(str)) {
            return df.parse(str);
        }
        return null;
    }

    /**
     * 字符串转换为时间(无年月有时分)
     *
     * @param str 待转换字符串
     */
    public static Date strToHour(String str) throws ParseException {
        DateFormat df = new SimpleDateFormat(MyConstant.dateHour);
        if (MyUtils.StringIsNotNull(str)) {
            return df.parse(str);
        }
        return null;
    }

    /**
     * 字符串转换为时间(有年月无时分)
     *
     * @param str 待转换字符串
     */
    public static Date strToDateNotHour(String str) throws ParseException {
        DateFormat df = new SimpleDateFormat(MyConstant.dateNotHourFormat);
        if (MyUtils.StringIsNotNull(str)) {
            return df.parse(str);
        }
        return null;
    }

    /**
     * 字符串转换为时间(自定义)
     *
     * @param str    待转换字符串
     * @param adfStr 时间格式
     */
    public static Date strToDate(String str, String adfStr) throws ParseException {
        DateFormat df = new SimpleDateFormat(adfStr);
        return df.parse(str);
    }

    /**
     * 统一返回前端的JSON字符串
     *
     * @param map     封装数据
     * @param myError 错误信息对象
     * @return
     */
    public static String returnJson(Map<String, Object> map, MyError myError) {
        JSONObject json = new JSONObject();
        JSONObject error = new JSONObject();
        if (null != map) {
            json = JSONObject.fromObject(map);
        } else {
            map = new HashMap<>();
            json.put("obj", map);
        }
        if (null == myError) {
            myError = new MyError();
        }
        error.put("err_msg", myError.getErr_msg());
        error.put("err_code", myError.getErr_code());
        json.put("error", error);
        System.out.println(json.toString());
        return json.toString();
    }

    /**
     * 必填字段校验
     *
     * @param fieldList 待检测的字段集合(String为字段的名称，Object为字段的实体)
     */
    public static void fieldCheck(List<Map<String, Object>> fieldList) throws Exception {
        for (Map<String, Object> fieldMap : fieldList) {
            for (String s : fieldMap.keySet()) {
                Object o = fieldMap.get(s);
                if (null == o) {
                    throw new Exception(s + "不能为空！");
                }
                if (MyConstant.loginKey.equals(s)) {
                    User u = MyCache.getLoginUsersMap().get(o.toString());
                    if (null == u) {
                        throw new Exception(MyErrorMsg.loginStatusError);
                    }
                }
                //判断该对象是否是String的状态码
                boolean flagCode = false;
                String ss = "";
                try {
                    ss = (String) o;
                    flagCode = true;
                } catch (Exception e) {
                }
                if (flagCode) {
                    if (!MyUtils.StringIsNotNull(ss)) {
                        throw new Exception(s + "不能为空！");
                    }
                }
            }
        }
    }

    /**
     * 生成或更新loginKey(登陆时间+50个随机字符串)
     */
    public static String createLoginKey(User user) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String loginKey = sdf.format(new Date()) + RandomStringUtils.randomAlphanumeric(50);
        ConcurrentHashMap<String, User> loginUsersMap = MyCache.getLoginUsersMap();
        if (loginUsersMap.containsValue(user)) {
            Collection<User> values = loginUsersMap.values();
            values.remove(user);
        }
        loginUsersMap.put(loginKey, user);
        MyCache.setLoginUsersMap(loginUsersMap);
        return loginKey;
    }

    /**
     * 删除loginKey
     */
    public static void deleteLoginKey(User user, String loginKey) {
        ConcurrentHashMap<String, User> loginUsersMap = MyCache.getLoginUsersMap();
        if (loginUsersMap.containsValue(user)) {
            Collection<User> values = loginUsersMap.values();
            values.remove(user);
        }
        loginUsersMap.remove(loginKey);
    }

    /**
     * 字典数组转换
     *
     * @param str              代转换字符数组
     * @param dictionaryMapper 字典DAO
     */
    public static String strToDictionary(String str, DictionaryMapper dictionaryMapper) throws Exception {
        StringBuilder dictionary = new StringBuilder(" ");
        try {
            if (MyUtils.StringIsNotNull(str)) {
                for (Integer dictionaryId : MyUtils.strToSet(str)) {
                    Dictionary d = dictionaryMapper.getDictionaryById(dictionaryId);
                    if (null != d) {
                        dictionary.append(d.getName()).append(" ");
                    }
                }
            }
        } catch (Exception e1) {
            e1.printStackTrace();
            throw new Exception(e1.getMessage());
        }
        return dictionary.toString();
    }


    /**
     * 创建指定位数随机字符串
     */
    public static String createRandomNum(int num) {
        Random random = new Random();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < num; i++) {
            result.append(random.nextInt(10));
        }
        return result.toString();
    }

    /**
     * 字符串数组转换
     */
    public static Integer[] arrayChange(String array) throws Exception {
        Integer[] Ids = null;
        try {
            if (MyUtils.StringIsNotNull(array)) {
                String[] idStr = array.split(",");
                Ids = new Integer[idStr.length];
                for (int i = 0; i < idStr.length; i++) {
                    Ids[i] = Integer.parseInt(idStr[i]);
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            throw new Exception(MyErrorMsg.arrayChangeError);
        }
        return Ids;
    }

    /**
     * 字典数据编码校验（通过子类编码判断）
     *
     * @param code             字典子类编码
     * @param parentCode       父类校验编码
     * @param dictionaryMapper 字典DAO
     * @return 子类编码对应的字典数据实体
     */
    public static Dictionary dictionaryCheck(String code, String parentCode, DictionaryMapper dictionaryMapper) throws Exception {
        Dictionary dictionary = dictionaryMapper.getDictionaryByCode(code);
        if (null == dictionary || null == dictionary.getType() || null == dictionaryMapper.getDictionaryById(dictionary.getType().getId())
                || !parentCode.equals(dictionaryMapper.getDictionaryById(dictionary.getType().getId()).getCode())) {
            throw new Exception(MyErrorMsg.dictionaryError);
        }
        return dictionary;
    }

    /**
     * 字典数据ID校验（通过子类编码判断）
     *
     * @param dictionaryId     字典子类编码
     * @param parentCode       父类校验编码
     * @param dictionaryMapper 字典DAO
     * @return 子类编码对应的字典数据实体
     */
    public static Dictionary dictionaryCheck(Integer dictionaryId, String parentCode, DictionaryMapper dictionaryMapper) throws Exception {
        Dictionary dictionary = dictionaryMapper.getDictionaryById(dictionaryId);
        if (null == dictionary || null == dictionary.getType() || null == dictionaryMapper.getDictionaryById(dictionary.getType().getId())
                || !parentCode.equals(dictionaryMapper.getDictionaryById(dictionary.getType().getId()).getCode())) {
            throw new Exception(MyErrorMsg.dictionaryError);
        }
        return dictionary;
    }

    /**
     * 常量枚举名拆分
     *
     * @param className 待拆分常量枚举名
     */
    public static String enumStrSplit(String className) {
        return className.split("\\$")[1];
    }

    /**
     * 封装ID数组格式
     */
    public static String packStrIDs(String str) {
        if (MyUtils.StringIsNotNull(str)) {
            return "," + str + ",";
        }
        return str;
    }

    /**
     * 拆封ID数组格式
     */
    public static String splitStrIDs(String str) {
        if (MyUtils.StringIsNotNull(str) && str.length() > 1) {
            return str.substring(1, str.length() - 1);
        }
        return str;
    }


    /**
     * 对链接发起POST请求，并返回结果
     *
     * @param url    请求链接
     * @param params 请求参数
     */
    public static String postResult(String url, List<BasicNameValuePair> params)
            throws Exception {
        String result = "";
        // 创建默认的httpClient实例.
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 创建httppost
        HttpPost httppost = new HttpPost(url);
        // 创建参数队列
        List<NameValuePair> formparams = new ArrayList<>();
        if (null != params) {
            formparams.addAll(params);
        }
        UrlEncodedFormEntity uefEntity;
        CloseableHttpResponse response = null;
        try {
            if (null != params) {
                uefEntity = new UrlEncodedFormEntity(formparams, "utf-8");
                httppost.setEntity(uefEntity);
            }
            response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity, "utf-8");
            }
        } catch (Exception e) {
            throw new Exception(MyErrorMsg.postError);
        } finally {
            // 关闭连接,释放资源
            try {
                if (response != null)
                    response.close();
                if (httpclient != null)
                    httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw new Exception(MyErrorMsg.postError);
            }
        }
        return result;
    }

    /**
     * 字符串转化为集合
     */
    private static Set<Integer> strToSet(String str) throws Exception {
        Set<Integer> set = new HashSet<>();
        try {
            String[] split = str.split(",");
            for (String s : split) {
                if (MyUtils.StringIsNotNull(s)) {
                    set.add(Integer.parseInt(s));
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            throw new Exception(MyErrorMsg.strToSetError);
        }
        return set;
    }

    /**
     * 封装文件信息(除PPT以外)
     *
     * @param fieldName        对应字典名称(做返回字段的拼接使用)
     * @param file             文件对象
     * @param mapOt            需要封装数组的Map
     * @param fileMapper       文件DAO
     * @param dictionaryMapper 字典数据DAO
     * @param isVideo          是否是视频文件
     */
    /*public static void packFileMap(String fieldName, File1 file, Map<String, Object> mapOt, FileMapper fileMapper, DictionaryMapper dictionaryMapper, Boolean isVideo) {
        OSSClient ossClient = new OSSClient(MyConfig.getEndpoint(), MyConfig.getAccessKeyId(), MyConfig.getAccessKeySecret());
        if (MyUtils.StringIsNotNull(fieldName)) {
            if (null != file && null != file.getId()) {
                File1 f = fileMapper.getFileById(file.getId());
                //文件ID
                mapOt.put(fieldName + "Id", file.getId());
                //文件名
                mapOt.put(fieldName + "Name", f.getName());
                //文件路径
                *//*if (isVideo) {
                    try {
                        AliVideo aliVideo = getVODVideoUrl(f.getPath());
                        mapOt.put(fieldName + "Path", aliVideo.getPlayURL());
                        mapOt.put("requestId", aliVideo.getRequestId());
                        mapOt.put("uploadAuth", aliVideo.getUploadAuth());
                        mapOt.put("uploadAddress", aliVideo.getUploadAddress());
                    } catch (Exception e) {
                        e.printStackTrace();
                        mapOt.put(fieldName + "Path", "");
                        mapOt.put("requestId", "");
                        mapOt.put("uploadAuth", "");
                        mapOt.put("uploadAddress", "");
                    }
                } else {
                    mapOt.put(fieldName + "Path", getOSSFileUrl(ossClient, f.getPath()));
                }*//*
                mapOt.put(fieldName + "Path", getOSSFileUrl(ossClient, f.getFile_path()));
                // 文件类型名称(必填字段)
                mapOt.put(fieldName + "TypeName", dictionaryMapper.getDictionaryById(f.getFile_type().getId()).getName());
                // 文件类型编号(必填字段)
                mapOt.put(fieldName + "TypeCode", dictionaryMapper.getDictionaryById(f.getFile_type().getId()).getCode());
                // 是否是视频
                mapOt.put(fieldName + "IsVideo", f.getVideo());
            } else {
                mapOt.put(fieldName + "Id", "");
                mapOt.put(fieldName + "Name", "");
                mapOt.put(fieldName + "Path", "");
                mapOt.put(fieldName + "TypeName", "");
                mapOt.put(fieldName + "TypeCode", "");
                mapOt.put(fieldName + "IsVideo", "");
            }
        }
        ossClient.shutdown();
    }*/

    /**
     * 获取阿里云OSS存储文件完整路径
     *
     * @param filePath 文件相对路径
     */
    private static String getOSSFileUrl(OSSClient ossClient, String filePath) {
        // 设置URL过期时间为5分钟
        Date expiration = new Date(new Date().getTime() + 300 * 1000);
        URL url = ossClient.generatePresignedUrl(MyConfig.getBucket(), filePath, expiration);
        StringBuilder URL = new StringBuilder();
        URL.append("http://");
        URL.append(url.getHost());
        URL.append(url.getPath());
        URL.append("?");
        URL.append(url.getQuery());
        // 关闭OSSClient。
        ossClient.shutdown();
        return URL.toString();
    }
    /**
     * 获取文件后缀
     *
     * @param fileName 文件名
     */
    public static String getFileSuffix(String fileName) {
        int begin = fileName.lastIndexOf(".");
        int end = fileName.length();
        return fileName.substring(begin, end);
    }
    /**
     * 网站创建用户名
     */
    public static String createUsername() {
        return "知识" + UUID.randomUUID().toString();
    }
}
