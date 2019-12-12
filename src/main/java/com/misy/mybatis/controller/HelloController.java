package com.misy.mybatis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("th")
public class HelloController {
    @RequestMapping("/login")
    public String getHelloWorld() {
        return "login";
    }
    @RequestMapping("/home")
    public String getHome(){
        return "home";
    }
}
