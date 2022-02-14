package com.jokerpz.jpzspringscurity.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage("/login.html")
                //当发现/login时认为是登录，必须和表单提交的地址一样。去执行UserServiceImpl
                .loginProcessingUrl("/login1")
                //登录成功后跳转页面，POST请求
                .successForwardUrl("/home");

        http.authorizeRequests()
                //login.html不需要被认证
                .antMatchers("/login.html").permitAll()
                //所有请求都必须被认证，必须登录后被访问
                .anyRequest().authenticated();

        //关闭csrf防护
        http.csrf().disable();
    }
}
