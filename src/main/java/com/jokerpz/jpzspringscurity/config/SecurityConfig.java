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
                //?????????/login?????????????????????????????????????????????????????????????????????UserServiceImpl
                .loginProcessingUrl("/login")
                //??????????????????????????????POST??????
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
                    response.getWriter().write("???????????????????????????");
                }).accessDeniedHandler((request, response, e) -> {
                    response.setContentType("application/json;charset=utf-8");
                    response.getWriter().write("????????????");
                })
            .and()
            .logout()
                //.logoutUrl("/logout")
                .logoutSuccessHandler((request, response, authentication) -> {
                    response.setContentType("application/json;charset=utf-8");
                    response.getWriter().write("????????????");
                })
                //.and()
                //.userDetailsService(jpaUserDetailService)
            .and()
            .rememberMe()
                .key("test")
                .tokenRepository(jdbcTokenRepository)
        ;

        http.authorizeRequests()
                //login.html??????????????????
                .antMatchers("/login.html").permitAll()
                .antMatchers("/admin/**").hasRole("admin")
                .antMatchers("/user/**").hasRole("user")
                //?????????????????????????????????????????????????????????
                .anyRequest().authenticated();

        //??????csrf??????
        http.csrf().disable();
    }
}
