package com.gdsp.dev.persist.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * 持久化工具类
 * @author xiangguo
 *
 */
public class PersistUtils {

    /**
     * 获取正则匹配的字符串
     * @param reg
     * @param content
     * @return
     */
    public static List<String> getRegexpMathList(String reg, String content) {
        List<String> mappingList = new ArrayList<String>();
        if (StringUtils.isNotBlank(content)) {
            Matcher m = Pattern.compile(reg).matcher(content);
            while (m.find()) {
                mappingList.add(m.group());
            }
        }
        return mappingList;
    }
}
