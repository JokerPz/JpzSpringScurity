package com.jokerpz.jpzspringscurity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class LoginController {
    @RequestMapping("/login")
    public String login() {
        return "登陆成功";
    }

    @RequestMapping("/home")
    public String home() {
        return "hello world~";
    }

    @RequestMapping("/toError")
    public String error() {
        return "redirect:/error.html";
    }

    @GetMapping("/admin/hello")
    @ResponseBody
    public String admin() {
        return "hello admin";
    }

    @GetMapping("/user/hello")
    @ResponseBody
    public String user() {
        return "hello user";
    }
}
