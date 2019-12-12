package com.misy.mybatis.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.misy.mybatis.entity.UserAccount;
import com.misy.mybatis.mapper.UserMapper;
import com.misy.mybatis.service.UserService;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserAccount> implements UserService {

    private static final String EMPTY_STRING = "";

    @Autowired
    UserMapper userMapper;

    @Override
    public boolean isRegisterAccount(String username) {
        LambdaQueryWrapper<UserAccount> wrapper = new LambdaQueryWrapper<UserAccount>().eq(UserAccount::getUsername, username);
        return null != getOne(wrapper, false);
    }


    @Override
    public UserAccount uploadToken(UserAccount userAccount) {
        LambdaQueryWrapper<UserAccount> wrapper = new LambdaQueryWrapper<UserAccount>()
                .eq(UserAccount::getUsername, userAccount.getUsername());
        UserAccount user = getOne(wrapper);
        saveOrUpdate(userAccount, wrapper);
        return user;
    }

    @Override
    public UserAccount getUser(UserAccount userAccount) {
        return getOne(new LambdaQueryWrapper<UserAccount>().eq(UserAccount::getUsername, userAccount.getUsername()));
    }

    @Override
    public Map selectUser(String userId) {
        return getBaseMapper().selectUser(userId);
    }

    @Override
    public void updataHeaderUrl(String url, String userId) {
        getBaseMapper().updataUserData(null, null, url, userId);
    }

    @Override
    public void updataRealName(String realName, String userId) {
        getBaseMapper().updataUserData(realName, null, null, userId);
    }

    @Override
    public void updataIntro(String intro, String userId) {
        getBaseMapper().updataUserData(null, intro, null, userId);
    }

    public List getAllUser() {
        return getBaseMapper().getAllUser();
    }
}
