package com.misy.mybatis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.misy.mybatis.entity.ChatEntity;

import java.util.List;

public interface ChatService extends IService<ChatEntity> {
    List<ChatEntity> qyChatHistory(String sendUserId, String targetUserId);
    List<ChatEntity> qyOffLineChatHistory(String sendUserId, String targetUserId);
}
