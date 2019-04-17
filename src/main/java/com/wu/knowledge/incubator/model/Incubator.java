package com.wu.knowledge.incubator.model;

import com.wu.knowledge.common.model.BaseModel;

// 孵化器
public class Incubator extends BaseModel {

    /**
     *  孵化器名称
     */
    private String name;
    /**
     *  标签（字典id）
     */
    //private int lable;
    /**
     * 封面(文件ID)
     */
    //private File cover;
    /**
     * 详情图(文件ID)
     */
    //private File image;
    /**
     * 详情图数组
     * (数据字典ID数组，只做SQL查询封装使用，不存入数据库)
     */
    private Integer[] imageIDs;
    /**
     * 卖点标签
     */
    private String advantage;
    /**
     *  人气
     */
    private Integer popularity;
    /**
     *  人气默认值
     */
    private Integer def_popularity;
    /**
     *  地址
     */
    private String address;
    /**
     *  可租面积
     */
    private String renting_area;
    /**
     * 价格
     */
    private String price;
    /**
     * 物业优势
     */
    private String property;
    /**
     * 简介
     */
    private String profile;
    /**
     * 地图坐标
     */
    private String coordinate;
    /**
     *  园区面积
     */
    private String area;
    /**
     * 电梯
     */
    private String elevator;
    /**
     * 电费
     */
    private String electricity;
    /**
     * 绿化
     */
    private String virescence;
    /**
     * 楼体栋数
     */
    private String building;
    /**
     * 宽带
     */
    private String broadband;
    /**
     * 水费
     */
    private String water;
    /**
     * 楼层
     */
    private String storey;
    /**
     * 车位
     */
    private String park;
    /**
     * 停车费
     */
    private String park_price;
    /**
     * 地铁
     */
    private String subway;
    /**
     * 公交
     */
    private String bus;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public Integer[] getImageIDs() {
        return imageIDs;
    }

    public void setImageIDs(Integer[] imageIDs) {
        this.imageIDs = imageIDs;
    }

    public String getAdvantage() {
        return advantage;
    }

    public void setAdvantage(String advantage) {
        this.advantage = advantage;
    }

    public Integer getPopularity() {
        return popularity;
    }

    public void setPopularity(Integer popularity) {
        this.popularity = popularity;
    }

    public Integer getDef_popularity() {
        return def_popularity;
    }

    public void setDef_popularity(Integer def_popularity) {
        this.def_popularity = def_popularity;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRenting_area() {
        return renting_area;
    }

    public void setRenting_area(String renting_area) {
        this.renting_area = renting_area;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getElevator() {
        return elevator;
    }

    public void setElevator(String elevator) {
        this.elevator = elevator;
    }

    public String getElectricity() {
        return electricity;
    }

    public void setElectricity(String electricity) {
        this.electricity = electricity;
    }

    public String getVirescence() {
        return virescence;
    }

    public void setVirescence(String virescence) {
        this.virescence = virescence;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getBroadband() {
        return broadband;
    }

    public void setBroadband(String broadband) { this.broadband = broadband; }

    public String getWater() {
        return water;
    }

    public void setWater(String water) {
        this.water = water;
    }

    public String getStorey() {
        return storey;
    }

    public void setStorey(String storey) {
        this.storey = storey;
    }

    public String getPark() {
        return park;
    }

    public void setPark(String park) {
        this.park = park;
    }

    public String getPark_price() {
        return park_price;
    }

    public void setPark_price(String park_price) {
        this.park_price = park_price;
    }

    public String getSubway() {
        return subway;
    }

    public void setSubway(String subway) {
        this.subway = subway;
    }

    public String getBus() {
        return bus;
    }

    public void setBus(String bus) {
        this.bus = bus;
    }


}
