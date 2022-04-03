package com.jokerpz.jpzspringscurity.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jokerpz.jpzspringscurity.config.userdetail.JpaUserDetailService;
import jdk.nashorn.internal.ir.RuntimeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JpaUserDetailService jpaUserDetailService;

    @Autowired
    private JdbcTokenRepositoryImpl jdbcTokenRepository;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //auth.inMemoryAuthentication()
        //        .withUser("joker")
        //        .password(passwordEncoder.encode("1"))
        //        .roles("admin");
        auth.userDetailsService(jpaUserDetailService);
        super.configure(auth);
    }
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/js/**", "/css/**","/images/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage("/login.html")
                //当发现/login时认为是登录，必须和表单提交的地址一样。去执行UserServiceImpl
                .loginProcessingUrl("/login")
                //登录成功后跳转页面，POST请求
                //.successForwardUrl("/home");
                .successHandler((request, response, authentication) -> {
                    response.setContentType("application/json;charset=utf-8");
                    response.getWriter().write(new ObjectMapper().writeValueAsString(authentication.getPrincipal()));
                })
                .failureHandler((request, response, e) -> {
                    response.setContentType("application/json;charset=utf-8");
                    response.getWriter().write(e.getMessage());
                })
            .and()
            .exceptionHandling()
                .authenticationEntryPoint((request, response, e) -> {
                    response.setContentType("application/json;charset=utf-8");
                    response.getWriter().write("尚未登陆，请先登陆");
                }).accessDeniedHandler((request, response, e) -> {
                    response.setContentType("application/json;charset=utf-8");
                    response.getWriter().write("无此权限");
                })
            .and()
            .logout()
                //.logoutUrl("/logout")
                .logoutSuccessHandler((request, response, authentication) -> {
                    response.setContentType("application/json;charset=utf-8");
                    response.getWriter().write("注销成功");
                })
                //.and()
                //.userDetailsService(jpaUserDetailService)
            .and()
            .rememberMe()
                .key("test")
                .tokenRepository(jdbcTokenRepository)
        ;

        http.authorizeRequests()
                //login.html不需要被认证
                .antMatchers("/login.html").permitAll()
                .antMatchers("/admin/**").hasRole("admin")
                .antMatchers("/user/**").hasRole("user")
                //所有请求都必须被认证，必须登录后被访问
                .anyRequest().authenticated();

        //关闭csrf防护
        http.csrf().disable();
    }
}
