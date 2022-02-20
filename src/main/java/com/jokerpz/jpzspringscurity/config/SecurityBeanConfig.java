package com.jokerpz.jpzspringscurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SecurityBeanConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService inMemoryUserDetailsManager() {
        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
        inMemoryUserDetailsManager.createUser(User.withUsername("joker").password(bCryptPasswordEncoder().encode("1")).roles("admin", "user").build());
        inMemoryUserDetailsManager.createUser(User.withUsername("user").password(bCryptPasswordEncoder().encode("1")).roles("user").build());
        return inMemoryUserDetailsManager;
    }
}
