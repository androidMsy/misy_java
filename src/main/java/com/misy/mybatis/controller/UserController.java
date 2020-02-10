package com.misy.mybatis.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.misy.mybatis.entity.UserAccount;
import com.misy.mybatis.service.UserService;
import com.misy.mybatis.utils.RequestUtils;
import com.misy.mybatis.utils.ResultUtil;
import com.misy.mybatis.utils.TextUtils;
import com.misy.mybatis.utils.UUIDUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;

import java.io.Serializable;
import java.util.List;

import static com.misy.mybatis.contacts.LoginContacts.*;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Object register(@RequestBody UserAccount account){
        if (TextUtils.isEmpty(account.getUsername()) || TextUtils.isEmpty(account.getPassword())){
            return ResultUtil.fail.msg(INPUT_USERORPSWD);
        }else {
            UserAccount userAccount = account;
            if (userService.isRegisterAccount(account.getUsername()))return ResultUtil.success.msg(ACCOUNT_ISREGISTED);
            userAccount.setUserId(UUIDUtils.getUUID());
            userService.save(userAccount);
            return ResultUtil.build(userAccount);
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Object login(@RequestBody UserAccount account, HttpServletRequest request){
        try {
            UsernamePasswordToken token = new UsernamePasswordToken(account.getUsername(), account.getPassword());
            Subject subject = SecurityUtils.getSubject();
            subject.login(token);
            SecurityUtils.getSubject().getSession().setTimeout(-1000L);
            Serializable authToken =  subject.getSession().getId();
            UserAccount result = (UserAccount) subject.getPrincipal();
            result.setToken((String) authToken);
            userService.uploadToken(result);
            return ResultUtil.build(result);
        }catch (DisabledAccountException e){
            request.setAttribute("msg", USER_NOT_PEIMISSION);
            return ResultUtil.fail.msg(USER_NOT_PEIMISSION);
        }catch (UnknownAccountException e){
            request.setAttribute("msg", LOGIN_FAILED);
            return ResultUtil.fail.msg(LOGIN_FAILED);
        }catch (Exception e){
            request.setAttribute("msg", e.getMessage());
            return ResultUtil.fail.msg(e.getMessage());
        }
    }
    @RequestMapping(value = "/loginout",method = RequestMethod.GET)
    public Object loginout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return ResultUtil.success.msg(LOGINOUT_SUCCESS);
    }

    /**
     * 获取当前登录用户信息
     * @return
     */
    @RequestMapping(value = "/getUser", method = RequestMethod.GET)
    @ResponseBody
    public Object getUser(){
        UserAccount userAccount = RequestUtils.currentLoginUser();
        if (null != userAccount && !TextUtils.isEmpty(userAccount.getUsername())){
            return ResultUtil.build(userService.selectUser(userAccount.getUserId()));
        }else {
            return ResultUtil.fail.status(401).msg(UNLOGIN_STATUS401);
        }
    }

    /**
     * 通过userId获取用户信息
     * @param userId
     * @return
     */
    @RequestMapping(value = "/findUser",method = RequestMethod.GET)
    public Object getUser(@RequestParam("userId")String userId){
        return ResultUtil.build(userService.selectUser(userId));
    }

    @RequestMapping(value = "/getAllUser",method = RequestMethod.GET)
    public Object getAllUser(@RequestParam(value = "pageNum", required = false) Integer pageNum,
                             @RequestParam(value = "pageSize", required = false) Integer pageSize){
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        List<UserAccount> list = userService.getAllUser();
        PageInfo pageInfo = new PageInfo(list);
        return ResultUtil.success.data(pageInfo);
    }

    @RequestMapping(value = "/setHeaderUrl", method = RequestMethod.PUT)
    public Object setHeadUrl(@RequestParam("headerUrl")String url,
                             @RequestParam(value = "userId", required = false)String userId){
        if (!TextUtils.isEmpty(userId)){//修改其他用户
            userService.updataHeaderUrl(url, userId);
            return ResultUtil.success.data(userService.selectUser(userId));
        }else {
            if (RequestUtils.tokenIsAuthentication()) {
                userService.updataHeaderUrl(url, RequestUtils.currentLoginUser().getUserId());
                return ResultUtil.success.data(userService.selectUser(RequestUtils.currentLoginUser().getUserId()));
            } else {
                return ResultUtil.fail.status(401).msg(UNLOGIN_STATUS401);
            }
        }
    }

    @RequestMapping(value = "/setRealName", method = RequestMethod.PUT)
    public Object setRealName(@RequestParam("realName")String realName,
                              @RequestParam(value = "userId", required = false)String userId){
        if (!TextUtils.isEmpty(userId)){//修改其他用户
            userService.updataRealName(realName, userId);
            return ResultUtil.success.data(userService.selectUser(userId));
        }else {
            if (RequestUtils.tokenIsAuthentication()){
                userService.updataRealName(realName, RequestUtils.currentLoginUser().getUserId());
                return ResultUtil.success.data(userService.selectUser(RequestUtils.currentLoginUser().getUserId()));
            }else {
                return ResultUtil.fail.status(401).msg(UNLOGIN_STATUS401);
            }
        }

    }

    @RequestMapping(value = "/setIntro", method = RequestMethod.PUT)
    public Object setIntroduction(@RequestParam("intro")String intro,
                                  @RequestParam(value = "userId", required = false)String userId){
        if (!TextUtils.isEmpty(userId)){//修改其他用户
            userService.updataIntro(intro, userId);
            return ResultUtil.success.data(userService.selectUser(userId));
        }else {
            if (RequestUtils.tokenIsAuthentication()) {
                userService.updataIntro(intro, RequestUtils.currentLoginUser().getUserId());
                return ResultUtil.success.data(userService.selectUser(RequestUtils.currentLoginUser().getUserId()));
            } else {
                return ResultUtil.fail.status(401).msg(UNLOGIN_STATUS401);
            }
        }
    }
    @RequestMapping(value = "/deleteUser", method = RequestMethod.DELETE)
    public Object deleteUser(@RequestParam("userId")String userId){
        if (RequestUtils.tokenIsAuthentication()){
            LambdaQueryWrapper<UserAccount> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(UserAccount::getUserId, userId);
            return userService.remove(wrapper) ? ResultUtil.success.msg("删除成功").data("") : ResultUtil.fail.msg("删除失败").data("");
        }else {
            return ResultUtil.fail.status(401).msg(UNLOGIN_STATUS401);
        }

    }
}
