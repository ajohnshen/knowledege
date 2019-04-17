package com.wu.knowledge.common.cache;


import com.wu.knowledge.user.model.User;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 系统数据缓存类
 * Created by TZ on 2018/9/12.
 */
public class MyCache {

    /**
     * 所有用户登录密匙缓存(KEY为LoginKey，VALUE为用户对象)
     */
    private static ConcurrentHashMap<String, User> loginUsersMap = new ConcurrentHashMap<>();
    /**
     * 用户缓存Map(KEY为用户ID，VALUE为用户对象)[暂未使用]
     */
    private static ConcurrentHashMap<Integer, User> userCacheMap = new ConcurrentHashMap<>();

    public static ConcurrentHashMap<String, User> getLoginUsersMap() {
        return loginUsersMap;
    }

    public static void setLoginUsersMap(ConcurrentHashMap<String, User> loginUsersMap) {
        MyCache.loginUsersMap = loginUsersMap;
    }

    public static ConcurrentHashMap<Integer, User> getUserCacheMap() {
        return userCacheMap;
    }

    public static void setUserCacheMap(ConcurrentHashMap<Integer, User> userCacheMap) {
        MyCache.userCacheMap = userCacheMap;
    }

}
