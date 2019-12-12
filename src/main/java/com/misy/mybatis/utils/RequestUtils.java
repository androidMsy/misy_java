package com.misy.mybatis.utils;

import com.misy.mybatis.entity.UserAccount;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestUtils {
    private static Logger logger = LoggerFactory.getLogger(RequestUtils.class);

    /**
     * 获取当前登录的用户，若用户未登录，则返回未登录 json
     *
     * @return
     */
    public static UserAccount currentLoginUser() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            Object principal = subject.getPrincipals().getPrimaryPrincipal();
            if (principal instanceof UserAccount) {
                return (UserAccount) principal;
            }
        }
        return null;
    }

    /**
     * 判断当前用户token是否过期
     * @return
     */
    public static boolean tokenIsAuthentication(){
        boolean isAuth = false;
        if (null != currentLoginUser() && !TextUtils.isEmpty(currentLoginUser().getUsername()))isAuth = true;
        return isAuth;
    }
    /**
     * 获取当前用户userId
     */
    public static String getUserId(){
        return currentLoginUser().getUserId();
    }
}
