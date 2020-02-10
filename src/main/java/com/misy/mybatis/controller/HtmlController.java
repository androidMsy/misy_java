package com.misy.mybatis.controller;

import com.misy.mybatis.utils.RequestUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("th")
public class HtmlController {
    @RequestMapping("/login")
    public String getHelloWorld() {
        return "login";
    }
    @RequestMapping("/home")
    public String getHome(){
        if (!RequestUtils.tokenIsAuthentication())return "login";
        return "home";
    }
    @RequestMapping("/chat")
    public String getChat(){
        if (!RequestUtils.tokenIsAuthentication())return "login";
        return "chat";
    }
}
