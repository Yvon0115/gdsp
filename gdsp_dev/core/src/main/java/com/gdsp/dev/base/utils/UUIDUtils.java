/*
 * Copyright(c) FastSpace Software 2014. All Rights Reserved.
 */
package com.gdsp.dev.base.utils;

import java.util.UUID;

/**
 * UUID工具类
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public class UUIDUtils {

    /**
     * 取得uuid
     * @return uuid串
     */
    public static String getRandomUUID() {
        String uuid = UUID.randomUUID().toString();
        return uuid.substring(0, 8) + uuid.substring(9, 13) + uuid.substring(14, 18) + uuid.substring(19, 23) + uuid.substring(24);
    }
}
