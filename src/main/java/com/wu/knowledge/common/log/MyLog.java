package com.wu.knowledge.common.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 控制台日志
 * Created by TZ on 2018/11/30.
 */
public class MyLog {

    private static final Logger logger = LoggerFactory.getLogger(MyLog.class);

    /**
     * 输出日志
     */
    public static void info(String str) {
        logger.info(str);
    }

}
