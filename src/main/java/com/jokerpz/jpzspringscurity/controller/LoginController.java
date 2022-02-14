package com.jokerpz.jpzspringscurity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
    @RequestMapping("/login1")
    public String login() {
        return "登陆成功";
    }
    @RequestMapping("/home")
    public String home() {
        return "redirect:/home.html";
    }
}
