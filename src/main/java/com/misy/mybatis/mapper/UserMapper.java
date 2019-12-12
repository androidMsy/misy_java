package com.misy.mybatis.mapper;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.misy.mybatis.entity.UserAccount;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public interface UserMapper extends BaseMapper<UserAccount> {
    Map selectUser(String userId);
    void updataUserData(String realName, String intro, String url, String userId);
    List getAllUser();
}
