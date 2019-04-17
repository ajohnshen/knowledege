package com.wu.knowledge.incubator.controller;

import com.wu.knowledge.common.constant.MyConstant;
import com.wu.knowledge.common.constant.MyErrorMsg;
import com.wu.knowledge.common.model.MyError;
import com.wu.knowledge.common.utils.MyUtils;
import com.wu.knowledge.incubator.model.Incubator;
import com.wu.knowledge.incubator.service.IIncubatorService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 〈功能简述〉<br>
 * 〈孵化器控制器〉
 *
 * @create create by WSD on 2018/12/18
 */
@Controller
@RequestMapping("/incubator/IncubatorController")
public class IncubatorController {

    @Resource
    private IIncubatorService incubatorService;

    //创建孵化器
    @ResponseBody
    @RequestMapping("/createIncubator")
    private Object createIncubator(
            @RequestParam(value = "loginKey", required = false) String loginKey,
            @RequestParam(value = "active", required = false) Boolean active,
            @RequestParam(value = "sequence", required = false) Integer sequence,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "user", required = false) Integer user,
            @RequestParam(value = "lable", required = false) Integer lable,
            @RequestParam(value = "cover", required = false) Integer cover,
            @RequestParam(value = "image", required = false) Integer image,
            @RequestParam(value = "price_unit", required = false) Integer price_unit,
            @RequestParam(value = "type", required = false) Integer type,
            @RequestParam(value = "advantage", required = false) String advantage,
            @RequestParam(value = "popularity", required = false) Integer popularity,
            @RequestParam(value = "def_popularity", required = false) Integer def_popularity,
            @RequestParam(value = "address", required = false) String address,
            @RequestParam(value = "renting_area", required = false) String renting_area,
            @RequestParam(value = "price", required = false) String price,
            @RequestParam(value = "property", required = false) String property,
            @RequestParam(value = "profile", required = false) String profile,
            @RequestParam(value = "coordinate", required = false) String coordinate,
            @RequestParam(value = "area", required = false) String area,
            @RequestParam(value = "elevator", required = false) String elevator,
            @RequestParam(value = "electricity", required = false) String electricity,
            @RequestParam(value = "virescence", required = false) String virescence,
            @RequestParam(value = "building", required = false) String building,
            @RequestParam(value = "broadband", required = false) String broadband,
            @RequestParam(value = "water", required = false) String water,
            @RequestParam(value = "storey", required = false) String storey,
            @RequestParam(value = "park", required = false) String park,
            @RequestParam(value = "park_price", required = false) String park_price,
            @RequestParam(value = "subway", required = false) String subway,
            @RequestParam(value = "bus", required = false) String bus
    ){
        //必填字段校验
        List<Map<String, Object>> fieldList = new LinkedList<>();
        Map<String, Object> fieldMap = new HashMap<>();
        fieldMap.put("是否可用", active);
        fieldMap.put("孵化器名称", name);
        fieldMap.put("顺序", sequence);
        fieldList.add(fieldMap);
        try {
            MyUtils.fieldCheck(fieldList);
        } catch (Exception e) {
            e.printStackTrace();
            return MyUtils.setMyErrorJson(MyConstant.returnGeneralErrorCode, e.getMessage());
        }

        Incubator incubator = new Incubator();
        incubator.setActive(active);
        incubator.setSequence(sequence);
        incubator.setName(name);
        incubator.setAdvantage(advantage);
        incubator.setDef_popularity(def_popularity);
        incubator.setPopularity(popularity);
        incubator.setAddress(address);
        incubator.setRenting_area(renting_area);
        incubator.setPrice(price);
        incubator.setProperty(property);
        incubator.setProfile(profile);
        incubator.setCoordinate(coordinate);
        incubator.setArea(area);

        incubator.setElevator(elevator);
        incubator.setElectricity(electricity);
        incubator.setVirescence(virescence);
        incubator.setBuilding(building);
        incubator.setBroadband(broadband);
        incubator.setWater(water);
        incubator.setStorey(storey);
        incubator.setPark(park);
        incubator.setPark_price(park_price);
        incubator.setSubway(subway);
        incubator.setBus(bus);

        Map<String, Object> map = incubatorService.createIncubator(incubator);
        return MyUtils.returnJson(map, (MyError) map.get("error"));
    }

    //更新孵化器
    @ResponseBody
    @RequestMapping("/updateIncubator")
    private Object updateIncubator(
            @RequestParam(value = "loginKey", required = false) String loginKey,
            @RequestParam(value = "id", required = false) Integer id,
            @RequestParam(value = "active", required = false) Boolean active,
            @RequestParam(value = "sequence", required = false) Integer sequence,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "user", required = false) Integer user,
            @RequestParam(value = "lable", required = false) Integer lable,
            @RequestParam(value = "cover", required = false) Integer cover,
            @RequestParam(value = "image", required = false) Integer image,
            @RequestParam(value = "price_unit", required = false) Integer price_unit,
            @RequestParam(value = "type", required = false) Integer type,
            @RequestParam(value = "advantage", required = false) String advantage,
            @RequestParam(value = "popularity", required = false) Integer popularity,
            @RequestParam(value = "def_popularity", required = false) Integer def_popularity,
            @RequestParam(value = "address", required = false) String address,
            @RequestParam(value = "renting_area", required = false) String renting_area,
            @RequestParam(value = "price", required = false) String price,
            @RequestParam(value = "property", required = false) String property,
            @RequestParam(value = "profile", required = false) String profile,
            @RequestParam(value = "coordinate", required = false) String coordinate,
            @RequestParam(value = "area", required = false) String area,
            @RequestParam(value = "elevator", required = false) String elevator,
            @RequestParam(value = "electricity", required = false) String electricity,
            @RequestParam(value = "virescence", required = false) String virescence,
            @RequestParam(value = "building", required = false) String building,
            @RequestParam(value = "broadband", required = false) String broadband,
            @RequestParam(value = "water", required = false) String water,
            @RequestParam(value = "storey", required = false) String storey,
            @RequestParam(value = "park", required = false) String park,
            @RequestParam(value = "park_price", required = false) String park_price,
            @RequestParam(value = "subway", required = false) String subway,
            @RequestParam(value = "bus", required = false) String bus
    ){
        //必填字段校验
        List<Map<String, Object>> fieldList = new LinkedList<>();
        Map<String, Object> fieldMap = new HashMap<>();
        fieldMap.put("孵化器ID", id);
        fieldList.add(fieldMap);
        try {
            MyUtils.fieldCheck(fieldList);
        } catch (Exception e) {
            e.printStackTrace();
            return MyUtils.setMyErrorJson(MyConstant.returnGeneralErrorCode, e.getMessage());
        }
        Incubator incubator = new Incubator();
        incubator.setId(id);
        incubator.setActive(active);
        incubator.setSequence(sequence);
        incubator.setName(name);
        incubator.setAdvantage(advantage);
        incubator.setDef_popularity(def_popularity);
        incubator.setPopularity(popularity);
        incubator.setAddress(address);
        incubator.setRenting_area(renting_area);
        incubator.setPrice(price);
        incubator.setProperty(property);
        incubator.setProfile(profile);
        incubator.setCoordinate(coordinate);
        incubator.setArea(area);

        incubator.setElevator(elevator);
        incubator.setElectricity(electricity);
        incubator.setVirescence(virescence);
        incubator.setBuilding(building);
        incubator.setBroadband(broadband);
        incubator.setWater(water);
        incubator.setStorey(storey);
        incubator.setPark(park);
        incubator.setPark_price(park_price);
        incubator.setSubway(subway);
        incubator.setBus(bus);

        Map<String, Object> map = incubatorService.updateIncubator(incubator);
        return MyUtils.returnJson(map, (MyError) map.get("error"));
    }

    //条件查询孵化器
    @ResponseBody
    @RequestMapping("/getIncubators")
    private Object getIncubators(
            @RequestParam(value = "loginKey", required = false) String loginKey,
            @RequestParam(value = "id", required = false) Integer id,
            @RequestParam(value = "active", required = false) Boolean active,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "type", required = false) Integer type,
            @RequestParam(value = "address", required = false) String address,
            @RequestParam(value = "lable", required = false) String lable,
            @RequestParam(value = "price", required = false) String price,
            @RequestParam(value = "renting_area", required = false) String renting_area,
            @RequestParam(value = "popularity", required = false) Integer popularity,
            @RequestParam(value = "pageNo", required = false) String pageNo,//页码从0开始
            @RequestParam(value = "pageSize", required = false) String pageSize//每页显示数
    ){
        Map<String, Object> map;
        Incubator incubator = new Incubator();
        incubator.setId(id);
        incubator.setActive(active);
        incubator.setName(name);

        incubator.setAddress(address);
        incubator.setPrice(price);
        incubator.setRenting_area(renting_area);
        incubator.setPrice(price);
        incubator.setPopularity(popularity);
        //分页数据
        int pNo;
        int pSize;
        try {
            pNo = MyUtils.getPageNo(pageNo);
            pSize = MyUtils.getPageSize(pageSize);
        } catch (Exception e1) {
            e1.printStackTrace();
            return MyUtils.setMyErrorJson(MyConstant.returnGeneralErrorCode, MyErrorMsg.pagingError);
        }
        map = incubatorService.getIncubators(incubator,pNo,pSize);
        return MyUtils.returnJson(map, (MyError) map.get("error"));
    }
}
