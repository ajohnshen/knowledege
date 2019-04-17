package com.wu.knowledge.common.constant;

/**
 * 常量类(与数据库字典表数据对应)
 * Created by TZ on 2018/9/11.
 */
public interface MyConstant {

    /**
     * 登录密匙
     */
    String loginKey = "loginKey";

    /**
     * 系统无异常编号
     */
    int returnSuccessCode = 0;

    /**
     * 登陆错误编码
     */
    int returnNoLoginErrorCode = 1;

    /**
     * 系统错误编码
     */
    int returnGeneralErrorCode = 2;

    /**
     * 微信小程序我要路演提交推送手机号
     */
    String[] miniprogramConsultMobile = {"13076714579","15989108849","18928863736"};//13570973932张莹
//    String[] miniprogramConsultMobile = {"13076714579"};

    /**
     * 日期格式(年月日时分秒)
     */
    String dateFormat = "yyyy-MM-dd HH:mm:ss";

    /**
     * 日期格式(年月日)
     */
    String dateNotHourFormat = "yyyy-MM-dd";
    /**
     * 时间格式(时分秒)
     */
    String dateHour = "HH:mm:ss";

    /**
     * 用户角色
     */
    enum UserRole {
        //普通用户
        UserRoleOrdinary,
        //管理员
        UserRoleAdmin,
        //投资人
        UserRoleInvestor,
        //投资机构
        UserRoleInvest,
        //企业
        UserRoleEnterprise
    }

    /**
     * 书籍类型
     */
    enum BookType {
        //经典著作
        ClassicBook,
        //教育
        EducationBook,
        //生活
        LifeBook,
        //其他书籍
        OtherBook
    }

    /**
     * 文件类型
     */
    enum FileType {
        //PPT文件
        FileTypePPT,
        //视频文件
        FileTypeVideo,
        //其他文件类型
        FileTypeOther
    }
    /**
     * 课程类型
     */
    enum CourseType {
        //必修课程
        RequiredCourse,
        //选修课程
        ElectiveCourse
    }
    /**
     * 记事本类型
     */
    enum NoteType {
        //日常生活
        DayLifeNote,
        //学习笔记
        StudyNote,
        //其他笔记
        OtherNote
    }

    /**
     * 文件目录
     */
    enum Catalog {
        //用户-头像
        CatalogUserHead("HeadLogo", "knowledge/file/"),
        //投资人-头像
        CatalogInvestorHead("CatalogInvestorHead", "investor/head/"),
        //书籍-Logo
        CatalogBookLogo("CatalogBookLogo", "knowledge/file/"),
        //投资机构-投资分析
        CatalogInvestAnalysis("CatalogInvestAnalysis", "invest/analysis/"),
        //企业-Logo
        CatalogEnterpriseLogo("CatalogEnterpriseLogo", "enterprise/logo/"),
        //企业-商业计划书
        CatalogEnterpriseProposal("CatalogEnterpriseProposal", "enterprise/proposal/"),
        //路演-文件
        CatalogRoadshowFile("CatalogRoadshowFile", "knowledge/file/"),
        //路演-视频
        CatalogRoadshowVideo("CatalogRoadshowVideo", "knowledge/video/"),
        //路演-封面
        CatalogRoadshowCover("CatalogRoadshowCover", "knowledge/cover/"),
        //专辑-封面
        CatalogAlbumCover("CatalogAlbumCover", "album/cover/"),
        //活动-封面
        CatalogActivityCover("CatalogActivityCover", "activity/cover/"),
        //热门推介-文件
        CatalogHotFile("CatalogHotFile", "hot/file/"),
        //热门推介-视频
        CatalogHotVideo("CatalogHotVideo", "hot/video/"),
        //热门推介-封面
        CatalogHotCover("CatalogHotCover", "hot/cover/");

        private String key;
        private String value;

        Catalog(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public static String getValueByKey(String key) {
            Catalog[] catalogs = Catalog.values();
            for (Catalog c : catalogs) {
                if (c.getKey().equals(key)) {
                    return c.getValue();
                }
            }
            return "";
        }

        String getKey() {
            return key;
        }

        void setKey(String key) {
            this.key = key;
        }

        String getValue() {
            return value;
        }

        void setValue(String value) {
            this.value = value;
        }
    }

    /**
     * 性别
     */
    enum Sex {
        //男
        SexMan,
        //女
        SexWoMan
    }

    /**
     * 视频点播分类
     */
    enum VideoCategory {
        //视频点播分类-企业
        VideoCategoryEnterprise("VideoCategoryEnterprise", 825770038L),
        //视频点播分类-路演
        VideoCategoryRoadshow("VideoCategoryRoadshow", 303814964L),
        //视频点播分类-投资机构
        VideoCategoryInvest("VideoCategoryInvest", 266332756L),
        //视频点播分类-投资人
        VideoCategoryInvestor("VideoCategoryInvestor", 375099204L),
        //视频点播分类-热门推介
        VideoCategoryHot("VideoCategoryHot", 580321123L),
        //视频点播分类-活动
        VideoCategoryActivity("VideoCategoryActivity", 19773851L);

        private String key;
        private Long value;

        VideoCategory(String key, Long value) {
            this.key = key;
            this.value = value;
        }

        public static Long getValueByKey(String key) {
            VideoCategory[] videoCategories = VideoCategory.values();
            for (VideoCategory vc : videoCategories) {
                if (vc.getKey().equals(key)) {
                    return vc.getValue();
                }
            }
            return null;
        }

        String getKey() {
            return key;
        }

        void setKey(String key) {
            this.key = key;
        }

        Long getValue() {
            return value;
        }

        void setValue(Long value) {
            this.value = value;
        }
    }

    /**
     * 路演时间（标签搜索条件）
     */
    enum RoadShowTime {
        //路演时间-近期路演（SQL查询条件）（当前设置最近30天为近期）
        RoadshowTimeNear(
                "RoadshowTimeNear",
                "recording >= CONCAT(DATE_SUB(CURDATE(),INTERVAL 30 DAY), ' 00:00:00')"),
        //路演时间-往期路演（SQL查询条件）（当前设置30天前为往期）
        RoadshowTimePeriod(
                "RoadshowTimePeriod",
                "recording <= CONCAT(DATE_SUB(CURDATE(),INTERVAL 30 DAY), ' 00:00:00')");
        private String key;
        private String value;

        RoadShowTime(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public static String getValueByKey(String key) {
            RoadShowTime[] roadShowTimes = RoadShowTime.values();
            for (RoadShowTime rst : roadShowTimes) {
                if (rst.getKey().equals(key)) {
                    return rst.getValue();
                }
            }
            return "";
        }

        private String getKey() {
            return key;
        }

        private void setKey(String key) {
            this.key = key;
        }

        private String getValue() {
            return value;
        }

        private void setValue(String value) {
            this.value = value;
        }
    }

    /**
     * 企业成立时间
     */
    enum EnterpriseCreateDate {
        //半年以内
        EnterpriseCreateDateHalfYear(
                "EnterpriseCreateDateHalfYear",
                "(found_date >= DATE_SUB(CURDATE(),INTERVAL 182 DAY))"
        ),
        //半年至一年
        EnterpriseCreateDateOneYear(
                "EnterpriseCreateDateOneYear",
                "(found_date <= DATE_SUB(CURDATE(),INTERVAL 182 DAY) AND found_date >= DATE_SUB(CURDATE(),INTERVAL 1 YEAR))"
        ),
        //一年至两年
        EnterpriseCreateDateTwoYear(
                "EnterpriseCreateDateTwoYear",
                "(found_date <= DATE_SUB(CURDATE(),INTERVAL 1 YEAR) AND found_date >= DATE_SUB(CURDATE(),INTERVAL 2 YEAR))"
        ),
        //两年至三年
        EnterpriseCreateDateThreeYear(
                "EnterpriseCreateDateThreeYear",
                "(found_date <= DATE_SUB(CURDATE(),INTERVAL 2 YEAR) AND found_date >= DATE_SUB(CURDATE(),INTERVAL 3 YEAR))"
        ),
        //三年以上
        EnterpriseCreateDateMoreThanThreeYear(
                "EnterpriseCreateDateMoreThanThreeYear",
                "(found_date <= DATE_SUB(CURDATE(),INTERVAL 3 YEAR))"
        );

        private String key;
        private String value;

        EnterpriseCreateDate(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public static String getValueByKey(String key) {
            EnterpriseCreateDate[] enterpriseCreateDates = EnterpriseCreateDate.values();
            for (EnterpriseCreateDate ecd : enterpriseCreateDates) {
                if (ecd.getKey().equals(key)) {
                    return ecd.getValue();
                }
            }
            return "";
        }

        private String getKey() {
            return key;
        }

        private void setKey(String key) {
            this.key = key;
        }

        private String getValue() {
            return value;
        }

        private void setValue(String value) {
            this.value = value;
        }
    }



    /**
     * 领域（行业）
     */
    interface Field {

        /**
         * 电子信息
         */
        enum FieldElectronic implements Field {
            //软件
            FieldSoftware,
            //微电子技术
            FieldMicroelectronic,
            //计算机产品及其网络应用技术
            FieldComputer,
            //通信技术
            FieldCommunications,
            //广播影视技术
            FieldRadio,
            //新型电子元器件
            FieldNew,
            //信息安全技术
            FieldInformation,
            //智能交通和轨道交通技术
            FieldIntelligent
        }

        /**
         * 生物与新医药
         */
        enum FieldBioMedicine implements Field {
            //医药生物技术
            FieldMedical,
            //中药、天然药物
            FieldTraditional,
            //化学药研发技术
            FieldChemical,
            //药物新剂型与制剂创制技术
            FieldNewDosage,
            //医疗仪器、设备与医学专用软件
            FieldMedicalDevices,
            //轻工和化工生物技术
            FieldLight,
            //农业生物技术
            FieldAgricultural
        }

        /**
         * 航空航天
         */
        enum FieIdAeronautics implements Field {
            //航空技术
            FildAerotechnics,
            //航天技术
            FildSpaceTechnology
        }

        /**
         * 新材料
         */
        enum FieldNewMaterial implements Field {
            //金属材料
            FildMetalMaterial,
            //无机非金属材料
            FildInorganicNonmetallicMaterials,
            //高分子材料
            FildHighPolymerMaterial,
            //生物医用材料
            FildBiomedicalMaterials,
            //精细和专用化学品
            FildFineSpecialtyChemicals,
            //与文化艺术产业相关的新材料
            FildNewMaterialsRelated
        }

        /**
         * 高技术服务
         */
        enum FieldHighTechnologyServices implements Field {
            //研发与设计服务
            FildDesignServices,
            //检验检测认证与标准服务
            FildInspectionTestingServices,
            //信息技术服务
            FildInformationTechnology,
            //高技术专业化服务
            FildHighTechnology,
            // 知识产权与成果转化服务
            FildIntellectualProperty,
            //电子商务与现代物流技术
            FildEcommerceModernLogistics,
            //城市管理与社会服务
            FildUrbanManagement,
            //文化创意产业支撑技术
            FildSupportingTechnologiesCultura
        }

        /**
         * 新能源与节能
         */
        enum FieldNewEnergy implements Field {
            //可再生清洁能源
            FildRenewableEnergy,
            //核能及氢能
            FildNuclearHydrogenEnergy,
            //新型高效能量转换与储存技术
            FildNewEfficientEnergy,
            //高效节能技术
            FildHighEfficiency
        }

        /**
         * 资源与环境
         */
        enum FieldResourceEnvironment implements Field {
            ///水污染控制与水资源利用技术
            FildWaterPollutionControl,
            //大气污染控制技术
            FildAirPollutionControl,
            //固体废弃物处置与综合利用技术
            FildSolidWasteDisposal,
            //物理性污染防治技术
            FildPhysicalPollutionControl,
            //环境监测及环境事故应急处理技术
            FildEnvironmentalMonitoring,
            //生态环境建设与保护技术
            FildConstructionProtection,
            //清洁生产技术
            FildClearerProduction,
            //资源勘查、高效开采与综合利用技术
            FildResourcesExploration
        }

        /**
         * 先进制造与自动化
         */
        enum FieldAdvancedManufacturing implements Field {
            //工业生产过程控制系统
            FildIndustrialProcess,
            //安全生产技术
            FildSafeProduction,
            //高性能、智能化仪器仪表
            FildHighPerformance,
            //先进制造工艺与装备
            FildAdvancedManufacturing,
            //新型机械
            FildNewTypeMechanical,
            //电力系统与设备
            FildPowerSystems,
            //汽车及轨道车辆相关技术
            FildVehicleAutomotiveRail,
            //高技术船舶与海洋工程装备设计制造技术
            FildHighTechShipMarine,
            //传统文化产业改造技术
            FildTransformationTechnology
        }

        /**
         * 其他行业
         */
        enum FieldOtherIndustries implements Field {
            //教育
            FildEducation,
            //金融
            FildFinancial,
            //旅游
            FildTravel,
            //农业
            FildAgriculture,
            //文化娱乐
            FildEntertainment,
            //房产服务
            FildRealEstateService,
            //本地生活
            FildLocalLife,
            //体育运动
            FildPhysicalExercise,
            //广告营销
            FildAdvertising,
            //其他
            FildOther
        }

    }

}
