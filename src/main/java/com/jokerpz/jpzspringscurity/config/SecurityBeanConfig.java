package com.jokerpz.jpzspringscurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;

import javax.sql.DataSource;

@Configuration
public class SecurityBeanConfig {

    @Autowired
    private DataSource dataSource;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //@Bean
    public UserDetailsService inMemoryUserDetailsManager() {
        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
        inMemoryUserDetailsManager.createUser(User.withUsername("joker").password(bCryptPasswordEncoder().encode("1")).roles("admin", "user").build());
        inMemoryUserDetailsManager.createUser(User.withUsername("user").password(bCryptPasswordEncoder().encode("1")).roles("user").build());
        return inMemoryUserDetailsManager;
    }

    //@Bean
    public UserDetailsService jdbcUserDetailsManager() {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager();
        jdbcUserDetailsManager.setDataSource(dataSource);
        if (!jdbcUserDetailsManager.userExists("jdbcAdmin")) {
            jdbcUserDetailsManager.createUser(User.withUsername("jdbcAdmin").password(bCryptPasswordEncoder().encode("1")).roles("admin", "user").build());
        }
        if (!jdbcUserDetailsManager.userExists("jdbcUser")) {
            jdbcUserDetailsManager.createUser(User.withUsername("jdbcUser").password(bCryptPasswordEncoder().encode("1")).roles("admin").build());
        }
        return jdbcUserDetailsManager;
    }

    @Bean
    public JdbcTokenRepositoryImpl jdbcTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        //jdbcTokenRepository.setCreateTableOnStartup(true);
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }
}
