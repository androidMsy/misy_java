package com.misy.mybatis.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserAccount implements Serializable {

    private String userId;
    private String username;
    private String password;
    private String token;
    private String realname;
    private String header_url;
    private String intro;
}
