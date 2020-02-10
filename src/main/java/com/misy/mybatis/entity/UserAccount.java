package com.misy.mybatis.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@TableName("user_account")
public class UserAccount implements Serializable {

    private String userId;
    private String username;
    private String password;
    private String token;
    private String realname;
    @TableField("header_url")
    private String headerUrl;
    private String intro;
}
