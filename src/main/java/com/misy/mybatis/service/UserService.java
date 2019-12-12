package com.misy.mybatis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.misy.mybatis.entity.UserAccount;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public interface UserService extends IService<UserAccount> {
    boolean isRegisterAccount(String username);
    UserAccount uploadToken(UserAccount userAccount);
    UserAccount getUser(UserAccount userAccount);
    Map selectUser(String userId);
    void updataHeaderUrl(String url, String userId);
    void updataRealName(String realName, String userId);
    void updataIntro(String intro, String userId);
    List getAllUser();
}
