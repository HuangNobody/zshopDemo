package com.itany.zshop.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/backend/sysuser")
public class SysuserController {

    @RequestMapping("/login")
    public String login(){
        //实现登录
        return "main";
    }
}
