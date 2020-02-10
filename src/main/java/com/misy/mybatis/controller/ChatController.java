package com.misy.mybatis.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.misy.mybatis.MybatisApplication;
import com.misy.mybatis.entity.ChatEntity;
import com.misy.mybatis.service.ChatService;
import com.misy.mybatis.utils.RequestUtils;
import com.misy.mybatis.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

import static com.misy.mybatis.contacts.LoginContacts.UNLOGIN_STATUS401;
import static com.misy.mybatis.utils.RequestUtils.getUserId;
import static com.misy.mybatis.utils.RequestUtils.tokenIsAuthentication;
import static com.misy.mybatis.utils.ResultUtil.RESPONSE_SUCCESS;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    ChatService chatService;

    @RequestMapping(value = "/getChatHistory", method = RequestMethod.GET)
    public Object getChatHistory(@RequestParam("targetUserId") String targetUserId
            ,@RequestParam("pageNum") Integer pageNum
            , @RequestParam("pageSize")Integer pageSize){
        if (!tokenIsAuthentication())return ResultUtil.fail.status(401).msg(UNLOGIN_STATUS401);
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        List<ChatEntity> list = chatService.qyChatHistory(RequestUtils.getUserId(), targetUserId);
        PageInfo pageInfo = new PageInfo(list);
        return ResultUtil.success.msg(RESPONSE_SUCCESS).data(pageInfo);
    }
}
