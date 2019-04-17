package com.wu.knowledge.incubator.service.impl;

import com.wu.knowledge.common.constant.MyConstant;
import com.wu.knowledge.common.constant.MyErrorMsg;
import com.wu.knowledge.common.model.MyError;
import com.wu.knowledge.common.utils.MyUtils;
import com.wu.knowledge.incubator.dao.IncubatorMapper;
import com.wu.knowledge.incubator.model.Incubator;
import com.wu.knowledge.incubator.service.IIncubatorService;
import com.wu.knowledge.user.model.User;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 〈功能简述〉<br>
 * 〈孵化器服务层实现类〉
 *
 * @create create by WSD on 2018/12/18
 */
@Service("incubatorService")
public class IncubatorServiceImpl implements IIncubatorService {

    @Resource
    private IncubatorMapper incubatorMapper;

    //条件查询孵化器
    @Override
    public Map<String, Object> getIncubators(Incubator incubator, Integer pageNo, Integer pageSize) {
        Map<String, Object> mapObj = new HashMap<>();
        MyError myError = new MyError();
        int pageStartNo = pageSize * pageNo;
        int pageEndNo = pageSize * (pageNo + 1);
        //条件查询出的总数
        int totalSize = incubatorMapper.totalSize(incubator);
        mapObj.put("totalSize", totalSize);
        //判断下一页是否还有数据
        boolean isNext = false;
        if (totalSize > pageEndNo) {
            isNext = true;
        }
        mapObj.put("isNext", isNext);
        List<Incubator> incubators = incubatorMapper.getIncubators(incubator,pageStartNo,pageSize);
        List<Map<String, Object>> listMap = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(incubators)){
            for(Incubator i : incubators){
                Map<String, Object> mapOt = new HashMap<>();
                /*if (null != user && null != user.getId()) {
                    //管理员角色可查看数据
                    if (MyConstant.UserRole.UserRoleAdmin.toString().equals(dictionaryMapper.getDictionaryById(user.getRole().getId()).getCode())) {
                        //mapOt = MyUtils.getBaseResultMap(i, userMapper);
                        //排序
                        mapOt.put("sequence", i.getSequence());
                        //是否可用
                        mapOt.put("active", i.getActive());
                    }
                }*/
                mapOt.put("id",i.getId());
                mapOt.put("name",i.getName());
                //mapOt.put("type",i.getOfficeType().getId());
                mapOt.put("advantage",i.getAdvantage());
               // mapOt.put("label",i.getOfficeLabel());
                mapOt.put("popularity",i.getPopularity());
                mapOt.put("def_popularity",i.getDef_popularity());
                mapOt.put("address",i.getAddress());
                mapOt.put("renting_area",i.getRenting_area());
                mapOt.put("price",i.getPrice());
                mapOt.put("property",i.getProperty());
                mapOt.put("profile",i.getProfile());
                mapOt.put("coordinate",i.getCoordinate());
                mapOt.put("area",i.getArea());
                mapOt.put("elevator",i.getElevator());
                mapOt.put("electricity",i.getElectricity());
                mapOt.put("virescence",i.getVirescence());
                mapOt.put("building",i.getBuilding());
                mapOt.put("broadband",i.getBroadband());
                mapOt.put("water",i.getWater());
                mapOt.put("storey",i.getStorey());
                mapOt.put("park",i.getPark());
                mapOt.put("park_price",i.getPark_price());
                mapOt.put("subway",i.getSubway());
                mapOt.put("bus",i.getBus());

                listMap.add(mapOt);
            }
        }
        mapObj.put("list",listMap);
        return MyUtils.setDateMap(myError,mapObj);
    }

    //创建孵化器
    @Override
    public Map<String, Object> createIncubator(Incubator incubator) {
        Map<String, Object> mapObj = new HashMap<>();
        MyError myError = new MyError();
        //设置创建人和时间，修改人和时间
        User user =new User();
        Date date = new Date();
        incubator.setCreate_user(user);
        incubator.setWrite_user(user);
        incubator.setCreate_date(date);
        incubator.setWrite_date(date);
        int count;
        try {
            count = incubatorMapper.createIncubator(incubator);
            if (count <= 0) {
                return MyUtils.setMyErrorMap(MyConstant.returnGeneralErrorCode, MyErrorMsg.createError);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return MyUtils.setMyErrorMap(MyConstant.returnGeneralErrorCode, MyErrorMsg.dbError);
        }
        //发生异常和正常流程走完的信息返回调用不一样的工具类数据返回接口
        return MyUtils.setDateMap(myError, mapObj);
    }

    //更新孵化器
    @Override
    public Map<String, Object> updateIncubator(Incubator incubator) {
        Map<String, Object> mapObj = new HashMap<>();
        MyError myError = new MyError();
        //设置修改人和时间
        User user = new User();
        Date date = new Date();
        incubator.setWrite_user(user);
        incubator.setWrite_date(date);
        int count;
        try {
            count = incubatorMapper.updateIncubator(incubator);
            if (count <= 0) {
                return MyUtils.setMyErrorMap(MyConstant.returnGeneralErrorCode, MyErrorMsg.updateError);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return MyUtils.setMyErrorMap(MyConstant.returnGeneralErrorCode, MyErrorMsg.dbError);
        }
        return MyUtils.setDateMap(myError, mapObj);
    }
}
