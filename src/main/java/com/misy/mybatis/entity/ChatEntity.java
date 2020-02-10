package com.misy.mybatis.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("chat_content")
public class ChatEntity implements Serializable {
    private String sendUserId;
    private String targetUserId;
    private String realname;
    private String headerUrl;
    private String content;
    private String extend;
    private byte[] byteContent;
    private int messageType;
    private int isOnline;
    private int isRecept;
    private long createDate;
}
