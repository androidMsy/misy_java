package com.misy.mybatis.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;

import java.util.Arrays;
import java.util.List;

/**
 *  fastjson utils
 *  author:misy
 *  createDate:2019.11.4
 */
public class JSONUtils {
    /**
     * 过滤掉some属性
     * @param o
//     * @param excludeKeys
     * @return
     */
    public static JSON toJson(Object o) {
        List<String> excludes = Arrays.asList("password", "token");
        SimplePropertyPreFilter filter = new SimplePropertyPreFilter();
        filter.getExcludes().addAll(excludes);    //重点！！！
        return JSON.parseObject(JSON.toJSONString(o, filter));
    }
}
