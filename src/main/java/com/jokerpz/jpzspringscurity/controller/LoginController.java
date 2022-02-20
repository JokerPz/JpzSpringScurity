package com.jokerpz.jpzspringscurity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
    @RequestMapping("/login")
    public String login() {
        return "登陆成功";
    }
    @RequestMapping("/home")
    public String home() {
        return "redirect:/home.html";
    }

    @RequestMapping("/admin/hello")
    public String admin() {
        return "hello admin";
    }

    @RequestMapping("/user/hello")
    public String user() {
        return "hello user";
    }
}
