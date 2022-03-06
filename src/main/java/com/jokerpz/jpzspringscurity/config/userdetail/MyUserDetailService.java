package com.jokerpz.jpzspringscurity.config.userdetail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

//@Service
public class MyUserDetailService implements UserDetailsService {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String encodePassword = bCryptPasswordEncoder.encode("1");
        // 判断用户名是否符合要求
        if (Objects.equals(username, "joker")) {
            return new User(username, encodePassword, AuthorityUtils.commaSeparatedStringToAuthorityList("admin,normal"));
        }
        return null;
    }
}
