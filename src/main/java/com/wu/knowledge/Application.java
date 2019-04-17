package com.wu.knowledge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * 启动器(项目的启动和初始化)
 */
@SpringBootApplication
@ServletComponentScan
public class Application {

    public static void main(String[] args) {
        // 接口测试示例: http://localhost:9090//basedata/dictionary/DictionaryController/getDictionaries
        SpringApplication.run(Application.class, args);
    }

}

