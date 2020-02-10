package com.misy.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.misy.mybatis.entity.ChatEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMapper extends BaseMapper<ChatEntity> {
    List<ChatEntity> qyChatHistory(String sendUserId, String targetUserId);
}
