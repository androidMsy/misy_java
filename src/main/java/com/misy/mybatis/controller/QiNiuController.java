package com.misy.mybatis.controller;

import com.qiniu.util.Auth;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static com.misy.mybatis.contacts.CommonContacts.*;
@RestController
public class QiNiuController {
    @RequestMapping(value = "/getUpToken",method = RequestMethod.GET)
    public String getQNToken(){
        String accessKey = QN_ACCESS_KEY;
        String secretKey = QN_SECRET_KEY;
        String bucket = QN_BUCKET_NAME;
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        return upToken;
    }
}
