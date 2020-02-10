package com.misy.mybatis.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.misy.mybatis.entity.ChatEntity;
import com.misy.mybatis.mapper.ChatMapper;
import com.misy.mybatis.service.ChatService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatServiceImpl extends ServiceImpl<ChatMapper, ChatEntity> implements ChatService {
    @Override
    public List<ChatEntity> qyChatHistory(String sendUserId, String targetUserId) {
        return getBaseMapper().qyChatHistory(sendUserId, targetUserId);
    }

    @Override
    public List<ChatEntity> qyOffLineChatHistory(String sendUserId, String targetUserId) {
        return null;
    }
}
