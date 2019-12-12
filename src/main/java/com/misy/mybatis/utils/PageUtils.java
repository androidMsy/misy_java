package com.misy.mybatis.utils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.misy.mybatis.entity.UserAccount;

import java.util.List;

public class PageUtils {
    public static PageInfo getPageData(int pageNum, int pageSize, List mList){
        PageHelper.startPage(pageNum, pageSize);
        PageInfo info = new PageInfo(mList);
        return info;
    }
}
